package ar.com.geneos.mrp.plugin.util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.openXpertya.util.CLogger;
import org.openXpertya.util.DB;
import org.openXpertya.util.DBException;

public class MUDB {

	private static CLogger log = CLogger.getCLogger(MUDB.class);

	/**
	 * Get Timestamp Value from sql
	 * 
	 * @param trxName
	 *            trx
	 * @param sql
	 *            sql
	 * @param params
	 *            array of parameters
	 * @return first value or null
	 * @throws DBException
	 *             if there is any SQLException
	 */
	public static Timestamp getSQLValueTSEx(String trxName, String sql, Object... params) {
		Timestamp retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getTimestamp(1);
			else
				log.fine("No Value " + sql);
		} catch (SQLException e) {
			throw new DBException(e, sql);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}

	/**
	 * Get int Value from sql
	 * 
	 * @param trxName
	 *            trx
	 * @param sql
	 *            sql
	 * @param params
	 *            array of parameters
	 * @return first value or -1 if not found or error
	 */
	public static int getSQLValue(String trxName, String sql, Object... params) {
		int retValue = -1;
		try {
			retValue = DB.getSQLValueEx(trxName, sql, params);
		} catch (Exception e) {
			log.log(Level.SEVERE, sql, DB.getSQLException(e));
		}
		return retValue;
	}

	/**
	 * Get BigDecimal Value from sql
	 * 
	 * @param trxName
	 *            trx
	 * @param sql
	 *            sql
	 * @param params
	 *            array of parameters
	 * @return first value or null if not found
	 * @throws DBException
	 *             if there is any SQLException
	 */
	public static BigDecimal getSQLValueBD(String trxName, String sql, Object... params) {
		BigDecimal retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getBigDecimal(1);
			else
				log.fine("No Value " + sql);
		} catch (SQLException e) {
			// log.log(Level.SEVERE, sql, getSQLException(e));
			throw new DBException(e, sql);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return retValue;
	}

}
