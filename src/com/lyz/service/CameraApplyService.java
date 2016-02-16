package com.lyz.service;

import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpCameramanAppform;
import com.lyz.db.dao.BaseDaoImpl;

/**
 * 摄影师审核服务
 * @author luyongzhao
 *
 */
public class CameraApplyService {

	private static IBaseDao<LpCameramanAppform> applyDao = new BaseDaoImpl<LpCameramanAppform>();
	
	/**
	 * 判断是否已经提交了审核
	 * @param userId
	 * @return
	 */
	public static  boolean isExist(String userId){
		
		String sql = "select count(*) from lp_cameraman_appform where check_status in (0,1) and user_id=?";
		
		return applyDao.count(sql, userId) > 0;
	}
	
	public static void main(String args[]){
		
		System.out.println(CameraApplyService.isExist("8a2a76634ca164e9014ca1e05dc30481"));
	}
}
