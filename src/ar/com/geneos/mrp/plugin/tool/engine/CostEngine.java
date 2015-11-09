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
 * Contributor(s): victor.perez@e-evolution.com http://www.e-evolution.com    *
 *****************************************************************************/

package ar.com.geneos.mrp.plugin.tool.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MClient;
import org.openXpertya.model.MCost;
import org.openXpertya.model.MCostElement;
import org.openXpertya.model.MCostType;
import org.openXpertya.model.MDocType;
import org.openXpertya.model.MInOutLine;
import org.openXpertya.model.MInventoryLine;
import org.openXpertya.model.MMatchInv;
import org.openXpertya.model.MMatchPO;
import org.openXpertya.model.MMovementLine;
import org.openXpertya.model.MOrderLine;
import org.openXpertya.model.MPeriod;
import org.openXpertya.model.MPeriodControl;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MProductPO;
import org.openXpertya.model.MProject;
import org.openXpertya.model.MProjectIssue;
import org.openXpertya.model.MTransaction;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;
import org.openXpertya.model.X_C_ProjectIssue;
import org.openXpertya.model.X_M_CostDetail;
import org.openXpertya.model.X_M_InOut;
import org.openXpertya.model.X_M_Inventory;
import org.openXpertya.model.X_M_MatchInv;
import org.openXpertya.model.X_M_MatchPO;
import org.openXpertya.model.X_M_Movement;
import org.openXpertya.model.X_M_Product;
import org.openXpertya.model.X_M_Production;
import org.openXpertya.model.X_M_Transaction;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;
import org.openXpertya.util.Util;

import ar.com.geneos.mrp.plugin.model.IDocumentLine;
import ar.com.geneos.mrp.plugin.model.LP_C_LandedCostAllocation;
import ar.com.geneos.mrp.plugin.model.LP_C_OrderLine;
import ar.com.geneos.mrp.plugin.model.LP_M_CostElement;
import ar.com.geneos.mrp.plugin.model.LP_M_CostType;
import ar.com.geneos.mrp.plugin.model.LP_M_InOutLine;
import ar.com.geneos.mrp.plugin.model.LP_M_InventoryLine;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchInv;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchPO;
import ar.com.geneos.mrp.plugin.model.LP_M_MovementLine;
import ar.com.geneos.mrp.plugin.model.LP_M_Transaction;
import ar.com.geneos.mrp.plugin.model.LP_PP_Cost_Collector;
import ar.com.geneos.mrp.plugin.model.LP_PP_Order;
import ar.com.geneos.mrp.plugin.model.MCostDetail;
import ar.com.geneos.mrp.plugin.model.MPPCostCollector;
import ar.com.geneos.mrp.plugin.model.MPPOrder;
import ar.com.geneos.mrp.plugin.util.MUMAcctSchema;
import ar.com.geneos.mrp.plugin.util.MUMCostElement;
import ar.com.geneos.mrp.plugin.util.MUMProduct;
import ar.com.geneos.mrp.plugin.util.MUMTransaction;
import ar.com.geneos.mrp.plugin.util.MUMatchInv;
import ar.com.geneos.mrp.plugin.util.MUMatchPO;

/**
 * Cost Engine
 * 
 * @author victor.perez@e-evolution.com http://www.e-evolution.com
 * 
 */
public class CostEngine {
	/** Logger */
	protected transient CLogger log = CLogger.getCLogger(getClass());

	/** AD_Table_ID's of documents */
	public final static int[] documentsTableID = { X_M_InOut.Table_ID, X_M_Inventory.Table_ID, X_M_Movement.Table_ID, X_M_Product.Table_ID,
			X_C_ProjectIssue.Table_ID, LP_PP_Cost_Collector.Table_ID, X_M_MatchPO.Table_ID, X_M_MatchInv.Table_ID };

	/** Table Names of documents */
	public final static String[] documentsTableName = { X_M_InOut.Table_Name, X_M_Inventory.Table_Name, X_M_Movement.Table_Name, X_M_Production.Table_Name,
			X_C_ProjectIssue.Table_Name, LP_PP_Cost_Collector.Table_Name, X_M_MatchPO.Table_Name, X_M_MatchInv.Table_Name };

