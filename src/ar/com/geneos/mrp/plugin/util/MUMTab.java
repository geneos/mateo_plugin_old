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

public class MUMTab {
	
	protected static CLogger log = CLogger.getCLogger( MTable.class.getName());

	/**
	 * 	Grant independence to GenerateModel from AD_Table_ID
	 *	@param String tableName
	 *	@return int retValue
	 *  @author 
	 */
	
	public static int getTab_ID(int AD_Window_ID , String TabName) {
		int retValue = 0;
		String SQL = "SELECT AD_Tab_ID FROM AD_Tab WHERE AD_Window_ID= ?  AND Name = ?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(SQL, null);
			pstmt.setInt(1, AD_Window_ID);
			pstmt.setString(2, TabName);
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
