package ar.com.geneos.mrp.plugin.tool.engine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MClient;
import org.openXpertya.model.MCost;
import org.openXpertya.model.MCostElement;
import org.openXpertya.model.MCostType;
import org.openXpertya.model.MDocType;
import org.openXpertya.model.MInvoice;
import org.openXpertya.model.MInvoiceLine;
import org.openXpertya.model.MPeriod;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MTransaction;
import org.openXpertya.util.Env;
import org.openXpertya.util.Util;
import org.openXpertya.wf.MWFNode;

import ar.com.geneos.mrp.plugin.model.IDocumentLine;
import ar.com.geneos.mrp.plugin.model.LP_C_AcctSchema;
import ar.com.geneos.mrp.plugin.model.LP_C_LandedCostAllocation;
import ar.com.geneos.mrp.plugin.model.LP_M_CostType;
import ar.com.geneos.mrp.plugin.model.LP_M_InOutLine;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchInv;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchPO;
import ar.com.geneos.mrp.plugin.model.MCostDetail;
import ar.com.geneos.mrp.plugin.model.MPPCostCollector;
import ar.com.geneos.mrp.plugin.model.MPPOrderBOMLine;
import ar.com.geneos.mrp.plugin.model.MPPOrderCost;
import ar.com.geneos.mrp.plugin.model.RoutingService;
import ar.com.geneos.mrp.plugin.model.RoutingServiceFactory;
import ar.com.geneos.mrp.plugin.util.MUMCostElement;
import ar.com.geneos.mrp.plugin.util.MUMProduct;
import ar.com.geneos.mrp.plugin.util.MUMTransaction;

public class StandardCostingMethod extends AbstractCostingMethod implements ICostingMethod {

	public void setCostingMethod(MAcctSchema accountSchema, MTransaction transaction, IDocumentLine model, MCost dimension, BigDecimal costThisLevel,
			BigDecimal costLowLevel, Boolean isSalesTransaction) {
		this.accountSchema = accountSchema;
		this.transaction = transaction;
		this.dimension = dimension;
		this.costThisLevel = (costThisLevel == null ? Env.ZERO : costThisLevel);
		this.costLowLevel = (costLowLevel == null ? Env.ZERO : costLowLevel);
		this.isSalesTransaction = isSalesTransaction;
		this.model = MUMTransaction.getDocumentLine(transaction);
		this.costingLevel = MUMProduct.getCostingLevel(MProduct.get(this.transaction.getCtx(), this.transaction.getM_Product_ID()), this.accountSchema,
				this.transaction.getAD_Org_ID());
		this.costDetail = MCostDetail.getByTransaction(this.model, this.transaction, this.accountSchema.getC_AcctSchema_ID(),
				this.dimension.getM_CostType_ID(), this.dimension.getM_CostElement_ID());
		this.movementQuantity = transaction.getMovementQty();
		// Setting Accounting period status
		MDocType documentType = new MDocType(transaction.getCtx(), MUMTransaction.getDocumentLine(transaction).getC_DocType_ID(), transaction.get_TrxName());
		this.isOpenPeriod = MPeriod.isOpen(transaction.getCtx(), model.getDateAcct(), documentType.getDocBaseType(), transaction.getAD_Org_ID());

		// Setting Date Accounting based on Open Period
		if (this.isOpenPeriod)
			this.dateAccounting = model.getDateAcct();
		else if (model instanceof LP_C_LandedCostAllocation) {
			LP_C_LandedCostAllocation document = (LP_C_LandedCostAllocation) model;
			this.dateAccounting = new MInvoice(document.getCtx(),
					new MInvoiceLine(document.getCtx(), document.getC_InvoiceLine_ID(), document.get_TrxName()).getC_Invoice_ID(), document.get_TrxName())
					.getDateAcct();
		} else if (model instanceof LP_M_MatchInv) {
			LP_M_MatchInv document = (LP_M_MatchInv) model;
			this.dateAccounting = new MInvoice(document.getCtx(),
					new MInvoiceLine(document.getCtx(), document.getC_InvoiceLine_ID(), document.get_TrxName()).getC_Invoice_ID(), document.get_TrxName())
					.getDateAcct();
		} else
			this.dateAccounting = null; // Is Necessary define that happen in
										// this case when period is close
	}