	/**
	 * get seed cost
	 * 
	 * @param context
	 * @param productId
	 * @param trxName
	 * @return
	 */
	public static BigDecimal getSeedCost(Properties context, int productId, String trxName) {
		BigDecimal costThisLevel = Env.ZERO;
		for (MProductPO productPO : MProductPO.getOfProduct(context, productId, trxName)) {
			if (productPO.isCurrentVendor()) {
				if (productPO.getPriceLastInv().signum() != 0)
					costThisLevel = productPO.getPriceLastInv();
				else if (productPO.getPriceLastPO().signum() != 0)
					costThisLevel = productPO.getPriceLastPO();
				else if (productPO.getPricePO().signum() != 0)
					costThisLevel = productPO.getPricePO();
				else
					costThisLevel = productPO.getPriceList();
				return costThisLevel;
			}
		}
		return costThisLevel;
	}

	/**
	 * Get Actual Cost of Parent Product Based on Cost Type
	 * 
	 * @param accountSchema
	 * @param costTypeId
	 * @param costElementId
	 * @param order
	 * @return
	 */
	public static BigDecimal getParentActualCostByCostType(MAcctSchema accountSchema, int costTypeId, int costElementId, MPPOrder order) {
		StringBuffer whereClause = new StringBuffer();

		whereClause.append(MCostDetail.COLUMNNAME_M_CostType_ID + "=? AND ");
		whereClause.append(MCostDetail.COLUMNNAME_M_CostElement_ID + "=? AND ");
		whereClause.append(MCostDetail.COLUMNNAME_PP_Cost_Collector_ID);
		whereClause.append(" IN (SELECT PP_Cost_Collector_ID FROM PP_Cost_Collector cc WHERE cc.PP_Order_ID=? AND ");
		whereClause.append(" cc.CostCollectorType <> '").append(MPPCostCollector.COSTCOLLECTORTYPE_MaterialReceipt).append("')");

		BigDecimal actualCost = new Query(order.getCtx(), X_M_CostDetail.Table_Name, whereClause.toString(), order.get_TrxName()).setClient_ID()
				.setParameters(costTypeId, costElementId, order.getPP_Order_ID())
				.sum("(" + MCostDetail.COLUMNNAME_Amt + "+" + MCostDetail.COLUMNNAME_costamtll + ")");

		whereClause = new StringBuffer();
		whereClause
				.append(" EXISTS (SELECT 1 FROM PP_Cost_Collector cc WHERE PP_Cost_Collector_ID=M_Transaction.PP_Cost_Collector_ID AND cc.PP_Order_ID=? AND cc.M_Product_ID=? )");
		BigDecimal qtyDelivered = new Query(order.getCtx(), X_M_Transaction.Table_Name, whereClause.toString(), order.get_TrxName()).setClient_ID()
				.setParameters(order.getPP_Order_ID(), order.getM_Product_ID()).sum(LP_M_Transaction.COLUMNNAME_MovementQty);

		if (actualCost == null)
			actualCost = Env.ZERO;

		if (qtyDelivered.signum() != 0)
			actualCost = actualCost.divide(qtyDelivered, accountSchema.getCostingPrecision(), BigDecimal.ROUND_HALF_DOWN);

		// return actualCost.negate();
		return actualCost;
	}

