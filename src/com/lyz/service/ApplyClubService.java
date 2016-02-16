package com.lyz.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LPApplyClub;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.util.LaiPaiEntityIdGenerator;
import com.lyz.util.ShortMessageUtil;

public class ApplyClubService {
	
	private static final Logger logger = Logger.getLogger(ApplyClubService.class);
	
	private static final IBaseDao<LPApplyClub> applyClubDao = new BaseDaoImpl<LPApplyClub>();
	/**
	 *保存摄影师加入来拍社的信息 
	 */
	public static String saveApplyClub(LPApplyClub applyClub){
		
		String applyId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeUser)+"";
		applyClub.setApplyId(applyId);
		int count=0;
		String sql =  applyClubDao.getSqlForInsert(applyClub, "lp_apply_club");
		
		try{
		
		count = applyClubDao.upsertObject(sql);
		
		}catch(Exception e){
			logger.error("插入数据库失败！"+sql,e);
		}
		if(count > 0){
			return applyId;
		}
		return null;
	}
	/**
	 * 判断该摄影师是否存在
	 * @param phoneNum
	 * @return
	 */
	public static boolean checkExit(String phoneNum) {
		String sql = "select count(*) from lp_apply_club where phoneNum='"+phoneNum+"'";
		
		int count = applyClubDao.count(sql);
		if(count>0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 获取摄影师总数
	 */
	public static int getCount(){
		int count = 0;
		String sql = "select count(*) from lp_apply_club";
		count = applyClubDao.count(sql);
		return count;
	}
	/**
	 * 获取申请加入来拍社列表列表
	 * @param check 审核状态，0：未审核，1-审核通过，2-审核不通过
	 * @return
	 */
	public static List<LPApplyClub> getApplyClubList(int check){
		
		String sql = "select camera_name,phoneNum from lp_apply_club where `check`=?";
		
		return applyClubDao.queryObjects(sql, LPApplyClub.class, check);
	}
	
	public static void main(String args[]){
		
		List<LPApplyClub> list = ApplyClubService.getApplyClubList(1);
		logger.info("prepare to send count="+list.size());
		for(LPApplyClub club : list){
			
			logger.info(club.getCameraName()+"\t"+club.getPhoneNum());
			/*发送短信*/
			ShortMessageUtil.sendMsg4ApplyLaiPaiClub(club.getPhoneNum(), club.getCameraName());
		}
	}
}
