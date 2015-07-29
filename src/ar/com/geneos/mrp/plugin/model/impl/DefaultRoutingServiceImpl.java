/**
 * 
 */
package ar.com.geneos.mrp.plugin.model.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Properties;

import org.openXpertya.model.MResource;
import org.openXpertya.model.MResourceType;
import org.openXpertya.model.MUOM;
import org.openXpertya.model.PO;
import org.openXpertya.model.X_AD_WF_Node;
import org.openXpertya.model.X_AD_Workflow;
import org.openXpertya.model.X_S_Resource;
import org.openXpertya.util.Env;
import org.openXpertya.wf.MWFNode;
import org.openXpertya.wf.MWorkflow;

import ar.com.geneos.mrp.plugin.model.LP_PP_Cost_Collector;
import ar.com.geneos.mrp.plugin.model.LP_PP_Order_Node;
import ar.com.geneos.mrp.plugin.model.MPPCostCollector;
import ar.com.geneos.mrp.plugin.model.MPPOrderNode;
import ar.com.geneos.mrp.plugin.model.RoutingService;
import ar.com.geneos.mrp.plugin.util.MUMResource;
import ar.com.geneos.mrp.plugin.util.MUMResourceType;
import ar.com.geneos.mrp.plugin.util.MUMUOM;
import ar.com.geneos.mrp.plugin.util.MUMWorkflow;


/**
 * Default Routing Service Implementation
 * @author Teo Sarca
 */
public class DefaultRoutingServiceImpl implements RoutingService
{
//	private final CLogger log = CLogger.getCLogger(getClass());
	
	public BigDecimal estimateWorkingTime(MWFNode node)
	{
		final double duration;
		if (node.getUnitsCycles().signum() == 0)
		{
			duration = node.getDuration();
		}
		else
		{
			duration = node.getDuration() / node.getUnitsCycles().doubleValue();
		}
		return BigDecimal.valueOf(duration);
	}
	public BigDecimal estimateWorkingTime(MPPOrderNode node, BigDecimal qty)
	{
		double unitDuration = node.getDuration();
		double cycles = calculateCycles(node.getUnitsCycles(), qty);
		BigDecimal duration = BigDecimal.valueOf(unitDuration * cycles);
		return duration;
	}
	
	public BigDecimal estimateWorkingTime(MPPCostCollector cc)
	{
		final String trxName = (cc instanceof PO ? ((PO)cc).get_TrxName() : null);
		final BigDecimal qty = cc.getMovementQty();
		MPPOrderNode node = MPPOrderNode.get(Env.getCtx(), cc.getPP_Order_Node_ID(), trxName);
		return estimateWorkingTime(node, qty);
	}

	
	/**
	 * Calculate how many cycles are needed for given qty and units per cycle
	 * @param unitsCycle
	 * @param qty
	 * @return number of cycles
	 */
	protected int calculateCycles(int unitsCycle, BigDecimal qty)
	{
		BigDecimal cycles = qty;
		BigDecimal unitsCycleBD = BigDecimal.valueOf(unitsCycle);
		if (unitsCycleBD.signum() > 0)
		{
			cycles = qty.divide(unitsCycleBD, 0, RoundingMode.UP);
		}
		return cycles.intValue();
	}
	
	/**
	 * Calculate node duration in DurationUnit UOM (see AD_Workflow.DurationUnit)
	 * @param node
	 * @param setupTime setup time (workflow duration unit)
	 * @param durationTotal (workflow duration unit)
	 * @reeturn duration
	 */
	protected BigDecimal calculateDuration(MWFNode node, MPPCostCollector cc)
	{
		if (node == null)
		{
			node = new MWFNode(cc.getCtx(),cc.getPP_Order_Node().getAD_WF_Node_ID(),cc.get_TrxName());
		}
		final MWorkflow workflow = new MWorkflow(node.getCtx(),node.getAD_Workflow_ID(),node.get_TrxName());
		final double batchSize = workflow.getQtyBatchSize().doubleValue();
		final double setupTime;
		final double duration;
		if (cc != null)
		{
			setupTime = cc.getSetupTimeReal().doubleValue();
			duration = cc.getDurationReal().doubleValue();
		}
		else
		{
			setupTime = node.getSetupTime();
			// Estimate total duration for 1 unit of final product as duration / units cycles
			duration = estimateWorkingTime(node).doubleValue(); 
		}
		
		double totalDuration;
		if(batchSize > 0)
			totalDuration = ((setupTime / batchSize) + duration);
		else
			totalDuration = setupTime  + duration;
		
		return BigDecimal.valueOf(totalDuration);
	}
	
