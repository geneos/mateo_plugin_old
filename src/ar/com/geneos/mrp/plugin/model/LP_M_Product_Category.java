/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import org.openXpertya.model.*;
import java.util.logging.Level;
import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;

/**
 * Modelo Generado por M_Product_Category
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-08-25 08:18:02.383
 */
public class LP_M_Product_Category extends org.openXpertya.model.MProductCategory {
	/** Constructor estándar */
	public LP_M_Product_Category(Properties ctx, int M_Product_Category_ID, String trxName) {
		super(ctx, M_Product_Category_ID, trxName);
		/**
		 * if (M_Product_Category_ID == 0) { }
		 */
	}

	/** Load Constructor */
	public LP_M_Product_Category(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_Product_Category[").append(getID()).append("]");
		return sb.toString();
	}

	/** Set PP_Tolerance */
	public static final String COLUMNNAME_PP_Tolerance = "PP_Tolerance";

	public void setPP_Tolerance(BigDecimal PP_Tolerance) {
		set_Value("PP_Tolerance", PP_Tolerance);
	}

	/** Get PP_Tolerance */
	public BigDecimal getPP_Tolerance() {
		BigDecimal bd = (BigDecimal) get_Value("PP_Tolerance");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}
}
