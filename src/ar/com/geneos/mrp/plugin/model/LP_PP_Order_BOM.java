package ar.com.geneos.mrp.plugin.model;

/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MReference;
import org.openXpertya.model.M_Table;
import org.openXpertya.model.POInfo;
import org.openXpertya.util.KeyNamePair;

/**
 * Modelo Generado por PP_Order_BOM
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-05-04 07:43:33.934
 */
public class LP_PP_Order_BOM extends org.openXpertya.model.PO {
	/** Constructor estándar */
	public LP_PP_Order_BOM(Properties ctx, int PP_Order_BOM_ID, String trxName) {
		super(ctx, PP_Order_BOM_ID, trxName);
		/**
		 * if (PP_Order_BOM_ID == 0) { setC_UOM_ID (0); setM_Product_ID (0);
		 * setName (null); setPP_Order_BOM_ID (0); setPP_Order_ID (0);
		 * setValidFrom (new Timestamp(System.currentTimeMillis())); setValue
		 * (null); }
		 */
	}

	/** Load Constructor */
	public LP_PP_Order_BOM(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/** AD_Table_ID */
	public static final int Table_ID = M_Table.getTableID("PP_Order_BOM");

	/** TableName=PP_Order_BOM */
	public static final String Table_Name = "PP_Order_BOM";

	protected static KeyNamePair Model = new KeyNamePair(Table_ID, "PP_Order_BOM");
	protected static BigDecimal AccessLevel = new BigDecimal(3);

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx) {
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID);
		return poi;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_PP_Order_BOM[").append(getID()).append("]");
		return sb.toString();
	}

	public static final int BOMTYPE_AD_Reference_ID = MReference.getReferenceID("M_BOM Type");
	/** Maintenance = M */
	public static final String BOMTYPE_Maintenance = "M";
	/** Repair = R */
	public static final String BOMTYPE_Repair = "R";
	/** Current Active = A */
	public static final String BOMTYPE_CurrentActive = "A";
	/** Make-To-Order = O */
	public static final String BOMTYPE_Make_To_Order = "O";
	/** Previous = P */
	public static final String BOMTYPE_Previous = "P";
	/** Previous, Spare = S */
	public static final String BOMTYPE_PreviousSpare = "S";
	/** Future = F */
	public static final String BOMTYPE_Future = "F";
	/** Product Configure = C */
	public static final String BOMTYPE_ProductConfigure = "C";
	/** Make-To-Kit = K */
	public static final String BOMTYPE_Make_To_Kit = "K";
	/**
	 * Set BOM Type. Type of BOM
	 */
	public static final String COLUMNNAME_BOMType = "BOMType";

	public void setBOMType(String BOMType) {
		if (BOMType == null || BOMType.equals("M") || BOMType.equals("R") || BOMType.equals("A") || BOMType.equals("O") || BOMType.equals("P")
				|| BOMType.equals("S") || BOMType.equals("F") || BOMType.equals("C") || BOMType.equals("K"))
			;
		else
			throw new IllegalArgumentException("BOMType Invalid value - Reference = BOMTYPE_AD_Reference_ID - M - R - A - O - P - S - F - C - K");
		if (BOMType != null && BOMType.length() > 1) {
			log.warning("Length > 1 - truncated");
			BOMType = BOMType.substring(0, 1);
		}
		set_Value("BOMType", BOMType);
	}

	/**
	 * Get BOM Type. Type of BOM
	 */
	public String getBOMType() {
		return (String) get_Value("BOMType");
	}

	public static final int BOMUSE_AD_Reference_ID = MReference.getReferenceID("M_BOM Use");
	/** Master = A */
	public static final String BOMUSE_Master = "A";
	/** Engineering = E */
	public static final String BOMUSE_Engineering = "E";
	/** Manufacturing = M */
	public static final String BOMUSE_Manufacturing = "M";
	/** Planning = P */
	public static final String BOMUSE_Planning = "P";
	/** Quality = Q */
	public static final String BOMUSE_Quality = "Q";
	/**
	 * Set BOM Use. The use of the Bill of Material
	 */
	public static final String COLUMNNAME_BOMUse = "BOMUse";

	public void setBOMUse(String BOMUse) {
		if (BOMUse == null || BOMUse.equals("A") || BOMUse.equals("E") || BOMUse.equals("M") || BOMUse.equals("P") || BOMUse.equals("Q"))
			;
		else
			throw new IllegalArgumentException("BOMUse Invalid value - Reference = BOMUSE_AD_Reference_ID - A - E - M - P - Q");
		if (BOMUse != null && BOMUse.length() > 1) {
			log.warning("Length > 1 - truncated");
			BOMUse = BOMUse.substring(0, 1);
		}
		set_Value("BOMUse", BOMUse);
	}

	/**
	 * Get BOM Use. The use of the Bill of Material
	 */
	public String getBOMUse() {
		return (String) get_Value("BOMUse");
	}

	/**
	 * Set Copy From. Copy From Record
	 */
	public static final String COLUMNNAME_CopyFrom = "CopyFrom";

	public void setCopyFrom(boolean CopyFrom) {
		set_Value("CopyFrom", new Boolean(CopyFrom));
	}

	/**
	 * Get Copy From. Copy From Record
	 */
	public boolean isCopyFrom() {
		Object oo = get_Value("CopyFrom");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/**
	 * Set UOM. Unit of Measure
	 */
	public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	public void setC_UOM_ID(int C_UOM_ID) {
		set_Value("C_UOM_ID", new Integer(C_UOM_ID));
	}

	/**
	 * Get UOM. Unit of Measure
	 */
	public int getC_UOM_ID() {
		Integer ii = (Integer) get_Value("C_UOM_ID");
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

	/**
	 * Set Document No. Document sequence NUMERIC of the document
	 */
	public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	public void setDocumentNo(String DocumentNo) {
		if (DocumentNo != null && DocumentNo.length() > 20) {
			log.warning("Length > 20 - truncated");
			DocumentNo = DocumentNo.substring(0, 20);
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
	 * Set Attribute Set Instance. Product Attribute Set Instance
	 */
	public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	public void setM_AttributeSetInstance_ID(int M_AttributeSetInstance_ID) {
		if (M_AttributeSetInstance_ID <= 0)
			set_Value("M_AttributeSetInstance_ID", null);
		else
			set_Value("M_AttributeSetInstance_ID", new Integer(M_AttributeSetInstance_ID));
	}

	/**
	 * Get Attribute Set Instance. Product Attribute Set Instance
	 */
	public int getM_AttributeSetInstance_ID() {
		Integer ii = (Integer) get_Value("M_AttributeSetInstance_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Change Notice. Bill of Materials (Engineering) Change Notice
	 * (Version)
	 */
	public static final String COLUMNNAME_M_ChangeNotice_ID = "M_ChangeNotice_ID";

	public void setM_ChangeNotice_ID(int M_ChangeNotice_ID) {
		if (M_ChangeNotice_ID <= 0)
			set_Value("M_ChangeNotice_ID", null);
		else
			set_Value("M_ChangeNotice_ID", new Integer(M_ChangeNotice_ID));
	}

	/**
	 * Get Change Notice. Bill of Materials (Engineering) Change Notice
	 * (Version)
	 */
	public int getM_ChangeNotice_ID() {
		Integer ii = (Integer) get_Value("M_ChangeNotice_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Product. Product, Service, Item
	 */
	public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	public void setM_Product_ID(int M_Product_ID) {
		set_Value("M_Product_ID", new Integer(M_Product_ID));
	}

	/**
	 * Get Product. Product, Service, Item
	 */
	public int getM_Product_ID() {
		Integer ii = (Integer) get_Value("M_Product_ID");
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

	/** Set PP_Order_BOM_ID */
	public static final String COLUMNNAME_PP_Order_BOM_ID = "PP_Order_BOM_ID";

	public void setPP_Order_BOM_ID(int PP_Order_BOM_ID) {
		set_ValueNoCheck("PP_Order_BOM_ID", new Integer(PP_Order_BOM_ID));
	}

	/** Get PP_Order_BOM_ID */
	public int getPP_Order_BOM_ID() {
		Integer ii = (Integer) get_Value("PP_Order_BOM_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
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

	/** Set Process Now */
	public static final String COLUMNNAME_Processing = "Processing";

	public void setProcessing(boolean Processing) {
		set_Value("Processing", new Boolean(Processing));
	}

	/** Get Process Now */
	public boolean isProcessing() {
		Object oo = get_Value("Processing");
		if (oo != null) {
			if (oo instanceof Boolean)
				return ((Boolean) oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Revision */
	public static final String COLUMNNAME_Revision = "Revision";

	public void setRevision(String Revision) {
		if (Revision != null && Revision.length() > 10) {
			log.warning("Length > 10 - truncated");
			Revision = Revision.substring(0, 10);
		}
		set_Value("Revision", Revision);
	}

	/** Get Revision */
	public String getRevision() {
		return (String) get_Value("Revision");
	}

	/**
	 * Set Valid from. Valid from including this date (first day)
	 */
	public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	public void setValidFrom(Timestamp ValidFrom) {
		if (ValidFrom == null)
			throw new IllegalArgumentException("ValidFrom is mandatory");
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
		if (Value == null)
			throw new IllegalArgumentException("Value is mandatory");
		if (Value.length() > 80) {
			log.warning("Length > 80 - truncated");
			Value = Value.substring(0, 80);
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
}
