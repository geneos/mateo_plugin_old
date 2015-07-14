package ar.com.geneos.mrp.plugin.util;

import java.util.List;
import java.util.Properties;

import org.openXpertya.model.MCostType;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.LP_M_CostElement;
import ar.com.geneos.mrp.plugin.model.LP_M_CostType;
import ar.com.geneos.mrp.plugin.model.LP_M_Product_Category_Acct;

public class MUMCostType {

	private static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	public static List<MCostType> get(Properties ctx, String trxName) {
		return new Query(ctx, MCostType.Table_Name, null, trxName).setOnlyActiveRecords(true).setClient_ID().setOrderBy(COLUMNNAME_M_CostType_ID).list();
	}

	public static String getCostingMethod(MCostType ct) {
		LP_M_CostType aux = new LP_M_CostType(ct.getCtx(), ct.getID(), ct.get_TrxName());
		return aux.getCostingMethod();
	}

	/**
	 * Is FiFo Costing Method
	 *
	 * @return true if Fifo
	 */
	public static boolean isFifo(MCostType ct) {
		String cm = getCostingMethod(ct);
		return cm != null && cm.equals(LP_M_CostElement.COSTINGMETHOD_Fifo);
	} // isFifo

	/**
	 * Is LiFo Costing Method
	 *
	 * @return true if Lifo
	 */
	public static boolean isLifo(MCostType ct) {
		String cm = getCostingMethod(ct);
		return cm != null && cm.equals(LP_M_CostElement.COSTINGMETHOD_Lifo);
	} // isLiFo

}
