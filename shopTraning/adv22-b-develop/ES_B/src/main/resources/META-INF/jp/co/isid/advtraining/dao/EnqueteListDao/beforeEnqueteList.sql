SELECT enquete.enquete_id,enquete.enquete_name, enquete.enquete_state_id
FROM enquete_admin_user INNER JOIN enquete
on enquete_admin_user.enquete_id =  enquete.enquete_id
where  enquete_state_id in  (1 , 2) and delete_flag = '0' and esq_id = /*esqId*/'li9010' order by create_date desc;
