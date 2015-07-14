/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.mrp.plugin.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.openXpertya.model.MInOut;
import org.openXpertya.model.MMovement;
import org.openXpertya.util.DB;
import org.openXpertya.util.Env;

import ar.com.geneos.mrp.plugin.util.MUDB;

/**
 * Modelo Generado por C_OrderLine
 * 
 * @author Comunidad de Desarrollo Libertya* *Basado en Codigo Original
 *         Modificado, Revisado y Optimizado de:* * Jorg Janke
 * @version - 2015-06-15 12:11:13.306
 */
public class LP_M_MovementLine extends org.openXpertya.model.MMovementLine implements IDocumentLine {

	public LP_M_MovementLine(MMovement mov) {
		super(mov);
		// TODO Auto-generated constructor stub
	}

	public LP_M_MovementLine(Properties ctx, int M_Movement_ID, String trxName) {
		super(ctx, M_Movement_ID, trxName);
		// TODO Auto-generated constructor stub
	}

	/** Load Constructor */
	public LP_M_MovementLine(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	private MMovement m_parent = null;

	public String toString() {
		StringBuffer sb = new StringBuffer("LP_M_InOutLine[").append(getID()).append("]");
		return sb.toString();
	}

	public MMovement getParent() {
		if (m_parent == null)
			m_parent = new MMovement(getCtx(), getM_Movement_ID(), get_TrxName());
		return m_parent;
	} // getParent

	@Override
	public int get_ID() {
		// TODO Auto-generated method stub
		return getID();
	}

	@Override
	public int getM_AttributeSetInstanceTo_ID() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getM_LocatorTo_ID() {
		// TODO Auto-generated method stub
		return -1;
	}

	/** Column name ReversalLine_ID */
	public static final String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";
	private static final String COLUMNNAME_M_InOutLine_ID = null;

	/**
	 * Set Reversal Line.
	 * 
	 * @param ReversalLine_ID
	 *            Use to keep the reversal line ID for reversing costing purpose
	 */
	public void setReversalLine_ID(int ReversalLine_ID) {
		if (ReversalLine_ID < 1)
			set_Value(COLUMNNAME_ReversalLine_ID, null);
		else
			set_Value(COLUMNNAME_ReversalLine_ID, Integer.valueOf(ReversalLine_ID));
	}

	/**
	 * Get Reversal Line.
	 * 
	 * @return Use to keep the reversal line ID for reversing costing purpose
	 */
	public int getReversalLine_ID() {
		Integer ii = (Integer) get_Value(COLUMNNAME_ReversalLine_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public Timestamp getDateAcct() {
		return getParent().getMovementDate();
	}

	public BigDecimal getPriceActual() {
		return Env.ZERO;
	}

	public int getC_DocType_ID() {
		return getParent().getC_DocType_ID();
	}

	public boolean isSOTrx() {
		return false;
	}

}