	/*
	 * public static BigDecimal getParentActualCostByCostType(MAcctSchema
	 * accountSchema, MCostType costType, MCostElement costElement,
	 * X_M_Production production) { // Get BOM Cost - Sum of individual lines
	 * BigDecimal totalCost = Env.ZERO; for (MProductionLine productionLine :
	 * ((MProduction)production).getLines()) { if (productionLine.isParent())
	 * continue;
	 * 
	 * String productType = productionLine.getM_Product().getProductType();
	 * 
	 * BigDecimal cost = BigDecimal.ZERO;
	 * 
	 * if (X_M_Product.PRODUCTTYPE_Item.equals(productType)) { cost =
	 * MCostDetail.getCostByModel(accountSchema.getC_AcctSchema_ID(),
	 * costType.getM_CostType_ID() , costElement.getM_CostElement_ID() ,
	 * productionLine); } else
	 * if(X_M_Product.PRODUCTTYPE_Resource.equals(productType)) { MCost
	 * costDimension = MCost.validateCostForCostType(accountSchema, costType,
	 * costElement, productionLine.getM_Product_ID(),
	 * productionLine.getAD_Org_ID(),
	 * productionLine.getM_Locator().getM_Warehouse_ID(),
	 * productionLine.getM_AttributeSetInstance_ID(),
	 * productionLine.get_TrxName());
	 * 
	 * if (costDimension != null && costDimension.getCurrentCostPrice().signum()
	 * != 0 ) cost =
	 * costDimension.getCurrentCostPrice().multiply(productionLine.
	 * getMovementQty().negate()); }
	 * 
	 * 
	 * if (cost != null && cost.signum() != 0) totalCost = totalCost.add(cost);
	 * 
	 * }
	 * 
	 * BigDecimal unitCost = Env.ZERO; if
	 * (production.getProductionQty().signum() != 0 && totalCost.signum() != 0)
	 * unitCost = totalCost.divide(production.getProductionQty() ,
	 * accountSchema.getCostingPrecision() , BigDecimal.ROUND_HALF_UP);
	 * 
	 * return unitCost; }
	 */
	protected static BigDecimal roundCost(BigDecimal price, int accountSchemaId) {
		// Fix Cost Precision
		int precision = MAcctSchema.get(Env.getCtx(), accountSchemaId).getCostingPrecision();
		BigDecimal priceRounded = price;
		if (priceRounded.scale() > precision) {
			priceRounded = priceRounded.setScale(precision, RoundingMode.HALF_UP);
		}
		return priceRounded;
	}

	/**
	 * Generate by transaction
	 * 
	 * @param transaction
	 */
	public void createCostDetail(MTransaction transaction, IDocumentLine model) {

		MClient client = new MClient(transaction.getCtx(), transaction.getAD_Client_ID(), transaction.get_TrxName());
		StringBuilder description = new StringBuilder();
		if (!Util.isEmpty(model.getDescription(), true))
			description.append(model.getDescription());
		if (model != null) {
			description.append(model.isSOTrx() ? "(|->)" : "(|<-)");
		}

		List<MAcctSchema> acctSchemas = new ArrayList(Arrays.asList(MUMAcctSchema.getClientAcctSchema(transaction.getCtx(), transaction.getAD_Client_ID(),
				transaction.get_TrxName())));

		List<MCostElement> costElements = MUMCostElement.getCostElement(transaction.getCtx(), transaction.get_TrxName());
		List<MCostType> costTypes = MCostType.get(transaction.getCtx(), transaction.get_TrxName());
		for (MAcctSchema accountSchema : acctSchemas) {
			for (MCostType costType : costTypes) {
				if (!costType.isActive())
					continue;
				/**
				 * Libero to Libertya migration Always is costImmediate
				 */
				for (MCostElement costElement : costElements) {
					createCostDetail(accountSchema, costType, costElement, transaction, model, /*
																								 * client
																								 * .
																								 * isCostImmediate
																								 * (
																								 * )
																								 */true);
				}
			}
		}
	}

