/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;
import org.openXpertya.model.*;
import java.util.logging.Level;
 import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;
/** Modelo Generado por M_Product_Category_Acct
 *  @author Comunidad de Desarrollo Libertya*         *Basado en Codigo Original Modificado, Revisado y Optimizado de:*         * Jorg Janke 
 *  @version  - 2015-12-14 16:59:01.405 */
public class LP_M_Product_Category_Acct extends org.openXpertya.model.MProductCategoryAcct
{
/** Constructor estándar */
public LP_M_Product_Category_Acct (Properties ctx, int M_Product_Category_Acct_ID, String trxName)
{
super (ctx, M_Product_Category_Acct_ID, trxName);
/** if (M_Product_Category_Acct_ID == 0)
{
}
 */
}
/** Load Constructor */
public LP_M_Product_Category_Acct (Properties ctx, ResultSet rs, String trxName)
{
super (ctx, rs, trxName);
}
public String toString()
{
StringBuffer sb = new StringBuffer ("LP_M_Product_Category_Acct[").append(getID()).append("]");
return sb.toString();
}
public static final int COSTINGLEVEL_AD_Reference_ID = MReference.getReferenceID("Niveles de Costeo");
/** Lote = B */
public static final String COSTINGLEVEL_Lote = "B";
/** Compañía = C */
public static final String COSTINGLEVEL_Compañía = "C";
/** Organización = O */
public static final String COSTINGLEVEL_Organización = "O";
/** Almacén = W */
public static final String COSTINGLEVEL_Almacén = "W";
/** Set CostingLevel */
public void setCostingLevel (String CostingLevel)
{
if (CostingLevel == null || CostingLevel.equals("B") || CostingLevel.equals("C") || CostingLevel.equals("O") || CostingLevel.equals("W"));
 else throw new IllegalArgumentException ("CostingLevel Invalid value - Reference = COSTINGLEVEL_AD_Reference_ID - B - C - O - W");
if (CostingLevel != null && CostingLevel.length() > 1)
{
log.warning("Length > 1 - truncated");
CostingLevel = CostingLevel.substring(0,1);
}
set_Value ("CostingLevel", CostingLevel);
}
/** Get CostingLevel */
public String getCostingLevel() 
{
return (String)get_Value("CostingLevel");
}
/** Set Cost Type.
Type of Cost (e.g. Current, Plan, Future) */
public void setM_CostType_ID (int M_CostType_ID)
{
if (M_CostType_ID <= 0) set_Value ("M_CostType_ID", null);
 else 
set_Value ("M_CostType_ID", new Integer(M_CostType_ID));
}
/** Get Cost Type.
Type of Cost (e.g. Current, Plan, Future) */
public int getM_CostType_ID() 
{
Integer ii = (Integer)get_Value("M_CostType_ID");
if (ii == null) return 0;
return ii.intValue();
}
}
