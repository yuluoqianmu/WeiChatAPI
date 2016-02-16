package com.lyz.service;

import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpLike;
import com.lyz.db.dao.BaseDaoImpl;

public class LikeService {
	
	private static final IBaseDao<LpLike> likeDao = new BaseDaoImpl<LpLike>();
	/**
	 * 喜欢数量
	 * @param galaryId
	 * @param userId
	 * @return
	 */
	public static int getLikeCount(String galaryId, String userId){
		
		String sql = "select count(*) from lp_like where galary_id=? and user_id=? and like_status=0 and like_type=0";
		
		return likeDao.count(sql,galaryId,userId);
	}
	/**
	 * 查询用户都喜欢数量
	 * @param userId
	 * @return
	 */
	public static int getLikeCount4User(String userId){
		
		String sql = "select count(*) from lp_like where user_id=? and like_status=0 and like_type=0";
		
		return likeDao.count(sql, userId);
	}
	
	public static void main(String args[]){
		
		System.out.println(LikeService.getLikeCount("8a2a76634c87891a014c8872b24108d4", "8a2a76634c5af695014c5b3f9f570043"));
	}

}
