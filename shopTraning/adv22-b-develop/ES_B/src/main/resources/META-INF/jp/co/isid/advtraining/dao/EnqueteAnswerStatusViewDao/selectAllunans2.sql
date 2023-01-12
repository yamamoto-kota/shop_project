SELECT count(esq_id) FROM esq_user 
WHERE esq_user.esq_id  NOT IN 
(SELECT esq_user.esq_id 
FROM enquete_answer INNER JOIN esq_user
on enquete_answer.esq_id = esq_user.esq_id
WHERE enquete_answer.enquete_id=/*enqueteId*/1)
and dept_id IN (
select dept_id from enquete_dept 
where enquete_dept.enquete_id=/*enqueteId*/1);