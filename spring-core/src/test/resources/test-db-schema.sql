drop table EMPLOYEE if exists;
drop table NOTIFICATION if exists;

create table EMPLOYEE (ID varchar(20) primary key, NAME varchar(50), PHONE varchar(30));
create table NOTIFICATION (ID varchar(20) primary key, MESSAGE varchar(100));