	private void calculate() {
		/**
		 * Libero to Libertya migration Reversal Cost not implemented
		 */
		/*
		 * if (model.getReversalLine_ID() > 0) return;
		 */
		// try find the last cost detail transaction
		lastCostDetail = MCostDetail.getLastTransaction(model, transaction, accountSchema.getC_AcctSchema_ID(), dimension.getM_CostType_ID(),
				dimension.getM_CostElement_ID(), dateAccounting, costingLevel);

		// created a new instance cost detail to process calculated cost
		if (lastCostDetail == null) {
			lastCostDetail = new MCostDetail(transaction, accountSchema.getC_AcctSchema_ID(), dimension.getM_CostType_ID(), dimension.getM_CostElement_ID(),
					Env.ZERO, Env.ZERO, Env.ZERO, transaction.get_TrxName());
			lastCostDetail.setDateAcct(dateAccounting);
		}

		if (movementQuantity.signum() < 0) {
			currentCostPrice = dimension.getCurrentCostPrice();
			currentCostPriceLowerLevel = dimension.getCurrentCostPriceLL();
			amount = movementQuantity.multiply(currentCostPrice);
			amountLowerLevel = movementQuantity.multiply(currentCostPriceLowerLevel);
			accumulatedQuantity = dimension.getCumulatedQty().add(movementQuantity);
			accumulatedAmount = dimension.getCumulatedAmt().add(amount);
			accumulatedAmountLowerLevel = dimension.getCumulatedAmtLL().add(amountLowerLevel);
			return;
		}

		if (costDetail != null) {
			amount = movementQuantity.multiply(costDetail.getCurrentCostPrice());
			amountLowerLevel = movementQuantity.multiply(costDetail.getCurrentCostPriceLL());
			accumulatedQuantity = costDetail.getCumulatedQty().add(movementQuantity);
			accumulatedAmount = costDetail.getCumulatedAmt().add(amount);
			accumulatedAmountLowerLevel = costDetail.getCumulatedAmtLL().add(amountLowerLevel);
			currentCostPrice = dimension.getCurrentCostPrice();
			currentCostPriceLowerLevel = dimension.getCurrentCostPriceLL();
			adjustCost = currentCostPrice.multiply(dimension.getCumulatedQty()).subtract(dimension.getCumulatedAmt());
			adjustCost = currentCostPriceLowerLevel.multiply(dimension.getCumulatedQty()).subtract(dimension.getCumulatedAmtLL());
			return;
		}

		amount = movementQuantity.multiply(dimension.getCurrentCostPrice());
		amountLowerLevel = movementQuantity.multiply(dimension.getCurrentCostPriceLL());
		accumulatedAmount = dimension.getCumulatedAmt().add(amount).add(adjustCost);
		accumulatedAmountLowerLevel = dimension.getCumulatedAmtLL().add(amountLowerLevel).add(adjustCostLowerLevel);
		accumulatedQuantity = dimension.getCumulatedQty().add(movementQuantity);
		currentCostPrice = dimension.getCurrentCostPrice();
		currentCostPriceLowerLevel = dimension.getCurrentCostPriceLL();
	}

