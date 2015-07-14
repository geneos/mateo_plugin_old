package ar.com.geneos.mrp.plugin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MClientInfo;
import org.openXpertya.model.MCostType;
import org.openXpertya.model.Query;
import org.openXpertya.util.CCache;

import ar.com.geneos.mrp.plugin.model.LP_C_AcctSchema;

public class MUMAcctSchema {

	private static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/** Cache of Client AcctSchema Arrays **/
	private static CCache<Integer, MAcctSchema[]> s_schema = new CCache<Integer, MAcctSchema[]>("AD_ClientInfo", 3); // 3
																														// clients

	public static String getCostingLevel(MAcctSchema as) {
		LP_C_AcctSchema aux = new LP_C_AcctSchema(as.getCtx(), as.getID(), as.get_TrxName());
		return aux.getCostingLevel();
	}

	public static boolean isAdjustCOGS(MAcctSchema as) {
		LP_C_AcctSchema aux = new LP_C_AcctSchema(as.getCtx(), as.getID(), as.get_TrxName());
		return aux.isAdjustCOGS();
	}

	/**
	 * Get AccountSchema of Client
	 * 
	 * @param ctx
	 *            context
	 * @param AD_Client_ID
	 *            client or 0 for all
	 * @param trxName
	 *            optional trx
	 * @return Array of AcctSchema of Client
	 */
	public static MAcctSchema[] getClientAcctSchema(Properties ctx, int AD_Client_ID, String trxName) {
		// Check Cache
		Integer key = new Integer(AD_Client_ID);
		if (s_schema.containsKey(key))
			return (MAcctSchema[]) s_schema.get(key);

		// Create New
		ArrayList<MAcctSchema> list = new ArrayList<MAcctSchema>();
		MClientInfo info = new MClientInfo(ctx, AD_Client_ID, trxName);
		MAcctSchema as = new MAcctSchema(ctx, info.getC_AcctSchema1_ID(), trxName);
		if (as.getID() != 0)
			list.add(as);

		ArrayList<Object> params = new ArrayList<Object>();
		String whereClause = "IsActive=? " + " AND EXISTS (SELECT * FROM C_AcctSchema_GL gl WHERE C_AcctSchema.C_AcctSchema_ID=gl.C_AcctSchema_ID)"
				+ " AND EXISTS (SELECT * FROM C_AcctSchema_Default d WHERE C_AcctSchema.C_AcctSchema_ID=d.C_AcctSchema_ID)";
		params.add("Y");
		if (AD_Client_ID != 0) {
			whereClause += " AND AD_Client_ID=?";
			params.add(AD_Client_ID);
		}

		List<MAcctSchema> ass = new Query(ctx, LP_C_AcctSchema.Table_Name, whereClause, trxName).setParameters(params)
				.setOrderBy(MUMAcctSchema.COLUMNNAME_C_AcctSchema_ID).list();

		for (MAcctSchema acctschema : ass) {
			if (acctschema.getID() != info.getC_AcctSchema1_ID()) // already in
																	// list
			{
				if (acctschema.getID() != 0)
					list.add(acctschema);
			}
		}
		// Save
		MAcctSchema[] retValue = new MAcctSchema[list.size()];
		list.toArray(retValue);
		s_schema.put(key, retValue);
		return retValue;
	} // getClientAcctSchema

	public static MCostType getM_CostType(MAcctSchema accountSchema) {
		// TODO Auto-generated method stub
		return new MCostType(accountSchema.getCtx(), accountSchema.getM_CostType_ID(), accountSchema.get_TrxName());
	}

}
