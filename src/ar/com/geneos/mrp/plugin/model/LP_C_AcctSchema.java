/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.MReference;

/**
 * Modelo Generado por C_AcctSchema
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:36:59.8
 */
public class LP_C_AcctSchema extends org.openXpertya.model.MAcctSchema {
	/** Constructor estándar */
	public LP_C_AcctSchema(Properties ctx, int C_AcctSchema_ID, String trxName) {
		super(ctx, C_AcctSchema_ID, trxName);
		/**
		 * if (C_AcctSchema_ID == 0) { setcostinglevel (null);
		 * setisexplicitcostadjustment (false); }
		 */
	}

	/** Load Constructor */
	public LP_C_AcctSchema(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_C_AcctSchema[").append(getID()).append("]");
		return sb.toString();
	}

	// FIXME: Poner AD_Reference_ID correspondiente cuando se cree en el plugin
	public static final int COSTINGLEVEL_AD_Reference_ID = MReference.getReferenceID("C_AcctSchema Costing Level");
	/** Batch/Lot = B */
	public static final String COSTINGLEVEL_BatchLot = "B";
	/** Client = C */
	public static final String COSTINGLEVEL_Client = "C";
	/** Organization = O */
	public static final String COSTINGLEVEL_Organization = "O";
	/** Warehouse = W */
	public static final String COSTINGLEVEL_Warehouse = "W";
	/** Set costinglevel */
	public static final String COLUMNNAME_CostingLevel = "CostingLevel";

	public void setCostingLevel(String CostingLevel) {
		if (CostingLevel.equals("B") || CostingLevel.equals("C") || CostingLevel.equals("O") || CostingLevel.equals("W"))
			;
		else
			throw new IllegalArgumentException("costinglevel Invalid value - Reference = COSTINGLEVEL_AD_Reference_ID - B - C - O - W");
		if (CostingLevel == null)
			throw new IllegalArgumentException("costinglevel is mandatory");
		if (CostingLevel.length() > 1) {
			log.warning("Length > 1 - truncated");
			CostingLevel = CostingLevel.substring(0, 1);
		}
		set_Value("CostingLevel", CostingLevel);
	}

	/** Get costinglevel */
	public String getCostingLevel() {
		return (String) get_Value("CostingLevel");
	}

	/** Set isexplicitcostadjustment */
	public static final String COLUMNNAME_IsExplicitCostAdjustment = "IsExplicitCostAdjustment";

	public void setIsExplicitCostAdjustment(boolean IsExplicitCostAdjustment) {
		set_Value("IsExplicitCostAdjustment", new Boolean(IsExplicitCostAdjustment));
	}

	/** Get isexplicitcostadjustment */
	public boolean isExplicitCostAdjustment() {
		Object oo = get_Value("IsExplicitCostAdjustment");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Column name IsAdjustCOGS */
	public static final String COLUMNNAME_IsAdjustCOGS = "IsAdjustCOGS";

	/**
	 * Set Adjust COGS.
	 * 
	 * @param IsAdjustCOGS
	 *            Adjust Cost of Good Sold
	 */
	public void setIsAdjustCOGS(boolean IsAdjustCOGS) {
		set_Value(COLUMNNAME_IsAdjustCOGS, Boolean.valueOf(IsAdjustCOGS));
	}

	/**
	 * Get Adjust COGS.
	 * 
	 * @return Adjust Cost of Good Sold
	 */
	public boolean isAdjustCOGS() {
		Object oo = get_Value(COLUMNNAME_IsAdjustCOGS);
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}
