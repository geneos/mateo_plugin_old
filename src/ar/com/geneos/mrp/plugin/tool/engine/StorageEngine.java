package ar.com.geneos.mrp.plugin.tool.engine;

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): victor.perez@e-evolution.com http://www.e-evolution.com    *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.openXpertya.model.MAttributeSetInstance;
import org.openXpertya.model.MClient;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MStorage;
import org.openXpertya.model.MTable;
import org.openXpertya.model.MTransaction;
import org.openXpertya.model.MWarehouse;
import org.openXpertya.model.M_Table;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.model.IDocumentLine;
import ar.com.geneos.mrp.plugin.model.IInventoryAllocation;
import ar.com.geneos.mrp.plugin.util.MUMAttributeSetInstance;
import ar.com.geneos.mrp.plugin.util.MUMProduct;
import ar.com.geneos.mrp.plugin.util.MUMStorage;

/**
 * Storage Engine
 * 
 * @author victor.perez@e-evolution.com http://www.e-evolution.com
 * @author Teo Sarca
 */
public class StorageEngine {

	/** Logger */
	protected static transient CLogger log = CLogger.getCLogger(StorageEngine.class);

	public static void createTrasaction(IDocumentLine docLine, String MovementType, Timestamp MovementDate, BigDecimal Qty, boolean isReversal,
			int M_Warehouse_ID, int o_M_AttributeSetInstance_ID, int o_M_Warehouse_ID, boolean isSOTrx) {
		MProduct product = MProduct.get(docLine.getCtx(), docLine.getM_Product_ID());
		// Incoming Trx
		boolean incomingTrx = MovementType.charAt(1) == '+'; // V+ Vendor
																// Receipt

		if (product != null && product.isStocked()) {
			// Ignore the Material Policy when is Reverse Correction
			if (!isReversal) {
				checkMaterialPolicy(docLine, MovementType, MovementDate, M_Warehouse_ID);
			}

			// Reservation ASI
			int reservationAttributeSetInstance_ID = o_M_AttributeSetInstance_ID;
			//
			if (docLine.getM_AttributeSetInstance_ID() == 0) {
				IInventoryAllocation mas[] = StorageEngine.getMA(docLine);
				for (int j = 0; j < mas.length; j++) {
					IInventoryAllocation ma = mas[j];
					BigDecimal QtyMA = ma.getMovementQty();
					if (!incomingTrx) // C- Customer Shipment - V- Vendor Return
						QtyMA = QtyMA.negate();

					// Update Storage - see also VMatch.createMatchRecord
					if (!MUMStorage.add(docLine.getCtx(), M_Warehouse_ID, docLine.getM_Locator_ID(), docLine.getM_Product_ID(),
							ma.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID, QtyMA, Env.ZERO, Env.ZERO, docLine.get_TrxName())) {
						throw new IllegalStateException("Cannot correct Inventory (MA)"); // Cannot
																							// correct
																							// Inventory
																							// (MA)
					}
					create(docLine, MovementType, MovementDate, ma.getM_AttributeSetInstance_ID(), QtyMA);
				}
			}
			// sLine.getM_AttributeSetInstance_ID() != 0
			// if (mtrx == null)
			else {

				if (!incomingTrx) // C- Customer Shipment - V- Vendor Return
					Qty = Qty.negate();

				// Fallback: Update Storage - see also VMatch.createMatchRecord
				if (!MUMStorage.add(docLine.getCtx(), M_Warehouse_ID, docLine.getM_Locator_ID(), docLine.getM_Product_ID(),
						docLine.getM_AttributeSetInstance_ID(), reservationAttributeSetInstance_ID, Qty, Env.ZERO, Env.ZERO, docLine.get_TrxName())) {
					throw new IllegalStateException("Cannot correct Inventory (MA)"); // Cannot
																						// correct
																						// Inventory
																						// (MA)
				}
				create(docLine, MovementType, MovementDate, docLine.getM_AttributeSetInstance_ID(), Qty);
			}
		} // stock movement
	}

