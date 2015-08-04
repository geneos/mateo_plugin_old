/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import org.openXpertya.model.*;

import java.util.logging.Level;
import java.util.*;
import java.sql.*;
import java.math.*;

import org.openXpertya.util.*;

/**
 * Modelo Generado por PP_MRP
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-05-19 07:07:58.877
 */
public class LP_PP_MRP extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_PP_MRP(Properties ctx, int PP_MRP_ID, String trxName) {
		super(ctx, PP_MRP_ID, trxName);
		/**
		 * if (PP_MRP_ID == 0) { setDateOrdered (new
		 * Timestamp(System.currentTimeMillis())); setDatePromised (new
		 * Timestamp(System.currentTimeMillis())); setM_Warehouse_ID (0);
		 * setPP_Mrp_ID (0); setValue (null); }
		 */
	}

	/** Load Constructor */
	public LP_PP_MRP(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("PP_MRP");

	/** TableName=PP_MRP */
	public static final String Table_Name = "PP_MRP";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "PP_MRP");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_PP_MRP[").append(getID()).append("]");
		return sb.toString();
	}

	/**
	 * Set Business Partner . Identifies a Business Partner
	 */
	public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	public void setC_BPartner_ID(int C_BPartner_ID) {
		if (C_BPartner_ID <= 0)
			set_Value("C_BPartner_ID", null);
		else
			set_Value("C_BPartner_ID", new Integer(C_BPartner_ID));
	}

	/**
	 * Get Business Partner . Identifies a Business Partner
	 */
	public int getC_BPartner_ID() {
		Integer ii = (Integer) get_Value("C_BPartner_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Order. Order
	 */
	public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	public void setC_Order_ID(int C_Order_ID) {
		if (C_Order_ID <= 0)
			set_Value("C_Order_ID", null);
		else
			set_Value("C_Order_ID", new Integer(C_Order_ID));
	}

	/**
	 * Get Order. Order
	 */
	public int getC_Order_ID() {
		Integer ii = (Integer) get_Value("C_Order_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
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

	/** Set Date Simulation */
	public static final String COLUMNNAME_DateSimulation = "DateSimulation";

	public void setDateSimulation(Timestamp DateSimulation) {
		set_Value("DateSimulation", DateSimulation);
	}

	/** Get Date Simulation */
	public Timestamp getDateSimulation() {
		return (Timestamp) get_Value("DateSimulation");
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
		set_Value("DateStartSchedule", DateStartSchedule);
	}

	/**
	 * Get Starting Date Scheduled. Starting Date Scheduled
	 */
	public Timestamp getDateStartSchedule() {
		return (Timestamp) get_Value("DateStartSchedule");
	}

	/** Set DD_Order_ID */
	public static final String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	public void setDD_Order_ID(int DD_Order_ID) {
		if (DD_Order_ID <= 0)
			set_Value("DD_Order_ID", null);
		else
			set_Value("DD_Order_ID", new Integer(DD_Order_ID));
	}

	/** Get DD_Order_ID */
	public int getDD_Order_ID() {
		Integer ii = (Integer) get_Value("DD_Order_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set DD_Orderline_ID */
	public static final String COLUMNNAME_DD_Orderline_ID = "DD_Orderline_ID";

	public void setDD_Orderline_ID(int DD_Orderline_ID) {
		if (DD_Orderline_ID <= 0)
			set_Value("DD_Orderline_ID", null);
		else
			set_Value("DD_Orderline_ID", new Integer(DD_Orderline_ID));
	}

	/** Get DD_Orderline_ID */
	public int getDD_Orderline_ID() {
		Integer ii = (Integer) get_Value("DD_Orderline_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Description. Optional short description of the record
	 */
	public static final String COLUMNNAME_Description = "Description";

	public void setDescription(String Description) {
		if (Description != null && Description.length() > 1020) {
			log.warning("Length > 1020 - truncated");
			Description = Description.substring(0, 1020);
		}
		set_Value("Description", Description);
	}

	/**
	 * Get Description. Optional short description of the record
	 */
	public String getDescription() {
		return (String) get_Value("Description");
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
		if (DocStatus == null || DocStatus.equals("VO") || DocStatus.equals("NA") || DocStatus.equals("IP") || DocStatus.equals("CO") || DocStatus.equals("AP")
				|| DocStatus.equals("CL") || DocStatus.equals("WC") || DocStatus.equals("WP") || DocStatus.equals("??") || DocStatus.equals("DR")
				|| DocStatus.equals("IN") || DocStatus.equals("RE"))
			;
		else
			throw new IllegalArgumentException(
					"DocStatus Invalid value - Reference = DOCSTATUS_AD_Reference_ID - VO - NA - IP - CO - AP - CL - WC - WP - ?? - DR - IN - RE");
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
	 * Set Available. Resource is available
	 */
	public static final String COLUMNNAME_IsAvailable = "IsAvailable";

	public void setIsAvailable(boolean IsAvailable) {
		set_Value("IsAvailable", new Boolean(IsAvailable));
	}

	/**
	 * Get Available. Resource is available
	 */
	public boolean isAvailable() {
		Object oo = get_Value("IsAvailable");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Forecast. Material Forecast
	 */
	public static final String COLUMNNAME_M_Forecast_ID = "M_Forecast_ID";

	public void setM_Forecast_ID(int M_Forecast_ID) {
		if (M_Forecast_ID <= 0)
			set_Value("M_Forecast_ID", null);
		else
			set_Value("M_Forecast_ID", new Integer(M_Forecast_ID));
	}

	/**
	 * Get Forecast. Material Forecast
	 */
	public int getM_Forecast_ID() {
		Integer ii = (Integer) get_Value("M_Forecast_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Forecast Line. Forecast Line
	 */
	public static final String COLUMNNAME_M_ForecastLine_ID = "M_ForecastLine_ID";

	public void setM_ForecastLine_ID(int M_ForecastLine_ID) {
		if (M_ForecastLine_ID <= 0)
			set_Value("M_ForecastLine_ID", null);
		else
			set_Value("M_ForecastLine_ID", new Integer(M_ForecastLine_ID));
	}

	/**
	 * Get Forecast Line. Forecast Line
	 */
	public int getM_ForecastLine_ID() {
		Integer ii = (Integer) get_Value("M_ForecastLine_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Product. Product, Service, Item
	 */
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	public void setM_Product_ID(int M_Product_ID) {
		if (M_Product_ID <= 0)
			set_Value("M_Product_ID", null);
		else
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
	 * Set Requisition. Material Requisition
	 */
	public static final String COLUMNNAME_M_Requisition_ID = "M_Requisition_ID";

	public void setM_Requisition_ID(int M_Requisition_ID) {
		if (M_Requisition_ID <= 0)
			set_Value("M_Requisition_ID", null);
		else
			set_Value("M_Requisition_ID", new Integer(M_Requisition_ID));
	}

	/**
	 * Get Requisition. Material Requisition
	 */
	public int getM_Requisition_ID() {
		Integer ii = (Integer) get_Value("M_Requisition_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Requisition Line. Material Requisition Line
	 */
	public static final String COLUMNNAME_M_RequisitionLine_ID = "M_RequisitionLine_ID";

	public void setM_RequisitionLine_ID(int M_RequisitionLine_ID) {
		if (M_RequisitionLine_ID <= 0)
			set_Value("M_RequisitionLine_ID", null);
		else
			set_Value("M_RequisitionLine_ID", new Integer(M_RequisitionLine_ID));
	}

	/**
	 * Get Requisition Line. Material Requisition Line
	 */
	public int getM_RequisitionLine_ID() {
		Integer ii = (Integer) get_Value("M_RequisitionLine_ID");
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
	 * Set Name. Alphanumeric identifier of the entity
	 */
	public static final String COLUMNNAME_Name = "Name";

	public void setName(String Name) {
		if (Name != null && Name.length() > 120) {
			log.warning("Length > 120 - truncated");
			Name = Name.substring(0, 120);
		}
		set_Value("Name", Name);
	}

	/**
	 * Get Name. Alphanumeric identifier of the entity
	 */
	public String getName() {
		return (String) get_Value("Name");
	}

	public KeyNamePair getKeyNamePair() {
		return new KeyNamePair(getID(), getName());
	}

	public static final int ORDERTYPE_AD_Reference_ID = MReference.getReferenceID("_MRP Order Type");
	/** Distribution Order = DOO */
	public static final String ORDERTYPE_DistributionOrder = "DOO";
	/** Forecast = FCT */
	public static final String ORDERTYPE_Forecast = "FCT";
	/** Manufacturing Order = MOP */
	public static final String ORDERTYPE_ManufacturingOrder = "MOP";
	/** Purchase Order = POO */
	public static final String ORDERTYPE_PurchaseOrder = "POO";
	/** Material Requisition = POR */
	public static final String ORDERTYPE_MaterialRequisition = "POR";
	/** Sales Order = SOO */
	public static final String ORDERTYPE_SalesOrder = "SOO";
	/** Safety Stock = STK */
	public static final String ORDERTYPE_SafetyStock = "STK";
	/**
	 * Set Order Type. Order type: discrete, reprocessing, expenses, repetitive
	 */
	public static final String COLUMNNAME_OrderType = "OrderType";

	public void setOrderType(String OrderType) {
		if (OrderType == null || OrderType.equals("DOO") || OrderType.equals("FCT") || OrderType.equals("MOP") || OrderType.equals("POO")
				|| OrderType.equals("POR") || OrderType.equals("SOO") || OrderType.equals("STK"))
			;
		else
			throw new IllegalArgumentException("OrderType Invalid value - Reference = ORDERTYPE_AD_Reference_ID - DOO - FCT - MOP - POO - POR - SOO - STK");
		if (OrderType != null && OrderType.length() > 3) {
			log.warning("Length > 3 - truncated");
			OrderType = OrderType.substring(0, 3);
		}
		set_Value("OrderType", OrderType);
	}

	/**
	 * Get Order Type. Order type: discrete, reprocessing, expenses, repetitive
	 */
	public String getOrderType() {
		return (String) get_Value("OrderType");
	}

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

	/** Set PP_MRP_ID */
	public static final String COLUMNNAME_PP_Mrp_ID = "PP_MRP_ID";

	public void setPP_MRP_ID(int PP_Mrp_ID) {
		set_ValueNoCheck("PP_MRP_ID", new Integer(PP_Mrp_ID));
	}

	/** Get PP_MRP_ID */
	public int getPP_MRP_ID() {
		Integer ii = (Integer) get_Value("PP_MRP_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_Bomline_ID */
	public static final String COLUMNNAME_PP_Order_Bomline_ID = "PP_Order_Bomline_ID";

	public void setPP_Order_Bomline_ID(int PP_Order_Bomline_ID) {
		if (PP_Order_Bomline_ID <= 0)
			set_Value("PP_Order_Bomline_ID", null);
		else
			set_Value("PP_Order_Bomline_ID", new Integer(PP_Order_Bomline_ID));
	}

	/** Get PP_Order_Bomline_ID */
	public int getPP_Order_Bomline_ID() {
		Integer ii = (Integer) get_Value("PP_Order_Bomline_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_ID */
	public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	public void setPP_Order_ID(int PP_Order_ID) {
		if (PP_Order_ID <= 0)
			set_Value("PP_Order_ID", null);
		else
			set_Value("PP_Order_ID", new Integer(PP_Order_ID));
	}

	/** Get PP_Order_ID */
	public int getPP_Order_ID() {
		Integer ii = (Integer) get_Value("PP_Order_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Priority. Indicates if this request is of a high, medium or low
	 * priority.
	 */
	public static final String COLUMNNAME_Priority = "Priority";

	public void setPriority(String Priority) {
		if (Priority != null && Priority.length() > 10) {
			log.warning("Length > 10 - truncated");
			Priority = Priority.substring(0, 10);
		}
		set_Value("Priority", Priority);
	}

	/**
	 * Get Priority. Indicates if this request is of a high, medium or low
	 * priority.
	 */
	public String getPriority() {
		return (String) get_Value("Priority");
	}

	/**
	 * Set Quantity. Quantity
	 */
	public static final String COLUMNNAME_Qty = "Qty";

	public void setQty(BigDecimal Qty) {
		set_Value("Qty", Qty);
	}

	/**
	 * Get Quantity. Quantity
	 */
	public BigDecimal getQty() {
		BigDecimal bd = (BigDecimal) get_Value("Qty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Resource. Resource
	 */
	public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	public void setS_Resource_ID(int S_Resource_ID) {
		if (S_Resource_ID <= 0)
			set_Value("S_Resource_ID", null);
		else
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

	public static final int TYPEMRP_AD_Reference_ID = MReference.getReferenceID("_MRP Type");
	/** Demand = D */
	public static final String TYPEMRP_Demand = "D";
	/** Supply = S */
	public static final String TYPEMRP_Supply = "S";
	/** Set TypeMRP */
	public static final String COLUMNNAME_TypeMRP = "TypeMRP";

	public void setTypeMRP(String TypeMRP) {
		if (TypeMRP == null || TypeMRP.equals("D") || TypeMRP.equals("S"))
			;
		else
			throw new IllegalArgumentException("TypeMRP Invalid value - Reference = TYPEMRP_AD_Reference_ID - D - S");
		if (TypeMRP != null && TypeMRP.length() > 1) {
			log.warning("Length > 1 - truncated");
			TypeMRP = TypeMRP.substring(0, 1);
		}
		set_Value("TypeMRP", TypeMRP);
	}

	/** Get TypeMRP */
	public String getTypeMRP() {
		return (String) get_Value("TypeMRP");
	}

	/**
	 * Set Search Key. Search key for the record in the format required - must
	 * be unique
	 */
	public static final String COLUMNNAME_Value = "Value";

	public void setValue(String Value) {
		if (Value == null)
			throw new IllegalArgumentException("Value is mandatory");
		if (Value.length() > 80) {
			log.warning("Length > 80 - truncated");
			Value = Value.substring(0, 80);
		}
		set_Value("Value", Value);
	}

	/**
	 * Get Search Key. Search key for the record in the format required - must
	 * be unique
	 */
	public String getValue() {
		return (String) get_Value("Value");
	}

	/**
	 * Set Version. Version of the table definition
	 */
	public static final String COLUMNNAME_Version = "Version";

	public void setVersion(BigDecimal Version) {
		set_Value("Version", Version);
	}

	/**
	 * Get Version. Version of the table definition
	 */
	public BigDecimal getVersion() {
		BigDecimal bd = (BigDecimal) get_Value("Version");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Column name C_Project_ID */
	public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project.
	 * 
	 * @param C_Project_ID
	 *            Financial Project
	 */
	public void setC_Project_ID(int C_Project_ID) {
		if (C_Project_ID < 1)
			set_Value(COLUMNNAME_C_Project_ID, null);
		else
			set_Value(COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/**
	 * Get Project.
	 * 
	 * @return Financial Project
	 */
	public int getC_Project_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}
}
