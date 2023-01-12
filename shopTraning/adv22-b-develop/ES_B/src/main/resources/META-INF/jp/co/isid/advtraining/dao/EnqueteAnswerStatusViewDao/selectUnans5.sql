select ifnull(DeptNoAnsList,0) as DeptNoAns
from
(select dept_id
from enquete_dept
where enquete_dept.enquete_id=/*enqueteId*/1
order by dept_id asc)as deptList

left outer join

(SELECT dept_id, count(esq_id is not null or null) as DeptNoAnsList
FROM esq_user
WHERE esq_user.user_name  NOT IN
(SELECT esq_user.user_name FROM enquete_answer INNER JOIN esq_user
on enquete_answer.esq_id = esq_user.esq_id
WHERE enquete_answer.enquete_id=/*enqueteId*/1)
group by dept_id) as no_ansList

on deptList.dept_id=no_ansList.dept_id