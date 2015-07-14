package ar.com.geneos.mrp.plugin.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.model.X_M_Forecast;

public class LP_M_Forecast extends X_M_Forecast {

	public LP_M_Forecast(Properties ctx, int M_Forecast_ID, String trxName) {
		super(ctx, M_Forecast_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public LP_M_Forecast(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/** Column name C_Project_ID */
	public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project.
	 * 
	 * @param C_Project_ID
	 *            Financial Project
	 */
	public void setC_Project_ID(int C_Project_ID) {
		if (C_Project_ID < 1)
			set_Value(COLUMNNAME_C_Project_ID, null);
		else
			set_Value(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/**
	 * Get Project.
	 * 
	 * @return Financial Project
	 */
	public int getC_Project_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

}
