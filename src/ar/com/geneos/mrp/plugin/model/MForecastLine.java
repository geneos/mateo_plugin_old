package ar.com.geneos.mrp.plugin.model;
 
import java.util.Properties;

import org.openXpertya.model.PO;
import org.openXpertya.plugin.MPluginPO;
import org.openXpertya.plugin.MPluginStatusPO;
import org.openXpertya.util.CLogger;

 
public class MForecastLine extends MPluginPO {
 

	/** Logger */
	private static CLogger log = CLogger.getCLogger(MForecastLine.class);
 
	public MForecastLine(PO po, Properties ctx, String trxName, String aPackage) {
		super(po, ctx, trxName, aPackage);
		// TODO Auto-generated constructor stub
	}
 
	/**
	 * Ejecución posterior al beforeSave
	 * @return estado del procesamiento
	 */
	public MPluginStatusPO postAfterSave(PO po, boolean newRecord, boolean success) {
		if (newRecord)
			MRPValidator.modelChange(po, ModelValidator.TYPE_AFTER_NEW, log);
		else
			MRPValidator.modelChange(po, ModelValidator.TYPE_AFTER_CHANGE, log);
		return status_po;
	}
	
	/**
	 * Ejecución posterior al afterSave
	 * @return estado del procesamiento
	 */
	public MPluginStatusPO postBeforeDelete(PO po, boolean newRecord, boolean success) {
		MRPValidator.modelChange(po, ModelValidator.TYPE_BEFORE_DELETE, log);
		return status_po;
	}
	
}