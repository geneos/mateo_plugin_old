package ar.com.geneos.mrp.plugin.model;

/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import org.openXpertya.model.*;

import java.util.logging.Level;
import java.util.*;
import java.sql.*;
import java.math.*;

import org.openXpertya.util.*;

/**
 * Modelo Generado por PP_Order_NodeNext
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-04-29 14:39:08.581
 */
public class LP_PP_Order_NodeNext extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_PP_Order_NodeNext(Properties ctx, int PP_Order_NodeNext_ID, String trxName) {
		super(ctx, PP_Order_NodeNext_ID, trxName);
		/**
		 * if (PP_Order_NodeNext_ID == 0) { setAD_WF_Node_ID (0); setEntityType
		 * (0); // U setPP_Order_ID (0); setPP_Order_Node_ID (0);
		 * setPP_Order_NodeNext_ID (0); setSeqNo (0); }
		 */
	}

	/** Load Constructor */
	public LP_PP_Order_NodeNext(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("PP_Order_NodeNext");

	/** TableName=PP_Order_NodeNext */
	public static final String Table_Name = "PP_Order_NodeNext";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "PP_Order_NodeNext");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_PP_Order_NodeNext[").append(getID()).append("]");
		return sb.toString();
	}

	public static final int AD_WF_NEXT_ID_AD_Reference_ID = MReference.getReferenceID("AD_WF_Next Nodes");
	/**
	 * Set Next Node. Next Node in workflow
	 */
	public static final String COLUMNNAME_AD_WF_Next_ID = "AD_WF_Next_ID";

	public void setAD_WF_Next_ID(int AD_WF_Next_ID) {
		if (AD_WF_Next_ID <= 0)
			set_Value("AD_WF_Next_ID", null);
		else
			set_Value("AD_WF_Next_ID", new Integer(AD_WF_Next_ID));
	}

	/**
	 * Get Next Node. Next Node in workflow
	 */
	public int getAD_WF_Next_ID() {
		Integer ii = (Integer) get_Value("AD_WF_Next_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Node. Workflow Node (activity), step or process
	 */
	public static final String COLUMNNAME_AD_WF_Node_ID = "AD_WF_Node_ID";

	public void setAD_WF_Node_ID(int AD_WF_Node_ID) {
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

	public static final int ENTITYTYPE_AD_Reference_ID = MReference.getReferenceID("_EntityType_New");
	/**
	 * Set Entity Type. Dictionary Entity Type; Determines ownership and
	 * synchronization
	 */
	public static final String COLUMNNAME_EntityType = "EntityType";

	public void setEntityType(String entitytypeUsermaintained) {
		set_Value("EntityType", new String(entitytypeUsermaintained));
	}

	/**
	 * Get Entity Type. Dictionary Entity Type; Determines ownership and
	 * synchronization
	 */
	public int getEntityType() {
		Integer ii = (Integer) get_Value("EntityType");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Std User Workflow. Standard Manual User Approval Workflow
	 */
	public static final String COLUMNNAME_IsStdUserWorkflow = "IsStdUserWorkflow";

	public void setIsStdUserWorkflow(boolean IsStdUserWorkflow) {
		set_Value("IsStdUserWorkflow", new Boolean(IsStdUserWorkflow));
	}

	/**
	 * Get Std User Workflow. Standard Manual User Approval Workflow
	 */
	public boolean isStdUserWorkflow() {
		Object oo = get_Value("IsStdUserWorkflow");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
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

	public static final int PP_ORDER_NEXT_ID_AD_Reference_ID = MReference.getReferenceID("PP_Order_Node");
	/** Set PP_Order_Next_ID */
	public static final String COLUMNNAME_PP_Order_Next_ID = "PP_Order_Next_ID";

	public void setPP_Order_Next_ID(int PP_Order_Next_ID) {
		if (PP_Order_Next_ID <= 0)
			set_Value("PP_Order_Next_ID", null);
		else
			set_Value("PP_Order_Next_ID", new Integer(PP_Order_Next_ID));
	}

	/** Get PP_Order_Next_ID */
	public int getPP_Order_Next_ID() {
		Integer ii = (Integer) get_Value("PP_Order_Next_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_Node_ID */
	public static final String COLUMNNAME_PP_Order_Node_ID = "PP_Order_Node_ID";

	public void setPP_Order_Node_ID(int PP_Order_Node_ID) {
		set_Value("PP_Order_Node_ID", new Integer(PP_Order_Node_ID));
	}

	/** Get PP_Order_Node_ID */
	public int getPP_Order_Node_ID() {
		Integer ii = (Integer) get_Value("PP_Order_Node_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/** Set PP_Order_NodeNext_ID */
	public static final String COLUMNNAME_PP_Order_NodeNext_ID = "PP_Order_NodeNext_ID";

	public void setPP_Order_NodeNext_ID(int PP_Order_NodeNext_ID) {
		set_ValueNoCheck("PP_Order_NodeNext_ID", new Integer(PP_Order_NodeNext_ID));
	}

	/** Get PP_Order_NodeNext_ID */
	public int getPP_Order_NodeNext_ID() {
		Integer ii = (Integer) get_Value("PP_Order_NodeNext_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Sequence. Method of ordering records; lowest number comes first
	 */
	public static final String COLUMNNAME_SeqNo = "SeqNo";

	public void setSeqNo(int SeqNo) {
		set_Value("SeqNo", new Integer(SeqNo));
	}

	/**
	 * Get Sequence. Method of ordering records; lowest number comes first
	 */
	public int getSeqNo() {
		Integer ii = (Integer) get_Value("SeqNo");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Transition Code. Code resulting in TRUE of FALSE
	 */
	public static final String COLUMNNAME_TransitionCode = "TransitionCode";

	public void setTransitionCode(String TransitionCode) {
		if (TransitionCode != null && TransitionCode.length() > 2000) {
			log.warning("Length > 2000 - truncated");
			TransitionCode = TransitionCode.substring(0, 2000);
		}
		set_Value("TransitionCode", TransitionCode);
	}

	/**
	 * Get Transition Code. Code resulting in TRUE of FALSE
	 */
	public String getTransitionCode() {
		return (String) get_Value("TransitionCode");
	}
}