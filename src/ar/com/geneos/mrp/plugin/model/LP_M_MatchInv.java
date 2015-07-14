package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MInOutLine;
import org.openXpertya.model.MInvoiceLine;
import org.openXpertya.model.MMatchInv;
import org.openXpertya.model.MMatchPO;

public class LP_M_MatchInv extends MMatchInv implements IDocumentLine {

	public LP_M_MatchInv(MInvoiceLine iLine, Timestamp dateTrx, BigDecimal qty) {
		super(iLine, dateTrx, qty);
		// TODO Auto-generated constructor stub
	}

	public LP_M_MatchInv(Properties ctx, int M_MatchPO_ID, String trxName) {
		super(ctx, M_MatchPO_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public LP_M_MatchInv(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int get_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_DocType_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getM_Locator_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getM_LocatorTo_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setM_Locator_ID(int M_Locator_ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getM_AttributeSetInstance_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getM_AttributeSetInstanceTo_ID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getMovementQty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSOTrx() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BigDecimal getPriceActual() {
		// TODO Auto-generated method stub
		return null;
	}

}
