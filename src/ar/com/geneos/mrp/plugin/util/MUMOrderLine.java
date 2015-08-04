package ar.com.geneos.mrp.plugin.util;

import java.util.List;

import org.openXpertya.model.MOrder;
import org.openXpertya.model.MOrderLine;
import org.openXpertya.model.MOrg;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.LP_C_OrderLine;

public class MUMOrderLine {

	public static boolean isConsumesForecast(MOrderLine ol) {
		LP_C_OrderLine aux = new LP_C_OrderLine(ol.getCtx(), ol.getC_OrderLine_ID(), ol.get_TrxName());
		return aux.isConsumesForecast();
	}

	public static MOrder getParent(MOrderLine ol) {
		LP_C_OrderLine aux = new LP_C_OrderLine(ol.getCtx(), ol.getC_OrderLine_ID(), ol.get_TrxName());
		return aux.getParent();
	}
	
	public static int getPP_Cost_Collector_ID(MOrderLine ol) {
		LP_C_OrderLine aux = new LP_C_OrderLine(ol.getCtx(), ol.getC_OrderLine_ID(), ol.get_TrxName());
		return aux.getM_AttributeSetInstance_ID();
	}


}
