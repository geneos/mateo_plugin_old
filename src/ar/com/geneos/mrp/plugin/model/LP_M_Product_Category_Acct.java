/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.MReference;

/**
 * Modelo Generado por M_Product_Category_Acct
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:37:03.339
 */
public class LP_M_Product_Category_Acct extends org.openXpertya.model.MProductCategoryAcct {
	/** Constructor estándar */
	public LP_M_Product_Category_Acct(Properties ctx, int M_Product_Category_Acct_ID, String trxName) {
		super(ctx, M_Product_Category_Acct_ID, trxName);
		/**
		 * if (M_Product_Category_Acct_ID == 0) { }
		 */
	}

	/** Load Constructor */
	public LP_M_Product_Category_Acct(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_Product_Category_Acct[").append(getID()).append("]");
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
	public static final String COLUMNNAME_costinglevel = "costinglevel";

	public void setCostingLevel(String costinglevel) {
		if (costinglevel == null || costinglevel.equals("B") || costinglevel.equals("C") || costinglevel.equals("O") || costinglevel.equals("W"))
			;
		else
			throw new IllegalArgumentException("costinglevel Invalid value - Reference = COSTINGLEVEL_AD_Reference_ID - B - C - O - W");
		if (costinglevel != null && costinglevel.length() > 1) {
			log.warning("Length > 1 - truncated");
			costinglevel = costinglevel.substring(0, 1);
		}
		set_Value("costinglevel", costinglevel);
	}

	/** Get costinglevel */
	public String getCostingLevel() {
		return (String) get_Value("costinglevel");
	}
}
