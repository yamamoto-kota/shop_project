SELECT enquete.enquete_id,enquete.enquete_name, enquete.enquete_state_id
FROM enquete_answer INNER JOIN enquete
on enquete_answer.enquete_id =  enquete.enquete_id
where esq_id = /*esqId*/'li9010' order by start_date desc;

