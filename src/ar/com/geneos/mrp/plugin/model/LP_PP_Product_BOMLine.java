/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;
import org.openXpertya.model.*;
import java.util.logging.Level;
 import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;
/** Modelo Generado por PP_Product_BOMLine
 *  @author Comunidad de Desarrollo Libertya*         *Basado en Codigo Original Modificado, Revisado y Optimizado de:*         * Jorg Janke 
 *  @version  - 2015-05-13 07:28:48.307 */
public class LP_PP_Product_BOMLine extends org.openXpertya.model.PO
{
/** Constructor estándar */
public LP_PP_Product_BOMLine (Properties ctx, int PP_Product_BOMLine_ID, String trxName)
{
super (ctx, PP_Product_BOMLine_ID, trxName);
/** if (PP_Product_BOMLine_ID == 0)
{
setIssueMethod (false);
setLine (0);
setM_Product_ID (0);
setPP_Product_Bom_ID (0);
setPP_Product_Bomline_ID (0);
setValidFrom (new Timestamp(System.currentTimeMillis()));
}
 */
}
/** Load Constructor */
public LP_PP_Product_BOMLine (Properties ctx, ResultSet rs, String trxName)
{
super (ctx, rs, trxName);
}
/** AD_Table_ID */
public static final int Table_ID = M_Table.getTableID("PP_Product_BOMLine");

/** TableName=PP_Product_BOMLine */
public static final String Table_Name="PP_Product_BOMLine";

protected static KeyNamePair Model = new KeyNamePair(Table_ID,"PP_Product_BOMLine");
protected static BigDecimal AccessLevel = new BigDecimal(3);

/** Load Meta Data */
protected POInfo initPO (Properties ctx)
{
POInfo poi = POInfo.getPOInfo (ctx, Table_ID);
return poi;
}
public String toString()
{
StringBuffer sb = new StringBuffer ("LP_PP_Product_BOMLine[").append(getID()).append("]");
return sb.toString();
}
/** Set Assay */
public static final String COLUMNNAME_Assay = "Assay";

public void setAssay (BigDecimal Assay)
{
set_Value ("Assay", Assay);
}
/** Get Assay */
public BigDecimal getAssay() 
{
BigDecimal bd = (BigDecimal)get_Value("Assay");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set BackflushGroup */
public static final String COLUMNNAME_BackflushGroup = "BackflushGroup";

public void setBackflushGroup (String BackflushGroup)
{
if (BackflushGroup != null && BackflushGroup.length() > 20)
{
log.warning("Length > 20 - truncated");
BackflushGroup = BackflushGroup.substring(0,20);
}
set_Value ("BackflushGroup", BackflushGroup);
}
/** Get BackflushGroup */
public String getBackflushGroup() 
{
return (String)get_Value("BackflushGroup");
}
public static final int COMPONENTTYPE_AD_Reference_ID = MReference.getReferenceID("PP_ComponentType");
/** By-Product = BY */
public static final String COMPONENTTYPE_By_Product = "BY";
/** Component = CO */
public static final String COMPONENTTYPE_Component = "CO";
/** Co-Product = CP */
public static final String COMPONENTTYPE_Co_Product = "CP";
/** Option = OP */
public static final String COMPONENTTYPE_Option = "OP";
/** Phantom = PH */
public static final String COMPONENTTYPE_Phantom = "PH";
/** Packing = PK */
public static final String COMPONENTTYPE_Packing = "PK";
/** Planning = PL */
public static final String COMPONENTTYPE_Planning = "PL";
/** Tools = TL */
public static final String COMPONENTTYPE_Tools = "TL";
/** Variant = VA */
public static final String COMPONENTTYPE_Variant = "VA";
/** Set Component Type */
public static final String COLUMNNAME_ComponentType = "ComponentType";

public void setComponentType (String ComponentType)
{
if (ComponentType == null || ComponentType.equals("BY") || ComponentType.equals("CO") || ComponentType.equals("CP") || ComponentType.equals("OP") || ComponentType.equals("PH") || ComponentType.equals("PK") || ComponentType.equals("PL") || ComponentType.equals("TL") || ComponentType.equals("VA"));
 else throw new IllegalArgumentException ("ComponentType Invalid value - Reference = COMPONENTTYPE_AD_Reference_ID - BY - CO - CP - OP - PH - PK - PL - TL - VA");
if (ComponentType != null && ComponentType.length() > 2)
{
log.warning("Length > 2 - truncated");
ComponentType = ComponentType.substring(0,2);
}
set_Value ("ComponentType", ComponentType);
}
/** Get Component Type */
public String getComponentType() 
{
return (String)get_Value("ComponentType");
}
/** Set costallocationperc */
public static final String COLUMNNAME_costallocationperc = "costallocationperc";

public void setcostallocationperc (BigDecimal costallocationperc)
{
set_Value ("costallocationperc", costallocationperc);
}
/** Get costallocationperc */
public BigDecimal getcostallocationperc() 
{
BigDecimal bd = (BigDecimal)get_Value("costallocationperc");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set UOM.
Unit of Measure */
public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

public void setC_UOM_ID (int C_UOM_ID)
{
if (C_UOM_ID <= 0) set_Value ("C_UOM_ID", null);
 else 
set_Value ("C_UOM_ID", new Integer(C_UOM_ID));
}
/** Get UOM.
Unit of Measure */
public int getC_UOM_ID() 
{
Integer ii = (Integer)get_Value("C_UOM_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Description.
Optional short description of the record */
public static final String COLUMNNAME_Description = "Description";

public void setDescription (String Description)
{
if (Description != null && Description.length() > 255)
{
log.warning("Length > 255 - truncated");
Description = Description.substring(0,255);
}
set_Value ("Description", Description);
}
/** Get Description.
Optional short description of the record */
public String getDescription() 
{
return (String)get_Value("Description");
}
/** Set feature */
public static final String COLUMNNAME_feature = "feature";

public void setfeature (String feature)
{
if (feature != null && feature.length() > 30)
{
log.warning("Length > 30 - truncated");
feature = feature.substring(0,30);
}
set_Value ("feature", feature);
}
/** Get feature */
public String getfeature() 
{
return (String)get_Value("feature");
}
/** Set Forecast */
public static final String COLUMNNAME_Forecast = "Forecast";

public void setForecast (BigDecimal Forecast)
{
set_Value ("Forecast", Forecast);
}
/** Get Forecast */
public BigDecimal getForecast() 
{
BigDecimal bd = (BigDecimal)get_Value("Forecast");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set Comment/Help.
Comment or Hint */
public static final String COLUMNNAME_Help = "Help";

public void setHelp (String Help)
{
if (Help != null && Help.length() > 2000)
{
log.warning("Length > 2000 - truncated");
Help = Help.substring(0,2000);
}
set_Value ("Help", Help);
}
/** Get Comment/Help.
Comment or Hint */
public String getHelp() 
{
return (String)get_Value("Help");
}
/** Set IsCritical */
public static final String COLUMNNAME_IsCritical = "IsCritical";

public void setIsCritical (boolean IsCritical)
{
set_Value ("IsCritical", new Boolean(IsCritical));
}
/** Get IsCritical */
public boolean isCritical() 
{
Object oo = get_Value("IsCritical");
if (oo != null) 
{
 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
 return "Y".equals(oo);
}
return false;
}
/** Set IsQtyPercentage */
public static final String COLUMNNAME_IsQtyPercentage = "IsQtyPercentage";

public void setIsQtyPercentage (boolean IsQtyPercentage)
{
set_Value ("IsQtyPercentage", new Boolean(IsQtyPercentage));
}
/** Get IsQtyPercentage */
public boolean isQtyPercentage() 
{
Object oo = get_Value("IsQtyPercentage");
if (oo != null) 
{
 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
 return "Y".equals(oo);
}
return false;
}
/** Set IssueMethod */
public static final String COLUMNNAME_IssueMethod = "IssueMethod";

public void setIssueMethod (boolean IssueMethod)
{
set_Value ("IssueMethod", new Boolean(IssueMethod));
}
/** Get IssueMethod */
public boolean issueMethod() 
{
Object oo = get_Value("IssueMethod");
if (oo != null) 
{
 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
 return "Y".equals(oo);
}
return false;
}
/** Set Lead Time Offset.
Optional Lead Time offest before starting production */
public static final String COLUMNNAME_LeadTimeOffset = "LeadTimeOffset";

public void setLeadTimeOffset (int LeadTimeOffset)
{
set_Value ("LeadTimeOffset", new Integer(LeadTimeOffset));
}
/** Get Lead Time Offset.
Optional Lead Time offest before starting production */
public int getLeadTimeOffset() 
{
Integer ii = (Integer)get_Value("LeadTimeOffset");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Line No.
Unique line for this document */
public static final String COLUMNNAME_Line = "Line";

public void setLine (int Line)
{
set_Value ("Line", new Integer(Line));
}
/** Get Line No.
Unique line for this document */
public int getLine() 
{
Integer ii = (Integer)get_Value("Line");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Attribute Set Instance.
Product Attribute Set Instance */
public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
{
if (M_AttributeSetInstance_ID <= 0) set_Value ("M_AttributeSetInstance_ID", null);
 else 
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
/** Set Change Notice.
Bill of Materials (Engineering) Change Notice (Version) */
public static final String COLUMNNAME_M_ChangeNotice_ID = "M_ChangeNotice_ID";

public void setM_ChangeNotice_ID (int M_ChangeNotice_ID)
{
if (M_ChangeNotice_ID <= 0) set_Value ("M_ChangeNotice_ID", null);
 else 
set_Value ("M_ChangeNotice_ID", new Integer(M_ChangeNotice_ID));
}
/** Get Change Notice.
Bill of Materials (Engineering) Change Notice (Version) */
public int getM_ChangeNotice_ID() 
{
Integer ii = (Integer)get_Value("M_ChangeNotice_ID");
if (ii == null) return 0;
return ii.intValue();
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
/** Set PP_Product_Bom_ID */
public static final String COLUMNNAME_PP_Product_Bom_ID = "PP_Product_Bom_ID";

public void setPP_Product_Bom_ID (int PP_Product_Bom_ID)
{
set_Value ("PP_Product_Bom_ID", new Integer(PP_Product_Bom_ID));
}
/** Get PP_Product_Bom_ID */
public int getPP_Product_Bom_ID() 
{
Integer ii = (Integer)get_Value("PP_Product_Bom_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set PP_Product_Bomline_ID */
public static final String COLUMNNAME_PP_Product_Bomline_ID = "PP_Product_Bomline_ID";

public void setPP_Product_Bomline_ID (int PP_Product_Bomline_ID)
{
set_ValueNoCheck ("PP_Product_Bomline_ID", new Integer(PP_Product_Bomline_ID));
}
/** Get PP_Product_Bomline_ID */
public int getPP_Product_Bomline_ID() 
{
Integer ii = (Integer)get_Value("PP_Product_Bomline_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Qty % */
public static final String COLUMNNAME_QtyBatch = "QtyBatch";

public void setQtyBatch (BigDecimal QtyBatch)
{
set_Value ("QtyBatch", QtyBatch);
}
/** Get Qty % */
public BigDecimal getQtyBatch() 
{
BigDecimal bd = (BigDecimal)get_Value("QtyBatch");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set Qty.
Bill of Materials Quantity */
public static final String COLUMNNAME_QtyBOM = "QtyBOM";

public void setQtyBOM (BigDecimal QtyBOM)
{
set_Value ("QtyBOM", QtyBOM);
}
/** Get Qty.
Bill of Materials Quantity */
public BigDecimal getQtyBOM() 
{
BigDecimal bd = (BigDecimal)get_Value("QtyBOM");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set Scrap */
public static final String COLUMNNAME_Scrap = "Scrap";

public void setScrap (BigDecimal Scrap)
{
set_Value ("Scrap", Scrap);
}
/** Get Scrap */
public BigDecimal getScrap() 
{
BigDecimal bd = (BigDecimal)get_Value("Scrap");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set Valid from.
Valid from including this date (first day) */
public static final String COLUMNNAME_ValidFrom = "ValidFrom";

public void setValidFrom (Timestamp ValidFrom)
{
if (ValidFrom == null) throw new IllegalArgumentException ("ValidFrom is mandatory");
set_Value ("ValidFrom", ValidFrom);
}
/** Get Valid from.
Valid from including this date (first day) */
public Timestamp getValidFrom() 
{
return (Timestamp)get_Value("ValidFrom");
}
/** Set Valid to.
Valid to including this date (last day) */
public static final String COLUMNNAME_ValidTo = "ValidTo";

public void setValidTo (Timestamp ValidTo)
{
set_Value ("ValidTo", ValidTo);
}
/** Get Valid to.
Valid to including this date (last day) */
public Timestamp getValidTo() 
{
return (Timestamp)get_Value("ValidTo");
}
}
