/*
 *    El contenido de este fichero está sujeto a la  Licencia Pública openXpertya versión 1.1 (LPO)
 * en tanto en cuanto forme parte íntegra del total del producto denominado:  openXpertya, solución 
 * empresarial global , y siempre según los términos de dicha licencia LPO.
 *    Una copia  íntegra de dicha  licencia está incluida con todas  las fuentes del producto.
 *    Partes del código son CopyRight (c) 2002-2007 de Ingeniería Informática Integrada S.L., otras 
 * partes son  CopyRight (c) 2002-2007 de  Consultoría y  Soporte en  Redes y  Tecnologías  de  la
 * Información S.L.,  otras partes son  adaptadas, ampliadas,  traducidas, revisadas  y/o mejoradas
 * a partir de código original de  terceros, recogidos en el  ADDENDUM  A, sección 3 (A.3) de dicha
 * licencia  LPO,  y si dicho código es extraido como parte del total del producto, estará sujeto a
 * su respectiva licencia original.  
 *     Más información en http://www.openxpertya.org/ayuda/Licencia.html
 */



package org.openXpertya.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Descripción de Clase
 *
 *
 * @version    2.2, 12.10.07
 * @author     Equipo de Desarrollo de openXpertya    
 */

public class MProductCategoryAcct extends X_M_Product_Category_Acct {

    /**
     * Constructor de la clase ...
     *
     *
     * @param ctx
     * @param ignored
     * @param trxName
     */

    public MProductCategoryAcct( Properties ctx,int ignored,String trxName ) {
        super( ctx,ignored,trxName );

        if( ignored != 0 ) {
            throw new IllegalArgumentException( "Multi-Key" );
        }
    }    // MProductCategoryAcct

    /**
     * Constructor de la clase ...
     *
     *
     * @param ctx
     * @param rs
     * @param trxName
     */

    public MProductCategoryAcct( Properties ctx,ResultSet rs,String trxName ) {
        super( ctx,rs,trxName );
    }    // MProductCategoryAcct

    /**
     * Descripción de Método
     *
     */

    public void checkCosting() {

        // Create Cost Elements

        if( (getCostingMethod() != null) && (getCostingMethod().length() > 0) ) {
            MCostElement.getMaterialCostElement( this,getCostingMethod());
        }
    }    // checkCosting

    /**
     * Descripción de Método
     *
     *
     * @param newRecord
     * @param success
     *
     * @return
     */

    protected boolean afterSave( boolean newRecord,boolean success ) {
        checkCosting();

        return success;
    }    // afterSave

    public static final int COSTINGMETHOD_AD_Reference_ID = MReference.getReferenceID("C_AcctSchema Costing Method");
    /** Standard Costing = S */
    public static final String COSTINGMETHOD_StandardCosting = "S";
    /** Average PO = A */
    public static final String COSTINGMETHOD_AveragePO = "A";
    /** Lifo = L */
    public static final String COSTINGMETHOD_Lifo = "L";
    /** Fifo = F */
    public static final String COSTINGMETHOD_Fifo = "F";
    /** Last PO Price = p */
    public static final String COSTINGMETHOD_LastPOPrice = "p";
    /** Average Invoice = I */
    public static final String COSTINGMETHOD_AverageInvoice = "I";
    /** Last Invoice = i */
    public static final String COSTINGMETHOD_LastInvoice = "i";
    /** User Defined = U */
    public static final String COSTINGMETHOD_UserDefined = "U";
    /** Set Costing Method.
    Indicates how Costs will be calculated */
    @Override
    public void setCostingMethod (String CostingMethod)
    {
    if (CostingMethod == null || CostingMethod.equals("S") || CostingMethod.equals("A") 
    						|| CostingMethod.equals("L") || CostingMethod.equals("F") 
    						|| CostingMethod.equals("p") || CostingMethod.equals("I")
    						|| CostingMethod.equals("i") || CostingMethod.equals("U"));
     else throw new IllegalArgumentException ("CostingMethod Invalid value - Reference = COSTINGMETHOD_AD_Reference_ID - P - S - A - L - F");
    if (CostingMethod != null && CostingMethod.length() > 1)
    {
    log.warning("Length > 1 - truncated");
    CostingMethod = CostingMethod.substring(0,1);
    }
    set_Value ("CostingMethod", CostingMethod);
    }
    /** Get Costing Method.
    Indicates how Costs will be calculated */
    @Override
    public String getCostingMethod() 
    {
    return (String)get_Value("CostingMethod");
    }


}    // MProductCategoryAcct



/*
 *  @(#)MProductCategoryAcct.java   02.07.07
 * 
 *  Fin del fichero MProductCategoryAcct.java
 *  
 *  Versión 2.2
 *
 */
