package ar.com.geneos.mrp.plugin.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openXpertya.model.MAcctSchema;
import org.openXpertya.model.MCostElement;
import org.openXpertya.model.MProductCategoryAcct;
import org.openXpertya.model.MRefList;
import org.openXpertya.model.PO;
import org.openXpertya.model.Query;
import org.openXpertya.util.CCache;
import org.openXpertya.util.CLogger;
import org.openXpertya.util.Env;
import org.openXpertya.util.Msg;

import ar.com.geneos.mrp.plugin.model.LP_M_CostElement;
import ar.com.geneos.mrp.plugin.model.LP_M_Product_Category_Acct;

public class MUMCostElement {

	public static final String COLUMNNAME_Created = "Created";

	public static final String COLUMNNAME_CostingMethod = "CostingMethod";

	/**
	 * get or create Cost Element
	 * 
	 * @param po
	 *            Persistence Object
	 * @return get Cost Element
	 */
	public static List<MCostElement> getDefaultElements(PO po) {
		//
		final String whereClause = "IsDefault=?";
		List<MCostElement> elements = new Query(po.getCtx(), MCostElement.Table_Name, whereClause, po.get_TrxName()).setParameters(true).setClient_ID()
				.setOnlyActiveRecords(true).setOrderBy("AD_Org_ID DESC").list();

		if (elements != null && elements.size() > 0)
			return elements;

		MCostElement costElement = MUMCostElement.getByMaterialCostElementType(po);

		if (costElement != null) {
			if (!costElement.isActive()) {
				costElement.setIsActive(true);
				costElement.save();
			}
		} else {
			// Create New
			costElement = new LP_M_CostElement(po.getCtx(), 0, po.get_TrxName());
			costElement.setClientOrg(po.getAD_Client_ID(), 0);
			costElement.setName("Material");
			((LP_M_CostElement) costElement).setIsDefault(true);
			costElement.setIsActive(true);
			costElement.setCostElementType(MCostElement.COSTELEMENTTYPE_Material);
			costElement.save();
		}

		elements = new ArrayList();
		elements.add(costElement);
		//
		return elements;
	} // getMaterialCostElement

	/**
	 * Get Cost Element
	 * 
	 * @param po
	 *            Persistence Object
	 * @return MCostElement Cost Element
	 */
	public static MCostElement getByMaterialCostElementType(PO po) {
		final String whereClause = "CostElementType=?";
		MCostElement retValue = new Query(po.getCtx(), MCostElement.Table_Name, whereClause, po.get_TrxName())
				.setParameters(MCostElement.COSTELEMENTTYPE_Material).setClient_ID().setOrderBy("M_CostElement_ID ,IsDefault, AD_Org_ID DESC").first();
		//
		return retValue;
	} // getMaterialCostElement

	/**
	 * Get All Cost Elements for current AD_Client_ID
	 * 
	 * @param ctx
	 *            context
	 * @param trxName
	 *            transaction
	 * @return List with cost elements
	 */
	public static List<MCostElement> getCostElement(Properties ctx, String trxName) {
		return new Query(ctx, MCostElement.Table_Name, null, trxName).setClient_ID().setOnlyActiveRecords(true).setOrderBy(COLUMNNAME_Created).list();
	}

	/**
	 * Get Material Cost Element or create it
	 * 
	 * @param po
	 * @return cost element entity
	 */
	public static MCostElement getMaterialCostElement(PO po) {

		MCostElement costElement = MUMCostElement.getByMaterialCostElementType(po);
		if (costElement != null)
			return costElement;

		// Create New
		costElement = new MCostElement(po.getCtx(), 0, po.get_TrxName());
		costElement.setClientOrg(po.getAD_Client_ID(), 0);
		String name = MRefList.getListName(po.getCtx(), MCostElement.COSTELEMENTTYPE_AD_Reference_ID, MCostElement.COSTELEMENTTYPE_Material);
		if (name == null || name.length() == 0)
			name = MCostElement.COSTELEMENTTYPE_Material;
		costElement.setName(name);
		costElement.setCostElementType(MCostElement.COSTELEMENTTYPE_Material);
		costElement.save();
		return costElement;
	}

	/**
	 * Get Cost Element from Cache
	 *
	 * @param ctx
	 *            context
	 * @param M_CostElement_ID
	 *            id
	 * @return Cost Element
	 */
	public static MCostElement get(Properties ctx, int M_CostElement_ID) {
		Integer key = new Integer(M_CostElement_ID);
		MCostElement retValue = (MCostElement) s_cache.get(key);
		if (retValue != null)
			return retValue;
		retValue = new MCostElement(ctx, M_CostElement_ID, null);
		if (retValue.getID() != 0)
			s_cache.put(key, retValue);
		return retValue;
	} // get

