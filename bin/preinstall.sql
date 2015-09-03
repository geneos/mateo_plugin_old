----------------------------------------------------------------------
---------- Nuevas Tablas y/o Vistas 
----------------------------------------------------------------------
CREATE TABLE PP_Product_BOM(

value character varying(80) NOT NULL ,
name character varying(60) NOT NULL ,
documentno character varying(22) ,
revision character varying(10) ,
description character varying(255) ,
copyfrom character(1) ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
help character varying(2000) ,
isactive character(1) NOT NULL ,
ad_client_id integer NOT NULL ,
m_changenotice_id integer ,
m_product_id integer NOT NULL ,
pp_product_bom_id integer NOT NULL ,
processing character(1) ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
validfrom timestamp without time zone NOT NULL ,
validto timestamp without time zone ,
m_attributesetinstance_id integer ,
ad_org_id integer NOT NULL ,
bomtype character(1) DEFAULT 'A'::bpchar ,
bomuse character(1) DEFAULT 'M'::bpchar ,
c_uom_id integer ,
ad_componentobjectuid character varying(100),
CONSTRAINT pp_product_bom_pkey PRIMARY KEY (pp_product_bom_id) ,
CONSTRAINT cuom_ppproductbom FOREIGN KEY (c_uom_id) REFERENCES c_uom (c_uom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mattributesetinstance_ppproduc FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mchangenotice_ppproductbom FOREIGN KEY (m_changenotice_id) REFERENCES m_changenotice (m_changenotice_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_ppproductbom FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Product_BOMLine(

feature character varying(30) ,
ad_org_id integer NOT NULL ,
assay numeric ,
backflushgroup character varying(20) ,
c_uom_id integer ,
componenttype character(2) DEFAULT 'CO'::bpchar ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
description character varying(255) ,
forecast numeric ,
help character varying(2000) ,
isactive character(1) NOT NULL ,
iscritical character(1) DEFAULT 'N'::bpchar ,
isqtypercentage character(1) ,
issuemethod character(1) NOT NULL DEFAULT '1'::bpchar ,
leadtimeoffset numeric(10) ,
line numeric(10) NOT NULL ,
m_attributesetinstance_id integer ,
m_changenotice_id integer ,
m_product_id integer NOT NULL ,
pp_product_bomline_id integer NOT NULL ,
pp_product_bom_id integer NOT NULL ,
qtybom numeric ,
qtybatch numeric ,
scrap numeric ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
validfrom timestamp without time zone NOT NULL ,
ad_client_id integer NOT NULL ,
validto timestamp without time zone ,
costallocationperc numeric DEFAULT 0 ,
ad_componentobjectuid character varying(100),
CONSTRAINT pp_product_bomline_pkey PRIMARY KEY (pp_product_bomline_id) ,
CONSTRAINT cuom_ppproductbomline FOREIGN KEY (c_uom_id) REFERENCES c_uom (c_uom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mattributesetinstance_ppprodbl FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mchangenotice_ppproductbomline FOREIGN KEY (m_changenotice_id) REFERENCES m_changenotice (m_changenotice_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_ppproductbomline FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppproductbom_ppproductbomline FOREIGN KEY (pp_product_bom_id) REFERENCES pp_product_bom (pp_product_bom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Order(

documentno character varying(60) NOT NULL ,
s_resource_id integer NOT NULL ,
m_product_id integer NOT NULL ,
m_warehouse_id integer NOT NULL ,
assay numeric DEFAULT 0 ,
c_activity_id integer ,
c_campaign_id integer ,
c_doctypetarget_id integer NOT NULL DEFAULT 0 ,
c_doctype_id integer DEFAULT 0 ,
c_orderline_id integer ,
c_project_id integer ,
c_uom_id integer NOT NULL ,
copyfrom character(1) ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
dateconfirm timestamp without time zone ,
datedelivered timestamp without time zone ,
datefinish timestamp without time zone ,
datefinishschedule timestamp without time zone ,
dateordered timestamp without time zone NOT NULL ,
datepromised timestamp without time zone NOT NULL ,
datestart timestamp without time zone ,
datestartschedule timestamp without time zone NOT NULL ,
description character varying(510) ,
docaction character(2) NOT NULL DEFAULT 'AP'::bpchar ,
docstatus character(2) NOT NULL DEFAULT 'DR'::bpchar ,
floatafter numeric ,
floatbefored numeric ,
isactive character(1) NOT NULL ,
isapproved character(1) NOT NULL DEFAULT 'N'::bpchar ,
isprinted character(1) NOT NULL DEFAULT 'N'::bpchar ,
isqtypercentage character(1) ,
issotrx character(1) NOT NULL DEFAULT 'N'::bpchar ,
isselected character(1) NOT NULL DEFAULT 'N'::bpchar ,
line numeric(10) NOT NULL ,
lot character varying(20) ,
m_attributesetinstance_id integer ,
ordertype character varying(1) ,
pp_order_id integer NOT NULL ,
pp_product_bom_id integer NOT NULL ,
planner_id integer ,
posted character(1) ,
priorityrule character(1) NOT NULL ,
processed character(1) NOT NULL DEFAULT 'N'::bpchar ,
processing character(1) ,
qtybatchsize numeric DEFAULT 0 ,
qtybatchs numeric DEFAULT 0 ,
qtydelivered numeric NOT NULL DEFAULT 0 ,
qtyentered numeric DEFAULT 1 ,
qtyordered numeric NOT NULL DEFAULT 1 ,
qtyreject numeric NOT NULL DEFAULT 0 ,
qtyreserved numeric ,
qtyscrap numeric NOT NULL DEFAULT 0 ,
scheduletype character varying(1) DEFAULT 'D'::character varying ,
serno character varying(20) ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
user1_id integer ,
user2_id integer ,
ad_client_id integer NOT NULL ,
yield numeric NOT NULL DEFAULT 100 ,
ad_orgtrx_id integer ,
ad_org_id integer NOT NULL ,
ad_workflow_id integer NOT NULL ,
processedon numeric ,
CONSTRAINT pp_order_pkey PRIMARY KEY (pp_order_id) ,
CONSTRAINT adorgtrx_pporder FOREIGN KEY (ad_orgtrx_id) REFERENCES ad_org (ad_org_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adworkflow_pporder FOREIGN KEY (ad_workflow_id) REFERENCES ad_workflow (ad_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cactivity_pporder FOREIGN KEY (c_activity_id) REFERENCES c_activity (c_activity_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ccampaign_pporder FOREIGN KEY (c_campaign_id) REFERENCES c_campaign (c_campaign_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cdoctype_pporder FOREIGN KEY (c_doctype_id) REFERENCES c_doctype (c_doctype_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cdoctypetarget_pporder FOREIGN KEY (c_doctypetarget_id) REFERENCES c_doctype (c_doctype_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT corderline_pporder FOREIGN KEY (c_orderline_id) REFERENCES c_orderline (c_orderline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cproject_pporder FOREIGN KEY (c_project_id) REFERENCES c_project (c_project_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cuom_pporder FOREIGN KEY (c_uom_id) REFERENCES c_uom (c_uom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mattributesetinstance_pporder FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_pporder FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mwarehouse_pporder FOREIGN KEY (m_warehouse_id) REFERENCES m_warehouse (m_warehouse_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT planner_pporder FOREIGN KEY (planner_id) REFERENCES ad_user (ad_user_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppproductbom_pporder FOREIGN KEY (pp_product_bom_id) REFERENCES pp_product_bom (pp_product_bom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT sresource_pporder FOREIGN KEY (s_resource_id) REFERENCES s_resource (s_resource_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT user1_pporder FOREIGN KEY (user1_id) REFERENCES c_elementvalue (c_elementvalue_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT user2_pporder FOREIGN KEY (user2_id) REFERENCES c_elementvalue (c_elementvalue_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Order_BOM(

name character varying(60) NOT NULL ,
ad_org_id integer NOT NULL ,
bomtype character(1) ,
bomuse character(1) ,
c_uom_id integer NOT NULL ,
copyfrom character(1) ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
description character varying(255) ,
documentno character varying(20) ,
help character varying(2000) ,
isactive character(1) NOT NULL ,
m_attributesetinstance_id integer ,
m_changenotice_id integer ,
m_product_id integer NOT NULL ,
pp_order_bom_id integer NOT NULL ,
pp_order_id integer NOT NULL ,
processing character(1) ,
revision character varying(10) ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
validfrom timestamp without time zone NOT NULL ,
validto timestamp without time zone ,
ad_client_id integer NOT NULL ,
value character varying(80) NOT NULL ,
CONSTRAINT pp_order_bom_pkey PRIMARY KEY (pp_order_bom_id) ,
CONSTRAINT cuom_pporderbom FOREIGN KEY (c_uom_id) REFERENCES c_uom (c_uom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mattributesetinstance_pporderb FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mchangenotice_pporderbom FOREIGN KEY (m_changenotice_id) REFERENCES m_changenotice (m_changenotice_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_pporderbom FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_pporderbom FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Order_BOMLine(

description character varying(255) ,
feature character varying(30) ,
m_product_id integer NOT NULL ,
backflushgroup character varying(30) ,
c_uom_id integer NOT NULL ,
componenttype character(2) DEFAULT 'CO'::bpchar ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
datedelivered timestamp without time zone ,
forecast numeric ,
help character varying(2000) ,
isactive character(1) NOT NULL ,
iscritical character(1) NOT NULL ,
isqtypercentage character(1) ,
issuemethod character(1) ,
leadtimeoffset numeric(10) ,
line numeric(10) NOT NULL ,
m_attributesetinstance_id integer ,
m_changenotice_id integer ,
m_locator_id integer ,
m_warehouse_id integer NOT NULL ,
pp_order_bomline_id integer NOT NULL ,
pp_order_bom_id integer NOT NULL ,
pp_order_id integer NOT NULL ,
qtybom numeric NOT NULL ,
qtybatch numeric NOT NULL ,
qtydelivered numeric NOT NULL ,
qtyentered numeric ,
qtypost numeric NOT NULL ,
qtyreject numeric NOT NULL ,
qtyrequired numeric NOT NULL ,
qtyreserved numeric NOT NULL ,
qtyscrap numeric NOT NULL ,
scrap numeric ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
validfrom timestamp without time zone NOT NULL ,
ad_client_id integer NOT NULL ,
validto timestamp without time zone ,
ad_org_id integer NOT NULL ,
assay numeric ,
ad_user_id integer ,
costallocationperc numeric DEFAULT 0 ,
CONSTRAINT pp_order_bomline_pkey PRIMARY KEY (pp_order_bomline_id) ,
CONSTRAINT aduser_pporderbomline FOREIGN KEY (ad_user_id) REFERENCES ad_user (ad_user_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cuom_pporderbomline FOREIGN KEY (c_uom_id) REFERENCES c_uom (c_uom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mattributesetinstance_ppordbl FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mchangenotice_pporderbomline FOREIGN KEY (m_changenotice_id) REFERENCES m_changenotice (m_changenotice_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mlocator_pporderbomline FOREIGN KEY (m_locator_id) REFERENCES m_locator (m_locator_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_pporderbomline FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mwarehouse_pporderbomline FOREIGN KEY (m_warehouse_id) REFERENCES m_warehouse (m_warehouse_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporderbom_pporderbomline FOREIGN KEY (pp_order_bom_id) REFERENCES pp_order_bom (pp_order_bom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_pporderbomline FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Order_Workflow(

workflowtype character(1) DEFAULT 'M'::bpchar ,
name character varying(60) NOT NULL ,
ad_table_id integer ,
ad_wf_node_id integer ,
ad_wf_responsible_id integer ,
ad_workflowprocessor_id integer ,
ad_workflow_id integer NOT NULL ,
accesslevel character(1) NOT NULL ,
author character varying(20) NOT NULL ,
cost numeric ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
description character varying(255) ,
documentno character varying(32) ,
duration numeric(10) NOT NULL DEFAULT 0 ,
durationunit character(1) NOT NULL DEFAULT 'h'::bpchar ,
entitytype character varying(40) NOT NULL DEFAULT 'U'::character varying ,
help character varying(2000) ,
isactive character(1) NOT NULL ,
isdefault character(1) ,
limit_time numeric(10) NOT NULL ,
movingtime numeric(10) ,
pp_order_id integer NOT NULL ,
pp_order_node_id integer ,
pp_order_workflow_id integer NOT NULL ,
priority numeric(10) NOT NULL ,
processtype character(2) ,
publishstatus character(1) NOT NULL DEFAULT 'U'::bpchar ,
qtybatchsize numeric DEFAULT 1 ,
queuingtime numeric(10) ,
s_resource_id integer ,
setuptime numeric(10) ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
validfrom timestamp without time zone ,
validto timestamp without time zone ,
validateworkflow character(1) ,
value character varying(240) ,
version numeric(10) NOT NULL ,
waitingtime numeric(10) NOT NULL ,
ad_client_id integer NOT NULL ,
workingtime numeric(10) ,
ad_org_id integer NOT NULL ,
yield numeric(10) DEFAULT 100 ,
unitscycles numeric ,
overlapunits numeric ,
CONSTRAINT pp_order_workflow_pkey PRIMARY KEY (pp_order_workflow_id) ,
CONSTRAINT adtable_pporderworkflow FOREIGN KEY (ad_table_id) REFERENCES ad_table (ad_table_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adwfnode_pporderworkflow FOREIGN KEY (ad_wf_node_id) REFERENCES ad_wf_node (ad_wf_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adwfresponsible_pporderworkflo FOREIGN KEY (ad_wf_responsible_id) REFERENCES ad_wf_responsible (ad_wf_responsible_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adworkflow_pporderworkflow FOREIGN KEY (ad_workflow_id) REFERENCES ad_workflow (ad_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adworkflowprocessor_pporderwor FOREIGN KEY (ad_workflowprocessor_id) REFERENCES ad_workflowprocessor (ad_workflowprocessor_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_pporderworkflow FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT sresource_pporderworkflow FOREIGN KEY (s_resource_id) REFERENCES s_resource (s_resource_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );


CREATE TABLE PP_Order_Node(

name character varying(60) NOT NULL ,
ad_column_id integer ,
ad_form_id integer ,
ad_image_id integer ,
ad_org_id integer NOT NULL ,
ad_process_id integer ,
ad_task_id integer ,
ad_wf_block_id integer ,
ad_wf_node_id integer NOT NULL ,
ad_wf_responsible_id integer ,
ad_window_id integer ,
ad_workflow_id integer NOT NULL ,
action character(1) NOT NULL DEFAULT 'N'::bpchar ,
attributename character varying(60) ,
attributevalue character varying(60) ,
c_bpartner_id integer ,
cost numeric NOT NULL ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
datefinish timestamp without time zone ,
datefinishschedule timestamp without time zone ,
datestart timestamp without time zone ,
datestartschedule timestamp without time zone ,
description character varying(255) ,
docaction character(2) ,
docstatus character(2) ,
duration numeric(10) DEFAULT 0 ,
durationreal numeric(10) ,
durationrequired numeric(10) ,
entitytype character varying(40) NOT NULL DEFAULT 'U'::character varying ,
finishmode character(1) ,
help character varying(2000) ,
isactive character(1) NOT NULL ,
iscentrallymaintained character(1) NOT NULL ,
ismilestone character(1) ,
issubcontracting character(1) ,
joinelement character(1) NOT NULL DEFAULT 'X'::bpchar ,
limit_time numeric(10) NOT NULL ,
movingtime numeric(10) ,
overlapunits numeric(10) ,
pp_order_id integer NOT NULL ,
pp_order_node_id integer NOT NULL ,
pp_order_workflow_id integer NOT NULL ,
priority numeric(10) NOT NULL ,
qtydelivered numeric ,
qtyreject numeric ,
qtyrequired numeric ,
qtyscrap numeric ,
queuingtime numeric(10) ,
s_resource_id integer ,
setuptime numeric(10) ,
setuptimereal numeric(10) ,
setuptimerequired numeric(10) ,
splitelement character(1) NOT NULL DEFAULT 'X'::bpchar ,
startmode character(1) ,
subflowexecution character(1) ,
unitscycles numeric(10) ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
validfrom timestamp without time zone ,
validto timestamp without time zone ,
value character varying(40) NOT NULL ,
waitingtime numeric(10) NOT NULL ,
workflow_id integer ,
workingtime numeric(10) NOT NULL ,
xposition numeric(10) NOT NULL ,
ad_client_id integer NOT NULL ,
yposition numeric(10) NOT NULL ,
yield numeric(10) DEFAULT 100 ,
CONSTRAINT pp_order_node_pkey PRIMARY KEY (pp_order_node_id) ,
CONSTRAINT adcolumn_ppordernode FOREIGN KEY (ad_column_id) REFERENCES ad_column (ad_column_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adform_ppordernode FOREIGN KEY (ad_form_id) REFERENCES ad_form (ad_form_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adimage_ppordernode FOREIGN KEY (ad_image_id) REFERENCES ad_image (ad_image_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adprocess_ppordernode FOREIGN KEY (ad_process_id) REFERENCES ad_process (ad_process_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adtask_ppordernode FOREIGN KEY (ad_task_id) REFERENCES ad_task (ad_task_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adwfblock_ppordernode FOREIGN KEY (ad_wf_block_id) REFERENCES ad_wf_block (ad_wf_block_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adwfnode_ppordernode FOREIGN KEY (ad_wf_node_id) REFERENCES ad_wf_node (ad_wf_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adwfresponsible_ppordernode FOREIGN KEY (ad_wf_responsible_id) REFERENCES ad_wf_responsible (ad_wf_responsible_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adwindow_ppordernode FOREIGN KEY (ad_window_id) REFERENCES ad_window (ad_window_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adworkflow_ppordernode FOREIGN KEY (ad_workflow_id) REFERENCES ad_workflow (ad_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cbpartner_ppordernode FOREIGN KEY (c_bpartner_id) REFERENCES c_bpartner (c_bpartner_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_ppordernode FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporderworkflow_ppordernode FOREIGN KEY (pp_order_workflow_id) REFERENCES pp_order_workflow (pp_order_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT sresource_ppordernode FOREIGN KEY (s_resource_id) REFERENCES s_resource (s_resource_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT workflow_ppordernode FOREIGN KEY (workflow_id) REFERENCES ad_workflow (ad_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Order_Node_Product(

ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
isactive character(1) NOT NULL ,
m_product_id integer NOT NULL ,
pp_order_id integer NOT NULL ,
pp_order_node_id integer NOT NULL ,
pp_order_node_product_id integer NOT NULL ,
pp_order_workflow_id integer NOT NULL ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
yield numeric(10) ,
issubcontracting character(1) ,
seqno numeric(10) ,
qty numeric ,
CONSTRAINT pp_order_node_product_pkey PRIMARY KEY (pp_order_node_product_id) ,
CONSTRAINT mproduct_ppordernodeproduct FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppordernode_ppordernodeproduct FOREIGN KEY (pp_order_node_id) REFERENCES pp_order_node (pp_order_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_ppordernodeproduct FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporderworkflow_ppordernodepro FOREIGN KEY (pp_order_workflow_id) REFERENCES pp_order_workflow (pp_order_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );


CREATE TABLE PP_Order_NodeNext(

ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
ad_wf_next_id integer ,
ad_wf_node_id integer NOT NULL ,
created timestamp without time zone ,
createdby numeric(10) ,
description character varying(255) ,
entitytype character varying(40) NOT NULL DEFAULT 'U'::character varying ,
isactive character(1) NOT NULL ,
isstduserworkflow character(1) ,
pp_order_id integer NOT NULL ,
pp_order_next_id integer ,
pp_order_nodenext_id integer NOT NULL ,
pp_order_node_id integer NOT NULL ,
seqno numeric(10) NOT NULL DEFAULT 10 ,
transitioncode character varying(2000) ,
updated timestamp without time zone ,
updatedby numeric(10) NOT NULL ,
CONSTRAINT pp_order_nodenext_pkey PRIMARY KEY (pp_order_nodenext_id) ,
CONSTRAINT adwfnext_ppordernodenext FOREIGN KEY (ad_wf_next_id) REFERENCES ad_wf_node (ad_wf_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adwfnode_ppordernodenext FOREIGN KEY (ad_wf_node_id) REFERENCES ad_wf_node (ad_wf_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT entityt_ppordernodenext FOREIGN KEY (entitytype) REFERENCES ad_entitytype (entitytype) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppordernext_ppordernodenext FOREIGN KEY (pp_order_next_id) REFERENCES pp_order_node (pp_order_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppordernode_ppordernodenext FOREIGN KEY (pp_order_node_id) REFERENCES pp_order_node (pp_order_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_ppordernodenext FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Order_Node_Asset(

ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
a_asset_id integer NOT NULL ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
isactive character(1) NOT NULL ,
pp_order_id integer NOT NULL ,
pp_order_node_asset_id integer NOT NULL ,
pp_order_node_id integer NOT NULL ,
pp_order_workflow_id integer NOT NULL ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
CONSTRAINT pp_order_node_asset_pkey PRIMARY KEY (pp_order_node_asset_id) ,
CONSTRAINT aasset_ppordernodeasset FOREIGN KEY (a_asset_id) REFERENCES a_asset (a_asset_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppordernode_ppordernodeasset FOREIGN KEY (pp_order_node_id) REFERENCES pp_order_node (pp_order_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_ppordernodeasset FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporderworkflow_ppordernodeass FOREIGN KEY (pp_order_workflow_id) REFERENCES pp_order_workflow (pp_order_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Product_BOM_Trl(

ad_client_id integer NOT NULL ,
ad_language character varying(6) NOT NULL ,
ad_org_id integer NOT NULL ,
created timestamp without time zone NOT NULL DEFAULT now() ,
createdby numeric(10) NOT NULL ,
description character varying(255) ,
help character varying(2000) ,
isactive character(1) NOT NULL DEFAULT 'Y'::bpchar ,
istranslated character(1) NOT NULL ,
name character varying(60) NOT NULL ,
pp_product_bom_id integer NOT NULL ,
updated timestamp without time zone NOT NULL DEFAULT now() ,
updatedby numeric(10) NOT NULL ,
CONSTRAINT pp_product_bom_trl_pkey PRIMARY KEY (ad_language,pp_product_bom_id) ,
CONSTRAINT adlangu_ppproductbomtrl FOREIGN KEY (ad_language) REFERENCES ad_language (ad_language) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppproductbom_ppproductbomtrl FOREIGN KEY (pp_product_bom_id) REFERENCES pp_product_bom (pp_product_bom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Product_BOMLine_Trl(

ad_client_id integer NOT NULL ,
ad_language character varying(6) NOT NULL ,
ad_org_id integer NOT NULL ,
created timestamp without time zone NOT NULL DEFAULT now() ,
createdby numeric(10) NOT NULL ,
description character varying(255) ,
help character varying(2000) ,
isactive character(1) NOT NULL DEFAULT 'Y'::bpchar ,
istranslated character(1) NOT NULL ,
pp_product_bomline_id integer NOT NULL ,
updated timestamp without time zone NOT NULL DEFAULT now() ,
updatedby numeric(10) NOT NULL ,
CONSTRAINT pp_product_bomline_trl_pkey PRIMARY KEY (ad_language,pp_product_bomline_id) ,
CONSTRAINT adlangu_ppproductbomlinetrl FOREIGN KEY (ad_language) REFERENCES ad_language (ad_language) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppproductbomline_ppproductboml FOREIGN KEY (pp_product_bomline_id) REFERENCES pp_product_bomline (pp_product_bomline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );


CREATE TABLE PP_Product_Planning(

ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL DEFAULT 0 ,
ad_workflow_id integer ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
deliverytime_promised numeric ,
isactive character(1) NOT NULL ,
iscreateplan character(1) NOT NULL DEFAULT 'Y'::bpchar ,
isissue character(1) NOT NULL DEFAULT 'Y'::bpchar ,
ismps character(1) ,
isphantom character(1) NOT NULL ,
isrequiredmrp character(1) NOT NULL ,
m_product_id integer NOT NULL ,
m_warehouse_id integer ,
order_max numeric ,
order_min numeric ,
order_pack numeric ,
order_period numeric ,
order_policy character varying(3) ,
order_qty numeric ,
pp_product_bom_id integer ,
pp_product_planning_id integer NOT NULL ,
planner_id integer ,
s_resource_id integer ,
timefence numeric ,
transferttime numeric ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
workingtime numeric ,
yield numeric(10) DEFAULT 100 ,
dd_networkdistribution_id integer ,
safetystock numeric ,
isrequireddrp character(1) NOT NULL DEFAULT 'N'::bpchar ,
isalternative character(1) NOT NULL DEFAULT 'N'::bpchar ,
ad_componentobjectuid character varying(100) ,
CONSTRAINT pp_product_planning_pkey PRIMARY KEY (pp_product_planning_id) ,
CONSTRAINT adworkflow_ppproductplanning FOREIGN KEY (ad_workflow_id) REFERENCES ad_workflow (ad_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_ppproductplanning FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mwarehouse_ppproductplanning FOREIGN KEY (m_warehouse_id) REFERENCES m_warehouse (m_warehouse_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT planner_ppproductplanning FOREIGN KEY (planner_id) REFERENCES ad_user (ad_user_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppproductbom_ppproductplanning FOREIGN KEY (pp_product_bom_id) REFERENCES pp_product_bom (pp_product_bom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT sresource_ppproductplanning FOREIGN KEY (s_resource_id) REFERENCES s_resource (s_resource_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_WF_Node_Product(

entitytype character varying(40) NOT NULL DEFAULT 'U'::character varying ,
configurationlevel character(1) DEFAULT 'S'::bpchar ,
ad_wf_node_id integer NOT NULL ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
isactive character(1) NOT NULL ,
m_product_id integer NOT NULL ,
pp_wf_node_product_id integer NOT NULL ,
qty numeric ,
seqno numeric(10) ,
updated timestamp without time zone NOT NULL ,
ad_client_id integer NOT NULL ,
updatedby numeric(10) NOT NULL ,
ad_org_id integer NOT NULL ,
issubcontracting character(1) DEFAULT 'N'::bpchar ,
yield numeric(10) ,
ad_componentobjectuid character varying(100),
CONSTRAINT pp_wf_node_product_pkey PRIMARY KEY (pp_wf_node_product_id) ,
CONSTRAINT adwfnode_ppwfnodeproduct FOREIGN KEY (ad_wf_node_id) REFERENCES ad_wf_node (ad_wf_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT entityt_ppwfnodeproduct FOREIGN KEY (entitytype) REFERENCES ad_entitytype (entitytype) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_ppwfnodeproduct FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_WF_Node_Asset(

pp_wf_node_asset_id integer NOT NULL ,
ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
created timestamp without time zone NOT NULL ,
createdby numeric(10) NOT NULL ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
isactive character(1) NOT NULL ,
ad_wf_node_id integer NOT NULL ,
a_asset_id integer NOT NULL ,
seqno numeric(10) NOT NULL ,
ad_componentobjectuid character varying(100),
CONSTRAINT pp_wf_node_asset_pkey PRIMARY KEY (pp_wf_node_asset_id) ,
CONSTRAINT aasset_ppwfnodeasset FOREIGN KEY (a_asset_id) REFERENCES a_asset (a_asset_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adwfnode_ppwfnodeasset FOREIGN KEY (ad_wf_node_id) REFERENCES ad_wf_node (ad_wf_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_MRP(

name character varying(120) ,
ad_org_id integer NOT NULL ,
c_bpartner_id integer ,
c_orderline_id integer ,
c_order_id integer ,
created timestamp without time zone NOT NULL ,
createdby integer NOT NULL ,
dateconfirm timestamp without time zone ,
datefinishschedule timestamp without time zone ,
dateordered timestamp without time zone NOT NULL ,
datepromised timestamp without time zone NOT NULL ,
datesimulation timestamp without time zone ,
datestart timestamp without time zone ,
datestartschedule timestamp without time zone ,
description character varying(1020) ,
docstatus character varying(2) ,
isactive character(1) NOT NULL ,
isavailable character(1) ,
m_forecastline_id integer ,
m_forecast_id integer ,
m_product_id integer ,
m_requisitionline_id integer ,
m_requisition_id integer ,
m_warehouse_id integer NOT NULL ,
pp_mrp_id integer NOT NULL ,
pp_order_bomline_id integer ,
pp_order_id integer ,
planner_id integer ,
priority character varying(10) ,
qty numeric ,
s_resource_id integer ,
ordertype character varying(3) ,
typemrp character(1) ,
updated timestamp without time zone NOT NULL ,
updatedby numeric(10) NOT NULL ,
value character varying(80) NOT NULL ,
ad_client_id integer NOT NULL ,
version numeric ,
c_project_id integer DEFAULT NULL::numeric ,
c_projectphase_id integer DEFAULT NULL::numeric ,
c_projecttask_id integer DEFAULT NULL::numeric ,
CONSTRAINT pp_mrp_pkey PRIMARY KEY (pp_mrp_id) ,
CONSTRAINT cbpartner_ppmrp FOREIGN KEY (c_bpartner_id) REFERENCES c_bpartner (c_bpartner_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT corderline_ppmrp FOREIGN KEY (c_orderline_id) REFERENCES c_orderline (c_orderline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT corder_ppmrp FOREIGN KEY (c_order_id) REFERENCES c_order (c_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cprojectphase_ppmrp FOREIGN KEY (c_projectphase_id) REFERENCES c_projectphase (c_projectphase_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cproject_ppmrp FOREIGN KEY (c_project_id) REFERENCES c_project (c_project_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cprojecttask_ppmrp FOREIGN KEY (c_projecttask_id) REFERENCES c_projecttask (c_projecttask_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mforecastline_ppmrp FOREIGN KEY (m_forecastline_id) REFERENCES m_forecastline (m_forecastline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mforecast_ppmrp FOREIGN KEY (m_forecast_id) REFERENCES m_forecast (m_forecast_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_ppmrp FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mrequisitionline_ppmrp FOREIGN KEY (m_requisitionline_id) REFERENCES m_requisitionline (m_requisitionline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mrequisition_ppmrp FOREIGN KEY (m_requisition_id) REFERENCES m_requisition (m_requisition_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mwarehouse_ppmrp FOREIGN KEY (m_warehouse_id) REFERENCES m_warehouse (m_warehouse_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT planner_ppmrp FOREIGN KEY (planner_id) REFERENCES ad_user (ad_user_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporderbomline_ppmrp FOREIGN KEY (pp_order_bomline_id) REFERENCES pp_order_bomline (pp_order_bomline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_ppmrp FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT sresource_ppmrp FOREIGN KEY (s_resource_id) REFERENCES s_resource (s_resource_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

---- FUNCTION NECESARIA PARA LA VISTA ---------

CREATE OR REPLACE FUNCTION libertya.documentno(p_pp_mrp_id numeric)
  RETURNS character varying AS
$BODY$
DECLARE
	v_DocumentNo libertya.PP_MRP.Value%TYPE := '';
BEGIN
	-- If NO id return empty string
	IF p_PP_MRP_ID <= 0 THEN
		RETURN '';
	END IF;
	SELECT --ordertype, m_forecast_id, c_order_id, dd_order_id, pp_order_id, m_requisition_id,
	CASE
			WHEN trim(mrp.ordertype) = 'FTC' THEN (SELECT f.Name FROM libertya.M_Forecast f WHERE f.M_Forecast_ID=mrp.M_Forecast_ID)
			WHEN trim(mrp.ordertype) = 'POO' THEN (SELECT co.DocumentNo  FROM libertya.C_Order co WHERE co.C_Order_ID=mrp.C_Order_ID)
			WHEN trim(mrp.ordertype) = 'SOO' THEN (SELECT co.DocumentNo  FROM libertya.C_Order co WHERE co.C_Order_ID=mrp.C_Order_ID)
			WHEN trim(mrp.ordertype) = 'MOP' THEN (SELECT po.DocumentNo FROM libertya.PP_Order po WHERE po.PP_Order_ID=mrp.PP_Order_ID)
			WHEN trim(mrp.ordertype) = 'POR' THEN (SELECT r.DocumentNo  FROM libertya.M_Requisition r WHERE r.M_Requisition_ID=mrp.M_Requisition_ID)
			
	END INTO v_DocumentNo
	FROM libertya.pp_mrp mrp
	WHERE mrp.pp_mrp_id = p_PP_MRP_ID;
	RETURN v_DocumentNo;
END;	
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION libertya.documentno(numeric)
  OWNER TO libertya;

-------------

CREATE VIEW RV_PP_MRP AS
SELECT mrp.pp_mrp_id, mrp.ad_client_id, mrp.ad_org_id, mrp.created, mrp.createdby, mrp.isactive, mrp.isavailable, mrp.updated, mrp.updatedby, pp.ismps, pp.isrequiredmrp, pp.isrequireddrp, p.isbom, p.ispurchased, p.m_product_category_id, p.m_attributesetinstance_id, mrp.name, mrp.description, mrp.c_order_id, mrp.c_orderline_id, mrp.dateordered, mrp.dateconfirm, mrp.datepromised, mrp.datestartschedule, mrp.datefinishschedule, mrp.datestart, mrp.datesimulation, mrp.docstatus, mrp.m_forecast_id, mrp.m_forecastline_id, mrp.value, mrp.m_product_id, mrp.m_requisition_id, mrp.m_requisitionline_id, mrp.m_warehouse_id, mrp.pp_order_id, mrp.pp_order_bomline_id, mrp.qty, mrp.s_resource_id, mrp.planner_id, mrp.priority, mrp.ordertype, mrp.typemrp, p.lowlevel, mrp.c_bpartner_id, mrp.version, documentno((mrp.pp_mrp_id)::numeric) AS documentno, mrp.c_project_id, mrp.c_projectphase_id, mrp.c_projecttask_id FROM ((pp_mrp mrp JOIN m_product p ON ((mrp.m_product_id = p.m_product_id))) LEFT JOIN pp_product_planning pp ON (((pp.m_product_id = mrp.m_product_id) AND (mrp.m_warehouse_id = pp.m_warehouse_id)))) WHERE (mrp.qty <> (0)::numeric) UNION SELECT 0 AS pp_mrp_id, pp.ad_client_id, pp.ad_org_id, pp.created, pp.createdby, pp.isactive, 'Y'::bpchar AS isavailable, pp.updated, pp.updatedby, pp.ismps, pp.isrequiredmrp, pp.isrequireddrp, p.isbom, p.ispurchased, p.m_product_category_id, p.m_attributesetinstance_id, NULL::character varying AS name, NULL::character varying AS description, NULL::numeric AS c_order_id, NULL::numeric AS c_orderline_id, now() AS dateordered, now() AS dateconfirm, now() AS datepromised, now() AS datestartschedule, now() AS datefinishschedule, now() AS datestart, now() AS datesimulation, 'CO'::character varying AS docstatus, NULL::numeric AS m_forecast_id, NULL::numeric AS m_forecastline_id, NULL::character varying AS value, pp.m_product_id, NULL::numeric AS m_requisition_id, NULL::numeric AS m_requisitionline_id, pp.m_warehouse_id, NULL::numeric AS pp_order_id, NULL::numeric AS pp_order_bomline_id, (pp.safetystock - bomqtyonhand((pp.m_product_id)::numeric, (pp.m_warehouse_id)::numeric, (0)::numeric)) AS qty, pp.s_resource_id, NULL::numeric AS planner_id, NULL::character varying AS priority, 'STK'::character varying AS ordertype, 'D'::bpchar AS typemrp, p.lowlevel, NULL::numeric AS c_bpartner_id, NULL::numeric AS version, 'Safety Strock'::character varying AS documentno, NULL::numeric AS c_project_id, NULL::numeric AS c_projectphase_id, NULL::numeric AS c_projecttask_id FROM (pp_product_planning pp JOIN m_product p ON ((pp.m_product_id = p.m_product_id))) WHERE (bomqtyonhand((pp.m_product_id)::numeric, (pp.m_warehouse_id)::numeric, (0)::numeric) < pp.safetystock);

CREATE TABLE PP_Order_Cost(

ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
ad_workflow_id integer ,
c_acctschema_id integer NOT NULL ,
costingmethod character(1) DEFAULT 'x'::bpchar ,
created timestamp without time zone NOT NULL ,
createdby integer NOT NULL ,
cumulatedamt numeric ,
cumulatedamtpost numeric ,
cumulatedqty numeric ,
cumulatedqtypost numeric ,
currentcostprice numeric ,
currentcostpricell numeric ,
currentqty numeric ,
isactive character(1) NOT NULL ,
m_attributesetinstance_id integer ,
m_costelement_id integer ,
m_costtype_id integer NOT NULL ,
m_product_id integer NOT NULL ,
pp_order_cost_id integer NOT NULL ,
pp_order_id integer NOT NULL ,
updated timestamp without time zone NOT NULL ,
updatedby integer NOT NULL ,
CONSTRAINT pp_order_cost_pkey PRIMARY KEY (pp_order_cost_id) ,
CONSTRAINT adworkflow_ppordercost FOREIGN KEY (ad_workflow_id) REFERENCES ad_workflow (ad_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cacctschema_ppordercost FOREIGN KEY (c_acctschema_id) REFERENCES c_acctschema (c_acctschema_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mattributesetinstance_pporderc FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mcostelement_ppordercost FOREIGN KEY (m_costelement_id) REFERENCES m_costelement (m_costelement_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mcosttype_ppordercost FOREIGN KEY (m_costtype_id) REFERENCES m_costtype (m_costtype_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_ppordercost FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_ppordercost FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE PP_Cost_Collector(

m_product_id integer NOT NULL ,
ad_orgtrx_id integer ,
ad_org_id integer NOT NULL ,
ad_user_id integer ,
c_activity_id integer ,
c_campaign_id integer ,
c_doctypetarget_id integer NOT NULL ,
c_doctype_id integer NOT NULL ,
c_project_id integer ,
c_uom_id integer ,
created timestamp without time zone NOT NULL ,
createdby integer NOT NULL ,
dateacct timestamp without time zone NOT NULL ,
description character varying(255) ,
docaction character(2) DEFAULT 'CO'::bpchar ,
docstatus character(2) DEFAULT 'DR'::bpchar ,
durationreal numeric ,
isactive character(1) NOT NULL ,
isbatchtime character(1) DEFAULT 'N'::bpchar ,
m_attributesetinstance_id integer ,
m_locator_id integer NOT NULL ,
m_warehouse_id integer NOT NULL ,
movementdate timestamp without time zone NOT NULL ,
movementqty numeric NOT NULL DEFAULT 0 ,
pp_cost_collector_id integer NOT NULL ,
pp_order_bomline_id integer ,
pp_order_id integer NOT NULL ,
pp_order_node_id integer ,
pp_order_workflow_id integer ,
posted character(1) NOT NULL ,
processed character(1) NOT NULL ,
processing character(1) ,
qtyreject numeric DEFAULT 0 ,
s_resource_id integer NOT NULL ,
scrappedqty numeric DEFAULT 0 ,
setuptimereal numeric DEFAULT 0 ,
updated timestamp without time zone NOT NULL ,
updatedby integer NOT NULL ,
user1_id integer ,
ad_client_id integer NOT NULL ,
user2_id integer ,
reversal_id integer ,
costcollectortype character varying(3) NOT NULL ,
issubcontracting character(1) ,
documentno character varying(30) NOT NULL ,
processedon numeric ,
CONSTRAINT pp_cost_collector_pkey PRIMARY KEY (pp_cost_collector_id) ,
CONSTRAINT adorgtrx_ppcostcollector FOREIGN KEY (ad_orgtrx_id) REFERENCES ad_org (ad_org_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT aduser_ppcostcollector FOREIGN KEY (ad_user_id) REFERENCES ad_user (ad_user_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cactivity_ppcostcollector FOREIGN KEY (c_activity_id) REFERENCES c_activity (c_activity_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ccampaign_ppcostcollector FOREIGN KEY (c_campaign_id) REFERENCES c_campaign (c_campaign_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cdoctype_ppcostcollector FOREIGN KEY (c_doctype_id) REFERENCES c_doctype (c_doctype_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cdoctypetarget_ppcostcollector FOREIGN KEY (c_doctypetarget_id) REFERENCES c_doctype (c_doctype_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cproject_ppcostcollector FOREIGN KEY (c_project_id) REFERENCES c_project (c_project_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cuom_ppcostcollector FOREIGN KEY (c_uom_id) REFERENCES c_uom (c_uom_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mattributesetinstance_ppcostco FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mlocator_ppcostcollector FOREIGN KEY (m_locator_id) REFERENCES m_locator (m_locator_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_ppcostcollector FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mwarehouse_ppcostcollector FOREIGN KEY (m_warehouse_id) REFERENCES m_warehouse (m_warehouse_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporderbomline_ppcostcollector FOREIGN KEY (pp_order_bomline_id) REFERENCES pp_order_bomline (pp_order_bomline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppordernode_ppcostcollector FOREIGN KEY (pp_order_node_id) REFERENCES pp_order_node (pp_order_node_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporder_ppcostcollector FOREIGN KEY (pp_order_id) REFERENCES pp_order (pp_order_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT pporderworkflow_ppcostcollecto FOREIGN KEY (pp_order_workflow_id) REFERENCES pp_order_workflow (pp_order_workflow_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT reversal_ppcostcollector FOREIGN KEY (reversal_id) REFERENCES pp_cost_collector (pp_cost_collector_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT sresource_ppcostcollector FOREIGN KEY (s_resource_id) REFERENCES s_resource (s_resource_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT user1_ppcostcollector FOREIGN KEY (user1_id) REFERENCES ad_user (ad_user_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT user2_ppcostcollector FOREIGN KEY (user2_id) REFERENCES ad_user (ad_user_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE M_Costdetail(

m_costdetail_id integer NOT NULL ,
ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
c_acctschema_id integer NOT NULL ,
m_product_id integer NOT NULL ,
m_attributesetinstance_id integer NOT NULL ,
isactive character(1) NOT NULL DEFAULT 'Y'::bpchar ,
created timestamp without time zone NOT NULL DEFAULT now() ,
createdby integer NOT NULL ,
updated timestamp without time zone NOT NULL DEFAULT now() ,
updatedby integer NOT NULL ,
m_costelement_id integer ,
c_orderline_id integer ,
m_inoutline_id integer ,
c_invoiceline_id integer ,
m_movementline_id integer ,
m_inventoryline_id integer ,
m_productionline_id integer ,
c_projectissue_id integer ,
issotrx character(1) NOT NULL DEFAULT 'Y'::bpchar ,
amt numeric NOT NULL DEFAULT 0 ,
qty numeric NOT NULL DEFAULT 0 ,
deltaamt numeric DEFAULT 0 ,
deltaqty numeric DEFAULT 0 ,
description character varying(255) ,
processed character(1) NOT NULL DEFAULT 'N'::bpchar ,
pp_cost_collector_id integer ,
currentcostprice numeric ,
currentqty numeric ,
cumulatedamt numeric ,
cumulatedqty numeric ,
m_costtype_id integer DEFAULT NULL::numeric ,
dateacct timestamp without time zone ,
m_transaction_id integer DEFAULT NULL::numeric ,
costingmethod character(1) ,
c_landedcostallocation_id integer DEFAULT NULL::numeric ,
currentcostpricell numeric ,
costadjustment numeric ,
costadjustmentdate timestamp without time zone ,
costamt numeric ,
isreversal character(1) DEFAULT 'N'::bpchar ,
processing character(1) DEFAULT 'N'::bpchar ,
amtll numeric ,
costadjustmentll numeric ,
costadjustmentdatell timestamp without time zone ,
costamtll numeric ,
cumulatedamtll numeric ,
seqno integer DEFAULT NULL::numeric ,
m_warehouse_id integer ,
ad_componentobjectuid character varying(100),
CONSTRAINT m_costdetail_pkey PRIMARY KEY (m_costdetail_id) ,
CONSTRAINT adclient_mcostdetail FOREIGN KEY (ad_client_id) REFERENCES ad_client (ad_client_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT adorg_mcostdetail FOREIGN KEY (ad_org_id) REFERENCES ad_org (ad_org_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cacctschema_mcostdetail FOREIGN KEY (c_acctschema_id) REFERENCES c_acctschema (c_acctschema_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT corderline_mcostdetail FOREIGN KEY (c_orderline_id) REFERENCES c_orderline (c_orderline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT cprojectissue_mcostdetail FOREIGN KEY (c_projectissue_id) REFERENCES c_projectissue (c_projectissue_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT masi_mcostdetail FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mcostelement_mcostdetail FOREIGN KEY (m_costelement_id) REFERENCES m_costelement (m_costelement_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mcosttype_mcostdetail FOREIGN KEY (m_costtype_id) REFERENCES m_costtype (m_costtype_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT minoutline_mcostdetail FOREIGN KEY (m_inoutline_id) REFERENCES m_inoutline (m_inoutline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT minventoryline_mcostdetail FOREIGN KEY (m_inventoryline_id) REFERENCES m_inventoryline (m_inventoryline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT minvoiceline_mcostdetail FOREIGN KEY (c_invoiceline_id) REFERENCES c_invoiceline (c_invoiceline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mmovementline_mcostdetail FOREIGN KEY (m_movementline_id) REFERENCES m_movementline (m_movementline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproductionline_mcostdetail FOREIGN KEY (m_productionline_id) REFERENCES m_productionline (m_productionline_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mproduct_mcostdetail FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT mwarehouse_mcostdetail FOREIGN KEY (m_warehouse_id) REFERENCES m_warehouse (m_warehouse_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  ,
CONSTRAINT ppcostcollector_mcostdetail FOREIGN KEY (pp_cost_collector_id) REFERENCES pp_cost_collector (pp_cost_collector_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE TABLE M_Costqueue(

m_costqueue_id integer NOT NULL ,
ad_client_id integer NOT NULL ,
ad_org_id integer NOT NULL ,
isactive character(1) NOT NULL DEFAULT 'Y'::bpchar ,
created timestamp without time zone NOT NULL DEFAULT now() ,
createdby integer NOT NULL ,
updated timestamp without time zone NOT NULL DEFAULT now() ,
updatedby integer NOT NULL ,
m_costtype_id integer NOT NULL ,
c_acctschema_id integer NOT NULL ,
m_product_id integer NOT NULL ,
m_attributesetinstance_id integer NOT NULL ,
m_costelement_id integer NOT NULL ,
currentcostprice numeric NOT NULL DEFAULT 0 ,
currentqty numeric NOT NULL DEFAULT 0 ,
dateacct timestamp without time zone ,
ad_componentobjectuid character varying(100),
CONSTRAINT m_costqueue_pkey PRIMARY KEY (m_costqueue_id) ,
CONSTRAINT cacctschema_mcostqueue FOREIGN KEY (c_acctschema_id) REFERENCES c_acctschema (c_acctschema_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE  ,
CONSTRAINT masi_mcostqueue FOREIGN KEY (m_attributesetinstance_id) REFERENCES m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE  ,
CONSTRAINT mcostelement_mcostqueue FOREIGN KEY (m_costelement_id) REFERENCES m_costelement (m_costelement_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE  ,
CONSTRAINT mcosttype_mcostqueue FOREIGN KEY (m_costtype_id) REFERENCES m_costtype (m_costtype_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE CASCADE  ,
CONSTRAINT mproduct_mcostqueue FOREIGN KEY (m_product_id) REFERENCES m_product (m_product_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION  );

CREATE OR REPLACE VIEW libertya.rv_pp_order_storage AS 
 SELECT obl.ad_client_id, obl.ad_org_id, obl.createdby, obl.updatedby, obl.updated, obl.created, obl.isactive, obl.pp_order_bom_id, obl.pp_order_bomline_id, obl.pp_order_id, obl.iscritical, obl.m_product_id, ( SELECT p.name
           FROM libertya.m_product p
          WHERE p.m_product_id = o.m_product_id) AS name, obl.c_uom_id, s.qtyonhand, round(obl.qtyrequired, 4) AS qtyrequired, 
        CASE
            WHEN o.qtybatchs = 0::numeric THEN 1::numeric
            ELSE round(obl.qtyrequired / o.qtybatchs, 4)
        END AS qtybatchsize, round(libertya.bomqtyreserved(obl.m_product_id::numeric, obl.m_warehouse_id::numeric, 0::numeric), 4) AS qtyreserved, round(libertya.bomqtyavailable(obl.m_product_id::numeric, obl.m_warehouse_id::numeric, 0::numeric), 4) AS qtyavailable, obl.m_warehouse_id, obl.qtybom, obl.isqtypercentage, round(obl.qtybatch, 4) AS qtybatch, obl.m_attributesetinstance_id, l.m_locator_id, l.x, l.y, l.z
   FROM libertya.pp_order_bomline obl
   JOIN libertya.pp_order o ON o.pp_order_id = obl.pp_order_id
   LEFT JOIN libertya.m_storage s ON s.m_product_id = obl.m_product_id AND s.qtyonhand <> 0::numeric AND obl.m_warehouse_id = (( SELECT ld.m_warehouse_id
   FROM libertya.m_locator ld
  WHERE s.m_locator_id = ld.m_locator_id))
   LEFT JOIN libertya.m_locator l ON l.m_locator_id = s.m_locator_id
  ORDER BY obl.m_product_id;

ALTER TABLE libertya.rv_pp_order_storage
  OWNER TO libertya;

CREATE TABLE libertya.pp_cost_collectorma
(
  ad_client_id integer NOT NULL,
  ad_org_id integer NOT NULL,
  created timestamp without time zone NOT NULL,
  createdby integer NOT NULL,
  isactive character(1) NOT NULL,
  m_attributesetinstance_id integer NOT NULL,
  movementqty numeric NOT NULL,
  pp_cost_collectorma_id integer NOT NULL,
  pp_cost_collector_id integer NOT NULL,
  updated timestamp without time zone NOT NULL,
  updatedby integer NOT NULL,
  CONSTRAINT pp_cost_collectorma_pkey PRIMARY KEY (pp_cost_collectorma_id),
  CONSTRAINT mattributesetinstance_ppcostma FOREIGN KEY (m_attributesetinstance_id)
      REFERENCES libertya.m_attributesetinstance (m_attributesetinstance_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY DEFERRED,
  CONSTRAINT ppcostcollector_ppccma FOREIGN KEY (pp_cost_collector_id)
      REFERENCES libertya.pp_cost_collector (pp_cost_collector_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY DEFERRED,
  CONSTRAINT pp_ccma_isactive_check CHECK (isactive = ANY (ARRAY['Y'::bpchar, 'N'::bpchar])),
  CONSTRAINT pp_cost_collectorma_isactive_check CHECK (isactive = ANY (ARRAY['Y'::bpchar, 'N'::bpchar]))
);

ALTER TABLE libertya.pp_cost_collectorma
  OWNER TO libertya;

----------------------------------------------------------------------
---------- Nuevas columnas en tablas y/o vistas 
----------------------------------------------------------------------

ALTER TABLE M_Product_Category_Acct ADD COLUMN CostingLevel character(1);
ALTER TABLE M_RequisitionLine ADD COLUMN C_BPartner_ID integer;
ALTER TABLE M_ForecastLine ADD COLUMN DatePromised timestamp without time zone;
ALTER TABLE M_RequisitionLine ADD COLUMN C_UOM_ID integer;
ALTER TABLE C_LandedCostAllocation ADD COLUMN M_AttributeSetInstance_ID integer;
ALTER TABLE M_Cost ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE M_MatchPO ADD COLUMN M_AttributeSetInstance_ID integer;
ALTER TABLE M_Cost ADD COLUMN CurrentCostPriceLL numeric;
ALTER TABLE M_Cost ADD COLUMN CurrentQty numeric;
ALTER TABLE M_MatchPO ADD COLUMN Description character varying(255);
ALTER TABLE M_Requisition ADD COLUMN C_DocType_ID integer;
ALTER TABLE M_ForecastLine ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE C_LandedCostAllocation ADD COLUMN C_InvoiceLine_ID integer;
ALTER TABLE M_Cost ADD COLUMN CumulatedAmtLL numeric;
ALTER TABLE M_RequisitionLine ADD COLUMN M_AttributeSetInstance_ID integer;
ALTER TABLE C_LandedCostAllocation ADD COLUMN M_InOutLine_ID integer;
ALTER TABLE M_Forecast ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE C_AcctSchema ADD COLUMN CostingLevel character(1);
ALTER TABLE C_LandedCostAllocation ADD COLUMN C_Landedcostallocation_ID integer;
ALTER TABLE M_RequisitionLine ADD COLUMN C_Charge_ID integer;
ALTER TABLE M_CostElement ADD COLUMN IsDefault character(1);
ALTER TABLE M_Cost ADD COLUMN FutureCostPriceLL numeric;
ALTER TABLE M_Requisition ADD COLUMN DateDoc timestamp without time zone;
ALTER TABLE C_LandedCostAllocation ADD COLUMN base numeric;
ALTER TABLE S_Resource ADD COLUMN PlanningHorizon numeric(10);
ALTER TABLE M_ForecastLine ADD COLUMN M_Warehouse_ID integer;
ALTER TABLE M_Cost ADD COLUMN IsCostFrozen character(1);
ALTER TABLE M_Cost ADD COLUMN CumulatedAmt numeric;
ALTER TABLE M_Cost ADD COLUMN Percent numeric(10);
ALTER TABLE M_ForecastLine ADD COLUMN SalesRep_ID integer;
ALTER TABLE C_AcctSchema ADD COLUMN IsExplicitCostAdjustment character(1);
ALTER TABLE M_Product_Category ADD COLUMN PP_Tolerance numeric(6,3);
ALTER TABLE M_Cost ADD COLUMN M_AttributeSetInstance_ID integer;
ALTER TABLE C_LandedCostAllocation ADD COLUMN IsActive character(1);
ALTER TABLE C_LandedCostAllocation ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE C_OrderLine ADD COLUMN PP_Cost_Collector_ID integer;
ALTER TABLE M_Transaction ADD COLUMN PP_Cost_Collector_ID integer;
ALTER TABLE M_Cost ADD COLUMN M_Warehouse_ID integer;
ALTER TABLE C_LandedCostAllocation ADD COLUMN M_CostElement_ID integer;
ALTER TABLE M_CostElement ADD COLUMN AD_ComponentObjectUID character varying(100);
ALTER TABLE M_CostType ADD COLUMN CostingMethod character(1);
ALTER TABLE M_Product ADD COLUMN PP_Tolerance numeric(6,3);
ALTER TABLE M_Cost ADD COLUMN CumulatedQty numeric;
ALTER TABLE M_InOut ADD COLUMN ad_componentobjectuid character varying(100);
ALTER TABLE M_InOutLine ADD COLUMN ad_componentobjectuid character varying(100);

----------------------------------------------------------------------
---------- Modificación de tablas y/o vistas
----------------------------------------------------------------------

ALTER TABLE M_ForecastLine ALTER COLUMN DatePromised TYPE timestamp without time zone;
ALTER TABLE S_Resource ALTER COLUMN PlanningHorizon TYPE numeric(10);
ALTER TABLE M_CostType ALTER COLUMN CostingMethod TYPE character(1);
ALTER TABLE M_Cost ALTER COLUMN M_AttributeSetInstance_ID DROP NOT NULL ;
ALTER TABLE C_AcctSchema ALTER COLUMN CostingLevel TYPE character(1);
ALTER TABLE M_Product_Category_Acct ALTER COLUMN CostingLevel TYPE character(1);
ALTER TABLE PP_Order_Cost ALTER COLUMN CostingMethod TYPE character(1);

----------------------------------------------------------------------
---------- Funciones (Agregar manualmente al XML) --------------------
----------------------------------------------------------------------

CREATE OR REPLACE FUNCTION libertya.nextid(IN p_ad_sequence_id integer, IN p_system character varying, OUT o_nextid integer)
  RETURNS integer AS
$BODY$
BEGIN
    IF (p_System = 'Y') THEN
	RAISE NOTICE 'system';
        SELECT CurrentNextSys
            INTO o_NextID
        FROM libertya.AD_Sequence
        WHERE AD_Sequence_ID=p_AD_Sequence_ID;
        --
        UPDATE libertya.AD_Sequence
          SET CurrentNextSys = CurrentNextSys + IncrementNo
        WHERE AD_Sequence_ID=p_AD_Sequence_ID;
    ELSE
        SELECT CurrentNext
            INTO o_NextID
        FROM libertya.AD_Sequence
        WHERE AD_Sequence_ID=p_AD_Sequence_ID;
        --
        UPDATE libertya.AD_Sequence
          SET CurrentNext = CurrentNext + IncrementNo
        WHERE AD_Sequence_ID=p_AD_Sequence_ID;
    END IF;
    --
EXCEPTION
    WHEN  OTHERS THEN
    	RAISE NOTICE '%',SQLERRM;
END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION libertya.nextid(integer, character varying)
  OWNER TO libertya;

---

CREATE OR REPLACE FUNCTION libertya.nextidfunc(p_ad_sequence_id integer, p_system character varying)
  RETURNS integer AS
$BODY$
DECLARE
          o_NextIDFunc INTEGER;
	  dummy INTEGER;
BEGIN
    o_NextIDFunc := nextid(p_AD_Sequence_ID, p_System);
    RETURN o_NextIDFunc;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION libertya.nextidfunc(integer, character varying)
  OWNER TO libertya;
