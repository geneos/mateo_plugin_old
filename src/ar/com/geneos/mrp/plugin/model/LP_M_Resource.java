/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MInOut;
import org.openXpertya.model.MMovement;
import org.openXpertya.model.MResource;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.util.MUDB;

/**
 * Modelo Generado por C_OrderLine
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:13.306
 */
public class LP_M_Resource extends MResource {

	public LP_M_Resource(Properties ctx, int S_Resource_ID, String trxName) {
		super(ctx, S_Resource_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public LP_M_Resource(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

    /** Column name PlanningHorizon */
    public static final String COLUMNNAME_PlanningHorizon = "PlanningHorizon";
	
	/**
	 * Set Planning Horizon.
	 * 
	 * @param PlanningHorizon
	 *            The planning horizon is the amount of time (Days) an
	 *            organisation will look into the future when preparing a
	 *            strategic plan.
	 */
	public void setPlanningHorizon(int PlanningHorizon) {
		set_Value(COLUMNNAME_PlanningHorizon, Integer.valueOf(PlanningHorizon));
	}

	/**
	 * Get Planning Horizon.
	 * 
	 * @return The planning horizon is the amount of time (Days) an organisation
	 *         will look into the future when preparing a strategic plan.
	 */
	public int getPlanningHorizon() {
		Integer ii = (Integer) get_Value(COLUMNNAME_PlanningHorizon);
		if (ii == null)
			return 0;
		return ii.intValue();
	}
}
