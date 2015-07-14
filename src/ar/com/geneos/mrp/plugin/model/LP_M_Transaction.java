/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.openXpertya.util.CPreparedStatement;
import org.openXpertya.util.DB;

/**
 * Modelo Generado por M_Transaction
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:15.933
 */
public class LP_M_Transaction extends org.openXpertya.model.MTransaction {
	/** Constructor estándar */
	public LP_M_Transaction(Properties ctx, int M_Transaction_ID, String trxName) {
		super(ctx, M_Transaction_ID, trxName);
		/**
		 * if (M_Transaction_ID == 0) { }
		 */
	}

	/** Load Constructor */
	public LP_M_Transaction(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_Transaction[").append(getID()).append("]");
		return sb.toString();
	}

	/** Set PP_Cost_Collector_ID */
	public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
	public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";
	public static final String COLUMNNAME_MovementType = "MovementType";
	public static final String COLUMNNAME_M_Transaction_ID = "M_Transaction_ID";
	public static final String COLUMNNAME_MovementQty = "MovementQty";

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

			String sql = " INSERT INTO M_Transaction(AD_Client_ID,AD_Org_ID,C_ProjectIssue_ID,Created,CreatedBy,Description,IsActive,M_AttributeSetInstance_ID,M_InOutLine_ID,M_InventoryLine_ID,M_Locator_ID,M_MovementLine_ID,MovementDate,MovementQty,MovementType,MPC_Order_BOMLine_ID,MPC_Order_ID,M_Product_ID,M_ProductionLine_ID,M_Transaction_ID,PP_Cost_Collector_ID,Updated,UpdatedBy) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			if (getAD_Client_ID() == 0)
				sql = sql.replaceFirst("AD_Client_ID,", "").replaceFirst("\\?,", "");
			if (getAD_Org_ID() == 0)
				sql = sql.replaceFirst("AD_Org_ID,", "").replaceFirst("\\?,", "");
			if (getC_ProjectIssue_ID() == 0)
				sql = sql.replaceFirst("C_ProjectIssue_ID,", "").replaceFirst("\\?,", "");
			if (getCreated() == null)
				sql = sql.replaceFirst("Created,", "").replaceFirst("\\?,", "");
			if (getCreatedBy() == 0)
				sql = sql.replaceFirst("CreatedBy,", "").replaceFirst("\\?,", "");
			if (getDescription() == null)
				sql = sql.replaceFirst("Description,", "").replaceFirst("\\?,", "");
			if (getM_AttributeSetInstance_ID() == 0)
				sql = sql.replaceFirst("M_AttributeSetInstance_ID,", "").replaceFirst("\\?,", "");
			if (getM_InOutLine_ID() == 0)
				sql = sql.replaceFirst("M_InOutLine_ID,", "").replaceFirst("\\?,", "");
			if (getM_InventoryLine_ID() == 0)
				sql = sql.replaceFirst("M_InventoryLine_ID,", "").replaceFirst("\\?,", "");
			if (getM_Locator_ID() == 0)
				sql = sql.replaceFirst("M_Locator_ID,", "").replaceFirst("\\?,", "");
			if (getM_MovementLine_ID() == 0)
				sql = sql.replaceFirst("M_MovementLine_ID,", "").replaceFirst("\\?,", "");
			if (getMovementDate() == null)
				sql = sql.replaceFirst("MovementDate,", "").replaceFirst("\\?,", "");
			if (getMovementQty() == null)
				sql = sql.replaceFirst("MovementQty,", "").replaceFirst("\\?,", "");
			if (getMovementType() == null)
				sql = sql.replaceFirst("MovementType,", "").replaceFirst("\\?,", "");
			if (getMPC_Order_BOMLine_ID() == 0)
				sql = sql.replaceFirst("MPC_Order_BOMLine_ID,", "").replaceFirst("\\?,", "");
			if (getMPC_Order_ID() == 0)
				sql = sql.replaceFirst("MPC_Order_ID,", "").replaceFirst("\\?,", "");
			if (getM_Product_ID() == 0)
				sql = sql.replaceFirst("M_Product_ID,", "").replaceFirst("\\?,", "");
			if (getM_ProductionLine_ID() == 0)
				sql = sql.replaceFirst("M_ProductionLine_ID,", "").replaceFirst("\\?,", "");
			if (getM_Transaction_ID() == 0)
				sql = sql.replaceFirst("M_Transaction_ID,", "").replaceFirst("\\?,", "");
			if (getPP_Cost_Collector_ID() == 0)
				sql = sql.replaceFirst("PP_Cost_Collector_ID,", "").replaceFirst("\\?,", "");
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
			if (getC_ProjectIssue_ID() != 0)
				pstmt.setInt(col++, getC_ProjectIssue_ID());
			if (getCreated() != null)
				pstmt.setTimestamp(col++, getCreated());
			if (getCreatedBy() != 0)
				pstmt.setInt(col++, getCreatedBy());
			if (getDescription() != null)
				pstmt.setString(col++, getDescription());
			pstmt.setString(col++, isActive() ? "Y" : "N");
			if (getM_AttributeSetInstance_ID() != 0)
				pstmt.setInt(col++, getM_AttributeSetInstance_ID());
			if (getM_InOutLine_ID() != 0)
				pstmt.setInt(col++, getM_InOutLine_ID());
			if (getM_InventoryLine_ID() != 0)
				pstmt.setInt(col++, getM_InventoryLine_ID());
			if (getM_Locator_ID() != 0)
				pstmt.setInt(col++, getM_Locator_ID());
			if (getM_MovementLine_ID() != 0)
				pstmt.setInt(col++, getM_MovementLine_ID());
			if (getMovementDate() != null)
				pstmt.setTimestamp(col++, getMovementDate());
			if (getMovementQty() != null)
				pstmt.setBigDecimal(col++, getMovementQty());
			if (getMovementType() != null)
				pstmt.setString(col++, getMovementType());
			if (getMPC_Order_BOMLine_ID() != 0)
				pstmt.setInt(col++, getMPC_Order_BOMLine_ID());
			if (getMPC_Order_ID() != 0)
				pstmt.setInt(col++, getMPC_Order_ID());
			if (getM_Product_ID() != 0)
				pstmt.setInt(col++, getM_Product_ID());
			if (getM_ProductionLine_ID() != 0)
				pstmt.setInt(col++, getM_ProductionLine_ID());
			if (getM_Transaction_ID() != 0)
				pstmt.setInt(col++, getM_Transaction_ID());
			if (getPP_Cost_Collector_ID() != 0)
				pstmt.setInt(col++, getPP_Cost_Collector_ID());
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

}