	/**
	 * Get All Cost Elements for current AD_Client_ID
	 * 
	 * @param ctx
	 *            context
	 * @param trxName
	 *            transaction
	 * @return array cost elements
	 */
	@Deprecated
	public static MCostElement[] getElements(Properties ctx, String trxName) {
		int AD_Org_ID = 0; // Org is always ZERO - see beforeSave

		final String whereClause = "AD_Client_ID = ? AND AD_Org_ID=?";
		List<MCostElement> list = new Query(ctx, MCostElement.Table_Name, whereClause, trxName).setParameters(Env.getAD_Client_ID(ctx), AD_Org_ID).list();
		MCostElement[] retValue = new MCostElement[list.size()];
		list.toArray(retValue);
		return retValue;
	}

	/** Cache */
	private static CCache<Integer, MCostElement> s_cache = new CCache<Integer, MCostElement>("M_CostElement", 20);

	/** Logger */
	private static CLogger s_log = CLogger.getCLogger(MCostElement.class);

	/**
	 * Is this a Costing Method
	 *
	 * @return true if not Material cost or no costing method.
	 */
	public static boolean isCostingMethod(MCostElement ce) {
		return LP_M_CostElement.COSTELEMENTTYPE_Material.equals(ce.getCostElementType()) && ce.getCostingMethod() != null;
	} // isCostingMethod

	/**
	 * Is Avg Invoice Costing Method
	 *
	 * @return true if AverageInvoice
	 * @deprecated
	 */
	/*
	 * public boolean isAverageInvoice(MCostElement ce) { String cm =
	 * ce.getCostingMethod(); return cm != null &&
	 * cm.equals(LP_M_CostElement.COSTINGMETHOD_AverageInvoice) &&
	 * LP_M_CostElement
	 * .COSTELEMENTTYPE_Material.equals(ce.getCostElementType()); } //
	 * isAverageInvoice
	 */

	/**
	 * Is Avg PO Costing Method
	 *
	 * @return true if AveragePO
	 * @deprecated
	 */
	/*
	 * public boolean isAveragePO(MCostElement ce) { String cm =
	 * ce.getCostingMethod(); return cm != null &&
	 * cm.equals(LP_M_CostElement.COSTINGMETHOD_AveragePO) &&
	 * LP_M_CostElement.COSTELEMENTTYPE_Material
	 * .equals(ce.getCostElementType()); } // isAveragePO
	 */
	/**
	 * Is FiFo Costing Method
	 *
	 * @return true if Fifo
	 * @deprecated
	 */

	public static boolean isFifo(MCostElement ce) {
		String cm = ce.getCostingMethod();
		return cm != null && cm.equals(LP_M_CostElement.COSTINGMETHOD_Fifo) && LP_M_CostElement.COSTELEMENTTYPE_Material.equals(ce.getCostElementType());
	} // isFifo

	/**
	 * Is Last Invoice Costing Method
	 *
	 * @return true if LastInvoice
	 * @deprecated
	 */
	/*
	 * public boolean isLastInvoice() { String cm = getCostingMethod(); return
	 * cm != null && cm.equals(COSTINGMETHOD_LastInvoice) &&
	 * COSTELEMENTTYPE_Material.equals(getCostElementType()); } // isLastInvoice
	 */
	/**
	 * Is Last PO Costing Method
	 *
	 * @return true if LastPOPrice
	 * @deprecated
	 */
	/*
	 * public boolean isLastPOPrice() { String cm = getCostingMethod(); return
	 * cm != null && cm.equals(COSTINGMETHOD_LastPOPrice) &&
	 * COSTELEMENTTYPE_Material.equals(getCostElementType()); } // isLastPOPrice
	 */
	/**
	 * Is LiFo Costing Method
	 *
	 * @return true if Lifo
	 * @deprecated
	 */
	/*
	 * public boolean isLifo() { String cm = getCostingMethod(); return cm !=
	 * null && cm.equals(COSTINGMETHOD_Lifo) &&
	 * COSTELEMENTTYPE_Material.equals(getCostElementType()); } // isLiFo
	 */
	/**
	 * Is Std Costing Method
	 *
	 * @return true if StandardCosting
	 * @deprecated
	 */
	/*
	 * public boolean isStandardCosting() { String cm = getCostingMethod();
	 * return cm != null && cm.equals(COSTINGMETHOD_StandardCosting) &&
	 * COSTELEMENTTYPE_Material.equals(getCostElementType()); } //
	 * isStandardCosting
	 */
	/**
	 * Is User Costing Method
	 *
	 * @return true if User Defined
	 * @deprecated
	 */
	/*
	 * public boolean isUserDefined() { String cm = getCostingMethod(); return
	 * cm != null && cm.equals(COSTINGMETHOD_UserDefined) &&
	 * COSTELEMENTTYPE_Material.equals(getCostElementType()); } // isAveragePO
	 */

}
