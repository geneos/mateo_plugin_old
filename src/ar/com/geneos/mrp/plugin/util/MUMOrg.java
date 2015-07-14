package ar.com.geneos.mrp.plugin.util;

import java.util.List;

import org.openXpertya.model.MOrg;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;

public class MUMOrg {

	private static final String COLUMNNAME_Value = "Value";

	/**
	 * Get Active Organizations Of Client
	 *
	 * @param po
	 *            persistent object
	 * @return array of orgs
	 */
	public static MOrg[] getOfClient(PO po) {
		List<MOrg> list = new Query(po.getCtx(), MOrg.Table_Name, "AD_Client_ID=?", null).setOrderBy(COLUMNNAME_Value).setOnlyActiveRecords(true)
				.setParameters(po.getAD_Client_ID()).list();
		return list.toArray(new MOrg[list.size()]);
	} // getOfClient

}
