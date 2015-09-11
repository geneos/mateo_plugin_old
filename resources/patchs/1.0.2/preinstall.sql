----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

ALTER TABLE M_ForecastLine ADD COLUMN S_Resource_ID integer NOT NULL;
----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

DROP VIEW RV_PP_MRP;

ALTER TABLE PP_MRP ALTER COLUMN OrderType TYPE character varying(3);

CREATE VIEW RV_PP_MRP AS
SELECT mrp.pp_mrp_id, mrp.ad_client_id, mrp.ad_org_id, mrp.created, mrp.createdby, mrp.isactive, mrp.isavailable, mrp.updated, mrp.updatedby, pp.ismps, pp.isrequiredmrp, pp.isrequireddrp, p.isbom, p.ispurchased, p.m_product_category_id, p.m_attributesetinstance_id, mrp.name, mrp.description, mrp.c_order_id, mrp.c_orderline_id, mrp.dateordered, mrp.dateconfirm, mrp.datepromised, mrp.datestartschedule, mrp.datefinishschedule, mrp.datestart, mrp.datesimulation, mrp.docstatus, mrp.m_forecast_id, mrp.m_forecastline_id, mrp.value, mrp.m_product_id, mrp.m_requisition_id, mrp.m_requisitionline_id, mrp.m_warehouse_id, mrp.pp_order_id, mrp.pp_order_bomline_id, mrp.qty, mrp.s_resource_id, mrp.planner_id, mrp.priority, mrp.ordertype, mrp.typemrp, p.lowlevel, mrp.c_bpartner_id, mrp.version, documentno((mrp.pp_mrp_id)::numeric) AS documentno, mrp.c_project_id, mrp.c_projectphase_id, mrp.c_projecttask_id FROM ((pp_mrp mrp JOIN m_product p ON ((mrp.m_product_id = p.m_product_id))) LEFT JOIN pp_product_planning pp ON (((pp.m_product_id = mrp.m_product_id) AND (mrp.m_warehouse_id = pp.m_warehouse_id)))) WHERE (mrp.qty <> (0)::numeric) UNION SELECT 0 AS pp_mrp_id, pp.ad_client_id, pp.ad_org_id, pp.created, pp.createdby, pp.isactive, 'Y'::bpchar AS isavailable, pp.updated, pp.updatedby, pp.ismps, pp.isrequiredmrp, pp.isrequireddrp, p.isbom, p.ispurchased, p.m_product_category_id, p.m_attributesetinstance_id, NULL::character varying AS name, NULL::character varying AS description, NULL::numeric AS c_order_id, NULL::numeric AS c_orderline_id, now() AS dateordered, now() AS dateconfirm, now() AS datepromised, now() AS datestartschedule, now() AS datefinishschedule, now() AS datestart, now() AS datesimulation, 'CO'::character varying AS docstatus, NULL::numeric AS m_forecast_id, NULL::numeric AS m_forecastline_id, NULL::character varying AS value, pp.m_product_id, NULL::numeric AS m_requisition_id, NULL::numeric AS m_requisitionline_id, pp.m_warehouse_id, NULL::numeric AS pp_order_id, NULL::numeric AS pp_order_bomline_id, (pp.safetystock - bomqtyonhand((pp.m_product_id)::numeric, (pp.m_warehouse_id)::numeric, (0)::numeric)) AS qty, pp.s_resource_id, NULL::numeric AS planner_id, NULL::character varying AS priority, 'STK'::character varying AS ordertype, 'D'::bpchar AS typemrp, p.lowlevel, NULL::numeric AS c_bpartner_id, NULL::numeric AS version, 'Safety Strock'::character varying AS documentno, NULL::numeric AS c_project_id, NULL::numeric AS c_projectphase_id, NULL::numeric AS c_projecttask_id FROM (pp_product_planning pp JOIN m_product p ON ((pp.m_product_id = p.m_product_id))) WHERE (bomqtyonhand((pp.m_product_id)::numeric, (pp.m_warehouse_id)::numeric, (0)::numeric) < pp.safetystock);