	private void createCostDetail() {
		final String idColumnName = CostEngine.getIDColumnName(model);
		/**
		 * Libero to Libertya migration Reversal Cost not implemented
		 */
		/*
		 * if (model.getReversalLine_ID() > 0) { createReversalCostDetail();
		 * return; }
		 */
		int seqNo = lastCostDetail.getSeqNo() + 10;

		if (model instanceof MPPCostCollector) {
			MPPCostCollector cc = (MPPCostCollector) model;
			if (MPPCostCollector.COSTCOLLECTORTYPE_MethodChangeVariance.equals(cc.getCostCollectorType())) {
				createMethodVariances(cc);
			} else if (MPPCostCollector.COSTCOLLECTORTYPE_UsegeVariance.equals(cc.getCostCollectorType())) {
				createUsageVariances(cc);
			} else if (MPPCostCollector.COSTCOLLECTORTYPE_RateVariance.equals(cc.getCostCollectorType())) {
				createRateVariances(cc);
			} else if (MPPCostCollector.COSTCOLLECTORTYPE_MixVariance.equals(cc.getCostCollectorType())) {
				; // no implement
			}
		}

		if (costDetail == null) {
			costDetail = new MCostDetail(transaction, accountSchema.getC_AcctSchema_ID(), dimension.getM_CostType_ID(), dimension.getM_CostElement_ID(),
					currentCostPrice.multiply(movementQuantity).abs(), currentCostPriceLowerLevel.multiply(movementQuantity).abs(), movementQuantity,
					transaction.get_TrxName());
			costDetail.setDateAcct(dateAccounting);
			costDetail.setSeqNo(seqNo);

		}

		if (adjustCost.signum() != 0 || adjustCostLowerLevel.signum() != 0) {
			String description = costDetail.getDescription() != null ? costDetail.getDescription() : "";
			// update adjustment cost this level
			if (adjustCost.signum() != 0) {
				costDetail.setCostAdjustmentDate(model.getDateAcct());
				costDetail.setCostAdjustment(adjustCost);
				// costDetail.setCostAmt(BigDecimal.ZERO);
				costDetail.setAmt(costDetail.getAmt().add(costDetail.getCostAdjustment()));
				costDetail.setDescription(description + " Adjust Cost:" + adjustCost);
			}
			// update adjustment cost lower level
			if (adjustCostLowerLevel.signum() != 0) {
				description = costDetail.getDescription() != null ? costDetail.getDescription() : "";
				costDetail.setCostAdjustmentDateLL(model.getDateAcct());
				costDetail.setCostAdjustmentLL(adjustCostLowerLevel);
				// costDetail.setCostAmtLL(BigDecimal.ZERO);
				costDetail.setAmt(costDetail.getCostAmtLL().add(costDetail.getCostAdjustmentLL()));
				costDetail.setDescription(description + " Adjust Cost LL:" + adjustCost);
			}
		}
		costDetail.set_ValueOfColumn(idColumnName, model.get_ID());
		if (!costDetail.get_Value(idColumnName).equals(model.get_ID()))
			throw new RuntimeException("Cannot set " + idColumnName);

		// set if transaction is sales order type or not
		if (isSalesTransaction != null)
			costDetail.setIsSOTrx(isSalesTransaction);
		else
			costDetail.setIsSOTrx(model.isSOTrx());

		// set transaction id
		if (transaction != null)
			costDetail.setM_Transaction_ID(transaction.getM_Transaction_ID());

		costDetail.setCumulatedQty(dimension.getCumulatedQty());
		costDetail.setCumulatedAmt(dimension.getCumulatedAmt());
		costDetail.setCurrentCostPrice(dimension.getCurrentCostPrice());
		costDetail.setCumulatedAmtLL(dimension.getCumulatedAmtLL());
		costDetail.setCurrentCostPriceLL(dimension.getCurrentCostPriceLL());
		StringBuilder description = new StringBuilder();
		if (!Util.isEmpty(model.getDescription(), true))
			description.append(model.getDescription());
		if (isSalesTransaction != null) {
			description.append(isSalesTransaction ? "(|->)" : "(|<-)");
		}
		if (transaction != null)
			costDetail.setM_Transaction_ID(transaction.getM_Transaction_ID());
		costDetail.setDescription(description.toString());
		updateAmountCost();
		costDetail.save();
		// CostAms

		return;
	}

	public void createCostAdjustment() {
	}

	public MCostDetail process() {
		calculate();
		createCostDetail();
		updateInventoryValue();
		createCostAdjustment();
		return costDetail;
	}

	/**
	 * Average Invoice Get the New Current Cost Price This Level
	 * 
	 * @param cd
	 *            Cost Detail
	 * @param scale
	 *            Scale
	 * @param roundingMode
	 *            Rounding Mode
	 * @return New Current Cost Price This Level
	 */
	public BigDecimal getNewCurrentCostPrice(MCostDetail cd, int scale, int roundingMode) {
		if (getNewAccumulatedQuantity(cd).signum() != 0)
			return cd.getCurrentCostPrice();
		else
			return BigDecimal.ZERO;
	}

	/**
	 * Average Invoice Get the New Cumulated Amt This Level
	 * 
	 * @param cd
	 *            Cost Detail
	 * @return New Cumulated Amt This Level
	 */
	public BigDecimal getNewAccumulatedAmount(MCostDetail cd) {
		return cd.getCumulatedAmt().add(cd.getCostAmt()).add(cd.getCostAdjustment());
	}

	/**
	 * Average Invoice Get the New Current Cost Price low level
	 * 
	 * @param cd
	 *            Cost Detail
	 * @param scale
	 *            Scale
	 * @param roundingMode
	 *            Rounding Mode
	 * @return New Current Cost Price low level
	 */
	public BigDecimal getNewCurrentCostPriceLowerLevel(MCostDetail cd, int scale, int roundingMode) {
		if (getNewAccumulatedQuantity(cd).signum() != 0)
			return cd.getCurrentCostPriceLL();
		else
			return BigDecimal.ZERO;
	}

	/**
	 * Average Invoice Get the new Cumulated Amt Low Level
	 * 
	 * @param cd
	 *            MCostDetail
	 * @return New Cumulated Am Low Level
	 */
	public BigDecimal getNewAccumulatedAmountLowerLevel(MCostDetail cd) {
		return cd.getCumulatedAmtLL().add(cd.getCostAmtLL()).add(cd.getCostAdjustmentLL());
	}

