SELECT DISTINCT
esq_user.esq_id,user_name,dept_name
FROM
esq_user
left outer join
enquete_admin_user
on esq_user.esq_id=enquete_admin_user.esq_id
INNER JOIN
dept
ON
esq_user.dept_id=dept.dept_id
where
esq_user.user_name like /*@infix(searchWord)*/'%%'escape'$'
and
esq_user.esq_id
not in
(SELECT
esq_user.esq_id
FROM
esq_user
left outer join
enquete_admin_user
on esq_user.esq_id=enquete_admin_user.esq_id
INNER JOIN
dept
ON
esq_user.dept_id=dept.dept_id
WHERE
enquete_id = /*enqueteId*/3
and enquete_admin_user.delete_flag!=1) ;