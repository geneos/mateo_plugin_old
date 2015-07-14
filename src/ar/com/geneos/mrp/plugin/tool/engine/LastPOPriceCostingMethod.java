/**
 * 
 */
package ar.com.geneos.mrp.plugin.tool.engine;

import java.math.BigDecimal;
import java.util.List;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MTransaction;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.model.IDocumentLine;
import ar.com.geneos.mrp.plugin.model.MCost;
import ar.com.geneos.mrp.plugin.model.MCostDetail;
import ar.com.geneos.mrp.plugin.util.MUMProduct;

/**
 * @author anca_bradau
 * 
 */
public class LastPOPriceCostingMethod extends AbstractCostingMethod implements ICostingMethod
{

    /**
     * Constructor for Cost Engine
     * @param accountSchema
     * @param transaction
     * @param model
     * @param dimension
     * @param costThisLevel
     * @param costLowLevel
     * @param isSalesTransaction
     */
    public void setCostingMethod(MAcctSchema accountSchema, MTransaction transaction, IDocumentLine model,
                                 MCost dimension, BigDecimal costThisLevel,
                                 BigDecimal costLowLevel, Boolean isSalesTransaction) {
        this.accountSchema = accountSchema;
        this.transaction = transaction;
        this.dimension = dimension;
        this.costThisLevel = (costThisLevel == null ? Env.ZERO : costThisLevel);
        this.costLowLevel = (costLowLevel == null ? Env.ZERO : costLowLevel);
        this.isSalesTransaction = isSalesTransaction;
        this.model = model;
        this.costingLevel = MUMProduct.getCostingLevel(MProduct.get(this.transaction.getCtx(), this.transaction.getM_Product_ID()),accountSchema, transaction.getAD_Org_ID());
        // find if this transaction exist into cost detail
        this.costDetail = MCostDetail.getByTransaction(this.model, this.transaction,
                this.accountSchema.getC_AcctSchema_ID(), this.dimension.getM_CostType_ID(),
                this.dimension.getM_CostElement_ID());
    }
	
	public MCostDetail process() {
		MCost cost = ((MCostDetail)  costDetail).getM_Cost();
		CLogger s_log = CLogger.getCLogger(LastPOPriceCostingMethod.class);

		boolean isReturnTrx =  costDetail.getQty().signum() < 0;
		MAcctSchema as = new MAcctSchema(model.getCtx(),  costDetail.getC_AcctSchema_ID(), model.get_TrxName());
		int precision = as.getCostingPrecision();
		BigDecimal price = costDetail.getAmt();

		if ( costDetail.getQty().signum() != 0)
			price =  costDetail.getAmt().divide(costDetail.getQty(), precision,
					BigDecimal.ROUND_HALF_UP);

		if ( costDetail.getC_OrderLine_ID() != 0) {
			if (!isReturnTrx) {
				if ( costDetail.getQty().signum() != 0)
					cost.setCurrentCostPrice(price);
				else {
					BigDecimal cCosts = cost.getCurrentCostPrice().add(
                            costDetail.getAmt());
					cost.setCurrentCostPrice(cCosts);
				}
			}
			cost.add( costDetail.getAmt(),  costDetail.getQty());
			s_log.finer("PO - LastPO - " + cost);
		} 
		else if ( costDetail.getM_InOutLine_ID() != 0 // AR Shipment Detail Record
				||  costDetail.getM_MovementLine_ID() != 0
				||  costDetail.getM_InventoryLine_ID() != 0
				||  costDetail.getM_ProductionLine_ID() != 0
				||  costDetail.getC_ProjectIssue_ID() != 0
				||  costDetail.getPP_Cost_Collector_ID() != 0)
		{
			cost.setCurrentQty(cost.getCurrentQty().add(costDetail.getQty()));
			s_log.finer("QtyAdjust - LastPO - " + cost);
			cost.save();
		}
		return costDetail;
	}

	@Override
	public BigDecimal getNewAccumulatedAmount(MCostDetail cd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getNewCurrentCostPriceLowerLevel(MCostDetail cd,
			int scale, int roundingMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getNewAccumulatedAmountLowerLevel(MCostDetail cd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getNewAccumulatedQuantity(MCostDetail cd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processCostDetail(MCostDetail costDetail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<CostComponent> getCalculatedCosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAmountCost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getNewCurrentCostPrice(MCostDetail cd, int scale, int roundingMode) {
		// TODO Auto-generated method stub
		return null;
	}
}
