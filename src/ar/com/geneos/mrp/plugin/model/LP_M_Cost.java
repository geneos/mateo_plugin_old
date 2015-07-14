/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.util.Env;

/**
 * Modelo Generado por M_Cost
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:14.824
 */
public class LP_M_Cost extends org.openXpertya.model.X_M_Cost {

	/** Constructor estándar */
	public LP_M_Cost(Properties ctx, int M_Cost_ID, String trxName) {
		super(ctx, M_Cost_ID, trxName);
		/**
		 * if (M_Cost_ID == 0) { setcurrentqty (Env.ZERO);
		 * setM_AttributeSetInstance_ID (0); setM_Warehouse_ID (0); }
		 */
	}

	/** Load Constructor */
	public LP_M_Cost(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_Cost[").append(getID()).append("]");
		return sb.toString();
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

	/** Set cumulatedamtll */
	public static final String COLUMNNAME_cumulatedamtll = "cumulatedamtll";

	public void setCumulatedAmtLL(BigDecimal cumulatedamtll) {
		set_Value("cumulatedamtll", cumulatedamtll);
	}

	/** Get cumulatedamtll */
	public BigDecimal getCumulatedAmtLL() {
		BigDecimal bd = (BigDecimal) get_Value("cumulatedamtll");
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
		if (currentqty == null)
			throw new IllegalArgumentException("currentqty is mandatory");
		set_Value("currentqty", currentqty);
	}

	/** Get currentqty */
	public BigDecimal getCurrentQty() {
		BigDecimal bd = (BigDecimal) get_Value("currentqty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set futurecostpricell */
	public static final String COLUMNNAME_futurecostpricell = "futurecostpricell";

	public void setFutureCostPriceLL(BigDecimal futurecostpricell) {
		set_Value("futurecostpricell", futurecostpricell);
	}

	/** Get futurecostpricell */
	public BigDecimal getFutureCostPriceLL() {
		BigDecimal bd = (BigDecimal) get_Value("futurecostpricell");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set iscostfrozen */
	public static final String COLUMNNAME_iscostfrozen = "iscostfrozen";

	public void setIsCostFrozen(boolean iscostfrozen) {
		set_Value("iscostfrozen", new Boolean(iscostfrozen));
	}

	/** Get iscostfrozen */
	public boolean isCostFrozen() {
		Object oo = get_Value("iscostfrozen");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Attribute Set Instance. Product Attribute Set Instance
	 */
	public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
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
	 * Set Warehouse. Storage Warehouse and Service Point
	 */
	public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	public void setM_Warehouse_ID(int M_Warehouse_ID) {
		set_Value("M_Warehouse_ID", new Integer(M_Warehouse_ID));
	}

	/**
	 * Get Warehouse. Storage Warehouse and Service Point
	 */
	public int getM_Warehouse_ID() {
		Integer ii = (Integer) get_Value("M_Warehouse_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Percent. Percentage
	 */
	public static final String COLUMNNAME_Percent = "Percent";

	public void setPercent(int Percent) {
		set_Value("Percent", new Integer(Percent));
	}

	/**
	 * Get Percent. Percentage
	 */
	public int getPercent() {
		Integer ii = (Integer) get_Value("Percent");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Costing Method.
	 * 
	 * @param CostingMethod
	 *            Indicates how Costs will be calculated
	 */
	public void setCostingMethod(String CostingMethod) {

		throw new IllegalArgumentException("CostingMethod is virtual column");
	}

	/**
	 * Get Costing Method.
	 * 
	 * @return Indicates how Costs will be calculated
	 */

	public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	public String getCostingMethod() {
		return (String) get_Value(COLUMNNAME_CostingMethod);
	}
}
