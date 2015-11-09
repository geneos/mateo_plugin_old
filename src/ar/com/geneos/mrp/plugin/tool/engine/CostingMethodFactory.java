/**
 * 
 */
package ar.com.geneos.mrp.plugin.tool.engine;

import java.util.HashMap;
import java.util.Map;

import org.openXpertya.model.X_M_CostType;

import ar.com.geneos.mrp.plugin.model.LP_M_CostType;

/**
 * @author teo_sarca
 *
 */
public class CostingMethodFactory {
	private static final CostingMethodFactory s_instance = new CostingMethodFactory();

	public static CostingMethodFactory get() {
		return s_instance;
	}

	private static final Map<String, Class<? extends ICostingMethod>> s_map = new HashMap<String, Class<? extends ICostingMethod>>();
	static {
		s_map.put(LP_M_CostType.COSTINGMETHOD_Fifo, FifoLifoCostingMethod.class);
		s_map.put(LP_M_CostType.COSTINGMETHOD_Lifo, FifoLifoCostingMethod.class);
		s_map.put(LP_M_CostType.COSTINGMETHOD_AverageInvoice, AverageInvoiceCostingMethod.class);
		s_map.put(LP_M_CostType.COSTINGMETHOD_AveragePO, AveragePOCostingMethod.class);
		s_map.put(LP_M_CostType.COSTINGMETHOD_LastInvoice, LastInvoiceCostingMethod.class);
		s_map.put(LP_M_CostType.COSTINGMETHOD_LastPOPrice, LastPOPriceCostingMethod.class);
		s_map.put(LP_M_CostType.COSTINGMETHOD_StandardCosting, StandardCostingMethod.class);
	}

	private CostingMethodFactory() {
	}

	/**
	 * Get Costing method
	 * 
	 * @param ce
	 *            cost element
	 * @param costingMethod
	 *            costing method. Optional. If null, we get the costing method
	 *            from cost element
	 * @return costing method class instance
	 */
	public ICostingMethod getCostingMethod(String costingMethod) {
		Class<? extends ICostingMethod> cl = s_map.get(costingMethod);
		if (cl == null) {
			throw new IllegalStateException("No implementation found for costing method " + costingMethod);
		}
		ICostingMethod cm;
		try {
			cm = cl.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return cm;
	}
}