	/**
	 * Create Cost Detail
	 * 
	 * @param accountSchema
	 *            Account Schema
	 * @param transaction
	 *            Material Transaction
	 * @param model
	 *            IDocumentLine
	 * @param costType
	 *            Cost Type
	 * @param costElement
	 *            Cost Element
	 */
	public void createCostDetail(MAcctSchema accountSchema, MCostType costType, MCostElement costElement, MTransaction transaction, IDocumentLine model,
			boolean force) {

		if (!force)
			return;

		BigDecimal costThisLevel = Env.ZERO;
		BigDecimal costLowLevel = Env.ZERO;
		String costingLevel = MUMProduct.getCostingLevel(MProduct.get(transaction.getCtx(), transaction.getM_Product_ID()), accountSchema,
				transaction.getAD_Org_ID());

		// The Change of price in the Invoice Line is not generated cost
		// adjustment for Average PO Costing method
		/*
		 * if (model instanceof MMatchInv &&
		 * LP_M_CostType.COSTINGMETHOD_AveragePO.equals(costType
		 * .getCostingMethod())) return;
		 */

		// The Change of price in the Invoice Line is not generated cost
		// adjustment for Average PO Costing method
		if (model instanceof LP_M_MatchPO && LP_M_CostType.COSTINGMETHOD_AverageInvoice.equals(costType.getCostingMethod()))
			return;

		if (model instanceof LP_C_LandedCostAllocation) {
			LP_C_LandedCostAllocation allocation = (LP_C_LandedCostAllocation) model;
			costThisLevel = allocation.getPriceActual();
		}

		MCost cost = MCost.validateCostForCostType(accountSchema, costType, costElement, transaction.getM_Product_ID(), transaction.getAD_Org_ID(),
				MUMTransaction.getM_Warehouse_ID(transaction), transaction.getM_AttributeSetInstance_ID(), transaction.get_TrxName());

		// get the cost for positive transaction
		if ((MCostElement.COSTELEMENTTYPE_Material.equals(costElement.getCostElementType()) || LP_M_CostElement.COSTELEMENTTYPE_LandedCost.equals(costElement
				.getCostElementType()))
				&& transaction.getMovementType().contains("+")
				&& !LP_M_CostType.COSTINGMETHOD_StandardCosting.equals(costType.getCostingMethod())) {
			if (model instanceof MMovementLine || model instanceof MInventoryLine
					|| (model instanceof MInOutLine && MTransaction.MOVEMENTTYPE_CustomerReturns.equals(transaction.getMovementType()))) {
				costThisLevel = getCostThisLevel(accountSchema, costType, costElement, transaction, model, costingLevel);

				// If cost this level is zero and is a physical inventory then
				// try get cost from physical inventory
				if (model instanceof MInventoryLine && costThisLevel.signum() == 0
						&& MCostElement.COSTELEMENTTYPE_Material.equals(costElement.getCostElementType())) {
					MInventoryLine inventoryLine = (MInventoryLine) model;
					// Use the cost only for Physical Inventory
					if (inventoryLine.getQtyInternalUse().signum() == 0)
						/**
						 * Libero to libertya migration camnio
						 * getCurrentCostPrice por getCost()
						 */
						costThisLevel = inventoryLine.getCost();// .getCurrentCostPrice();

					if (costThisLevel.signum() == 0)
						costThisLevel = getCostThisLevel(accountSchema, costType, costElement, transaction, model, costingLevel);
				}
				// Get cost from movement from if it > that zero replace cost
				// This Level
				if (model instanceof MMovementLine) {
					MTransaction transactionFrom = MUMTransaction.getByDocumentLine(model, MTransaction.MOVEMENTTYPE_MovementFrom);
					BigDecimal costMovementFrom = getCostThisLevel(accountSchema, costType, costElement, transactionFrom == null ? transaction
							: transactionFrom, model, costingLevel);
					if (costMovementFrom.signum() > 0)
						costThisLevel = costMovementFrom;
				}
			} else if (MCostElement.COSTELEMENTTYPE_Material.equals(costElement.getCostElementType())) {
				if (model.getPriceActual().signum() != 0)
					costThisLevel = model.getPriceActual();
				// else of cost should only be take from source document in this
				// case purchase order
				// costThisLevel = cost.getCurrentCostPrice();
			}
		}

		if (!LP_M_CostType.COSTINGMETHOD_StandardCosting.equals(costType.getCostingMethod())) {
			if (model instanceof MPPCostCollector) {
				MPPCostCollector costCollector = (MPPCostCollector) model;
				if (MPPCostCollector.COSTCOLLECTORTYPE_MaterialReceipt.equals(costCollector.getCostCollectorType())) {
					// get Actual Cost for Cost Type and Cost Element
					costLowLevel = CostEngine.getParentActualCostByCostType(accountSchema, costType.getM_CostType_ID(), costElement.getM_CostElement_ID(),
							costCollector.getPP_Order());
				}
			}/*
			 * if (model instanceof MProductionLine) {
			 * 
			 * MProductionLine productionLine = (MProductionLine) model; if
			 * (productionLine.isParent()) costThisLevel =
			 * CostEngine.getParentActualCostByCostType(accountSchema, costType,
			 * costElement, productionLine.getM_Production());
			 * 
			 * if (costThisLevel.signum() == 0) costThisLevel =
			 * cost.getCurrentCostPrice(); if (costThisLevel.signum() == 0 &&
			 * MCostElement
			 * .COSTELEMENTTYPE_Material.equals(costElement.getCostElementType
			 * ())) costThisLevel = getSeedCost(transaction.getCtx(),
			 * transaction.getM_Product_ID(), transaction.get_TrxName());
			 * 
			 * // Material Receipt for Production light if
			 * (productionLine.isParent()) { // get Actual Cost for Cost Type
			 * and Cost Element // if the product is purchase then no use low
			 * level if (!productionLine.getM_Product().isPurchased()) {
			 * costLowLevel = costThisLevel; costThisLevel = Env.ZERO; } } else
			 * if (productionLine.getMovementQty().signum() < 0) costLowLevel =
			 * Env.ZERO; }
			 */
		} else if (LP_M_CostType.COSTINGMETHOD_StandardCosting.equals(costType.getCostingMethod())) {
			costThisLevel = cost.getCurrentCostPrice();
			costLowLevel = cost.getCurrentCostPriceLL();
			if (costThisLevel.signum() == 0 && MCostElement.COSTELEMENTTYPE_Material.equals(costElement.getCostElementType())) {
				costThisLevel = getSeedCost(transaction.getCtx(), transaction.getM_Product_ID(), transaction.get_TrxName());
				if (costThisLevel.signum() == 0)
					if (model instanceof MInOutLine && !model.isSOTrx()) {
						MInOutLine inOutLine = (MInOutLine) model;
						costThisLevel = new MOrderLine(inOutLine.getCtx(), inOutLine.getC_OrderLine_ID(), inOutLine.get_TrxName()).getPriceActual();
					}
				if (costThisLevel.signum() != 0) {
					cost.setCurrentCostPrice(costThisLevel);
					cost.save();
				}
			}
		}

		final ICostingMethod method = CostingMethodFactory.get().getCostingMethod(costType.getCostingMethod());
		method.setCostingMethod(accountSchema, transaction, model, cost, costThisLevel, costLowLevel, model.isSOTrx());
		method.process();
	}

