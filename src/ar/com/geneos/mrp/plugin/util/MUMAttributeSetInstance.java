package ar.com.geneos.mrp.plugin.util;

import java.util.Properties;

import org.openXpertya.model.MAttributeSetInstance;
import org.openXpertya.model.MProduct;

public class MUMAttributeSetInstance {

	/**
	 * Create & save a new ASI for given product. Automatically creates Lot#,
	 * Serial# and Guarantee Date.
	 * 
	 * @param ctx
	 * @param product
	 * @param trxName
	 * @return newly created ASI
	 */
	public static MAttributeSetInstance create(Properties ctx, MProduct product, String trxName) {
		MAttributeSetInstance asi = new MAttributeSetInstance(ctx, 0, trxName);
		asi.setClientOrg(product.getAD_Client_ID(), 0);
		asi.setM_AttributeSet_ID(product.getM_AttributeSet_ID());
		// Create new Lot, Serial# and Guarantee Date
		if (asi.getM_AttributeSet_ID() > 0) {
			asi.getLot(true, product.getID());
			asi.getSerNo(true);
			asi.getGuaranteeDate(true);
		}
		//
		asi.save();
		return asi;
	}

}
