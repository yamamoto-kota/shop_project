SELECT * FROM adv22.esq_user 
WHERE adv22.esq_user.user_name  NOT IN 
(SELECT adv22.esq_user.user_name FROM adv22.enquete_answer INNER JOIN adv22.esq_user
on enquete_answer.esq_id = esq_user.esq_id
WHERE adv22.enquete_answer.enquete_id=/*enqueteId*/1) 
group by dept_id,esq_id
having dept_id IN (
select dept_id from adv22.enquete_dept 
where adv22.enquete_dept.enquete_id=/*enqueteId*/1);