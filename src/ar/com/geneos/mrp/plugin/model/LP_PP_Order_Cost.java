package ar.com.geneos.mrp.plugin.model;

/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import org.openXpertya.model.*;
import java.util.logging.Level;
import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;

/**
 * Modelo Generado por PP_Order_Cost
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-04-29 14:39:08.546
 */
public class LP_PP_Order_Cost extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_PP_Order_Cost(Properties ctx, int PP_Order_Cost_ID, String trxName) {
		super(ctx, PP_Order_Cost_ID, trxName);
		/**
		 * if (PP_Order_Cost_ID == 0) { setC_AcctSchema_ID (0); setM_CostType_ID
		 * (0); setM_Product_ID (0); setPP_Order_Cost_ID (0); setPP_Order_ID
		 * (0); }
		 */
	}

	/** Load Constructor */
	public LP_PP_Order_Cost(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("PP_Order_Cost");

	/** TableName=PP_Order_Cost */
	public static final String Table_Name = "PP_Order_Cost";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "PP_Order_Cost");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_PP_Order_Cost[").append(getID()).append("]");
		return sb.toString();
	}

	/**
	 * Set Workflow. Workflow or combination of tasks
	 */
	public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	public void setAD_Workflow_ID(int AD_Workflow_ID) {
		if (AD_Workflow_ID <= 0)
			set_Value("AD_Workflow_ID", null);
		else
			set_Value("AD_Workflow_ID", new Integer(AD_Workflow_ID));
	}

	/**
	 * Get Workflow. Workflow or combination of tasks
	 */
	public int getAD_Workflow_ID() {
		Integer ii = (Integer) get_Value("AD_Workflow_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Accounting Schema. Rules for accounting
	 */
	public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
		set_Value("C_AcctSchema_ID", new Integer(C_AcctSchema_ID));
	}

	/**
	 * Get Accounting Schema. Rules for accounting
	 */
	public int getC_AcctSchema_ID() {
		Integer ii = (Integer) get_Value("C_AcctSchema_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Costing Method. Indicates how Costs will be calculated
	 */
	public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	public void setCostingMethod(boolean CostingMethod) {
		set_Value("CostingMethod", new Boolean(CostingMethod));
	}

	/**
	 * Get Costing Method. Indicates how Costs will be calculated
	 */
	public boolean isCostingMethod() {
		Object oo = get_Value("CostingMethod");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set cumulatedamt */
	public static final String COLUMNNAME_cumulatedamt = "cumulatedamt";

	public void setCumulatedAmt(BigDecimal cumulatedamt) {
		set_Value("cumulatedamt", cumulatedamt);
	}

	/** Get cumulatedamt */
	public BigDecimal getCumulatedAmt() {
		BigDecimal bd = (BigDecimal) get_Value("cumulatedamt");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cumulatedamtpost */
	public static final String COLUMNNAME_cumulatedamtpost = "cumulatedamtpost";

	public void setCumulatedQmtPost(BigDecimal cumulatedamtpost) {
		set_Value("cumulatedamtpost", cumulatedamtpost);
	}

	/** Get cumulatedamtpost */
	public BigDecimal getCumulatedAmtPost() {
		BigDecimal bd = (BigDecimal) get_Value("cumulatedamtpost");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cumulatedqty */
	public static final String COLUMNNAME_cumulatedqty = "cumulatedqty";

	public void setCumulatedQty(BigDecimal cumulatedqty) {
		set_Value("cumulatedqty", cumulatedqty);
	}

	/** Get cumulatedqty */
	public BigDecimal getCumulatedQty() {
		BigDecimal bd = (BigDecimal) get_Value("cumulatedqty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cumulatedqtypost */
	public static final String COLUMNNAME_cumulatedqtypost = "cumulatedqtypost";

	public void setCumulatedQtyPost(BigDecimal cumulatedqtypost) {
		set_Value("cumulatedqtypost", cumulatedqtypost);
	}

	/** Get cumulatedqtypost */
	public BigDecimal getCumulatedQtyPost() {
		BigDecimal bd = (BigDecimal) get_Value("cumulatedqtypost");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Current Cost Price. The currently used cost price
	 */
	public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	public void setCurrentCostPrice(BigDecimal CurrentCostPrice) {
		set_Value("CurrentCostPrice", CurrentCostPrice);
	}

	/**
	 * Get Current Cost Price. The currently used cost price
	 */
	public BigDecimal getCurrentCostPrice() {
		BigDecimal bd = (BigDecimal) get_Value("CurrentCostPrice");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set currentcostpricell */
	public static final String COLUMNNAME_currentcostpricell = "currentcostpricell";

	public void setCurrentCostPriceLL(BigDecimal currentcostpricell) {
		set_Value("currentcostpricell", currentcostpricell);
	}

	/** Get currentcostpricell */
	public BigDecimal getCurrentCostPriceLL() {
		BigDecimal bd = (BigDecimal) get_Value("currentcostpricell");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set currentqty */
	public static final String COLUMNNAME_currentqty = "currentqty";

	public void setCurrentQty(BigDecimal currentqty) {
		set_Value("currentqty", currentqty);
	}

	/** Get currentqty */
	public BigDecimal getCurrentQty() {
		BigDecimal bd = (BigDecimal) get_Value("currentqty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Attribute Set Instance. Product Attribute Set Instance
	 */
	public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
		if (M_AttributeSetInstance_ID <= 0)
			set_Value("M_AttributeSetInstance_ID", null);
		else
			set_Value("M_AttributeSetInstance_ID", new Integer(M_AttributeSetInstance_ID));
	}

	/**
	 * Get Attribute Set Instance. Product Attribute Set Instance
	 */
	public int getM_AttributeSetInstance_ID() {
		Integer ii = (Integer) get_Value("M_AttributeSetInstance_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Cost Element. Product Cost Element
	 */
	public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	public void setM_CostElement_ID(int M_CostElement_ID) {
		if (M_CostElement_ID <= 0)
			set_Value("M_CostElement_ID", null);
		else
			set_Value("M_CostElement_ID", new Integer(M_CostElement_ID));
	}

	/**
	 * Get Cost Element. Product Cost Element
	 */
	public int getM_CostElement_ID() {
		Integer ii = (Integer) get_Value("M_CostElement_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Cost Type. Type of Cost (e.g. Current, Plan, Future)
	 */
	public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	public void setM_CostType_ID(int M_CostType_ID) {
		set_Value("M_CostType_ID", new Integer(M_CostType_ID));
	}

	/**
	 * Get Cost Type. Type of Cost (e.g. Current, Plan, Future)
	 */
	public int getM_CostType_ID() {
		Integer ii = (Integer) get_Value("M_CostType_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Product. Product, Service, Item
	 */
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	public void setM_Product_ID(int M_Product_ID) {
		set_Value("M_Product_ID", new Integer(M_Product_ID));
	}

	/**
	 * Get Product. Product, Service, Item
	 */
	public int getM_Product_ID() {
		Integer ii = (Integer) get_Value("M_Product_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_Cost_ID */
	public static final String COLUMNNAME_PP_Order_Cost_ID = "PP_Order_Cost_ID";

	public void setPP_Order_Cost_ID(int PP_Order_Cost_ID) {
		set_ValueNoCheck("PP_Order_Cost_ID", new Integer(PP_Order_Cost_ID));
	}

	/** Get PP_Order_Cost_ID */
	public int getPP_Order_Cost_ID() {
		Integer ii = (Integer) get_Value("PP_Order_Cost_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_ID */
	public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	public void setPP_Order_ID(int PP_Order_ID) {
		set_Value("PP_Order_ID", new Integer(PP_Order_ID));
	}

	/** Get PP_Order_ID */
	public int getPP_Order_ID() {
		Integer ii = (Integer) get_Value("PP_Order_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}
}
