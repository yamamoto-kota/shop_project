SELECT enquete.enquete_id, enquete.enquete_name, enquete.enquete_state_id
FROM enquete
INNER JOIN enquete_dept-- ①　アンケートテーブルと部署テーブルを結合
ON enquete.enquete_id = enquete_dept.enquete_id
LEFT OUTER JOIN esq_user-- ②　①とユーザテーブルを結合
ON enquete_dept.dept_id = esq_user.dept_id
LEFT OUTER JOIN enquete_answer-- ③　②とアンサーテーブルを結合
ON enquete.enquete_id = enquete_answer.enquete_id AND esq_user.esq_id = enquete_answer.esq_id
WHERE esq_user.esq_id = /*esqId*/'li9025'
and enquete.enquete_state_id=3
and answer_date is null
order by start_date desc;