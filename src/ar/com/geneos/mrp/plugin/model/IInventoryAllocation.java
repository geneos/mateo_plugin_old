package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;

public interface IInventoryAllocation
{
	public void setAD_Org_ID(int AD_Org_ID);
	public int getAD_Org_ID();
	
	public int getM_AttributeSetInstance_ID();
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID);
	
	public BigDecimal getMovementQty();
	public void setMovementQty(BigDecimal MovementQty);
}
