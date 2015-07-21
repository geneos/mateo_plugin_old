package ar.com.geneos.mrp.plugin.util;

import java.util.List;
import java.util.Properties;

import org.openXpertya.util.DB;
import org.openXpertya.model.MMatchInv;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.LP_M_InOutLine;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchInv;

public class MUMPrintFormat {

	/**
	 * get Match PO entity
	 * 
	 * @param ioLine
	 * @return
	 */
	
	//FIXME: Ver si se puede cambiar owner de la tabla para que funcione el Query
	
	public static int getPrintFormatID(String formatName, int AD_Table_ID, int AD_Client_ID) {
		final String sql = "SELECT AD_PrintFormat_ID FROM AD_PrintFormat"
				+ " WHERE Name = ? AND AD_Table_ID = ? AND AD_Client_ID IN (0, ?)"
				+ " ORDER BY AD_Client_ID DESC";
		return MUDB.getSQLValue(null, sql, formatName, AD_Table_ID, AD_Client_ID);
	}

}
