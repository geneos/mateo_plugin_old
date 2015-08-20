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
	public static final String COLUMNNAME_CumulatedAmt = "CumulatedAmt";

	public void setCumulatedAmt(BigDecimal cumulatedamt) {
		set_Value("CumulatedAmt", cumulatedamt);
	}

	/** Get cumulatedamt */
	public BigDecimal getCumulatedAmt() {
		BigDecimal bd = (BigDecimal) get_Value("CumulatedAmt");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cumulatedamtll */
	public static final String COLUMNNAME_CumulatedAmtLL = "CumulatedAmtLL";

	public void setCumulatedAmtLL(BigDecimal CumulatedAmtLL) {
		set_Value("CumulatedAmtLL", CumulatedAmtLL);
	}

	/** Get cumulatedamtll */
	public BigDecimal getCumulatedAmtLL() {
		BigDecimal bd = (BigDecimal) get_Value("CumulatedAmtLL");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cumulatedqty */
	public static final String COLUMNNAME_CumulatedQty = "CumulatedQty";

	public void setCumulatedQty(BigDecimal cumulatedqty) {
		set_Value("CumulatedQty", cumulatedqty);
	}

	/** Get cumulatedqty */
	public BigDecimal getCumulatedQty() {
		BigDecimal bd = (BigDecimal) get_Value("CumulatedQty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set currentcostpricell */
	public static final String COLUMNNAME_CurrentCostPriceLL = "CurrentCostPriceLL";

	public void setCurrentCostPriceLL(BigDecimal CurrentCostPriceLL) {
		set_Value("CurrentCostPriceLL", CurrentCostPriceLL);
	}

	/** Get currentcostpricell */
	public BigDecimal getCurrentCostPriceLL() {
		BigDecimal bd = (BigDecimal) get_Value("CurrentCostPriceLL");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set currentqty */
	public static final String COLUMNNAME_CurrentQty = "CurrentQty";

	public void setCurrentQty(BigDecimal currentqty) {
		if (currentqty == null)
			throw new IllegalArgumentException("currentqty is mandatory");
		set_Value("CurrentQty", currentqty);
	}

	/** Get currentqty */
	public BigDecimal getCurrentQty() {
		BigDecimal bd = (BigDecimal) get_Value("CurrentQty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set futurecostpricell */
	public static final String COLUMNNAME_FutureCostPriceLL = "FutureCostPriceLL";

	public void setFutureCostPriceLL(BigDecimal FutureCostPriceLL) {
		set_Value("FutureCostPriceLL", FutureCostPriceLL);
	}

	/** Get futurecostpricell */
	public BigDecimal getFutureCostPriceLL() {
		BigDecimal bd = (BigDecimal) get_Value("FutureCostPriceLL");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set iscostfrozen */
	public static final String COLUMNNAME_IsCostFrozen = "IsCostFrozen";

	public void setIsCostFrozen(boolean IsCostFrozen) {
		set_Value("IsCostFrozen", new Boolean(IsCostFrozen));
	}

	/** Get iscostfrozen */
	public boolean isCostFrozen() {
		Object oo = get_Value("IsCostFrozen");
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
