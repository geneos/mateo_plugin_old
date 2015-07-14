/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MInOut;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.util.MUDB;

/**
 * Modelo Generado por C_OrderLine
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:13.306
 */
public class LP_M_InOutLine extends org.openXpertya.model.MInOutLine implements IDocumentLine {

	public LP_M_InOutLine(MInOut inout) {
		super(inout);
		// TODO Auto-generated constructor stub
	}

	public LP_M_InOutLine(Properties ctx, int M_InOutLine_ID, String trxName) {
		super(ctx, M_InOutLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/** Load Constructor */
	public LP_M_InOutLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	private MInOut m_parent = null;

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_InOutLine[").append(getID()).append("]");
		return sb.toString();
	}

	public MInOut getParent() {
		if (m_parent == null)
			m_parent = new MInOut(getCtx(), getM_InOut_ID(), get_TrxName());
		return m_parent;
	} // getParent

	@Override
	public int get_ID() {
		// TODO Auto-generated method stub
		return getID();
	}

	@Override
	public Timestamp getDateAcct() {
		return getParent().getDateAcct();
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
		return getParent().getC_DocType_ID();
	}

	public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
	public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	@Override
	public boolean isSOTrx() {
		return getParent().isSOTrx();
	}

	@Override
	/**
	 * get the Price Actual 
	 * @return BigDecimal with the Price Actual
	 */
	public BigDecimal getPriceActual() {
		// FIXME: ancabradau: we need to implement a real solution that will
		// cover all cases
		BigDecimal price = null;
		if (getC_OrderLine_ID() > 0) {
			price = MUDB.getSQLValueBD(get_TrxName(), "SELECT currencyBase(ol.PriceActual,o.C_Currency_ID,o.DateAcct,o.AD_Client_ID,o.AD_Org_ID) AS price "
					+ " FROM C_OrderLine ol INNER JOIN C_Order o ON (o.C_Order_ID=ol.C_Order_ID) " + " WHERE " + LP_C_OrderLine.COLUMNNAME_C_OrderLine_ID
					+ "=?", getC_OrderLine_ID());

			if (price == null || price.signum() == 0)
				price = DB.getSQLValueBD(get_TrxName(), " SELECT currencyBase(ol.PriceActual,o.C_Currency_ID,o.DateAcct,o.AD_Client_ID,o.AD_Org_ID) AS price"
						+ " FROM M_MatchPO mpo LEFT JOIN C_OrderLine ol ON ( mpo.C_OrderLine_ID=ol.C_OrderLine_ID) "
						+ " INNER JOIN C_Order o ON (o.C_Order_ID=ol.C_Order_ID) " + " WHERE  mpo." + LP_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "=?",
						getM_InOutLine_ID());

			if (price == null || price.signum() == 0)
				price = DB.getSQLValueBD(get_TrxName(), " SELECT currencyBase(il.PriceActual,i.C_Currency_ID,i.DateAcct,i.AD_Client_ID,i.AD_Org_ID) AS price "
						+ " FROM M_MatchInv mi LEFT JOIN C_InvoiceLine il ON (il.C_InvoiceLine_ID=mi.C_InvoiceLine_ID) "
						+ " INNER JOIN C_Invoice i ON (i.C_Invoice_ID=il.C_Invoice_ID) " + " WHERE  mi." + LP_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "=?",
						getM_InOutLine_ID());
		}
		if (price == null) {
			// throw new AdempiereException("Shipment: PriceActual not found");
			price = Env.ZERO;
		}
		return price;
	}

}
