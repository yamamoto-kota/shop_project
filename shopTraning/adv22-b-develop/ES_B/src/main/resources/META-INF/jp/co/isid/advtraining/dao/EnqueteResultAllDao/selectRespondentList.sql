SELECT
ea.esq_id,e.user_name,d.dept_name
FROM
enquete_answer ea
INNER JOIN
esq_user e
ON
ea.esq_id = e.esq_id
INNER JOIN
dept d
ON
e.dept_id=d.dept_id
WHERE
ENQUETE_ID = /*enqueteId*/1;