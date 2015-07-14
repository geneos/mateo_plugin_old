package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

/**
 *  Inventory Document Line interface
 *  @author victor.perez@e-evolution.com http://www.e-evolution.com
 *
 */
public interface IDocumentLine
{
	public Properties getCtx();
	public String get_TrxName();
	public String get_TableName();
	//
	public int get_ID();
	public int getAD_Client_ID();
	public int getAD_Org_ID();
	public int getM_Product_ID();
	public int getC_DocType_ID();
	public String getDescription();
	public int getM_Locator_ID();
	public int getM_LocatorTo_ID();
	public void setM_Locator_ID(int M_Locator_ID);
	public int getM_AttributeSetInstance_ID();
	public int getM_AttributeSetInstanceTo_ID();
	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID);
	public Timestamp getDateAcct();
	public BigDecimal getMovementQty();
	public boolean isSOTrx();
	/**
	 * Libero to Libertya migration
	 * Not used
	 */
	//public int getReversalLine_ID();
	public BigDecimal getPriceActual();
	/**
	 * Libero to Libertya migration
	 * Not used
	 */
	//public IDocumentLine getReversalDocumentLine();
}
