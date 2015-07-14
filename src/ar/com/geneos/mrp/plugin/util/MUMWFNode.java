package ar.com.geneos.mrp.plugin.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.openXpertya.model.Query;
import org.openXpertya.wf.MWFNode;
import org.openXpertya.wf.MWorkflow;

public class MUMWFNode {

	/** WF Nodes */
	private List<MWFNode> m_nodes = new ArrayList<MWFNode>();

	/**
	 * Check if the workflow is valid for given date
	 * 
	 * @param date
	 * @return true if valid
	 */
	public static boolean isValidFromTo(MWFNode wfn, Timestamp date) {
		Timestamp validFrom = wfn.getValidFrom();
		Timestamp validTo = wfn.getValidTo();

		if (validFrom != null && date.before(validFrom))
			return false;
		if (validTo != null && date.after(validTo))
			return false;
		return true;
	}

	/**
	 * Get Workflow from Cache
	 *
	 * @param ctx
	 *            context
	 * @param AD_Workflow_ID
	 *            id
	 * @return workflow
	 */
	/*
	 * public static LP_M_WFNode get(Properties ctx, int AD_WFNode_ID) {
	 * LP_M_WFNode retValue = s_cache.get(AD_WFNode_ID); if (retValue != null)
	 * return retValue; retValue = new LP_M_WFNode(ctx, AD_WFNode_ID, null); if
	 * (retValue.getID() != 0) s_cache.put(AD_WFNode_ID, retValue); return
	 * retValue; } // get
	 */

}
