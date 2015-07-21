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
import org.openXpertya.plugin.CalloutPluginEngine;
import org.openXpertya.plugin.MPluginStatusCallout;
import org.openXpertya.util.Env;
import org.openXpertya.util.Msg;

import ar.com.geneos.mrp.plugin.model.LP_PP_Product_BOM;
import ar.com.geneos.mrp.plugin.model.LP_PP_Product_BOMLine;
import ar.com.geneos.mrp.plugin.model.MPPOrderBOMLine;
import ar.com.geneos.mrp.plugin.model.MPPProductBOM;
import ar.com.geneos.mrp.plugin.model.MPPProductBOMLine;
import ar.com.geneos.mrp.plugin.util.MUMProduct;



/**
 * BOM Callouts
 *	
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 * 			<li>BF [ 2820743 ] CalloutBOM - apply ABP
 * 				https://sourceforge.net/tracker/?func=detail&aid=2820743&group_id=176962&atid=934929  
 */
public class CalloutBOM extends CalloutPluginEngine
{
	
	/**
	 *	Parent cycle check and BOM Line defaults.
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 */
	
	public MPluginStatusCallout parent (Properties ctx,int WindowNo,MTab mTab,MField mField,Object value)
	{
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
    	if( isCalloutActive() || (value == null) ) {
            return state;
        }
		final int M_Product_ID = (Integer)value;
		if (M_Product_ID <= 0)
			return state;
		int Product_bomLine_ID = (Integer) mTab.getField(MPPProductBOMLine.COLUMNNAME_PP_Product_BOMLine_ID).getValue();
		
		MPPProductBOMLine bomLine = new MPPProductBOMLine(Env.getCtx(),Product_bomLine_ID,null);
		MPPProductBOM bom = new MPPProductBOM(Env.getCtx(),bomLine.getPP_Product_BOM_ID(),null);

		if( bom == null ) //Adempiere-272 changes
		{
			setCalloutActive(false);
        	Msg.getMsg(ctx,"Please save header record first.", new Object[] {Msg.translate(ctx,"Please save header record first.")});
        	state.setContinueStatus(MPluginStatusCallout.STATE_FALSE);
        	return state;
		}    

		if (bom.getM_Product_ID() ==  bomLine.getM_Product_ID())
		{                                                                               
			setCalloutActive(false);
        	Msg.getMsg(ctx,"@ValidComponent@", new Object[] {Msg.translate(ctx,"@ValidComponent@ - Error Parent not be Component")});
        	state.setContinueStatus(MPluginStatusCallout.STATE_FALSE);
        	return state;
		}
		// Set BOM Line defaults
		
		MProduct product = MProduct.get(ctx, M_Product_ID);  // May be the parent;
		mTab.setValue("description",product); //bomLine.setDescription(product.getDescription());
		mTab.setValue("help",product.getHelp()); //bomLine.setHelp(product.getHelp());
		mTab.setValue("C_UOM_ID",product.getC_UOM_ID()); //bomLine.setC_UOM_ID(product.getC_UOM_ID());
		mTab.setValue("M_AttributeSetInstance_ID",MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo) == null ? 0 : MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo)); //bomLine.setM_AttributeSetInstance_ID(MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo) == null ? 0 : MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo) );
		setCalloutActive( false );
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		return state;
	}
        
    public MPluginStatusCallout qtyLine (Properties ctx,int WindowNo,MTab mTab,MField mField,Object value)
	{
    	state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
    	if( isCalloutActive() || (value == null) ) {
            return state;
        }

		int bomLine_ID = (Integer) mTab.getField("pp_order_bomline_id").getValue();
		final MPPOrderBOMLine bomLine = new MPPOrderBOMLine(Env.getCtx(),bomLine_ID,null); //GridTabWrapper.create(mTab, I_PP_Order_BOMLine.class);
		final int M_Product_ID = bomLine.getM_Product_ID();
		final String columnName = mField.getColumnName();
		
		//	No Product
		if (M_Product_ID <= 0)
		{
			BigDecimal QtyEntered = bomLine.getQtyEntered();
			mTab.setValue("qtyRequired",QtyEntered); //bomLine.setQtyRequired(QtyEntered);
		}
		//	UOM Changed - convert from Entered -> Product
		//	QtyEntered changed - calculate QtyOrdered
		else if (MPPOrderBOMLine.COLUMNNAME_C_UOM_ID.equals(columnName)
			|| MPPOrderBOMLine.COLUMNNAME_QtyEntered.equals(columnName) )
		{
			final BigDecimal QtyEntered = bomLine.getQtyEntered();
			BigDecimal QtyRequired = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
					bomLine.getC_UOM_ID(), QtyEntered);
			if (QtyRequired == null) // NO Conversion Found
				QtyRequired = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyRequired) != 0;
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion);
			mTab.setValue("qtyRequired",QtyEntered); //bomLine.setQtyRequired(QtyEntered);
		}
		setCalloutActive( false );
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		return state; 
	}	//	qty
    
	/**
	 *	getdefaults   
	 *  get defaults for Product (search key, name, description, help and UOM)
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 */
    
	public MPluginStatusCallout getdefaults (Properties ctx,int WindowNo,MTab mTab,MField mField,Object value)
	{
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
    	if( isCalloutActive() || (value == null) ) {
            return state;
        }
    	
		int M_Product_ID = (Integer)value;
		if (M_Product_ID <= 0)
			return state;
		
        MProduct product =  MProduct.get(ctx, M_Product_ID);
        int product_bom_ID = (Integer) mTab.getField("pp_product_bom_id").getValue();
        MPPProductBOM bom = new MPPProductBOM(Env.getCtx(),product_bom_ID,null);//GridTabWrapper.create(mTab, I_PP_Product_BOM.class);
        
        mTab.setValue("value",product.getValue()); //bom.setValue(product.getValue());
        mTab.setValue("name",product.getName()); //bom.setName(product.getName());
        mTab.setValue("description",product.getDescription());//bom.setDescription(product.getDescription());
        mTab.setValue("help",product.getHelp());//bom.setHelp(product.getHelp());
		mTab.setValue("C_UOM_ID",product.getC_UOM_ID()); //bomLine.setC_UOM_ID(product.getC_UOM_ID());
		mTab.setValue("M_AttributeSetInstance_ID",MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo) == null ? 0 : MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo)); //bomLine.setM_AttributeSetInstance_ID(MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo) == null ? 0 : MUMProduct.getEnvAttributeSetInstance(product,ctx,WindowNo) );

		setCalloutActive( false );
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		return state; 
	}	//	getdefaults 
}	//	CalloutOrder

