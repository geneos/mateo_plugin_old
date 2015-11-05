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
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/

import java.math.BigDecimal;
import java.util.Properties;

import org.openXpertya.model.MField;
import org.openXpertya.model.MTab;
import org.openXpertya.plugin.CalloutPluginEngine;
import org.openXpertya.plugin.MPluginStatusCallout;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.model.MPPCostCollector;
import ar.com.geneos.mrp.plugin.model.MPPOrder;
import ar.com.geneos.mrp.plugin.model.MPPOrderNode;
import ar.com.geneos.mrp.plugin.model.RoutingService;
import ar.com.geneos.mrp.plugin.model.RoutingServiceFactory;

/**
 * Cost Collector Callout
 *
 * @author Victor Perez www.e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class CalloutCostCollector extends CalloutPluginEngine {

	public MPluginStatusCallout order(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		if (isCalloutActive() || (value == null)) {
			return state;
		}

		Integer PP_Order_ID = (Integer) value;
		if (PP_Order_ID == null || PP_Order_ID <= 0)
			return state;

		MPPOrder pp_order = new MPPOrder(ctx, PP_Order_ID, null);
		
		mTab.setValue("Description", pp_order.getDescription());
		mTab.setValue("C_Resource_ID", pp_order.getS_Resource_ID());
		System.out.println("S_Resource_ID: " + pp_order.getS_Resource_ID());
		mTab.setValue("M_Product_ID", pp_order.getM_Product_ID());
		mTab.setValue("PP_Order_Workflow_ID", pp_order.getMPPOrderWorkflow().getPP_Order_Workflow_ID());
		System.out.println("PP_Order_Workflow_ID: " + pp_order.getAD_Workflow_ID());
		mTab.setValue("C_UOM_ID", pp_order.getC_UOM_ID());
		mTab.setValue("costcollectortype", "160");
		
		
		setCalloutActive(false);
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		return state;
	}

	public MPluginStatusCallout node(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		if (isCalloutActive() || (value == null)) {
			return state;
		}

		Integer PP_Order_Node_ID = (Integer) value;
		if (PP_Order_Node_ID == null || PP_Order_Node_ID <= 0)
			return state;

		//int cost_collector_id = (Integer) mTab.getValue("PP_Cost_Collector_ID");
		//MPPCostCollector cc = new MPPCostCollector(Env.getCtx(), cost_collector_id, null);// GridTabWrapper.create(mTab,
																							// I_PP_Cost_Collector.class);
		//
		MPPOrderNode node = getPP_Order_Node(ctx, PP_Order_Node_ID);
		mTab.setValue("S_Resource_ID", node.getS_Resource_ID()); // cc.setS_Resource_ID(node.getS_Resource_ID());
		mTab.setValue("IsSubcontracting", node.isSubcontracting()); // cc.setIsSubcontracting(node.isSubcontracting());
		mTab.setValue("MovementQty", node.getQtyToDeliver()); // cc.setMovementQty(node.getQtyToDeliver());
		//
		setCalloutActive(false);
		//duration(ctx, WindowNo, mTab, mField, value);
		//
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		return state;
	}

	public MPluginStatusCallout duration(Properties ctx, int WindowNo, MTab mTab, MField mField, Object value) {
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		if (isCalloutActive() || (value == null)) {
			return state;
		}

		int cost_collector_id = (Integer) mTab.getValue("PP_Cost_Collector_ID");
		MPPCostCollector cc = new MPPCostCollector(Env.getCtx(), cost_collector_id, null);// GridTabWrapper.create(mTab,
																							// I_PP_Cost_Collector.class);

		if (cc.getPP_Order_Node_ID() <= 0)
			return state;

		RoutingService routingService = RoutingServiceFactory.get().getRoutingService(ctx);
		BigDecimal durationReal = routingService.estimateWorkingTime(cc);
		
		// If Activity Control Duration should be specified
		// FIXME: this message is really anoying. We need to find a proper
		// solution - teo_sarca
		// if(durationReal.signum() == 0)
		// {
		// throw new
		// FillMandatoryException(MPPOrderNode.COLUMNNAME_SetupTimeReal,
		// MPPOrderNode.COLUMNNAME_DurationReal);
		// }
		//
		
		mTab.setValue("DurationReal", durationReal); // cc.setDurationReal(durationReal);
		
		//
		setCalloutActive(false);
		state.setContinueStatus(MPluginStatusCallout.STATE_TRUE_AND_SKIP);
		return state;
	}

	private MPPOrderNode m_node = null;

	private MPPOrderNode getPP_Order_Node(Properties ctx, int PP_Order_Node_ID) {
		if (m_node != null && m_node.getID() == PP_Order_Node_ID) {
			return m_node;
		}
		m_node = new MPPOrderNode(ctx, PP_Order_Node_ID, null);
		return m_node;
	}

}
