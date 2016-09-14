SET DATABASE SQL SYNTAX ORA TRUE;

create sequence seq INCREMENT BY 1 	MINVALUE 1 START WITH 10001;

CREATE TABLE COMPANIES (
	ID    NUMBER(19,0) primary key,
	NAME  VARCHAR2(50)
);

CREATE TABLE SITES (
	ID          NUMBER(19,0) PRIMARY KEY,
	NAME        VARCHAR2(50),
	COMPANY_ID  NUMBER(19,0) REFERENCES COMPANIES(ID)
);

CREATE TABLE EMPLOYEES (
	ID          NUMBER(19,0) PRIMARY KEY,
	NAME        VARCHAR2(50),
	PHONE_NUMBER VARCHAR2(50),
	COMPANY_ID  NUMBER(19,0) NOT NULL REFERENCES COMPANIES(ID),
	SITE_ID     NUMBER(19,0) NOT NULL REFERENCES SITES(ID),
	DISCRIMINATOR VARCHAR2(50)
);

CREATE TABLE EMPLOYEE_DETAILS (
	ID                NUMBER(19,0) PRIMARY KEY,
	EMPLOYEE_ID       NUMBER(19,0) NOT NULL REFERENCES EMPLOYEES(ID),
	EMPLOYEMENT_DATE  DATE
);

CREATE TABLE PROJECTS (
	ID          NUMBER(19,0) PRIMARY KEY,
	NAME        VARCHAR2(50),
	TYPE        VARCHAR2(50) NOT NULL,
	MANAGER_ID  NUMBER(19,0) REFERENCES EMPLOYEES(ID)
);

CREATE TABLE EMP_PRJ (
	EMPLOYEE_ID  NUMBER(19,0) NOT NULL REFERENCES EMPLOYEES(ID),
	PROJECT_ID   NUMBER(19,0) NOT NULL REFERENCES PROJECTS(ID)
);

insert into companies(id, name) values (1, 'Good Stuff Inc.');
insert into companies(id, name) values (2, 'Bad Stuff Inc.');
insert into companies(id, name) values (3, 'Cheap Stuff Inc.');

insert into sites(id, name, company_id) values (1, 'Bucharest', 1);
insert into sites(id, name, company_id) values (2, 'London', 1);
insert into sites(id, name, company_id) values (3, 'Bucharest', 2);
insert into sites(id, name, company_id) values (4, 'Chisinau', 3);

INSERT INTO EMPLOYEES(ID, NAME, PHONE_NUMBER, COMPANY_ID, SITE_ID, DISCRIMINATOR) 
values (1, 'John Doe', '080000123', 1, 1, 'EMPLOYEE');

INSERT INTO EMPLOYEES(ID, NAME, PHONE_NUMBER, COMPANY_ID, SITE_ID, DISCRIMINATOR) 
values (2, 'Manager1', '089999999', 1, 2, 'MANAGER');

INSERT INTO EMPLOYEES(ID, NAME, PHONE_NUMBER, COMPANY_ID, SITE_ID, DISCRIMINATOR) 
values (3, 'Manager2', '085555555', 2, 3, 'MANAGER');

INSERT INTO EMPLOYEES(ID, NAME, PHONE_NUMBER, COMPANY_ID, SITE_ID, DISCRIMINATOR) 
values (4, 'Jane Doe', '087777777', 3, 4, 'EMPLOYEE');

INSERT INTO EMPLOYEE_DETAILS(ID, EMPLOYEE_ID, EMPLOYEMENT_DATE) 
values (1, 1, to_date('2016-01-01', 'RRRR-MM-DD'));

	
insert into projects(id, name, type, manager_id) 
values (1, 'MyBatis POC', 'PRIVATE', 2);

insert into projects(id, name, type, manager_id) 
values (2, 'OpenSOurceProject', 'PUBLIC', 3);

insert into emp_prj(employee_id, project_id) values (1,1);
insert into emp_prj(employee_id, project_id) values (2,1);
insert into emp_prj(employee_id, project_id) values (3,1);
insert into emp_prj(employee_id, project_id) values (4,1);
