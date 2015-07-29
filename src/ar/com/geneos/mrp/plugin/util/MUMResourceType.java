package ar.com.geneos.mrp.plugin.util;

import org.openXpertya.model.MResource;
import org.openXpertya.model.MResourceType;
import org.openXpertya.model.X_M_Forecast;

import ar.com.geneos.mrp.plugin.model.LP_M_Forecast;
import ar.com.geneos.mrp.plugin.model.LP_M_Resource;

public class MUMResourceType {

	/**
	 * Get how many hours/day a is available. Minutes, secords and millis are
	 * discarded.
	 * 
	 * @return available hours
	 */
	public static int getTimeSlotHours(MResourceType rt) {
		long hours;
		if (rt.isTimeSlot())
			hours = (rt.getTimeSlotEnd().getTime() - rt.getTimeSlotStart().getTime()) / (60 * 60 * 1000);
		else
			hours = 24;
		return (int) hours;
	}
	
	/**
	 * Get available days / week.
	 * @return available days / week
	 */
	public static int getAvailableDaysWeek(MResourceType rt)
	{
		int availableDays = 0;
		if (rt.isDateSlot())
		{
			if (rt.isOnMonday())
				availableDays += 1; 
			if (rt.isOnTuesday())
				availableDays += 1;
			if (rt.isOnThursday())
				availableDays += 1;
			if (rt.isOnWednesday())	
				availableDays += 1;
			if (rt.isOnFriday())	 
				availableDays += 1;
			if (rt.isOnSaturday())	
				availableDays += 1;
			if (rt.isOnSunday())
				availableDays += 1;
		}
		else
		{
			availableDays = 7;
		}
		return availableDays;
	}

}
