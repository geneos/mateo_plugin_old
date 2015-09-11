package org.openXpertya.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.openXpertya.util.CLogger;

import ar.com.geneos.mrp.plugin.model.LP_M_ForecastLine;
import ar.com.geneos.mrp.plugin.model.MRPValidator;
import ar.com.geneos.mrp.plugin.model.ModelValidator;

public class MForecastLine extends LP_M_ForecastLine {

	/** Logger */
	private static CLogger log = CLogger.getCLogger(MForecastLine.class);

	/**
	 * Constructor de la clase ...
	 *
	 *
	 * @param ctx
	 * @param M_ForecastLine_ID
	 * @param trxName
	 */

	public MForecastLine(Properties ctx, int M_ForecastLine_ID, String trxName) {
		super(ctx, M_ForecastLine_ID, trxName);

		if (M_ForecastLine_ID == 0) {
		}
	} // MForcastLine

	/**
	 * Constructor de la clase ...
	 *
	 *
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */

	public MForecastLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	} // MRequisitionLine

	/**
	 * Descripción de Método
	 *
	 *
	 * @param newRecord
	 *
	 * @return
	 */

	protected boolean beforeSave(boolean newRecord) {
		return true;
	} // beforeSave

	/**
	 * Descripción de Método
	 *
	 *
	 * @param newRecord
	 * @param success
	 *
	 * @return
	 */

	protected boolean afterSave(boolean newRecord, boolean success) {
		if (!success) {
			return success;
		}

		if (newRecord)
			MRPValidator.modelChange(this, ModelValidator.TYPE_AFTER_NEW, log);
		else
			MRPValidator.modelChange(this, ModelValidator.TYPE_AFTER_CHANGE, log);

		return true;
	} // afterSave

	/**
	 * Descripción de Método
	 *
	 *
	 * @param success
	 *
	 * @return
	 */

	protected boolean afterDelete(boolean success) {
		if (!success) {
			return success;
		}

		MRPValidator.modelChange(this, ModelValidator.TYPE_BEFORE_DELETE, log);

		return true;
	} // afterDelete

}