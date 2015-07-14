/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.M_Table;
import org.openXpertya.model.POInfo;
import org.openXpertya.util.Env;
import org.openXpertya.util.KeyNamePair;

/**
 * Modelo Generado por M_CostDetail
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:14.839
 */
public class LP_M_CostDetail extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_M_CostDetail(Properties ctx, int M_CostDetail_ID, String trxName) {
		super(ctx, M_CostDetail_ID, trxName);
		/**
		 * if (M_CostDetail_ID == 0) { setAmt (Env.ZERO); setC_AcctSchema_ID
		 * (0); setIsSOTrx (false); setM_AttributeSetInstance_ID (0);
		 * setM_Costdetail_ID (0); setM_Product_ID (0); setProcessed (false);
		 * setQty (Env.ZERO); }
		 */
	}

	/** Load Constructor */
	public LP_M_CostDetail(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("M_CostDetail");

	/** TableName=M_CostDetail */
	public static final String Table_Name = "M_CostDetail";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "M_CostDetail");
	protected static BigDecimal AccessLevel = new BigDecimal(4);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_CostDetail[").append(getID()).append("]");
		return sb.toString();
	}

	/**
	 * Set Amount. Amount
	 */
	public static final String COLUMNNAME_Amt = "Amt";

	public void setAmt(BigDecimal Amt) {
		if (Amt == null)
			throw new IllegalArgumentException("Amt is mandatory");
		set_Value("Amt", Amt);
	}

	/**
	 * Get Amount. Amount
	 */
	public BigDecimal getAmt() {
		BigDecimal bd = (BigDecimal) get_Value("Amt");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set amtll */
	public static final String COLUMNNAME_amtll = "amtll";

	public void setAmtLL(BigDecimal amtll) {
		set_Value("amtll", amtll);
	}

	/** Get amtll */
	public BigDecimal getAmtLL() {
		BigDecimal bd = (BigDecimal) get_Value("amtll");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Accounting Schema. Rules for accounting
	 */
	public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	public void setC_AcctSchema_ID(int C_AcctSchema_ID) {
		set_Value("C_AcctSchema_ID", new Integer(C_AcctSchema_ID));
	}

	/**
	 * Get Accounting Schema. Rules for accounting
	 */
	public int getC_AcctSchema_ID() {
		Integer ii = (Integer) get_Value("C_AcctSchema_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Invoice Line. Invoice Detail Line
	 */
	public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
		if (C_InvoiceLine_ID <= 0)
			set_Value("C_InvoiceLine_ID", null);
		else
			set_Value("C_InvoiceLine_ID", new Integer(C_InvoiceLine_ID));
	}

	/**
	 * Get Invoice Line. Invoice Detail Line
	 */
	public int getC_InvoiceLine_ID() {
		Integer ii = (Integer) get_Value("C_InvoiceLine_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}
	
	/**
	 * Set Landed Cost Allocation. 
	 */
	public static final String COLUMNNAME_C_LandedCostAllocation_ID = "C_LandedCostAllocation_ID";

	public void setC_LandedCostAllocation_ID(int C_LandedCostAllocation_ID) {
		if (C_LandedCostAllocation_ID <= 0)
			set_Value("C_LandedCostAllocation_ID", null);
		else
			set_Value("C_LandedCostAllocation_ID", new Integer(C_LandedCostAllocation_ID));
	}

	/**
	 * Get Landed Cost Allocation
	 */
	public int getC_LandedCostAllocation_ID() {
		Integer ii = (Integer) get_Value("C_LandedCostAllocation_ID");
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

	/** Set costadjustment */
	public static final String COLUMNNAME_costadjustment = "costadjustment";

	public void setCostAdjustment(BigDecimal costadjustment) {
		set_Value("costadjustment", costadjustment);
	}

	/** Get costadjustment */
	public BigDecimal getCostAdjustment() {
		BigDecimal bd = (BigDecimal) get_Value("costadjustment");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set costadjustmentdate */
	public static final String COLUMNNAME_costadjustmentdate = "costadjustmentdate";

	public void setCostAdjustmentDate(Timestamp costadjustmentdate) {
		set_Value("costadjustmentdate", costadjustmentdate);
	}

	/** Get costadjustmentdate */
	public Timestamp getCostAdjustmentDate() {
		return (Timestamp) get_Value("costadjustmentdate");
	}

	/** Set costadjustmentdatell */
	public static final String COLUMNNAME_costadjustmentdatell = "costadjustmentdatell";

	public void setCostAdjustmentDateLL(Timestamp costadjustmentdatell) {
		set_Value("costadjustmentdatell", costadjustmentdatell);
	}

	/** Get costadjustmentdatell */
	public Timestamp getCostAdjustmentDateLL() {
		return (Timestamp) get_Value("costadjustmentdatell");
	}

	/** Set costadjustmentll */
	public static final String COLUMNNAME_costadjustmentll = "costadjustmentll";

	public void setCostAdjustmentLL(BigDecimal costadjustmentll) {
		set_Value("costadjustmentll", costadjustmentll);
	}

	/** Get costadjustmentll */
	public BigDecimal getCostAdjustmentLL() {
		BigDecimal bd = (BigDecimal) get_Value("costadjustmentll");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set costamt */
	public static final String COLUMNNAME_costamt = "costamt";

	public void setCostAmt(BigDecimal costamt) {
		set_Value("costamt", costamt);
	}

	/** Get costamt */
	public BigDecimal getCostAmt() {
		BigDecimal bd = (BigDecimal) get_Value("costamt");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set costamtll */
	public static final String COLUMNNAME_costamtll = "costamtll";

	public void setCostAmtLL(BigDecimal costamtll) {
		set_Value("costamtll", costamtll);
	}

	/** Get costamtll */
	public BigDecimal getCostAmtLL() {
		BigDecimal bd = (BigDecimal) get_Value("costamtll");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Costing Method. Indicates how Costs will be calculated
	 */
	public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	public void setCostingMethod(String CostingMethod) {
		if (CostingMethod.equals("P") || CostingMethod.equals("S") || CostingMethod.equals("A") || CostingMethod.equals("L") || CostingMethod.equals("F"))
			;
		else
			throw new IllegalArgumentException("CostingMethod Invalid value - Reference = COSTINGMETHOD_AD_Reference_ID - P - S - A - L - F");
		if (CostingMethod == null)
			throw new IllegalArgumentException("CostingMethod is mandatory");
		if (CostingMethod.length() > 1) {
			log.warning("Length > 1 - truncated");
			CostingMethod = CostingMethod.substring(0, 1);
		}
		set_Value("CostingMethod", CostingMethod);
	}

	/**
	 * Get Costing Method. Indicates how Costs will be calculated
	 */
	public String getCostingMethod() {
		return (String) get_Value("CostingMethod");
	}

	/**
	 * Set Project Issue. Project Issues (Material, Labor)
	 */
	public static final String COLUMNNAME_C_ProjectIssue_ID = "C_ProjectIssue_ID";

	public void setC_ProjectIssue_ID(int C_ProjectIssue_ID) {
		if (C_ProjectIssue_ID <= 0)
			set_Value("C_ProjectIssue_ID", null);
		else
			set_Value("C_ProjectIssue_ID", new Integer(C_ProjectIssue_ID));
	}

	/**
	 * Get Project Issue. Project Issues (Material, Labor)
	 */
	public int getC_ProjectIssue_ID() {
		Integer ii = (Integer) get_Value("C_ProjectIssue_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set cumulatedamt */
	public static final String COLUMNNAME_cumulatedamt = "cumulatedamt";

	public void setCumulatedAmt(BigDecimal cumulatedamt) {
		set_Value("cumulatedamt", cumulatedamt);
	}

	/** Get cumulatedamt */
	public BigDecimal getCumulatedAmt() {
		BigDecimal bd = (BigDecimal) get_Value("cumulatedamt");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cumulatedamtll */
	public static final String COLUMNNAME_cumulatedamtll = "cumulatedamtll";

	public void setCumulatedAmtLL(BigDecimal cumulatedamtll) {
		set_Value("cumulatedamtll", cumulatedamtll);
	}

	/** Get cumulatedamtll */
	public BigDecimal getCumulatedAmtLL() {
		BigDecimal bd = (BigDecimal) get_Value("cumulatedamtll");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set cumulatedqty */
	public static final String COLUMNNAME_cumulatedqty = "cumulatedqty";

	public void setCumulatedQty(BigDecimal cumulatedqty) {
		set_Value("cumulatedqty", cumulatedqty);
	}

	/** Get cumulatedqty */
	public BigDecimal getCumulatedQty() {
		BigDecimal bd = (BigDecimal) get_Value("cumulatedqty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Current Cost Price. The currently used cost price
	 */
	public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	public void setCurrentCostPrice(BigDecimal CurrentCostPrice) {
		set_Value("CurrentCostPrice", CurrentCostPrice);
	}

	/**
	 * Get Current Cost Price. The currently used cost price
	 */
	public BigDecimal getCurrentCostPrice() {
		BigDecimal bd = (BigDecimal) get_Value("CurrentCostPrice");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set currentcostpricell */
	public static final String COLUMNNAME_currentcostpricell = "currentcostpricell";

	public void setCurrentCostPriceLL(BigDecimal currentcostpricell) {
		set_Value("currentcostpricell", currentcostpricell);
	}

	/** Get currentcostpricell */
	public BigDecimal getCurrentCostPriceLL() {
		BigDecimal bd = (BigDecimal) get_Value("currentcostpricell");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set currentqty */
	public static final String COLUMNNAME_currentqty = "currentqty";

	public void setCurrentQty(BigDecimal currentqty) {
		set_Value("currentqty", currentqty);
	}

	/** Get currentqty */
	public BigDecimal getCurrentQty() {
		BigDecimal bd = (BigDecimal) get_Value("currentqty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Account Date. Accounting Date
	 */
	public static final String COLUMNNAME_DateAcct = "DateAcct";

	public void setDateAcct(Timestamp DateAcct) {
		set_Value("DateAcct", DateAcct);
	}

	/**
	 * Get Account Date. Accounting Date
	 */
	public Timestamp getDateAcct() {
		return (Timestamp) get_Value("DateAcct");
	}

	/** Set deltaamt */
	public static final String COLUMNNAME_deltaamt = "deltaamt";

	public void setDeltaAmt(BigDecimal deltaamt) {
		set_Value("deltaamt", deltaamt);
	}

	/** Get deltaamt */
	public BigDecimal getDeltaAmt() {
		BigDecimal bd = (BigDecimal) get_Value("deltaamt");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set deltaqty */
	public static final String COLUMNNAME_deltaqty = "deltaqty";

	public void setDeltaQty(BigDecimal deltaqty) {
		set_Value("deltaqty", deltaqty);
	}

	/** Get deltaqty */
	public BigDecimal getDeltaQty() {
		BigDecimal bd = (BigDecimal) get_Value("deltaqty");
		if (bd == null)
			return Env.ZERO;
		return bd;
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
	 * Set Reversal. This is a reversing transaction
	 */
	public static final String COLUMNNAME_IsReversal = "IsReversal";

	public void setIsReversal(boolean IsReversal) {
		set_Value("IsReversal", new Boolean(IsReversal));
	}

	/**
	 * Get Reversal. This is a reversing transaction
	 */
	public boolean isReversal() {
		Object oo = get_Value("IsReversal");
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
	 * Set Attribute Set Instance. Product Attribute Set Instance
	 */
	public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
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

	/** Set M_Costdetail_ID */
	public static final String COLUMNNAME_M_Costdetail_ID = "M_Costdetail_ID";

	public void setM_Costdetail_ID(int M_Costdetail_ID) {
		set_ValueNoCheck("M_Costdetail_ID", new Integer(M_Costdetail_ID));
	}

	/** Get M_Costdetail_ID */
	public int getM_Costdetail_ID() {
		Integer ii = (Integer) get_Value("M_Costdetail_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Cost Element. Product Cost Element
	 */
	public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	public void setM_CostElement_ID(int M_CostElement_ID) {
		if (M_CostElement_ID <= 0)
			set_Value("M_CostElement_ID", null);
		else
			set_Value("M_CostElement_ID", new Integer(M_CostElement_ID));
	}

	/**
	 * Get Cost Element. Product Cost Element
	 */
	public int getM_CostElement_ID() {
		Integer ii = (Integer) get_Value("M_CostElement_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Cost Type. Type of Cost (e.g. Current, Plan, Future)
	 */
	public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	public void setM_CostType_ID(int M_CostType_ID) {
		if (M_CostType_ID <= 0)
			set_Value("M_CostType_ID", null);
		else
			set_Value("M_CostType_ID", new Integer(M_CostType_ID));
	}

	/**
	 * Get Cost Type. Type of Cost (e.g. Current, Plan, Future)
	 */
	public int getM_CostType_ID() {
		Integer ii = (Integer) get_Value("M_CostType_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Shipment/Receipt Line. Line on Shipment or Receipt document
	 */
	public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	public void setM_InOutLine_ID(int M_InOutLine_ID) {
		if (M_InOutLine_ID <= 0)
			set_Value("M_InOutLine_ID", null);
		else
			set_Value("M_InOutLine_ID", new Integer(M_InOutLine_ID));
	}

	/**
	 * Get Shipment/Receipt Line. Line on Shipment or Receipt document
	 */
	public int getM_InOutLine_ID() {
		Integer ii = (Integer) get_Value("M_InOutLine_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Phys.Inventory Line. Unique line in an Inventory document
	 */
	public static final String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	public void setM_InventoryLine_ID(int M_InventoryLine_ID) {
		if (M_InventoryLine_ID <= 0)
			set_Value("M_InventoryLine_ID", null);
		else
			set_Value("M_InventoryLine_ID", new Integer(M_InventoryLine_ID));
	}

	/**
	 * Get Phys.Inventory Line. Unique line in an Inventory document
	 */
	public int getM_InventoryLine_ID() {
		Integer ii = (Integer) get_Value("M_InventoryLine_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Move Line. Inventory Move document Line
	 */
	public static final String COLUMNNAME_M_MovementLine_ID = "M_MovementLine_ID";

	public void setM_MovementLine_ID(int M_MovementLine_ID) {
		if (M_MovementLine_ID <= 0)
			set_Value("M_MovementLine_ID", null);
		else
			set_Value("M_MovementLine_ID", new Integer(M_MovementLine_ID));
	}

	/**
	 * Get Move Line. Inventory Move document Line
	 */
	public int getM_MovementLine_ID() {
		Integer ii = (Integer) get_Value("M_MovementLine_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
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
	 * Set Production Line. Document Line representing a production
	 */
	public static final String COLUMNNAME_M_ProductionLine_ID = "M_ProductionLine_ID";

	public void setM_ProductionLine_ID(int M_ProductionLine_ID) {
		if (M_ProductionLine_ID <= 0)
			set_Value("M_ProductionLine_ID", null);
		else
			set_Value("M_ProductionLine_ID", new Integer(M_ProductionLine_ID));
	}

	/**
	 * Get Production Line. Document Line representing a production
	 */
	public int getM_ProductionLine_ID() {
		Integer ii = (Integer) get_Value("M_ProductionLine_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Inventory Transaction */
	public static final String COLUMNNAME_M_Transaction_ID = "M_Transaction_ID";

	public void setM_Transaction_ID(int M_Transaction_ID) {
		if (M_Transaction_ID <= 0)
			set_Value("M_Transaction_ID", null);
		else
			set_Value("M_Transaction_ID", new Integer(M_Transaction_ID));
	}

	/** Get Inventory Transaction */
	public int getM_Transaction_ID() {
		Integer ii = (Integer) get_Value("M_Transaction_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Warehouse. Storage Warehouse and Service Point
	 */
	public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	public void setM_Warehouse_ID(int M_Warehouse_ID) {
		if (M_Warehouse_ID <= 0)
			set_Value("M_Warehouse_ID", null);
		else
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

	/** Set PP_Cost_Collector_ID */
	public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	public void setPP_Cost_Collector_ID(int PP_Cost_Collector_ID) {
		if (PP_Cost_Collector_ID <= 0)
			set_Value("PP_Cost_Collector_ID", null);
		else
			set_Value("PP_Cost_Collector_ID", new Integer(PP_Cost_Collector_ID));
	}

	/** Get PP_Cost_Collector_ID */
	public int getPP_Cost_Collector_ID() {
		Integer ii = (Integer) get_Value("PP_Cost_Collector_ID");
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

	/**
	 * Set Quantity. Quantity
	 */
	public static final String COLUMNNAME_Qty = "Qty";

	public void setQty(BigDecimal Qty) {
		if (Qty == null)
			throw new IllegalArgumentException("Qty is mandatory");
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
	 * Set Sequence. Method of ordering records; lowest number comes first
	 */
	public static final String COLUMNNAME_SeqNo = "SeqNo";

	public void setSeqNo(int SeqNo) {
		set_Value("SeqNo", new Integer(SeqNo));
	}

	/**
	 * Get Sequence. Method of ordering records; lowest number comes first
	 */
	public int getSeqNo() {
		Integer ii = (Integer) get_Value("SeqNo");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Price.
	 * 
	 * @param Price
	 *            Price
	 */
	public void setPrice(BigDecimal Price) {
		throw new IllegalArgumentException("Price is virtual column");
	}

	/**
	 * Get Price.
	 * 
	 * @return Price
	 */
	
	public static final String COLUMNNAME_Price = "Price";
	
	public BigDecimal getPrice() {
		BigDecimal bd = (BigDecimal) get_Value(COLUMNNAME_Price);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}
}
