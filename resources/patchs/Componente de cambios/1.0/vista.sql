CREATE OR REPLACE VIEW pp_om_collectors_mat_out AS 
 SELECT collector.ad_client_id, collector.ad_org_id, collector.isactive, collector.created, collector.createdby, collector.updated, collector.updatedby, collector.costcollectortype, collector.docstatus, collector.movementdate, prod.value, prod.name, collector.movementqty, collector.qtyreject, collector.scrappedqty, collector.pp_order_id, collector.pp_order_bomline_id, ord.documentno, wh.name AS name_wh, loc.value AS name_loc, attr.description, ord.pp_order_id AS pp_om_id
   FROM pp_cost_collector collector
   LEFT JOIN pp_order ord ON ord.pp_order_id = collector.pp_order_id
   LEFT JOIN pp_order_bomline bomline ON bomline.pp_order_bomline_id = collector.pp_order_bomline_id
   LEFT JOIN m_warehouse wh ON wh.m_warehouse_id = collector.m_warehouse_id
   LEFT JOIN m_locator loc ON loc.m_locator_id = collector.m_locator_id
   LEFT JOIN m_product prod ON prod.m_product_id = collector.m_product_id
   LEFT JOIN m_attributesetinstance attr ON attr.m_attributesetinstance_id = collector.m_attributesetinstance_id
  WHERE collector.isactive = 'Y'::bpchar AND (collector.costcollectortype::text = '110'::text  OR collector.costcollectortype::text = '105'::text OR collector.costcollectortype::text = '115'::text );

