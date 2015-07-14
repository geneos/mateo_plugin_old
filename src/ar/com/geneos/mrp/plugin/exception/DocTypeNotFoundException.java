package ar.com.geneos.mrp.plugin.exception;

import org.openXpertya.model.MDocType;
import org.openXpertya.model.MRefList;
import org.openXpertya.util.Env;
import org.openXpertya.util.Util;

public class DocTypeNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4218893853798807816L;
	/** Doc Base Type */
	private String m_docBaseType = null;

	/**
	 * @param docBaseType
	 *            Document Base Type (see MDocType.DOCBASETYPE_*)
	 * @param additionalInfo
	 *            optional if there is some additional info
	 */
	public DocTypeNotFoundException(String docBaseType, String additionalInfo) {
		super(additionalInfo);
		m_docBaseType = docBaseType;
	}

	public String getDocBaseType() {
		return m_docBaseType;
	}

	@Override
	public String getMessage() {
		String additionalInfo = super.getMessage();
		String docBaseTypeName = MRefList.getListName(Env.getCtx(), MDocType.DOCBASETYPE_AD_Reference_ID, getDocBaseType());
		StringBuffer sb = new StringBuffer("@NotFound@ @C_DocType_ID@");
		sb.append(" - @DocBaseType@ : " + docBaseTypeName);
		if (!Util.isEmpty(additionalInfo, true)) {
			sb.append(" (").append(additionalInfo).append(")");
		}
		return sb.toString();
	}

}
