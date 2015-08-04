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
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/
package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.util.Collection;

import org.openXpertya.model.MClient;
import org.openXpertya.model.MForecastLine;
import org.openXpertya.model.MInOut;
import org.openXpertya.model.MInOutLine;
import org.openXpertya.model.MMovement;
import org.openXpertya.model.MOrder;
import org.openXpertya.model.MOrderLine;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MRequisition;
import org.openXpertya.model.MRequisitionLine;
import org.openXpertya.model.ModelValidationEngine;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;
import org.openXpertya.model.X_M_Forecast;
import org.openXpertya.process.DocAction;
import org.openXpertya.reflection.CallResult;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.Env;
import org.openXpertya.util.Msg;

import ar.com.geneos.mrp.plugin.util.MUColumnNames;
import ar.com.geneos.mrp.plugin.util.MUMInOut;
import ar.com.geneos.mrp.plugin.util.MUMOrderLine;

/**
 * Libero Validator
 *
 * @author Victor Perez
 * @author Trifon Trifonov <li>[ 2270421 ] Can not complete Shipment (Customer)</li>
 * @author Teo Sarca, www.arhipac.ro
 */
public class MRPValidator {
	
	/** Client */
	private int m_AD_Client_ID = -1;

	
	public static String modelChange(PO po, int type, CLogger log) {
		log.info(po.get_TableName() + " Type: " + type);
		boolean isChange = (ModelValidator.TYPE_AFTER_NEW == type || (ModelValidator.TYPE_AFTER_CHANGE == type && MPPMRP.isChanged(po)));
		boolean isDelete = (ModelValidator.TYPE_BEFORE_DELETE == type);
		boolean isReleased = false;
		boolean isVoided = false;

		// Update MRP Change Net
		if (MPPMRP.isChanged(po) && (ModelValidator.TYPE_AFTER_CHANGE == type || ModelValidator.TYPE_AFTER_NEW == type)) {
			MPPMRP.setIsRequired(po, MPPProductPlanning.COLUMNNAME_IsRequiredMRP, true, po.get_TrxName());
		}

		DocAction doc = null;
		if (po instanceof DocAction) {
			doc = (DocAction) po;
		} else if (po instanceof MOrderLine) {
			doc = MUMOrderLine.getParent((MOrderLine) po);
		}
		if (doc != null) {
			String docStatus = doc.getDocStatus();
			isReleased = DocAction.STATUS_InProgress.equals(docStatus) || DocAction.STATUS_Completed.equals(docStatus);
			isVoided = DocAction.STATUS_Voided.equals(docStatus);
		}
		//
		// Can we change M_Product.C_UOM_ID ?
		if (po instanceof MProduct && ModelValidator.TYPE_BEFORE_CHANGE == type && po.is_ValueChanged(MUColumnNames.COLUMNNAME_C_UOM_ID)
				&& MPPMRP.hasProductRecords((MProduct) po)) {
			return ("@SaveUomError@");
		}
		//
		//
		if (isDelete || isVoided || !po.isActive()) {
			if (MOrder.Table_Name.equals(po.get_TableName()) || MOrderLine.Table_Name.equals(po.get_TableName())
					|| MPPOrder.Table_Name.equals(po.get_TableName()) || MPPOrderBOMLine.Table_Name.equals(po.get_TableName())
					|| MRequisition.Table_Name.equals(po.get_TableName()) || MRequisitionLine.Table_Name.equals(po.get_TableName())
					|| X_M_Forecast.Table_Name.equals(po.get_TableName()) || MForecastLine.Table_Name.equals(po.get_TableName()))
				MPPMRP.deleteMRP(po);
		} else if (po instanceof MOrder) {
			MOrder order = (MOrder) po;
			// Create/Update a planning supply when isPurchase Order
			// or when you change DatePromised or DocStatus and is Purchase
			// Order
			if (isChange && !order.isSOTrx()) {
				MPPMRP.C_Order(order);
			}
			// Update MRP when you change the status order to complete or in
			// process for a sales order
			// or you change DatePromised
			else if (type == ModelValidator.TYPE_AFTER_CHANGE && order.isSOTrx()) {
				if (isReleased || MPPMRP.isChanged(order)) {
					MPPMRP.C_Order(order);
				}
			}
		}
		//
		else if (po instanceof MOrderLine && isChange) {
			MOrderLine ol = (MOrderLine) po;
			MOrder order = MUMOrderLine.getParent(ol);
			// Create/Update a planning supply when isPurchase Order or you
			// change relevant fields
			if (!order.isSOTrx()) {
				MPPMRP.C_OrderLine(ol);
			}
			// Update MRP when Sales Order have document status in process or
			// complete and
			// you change relevant fields
			else if (order.isSOTrx() && isReleased) {
				MPPMRP.C_OrderLine(ol);
			}
		}
		//
		else if (po instanceof MRequisition && isChange) {
			MRequisition r = (MRequisition) po;
			MPPMRP.M_Requisition(r);
		}
		//
		else if (po instanceof MRequisitionLine && isChange) {
			MRequisitionLine rl = (MRequisitionLine) po;
			MPPMRP.M_RequisitionLine(rl);
		}
		//
		else if (po instanceof X_M_Forecast && isChange) {
			X_M_Forecast fl = (X_M_Forecast) po;
			MPPMRP.M_Forecast(fl);
		}
		//
		else if (po instanceof MForecastLine && isChange) {
			MForecastLine fl = (MForecastLine) po;
			MPPMRP.M_ForecastLine(fl);
		}

		/**
		 * Libero to Libertya migration Distribution Order not implemented
		 */
		/*
		 * else if (po instanceof MDDOrder && isChange) { MDDOrder order =
		 * (MDDOrder) po; MPPMRP.DD_Order(order); }
		 * 
		 * // else if (po instanceof MDDOrderLine && isChange) { MDDOrderLine ol
		 * = (MDDOrderLine) po; MPPMRP.DD_OrderLine(ol); }
		 */
		//
		else if (po instanceof MPPOrder && isChange) {
			MPPOrder order = (MPPOrder) po;
			MPPMRP.PP_Order(order);
		}
		//
		else if (po instanceof MPPOrderBOMLine && isChange) {
			MPPOrderBOMLine obl = (MPPOrderBOMLine) po;
			MPPMRP.PP_Order_BOMLine(obl);
		}
		//
		return null;
	} // modelChange

