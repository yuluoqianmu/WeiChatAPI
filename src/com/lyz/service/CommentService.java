package com.lyz.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.serviceInfo.pojo.LpService;
import com.lyz.api.bean.AddPayCommentReq;
import com.lyz.api.bean.ShowPayCommentListResp.Cmt;
import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpComment;
import com.lyz.db.bean.LpMergedService;
import com.lyz.db.bean.LpPayComment;
import com.lyz.db.bean.LpPayOrder;
import com.lyz.db.bean.LpUser;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.labelData.util.BaseTypeUtil;
import com.lyz.util.ClassUtil;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * 评论服务
 * @author luyongzhao
 *
 */
public class CommentService {
	
	private static final Logger logger = Logger.getLogger(CommentService.class);
	
	private static final IBaseDao<LpComment> commDao = new BaseDaoImpl<LpComment>();
	
	private static IBaseDao<LpPayOrder> orderDao = new BaseDaoImpl<LpPayOrder>();

	/**
	 * 支付评价表操作类
	 */
	private static final IBaseDao<LpPayComment> payCommDao = new BaseDaoImpl<LpPayComment>();
	
	public static int getCommentCount(String galaryId){
		
		String sql = "select count(*) from lp_comment where comment_type in (0,4) and galary_id=?";
		
		return commDao.count(sql, galaryId);
	}
	/**
	 * 返回评论列表
	 * @param cameraId
	 * @return
	 */
	public static List<Cmt> getPayCmtList(String cameraId, int pageNo, int pageSize){
		
		int offset = (pageNo-1)*pageSize;
		String sql = "select id,user_name,user_head_img,service_name,cmt_score,cmt_content " +
				"from lp_pay_comment " +
				"where camera_id=? " +
				"order by id desc " +
				"limit "+offset+","+pageSize;
		
		List<LpPayComment> cmtList = payCommDao.queryObjects(sql, LpPayComment.class, cameraId);
		
		if(cmtList == null || cmtList.size() == 0){
			return null;
		}
		
		List<Cmt> list = new ArrayList<Cmt>();
		/*组装对象*/
		for(LpPayComment cmt : cmtList){
			
			Cmt c = (Cmt)ClassUtil.copyAttr(Cmt.class, cmt);
			
			c.setTime(LaiPaiEntityIdGenerator.getTime(cmt.getId(), "yyyy.MM.dd HH:mm:ss"));
			
			list.add(c);
		}
		
		return list;
	}
	
	
	/**
	 * 添加支付评论
	 * @param req
	 * @return
	 */
	public static int addPayComment(AddPayCommentReq req){
		
		long orderId = BaseTypeUtil.getLong(req.getOrderId(), 0);
		/*获取用户信息*/
		String orderSql = "select buyer_name,buyer_head_img,service_name " +
				"from lp_pay_order where order_id=? and buyer_id=? and camera_id=? and service_id=?";
		/*获取摄影师信息*/
		LpPayOrder order = orderDao.queryObject(orderSql, LpPayOrder.class, orderId,req.getUserId(),req.getCameraId(),req.getServiceId());
		
		if(order == null){
			logger.error("order does not exist,orderId="+req.getOrderId()+"...");
			return -1;
		}
		
		/*组装对象*/
		LpPayComment cmt = new LpPayComment();
		cmt.setCameraId(req.getCameraId());
		cmt.setCmtContent(req.getContent());
		cmt.setCmtScore(req.getScore());
		cmt.setId(LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeComment));
		cmt.setOrderId(orderId);
		cmt.setServiceId(req.getServiceId());
		cmt.setServiceName(order.getServiceName());
		cmt.setUserHeadImg(order.getBuyerHeadImg());
		cmt.setUserId(req.getUserId());
		cmt.setUserName(order.getBuyerName());
		
		String cmtSql = payCommDao.getSqlForInsert(cmt, "lp_pay_comment");
		return payCommDao.upsertObject(cmtSql);
		
	}
	/**
	 * 获取指定评论数量
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public static int getPayCmtCount(long orderId, String userId){
		
		String sql = "select count(*) from lp_pay_comment " +
				"where order_id=? and user_Id=?";
		
		int count = payCommDao.count(sql, orderId,userId);
		
		return count;
	}
	/**
	 * 获取摄影师的支付评论数
	 * @param cameraId
	 * @return
	 */
	public static int getPayCmtCount(String cameraId){
				
		String sql = "select count(*) from lp_pay_comment where camera_id=?";
		
		return payCommDao.count(sql,cameraId);
	}
	
	public static void main(String args[]){
		
		System.out.println(CommentService.getCommentCount("8a2a76634c7e5e72014c7ec04d180496"));
	}
}
