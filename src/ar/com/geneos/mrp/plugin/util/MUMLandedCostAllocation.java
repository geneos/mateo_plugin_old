package ar.com.geneos.mrp.plugin.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openXpertya.model.MMatchPO;
import org.openXpertya.model.Query;
import org.openXpertya.util.DB;
import org.openXpertya.util.DBException;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.model.LP_M_InOutLine;
import ar.com.geneos.mrp.plugin.model.LP_C_LandedCostAllocation;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchPO;

public class MUMLandedCostAllocation {

	/**
	 * get Match PO entity
	 * 
	 * @param ioLine
	 * @return
	 */
	public static List<LP_C_LandedCostAllocation> getOfInOuline(LP_M_InOutLine ioLine, int m_CostElement_ID) {
		List<LP_C_LandedCostAllocation> ret = new ArrayList<LP_C_LandedCostAllocation>();
		String sql = "Select * from " + MMatchPO.Table_Name + " where " + LP_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "=" + ioLine.getM_InOutLine_ID()
				+ " and M_CostElement_ID = " + m_CostElement_ID + " and ad_client_id = " + Env.getAD_Client_ID(ioLine.getCtx());
		PreparedStatement pstmt = DB.prepareStatement(sql, ioLine.get_TrxName());
		ResultSet rs = null;
		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ret.add(new LP_C_LandedCostAllocation(ioLine.getCtx(), rs, ioLine.get_TrxName()));
			}
		} catch (SQLException e) {
			throw new DBException(e, sql);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return ret;
	}

}
