package ar.com.geneos.mrp.plugin.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.X_M_ForecastLine;

public class LP_M_ForecastLine extends X_M_ForecastLine {

	public LP_M_ForecastLine(Properties ctx, int M_ForecastLine_ID, String trxName) {
		super(ctx, M_ForecastLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public LP_M_ForecastLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	/** Column name DatePromised */
	public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Date Promised.
	 * 
	 * @param DatePromised
	 *            Date Order was promised
	 */
	public void setDatePromised(Timestamp DatePromised) {
		set_Value(COLUMNNAME_DatePromised, DatePromised);
	}

	/**
	 * Get Date Promised.
	 * 
	 * @return Date Order was promised
	 */
	public Timestamp getDatePromised() {
		return (Timestamp) get_Value(COLUMNNAME_DatePromised);
	}

	/** Column name M_Warehouse_ID */
	public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Warehouse.
	 * 
	 * @param M_Warehouse_ID
	 *            Storage Warehouse and Service Point
	 */
	public void setM_Warehouse_ID(int M_Warehouse_ID) {
		if (M_Warehouse_ID < 1)
			set_Value(COLUMNNAME_M_Warehouse_ID, null);
		else
			set_Value(COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/**
	 * Get Warehouse.
	 * 
	 * @return Storage Warehouse and Service Point
	 */
	public int getM_Warehouse_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}
}
