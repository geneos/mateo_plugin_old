package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MInventory;
import org.openXpertya.model.MInventoryLine;

public class LP_M_InventoryLine extends MInventoryLine implements IDocumentLine {

	/** Parent */
	private MInventory m_parent = null;

	public LP_M_InventoryLine(MInventory inventory, int M_Locator_ID, int M_Product_ID, int M_AttributeSetInstance_ID, BigDecimal QtyBook, BigDecimal QtyCount) {
		super(inventory, M_Locator_ID, M_Product_ID, M_AttributeSetInstance_ID, QtyBook, QtyCount);
		// TODO Auto-generated constructor stub
	}

	public LP_M_InventoryLine(Properties ctx, int M_InventoryLine_ID, String trxName) {
		super(ctx, M_InventoryLine_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	public LP_M_InventoryLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int get_ID() {
		return getID();
	}

	@Override
	public int getC_DocType_ID() {
		return getParent().getC_DocType_ID();
	}

	@Override
	/**
	 * Get Movement Qty (absolute value)
	 * <li>negative value means outgoing trx
	 * <li>positive value means incoming trx
	 * @return movement qty
	 */
	public BigDecimal getMovementQty() {
		if (isInternalUseInventory()) {
			return getQtyInternalUse().negate();
		} else {
			return getQtyCount().subtract(getQtyBook());
		}
	}

	/**
	 * Is Internal Use Inventory
	 * 
	 * @return true if is internal use inventory
	 */
	public boolean isInternalUseInventory() {
		/*
		 * TODO: need to add M_Inventory.IsInternalUseInventory flag see FR [
		 * 1879029 ] Added IsInternalUseInventory flag to M_Inventory table
		 * MInventory parent = getParent(); return parent != null &&
		 * parent.isInternalUseInventory();
		 */
		return getQtyInternalUse().signum() != 0;
	}

	@Override
	public int getM_AttributeSetInstanceTo_ID() {
		return -1;
	}

	@Override
	public int getM_LocatorTo_ID() {
		return -1;
	}

	/**
	 * @return true if is an outgoing transaction
	 */
	public boolean isSOTrx() {
		return getMovementQty().signum() < 0;
	}

	public BigDecimal getPriceActual() {
		return null;
	}

	@Override
	public Timestamp getDateAcct() {
		return getParent().getMovementDate();
	}

	/**
	 * Get Parent
	 *
	 * @return parent
	 */
	public MInventory getParent() {
		if (m_parent == null)
			m_parent = new MInventory(getCtx(), getM_Inventory_ID(), get_TrxName());
		return m_parent;
	} // getParent

}
