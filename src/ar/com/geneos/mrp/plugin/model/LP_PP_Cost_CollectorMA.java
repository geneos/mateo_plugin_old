package ar.com.geneos.mrp.plugin.model;
/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import org.openXpertya.model.*;
import java.util.logging.Level;
 import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;
/** Modelo Generado por PP_Cost_CollectorMA
 *  @author Comunidad de Desarrollo Libertya*         *Basado en Codigo Original Modificado, Revisado y Optimizado de:*         * Jorg Janke 
 *  @version  - 2015-04-29 14:39:08.464 */
public class LP_PP_Cost_CollectorMA extends org.openXpertya.model.PO
{
/** Constructor estándar */
public LP_PP_Cost_CollectorMA (Properties ctx, int PP_Cost_CollectorMA_ID, String trxName)
{
super (ctx, PP_Cost_CollectorMA_ID, trxName);
/** if (PP_Cost_CollectorMA_ID == 0)
{
setM_AttributeSetInstance_ID (0);
setMovementQty (Env.ZERO);
setPP_Cost_Collector_ID (0);
setPP_Cost_Collectorma_ID (0);
}
 */
}
/** Load Constructor */
public LP_PP_Cost_CollectorMA (Properties ctx, ResultSet rs, String trxName)
{
super (ctx, rs, trxName);
}
/** AD_Table_ID */
public static final int Table_ID = M_Table.getTableID("PP_Cost_CollectorMA");

/** TableName=PP_Cost_CollectorMA */
public static final String Table_Name="PP_Cost_CollectorMA";

protected static KeyNamePair Model = new KeyNamePair(Table_ID,"PP_Cost_CollectorMA");
protected static BigDecimal AccessLevel = new BigDecimal(3);

/** Load Meta Data */
protected POInfo initPO (Properties ctx)
{
POInfo poi = POInfo.getPOInfo (ctx, Table_ID);
return poi;
}
public String toString()
{
StringBuffer sb = new StringBuffer ("LP_PP_Cost_CollectorMA[").append(getID()).append("]");
return sb.toString();
}
/** Set Attribute Set Instance.
Product Attribute Set Instance */
public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
{
set_Value ("M_AttributeSetInstance_ID", new Integer(M_AttributeSetInstance_ID));
}
/** Get Attribute Set Instance.
Product Attribute Set Instance */
public int getM_AttributeSetInstance_ID() 
{
Integer ii = (Integer)get_Value("M_AttributeSetInstance_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Movement Quantity.
Quantity of a product moved. */
public static final String COLUMNNAME_MovementQty = "MovementQty";

public void setMovementQty (BigDecimal MovementQty)
{
if (MovementQty == null) throw new IllegalArgumentException ("MovementQty is mandatory");
set_Value ("MovementQty", MovementQty);
}
/** Get Movement Quantity.
Quantity of a product moved. */
public BigDecimal getMovementQty() 
{
BigDecimal bd = (BigDecimal)get_Value("MovementQty");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set PP_Cost_Collector_ID */
public static final String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID)
{
set_Value ("PP_Cost_Collector_ID", new Integer(PP_Cost_Collector_ID));
}
/** Get PP_Cost_Collector_ID */
public int getPP_Cost_Collector_ID() 
{
Integer ii = (Integer)get_Value("PP_Cost_Collector_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set PP_Cost_Collectorma_ID */
public static final String COLUMNNAME_PP_Cost_Collectorma_ID = "PP_Cost_Collectorma_ID";

public void setPP_Cost_Collectorma_ID (int PP_Cost_Collectorma_ID)
{
set_ValueNoCheck ("PP_Cost_Collectorma_ID", new Integer(PP_Cost_Collectorma_ID));
}
/** Get PP_Cost_Collectorma_ID */
public int getPP_Cost_Collectorma_ID() 
{
Integer ii = (Integer)get_Value("PP_Cost_Collectorma_ID");
if (ii == null) return 0;
return ii.intValue();
}
}
