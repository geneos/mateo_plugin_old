package ar.com.geneos.mrp.plugin.exception;

import java.sql.Timestamp;

import ar.com.geneos.mrp.plugin.model.LP_PP_Product_BOM;

public class BOMExpiredException extends MRPException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3084324343550833077L;

	public BOMExpiredException(LP_PP_Product_BOM bom, Timestamp date) {
		super(buildMessage(bom, date));
	}

	private static final String buildMessage(LP_PP_Product_BOM bom, Timestamp date) {
		return "@NotValid@ @PP_Product_BOM_ID@:" + bom.getValue() + " - @Date@:" + date;
	}
}