	public String docValidate(PO po, int timing) {
		return null;
	}
	
	public static String docValidate(PO document, int timing, CLogger log) {
		log.info(document.get_TableName() + " Timing: " + timing);

		if (document instanceof MInOut && timing == ModelValidator.TIMING_AFTER_COMPLETE) {
			MInOut inout = (MInOut) document;
			if (inout.isSOTrx()) {
				for (MInOutLine outline : inout.getLines()) {
					updateMPPOrder(outline);

				}
			}
			// Purchase Receipt
			else {
				for (MInOutLine line : inout.getLines()) {
					final String whereClause = "C_OrderLine_ID=? AND PP_Cost_Collector_ID IS NOT NULL";
					Collection<MOrderLine> olines = new Query(document.getCtx(), MOrderLine.Table_Name, whereClause, document.get_TrxName()).setParameters(
							new Object[] { line.getC_OrderLine_ID() }).list();
					for (MOrderLine oline : olines) {
						if (oline.getQtyOrdered().compareTo(oline.getQtyDelivered()) >= 0) {
							MPPCostCollector cc = new MPPCostCollector(document.getCtx(), MUMOrderLine.getPP_Cost_Collector_ID(oline), document.get_TrxName());
							String docStatus = cc.completeIt();
							cc.setDocStatus(docStatus);
							cc.setDocAction(DocAction.ACTION_Close);
							cc.save();
							return null;
						}
					}
				}
			}
		}
		/**
		 * Libero to Libertya migration Distribution Order not Implemented
		 */
		// Update Distribution Order Line
		/*else if (po instanceof MMovement && timing == TIMING_AFTER_COMPLETE) {
			

			
			 * MMovement move = (MMovement) po; for (MMovementLine line :
			 * move.getLines(false)) {
			 * 
			 * 
			 * if (line.getDD_OrderLine_ID() > 0) { MDDOrderLine oline = new
			 * MDDOrderLine(line.getCtx(), line.getDD_OrderLine_ID(),
			 * po.get_TrxName()); MLocator locator_to =
			 * MLocator.get(line.getCtx(), line.getM_LocatorTo_ID()); MWarehouse
			 * warehouse = MWarehouse.get(line.getCtx(),
			 * locator_to.getM_Warehouse_ID()); if (warehouse.isInTransit()) {
			 * oline
			 * .setQtyInTransit(oline.getQtyInTransit().add(line.getMovementQty
			 * ())); oline.setConfirmedQty(Env.ZERO); } else {
			 * oline.setQtyInTransit
			 * (oline.getQtyInTransit().subtract(line.getMovementQty()));
			 * oline.setQtyDelivered
			 * (oline.getQtyDelivered().add(line.getMovementQty())); }
			 * oline.saveEx();
			 * 
			 * } }
			 * 
			 * if (move.getDD_Order_ID() > 0) { MDDOrder order = new
			 * MDDOrder(move.getCtx(), move.getDD_Order_ID(),
			 * move.get_TrxName()); order.setIsInTransit(isInTransting(order));
			 * order.reserveStock(order.getLines(true, null)); order.saveEx(); }
			 *

		}*/
		return null;
	} // docValidate

