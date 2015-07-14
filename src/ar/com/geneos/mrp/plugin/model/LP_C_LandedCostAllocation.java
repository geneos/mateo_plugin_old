package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MInOutLine;
import org.openXpertya.model.MLandedCostAllocation;
import org.openXpertya.model.Query;
import org.openXpertya.util.Env;

public class LP_C_LandedCostAllocation extends MLandedCostAllocation implements IDocumentLine {

	/** Constructor estándar */
	public LP_C_LandedCostAllocation(Properties ctx, int C_LandedCostAllocation_ID, String trxName) {
		super(ctx, C_LandedCostAllocation_ID, trxName);
		/**
		 * if (C_LandedCostAllocation_ID == 0) { setbase (Env.ZERO);
		 * setC_InvoiceLine_ID (0); setM_CostElement_ID (0); }
		 */
	}

	/** Load Constructor */
	public LP_C_LandedCostAllocation(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_C_LandedCostAllocation[").append(getID()).append("]");
		return sb.toString();
	}

	/** Set base */
	public static final String COLUMNNAME_base = "base";

	public void setbase(BigDecimal base) {
		if (base == null)
			throw new IllegalArgumentException("base is mandatory");
		set_Value("base", base);
	}

	/** Get base */
	public BigDecimal getbase() {
		BigDecimal bd = (BigDecimal) get_Value("base");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Invoice Line. Invoice Detail Line
	 */
	public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	public void setC_InvoiceLine_ID(int C_InvoiceLine_ID) {
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

	@Override
	public int get_ID() {
		return getID();
	}

	@Override
	public int getC_DocType_ID() {
		return -1;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public int getM_Locator_ID() {
		return 0;
	}

	@Override
	public void setM_Locator_ID(int M_Locator_ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getMovementQty() {
		return this.getQty();
	}

	@Override
	public boolean isSOTrx() {
		return false;
	}

	@Override
	public int getM_AttributeSetInstanceTo_ID() {
		return -1;
	}

	@Override
	public int getM_LocatorTo_ID() {
		return -1;
	}

	@Override
	public BigDecimal getPriceActual() {
		final String where = "EXISTS (SELECT 1 FROM C_Invoice i INNER JOIN C_InvoiceLine il ON (i.C_Invoice_ID=il.C_Invoice_ID) WHERE C_Currency.C_Currency_ID=i.C_Currency_ID AND il.C_InvoiceLine_ID=?)";
		org.openXpertya.model.MCurrency currency = new Query(getCtx(), org.openXpertya.model.MCurrency.Table_Name, where, get_TrxName()).setParameters(
				getC_InvoiceLine_ID()).firstOnly();
		BigDecimal price = getAmt().divide(getQty(), currency.getCostingPrecision(), RoundingMode.HALF_UP);
		return price;
	}

	public Timestamp getDateAcct() {
		return new LP_M_InOutLine(getCtx(), getM_InOutLine_ID(), get_TrxName()).getDateAcct();
	}

}