	/*
	 * public void createCostDetail(MAcctSchema accountSchema, CostComponent
	 * costCollector, IDocumentLine model, Boolean isSOTrx, boolean
	 * setProcessed) { final String idColumnName = model.get_TableName() +
	 * "_ID"; final String trxName = model.get_TrxName();
	 * 
	 * // Delete Unprocessed zero Differences String sql =
	 * "DELETE M_CostDetail " +
	 * "WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
	 * + " AND " + idColumnName + "=?" + " AND C_AcctSchema_ID=?" +
	 * " AND M_AttributeSetInstance_ID=?"; if (isSOTrx != null) { sql += " AND "
	 * + I_M_CostDetail.COLUMNNAME_IsSOTrx + "=" + (isSOTrx ? "'Y'" : "'N'"); }
	 * int no = DB.executeUpdateEx( sql, new Object[] { model.get_ID(),
	 * accountSchema.getC_AcctSchema_ID(), model.getM_AttributeSetInstance_ID()
	 * }, trxName); if (no != 0) log.config("Deleted #" + no);
	 * 
	 * // Build Description string StringBuilder description = new
	 * StringBuilder(); if (!Util.isEmpty(model.getDescription(), true))
	 * description.append(model.getDescription()); if (isSOTrx != null) {
	 * description.append(isSOTrx ? "(|->)" : "(|<-)"); }
	 * 
	 * List<MCost> costs = MCost.getForProduct(accountSchema, model); for (MCost
	 * cost : costs) { final MCostElement ce = MCostElement.get(cost.getCtx(),
	 * cost.getM_CostElement_ID()); if
	 * (MCostElement.COSTELEMENTTYPE_LandedCost.equals(ce
	 * .getCostElementType())) { // skip landed costs continue; }
	 * 
	 * final ICostingMethod method = CostingMethodFactory.get()
	 * .getCostingMethod(cost.getCostingMethod());
	 * method.setCostingMethod(accountSchema, null, model, cost,
	 * model.getPriceActual(), Env.ZERO, isSOTrx);
	 * 
	 * method.process(); } }
	 */

