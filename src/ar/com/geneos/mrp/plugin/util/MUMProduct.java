package ar.com.geneos.mrp.plugin.util;

import java.util.Properties;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MAttributeSet;
import org.openXpertya.model.MAttributeSetInstance;
import org.openXpertya.model.MClient;
import org.openXpertya.model.MProduct;
import org.openXpertya.model.MProductCategory;
import org.openXpertya.model.MProductCategoryAcct;
import org.openXpertya.model.MResource;
import org.openXpertya.model.Query;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.model.LP_C_AcctSchema;

public class MUMProduct {

	private static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";
	public static final String COLUMNNAME_IsCurrentVendor = "IsCurrentVendor";
	public static final String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";
	public static final String COLUMNNAME_Order_Min = "Order_Min";
	public static final String COLUMNNAME_Order_Pack = "Order_Pack";
	public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";
	public static final String COLUMNNAME_IsBOM = "IsBOM";
	public static final String COLUMNNAME_IsPurchased = "IsPurchased";
	public static final String COLUMNNAME_IsVerified = "IsVerified";
	public static final String COLUMNNAME_IsStocked = "IsStocked";
	public static final String COLUMNNAME_ProductType = "ProductType";

	/**
	 * Gets Material Management Policy. Tries: Product Category, Client (in this
	 * order)
	 * 
	 * @return Material Management Policy
	 */
	public static String getMMPolicy(MProduct p) {
		MProductCategory pc = MProductCategory.get(p.getCtx(), p.getM_Product_Category_ID(), null);
		String MMPolicy = pc.getMMPolicy();
		if (MMPolicy == null || MMPolicy.length() == 0)
			MMPolicy = MClient.get(p.getCtx()).getMMPolicy();
		return MMPolicy;
	}

	/**
	 * Get Product Costing Level
	 * 
	 * @param as
	 *            accounting schema
	 * @return product costing level
	 */

	public static String getCostingLevel(MProduct p, MAcctSchema as) {
		MProductCategoryAcct pca = MUMProductCategoryAcct.get(p.getCtx(), p.getM_Product_Category_ID(), as.getID(), p.get_TrxName());
		String costingLevel = null;

		if (pca != null) {
			costingLevel = MUMProductCategoryAcct.getCostingLevel(pca);
			if (costingLevel == null) {
				costingLevel = MUMAcctSchema.getCostingLevel(as);
			}
		}

		return costingLevel;
	}

	/**
	 * Get Product Costing Level
	 * 
	 * @param as
	 *            accounting schema
	 * @param AD_Org_ID
	 *            Organization ID
	 * @return product costing level
	 */
	public static String getCostingLevel(MProduct p, MAcctSchema as, int AD_Org_ID) {
		MProductCategoryAcct pca = MUMProductCategoryAcct.get(p.getCtx(), p.getM_Product_Category_ID(), as.getID(), AD_Org_ID, p.get_TrxName());
		if (pca == null) {
			return getCostingLevel(p, as);
		}

		String costingLevel = MUMProductCategoryAcct.getCostingLevel(pca);
		if (costingLevel == null) {
			costingLevel = MUMAcctSchema.getCostingLevel(as);
		}
		return costingLevel;
	}

	/**
	 * Get Product Costing Method
	 * 
	 * @param accountSchema
	 *            accounting schema ID
	 * @return product costing method
	 */
	public static String getCostingMethod(MProduct p, MAcctSchema accountSchema) {
		MProductCategoryAcct pca = MUMProductCategoryAcct.get(p.getCtx(), p.getM_Product_Category_ID(), accountSchema.getID(), p.get_TrxName());
		String costingMethod = pca.getCostingMethod();
		if (costingMethod == null) {
			costingMethod = accountSchema.getCostingMethod();
		}
		return costingMethod;
	}

	/**
	 * Get Product Costing Method
	 * 
	 * @param assetSchema
	 *            accounting schema ID
	 * @return product costing method
	 */
	public static String getCostingMethod(MProduct p, MAcctSchema assetSchema, int AD_Org_ID) {
		MProductCategoryAcct pca = MUMProductCategoryAcct.get(p.getCtx(), p.getM_Product_Category_ID(), assetSchema.getID(), AD_Org_ID, p.get_TrxName());
		String costingMethod = pca.getCostingMethod();
		if (costingMethod == null) {
			costingMethod = assetSchema.getCostingMethod();
		}
		return costingMethod;
	}

