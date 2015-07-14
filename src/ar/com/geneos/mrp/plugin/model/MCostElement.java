package ar.com.geneos.mrp.plugin.model;

import java.util.Properties;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MProductCategoryAcct;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;
import org.openXpertya.plugin.MPluginPO;
import org.openXpertya.plugin.MPluginStatusPO;
import org.openXpertya.util.Msg;

import ar.com.geneos.mrp.plugin.util.MUDB;

public class MCostElement extends MPluginPO {

	public MCostElement(PO po, Properties ctx, String trxName, String aPackage) {
		super(po, ctx, trxName, aPackage);
		// TODO Auto-generated constructor stub
	}

	public MPluginStatusPO postBeforeSave(PO po, boolean newRecord) {

		LP_M_CostElement ce = (LP_M_CostElement) po;

		// Check Unique Costing Method
		if ((LP_M_CostElement.COSTELEMENTTYPE_Material.equals(ce.getCostElementType())
		// || COSTELEMENTTYPE_Resource.equals(getCostElementType())
		// || COSTELEMENTTYPE_BurdenMOverhead.equals(getCostElementType())
		// || COSTELEMENTTYPE_Overhead.equals(getCostElementType())
				|| LP_M_CostElement.COSTELEMENTTYPE_OutsideProcessing.equals(ce.getCostElementType()))
				&& (newRecord || ce.is_ValueChanged(LP_M_CostElement.COLUMNNAME_CostingMethod))) {
			String sql = "SELECT  COALESCE(MAX(M_CostElement_ID),0) FROM M_CostElement " + "WHERE AD_Client_ID=? AND CostingMethod=? AND CostElementType=?";
			int id = MUDB.getSQLValue(ce.get_TrxName(), sql, ce.getAD_Client_ID(), ce.getCostingMethod(), ce.getCostElementType());
			if (id > 0 && id != ce.getID()) {
				status_po.setContinueStatus(0);
				status_po.setErrorMessage("AlreadyExists: " + Msg.getElement(ce.getCtx(), "CostingMethod"));
				return status_po;
			}
		}

		// Maintain Calculated
		/*
		 * if (COSTELEMENTTYPE_Material.equals(getCostElementType())) { String
		 * cm = getCostingMethod(); if (cm == null || cm.length() == 0 ||
		 * COSTINGMETHOD_StandardCosting.equals(cm)) setIsCalculated(false);
		 * else setIsCalculated(true); } else { if (isCalculated())
		 * setIsCalculated(false); if (getCostingMethod() != null)
		 * setCostingMethod(null); }
		 */

		if (ce.getAD_Org_ID() != 0)
			ce.setAD_Org_ID(0);

		return status_po;

	} // beforeSave

	/**
	 * Before Delete
	 *
	 * @return true if can be deleted
	 */
	public MPluginStatusPO preBeforeDelete(PO po) {
		LP_M_CostElement ce = (LP_M_CostElement) po;
		String cm = ce.getCostingMethod();
		if (cm == null || !LP_M_CostElement.COSTELEMENTTYPE_Material.equals(ce.getCostElementType()))
			return status_po;

		// Costing Methods on AS level
		MAcctSchema[] ass = MAcctSchema.getClientAcctSchema(ce.getCtx(), ce.getAD_Client_ID());
		for (int i = 0; i < ass.length; i++) {
			if (ass[i].getCostingMethod().equals(ce.getCostingMethod())) {
				status_po.setContinueStatus(0);
				status_po.setErrorMessage("CannotDeleteUsed: " + Msg.getElement(ce.getCtx(), "C_AcctSchema_ID") + " - " + ass[i].getName());
				return status_po;
			}
		}

		// Costing Methods on PC level
		int M_Product_Category_ID = 0;
		final String whereClause = "CostingMethod=?";
		MProductCategoryAcct retValue = new Query(ce.getCtx(), LP_M_Product_Category_Acct.Table_Name, whereClause, null).setParameters(ce.getCostingMethod())
				.setClient_ID().first();
		if (retValue != null)
			M_Product_Category_ID = retValue.getM_Product_Category_ID();
		if (M_Product_Category_ID != 0) {
			status_po.setContinueStatus(0);
			status_po.setErrorMessage("CannotDeleteUsed: " + Msg.getElement(ce.getCtx(), "M_Product_Category_ID") + " (ID=" + M_Product_Category_ID + ")");
			return status_po;
		}
		return status_po;
	} // beforeDelete

}
