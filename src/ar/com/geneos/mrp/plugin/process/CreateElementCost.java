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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openXpertya.model.MBPartner;
import org.openXpertya.model.MOrder;
import org.openXpertya.model.MOrderLine;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MProductPO;
import org.openXpertya.model.MRequisition;
import org.openXpertya.model.MRequisitionLine;
import org.openXpertya.model.POResultSet;
import org.openXpertya.model.Query;
import org.openXpertya.process.ProcessInfoParameter;
import org.openXpertya.process.SvrProcess;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.Msg;
import org.openXpertya.util.ValueNamePair;

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
		/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			//else if (name.equals("AD_Org_ID"))
			//	p_AD_Org_ID = para[i].getParameterAsInt();
			//else if (name.equals("M_Warehouse_ID"))
			//	p_M_Warehouse_ID = para[i].getParameterAsInt();
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
		return "";
	} // doit

	
} // RequisitionPOCreate
