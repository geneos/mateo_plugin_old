package ar.com.geneos.mrp.plugin.model;

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/

import java.io.File;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MBPartner;
import org.openXpertya.model.MClient;
import org.openXpertya.model.MCost;
import org.openXpertya.model.MDocType;
import org.openXpertya.model.MLocator;
import org.openXpertya.model.MOrder;
import org.openXpertya.model.MPeriod;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MProductPO;
import org.openXpertya.model.MStorage;
import org.openXpertya.model.MTransaction;
import org.openXpertya.model.MUOM;
import org.openXpertya.model.MWarehouse;
import org.openXpertya.model.ModelValidationEngine;
import org.openXpertya.model.Query;
import org.openXpertya.print.ReportEngine;
import org.openXpertya.process.DocAction;
import org.openXpertya.process.DocumentEngine;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;
import org.openXpertya.util.Msg;
import org.openXpertya.util.TimeUtil;

import ar.com.geneos.mrp.plugin.tool.engine.CostDimension;
import ar.com.geneos.mrp.plugin.tool.engine.CostingMethodFactory;
import ar.com.geneos.mrp.plugin.tool.engine.StandardCostingMethod;
import ar.com.geneos.mrp.plugin.tool.engine.StorageEngine;
import ar.com.geneos.mrp.plugin.util.MUMProduct;

/**
 * PP Cost Collector Model
 *
 * @author victor.perez@e-evolution.com, e-Evolution http://www.e-evolution.com
 *         <li>Original contributor of Manufacturing Standard Cost <li>FR [
 *         2520591 ] Support multiples calendar for Org
 * @see http 
 *      ://sourceforge.net/tracker2/?func=detail&atid=879335&aid=2520591&group_id
 *      =176962
 * 
 * @author Teo Sarca, www.arhipac.ro
 * @version $Id: MPPCostCollector.java,v 1.1 2004/06/19 02:10:34 vpj-cd Exp $
 */
