package ar.com.geneos.mrp.plugin.util;

import java.util.ArrayList;
import java.util.List;

import org.openXpertya.model.MInOutLine;
import org.openXpertya.model.MInOutLineMA;
import org.openXpertya.model.MLocator;
import org.openXpertya.model.MProjectIssue;
import org.openXpertya.model.MTransaction;
import org.openXpertya.model.Query;

import ar.com.geneos.mrp.plugin.model.IDocumentLine;
import ar.com.geneos.mrp.plugin.model.LP_M_InOutLine;
import ar.com.geneos.mrp.plugin.model.LP_M_InventoryLine;
import ar.com.geneos.mrp.plugin.model.LP_M_MovementLine;
import ar.com.geneos.mrp.plugin.model.LP_M_Transaction;
import ar.com.geneos.mrp.plugin.model.MPPCostCollector;

public class MUMTransaction {

	public static IDocumentLine getDocumentLine(MTransaction t) {
		if (t.getM_InOutLine_ID() > 0)
			return (IDocumentLine) getM_InOutLine(t);
		if (t.getM_InventoryLine_ID() > 0)
			return (IDocumentLine) getM_InventoryLine(t);
		if (t.getM_MovementLine_ID() > 0)
			return (IDocumentLine) getM_MovementLine(t);
		/**
		 * Libero to Libertya migration Production Orders not implemented
		 */
		/*
		 * if (t.getM_ProductionLine_ID() > 0) return (IDocumentLine)
		 * getM_ProductionLine(t);
		 */
		if (getPP_Cost_Collector_ID(t) > 0)
			return (IDocumentLine) getPP_Cost_Collector(t);

		return null;
	}

	public static LP_M_InOutLine getM_InOutLine(MTransaction t) throws RuntimeException {
		return new LP_M_InOutLine(t.getCtx(), t.getM_InOutLine_ID(), t.get_TrxName());
	}

	public static MProjectIssue getC_ProjectIssue(MTransaction t) throws RuntimeException {
		return new MProjectIssue(t.getCtx(), t.getC_ProjectIssue_ID(), t.get_TrxName());
	}

	public static LP_M_InventoryLine getM_InventoryLine(MTransaction t) throws RuntimeException {
		return new LP_M_InventoryLine(t.getCtx(), t.getM_InventoryLine_ID(), t.get_TrxName());
	}

	public static LP_M_MovementLine getM_MovementLine(MTransaction t) throws RuntimeException {
		return new LP_M_MovementLine(t.getCtx(), t.getM_MovementLine_ID(), t.get_TrxName());
	}

	/**
	 * Libero to Libertya migration Production Orders not implemented
	 */
	/*
	 * public static MProductionLine getM_ProductionLine(MTransaction t) throws
	 * RuntimeException { return new MProductionLine(t.getCtx(),
	 * t.getM_ProductionLine_ID(), t.get_TrxName()); }
	 */

	public static MPPCostCollector getPP_Cost_Collector(MTransaction t) throws RuntimeException {
		return new MPPCostCollector(t.getCtx(), getPP_Cost_Collector_ID(t), t.get_TrxName());
	}

	public static int getPP_Cost_Collector_ID(MTransaction t) {
		LP_M_Transaction aux = new LP_M_Transaction(t.getCtx(), t.getID(), t.get_TrxName());
		return aux.getPP_Cost_Collector_ID();
	}

	/**
	 * get Warehouse ID
	 * 
	 * @return Warehouse ID
	 */
	public static int getM_Warehouse_ID(MTransaction t) {
		return getM_Locator(t).getM_Warehouse_ID();
	}

	public static MLocator getM_Locator(MTransaction t) throws RuntimeException {
		return new MLocator(t.getCtx(), t.getM_Locator_ID(), t.get_TrxName());
	}

	/**
	 * get the transaction based on Document Line and movement type
	 * 
	 * @param model
	 *            IDocumentLine
	 * @param type
	 *            Movement Type
	 * @return first MTransaction
	 */
	public static MTransaction getByDocumentLine(IDocumentLine model, String type) {
		final String column_id = model.get_TableName() + "_ID";
		final String whereClause = column_id + "=? AND " + LP_M_Transaction.COLUMNNAME_MovementType + "=? ";
		return new Query(model.getCtx(), LP_M_Transaction.Table_Name, whereClause, model.get_TrxName()).setClient_ID().setParameters(model.get_ID(), type)
				.first();
	}

	/**
	 * get all material transaction for MInOutLine
	 * 
	 * @param line
	 *            MInOutLine
	 * @return List the MTransaction
	 */
	static public List<MTransaction> getByInOutLine(MInOutLine line) {
		ArrayList<MTransaction> transactions = new ArrayList();

		MInOutLineMA[] lines = MInOutLineMA.get(line.getCtx(), line.getM_InOutLine_ID(), line.get_TrxName());
		if (lines != null && lines.length == 0) {
			MTransaction transaction = get(line, line.getM_AttributeSetInstance_ID());
			if (transaction != null && transaction.getID() > 0)
				transactions.add(transaction);

			return transactions;
		}
		for (MInOutLineMA ma : lines) {
			MTransaction trx = get(line, ma.getM_AttributeSetInstance_ID());
			transactions.add(trx);
		}
		return transactions;
	}

	static public MTransaction get(MInOutLine line, int M_ASI_ID) {
		final String whereClause = LP_M_InOutLine.COLUMNNAME_M_Product_ID + "=? AND " + LP_M_InOutLine.COLUMNNAME_M_InOutLine_ID + "=? AND "
				+ LP_M_InOutLine.COLUMNNAME_M_AttributeSetInstance_ID + "=?";

		return new Query(line.getCtx(), MInOutLine.Table_Name, whereClause, line.get_TrxName()).setClient_ID()
				.setParameters(line.getM_Product_ID(), line.getM_InOutLine_ID(), M_ASI_ID).firstOnly();
	}

}
