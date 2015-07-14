package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MConversionRate;
import org.openXpertya.model.MInOutLine;
import org.openXpertya.model.MInvoiceLine;
import org.openXpertya.model.MMatchPO;
import org.openXpertya.model.MRequisition;

public class LP_M_Requisition extends MRequisition {

	public LP_M_Requisition(Properties ctx, int M_Requisition_ID, String trxName) {
		super(ctx, M_Requisition_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public LP_M_Requisition(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";
	
	/**
	 * Set Document Date.
	 * 
	 * @param DateDoc
	 *            Date of the Document
	 */
	public void setDateDoc(Timestamp DateDoc) {
		set_Value(COLUMNNAME_DateDoc, DateDoc);
	}

	/**
	 * Get Document Date.
	 * 
	 * @return Date of the Document
	 */
	public Timestamp getDateDoc() {
		return (Timestamp) get_Value(COLUMNNAME_DateDoc);
	}

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";
	
	/**
	 * Set Document Type.
	 * 
	 * @param C_DocType_ID
	 *            Document type or rules
	 */
	public void setC_DocType_ID(int C_DocType_ID) {
		if (C_DocType_ID < 0)
			set_Value(COLUMNNAME_C_DocType_ID, null);
		else
			set_Value(COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/**
	 * Get Document Type.
	 * 
	 * @return Document type or rules
	 */
	public int getC_DocType_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

}
