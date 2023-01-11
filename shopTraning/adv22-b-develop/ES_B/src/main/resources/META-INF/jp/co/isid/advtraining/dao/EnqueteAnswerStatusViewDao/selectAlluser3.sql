SELECT count(esq_id) FROM esq_user 
WHERE dept_id IN (
select dept_id from enquete_dept 
where enquete_dept.enquete_id=/*enqueteId*/1);