public class MPPCostCollector extends LP_PP_Cost_Collector implements DocAction, IDocumentLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2329378809441860241L;

	protected static transient CLogger log = CLogger.getCLogger(MPPCostCollector.class);
	
	public static final String DOCBASETYPE_ManufacturingCostCollector = "MOR";

	public static final String DOCACTION_None = null;

	/** CostCollectorType AD_Reference_ID=53287 */
	public static final int COSTCOLLECTORTYPE_AD_Reference_ID = 53287;
	/** Material Receipt = 100 */
	public static final String COSTCOLLECTORTYPE_MaterialReceipt = "100";
	/** Material Receipt = 105 */
	public static final String COSTCOLLECTORTYPE_CoProductReceipt = "105";
	/** Component Issue = 110 */
	public static final String COSTCOLLECTORTYPE_ComponentIssue = "110";
	/** Usege Variance = 120 */
	public static final String COSTCOLLECTORTYPE_UsegeVariance = "120";
	/** Method Change Variance = 130 */
	public static final String COSTCOLLECTORTYPE_MethodChangeVariance = "130";
	/** Rate Variance = 140 */
	public static final String COSTCOLLECTORTYPE_RateVariance = "140";
	/** Mix Variance = 150 */
	public static final String COSTCOLLECTORTYPE_MixVariance = "150";
	/** Activity Control = 160 */
	public static final String COSTCOLLECTORTYPE_ActivityControl = "160";

	public static final String COSTCOLLECTORTYPE_ComponentReturn = "115";

	/**
	 * 
	 */

	public static MPPCostCollector createVarianceCostCollector(MPPCostCollector cc, String CostCollectorType) {
		MPPCostCollector costCollectorVariance = new MPPCostCollector(cc.getCtx(), 0, cc.get_TrxName());
		MPPCostCollector.copyValues(cc, costCollectorVariance);
		costCollectorVariance.setProcessing(false);
		costCollectorVariance.setProcessed(false);
		costCollectorVariance.setDocStatus(MPPCostCollector.STATUS_Drafted);
		costCollectorVariance.setDocAction(MPPCostCollector.ACTION_Complete);
		costCollectorVariance.setCostCollectorType(CostCollectorType);
		costCollectorVariance.setDocumentNo(null); // reset
		costCollectorVariance.save();
		return costCollectorVariance;
	}

	/**
	 * get Cost Collector That not was generate by inventory transaction
	 * 
	 * @param product
	 * @param AD_Client_ID
	 * @param dateAcct
	 * @return Collection the Cost Collector
	 */
	public static List<MPPCostCollector> getCostCollectorNotTransaction(Properties ctx, int M_Product_ID, int AD_Client_ID, Timestamp dateAcct, String trxName) {
		List<Object> params = new ArrayList();
		final StringBuffer whereClause = new StringBuffer();
		whereClause.append(MPPCostCollector.COLUMNNAME_costcollectortype + " NOT IN ('100','110') AND ");
		if (M_Product_ID > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_M_Product_ID + "=? AND ");
			params.add(M_Product_ID);
		}

		whereClause.append(MPPCostCollector.COLUMNNAME_DateAcct + ">=?");
		params.add(dateAcct);

		return new Query(ctx, LP_PP_Cost_Collector.Table_Name, whereClause.toString(), trxName).setClient_ID().setParameters(params).list();

	}

	/**
	 * Create & Complete Cost Collector
	 * 
	 * @param order
	 * @param productId
	 * @param locatorId
	 * @param attributeSetInstanceId
	 * @param resourceId
	 * @param orderBOMLineId
	 * @param orderNodeId
	 * @param docTypeId
	 * @param costCollectorType
	 * @param movementDate
	 * @param qty
	 * @param scrap
	 * @param reject
	 * @param durationSetup
	 * @param duration
	 * @return completed cost collector
	 */
	public static MPPCostCollector createCollector(MPPOrder order, int productId, int locatorId, int attributeSetInstanceId, int resourceId,
			int orderBOMLineId, int orderNodeId, int docTypeId, String costCollectorType, Timestamp movementDate, BigDecimal qty, BigDecimal scrap,
			BigDecimal reject, int durationSetup, BigDecimal duration) {
		MPPCostCollector cc = new MPPCostCollector(order);
		cc.setPP_Order_Bomline_ID(orderBOMLineId);
		cc.setPP_Order_Node_ID(orderNodeId);
		cc.setC_DocType_ID(docTypeId);
		cc.setC_DocTypeTarget_ID(docTypeId);
		cc.setCostCollectorType(costCollectorType);
		cc.setDocAction(MPPCostCollector.ACTION_Complete);
		cc.setDocStatus(MPPCostCollector.STATUS_Drafted);
		cc.setIsActive(true);
		cc.setM_Locator_ID(locatorId);
		cc.setM_AttributeSetInstance_ID(attributeSetInstanceId);
		cc.setS_Resource_ID(resourceId);
		cc.setMovementDate(movementDate);
		cc.setDateAcct(movementDate);
		cc.setMovementQty(qty);
		cc.setScrappedQty(scrap);
		cc.setQtyReject(reject);
		cc.setSetupTimeReal(new BigDecimal(durationSetup));
		cc.setDurationReal(duration);
		cc.setPosted(false);
		cc.setProcessed(false);
		cc.setProcessing(false);
		cc.setUser1_ID(order.getUser1_ID());
		cc.setUser2_ID(order.getUser2_ID());
		cc.setM_Product_ID(productId);
		if (orderNodeId > 0) {
			cc.setIsSubcontracting(orderNodeId);
		}
		// If this is an material issue, we should use BOM Line's UOM
		if (orderBOMLineId > 0) {
			cc.setC_UOM_ID(0); // we set the BOM Line UOM on beforeSave
		}
		cc.save();
		if (!cc.processIt(MPPCostCollector.ACTION_Complete)) {
			throw new IllegalStateException(cc.getProcessMsg());
		}
		cc.save();
		return cc;
	}

	public static void setPP_Order(LP_PP_Cost_Collector cc, MPPOrder order) {
		cc.setPP_Order_ID(order.getPP_Order_ID());
		cc.setPP_Order_Workflow_ID(order.getMPPOrderWorkflow().getID());
		cc.setAD_Org_ID(order.getAD_Org_ID());
		cc.setM_Warehouse_ID(order.getM_Warehouse_ID());
		cc.setAD_OrgTrx_ID(order.getAD_OrgTrx_ID());
		cc.setC_Activity_ID(order.getC_Activity_ID());
		cc.setC_Campaign_ID(order.getC_Campaign_ID());
		cc.setC_Project_ID(order.getC_Project_ID());
		cc.setDescription(order.getDescription());
		cc.setS_Resource_ID(order.getS_Resource_ID());
		cc.setM_Product_ID(order.getM_Product_ID());
		cc.setC_UOM_ID(order.getC_UOM_ID());
		cc.setM_AttributeSetInstance_ID(order.getM_AttributeSetInstance_ID());
		cc.setMovementQty(order.getQtyOrdered());
	}

	/**
	 * Standard Constructor
	 *
	 * @param ctx
	 *            context
	 * @param costCollectorId
	 *            id
	 */
	public MPPCostCollector(Properties ctx, int costCollectorId, String trxName) {
		super(ctx, costCollectorId, trxName);
		if (costCollectorId == 0) {
			// setC_DocType_ID(0);
			setDocStatus(STATUS_Drafted); // DR
			setDocAction(ACTION_Complete); // CO
			setMovementDate(new Timestamp(System.currentTimeMillis())); // @#Date@
			setIsActive(true);
			setPosted(false);
			setProcessing(false);
			setProcessed(false);
		}
	} // MPPCostCollector

	/**
	 * Load Constructor
	 *
	 * @param ctx
	 *            context
	 * @param rs
	 *            result set
	 */
	public MPPCostCollector(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	} // MPPCostCollector

	/**
	 * Load Constructor
	 *
	 * @param order
	 */
	public MPPCostCollector(MPPOrder order) {
		this(order.getCtx(), 0, order.get_TrxName());
		setPP_Order(this, order);
		m_order = order;
	} // MPPCostCollector

	/**
	 * Add to Description
	 *
	 * @param description
	 *            text
	 */
	public void addDescription(String description) {
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	} // addDescription

	public void setC_DocTypeTarget_ID(String docBaseType) {
		MDocType[] doc = MDocType.getOfDocBaseType(getCtx(), docBaseType);
		if (doc == null) {
			throw new IllegalStateException("DocType not Found " + docBaseType);
		} else {
			setC_DocTypeTarget_ID(doc[0].getID());
		}
	}

	// @Override
	public void setProcessed(boolean processed) {
		super.setProcessed(processed);
		if (getID() == 0)
			return;
		char value = processed ? 'Y' : 'N';
		final String sql = "UPDATE PP_Cost_Collector SET Processed='" + value + "' WHERE PP_Cost_Collector_ID=" + getID();
		int noLine = 0;
		try {
			noLine = DB.executeUpdateEx(sql, get_TrxName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.fine("setProcessed - " + processed + " - Lines=" + noLine);
	} // setProcessed

	// @Override
	public boolean processIt(String processAction) {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		// Migración Libero add log
		// @autor:pepo
		return engine.processIt(processAction, getDocAction(), log);
	} // processIt

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	/** Manufacturing Order **/
	private MPPOrder m_order = null;

	/** Manufacturing Order Activity **/
	private MPPOrderNode m_orderNode = null;

	/** Manufacturing Order BOM Line **/
	private MPPOrderBOMLine m_bomLine = null;
	/** Actual Cost **/
	private BigDecimal priceActual = Env.ZERO;

	// @Override
	public boolean unlockIt() {
		log.info("unlockIt - " + toString());
		setProcessing(false);
		return true;
	} // unlockIt

	// @Override
	public boolean invalidateIt() {
		log.info("invalidateIt - " + toString());
		setDocAction(ACTION_Prepare);
		return true;
	} // invalidateIt

	// @Override
	public String prepareIt() {
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null) {
			return DocAction.STATUS_Invalid;
		}

		MDocType dt = MDocType.get(getCtx(), getC_DocTypeTarget_ID());

		if (!MPeriod.isOpen(getCtx(), getDateAcct(), dt.getDocBaseType())) {
			throw new IllegalStateException("Period Close Exception " + dt.getDocBaseType() + " - " + getDateAcct());
		}

		// Convert/Check DocType
		setC_DocType_ID(getC_DocTypeTarget_ID());

		//
		// Operation Activity
		if (isActivityControl()) {
			MPPOrderNode activity = getPP_Order_Node();
			if (MPPOrderNode.DOCACTION_Complete.equals(activity.getDocStatus())) {
				throw new IllegalStateException("Activity Processed Exception " + activity);
			}

			if (activity.isSubcontracting()) {
				if (MPPOrderNode.DOCSTATUS_InProgress.equals(activity.getDocStatus()) && MPPCostCollector.STATUS_InProgress.equals(getDocStatus())) {
					return MPPOrderNode.DOCSTATUS_InProgress;
				} else if (MPPOrderNode.DOCSTATUS_InProgress.equals(activity.getDocStatus()) && MPPCostCollector.STATUS_Drafted.equals(getDocStatus())) {
					throw new IllegalStateException("Activity process exception: " + activity.getName());
				}
				m_processMsg = createPO(activity);
				m_justPrepared = false;
				activity.setInProgress(this);
				activity.save();
				return STATUS_InProgress;
			}

			activity.setInProgress(this);
			activity.setQtyDelivered(activity.getQtyDelivered().add(getMovementQty()));
			activity.setQtyScrap(activity.getQtyScrap().add(getScrappedQty()));
			activity.setQtyReject(activity.getQtyReject().add(getQtyReject()));
			activity.setDurationReal(activity.getDurationReal() + getDurationReal().intValueExact());
			activity.setSetupTimeReal(activity.getSetupTimeReal() + getSetupTimeReal().intValueExact());
			activity.save();

			// report all activity previews to milestone activity
			if (activity.isMilestone()) {
				MPPOrderWorkflow order_workflow = activity.getMPPOrderWorkflow();
				order_workflow.closeActivities(activity, getMovementDate(), true);
			}
		}
		// Issue
		else if (isIssue() || isCoProduct()) {
			MProduct product = getM_Product();
			if (getM_AttributeSetInstance_ID() == 0 && MUMProduct.isASIMandatory(product, false, getAD_Org_ID())) {
				throw new IllegalStateException("@M_AttributeSet_ID@ @IsMandatory@ @M_Product_ID@=" + product.getValue());
			}
		}
		// Receipt
		else if (isReceipt()) {
			MProduct product = getM_Product();
			if (getM_AttributeSetInstance_ID() == 0 && MUMProduct.isASIMandatory(product, true, getAD_Org_ID())) {
				throw new IllegalStateException("@M_AttributeSet_ID@ @IsMandatory@ @M_Product_ID@=" + product.getValue());
			}
		}
		// Return
		else if (isReturn()) {
			MProduct product = getM_Product();
			MPPOrderBOMLine bomline =getPP_Order_BOMLine();
			BigDecimal qtyDelivered = bomline.getQtyDelivered(getM_AttributeSetInstance_ID());
			if ( getMovementQty().negate().compareTo(qtyDelivered) == 1 ) {
				throw new IllegalStateException("@QtyDelivered@ ("+qtyDelivered+") menor que cantidad a devolver ("+getMovementQty().negate()+")@M_Product_ID@=" + product.getValue());
			}
		}

		m_justPrepared = true;
		setDocAction(ACTION_Complete);

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null) {
			return DocAction.STATUS_Invalid;
		}

		return DocAction.STATUS_InProgress;
	} // prepareIt

	// @Override
	public boolean approveIt() {
		log.info("approveIt - " + toString());
		// setIsApproved(true);
		return true;
	} // approveIt

	// @Override
	public boolean rejectIt() {
		log.info("rejectIt - " + toString());
		// setIsApproved(false);
		return true;
	} // rejectIt

	// @Override
	public String completeIt() {
		// Re-Check
		if (!m_justPrepared) {
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//
		// Material Issue (component issue, method change variance, mix
		// variance)
		// Material Receipt
		if (isIssue() || isReceipt() || isReturn() || isCoProduct()) {
			// Stock Movement
			MProduct product = getM_Product();
			if (product != null && product.isStocked() && !isVariance()) {
				StorageEngine.createTrasaction(this, getMovementType(), getMovementDate(), getMovementQty(), false, // IsReversal=false
						getM_Warehouse_ID(), getPP_Order().getM_AttributeSetInstance_ID(), // Reservation
																							// ASI
						getPP_Order().getM_Warehouse_ID(), // Reservation
															// Warehouse
						false // IsSOTrx=false
						);
			} // stock movement
			
			
			if (isIssue() || isReturn() || isCoProduct()) {
				MPPOrderBOMLine obomline = getPP_Order_BOMLine();

				// Chequeo que la cantidad entregada no quede negativa
				if (obomline.getQtyDelivered().add(getMovementQty()).signum() == -1)
					throw new IllegalStateException("PPCostCollector.completeIt: Cantidad entregada no puede quedar en negativo"+this);

				// Update PP Order Line
				obomline.setQtyDelivered(obomline.getQtyDelivered().add(getMovementQty()));
				obomline.setQtyScrap(obomline.getQtyScrap().add(getScrappedQty()));
				obomline.setQtyReject(obomline.getQtyReject().add(getQtyReject()));
				obomline.setDateDelivered(getMovementDate()); // overwrite=last
				obomline.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
				log.fine("OrderLine - Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
				obomline.save();
				log.fine("OrderLine -> Reserved=" + obomline.getQtyReserved() + ", Delivered=" + obomline.getQtyDelivered());
			}
			if (isReceipt()) {
				final MPPOrder order = getPP_Order();
				
				if (order.getQtyDelivered().add(getMovementQty()).signum() == -1)
					throw new IllegalStateException("PPCostoCollector.completeIt: Cantidad entregada no puede quedar en negativo"+this);

				// Update PP Order Qtys
				order.setQtyDelivered(order.getQtyDelivered().add(getMovementQty()));
				order.setQtyScrap(order.getQtyScrap().add(getScrappedQty()));
				order.setQtyReject(order.getQtyReject().add(getQtyReject()));
				
				// QtyReserved Se actualiza en el afterSave -> orderStock de MPPOrder
				//order.setQtyReserved(order.getQtyReserved().subtract(getMovementQty()));

				//
				// Update PP Order Dates
				order.setDateDelivered(getMovementDate()); // overwrite=last
				if (order.getDateStart() == null) {
					order.setDateStart(getDateStart());
				}
				if (order.getQtyOpen().signum() <= 0) {
					order.setDateFinish(getDateFinish());
				}
				order.save();
			}
		}
		//
		// Activity Control
		else if (isActivityControl()) {
			MPPOrderNode activity = getPP_Order_Node();
			if (activity.isProcessed()) {
				throw new IllegalStateException("Activity Processed Exception " + activity);
			}

			if (isSubcontracting()) {
				String whereClause = LP_C_OrderLine.COLUMNNAME_PP_Cost_Collector_ID + "=?";
				Collection<LP_C_OrderLine> olines = new Query(getCtx(), LP_C_OrderLine.Table_Name, whereClause, get_TrxName()).setParameters(
						new Object[] { getID() }).list();
				String DocStatus = MPPOrderNode.DOCSTATUS_Completed;
				StringBuffer msg = new StringBuffer("The quantity do not is complete for next Purchase Order : ");
				for (LP_C_OrderLine oline : olines) {
					if (oline.getQtyDelivered().compareTo(oline.getQtyOrdered()) < 0) {
						DocStatus = MPPOrderNode.DOCSTATUS_InProgress;
					}
					msg.append(oline.getParent().getDocumentNo()).append(",");
				}

				if (MPPOrderNode.DOCSTATUS_InProgress.equals(DocStatus)) {
					m_processMsg = msg.toString();
					return DocStatus;
				}
				setProcessed(true);
				setDocAction(MPPOrderNode.DOCACTION_Close);
				setDocStatus(MPPOrderNode.DOCSTATUS_Completed);
				activity.completeIt();
				activity.save();
				m_processMsg = Msg.translate(getCtx(), "PP_Order_ID") + ": " + getPP_Order().getDocumentNo() + " "
						+ Msg.translate(getCtx(), "PP_Order_Node_ID") + ": " + getPP_Order_Node().getValue();
				return DocStatus;
			} else {
				
				/*
				 * Comentamos la estructura de cálculos de costos original para simplificar a la estructura del módulo MRP versión 1.0
				 * Geneos
				 * 
				 *
				 
				final StandardCostingMethod standardCostingMethod = (StandardCostingMethod) CostingMethodFactory.get().getCostingMethod(
						LP_M_CostType.COSTINGMETHOD_StandardCosting);
				
				standardCostingMethod.createActivityControl(this);
				*/
				
				final MProduct product = MUMProduct.forS_Resource_ID(getCtx(), getS_Resource_ID(), null);
				final MAcctSchema as = MClient.get(getCtx(), getAD_Client_ID()).getAcctSchema();
				
				final CostDimension d = new CostDimension(product, as, as.getM_CostType_ID(), getAD_Org_ID(), getM_Warehouse_ID(), getM_AttributeSetInstance_ID(),
						CostDimension.ANY);
				final MPPOrder order = getPP_Order();
				
				Collection<MCost> costs = d.toQuery(MCost.class, get_TrxName()).list();
				
				for (MCost cost : costs) {
					/*
					 *  Crear dimensión de costos para la orden desde un elemento del colector de costos
					 *  Geneos
					 *  
					 */
					
					MPPOrderCost.createOrderCostDimensionFromCollector(order.getID(), cost, this);
				}			
				
				if (activity.getQtyDelivered().compareTo(activity.getQtyRequired()) >= 0) {
					activity.closeIt();
					activity.save();
				}
			}
		}
		//
		// Usage Variance (material)
		else if (isCostCollectorType(COSTCOLLECTORTYPE_UsegeVariance) && getPP_Order_Bomline_ID() > 0) {
			MPPOrderBOMLine orderBOMLine = getPP_Order_BOMLine();
			orderBOMLine.setQtyScrap(orderBOMLine.getQtyScrap().add(getScrappedQty()));
			orderBOMLine.setQtyReject(orderBOMLine.getQtyReject().add(getQtyReject()));
			// orderBOMLine.setDateDelivered(getMovementDate()); //
			// overwrite=last
			orderBOMLine.setM_AttributeSetInstance_ID(getM_AttributeSetInstance_ID());
			log.fine("OrderLine - Reserved=" + orderBOMLine.getQtyReserved() + ", Delivered=" + orderBOMLine.getQtyDelivered());
			orderBOMLine.save();
			log.fine("OrderLine -> Reserved=" + orderBOMLine.getQtyReserved() + ", Delivered=" + orderBOMLine.getQtyDelivered());
			// CostEngineFactory.getCostEngine(getAD_Client_ID()).createCostDetail(null,
			// this);
			final StandardCostingMethod standardCostingMethod = (StandardCostingMethod) CostingMethodFactory.get().getCostingMethod(
					LP_M_CostType.COSTINGMETHOD_StandardCosting);
			standardCostingMethod.createUsageVariances(this);
		}
		//
		// Usage Variance (resource)
		else if (isCostCollectorType(COSTCOLLECTORTYPE_UsegeVariance) && getPP_Order_Node_ID() > 0) {
			MPPOrderNode activity = getPP_Order_Node();
			activity.setDurationReal(activity.getDurationReal() + getDurationReal().intValueExact());
			activity.setSetupTimeReal(activity.getSetupTimeReal() + getSetupTimeReal().intValueExact());
			activity.save();
			final StandardCostingMethod standardCostingMethod = (StandardCostingMethod) CostingMethodFactory.get().getCostingMethod(
					LP_M_CostType.COSTINGMETHOD_StandardCosting);
			standardCostingMethod.createActivityControl(this);
		} else {
			; // nothing
		}
		//
		// CostEngineFactory.getCostEngine(getAD_Client_ID()).createRateVariances(this);
		// CostEngineFactory.getCostEngine(getAD_Client_ID()).createMethodVariances(this);

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		//
		setProcessed(true);
		setDocAction(ACTION_Close);
		setDocStatus(STATUS_Completed);

		return DocAction.STATUS_Completed;
	} // completeIt
	
	

	// @Override
	public boolean voidIt() {
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null){
			setDocStatus(DocAction.STATUS_Invalid);
			return false;
		}
		
		MProduct product = getM_Product();
		//Chequeo que el stock no se haya usado
		if ( (isReceipt() || isCoProduct()) && product != null && product.isStocked() && !isVariance()){
			MStorage storage = MStorage.get(getCtx(), getM_Locator_ID(), getM_Product_ID(), getM_AttributeSetInstance_ID(), get_TrxName());
			BigDecimal available = storage.getQtyOnHand().subtract(storage.getQtyReserved());
			if (getMovementQty().compareTo(available) == 1){
				log.log(Level.SEVERE, this+" :No se puede anular recepcion, no existe disponible. Entregado: "+getMovementQty()+" Disponible: "+available);
				return false;
			}
				
		}
		
		if (isIssue() || isReceipt() || isReturn() || isCoProduct()) {
			// Counter Stock Movement
			if (product != null && product.isStocked() && !isVariance()) {
				StorageEngine.createTrasaction(this, getCounterMovementType(), getMovementDate(), getMovementQty(), false, // IsReversal=false
						getM_Warehouse_ID(), getPP_Order().getM_AttributeSetInstance_ID(), // Reservation
																							// ASI
						getPP_Order().getM_Warehouse_ID(), // Reservation
															// Warehouse
						false // IsSOTrx=false
						);
			} // Counter stock movement
		}
		
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null){
			setDocStatus(STATUS_Voided);
			return false;
		}
		
		setDocAction(ACTION_None);		
		return true;
	} // voidIt

	// @Override
	public boolean closeIt() {
		log.info("closeIt - " + toString());
		setDocAction(DOCACTION_None);
		return true;
	} // closeIt

	// @Override
	public boolean reverseCorrectIt() {
		return false;
	}

	// @Override
	public boolean reverseAccrualIt() {
		return false;
	}

	// @Override
	public boolean reActivateIt() {
		return false;
	}

	// @Override
	public String getSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append(getDescription());
		return sb.toString();
	}

	// @Override
	public String getProcessMsg() {
		return m_processMsg;
	}

	// @Override
	public int getDoc_User_ID() {
		return getCreatedBy();
	}

	// @Override
	public int getC_Currency_ID() {
		return 0;
	}

	// @Override
	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	}

	// @Override
	public File createPDF() {
		try {
			File temp = File.createTempFile(get_TableName() + getID() + "_", ".pdf");
			return createPDF(temp);
		} catch (Exception e) {
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	} // getPDF

	/**
	 * Create PDF file
	 *
	 * @param file
	 *            output file
	 * @return file if success
	 */
	public File createPDF(File file) {
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.ORDER, getPP_Order_ID());
		if (re == null)
			return null;
		return re.getPDF(file);
	} // createPDF

	// @Override
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	} // getDocumentInfo

	@Override
	protected boolean beforeSave(boolean newRecord) {
		// Set default locator, if not set and we have the warehouse:
		if (getM_Locator_ID() <= 0 && getM_Warehouse_ID() > 0) {
			MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			MLocator loc = wh.getDefaultLocator();
			if (loc != null) {
				setM_Locator_ID(loc.getID());
			}
		}
		//
		if (isIssue()) {
			if (getPP_Order_Bomline_ID() <= 0) {
				throw new IllegalStateException("Fill Mandatory Exception" + COLUMNNAME_PP_Order_BOMLine_ID);
			}
			// If no UOM, use the UOM from BOMLine
			if (getC_UOM_ID() <= 0) {
				setC_UOM_ID(getPP_Order_BOMLine().getC_UOM_ID());
			}
			// If Cost Collector UOM differs from BOM Line UOM then throw
			// exception because this conversion is not supported yet
			if (getC_UOM_ID() != getPP_Order_BOMLine().getC_UOM_ID()) {
				throw new IllegalStateException("@PP_Cost_Collector_ID@ @C_UOM_ID@ <> @PP_Order_BOMLine_ID@ @C_UOM_ID@");
			}
		}
		//
		if (isActivityControl() && getPP_Order_Node_ID() <= 0) {
			throw new IllegalStateException("Fill Mandatory Exception " + COLUMNNAME_PP_Order_Node_ID);
		}
		return true;
	}

	// @Override
	public MPPOrderNode getPP_Order_Node() {
		int node_id = getPP_Order_Node_ID();
		if (node_id <= 0) {
			m_orderNode = null;
			return null;
		}
		if (m_orderNode == null || m_orderNode.getID() != node_id) {
			m_orderNode = new MPPOrderNode(getCtx(), node_id, get_TrxName());
		}
		return m_orderNode;
	}

	// @Override
	public MPPOrderBOMLine getPP_Order_BOMLine() {
		int id = getPP_Order_Bomline_ID();
		if (id <= 0) {
			m_bomLine = null;
			return null;
		}
		if (m_bomLine == null || m_bomLine.getID() != id) {
			m_bomLine = new MPPOrderBOMLine(getCtx(), id, get_TrxName());
		}
		m_bomLine.set_TrxName(get_TrxName());
		return m_bomLine;
	}

	// @Override
	public MPPOrder getPP_Order() {
		int id = getPP_Order_ID();
		if (id <= 0) {
			m_order = null;
			return null;
		}
		if (m_order == null || m_order.getID() != id) {
			m_order = new MPPOrder(getCtx(), id, get_TrxName());
		}
		return m_order;
	}

	/**
	 * Get Duration Base in Seconds
	 * 
	 * @return duration unit in seconds
	 * @see MPPOrderWorkflow#getDurationBaseSec()
	 */
	public long getDurationBaseSec() {
		return getPP_Order().getMPPOrderWorkflow().getDurationBaseSec();
	}

	/**
	 * @return Activity Control Report Start Date
	 */
	public Timestamp getDateStart() {
		double duration = getDurationReal().doubleValue();
		if (duration != 0) {
			long durationMillis = (long) (getDurationReal().doubleValue() * getDurationBaseSec() * 1000.0);
			return new Timestamp(getMovementDate().getTime() - durationMillis);
		} else {
			return getMovementDate();
		}
	}

	/**
	 * @return Activity Control Report End Date
	 */
	public Timestamp getDateFinish() {
		return getMovementDate();
	}

	/**
	 * Create Purchase Order (in case of Subcontracting)
	 * 
	 * @param activity
	 */
	private String createPO(MPPOrderNode activity) {
		String msg = "";
		HashMap<Integer, MOrder> orders = new HashMap<Integer, MOrder>();
		//
		String whereClause = MPPOrderNodeProduct.COLUMNNAME_PP_Order_Node_ID + "=?" + " AND " + MPPOrderNodeProduct.COLUMNNAME_IsSubcontracting + "=?";
		Collection<MPPOrderNodeProduct> subcontracts = new Query(getCtx(), MPPOrderNodeProduct.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { activity.getID(), true }).setOnlyActiveRecords(true).list();

		for (MPPOrderNodeProduct subcontract : subcontracts) {
			//
			// If Product is not Purchased or is not Service, then it is not a
			// subcontracting candidate [SKIP]
			MProduct product = MProduct.get(getCtx(), subcontract.getM_Product_ID());
			if (!product.isPurchased() || !MProduct.PRODUCTTYPE_Service.equals(product.getProductType()))
				throw new IllegalStateException("The Product: " + product.getName() + " Do not is Purchase or Service Type");

			//
			// Find Vendor and Product PO data
			int C_BPartner_ID = activity.getC_BPartner_ID();
			MProductPO product_po = null;
			for (MProductPO ppo : MProductPO.getOfProduct(getCtx(), product.getID(), null)) {
				if (C_BPartner_ID == ppo.getC_BPartner_ID()) {
					C_BPartner_ID = ppo.getC_BPartner_ID();
					product_po = ppo;
					break;
				}
				if (ppo.isCurrentVendor() && ppo.getC_BPartner_ID() != 0) {
					C_BPartner_ID = ppo.getC_BPartner_ID();
					product_po = ppo;
					break;
				}
			}
			if (C_BPartner_ID <= 0 || product_po == null) {
				throw new IllegalStateException("No Vendor For Product Exception" + product.getName());
			}
			//
			// Calculate Lead Time
			Timestamp today = new Timestamp(System.currentTimeMillis());
			Timestamp datePromised = TimeUtil.addDays(today, product_po.getDeliveryTime_Promised());
			//
			// Get/Create Purchase Order Header
			MOrder order = orders.get(C_BPartner_ID);
			if (order == null) {
				order = new MOrder(getCtx(), 0, get_TrxName());
				MBPartner vendor = new MBPartner(getCtx(), C_BPartner_ID, get_TrxName());
				order.setAD_Org_ID(getAD_Org_ID());
				order.setBPartner(vendor);
				order.setIsSOTrx(false);
				order.setC_DocTypeTarget_ID();
				order.setDatePromised(datePromised);
				order.setDescription(Msg.translate(getCtx(), MPPOrder.COLUMNNAME_PP_Order_ID) + ":" + getPP_Order().getDocumentNo());
				order.setDocStatus(MOrder.DOCSTATUS_Drafted);
				order.setDocAction(MOrder.DOCACTION_Complete);
				order.setAD_User_ID(getAD_User_ID());
				order.setM_Warehouse_ID(getM_Warehouse_ID());
				// order.setSalesRep_ID(getAD_User_ID());
				order.save();
				addDescription(Msg.translate(getCtx(), "C_Order_ID") + ": " + order.getDocumentNo());
				orders.put(C_BPartner_ID, order);
				msg = msg + Msg.translate(getCtx(), "C_Order_ID") + " : " + order.getDocumentNo() + " - " + Msg.translate(getCtx(), "C_BPartner_ID") + " : "
						+ vendor.getName() + " , ";
			}
			//
			// Create Order Line:
			BigDecimal QtyOrdered = getMovementQty().multiply(subcontract.getQty());
			// Check Order Min
			if (product_po.getOrder_Min().signum() > 0) {
				QtyOrdered = QtyOrdered.max(product_po.getOrder_Min());
			}
			// Check Order Pack
			if (product_po.getOrder_Pack().signum() > 0 && QtyOrdered.signum() > 0) {
				QtyOrdered = product_po.getOrder_Pack().multiply(QtyOrdered.divide(product_po.getOrder_Pack(), 0, BigDecimal.ROUND_UP));
			}
			LP_C_OrderLine oline = new LP_C_OrderLine(order);
			oline.setM_Product_ID(product.getM_Product_ID());
			oline.setDescription(activity.getDescription());
			oline.setM_Warehouse_ID(getM_Warehouse_ID());
			oline.setQty(QtyOrdered);
			// line.setPrice(m_product_po.getPricePO());
			// oline.setPriceList(m_product_po.getPriceList());
			oline.setPP_Cost_Collector_ID(getID());
			oline.setDatePromised(datePromised);
			oline.save();
			//
			// TODO: Mark this as processed?
			setProcessed(true);
		} // each subcontracting line
		return msg;
	}

	// @Override
	public MProduct getM_Product() {
		return MProduct.get(getCtx(), getM_Product_ID());
	}

	// @Override
	public MUOM getC_UOM() {
		return MUOM.get(getCtx(), getC_UOM_ID());
	}

	public boolean isIssue() {
		return isCostCollectorType(COSTCOLLECTORTYPE_ComponentIssue)
				|| (isCostCollectorType(COSTCOLLECTORTYPE_MethodChangeVariance) && getPP_Order_Bomline_ID() > 0) // need
																													// inventory
																													// adjustment
				|| (isCostCollectorType(COSTCOLLECTORTYPE_MixVariance) && getPP_Order_Bomline_ID() > 0) // need
																										// inventory
																										// adjustment
		;
	}

	public boolean isReturn() {
		return isCostCollectorType(COSTCOLLECTORTYPE_ComponentReturn);
	}
	
	public boolean isCoProduct() {
		return isCostCollectorType(COSTCOLLECTORTYPE_CoProductReceipt);
	}
	
	public boolean isReceipt() {
		return isCostCollectorType(COSTCOLLECTORTYPE_MaterialReceipt);
	}

	public boolean isActivityControl() {
		return isCostCollectorType(COSTCOLLECTORTYPE_ActivityControl);
	}

	public boolean isVariance() {
		return isCostCollectorType(COSTCOLLECTORTYPE_MethodChangeVariance, COSTCOLLECTORTYPE_UsegeVariance, COSTCOLLECTORTYPE_RateVariance,
				COSTCOLLECTORTYPE_MixVariance);
	}

	public String getMovementType() {
		if (isReceipt() || isCoProduct())
			return MTransaction.MOVEMENTTYPE_WorkOrderPlus;
		else if (isIssue() || isReturn())
			return MTransaction.MOVEMENTTYPE_WorkOrder_;
		else
			return null;
	}
	
	public String getCounterMovementType() {
		if (isIssue() || isReturn())
			return MTransaction.MOVEMENTTYPE_WorkOrderPlus;
		else if (isReceipt() || isCoProduct())
			return MTransaction.MOVEMENTTYPE_WorkOrder_;
		else
			return null;
	}

	/**
	 * Check if CostCollectorType is equal with any of provided types
	 * 
	 * @param types
	 * @return
	 */
	public boolean isCostCollectorType(String... types) {
		String type = getCostCollectorType();
		for (String t : types) {
			if (type.equals(t))
				return true;
		}
		return false;
	}

	public boolean isFloorStock() {
		final String whereClause = MPPOrderBOMLine.COLUMNNAME_PP_Order_BOMLine_ID + "=?" + " AND " + MPPOrderBOMLine.COLUMNNAME_IssueMethod + "=?";
		boolean isFloorStock = new Query(getCtx(), MPPOrderBOMLine.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true)
				.setParameters(new Object[] { getPP_Order_Bomline_ID(), MPPOrderBOMLine.ISSUEMETHOD_FloorStock }).match();
		return isFloorStock;
	}

	/**
	 * set Is SubContracting
	 * 
	 * @param PP_Order_Node_ID
	 **/
	public void setIsSubcontracting(int PP_Order_Node_ID) {

		setIsSubcontracting(MPPOrderNode.get(getCtx(), PP_Order_Node_ID, get_TrxName()).isSubcontracting());
	}

	@Override
	public int get_ID() {
		return getID();
	}

	@Override
	public boolean isSOTrx() {
		return false;
	}

	@Override
	public BigDecimal getPriceActual() {
		return priceActual;
	}

	@Override
	public int getM_LocatorTo_ID() {
		return 0;
	}

	@Override
	public int getM_AttributeSetInstanceTo_ID() {
		return 0;
	}

	@Override
	public boolean postIt() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Obtener los cost collectos para un Recurso y una OM
	 * 
	 * @param product
	 * @param AD_Client_ID
	 * @param dateAcct
	 * @return Collection the Cost Collector
	 */
	
	public static List<MPPCostCollector> getCostCollectorResourceOM(Properties ctx, int M_Product_ID, int PP_Order_ID, String trxName) {
		List<Object> params = new ArrayList();
		
		final StringBuffer whereClause = new StringBuffer();
		
		whereClause.append(MPPCostCollector.COLUMNNAME_costcollectortype + " NOT IN ('100','110') AND ");
		
		if (M_Product_ID > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_M_Product_ID + "=? AND ");
			params.add(M_Product_ID);
		}

		if (PP_Order_ID > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_PP_Order_ID + "=? AND ");
			params.add(PP_Order_ID);
		}

		return new Query(ctx, LP_PP_Cost_Collector.Table_Name, whereClause.toString(), trxName).setParameters(params).list();

	}

	
	/**
	 * Obtener los cost collectos para un Recurso y una OM
	 * 
	 * @param product
	 * @param AD_Client_ID
	 * @param dateAcct
	 * @return Collection the Cost Collector
	 */
	
	public static List<MPPCostCollector> getCostCollectorIssueOM(Properties ctx, int M_Product_ID, int PP_Order_ID, String trxName) {
		List<Object> params = new ArrayList();
		
		final StringBuffer whereClause = new StringBuffer();
		
		// Busco los registros que sena entregas de materiales
		
		whereClause.append(MPPCostCollector.COLUMNNAME_costcollectortype + " IN ('110') AND ");
		
		if (M_Product_ID > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_M_Product_ID + "=? AND ");
			params.add(M_Product_ID);
		}

		if (PP_Order_ID > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_PP_Order_ID + "=?");
			params.add(PP_Order_ID);
		}

		return new Query(ctx, LP_PP_Cost_Collector.Table_Name, whereClause.toString(), trxName).setParameters(params).list();

	}
	
	/**
	 * Obtener los cost collectos para un Recurso y una OM
	 * 
	 * @param product
	 * @param AD_Client_ID
	 * @param dateAcct
	 * @return Collection the Cost Collector
	 */
	
	public static List<MPPCostCollector> getCostCollectorOM(Properties ctx, int M_Product_ID, int PP_Order_ID, String trxName, String type) {
		List<Object> params = new ArrayList();
		
		final StringBuffer whereClause = new StringBuffer();
		
		// Busco los registros que sena entregas de materiales
		
		whereClause.append(MPPCostCollector.COLUMNNAME_costcollectortype + " IN ('"+type+"') AND ");
		
		if (M_Product_ID > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_M_Product_ID + "=? AND ");
			params.add(M_Product_ID);
		}

		if (PP_Order_ID > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_PP_Order_ID + "=?");
			params.add(PP_Order_ID);
		}

		return new Query(ctx, LP_PP_Cost_Collector.Table_Name, whereClause.toString(), trxName).setParameters(params).list();

	}

	public static MPPOrder getCostCollectorOrderAttr(Properties ctx,
			int m_Product_ID, int attr_id, String trxName) {
		// TODO Auto-generated method stub

		List<Object> params = new ArrayList();
		
		final StringBuffer whereClause = new StringBuffer();
		
		// Busco los registros que sean recepción de materiales
		
		whereClause.append(MPPCostCollector.COLUMNNAME_costcollectortype + " IN ('100') AND ");
		
		if (m_Product_ID > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_M_Product_ID + "=? AND ");
			params.add(m_Product_ID);
		}

		if (attr_id > 0) {
			whereClause.append(MPPCostCollector.COLUMNNAME_M_AttributeSetInstance_ID + "=?");
			params.add(attr_id);
		}

		List<MPPCostCollector> cc_list = new Query(ctx, LP_PP_Cost_Collector.Table_Name, whereClause.toString(), trxName).setParameters(params).list();
		
				
		for (MPPCostCollector cc_item : cc_list) {
			return cc_item.getPP_Order();
		}
		
		return null;
	}	

} // MPPCostCollector
