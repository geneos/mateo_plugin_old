/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.MReference;

/**
 * Modelo Generado por M_CostType
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-16 12:16:19.313
 */
public class LP_M_CostType extends org.openXpertya.model.MCostType {
	/** Constructor estándar */
	public LP_M_CostType(Properties ctx, int M_CostType_ID, String trxName) {
		super(ctx, M_CostType_ID, trxName);
		/**
		 * if (M_CostType_ID == 0) { setCostingMethod (null); }
		 */
	}

	/** Load Constructor */
	public LP_M_CostType(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_CostType[").append(getID()).append("]");
		return sb.toString();
	}

	public static final int COSTINGMETHOD_AD_Reference_ID = MReference.getReferenceID("C_AcctSchema Costing Method");
	/** Last PO Price = P */
	public static final String COSTINGMETHOD_LastPOPrice = "P";
	/** Standard Costing = S */
	public static final String COSTINGMETHOD_StandardCosting = "S";
	/** Average = A */
	public static final String COSTINGMETHOD_Average = "A";
	/** Lifo = L */
	public static final String COSTINGMETHOD_Lifo = "L";
	/** Fifo = F */
	public static final String COSTINGMETHOD_Fifo = "F";

	public static final String COSTINGMETHOD_AverageInvoice = null;

	public static final String COSTINGMETHOD_AveragePO = null;

	public static final String COSTINGMETHOD_LastInvoice = null;

	public static final String COSTINGMETHOD_UserDefined = null;
	/**
	 * Set Costing Method. Indicates how Costs will be calculated
	 */
	public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	public void setCostingMethod(String CostingMethod) {
		if (CostingMethod.equals("P") || CostingMethod.equals("S") || CostingMethod.equals("A") || CostingMethod.equals("L") || CostingMethod.equals("F"))
			;
		else
			throw new IllegalArgumentException("CostingMethod Invalid value - Reference = COSTINGMETHOD_AD_Reference_ID - P - S - A - L - F");
		if (CostingMethod == null)
			throw new IllegalArgumentException("CostingMethod is mandatory");
		if (CostingMethod.length() > 1) {
			log.warning("Length > 1 - truncated");
			CostingMethod = CostingMethod.substring(0, 1);
		}
		set_Value("CostingMethod", CostingMethod);
	}

	/**
	 * Get Costing Method. Indicates how Costs will be calculated
	 */
	public String getCostingMethod() {
		return (String) get_Value("CostingMethod");
	}
}