	/**
	 * Average Invoice Get the new Cumulated Qty
	 * 
	 * @param cd
	 *            Cost Detail
	 * @return New Cumulated Qty
	 */
	public BigDecimal getNewAccumulatedQuantity(MCostDetail cd) {
		return cd.getCumulatedQty().add(cd.getQty());
	}

	public void processCostDetail(MCostDetail costDetail) {
		if (!costDetail.isProcessed()) {
			MAcctSchema as = MAcctSchema.get(costDetail.getCtx(), costDetail.getC_AcctSchema_ID());
			MClient client = MClient.get(as.getCtx(), as.getAD_Client_ID());
			/**
			 * Libero to Libertya migration Always is costImmediate
			 */
			// if (MClient.isCostImmediate(client))
			costDetail.process();
		}
	}

	@Override
	protected List<CostComponent> getCalculatedCosts() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Update Cost Amt
	 */
	public void updateAmountCost() {

		if (movementQuantity.signum() > 0) {
			costDetail.setCostAmt(costDetail.getAmt().subtract(costDetail.getCostAdjustment()));
			costDetail.setCostAmtLL(costDetail.getAmtLL().subtract(costDetail.getCostAdjustmentLL()));
		} else if (movementQuantity.signum() < 0) {
			costDetail.setCostAmt(costDetail.getAmt().add(adjustCost));
			costDetail.setCostAmtLL(costDetail.getAmtLL().add(adjustCostLowerLevel));
		}

		costDetail.setCumulatedQty(getNewAccumulatedQuantity(lastCostDetail));
		costDetail.setCumulatedAmt(getNewAccumulatedAmount(lastCostDetail));

		// set the id for model
		final String idColumnName = CostEngine.getIDColumnName(model);
		costDetail.set_ValueOfColumn(idColumnName, CostEngine.getIDColumn(model));

		if (model instanceof LP_M_InOutLine) {
			LP_M_InOutLine ioLine = (LP_M_InOutLine) model;
			costDetail.setC_OrderLine_ID(ioLine.getC_OrderLine_ID());
			// IMPORTANT : reset possible provision purchase cost processed
			costDetail.setC_InvoiceLine_ID(0);
		}

		if (model instanceof LP_M_MatchInv && costDetail.getM_InOutLine_ID() == 0) {
			LP_M_MatchInv iMatch = (LP_M_MatchInv) model;
			costDetail.setM_InOutLine_ID(iMatch.getM_InOutLine_ID());
		}
		if (model instanceof LP_M_MatchPO && costDetail.getM_InOutLine_ID() == 0) {
			LP_M_MatchPO poMatch = (LP_M_MatchPO) model;
			costDetail.setM_InOutLine_ID(poMatch.getM_InOutLine_ID());
		}
		if (model instanceof LP_C_LandedCostAllocation) {
			LP_C_LandedCostAllocation allocation = (LP_C_LandedCostAllocation) model;
			costDetail.setM_InOutLine_ID(allocation.getM_InOutLine_ID());
			costDetail.setC_InvoiceLine_ID(allocation.getC_InvoiceLine_ID());
			costDetail.setProcessed(false);
		}
		costDetail.save();
	}

	/**
	 * Update the Inventory Value based in last transaction
	 */
	public void updateInventoryValue() {
		dimension.setCumulatedAmt(accumulatedAmount);
		dimension.setCumulatedAmtLL(accumulatedAmountLowerLevel);
		dimension.setCumulatedQty(accumulatedQuantity);
		dimension.setCurrentQty(accumulatedQuantity);
		dimension.save();
	}

	public BigDecimal getResourceStandardCostRate(MPPCostCollector cc, int S_Resource_ID, CostDimension d, String trxName) {
		final MProduct resourceProduct = MUMProduct.forS_Resource_ID(Env.getCtx(), S_Resource_ID, null);
		return getProductStandardCostPrice(cc, resourceProduct, MAcctSchema.get(Env.getCtx(), d.getC_AcctSchema_ID()),
				MUMCostElement.get(Env.getCtx(), d.getM_CostElement_ID()));
	}

	public BigDecimal getProductStandardCostPrice(MPPCostCollector cc, MProduct product, MAcctSchema as, MCostElement element) {
		CostDimension d = new CostDimension(product, as, as.getM_CostType_ID(), 0, // AD_Org_ID,
				0, 0, // M_ASI_ID,
				element.getM_CostElement_ID());
		MPPOrderCost oc = d.toQuery(MPPOrderCost.class, MPPOrderCost.COLUMNNAME_PP_Order_ID + "=?", new Object[] { cc.getPP_Order_ID() }, cc.get_TrxName())
				.firstOnly();
		if (oc == null) {
			return Env.ZERO;
		}
		BigDecimal costs = oc.getCurrentCostPrice().add(oc.getCurrentCostPriceLL());
		return CostEngine.roundCost(costs, as.getC_AcctSchema_ID());
	}