	/**
	 * Check if ASI is mandatory
	 * 
	 * @param isSOTrx
	 *            is outgoing trx?
	 * @return true if ASI is mandatory, false otherwise
	 */
	public static boolean isASIMandatory(MProduct p, boolean isSOTrx, int AD_Org_ID) {
		//
		// If CostingLevel is BatchLot ASI is always mandatory - check all
		// client acct schemas
		MAcctSchema[] mass = MAcctSchema.getClientAcctSchema(p.getCtx(), p.getAD_Client_ID());
		for (MAcctSchema as : mass) {
			// String cl = getCostingLevel(as,AD_Org_ID);
			String cl = getCostingLevel(p, as);
			if (LP_C_AcctSchema.COSTINGLEVEL_BatchLot.equals(cl)) {
				return true;
			}
		}
		//
		// Check Attribute Set settings
		int M_AttributeSet_ID = p.getM_AttributeSet_ID();
		if (M_AttributeSet_ID != 0) {
			MAttributeSet mas = MAttributeSet.get(p.getCtx(), M_AttributeSet_ID);
			if (mas == null || !mas.isInstanceAttribute())
				return false;
			// Outgoing transaction
			else if (isSOTrx)
				return mas.isMandatory();
			// Incoming transaction
			else
				// isSOTrx == false
				return mas.isMandatoryAlways();
		}
		//
		// Default not mandatory
		return false;
	}

	/**
	 * Get Product from Cache
	 * 
	 * @param ctx
	 *            context
	 * @param S_Resource_ID
	 *            resource ID
	 * @param trxName
	 * @return MProduct or null if not found
	 */
	public static MProduct forS_Resource_ID(Properties ctx, int S_Resource_ID, String trxName) {
		if (S_Resource_ID <= 0) {
			return null;
		}
		// Load from DB
		MProduct p = new Query(ctx, MProduct.Table_Name, COLUMNNAME_S_Resource_ID + "=?", trxName).setParameters(new Object[] { S_Resource_ID }).firstOnly();

		return p;
	}

	public static MResource getS_Resource(MProduct product) {
		return new MResource(product.getCtx(), product.getS_Resource_ID(), product.get_TrxName());
	}

	/**
	 * Get the Attribute Set Instance. This is called by callouts to fill the
	 * M_AttributeSetInstance_ID field. The ASI should override the context if
	 * the product has a defined ASI or if the context ASI does not use the same
	 * attribute set.
	 * 
	 * @param ctx
	 * @param WindowNo
	 *            number
	 */
	public static Integer getEnvAttributeSetInstance(MProduct product, Properties ctx, int WindowNo) {
		Integer M_AttributeSetInstance_ID = 0;

		// Set Attribute Instance from the context
		M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID");
		// Get Model and check if it has a product attribute instance
		if (product.getM_AttributeSetInstance_ID() > 0) {
			// If the product has a product instance associated with it. Use it
			// regardless of the context.
			// Product Attributes and Instance Attributes are exclusive
			M_AttributeSetInstance_ID = new Integer(product.getM_AttributeSetInstance_ID());
		} else if (product.getM_AttributeSet_ID() > 0 && M_AttributeSetInstance_ID > 0) {
			// Check compatibility of the instance with the product - they have
			// to use the same set.
			MAttributeSetInstance masi = MAttributeSetInstance.get(Env.getCtx(), M_AttributeSetInstance_ID, product.getID());
			MAttributeSet attributeSet = MAttributeSet.get(Env.getCtx(), product.getM_AttributeSet_ID());

			if (masi.getMAttributeSet().getID() != attributeSet.getID())
				M_AttributeSetInstance_ID = 0;
		}
		if (M_AttributeSetInstance_ID != 0)
			return M_AttributeSetInstance_ID;
		else
			return null;
	}
}
