package com.lyz.util;

import java.util.HashMap;
import java.util.Set;
import org.apache.log4j.Logger;
import com.cloopen.rest.sdk.CCPRestSDK;

/**
 * 短信发送接口
 * @author luzi
 *
 */
public class ShortMessageUtil {
private static final Logger logger = Logger.getLogger(ShortMessageUtil.class);
/*注册模板id，参考http://www.yuntongxun.com中设置的模板id*/
public static final String MODEID_REGISTER = "16944";
/*找回密码模板id*/
public static final String MODEID_FIND_PWD = "16946";
/*摄影师取消订单，并退款*/
public static final String CANCEL_ORDER_CAMERA = "20692";
/*摄影师取消线下订单*/
public static final String CANCEL_ORDER_CAMERA_OFFLINE = "20690";
/*用户取消订单*/
public static final String CANCEL_ORDER_USER = "20688";
/*摄影师接单*/
public static final String ACCEPT_ORDER_CAMERA = "20687";
/*预约通知-线下*/
public static final String ADD_ORDER_OFFLINE = "20686";
/*预约通知-全款*/
public static final String ADD_ORDER_FULL = "20757";
/*预约通知-定金*/
public static final String ADD_ORDER_BARGAIN = "20759";
/*摄影师拍摄完成*/
public static final String FINISH_SHOOT = "20976";
/*申请加入来拍社审核通过*/
public static final String APPLY_LAIPAI_CLUB = "21423";


private static CCPRestSDK restAPI = null;
static{
	restAPI = new CCPRestSDK();
	restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
	restAPI.setAccount("8a48b5514c9d9c05014cb756a30512c1", "0166e45728dc449591037fc146cbd26e");// 初始化主帐号名称和主帐号令牌
	restAPI.setAppId("aaf98f894c9d994b014cb75f8c1c1318");// 初始化应用ID
}
	/**
	 * 发送注册验证码方法
	 * @param phoneNum 手机号
	 * @param verCode 验证码
	 * @param timeout 超时时间
	 * @param modelId 模板id
	 * @return 成功返回true
	 */
	public static boolean sendRegisterMessage(String phoneNum, String verCode){

		String[] datas = new String[]{verCode,"1"};
		return sendMessage(phoneNum, MODEID_REGISTER, datas);
	}
	/**
	 * 发送找回密码验证码
	 * @param phoneNum
	 * @param verCode
	 * @return
	 */
	public static boolean sendFindPwdMessage(String phoneNum, String verCode){

		String[] datas = new String[]{verCode,"1"};
		return sendMessage(phoneNum, MODEID_FIND_PWD, datas);
	}
	
