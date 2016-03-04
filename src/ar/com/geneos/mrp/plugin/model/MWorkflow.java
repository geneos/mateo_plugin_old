package ar.com.geneos.mrp.plugin.model;

import java.util.Properties;

import org.openXpertya.model.PO;
import org.openXpertya.model.X_AD_WF_Node;
import org.openXpertya.plugin.MPluginPO;
import org.openXpertya.plugin.MPluginStatusPO;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.Env;
import org.openXpertya.wf.MWFNode;

public class MWorkflow extends MPluginPO {

	public MWorkflow(PO po, Properties ctx, String trxName, String aPackage) {
		super(po, ctx, trxName, aPackage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Ejecución posterior al beforeSave
	 * 
	 * @return estado del procesamiento
	 */

	public MPluginStatusPO preBeforeSave(PO po, boolean success) {
		
		org.openXpertya.wf.MWorkflow workflow = (org.openXpertya.wf.MWorkflow) po;
		
		if(workflow.getWorkflowType().equals("M")) {
		
			MWFNode[] nodes = workflow.getNodes(true);
			
			for(int i=0; i<nodes.length; i++) {
				MWFNode node = nodes[i];
				if(node.getS_Resource_ID() == 0) {
					status_po.setErrorMessage("Falta recurso en nodo del flujo de tipo manufactura.");
					status_po.setContinueStatus(MPluginStatusPO.STATE_FALSE);
					return status_po;
				}			
			}
		}
		
		return status_po;
		
	}

}