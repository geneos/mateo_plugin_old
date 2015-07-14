/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.openXpertya.model.MOrder;
import org.openXpertya.util.CPreparedStatement;
import org.openXpertya.util.DB;

/**
 * Modelo Generado por C_OrderLine
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:13.306
 */
public class LP_C_OrderLine extends org.openXpertya.model.MOrderLine {
	/** Constructor estándar */
	public LP_C_OrderLine(Properties ctx, int C_OrderLine_ID, String trxName) {
		super(ctx, C_OrderLine_ID, trxName);
		/**
		 * if (C_OrderLine_ID == 0) { }
		 */
	}

	public LP_C_OrderLine(MOrder order) {
		super(order);
		// TODO Auto-generated constructor stub
	}

	private MOrder m_parent = null;

	/** Load Constructor */
	public LP_C_OrderLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_C_OrderLine[").append(getID()).append("]");
		return sb.toString();
	}

	/** Set PP_Cost_Collector_ID */
	public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";
	public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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

	public boolean insertDirect() {

		try {

			String sql = " INSERT INTO C_OrderLine(AD_Client_ID,AD_Org_ID,C_BPartner_ID,C_BPartner_Location_ID,C_Charge_ID,C_Currency_ID,CheckoutPlace,C_Order_ID,C_OrderLine_ID,C_Project_ID,Created,CreatedBy,C_Tax_ID,C_UOM_ID,DateDelivered,DateInvoiced,DateOrdered,DatePromised,Description,Discount,DocumentDiscountAmt,FreightAmt,IsActive,IsDescription,Line,LineBonusAmt,LineDiscountAmt,LineNetAmt,LineTotalAmt,M_AttributeSetInstance_ID,M_Product_ID,M_Shipper_ID,M_Warehouse_ID,OpenMatrix,PP_Cost_Collector_ID,PriceActual,PriceEntered,PriceLimit,PriceList,Processed,QtyDelivered,QtyEntered,QtyInvoiced,QtyOrdered,QtyReserved,QtyTransferred,Ref_OrderLine_ID,S_ResourceAssignment_ID,Updated,UpdatedBy) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			if (getAD_Client_ID() == 0)
				sql = sql.replaceFirst("AD_Client_ID,", "").replaceFirst("\\?,", "");
			if (getAD_Org_ID() == 0)
				sql = sql.replaceFirst("AD_Org_ID,", "").replaceFirst("\\?,", "");
			if (getC_BPartner_ID() == 0)
				sql = sql.replaceFirst("C_BPartner_ID,", "").replaceFirst("\\?,", "");
			if (getC_BPartner_Location_ID() == 0)
				sql = sql.replaceFirst("C_BPartner_Location_ID,", "").replaceFirst("\\?,", "");
			if (getC_Charge_ID() == 0)
				sql = sql.replaceFirst("C_Charge_ID,", "").replaceFirst("\\?,", "");
			if (getC_Currency_ID() == 0)
				sql = sql.replaceFirst("C_Currency_ID,", "").replaceFirst("\\?,", "");
			if (getCheckoutPlace() == null)
				sql = sql.replaceFirst("CheckoutPlace,", "").replaceFirst("\\?,", "");
			if (getC_Order_ID() == 0)
				sql = sql.replaceFirst("C_Order_ID,", "").replaceFirst("\\?,", "");
			if (getC_OrderLine_ID() == 0)
				sql = sql.replaceFirst("C_OrderLine_ID,", "").replaceFirst("\\?,", "");
			if (getC_Project_ID() == 0)
				sql = sql.replaceFirst("C_Project_ID,", "").replaceFirst("\\?,", "");
			if (getCreated() == null)
				sql = sql.replaceFirst("Created,", "").replaceFirst("\\?,", "");
			if (getCreatedBy() == 0)
				sql = sql.replaceFirst("CreatedBy,", "").replaceFirst("\\?,", "");
			if (getC_Tax_ID() == 0)
				sql = sql.replaceFirst("C_Tax_ID,", "").replaceFirst("\\?,", "");
			if (getC_UOM_ID() == 0)
				sql = sql.replaceFirst("C_UOM_ID,", "").replaceFirst("\\?,", "");
			if (getDateDelivered() == null)
				sql = sql.replaceFirst("DateDelivered,", "").replaceFirst("\\?,", "");
			if (getDateInvoiced() == null)
				sql = sql.replaceFirst("DateInvoiced,", "").replaceFirst("\\?,", "");
			if (getDateOrdered() == null)
				sql = sql.replaceFirst("DateOrdered,", "").replaceFirst("\\?,", "");
			if (getDatePromised() == null)
				sql = sql.replaceFirst("DatePromised,", "").replaceFirst("\\?,", "");
			if (getDescription() == null)
				sql = sql.replaceFirst("Description,", "").replaceFirst("\\?,", "");
			if (getDiscount() == null)
				sql = sql.replaceFirst("Discount,", "").replaceFirst("\\?,", "");
			if (getDocumentDiscountAmt() == null)
				sql = sql.replaceFirst("DocumentDiscountAmt,", "").replaceFirst("\\?,", "");
			if (getFreightAmt() == null)
				sql = sql.replaceFirst("FreightAmt,", "").replaceFirst("\\?,", "");
			if (getLine() == 0)
				sql = sql.replaceFirst("Line,", "").replaceFirst("\\?,", "");
			if (getLineBonusAmt() == null)
				sql = sql.replaceFirst("LineBonusAmt,", "").replaceFirst("\\?,", "");
			if (getLineDiscountAmt() == null)
				sql = sql.replaceFirst("LineDiscountAmt,", "").replaceFirst("\\?,", "");
			if (getLineNetAmt() == null)
				sql = sql.replaceFirst("LineNetAmt,", "").replaceFirst("\\?,", "");
			if (getLineTotalAmt() == null)
				sql = sql.replaceFirst("LineTotalAmt,", "").replaceFirst("\\?,", "");
			if (getM_AttributeSetInstance_ID() == 0)
				sql = sql.replaceFirst("M_AttributeSetInstance_ID,", "").replaceFirst("\\?,", "");
			if (getM_Product_ID() == 0)
				sql = sql.replaceFirst("M_Product_ID,", "").replaceFirst("\\?,", "");
			if (getM_Shipper_ID() == 0)
				sql = sql.replaceFirst("M_Shipper_ID,", "").replaceFirst("\\?,", "");
			if (getM_Warehouse_ID() == 0)
				sql = sql.replaceFirst("M_Warehouse_ID,", "").replaceFirst("\\?,", "");
			if (getOpenMatrix() == null)
				sql = sql.replaceFirst("OpenMatrix,", "").replaceFirst("\\?,", "");
			if (getPP_Cost_Collector_ID() == 0)
				sql = sql.replaceFirst("PP_Cost_Collector_ID,", "").replaceFirst("\\?,", "");
			if (getPriceActual() == null)
				sql = sql.replaceFirst("PriceActual,", "").replaceFirst("\\?,", "");
			if (getPriceEntered() == null)
				sql = sql.replaceFirst("PriceEntered,", "").replaceFirst("\\?,", "");
			if (getPriceLimit() == null)
				sql = sql.replaceFirst("PriceLimit,", "").replaceFirst("\\?,", "");
			if (getPriceList() == null)
				sql = sql.replaceFirst("PriceList,", "").replaceFirst("\\?,", "");
			if (getQtyDelivered() == null)
				sql = sql.replaceFirst("QtyDelivered,", "").replaceFirst("\\?,", "");
			if (getQtyEntered() == null)
				sql = sql.replaceFirst("QtyEntered,", "").replaceFirst("\\?,", "");
			if (getQtyInvoiced() == null)
				sql = sql.replaceFirst("QtyInvoiced,", "").replaceFirst("\\?,", "");
			if (getQtyOrdered() == null)
				sql = sql.replaceFirst("QtyOrdered,", "").replaceFirst("\\?,", "");
			if (getQtyReserved() == null)
				sql = sql.replaceFirst("QtyReserved,", "").replaceFirst("\\?,", "");
			if (getQtyTransferred() == null)
				sql = sql.replaceFirst("QtyTransferred,", "").replaceFirst("\\?,", "");
			if (getRef_OrderLine_ID() == 0)
				sql = sql.replaceFirst("Ref_OrderLine_ID,", "").replaceFirst("\\?,", "");
			if (getS_ResourceAssignment_ID() == 0)
				sql = sql.replaceFirst("S_ResourceAssignment_ID,", "").replaceFirst("\\?,", "");
			if (getUpdated() == null)
				sql = sql.replaceFirst("Updated,", "").replaceFirst("\\?,", "");
			if (getUpdatedBy() == 0)
				sql = sql.replaceFirst("UpdatedBy,", "").replaceFirst("\\?,", "");

			int col = 1;

			CPreparedStatement pstmt = new CPreparedStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, sql, get_TrxName(), true);

			if (getAD_Client_ID() != 0)
				pstmt.setInt(col++, getAD_Client_ID());
			if (getAD_Org_ID() != 0)
				pstmt.setInt(col++, getAD_Org_ID());
			if (getC_BPartner_ID() != 0)
				pstmt.setInt(col++, getC_BPartner_ID());
			if (getC_BPartner_Location_ID() != 0)
				pstmt.setInt(col++, getC_BPartner_Location_ID());
			if (getC_Charge_ID() != 0)
				pstmt.setInt(col++, getC_Charge_ID());
			if (getC_Currency_ID() != 0)
				pstmt.setInt(col++, getC_Currency_ID());
			if (getCheckoutPlace() != null)
				pstmt.setString(col++, getCheckoutPlace());
			if (getC_Order_ID() != 0)
				pstmt.setInt(col++, getC_Order_ID());
			if (getC_OrderLine_ID() != 0)
				pstmt.setInt(col++, getC_OrderLine_ID());
			if (getC_Project_ID() != 0)
				pstmt.setInt(col++, getC_Project_ID());
			if (getCreated() != null)
				pstmt.setTimestamp(col++, getCreated());
			if (getCreatedBy() != 0)
				pstmt.setInt(col++, getCreatedBy());
			if (getC_Tax_ID() != 0)
				pstmt.setInt(col++, getC_Tax_ID());
			if (getC_UOM_ID() != 0)
				pstmt.setInt(col++, getC_UOM_ID());
			if (getDateDelivered() != null)
				pstmt.setTimestamp(col++, getDateDelivered());
			if (getDateInvoiced() != null)
				pstmt.setTimestamp(col++, getDateInvoiced());
			if (getDateOrdered() != null)
				pstmt.setTimestamp(col++, getDateOrdered());
			if (getDatePromised() != null)
				pstmt.setTimestamp(col++, getDatePromised());
			if (getDescription() != null)
				pstmt.setString(col++, getDescription());
			if (getDiscount() != null)
				pstmt.setBigDecimal(col++, getDiscount());
			if (getDocumentDiscountAmt() != null)
				pstmt.setBigDecimal(col++, getDocumentDiscountAmt());
			if (getFreightAmt() != null)
				pstmt.setBigDecimal(col++, getFreightAmt());
			pstmt.setString(col++, isActive() ? "Y" : "N");
			pstmt.setString(col++, isDescription() ? "Y" : "N");
			if (getLine() != 0)
				pstmt.setInt(col++, getLine());
			if (getLineBonusAmt() != null)
				pstmt.setBigDecimal(col++, getLineBonusAmt());
			if (getLineDiscountAmt() != null)
				pstmt.setBigDecimal(col++, getLineDiscountAmt());
			if (getLineNetAmt() != null)
				pstmt.setBigDecimal(col++, getLineNetAmt());
			if (getLineTotalAmt() != null)
				pstmt.setBigDecimal(col++, getLineTotalAmt());
			if (getM_AttributeSetInstance_ID() != 0)
				pstmt.setInt(col++, getM_AttributeSetInstance_ID());
			if (getM_Product_ID() != 0)
				pstmt.setInt(col++, getM_Product_ID());
			if (getM_Shipper_ID() != 0)
				pstmt.setInt(col++, getM_Shipper_ID());
			if (getM_Warehouse_ID() != 0)
				pstmt.setInt(col++, getM_Warehouse_ID());
			if (getOpenMatrix() != null)
				pstmt.setString(col++, getOpenMatrix());
			if (getPP_Cost_Collector_ID() != 0)
				pstmt.setInt(col++, getPP_Cost_Collector_ID());
			if (getPriceActual() != null)
				pstmt.setBigDecimal(col++, getPriceActual());
			if (getPriceEntered() != null)
				pstmt.setBigDecimal(col++, getPriceEntered());
			if (getPriceLimit() != null)
				pstmt.setBigDecimal(col++, getPriceLimit());
			if (getPriceList() != null)
				pstmt.setBigDecimal(col++, getPriceList());
			pstmt.setString(col++, isProcessed() ? "Y" : "N");
			if (getQtyDelivered() != null)
				pstmt.setBigDecimal(col++, getQtyDelivered());
			if (getQtyEntered() != null)
				pstmt.setBigDecimal(col++, getQtyEntered());
			if (getQtyInvoiced() != null)
				pstmt.setBigDecimal(col++, getQtyInvoiced());
			if (getQtyOrdered() != null)
				pstmt.setBigDecimal(col++, getQtyOrdered());
			if (getQtyReserved() != null)
				pstmt.setBigDecimal(col++, getQtyReserved());
			if (getQtyTransferred() != null)
				pstmt.setBigDecimal(col++, getQtyTransferred());
			if (getRef_OrderLine_ID() != 0)
				pstmt.setInt(col++, getRef_OrderLine_ID());
			if (getS_ResourceAssignment_ID() != 0)
				pstmt.setInt(col++, getS_ResourceAssignment_ID());
			if (getUpdated() != null)
				pstmt.setTimestamp(col++, getUpdated());
			if (getUpdatedBy() != 0)
				pstmt.setInt(col++, getUpdatedBy());

			pstmt.executeUpdate();

			return true;

		} catch (SQLException e) {
			log.log(Level.SEVERE, "insertDirect", e);
			log.saveError("Error", DB.getErrorMsg(e) + " - " + e);
			return false;
		} catch (Exception e2) {
			log.log(Level.SEVERE, "insertDirect", e2);
			return false;
		}

	}

	public MOrder getParent() {
		if (m_parent == null)
			m_parent = new MOrder(getCtx(), getC_Order_ID(), get_TrxName());
		return m_parent;
	} // getParent

	/** Column name IsConsumesForecast */
	public static final String COLUMNNAME_IsConsumesForecast = "IsConsumesForecast";

	/**
	 * Set Is Consumes Forecast.
	 * 
	 * @param IsConsumesForecast
	 *            Is Consumes Forecast
	 */
	public void setIsConsumesForecast(boolean IsConsumesForecast) {
		set_Value(COLUMNNAME_IsConsumesForecast, Boolean.valueOf(IsConsumesForecast));
	}

	/**
	 * Get Is Consumes Forecast.
	 * 
	 * @return Is Consumes Forecast
	 */
	public boolean isConsumesForecast() {
		Object oo = get_Value(COLUMNNAME_IsConsumesForecast);
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

}
