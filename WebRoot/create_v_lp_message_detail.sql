--查询消息
create or REPLACE VIEW v_lp_message_detail AS
select lmd.mesdetail_id as mesdetail_id,
       lmd.message_title as message_title,
       lmd.create_time as create_time,
	   lmd.message_send_person as message_send_person,
	   lmd.message_status as message_status,
	   lmd.message_content as message_content,
       lu.nick_name as nick_name,
	   lu.user_id as user_id,
	   lm.message_type as message_type
from lp_message_detail as lmd
left JOIN lp_user as lu on lmd.recieve_user_id=lu.user_id
left join lp_message lm on lm.message_id=lmd.message_id
where 1=1