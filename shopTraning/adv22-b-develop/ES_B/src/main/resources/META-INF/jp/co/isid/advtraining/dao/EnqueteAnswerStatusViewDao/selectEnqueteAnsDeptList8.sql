select dept.dept_id,dept.dept_name from dept,enquete_dept
where
enquete_dept.enquete_id=/*enqueteId*/2
 and dept.dept_id=enquete_dept.dept_id  
 order by dept.dept_id asc;
 
 