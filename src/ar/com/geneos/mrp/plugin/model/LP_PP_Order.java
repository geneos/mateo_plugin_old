/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MReference;
import org.openXpertya.model.M_Table;
import org.openXpertya.model.POInfo;
import org.openXpertya.util.Env;
import org.openXpertya.util.KeyNamePair;

/**
 * Modelo Generado por PP_Order
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-05-19 06:40:19.03
 */
public class LP_PP_Order extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_PP_Order(Properties ctx, int PP_Order_ID, String trxName) {
		super(ctx, PP_Order_ID, trxName);
		/**
		 * if (PP_Order_ID == 0) { setAD_Workflow_ID (0); setC_DocTypeTarget_ID
		 * (0); setC_UOM_ID (0); setDateOrdered (new
		 * Timestamp(System.currentTimeMillis())); setDatePromised (new
		 * Timestamp(System.currentTimeMillis())); setDateStartSchedule (new
		 * Timestamp(System.currentTimeMillis())); setDocAction (null);
		 * setDocStatus (null); // DR setDocumentNo (null); setIsApproved
		 * (false); setIsPrinted (false); setIsSelected (false); setIsSOTrx
		 * (false); setLine (0); setM_Product_ID (0); setM_Warehouse_ID (0);
		 * setPP_Order_ID (0); setPP_Product_BOM_ID (0); setPriorityRule (null);
		 * setProcessed (false); setQtyDelivered (Env.ZERO); setQtyOrdered
		 * (Env.ZERO); setQtyReject (Env.ZERO); setQtyScrap (Env.ZERO);
		 * setS_Resource_ID (0); setYield (Env.ZERO); }
		 */
	}

	/** Load Constructor */
	public LP_PP_Order(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("PP_Order");

	/** TableName=PP_Order */
	public static final String Table_Name = "PP_Order";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "PP_Order");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_PP_Order[").append(getID()).append("]");
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
	 * Set Workflow. Workflow or combination of tasks
	 */
	public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	public void setAD_Workflow_ID(int AD_Workflow_ID) {
		set_Value("AD_Workflow_ID", new Integer(AD_Workflow_ID));
	}

	/**
	 * Get Workflow. Workflow or combination of tasks
	 */
	public int getAD_Workflow_ID() {
		Integer ii = (Integer) get_Value("AD_Workflow_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Assay */
	public static final String COLUMNNAME_Assay = "Assay";

	public void setAssay(BigDecimal Assay) {
		set_Value("Assay", Assay);
	}

	/** Get Assay */
	public BigDecimal getAssay() {
		BigDecimal bd = (BigDecimal) get_Value("Assay");
		if (bd == null)
			return Env.ZERO;
		return bd;
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

	public static final int C_DOCTYPE_ID_AD_Reference_ID = MReference.getReferenceID("C_DocType MFG");
	/**
	 * Set Document Type. Document type or rules
	 */
	public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	public void setC_DocType_ID(int C_DocType_ID) {
		if (C_DocType_ID <= 0)
			set_Value("C_DocType_ID", null);
		else
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

	public static final int C_DOCTYPETARGET_ID_AD_Reference_ID = MReference.getReferenceID("C_DocType MFG");
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

	/**
	 * Set Copy From. Copy From Record
	 */
	public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	public void setCopyFrom(String CopyFrom) {
		if (CopyFrom != null && CopyFrom.length() > 1) {
			log.warning("Length > 1 - truncated");
			CopyFrom = CopyFrom.substring(0, 1);
		}
		set_Value("CopyFrom", CopyFrom);
	}

	/**
	 * Get Copy From. Copy From Record
	 */
	public String getCopyFrom() {
		return (String) get_Value("CopyFrom");
	}

	/**
	 * Set Sales Order Line. Sales Order Line
	 */
	public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	public void setC_OrderLine_ID(int C_OrderLine_ID) {
		if (C_OrderLine_ID <= 0)
			set_Value("C_OrderLine_ID", null);
		else
			set_Value("C_OrderLine_ID", new Integer(C_OrderLine_ID));
	}

	/**
	 * Get Sales Order Line. Sales Order Line
	 */
	public int getC_OrderLine_ID() {
		Integer ii = (Integer) get_Value("C_OrderLine_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
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
	 * Set Date Confirm. Date when the planned order was Confirmed from MRP.
	 */
	public static final String COLUMNNAME_DateConfirm = "DateConfirm";

	public void setDateConfirm(Timestamp DateConfirm) {
		set_Value("DateConfirm", DateConfirm);
	}

	/**
	 * Get Date Confirm. Date when the planned order was Confirmed from MRP.
	 */
	public Timestamp getDateConfirm() {
		return (Timestamp) get_Value("DateConfirm");
	}

	/**
	 * Set Date Delivered. Date when the product was delivered
	 */
	public static final String COLUMNNAME_DateDelivered = "DateDelivered";

	public void setDateDelivered(Timestamp DateDelivered) {
		set_Value("DateDelivered", DateDelivered);
	}

	/**
	 * Get Date Delivered. Date when the product was delivered
	 */
	public Timestamp getDateDelivered() {
		return (Timestamp) get_Value("DateDelivered");
	}

	/**
	 * Set Finish Date. Finish or (planned) completion date
	 */
	public static final String COLUMNNAME_DateFinish = "DateFinish";

	public void setDateFinish(Timestamp DateFinish) {
		set_Value("DateFinish", DateFinish);
	}

	/**
	 * Get Finish Date. Finish or (planned) completion date
	 */
	public Timestamp getDateFinish() {
		return (Timestamp) get_Value("DateFinish");
	}

	/**
	 * Set Finish Date Scheduled. Last date of the schedule.
	 */
	public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";

	public void setDateFinishSchedule(Timestamp DateFinishSchedule) {
		set_Value("DateFinishSchedule", DateFinishSchedule);
	}

	/**
	 * Get Finish Date Scheduled. Last date of the schedule.
	 */
	public Timestamp getDateFinishSchedule() {
		return (Timestamp) get_Value("DateFinishSchedule");
	}

	/**
	 * Set TIMESTAMP Ordered. TIMESTAMP of Order
	 */
	public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	public void setDateOrdered(Timestamp DateOrdered) {
		if (DateOrdered == null)
			throw new IllegalArgumentException("DateOrdered is mandatory");
		set_Value("DateOrdered", DateOrdered);
	}

	/**
	 * Get TIMESTAMP Ordered. TIMESTAMP of Order
	 */
	public Timestamp getDateOrdered() {
		return (Timestamp) get_Value("DateOrdered");
	}

	/**
	 * Set TIMESTAMP Promised. TIMESTAMP Order was promised
	 */
	public static final String COLUMNNAME_DatePromised = "DatePromised";

	public void setDatePromised(Timestamp DatePromised) {
		if (DatePromised == null)
			throw new IllegalArgumentException("DatePromised is mandatory");
		set_Value("DatePromised", DatePromised);
	}

	/**
	 * Get TIMESTAMP Promised. TIMESTAMP Order was promised
	 */
	public Timestamp getDatePromised() {
		return (Timestamp) get_Value("DatePromised");
	}

	/**
	 * Set Start Date. Starting Date
	 */
	public static final String COLUMNNAME_DateStart = "DateStart";

	public void setDateStart(Timestamp DateStart) {
		set_Value("DateStart", DateStart);
	}

	/**
	 * Get Start Date. Starting Date
	 */
	public Timestamp getDateStart() {
		return (Timestamp) get_Value("DateStart");
	}

	/**
	 * Set Starting Date Scheduled. Starting Date Scheduled
	 */
	public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

	public void setDateStartSchedule(Timestamp DateStartSchedule) {
		if (DateStartSchedule == null)
			throw new IllegalArgumentException("DateStartSchedule is mandatory");
		set_Value("DateStartSchedule", DateStartSchedule);
	}

	/**
	 * Get Starting Date Scheduled. Starting Date Scheduled
	 */
	public Timestamp getDateStartSchedule() {
		return (Timestamp) get_Value("DateStartSchedule");
	}

	/**
	 * Set Description. Optional short description of the record
	 */
	public static final String COLUMNNAME_Description = "Description";

	public void setDescription(String Description) {
		if (Description != null && Description.length() > 510) {
			log.warning("Length > 510 - truncated");
			Description = Description.substring(0, 510);
		}
		set_Value("Description", Description);
	}

	/**
	 * Get Description. Optional short description of the record
	 */
	public String getDescription() {
		return (String) get_Value("Description");
	}

	public static final int DOCACTION_AD_Reference_ID = MReference.getReferenceID("_Document Action");
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/**
	 * Set Document Action. The targeted status of the document
	 */
	public static final String COLUMNNAME_DocAction = "DocAction";

	public void setDocAction(String DocAction) {
		if (DocAction.equals("AP") || DocAction.equals("CL") || DocAction.equals("PR") || DocAction.equals("IN") || DocAction.equals("CO")
				|| DocAction.equals("--") || DocAction.equals("RC") || DocAction.equals("RJ") || DocAction.equals("RA") || DocAction.equals("WC")
				|| DocAction.equals("XL") || DocAction.equals("RE") || DocAction.equals("PO") || DocAction.equals("VO"))
			;
		else
			throw new IllegalArgumentException(
					"DocAction Invalid value - Reference = DOCACTION_AD_Reference_ID - AP - CL - PR - IN - CO - -- - RC - RJ - RA - WC - XL - RE - PO - VO");
		if (DocAction == null)
			throw new IllegalArgumentException("DocAction is mandatory");
		if (DocAction.length() > 2) {
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

	public static final int DOCSTATUS_AD_Reference_ID = MReference.getReferenceID("_Document Status");
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/**
	 * Set Document Status. The current status of the document
	 */
	public static final String COLUMNNAME_DocStatus = "DocStatus";

	public void setDocStatus(String DocStatus) {
		if (DocStatus.equals("VO") || DocStatus.equals("NA") || DocStatus.equals("IP") || DocStatus.equals("CO") || DocStatus.equals("AP")
				|| DocStatus.equals("CL") || DocStatus.equals("WC") || DocStatus.equals("WP") || DocStatus.equals("??") || DocStatus.equals("DR")
				|| DocStatus.equals("IN") || DocStatus.equals("RE"))
			;
		else
			throw new IllegalArgumentException(
					"DocStatus Invalid value - Reference = DOCSTATUS_AD_Reference_ID - VO - NA - IP - CO - AP - CL - WC - WP - ?? - DR - IN - RE");
		if (DocStatus == null)
			throw new IllegalArgumentException("DocStatus is mandatory");
		if (DocStatus.length() > 2) {
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
		if (DocumentNo.length() > 60) {
			log.warning("Length > 60 - truncated");
			DocumentNo = DocumentNo.substring(0, 60);
		}
		set_Value("DocumentNo", DocumentNo);
	}

	/**
	 * Get Document No. Document sequence NUMERIC of the document
	 */
	public String getDocumentNo() {
		return (String) get_Value("DocumentNo");
	}

	/** Set Float After */
	public static final String COLUMNNAME_FloatAfter = "FloatAfter";

	public void setFloatAfter(BigDecimal FloatAfter) {
		set_Value("FloatAfter", FloatAfter);
	}

	/** Get Float After */
	public BigDecimal getFloatAfter() {
		BigDecimal bd = (BigDecimal) get_Value("FloatAfter");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Float Before */
	public static final String COLUMNNAME_FloatBefored = "FloatBefored";

	public void setFloatBefored(BigDecimal FloatBefored) {
		set_Value("FloatBefored", FloatBefored);
	}

	/** Get Float Before */
	public BigDecimal getFloatBefored() {
		BigDecimal bd = (BigDecimal) get_Value("FloatBefored");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Approved. Indicates if this document requires approval
	 */
	public static final String COLUMNNAME_IsApproved = "IsApproved";

	public void setIsApproved(boolean IsApproved) {
		set_Value("IsApproved", new Boolean(IsApproved));
	}

	/**
	 * Get Approved. Indicates if this document requires approval
	 */
	public boolean isApproved() {
		Object oo = get_Value("IsApproved");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Printed. Indicates if this document / line is printed
	 */
	public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	public void setIsPrinted(boolean IsPrinted) {
		set_Value("IsPrinted", new Boolean(IsPrinted));
	}

	/**
	 * Get Printed. Indicates if this document / line is printed
	 */
	public boolean isPrinted() {
		Object oo = get_Value("IsPrinted");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsQtyPercentage */
	public static final String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";

	public void setIsQtyPercentage(BigDecimal IsQtyPercentage) {
		set_Value("IsQtyPercentage", IsQtyPercentage);
	}

	/** Get IsQtyPercentage */
	public BigDecimal getIsQtyPercentage() {
		BigDecimal bd = (BigDecimal) get_Value("IsQtyPercentage");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Selected */
	public static final String COLUMNNAME_IsSelected = "IsSelected";

	public void setIsSelected(boolean IsSelected) {
		set_Value("IsSelected", new Boolean(IsSelected));
	}

	/** Get Selected */
	public boolean isSelected() {
		Object oo = get_Value("IsSelected");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Sales Transaction. This is a Sales Transaction
	 */
	public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	public void setIsSOTrx(boolean IsSOTrx) {
		set_Value("IsSOTrx", new Boolean(IsSOTrx));
	}

	/**
	 * Get Sales Transaction. This is a Sales Transaction
	 */
	public boolean isSOTrx() {
		Object oo = get_Value("IsSOTrx");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Line No. Unique line for this document
	 */
	public static final String COLUMNNAME_Line = "Line";

	public void setLine(int Line) {
		set_Value("Line", new Integer(Line));
	}

	/**
	 * Get Line No. Unique line for this document
	 */
	public int getLine() {
		Integer ii = (Integer) get_Value("Line");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Lot No. Lot number (alphanumeric)
	 */
	public static final String COLUMNNAME_Lot = "Lot";

	public void setLot(String Lot) {
		if (Lot != null && Lot.length() > 20) {
			log.warning("Length > 20 - truncated");
			Lot = Lot.substring(0, 20);
		}
		set_Value("Lot", Lot);
	}

	/**
	 * Get Lot No. Lot number (alphanumeric)
	 */
	public String getLot() {
		return (String) get_Value("Lot");
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

	public static final int M_PRODUCT_ID_AD_Reference_ID = MReference.getReferenceID("M_Product BOM (stocked)");
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
	 * Set Order Type. Order type: discrete, reprocessing, expenses, repetitive
	 */
	public static final String COLUMNNAME_OrderType = "OrderType";

	public void setOrderType(String OrderType) {
		if (OrderType != null && OrderType.length() > 1) {
			log.warning("Length > 1 - truncated");
			OrderType = OrderType.substring(0, 1);
		}
		set_Value("OrderType", OrderType);
	}

	/**
	 * Get Order Type. Order type: discrete, reprocessing, expenses, repetitive
	 */
	public String getOrderType() {
		return (String) get_Value("OrderType");
	}

	public static final int PLANNER_ID_AD_Reference_ID = MReference.getReferenceID("AD_User - Internal");
	/**
	 * Set Planner . ID of the person responsible of planning the product.
	 */
	public static final String COLUMNNAME_Planner_ID = "Planner_ID";

	public void setPlanner_ID(int Planner_ID) {
		if (Planner_ID <= 0)
			set_Value("Planner_ID", null);
		else
			set_Value("Planner_ID", new Integer(Planner_ID));
	}

	/**
	 * Get Planner . ID of the person responsible of planning the product.
	 */
	public int getPlanner_ID() {
		Integer ii = (Integer) get_Value("Planner_ID");
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

	/** Set PP_Order_ID */
	public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	public void setPP_Order_ID(int PP_Order_ID) {
		set_ValueNoCheck("PP_Order_ID", new Integer(PP_Order_ID));
	}

	/** Get PP_Order_ID */
	public int getPP_Order_ID() {
		Integer ii = (Integer) get_Value("PP_Order_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Product_BOM_ID */
	public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	public void setPP_Product_BOM_ID(int PP_Product_BOM_ID) {
		set_Value("PP_Product_BOM_ID", new Integer(PP_Product_BOM_ID));
	}

	/** Get PP_Product_BOM_ID */
	public int getPP_Product_BOM_ID() {
		Integer ii = (Integer) get_Value("PP_Product_BOM_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public static final int PRIORITYRULE_AD_Reference_ID = MReference.getReferenceID("_PriorityRule");
	/** Medium = 5 */
	public static final String PRIORITYRULE_Medium = "5";
	/** High = 3 */
	public static final String PRIORITYRULE_High = "3";
	/** Low = 7 */
	public static final String PRIORITYRULE_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYRULE_Minor = "9";
	/**
	 * Set Priority. Priority of a document
	 */
	public static final String COLUMNNAME_PriorityRule = "PriorityRule";

	public void setPriorityRule(String PriorityRule) {
		if (PriorityRule.equals("5") || PriorityRule.equals("3") || PriorityRule.equals("7") || PriorityRule.equals("1") || PriorityRule.equals("9"))
			;
		else
			throw new IllegalArgumentException("PriorityRule Invalid value - Reference = PRIORITYRULE_AD_Reference_ID - 5 - 3 - 7 - 1 - 9");
		if (PriorityRule == null)
			throw new IllegalArgumentException("PriorityRule is mandatory");
		if (PriorityRule.length() > 1) {
			log.warning("Length > 1 - truncated");
			PriorityRule = PriorityRule.substring(0, 1);
		}
		set_Value("PriorityRule", PriorityRule);
	}

	/**
	 * Get Priority. Priority of a document
	 */
	public String getPriorityRule() {
		return (String) get_Value("PriorityRule");
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

	/** Set ProcessedOn */
	public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	public void setProcessedOn(BigDecimal ProcessedOn) {
		set_Value("ProcessedOn", ProcessedOn);
	}

	/** Get ProcessedOn */
	public BigDecimal getProcessedOn() {
		BigDecimal bd = (BigDecimal) get_Value("ProcessedOn");
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

	/** Set Qty Batchs */
	public static final String COLUMNNAME_QtyBatchs = "QtyBatchs";

	public void setQtyBatchs(BigDecimal QtyBatchs) {
		set_Value("QtyBatchs", QtyBatchs);
	}

	/** Get Qty Batchs */
	public BigDecimal getQtyBatchs() {
		BigDecimal bd = (BigDecimal) get_Value("QtyBatchs");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Qty Batch Size */
	public static final String COLUMNNAME_QtyBatchSize = "QtyBatchSize";

	public void setQtyBatchSize(BigDecimal QtyBatchSize) {
		set_Value("QtyBatchSize", QtyBatchSize);
	}

	/** Get Qty Batch Size */
	public BigDecimal getQtyBatchSize() {
		BigDecimal bd = (BigDecimal) get_Value("QtyBatchSize");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Delivered Quantity. Delivered Quantity
	 */
	public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

	public void setQtyDelivered(BigDecimal QtyDelivered) {
		if (QtyDelivered == null)
			throw new IllegalArgumentException("QtyDelivered is mandatory");
		set_Value("QtyDelivered", QtyDelivered);
	}

	/**
	 * Get Delivered Quantity. Delivered Quantity
	 */
	public BigDecimal getQtyDelivered() {
		BigDecimal bd = (BigDecimal) get_Value("QtyDelivered");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Quantity. The Quantity Entered is based on the selected UoM
	 */
	public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	public void setQtyEntered(BigDecimal QtyEntered) {
		set_Value("QtyEntered", QtyEntered);
	}

	/**
	 * Get Quantity. The Quantity Entered is based on the selected UoM
	 */
	public BigDecimal getQtyEntered() {
		BigDecimal bd = (BigDecimal) get_Value("QtyEntered");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Ordered Quantity. Ordered Quantity
	 */
	public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	public void setQtyOrdered(BigDecimal QtyOrdered) {
		if (QtyOrdered == null)
			throw new IllegalArgumentException("QtyOrdered is mandatory");
		set_Value("QtyOrdered", QtyOrdered);
	}

	/**
	 * Get Ordered Quantity. Ordered Quantity
	 */
	public BigDecimal getQtyOrdered() {
		BigDecimal bd = (BigDecimal) get_Value("QtyOrdered");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Qty Reject */
	public static final String COLUMNNAME_QtyReject = "QtyReject";

	public void setQtyReject(BigDecimal QtyReject) {
		if (QtyReject == null)
			throw new IllegalArgumentException("QtyReject is mandatory");
		set_Value("QtyReject", QtyReject);
	}

	/** Get Qty Reject */
	public BigDecimal getQtyReject() {
		BigDecimal bd = (BigDecimal) get_Value("QtyReject");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Reserved Quantity. Reserved Quantity
	 */
	public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	public void setQtyReserved(BigDecimal QtyReserved) {
		set_Value("QtyReserved", QtyReserved);
	}

	/**
	 * Get Reserved Quantity. Reserved Quantity
	 */
	public BigDecimal getQtyReserved() {
		BigDecimal bd = (BigDecimal) get_Value("QtyReserved");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Qty Scrap */
	public static final String COLUMNNAME_QtyScrap = "QtyScrap";

	public void setQtyScrap(BigDecimal QtyScrap) {
		if (QtyScrap == null)
			throw new IllegalArgumentException("QtyScrap is mandatory");
		set_Value("QtyScrap", QtyScrap);
	}

	/** Get Qty Scrap */
	public BigDecimal getQtyScrap() {
		BigDecimal bd = (BigDecimal) get_Value("QtyScrap");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Schedule Type. Type of schedule
	 */
	public static final String COLUMNNAME_ScheduleType = "ScheduleType";

	public void setScheduleType(String ScheduleType) {
		if (ScheduleType != null && ScheduleType.length() > 1) {
			log.warning("Length > 1 - truncated");
			ScheduleType = ScheduleType.substring(0, 1);
		}
		set_Value("ScheduleType", ScheduleType);
	}

	/**
	 * Get Schedule Type. Type of schedule
	 */
	public String getScheduleType() {
		return (String) get_Value("ScheduleType");
	}

	/**
	 * Set Serial No. Product Serial Number
	 */
	public static final String COLUMNNAME_SerNo = "SerNo";

	public void setSerNo(String SerNo) {
		if (SerNo != null && SerNo.length() > 20) {
			log.warning("Length > 20 - truncated");
			SerNo = SerNo.substring(0, 20);
		}
		set_Value("SerNo", SerNo);
	}

	/**
	 * Get Serial No. Product Serial Number
	 */
	public String getSerNo() {
		return (String) get_Value("SerNo");
	}

	public static final int S_RESOURCE_ID_AD_Reference_ID = MReference.getReferenceID("S_Resource_Manufacturing");
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

	/** Set Yield */
	public static final String COLUMNNAME_Yield = "Yield";

	public void setYield(BigDecimal Yield) {
		if (Yield == null)
			throw new IllegalArgumentException("Yield is mandatory");
		set_Value("Yield", Yield);
	}

	/** Get Yield */
	public BigDecimal getYield() {
		BigDecimal bd = (BigDecimal) get_Value("Yield");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	public LP_C_OrderLine getC_OrderLine() throws RuntimeException {
		return (LP_C_OrderLine) M_Table.get(getCtx(), LP_C_OrderLine.Table_Name).getPO(getC_OrderLine_ID(), get_TrxName());
	}
}
