package ar.com.geneos.mrp.plugin.util;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.openXpertya.model.MProduct;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;
import org.openXpertya.wf.MWFNode;
import org.openXpertya.wf.MWorkflow;

public class MUMWorkflow {

	/**
	 * Check if the workflow is valid for given date
	 * 
	 * @param date
	 * @return true if valid
	 */
	public static boolean isValidFromTo(MWorkflow wf, Timestamp date) {
		Timestamp validFrom = wf.getValidFrom();
		Timestamp validTo = wf.getValidTo();

		if (validFrom != null && date.before(validFrom))
			return false;
		if (validTo != null && date.after(validTo))
			return false;
		return true;
	}

	/**
	 * Get the nodes
	 * 
	 * @param ordered
	 *            ordered array
	 * @param AD_Client_ID
	 *            for client
	 * @return array of nodes
	 */
	public static MWFNode[] getNodes(MWorkflow wf, boolean ordered, int AD_Client_ID) {

		ArrayList<MWFNode> list = new ArrayList<MWFNode>();
		MWFNode[] m_nodes = wf.getNodes(ordered);

		for (int i = 0; i < m_nodes.length; i++) {
			MWFNode node = m_nodes[i];
			if (!node.isActive())
				continue;
			if (node.getAD_Client_ID() == 0 || node.getAD_Client_ID() == AD_Client_ID)
				list.add(node);
		}
		MWFNode[] retValue = new MWFNode[list.size()];
		list.toArray(retValue);
		return retValue;
	} // getNodes

	/**
	 * Get AD_Workflow_ID for given M_Product_ID
	 * @param M_Product_ID
	 * @return AD_Workflow_ID
	 */
	public static int getWorkflowSearchKey(MProduct product)
	{
		int AD_Client_ID = Env.getAD_Client_ID(product.getCtx());
		String sql = "SELECT AD_Workflow_ID FROM AD_Workflow "
						+" WHERE Value = ? AND AD_Client_ID = ?";
		return DB.getSQLValueEx(null, sql, product.getValue(), AD_Client_ID);
	}

}
