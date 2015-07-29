package ar.com.geneos.mrp.plugin.model;

/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import org.openXpertya.model.*;
import java.util.logging.Level;
import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;

/**
 * Modelo Generado por PP_Order_Workflow
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-04-30 08:09:44.6
 */
public class LP_PP_Order_Workflow extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_PP_Order_Workflow(Properties ctx, int PP_Order_Workflow_ID, String trxName) {
		super(ctx, PP_Order_Workflow_ID, trxName);
		/**
		 * if (PP_Order_Workflow_ID == 0) { setAccessLevel (null);
		 * setAD_Workflow_ID (0); setAuthor (null); setDuration (0);
		 * setDurationUnit (null); setEntityType (null); setlimit_time (0);
		 * setName (null); setPP_Order_ID (0); setPP_Order_Workflow_ID (0);
		 * setPriority (0); setPublishStatus (null); setVersion (0);
		 * setWaitingTime (0); }
		 */
	}

	/** Load Constructor */
	public LP_PP_Order_Workflow(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("PP_Order_Workflow");

	/** TableName=PP_Order_Workflow */
	public static final String Table_Name = "PP_Order_Workflow";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "PP_Order_Workflow");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_PP_Order_Workflow[").append(getID()).append("]");
		return sb.toString();
	}

	public static final int ACCESSLEVEL_AD_Reference_ID = MReference.getReferenceID("AD_Table Access Levels");
	/** Organization = 1 */
	public static final String ACCESSLEVEL_Organization = "1";
	/** Client+Organization = 3 */
	public static final String ACCESSLEVEL_ClientPlusOrganization = "3";
	/** System only = 4 */
	public static final String ACCESSLEVEL_SystemOnly = "4";
	/** All = 7 */
	public static final String ACCESSLEVEL_All = "7";
	/** Client only = 2 */
	public static final String ACCESSLEVEL_ClientOnly = "2";
	/** System+Client = 6 */
	public static final String ACCESSLEVEL_SystemPlusClient = "6";
	/**
	 * Set Data Access Level. Access Level required
	 */
	public static final String COLUMNNAME_AccessLevel = "AccessLevel";

	public void setAccessLevel(String AccessLevel) {
		if (AccessLevel.equals("1") || AccessLevel.equals("3") || AccessLevel.equals("4") || AccessLevel.equals("7") || AccessLevel.equals("2")
				|| AccessLevel.equals("6"))
			;
		else
			throw new IllegalArgumentException("AccessLevel Invalid value - Reference = ACCESSLEVEL_AD_Reference_ID - 1 - 3 - 4 - 7 - 2 - 6");
		if (AccessLevel == null)
			throw new IllegalArgumentException("AccessLevel is mandatory");
		if (AccessLevel.length() > 1) {
			log.warning("Length > 1 - truncated");
			AccessLevel = AccessLevel.substring(0, 1);
		}
		set_Value("AccessLevel", AccessLevel);
	}

	/**
	 * Get Data Access Level. Access Level required
	 */
	public String getAccessLevel() {
		return (String) get_Value("AccessLevel");
	}

	/**
	 * Set Table. Table for the Fields
	 */
	public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	public void setAD_Table_ID(int AD_Table_ID) {
		if (AD_Table_ID <= 0)
			set_Value("AD_Table_ID", null);
		else
			set_Value("AD_Table_ID", new Integer(AD_Table_ID));
	}

	/**
	 * Get Table. Table for the Fields
	 */
	public int getAD_Table_ID() {
		Integer ii = (Integer) get_Value("AD_Table_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Node. Workflow Node (activity), step or process
	 */
	public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	public void setAD_WF_Node_ID(int AD_WF_Node_ID) {
		if (AD_WF_Node_ID <= 0)
			set_Value("AD_WF_Node_ID", null);
		else
			set_Value("AD_WF_Node_ID", new Integer(AD_WF_Node_ID));
	}

	/**
	 * Get Node. Workflow Node (activity), step or process
	 */
	public int getAD_WF_Node_ID() {
		Integer ii = (Integer) get_Value("AD_WF_Node_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Workflow Responsible. Responsible for Workflow Execution
	 */
	public static final String COLUMNNAME_AD_WF_Responsible_ID = "AD_WF_Responsible_ID";

	public void setAD_WF_Responsible_ID(int AD_WF_Responsible_ID) {
		if (AD_WF_Responsible_ID <= 0)
			set_Value("AD_WF_Responsible_ID", null);
		else
			set_Value("AD_WF_Responsible_ID", new Integer(AD_WF_Responsible_ID));
	}

	/**
	 * Get Workflow Responsible. Responsible for Workflow Execution
	 */
	public int getAD_WF_Responsible_ID() {
		Integer ii = (Integer) get_Value("AD_WF_Responsible_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public static final int AD_WORKFLOW_ID_AD_Reference_ID = MReference.getReferenceID("AD_Workflow_Manufacturing");
	/**
	 * Set Workflow. Workflow or combination of tasks
	 */
	public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

	public void setAD_Workflow_ID(int AD_Workflow_ID) {
		set_Value("AD_Workflow_ID", new Integer(AD_Workflow_ID));
	}

	/**
	 * Get Workflow. Workflow or combination of tasks
	 */
	public int getAD_Workflow_ID() {
		Integer ii = (Integer) get_Value("AD_Workflow_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Workflow Processor. Workflow Processor Server
	 */
	public static final String COLUMNNAME_AD_WorkflowProcessor_ID = "AD_WorkflowProcessor_ID";

	public void setAD_WorkflowProcessor_ID(int AD_WorkflowProcessor_ID) {
		if (AD_WorkflowProcessor_ID <= 0)
			set_Value("AD_WorkflowProcessor_ID", null);
		else
			set_Value("AD_WorkflowProcessor_ID", new Integer(AD_WorkflowProcessor_ID));
	}

	/**
	 * Get Workflow Processor. Workflow Processor Server
	 */
	public int getAD_WorkflowProcessor_ID() {
		Integer ii = (Integer) get_Value("AD_WorkflowProcessor_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Author. Author/Creator of the Entity
	 */
	public static final String COLUMNNAME_Author = "Author";

	public void setAuthor(String Author) {
		if (Author == null)
			throw new IllegalArgumentException("Author is mandatory");
		if (Author.length() > 20) {
			log.warning("Length > 20 - truncated");
			Author = Author.substring(0, 20);
		}
		set_Value("Author", Author);
	}

	/**
	 * Get Author. Author/Creator of the Entity
	 */
	public String getAuthor() {
		return (String) get_Value("Author");
	}

	/**
	 * Set Cost. Cost information
	 */
	public static final String COLUMNNAME_Cost = "Cost";

	public void setCost(BigDecimal Cost) {
		set_Value("Cost", Cost);
	}

	/**
	 * Get Cost. Cost information
	 */
	public BigDecimal getCost() {
		BigDecimal bd = (BigDecimal) get_Value("Cost");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Description. Optional short description of the record
	 */
	public static final String COLUMNNAME_Description = "Description";

	public void setDescription(String Description) {
		if (Description != null && Description.length() > 255) {
			log.warning("Length > 255 - truncated");
			Description = Description.substring(0, 255);
		}
		set_Value("Description", Description);
	}

	/**
	 * Get Description. Optional short description of the record
	 */
	public String getDescription() {
		return (String) get_Value("Description");
	}

	/**
	 * Set Document No. Document sequence NUMERIC of the document
	 */
	public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	public void setDocumentNo(String DocumentNo) {
		if (DocumentNo != null && DocumentNo.length() > 32) {
			log.warning("Length > 32 - truncated");
			DocumentNo = DocumentNo.substring(0, 32);
		}
		set_Value("DocumentNo", DocumentNo);
	}

	/**
	 * Get Document No. Document sequence NUMERIC of the document
	 */
	public String getDocumentNo() {
		return (String) get_Value("DocumentNo");
	}

	/**
	 * Set Duration. Normal Duration in Duration Unit
	 */
	public static final String COLUMNNAME_Duration = "Duration";

	public void setDuration(int Duration) {
		set_Value("Duration", new Integer(Duration));
	}

	/**
	 * Get Duration. Normal Duration in Duration Unit
	 */
	public int getDuration() {
		Integer ii = (Integer) get_Value("Duration");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public static final int DURATIONUNIT_AD_Reference_ID = MReference.getReferenceID("WF_DurationUnit");
	/** Year = Y */
	public static final String DURATIONUNIT_Year = "Y";
	/** Month = M */
	public static final String DURATIONUNIT_Month = "M";
	/** Day = D */
	public static final String DURATIONUNIT_Day = "D";
	/** hour = h */
	public static final String DURATIONUNIT_Hour = "h";
	/** minute = m */
	public static final String DURATIONUNIT_Minute = "m";
	/** second = s */
	public static final String DURATIONUNIT_Second = "s";
	/**
	 * Set Duration Unit. Unit of Duration
	 */
	public static final String COLUMNNAME_DurationUnit = "DurationUnit";

	public void setDurationUnit(String DurationUnit) {
		if (DurationUnit.equals("Y") || DurationUnit.equals("M") || DurationUnit.equals("D") || DurationUnit.equals("h") || DurationUnit.equals("m")
				|| DurationUnit.equals("s"))
			;
		else
			throw new IllegalArgumentException("DurationUnit Invalid value - Reference = DURATIONUNIT_AD_Reference_ID - Y - M - D - h - m - s");
		if (DurationUnit == null)
			throw new IllegalArgumentException("DurationUnit is mandatory");
		if (DurationUnit.length() > 1) {
			log.warning("Length > 1 - truncated");
			DurationUnit = DurationUnit.substring(0, 1);
		}
		set_Value("DurationUnit", DurationUnit);
	}

	/**
	 * Get Duration Unit. Unit of Duration
	 */
	public String getDurationUnit() {
		return (String) get_Value("DurationUnit");
	}

	public static final int ENTITYTYPE_AD_Reference_ID = MReference.getReferenceID("_Entity Type");
	/** Applications Integrated with openXpertya = A */
	public static final String ENTITYTYPE_ApplicationsIntegratedWithOpenXpertya = "A";
	/** Country Version = C */
	public static final String ENTITYTYPE_CountryVersion = "C";
	/** Dictionary = D */
	public static final String ENTITYTYPE_Dictionary = "D";
	/** User maintained = U */
	public static final String ENTITYTYPE_UserMaintained = "U";
	/** Customization = CUST */
	public static final String ENTITYTYPE_Customization = "CUST";
	/**
	 * Set Entity Type. Dictionary Entity Type; Determines ownership and
	 * synchronization
	 */
	public static final String COLUMNNAME_EntityType = "EntityType";

	public void setEntityType(String EntityType) {
		if (EntityType == null)
			throw new IllegalArgumentException("EntityType is mandatory");
		if (EntityType.equals("A") || EntityType.equals("C") || EntityType.equals("D") || EntityType.equals("U") || EntityType.equals("CUST"))
			;
		else
			throw new IllegalArgumentException("EntityType Invalid value - Reference = ENTITYTYPE_AD_Reference_ID - A - C - D - U - CUST");
		if (EntityType == null)
			throw new IllegalArgumentException("EntityType is mandatory");
		if (EntityType.length() > 40) {
			log.warning("Length > 40 - truncated");
			EntityType = EntityType.substring(0, 40);
		}
		set_Value("EntityType", EntityType);
	}

	/**
	 * Get Entity Type. Dictionary Entity Type; Determines ownership and
	 * synchronization
	 */
	public String getEntityType() {
		return (String) get_Value("EntityType");
	}

	/**
	 * Set Comment/Help. Comment or Hint
	 */
	public static final String COLUMNNAME_Help = "Help";

	public void setHelp(String Help) {
		if (Help != null && Help.length() > 2000) {
			log.warning("Length > 2000 - truncated");
			Help = Help.substring(0, 2000);
		}
		set_Value("Help", Help);
	}

	/**
	 * Get Comment/Help. Comment or Hint
	 */
	public String getHelp() {
		return (String) get_Value("Help");
	}

	/**
	 * Set Default. Default value
	 */
	public static final String COLUMNNAME_IsDefault = "IsDefault";

	public void setIsDefault(boolean IsDefault) {
		set_Value("IsDefault", new Boolean(IsDefault));
	}

	/**
	 * Get Default. Default value
	 */
	public boolean isDefault() {
		Object oo = get_Value("IsDefault");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set limit_time */
	public static final String COLUMNNAME_limit_time = "limit_time";

	public void setlimit_time(int limit_time) {
		set_Value("limit_time", new Integer(limit_time));
	}

	/** Get limit_time */
	public int getlimit_time() {
		Integer ii = (Integer) get_Value("limit_time");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Move Time. Time to move material form one operation to another
	 */
	public static final String COLUMNNAME_MovingTime = "MovingTime";

	public void setMovingTime(int MovingTime) {
		set_Value("MovingTime", new Integer(MovingTime));
	}

	/**
	 * Get Move Time. Time to move material form one operation to another
	 */
	public int getMovingTime() {
		Integer ii = (Integer) get_Value("MovingTime");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Name. Alphanumeric identifier of the entity
	 */
	public static final String COLUMNNAME_Name = "Name";

	public void setName(String Name) {
		if (Name == null)
			throw new IllegalArgumentException("Name is mandatory");
		if (Name.length() > 60) {
			log.warning("Length > 60 - truncated");
			Name = Name.substring(0, 60);
		}
		set_Value("Name", Name);
	}

	/**
	 * Get Name. Alphanumeric identifier of the entity
	 */
	public String getName() {
		return (String) get_Value("Name");
	}

	public KeyNamePair getKeyNamePair() {
		return new KeyNamePair(getID(), getName());
	}

	/**
	 * Set Overlap Units. No. of prodcts you need to finish in a operation
	 * before you start the next one.
	 */
	public static final String COLUMNNAME_OverlapUnits = "OverlapUnits";

	public void setOverlapUnits(BigDecimal OverlapUnits) {
		set_Value("OverlapUnits", OverlapUnits);
	}

	/**
	 * Get Overlap Units. No. of prodcts you need to finish in a operation
	 * before you start the next one.
	 */
	public BigDecimal getOverlapUnits() {
		BigDecimal bd = (BigDecimal) get_Value("OverlapUnits");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set PP_Order_ID */
	public static final String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	public void setPP_Order_ID(int PP_Order_ID) {
		set_Value("PP_Order_ID", new Integer(PP_Order_ID));
	}

	/** Get PP_Order_ID */
	public int getPP_Order_ID() {
		Integer ii = (Integer) get_Value("PP_Order_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_Node_ID */
	public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	public void setPP_Order_Node_ID(int PP_Order_Node_ID) {
		if (PP_Order_Node_ID <= 0)
			set_Value("PP_Order_Node_ID", null);
		else
			set_Value("PP_Order_Node_ID", new Integer(PP_Order_Node_ID));
	}

	/** Get PP_Order_Node_ID */
	public int getPP_Order_Node_ID() {
		Integer ii = (Integer) get_Value("PP_Order_Node_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_Workflow_ID */
	public static final String COLUMNNAME_PP_Order_Workflow_ID = "PP_Order_Workflow_ID";

	public void setPP_Order_Workflow_ID(int PP_Order_Workflow_ID) {
		set_ValueNoCheck("PP_Order_Workflow_ID", new Integer(PP_Order_Workflow_ID));
	}

	/** Get PP_Order_Workflow_ID */
	public int getPP_Order_Workflow_ID() {
		Integer ii = (Integer) get_Value("PP_Order_Workflow_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Priority. Indicates if this request is of a high, medium or low
	 * priority.
	 */
	public static final String COLUMNNAME_Priority = "Priority";

	public void setPriority(int Priority) {
		set_Value("Priority", new Integer(Priority));
	}

	/**
	 * Get Priority. Indicates if this request is of a high, medium or low
	 * priority.
	 */
	public int getPriority() {
		Integer ii = (Integer) get_Value("Priority");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public static final int PROCESSTYPE_AD_Reference_ID = MReference.getReferenceID("PP_Process Type");
	/** Flujo Lote = BF */
	public static final String PROCESSTYPE_FlujoLote = "BF";
	/** Flujo Continuo = CF */
	public static final String PROCESSTYPE_FlujoContinuo = "CF";
	/** Flujo Repetitivo Dedicado = DR */
	public static final String PROCESSTYPE_FlujoRepetitivoDedicado = "DR";
	/** Taller de Trabajo = JS */
	public static final String PROCESSTYPE_TallerDeTrabajo = "JS";
	/** Flujo Repetitivo Mezclado = MR */
	public static final String PROCESSTYPE_FlujoRepetitivoMezclado = "MR";
	/** Planta = PL */
	public static final String PROCESSTYPE_Planta = "PL";
	/** Set Process Type */
	public static final String COLUMNNAME_ProcessType = "ProcessType";

	public void setProcessType(String ProcessType) {
		if (ProcessType == null || ProcessType.equals("BF") || ProcessType.equals("CF") || ProcessType.equals("DR") || ProcessType.equals("JS")
				|| ProcessType.equals("MR") || ProcessType.equals("PL"))
			;
		else
			throw new IllegalArgumentException("ProcessType Invalid value - Reference = PROCESSTYPE_AD_Reference_ID - BF - CF - DR - JS - MR - PL");
		if (ProcessType != null && ProcessType.length() > 2) {
			log.warning("Length > 2 - truncated");
			ProcessType = ProcessType.substring(0, 2);
		}
		set_Value("ProcessType", ProcessType);
	}

	/** Get Process Type */
	public String getProcessType() {
		return (String) get_Value("ProcessType");
	}

	public static final int PUBLISHSTATUS_AD_Reference_ID = MReference.getReferenceID("_PublishStatus");
	/** Released = R */
	public static final String PUBLISHSTATUS_Released = "R";
	/** Test = T */
	public static final String PUBLISHSTATUS_Test = "T";
	/** Under Revision = U */
	public static final String PUBLISHSTATUS_UnderRevision = "U";
	/** Void = V */
	public static final String PUBLISHSTATUS_Void = "V";
	/**
	 * Set Publication Status. Status of Publication
	 */
	public static final String COLUMNNAME_PublishStatus = "PublishStatus";

	public void setPublishStatus(String PublishStatus) {
		if (PublishStatus.equals("R") || PublishStatus.equals("T") || PublishStatus.equals("U") || PublishStatus.equals("V"))
			;
		else
			throw new IllegalArgumentException("PublishStatus Invalid value - Reference = PUBLISHSTATUS_AD_Reference_ID - R - T - U - V");
		if (PublishStatus == null)
			throw new IllegalArgumentException("PublishStatus is mandatory");
		if (PublishStatus.length() > 1) {
			log.warning("Length > 1 - truncated");
			PublishStatus = PublishStatus.substring(0, 1);
		}
		set_Value("PublishStatus", PublishStatus);
	}

	/**
	 * Get Publication Status. Status of Publication
	 */
	public String getPublishStatus() {
		return (String) get_Value("PublishStatus");
	}

	/** Set Qty Batch Size */
	public static final String COLUMNNAME_QtyBatchSize = "QtyBatchSize";

	public void setQtyBatchSize(BigDecimal QtyBatchSize) {
		set_Value("QtyBatchSize", QtyBatchSize);
	}

	/** Get Qty Batch Size */
	public BigDecimal getQtyBatchSize() {
		BigDecimal bd = (BigDecimal) get_Value("QtyBatchSize");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set QueuingTime */
	public static final String COLUMNNAME_QueuingTime = "QueuingTime";

	public void setQueuingTime(int QueuingTime) {
		set_Value("QueuingTime", new Integer(QueuingTime));
	}

	/** Get QueuingTime */
	public int getQueuingTime() {
		Integer ii = (Integer) get_Value("QueuingTime");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Setup Time CMPCS */
	public static final String COLUMNNAME_SetupTime = "SetupTime";

	public void setSetupTime(int SetupTime) {
		set_Value("SetupTime", new Integer(SetupTime));
	}

	/** Get Setup Time CMPCS */
	public int getSetupTime() {
		Integer ii = (Integer) get_Value("SetupTime");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public static final int S_RESOURCE_ID_AD_Reference_ID = MReference.getReferenceID("S_Resource_Manufacturing");
	/**
	 * Set Resource. Resource
	 */
	public static final String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	public void setS_Resource_ID(int S_Resource_ID) {
		if (S_Resource_ID <= 0)
			set_Value("S_Resource_ID", null);
		else
			set_Value("S_Resource_ID", new Integer(S_Resource_ID));
	}

	/**
	 * Get Resource. Resource
	 */
	public int getS_Resource_ID() {
		Integer ii = (Integer) get_Value("S_Resource_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Units Cycles */
	public static final String COLUMNNAME_UnitsCycles = "UnitsCycles";

	public void setUnitsCycles(BigDecimal UnitsCycles) {
		set_Value("UnitsCycles", UnitsCycles);
	}

	/** Get Units Cycles */
	public BigDecimal getUnitsCycles() {
		BigDecimal bd = (BigDecimal) get_Value("UnitsCycles");
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/** Set Validate Workflow */
	public static final String COLUMNNAME_ValidateWorkflow = "ValidateWorkflow";

	public void setValidateWorkflow(boolean ValidateWorkflow) {
		set_Value("ValidateWorkflow", new Boolean(ValidateWorkflow));
	}

	/** Get Validate Workflow */
	public boolean isValidateWorkflow() {
		Object oo = get_Value("ValidateWorkflow");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set Valid from. Valid from including this date (first day)
	 */
	public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	public void setValidFrom(Timestamp ValidFrom) {
		set_Value("ValidFrom", ValidFrom);
	}

	/**
	 * Get Valid from. Valid from including this date (first day)
	 */
	public Timestamp getValidFrom() {
		return (Timestamp) get_Value("ValidFrom");
	}

	/**
	 * Set Valid to. Valid to including this date (last day)
	 */
	public static final String COLUMNNAME_ValidTo = "ValidTo";

	public void setValidTo(Timestamp ValidTo) {
		set_Value("ValidTo", ValidTo);
	}

	/**
	 * Get Valid to. Valid to including this date (last day)
	 */
	public Timestamp getValidTo() {
		return (Timestamp) get_Value("ValidTo");
	}

	/**
	 * Set Search Key. Search key for the record in the format required - must
	 * be unique
	 */
	public static final String COLUMNNAME_Value = "Value";

	public void setValue(String Value) {
		if (Value != null && Value.length() > 240) {
			log.warning("Length > 240 - truncated");
			Value = Value.substring(0, 240);
		}
		set_Value("Value", Value);
	}

	/**
	 * Get Search Key. Search key for the record in the format required - must
	 * be unique
	 */
	public String getValue() {
		return (String) get_Value("Value");
	}

	/**
	 * Set Version. Version of the table definition
	 */
	public static final String COLUMNNAME_Version = "Version";

	public void setVersion(int Version) {
		set_Value("Version", new Integer(Version));
	}

	/**
	 * Get Version. Version of the table definition
	 */
	public int getVersion() {
		Integer ii = (Integer) get_Value("Version");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Waiting Time. Workflow Simulation Waiting time
	 */
	public static final String COLUMNNAME_WaitingTime = "WaitingTime";

	public void setWaitingTime(int WaitingTime) {
		set_Value("WaitingTime", new Integer(WaitingTime));
	}

	/**
	 * Get Waiting Time. Workflow Simulation Waiting time
	 */
	public int getWaitingTime() {
		Integer ii = (Integer) get_Value("WaitingTime");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public static final int WORKFLOWTYPE_AD_Reference_ID = MReference.getReferenceID("AD_Workflow Type");
	/** General = G */
	public static final String WORKFLOWTYPE_General = "G";
	/** Document Process = P */
	public static final String WORKFLOWTYPE_DocumentProcess = "P";
	/** Document Value = V */
	public static final String WORKFLOWTYPE_DocumentValue = "V";
	/** Manufacturing = M */
	public static final String WORKFLOWTYPE_Manufacturing = "M";
	/** Quality = Q */
	public static final String WORKFLOWTYPE_Quality = "Q";
	/**
	 * Set Workflow Type. Type of Worflow
	 */
	public static final String COLUMNNAME_WorkflowType = "WorkflowType";

	public void setWorkflowType(String WorkflowType) {
		if (WorkflowType == null || WorkflowType.equals("G") || WorkflowType.equals("P") || WorkflowType.equals("V") || WorkflowType.equals("M")
				|| WorkflowType.equals("Q"))
			;
		else
			throw new IllegalArgumentException("WorkflowType Invalid value - Reference = WORKFLOWTYPE_AD_Reference_ID - G - P - V - M - Q");
		if (WorkflowType != null && WorkflowType.length() > 1) {
			log.warning("Length > 1 - truncated");
			WorkflowType = WorkflowType.substring(0, 1);
		}
		set_Value("WorkflowType", WorkflowType);
	}

	/**
	 * Get Workflow Type. Type of Worflow
	 */
	public String getWorkflowType() {
		return (String) get_Value("WorkflowType");
	}

	/**
	 * Set Working Time. Workflow Simulation Execution Time
	 */
	public static final String COLUMNNAME_WorkingTime = "WorkingTime";

	public void setWorkingTime(int WorkingTime) {
		set_Value("WorkingTime", new Integer(WorkingTime));
	}

	/**
	 * Get Working Time. Workflow Simulation Execution Time
	 */
	public int getWorkingTime() {
		Integer ii = (Integer) get_Value("WorkingTime");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set Yield */
	public static final String COLUMNNAME_Yield = "Yield";

	public void setYield(int Yield) {
		set_Value("Yield", new Integer(Yield));
	}

	/** Get Yield */
	public int getYield() {
		Integer ii = (Integer) get_Value("Yield");
		if (ii == null)
			return 0;
		return ii.intValue();
	}
}
