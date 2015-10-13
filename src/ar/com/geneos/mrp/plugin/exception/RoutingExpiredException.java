package ar.com.geneos.mrp.plugin.exception;

import java.sql.Timestamp;

import org.openXpertya.model.X_AD_Workflow;

public class RoutingExpiredException extends MRPException {

	/**
		 * 
		 */
	private static final long serialVersionUID = -7522979292063177848L;

	public RoutingExpiredException(X_AD_Workflow wf, Timestamp date) {
		super(buildMessage(wf, date));
	}

	private static final String buildMessage(X_AD_Workflow wf, Timestamp date) {
		return "@NotValid@ @AD_Workflow_ID@:" + wf.getValue() + " - @Date@:" + date;
	}

}
