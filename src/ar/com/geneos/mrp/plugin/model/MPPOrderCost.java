package ar.com.geneos.mrp.plugin.model;
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



import java.sql.ResultSet;
import java.util.Properties; 
import org.openXpertya.model.MProduct;

import org.openXpertya.model.*;

/**
 * PP Order Cost Model.
 *
 * @author Victor Perez www.e-evolution.com     
 * @author Teo Sarca, www.arhipac.ro
 */
public class MPPOrderCost extends LP_PP_Order_Cost
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5350327491217294969L;
	private static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Create Order Cost Dimension based on Cost Dimension
	 * @param PP_Order_ID Manufacturing Order
	 * @param cost Cost Dimension
	 * @return Order Cost Dimension
	 */
	public static MPPOrderCost createOrderCostDimension(int PP_Order_ID, MCost cost)
	{
		// Check if we already added this cost dimension
		MPPOrderCost orderCostDimension = MPPOrderCost.getByCostDimension(PP_Order_ID,cost);
		if(orderCostDimension == null)
		{	
		   orderCostDimension = new MPPOrderCost(cost, PP_Order_ID, cost.get_TrxName());
		}
		else 
		{
			orderCostDimension.setCostDimension(cost, cost.get_TrxName());
		}
		orderCostDimension.save();
		return orderCostDimension;
	}
	
	/**
	 * get Order Cost Dimension 
	 * @param PP_Order_ID Manufacturing Order ID
	 * @param cost Cost Dimension
	 * @return MPPOrderCost Order Cost Dimension
	 */
	public static MPPOrderCost getByCostDimension(int PP_Order_ID , MCost cost)
	{
		final StringBuffer whereClause = new StringBuffer();
		whereClause.append(MPPOrderCost.COLUMNNAME_PP_Order_ID + "=? AND ");
		whereClause.append(MPPOrderCost.COLUMNNAME_AD_Org_ID+ "=? AND "); 
		whereClause.append(MPPOrderCost.COLUMNNAME_C_AcctSchema_ID + "=? AND ");
		whereClause.append(MPPOrderCost.COLUMNNAME_M_CostType_ID+ "=? AND "); 
		whereClause.append(MPPOrderCost.COLUMNNAME_M_CostElement_ID+ "=? AND "); 
		whereClause.append(MPPOrderCost.COLUMNNAME_M_Product_ID+ "=? AND "); 
		whereClause.append(MPPOrderCost.COLUMNNAME_M_AttributeSetInstance_ID+ "=? ");
		
		return new Query(cost.getCtx(), LP_PP_Order_Cost.Table_Name, whereClause.toString(), cost.get_TrxName())
		.setClient_ID()
		.setParameters(
				PP_Order_ID,
				cost.getAD_Org_ID(), 
				cost.getC_AcctSchema_ID(), 
				cost.getM_CostType_ID(), 
				cost.getM_CostElement_ID(), 
				cost.getM_Product_ID(), 
				cost.getM_AttributeSetInstance_ID())
		.firstOnly();
	}

	public MPPOrderCost(Properties ctx, int PP_Order_Cost_ID,String trxName)
	{
		super (ctx, PP_Order_Cost_ID, trxName);
	}	//	MOrder

	public MPPOrderCost(Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MOrder

	/**
	 * Peer constructor
	 * @param cost
	 * @param PP_Order_ID
	 */
	public MPPOrderCost(MCost cost, int PP_Order_ID, String trxName)
	{
		this(cost.getCtx(), 0, trxName);
		setPP_Order_ID(PP_Order_ID);
		setCostDimension(cost, trxName);
	}
	
	/*
	 *  Constructor para crear elemento de costos para la orden desde un elemento del colector de costos
	 *  Geneos
	 *  
	 */		
	
	public MPPOrderCost(MCost cost, MPPCostCollector costCollector,
			int PP_Order_ID, String trxName) {
		// TODO Auto-generated constructor stub
		this(cost.getCtx(), 0, trxName);
		setPP_Order_ID(PP_Order_ID);
		setCostDimensioncostCollector(cost, costCollector);		
	}

	/**
	 * Set Values from Cost Dimension  
	 * @param cost
	 * @param trxName 
	 */
	public void setCostDimension(MCost cost, String trxName)
	{
		setClientOrg(cost);
		setC_AcctSchema_ID(cost.getC_AcctSchema_ID());
		setM_CostType_ID(cost.getM_CostType_ID());
		setCumulatedAmt(cost.getCumulatedAmt());
		setCumulatedQty(cost.getCumulatedQty());
		setCurrentCostPrice(cost.getCurrentCostPrice());
		setCurrentCostPriceLL(cost.getCurrentCostPriceLL());
		setM_Product_ID(cost.getM_Product_ID());
		MProduct p = new MProduct(cost.getCtx(), cost.getM_Product_ID(), trxName);
		setDescription("Costo de Producto " + p.getValue() + " - " + p.getName());
		setM_AttributeSetInstance_ID(cost.getM_AttributeSetInstance_ID());
		setM_CostElement_ID(cost.getM_CostElement_ID());
	}
	
	/*
	 *  Crear dimensión de costos para la orden desde un elemento del colector de costos
	 *  Geneos
	 *  
	 */	

	/**
	 * Set Values from Cost Dimension from MPPCostCollector
	 * @param cost
	 * @param @param cost
	 */
	
	public static void createOrderCostDimensionFromCollector(int PP_Order_ID,
			MCost cost, MPPCostCollector costCollector) {
		// TODO Auto-generated method stub
		
		// Check if we already added this cost dimension
		MPPOrderCost orderCostDimension = MPPOrderCost.getByCostDimension(PP_Order_ID,cost);
		if(orderCostDimension == null) {	
		   orderCostDimension = new MPPOrderCost(cost, costCollector, PP_Order_ID, cost.get_TrxName());
		}
		
		orderCostDimension.setCostDimensioncostCollector(cost, costCollector);
		
		orderCostDimension.save();		
		
	}
	
	/**
	 * Set Values from Cost Dimension from Collector
	 * @param cost
	 * @param costCollector
	 */	

	private void setCostDimensioncostCollector(MCost cost,
			MPPCostCollector costCollector) {
		// TODO Auto-generated method stub
		setClientOrg(cost);
		setC_AcctSchema_ID(cost.getC_AcctSchema_ID());
		setM_CostType_ID(cost.getM_CostType_ID());
		setCumulatedAmt(cost.getCumulatedAmt());
		
		// En el contexto de un registro de actividad la cantidad es la duración de operación sobre el recurso
		setCurrentQty(costCollector.getDurationReal());
		// Estandarizar mensaje
		setDescription("Costo de Recursos");
		
		setCurrentCostPrice(cost.getCurrentCostPrice());
		setCurrentCostPriceLL(cost.getCurrentCostPriceLL());
		setM_Product_ID(cost.getM_Product_ID());
		setM_AttributeSetInstance_ID(cost.getM_AttributeSetInstance_ID());
		setM_CostElement_ID(cost.getM_CostElement_ID());		
		
	}


}
