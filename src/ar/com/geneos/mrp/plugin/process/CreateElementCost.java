/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package ar.com.geneos.mrp.plugin.process;

import java.math.BigDecimal;
import java.util.logging.Level;

import org.openXpertya.model.MInvoice;
import org.openXpertya.process.ProcessInfoParameter;
import org.openXpertya.process.SvrProcess;

import ar.com.geneos.mrp.plugin.model.MPPOrderCost;

/**
 * Create PO from Requisition
 *
 *
 * @author Jorg Janke
 * @version $Id: RequisitionPOCreate.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro <li>BF [ 2609760 ] RequisitionPOCreate not
 *         using DateRequired <li>BF [ 2605888 ] CreatePOfromRequisition creates
 *         more PO than needed <li>BF [ 2811718 ] Create PO from Requsition
 *         without any parameter teminate in NPE
 *         http://sourceforge.net/tracker/?
 *         func=detail&atid=879332&aid=2811718&group_id=176962 <li>FR [ 2844074
 *         ] Requisition PO Create - more selection fields
 *         https://sourceforge.net
 *         /tracker/?func=detail&aid=2844074&group_id=176962&atid=879335
 */
public class CreateElementCost extends SvrProcess {
	
	int p_PP_Order_ID = 0;
	int p_M_Cost_Type_ID = 0;
	int p_M_CostElement_ID = 0;
	int p_C_Invoice_ID = 0;
	int p_Cost = 0;
	int p_C_AcctSchema_ID = 0;
	String p_Description = "";
	
	
	/**
	 * Prepare - e.g., get Parameters.
	 * 
	 * @PP_Order_ID define una OM en concreto, o si se deja en blanco todas las OM.
	 * @date aplica a todas las OM en el rango de fechas
	 * 
	 */
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_AcctSchema_ID"))
				p_C_AcctSchema_ID = para[i].getParameterAsInt();
			else if (name.equals("M_CostType_ID"))
				p_M_Cost_Type_ID = para[i].getParameterAsInt();
			else if (name.equals("PP_Order_ID"))
				p_PP_Order_ID = para[i].getParameterAsInt();
			else if (name.equals("M_CostElement_ID"))
				p_M_CostElement_ID = para[i].getParameterAsInt();
			else if (name.equals("Descrption"))
				p_Description = para[i].valueToString();	
			else if (name.equals("C_Invoice_ID"))
				p_C_Invoice_ID = para[i].getParameterAsInt();
			else if (name.equals("Cost"))
				p_Cost = para[i].getParameterAsInt();			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	} // prepare

	/**
	 * Process
	 *
	 * @return info
	 * @throws Exception
	 */
	protected String doIt() throws Exception {
		
		MPPOrderCost orderCostDimension = new MPPOrderCost(this.getCtx(),0,this.get_TrxName());
		orderCostDimension.setC_AcctSchema_ID(p_C_AcctSchema_ID);
		orderCostDimension.setM_CostElement_ID(p_M_CostElement_ID);
		orderCostDimension.setM_CostType_ID(p_M_Cost_Type_ID);
		orderCostDimension.setPP_Order_ID(p_PP_Order_ID);
		orderCostDimension.setDescription(p_Description);
		if(p_C_Invoice_ID != 0) {
			MInvoice inv = new MInvoice(this.getCtx(),p_C_Invoice_ID,this.get_TrxName());		
			orderCostDimension.setCurrentCostPrice(inv.getGrandTotal());	
		} else if(p_Cost != 0) {		
			orderCostDimension.setCurrentCostPrice(BigDecimal.valueOf(p_Cost));	
		} else {
			throw new RuntimeException("@NotCostAvailable@");
		}
			 
		orderCostDimension.save();
		
		return "@OK@";
	} // doit

	
} // RequisitionPOCreate
