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
//package org.compiere.mfg.model;
package ar.com.geneos.mrp.plugin.callout;

import java.math.BigDecimal;
import java.util.Properties;

import org.openXpertya.model.CalloutEngine;
import org.openXpertya.model.MField;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MTab;
import org.openXpertya.model.MUOMConversion;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.model.MPPOrderBOMLine;
import ar.com.geneos.mrp.plugin.model.MPPProductBOM;
import ar.com.geneos.mrp.plugin.model.MPPProductBOMLine;
import ar.com.geneos.mrp.plugin.util.MUMProduct;

/**
 * BOM Callouts
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro <li>BF [ 2820743 ] CalloutBOM - apply ABP
 *         https
 *         ://sourceforge.net/tracker/?func=detail&aid=2820743&group_id=176962
 *         &atid=934929
 */
public class CalloutBOM extends CalloutEngine {

	/**
	 * Parent cycle check and BOM Line defaults.
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

	public String parent(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		if (isCalloutActive() || value == null)
			return "";

		final int M_Product_ID = (Integer) value;
		if (M_Product_ID <= 0)
			return "";

		int Product_bom_ID = (Integer) mTab.getField(MPPProductBOMLine.COLUMNNAME_PP_Product_BOM_ID).getValue();

		//MPPProductBOMLine bomLine = new MPPProductBOMLine(Env.getCtx(), Product_bomLine_ID, null);
		MPPProductBOM bom = new MPPProductBOM(Env.getCtx(), Product_bom_ID, null);
/*
		if (bom == null) // Adempiere-272 changes
		{
			throw new RuntimeException("Please save header record first.");

		}
*/
		if (bom.getM_Product_ID() == M_Product_ID) {
			throw new RuntimeException("@ValidComponent@ - Error Parent not be Component");

		}
		// Set BOM Line defaults

		MProduct product = MProduct.get(ctx, M_Product_ID); // May be the
															// parent;
		mTab.setValue("Description", product.getDescription()); // bomLine.setDescription(product.getDescription());
		mTab.setValue("Help", product.getHelp()); // bomLine.setHelp(product.getHelp());
		mTab.setValue("C_UOM_ID", product.getC_UOM_ID()); // bomLine.setC_UOM_ID(product.getC_UOM_ID());
		mTab.setValue("M_AttributeSetInstance_ID",
				MUMProduct.getEnvAttributeSetInstance(product, ctx, WindowNo) == null ? 0 : MUMProduct.getEnvAttributeSetInstance(product, ctx, WindowNo)); // bomLine.setM_AttributeSetInstance_ID(MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo)
																																							// ==
																																							// null
																																							// ?
																																							// 0
																																							// :
																																							// MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo)
																																							// );
		return "";
	}

	public String qtyLine(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		if (isCalloutActive() || value == null)
			return "";

		int bomLine_ID = (Integer) mTab.getField("PP_Order_BOMLine_ID").getValue();
		final MPPOrderBOMLine bomLine = new MPPOrderBOMLine(Env.getCtx(), bomLine_ID, null); // GridTabWrapper.create(mTab,
																								// I_PP_Order_BOMLine.class);
		final int M_Product_ID = bomLine.getM_Product_ID();
		final String columnName = mField.getColumnName();

		// No Product
		if (M_Product_ID <= 0) {
			BigDecimal QtyEntered = (BigDecimal) mTab.getField("QtyEntered").getValue();//bomLine.getQtyEntered();
			mTab.setValue("qtyRequired", QtyEntered); // bomLine.setQtyRequired(QtyEntered);
		}
		// UOM Changed - convert from Entered -> Product
		// QtyEntered changed - calculate QtyOrdered
		else if (MPPOrderBOMLine.COLUMNNAME_C_UOM_ID.equals(columnName) || MPPOrderBOMLine.COLUMNNAME_QtyEntered.equals(columnName)) {
			final BigDecimal QtyEntered = (BigDecimal) mTab.getField("QtyEntered").getValue();//bomLine.getQtyEntered();
			BigDecimal QtyRequired = MUOMConversion.convertProductFrom(ctx, M_Product_ID, bomLine.getC_UOM_ID(), QtyEntered);
			if (QtyRequired == null) // NO Conversion Found
				QtyRequired = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyRequired) != 0;
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion);
			mTab.setValue("qtyRequired", QtyEntered); // bomLine.setQtyRequired(QtyEntered);
		}
		return "";
	} // qty

	/**
	 * getdefaults get defaults for Product (search key, name, description, help
	 * and UOM)
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

	public String getdefaults(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		if (isCalloutActive() || value == null)
			return "";
		int M_Product_ID = (Integer) value;
		if (M_Product_ID <= 0)
			return "";

		MProduct product = MProduct.get(ctx, M_Product_ID);
		//int product_bom_ID = (Integer) mTab.getField("PP_Product_BOM_ID").getValue();
		//MPPProductBOM bom = new MPPProductBOM(Env.getCtx(), product_bom_ID, null);// GridTabWrapper.create(mTab,
																					// I_PP_Product_BOM.class);

		mTab.setValue("Value", product.getValue()); // bom.setValue(product.getValue());
		mTab.setValue("Name", product.getName()); // bom.setName(product.getName());
		mTab.setValue("Description", product.getDescription());// bom.setDescription(product.getDescription());
		mTab.setValue("Help", product.getHelp());// bom.setHelp(product.getHelp());
		mTab.setValue("C_UOM_ID", product.getC_UOM_ID()); // bomLine.setC_UOM_ID(product.getC_UOM_ID());
		mTab.setValue("M_AttributeSetInstance_ID",
				MUMProduct.getEnvAttributeSetInstance(product, ctx, WindowNo) == null ? 0 : MUMProduct.getEnvAttributeSetInstance(product, ctx, WindowNo)); // bomLine.setM_AttributeSetInstance_ID(MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo)
																																							// ==
																																							// null
																																							// ?
																																							// 0
																																							// :
																																							// MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo)
																																							// );

		return "";
	} // getdefaults
} // CalloutOrder

