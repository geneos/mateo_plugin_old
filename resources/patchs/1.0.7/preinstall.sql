----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------

CREATE VIEW PP_Prod_Prov AS
SELECT prod.m_product_id, prod.value AS codprod, prod.name AS nameprod, prod.m_product_category_id, prov.c_bpartner_id, prov.value AS codprov, prov.name AS nameprov, po.iscurrentvendor, prod.ad_client_id, prod.ad_org_id, prod.isactive, prod.created, prod.createdby, prod.updated, prod.updatedby FROM ((m_product prod LEFT JOIN m_product_po po ON ((prod.m_product_id = po.m_product_id))) LEFT JOIN c_bpartner prov ON ((prov.c_bpartner_id = po.c_bpartner_id))) WHERE (((prod.isactive = 'Y'::bpchar) AND (prov.isactive = 'Y'::bpchar)) AND (po.isactive = 'Y'::bpchar));

CREATE VIEW PP_Prod_BOM AS
SELECT prod.m_product_id, prod.value AS codprod, prod.name AS nameprod, prod.m_product_category_id, prod_bom.pp_product_bom_id, prod_bom.ad_client_id, prod_bom.ad_org_id, prod_bom.isactive, prod_bom.created, prod_bom.createdby, prod_bom.updated, prod_bom.updatedby FROM (pp_product_bom prod_bom LEFT JOIN m_product prod ON ((prod.m_product_id = prod_bom.m_product_id))) WHERE ((prod.isactive = 'Y'::bpchar) AND (prod_bom.isactive = 'Y'::bpchar));

CREATE VIEW PP_Prod_BOMLine AS
SELECT prod_bomline.pp_product_bom_id, prod_bomline.isqtypercentage, prod_bomline.qtybom, prod_bomline.scrap, prod_bomline.qtybatch, prod_bomline.componenttype, prod.value AS codprod, prod.name AS nameprod, uom.name, uom.uomsymbol, prod_bomline.ad_client_id, prod_bomline.ad_org_id, prod_bomline.isactive, prod_bomline.created, prod_bomline.createdby, prod_bomline.updated, prod_bomline.updatedby, prod_bomline.pp_product_bomline_id, prod.ispurchased FROM ((pp_product_bomline prod_bomline LEFT JOIN m_product prod ON ((prod.m_product_id = prod_bomline.m_product_id))) LEFT JOIN c_uom uom ON ((uom.c_uom_id = prod_bomline.c_uom_id))) WHERE ((prod.isactive = 'Y'::bpchar) AND (prod_bomline.isactive = 'Y'::bpchar));

CREATE VIEW PP_ReqInfo AS
SELECT reqlines.ad_client_id, reqlines.ad_org_id, reqlines.isactive, reqlines.created, reqlines.createdby, reqlines.updated, reqlines.updatedby, reqlines.qty, req.daterequired, req.datedoc, req.docstatus, prod.m_product_id, prod.value AS codprod, prod.name AS nameprod, uom.name, uom.uomsymbol, prov.c_bpartner_id, prov.value AS codprov, prov.name AS nameprov, req.documentno FROM ((((m_requisitionline reqlines LEFT JOIN m_requisition req ON ((req.m_requisition_id = reqlines.m_requisition_id))) LEFT JOIN c_uom uom ON ((uom.c_uom_id = reqlines.c_uom_id))) LEFT JOIN c_bpartner prov ON ((prov.c_bpartner_id = reqlines.c_bpartner_id))) LEFT JOIN m_product prod ON ((prod.m_product_id = reqlines.m_product_id))) WHERE ((prod.isactive = 'Y'::bpchar) AND (prov.isactive = 'Y'::bpchar));

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

