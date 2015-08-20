package ar.com.geneos.mrp.plugin.model;

/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.M_Table;
import org.openXpertya.model.POInfo;
import org.openXpertya.util.Env;
import org.openXpertya.util.KeyNamePair;

/**
 * Modelo Generado por PP_Cost_Collector
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-04-29 14:39:08.451
 */
public class LP_PP_Cost_Collector extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_PP_Cost_Collector(Properties ctx, int PP_Cost_Collector_ID, String trxName) {
		super(ctx, PP_Cost_Collector_ID, trxName);
		/**
		 * if (PP_Cost_Collector_ID == 0) { setC_DocType_ID (0);
		 * setC_DocTypeTarget_ID (0); setcostcollectortype (null); setDateAcct
		 * (new Timestamp(System.currentTimeMillis())); setDocumentNo (null);
		 * setM_Locator_ID (0); setMovementDate (new
		 * Timestamp(System.currentTimeMillis())); setMovementQty (Env.ZERO);
		 * setM_Product_ID (0); setM_Warehouse_ID (0); setPosted (false);
		 * setPP_Cost_Collector_ID (0); setPP_Order_ID (0); setProcessed
		 * (false); setS_Resource_ID (0); }
		 */
	}

	/** Load Constructor */
	public LP_PP_Cost_Collector(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("PP_Cost_Collector");

	/** TableName=PP_Cost_Collector */
	public static final String Table_Name = "PP_Cost_Collector";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "PP_Cost_Collector");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_PP_Cost_Collector[").append(getID()).append("]");
		return sb.toString();
	}

	/**
	 * Set Trx Organization. Performing or initiating organization
	 */
	public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	public void setAD_OrgTrx_ID(int AD_OrgTrx_ID) {
		if (AD_OrgTrx_ID <= 0)
			set_Value("AD_OrgTrx_ID", null);
		else
			set_Value("AD_OrgTrx_ID", new Integer(AD_OrgTrx_ID));
	}

	/**
	 * Get Trx Organization. Performing or initiating organization
	 */
	public int getAD_OrgTrx_ID() {
		Integer ii = (Integer) get_Value("AD_OrgTrx_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set User/Contact. User within the system - Internal or Business Partner
	 * Contact
	 */
	public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	public void setAD_User_ID(int AD_User_ID) {
		if (AD_User_ID <= 0)
			set_Value("AD_User_ID", null);
		else
			set_Value("AD_User_ID", new Integer(AD_User_ID));
	}

	/**
	 * Get User/Contact. User within the system - Internal or Business Partner
	 * Contact
	 */
	public int getAD_User_ID() {
		Integer ii = (Integer) get_Value("AD_User_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Activity. Business Activity
	 */
	public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	public void setC_Activity_ID(int C_Activity_ID) {
		if (C_Activity_ID <= 0)
			set_Value("C_Activity_ID", null);
		else
			set_Value("C_Activity_ID", new Integer(C_Activity_ID));
	}

	/**
	 * Get Activity. Business Activity
	 */
	public int getC_Activity_ID() {
		Integer ii = (Integer) get_Value("C_Activity_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Campaign. Marketing Campaign
	 */
	public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	public void setC_Campaign_ID(int C_Campaign_ID) {
		if (C_Campaign_ID <= 0)
			set_Value("C_Campaign_ID", null);
		else
			set_Value("C_Campaign_ID", new Integer(C_Campaign_ID));
	}

	/**
	 * Get Campaign. Marketing Campaign
	 */
	public int getC_Campaign_ID() {
		Integer ii = (Integer) get_Value("C_Campaign_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Document Type. Document type or rules
	 */
	public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	public void setC_DocType_ID(int C_DocType_ID) {
		set_Value("C_DocType_ID", new Integer(C_DocType_ID));
	}

	/**
	 * Get Document Type. Document type or rules
	 */
	public int getC_DocType_ID() {
		Integer ii = (Integer) get_Value("C_DocType_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Target Document Type. Target document type for conversing documents
	 */
	public static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	public void setC_DocTypeTarget_ID(int C_DocTypeTarget_ID) {
		set_Value("C_DocTypeTarget_ID", new Integer(C_DocTypeTarget_ID));
	}

	/**
	 * Get Target Document Type. Target document type for conversing documents
	 */
	public int getC_DocTypeTarget_ID() {
		Integer ii = (Integer) get_Value("C_DocTypeTarget_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set costcollectortype */
	public static final String COLUMNNAME_costcollectortype = "costcollectortype";

	public void setCostCollectorType(String costcollectortype) {
		if (costcollectortype == null)
			throw new IllegalArgumentException("costcollectortype is mandatory");
		if (costcollectortype.length() > 3) {
			log.warning("Length > 3 - truncated");
			costcollectortype = costcollectortype.substring(0, 3);
		}
		set_Value("costcollectortype", costcollectortype);
	}

	/** Get costcollectortype */
	public String getCostCollectorType() {
		return (String) get_Value("costcollectortype");
	}

	/**
	 * Set Project. Financial Project
	 */
	public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	public void setC_Project_ID(int C_Project_ID) {
		if (C_Project_ID <= 0)
			set_Value("C_Project_ID", null);
		else
			set_Value("C_Project_ID", new Integer(C_Project_ID));
	}

	/**
	 * Get Project. Financial Project
	 */
	public int getC_Project_ID() {
		Integer ii = (Integer) get_Value("C_Project_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set UOM. Unit of Measure
	 */
	public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	public void setC_UOM_ID(int C_UOM_ID) {
		if (C_UOM_ID <= 0)
			set_Value("C_UOM_ID", null);
		else
			set_Value("C_UOM_ID", new Integer(C_UOM_ID));
	}

	/**
	 * Get UOM. Unit of Measure
	 */
	public int getC_UOM_ID() {
		Integer ii = (Integer) get_Value("C_UOM_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Account Date. Accounting Date
	 */
	public static final String COLUMNNAME_DateAcct = "DateAcct";

	public void setDateAcct(Timestamp DateAcct) {
		if (DateAcct == null)
			throw new IllegalArgumentException("DateAcct is mandatory");
		set_Value("DateAcct", DateAcct);
	}

	/**
	 * Get Account Date. Accounting Date
	 */
	public Timestamp getDateAcct() {
		return (Timestamp) get_Value("DateAcct");
	}

	/**
	 * Set Description. Optional short description of the record
	 */
	public static final String COLUMNNAME_Description = "Description";

	public void setDescription(String Description) {
		if (Description != null && Description.length() > 255) {
			log.warning("Length > 255 - truncated");
			Description = Description.substring(0, 255);
		}
		set_Value("Description", Description);
	}

	/**
	 * Get Description. Optional short description of the record
	 */
	public String getDescription() {
		return (String) get_Value("Description");
	}

	/**
	 * Set Document Action. The targeted status of the document
	 */
	public static final String COLUMNNAME_DocAction = "DocAction";

	public void setDocAction(String DocAction) {
		if (DocAction != null && DocAction.length() > 2) {
			log.warning("Length > 2 - truncated");
			DocAction = DocAction.substring(0, 2);
		}
		set_Value("DocAction", DocAction);
	}

	/**
	 * Get Document Action. The targeted status of the document
	 */
	public String getDocAction() {
		return (String) get_Value("DocAction");
	}

	/**
	 * Set Document Status. The current status of the document
	 */
	public static final String COLUMNNAME_DocStatus = "DocStatus";

	public void setDocStatus(String DocStatus) {
		if (DocStatus != null && DocStatus.length() > 2) {
			log.warning("Length > 2 - truncated");
			DocStatus = DocStatus.substring(0, 2);
		}
		set_Value("DocStatus", DocStatus);
	}

	/**
	 * Get Document Status. The current status of the document
	 */
	public String getDocStatus() {
		return (String) get_Value("DocStatus");
	}

	/**
	 * Set Document No. Document sequence NUMERIC of the document
	 */
	public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	public void setDocumentNo(String DocumentNo) {
		if (DocumentNo == null)
			throw new IllegalArgumentException("DocumentNo is mandatory");
		if (DocumentNo.length() > 30) {
			log.warning("Length > 30 - truncated");
			DocumentNo = DocumentNo.substring(0, 30);
		}
		set_Value("DocumentNo", DocumentNo);
	}

	/**
	 * Get Document No. Document sequence NUMERIC of the document
	 */
	public String getDocumentNo() {
		return (String) get_Value("DocumentNo");
	}

	/** Set Duration Real */
	public static final String COLUMNNAME_DurationReal = "DurationReal";

	public void setDurationReal(BigDecimal DurationReal) {
		set_Value("DurationReal", DurationReal);
	}

	/** Get Duration Real */
	public BigDecimal getDurationReal() {
		BigDecimal bd = (BigDecimal) get_Value("DurationReal");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set isbatchtime */
	public static final String COLUMNNAME_isbatchtime = "isbatchtime";

	public void setisbatchtime(boolean isbatchtime) {
		set_Value("isbatchtime", new Boolean(isbatchtime));
	}

	/** Get isbatchtime */
	public boolean isbatchtime() {
		Object oo = get_Value("isbatchtime");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Is Subcontracting. The operation will be made in an external Work
	 * Center
	 */
	public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

	public void setIsSubcontracting(boolean IsSubcontracting) {
		set_Value("IsSubcontracting", new Boolean(IsSubcontracting));
	}

	/**
	 * Get Is Subcontracting. The operation will be made in an external Work
	 * Center
	 */
	public boolean isSubcontracting() {
		Object oo = get_Value("IsSubcontracting");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Attribute Set Instance. Product Attribute Set Instance
	 */
	public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
		if (M_AttributeSetInstance_ID <= 0)
			set_Value("M_AttributeSetInstance_ID", null);
		else
			set_Value("M_AttributeSetInstance_ID", new Integer(M_AttributeSetInstance_ID));
	}

	/**
	 * Get Attribute Set Instance. Product Attribute Set Instance
	 */
	public int getM_AttributeSetInstance_ID() {
		Integer ii = (Integer) get_Value("M_AttributeSetInstance_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Locator. Warehouse Locator
	 */
	public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	public void setM_Locator_ID(int M_Locator_ID) {
		set_Value("M_Locator_ID", new Integer(M_Locator_ID));
	}

	/**
	 * Get Locator. Warehouse Locator
	 */
	public int getM_Locator_ID() {
		Integer ii = (Integer) get_Value("M_Locator_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Movement Date. Date a product was moved in or out of inventory
	 */
	public static final String COLUMNNAME_MovementDate = "MovementDate";

	public void setMovementDate(Timestamp MovementDate) {
		if (MovementDate == null)
			throw new IllegalArgumentException("MovementDate is mandatory");
		set_Value("MovementDate", MovementDate);
	}

	/**
	 * Get Movement Date. Date a product was moved in or out of inventory
	 */
	public Timestamp getMovementDate() {
		return (Timestamp) get_Value("MovementDate");
	}

	/**
	 * Set Movement Quantity. Quantity of a product moved.
	 */
	public static final String COLUMNNAME_MovementQty = "MovementQty";

	public void setMovementQty(BigDecimal MovementQty) {
		if (MovementQty == null)
			throw new IllegalArgumentException("MovementQty is mandatory");
		set_Value("MovementQty", MovementQty);
	}

	/**
	 * Get Movement Quantity. Quantity of a product moved.
	 */
	public BigDecimal getMovementQty() {
		BigDecimal bd = (BigDecimal) get_Value("MovementQty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Product. Product, Service, Item
	 */
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	public void setM_Product_ID(int M_Product_ID) {
		set_Value("M_Product_ID", new Integer(M_Product_ID));
	}

	/**
	 * Get Product. Product, Service, Item
	 */
	public int getM_Product_ID() {
		Integer ii = (Integer) get_Value("M_Product_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Warehouse. Storage Warehouse and Service Point
	 */
	public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	public void setM_Warehouse_ID(int M_Warehouse_ID) {
		set_Value("M_Warehouse_ID", new Integer(M_Warehouse_ID));
	}

	/**
	 * Get Warehouse. Storage Warehouse and Service Point
	 */
	public int getM_Warehouse_ID() {
		Integer ii = (Integer) get_Value("M_Warehouse_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Posted. Posting status
	 */
	public static final String COLUMNNAME_Posted = "Posted";

	public void setPosted(boolean Posted) {
		set_Value("Posted", new Boolean(Posted));
	}

	/**
	 * Get Posted. Posting status
	 */
	public boolean isPosted() {
		Object oo = get_Value("Posted");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PP_Cost_Collector_ID */
	public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	public void setPP_Cost_Collector_ID(int PP_Cost_Collector_ID) {
		set_ValueNoCheck("PP_Cost_Collector_ID", new Integer(PP_Cost_Collector_ID));
	}

	/** Get PP_Cost_Collector_ID */
	public int getPP_Cost_Collector_ID() {
		Integer ii = (Integer) get_Value("PP_Cost_Collector_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_Bomline_ID */
	public static final String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	public void setPP_Order_Bomline_ID(int PP_Order_Bomline_ID) {
		if (PP_Order_Bomline_ID <= 0)
			set_Value("PP_Order_BOMLine_ID", null);
		else
			set_Value("PP_Order_BOMLine_ID", new Integer(PP_Order_Bomline_ID));
	}

	/** Get PP_Order_BOMLine_ID */
	public int getPP_Order_Bomline_ID() {
		Integer ii = (Integer) get_Value("PP_Order_BOMLine_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_ID */
	public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	public void setPP_Order_ID(int PP_Order_ID) {
		set_Value("PP_Order_ID", new Integer(PP_Order_ID));
	}

	/** Get PP_Order_ID */
	public int getPP_Order_ID() {
		Integer ii = (Integer) get_Value("PP_Order_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_Node_ID */
	public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	public void setPP_Order_Node_ID(int PP_Order_Node_ID) {
		if (PP_Order_Node_ID <= 0)
			set_Value("PP_Order_Node_ID", null);
		else
			set_Value("PP_Order_Node_ID", new Integer(PP_Order_Node_ID));
	}

	/** Get PP_Order_Node_ID */
	public int getPP_Order_Node_ID() {
		Integer ii = (Integer) get_Value("PP_Order_Node_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_Workflow_ID */
	public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

	public void setPP_Order_Workflow_ID(int PP_Order_Workflow_ID) {
		if (PP_Order_Workflow_ID <= 0)
			set_Value("PP_Order_Workflow_ID", null);
		else
			set_Value("PP_Order_Workflow_ID", new Integer(PP_Order_Workflow_ID));
	}

	/** Get PP_Order_Workflow_ID */
	public int getPP_Order_Workflow_ID() {
		Integer ii = (Integer) get_Value("PP_Order_Workflow_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Processed. The document has been processed
	 */
	public static final String COLUMNNAME_Processed = "Processed";

	public void setProcessed(boolean Processed) {
		set_Value("Processed", new Boolean(Processed));
	}

	/**
	 * Get Processed. The document has been processed
	 */
	public boolean isProcessed() {
		Object oo = get_Value("Processed");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set processedon */
	public static final String COLUMNNAME_processedon = "processedon";

	public void setprocessedon(BigDecimal processedon) {
		set_Value("processedon", processedon);
	}

	/** Get processedon */
	public BigDecimal getprocessedon() {
		BigDecimal bd = (BigDecimal) get_Value("processedon");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Process Now */
	public static final String COLUMNNAME_Processing = "Processing";

	public void setProcessing(boolean Processing) {
		set_Value("Processing", new Boolean(Processing));
	}

	/** Get Process Now */
	public boolean isProcessing() {
		Object oo = get_Value("Processing");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Qty Reject */
	public static final String COLUMNNAME_QtyReject = "QtyReject";

	public void setQtyReject(BigDecimal QtyReject) {
		set_Value("QtyReject", QtyReject);
	}

	/** Get Qty Reject */
	public BigDecimal getQtyReject() {
		BigDecimal bd = (BigDecimal) get_Value("QtyReject");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set reversal_id */
	public static final String COLUMNNAME_reversal_id = "reversal_id";

	public void setreversal_id(int reversal_id) {
		set_Value("reversal_id", new Integer(reversal_id));
	}

	/** Get reversal_id */
	public int getreversal_id() {
		Integer ii = (Integer) get_Value("reversal_id");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Scrapped Quantity. The Quantity scrapped due to QA issues
	 */
	public static final String COLUMNNAME_ScrappedQty = "ScrappedQty";

	public void setScrappedQty(BigDecimal ScrappedQty) {
		set_Value("ScrappedQty", ScrappedQty);
	}

	/**
	 * Get Scrapped Quantity. The Quantity scrapped due to QA issues
	 */
	public BigDecimal getScrappedQty() {
		BigDecimal bd = (BigDecimal) get_Value("ScrappedQty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Setup Time Real */
	public static final String COLUMNNAME_SetupTimeReal = "SetupTimeReal";

	public void setSetupTimeReal(BigDecimal SetupTimeReal) {
		set_Value("SetupTimeReal", SetupTimeReal);
	}

	/** Get Setup Time Real */
	public BigDecimal getSetupTimeReal() {
		BigDecimal bd = (BigDecimal) get_Value("SetupTimeReal");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Resource. Resource
	 */
	public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	public void setS_Resource_ID(int S_Resource_ID) {
		set_Value("S_Resource_ID", new Integer(S_Resource_ID));
	}

	/**
	 * Get Resource. Resource
	 */
	public int getS_Resource_ID() {
		Integer ii = (Integer) get_Value("S_Resource_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set User1. User defined element #1
	 */
	public static final String COLUMNNAME_User1_ID = "User1_ID";

	public void setUser1_ID(int User1_ID) {
		if (User1_ID <= 0)
			set_Value("User1_ID", null);
		else
			set_Value("User1_ID", new Integer(User1_ID));
	}

	/**
	 * Get User1. User defined element #1
	 */
	public int getUser1_ID() {
		Integer ii = (Integer) get_Value("User1_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set User2. User defined element #2
	 */
	public static final String COLUMNNAME_User2_ID = "User2_ID";

	public void setUser2_ID(int User2_ID) {
		if (User2_ID <= 0)
			set_Value("User2_ID", null);
		else
			set_Value("User2_ID", new Integer(User2_ID));
	}

	/**
	 * Get User2. User defined element #2
	 */
	public int getUser2_ID() {
		Integer ii = (Integer) get_Value("User2_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}
}
