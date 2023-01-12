SELECT esq_user.esq_id,esq_user.user_name,dept.dept_name FROM esq_user inner join dept on esq_user.dept_id=dept.dept_id
WHERE esq_user.esq_id  in
(SELECT esq_user.esq_id FROM enquete_answer INNER JOIN esq_user
on enquete_answer.esq_id = esq_user.esq_id
WHERE enquete_answer.enquete_id=/*enqueteId*/1)
group by esq_user.dept_id,esq_id
having esq_user.dept_id IN (
select dept_id from enquete_dept
where enquete_dept.enquete_id=/*enqueteId*/1);