	public void createRateVariances(MPPCostCollector costCollector) {
		MProduct product = null;
		if (costCollector.isCostCollectorType(MPPCostCollector.COSTCOLLECTORTYPE_ActivityControl)) {
			final MWFNode node = new MWFNode(costCollector.getCtx(), costCollector.getPP_Order_Node().getAD_WF_Node_ID(), costCollector.get_TrxName());
			product = MUMProduct.forS_Resource_ID(costCollector.getCtx(), node.getS_Resource_ID(), null);
		} else if (costCollector.isCostCollectorType(MPPCostCollector.COSTCOLLECTORTYPE_ComponentIssue)) {
			final MPPOrderBOMLine bomLine = costCollector.getPP_Order_BOMLine();
			product = MProduct.get(costCollector.getCtx(), bomLine.getM_Product_ID());
		} else if (MPPCostCollector.COSTCOLLECTORTYPE_RateVariance.equals(costCollector.getCostCollectorType()))
			product = MProduct.get(costCollector.getCtx(), costCollector.getM_Product_ID());

		MPPCostCollector costCollectorRateVariance = null; // Cost Collector -
															// Rate Variance
		for (MAcctSchema accountSchema : CostEngine.getAcctSchema(costCollector)) {
			for (MCostElement costElement : MUMCostElement.getCostElement(costCollector.getCtx(), costCollector.get_TrxName())) {
				final MCostDetail costDetail = MCostDetail.getCostDetail(costCollector, costElement.getM_CostElement_ID());
				if (costDetail == null)
					continue;
				//
				final BigDecimal quantity = costDetail.getQty();
				final BigDecimal priceStandard = getProductStandardCostPrice(costCollector, product, accountSchema, costElement);
				final BigDecimal priceActual = getProductActualCostPrice(costCollector, product, accountSchema, costElement, costCollector.get_TrxName());
				final BigDecimal amountStandard = CostEngine.roundCost(priceStandard.multiply(quantity), accountSchema.getC_AcctSchema_ID());
				final BigDecimal amtActual = CostEngine.roundCost(priceActual.multiply(quantity), accountSchema.getC_AcctSchema_ID());
				if (amountStandard.compareTo(amtActual) == 0)
					continue;
				//
				if (costCollectorRateVariance == null) {
					costCollectorRateVariance = MPPCostCollector.createVarianceCostCollector(costCollector, MPPCostCollector.COSTCOLLECTORTYPE_RateVariance);
				}

				List<MCostType> costTypes = MCostType.get(accountSchema.getCtx(), accountSchema.get_TrxName());
				for (MCostType costType : costTypes) {
					createVarianceCostDetail(costCollectorRateVariance, amtActual.negate(), quantity.negate(), costDetail, null, accountSchema, costType,
							costElement);
					createVarianceCostDetail(costCollectorRateVariance, amountStandard, quantity, costDetail, null, accountSchema, costType, costElement);
				}
			}
		}
		//
		if (costCollectorRateVariance != null) {
			boolean ok = costCollectorRateVariance.processIt(MPPCostCollector.ACTION_Complete);
			costCollectorRateVariance.save();
			if (!ok)
				throw new RuntimeException(costCollectorRateVariance.getProcessMsg());
		}
	}