	public BigDecimal calculateDuration(MWFNode node)
	{
		return calculateDuration(node, null);
	}
	public BigDecimal calculateDuration(MPPCostCollector cc)
	{
		return calculateDuration(getAD_WF_Node(cc), cc);
	}

	public BigDecimal calculateDuration(MWorkflow wf, MResource plant, BigDecimal qty)
	{
		if (plant == null)
			return Env.ZERO;
		final Properties ctx = ((PO)wf).getCtx();
		final MResourceType S_ResourceType = new MResourceType(ctx, plant.getS_ResourceType_ID(),null);

		BigDecimal AvailableDayTime  = BigDecimal.valueOf(MUMResourceType.getTimeSlotHours(S_ResourceType));
		int AvailableDays = MUMResourceType.getAvailableDaysWeek(S_ResourceType);

		double durationBaseSec = getDurationBaseSec(wf.getDurationUnit());

		double durationTotal = 0.0; 
		MWFNode[] nodes = MUMWorkflow.getNodes(wf,false, Env.getAD_Client_ID(ctx));
		for (MWFNode node : nodes)
		{
			// Qty independent times:
			durationTotal += node.getQueuingTime();
			durationTotal += node.getSetupTime();
			durationTotal += node.getWaitingTime();
			durationTotal += node.getMovingTime();
			
			// Get OverlapUnits - number of units that must be completed before they are moved the next activity 
			double overlapUnits = qty.doubleValue();
			if (node.getOverlapUnits() > 0 && node.getOverlapUnits() < overlapUnits)
			{
				overlapUnits = node.getOverlapUnits();
			}
			double durationBeforeOverlap = node.getDuration() * overlapUnits;
			
			durationTotal += durationBeforeOverlap;
		}
		BigDecimal requiredTime = BigDecimal.valueOf(durationTotal * durationBaseSec / 60 / 60);
		// TODO: implement here, Victor's suggestion - https://sourceforge.net/forum/message.php?msg_id=5179460

		// Weekly Factor  	
		BigDecimal WeeklyFactor = BigDecimal.valueOf(7).divide(BigDecimal.valueOf(AvailableDays), 8, RoundingMode.UP);

		return (requiredTime.multiply(WeeklyFactor)).divide(AvailableDayTime, 0, RoundingMode.UP);
	}

	protected BigDecimal convertDurationToResourceUOM(BigDecimal duration, int S_Resource_ID, MWFNode node)
	{
		MResource resource = new MResource(Env.getCtx(), S_Resource_ID,null);
		MWorkflow wf = MWorkflow.get(Env.getCtx(), node.getAD_Workflow_ID());
		MUOM resourceUOM = MUOM.get(Env.getCtx(), MUMResource.getC_UOM_ID(resource));
		return convertDuration(duration, wf.getDurationUnit(), resourceUOM);
	}
	
	@Override
	public BigDecimal getResourceBaseValue(int S_Resource_ID, MPPCostCollector cc)
	{
		return getResourceBaseValue(S_Resource_ID, null, cc);
	}
	@Override
	public BigDecimal getResourceBaseValue(int S_Resource_ID, MWFNode node)
	{
		return getResourceBaseValue(S_Resource_ID, node, null);
	}
	protected BigDecimal getResourceBaseValue(int S_Resource_ID, MWFNode node, MPPCostCollector cc)
	{
		if (node == null)
			node = new MWFNode(cc.getCtx(),cc.getPP_Order_Node().getAD_WF_Node_ID(),cc.get_TrxName());
		final Properties ctx = (node instanceof PO ? ((PO)node).getCtx() : Env.getCtx());
		final MResource resource = new MResource(ctx, S_Resource_ID,null);
		final MUOM resourceUOM = MUOM.get(ctx, MUMResource.getC_UOM_ID(resource));
		//
		if (isTime(resourceUOM))
		{
			BigDecimal duration = calculateDuration(node, cc);
			MWorkflow wf = MWorkflow.get(ctx, node.getAD_Workflow_ID());
			BigDecimal convertedDuration = convertDuration(duration, wf.getDurationUnit(), resourceUOM);
			return convertedDuration;
		}
		else
		{
			throw new RuntimeException("@NotSupported@ @C_UOM_ID@ - "+resourceUOM);
		}
	}

