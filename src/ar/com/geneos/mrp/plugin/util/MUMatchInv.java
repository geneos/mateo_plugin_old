package ar.com.geneos.mrp.plugin.util;

import java.util.List;

import org.openXpertya.model.MMatchInv;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.LP_M_InOutLine;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchInv;

public class MUMatchInv {

	/**
	 * get Match PO entity
	 * 
	 * @param ioLine
	 * @return
	 */
	
	//FIXME: Ver si se puede cambiar owner de la tabla para que funcione el Query
	public static List<LP_M_MatchInv> getInOutLine(LP_M_InOutLine ioLine) {
		return new Query(ioLine.getCtx(), MMatchInv.Table_Name, LP_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "=?", ioLine.get_TrxName()).setClient_ID()
				.setParameters(ioLine.getM_InOutLine_ID()).list();
	}

}