	public void createMethodVariances(MPPCostCollector cc) {
		if (!cc.isCostCollectorType(MPPCostCollector.COSTCOLLECTORTYPE_ActivityControl))
			return;
		//
		final int std_resource_id = new MWFNode(cc.getCtx(), cc.getPP_Order_Node().getAD_WF_Node_ID(), cc.get_TrxName()).getS_Resource_ID();
		final int actual_resource_id = cc.getS_Resource_ID();
		if (std_resource_id == actual_resource_id) {
			return;
		}
		//
		MPPCostCollector ccmv = null; // Cost Collector - Method Change Variance
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(cc.getAD_Client_ID());
		for (MAcctSchema as : CostEngine.getAcctSchema(cc)) {
			for (MCostElement element : MUMCostElement.getCostElement(cc.getCtx(), cc.get_TrxName())) {
				final MProduct resourcePStd = MUMProduct.forS_Resource_ID(cc.getCtx(), std_resource_id, null);
				final MProduct resourcePActual = MUMProduct.forS_Resource_ID(cc.getCtx(), actual_resource_id, null);
				final BigDecimal priceStd = getProductActualCostPrice(cc, resourcePStd, as, element, cc.get_TrxName());
				final BigDecimal priceActual = getProductActualCostPrice(cc, resourcePActual, as, element, cc.get_TrxName());
				if (priceStd.compareTo(priceActual) == 0) {
					continue;
				}
				//
				if (ccmv == null) {
					ccmv = MPPCostCollector.createVarianceCostCollector(cc, MPPCostCollector.COSTCOLLECTORTYPE_MethodChangeVariance);
				}
				//
				final BigDecimal qty = routingService.getResourceBaseValue(cc.getS_Resource_ID(), cc);
				final BigDecimal amtStd = priceStd.multiply(qty);
				final BigDecimal amtActual = priceActual.multiply(qty);
				//
				List<MCostType> costtypes = MCostType.get(as.getCtx(), as.get_TrxName());
				for (MCostType costType : costtypes) {
					// implementation only for standard cost
					if (!LP_M_CostType.COSTINGMETHOD_StandardCosting.equals(costType.getCostingMethod()))
						continue;
					createVarianceCostDetail(ccmv, amtActual, qty, null, resourcePActual, as, costType, element);
					createVarianceCostDetail(ccmv, amtStd.negate(), qty.negate(), null, resourcePStd, as, costType, element);
				}
			}
		}
		//
		if (ccmv != null) {
			boolean ok = ccmv.processIt(MPPCostCollector.ACTION_Complete);
			ccmv.save();
			if (!ok)
				throw new RuntimeException(ccmv.getProcessMsg());
		}
	}

	/**
	 * Create Cost Detail (Material Issue, Material Receipt)
	 * 
	 * @param model
	 * @param mtrx
	 *            Material Transaction
	 */
	/*
	 * public void createStandardCostDetail(IDocumentLine model, MTransaction
	 * mtrx) { final MPPCostCollector cc = (model instanceof MPPCostCollector ?
	 * (MPPCostCollector) model : null); for (MAcctSchema as :
	 * CostEngine.getAcctSchema(mtrx)) { // Cost Detail final MProduct product =
	 * MProduct.get(mtrx.getCtx(), mtrx.getM_Product_ID()); final String
	 * costingMethod = product.getCostingMethod(as, mtrx.getAD_Org_ID()); //
	 * Check costing method if (!getCostingMethod().equals(costingMethod)) {
	 * throw new AdempiereException("Costing method not supported - " +
	 * costingMethod); }
	 * 
	 * // for (MCostElement element : MCostElement.getCostElement(mtrx.getCtx(),
	 * mtrx.get_TrxName())) { // // Delete Unprocessed zero Differences
	 * CostEngine.deleteCostDetail(model, as, element.get_ID(),
	 * mtrx.getM_AttributeSetInstance_ID()); // // Get Costs final BigDecimal
	 * qty = mtrx.getMovementQty(); final BigDecimal price =
	 * getProductActualCostPrice(cc, product, as, element, mtrx.get_TrxName());
	 * final BigDecimal amt = CostEngine.roundCost(price.multiply(qty),
	 * as.getC_AcctSchema_ID()); // // Create / Update Cost Detail MCostDetail
	 * cd = MCostDetail.getCostDetail(model, mtrx, as, element.get_ID()); if (cd
	 * == null) // createNew { List<MCostType> costtypes =
	 * MCostType.get(as.getCtx(), as.get_TrxName()); for (MCostType mc :
	 * costtypes) { int M_CostType_ID = mc.get_ID(); cd = new MCostDetail(as,
	 * mtrx.getAD_Org_ID(), mtrx.getM_Product_ID(),
	 * mtrx.getM_AttributeSetInstance_ID(), element.get_ID(), amt, qty,
	 * model.getDescription(), mtrx.get_TrxName(), M_CostType_ID); //
	 * cd.setMovementDate(mtrx.getMovementDate()); // if (cost != null) // { //
	 * cd.setCurrentCostPrice(cost.getCurrentCostPrice()); //
	 * cd.setCurrentCostPriceLL(cost.getCurrentCostPriceLL()); // } // else // {
	 * // cd.setCurrentCostPrice(Env.ZERO); //
	 * cd.setCurrentCostPriceLL(Env.ZERO); // } //
	 * cd.setM_CostType_ID(as.getM_CostType_ID()); //
	 * //cd.setCostingMethod(element.getCostingMethod()); //
	 * cd.setM_Transaction_ID(mtrx.get_ID()); if (model instanceof
	 * MPPCostCollector) cd.setPP_Cost_Collector_ID(model.get_ID()); } } else {
	 * cd.setDeltaAmt(amt.subtract(cd.getAmt()));
	 * cd.setDeltaQty(mtrx.getMovementQty().subtract(cd.getQty())); if
	 * (cd.isDelta()) { cd.setProcessed(false); cd.setAmt(amt);
	 * cd.setQty(mtrx.getMovementQty()); } } cd.saveEx(); processCostDetail(cd);
	 * log.config("" + cd); } // for ELements } // Account Schema }
	 */

