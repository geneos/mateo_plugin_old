package ar.com.geneos.mrp.plugin.callout;

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
 *****************************************************************************/

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.openXpertya.model.MField;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MTab;
import org.openXpertya.model.MUOMConversion;
import org.openXpertya.plugin.CalloutPluginEngine;
import org.openXpertya.plugin.MPluginStatusCallout;
import org.openXpertya.util.Env;
import org.openXpertya.wf.MWorkflow;

import ar.com.geneos.mrp.plugin.model.MPPOrder;
import ar.com.geneos.mrp.plugin.model.MPPProductBOM;
import ar.com.geneos.mrp.plugin.model.MPPProductPlanning;
import ar.com.geneos.mrp.plugin.util.MUMWorkflow;

/**
 * Callout (Manufacturing) Order
 *
 * @author Victor Perez
 * @author Teo Sarca, www.arhipac.ro
 */
public class CalloutOrder extends CalloutPluginEngine {
	/** Debug Steps */
	private boolean steps = false;

	/**
	 * Order Line - Quantity. - called from C_UOM_ID, QtyEntered, QtyOrdered -
	 * enforces qty UOM relationship
	 * 
	 * @param ctx
	 *            Context
	 * @param WindowNo
	 *            current Window No
	 * @param mTab
	 *            Model Tab
	 * @param mField
	 *            Model Field
	 * @param value
	 *            The new value
	 */
	public MPluginStatusCallout qty(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		if (isCalloutActive() || (value == null)) {
			return state;
		}

		if (value == null)
			return state;

		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		if (steps)
			log.warning("qty - init - M_Product_ID=" + M_Product_ID + " - ");
		BigDecimal QtyOrdered = Env.ZERO;
		BigDecimal QtyEntered = Env.ZERO;

		// No Product
		if (M_Product_ID == 0) {
			QtyEntered = (BigDecimal) mTab.getValue("QtyEntered");
			mTab.setValue("QtyOrdered", QtyEntered);
		}
		// UOM Changed - convert from Entered -> Product
		else if (mField.getColumnName().equals("C_UOM_ID")) {

			int C_UOM_To_ID = ((Integer) value).intValue();
			QtyEntered = (BigDecimal) mTab.getValue("QtyEntered");
			QtyOrdered = MUOMConversion.convertProductFrom(ctx, M_Product_ID, C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		// QtyEntered changed - calculate QtyOrdered
		else if (mField.getColumnName().equals("QtyEntered")) {
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyEntered = (BigDecimal) value;
			QtyOrdered = MUOMConversion.convertProductFrom(ctx, M_Product_ID, C_UOM_To_ID, QtyEntered);
			if (QtyOrdered == null)
				QtyOrdered = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
			log.fine("qty - UOM=" + C_UOM_To_ID + ", QtyEntered=" + QtyEntered + " -> " + conversion + " QtyOrdered=" + QtyOrdered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyOrdered", QtyOrdered);
		}
		// QtyOrdered changed - calculate QtyEntered
		else if (mField.getColumnName().equals("QtyOrdered")) {
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyOrdered = (BigDecimal) value;
			QtyEntered = MUOMConversion.convertProductTo(ctx, M_Product_ID, C_UOM_To_ID, QtyOrdered);
			if (QtyEntered == null)
				QtyEntered = QtyOrdered;
			boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
			log.fine("qty - UOM=" + C_UOM_To_ID + ", QtyOrdered=" + QtyOrdered + " -> " + conversion + " QtyEntered=" + QtyEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyEntered", QtyEntered);
		}
		setCalloutActive(false);
		return qtyBatch(ctx, WindowNo, mTab, mField, value);
		// return "";
	} // qty

	public MPluginStatusCallout qtyBatch(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		int pp_order_id = (Integer) mTab.getValue("pp_order_id");

		MPPOrder order = new MPPOrder(Env.getCtx(), pp_order_id, null); // GridTabWrapper.create(mTab,
																		// I_PP_Order.class);

		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		if (isCalloutActive() || (value == null)) {
			return state;
		}

		// MPPOrder.updateQtyBatchs(ctx, order, true);
		BigDecimal qtyBatchSize = order.getQtyBatchSize();
		if (qtyBatchSize.signum() == 0 || true) {
			int AD_Workflow_ID = order.getAD_Workflow_ID();
			// No workflow entered, or is just a new record:
			if (AD_Workflow_ID <= 0)
				return state;
			;

			MWorkflow wf = MWorkflow.get(ctx, AD_Workflow_ID);
			qtyBatchSize = wf.getQtyBatchSize().setScale(0, RoundingMode.UP);
			mTab.setValue("QtyBatchSize", qtyBatchSize);// order.setQtyBatchSize(qtyBatchSize);
		}

		BigDecimal QtyBatchs;
		if (qtyBatchSize.signum() == 0)
			QtyBatchs = Env.ONE;
		else
			QtyBatchs = order.getQtyOrdered().divide(qtyBatchSize, 0, BigDecimal.ROUND_UP);
		mTab.setValue("QtyBatchSize", qtyBatchSize); // order.setQtyBatchs(QtyBatchs);

		setCalloutActive(false);
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		return state;
	}

	public MPluginStatusCallout product(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		if (isCalloutActive() || (value == null)) {
			return state;
		}
		int pp_order_id = (Integer) mTab.getValue("pp_order_id");
		MPPOrder order = new MPPOrder(Env.getCtx(), pp_order_id, null);// GridTabWrapper.create(mTab,
																		// I_PP_Order.class);
		MProduct product = MProduct.get(ctx, order.getM_Product_ID());
		if (product == null) {
			return state;
		}

		mTab.setValue("C_UOM_ID", product.getC_UOM_ID()); // order.setC_UOM_ID(product.getC_UOM_ID());

		MPPProductPlanning pp = getPP_Product_Planning(ctx, order);

		mTab.setValue("AD_Workflow_ID", pp.getAD_Workflow_ID()); // order.setAD_Workflow_ID(pp.getAD_Workflow_ID());
		mTab.setValue("PP_Product_BOM_ID", pp.getPP_Product_Bom_ID()); // order.setPP_Product_BOM_ID(pp.getPP_Product_Bom_ID());

		if (pp.getPP_Product_Bom_ID() > 0) {
			MPPProductBOM bom = pp.getPP_Product_BOM();
			mTab.setValue("C_UOM_ID", bom.getC_UOM_ID()); // order.setC_UOM_ID(bom.getC_UOM_ID());
		}

		// MPPOrder.updateQtyBatchs(ctx, order, true);
		BigDecimal qtyBatchSize = order.getQtyBatchSize();
		if (qtyBatchSize.signum() == 0 || true) {
			int AD_Workflow_ID = order.getAD_Workflow_ID();
			// No workflow entered, or is just a new record:
			if (AD_Workflow_ID <= 0)
				return state;
			;

			MWorkflow wf = MWorkflow.get(ctx, AD_Workflow_ID);
			qtyBatchSize = wf.getQtyBatchSize().setScale(0, RoundingMode.UP);
			mTab.setValue("QtyBatchSize", qtyBatchSize);// order.setQtyBatchSize(qtyBatchSize);
		}

		BigDecimal QtyBatchs;
		if (qtyBatchSize.signum() == 0)
			QtyBatchs = Env.ONE;
		else
			QtyBatchs = order.getQtyOrdered().divide(qtyBatchSize, 0, BigDecimal.ROUND_UP);
		mTab.setValue("QtyBatchSize", qtyBatchSize); // order.setQtyBatchs(QtyBatchs);

		return state;
	}

	/**
	 * Find Product Planning Data for given manufacturing order. If not planning
	 * found, a new one is created and filled with default values.
	 * <p>
	 * TODO: refactor with org.eevolution.process.MRP.getProductPlanning method
	 * 
	 * @param ctx
	 *            context
	 * @param order
	 *            manufacturing order
	 * @return product planning data (never return null)
	 */
	protected static MPPProductPlanning getPP_Product_Planning(Properties ctx, MPPOrder order) {
		MPPProductPlanning pp = MPPProductPlanning.find(ctx, order.getAD_Org_ID(), order.getM_Warehouse_ID(), order.getS_Resource_ID(),
				order.getM_Product_ID(), null);
		if (pp == null) {
			pp = new MPPProductPlanning(ctx, 0, null);
			pp.setAD_Org_ID(order.getAD_Org_ID());
			pp.setM_Warehouse_ID(order.getM_Warehouse_ID());
			pp.setS_Resource_ID(order.getS_Resource_ID());
			pp.setM_Product_ID(order.getM_Product_ID());
		}
		MProduct product = MProduct.get(ctx, pp.getM_Product_ID());
		//
		if (pp.getAD_Workflow_ID() <= 0) {
			pp.setAD_Workflow_ID(MUMWorkflow.getWorkflowSearchKey(product));
		}
		if (pp.getPP_Product_Bom_ID() <= 0) {
			MPPProductBOM bom = MPPProductBOM.getDefault(product, null);
			if (bom != null) {
				pp.setPP_Product_Bom_ID(bom.getPP_Product_Bom_ID());
			}
		}
		//
		return pp;
	}
} // CalloutOrder