	public static boolean sendCommonMessage(String phoneNum, String arg1,String arg2){
		
		String[] datas = new String[]{arg1,arg2};
		return sendMessage(phoneNum, MODEID_FIND_PWD, datas);
	}
	/**
	 * 摄影师接单
	 * @param phoneNum
	 * @param cameraName
	 * @return
	 */
	public static boolean sendMsg4AcceptOrder(String phoneNum, String cameraName){
		
		String[] datas = new String[]{cameraName};
		return sendMessage(phoneNum, ACCEPT_ORDER_CAMERA, datas);
	}
	/**
	 * 用户取消订单
	 * @param phoneNum
	 * @param buyerName 买家用户名称
	 * @return
	 */
	public static boolean sendMsg4CancelOrderBuyer(String phoneNum, String buyerName){
		
		String[] datas = new String[]{buyerName};
		return sendMessage(phoneNum, CANCEL_ORDER_USER, datas);
	}
	/**
	 * 摄影师取消线下订单
	 * @param phoneNum
	 * @param cameraName
	 * @return
	 */
	public static boolean sendMsg4CancelOrderCameraOffline(String phoneNum, String cameraName){
		
		String[] datas = new String[]{cameraName};
		return sendMessage(phoneNum, CANCEL_ORDER_CAMERA_OFFLINE, datas);
	}
	/**
	 * 摄影师取消订单
	 * @param phoneNum
	 * @param cameraName
	 * @param moneyNum 退款金额，元为单位
	 * @return
	 */
	public static boolean sendMsg4CancelOrderCamera(String phoneNum, String cameraName, double moneyNum){
		
		String[] datas = new String[]{cameraName,moneyNum+""};
		return sendMessage(phoneNum, CANCEL_ORDER_CAMERA, datas);
	}
	/**
	 * 预约通知-线下
	 * @param phoneNum
	 * @param buyerName
	 * @return
	 */
	public static boolean sendMsg4AddOrderOffline(String phoneNum, String buyerName){
		
		String[] datas = new String[]{buyerName};
		return sendMessage(phoneNum, ADD_ORDER_OFFLINE, datas);
	}
	/**
	 * 预约通知-全款
	 * @param phoneNum
	 * @param buyerName
	 * @param moneyNum
	 * @return
	 */
	public static boolean sendMsg4AddOrderFull(String phoneNum, String buyerName, double moneyNum){
		
		String[] datas = new String[]{buyerName,moneyNum+""};
		return sendMessage(phoneNum, ADD_ORDER_FULL, datas);
	}
	/**
	 * 预约通知-定金
	 * @param phoneNum
	 * @param buyerName
	 * @param moneyNum
	 * @return
	 */
	public static boolean sendMsg4AddOrderBargain(String phoneNum, String buyerName, double moneyNum){
		
		String[] datas = new String[]{buyerName,moneyNum+""};
		return sendMessage(phoneNum, ADD_ORDER_BARGAIN, datas);
	}
	/**
	 * 完成拍摄
	 * @param phoneNum
	 * @param cameraName
	 * @return
	 */
	public static boolean sendMsg4FinishShoot(String phoneNum, String cameraName){
		
		String[] datas = new String[]{cameraName};
		return sendMessage(phoneNum, FINISH_SHOOT, datas);
	}
	/**
	 * 申请加入来拍社审核通过
	 * @param phoneNum
	 * @param applierName
	 * @return
	 */
	public static boolean sendMsg4ApplyLaiPaiClub(String phoneNum, String applierName){
		
		String[] datas = new String[]{applierName,"微信：laipai2015"};
		return sendMessage(phoneNum, APPLY_LAIPAI_CLUB, datas);
	}
	
	
	
	
	/**
	 * 发送模板短信
	 * @param phoneNum 电话号码
	 * @param templateId 模板id
	 * @param datas 模板参数
	 * @return
	 */
	public static boolean sendMessage(String phoneNum, String templateId, String[] datas){
		HashMap<String, Object> result = null;

		
		try {
			result = restAPI.sendTemplateSMS(phoneNum, templateId, datas);
			logger.info("SDKTestGetSubAccounts result=" + result);
			if("000000".equals(result.get("statusCode"))){
				//正常返回输出data包体信息（map）
				HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
				Set<String> keySet = data.keySet();
				for(String key:keySet){
					Object object = data.get(key);
				}
				logger.info("success to send shortMsg!phonenum="+phoneNum);
			}else{
				//异常返回输出错误码和错误信息
				logger.error("fail to send shortMsg!phonenum="+phoneNum);
				return false;
			}
		} catch (Exception e) {
			//异常返回输出错误码和错误信息
			logger.error("Exception:fail to send shortMsg!phonenum="+phoneNum,e);
			return false;
		}
		
		return true;
	}
	
	public static void main(String args[]){
				
//		ShortMessageUtil.sendMessage("17710220684", "8421", "1", "16944");
		
//		ShortMessageUtil.sendMsg4AcceptOrder("17710220684", "柳叶");
		
//		ShortMessageUtil.sendMsg4CancelOrderBuyer("17710220684", "小路");
		
//		ShortMessageUtil.sendMsg4CancelOrderCamera("17710220684", "柳叶", 100);
		
//		ShortMessageUtil.sendMsg4CancelOrderCameraOffline("17710220684", "柳叶");
		
//		ShortMessageUtil.sendMsg4AddOrderOffline("17710220684", "小路");
		
//		ShortMessageUtil.sendMsg4AddOrderFull("17710220684", "小路", 300);
		
//		ShortMessageUtil.sendMsg4AddOrderBargain("17710220684", "小路", 100);
		
//		ShortMessageUtil.sendMsg4FinishShoot("17710220684", "小路");
		
		ShortMessageUtil.sendMsg4ApplyLaiPaiClub("17710220684", "小路");
	}


}
