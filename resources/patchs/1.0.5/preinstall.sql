----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

ALTER TABLE PP_Cost_Collector ALTER COLUMN DurationReal TYPE numeric;
ALTER TABLE PP_Cost_Collector ALTER COLUMN MovementQty TYPE numeric;
ALTER TABLE PP_Cost_Collector ALTER COLUMN DateAcct TYPE timestamp without time zone;
ALTER TABLE PP_Cost_Collector ALTER COLUMN DocAction TYPE character(2);
ALTER TABLE PP_Cost_Collector ALTER COLUMN MovementDate TYPE timestamp without time zone;
ALTER TABLE PP_Cost_Collector ALTER COLUMN Created TYPE timestamp without time zone;
ALTER TABLE PP_Cost_Collector ALTER COLUMN Created TYPE timestamp without time zone;
ALTER TABLE PP_Cost_Collector ALTER COLUMN ProcessedOn TYPE numeric;
ALTER TABLE PP_Cost_Collector ALTER COLUMN QtyReject TYPE numeric;
ALTER TABLE PP_Cost_Collector ALTER COLUMN ScrappedQty TYPE numeric;
ALTER TABLE PP_Cost_Collector ALTER COLUMN SetupTimeReal TYPE numeric;
ALTER TABLE M_Cost ALTER COLUMN M_Warehouse_ID DROP NOT NULL ;
