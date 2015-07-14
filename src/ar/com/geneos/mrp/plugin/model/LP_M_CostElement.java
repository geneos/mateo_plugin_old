/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo Generado por M_CostElement
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:14.849
 */
public class LP_M_CostElement extends org.openXpertya.model.MCostElement {
	/** Constructor estándar */
	public LP_M_CostElement(Properties ctx, int M_CostElement_ID, String trxName) {
		super(ctx, M_CostElement_ID, trxName);
		/**
		 * if (M_CostElement_ID == 0) { }
		 */
	}

	/** Load Constructor */
	public LP_M_CostElement(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_CostElement[").append(getID()).append("]");
		return sb.toString();
	}

	/**
	 * Set Default. Default value
	 */
	public static final String COLUMNNAME_IsDefault = "IsDefault";
	public static final String COLUMNNAME_CostingMethod = "CostingMethod";
	public static final String COSTELEMENTTYPE_LandedCost = null;


	public void setIsDefault(boolean IsDefault) {
		set_Value("IsDefault", new Boolean(IsDefault));
	}

	/**
	 * Get Default. Default value
	 */
	public boolean isDefault() {
		Object oo = get_Value("IsDefault");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}
