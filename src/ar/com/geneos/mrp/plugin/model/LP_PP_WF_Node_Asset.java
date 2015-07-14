package ar.com.geneos.mrp.plugin.model;
/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import org.openXpertya.model.*;
import java.util.logging.Level;
 import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;
/** Modelo Generado por PP_WF_Node_Asset
 *  @author Comunidad de Desarrollo Libertya*         *Basado en Codigo Original Modificado, Revisado y Optimizado de:*         * Jorg Janke 
 *  @version  - 2015-04-29 14:39:08.677 */
public class LP_PP_WF_Node_Asset extends org.openXpertya.model.PO
{
/** Constructor estándar */
public LP_PP_WF_Node_Asset (Properties ctx, int PP_WF_Node_Asset_ID, String trxName)
{
super (ctx, PP_WF_Node_Asset_ID, trxName);
/** if (PP_WF_Node_Asset_ID == 0)
{
setA_Asset_ID (0);
setAD_WF_Node_ID (0);
setPP_Wf_Node_Asset_ID (0);
setSeqNo (0);
}
 */
}
/** Load Constructor */
public LP_PP_WF_Node_Asset (Properties ctx, ResultSet rs, String trxName)
{
super (ctx, rs, trxName);
}
/** AD_Table_ID */
public static final int Table_ID = M_Table.getTableID("PP_WF_Node_Asset");

/** TableName=PP_WF_Node_Asset */
public static final String Table_Name="PP_WF_Node_Asset";

protected static KeyNamePair Model = new KeyNamePair(Table_ID,"PP_WF_Node_Asset");
protected static BigDecimal AccessLevel = new BigDecimal(3);

/** Load Meta Data */
protected POInfo initPO (Properties ctx)
{
POInfo poi = POInfo.getPOInfo (ctx, Table_ID);
return poi;
}
public String toString()
{
StringBuffer sb = new StringBuffer ("LP_PP_WF_Node_Asset[").append(getID()).append("]");
return sb.toString();
}
/** Set Asset.
Asset used internally or by customers */
public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

public void setA_Asset_ID (int A_Asset_ID)
{
set_Value ("A_Asset_ID", new Integer(A_Asset_ID));
}
/** Get Asset.
Asset used internally or by customers */
public int getA_Asset_ID() 
{
Integer ii = (Integer)get_Value("A_Asset_ID");
if (ii == null) return 0;
return ii.intValue();
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
/** Set PP_Wf_Node_Asset_ID */
public static final String COLUMNNAME_PP_Wf_Node_Asset_ID = "PP_Wf_Node_Asset_ID";

public void setPP_Wf_Node_Asset_ID (int PP_Wf_Node_Asset_ID)
{
set_ValueNoCheck ("PP_Wf_Node_Asset_ID", new Integer(PP_Wf_Node_Asset_ID));
}
/** Get PP_Wf_Node_Asset_ID */
public int getPP_Wf_Node_Asset_ID() 
{
Integer ii = (Integer)get_Value("PP_Wf_Node_Asset_ID");
if (ii == null) return 0;
return ii.intValue();
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
}
