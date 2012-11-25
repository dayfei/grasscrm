CREATE SEQUENCE hibernate_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 205
  CACHE 1;
ALTER TABLE hibernate_sequence
  OWNER TO postgres;

CREATE TABLE accounttype
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT accounttype_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE accounttype
  OWNER TO postgres;
  
CREATE TABLE calldirection
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT calldirection_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE calldirection
  OWNER TO postgres;
  
CREATE TABLE callstatus
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT callstatus_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE callstatus
  OWNER TO postgres;

CREATE TABLE campaignstatus
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT campaignstatus_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE campaignstatus
  OWNER TO postgres;
  
CREATE TABLE campaigntype
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT campaigntype_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE campaigntype
  OWNER TO postgres;
  
CREATE TABLE caseorigin
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT caseorigin_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE caseorigin
  OWNER TO postgres;
  
CREATE TABLE casepriority
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT casepriority_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE casepriority
  OWNER TO postgres;
  
CREATE TABLE casereason
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT casereason_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE casereason
  OWNER TO postgres;

CREATE TABLE casestatus
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT casestatus_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE casestatus
  OWNER TO postgres;

CREATE TABLE casetype
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT casetype_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE casetype
  OWNER TO postgres;

CREATE TABLE currency
(
  id integer NOT NULL,
  name character varying(255) NOT NULL,
  code character varying(255),
  rate double precision,
  symbol character varying(255),
  status character varying(255),
  CONSTRAINT currency_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE currency
  OWNER TO postgres;

CREATE TABLE documentcategory
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT documentcategory_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE documentcategory
  OWNER TO postgres;
  
CREATE TABLE documentstatus
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT documentstatus_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE documentstatus
  OWNER TO postgres;

CREATE TABLE documentsubcategory
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT documentsubcategory_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE documentsubcategory
  OWNER TO postgres;

CREATE TABLE documenttype
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT documenttype_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE documenttype
  OWNER TO postgres;
  
CREATE TABLE industry
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT industry_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE industry
  OWNER TO postgres;
  
CREATE TABLE leadsource
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT leadsource_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE leadsource
  OWNER TO postgres;
  
CREATE TABLE leadstatus
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT leadstatus_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE leadstatus
  OWNER TO postgres;

CREATE TABLE meetingstatus
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT meetingstatus_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE meetingstatus
  OWNER TO postgres;
  
CREATE TABLE opportunitytype
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT opportunitytype_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE opportunitytype
  OWNER TO postgres;

CREATE TABLE permission
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  url character varying(255),
  sequence integer,
  CONSTRAINT permission_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE permission
  OWNER TO postgres;

CREATE TABLE reminderoption
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT reminderoption_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE reminderoption
  OWNER TO postgres;
  
CREATE TABLE userstatus
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT userstatus_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE userstatus
  OWNER TO postgres; 
  
CREATE TABLE users
(
  id integer NOT NULL,
  name character varying(255) NOT NULL,
  first_name character varying(255),
  last_name character varying(255),
  status integer,
  password character varying(255),
  title character varying(255),
  email character varying(255),
  mobile character varying(255),
  phone character varying(255),
  fax character varying(255),
  department character varying(255),
  report_to integer,
  mail_street character varying(255),
  mail_city character varying(255),
  mail_state character varying(255),
  mail_postal_code character varying(255),
  mail_country character varying(255),
  other_street character varying(255),
  other_state character varying(255),
  other_postal_code character varying(255),
  other_country character varying(255),
  age integer,
  smtp_username character varying(255),
  smtp_password character varying(255),
  description character varying(255),
  created_by integer,
  updated_by integer,
  created_on timestamp without time zone,
  updated_on timestamp without time zone,
  CONSTRAINT users_pkey PRIMARY KEY (id ),
  CONSTRAINT fk6a68e084c37dd59 FOREIGN KEY (created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk6a68e085e111c0f FOREIGN KEY (status)
      REFERENCES userstatus (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk6a68e08e584d5b1 FOREIGN KEY (report_to)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk6a68e08e8f7c6a6 FOREIGN KEY (updated_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;  
  
CREATE TABLE role
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  created_by integer,
  updated_by integer,
  created_on timestamp without time zone,
  updated_on timestamp without time zone,
  CONSTRAINT role_pkey PRIMARY KEY (id ),
  CONSTRAINT fk3580764c37dd59 FOREIGN KEY (created_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk358076e8f7c6a6 FOREIGN KEY (updated_by)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE role
  OWNER TO postgres;
  
CREATE TABLE role_permission
(
  permission_id integer NOT NULL,
  role_id integer NOT NULL,
  CONSTRAINT role_permission_pkey PRIMARY KEY (role_id , permission_id ),
  CONSTRAINT fkbd40d5384ca45e7a FOREIGN KEY (role_id)
      REFERENCES role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkbd40d538c03253da FOREIGN KEY (permission_id)
      REFERENCES permission (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE role_permission
  OWNER TO postgres;

CREATE TABLE salesstage
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT salesstage_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE salesstage
  OWNER TO postgres;

CREATE TABLE targetlisttype
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT targetlisttype_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE targetlisttype
  OWNER TO postgres;

CREATE TABLE taskpriority
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT taskpriority_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE taskpriority
  OWNER TO postgres;
  
CREATE TABLE taskstatus
(
  id integer NOT NULL,
  name character varying(50) NOT NULL,
  sequence integer,
  CONSTRAINT taskstatus_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE taskstatus
  OWNER TO postgres;
    
CREATE TABLE users_role
(
  user_id integer NOT NULL,
  role_id integer NOT NULL,
  CONSTRAINT users_role_pkey PRIMARY KEY (user_id , role_id ),
  CONSTRAINT fk9459304d4ca45e7a FOREIGN KEY (role_id)
      REFERENCES role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk9459304df1cf225a FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users_role
  OWNER TO postgres;
   
  
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Analyst', 2);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Competitor', 3);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Customer', 4);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Integrator', 5);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Investor', 6);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Partner', 7);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Press', 8);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Prospect', 9);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Reseller', 10);
INSERT INTO accounttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Other', 11);

INSERT INTO calldirection(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Inbound', 1);
INSERT INTO calldirection(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Outbound', 2);

INSERT INTO callstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Planned', 1);
INSERT INTO callstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Held', 2);
INSERT INTO callstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Not Held', 3);

INSERT INTO campaignstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO campaignstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Planning', 2);
INSERT INTO campaignstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Active', 3);
INSERT INTO campaignstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Inactive', 4);
INSERT INTO campaignstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Complete', 5);
INSERT INTO campaignstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'In Queue', 6);
INSERT INTO campaignstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Sending', 7);

INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Telesales', 2);
INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Mail', 3);
INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Email', 4);
INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Print', 5);
INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Web', 6);
INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Radio', 7);
INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Television', 8);
INSERT INTO campaigntype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'NewsLetter', 9);

INSERT INTO caseorigin(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO caseorigin(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Email', 2);
INSERT INTO caseorigin(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Phone', 3);
INSERT INTO caseorigin(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Web', 4);

INSERT INTO casepriority(id, name, sequence) VALUES (nextval('hibernate_sequence'),'High', 1);
INSERT INTO casepriority(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Medium', 2);
INSERT INTO casepriority(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Low', 3);

INSERT INTO casereason(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO casereason(id, name, sequence) VALUES (nextval('hibernate_sequence'),'User did not attend training', 2);
INSERT INTO casereason(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Complex functionality', 3);
INSERT INTO casereason(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Existing problem', 4);
INSERT INTO casereason(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Instructions not clear', 5);
INSERT INTO casereason(id, name, sequence) VALUES (nextval('hibernate_sequence'),'New problem',6);

INSERT INTO casestatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'New', 1);
INSERT INTO casestatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Assigned', 2);
INSERT INTO casestatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Closed', 3);
INSERT INTO casestatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Pending Input', 4);
INSERT INTO casestatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Rejected', 5);
INSERT INTO casestatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Duplicate', 6);

INSERT INTO casetype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Administration', 1);
INSERT INTO casetype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Product', 2);
INSERT INTO casetype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'User', 3);

INSERT INTO currency(id, name, code, symbol) VALUES (nextval('hibernate_sequence'),'US Dollars','USD', '$');

INSERT INTO documentcategory(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO documentcategory(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Marketing', 2);
INSERT INTO documentcategory(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Knowledge Base', 3);
INSERT INTO documentcategory(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Sales', 4);

INSERT INTO documentstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Active', 1);
INSERT INTO documentstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Draft', 2);
INSERT INTO documentstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'FAQ', 3);
INSERT INTO documentstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Expired', 4);
INSERT INTO documentstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Under Review', 5);
INSERT INTO documentstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Pending', 6);

INSERT INTO documentsubcategory(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO documentsubcategory(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Marketing Collateral', 2);
INSERT INTO documentsubcategory(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Product Brochures', 3);
INSERT INTO documentsubcategory(id, name, sequence) VALUES (nextval('hibernate_sequence'),'FAQ', 4);

INSERT INTO documenttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO documenttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Mail Merge', 2);
INSERT INTO documenttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'License Agreement', 3);

INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Agriculture', 2);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Apparel', 3);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Banking', 4);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Biotechnology', 5);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Chemicals', 6);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Communications', 7);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Construction', 8);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Consulting', 9);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Education', 10);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Electronics', 11);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Energy', 12);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Engineering', 13);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Entertainment', 14);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Environmental', 15);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Finance', 16);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Government', 17);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Healthcare', 18);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Hospitality', 19);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Insurance', 20);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Machinery', 21);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Manufacturing', 22);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Media', 23);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Not For Profit', 24);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Recreation', 25);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Retail', 26);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Shipping', 27);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Technology', 28);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Telecommunications', 29);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Transportation', 30);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Utilities', 31);
INSERT INTO industry(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Other', 32);

INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Cold Call', 2);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Existing Customer', 3);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Self Generated', 4);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Employee', 5);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Partner', 6);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Public Relations', 7);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Direct Mail', 8);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Conference', 9);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Trade Show', 10);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Web Site', 11);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Word of mouth', 12);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Email', 13);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Campaign', 14);
INSERT INTO leadsource(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Other', 15);

INSERT INTO leadstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'New', 1);
INSERT INTO leadstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Assigned', 2);
INSERT INTO leadstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'In Process', 3);
INSERT INTO leadstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Converted', 4);
INSERT INTO leadstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Recycled', 5);
INSERT INTO leadstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Dead', 6);

