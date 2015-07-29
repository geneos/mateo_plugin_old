package ar.com.geneos.mrp.plugin.model;
/**
 * 
 */



import java.math.BigDecimal;

import org.openXpertya.model.*;
import org.openXpertya.wf.MWFNode;
import org.openXpertya.wf.MWorkflow;

/**
 * Rounting(Workflow Service)
 * @author Teo Sarca, www.arhipac.ro
 */
public interface RoutingService
{
	public BigDecimal estimateWorkingTime(MWFNode node);
	
	/**
	 * Estimate Activity Working Time for given qty.
	 * Please not that SetupTime or any other times are not considered.
	 * @param node activity
	 * @param qty qty required
	 * @return working time (using Workflow DurationUnit UOM)
	 */
	public BigDecimal estimateWorkingTime(MPPOrderNode node, BigDecimal qty);
	
	public BigDecimal estimateWorkingTime(MPPCostCollector cc);

	/**
	 * Calculate node duration for 1 item, AD_Workflow.DurationUnit UOM will be used
	 * @param node operation
	 * @return node duration for 1 item (AD_Workflow.DurationUnit UOM)
	 */
	public BigDecimal calculateDuration(MWFNode node);
	
	/**
	 * Calculate workflow duration for given qty
	 * @param node operation
	 * @return node duration for 1 item (AD_Workflow.DurationUnit UOM)
	 */
	public BigDecimal calculateDuration(MWorkflow wf, MResource plant, BigDecimal qty);
	
	/**
	 * Calculate activity duration based on reported data from Cost Collector.
	 * @param cc cost collector
	 * @return activity duration (using Workflow DurationUnit UOM)
	 */
	public BigDecimal calculateDuration(MPPCostCollector cc);
	
	/**
	 * Return cost collector base value in resource UOM (e.g. duration)
	 * @param S_Resource_ID resource
	 * @param cc cost collector
	 * @return value (e.g. duration)
	 */
	public BigDecimal getResourceBaseValue(int S_Resource_ID, MPPCostCollector cc);

	/**
	 * Return node base value in resource UOM (e.g. duration)
	 * @param S_Resource_ID resource
	 * @param node
	 * @return value (e.g. duration)
	 */
	public BigDecimal getResourceBaseValue(int S_Resource_ID, MWFNode node);
}
