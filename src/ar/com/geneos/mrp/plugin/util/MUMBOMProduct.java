package ar.com.geneos.mrp.plugin.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.openXpertya.util.CLogger;
import org.openXpertya.util.DB;
import org.openXpertya.model.MBOM;
import org.openXpertya.model.MBOMProduct;
import org.openXpertya.model.MMatchInv;
import org.openXpertya.model.MTable;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.LP_M_InOutLine;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchInv;

public class MUMBOMProduct {

	protected static CLogger log = CLogger.getCLogger(MTable.class.getName());

	/**
	 * Grant independence to GenerateModel from AD_Table_ID
	 *
	 * @param String
	 *            tableName
	 * @return int retValue
	 * @author
	 */

	public static int getTable_ID(String tableName) {
		int retValue = 0;
		String SQL = "SELECT AD_Table_ID FROM AD_Table WHERE tablename = ?";
		try {
			PreparedStatement pstmt = DB.prepareStatement(SQL, null);
			pstmt.setString(1, tableName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getInt(1);
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			log.log(Level.SEVERE, SQL, e);
			retValue = -1;
		}
		return retValue;
	}

	/**
	 * Get Products of BOM
	 *
	 * @param bom
	 *            bom
	 * @return array of BOM Products
	 */
	public static MBOMProduct[] getOfBOM(MBOM bom) {
		// FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
		String whereClause = "M_BOM_ID=?";
		List<MBOMProduct> list = new Query(bom.getCtx(), MBOMProduct.Table_Name, whereClause, bom.get_TrxName()).setParameters(bom.getM_BOM_ID())
				.setOrderBy("SeqNo").list();

		MBOMProduct[] retValue = new MBOMProduct[list.size()];
		list.toArray(retValue);
		return retValue;
	} // getOfProduct

}