	protected MWFNode getAD_WF_Node(MPPCostCollector cc)
	{
		MPPOrderNode activity = cc.getPP_Order_Node();
		return new MWFNode(activity.getCtx(),activity.getAD_WF_Node_ID(),activity.get_TrxName());
	}
	
	/**
	 * Convert durationUnit to seconds
	 * @param durationUnit
	 * @return duration in seconds
	 */
	public long getDurationBaseSec (String durationUnit)
	{
		if (durationUnit == null)
			return 0;
		else if (MWorkflow.DURATIONUNIT_Second.equals(durationUnit))
			return 1;
		else if (MWorkflow.DURATIONUNIT_Minute.equals(durationUnit))
			return 60;
		else if (MWorkflow.DURATIONUNIT_Hour.equals(durationUnit))
			return 3600;
		else if (MWorkflow.DURATIONUNIT_Day.equals(durationUnit))
			return 86400;
		else if (MWorkflow.DURATIONUNIT_Month.equals(durationUnit))
			return 2592000;
		else if (MWorkflow.DURATIONUNIT_Year.equals(durationUnit))
			return 31536000;
		return 0;
	}	//	getDurationSec
	
	/**
	 * Convert uom to seconds
	 * @param uom time UOM 
	 * @return duration in seconds
	 * @throws AdempiereException if UOM is not supported
	 */
	public long getDurationBaseSec(MUOM uom)
	{
		MUOM uomImpl = (MUOM)uom;
		//
		if(uomImpl.isWeek())
		{
			return 60*60*24*7;
		}
		if(uomImpl.isDay())
		{
			return 60*60*24;
		}
		else if (uomImpl.isHour())
		{
			return 60*60;
		}
		else if (uomImpl.isMinute())
		{
			return 60;
		}
		/**
		 * Libero to Libertya migration
		 * UOM in seconds not implemented
		 */
		/*
		else if (uomImpl.isSecond())
		{
			return 1;
		}*/
		else
		{
			throw new RuntimeException("@NotSupported@ @C_UOM_ID@="+uom.getName());
		}
	}
	
	/**
	 * Check if it's an UOM that measures time 
	 * @param uom 
	 * @return true if is time UOM
	 */
	public boolean isTime(MUOM uom)
	{
		String x12de355 = uom.getX12DE355();
		/**
		 * Libero to Libertya migration
		 * UOM in seconds not implemented
		 */
		return /*MUOM.X12_SECOND.equals(x12de355)
		||*/ MUMUOM.X12_MINUTE.equals(x12de355)
		|| MUMUOM.X12_HOUR.equals(x12de355)
		|| MUMUOM.X12_DAY.equals(x12de355)
		|| MUMUOM.X12_DAY_WORK.equals(x12de355)
		|| MUMUOM.X12_WEEK.equals(x12de355)
		|| MUMUOM.X12_MONTH.equals(x12de355)
		|| MUMUOM.X12_MONTH_WORK.equals(x12de355)
		|| MUMUOM.X12_YEAR.equals(x12de355)
		;
	}
	
	/**
	 * Convert duration from given UOM to given UOM
	 * @param duration
	 * @param fromDurationUnit duration UOM
	 * @param toUOM target UOM
	 * @return duration converted to toUOM
	 */
	public BigDecimal convertDuration(BigDecimal duration, String fromDurationUnit, MUOM toUOM)
	{
		double fromMult = getDurationBaseSec(fromDurationUnit);
		double toDiv = getDurationBaseSec(toUOM);
		BigDecimal convertedDuration = BigDecimal.valueOf(duration.doubleValue() * fromMult / toDiv);
		//https://adempiere.atlassian.net/browse/MFG-4
		// Adjust scale to UOM precision
		/*int precision = toUOM.getStdPrecision();
		if (convertedDuration.scale() > precision)
			convertedDuration = convertedDuration.setScale(precision, RoundingMode.HALF_UP);*/
		//
		return convertedDuration;
	}

}
