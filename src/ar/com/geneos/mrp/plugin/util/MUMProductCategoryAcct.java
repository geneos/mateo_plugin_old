package ar.com.geneos.mrp.plugin.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MProductCategoryAcct;
import org.openXpertya.model.Query;
import org.openXpertya.util.CCache;
import org.openXpertya.util.DB;

import ar.com.geneos.mrp.plugin.model.LP_C_AcctSchema;
import ar.com.geneos.mrp.plugin.model.LP_M_Product_Category_Acct;

public class MUMProductCategoryAcct {

	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
	public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";
	public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";
	public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	private static CCache<String, MProductCategoryAcct> s_cache = new CCache<String, MProductCategoryAcct>(MProductCategoryAcct.Table_Name, 40, 5);

	/**
	 * Get Category Acct
	 *
	 * @param ctx
	 *            context
	 * @param M_Product_Category_ID
	 *            category
	 * @param C_AcctSchema_ID
	 *            acct schema
	 * @param trxName
	 *            trx
	 * @return category acct
	 */
	public static MProductCategoryAcct get(Properties ctx, int M_Product_Category_ID, int C_AcctSchema_ID, int AD_Org_ID, String trxName) {

		MAcctSchema as = new MAcctSchema(ctx, C_AcctSchema_ID, trxName);

		if (!LP_C_AcctSchema.COSTINGLEVEL_Organization.equals(MUMAcctSchema.getCostingLevel(as))) {
			return MUMProductCategoryAcct.get(ctx, M_Product_Category_ID, C_AcctSchema_ID, trxName);
		}

		String key = M_Product_Category_ID + "#" + C_AcctSchema_ID + "#" + AD_Org_ID;
		MProductCategoryAcct acct = s_cache.get(key);
		if (acct != null)
			return acct;

		final String whereClause = "M_Product_Category_ID=? AND C_AcctSchema_ID=? AND (AD_Org_ID=? OR AD_Org_ID = 0)";
		acct = new Query(ctx, MProductCategoryAcct.Table_Name, whereClause, trxName).setParameters(M_Product_Category_ID, C_AcctSchema_ID, AD_Org_ID)
				.setOrderBy(COLUMNNAME_AD_Org_ID + " DESC").first();
		if (acct != null) {
			s_cache.put(key, acct);
		}
		return acct;
	} // get

	/**
	 * Get Category Acct
	 *
	 * @param ctx
	 *            context
	 * @param M_Product_Category_ID
	 *            category
	 * @param C_AcctSchema_ID
	 *            acct schema
	 * @param trxName
	 *            trx
	 * @return category acct
	 */
	public static MProductCategoryAcct get(Properties ctx, int M_Product_Category_ID, int C_AcctSchema_ID, String trxName) {
		String key = M_Product_Category_ID + "#" + C_AcctSchema_ID;
		MProductCategoryAcct acct = s_cache.get(key);
		if (acct != null)
			return acct;

		final String whereClause = "M_Product_Category_ID=? AND C_AcctSchema_ID=?";
		acct = new Query(ctx, MProductCategoryAcct.Table_Name, whereClause, trxName).setParameters(M_Product_Category_ID, C_AcctSchema_ID).firstOnly();
		if (acct != null) {
			s_cache.put(key, acct);
		}
		return acct;
	} // get

	public static String getCostingLevel(MProductCategoryAcct pca) {
		LP_M_Product_Category_Acct aux = null;
		String SQL = "SELECT * FROM M_Product_Category_Acct WHERE M_Product_Category_ID = ?  AND C_AcctSchema_ID = ?";

		try {
			PreparedStatement pstmt = DB.prepareStatement(SQL, pca.get_TrxName());
			pstmt.setInt(1, pca.getM_Product_Category_ID());
			pstmt.setInt(2, pca.getC_AcctSchema_ID());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				aux = new LP_M_Product_Category_Acct(pca.getCtx(), rs, pca.get_TrxName());
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (aux == null)
			return null;
		else
			return aux.getCostingLevel();
	}
}
