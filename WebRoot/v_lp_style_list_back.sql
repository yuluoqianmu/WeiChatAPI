--后台页面，首页-->运营后台-->拍摄风格
create or replace view  v_lp_style_list_back  as
SELECT
lp_style.style_id,
lp_style.style_name,
lp_style.style_type,
lp_style.style_status,
lp_style.create_userId,
lp_style.online_time,
lp_style.style_location,
lp_style.is_online,
lp_style.man_number,
lp_style.is_true_delete,
(select  count(lp_cameraman_style.user_id) from  lp_cameraman_style where  lp_style.style_id = lp_cameraman_style.style_id)  cammer_man_number
FROM
lp_style
order by lp_style.style_location;