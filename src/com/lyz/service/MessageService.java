package com.lyz.service;

import org.apache.log4j.Logger;

import com.laipai.message.util.PushNoticeMessage;
import com.laipai.orderInfo.pojo.LpOrder;
import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpMessageDetail;
import com.lyz.db.bean.LpPayOrder;
import com.lyz.db.bean.LpUser;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.pay.PayType;
import com.lyz.util.ShortMessageUtil;

public class MessageService {
	
	private static final Logger logger = Logger.getLogger(MessageService.class);
	
	private static final PushNoticeMessage pushNoticeMsg = new PushNoticeMessage();
	
	private static final IBaseDao<LpMessageDetail> msgDetailDao = new BaseDaoImpl<LpMessageDetail>();
	/**
	 * 获取消息数量
	 * @param userId
	 * @return
	 */
	public static int getMessageCount(String userId){
		
		String sql = "select count(*) from lp_message_detail where message_status=0 and recieve_user_id=?";
		
		return msgDetailDao.count(sql, userId);
	}
	/**
	 * 预约摄影师订单(线下)
	 * @param buyerId
	 * @param cameraId
	 */
	public static void sendMsg4AddOrderOffline(String buyerId, String cameraId){
				
		/*推送消息*/
		try {
			LpUser camera = UserService.getUserInfo(cameraId);
			LpUser buyer = UserService.getUserInfo(buyerId);
			if(camera != null && buyer != null){
				String pushTitle = "【来拍】顾客"+buyer.getNickName()+"通过来拍预约了您的拍摄服务。";
				pushNoticeMsg.pushOrderAlert(camera.getLastMobileToken(),camera.getUserName(), pushTitle, "", "", "1");
			}
			/*发送短信*/
			if(camera.getMobile()!=null && !"".equals(camera.getMobile())){
				
				ShortMessageUtil.sendMsg4AddOrderOffline(camera.getMobile(), buyer.getNickName());
			}
		} catch (Exception e) {
			logger.error("fail to sendMsg4AddOrder",e);
		}
		
		
		
	}
	/**
	 * 预约摄影师订单(线上)
	 * @param buyerName
	 * @param cameraId
	 * @param payType 支付类型
	 * @param payMoney 支付金额
	 */
	public static void sendMsg4AddOrderOnline(String buyerName, String cameraId, int payType, double payMoney){
		
		/*推送消息*/
		try {
			LpUser camera = UserService.getUserInfo(cameraId);
			if(camera != null && buyerName != null){
				String pushTitle = "【来拍】顾客"+buyerName+"通过来拍预约了您的拍摄服务。";
				pushNoticeMsg.pushOrderAlert(camera.getLastMobileToken(),camera.getUserName(), pushTitle, "", "", "1");
			}
			
			/*发送短信*/
			if(camera.getMobile()!=null && !"".equals(camera.getMobile())){
				if(payType == PayType.PAY_ALL){
					
					ShortMessageUtil.sendMsg4AddOrderFull(camera.getMobile(), buyerName, payMoney);
				}else if(payType == PayType.PAY_BARGAIN){
					
					ShortMessageUtil.sendMsg4AddOrderBargain(camera.getMobile(), buyerName, payMoney);
				}
			}
			
		} catch (Exception e) {
			logger.error("fail to sendMsg4AddOrder",e);
		}
		
//		/*发送短信*/
//		if(user.getMobile()!=null && !"".equals(user.getMobile())){
//			
//			ShortMessageUtil
//		}
		
	}
	/**
	 * 摄影师接单，给用户推送消息
	 * @param buyerId
	 * @param cameraName
	 */
	public static void sendMsg4AcceptOrder(LpPayOrder order){
		
		try {
			/*推送消息*/
			LpUser buyer = UserService.getUserInfo(order.getBuyerId());
			if(order.getCameraName() != null && buyer != null){
				String pushTitle = "【来拍】您好，您预约的摄影师"+order.getCameraName()+"已接单。";
				pushNoticeMsg.pushOrderAlert(buyer.getLastMobileToken(),buyer.getUserName(), pushTitle, "", "", "1");
				/*发送短信*/
				ShortMessageUtil.sendMsg4AcceptOrder(order.getBuyerPhone(), order.getCameraName());
			}
					
		} catch (Exception e) {
			logger.error("fail to sendMsg4AcceptOrder", e);
		}
	}
	/**
	 * 申请退款
	 * @param buyerName
	 * @param cameraId
	 */
	public static void sendMsg4ApplyRefund(String buyerName, String cameraId){
		
		try {
			/*推送消息*/
			LpUser camera = UserService.getUserInfo(cameraId);
			if(camera != null && buyerName != null){
				String pushTitle = "【来拍】用户"+buyerName+"已取消您的拍摄服务，请见谅。";
				pushNoticeMsg.pushOrderAlert(camera.getLastMobileToken(),camera.getUserName(), pushTitle, "", "", "1");
				
				/*发送短信*/
				ShortMessageUtil.sendMsg4CancelOrderBuyer(camera.getMobile(), buyerName);
			}
		} catch (Exception e) {
			logger.error("fail to sendMsg4ApplyRefund",e);
		}
	}
	/**
	 * 摄影师取消订单
	 * @param buyerId
	 * @param cameraName
	 */
	public static void sendMsg4CancelOrder(String buyerId, String cameraName, boolean isOffline, LpPayOrder order){
		
		try {
			/*推送消息*/
			LpUser buyer = UserService.getUserInfo(buyerId);
			if(cameraName != null && buyer != null){
				String pushTitle = null;
				if(isOffline){
					pushTitle = "【来拍】摄影师"+cameraName+"取消了您的预约。";
					ShortMessageUtil.sendMsg4CancelOrderCameraOffline(buyer.getMobile(), cameraName);
				}else{
					pushTitle = "【来拍】摄影师"+cameraName+"取消了您的预约，来拍已发起您的退款流程。";
					if(order.getPayType() == PayType.PAY_ALL){
						ShortMessageUtil.sendMsg4CancelOrderCamera(order.getBuyerPhone(), cameraName, ((double)order.getServicePrice())/100);
					}else if(order.getPayType() == PayType.PAY_BARGAIN){
						ShortMessageUtil.sendMsg4CancelOrderCamera(order.getBuyerPhone(), cameraName, OrderService.bargain);
					}
					
				}
				
				pushNoticeMsg.pushOrderAlert(buyer.getLastMobileToken(),buyer.getUserName(), pushTitle, "", "", "1");
			}
		} catch (Exception e) {
			logger.error("fail to sendMsg4CancelOrder",e);
		}
	}
	/**
	 * 完成拍摄
	 * @param order
	 */
	public static void sendMsg4FinishShoot(LpPayOrder order){
		
		if(order == null){
			return;
		}
		try {
			/*推送消息*/
			LpUser buyer = UserService.getUserInfo(order.getBuyerId());
			
			/**/
			if(buyer != null){
				
				ShortMessageUtil.sendMsg4FinishShoot(buyer.getMobile(), order.getCameraName());
			}
		} catch (Exception e) {
			logger.error("fail to sendMsg4FinishShoot!",e);
		}
		
	}
	
	
//	public static void sendOrder
	
	public static void main(String args[]){
		
		System.out.println(MessageService.getMessageCount("8a2a76634c8a1a04014c9317070a4cd3"));
	}
}