	// Create cost detail for by document

	public void createCostDetailForLandedCostAllocation(LP_C_LandedCostAllocation allocation) {
		MInOutLine ioLine = new MInOutLine(allocation.getCtx(), allocation.getM_InOutLine_ID(), allocation.get_TrxName());

		for (MTransaction transaction : MUMTransaction.getByInOutLine(ioLine)) {
			for (MAcctSchema accountSchema : MAcctSchema.getClientAcctSchema(allocation.getCtx(), allocation.getAD_Client_ID())) {
				List<MCostType> costTypes = MCostType.get(allocation.getCtx(), allocation.get_TrxName());

				for (MCostType costType : costTypes) {
					MCostElement costElement = new MCostElement(allocation.getCtx(), allocation.getM_CostElement_ID(), allocation.get_TrxName());
					CostEngineFactory.getCostEngine(allocation.getAD_Client_ID()).createCostDetail(accountSchema, costType, costElement, transaction,
							allocation, true);
				}
			}
		}
	}

	/*
	 * public static int deleteCostDetail(MAcctSchema accountSchema, int
	 * costElementId, int attributeSetInstanceId, IDocumentLine model) { //
	 * Delete Unprocessed zero Differences String sql = "DELETE " +
	 * MCostDetail.Table_Name +
	 * " WHERE Processed='N' AND COALESCE(DeltaAmt,0)=0 AND COALESCE(DeltaQty,0)=0"
	 * + " AND " + model.get_TableName() + "_ID=?" + " AND " +
	 * MCostDetail.COLUMNNAME_C_AcctSchema_ID + "=?" + " AND " +
	 * MCostDetail.COLUMNNAME_M_AttributeSetInstance_ID + "=?" // +
	 * " AND "+MCostDetail.COLUMNNAME_M_CostType_ID+"=?" + " AND " +
	 * MCostDetail.COLUMNNAME_M_CostElement_ID + "=?"; Object[] parameters = new
	 * Object[] { model.get_ID(), accountSchema.getC_AcctSchema_ID(),
	 * attributeSetInstanceId, // as.getM_CostType_ID(), costElementId};
	 * 
	 * int no = DB.executeUpdateEx(sql, parameters, model.get_TrxName()); //if
	 * (no != 0) // log.config("Deleted #" + no); return no; }
	 */

	public static boolean isActivityControlElement(MCostElement element) {
		String costElementType = element.getCostElementType();
		return MCostElement.COSTELEMENTTYPE_Resource.equals(costElementType) || MCostElement.COSTELEMENTTYPE_Overhead.equals(costElementType)
				|| MCostElement.COSTELEMENTTYPE_BurdenMOverhead.equals(costElementType);
	}

	public static List<MAcctSchema> getAcctSchema(PO po) {
		int AD_Org_ID = po.getAD_Org_ID();
		MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(po.getCtx(), po.getAD_Client_ID());
		ArrayList<MAcctSchema> list = new ArrayList<MAcctSchema>(ass.length);
		for (MAcctSchema as : ass) {
			if (!as.isSkipOrg(AD_Org_ID))
				list.add(as);
		}
		return list;
	}

	static public String getIDColumnName(ar.com.geneos.mrp.plugin.model.IDocumentLine model) {
		String idColumnName = model.get_TableName() + "_ID";
		if (model instanceof MMatchPO) {
			idColumnName = LP_C_OrderLine.COLUMNNAME_C_OrderLine_ID;
		}
		/*
		 * if (model instanceof MMatchInv) { idColumnName =
		 * I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID; }
		 */
		return idColumnName;
	}

