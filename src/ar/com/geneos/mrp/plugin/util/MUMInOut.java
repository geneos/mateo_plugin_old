package ar.com.geneos.mrp.plugin.util;

import java.util.List;

import org.openXpertya.model.MInOut;
import org.openXpertya.model.MInOutLine;
import org.openXpertya.model.MOrder;
import org.openXpertya.model.MOrderLine;
import org.openXpertya.model.MOrg;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.LP_C_OrderLine;

public class MUMInOut {

	public static MInOut getParent(MInOutLine ol) {
		MInOut aux = new MInOut(ol.getCtx(), ol.getM_InOut_ID(), ol.get_TrxName());
		return aux;
	}


}