	/**
	 * Create Cost detail from cost collector
	 * 
	 * @param costCollector
	 * @param amount
	 * @param quantity
	 * @param costDetail
	 * @param product
	 * @param accountSchema
	 * @param costType
	 * @param costElement
	 * @return
	 */
	public MCostDetail createVarianceCostDetail(MPPCostCollector costCollector, BigDecimal amount, BigDecimal quantity, MCostDetail costDetail,
			MProduct product, MAcctSchema accountSchema, MCostType costType, MCostElement costElement) {
		final MCostDetail costDetailVariance = new MCostDetail(costCollector.getCtx(), 0, costCollector.get_TrxName());
		if (costDetail != null) {
			MCostDetail.copyValues(costDetail, costDetailVariance);
			costDetailVariance.setProcessed(false);
		}
		if (product != null) {
			costDetailVariance.setM_Product_ID(product.getM_Product_ID());
			costDetailVariance.setM_AttributeSetInstance_ID(costCollector.getM_AttributeSetInstance_ID());
		}
		if (accountSchema != null) {
			costDetailVariance.setC_AcctSchema_ID(accountSchema.getC_AcctSchema_ID());
		}
		if (costElement != null) {
			costDetailVariance.setM_CostElement_ID(costElement.getM_CostElement_ID());
		}
		//
		costDetailVariance.setPP_Cost_Collector_ID(costCollector.getPP_Cost_Collector_ID());
		costDetailVariance.setM_CostType_ID(costType.getM_CostType_ID());
		costDetailVariance.setM_CostElement_ID(costElement.getM_CostElement_ID());
		costDetailVariance.setAmt(amount);
		costDetailVariance.setAmtLL(BigDecimal.ZERO);
		costDetailVariance.setQty(quantity);
		costDetailVariance.setDateAcct(costCollector.getDateAcct());
		costDetailVariance.save();
		processCostDetail(costDetailVariance);
		return costDetailVariance;
	}

	public void createActivityControl(MPPCostCollector costCollector) {
		if (!costCollector.isCostCollectorType(MPPCostCollector.COSTCOLLECTORTYPE_ActivityControl))
			return;
		//
		final MProduct product = MUMProduct.forS_Resource_ID(costCollector.getCtx(), costCollector.getS_Resource_ID(), null);
		final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(costCollector.getAD_Client_ID());
		final BigDecimal quantity = routingService.getResourceBaseValue(costCollector.getS_Resource_ID(), costCollector);
		for (MAcctSchema accountSchema : CostEngine.getAcctSchema(costCollector)) {
			for (MCostElement costElement : MUMCostElement.getCostElement(costCollector.getCtx(), costCollector.get_TrxName())) {
				if (!CostEngine.isActivityControlElement(costElement)) {
					continue;
				}
				final CostDimension dimension = new CostDimension(product, accountSchema, accountSchema.getM_CostType_ID(), 0, // AD_Org_ID,
						0, 0, // M_ASI_ID
						costElement.getM_CostElement_ID());
				final BigDecimal price = getResourceActualCostRate(costCollector, costCollector.getS_Resource_ID(), dimension, costCollector.get_TrxName());
				BigDecimal costs = price.multiply(quantity);
				if (costs.scale() > accountSchema.getCostingPrecision())
					costs = costs.setScale(accountSchema.getCostingPrecision(), RoundingMode.HALF_UP);
				//
				List<MCostType> costTypes = MCostType.get(accountSchema.getCtx(), accountSchema.get_TrxName());
				for (MCostType costType : costTypes) {
					// implementation only for standard cost
					if (!LP_M_CostType.COSTINGMETHOD_StandardCosting.equals(costType.getCostingMethod()))
						continue;

					MCostDetail costDetail = new MCostDetail(accountSchema, costCollector.getAD_Org_ID(), // AD_Org_ID,
							dimension.getM_Product_ID(), 0, // M_AttributeSetInstance_ID,
							costElement.getM_CostElement_ID(), costs.negate(), quantity.negate(), costElement.getName(), // Description,
							costCollector.get_TrxName(), costType.getM_CostType_ID());
					costDetail.setPP_Cost_Collector_ID(costCollector.getPP_Cost_Collector_ID());
					costDetail.setDateAcct(costCollector.getDateAcct());
					costDetail.save();
					processCostDetail(costDetail);
				}
			}
		}
	}

