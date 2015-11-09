/**
 * 
 */
package ar.com.geneos.mrp.plugin.tool.engine;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MCost;
import org.openXpertya.model.MCostElement;
import org.openXpertya.model.MCostType;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MTransaction;
import org.openXpertya.model.Query;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.model.IDocumentLine;
import ar.com.geneos.mrp.plugin.model.LP_M_CostElement;
import ar.com.geneos.mrp.plugin.model.LP_M_Transaction;
import ar.com.geneos.mrp.plugin.model.MCostDetail;
import ar.com.geneos.mrp.plugin.model.MCostQueue;
import ar.com.geneos.mrp.plugin.util.MUMAcctSchema;
import ar.com.geneos.mrp.plugin.util.MUMProduct;
import ar.com.geneos.mrp.plugin.util.MUMTransaction;

/**
 * @author teo_sarca
 * @author anca_bradau
 */
public class FifoLifoCostingMethod extends AbstractCostingMethod {

	public void setCostingMethod(MAcctSchema accountSchema, MTransaction transaction, IDocumentLine model, MCost dimension, BigDecimal costThisLevel,
			BigDecimal costLowLevel, Boolean isSalesTransaction) {
		this.accountSchema = accountSchema;
		this.transaction = transaction;
		this.dimension = dimension;
		this.costThisLevel = (costThisLevel == null ? Env.ZERO : costThisLevel);
		this.costLowLevel = (costLowLevel == null ? Env.ZERO : costLowLevel);
		this.isSalesTransaction = isSalesTransaction;
		this.model = MUMTransaction.getDocumentLine(transaction);
		this.costingLevel = MUMProduct.getCostingLevel(MProduct.get(this.transaction.getCtx(), this.transaction.getM_Product_ID()), accountSchema,
				this.transaction.getAD_Org_ID());
		this.costDetail = MCostDetail.getByTransaction(this.model, this.transaction, this.accountSchema.getC_AcctSchema_ID(),
				this.dimension.getM_CostType_ID(), this.dimension.getM_CostElement_ID());
	}

	public void calculate() {

		ProductCost pc = new ProductCost(model.getCtx(), model.getM_Product_ID(), model.getM_AttributeSetInstance_ID(), model.get_TrxName());
		pc.setQty(transaction.getMovementQty());
		//

		List<CostComponent> ccs = pc.getProductCostsLayers(dimension, 0, false);
		if (ccs == null || ccs.size() == 0) {
			MProduct product = MProduct.get(Env.getCtx(), model.getM_Product_ID());
			throw new RuntimeException("No Costs for " + product.getName());
		}
		m_calculatedCosts = ccs;
	}

	protected List<CostComponent> getCalculatedCosts() {
		if (m_calculatedCosts == null)
			calculate();
		return m_calculatedCosts;
	}

	@Override
	protected void updateInventoryValue() {
		dimension.setCumulatedAmt(accumulatedAmount);
		dimension.setCumulatedAmtLL(accumulatedAmountLowerLevel);
		dimension.setCumulatedQty(accumulatedQuantity);
		dimension.setCurrentQty(accumulatedQuantity);
		dimension.save();
	}

	private List<CostComponent> m_calculatedCosts = null;

	public void updateCurrentCost(MCostDetail m_costdetail) {
		MCostQueue[] cQueue = MCostQueue.getQueue(dimension, m_costdetail.getDateAcct(), m_costdetail.get_TrxName());
		// TODO: need evaluate this!
		if (cQueue != null) {
			MCostType ct = new MCostType(dimension.getCtx(), dimension.getM_CostType_ID(), dimension.get_TrxName());
			if (cQueue.length > 0 && ct.isFifo())
				dimension.setCurrentCostPrice(cQueue[0].getCurrentCostPrice());
			else if (cQueue.length > 0
					&& LP_M_CostElement.COSTELEMENTTYPE_LandedCost.equals(new MCostElement(dimension.getCtx(), dimension.getM_CostElement_ID(), dimension
							.get_TrxName()).getCostElementType()))
				dimension.setCurrentCostPrice(cQueue[0].getCurrentCostPrice());
		}
		dimension.setCurrentQty(dimension.getCurrentQty().add(m_costdetail.getQty()));

		if (cQueue != null && cQueue.length > 0) {
			BigDecimal cAmt = cQueue[0].getCurrentCostPrice().multiply(m_costdetail.getQty());
			dimension.setCumulatedAmt(dimension.getCumulatedAmt().add(cAmt));
			dimension.setCumulatedQty(dimension.getCumulatedQty().add(m_costdetail.getQty()));
		}
		log.finer("QtyAdjust - FiFo/Lifo - " + dimension);
		dimension.save();
	}