	private static void updateMPPOrder(MInOutLine outline) {
		MPPOrder order = null;
		BigDecimal qtyShipment = Env.ZERO;
		MInOut inout = MUMInOut.getParent(outline);
		String movementType = inout.getMovementType();
		int C_OrderLine_ID = 0;
		if (MInOut.MOVEMENTTYPE_CustomerShipment.equals(movementType)) {
			C_OrderLine_ID = outline.getC_OrderLine_ID();
			qtyShipment = outline.getMovementQty();
		}
		/**
		 * Libero to Libertya migration MRMALine (Para que se utilizaria?)
		 */
		/*
		 * else if (MInOut.MOVEMENTTYPE_CustomerReturns.equals(movementType)) {
		 * MRMALine rmaline = new MRMALine(outline.getCtx(),
		 * outline.getM_RMALine_ID(), null); MInOutLine line = (MInOutLine)
		 * rmaline.getM_InOutLine(); C_OrderLine_ID = line.getC_OrderLine_ID();
		 * qtyShipment = outline.getMovementQty().negate(); }
		 */

		final String whereClause = " C_OrderLine_ID = ? " + " AND DocStatus IN  (?,?)" + " AND EXISTS (SELECT 1 FROM  PP_Order_BOM "
				+ " WHERE PP_Order_BOM.PP_Order_ID=PP_Order.PP_Order_ID AND PP_Order_BOM.BOMType =? )";

		order = new Query(outline.getCtx(), LP_PP_Order.Table_Name, whereClause, outline.get_TrxName()).setParameters(
				new Object[] { C_OrderLine_ID, MPPOrder.DOCSTATUS_InProgress, MPPOrder.DOCSTATUS_Completed, MPPOrderBOM.BOMTYPE_Make_To_Kit }).firstOnly();
		if (order == null) {
			return;
		}

		if (MPPOrder.DOCSTATUS_InProgress.equals(order.getDocStatus())) {
			order.completeIt();
			order.setDocStatus(MPPOrder.ACTION_Complete);
			order.setDocAction(MPPOrder.DOCACTION_Close);
			order.save();
		}
		if (MPPOrder.DOCSTATUS_Completed.equals(order.getDocStatus())) {
			String description = order.getDescription() != null ? order.getDescription() : ""
					+ Msg.translate(inout.getCtx(), MUColumnNames.COLUMNNAME_M_InOut_ID) + " : "
					+ Msg.translate(inout.getCtx(), MUColumnNames.COLUMNNAME_DocumentNo);
			order.setDescription(description);
			order.updateMakeToKit(qtyShipment);
			order.save();
		}

		if (order.getQtyToDeliver().compareTo(Env.ZERO) == 0) {
			order.closeIt();
			order.setDocStatus(MPPOrder.DOCACTION_Close);
			order.setDocAction(MPPOrder.DOCACTION_None);
			order.save();
		}
		return;
	}

} // LiberoValidator
