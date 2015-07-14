package ar.com.geneos.mrp.plugin.util;

import org.openXpertya.model.X_M_Forecast;

import ar.com.geneos.mrp.plugin.model.LP_M_Forecast;

public class MUMForecast {

	public static int getC_Project_ID(X_M_Forecast f) {
		LP_M_Forecast aux = new LP_M_Forecast(f.getCtx(),f.getM_Forecast_ID(),f.get_TrxName());
		return aux.getC_Project_ID();
	}

}
