package ar.com.geneos.mrp.plugin.model;
 
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;

import org.openXpertya.model.MBPartner;
import org.openXpertya.model.MInvoiceLine;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.PO;
import org.openXpertya.plugin.MPluginDocAction;
import org.openXpertya.plugin.MPluginStatusDocAction;
import org.openXpertya.process.DocAction;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.Env;
 
public class MInOut extends MPluginDocAction {
 

	/** Logger */
	private static CLogger log = CLogger.getCLogger(MInOut.class);
 
	public MInOut(PO po, Properties ctx, String trxName, String aPackage) {
		super(po, ctx, trxName, aPackage);
		// TODO Auto-generated constructor stub
	}
 
	public MPluginStatusDocAction postCompleteIt(DocAction document) {

		MRPValidator.docValidate((PO) document, ModelValidator.TIMING_AFTER_COMPLETE, log);
		return status_docAction;
		
	}
	
}