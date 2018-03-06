drop table nopk
/
create table nopk (
	a int,
	b int, 
	c int
)
/
insert into nopk values (1,2,3)

/
DROP VIEW project_overview
/

create view project_overview as 
select p.name project_name, count(i.iteration) ITERATION_COUNT
FROM project p 
left join iteration i on i.project_id = p.id
group by p.id

/

/
DROP procedure IF EXISTS plus1inout
/
CREATE procedure plus1inout (IN arg int, OUT res int)
BEGIN ATOMIC
 set res = arg + 1;
END
/
  
  
  