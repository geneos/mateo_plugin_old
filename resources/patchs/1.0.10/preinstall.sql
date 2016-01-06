----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

ALTER TABLE M_Product_Category_Acct ADD COLUMN M_CostType_ID integer;
----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

-- Actualizacion funciones, agregado a mano --
CREATE OR REPLACE FUNCTION qtyAvailableForBOMLine(p_order_bomline_id integer)
  RETURNS numeric AS
$BODY$
DECLARE

		v_Warehouse_ID		INTEGER;
		v_Product_ID		INTEGER;
		v_QuantityReserved	NUMERIC := 0;
BEGIN

		IF (p_order_bomline_id IS NULL) THEN
			RETURN 0;
		ELSE
			SELECT 	M_Warehouse_ID,M_Product_ID,qtyReserved INTO v_Warehouse_ID,v_Product_ID,v_QuantityReserved
			FROM	PP_Order_BOMLine
			WHERE	PP_Order_BOMLine_ID=p_order_bomline_id;
		END IF;
		-- Disponible: 
		-- Si Stock - reservados es negativo entonces mi disponible es el minimo entre stock y mi reservado
		IF (bomQtyOnHand(v_Product_ID, v_Warehouse_ID, 0) < bomQtyReserved(v_Product_ID, v_Warehouse_ID, 0) ) THEN
			RETURN bomQtyOnHand(v_Product_ID, v_Warehouse_ID, 0);
		-- Sino entonces mi disponible es igual a stockGeneral - reservadoGeneral + reservadoLocal		
		ELSE
			RETURN bomQtyOnHand(v_Product_ID, v_Warehouse_ID, 0) - bomQtyReserved(v_Product_ID, v_Warehouse_ID, 0) + v_QuantityReserved;
	        END IF;
	
END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION libertya.qtyAvailableForBOMLine(integer)
  OWNER TO libertya;

