SELECT enquete.enquete_id,enquete.enquete_name, enquete.enquete_state_id
FROM enquete_admin_user INNER JOIN enquete
on enquete_admin_user.enquete_id =  enquete.enquete_id
where delete_flag = '0' and esq_id = /*esqId*/'li9010' and enquete_state_id = 3 order by create_date desc;

