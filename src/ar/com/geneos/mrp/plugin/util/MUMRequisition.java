package ar.com.geneos.mrp.plugin.util;

import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MAttributeSet;
import org.openXpertya.model.MClient;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MProductCategory;
import org.openXpertya.model.MProductCategoryAcct;
import org.openXpertya.model.MRequisition;
import org.openXpertya.model.MResource;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.LP_C_AcctSchema;
import ar.com.geneos.mrp.plugin.model.LP_M_Requisition;

public class MUMRequisition {

	public static Timestamp getDateDoc(MRequisition r) {
		LP_M_Requisition aux = new LP_M_Requisition(r.getCtx(),r.getM_Requisition_ID(),r.get_TrxName());
		return aux.getDateDoc();
	}
}
