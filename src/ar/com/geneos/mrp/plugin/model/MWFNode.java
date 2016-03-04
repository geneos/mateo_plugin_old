package ar.com.geneos.mrp.plugin.model;

import java.util.Properties;

import org.openXpertya.model.PO;
import org.openXpertya.model.X_AD_WF_Node;
import org.openXpertya.plugin.MPluginPO;
import org.openXpertya.plugin.MPluginStatusPO;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.Env;
import org.openXpertya.wf.MWorkflow;

public class MWFNode extends MPluginPO {

	public MWFNode(PO po, Properties ctx, String trxName, String aPackage) {
		super(po, ctx, trxName, aPackage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Ejecución posterior al beforeSave
	 * 
	 * @return estado del procesamiento
	 */

	public MPluginStatusPO preBeforeSave(PO po, boolean success) {
		
		X_AD_WF_Node node = (X_AD_WF_Node) po;
		 
		int workflow_ID = node.getAD_Workflow_ID();
		
		MWorkflow workflow = new MWorkflow(this.m_ctx, workflow_ID, this.m_trx);
		
		if(workflow.getWorkflowType().equals("M")) {
			if(node.getS_Resource_ID() == 0) {
				status_po.setErrorMessage("Falta recurso en nodo del flujo de tipó manufactura");
				status_po.setContinueStatus(MPluginStatusPO.STATE_FALSE);				
			}
		}
			
		return status_po;
		
	}

}