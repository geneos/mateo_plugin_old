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

import java.util.Properties;

import org.openXpertya.model.CalloutEngine;
import org.openXpertya.model.MField;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MTab;

/**
 * Callout Forecast
 *
 * @author Cooperativa Geneos
 */
public class CalloutForecast extends CalloutEngine {

	/**
	 * product - called from M_Product_ID
	 * sets UOM in Forecast Line
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
	public String product(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {

		if (value == null)
			return "";

		int M_Product_ID = (Integer) value;

		// No Product
		if (M_Product_ID == 0) {
			return "";
		}

		MProduct product = new MProduct(ctx, M_Product_ID, null);

		mTab.setValue("C_UOM_ID", product.getC_UOM_ID());
		return "";
	} // product

} // CalloutForecast

