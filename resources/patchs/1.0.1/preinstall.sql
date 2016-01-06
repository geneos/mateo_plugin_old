----------------------------------------------------------------------
---------- Funciones 
----------------------------------------------------------------------

------------------------
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
			RETURN LEAST(bomQtyOnHand(v_Product_ID, v_Warehouse_ID, 0),v_QuantityReserved);
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

------------------------------------

CREATE OR REPLACE FUNCTION qtyDeliveredForBOMLine(p_order_bomline_id integer,p_attributesetinstance_id integer)
  RETURNS numeric AS
$BODY$
DECLARE

		v_qtyDelivered		NUMERIC:= 0;
BEGIN

		IF (p_order_bomline_id IS NULL) THEN
			RETURN 0;
		ELSE
			-- Delivered
			IF (p_attributesetinstance_id = 0) THEN
				SELECT 	SUM(movementqty) INTO v_qtyDelivered
				FROM	PP_Cost_Collector
				WHERE	PP_Order_BOMLine_ID=p_order_bomline_id AND costcollectortype in ('110','115') AND docstatus in ('CO','CL');
			ELSE
				SELECT 	SUM(movementqty) INTO v_qtyDelivered
				FROM	PP_Cost_Collector
				WHERE	PP_Order_BOMLine_ID=p_order_bomline_id AND costcollectortype in ('110','115') AND docstatus in ('CO','CL') AND m_attributesetinstance_id=p_attributesetinstance_id;
			END IF;
		END IF;
		IF (v_qtyDelivered IS NULL) THEN
			return 0;
		END IF;
		RETURN v_qtyDelivered;
	
END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION qtyDeliveredForBOMLine(integer,integer)
  OWNER TO libertya;

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

ALTER TABLE PP_Cost_Collector ALTER COLUMN costcollectortype TYPE character varying(3);