INSERT INTO meetingstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Planned', 1);
INSERT INTO meetingstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Held', 2);
INSERT INTO meetingstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Not Held', 3);

INSERT INTO opportunitytype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO opportunitytype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Existing Business', 2);
INSERT INTO opportunitytype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'New Business', 3);

INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'1 minute prior', 1);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'5 minutes prior', 2);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'10 minutes prior', 3);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'15 minutes prior', 4);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'30 minutes prior', 5);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'1 hour prior', 6);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'2 hours prior', 7);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'3 hours prior', 8);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'5 hours prior', 9);
INSERT INTO reminderoption(id, name, sequence) VALUES (nextval('hibernate_sequence'),'1 day prior', 10);

INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'--None--', 1);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Prospecting', 2);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Qualification', 3);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Needs Analysis', 4);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Value Proposition', 5);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Id. Decision Makers', 6);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Perception Analysis', 7);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Proposal/Price Quote ', 8);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Negotiation/Review', 9);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Closed Won', 10);
INSERT INTO salesstage(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Closed Lost', 11);

INSERT INTO taskpriority(id, name, sequence) VALUES (nextval('hibernate_sequence'),'High', 1);
INSERT INTO taskpriority(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Medium', 2);
INSERT INTO taskpriority(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Low', 3);

INSERT INTO taskstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Not Started', 1);
INSERT INTO taskstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'In Progress', 2);
INSERT INTO taskstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Completed', 3);
INSERT INTO taskstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Pending Input', 4);
INSERT INTO taskstatus(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Deferred', 5);

INSERT INTO targetlisttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Default', 1);
INSERT INTO targetlisttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Seed', 2);
INSERT INTO targetlisttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Suppression list - Ey Domain', 3);
INSERT INTO targetlisttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Suppression list - Ey Email Address', 4);
INSERT INTO targetlisttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Suppression list - Ey ID', 5);
INSERT INTO targetlisttype(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Test', 6);

INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Account', '/jsp/crm/listAccount.jsp,/jsp/crm/editAccount.jsp,/jsp/crm/deleteAccount.action,/jsp/crm/saveAccount.action,/jsp/crm/listAccountFull.action',1);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Call', '/jsp/crm/listCall.jsp,/jsp/crm/editCall.jsp,/jsp/crm/deleteCall.action,/jsp/crm/saveCall.action',2);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Campaign', '/jsp/crm/listCampaign.jsp,/jsp/crm/editCampaign.jsp,/jsp/crm/deleteCampaign.action,/jsp/crm/saveCampaign.action',3);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Case', '/jsp/crm/listCase.jsp,/jsp/crm/editCase.jsp,/jsp/crm/deleteCase.action,/jsp/crm/saveCase.action',4);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Contact', '/jsp/crm/listContact.jsp,/jsp/crm/editContact.jsp,/jsp/crm/deleteContact.action,/jsp/crm/saveContact.action',5);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Document', '/jsp/crm/listDocument.jsp,/jsp/crm/editDocument.jsp,/jsp/crm/deleteDocument.action,/jsp/crm/saveDocument.action',6);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Lead', '/jsp/crm/listLead.jsp,/jsp/crm/editLead.jsp,/jsp/crm/deleteLead.action,/jsp/crm/saveLead.action',7);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Meeting', '/jsp/crm/listMeeting.jsp,/jsp/crm/editMeeting.jsp,/jsp/crm/deleteMeeting.action,/jsp/crm/saveMeeting.action',8);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Opportunity', '/jsp/crm/listOpportunity.jsp,/jsp/crm/editOpportunity.jsp,/jsp/crm/deleteOpportunity.action,/jsp/crm/saveOpportunity.action',9);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Target', '/jsp/crm/listTarget.jsp,/jsp/crm/editTarget.jsp,/jsp/crm/deleteTarget.action,/jsp/crm/saveTarget.action',10);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'TargetList', '/jsp/crm/listTargetList.jsp,/jsp/crm/editTargetList.jsp,/jsp/crm/deleteTargetList.action,/jsp/crm/saveTargetList.action',11);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Task', '/jsp/crm/listTask.jsp,/jsp/crm/editTask.jsp,/jsp/crm/deleteTask.action,/jsp/crm/saveTask.action',12);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'User', '/jsp/crm/listUser.jsp,/jsp/system/editUser.jsp,/jsp/system/deleteUser.action,/jsp/system/saveUser.action',13);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Role', '/jsp/system/listRole.jsp,/jsp/system/editRole.jsp,/jsp/system/deleteRole.action,/jsp/system/saveRole.action',14);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Permission', '/jsp/system/listPermission.jsp,/jsp/system/deletePermission.action,/jsp/system/savePermission.action',15);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Account Type', '/jsp/system/listAccountType.jsp,/jsp/system/deleteAccountType.action,/jsp/system/saveAccountType.action',16);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Call Status', '/jsp/system/listCallStatus.jsp,/jsp/system/deleteCallStatus.action,/jsp/system/saveCallStatus.action',17);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Call Direction', '/jsp/system/listCallDirection.jsp,/jsp/system/deleteCallDirection.action,/jsp/system/saveCallDirection.action',18);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Campaign Type', '/jsp/system/listCampaignType.jsp,/jsp/system/deleteCampaignType.action,/jsp/system/saveCampaignType.action',19);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Campaign Status', '/jsp/system/listCampaignStatus.jsp,/jsp/system/deleteCampaignStatus.action,/jsp/system/saveCampaignStatus.action',20);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Case Origin', '/jsp/system/listCaseOrigin.jsp,/jsp/system/deleteCaseOrigin.action,/jsp/system/saveCaseOrigin.action',21);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Case Priority', '/jsp/system/listCasePriority.jsp,/jsp/system/deleteCasePriority.action,/jsp/system/saveCasePriority.action',22);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Case Reason', '/jsp/system/listCaseReason.jsp,/jsp/system/deleteCaseReason.action,/jsp/system/saveCaseReason.action',23);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Case Status', '/jsp/system/listCaseStatus.jsp,/jsp/system/deleteCaseStatus.action,/jsp/system/saveCaseStatus.action',24);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Currency', '/jsp/system/listCurrency.jsp,/jsp/system/deleteCurrency.action,/jsp/system/saveCurrency.action',25);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Document Category', '/jsp/system/listDocumentCategory.jsp,/jsp/system/deleteDocumentCategory.action,/jsp/system/saveDocumentCategory.action',26);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Document Status', '/jsp/system/listDocumentStatus.jsp,/jsp/system/deleteDocumentStatus.action,/jsp/system/saveDocumentStatus.action',27);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Document SubCategory', '/jsp/system/listDocumentSubCategory.jsp,/jsp/system/deleteDocumentSubCategory.action,/jsp/system/saveDocumentSubCategory.action',28);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Document Type', '/jsp/system/listDocumentType.jsp,/jsp/system/deleteDocumentType.action,/jsp/system/saveDocumentType.action',29);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Industry', '/jsp/system/listIndustry.jsp,/jsp/system/deleteIndustry.action,/jsp/system/saveIndustry.action',30);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Lead Source', '/jsp/system/listLeadSource.jsp,/jsp/system/deleteLeadSource.action,/jsp/system/saveLeadSource.action',31);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Lead Status', '/jsp/system/listLeadStatus.jsp,/jsp/system/deleteLeadStatus.action,/jsp/system/saveLeadStatus.action',32);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'User Status', '/jsp/system/listUserStatus.jsp,/jsp/system/deleteUserStatus.action,/jsp/system/saveUserStatus.action',33);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Task Status', '/jsp/system/listTaskStatus.jsp,/jsp/system/deleteTaskStatus.action,/jsp/system/saveTaskStatus.action',34);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Task Priority', '/jsp/system/listTaskPriority.jsp,/jsp/system/deleteTaskPriority.action,/jsp/system/saveTaskPriority.action',35);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'TargetList Type', '/jsp/system/listTargetListType.jsp,/jsp/system/deleteTargetListType.action,/jsp/system/saveTargetListType.action',36);
INSERT INTO permission(id, name, url, sequence) VALUES (nextval('hibernate_sequence'),'Reminder Option', '/jsp/system/listReminderOption.jsp,/jsp/system/deleteReminderOption.action,/jsp/system/saveReminderOption.action',37);

INSERT INTO role(id, name, sequence) VALUES (nextval('hibernate_sequence'),'Administrator', 1);

INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 1);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 2);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 3);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 4);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 5);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 6);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 7);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 8);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 9);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 10);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 11);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 12);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 13);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 14);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 15);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 16);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 17);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 18);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 19);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 20);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 21);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 22);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 23);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 24);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 25);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 26);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 27);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 28);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 29);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 30);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 31);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 32);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 33);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 34);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 35);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 36);
INSERT INTO role_permission(role_id, permission_id) VALUES (currval('hibernate_sequence') ,currval('hibernate_sequence') - 37);

INSERT INTO users(id, name,password) VALUES (nextval('hibernate_sequence'),'admin','f23434d100b958477670c0c4593f69b5');

INSERT INTO users_role(user_id, role_id) VALUES (currval('hibernate_sequence'),currval('hibernate_sequence') - 1);



