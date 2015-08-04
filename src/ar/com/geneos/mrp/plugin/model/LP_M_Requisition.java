/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;
import org.openXpertya.model.*;
import java.util.logging.Level;
 import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;
/** Modelo Generado por M_Requisition
 *  @author Comunidad de Desarrollo Libertya*         *Basado en Codigo Original Modificado, Revisado y Optimizado de:*         * Jorg Janke 
 *  @version  - 2015-07-31 20:08:51.615 */
public class LP_M_Requisition extends X_M_Requisition
{
/** Constructor estándar */
public LP_M_Requisition (Properties ctx, int M_Requisition_ID, String trxName)
{
super (ctx, M_Requisition_ID, trxName);
/** if (M_Requisition_ID == 0)
{
setC_DocType_ID (0);
setDateDoc (new Timestamp(System.currentTimeMillis()));
}
 */
}
/** Load Constructor */
public LP_M_Requisition (Properties ctx, ResultSet rs, String trxName)
{
super (ctx, rs, trxName);
}
public String toString()
{
StringBuffer sb = new StringBuffer ("LP_M_Requisition[").append(getID()).append("]");
return sb.toString();
}
/** Set Document Type.
Document type or rules */
public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

public void setC_DocType_ID (int C_DocType_ID)
{
set_Value ("C_DocType_ID", new Integer(C_DocType_ID));
}
/** Get Document Type.
Document type or rules */
public int getC_DocType_ID() 
{
Integer ii = (Integer)get_Value("C_DocType_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Document Date.
Date of the Document */
public static final String COLUMNNAME_DateDoc = "DateDoc";

public void setDateDoc (Timestamp DateDoc)
{
if (DateDoc == null) throw new IllegalArgumentException ("DateDoc is mandatory");
set_Value ("DateDoc", DateDoc);
}
/** Get Document Date.
Date of the Document */
public Timestamp getDateDoc() 
{
return (Timestamp)get_Value("DateDoc");
}
}