	public void createUsageVariances(MPPCostCollector usageVariance) {
		// Apply only for material Usage Variance
		if (!usageVariance.isCostCollectorType(MPPCostCollector.COSTCOLLECTORTYPE_UsegeVariance)) {
			throw new IllegalArgumentException("Cost Collector is not Material Usage Variance");
		}
		//
		final MProduct product;
		final BigDecimal quantity;
		if (usageVariance.getPP_Order_Bomline_ID() > 0) {
			product = MProduct.get(usageVariance.getCtx(), usageVariance.getM_Product_ID());
			quantity = usageVariance.getMovementQty();
		} else {
			product = MUMProduct.forS_Resource_ID(usageVariance.getCtx(), usageVariance.getS_Resource_ID(), null);
			final RoutingService routingService = RoutingServiceFactory.get().getRoutingService(usageVariance.getAD_Client_ID());
			quantity = routingService.getResourceBaseValue(usageVariance.getS_Resource_ID(), usageVariance);
		}
		//
		for (MAcctSchema accountSchema : CostEngine.getAcctSchema(usageVariance)) {
			for (MCostElement element : MUMCostElement.getCostElement(usageVariance.getCtx(), usageVariance.get_TrxName())) {
				final BigDecimal price = getProductActualCostPrice(usageVariance, product, accountSchema, element, usageVariance.get_TrxName());
				final BigDecimal amt = CostEngine.roundCost(price.multiply(quantity), accountSchema.getC_AcctSchema_ID());
				//
				// Create / Update Cost Detail
				if (amt.compareTo(Env.ZERO) != 0) {
					List<MCostType> costTypes = MCostType.get(accountSchema.getCtx(), accountSchema.get_TrxName());
					for (MCostType costType : costTypes) {
						createVarianceCostDetail(usageVariance, amt, quantity, null, product, accountSchema, costType, element);
					}
				}
			} // for Elments
		} // Account Schema
	}

	public static BigDecimal getResourceActualCostRate(MPPCostCollector cc, int S_Resource_ID, CostDimension d, String trxName) {
		if (S_Resource_ID <= 0)
			return Env.ZERO;
		final MProduct resourceProduct = MUMProduct.forS_Resource_ID(Env.getCtx(), S_Resource_ID, null);
		return getProductActualCostPrice(cc, resourceProduct, MAcctSchema.get(Env.getCtx(), d.getC_AcctSchema_ID()),
				MUMCostElement.get(Env.getCtx(), d.getM_CostElement_ID()), trxName);
	}

	public static BigDecimal getProductActualCostPrice(MPPCostCollector costCollector, MProduct product, MAcctSchema as, MCostElement element, String trxName) {
		String CostingLevel = MUMProduct.getCostingLevel(product, as);
		// Org Element
		int AD_Org_ID = 0;
		int M_Warehouse_ID = 0;
		int M_ASI_ID = 0;
		if (costCollector != null) {
			AD_Org_ID = costCollector.getAD_Org_ID();
			M_Warehouse_ID = costCollector.getM_Warehouse_ID();
			M_ASI_ID = costCollector.getM_AttributeSetInstance_ID();
		}

		if (LP_C_AcctSchema.COSTINGLEVEL_Client.equals(CostingLevel)) {
			AD_Org_ID = 0;
			M_ASI_ID = 0;
			M_Warehouse_ID = 0;
		} else if (LP_C_AcctSchema.COSTINGLEVEL_Organization.equals(CostingLevel)) {
			M_ASI_ID = 0;
			M_Warehouse_ID = 0;
		} else if (LP_C_AcctSchema.COSTINGLEVEL_Warehouse.equals(CostingLevel)) {
			M_ASI_ID = 0;
		} else if (LP_C_AcctSchema.COSTINGLEVEL_BatchLot.equals(CostingLevel)) {
			AD_Org_ID = 0;
			M_Warehouse_ID = 0;
		}

		CostDimension d = new CostDimension(product, as, as.getM_CostType_ID(), AD_Org_ID, M_Warehouse_ID, M_ASI_ID, // M_ASI_ID,
				element.getM_CostElement_ID());
		MCost cost = d.toQuery(MCost.class, trxName).firstOnly();
		if (cost == null)
			return Env.ZERO;
		BigDecimal price = cost.getCurrentCostPrice().add(cost.getCurrentCostPriceLL());
		return CostEngine.roundCost(price, as.getC_AcctSchema_ID());
	}

}
