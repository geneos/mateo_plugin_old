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
package ar.com.geneos.mrp.plugin.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.logging.Level;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MClient;
import org.openXpertya.model.MCost;
import org.openXpertya.model.MCostType;
import org.openXpertya.model.MCostElement;
import org.openXpertya.model.MDocType;
import org.openXpertya.model.MLocator;
import org.openXpertya.model.MOrder;
import org.openXpertya.model.MPreference;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MProductCategoryAcct;
import org.openXpertya.model.MProject;
import org.openXpertya.model.MResource;
import org.openXpertya.model.MStorage;
import org.openXpertya.model.MUOM;
import org.openXpertya.model.MWarehouse;
import org.openXpertya.model.M_Table;
import org.openXpertya.model.ModelValidationEngine;
import org.openXpertya.model.PO;
import org.openXpertya.model.POResultSet;
import org.openXpertya.model.Query;
import org.openXpertya.print.ReportEngine;
import org.openXpertya.process.DocAction;
import org.openXpertya.process.DocumentEngine;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;
import org.openXpertya.util.KeyNamePair;
import org.openXpertya.util.Msg;
import org.openXpertya.util.Trx;
import org.openXpertya.wf.MWFNode;
import org.openXpertya.wf.MWFNodeNext;
import org.openXpertya.wf.MWorkflow;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;

import ar.com.geneos.mrp.plugin.exception.BOMExpiredException;
import ar.com.geneos.mrp.plugin.exception.DocTypeNotFoundException;
import ar.com.geneos.mrp.plugin.exception.MRPException;
import ar.com.geneos.mrp.plugin.exception.RoutingExpiredException;
import ar.com.geneos.mrp.plugin.tool.engine.CostDimension;
import ar.com.geneos.mrp.plugin.util.MUColumnNames;
import ar.com.geneos.mrp.plugin.util.MUMProduct;
import ar.com.geneos.mrp.plugin.util.MUMStorage;
import ar.com.geneos.mrp.plugin.util.MUMWFNode;
import ar.com.geneos.mrp.plugin.util.MUMWorkflow;