	private static void checkMaterialPolicy(IDocumentLine line, String MovementType, Timestamp MovementDate, int M_Warehouse_ID) {
		deleteMA(line);

		// Incoming Trx
		boolean incomingTrx = MovementType.charAt(1) == '+'; // V+ Vendor
																// Receipt
		MProduct product = MProduct.get(line.getCtx(), line.getM_Product_ID());

		// Need to have Location
		if (line.getM_Locator_ID() == 0) {
			// MWarehouse w = MWarehouse.get(getCtx(), getM_Warehouse_ID());
			// line.setM_Warehouse_ID(M_Warehouse_ID);
			// line.setM_Locator_ID(getM_Locator_ID(line.getCtx(),line.getM_Warehouse_ID(),
			// line.getM_Product_ID(),line.getM_AttributeSetInstance_ID(),
			// incomingTrx ? Env.ZERO : line.getMovementQty(),
			// line.get_TrxName()));
		}

		// Attribute Set Instance
		// Create an Attribute Set Instance to any receipt FIFO/LIFO
		if (line.getM_AttributeSetInstance_ID() == 0) {
			// Validate Transaction
			if (incomingTrx) {
				MAttributeSetInstance asi = null;
				// auto balance negative on hand
				MStorage[] storages = MUMStorage.getWarehouse(line.getCtx(), M_Warehouse_ID, line.getM_Product_ID(), 0, null,
						MClient.MMPOLICY_FiFo.equals(MUMProduct.getMMPolicy(product)), false, line.getM_Locator_ID(), line.get_TrxName());
				for (MStorage storage : storages) {
					if (storage.getQtyOnHand().signum() < 0) {
						asi = new MAttributeSetInstance(line.getCtx(), storage.getM_AttributeSetInstance_ID(), line.get_TrxName());
						break;
					}
				}
				// always create asi so fifo/lifo work.
				if (asi == null) {
					asi = MUMAttributeSetInstance.create(line.getCtx(), product, line.get_TrxName());
				}
				line.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
				log.config("New ASI=" + line);
				createMA(line, line.getM_AttributeSetInstance_ID(), line.getMovementQty());
			}// Create consume the Attribute Set Instance using policy FIFO/LIFO
			else {
				String MMPolicy = MUMProduct.getMMPolicy(product);
				Timestamp minGuaranteeDate = MovementDate;
				MStorage[] storages = MUMStorage.getWarehouse(line.getCtx(), M_Warehouse_ID, line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
						minGuaranteeDate, MClient.MMPOLICY_FiFo.equals(MMPolicy), true, line.getM_Locator_ID(), line.get_TrxName());
				BigDecimal qtyToDeliver = line.getMovementQty();
				for (MStorage storage : storages) {
					if (storage.getQtyOnHand().compareTo(qtyToDeliver) >= 0) {
						createMA(line, storage.getM_AttributeSetInstance_ID(), qtyToDeliver);
						qtyToDeliver = Env.ZERO;
					} else {
						createMA(line, storage.getM_AttributeSetInstance_ID(), storage.getQtyOnHand());
						qtyToDeliver = qtyToDeliver.subtract(storage.getQtyOnHand());
						log.fine("QtyToDeliver=" + qtyToDeliver);
					}

					if (qtyToDeliver.signum() == 0)
						break;
				}

				if (qtyToDeliver.signum() != 0) {
					// deliver using new asi
					MAttributeSetInstance asi = MUMAttributeSetInstance.create(line.getCtx(), product, line.get_TrxName());
					createMA(line, asi.getM_AttributeSetInstance_ID(), qtyToDeliver);
				}
			} // outgoing Trx
		} else {
			if (incomingTrx) {
				;
			} else {
				createMA(line, line.getM_AttributeSetInstance_ID(), line.getMovementQty());
			}
		}
		save(line);
	}

	private static String getTableNameMA(IDocumentLine model) {
		return model.get_TableName() + "MA";
	}