	public MCostDetail process() {
		processCostDetail();
		return costDetail;
	}

	public void processCostDetail() {
		// check if document is entered with delay
		lastCostDetail = MCostDetail.getLastTransaction(transaction, accountSchema.getC_AcctSchema_ID(), dimension.getM_CostType_ID(),
				dimension.getM_CostElement_ID(), costingLevel);
		if (lastCostDetail == null) {
			lastCostDetail = new MCostDetail(transaction, accountSchema.getC_AcctSchema_ID(), dimension.getM_CostType_ID(), dimension.getM_CostElement_ID(),
					Env.ZERO, Env.ZERO, Env.ZERO, transaction.get_TrxName());
			lastCostDetail.setDateAcct(new Timestamp(System.currentTimeMillis()));
		}

		/**
		 * Libero to Libertya migration reversal cost not implemented
		 */
		/*
		 * if (model.getReversalLine_ID() > 0) { createReversalCostDetail();
		 * MCostQueue cq = MCostQueue.getQueueForAdjustment(costDetail,
		 * dimension, model.get_TrxName()); if
		 * (cq.getCurrentQty().compareTo(costDetail.getQty()) == 1 ||
		 * cq.getCurrentQty().compareTo(costDetail.getCurrentQty()) == 0) {
		 * cq.setCurrentQty(cq.getCurrentQty().add(costDetail.getQty()));
		 * dimension
		 * .setCurrentQty(dimension.getCurrentQty().add(costDetail.getQty()));
		 * cq.save(); dimension.save(); } else processCostDetail(costDetail);
		 * return; }
		 */
		if (costDetail == null) {
			for (MCostDetail cd : createCostDetails(dimension, transaction)) {
				if (CostDimension.isSameCostDimension(accountSchema, model)
						&& (transaction.getMovementType().equals("M+") || transaction.getMovementType().equals("M-"))) {
					costDetail = cd;
					continue;
				}
				processCostDetail(cd);

				if (costDetail.getDateAcct().compareTo(lastCostDetail.getDateAcct()) < 0) {
					adjustementQueue(cd);
				}
			}
		} else {
			amount = transaction.getMovementQty().multiply(costThisLevel.add(costLowLevel));
			amountLowerLevel = transaction.getMovementQty().multiply(costLowLevel);
			accumulatedQuantity = dimension.getCumulatedQty();
			adjustCost = amount.subtract(costDetail.getAmt());
			accumulatedAmount = costDetail.getCumulatedAmt().add(amount).add(adjustCost);
			accumulatedAmountLowerLevel = getNewAccumulatedAmountLowerLevel(lastCostDetail).add(amountLowerLevel);
			if (accumulatedAmount.signum() != 0)
				currentCostPrice = accumulatedAmount.divide(accumulatedQuantity.signum() != 0 ? accumulatedQuantity : BigDecimal.ONE,
						accountSchema.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
			else
				currentCostPrice = Env.ZERO;
			if (accumulatedAmountLowerLevel.signum() != 0)
				currentCostPriceLowerLevel = accumulatedAmountLowerLevel.divide(accumulatedQuantity.signum() != 0 ? accumulatedQuantity : BigDecimal.ONE,
						accountSchema.getCostingPrecision(), BigDecimal.ROUND_HALF_UP);
			else
				currentCostPriceLowerLevel = Env.ZERO;

			if (adjustCost.compareTo(Env.ZERO) != 0) {
				costDetail.setCostAdjustment(adjustCost);
				costDetail.setProcessed(false);
				costDetail.setDescription("Adjust Cost:" + adjustCost);
				costDetail.setCostAdjustmentDate(model.getDateAcct());
				costDetail.save();
			}
			costDetail.save();
			if (costDetail.getCostAdjustmentDate() != null) {
				if (MUMAcctSchema.isAdjustCOGS(accountSchema)) {
					adjustementQueue(costDetail);
				}
			}
		}
	}

	// need this for reversal documents
	public void processCostDetail(MCostDetail costDetail) {
		boolean addition = costDetail.getQty().signum() > 0;
		MAcctSchema as = MAcctSchema.get(costDetail.getCtx(), costDetail.getC_AcctSchema_ID());
		int precision = as.getCostingPrecision();
		MProduct product = MProduct.get(costDetail.getCtx(), costDetail.getM_Product_ID());
		BigDecimal price = costDetail.getAmt();

		if (costDetail.getQty().signum() != 0)
			price = costDetail.getAmt().divide(costDetail.getQty(), precision, BigDecimal.ROUND_HALF_UP);

		int AD_Org_ID = costDetail.getAD_Org_ID();
		int M_ASI_ID = costDetail.getM_AttributeSetInstance_ID();

		/**
		 * Libero to Libertya migration Reversal cost not implemented
		 */
		/*
		 * if (costDetail.getC_OrderLine_ID() != 0 &&
		 * !(model.getReversalLine_ID() > 0)) {
		 * log.finer("Inv - FiFo/LiFo - amt=" + costDetail.getAmt() + ", qty=" +
		 * costDetail.getQty() + " [NOTHING TO DO]"); }
		 * 
		 * else
		 */if (costDetail.getM_InOutLine_ID() != 0 // AR Shipment Detail
													// Record
				|| costDetail.getM_MovementLine_ID() != 0
				|| costDetail.getM_InventoryLine_ID() != 0
				|| costDetail.getM_ProductionLine_ID() != 0
				|| costDetail.getC_ProjectIssue_ID() != 0 || costDetail.getPP_Cost_Collector_ID() != 0 /*
																										 * ||
																										 * costDetail
																										 * .
																										 * getC_LandedCostAllocation_ID
																										 * (
																										 * )
																										 * !=
																										 * 0
																										 */) {
			if (addition) {
				MCostQueue.add(product, M_ASI_ID, as, AD_Org_ID, costDetail.getM_CostElement_ID(), costDetail.getAmt(), costDetail.getQty(), precision,
						(MCostDetail) costDetail, costDetail.get_TrxName());
			} else {
				BigDecimal amtQueue = MCostQueue.adjustQty(dimension, costDetail.getQty().negate(), costDetail.getDateAcct(), costDetail.get_TrxName());
				amtQueue = amtQueue.negate(); // outgoing amt should be negative
				if (costDetail.getAmt().compareTo(amtQueue) != 0) {
					BigDecimal priceQueue = Env.ZERO;
					if (costDetail.getQty().signum() != 0)
						priceQueue = amtQueue.divide(costDetail.getQty(), precision, BigDecimal.ROUND_HALF_UP);
					log.warning("Amt not match " + this + ": price=" + price + ", priceQueue=" + priceQueue + " [ADJUSTED]");
					// FIXME: teo_sarca: should not happen
					if ("Y".equals(Env.getContext(costDetail.getCtx(), "#M_CostDetail_CorrectAmt"))) {
						costDetail.setAmt(amtQueue);
						costDetail.setAmt(amtQueue);
						costDetail.setPrice(priceQueue);
					} else {
						throw new RuntimeException("Amt not match " + this + ": price=" + price + ", priceQueue=" + priceQueue);
					}
				}
			}
			costDetail.setCumulatedQty(dimension.getCumulatedQty());
			costDetail.setCumulatedAmt(dimension.getCumulatedQty());
			costDetail.setCurrentCostPrice(dimension.getCurrentCostPrice());
			updateCurrentCost(costDetail);
			costDetail.save();
			this.costDetail = costDetail;
		}
	}

	public void adjustementQueue(MCostDetail costDetail) {
		final List<MCostDetail> cds;
		if (costDetail.getCostAdjustmentDate() != null) {
			cds = MCostDetail.getAfterDate(costDetail, costingLevel);
		} else
			cds = MCostDetail.getAfterDate(costDetail, costingLevel);
		List<Object> list = new ArrayList<Object>();

		for (MCostDetail cd : cds) {
			if (cd == null)
				throw new RuntimeException("Error do not exist adjustment");
			MCostQueue cq = MCostQueue.getQueueForAdjustment(cd, dimension, model.get_TrxName());
			MTransaction trx = get(cd);
			// first condition - cost adjustement
			// second condition - delayed transaction
			if ((!(cq.getCurrentQty().compareTo(Env.ZERO) == 0)
					&& (trx.getMovementType().equals("C-") || trx.getMovementType().equals("I+") || trx.getMovementType().equals("I-")) && cd
					.getCostAdjustmentDate() != null)
					|| ((trx.getMovementType().equals("C-") || trx.getMovementType().equals("I+") || trx.getMovementType().equals("I-")) && transaction
							.getMovementType().endsWith("+"))) {
				cq.addCurrentQty(cd.getQty().negate());
				cq.save();
				cd.setProcessed(false);
				cd.setAmt(cd.getQty().multiply(costThisLevel.add(costLowLevel)));
				cd.save();
				list.add(cd);
			} else if (trx.getMovementType().equals("V+") && costDetail.getCostAdjustmentDate() != null) {
				cd.setProcessed(false);
				cd.setAmt(amount);
				cd.save();
				cq.setCurrentCostPrice(cd.getAmt().divide(cd.getQty()));
				cq.save();
				break;
			} else if (trx.getMovementType().equals("M+") || trx.getMovementType().equals("M-")) {
				MTransaction trxTo;
				if (trx.getMovementType().equals("M+"))
					trxTo = getPrevious(trx);
				else
					trxTo = getNext(trx);
				cd.setProcessed(false);
				if (CostDimension.isSameCostDimension(accountSchema, trx, trxTo)) {
					cd.setAmt(cd.getQty().multiply(costThisLevel.add(costLowLevel)));
					cd.save();
				} else {
					cq.addCurrentQty(cd.getQty().negate());
					cd.setAmt(cd.getQty().multiply(costThisLevel.add(costLowLevel)));
					cd.save();
					if (trx.getMovementType().equals("M+"))
						cq.setCurrentCostPrice(cd.getAmt().divide(cd.getQty()));
					if (cq.getCurrentCostPrice().compareTo(Env.ZERO) == 0)
						cq.setCurrentCostPrice(cd.getCurrentCostPrice());
					cq.save();
					list.add(cd);
				}
			} else
				continue;

		}
		for (MCostDetail cd : list.toArray(new MCostDetail[list.size()])) {
			processCostDetail(cd);
		}
	}

	static public MTransaction get(MCostDetail cd) {
		final String whereClause = LP_M_Transaction.COLUMNNAME_M_Product_ID + "=? AND " + LP_M_Transaction.COLUMNNAME_M_Transaction_ID + "=? AND "
				+ LP_M_Transaction.COLUMNNAME_MovementQty + "=?";
		MTransaction trx = new Query(cd.getCtx(), MTransaction.Table_Name, whereClause, cd.get_TrxName()).setClient_ID()
				.setParameters(cd.getM_Product_ID(), cd.getM_Transaction_ID(), cd.getQty()).firstOnly();
		return trx;

	}

	static public MTransaction getPrevious(MTransaction trx) {
		final String whereClause = LP_M_Transaction.COLUMNNAME_M_Transaction_ID + "<? AND " + LP_M_Transaction.COLUMNNAME_M_Product_ID + "=? AND "
				+ LP_M_Transaction.COLUMNNAME_MovementQty + "=?";
		return new Query(trx.getCtx(), MTransaction.Table_Name, whereClause, trx.get_TrxName()).setClient_ID()
				.setParameters(trx.getM_Transaction_ID(), trx.getM_Product_ID(), trx.getMovementQty().negate()).first();
	}

	static public MTransaction getNext(MTransaction trx) {
		final String whereClause = LP_M_Transaction.COLUMNNAME_M_Transaction_ID + ">? AND " + LP_M_Transaction.COLUMNNAME_M_Product_ID + "=? AND "
				+ LP_M_Transaction.COLUMNNAME_MovementQty + "=?";
		return new Query(trx.getCtx(), MTransaction.Table_Name, whereClause, trx.get_TrxName()).setClient_ID()
				.setParameters(trx.getM_Transaction_ID(), trx.getM_Product_ID(), trx.getMovementQty().negate()).first();
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
		if (getNewAccumulatedQuantity(cd).signum() != 0 && getNewAccumulatedAmount(cd).signum() != 0)
			return getNewAccumulatedAmount(cd).divide(getNewAccumulatedQuantity(cd), scale, roundingMode);
		else
			return BigDecimal.ZERO;
	}

	/**
	 * Get the New Cumulated Amt This Level
	 * 
	 * @param cd
	 *            Cost Detail
	 * @return New Cumulated Amt This Level
	 */
	public BigDecimal getNewAccumulatedAmount(MCostDetail cd) {

		BigDecimal cumulatedAmt = Env.ZERO;
		if (cd.getQty().signum() > 0)
			cumulatedAmt = cd.getCumulatedAmt().add(cd.getCostAmt()).add(cd.getCostAdjustment());
		else
			cumulatedAmt = cd.getCumulatedAmt().add(cd.getCostAmt().negate()).add(cd.getCostAdjustment().negate());

		return cumulatedAmt;
	}

	/**
	 * Get the New Current Cost Price low level
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
		if (getNewAccumulatedQuantity(cd).signum() != 0 && getNewAccumulatedAmountLowerLevel(cd).signum() != 0)
			return getNewAccumulatedAmountLowerLevel(cd).divide(getNewAccumulatedQuantity(cd), scale, roundingMode);
		else
			return BigDecimal.ZERO;
	}

	/**
	 * Get the new Cumulated Amt Low Level
	 * 
	 * @param cd
	 *            MCostDetail
	 * @return New Cumulated Am Low Level
	 */
	public BigDecimal getNewAccumulatedAmountLowerLevel(MCostDetail cd) {
		BigDecimal cumulatedAmtLL = Env.ZERO;
		if (cd.getQty().signum() > 0)
			cumulatedAmtLL = cd.getCumulatedAmtLL().add(cd.getCostAmtLL()).add(cd.getCostAdjustmentLL());
		else
			cumulatedAmtLL = cd.getCumulatedAmtLL().add(cd.getCostAmtLL().negate()).add(cd.getCostAdjustmentLL().negate());
		return cumulatedAmtLL;
	}

	/**
	 * Get the new Cumulated Qty
	 * 
	 * @param cd
	 *            Cost Detail
	 * @return New Cumulated Qty
	 */
	public BigDecimal getNewAccumulatedQuantity(MCostDetail cd) {
		return cd.getCumulatedQty().add(cd.getQty());
	}

	/**
	 * Update Cost Amt
	 */
	public void updateAmountCost() {
		if (transaction.getMovementType().contains("+")) {
			costDetail.setCostAmt(costThisLevel.multiply(transaction.getMovementQty()).abs());
			costDetail.setCostAmtLL(costLowLevel.multiply(transaction.getMovementQty()).abs());
		}
		if (transaction.getMovementType().contains("-")) {
			costDetail.setCostAmt(costDetail.getAmt());
			costDetail.setCostAmtLL(costDetail.getAmtLL());
		}
	}

}