package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MConversionRate;
import org.openXpertya.model.MInOutLine;
import org.openXpertya.model.MInvoiceLine;
import org.openXpertya.model.MMatchPO;

public class LP_M_MatchPO extends MMatchPO implements IDocumentLine {

	public LP_M_MatchPO(MInOutLine sLine, Timestamp dateTrx, BigDecimal qty) {
		super(sLine, dateTrx, qty);
		// TODO Auto-generated constructor stub
	}

	public LP_M_MatchPO(MInvoiceLine iLine, Timestamp dateTrx, BigDecimal qty) {
		super(iLine, dateTrx, qty);
		// TODO Auto-generated constructor stub
	}

	public LP_M_MatchPO(Properties ctx, int M_MatchPO_ID, String trxName) {
		super(ctx, M_MatchPO_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public LP_M_MatchPO(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int get_ID() {
		return getID();
	}

	@Override
	public int getM_AttributeSetInstanceTo_ID() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getM_LocatorTo_ID() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getC_DocType_ID() {
		return -1;
	}

	@Override
	public boolean isSOTrx() {
		return false;
	}

	@Override
	public BigDecimal getPriceActual() {
		LP_C_OrderLine ol = new LP_C_OrderLine(getCtx(), getC_OrderLine_ID(), get_TrxName());
		return MConversionRate.convertBase(getCtx(), ol.getPriceActual(), ol.getParent().getC_Currency_ID(), ol.getParent().getDateAcct(), ol.getParent()
				.getC_ConversionType_ID(), getAD_Client_ID(), getAD_Org_ID());
	}

	@Override
	public int getM_Locator_ID() {
		return -1;
	}

	@Override
	public BigDecimal getMovementQty() {
		return getQty();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setM_Locator_ID(int M_Locator_ID) {
		;
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

}