	static public int getIDColumn(IDocumentLine model) {
		int id = model.get_ID();

		if (model instanceof MMatchPO) {
			id = ((MMatchPO) model).getC_OrderLine_ID();
		}
		if (model instanceof MMatchInv) {
			id = ((MMatchInv) model).getC_InvoiceLine_ID();
		}
		return id;
	}

	/**
	 * get cost this level
	 * 
	 * @param accountSchema
	 * @param costType
	 * @param costElement
	 * @param transaction
	 * @param model
	 * @param costingLevel
	 * @return
	 */
	public static BigDecimal getCostThisLevel(MAcctSchema accountSchema, MCostType costType, MCostElement costElement, MTransaction transaction,
			IDocumentLine model, String costingLevel) {
		BigDecimal costThisLevel = Env.ZERO;
		MCostDetail lastCostDetail = MCostDetail.getLastTransaction(model, transaction, accountSchema.getC_AcctSchema_ID(), costType.getM_CostType_ID(),
				costElement.getM_CostElement_ID(), model.getDateAcct(), costingLevel);
		if (lastCostDetail != null) {

			// Return of unit cost from last transaction
			// transaction quantity is different of zero
			// then cost this level is equal that:
			// (Total Cost transaction + cost adjustments) divide by transaction
			// quantity
			if (lastCostDetail.getQty().signum() != 0) {
				costThisLevel = lastCostDetail.getCostAmt().add(lastCostDetail.getCostAdjustment())
						.divide(lastCostDetail.getQty(), accountSchema.getCostingPrecision(), BigDecimal.ROUND_HALF_UP).abs();
			}
			// return unit cost from last transaction
			// transaction quantity is zero
			// the cost this level is equal that:
			// (Total Cost Transaction + cost adjustments + accumulate cost)
			// divide between on hand quantity
			else if (lastCostDetail.getCumulatedQty().add(lastCostDetail.getQty()).signum() != 0) {
				costThisLevel = lastCostDetail.getCostAmt().add(lastCostDetail.getCostAdjustment()).add(lastCostDetail.getCumulatedAmt())
						.divide(lastCostDetail.getCumulatedQty().add(lastCostDetail.getQty()), accountSchema.getCostingPrecision(), BigDecimal.ROUND_HALF_UP)
						.abs();

				return costThisLevel;
			}
			// Return of unit cost from inventory value
			// Cumulated quantity is different of zero
			// then cost this level is equal that:
			// (Total Cost transaction + cost adjustments + Cumulated amount)
			// divide by On hand Quantity
			else if (lastCostDetail.getCumulatedQty().signum() != 0) {
				costThisLevel = lastCostDetail.getCumulatedAmt()
						.divide(lastCostDetail.getCumulatedQty(), accountSchema.getCostingPrecision(), BigDecimal.ROUND_HALF_UP).abs();

				return costThisLevel;
			}

		}

		return costThisLevel;
	}

