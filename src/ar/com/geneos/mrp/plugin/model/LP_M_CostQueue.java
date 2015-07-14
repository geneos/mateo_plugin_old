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
 * Modelo Generado por M_CostQueue
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:14.865
 */
public class LP_M_CostQueue extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_M_CostQueue(Properties ctx, int M_CostQueue_ID, String trxName) {
		super(ctx, M_CostQueue_ID, trxName);
		/**
		 * if (M_CostQueue_ID == 0) { setC_AcctSchema_ID (0);
		 * setCurrentCostPrice (Env.ZERO); setcurrentqty (Env.ZERO);
		 * setM_AttributeSetInstance_ID (0); setM_CostElement_ID (0);
		 * setM_Costqueue_ID (0); setM_CostType_ID (0); setM_Product_ID (0); }
		 */
	}

	/** Load Constructor */
	public LP_M_CostQueue(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("M_CostQueue");

	/** TableName=M_CostQueue */
	public static final String Table_Name = "M_CostQueue";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "M_CostQueue");
	protected static BigDecimal AccessLevel = new BigDecimal(4);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_CostQueue[").append(getID()).append("]");
		return sb.toString();
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
	 * Set Current Cost Price. The currently used cost price
	 */
	public static final String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	public void setCurrentCostPrice(BigDecimal CurrentCostPrice) {
		if (CurrentCostPrice == null)
			throw new IllegalArgumentException("CurrentCostPrice is mandatory");
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

	/** Set currentqty */
	public static final String COLUMNNAME_currentqty = "currentqty";

	public void setCurrentQty(BigDecimal currentqty) {
		if (currentqty == null)
			throw new IllegalArgumentException("currentqty is mandatory");
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

	/**
	 * Set Cost Element. Product Cost Element
	 */
	public static final String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	public void setM_CostElement_ID(int M_CostElement_ID) {
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

	/** Set M_Costqueue_ID */
	public static final String COLUMNNAME_M_Costqueue_ID = "M_Costqueue_ID";

	public void setM_Costqueue_ID(int M_Costqueue_ID) {
		set_ValueNoCheck("M_Costqueue_ID", new Integer(M_Costqueue_ID));
	}

	/** Get M_Costqueue_ID */
	public int getM_Costqueue_ID() {
		Integer ii = (Integer) get_Value("M_Costqueue_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Cost Type. Type of Cost (e.g. Current, Plan, Future)
	 */
	public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	public void setM_CostType_ID(int M_CostType_ID) {
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
}
