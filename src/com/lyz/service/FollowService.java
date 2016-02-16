package com.lyz.service;

import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpFollow;
import com.lyz.db.dao.BaseDaoImpl;

/**
 * 关注服务
 * @author luyongzhao
 *
 */
public class FollowService {
	
	private static final IBaseDao<LpFollow> followDao = new BaseDaoImpl<LpFollow>();
	
	/**
	 * 获取关注的摄影师数
	 * @param cameraId
	 * @return
	 */
	public static int getFollowCount(String userId){
		
		String sql = "select count(*) from lp_follow where user_id=?";
		
		return followDao.count(sql, userId);
	}
	/**
	 * 获取摄影师的粉丝人数
	 * @param cameraId
	 * @return
	 */
	public static int getFansCount(String cameraId){
		
		String sql = "select count(*) from lp_follow where camera_id=?";
		
		return followDao.count(sql, cameraId);
	}
}
