package ar.com.geneos.mrp.plugin.model;
 
import java.util.Properties;

import org.openXpertya.model.PO;
import org.openXpertya.plugin.MPluginPO;
import org.openXpertya.plugin.MPluginStatusPO;
import org.openXpertya.util.CLogger;

 
public class MOrder extends MPluginPO {
 

	/** Logger */
	private static CLogger log = CLogger.getCLogger(MOrder.class);
 
	public MOrder(PO po, Properties ctx, String trxName, String aPackage) {
		super(po, ctx, trxName, aPackage);
		// TODO Auto-generated constructor stub
	}
 
	/**
	 * Ejecución posterior al afterSave
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
	 * Ejecución posterior al BeforeDelete
	 * @return estado del procesamiento
	 */
	@Override
	public MPluginStatusPO postBeforeDelete(PO po) {
		MRPValidator.modelChange(po, ModelValidator.TYPE_BEFORE_DELETE, log);
		return status_po;
	}
	
	
	
}