	/**
	 * clear Accounting
	 * 
	 * @param accountSchema
	 * @param transaction
	 */
	public void clearAccounting(MAcctSchema accountSchema, MTransaction transaction) {

		if (transaction.getM_InOutLine_ID() > 0) {
			LP_M_InOutLine line = MUMTransaction.getM_InOutLine(transaction);

			if (!clearAccounting(accountSchema, MUMAcctSchema.getM_CostType(accountSchema), line.getParent(), line.getDateAcct()))
				return;

			// get Purchase matches
			List<LP_M_MatchPO> orderMatches = MUMatchPO.getInOutLine(line);
			for (LP_M_MatchPO match : orderMatches) {
				if (!clearAccounting(accountSchema, MUMAcctSchema.getM_CostType(accountSchema), match, line.getDateAcct()))
					return;
			}

			// get invoice matches
			List<LP_M_MatchInv> invoiceMatches = MUMatchInv.getInOutLine(line);
			for (LP_M_MatchInv match : invoiceMatches) {
				if (!clearAccounting(accountSchema, MUMAcctSchema.getM_CostType(accountSchema), match, line.getDateAcct()))
					return;
			}

		} else if (transaction.getC_ProjectIssue_ID() > 0) {
			MProjectIssue line = MUMTransaction.getC_ProjectIssue(transaction);
			if (!clearAccounting(accountSchema, MUMAcctSchema.getM_CostType(accountSchema),
					new MProject(line.getCtx(), line.getC_Project_ID(), line.get_TrxName()), line.getMovementDate()))
				return;
		}

		else if (transaction.getM_InventoryLine_ID() > 0) {
			LP_M_InventoryLine line = MUMTransaction.getM_InventoryLine(transaction);
			if (!clearAccounting(accountSchema, MUMAcctSchema.getM_CostType(accountSchema), line.getParent(), line.getDateAcct()))
				return;
		} else if (transaction.getM_MovementLine_ID() > 0) {
			LP_M_MovementLine line = MUMTransaction.getM_MovementLine(transaction);
			if (!clearAccounting(accountSchema, MUMAcctSchema.getM_CostType(accountSchema), line.getParent(), line.getDateAcct()))
				return;
		}

		/**
		 * Libero to Libertya migration Not Used
		 */
		/*
		 * else if (transaction.getM_ProductionLine_ID() > 0) { MProductionLine
		 * line = (MProductionLine) transaction.getM_ProductionLine();
		 * MProduction production = (MProduction)
		 * line.getM_ProductionPlan().getM_Production(); if
		 * (!clearAccounting(accountSchema, accountSchema.getM_CostType(),
		 * production, production.getMovementDate())) return;
		 * 
		 * }
		 */else if (MUMTransaction.getPP_Cost_Collector_ID(transaction) > 0) {
			MPPCostCollector costCollector = MUMTransaction.getPP_Cost_Collector(transaction);
			if (!clearAccounting(accountSchema, MUMAcctSchema.getM_CostType(accountSchema), costCollector, costCollector.getDateAcct()))
				;
			return;
		} else {
			System.out.println("Document does not exist :" + transaction);
		}

	}

	/**
	 * Clear Accounting
	 * 
	 * @param accountSchema
	 * @param costType
	 * @param model
	 * @param dateAcct
	 * @return true clean
	 */
	public boolean clearAccounting(MAcctSchema accountSchema, MCostType costType, PO model, Timestamp dateAcct) {

		// check if costing type need reset accounting
		if (!accountSchema.getCostingMethod().equals(costType.getCostingMethod()))
			return false;
		final String docBaseType;
		// check if account period is open
		if (model instanceof MMatchInv)
			docBaseType = MPeriodControl.DOCBASETYPE_MatchInvoice;
		else if (model instanceof MMatchPO)
			docBaseType = MPeriodControl.DOCBASETYPE_MatchPO;
		/*
		 * else if (model instanceof MProduction) docBaseType =
		 * MPeriodControl.DOCBASETYPE_MaterialProduction;
		 */
		else {
			MDocType dt = MDocType.get(model.getCtx(), (Integer) model.get_Value(LP_PP_Order.COLUMNNAME_C_DocType_ID));
			docBaseType = dt.getDocBaseType();
		}

		Boolean openPeriod = MPeriod.isOpen(model.getCtx(), dateAcct, docBaseType, model.getAD_Org_ID());
		if (!openPeriod) {
			System.out.println("Period closed.");
			return false;
		}

		final String sqlUpdate = "UPDATE " + model.get_TableName() + " SET Posted = 'N' WHERE " + model.get_TableName() + "_ID=" + model.getID();
		DB.executeUpdate(sqlUpdate, false, model.get_TrxName());
		// Delete account
		final String sqldelete = "DELETE FROM Fact_Acct WHERE Record_ID =" + model.getID() + " AND AD_Table_ID=" + model.get_Table_ID();
		DB.executeUpdate(sqldelete, false, model.get_TrxName());
		return true;
	}
}
