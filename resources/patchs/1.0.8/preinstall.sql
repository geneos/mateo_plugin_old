----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------

CREATE VIEW PP_OM AS
SELECT om.ad_client_id, om.ad_org_id, om.isactive, om.created, om.createdby, om.updated, om.updatedby, om.m_product_id, prod.value AS codprod, prod.name AS nameprod, om.pp_product_bom_id, om.m_warehouse_id, om.ad_workflow_id, om.qtyentered, om.qtydelivered, om.dateordered, om.datepromised, om.datestart, om.datestartschedule, om.datefinish, om.datefinishschedule, om.docstatus, om.m_attributesetinstance_id, bom.name AS nameprodbom, wh.name AS namewarehouse, wf.name AS nameworkflow, attr.description AS nameattr, om.pp_order_id, om.pp_order_id AS pp_om_id FROM (((((pp_order om LEFT JOIN m_product prod ON ((prod.m_product_id = om.m_product_id))) LEFT JOIN ad_workflow wf ON ((wf.ad_workflow_id = om.ad_workflow_id))) LEFT JOIN m_warehouse wh ON ((wh.m_warehouse_id = om.m_warehouse_id))) LEFT JOIN pp_product_bom bom ON ((bom.pp_product_bom_id = om.pp_product_bom_id))) LEFT JOIN m_attributesetinstance attr ON ((attr.m_attributesetinstance_id = om.m_attributesetinstance_id))) WHERE (om.isactive = 'Y'::bpchar);

CREATE VIEW PP_OM_BOM AS
SELECT ombomline.ad_client_id, ombomline.ad_org_id, ombomline.isactive, ombomline.created, ombomline.createdby, ombomline.updated, ombomline.updatedby, ombomline.m_product_id, prod.value AS codprod, prod.name AS nameprod, ombomline.qtybom, ombomline.qtyentered, ombomline.qtyreserved, ombomline.iscritical, ombomline.isqtypercentage, ombom.pp_order_bom_id, ombom.pp_order_id, ombom.pp_order_id AS pp_om_id FROM ((pp_order_bomline ombomline LEFT JOIN pp_order_bom ombom ON ((ombom.pp_order_bom_id = ombomline.pp_order_bom_id))) LEFT JOIN m_product prod ON ((prod.m_product_id = ombomline.m_product_id)));

CREATE VIEW PP_OM_WF AS
SELECT omnode.ad_client_id, omnode.ad_org_id, omnode.isactive, omnode.created, omnode.createdby, omnode.updated, omnode.updatedby, omnode.value, omnode.name, omnode.qtyrequired, omnode.durationrequired, omnode.durationreal, omwf.pp_order_id, omnode.datestart, omnode.datestartschedule, omnode.datefinish, omnode.datefinishschedule, res.name AS nameresource, res.value AS valueresource, omwf.pp_order_id AS pp_om_id FROM ((pp_order_node omnode LEFT JOIN pp_order_workflow omwf ON ((omwf.pp_order_workflow_id = omnode.pp_order_workflow_id))) LEFT JOIN s_resource res ON ((res.s_resource_id = omnode.s_resource_id)));

----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