/**
 * PP Order Model.
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPOrder extends LP_PP_Order implements DocAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7341647141173120822L;

	/**
	 * get Manufacturing Order based in Sales Order ID
	 * 
	 * @param ctx
	 *            Context
	 * @param C_OrderLine_ID
	 *            Sales Order Line
	 * @param M_Product_ID
	 *            Product
	 * @param trxName
	 *            Transaction
	 * @return Manufacturing Order
	 */
	public static MPPOrder forC_OrderLine_ID(Properties ctx, int C_OrderLine_ID, int M_Product_ID, String trxName) {
		return new Query(ctx, MPPOrder.Table_Name, COLUMNNAME_C_OrderLine_ID + "=? AND " + COLUMNNAME_M_Product_ID + "=?", trxName).setParameters(
				C_OrderLine_ID, M_Product_ID).firstOnly();
	}

	/**
	 * Set QtyBatchSize and QtyBatchs using Workflow and QtyEntered
	 * 
	 * @param ctx
	 *            context
	 * @param order
	 *            MO
	 * @param override
	 *            if false, will set QtyBatchSize even if is already set
	 *            (QtyBatchSize!=0)
	 */
	public static void updateQtyBatchs(Properties ctx, LP_PP_Order order, boolean override) {
		BigDecimal qtyBatchSize = order.getQtyBatchSize();
		if (qtyBatchSize.signum() == 0 || override) {
			int AD_Workflow_ID = order.getAD_Workflow_ID();
			// No workflow entered, or is just a new record:
			if (AD_Workflow_ID <= 0)
				return;

			MWorkflow wf = MWorkflow.get(ctx, AD_Workflow_ID);
			qtyBatchSize = wf.getQtyBatchSize().setScale(0, RoundingMode.UP);
			order.setQtyBatchSize(qtyBatchSize);
		}

		BigDecimal QtyBatchs;
		if (qtyBatchSize.signum() == 0)
			QtyBatchs = Env.ONE;
		else
			QtyBatchs = order.getQtyOrdered().divide(qtyBatchSize, 0, BigDecimal.ROUND_UP);
		order.setQtyBatchs(QtyBatchs);
	}
	
	/**
	 * get if Component is Available
	 * 
	 * @param MPPOrdrt
	 *            Manufacturing order
	 * @param ArrayList
	 *            Issues
	 * @param minGuaranteeDate
	 *            Guarantee Date
	 * @throw exception when the qty available isn't enough (Acumulated by Product)
	 */
	public static void isQtyAvailableEx(MPPOrder order, ArrayList[][] issue, Timestamp minGuaranteeDate) {
		int currM_Product_ID = 0;
		MPPOrderBOMLine currOrderBOMLine = null;
		BigDecimal acumToDeliver = Env.ZERO;
		MProduct product = null;	
		
		for (int i = 0; i < issue.length; i++) {
			
			KeyNamePair key = (KeyNamePair) issue[i][0].get(0);
			boolean isSelected = key.getName().equals("Y");
			if (key == null || !isSelected) {
				continue;
			}

			String value = (String) issue[i][0].get(2);
			KeyNamePair productkey = (KeyNamePair) issue[i][0].get(3);
			int M_Product_ID = productkey.getKey();
			
			//Seteo
			if (currM_Product_ID == 0){
				currM_Product_ID = M_Product_ID;
				product = MProduct.get(order.getCtx(), currM_Product_ID);

			}
			//Acumulo
			if (currM_Product_ID == M_Product_ID){
				BigDecimal qtyToDeliver = (BigDecimal) issue[i][0].get(4);
				BigDecimal qtyScrapComponent = (BigDecimal) issue[i][0].get(5);
				acumToDeliver = acumToDeliver.add(qtyToDeliver).add(qtyScrapComponent);
				//Obtengo BOMLine
				if (product == null || !product.isStocked()) {
					continue;
				}

				if (value == null && isSelected) {
					currOrderBOMLine = MPPOrderBOMLine.forM_Product_ID(
							Env.getCtx(), order.getID(), currM_Product_ID,
							order.get_TrxName());
				} else if (value != null && isSelected) {
					int PP_Order_BOMLine_ID = (Integer) key.getKey();
					if (PP_Order_BOMLine_ID > 0) {
						currOrderBOMLine = new MPPOrderBOMLine(order.getCtx(), PP_Order_BOMLine_ID, order.get_TrxName());
						
					}
				}
			}
			// Chequeo si me alcanza lo disponible para lo acumulado 
			else {
				BigDecimal qtyAvailable = currOrderBOMLine.getQtyAvailable();
				if (qtyAvailable.subtract(acumToDeliver).signum() < 0)
					throw new IllegalStateException("Cantidad surtida invalida para producto: "+product+"(Disponible: "+qtyAvailable+", Necesario: "+acumToDeliver+")");
				//Reseteo
				currM_Product_ID = M_Product_ID;
				acumToDeliver = Env.ZERO;
				product = MProduct.get(order.getCtx(), currM_Product_ID);
			}
		} // for each line
		//Al finalizar si lo acumulado es <> de 0 chequeo
		if (acumToDeliver.signum() != 0){
			BigDecimal qtyAvailable = currOrderBOMLine.getQtyAvailable();
			if (qtyAvailable.subtract(acumToDeliver).signum() < 0)
				throw new IllegalStateException("Cantidad surtida invalida para producto: "+product+"(Disponible: "+qtyAvailable+", Necesario: "+acumToDeliver+")");
		}

	}

	/**
	 * get if Component is Available
	 * 
	 * @param MPPOrdrt
	 *            Manufacturing order
	 * @param ArrayList
	 *            Issues
	 * @param minGuaranteeDate
	 *            Guarantee Date
	 * @return true when the qty available is enough
	 */
	public static boolean isQtyAvailable(MPPOrder order, ArrayList[][] issue, Timestamp minGuaranteeDate) {
		boolean isCompleteQtyDeliver = false;
		for (int i = 0; i < issue.length; i++) {
			KeyNamePair key = (KeyNamePair) issue[i][0].get(0);
			boolean isSelected = key.getName().equals("Y");
			if (key == null || !isSelected) {
				continue;
			}

			String value = (String) issue[i][0].get(2);
			KeyNamePair productkey = (KeyNamePair) issue[i][0].get(3);
			int M_Product_ID = productkey.getKey();
			BigDecimal qtyToDeliver = (BigDecimal) issue[i][0].get(4);
			BigDecimal qtyScrapComponent = (BigDecimal) issue[i][0].get(5);

			MProduct product = MProduct.get(order.getCtx(), M_Product_ID);
			if (product != null && product.isStocked()) {
				int M_AttributeSetInstance_ID = 0;
				if (value == null && isSelected) {
					M_AttributeSetInstance_ID = (Integer) key.getKey();
				} else if (value != null && isSelected) {
					int PP_Order_BOMLine_ID = (Integer) key.getKey();
					if (PP_Order_BOMLine_ID > 0) {
						MPPOrderBOMLine orderBOMLine = new MPPOrderBOMLine(order.getCtx(), PP_Order_BOMLine_ID, order.get_TrxName());
						// Validate if AttributeSet generate instance
						M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
					}
				}

				MStorage[] storages = MPPOrder.getStorages(order.getCtx(), M_Product_ID, order.getM_Warehouse_ID(), M_AttributeSetInstance_ID,
						minGuaranteeDate,true, order.get_TrxName());

				if (M_AttributeSetInstance_ID == 0) {
					BigDecimal toIssue = qtyToDeliver.add(qtyScrapComponent);
					for (MStorage storage : storages) {
						// TODO Selection of ASI
						if (storage.getQtyOnHand().signum() == 0)
							continue;
						BigDecimal issueActual = toIssue.min(storage.getQtyOnHand());
						toIssue = toIssue.subtract(issueActual);
						if (toIssue.signum() <= 0)
							break;
					}
				} else {
					BigDecimal qtydelivered = qtyToDeliver;
					qtydelivered.setScale(4, BigDecimal.ROUND_HALF_UP);
					qtydelivered = Env.ZERO;
				}

				BigDecimal onHand = Env.ZERO;
				for (MStorage storage : storages) {
					onHand = onHand.add(storage.getQtyOnHand());
				}

				isCompleteQtyDeliver = onHand.compareTo(qtyToDeliver.add(qtyScrapComponent)) >= 0;
				if (!isCompleteQtyDeliver)
					break;

			}
		} // for each line

		return isCompleteQtyDeliver;
	}

	public static MStorage[] getStorages(Properties ctx, int M_Product_ID, int M_Warehouse_ID, int M_ASI_ID, Timestamp minGuaranteeDate,boolean positiveOnly, String trxName) {
		MProduct product = MProduct.get(ctx, M_Product_ID);
		if (product != null && product.isStocked()) {
			// Validate if AttributeSet of product generated instance
			if (product.getM_AttributeSetInstance_ID() == 0) {
				String MMPolicy = MUMProduct.getMMPolicy(product);
				return MUMStorage.getWarehouse(ctx, M_Warehouse_ID, M_Product_ID, M_ASI_ID, minGuaranteeDate, MClient.MMPOLICY_FiFo.equals(MMPolicy), // FiFo
						true, // positiveOnly
						0, // M_Locator_ID
						trxName);
			} else {
				// TODO: vpj-cd Create logic to get storage that matched with
				// attribure set that not create instances
				String MMPolicy = MUMProduct.getMMPolicy(product);
				return MUMStorage.getWarehouse(ctx, M_Warehouse_ID, M_Product_ID, 0, minGuaranteeDate, MClient.MMPOLICY_FiFo.equals(MMPolicy), // FiFo
						true, // positiveOnly
						0, // M_Locator_ID
						trxName);
			}

		} else {
			return new MStorage[0];
		}
	}

	/**************************************************************************
	 * Default Constructor
	 * 
	 * @param ctx
	 *            context
	 * @param PP_Order_ID
	 *            order to load, (0 create new order)
	 */
	public MPPOrder(Properties ctx, int PP_Order_ID, String trxName) {
		super(ctx, PP_Order_ID, trxName);
		// New
		if (PP_Order_ID == 0) {
			setDefault();
		}
	} // PP_Order

	/**************************************************************************
	 * Project Constructor
	 * 
	 * @param project
	 *            Project to create Order from
	 * @param DocSubTypeSO
	 *            if SO DocType Target (default DocSubTypeSO_OnCredit)
	 */
	public MPPOrder(MProject project, int PP_Product_BOM_ID, int AD_Workflow_ID) {
		this(project.getCtx(), 0, project.get_TrxName());
		setAD_Client_ID(project.getAD_Client_ID());
		setAD_Org_ID(project.getAD_Org_ID());
		setC_Campaign_ID(project.getC_Campaign_ID());
		setC_Project_ID(project.getC_Project_ID());
		setDescription(project.getName());
		setLine(10);
		setPriorityRule(MPPOrder.PRIORITYRULE_Medium);
		if (project.getDateContract() == null)
			throw new IllegalStateException("Date Contract is mandatory for Manufacturing Order.");
		if (project.getDateFinish() == null)
			throw new IllegalStateException("Date Finish is mandatory for Manufacturing Order.");

		Timestamp ts = project.getDateContract();
		Timestamp df = project.getDateContract();

		if (ts != null)
			setDateOrdered(ts);
		if (ts != null)
			this.setDateStartSchedule(ts);
		ts = project.getDateFinish();
		if (df != null)
			setDatePromised(df);
		setM_Warehouse_ID(project.getM_Warehouse_ID());
		setPP_Product_BOM_ID(PP_Product_BOM_ID);
		setAD_Workflow_ID(AD_Workflow_ID);
		setQtyEntered(Env.ONE);
		setQtyOrdered(Env.ONE);
		MPPProductBOM bom = new MPPProductBOM(project.getCtx(), PP_Product_BOM_ID, project.get_TrxName());
		MProduct product = MProduct.get(project.getCtx(), bom.getM_Product_ID());
		setC_UOM_ID(product.getC_UOM_ID());

		setM_Product_ID(bom.getM_Product_ID());

		String where = MUColumnNames.COLUMNNAME_IsManufacturingResource + " = 'Y' AND " + MUColumnNames.COLUMNNAME_ManufacturingResourceType + " = '"
				+ MResource.MANUFACTURINGRESOURCETYPE_Plant + "' AND " + MUColumnNames.COLUMNNAME_M_Warehouse_ID + " = " + project.getM_Warehouse_ID();
		MResource resoruce = (MResource) M_Table.get(project.getCtx(), MResource.Table_ID).getPO(where, project.get_TrxName());
		if (resoruce == null)
			throw new IllegalStateException("Resource is mandatory.");
		setS_Resource_ID(resoruce.getS_Resource_ID());
	} // MOrder

	/**
	 * Load Constructor
	 * 
	 * @param ctx
	 *            context
	 * @param rs
	 *            result set record
	 */
	public MPPOrder(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	} // MOrder

	/**
	 * @return Open Qty (Ordered - Delivered - Scrap)
	 */
	public BigDecimal getQtyOpen() {
		return getQtyOrdered().subtract(getQtyDelivered()).subtract(getQtyScrap());
	}

	public static void set_TrxName(PO[] lines, String trxName) {
		for (PO line : lines)
			line.set_TrxName(trxName);
	}

	/**
	 * Get BOM Lines of PP Order
	 * 
	 * @param requery
	 * @return Order BOM Lines
	 */
	public MPPOrderBOMLine[] getLines(boolean requery) {
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		String whereClause = MPPOrderBOMLine.COLUMNNAME_PP_Order_ID + "=?";
		List<MPPOrderBOMLine> list = new Query(getCtx(), MPPOrderBOMLine.Table_Name, whereClause, get_TrxName())
				.setParameters(new Object[] { getPP_Order_ID() }).setOrderBy(MPPOrderBOMLine.COLUMNNAME_Line).list();
		m_lines = list.toArray(new MPPOrderBOMLine[list.size()]);
		return m_lines;
	}

	private MPPOrderBOMLine[] m_lines = null;
	private boolean isLocalTrx;

	/**
	 * Get Order BOM Lines
	 * 
	 * @return Order BOM Lines
	 */
	public MPPOrderBOMLine[] getLines() {
		return getLines(true);
	}

	public void setC_DocTypeTarget_ID(String docBaseType) {
		if (getC_DocTypeTarget_ID() > 0)
			return;

		MDocType[] doc = MDocType.getOfDocBaseType(getCtx(), docBaseType);
		if (doc == null) {
			throw new DocTypeNotFoundException(docBaseType, "");
		} else {
			setC_DocTypeTarget_ID(doc[0].getID());
		}
	}

	@Override
	public void setProcessed(boolean processed) {
		super.setProcessed(processed);

		// Update DB:
		if (getID() <= 0)
			return;
		char value = processed ? 'Y' : 'N';
		final String sql = "UPDATE PP_Order SET Processed='" + value + "' WHERE PP_Order_ID="+getID();
		DB.executeUpdate(sql, get_TrxName());
	} // setProcessed

	@Override
	protected boolean beforeSave(boolean newRecord) {
		
		isLocalTrx = false;
		//Creo transaccion si no existe para que sea atomica la operacion, si falla el after save se debe volver atras
		if (get_TrxName() == null){
			set_TrxName(Trx.createTrxName("MPPProductBOMLine"));
			Trx.createTrx(get_TrxName());
			isLocalTrx = true;
		}
		
		if (getAD_Client_ID() == 0) {
			m_processMsg = "AD_Client_ID = 0";
			return false;
		}
		if (getAD_Org_ID() == 0) {
			int context_AD_Org_ID = Env.getAD_Org_ID(getCtx());
			if (context_AD_Org_ID == 0) {
				m_processMsg = "AD_Org_ID = 0";
				return false;
			}
			setAD_Org_ID(context_AD_Org_ID);
			log.warning("beforeSave - Changed Org to Context=" + context_AD_Org_ID);
		}
		if (getM_Warehouse_ID() == 0) {
			int ii = Env.getContextAsInt(getCtx(), "#M_Warehouse_ID");
			if (ii != 0) {
				setM_Warehouse_ID(ii);
			}
		}
		// If UOM not filled, get it from Product
		if (getC_UOM_ID() <= 0 && getM_Product_ID() > 0) {
			setC_UOM_ID(getM_Product().getC_UOM_ID());
		}
		// If DateFinishSchedule is not filled, use DatePromised
		if (getDateFinishSchedule() == null) {
			setDateFinishSchedule(getDatePromised());
		}

		// Order Stock
		// Solo actualizo si la orden esta en proceso o en estado completo
		if ( (MPPOrder.DOCSTATUS_InProgress.equals(getDocStatus()) || MPPOrder.DOCSTATUS_Completed.equals(getDocStatus()) )
				&& 
			( is_ValueChanged(MPPOrder.COLUMNNAME_QtyDelivered) || is_ValueChanged(MPPOrder.COLUMNNAME_QtyOrdered) ) ) {
			
			try {
				orderStock();
			} catch (Exception e) {
				log.saveError("SaveError", e);
				e.printStackTrace();
				return false;
			}
		}

		updateQtyBatchs(getCtx(), this, false);

		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success) {
			return false;
		}

		if (MPPOrder.ACTION_Close.equals(getDocAction()) || MPPOrder.ACTION_Void.equals(getDocAction())) {
			return true;
		}

		try {

			if (is_ValueChanged(MPPOrder.COLUMNNAME_QtyEntered) && !isDelivered()) {
				deleteWorkflowAndBOM();
				explotion();
			}
			if (is_ValueChanged(MPPOrder.COLUMNNAME_QtyEntered) && isDelivered()) {
				// FIXME:Create Message for Translation
				log.saveError("Error", Msg.getMsg(getCtx(), "ChangeQuatityNotDraftOrInProcess"));
				return false;
			}

			if (!newRecord) {
				return success;
			}

			explotion();

			/**
			 * Libero to Libertya Migration Force ModelValidator
			 */
			if (newRecord)
				MRPValidator.modelChange(this, ModelValidator.TYPE_AFTER_NEW, log);
			else
				MRPValidator.modelChange(this, ModelValidator.TYPE_AFTER_CHANGE, log);
		}
		catch (Exception e){
			log.saveError("Error",
					Msg.getMsg(getCtx(), e.getLocalizedMessage()));
			//Vuelvo transaccion atras
			if (isLocalTrx && get_TrxName() != null){
				Trx.get(get_TrxName(), false).rollback();
			}
			return false;
		}

		//Commit manual si isLocalTrx esta seteado
		if (isLocalTrx && get_TrxName() != null){
			Trx.get(get_TrxName(), false).commit();
		}
		return true;
	} // beforeSave

	@Override
	protected boolean beforeDelete() {
		// OrderBOMLine
		if (getDocStatus().equals(DOCSTATUS_Drafted) || getDocStatus().equals(DOCSTATUS_InProgress)) {
			String whereClause = "PP_Order_ID=" + getID() + " AND AD_Client_ID=" + getAD_Client_ID();

			deletePO(MPPOrderCost.Table_Name, whereClause);
			deleteWorkflowAndBOM();
		}

		// Un-Order Stock
		if (MPPOrder.DOCSTATUS_InProgress.equals(getDocStatus()) || MPPOrder.DOCSTATUS_Completed.equals(getDocStatus())) {
			setQtyOrdered(Env.ZERO);
			try {
				orderStock();
			} catch (Exception e) {
				log.saveError("SaveError", e);
				e.printStackTrace();
				return false;
			}
		}

		MRPValidator.modelChange(this, ModelValidator.TYPE_BEFORE_DELETE, log);

		return true;
	} // beforeDelete

	private void deleteWorkflowAndBOM() {
		// New record, nothing to do
		if (getID() <= 0) {
			return;
		}
		//
		String whereClause = "PP_Order_ID=" + getID() + " AND AD_Client_ID=" + getAD_Client_ID();
		// Reset workflow start node
		DB.executeUpdate("UPDATE PP_Order_Workflow SET PP_Order_Node_ID=NULL WHERE " + whereClause, get_TrxName());
		// Delete workflow:
		deletePO(LP_PP_Order_Node_Asset.Table_Name, whereClause);
		deletePO(LP_PP_Order_Node_Product.Table_Name, whereClause);
		deletePO(MPPOrderNodeNext.Table_Name, whereClause);
		deletePO(MPPOrderNode.Table_Name, whereClause);
		deletePO(MPPOrderWorkflow.Table_Name, whereClause);
		// Delete BOM:
		deletePO(MPPOrderBOMLine.Table_Name, whereClause);
		deletePO(MPPOrderBOM.Table_Name, whereClause);

	}

	public boolean processIt(String processAction) {
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine(this, getDocStatus());
		return engine.processIt(processAction, getDocAction(), log);
	} // processIt

	/** Process Message */
	private String m_processMsg = null;
	/** Just Prepared Flag */
	private boolean m_justPrepared = false;

	public boolean unlockIt() {
		log.info(toString());
		setProcessing(false);
		return true;
	} // unlockIt

	public boolean invalidateIt() {
		log.info(toString());
		setDocAction(ACTION_Prepare);
		return true;
	} // invalidateIt

	public String prepareIt() {
		log.info(toString());

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		// Lines
		MPPOrderBOMLine[] lines = getLines(true);
		if (lines.length == 0) {
			m_processMsg = "@NoLines@";
			return DocAction.STATUS_Invalid;
		}

		// Cannot change Std to anything else if different warehouses
		if (getC_DocType_ID() != 0) {
			for (int i = 0; i < lines.length; i++) {
				if (lines[i].getM_Warehouse_ID() != getM_Warehouse_ID()) {
					log.warning("different Warehouse " + lines[i]);
					m_processMsg = "@CannotChangeDocType@";
					return DocAction.STATUS_Invalid;
				}
			}
		}

		// New or in Progress/Invalid
		if (DOCSTATUS_Drafted.equals(getDocStatus()) || DOCSTATUS_InProgress.equals(getDocStatus()) || DOCSTATUS_Invalid.equals(getDocStatus())
				|| getC_DocType_ID() == 0) {
			setC_DocType_ID(getC_DocTypeTarget_ID());
		}

		String docBaseType = MDocType.get(getCtx(), getC_DocType_ID()).getDocBaseType();
		/**
		 * Migracion Libero a Libertya Quality Order no implementado en version
		 * 1.0
		 */
		/*
		 * if (MDocType.DOCBASETYPE_QualityOrder.equals(docBaseType)) { ; //
		 * nothing } // ManufacturingOrder, MaintenanceOrder else {
		 */
		if (!reserveStock(lines)) { // Clear Reservations{
			m_processMsg = "Error doing reservations";
			return DocAction.STATUS_Invalid;
		}

		try {
			orderStock();
		} catch (Exception e) {
			log.saveError("SaveError", e);
			e.printStackTrace();
			return DocAction.STATUS_Invalid;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;

		m_justPrepared = true;
		return DocAction.STATUS_InProgress;
	} // prepareIt

	private void orderStock() {
		MProduct product = getM_Product();
		if (!product.isStocked()) {
			return;
		}
		BigDecimal target = getQtyOrdered();
		BigDecimal difference = target.subtract(getQtyReserved()).subtract(getQtyDelivered());

		if (difference.signum() == 0) {
			return;
		}
		
		/*
		 * La cantidad reservada mantiene la cantidad que es necesaria recibir
		 * Nunca puede ser negativa ni mayor a la cantidad ordenada 
		 */
		
		//Ajuste para que el reservado no quede en negativo
		if (getQtyReserved().add(difference).signum() == -1) {
			difference = getQtyReserved().negate();
		}
		
		//Ajuste para que el reservado no sea mayor que la cantidad ordenada
		//Nunca deberia pasar ya que qtyDelivered debe ser mayor o igual que 0
		if (getQtyReserved().add(difference).compareTo(target) == 1) {
			difference = target.subtract(getQtyReserved());
		}
		
		BigDecimal ordered = difference;

		int M_Locator_ID = getM_Locator_ID(ordered);
		// Necessary to clear order quantities when called from closeIt -
		// 4Layers
		if (ACTION_Close.equals(getDocAction())) {
			if (!MStorage.add(getCtx(), getM_Warehouse_ID(), M_Locator_ID, getM_Product_ID(), getM_AttributeSetInstance_ID(), 0, //Reserved and Ordered always on AttributeSetInstance 0
					Env.ZERO, Env.ZERO, ordered, get_TrxName())) {
				throw new RuntimeException("MPPOrder.orderStock -> MStorage: Update storage Fail");
			}
		} else {
			// Update Storage
			if (!MStorage.add(getCtx(), getM_Warehouse_ID(), M_Locator_ID, getM_Product_ID(), getM_AttributeSetInstance_ID(), 0, //Reserved and Ordered on AttributeSetInstance 0
					Env.ZERO, Env.ZERO, ordered, get_TrxName())) {
				throw new RuntimeException("MPPOrder.orderStock -> MStorage: Update storage Fail");
			}
		}

		setQtyReserved(getQtyReserved().add(difference));
	}

	/**
	 * Reserve Inventory.
	 * 
	 * @param lines
	 *            order lines (ordered by M_Product_ID for deadlock prevention)
	 * @return true if (un) reserved
	 */
	private boolean reserveStock(MPPOrderBOMLine[] lines) {
		// Always check and (un) Reserve Inventory
		for (MPPOrderBOMLine line : lines) {
			line.reserveStock();
			if (!line.save())
				return false;
		}
		return true;
	} // reserveStock

	public boolean approveIt() {
		log.info("approveIt - " + toString());
		MDocType doc = MDocType.get(getCtx(), getC_DocType_ID());
		/**
		 * Migracion Libero a Libertya Quality Order no implementado en version
		 * 1.0
		 */
		/*
		 * if (MDocType.DOCBASETYPE_QualityOrder.equals(doc.getDocBaseType())) {
		 * String whereClause = COLUMNNAME_PP_Product_BOM_ID + "=? AND " +
		 * COLUMNNAME_AD_Workflow_ID + "=?"; MQMSpecification qms = new
		 * Query(getCtx(), MQMSpecification.Table_Name, whereClause,
		 * get_TrxName()).setParameters( new Object[] { getPP_Product_BOM_ID(),
		 * getAD_Workflow_ID() }).firstOnly(); return qms != null ?
		 * qms.isValid(getM_AttributeSetInstance_ID()) : true; } else {
		 */
		setIsApproved(true);
		// }

		return true;
	} // approveIt

	public boolean rejectIt() {
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	} // rejectIt

	public String completeIt() {
		// Just prepare
		if (ACTION_Prepare.equals(getDocAction())) {
			setProcessed(false);
			return DocAction.STATUS_InProgress;
		}

		// Re-Check
		if (!m_justPrepared) {
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null) {
			return DocAction.STATUS_Invalid;
		}

		// Implicit Approval
		if (!isApproved()) {
			setIsApproved(approveIt());
			MDocType doc = MDocType.get(getCtx(), getC_DocType_ID());
			/**
			 * Migracion Libero a Libertya Quality Order no implementado en
			 * version 1.0
			 */
			/*
			 * if (isApproved() &&
			 * MDocType.DOCBASETYPE_QualityOrder.equals(doc.getDocBaseType())) {
			 * setProcessed(true); setDocAction(DOCACTION_Complete); String
			 * valid = ModelValidationEngine.get().fireDocValidate(this,
			 * ModelValidator.TIMING_AFTER_COMPLETE); if (valid != null) {
			 * m_processMsg = valid; return DocAction.STATUS_Invalid; } return
			 * DocAction.STATUS_Completed;
			 * 
			 * } else if
			 * (MDocType.DOCBASETYPE_QualityOrder.equals(doc.getDocBaseType()))
			 * { setProcessed(true); setDocAction(DOCACTION_Complete);
			 * 
			 * String valid = ModelValidationEngine.get().fireDocValidate(this,
			 * ModelValidator.TIMING_AFTER_COMPLETE); if (valid != null) {
			 * m_processMsg = valid; return DocAction.STATUS_Invalid; } return
			 * DocAction.STATUS_Completed; }
			 */
		}

		createStandardCosts();

		// Create the Activity Control
		autoReportActivities();

		// setProcessed(true);
		setDocAction(ACTION_Close);

		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null) {
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		return DocAction.STATUS_Completed;
	} // completeIt

	/**
	 * Check if the Quantity from all Critical BOM Lines is available. Not exist
	 * a line where QtyRequired > QtyAvailable
	 * 
	 * @return true if entire Qty is available for this Order
	 */
	public boolean isAvailable() {
		String whereClause = "QtyRequired > QtyAvailable AND iscritical = 'Y' AND PP_Order_ID=?";
		// Que no sean alternativos
		whereClause += " AND M_Product_ID NOT IN (SELECT M_Product_ID from PP_Product_Planning where isAlternative='Y' AND M_Warehouse_ID=? AND S_Resource_ID =? AND AD_Org_ID=?)";
		boolean available = new Query(getCtx(), "RV_PP_Order_Storage", whereClause, get_TrxName()).setParameters(new Object[] { getID(),getM_Warehouse_ID(),getS_Resource_ID(),getAD_Org_ID() }).match();
		return !available;
	}

	public boolean voidIt() {
		log.info(toString());
		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		if (isDelivered()) {
			throw new RuntimeException("Cannot void this document because exist transactions"); // TODO:
																								// Create
																								// Message
																								// for
																								// Translation
		}

		for (MPPOrderBOMLine line : getLines()) {
			BigDecimal old = line.getQtyRequired();
			if (old.signum() != 0) {
				line.addDescription(Msg.parseTranslation(getCtx(), "@Voided@ @QtyRequired@ : (" + old + ")"));
				line.setQtyRequired(Env.ZERO);
				line.save();
			}
		}

		getMPPOrderWorkflow().voidActivities();

		BigDecimal old = getQtyOrdered();
		if (old.signum() != 0) {
			addDescription(Msg.parseTranslation(getCtx(), "@Voided@ @QtyOrdered@ : (" + old + ")"));
			setQtyOrdered(Env.ZERO);
			setQtyEntered(Env.ZERO);
			save();
		}

		try {
			orderStock(); // Clear Ordered Quantities
		} catch (Exception e) {
			log.saveError("SaveError", e);
			e.printStackTrace();
			return false;
		}

		if (!reserveStock(getLines())) { // Clear Reservations{
			m_processMsg = "Error Clearing reservations";
			return false;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		// setProcessed(true);
		setDocAction(ACTION_None);
		return true;
	} // voidIt

	public boolean closeIt() {
		log.info(toString());
		// Before Close
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		if (m_processMsg != null)
			return false;

		if (MPPOrder.DOCSTATUS_Closed.equals(getDocStatus()))
			return true;

		if (!MPPOrder.DOCSTATUS_Completed.equals(getDocStatus())) {
			String DocStatus = completeIt();
			setDocStatus(DocStatus);
			setDocAction(MPPOrder.ACTION_None);
		}

		if (!isDelivered()) {
			m_processMsg = "Cannot close this document because do not exist transactions"; // TODO:
			return false; // Create
			// Message
			// for
			// Translation
		}
		
		String msgSatisfied = isSatisfied();
		if (!msgSatisfied.isEmpty()) {
			m_processMsg = msgSatisfied; 
			return false; 
		}

		//createVariances();
		
		// Agregar actualización de costos
		
		updateCostValues();

		for (MPPOrderBOMLine line : getLines()) {
			BigDecimal old = line.getQtyRequired();
			if (old.compareTo(line.getQtyDelivered()) != 0) {
				line.setQtyRequired(line.getQtyDelivered());
				line.addDescription(Msg.parseTranslation(getCtx(), "@closed@ @QtyRequired@ (" + old + ")"));
				line.save();
			}
		}

		// Close all the activity do not reported
		MPPOrderWorkflow m_order_wf = getMPPOrderWorkflow();
		m_order_wf.closeActivities(m_order_wf.getLastNode(getAD_Client_ID()), getUpdated(), false);

		BigDecimal old = getQtyOrdered();
		if (old.signum() != 0) {
			addDescription(Msg.parseTranslation(getCtx(), "@closed@ ( "+getUpdated()+" ), @QtyOrdered@ : (" + old + "), "+" @QtyDelivered@ : (" + getQtyDelivered() + ")"));
			setQtyOrdered(getQtyDelivered());
			save();
		}
		try {
			orderStock(); // Clear Ordered Quantities
		} catch (Exception e) {
			log.saveError("SaveError", e);
			e.printStackTrace();
			return false;
		}

		if (!reserveStock(getLines())) { // Clear Reservations{
			m_processMsg = "Error Clearing reservations";
			return false;
		}

		setDocStatus(DOCSTATUS_Closed);
		// setProcessed(true);
		setDocAction(ACTION_None);
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		if (m_processMsg != null)
			return false;
		return true;
	} // closeIt

	/*
	 * 	
	 * 	Actualización de los elementos de costos directos.
	 * 	Actualiza los costos de materiales en función de los consumos reales y 
	 * 	los costos configurados en los elementos.
	 * 
	 * 	@ Geneos
	 * 	@ 26/10/2015
	 * 
	 */
	
	private void updateCostValues() {
		// TODO Auto-generated method stub
		
		ArrayList<Object> finalParams = new ArrayList<Object>();
		StringBuffer finalWhereClause = new StringBuffer();

		finalWhereClause.append(MPPOrder.COLUMNNAME_PP_Order_ID + "=? ");
		finalParams.add(this.getPP_Order_ID());		
		
		Query costos_om = new Query(getCtx(), MPPOrderCost.Table_Name, finalWhereClause.toString(), get_TrxName()).setParameters(finalParams);
		Collection<MPPOrderCost> costos_om_lista = costos_om.list();
		
		for (MPPOrderCost cost_om_item : costos_om_lista) {

			// Dependiendo del elemento y el tipo de costo, actualizar el costo en la OM.
			
			MCostElement elem = new MCostElement(getCtx(), cost_om_item.getM_CostElement_ID(), get_TrxName());
			LP_M_CostType tipo = new LP_M_CostType(getCtx(), cost_om_item.getM_CostType_ID(), get_TrxName());
						
			MProduct prod = new MProduct(getCtx(), cost_om_item.getM_Product_ID(), get_TrxName());
			
			String metodo = "";
			
			// El método de costeo se obtiene de la categoría del producto y en caso de no tener
			// configurada se toma el método asociado al Tipo de Costo.
			
			if(cost_om_item.getM_Product_ID() != 0){

				ArrayList<Object> finalParams_cat = new ArrayList<Object>();
				StringBuffer finalWhereClause_cat = new StringBuffer();
				finalWhereClause_cat.append("M_Product_Category_ID " + "=? ");
				finalParams_cat.add(prod.getM_Product_Category_ID());		
				MProductCategoryAcct prod_cat_acct = new Query(getCtx(), MProductCategoryAcct.Table_Name, finalWhereClause_cat.toString(), get_TrxName()).setParameters(finalParams_cat).firstOnly();
				metodo = prod_cat_acct.getCostingMethod();
				
			}
			
			if(metodo == null || metodo.equals(""))
				metodo = tipo.getCostingMethod();
			
			
			BigDecimal cost_instance = Env.ZERO;
			
			if (LP_M_CostElement.COSTELEMENTTYPE_Material.equals(elem.getCostElementType())) {
				
				// Completando comportamiento
				// 19/01/2106
				// Falta definir que hacer cuando el producto es fabricado.
				// Debería obtenerse el costo unitario, en función de la cantidad rendida
				// de la OM desde la que fue producto y el costo de la OM.
				
				if(prod.isBOM()) {
					
					// Obtener la cantidad de material real utilizada
					
					BigDecimal qty_bom = Env.ZERO;
					MPPOrderBOMLine[] lineas = this.getLines();
					
					if(metodo.equals(LP_M_CostType.COSTINGMETHOD_OrderMaterials)) {
						cost_instance = MCost.OrderMaterials(getCtx(),prod, cost_om_item.getPP_Order_ID(),get_TrxName());
						cost_om_item.setCurrentCostPrice(cost_instance.multiply(qty_bom));
						cost_om_item.setCumulatedQty(qty_bom);
						cost_om_item.save();
					}
					
				} else {
					
					// Obtener la cantidad de material real utilizada
					
					BigDecimal qty_bom = Env.ZERO;
					MPPOrderBOMLine[] lineas = this.getLines();
					
					for(int ind = 0; ind<lineas.length; ind++) {
						if(lineas[ind].getM_Product_ID() == cost_om_item.getM_Product_ID()) {
							qty_bom = qty_bom.add(lineas[ind].getQtyDelivered());
						}	
					}			
					
					if(metodo.equals(LP_M_CostType.COSTINGMETHOD_LastInvoice)) {
						
						cost_instance = MCost.lastInvoiceCostingMethod(prod);
						cost_om_item.setCurrentCostPrice(cost_instance.multiply(qty_bom));
						cost_om_item.setCumulatedQty(qty_bom);
						cost_om_item.save();
						
					} else if(metodo.equals(LP_M_CostType.COSTINGMETHOD_AverageInvoice)) {
						
						int days = Integer.valueOf(MPreference.GetCustomPreferenceValue("daysAvarageCost"));
						cost_instance = MCost.averageInvoiceCostingMethod(prod, days);
						cost_om_item.setCurrentCostPrice(cost_instance.multiply(qty_bom));
						cost_om_item.setCumulatedQty(qty_bom);
						cost_om_item.save();
						
					} else if(metodo.equals(LP_M_CostType.COSTINGMETHOD_LastPOPrice)) {
					
						cost_instance = MCost.lastPOPriceCostingMethod(prod);
						cost_om_item.setCurrentCostPrice(cost_instance.multiply(qty_bom));
						cost_om_item.setCumulatedQty(qty_bom);
						cost_om_item.save();
						
					} else if(metodo.equals(LP_M_CostType.COSTINGMETHOD_AveragePO)) {
					
						int days = Integer.valueOf(MPreference.GetCustomPreferenceValue("daysAvarageCost"));
						cost_instance = MCost.averagePOCostingMethod(prod, days);
						cost_om_item.setCurrentCostPrice(cost_instance.multiply(qty_bom));
						cost_om_item.setCumulatedQty(qty_bom);
						cost_om_item.save();
						
					} else if(metodo.equals(LP_M_CostType.COSTINGMETHOD_StandardCosting)) {
						
						cost_instance = MCost.standardCostingMethod(prod, cost_om_item.getM_CostType_ID(), cost_om_item.getM_CostElement_ID());
						cost_om_item.setCurrentCostPrice(cost_instance.multiply(qty_bom));
						cost_om_item.setCumulatedQty(qty_bom);
						cost_om_item.save();
			
					}
					
					
				}
				
			} else if (LP_M_CostElement.COSTELEMENTTYPE_Resource.equals(elem.getCostElementType())) {
				// Recursos deben actualizarse
				
				cost_instance = MCost.standardCostingMethod(prod, cost_om_item.getM_CostType_ID(), cost_om_item.getM_CostElement_ID());
				
				// qty_cc debe instanciarse con la suma de los registros de cost collector para el recurso.
				BigDecimal qty_cc = Env.ZERO;
				
				List<MPPCostCollector> list_cc = MPPCostCollector.getCostCollectorResourceOM(getCtx(), cost_om_item.getM_Product_ID(), this.getPP_Order_ID(), null);
				
				for (MPPCostCollector cc : list_cc) {
					qty_cc = qty_cc.add(cc.getMovementQty());
				}
				
				cost_om_item.setCurrentCostPrice(cost_instance.multiply(qty_cc));
				cost_om_item.setCumulatedQty(qty_cc);
				cost_om_item.save();
				
			} else if (LP_M_CostElement.COSTELEMENTTYPE_BurdenMOverhead.equals(elem.getCostElementType())) {
				// Costos indirectos no deben actualizarse				
				
			} else {
				// Sino error.
				// Analizar como manejarlo.
				log.info("No existe metodo de costeo establecido para la categoría de producto y tampoco para el esquema contable.");
				return;
			} 				
			
		}
		
	}

	public boolean reverseCorrectIt() {
		log.info("reverseCorrectIt - " + toString());
		return voidIt();
	} // reverseCorrectionIt

	public boolean reverseAccrualIt() {
		log.info("reverseAccrualIt - " + toString());
		return false;
	} // reverseAccrualIt

	public boolean reActivateIt() {
		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;
		if (isDelivered())
			throw new RuntimeException("Cannot re activate this document because exist transactions"); // TODO:
																										// Create
																										// Message
																										// for
																										// Translation

		setDocAction(ACTION_Complete);
		setProcessed(false);
		return true;
	} // reActivateIt

	public int getDoc_User_ID() {
		return getPlanner_ID();
	} // getDoc_User_ID

	public BigDecimal getApprovalAmt() {
		return Env.ZERO;
	} // getApprovalAmt

	public int getC_Currency_ID() {
		return 0;
	}

	public String getProcessMsg() {
		return m_processMsg;
	}

	public String getSummary() {
		return "" + getDocumentNo() + "/" + getDatePromised();
	}

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
		ReportEngine re = ReportEngine.get(getCtx(), ReportEngine.MANUFACTURING_ORDER, getPP_Order_ID());
		if (re == null)
			return null;
		return re.getPDF(file);
	} // createPDF

	/**
	 * Get Document Info
	 *
	 * @return document info (untranslated)
	 */
	public String getDocumentInfo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	} // getDocumentInfo

	private void deletePO(String tableName, String whereClause) {
		// TODO: refactor this method and move it to org.compiere.model.Query
		// class
		POResultSet<PO> rs = new Query(getCtx(), tableName, whereClause, get_TrxName()).scroll();
		try {
			while (rs.hasNext()) {
				rs.next().delete(true);
			}
		} finally {
			rs.close();
		}
	}

	/**
	 * Set Qty Entered/Ordered. Use this Method if the Line UOM is the Product
	 * UOM
	 *
	 * @param Qty
	 *            QtyOrdered/Entered
	 */
	public void setQty(BigDecimal Qty) {
		super.setQtyEntered(Qty);
		super.setQtyOrdered(getQtyEntered());
	} // setQty

	/**
	 * Set Qty Entered - enforce entered UOM
	 *
	 * @param QtyEntered
	 */
	public void setQtyEntered(BigDecimal QtyEntered) {
		if (QtyEntered != null && getC_UOM_ID() != 0) {
			int precision = MUOM.getPrecision(getCtx(), getC_UOM_ID());
			QtyEntered = QtyEntered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyEntered(QtyEntered);
	} // setQtyEntered

	/**
	 * Set Qty Ordered - enforce Product UOM
	 *
	 * @param QtyOrdered
	 */
	public void setQtyOrdered(BigDecimal QtyOrdered) {
		if (QtyOrdered != null) {
			int precision = getM_Product().getUOMPrecision();
			QtyOrdered = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
		}
		super.setQtyOrdered(QtyOrdered);
	} // setQtyOrdered

	// @Override
	public MProduct getM_Product() {
		return MProduct.get(getCtx(), getM_Product_ID());
	} // getProduct

	public MPPOrderBOM getMPPOrderBOM() {
		final String whereClause = MPPOrderBOM.COLUMNNAME_PP_Order_ID + "=?";
		return new Query(getCtx(), MPPOrderBOM.Table_Name, whereClause, get_TrxName()).setParameters(new Object[] { getPP_Order_ID() }).firstOnly();
	}

	private MPPOrderWorkflow m_PP_Order_Workflow = null;

	public MPPOrderWorkflow getMPPOrderWorkflow() {
		if (m_PP_Order_Workflow != null) {
			return m_PP_Order_Workflow;
		}
		final String whereClause = MPPOrderWorkflow.COLUMNNAME_PP_Order_ID + "=?";
		m_PP_Order_Workflow = new Query(getCtx(), MPPOrderWorkflow.Table_Name, whereClause, get_TrxName()).setParameters(new Object[] { getPP_Order_ID() })
				.firstOnly();
		return m_PP_Order_Workflow;
	}

	/**
	 * Create PP_Order_BOM from PP_Product_BOM. Create PP_Order_Workflow from
	 * AD_Workflow.
	 */
	private void explotion() throws MRPException{
		// Create BOM Head
		final MPPProductBOM PP_Product_BOM = MPPProductBOM.get(getCtx(), getPP_Product_BOM_ID());
		
		// Product from Order should be same as product from BOM - teo_sarca [
		// 2817870 ]
		if (getM_Product_ID() != PP_Product_BOM.getM_Product_ID()) {
			throw new MRPException("@NotMatch@ @PP_Product_BOM_ID@ , @M_Product_ID@");
		}
		// Product BOM Configuration should be verified - teo_sarca [ 2817870 ]
		final MProduct product = MProduct.get(getCtx(), PP_Product_BOM.getM_Product_ID());
		if (!product.isVerified()) {
			throw new MRPException("Product BOM Configuration not verified. Please verify the product first - " + product.getValue()); // TODO:
																																			// translate
		}
		if (PP_Product_BOM.isValidFromTo(getDateStartSchedule())) {
			MPPOrderBOM PP_Order_BOM = new MPPOrderBOM(PP_Product_BOM, getPP_Order_ID(), get_TrxName());
			PP_Order_BOM.setAD_Org_ID(getAD_Org_ID());
			PP_Order_BOM.save();

			for (MPPProductBOMLine PP_Product_BOMline : PP_Product_BOM.getLines(true)) {
				if (PP_Product_BOMline.isValidFromTo(getDateStartSchedule())) {
					MPPOrderBOMLine PP_Order_BOMLine = new MPPOrderBOMLine(PP_Product_BOMline, getPP_Order_ID(), PP_Order_BOM.getID(), getM_Warehouse_ID(),
							get_TrxName());
					PP_Order_BOMLine.setAD_Org_ID(getAD_Org_ID());
					PP_Order_BOMLine.setM_Warehouse_ID(getM_Warehouse_ID());
					PP_Order_BOMLine.setM_Locator_ID(getM_Locator_ID());
					PP_Order_BOMLine.setQtyOrdered(getQtyOrdered());
					PP_Order_BOMLine.save();
				} // end if valid From / To
				else {
					log.fine("BOM Line skiped - " + PP_Product_BOMline);
				}
			} // end Create Order BOM
		} // end if From / To parent
		else {
			throw new BOMExpiredException(PP_Product_BOM, getDateStartSchedule());
		}

		// Create Workflow (Routing & Process)
		final MWorkflow AD_Workflow = MWorkflow.get(getCtx(), getAD_Workflow_ID());
		// Workflow should be validated first - teo_sarca [ 2817870 ]
		if (!AD_Workflow.isValid()) {
			throw new MRPException("Routing is not valid. Please validate it first - " + AD_Workflow.getValue()); // TODO:
																														// translate
		}
		if (MUMWorkflow.isValidFromTo(AD_Workflow, getDateStartSchedule())) {
			MPPOrderWorkflow PP_Order_Workflow = new MPPOrderWorkflow(AD_Workflow, getID(), get_TrxName());
			PP_Order_Workflow.setAD_Org_ID(getAD_Org_ID());
			PP_Order_Workflow.save();
			for (MWFNode AD_WF_Node : MUMWorkflow.getNodes(AD_Workflow, false, getAD_Client_ID())) {
				if (MUMWFNode.isValidFromTo(AD_WF_Node, getDateStartSchedule())) {
					MPPOrderNode PP_Order_Node = new MPPOrderNode(AD_WF_Node, PP_Order_Workflow, getQtyOrdered(), get_TrxName());
					PP_Order_Node.setAD_Org_ID(getAD_Org_ID());
					PP_Order_Node.save();

					for (MWFNodeNext AD_WF_NodeNext : AD_WF_Node.getTransitions()) {
						MPPOrderNodeNext nodenext = new MPPOrderNodeNext(AD_WF_NodeNext, PP_Order_Node);
						nodenext.setAD_Org_ID(getAD_Org_ID());
						nodenext.save();
					}// for NodeNext

					for (MPPWFNodeProduct wfnp : MPPWFNodeProduct.forAD_WF_Node_ID(getCtx(), AD_WF_Node.getID())) {
						MPPOrderNodeProduct nodeOrderProduct = new MPPOrderNodeProduct(wfnp, PP_Order_Node);
						nodeOrderProduct.setAD_Org_ID(getAD_Org_ID());
						nodeOrderProduct.save();
					}

					for (MPPWFNodeAsset wfna : MPPWFNodeAsset.forAD_WF_Node_ID(getCtx(), AD_WF_Node.getID())) {
						MPPOrderNodeAsset nodeorderasset = new MPPOrderNodeAsset(wfna, PP_Order_Node);
						nodeorderasset.setAD_Org_ID(getAD_Org_ID());
						nodeorderasset.save();
					}
				}// for node

			}
			// Update transitions nexts and set first node
			PP_Order_Workflow.getNodes(true); // requery
			for (MPPOrderNode orderNode : PP_Order_Workflow.getNodes(false, getAD_Client_ID())) {
				// set workflow start node
				if (PP_Order_Workflow.getAD_WF_Node_ID() == orderNode.getAD_WF_Node_ID()) {
					PP_Order_Workflow.setPP_Order_Node_ID(orderNode.getPP_Order_Node_ID());
				}
				// set node next
				for (MPPOrderNodeNext next : orderNode.getTransitions(getAD_Client_ID())) {
					next.setPP_Order_Next_ID();
					next.save();
				}
			}
			PP_Order_Workflow.save();
		} // workflow valid from/to
		else {
			throw new RoutingExpiredException(AD_Workflow, getDateStartSchedule());
		}
	}

	/**
	 * Create Receipt Finish Good
	 * 
	 * @param order
	 * @param movementDate
	 * @param qtyDelivered
	 * @param qtyToDeliver
	 * @param qtyScrap
	 * @param qtyReject
	 * @param M_Locator_ID
	 * @param M_AttributeSetInstance_ID
	 * @param IsCloseDocument
	 * @param trxName
	 */
	static public void createReceipt(MPPOrder order, Timestamp movementDate, BigDecimal qtyDelivered, BigDecimal qtyToDeliver, BigDecimal qtyScrap,
			BigDecimal qtyReject, int M_Locator_ID, int M_AttributeSetInstance_ID) {

		if (qtyToDeliver.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0) {
			MPPCostCollector.createCollector(order, // MPPOrder
					order.getM_Product_ID(), // M_Product_ID
					M_Locator_ID, // M_Locator_ID
					M_AttributeSetInstance_ID, // M_AttributeSetInstance_ID
					order.getS_Resource_ID(), // S_Resource_ID
					0, // PP_Order_BOMLine_ID
					0, // PP_Order_Node_ID
					MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(), // C_DocType_ID
					MPPCostCollector.COSTCOLLECTORTYPE_MaterialReceipt, // Production
																		// "+"
					movementDate, // MovementDate
					qtyToDeliver, qtyScrap, qtyReject, // qty,scrap,reject
					0, Env.ZERO); // durationSetup,duration
		}

		order.setDateDelivered(movementDate);
		if (order.getDateStart() == null) {
			order.setDateStart(movementDate);
		}

		BigDecimal DQ = qtyDelivered;
		BigDecimal SQ = qtyScrap;
		BigDecimal OQ = qtyToDeliver;
		if (DQ.add(SQ).compareTo(OQ) >= 0) {
			order.setDateFinish(movementDate);
		}
		order.save();

	}

	/**
	 * Create Issue
	 * 
	 * @param PP_OrderBOMLine_ID
	 * @param movementdate
	 * @param qty
	 * @param qtyScrap
	 * @param qtyReject
	 * @param storages
	 * @param force
	 *            Issue
	 */
	public static void createIssue(MPPOrder order, int PP_OrderBOMLine_ID, Timestamp movementdate, BigDecimal qty, BigDecimal qtyScrap, BigDecimal qtyReject,
			MStorage[] storages, boolean forceIssue) {
		if (qty.signum() == 0)
			return;
		MPPOrderBOMLine PP_orderbomLine = new MPPOrderBOMLine(order.getCtx(), PP_OrderBOMLine_ID, order.get_TrxName());
		BigDecimal toIssue = qty.add(qtyScrap);
		for (MStorage storage : storages) {
			// TODO Selection of ASI

			if (storage.getQtyOnHand().signum() == 0)
				continue;

			BigDecimal qtyIssue = toIssue.min(storage.getQtyOnHand());
			// log.fine("ToIssue: " + issue);
			// create record for negative and positive transaction
			if (qtyIssue.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0) {
				String CostCollectorType = MPPCostCollector.COSTCOLLECTORTYPE_ComponentIssue;
				
				if (PP_orderbomLine.isComponentType(MPPOrderBOMLine.COMPONENTTYPE_Co_Product)) {
					CostCollectorType = MPPCostCollector.COSTCOLLECTORTYPE_MixVariance;
				}
				
				if (qtyIssue.signum() == -1 )
					CostCollectorType = MPPCostCollector.COSTCOLLECTORTYPE_ComponentReturn;

				//
				MPPCostCollector.createCollector(order, // MPPOrder
						PP_orderbomLine.getM_Product_ID(), // M_Product_ID
						storage.getM_Locator_ID(), // M_Locator_ID
						storage.getM_AttributeSetInstance_ID(), // M_AttributeSetInstance_ID
						order.getS_Resource_ID(), // S_Resource_ID
						PP_OrderBOMLine_ID, // PP_Order_BOMLine_ID
						0, // PP_Order_Node_ID
						MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(), // C_DocType_ID,
						CostCollectorType, // Production "-"
						movementdate, // MovementDate
						qtyIssue, qtyScrap, qtyReject, // qty,scrap,reject
						0, Env.ZERO // durationSetup,duration
						);
				PP_orderbomLine.load(order.get_TrxName());
				// Method Variance
				if (PP_orderbomLine.getQtyBatch().signum() == 0 && PP_orderbomLine.getQtyBOM().signum() == 0) {
					order.createMethodChangeVariance(PP_orderbomLine);
				}

			}
			toIssue = toIssue.subtract(qtyIssue);
			if (toIssue.signum() == 0)
				break;
		}
		if (forceIssue && toIssue.signum() != 0) {
			MPPCostCollector.createCollector(order, // MPPOrder
					PP_orderbomLine.getM_Product_ID(), // M_Product_ID
					PP_orderbomLine.getM_Locator_ID(), // M_Locator_ID
					PP_orderbomLine.getM_AttributeSetInstance_ID(), // M_AttributeSetInstance_ID
					order.getS_Resource_ID(), // S_Resource_ID
					PP_OrderBOMLine_ID, // PP_Order_BOMLine_ID
					0, // PP_Order_Node_ID
					MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(), // C_DocType_ID,
					MPPCostCollector.COSTCOLLECTORTYPE_ComponentIssue, // Production
																		// "-"
					movementdate, // MovementDate
					toIssue, Env.ZERO, Env.ZERO, // qty,scrap,reject
					0, Env.ZERO // durationSetup,duration
					);
			return;
		}
		
		//
		if (toIssue.signum() != 0) {
			// should not happen because we validate Qty On Hand on start of
			// this process
			throw new RuntimeException("Stock insuficiente para producto:"+PP_orderbomLine.getM_Product() + ". Faltan: "+ toIssue);
		}
	}
	
	/**
	 * Create Return
	 * 
	 * @param PP_OrderBOMLine_ID
	 * @param movementdate
	 * @param qty
	 * @param qtyScrap
	 * @param qtyReject
	 * @param storages
	 * @param force
	 *            Issue
	 */
	public static void createReturn(MPPOrder order, int PP_OrderBOMLine_ID, Timestamp movementdate, BigDecimal qty, BigDecimal qtyScrap, BigDecimal qtyReject,
			MStorage[] storages, boolean forceReturn) {
		if (qty.signum() == 0)
			return;
		MPPOrderBOMLine PP_orderbomLine = new MPPOrderBOMLine(order.getCtx(), PP_OrderBOMLine_ID, order.get_TrxName());
		BigDecimal toReturn = qty;
		for (int i = storages.length - 1 ; i >= 0 ; i-- ) {
			MStorage storage = storages[i];

			if (PP_orderbomLine.getQtyDelivered(storage.getM_AttributeSetInstance_ID()).signum() == 0)
				continue;

			BigDecimal qtyReturn = toReturn.max(PP_orderbomLine.getQtyDelivered(storage.getM_AttributeSetInstance_ID()).negate());
			// log.fine("ToIssue: " + issue);
			// create record for negative and positive transaction
			if (toReturn.signum() != 0 || qtyScrap.signum() != 0 || qtyReject.signum() != 0) {
				String CostCollectorType = MPPCostCollector.COSTCOLLECTORTYPE_ComponentReturn;

				//
				MPPCostCollector.createCollector(order, // MPPOrder
						PP_orderbomLine.getM_Product_ID(), // M_Product_ID
						storage.getM_Locator_ID(), // M_Locator_ID
						storage.getM_AttributeSetInstance_ID(), // M_AttributeSetInstance_ID
						order.getS_Resource_ID(), // S_Resource_ID
						PP_OrderBOMLine_ID, // PP_Order_BOMLine_ID
						0, // PP_Order_Node_ID
						MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(), // C_DocType_ID,
						CostCollectorType, // Production "-"
						movementdate, // MovementDate
						qtyReturn, qtyScrap, qtyReject, // qty,scrap,reject
						0, Env.ZERO // durationSetup,duration
						);

			}
			toReturn = toReturn.subtract(qtyReturn);
			if (toReturn.signum() == 0)
				break;
		}
		if (forceReturn && toReturn.signum() != 0) {
			MPPCostCollector.createCollector(order, // MPPOrder
					PP_orderbomLine.getM_Product_ID(), // M_Product_ID
					PP_orderbomLine.getM_Locator_ID(), // M_Locator_ID
					PP_orderbomLine.getM_AttributeSetInstance_ID(), // M_AttributeSetInstance_ID
					order.getS_Resource_ID(), // S_Resource_ID
					PP_OrderBOMLine_ID, // PP_Order_BOMLine_ID
					0, // PP_Order_Node_ID
					MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(), // C_DocType_ID,
					MPPCostCollector.COSTCOLLECTORTYPE_ComponentReturn, // Production
																		// "-"
					movementdate, // MovementDate
					toReturn, Env.ZERO, Env.ZERO, // qty,scrap,reject
					0, Env.ZERO // durationSetup,duration
					);
			return;
		}
		
		//
		if (toReturn.signum() != 0) {
			// should not happen 
			throw new RuntimeException("Cantidad entregada insuficiente para devolucion, producto:"+PP_orderbomLine.getM_Product() + ". Faltan: "+ toReturn);
		}
	}

	public static boolean isQtyAvailable(MPPOrder order, LP_PP_Order_BOMLine line) {
		MProduct product = MProduct.get(order.getCtx(), line.getM_Product_ID());
		if (product == null || !product.isStocked()) {
			return true;
		}

		BigDecimal qtyToDeliver = line.getQtyRequired();
		BigDecimal qtyScrap = line.getQtyScrap();
		BigDecimal qtyRequired = qtyToDeliver.add(qtyScrap);
		BigDecimal qtyAvailable = MStorage.getQtyAvailable(order.getM_Warehouse_ID(), line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
				order.get_TrxName());
		return qtyAvailable.compareTo(qtyRequired) >= 0;
	}

	/**
	 * @return Default Locator for current Warehouse
	 */
	public int getM_Locator_ID() {
		MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
		return wh.getDefaultLocator().getM_Locator_ID();
	}

	/**
	 * @param qty
	 * @return Storage locator for current product/asi/warehouse and qty
	 * @see MStorage#getM_Locator_ID(int, int, int, BigDecimal, String)
	 */
	private int getM_Locator_ID(BigDecimal qty) {
		int M_Locator_ID = 0;
		int M_ASI_ID = getM_AttributeSetInstance_ID();
		// Get existing Locator
		if (M_ASI_ID != 0) {
			M_Locator_ID = MStorage.getM_Locator_ID(getM_Warehouse_ID(), getM_Product_ID(), M_ASI_ID, qty, get_TrxName());
		}
		// Get Default
		if (M_Locator_ID == 0) {
			M_Locator_ID = getM_Locator_ID();
		}
		return M_Locator_ID;
	}

	/**
	 * @return Cadena vacia si la orden esta satisfecha
	 * Mensaje de error si la cabecera o alguna de las lineas no satisfacen la tolerancia para el producto/categoria
	 */
	public String isSatisfied() {
		String retValue = "";
		
		// Cabecera
		String msgHeader = MUMProduct.validateTolerance(getM_Product_ID(),getQtyDelivered(), getQtyOrdered());
		if (!msgHeader.isEmpty())
			retValue+="Cabecera: "+msgHeader+"\n";
		
		// Lineas
		for (MPPOrderBOMLine line : getLines()) {
			// Si el producto es alternativo no chequeo tolerancia
			MPPProductPlanning prodPlanning = MPPProductPlanning.find(getCtx(), getAD_Org_ID(), getM_Warehouse_ID(),getS_Resource_ID() , line.getM_Product_ID(), get_TrxName());
			if (prodPlanning.isAlternative())
				continue;
			
			String msgLine = MUMProduct.validateTolerance(line.getM_Product_ID(),line.getQtyDelivered(), line.explodeQty(getQtyDelivered()));
			if (!msgLine.isEmpty())
				retValue+="Linea "+line.getLine()+": "+msgLine+"\n";
		}
		return retValue;
	}
	
	/**
	 * @return true if work was delivered for this MO (i.e. Stock Issue, Stock
	 *         Receipt, Activity Control Report)
	 */
	public boolean isDelivered() {
		if (getQtyDelivered().signum() > 0 || getQtyScrap().signum() > 0 || getQtyReject().signum() > 0) {
			return true;
		}

		for (MPPOrderBOMLine line : getLines()) {
			if (line.getQtyDelivered().signum() > 0) {
				return true;
			}
		}

		for (MPPOrderNode node : this.getMPPOrderWorkflow().getNodes(true, getAD_Client_ID())) {
			if (node.getQtyDelivered().signum() > 0) {
				return true;
			}
			if (node.getDurationReal() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set default value and reset values whe copy record
	 */
	public void setDefault() {
		setLine(10);
		setPriorityRule(PRIORITYRULE_Medium);
		setDescription("");
		setQtyDelivered(Env.ZERO);
		setQtyReject(Env.ZERO);
		setQtyScrap(Env.ZERO);
		setIsSelected(false);
		setIsSOTrx(false);
		setIsApproved(false);
		setIsPrinted(false);
		setProcessed(false);
		setProcessing(false);
		setPosted(false);
		setC_DocTypeTarget_ID(MDocType.DOCBASETYPE_ManufacturingOrder);
		setC_DocType_ID(getC_DocTypeTarget_ID());
		setDocStatus(DOCSTATUS_Drafted);
		setDocAction(ACTION_Prepare);
	}

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

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("MPPOrder[").append(getID()).append("-").append(getDocumentNo()).append(",IsSOTrx=").append(isSOTrx())
				.append(",C_DocType_ID=").append(getC_DocType_ID()).append("]");
		return sb.toString();
	}

	/*
	 * Auto report the first Activity and Sub contracting if are Milestone
	 * Activity
	 */
	public void autoReportActivities() {
		for (MPPOrderNode activity : getMPPOrderWorkflow().getNodes()) {

			if (activity.isMilestone()) {
				if (activity.isSubcontracting() || activity.getID() == getMPPOrderWorkflow().getPP_Order_Node_ID()) {
					MPPCostCollector cc = MPPCostCollector.createCollector(this, getM_Product_ID(), getM_Locator_ID(), getM_AttributeSetInstance_ID(),
							getS_Resource_ID(), 0, activity.getPP_Order_Node_ID(),
							MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(),
							MPPCostCollector.COSTCOLLECTORTYPE_ActivityControl, getUpdated(), activity.getQtyToDeliver(), Env.ZERO, Env.ZERO, 0, Env.ZERO);
				}
			}
		}
	}

	/**
	 *	Crea los registros en PP_Order_Cost.
	 *	Dependiendo de la consfiguración para cada producto, busca registros en M_Cost
	 *	que tengan correspondencia con le tipo de costos configurado en la categoría del producto
	 *	o en el escquema contable.
	 *	
	 *	19/01/2016
	 *	Cooperativa Geneos
	 *
	 */
	public final void createStandardCosts() {
		
		MAcctSchema as = MClient.get(getCtx(), getAD_Client_ID()).getAcctSchema();
		
		log.info("Cost_Group_ID" + as.getM_CostType_ID());

		final TreeSet<Integer> productsAdded = new TreeSet<Integer>();

		//
		// Create Standard Costs for Order Header (resulting product)
		{
			final MProduct product = getM_Product();
			productsAdded.add(product.getM_Product_ID());
			
			int costType_ID = MUMProduct.getProductCategoryCostTypeID(getCtx(), get_TrxName(), product.getM_Product_Category_ID(), as);
			
			final CostDimension d;
			
			if(costType_ID == 0)
				d = new CostDimension(product, as, as.getM_CostType_ID(), getAD_Org_ID(), getM_Warehouse_ID(), getM_AttributeSetInstance_ID(),
					CostDimension.ANY);
			else
				d = new CostDimension(product, as, costType_ID, getAD_Org_ID(), getM_Warehouse_ID(), getM_AttributeSetInstance_ID(),
						CostDimension.ANY);
			
			Collection<MCost> costs = d.toQuery(MCost.class, get_TrxName()).list();
			
			for (MCost cost : costs) {
				// Create or Update the Order Cost dimension
				MPPOrderCost.createOrderCostDimension(getID(), cost);
			}
			
		}
		//
		// Create Standard Costs for Order BOM Line
		for (MPPOrderBOMLine line : getLines()) {
			final MProduct product = line.getM_Product();
			//
			// Check if we already added this product
			if (productsAdded.contains(product.getM_Product_ID())) {
				continue;
			}
			
			productsAdded.add(product.getM_Product_ID());
			
			int costType_ID = MUMProduct.getProductCategoryCostTypeID(getCtx(), get_TrxName(), product.getM_Product_Category_ID(), as);
						
			final CostDimension d;
			
			if(costType_ID == 0)
				d = new CostDimension(line.getM_Product(), as, as.getM_CostType_ID(), line.getAD_Org_ID(), getM_Warehouse_ID(),
						line.getM_AttributeSetInstance_ID(), CostDimension.ANY);
			else
				d = new CostDimension(line.getM_Product(), as, costType_ID, line.getAD_Org_ID(), getM_Warehouse_ID(),
						line.getM_AttributeSetInstance_ID(), CostDimension.ANY);
			
			Collection<MCost> costs = d.toQuery(MCost.class, get_TrxName()).list();
			for (MCost cost : costs) {
				// Create or Update the Order Cost dimension
				MPPOrderCost.createOrderCostDimension(getID(), cost);
			}
		}
		//
		// Create Standard Costs from Activity Resources
		for (MPPOrderNode node : getMPPOrderWorkflow().getNodes(true)) {
			final int S_Resource_ID = node.getS_Resource_ID();
			if (S_Resource_ID <= 0)
				continue;
			//
			final MProduct resourceProduct = MUMProduct.forS_Resource_ID(getCtx(), S_Resource_ID, null);
			//
			// Check if we already added this product
			if (productsAdded.contains(resourceProduct.getM_Product_ID())) {
				continue;
			}
			productsAdded.add(resourceProduct.getM_Product_ID());

			int costType_ID = MUMProduct.	getProductCategoryCostTypeID(getCtx(), get_TrxName(), resourceProduct.getM_Product_Category_ID(), as);
						
			final CostDimension d;
			
			if(costType_ID == 0)
				d = new CostDimension(resourceProduct, as, as.getM_CostType_ID(), node.getAD_Org_ID(), getM_Warehouse_ID(), 0, // ASI
						CostDimension.ANY);
			else
				d = new CostDimension(resourceProduct, as, costType_ID, node.getAD_Org_ID(), getM_Warehouse_ID(), 0, // ASI
						CostDimension.ANY);

			Collection<MCost> costs = d.toQuery(MCost.class, get_TrxName()).list();
			for (MCost cost : costs) {
				// Create or Update the Order Cost dimension
				MPPOrderCost.createOrderCostDimension(getID(), cost);
			}
		}
	}

	public void createVariances() {
		for (MPPOrderBOMLine line : getLines(true)) {
			createUsageVariance(line);
		}
		m_lines = null; // needs to be requeried
		//
		MPPOrderWorkflow orderWorkflow = getMPPOrderWorkflow();
		if (orderWorkflow != null) {
			for (MPPOrderNode node : orderWorkflow.getNodes(true)) {
				createUsageVariance(node);
			}
		}
		// orderWorkflow.m_nodes = null; // TODO: reset nodes cache
	}

	private void createUsageVariance(LP_PP_Order_BOMLine bomLine) {
		MPPOrder order = this;
		Timestamp movementDate = order.getUpdated();
		MPPOrderBOMLine line = (MPPOrderBOMLine) bomLine;

		// If QtyBatch and QtyBOM is zero, than this is a use variance
		// (a product that "was not" in BOM was used)
		if (line.getQtyBatch().signum() == 0 && line.getQtyBOM().signum() == 0) {
			return;
		}

		final BigDecimal qtyUsageVariancePrev = line.getQtyVariance(); // Previous
																		// booked
																		// usage
																		// variance
		final BigDecimal qtyOpen = line.getQtyOpen();
		// Current usage variance = QtyOpen - Previous Usage Variance
		final BigDecimal qtyUsageVariance = qtyOpen.subtract(qtyUsageVariancePrev);
		//
		if (qtyUsageVariance.signum() == 0) {
			return;
		}
		// Get Locator
		int M_Locator_ID = line.getM_Locator_ID();
		if (M_Locator_ID <= 0) {
			MLocator locator = null;
			try {
				locator = MLocator.getDefault(order.getCtx(), order.getM_Warehouse_ID(), true, null);
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error: createUsageVariance", e);
			}
			if (locator != null) {
				M_Locator_ID = locator.getM_Locator_ID();
			}
		}
		//
		MPPCostCollector.createCollector(order, line.getM_Product_ID(), M_Locator_ID, line.getM_AttributeSetInstance_ID(), order.getS_Resource_ID(),
				line.getPP_Order_BOMLine_ID(), 0, // PP_Order_Node_ID,
				MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(), // C_DocType_ID,
				MPPCostCollector.COSTCOLLECTORTYPE_UsegeVariance, movementDate, qtyUsageVariance, // Qty
				Env.ZERO, // scrap,
				Env.ZERO, // reject,
				0, // durationSetup,
				Env.ZERO // duration
				);
	}

	private void createMethodChangeVariance(MPPOrderBOMLine line) {
		MPPOrder order = this;
		Timestamp movementDate = order.getUpdated();
		// If QtyBatch and QtyBOM is zero, than this is a method variance
		// (a product that "was not" in BOM was used)
		if (line.getQtyBatch().signum() != 0 && line.getQtyBOM().signum() != 0) {
			return;
		}

		final BigDecimal qtyMethodChangeVariancePrev = line.getQtyMethodChangeVariance(); // Previous
																							// booked
																							// method
																							// Change
																							// variance
		final BigDecimal qtyOpen = line.getQtyOpen();
		// Current usage variance = QtyOpen - Previous Method Change Variance
		final BigDecimal qtyMethodChangeVariance = qtyOpen.subtract(qtyMethodChangeVariancePrev);
		//
		if (qtyMethodChangeVariance.signum() == 0) {
			return;
		}
		// Get Locator
		int M_Locator_ID = line.getM_Locator_ID();
		if (M_Locator_ID <= 0) {
			MLocator locator = null;
			try {
				locator = MLocator.getDefault(order.getCtx(), order.getM_Warehouse_ID(), true, null);
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error: createUsageVariance", e);
			}
			if (locator != null) {
				M_Locator_ID = locator.getM_Locator_ID();
			}
			if (locator != null) {
				M_Locator_ID = locator.getM_Locator_ID();
			}
		}
		//
		MPPCostCollector cc = MPPCostCollector.createCollector(order, line.getM_Product_ID(), M_Locator_ID, line.getM_AttributeSetInstance_ID(),
				order.getS_Resource_ID(), line.getPP_Order_BOMLine_ID(), 0, // PP_Order_Node_ID,
				MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(), // C_DocType_ID,
				MPPCostCollector.COSTCOLLECTORTYPE_MethodChangeVariance, movementDate, qtyMethodChangeVariance, // Qty
				Env.ZERO, // scrap,
				Env.ZERO, // reject,
				0, // durationSetup,
				Env.ZERO // duration
				);
	}

	private void createUsageVariance(LP_PP_Order_Node orderNode) {
		MPPOrder order = this;
		final Timestamp movementDate = order.getUpdated();
		final MPPOrderNode node = (MPPOrderNode) orderNode;
		//
		final BigDecimal setupTimeReal = BigDecimal.valueOf(node.getSetupTimeReal());
		final BigDecimal durationReal = BigDecimal.valueOf(node.getDurationReal());
		if (setupTimeReal.signum() == 0 && durationReal.signum() == 0) {
			// nothing reported on this activity => it's not a variance, this
			// will be auto-reported on close
			return;
		}
		//
		final BigDecimal setupTimeVariancePrev = node.getSetupTimeUsageVariance();
		final BigDecimal durationVariancePrev = node.getDurationUsageVariance();
		final BigDecimal setupTimeRequired = BigDecimal.valueOf(node.getSetupTimeRequired());
		final BigDecimal durationRequired = BigDecimal.valueOf(node.getDurationRequired());
		final BigDecimal qtyOpen = node.getQtyToDeliver();
		//
		final BigDecimal setupTimeVariance = setupTimeRequired.subtract(setupTimeReal).subtract(setupTimeVariancePrev);
		final BigDecimal durationVariance = durationRequired.subtract(durationReal).subtract(durationVariancePrev);
		//
		if (qtyOpen.signum() == 0 && setupTimeVariance.signum() == 0 && durationVariance.signum() == 0) {
			return;
		}
		//
		MPPCostCollector.createCollector(order, order.getM_Product_ID(), order.getM_Locator_ID(), order.getM_AttributeSetInstance_ID(),
				node.getS_Resource_ID(), 0, // PP_Order_BOMLine_ID
				node.getPP_Order_Node_ID(), MDocType.getOfDocBaseType(Env.getCtx(), MPPCostCollector.DOCBASETYPE_ManufacturingCostCollector)[0].getID(), // C_DocType_ID
				MPPCostCollector.COSTCOLLECTORTYPE_UsegeVariance, movementDate, qtyOpen, // Qty
				Env.ZERO, // scrap,
				Env.ZERO, // reject,
				setupTimeVariance.intValueExact(), // durationSetup,
				durationVariance // duration
				);
	}

	/**
	 * Get Quantity to Deliver
	 * 
	 * @return Quantity to Deliver
	 */
	public BigDecimal getQtyToDeliver() {
		return getQtyOrdered().subtract(getQtyDelivered());
	}

	/**
	 * Create Auto Receipt and Issue based on Quantity
	 * 
	 * @param qtyShipment
	 */
	public void updateMakeToKit(BigDecimal qtyShipment) {
		MPPOrderBOM obom = (MPPOrderBOM) getMPPOrderBOM();
		getLines(true);
		// Auto receipt and issue for kit
		if (MPPOrderBOM.BOMTYPE_Make_To_Kit.equals(obom.getBOMType()) && MPPOrderBOM.BOMUSE_Manufacturing.equals(obom.getBOMUse())) {
			Timestamp today = new Timestamp(System.currentTimeMillis());
			ArrayList[][] issue = new ArrayList[m_lines.length][1];

			for (int i = 0; i < getLines().length; i++) {
				MPPOrderBOMLine line = m_lines[i];

				KeyNamePair id = null;

				if (MPPOrderBOMLine.ISSUEMETHOD_Backflush.equals(line.getIssueMethod())) {
					id = new KeyNamePair(line.getID(), "Y");
				} else
					id = new KeyNamePair(line.getID(), "N");

				ArrayList<Object> data = new ArrayList<Object>();
				BigDecimal qtyToDeliver = qtyShipment.multiply(line.getQtyMultiplier());
				data.add(id); // 0 - MPPOrderBOMLine ID
				data.add(line.isCritical()); // 1 - Critical
				MProduct product = (MProduct) line.getM_Product();
				data.add(product.getValue()); // 2 - Value
				KeyNamePair productKey = new KeyNamePair(product.getID(), product.getName());
				data.add(productKey); // 3 - KeyNamePair Product
				data.add(qtyToDeliver); // 4 - QtyToDeliver
				data.add(Env.ZERO); // 5 - QtyScrapComponent
				issue[i][0] = data;

			}

			boolean forceIssue = false;
			LP_C_OrderLine oline = (LP_C_OrderLine) getC_OrderLine();
			if (MOrder.DELIVERYRULE_CompleteLine.equals(oline.getParent().getDeliveryRule())
					|| MOrder.DELIVERYRULE_CompleteOrder.equals(oline.getParent().getDeliveryRule())) {
				boolean isCompleteQtyDeliver = MPPOrder.isQtyAvailable(this, issue, today);
				if (!isCompleteQtyDeliver) {
					throw new RuntimeException("@NoQtyAvailable@");
				}
			} else if (MOrder.DELIVERYRULE_Availability.equals(oline.getParent().getDeliveryRule())
					|| MOrder.DELIVERYRULE_AfterReceipt.equals(oline.getParent().getDeliveryRule())
			/**
			 * Migracion Libero a Libertya MOrder.DELIVERYRULE_Manual no esta
			 * implementado en libertya
			 */
			// ||
			// MOrder.DELIVERYRULE_Manual.equals(oline.getParent().getDeliveryRule())
			) {
				throw new RuntimeException("@ActionNotSupported@");
			} else if (MOrder.DELIVERYRULE_Force.equals(oline.getParent().getDeliveryRule())) {
				forceIssue = true;
			}

			for (int i = 0; i < issue.length; i++) {
				int M_AttributeSetInstance_ID = 0;
				KeyNamePair key = (KeyNamePair) issue[i][0].get(0);
				Boolean isCritical = (Boolean) issue[i][0].get(1);
				String value = (String) issue[i][0].get(2);
				KeyNamePair productkey = (KeyNamePair) issue[i][0].get(3);
				int M_Product_ID = productkey.getKey();
				MProduct product = MProduct.get(getCtx(), M_Product_ID);
				BigDecimal qtyToDeliver = (BigDecimal) issue[i][0].get(4);
				BigDecimal qtyScrapComponent = (BigDecimal) issue[i][0].get(5);

				int PP_Order_BOMLine_ID = (Integer) key.getKey();
				if (PP_Order_BOMLine_ID > 0) {
					MPPOrderBOMLine orderBOMLine = new MPPOrderBOMLine(getCtx(), PP_Order_BOMLine_ID, get_TrxName());
					// Validate if AttributeSet generate instance
					M_AttributeSetInstance_ID = orderBOMLine.getM_AttributeSetInstance_ID();
				}

				MStorage[] storages = MPPOrder.getStorages(getCtx(), M_Product_ID, getM_Warehouse_ID(), M_AttributeSetInstance_ID, today,true, get_TrxName());

				MPPOrder.createIssue(this, key.getKey(), today, qtyToDeliver, qtyScrapComponent, Env.ZERO, storages, forceIssue);
			}
			MPPOrder.createReceipt(this, today, getQtyDelivered(), qtyShipment, getQtyScrap(), getQtyReject(), getM_Locator_ID(),
					getM_AttributeSetInstance_ID());
			// setQtyDelivered(getQtyOpen());
			// return DOCSTATUS_Closed;
		}
	}

	@Override
	public boolean postIt() {
		// TODO Auto-generated method stub
		return false;
	}
} // MPPOrder
