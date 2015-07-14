package ar.com.geneos.mrp.plugin.model;
/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import org.openXpertya.model.*;
import java.util.logging.Level;
 import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;
/** Modelo Generado por PP_WF_Node_Product
 *  @author Comunidad de Desarrollo Libertya*         *Basado en Codigo Original Modificado, Revisado y Optimizado de:*         * Jorg Janke 
 *  @version  - 2015-04-29 14:39:08.689 */
public class LP_PP_WF_Node_Product extends org.openXpertya.model.PO
{
/** Constructor estándar */
public LP_PP_WF_Node_Product (Properties ctx, int PP_WF_Node_Product_ID, String trxName)
{
super (ctx, PP_WF_Node_Product_ID, trxName);
/** if (PP_WF_Node_Product_ID == 0)
{
setAD_WF_Node_ID (0);
setEntityType (null);
setM_Product_ID (0);
setPP_Wf_Node_Product_ID (0);
}
 */
}
/** Load Constructor */
public LP_PP_WF_Node_Product (Properties ctx, ResultSet rs, String trxName)
{
super (ctx, rs, trxName);
}
/** AD_Table_ID */
public static final int Table_ID = M_Table.getTableID("PP_WF_Node_Product");

/** TableName=PP_WF_Node_Product */
public static final String Table_Name="PP_WF_Node_Product";

protected static KeyNamePair Model = new KeyNamePair(Table_ID,"PP_WF_Node_Product");
protected static BigDecimal AccessLevel = new BigDecimal(3);

/** Load Meta Data */
protected POInfo initPO (Properties ctx)
{
POInfo poi = POInfo.getPOInfo (ctx, Table_ID);
return poi;
}
public String toString()
{
StringBuffer sb = new StringBuffer ("LP_PP_WF_Node_Product[").append(getID()).append("]");
return sb.toString();
}
/** Set Node.
Workflow Node (activity), step or process */
public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

public void setAD_WF_Node_ID (int AD_WF_Node_ID)
{
set_Value ("AD_WF_Node_ID", new Integer(AD_WF_Node_ID));
}
/** Get Node.
Workflow Node (activity), step or process */
public int getAD_WF_Node_ID() 
{
Integer ii = (Integer)get_Value("AD_WF_Node_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set ConfigurationLevel */
public static final String COLUMNNAME_ConfigurationLevel = "ConfigurationLevel";

public void setConfigurationLevel (boolean ConfigurationLevel)
{
set_Value ("ConfigurationLevel", new Boolean(ConfigurationLevel));
}
/** Get ConfigurationLevel */
public boolean isConfigurationLevel() 
{
Object oo = get_Value("ConfigurationLevel");
if (oo != null) 
{
 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
 return "Y".equals(oo);
}
return false;
}
/** Set Entity Type.
Dictionary Entity Type;
 Determines ownership and synchronization */
public static final String COLUMNNAME_EntityType = "EntityType";

public void setEntityType (String EntityType)
{
if (EntityType == null) throw new IllegalArgumentException ("EntityType is mandatory");
if (EntityType.length() > 40)
{
log.warning("Length > 40 - truncated");
EntityType = EntityType.substring(0,40);
}
set_Value ("EntityType", EntityType);
}
/** Get Entity Type.
Dictionary Entity Type;
 Determines ownership and synchronization */
public String getEntityType() 
{
return (String)get_Value("EntityType");
}
/** Set Is Subcontracting.
The operation will be made in an external Work Center */
public static final String COLUMNNAME_IsSubcontracting = "IsSubcontracting";

public void setIsSubcontracting (boolean IsSubcontracting)
{
set_Value ("IsSubcontracting", new Boolean(IsSubcontracting));
}
/** Get Is Subcontracting.
The operation will be made in an external Work Center */
public boolean isSubcontracting() 
{
Object oo = get_Value("IsSubcontracting");
if (oo != null) 
{
 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
 return "Y".equals(oo);
}
return false;
}
/** Set Product.
Product, Service, Item */
public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

public void setM_Product_ID (int M_Product_ID)
{
set_Value ("M_Product_ID", new Integer(M_Product_ID));
}
/** Get Product.
Product, Service, Item */
public int getM_Product_ID() 
{
Integer ii = (Integer)get_Value("M_Product_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set PP_Wf_Node_Product_ID */
public static final String COLUMNNAME_PP_Wf_Node_Product_ID = "PP_Wf_Node_Product_ID";

public void setPP_Wf_Node_Product_ID (int PP_Wf_Node_Product_ID)
{
set_ValueNoCheck ("PP_Wf_Node_Product_ID", new Integer(PP_Wf_Node_Product_ID));
}
/** Get PP_Wf_Node_Product_ID */
public int getPP_Wf_Node_Product_ID() 
{
Integer ii = (Integer)get_Value("PP_Wf_Node_Product_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Quantity.
Quantity */
public static final String COLUMNNAME_Qty = "Qty";

public void setQty (BigDecimal Qty)
{
set_Value ("Qty", Qty);
}
/** Get Quantity.
Quantity */
public BigDecimal getQty() 
{
BigDecimal bd = (BigDecimal)get_Value("Qty");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set Sequence.
Method of ordering records;
 lowest number comes first */
public static final String COLUMNNAME_SeqNo = "SeqNo";

public void setSeqNo (int SeqNo)
{
set_Value ("SeqNo", new Integer(SeqNo));
}
/** Get Sequence.
Method of ordering records;
 lowest number comes first */
public int getSeqNo() 
{
Integer ii = (Integer)get_Value("SeqNo");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Yield */
public static final String COLUMNNAME_Yield = "Yield";

public void setYield (int Yield)
{
set_Value ("Yield", new Integer(Yield));
}
/** Get Yield */
public int getYield() 
{
Integer ii = (Integer)get_Value("Yield");
if (ii == null) return 0;
return ii.intValue();
}
}
