package com.lyz.service;

import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpAppNotify;
import com.lyz.db.dao.BaseDaoImpl;

public class AppNotifyService {
	
	private static IBaseDao<LpAppNotify> notifyDao = new BaseDaoImpl<LpAppNotify>();
	/**
	 * 获取升级提醒消息
	 * @param notifyId
	 * @param appType
	 * @return
	 */
	public static LpAppNotify getAppUpNotify(String appType){
		
		String sql = "select app_version,notify_content,up_type,url from lp_app_notify where notify_type=0 and effective=1 and app_type=?";
		
		return notifyDao.queryObject(sql, LpAppNotify.class,appType);
	}
}
