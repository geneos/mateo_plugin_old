package ar.com.geneos.mrp.plugin.model;

/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import org.openXpertya.model.*;
import java.util.logging.Level;
import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;

/**
 * Modelo Generado por PP_Product_Planning
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-04-29 14:39:08.664
 */
public class LP_PP_Product_Planning extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_PP_Product_Planning(Properties ctx, int PP_Product_Planning_ID, String trxName) {
		super(ctx, PP_Product_Planning_ID, trxName);
		/**
		 * if (PP_Product_Planning_ID == 0) { setIsCreatePlan (false);
		 * setIsIssue (false); setIsPhantom (false); setIsRequiredDRP (false);
		 * setIsRequiredMRP (false); setM_Product_ID (0);
		 * setPP_Product_Planning_ID (0); }
		 */
	}

	/** Load Constructor */
	public LP_PP_Product_Planning(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("PP_Product_Planning");

	/** TableName=PP_Product_Planning */
	public static final String Table_Name = "PP_Product_Planning";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "PP_Product_Planning");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_PP_Product_Planning[").append(getID()).append("]");
		return sb.toString();
	}

	public static final int AD_WORKFLOW_ID_AD_Reference_ID = MReference.getReferenceID("AD_Workflow");
	/**
	 * Set Workflow. Workflow or combination of tasks
	 */
	public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	public void setAD_Workflow_ID(int AD_Workflow_ID) {
		if (AD_Workflow_ID <= 0)
			set_Value("AD_Workflow_ID", null);
		else
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

	/** Set DD_Networkdistribution_ID */
	public static final String COLUMNNAME_DD_Networkdistribution_ID = "DD_Networkdistribution_ID";

	public void setDD_Networkdistribution_ID(int DD_Networkdistribution_ID) {
		if (DD_Networkdistribution_ID <= 0)
			set_Value("DD_Networkdistribution_ID", null);
		else
			set_Value("DD_Networkdistribution_ID", new Integer(DD_Networkdistribution_ID));
	}

	/** Get DD_Networkdistribution_ID */
	public int getDD_Networkdistribution_ID() {
		Integer ii = (Integer) get_Value("DD_Networkdistribution_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Promised Delivery Time. Promised days between order and delivery
	 */
	public static final String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

	public void setDeliveryTime_Promised(BigDecimal DeliveryTime_Promised) {
		set_Value("DeliveryTime_Promised", DeliveryTime_Promised);
	}

	/**
	 * Get Promised Delivery Time. Promised days between order and delivery
	 */
	public BigDecimal getDeliveryTime_Promised() {
		BigDecimal bd = (BigDecimal) get_Value("DeliveryTime_Promised");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Is Create Plan */
	public static final String COLUMNNAME_IsCreatePlan = "IsCreatePlan";

	public void setIsCreatePlan(boolean IsCreatePlan) {
		set_Value("IsCreatePlan", new Boolean(IsCreatePlan));
	}

	/** Get Is Create Plan */
	public boolean isCreatePlan() {
		Object oo = get_Value("IsCreatePlan");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Issue */
	public static final String COLUMNNAME_IsIssue = "IsIssue";

	public void setIsIssue(boolean IsIssue) {
		set_Value("IsIssue", new Boolean(IsIssue));
	}

	/** Get Is Issue */
	public boolean isIssue() {
		Object oo = get_Value("IsIssue");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Master Production Schedule */
	public static final String COLUMNNAME_IsMPS = "IsMPS";

	public void setIsMPS(boolean IsMPS) {
		set_Value("IsMPS", new Boolean(IsMPS));
	}

	/** Get Is Master Production Schedule */
	public boolean isMPS() {
		Object oo = get_Value("IsMPS");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsPhantom */
	public static final String COLUMNNAME_IsPhantom = "IsPhantom";

	public void setIsPhantom(boolean IsPhantom) {
		set_Value("IsPhantom", new Boolean(IsPhantom));
	}

	/** Get IsPhantom */
	public boolean isPhantom() {
		Object oo = get_Value("IsPhantom");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsRequiredDRP */
	public static final String COLUMNNAME_IsRequiredDRP = "IsRequiredDRP";

	public void setIsRequiredDRP(boolean IsRequiredDRP) {
		set_Value("IsRequiredDRP", new Boolean(IsRequiredDRP));
	}

	/** Get IsRequiredDRP */
	public boolean isRequiredDRP() {
		Object oo = get_Value("IsRequiredDRP");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Required MRP */
	public static final String COLUMNNAME_IsRequiredMRP = "IsRequiredMRP";

	public void setIsRequiredMRP(boolean IsRequiredMRP) {
		set_Value("IsRequiredMRP", new Boolean(IsRequiredMRP));
	}

	/** Get Is Required MRP */
	public boolean isRequiredMRP() {
		Object oo = get_Value("IsRequiredMRP");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Order Max */
	public static final String COLUMNNAME_Order_Max = "Order_Max";

	public void setOrder_Max(BigDecimal Order_Max) {
		set_Value("Order_Max", Order_Max);
	}

	/** Get Order Max */
	public BigDecimal getOrder_Max() {
		BigDecimal bd = (BigDecimal) get_Value("Order_Max");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Minimum Order Qty. Minimum order quantity in UOM
	 */
	public static final String COLUMNNAME_Order_Min = "Order_Min";

	public void setOrder_Min(BigDecimal Order_Min) {
		set_Value("Order_Min", Order_Min);
	}

	/**
	 * Get Minimum Order Qty. Minimum order quantity in UOM
	 */
	public BigDecimal getOrder_Min() {
		BigDecimal bd = (BigDecimal) get_Value("Order_Min");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Order Pack Qty. Package order size in UOM (e.g. order set of 5 units)
	 */
	public static final String COLUMNNAME_Order_Pack = "Order_Pack";

	public void setOrder_Pack(BigDecimal Order_Pack) {
		set_Value("Order_Pack", Order_Pack);
	}

	/**
	 * Get Order Pack Qty. Package order size in UOM (e.g. order set of 5 units)
	 */
	public BigDecimal getOrder_Pack() {
		BigDecimal bd = (BigDecimal) get_Value("Order_Pack");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set OrderPeriod */
	public static final String COLUMNNAME_Order_Period = "Order_Period";

	public void setOrder_Period(BigDecimal Order_Period) {
		set_Value("Order_Period", Order_Period);
	}

	/** Get OrderPeriod */
	public BigDecimal getOrder_Period() {
		BigDecimal bd = (BigDecimal) get_Value("Order_Period");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	public static final int ORDER_POLICY_AD_Reference_ID = MReference.getReferenceID("PP_Product_Planning Order Policy");
	/** Fixed Order Quantity = FOQ */
	public static final String ORDER_POLICY_FixedOrderQuantity = "FOQ";
	/** Lot-for-Lot = LFL */
	public static final String ORDER_POLICY_Lot_For_Lot = "LFL";
	/** Period Order Quantity = POQ */
	public static final String ORDER_POLICY_PeriodOrderQuantity = "POQ";
	/** Set Order Policy */
	public static final String COLUMNNAME_Order_Policy = "Order_Policy";

	public void setOrder_Policy(String Order_Policy) {
		if (Order_Policy == null || Order_Policy.equals("FOQ") || Order_Policy.equals("LFL") || Order_Policy.equals("POQ"))
			;
		else
			throw new IllegalArgumentException("Order_Policy Invalid value - Reference = ORDER_POLICY_AD_Reference_ID - FOQ - LFL - POQ");
		if (Order_Policy != null && Order_Policy.length() > 3) {
			log.warning("Length > 3 - truncated");
			Order_Policy = Order_Policy.substring(0, 3);
		}
		set_Value("Order_Policy", Order_Policy);
	}

	/** Get Order Policy */
	public String getOrder_Policy() {
		return (String) get_Value("Order_Policy");
	}

	/** Set Order Qty */
	public static final String COLUMNNAME_Order_Qty = "Order_Qty";

	public void setOrder_Qty(BigDecimal Order_Qty) {
		set_Value("Order_Qty", Order_Qty);
	}

	/** Get Order Qty */
	public BigDecimal getOrder_Qty() {
		BigDecimal bd = (BigDecimal) get_Value("Order_Qty");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	public static final int PLANNER_ID_AD_Reference_ID = MReference.getReferenceID("AD_User");
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

	/** Set PP_Product_BOM_ID */
	public static final String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	public void setPP_Product_BOM_ID(int PP_Product_BOM_ID) {
		if (PP_Product_BOM_ID <= 0)
			set_Value("PP_Product_BOM_ID", null);
		else
			set_Value("PP_Product_BOM_ID", new Integer(PP_Product_BOM_ID));
	}

	/** Get PP_Product_BOM_ID */
	public int getPP_Product_BOM_ID() {
		Integer ii = (Integer) get_Value("PP_Product_BOM_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Product_Planning_ID */
	public static final String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

	public void setPP_Product_Planning_ID(int PP_Product_Planning_ID) {
		set_ValueNoCheck("PP_Product_Planning_ID", new Integer(PP_Product_Planning_ID));
	}

	/** Get PP_Product_Planning_ID */
	public int getPP_Product_Planning_ID() {
		Integer ii = (Integer) get_Value("PP_Product_Planning_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set SafetyStock */
	public static final String COLUMNNAME_SafetyStock = "SafetyStock";

	public void setSafetyStock(BigDecimal SafetyStock) {
		set_Value("SafetyStock", SafetyStock);
	}

	/** Get SafetyStock */
	public BigDecimal getSafetyStock() {
		BigDecimal bd = (BigDecimal) get_Value("SafetyStock");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	public static final int S_RESOURCE_ID_AD_Reference_ID = MReference.getReferenceID("S_Resource_Manufacturing");
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

	/** Set TimeFence */
	public static final String COLUMNNAME_TimeFence = "TimeFence";

	public void setTimeFence(BigDecimal TimeFence) {
		set_Value("TimeFence", TimeFence);
	}

	/** Get TimeFence */
	public BigDecimal getTimeFence() {
		BigDecimal bd = (BigDecimal) get_Value("TimeFence");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set TransfertTime */
	public static final String COLUMNNAME_TransfertTime = "TransfertTime";

	public void setTransfertTime(BigDecimal TransfertTime) {
		set_Value("TransfertTime", TransfertTime);
	}

	/** Get TransfertTime */
	public BigDecimal getTransfertTime() {
		BigDecimal bd = (BigDecimal) get_Value("TransfertTime");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Working Time. Workflow Simulation Execution Time
	 */
	public static final String COLUMNNAME_WorkingTime = "WorkingTime";

	public void setWorkingTime(BigDecimal WorkingTime) {
		set_Value("WorkingTime", WorkingTime);
	}

	/**
	 * Get Working Time. Workflow Simulation Execution Time
	 */
	public BigDecimal getWorkingTime() {
		BigDecimal bd = (BigDecimal) get_Value("WorkingTime");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Yield */
	public static final String COLUMNNAME_Yield = "Yield";

	public void setYield(int Yield) {
		set_Value("Yield", new Integer(Yield));
	}

	/** Get Yield */
	public int getYield() {
		Integer ii = (Integer) get_Value("Yield");
		if (ii == null)
			return 0;
		return ii.intValue();
	}
}
