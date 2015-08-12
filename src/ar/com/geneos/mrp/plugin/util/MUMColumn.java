package ar.com.geneos.mrp.plugin.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.openXpertya.util.CLogger;
import org.openXpertya.util.DB;
import org.openXpertya.model.MMatchInv;
import org.openXpertya.model.MTable;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.LP_M_InOutLine;
import ar.com.geneos.mrp.plugin.model.LP_M_MatchInv;

public class MUMColumn {
	
	protected static CLogger log = CLogger.getCLogger(MUMColumn.class.getName());

	/**
	 * 	get Column ID
	 *  @param String windowName
	 *	@param String columnName
	 *	@return int retValue
	 */
	public static int getColumn_ID(String TableName,String columnName) {
		int m_table_id = MUMTable.getTable_ID(TableName);
		if (m_table_id == 0)
			return 0;
			
		int retValue = 0;
		String SQL = "SELECT AD_Column_ID FROM AD_Column WHERE AD_Table_ID = ?  AND columnname = ?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(SQL, null);
			pstmt.setInt(1, m_table_id);
			pstmt.setString(2, columnName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				retValue = rs.getInt(1);
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, SQL, e);
			retValue = -1;
		}
		return retValue;
	}

}