	private static int deleteMA(IDocumentLine model) {
		String sql = "DELETE FROM " + getTableNameMA(model) + " WHERE " + model.get_TableName() + "_ID=" + model.get_ID();
		int no = DB.executeUpdate(sql, model.get_TrxName());
		if (no > 0)
			log.config("Delete old #" + no);
		return no;
	}

	private static void saveMA(IInventoryAllocation ma) {
		((PO) ma).save();
	}

	private static void save(IDocumentLine line) {
		((PO) line).save();
	}

	private static void create(IDocumentLine model, String MovementType, Timestamp MovementDate, int M_AttributeSetInstance_ID, BigDecimal Qty) {
		/*
		 * MTransaction mtrx = new MTransaction (model.getCtx(),
		 * model.getAD_Org_ID(), MovementType, model.getM_Locator_ID(),
		 * model.getM_Product_ID(), M_AttributeSetInstance_ID, Qty,
		 * MovementDate, model.get_TrxName()); setReferenceLine_ID(mtrx, model);
		 */

		MTransaction mtrx = new MTransaction(model.getCtx(), 0, model.get_TrxName());
		mtrx.setAD_Org_ID(model.getAD_Org_ID());
		mtrx.setMovementType(MovementType);
		mtrx.setM_Locator_ID(model.getM_Locator_ID());
		mtrx.setM_Product_ID(model.getM_Product_ID());
		mtrx.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		mtrx.setMovementQty(Qty);
		mtrx.setMovementDate(MovementDate);

		setReferenceLine_ID(mtrx, model);

		mtrx.save();
	}

	private static IInventoryAllocation createMA(IDocumentLine model, int M_AttributeSetInstance_ID, BigDecimal MovementQty) {
		final Properties ctx = model.getCtx();
		final String tableName = getTableNameMA(model);
		final String trxName = model.get_TrxName();

		IInventoryAllocation ma = (IInventoryAllocation)M_Table.get(ctx,
		tableName).getPO(0, trxName);

		/*
		 * Migración Libero Verificar
		 * 
		 * @autor pepo
		 */

		ma.setAD_Org_ID(model.getAD_Org_ID());
		setReferenceLine_ID((PO) ma, model);
		ma.setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		ma.setMovementQty(MovementQty);

		saveMA(ma);
		log.fine("##: " + ma);

		return ma;
	}

	private static IInventoryAllocation[] getMA(IDocumentLine model) {
		final Properties ctx = model.getCtx();
		final String IDColumnName = model.get_TableName() + "_ID";
		final String tableName = getTableNameMA(model);
		final String trxName = model.get_TrxName();

		final String whereClause = IDColumnName + "=?";
		List<PO> list = new Query(ctx, tableName, whereClause, trxName).setClient_ID().setParameters(new Object[] { model.get_ID() }).setOrderBy(IDColumnName)
				.list();
		IInventoryAllocation[] arr = new IInventoryAllocation[list.size()];
		return list.toArray(arr);
	}

	private static void setReferenceLine_ID(PO model, IDocumentLine ref) {
		String refColumnName = ref.get_TableName() + "_ID";
		if (model.get_ColumnIndex(refColumnName) < 0) {
			throw new IllegalStateException("Invalid inventory document line " + ref);
		}
		model.set_ValueOfColumn(refColumnName, ref.get_ID());

	}

	/**
	 * Set (default) Locator based on qty.
	 * 
	 * @param Qty
	 *            quantity Assumes Warehouse is set
	 */
	public static int getM_Locator_ID(Properties ctx, int M_Warehouse_ID, int M_Product_ID, int M_AttributeSetInstance_ID, BigDecimal Qty, String trxName) {
		// Get existing Location
		int M_Locator_ID = MStorage.getM_Locator_ID(M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID, Qty, trxName);
		// Get default Location
		if (M_Locator_ID == 0) {
			MWarehouse wh = MWarehouse.get(ctx, M_Warehouse_ID);
			M_Locator_ID = wh.getDefaultLocator().getM_Locator_ID();
		}
		return M_Locator_ID;
	} // setM_Locator_ID

}
