package ar.com.geneos.mrp.plugin.util;

import org.openXpertya.model.MResource;
import org.openXpertya.model.X_M_Forecast;

import ar.com.geneos.mrp.plugin.model.LP_M_Forecast;
import ar.com.geneos.mrp.plugin.model.LP_M_Resource;

public class MUMResource {


	public static int getPlanningHorizon(MResource plant) {
		LP_M_Resource aux = new LP_M_Resource(plant.getCtx(),plant.getID(),plant.get_TrxName());
		return aux.getPlanningHorizon();
	}

}
