package ar.com.geneos.mrp.plugin.util;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.openXpertya.model.MProduct;
import org.openXpertya.model.MStorage;
import org.openXpertya.model.MWarehouse;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.DB;

public class MUMStorage {

	public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";
	private static CLogger s_log = CLogger.getCLogger(MUMStorage.class);

	/**
	 * Get Storage Info for Warehouse or locator
	 *
	 * @param ctx
	 *            context
	 * @param M_Warehouse_ID
	 *            ignore if M_Locator_ID > 0
	 * @param M_Product_ID
	 *            product
	 * @param M_AttributeSetInstance_ID
	 *            instance id, 0 to retrieve all instance
	 * @param minGuaranteeDate
	 *            optional minimum guarantee date if all attribute instances
	 * @param FiFo
	 *            first in-first-out
	 * @param positiveOnly
	 *            if true, only return storage records with qtyOnHand > 0
	 * @param M_Locator_ID
	 *            optional locator id
	 * @param trxName
	 *            transaction
	 * @return existing - ordered by location priority (desc) and/or guarantee
	 *         date
	 */
	public static MStorage[] getWarehouse(Properties ctx, int M_Warehouse_ID, int M_Product_ID, int M_AttributeSetInstance_ID, Timestamp minGuaranteeDate,
			boolean FiFo, boolean positiveOnly, int M_Locator_ID, String trxName) {
		if ((M_Warehouse_ID == 0 && M_Locator_ID == 0) || M_Product_ID == 0)
			return new MStorage[0];

		boolean allAttributeInstances = false;
		if (M_AttributeSetInstance_ID == 0)
			allAttributeInstances = true;

		ArrayList<MStorage> list = new ArrayList<MStorage>();
		// Specific Attribute Set Instance
		String sql = "SELECT s.M_Product_ID,s.M_Locator_ID,s.M_AttributeSetInstance_ID,"
				+ "s.AD_Client_ID,s.AD_Org_ID,s.IsActive,s.Created,s.CreatedBy,s.Updated,s.UpdatedBy,"
				+ "s.QtyOnHand,s.QtyReserved,s.QtyOrdered,s.DateLastInventory " + "FROM M_Storage s"
				+ " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID) ";
		if (M_Locator_ID > 0)
			sql += "WHERE l.M_Locator_ID = ?";
		else
			sql += "WHERE l.M_Warehouse_ID=?";
		sql += " AND s.M_Product_ID=?" + " AND COALESCE(s.M_AttributeSetInstance_ID,0)=? ";
		if (positiveOnly) {
			sql += " AND s.QtyOnHand > 0 ";
		} else {
			sql += " AND s.QtyOnHand <> 0 ";
		}
		sql += "ORDER BY l.PriorityNo DESC, M_AttributeSetInstance_ID";
		if (!FiFo)
			sql += " DESC";
		// All Attribute Set Instances
		if (allAttributeInstances) {
			sql = "SELECT s.M_Product_ID,s.M_Locator_ID,s.M_AttributeSetInstance_ID,"
					+ "s.AD_Client_ID,s.AD_Org_ID,s.IsActive,s.Created,s.CreatedBy,s.Updated,s.UpdatedBy,"
					+ "s.QtyOnHand,s.QtyReserved,s.QtyOrdered,s.DateLastInventory " + "FROM M_Storage s"
					+ " INNER JOIN M_Locator l ON (l.M_Locator_ID=s.M_Locator_ID)"
					+ " LEFT OUTER JOIN M_AttributeSetInstance asi ON (s.M_AttributeSetInstance_ID=asi.M_AttributeSetInstance_ID) ";
			if (M_Locator_ID > 0)
				sql += "WHERE l.M_Locator_ID = ?";
			else
				sql += "WHERE l.M_Warehouse_ID=?";
			sql += " AND s.M_Product_ID=? ";
			if (positiveOnly) {
				sql += " AND s.QtyOnHand > 0 ";
			} else {
				sql += " AND s.QtyOnHand <> 0 ";
			}
			if (minGuaranteeDate != null) {
				sql += "AND (asi.GuaranteeDate IS NULL OR asi.GuaranteeDate>?) ";
				sql += "ORDER BY l.PriorityNo DESC, " + "asi.GuaranteeDate, M_AttributeSetInstance_ID";
				if (!FiFo)
					sql += " DESC";
				sql += ", s.QtyOnHand DESC";
			} else {
				sql += "ORDER BY l.PriorityNo DESC, l.M_Locator_ID, s.M_AttributeSetInstance_ID";
				if (!FiFo)
					sql += " DESC";
				sql += ", s.QtyOnHand DESC";
			}
		}
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, M_Locator_ID > 0 ? M_Locator_ID : M_Warehouse_ID);
			pstmt.setInt(2, M_Product_ID);
			if (!allAttributeInstances) {
				pstmt.setInt(3, M_AttributeSetInstance_ID);
			} else if (minGuaranteeDate != null) {
				pstmt.setTimestamp(3, minGuaranteeDate);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getBigDecimal(11).signum() != 0)
					list.add(new MStorage(ctx, rs, trxName));
			}
		} catch (Exception e) {
			s_log.log(Level.SEVERE, sql, e);
		} finally {
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		MStorage[] retValue = new MStorage[list.size()];
		list.toArray(retValue);
		return retValue;
	} // getWarehouse
	
	/**
	 * 	Update Storage Info add.
	 * 	Called from MProjectIssue
	 *	@param ctx context
	 *	@param M_Warehouse_ID warehouse
	 *	@param M_Locator_ID locator
	 *	@param M_Product_ID product
	 *	@param M_AttributeSetInstance_ID AS Instance
	 *	@param reservationAttributeSetInstance_ID reservation AS Instance
	 *	@param diffQtyOnHand add on hand
	 *	@param diffQtyReserved add reserved
	 *	@param diffQtyOrdered add order
	 *	@param trxName transaction
	 *	@return true if updated
	 */
	public static boolean add (Properties ctx, int M_Warehouse_ID, int M_Locator_ID, 
		int M_Product_ID, int M_AttributeSetInstance_ID, int reservationAttributeSetInstance_ID,
		BigDecimal diffQtyOnHand, 
		BigDecimal diffQtyReserved, BigDecimal diffQtyOrdered, String trxName)
	{
		MStorage storage = null;
		StringBuffer diffText = new StringBuffer("(");

		//	Get Storage
		if (storage == null)
			storage = MStorage.getCreate (ctx, M_Locator_ID, 
				M_Product_ID, M_AttributeSetInstance_ID, trxName);
		//	Verify
		if (storage.getM_Locator_ID() != M_Locator_ID 
			&& storage.getM_Product_ID() != M_Product_ID
			&& storage.getM_AttributeSetInstance_ID() != M_AttributeSetInstance_ID)
		{
			s_log.severe ("No Storage found - M_Locator_ID=" + M_Locator_ID 
				+ ",M_Product_ID=" + M_Product_ID + ",ASI=" + M_AttributeSetInstance_ID);
			return false;
		}
		
		// CarlosRuiz - globalqss - Fix [ 1725383 ] QtyOrdered wrongly updated
		MProduct prd = new MProduct(ctx, M_Product_ID, trxName);
		if (prd.getM_AttributeSet_ID() == 0) {
			// Product doesn't manage attribute set, always reserved with 0
			reservationAttributeSetInstance_ID = 0;
		}
		//		
		
		MStorage storage0 = null;
		if (M_AttributeSetInstance_ID != reservationAttributeSetInstance_ID)
		{
			storage0 = MStorage.get(ctx, M_Locator_ID, 
				M_Product_ID, reservationAttributeSetInstance_ID, trxName);
			if (storage0 == null)	//	create if not existing - should not happen
			{
				MWarehouse wh = MWarehouse.get(ctx, M_Warehouse_ID);
				int xM_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
				storage0 = MStorage.getCreate (ctx, xM_Locator_ID, 
					M_Product_ID, reservationAttributeSetInstance_ID, trxName);
			}
		}		
		boolean changed = false;
		if (diffQtyOnHand != null && diffQtyOnHand.signum() != 0)
		{
			storage.setQtyOnHand (storage.getQtyOnHand().add (diffQtyOnHand));
			diffText.append("OnHand=").append(diffQtyOnHand);
			changed = true;
		}
		if (diffQtyReserved != null && diffQtyReserved.signum() != 0)
		{
			if (storage0 == null)
			{
				storage.setQtyReserved(storage.getQtyReserved().add(diffQtyReserved));
				//Util.assume(storage.getQtyReserved().signum() >= 0, "QtyReserved should be >=0 for " + storage);
			}
			else
			{
				storage0.setQtyReserved(storage0.getQtyReserved().add(diffQtyReserved));
				//Util.assume(storage0.getQtyReserved().signum() >= 0, "QtyReserved should be >=0 for " + storage0);
			}
			diffText.append(" Reserved=").append(diffQtyReserved);
			changed = true;
			
		}
		if (diffQtyOrdered != null && diffQtyOrdered.signum() != 0)
		{
			if (storage0 == null)
				storage.setQtyOrdered (storage.getQtyOrdered().add (diffQtyOrdered));
			else
				storage0.setQtyOrdered (storage0.getQtyOrdered().add (diffQtyOrdered));
			diffText.append(" Ordered=").append(diffQtyOrdered);
			changed = true;
		}
		if (changed)
		{
			diffText.append(") -> ").append(storage.toString());
			s_log.fine(diffText.toString());
			if (storage0 != null)
				storage0.save(trxName);		//	No AttributeSetInstance (reserved/ordered)
			return storage.save (trxName);
		}
		
		return true;
	}	//	add

}
