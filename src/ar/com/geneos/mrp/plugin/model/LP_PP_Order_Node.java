package ar.com.geneos.mrp.plugin.model;
/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import org.openXpertya.model.*;
import java.util.logging.Level;
 import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;
/** Modelo Generado por PP_Order_Node
 *  @author Comunidad de Desarrollo Libertya*         *Basado en Codigo Original Modificado, Revisado y Optimizado de:*         * Jorg Janke 
 *  @version  - 2015-04-30 14:38:07.662 */
public class LP_PP_Order_Node extends org.openXpertya.model.PO
{
/** Constructor estándar */
public LP_PP_Order_Node (Properties ctx, int PP_Order_Node_ID, String trxName)
{
super (ctx, PP_Order_Node_ID, trxName);
/** if (PP_Order_Node_ID == 0)
{
setAction (null);
setAD_WF_Node_ID (0);
setAD_Workflow_ID (0);
setCost (Env.ZERO);
setEntityType (null);
setIsCentrallyMaintained (false);
setJoinElement (null);	// X
setlimit (0);
setName (null);
setPP_Order_ID (0);
setPP_Order_Node_ID (0);
setPP_Order_Workflow_ID (0);
setPriority (0);
setSplitElement (null);	// X
setValue (null);
setWaitingTime (0);
setWorkingTime (0);
setXPosition (0);
setYPosition (0);
}
 */
}
/** Load Constructor */
public LP_PP_Order_Node (Properties ctx, ResultSet rs, String trxName)
{
super (ctx, rs, trxName);
}
/** AD_Table_ID */
public static final int Table_ID = M_Table.getTableID("PP_Order_Node");

/** TableName=PP_Order_Node */
public static final String Table_Name="PP_Order_Node";

protected static KeyNamePair Model = new KeyNamePair(Table_ID,"PP_Order_Node");
protected static BigDecimal AccessLevel = new BigDecimal(3);

/** Load Meta Data */
protected POInfo initPO (Properties ctx)
{
POInfo poi = POInfo.getPOInfo (ctx, Table_ID);
return poi;
}
public String toString()
{
StringBuffer sb = new StringBuffer ("LP_PP_Order_Node[").append(getID()).append("]");
return sb.toString();
}
public static final int ACTION_AD_Reference_ID = MReference.getReferenceID("WF_Action");
/** User Choice = C */
public static final String ACTION_UserChoice = "C";
/** Apps Report = R */
public static final String ACTION_AppsReport = "R";
/** Apps Process = P */
public static final String ACTION_AppsProcess = "P";
/** User Workbench = B */
public static final String ACTION_UserWorkbench = "B";
/** Apps Task = T */
public static final String ACTION_AppsTask = "T";
/** Document Action = D */
public static final String ACTION_DocumentAction = "D";
/** User Form = X */
public static final String ACTION_UserForm = "X";
/** Wait (Sleep) = Z */
public static final String ACTION_WaitSleep = "Z";
/** Sub Workflow = F */
public static final String ACTION_SubWorkflow = "F";
/** Set Variable = V */
public static final String ACTION_SetVariable = "V";
/** User Window = W */
public static final String ACTION_UserWindow = "W";
/** Set Action.
Indicates the Action to be performed */
public static final String COLUMNNAME_Action = "Action";

public void setAction (String Action)
{
if (Action.equals("C") || Action.equals("R") || Action.equals("P") || Action.equals("B") || Action.equals("T") || Action.equals("D") || Action.equals("X") || Action.equals("Z") || Action.equals("F") || Action.equals("V") || Action.equals("W"));
 else throw new IllegalArgumentException ("Action Invalid value - Reference = ACTION_AD_Reference_ID - C - R - P - B - T - D - X - Z - F - V - W");
if (Action == null) throw new IllegalArgumentException ("Action is mandatory");
if (Action.length() > 1)
{
log.warning("Length > 1 - truncated");
Action = Action.substring(0,1);
}
set_Value ("Action", Action);
}
/** Get Action.
Indicates the Action to be performed */
public String getAction() 
{
return (String)get_Value("Action");
}
/** Set Column.
Column in the table */
public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

public void setAD_Column_ID (int AD_Column_ID)
{
if (AD_Column_ID <= 0) set_Value ("AD_Column_ID", null);
 else 
set_Value ("AD_Column_ID", new Integer(AD_Column_ID));
}
/** Get Column.
Column in the table */
public int getAD_Column_ID() 
{
Integer ii = (Integer)get_Value("AD_Column_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Special Form.
Special Form */
public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

public void setAD_Form_ID (int AD_Form_ID)
{
if (AD_Form_ID <= 0) set_Value ("AD_Form_ID", null);
 else 
set_Value ("AD_Form_ID", new Integer(AD_Form_ID));
}
/** Get Special Form.
Special Form */
public int getAD_Form_ID() 
{
Integer ii = (Integer)get_Value("AD_Form_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Image.
System Image or Icon */
public static final String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

public void setAD_Image_ID (int AD_Image_ID)
{
if (AD_Image_ID <= 0) set_Value ("AD_Image_ID", null);
 else 
set_Value ("AD_Image_ID", new Integer(AD_Image_ID));
}
/** Get Image.
System Image or Icon */
public int getAD_Image_ID() 
{
Integer ii = (Integer)get_Value("AD_Image_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Process.
Process or Report */
public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

public void setAD_Process_ID (int AD_Process_ID)
{
if (AD_Process_ID <= 0) set_Value ("AD_Process_ID", null);
 else 
set_Value ("AD_Process_ID", new Integer(AD_Process_ID));
}
/** Get Process.
Process or Report */
public int getAD_Process_ID() 
{
Integer ii = (Integer)get_Value("AD_Process_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set OS Task.
Operation System Task */
public static final String COLUMNNAME_AD_Task_ID = "AD_Task_ID";

public void setAD_Task_ID (int AD_Task_ID)
{
if (AD_Task_ID <= 0) set_Value ("AD_Task_ID", null);
 else 
set_Value ("AD_Task_ID", new Integer(AD_Task_ID));
}
/** Get OS Task.
Operation System Task */
public int getAD_Task_ID() 
{
Integer ii = (Integer)get_Value("AD_Task_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Workflow Block.
Workflow Transaction Execution Block */
public static final String COLUMNNAME_AD_WF_Block_ID = "AD_WF_Block_ID";

public void setAD_WF_Block_ID (int AD_WF_Block_ID)
{
if (AD_WF_Block_ID <= 0) set_Value ("AD_WF_Block_ID", null);
 else 
set_Value ("AD_WF_Block_ID", new Integer(AD_WF_Block_ID));
}
/** Get Workflow Block.
Workflow Transaction Execution Block */
public int getAD_WF_Block_ID() 
{
Integer ii = (Integer)get_Value("AD_WF_Block_ID");
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
/** Set Workflow Responsible.
Responsible for Workflow Execution */
public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

public void setAD_WF_Responsible_ID (int AD_WF_Responsible_ID)
{
if (AD_WF_Responsible_ID <= 0) set_Value ("AD_WF_Responsible_ID", null);
 else 
set_Value ("AD_WF_Responsible_ID", new Integer(AD_WF_Responsible_ID));
}
/** Get Workflow Responsible.
Responsible for Workflow Execution */
public int getAD_WF_Responsible_ID() 
{
Integer ii = (Integer)get_Value("AD_WF_Responsible_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Window.
Data entry or display window */
public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

public void setAD_Window_ID (int AD_Window_ID)
{
if (AD_Window_ID <= 0) set_Value ("AD_Window_ID", null);
 else 
set_Value ("AD_Window_ID", new Integer(AD_Window_ID));
}
/** Get Window.
Data entry or display window */
public int getAD_Window_ID() 
{
Integer ii = (Integer)get_Value("AD_Window_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Workflow.
Workflow or combination of tasks */
public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

public void setAD_Workflow_ID (int AD_Workflow_ID)
{
set_Value ("AD_Workflow_ID", new Integer(AD_Workflow_ID));
}
/** Get Workflow.
Workflow or combination of tasks */
public int getAD_Workflow_ID() 
{
Integer ii = (Integer)get_Value("AD_Workflow_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Attribute Name.
Name of the Attribute */
public static final String COLUMNNAME_AttributeName = "AttributeName";

public void setAttributeName (String AttributeName)
{
if (AttributeName != null && AttributeName.length() > 60)
{
log.warning("Length > 60 - truncated");
AttributeName = AttributeName.substring(0,60);
}
set_Value ("AttributeName", AttributeName);
}
/** Get Attribute Name.
Name of the Attribute */
public String getAttributeName() 
{
return (String)get_Value("AttributeName");
}
/** Set Attribute Value.
Value of the Attribute */
public static final String COLUMNNAME_AttributeValue = "AttributeValue";

public void setAttributeValue (String AttributeValue)
{
if (AttributeValue != null && AttributeValue.length() > 60)
{
log.warning("Length > 60 - truncated");
AttributeValue = AttributeValue.substring(0,60);
}
set_Value ("AttributeValue", AttributeValue);
}
/** Get Attribute Value.
Value of the Attribute */
public String getAttributeValue() 
{
return (String)get_Value("AttributeValue");
}
/** Set Business Partner .
Identifies a Business Partner */
public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

public void setC_BPartner_ID (int C_BPartner_ID)
{
if (C_BPartner_ID <= 0) set_Value ("C_BPartner_ID", null);
 else 
set_Value ("C_BPartner_ID", new Integer(C_BPartner_ID));
}
/** Get Business Partner .
Identifies a Business Partner */
public int getC_BPartner_ID() 
{
Integer ii = (Integer)get_Value("C_BPartner_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Cost.
Cost information */
public static final String COLUMNNAME_Cost = "Cost";

public void setCost (BigDecimal Cost)
{
if (Cost == null) throw new IllegalArgumentException ("Cost is mandatory");
set_Value ("Cost", Cost);
}
/** Get Cost.
Cost information */
public BigDecimal getCost() 
{
BigDecimal bd = (BigDecimal)get_Value("Cost");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set Finish Date.
Finish or (planned) completion date */
public static final String COLUMNNAME_DateFinish = "DateFinish";

public void setDateFinish (Timestamp DateFinish)
{
set_Value ("DateFinish", DateFinish);
}
/** Get Finish Date.
Finish or (planned) completion date */
public Timestamp getDateFinish() 
{
return (Timestamp)get_Value("DateFinish");
}
/** Set Finish Date Scheduled.
Last date of the schedule. */
public static final String COLUMNNAME_DateFinishSchedule = "DateFinishSchedule";

public void setDateFinishSchedule (Timestamp DateFinishSchedule)
{
set_Value ("DateFinishSchedule", DateFinishSchedule);
}
/** Get Finish Date Scheduled.
Last date of the schedule. */
public Timestamp getDateFinishSchedule() 
{
return (Timestamp)get_Value("DateFinishSchedule");
}
/** Set Start Date.
Starting Date */
public static final String COLUMNNAME_DateStart = "DateStart";

public void setDateStart (Timestamp DateStart)
{
set_Value ("DateStart", DateStart);
}
/** Get Start Date.
Starting Date */
public Timestamp getDateStart() 
{
return (Timestamp)get_Value("DateStart");
}
/** Set Starting Date Scheduled.
Starting Date Scheduled */
public static final String COLUMNNAME_DateStartSchedule = "DateStartSchedule";

public void setDateStartSchedule (Timestamp DateStartSchedule)
{
set_Value ("DateStartSchedule", DateStartSchedule);
}
/** Get Starting Date Scheduled.
Starting Date Scheduled */
public Timestamp getDateStartSchedule() 
{
return (Timestamp)get_Value("DateStartSchedule");
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
public static final int DOCACTION_AD_Reference_ID = MReference.getReferenceID("_Document Action");
/** Approve = AP */
public static final String DOCACTION_Approve = "AP";
/** Close = CL */
public static final String DOCACTION_Close = "CL";
/** Prepare = PR */
public static final String DOCACTION_Prepare = "PR";
/** Invalidate = IN */
public static final String DOCACTION_Invalidate = "IN";
/** Complete = CO */
public static final String DOCACTION_Complete = "CO";
/** <None> = -- */
public static final String DOCACTION_None = "--";
/** Reverse - Correct = RC */
public static final String DOCACTION_Reverse_Correct = "RC";
/** Reject = RJ */
public static final String DOCACTION_Reject = "RJ";
/** Reverse - Accrual = RA */
public static final String DOCACTION_Reverse_Accrual = "RA";
/** Wait Complete = WC */
public static final String DOCACTION_WaitComplete = "WC";
/** Unlock = XL */
public static final String DOCACTION_Unlock = "XL";
/** Re-activate = RE */
public static final String DOCACTION_Re_Activate = "RE";
/** Post = PO */
public static final String DOCACTION_Post = "PO";
/** Void = VO */
public static final String DOCACTION_Void = "VO";
/** Set Document Action.
The targeted status of the document */
public static final String COLUMNNAME_DocAction = "DocAction";

public void setDocAction (String DocAction)
{
if (DocAction == null || DocAction.equals("AP") || DocAction.equals("CL") || DocAction.equals("PR") || DocAction.equals("IN") || DocAction.equals("CO") || DocAction.equals("--") || DocAction.equals("RC") || DocAction.equals("RJ") || DocAction.equals("RA") || DocAction.equals("WC") || DocAction.equals("XL") || DocAction.equals("RE") || DocAction.equals("PO") || DocAction.equals("VO"));
 else throw new IllegalArgumentException ("DocAction Invalid value - Reference = DOCACTION_AD_Reference_ID - AP - CL - PR - IN - CO - -- - RC - RJ - RA - WC - XL - RE - PO - VO");
if (DocAction != null && DocAction.length() > 2)
{
log.warning("Length > 2 - truncated");
DocAction = DocAction.substring(0,2);
}
set_Value ("DocAction", DocAction);
}
/** Get Document Action.
The targeted status of the document */
public String getDocAction() 
{
return (String)get_Value("DocAction");
}
public static final int DOCSTATUS_AD_Reference_ID = MReference.getReferenceID("_Document Status");
/** Voided = VO */
public static final String DOCSTATUS_Voided = "VO";
/** Not Approved = NA */
public static final String DOCSTATUS_NotApproved = "NA";
/** In Progress = IP */
public static final String DOCSTATUS_InProgress = "IP";
/** Completed = CO */
public static final String DOCSTATUS_Completed = "CO";
/** Approved = AP */
public static final String DOCSTATUS_Approved = "AP";
/** Closed = CL */
public static final String DOCSTATUS_Closed = "CL";
/** Waiting Confirmation = WC */
public static final String DOCSTATUS_WaitingConfirmation = "WC";
/** Waiting Payment = WP */
public static final String DOCSTATUS_WaitingPayment = "WP";
/** Unknown = ?? */
public static final String DOCSTATUS_Unknown = "??";
/** Drafted = DR */
public static final String DOCSTATUS_Drafted = "DR";
/** Invalid = IN */
public static final String DOCSTATUS_Invalid = "IN";
/** Reversed = RE */
public static final String DOCSTATUS_Reversed = "RE";
/** Set Document Status.
The current status of the document */
public static final String COLUMNNAME_DocStatus = "DocStatus";

public void setDocStatus (String DocStatus)
{
if (DocStatus == null || DocStatus.equals("VO") || DocStatus.equals("NA") || DocStatus.equals("IP") || DocStatus.equals("CO") || DocStatus.equals("AP") || DocStatus.equals("CL") || DocStatus.equals("WC") || DocStatus.equals("WP") || DocStatus.equals("??") || DocStatus.equals("DR") || DocStatus.equals("IN") || DocStatus.equals("RE"));
 else throw new IllegalArgumentException ("DocStatus Invalid value - Reference = DOCSTATUS_AD_Reference_ID - VO - NA - IP - CO - AP - CL - WC - WP - ?? - DR - IN - RE");
if (DocStatus != null && DocStatus.length() > 2)
{
log.warning("Length > 2 - truncated");
DocStatus = DocStatus.substring(0,2);
}
set_Value ("DocStatus", DocStatus);
}
/** Get Document Status.
The current status of the document */
public String getDocStatus() 
{
return (String)get_Value("DocStatus");
}
/** Set Duration.
Normal Duration in Duration Unit */
public static final String COLUMNNAME_Duration = "Duration";

public void setDuration (int Duration)
{
set_Value ("Duration", new Integer(Duration));
}
/** Get Duration.
Normal Duration in Duration Unit */
public int getDuration() 
{
Integer ii = (Integer)get_Value("Duration");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Duration Real */
public static final String COLUMNNAME_DurationReal = "DurationReal";

public void setDurationReal (int DurationReal)
{
set_Value ("DurationReal", new Integer(DurationReal));
}
/** Get Duration Real */
public int getDurationReal() 
{
Integer ii = (Integer)get_Value("DurationReal");
if (ii == null) return 0;
return ii.intValue();
}
/** Set durationrequired */
public static final String COLUMNNAME_durationrequired = "durationrequired";

public void setDurationRequired (int durationrequired)
{
set_Value ("durationrequired", new Integer(durationrequired));
}
/** Get durationrequired */
public int getDurationRequired() 
{
Integer ii = (Integer)get_Value("durationrequired");
if (ii == null) return 0;
return ii.intValue();
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
public static final int FINISHMODE_AD_Reference_ID = MReference.getReferenceID("WF_Start-Finish Mode");
/** Automatic = A */
public static final String FINISHMODE_Automatic = "A";
/** Manual = M */
public static final String FINISHMODE_Manual = "M";
/** Set Finish Mode.
Workflow Activity Finish Mode */
public static final String COLUMNNAME_FinishMode = "FinishMode";

public void setFinishMode (String FinishMode)
{
if (FinishMode == null || FinishMode.equals("A") || FinishMode.equals("M"));
 else throw new IllegalArgumentException ("FinishMode Invalid value - Reference = FINISHMODE_AD_Reference_ID - A - M");
if (FinishMode != null && FinishMode.length() > 1)
{
log.warning("Length > 1 - truncated");
FinishMode = FinishMode.substring(0,1);
}
set_Value ("FinishMode", FinishMode);
}
/** Get Finish Mode.
Workflow Activity Finish Mode */
public String getFinishMode() 
{
return (String)get_Value("FinishMode");
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
/** Set Centrally maintained.
Information maintained in System Element table */
public static final String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";

public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
{
set_Value ("IsCentrallyMaintained", new Boolean(IsCentrallyMaintained));
}
/** Get Centrally maintained.
Information maintained in System Element table */
public boolean isCentrallyMaintained() 
{
Object oo = get_Value("IsCentrallyMaintained");
if (oo != null) 
{
 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
 return "Y".equals(oo);
}
return false;
}
/** Set IsMilestone.
This opertion will be used to report shop floor activity in the work order. */
public static final String COLUMNNAME_IsMilestone = "IsMilestone";

public void setIsMilestone (boolean IsMilestone)
{
set_Value ("IsMilestone", new Boolean(IsMilestone));
}
/** Get IsMilestone.
This opertion will be used to report shop floor activity in the work order. */
public boolean isMilestone() 
{
Object oo = get_Value("IsMilestone");
if (oo != null) 
{
 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
 return "Y".equals(oo);
}
return false;
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
public static final int JOINELEMENT_AD_Reference_ID = MReference.getReferenceID("WF_Join_Split");
/** AND = A */
public static final String JOINELEMENT_AND = "A";
/** XOR = X */
public static final String JOINELEMENT_XOR = "X";
/** Set Join Element.
Semantics for multiple incoming Transitions */
public static final String COLUMNNAME_JoinElement = "JoinElement";

public void setJoinElement (String JoinElement)
{
if (JoinElement.equals("A") || JoinElement.equals("X"));
 else throw new IllegalArgumentException ("JoinElement Invalid value - Reference = JOINELEMENT_AD_Reference_ID - A - X");
if (JoinElement == null) throw new IllegalArgumentException ("JoinElement is mandatory");
if (JoinElement.length() > 1)
{
log.warning("Length > 1 - truncated");
JoinElement = JoinElement.substring(0,1);
}
set_Value ("JoinElement", JoinElement);
}
/** Get Join Element.
Semantics for multiple incoming Transitions */
public String getJoinElement() 
{
return (String)get_Value("JoinElement");
}
/** Set limit */
public static final String COLUMNNAME_limit = "limit";

public void setlimit (int limit)
{
set_Value ("limit", new Integer(limit));
}
/** Get limit */
public int getlimit() 
{
Integer ii = (Integer)get_Value("limit");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Move Time.
Time to move material form one operation to another */
public static final String COLUMNNAME_MovingTime = "MovingTime";

public void setMovingTime (int MovingTime)
{
set_Value ("MovingTime", new Integer(MovingTime));
}
/** Get Move Time.
Time to move material form one operation to another */
public int getMovingTime() 
{
Integer ii = (Integer)get_Value("MovingTime");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Name.
Alphanumeric identifier of the entity */
public static final String COLUMNNAME_Name = "Name";

public void setName (String Name)
{
if (Name == null) throw new IllegalArgumentException ("Name is mandatory");
if (Name.length() > 60)
{
log.warning("Length > 60 - truncated");
Name = Name.substring(0,60);
}
set_Value ("Name", Name);
}
/** Get Name.
Alphanumeric identifier of the entity */
public String getName() 
{
return (String)get_Value("Name");
}
public KeyNamePair getKeyNamePair() 
{
return new KeyNamePair(getID(), getName());
}
/** Set Overlap Units.
No. of prodcts you need to finish in a operation before you start the next one. */
public static final String COLUMNNAME_OverlapUnits = "OverlapUnits";

public void setOverlapUnits (int OverlapUnits)
{
set_Value ("OverlapUnits", new Integer(OverlapUnits));
}
/** Get Overlap Units.
No. of prodcts you need to finish in a operation before you start the next one. */
public int getOverlapUnits() 
{
Integer ii = (Integer)get_Value("OverlapUnits");
if (ii == null) return 0;
return ii.intValue();
}
/** Set PP_Order_ID */
public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

public void setPP_Order_ID (int PP_Order_ID)
{
set_Value ("PP_Order_ID", new Integer(PP_Order_ID));
}
/** Get PP_Order_ID */
public int getPP_Order_ID() 
{
Integer ii = (Integer)get_Value("PP_Order_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set PP_Order_Node_ID */
public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

public void setPP_Order_Node_ID (int PP_Order_Node_ID)
{
set_ValueNoCheck ("PP_Order_Node_ID", new Integer(PP_Order_Node_ID));
}
/** Get PP_Order_Node_ID */
public int getPP_Order_Node_ID() 
{
Integer ii = (Integer)get_Value("PP_Order_Node_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set PP_Order_Workflow_ID */
public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

public void setPP_Order_Workflow_ID (int PP_Order_Workflow_ID)
{
set_Value ("PP_Order_Workflow_ID", new Integer(PP_Order_Workflow_ID));
}
/** Get PP_Order_Workflow_ID */
public int getPP_Order_Workflow_ID() 
{
Integer ii = (Integer)get_Value("PP_Order_Workflow_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Priority.
Indicates if this request is of a high, medium or low priority. */
public static final String COLUMNNAME_Priority = "Priority";

public void setPriority (int Priority)
{
set_Value ("Priority", new Integer(Priority));
}
/** Get Priority.
Indicates if this request is of a high, medium or low priority. */
public int getPriority() 
{
Integer ii = (Integer)get_Value("Priority");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Delivered Quantity.
Delivered Quantity */
public static final String COLUMNNAME_QtyDelivered = "QtyDelivered";

public void setQtyDelivered (BigDecimal QtyDelivered)
{
set_Value ("QtyDelivered", QtyDelivered);
}
/** Get Delivered Quantity.
Delivered Quantity */
public BigDecimal getQtyDelivered() 
{
BigDecimal bd = (BigDecimal)get_Value("QtyDelivered");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set Qty Reject */
public static final String COLUMNNAME_QtyReject = "QtyReject";

public void setQtyReject (BigDecimal QtyReject)
{
set_Value ("QtyReject", QtyReject);
}
/** Get Qty Reject */
public BigDecimal getQtyReject() 
{
BigDecimal bd = (BigDecimal)get_Value("QtyReject");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set qtyrequired */
public static final String COLUMNNAME_qtyrequired = "qtyrequired";

public void setQtyRequired (BigDecimal qtyrequired)
{
set_Value ("qtyrequired", qtyrequired);
}
/** Get qtyrequired */
public BigDecimal getQtyRequired() 
{
BigDecimal bd = (BigDecimal)get_Value("qtyrequired");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set Qty Scrap */
public static final String COLUMNNAME_QtyScrap = "QtyScrap";

public void setQtyScrap (BigDecimal QtyScrap)
{
set_Value ("QtyScrap", QtyScrap);
}
/** Get Qty Scrap */
public BigDecimal getQtyScrap() 
{
BigDecimal bd = (BigDecimal)get_Value("QtyScrap");
if (bd == null) return Env.ZERO;
return bd;
}
/** Set QueuingTime */
public static final String COLUMNNAME_QueuingTime = "QueuingTime";

public void setQueuingTime (int QueuingTime)
{
set_Value ("QueuingTime", new Integer(QueuingTime));
}
/** Get QueuingTime */
public int getQueuingTime() 
{
Integer ii = (Integer)get_Value("QueuingTime");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Setup Time CMPCS */
public static final String COLUMNNAME_SetupTime = "SetupTime";

public void setSetupTime (int SetupTime)
{
set_Value ("SetupTime", new Integer(SetupTime));
}
/** Get Setup Time CMPCS */
public int getSetupTime() 
{
Integer ii = (Integer)get_Value("SetupTime");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Setup Time Real */
public static final String COLUMNNAME_SetupTimeReal = "SetupTimeReal";

public void setSetupTimeReal (int SetupTimeReal)
{
set_Value ("SetupTimeReal", new Integer(SetupTimeReal));
}
/** Get Setup Time Real */
public int getSetupTimeReal() 
{
Integer ii = (Integer)get_Value("SetupTimeReal");
if (ii == null) return 0;
return ii.intValue();
}
/** Set setuptimerequired */
public static final String COLUMNNAME_setuptimerequired = "setuptimerequired";

public void setSetupTimeRequired (int setuptimerequired)
{
set_Value ("setuptimerequired", new Integer(setuptimerequired));
}
/** Get setuptimerequired */
public int getSetupTimeRequired() 
{
Integer ii = (Integer)get_Value("setuptimerequired");
if (ii == null) return 0;
return ii.intValue();
}
public static final int SPLITELEMENT_AD_Reference_ID = MReference.getReferenceID("WF_Join_Split");
/** AND = A */
public static final String SPLITELEMENT_AND = "A";
/** XOR = X */
public static final String SPLITELEMENT_XOR = "X";
/** Set Split Element.
Semantics for multiple outgoing Transitions */
public static final String COLUMNNAME_SplitElement = "SplitElement";

public void setSplitElement (String SplitElement)
{
if (SplitElement.equals("A") || SplitElement.equals("X"));
 else throw new IllegalArgumentException ("SplitElement Invalid value - Reference = SPLITELEMENT_AD_Reference_ID - A - X");
if (SplitElement == null) throw new IllegalArgumentException ("SplitElement is mandatory");
if (SplitElement.length() > 1)
{
log.warning("Length > 1 - truncated");
SplitElement = SplitElement.substring(0,1);
}
set_Value ("SplitElement", SplitElement);
}
/** Get Split Element.
Semantics for multiple outgoing Transitions */
public String getSplitElement() 
{
return (String)get_Value("SplitElement");
}
/** Set Resource.
Resource */
public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

public void setS_Resource_ID (int S_Resource_ID)
{
if (S_Resource_ID <= 0) set_Value ("S_Resource_ID", null);
 else 
set_Value ("S_Resource_ID", new Integer(S_Resource_ID));
}
/** Get Resource.
Resource */
public int getS_Resource_ID() 
{
Integer ii = (Integer)get_Value("S_Resource_ID");
if (ii == null) return 0;
return ii.intValue();
}
public static final int STARTMODE_AD_Reference_ID = MReference.getReferenceID("WF_Start-Finish Mode");
/** Automatic = A */
public static final String STARTMODE_Automatic = "A";
/** Manual = M */
public static final String STARTMODE_Manual = "M";
/** Set Start Mode.
Workflow Activity Start Mode  */
public static final String COLUMNNAME_StartMode = "StartMode";

public void setStartMode (String StartMode)
{
if (StartMode == null || StartMode.equals("A") || StartMode.equals("M"));
 else throw new IllegalArgumentException ("StartMode Invalid value - Reference = STARTMODE_AD_Reference_ID - A - M");
if (StartMode != null && StartMode.length() > 1)
{
log.warning("Length > 1 - truncated");
StartMode = StartMode.substring(0,1);
}
set_Value ("StartMode", StartMode);
}
/** Get Start Mode.
Workflow Activity Start Mode  */
public String getStartMode() 
{
return (String)get_Value("StartMode");
}
public static final int SUBFLOWEXECUTION_AD_Reference_ID = MReference.getReferenceID("WF_SubFlow Execution");
/** Asynchronously = A */
public static final String SUBFLOWEXECUTION_Asynchronously = "A";
/** Synchronously = S */
public static final String SUBFLOWEXECUTION_Synchronously = "S";
/** Set Subflow Execution.
Mode how the sub-workflow is executed */
public static final String COLUMNNAME_SubflowExecution = "SubflowExecution";

public void setSubflowExecution (String SubflowExecution)
{
if (SubflowExecution == null || SubflowExecution.equals("A") || SubflowExecution.equals("S"));
 else throw new IllegalArgumentException ("SubflowExecution Invalid value - Reference = SUBFLOWEXECUTION_AD_Reference_ID - A - S");
if (SubflowExecution != null && SubflowExecution.length() > 1)
{
log.warning("Length > 1 - truncated");
SubflowExecution = SubflowExecution.substring(0,1);
}
set_Value ("SubflowExecution", SubflowExecution);
}
/** Get Subflow Execution.
Mode how the sub-workflow is executed */
public String getSubflowExecution() 
{
return (String)get_Value("SubflowExecution");
}
/** Set Units Cycles */
public static final String COLUMNNAME_UnitsCycles = "UnitsCycles";

public void setUnitsCycles (int UnitsCycles)
{
set_Value ("UnitsCycles", new Integer(UnitsCycles));
}
/** Get Units Cycles */
public int getUnitsCycles() 
{
Integer ii = (Integer)get_Value("UnitsCycles");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Valid from.
Valid from including this date (first day) */
public static final String COLUMNNAME_ValidFrom = "ValidFrom";

public void setValidFrom (Timestamp ValidFrom)
{
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
/** Set Search Key.
Search key for the record in the format required - must be unique */
public static final String COLUMNNAME_Value = "Value";

public void setValue (String Value)
{
if (Value == null) throw new IllegalArgumentException ("Value is mandatory");
if (Value.length() > 40)
{
log.warning("Length > 40 - truncated");
Value = Value.substring(0,40);
}
set_Value ("Value", Value);
}
/** Get Search Key.
Search key for the record in the format required - must be unique */
public String getValue() 
{
return (String)get_Value("Value");
}
/** Set Waiting Time.
Workflow Simulation Waiting time */
public static final String COLUMNNAME_WaitingTime = "WaitingTime";

public void setWaitingTime (int WaitingTime)
{
set_Value ("WaitingTime", new Integer(WaitingTime));
}
/** Get Waiting Time.
Workflow Simulation Waiting time */
public int getWaitingTime() 
{
Integer ii = (Integer)get_Value("WaitingTime");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Workflow.
Workflow or tasks */
public static final String COLUMNNAME_Workflow_ID = "Workflow_ID";

public void setWorkflow_ID (int Workflow_ID)
{
if (Workflow_ID <= 0) set_Value ("Workflow_ID", null);
 else 
set_Value ("Workflow_ID", new Integer(Workflow_ID));
}
/** Get Workflow.
Workflow or tasks */
public int getWorkflow_ID() 
{
Integer ii = (Integer)get_Value("Workflow_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Working Time.
Workflow Simulation Execution Time */
public static final String COLUMNNAME_WorkingTime = "WorkingTime";

public void setWorkingTime (int WorkingTime)
{
set_Value ("WorkingTime", new Integer(WorkingTime));
}
/** Get Working Time.
Workflow Simulation Execution Time */
public int getWorkingTime() 
{
Integer ii = (Integer)get_Value("WorkingTime");
if (ii == null) return 0;
return ii.intValue();
}
/** Set X Position.
Absolute X (horizontal) position in 1/72 of an inch */
public static final String COLUMNNAME_XPosition = "XPosition";

public void setXPosition (int XPosition)
{
set_Value ("XPosition", new Integer(XPosition));
}
/** Get X Position.
Absolute X (horizontal) position in 1/72 of an inch */
public int getXPosition() 
{
Integer ii = (Integer)get_Value("XPosition");
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
/** Set Y Position.
Absolute Y (vertical) position in 1/72 of an inch */
public static final String COLUMNNAME_YPosition = "YPosition";

public void setYPosition (int YPosition)
{
set_Value ("YPosition", new Integer(YPosition));
}
/** Get Y Position.
Absolute Y (vertical) position in 1/72 of an inch */
public int getYPosition() 
{
Integer ii = (Integer)get_Value("YPosition");
if (ii == null) return 0;
return ii.intValue();
}
}
