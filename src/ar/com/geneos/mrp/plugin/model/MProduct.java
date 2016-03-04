package ar.com.geneos.mrp.plugin.model;

import java.util.Properties;

import org.openXpertya.model.PO;
import org.openXpertya.plugin.MPluginPO;
import org.openXpertya.plugin.MPluginStatusPO;
import org.openXpertya.util.CLogger;

public class MProduct extends MPluginPO {

	/** Logger */
	private static CLogger log = CLogger.getCLogger(MProduct.class);

	public MProduct(PO po, Properties ctx, String trxName, String aPackage) {
		super(po, ctx, trxName, aPackage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Ejecución posterior al beforeSave
	 * 
	 * @return estado del procesamiento
	 */
	public MPluginStatusPO preBeforeSave(PO po, boolean success) {
		String ret = MRPValidator.modelChange(po, ModelValidator.TYPE_BEFORE_CHANGE, log);
		if (ret != null) {
			status_po.setErrorMessage(ret);
			status_po.setContinueStatus(MPluginStatusPO.STATE_FALSE);
		}
		return status_po;
	}

}