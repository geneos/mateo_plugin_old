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
package ar.com.geneos.mrp.plugin.process;


import java.util.logging.Level;

import org.openXpertya.model.MQuery;
import org.openXpertya.model.MTable;
import org.openXpertya.model.PrintInfo;
import org.openXpertya.print.MPrintFormat;
import org.openXpertya.print.ReportCtl;
import org.openXpertya.print.ReportEngine;
//import org.openXpertya.process.ClientProcess;
import org.openXpertya.process.ProcessInfoParameter;
import org.openXpertya.process.SvrProcess;

import ar.com.geneos.mrp.plugin.model.MPPOrder;
import ar.com.geneos.mrp.plugin.util.MUMPrintFormat;


/**
 * Complete & Print Manufacturing Order
 * @author victor.perez@e-evolution.com
 * @author Teo Sarca, www.arhipac.ro
 */
public class CompletePrintOrder extends SvrProcess {
	/** The Order */
	private int p_PP_Order_ID = 0;
	private boolean p_IsPrintPickList = false;
	private boolean p_IsPrintWorkflow = false;
	@SuppressWarnings("unused")
	private boolean p_IsPrintPackList = false; // for future use
	private boolean p_IsComplete = false;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParameter())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if (name.equals("PP_Order_ID"))
				p_PP_Order_ID = para.getParameterAsInt();
			else if (name.equals("IsPrintPickList"))
				p_IsPrintPickList = "Y".equals(para.getParameter());
			else if (name.equals("IsPrintWorkflow"))
				p_IsPrintWorkflow = "Y".equals(para.getParameter());
			else if (name.equals("IsPrintPackingList"))
				p_IsPrintPackList = "Y".equals(para.getParameter());
			else if (name.equals("IsComplete"))
				p_IsComplete = "Y".equals(para.getParameter());
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	} // prepare

	/**
	 * Perform process.
	 * 
	 * @return Message (clear text)
	 * @throws Exception
	 *             if not successful
	 */
	protected String doIt() throws Exception
	{

		if (p_PP_Order_ID == 0)
		{
			throw new RuntimeException(MPPOrder.COLUMNNAME_PP_Order_ID);
		}

		if (p_IsComplete)
		{
			MPPOrder order = new MPPOrder(getCtx(), p_PP_Order_ID, get_TrxName());
			if (!order.isAvailable())
			{
				throw new RuntimeException("@NoQtyAvailable@");
			}
			//
			// Process document
			boolean ok = order.processIt(MPPOrder.ACTION_Complete);
			order.save();
			if (!ok)
			{
				throw new RuntimeException(order.getProcessMsg());
			}
			//
			// Document Status should be completed
			if (!MPPOrder.DOCSTATUS_Completed.equals(order.getDocStatus()))
			{
				throw new RuntimeException(order.getProcessMsg());
			}
		}

		if (p_IsPrintPickList)
		{
			// Get Format & Data
			ReportEngine re = this.getReportEngine("Manufacturing_Order_BOM_Header ** TEMPLATE **","PP_Order_BOM_Header_v");
			if(re == null )
			{
				return "";
			}
			ReportCtl.preview(re);
			re.print(); // prints only original
		}
		if (p_IsPrintPackList)
		{
			// Get Format & Data
			ReportEngine re = this.getReportEngine("Manufacturing_Order_BOM_Header_Packing ** TEMPLATE **","PP_Order_BOM_Header_v");
			if(re == null )
			{
				return "";
			}
			ReportCtl.preview(re);
			re.print(); // prints only original
		}
		if (p_IsPrintWorkflow)
		{
			// Get Format & Data
			ReportEngine re = this.getReportEngine("Manufacturing_Order_Workflow_Header ** TEMPLATE **","PP_Order_Workflow_Header_v");
			if(re == null )
			{
				return "";
			}
			ReportCtl.preview(re);
			re.print(); // prints only original
		}

		return "@OK@";

	} // doIt
	
	/*
	 * get the a Report Engine Instance using the view table 
	 * @param tableName
	 */
	private ReportEngine getReportEngine(String formatName, String tableName)
	{
		// Get Format & Data
		int format_id= MUMPrintFormat.getPrintFormatID(formatName, MTable.getTable_ID(tableName), getAD_Client_ID());
				
		MPrintFormat format = MPrintFormat.get(getCtx(), format_id, true);
		if (format == null)
		{
			addLog (0, null, null, "@NotFound@ @AD_PrintFormat_ID@");
			return null;
		}
		// query
		MQuery query = new MQuery(tableName);
		query.addRestriction("PP_Order_ID", MQuery.EQUAL, p_PP_Order_ID);
		// Engine
		PrintInfo info = new PrintInfo(tableName,  MTable.getTable_ID(tableName), p_PP_Order_ID);
		ReportEngine re = new ReportEngine(getCtx(), format, query, info);
		return re;
	}
} // CompletePrintOrder
