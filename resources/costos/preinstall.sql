----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

ALTER TABLE M_Cost ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE M_Costqueue ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE C_LandedCostAllocation ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE M_Costdetail ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE M_CostElement ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE PP_Product_BOM ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE PP_Product_BOMLine ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE PP_WF_Node_Asset ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE PP_WF_Node_Product ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE M_Forecast ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE M_ForecastLine ADD COLUMN AD_ComponentObjectUID character varying(100);
----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

ALTER TABLE M_Product_Category_Acct ALTER COLUMN CostingLevel TYPE character(1);
ALTER TABLE PP_Order_Cost ALTER COLUMN CostingMethod TYPE character(1);
ALTER TABLE C_AcctSchema ALTER COLUMN CostingLevel TYPE character(1);
