package com.laipai.message.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.SpringContextUtil;
import com.laipai.message.pojo.LpMessagePushCode;
import com.laipai.userManInfo.pojo.LpUser;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;

public class PushNoticeMessage {
	
	private static final Logger logger = Logger.getLogger(PushNoticeMessage.class);
	
	public void pushOrderAlert(String token, String userName,String title,String content,String flag,String type){
		
		if(token==null || "".equals(token.trim())){
			return;
		}
		
		LpUser user = new LpUser();
		user.setLastMobileToken(token);
		user.setUserName(userName);
		
		pushOrderAlert(user, title, content, flag, type);
	}
	
	/**
	 * 安卓和ios的推送，使用腾讯信鸽 http://xg.qq.com/xg，点击我的应用进去，可以配置安卓和ios应用，获取ACCESS ID，SECRET KEY
	 * 将ACCESS ID，SECRET KEY保存到表lp_message_push_code,推送时查询此表,获取数据.
	 * 推送预约通知
	 * flag : 发送给谁的标记 : 为空时发送给单个
	 * type : 新消息类型1、 预约，2、评论(所有评论和回复)，3、赞，4、关注，5、系统消息
	 * */
	public void pushOrderAlert(LpUser user,String title,String content,String flag,String type) {

		try {
			if(user!=null && StringUtils.isNotEmpty(user.getLastMobileToken())){
				String token = user.getLastMobileToken();
				//查询信鸽中安卓，ios的ACCESS ID，SECRET KEY
				LpMessagePushCode pushCode = getLpMessagePushCode();
				long ANDROID_ACCESSID = pushCode.getAndroidAccessid();
				String ANDROID_SECRETKEY = pushCode.getAndroidSecretkey();
				long IOS_ACCESSID = pushCode.getIosAccessid();
				String IOS_SECRETKEY = pushCode.getIosSecretkey();
				
				//发送推送消息
				if(StringUtils.isEmpty(flag)){//发送给单个设备
					JSONObject  androidjson = XingeApp.pushTokenAndroid(ANDROID_ACCESSID, ANDROID_SECRETKEY, title, content, token);
					JSONObject  json = pushIOSSingleDevice(IOS_ACCESSID, IOS_SECRETKEY,token, content, type, XingeApp.IOSENV_DEV);
					logger.info("push message userName="+user.getUserName()+",token="+token+",androidjson="+androidjson+"-----iosjson="+json.toString());
				}else{//发送给所有设备
					XingeApp.pushAllAndroid(ANDROID_ACCESSID, ANDROID_SECRETKEY, title, content);
					XingeApp.pushAllIos(IOS_ACCESSID, IOS_SECRETKEY, content, XingeApp.IOSENV_DEV);
					logger.info("push message for all!title="+title);
				}
			}else{
				
				logger.info("no push message,user="+user+",token="+user==null?"":user.getLastMobileToken());
			}
		} catch (Exception e) {
			logger.error("fail to push message!",e);
		}
	}
	
	//IOS
	public JSONObject pushIOSSingleDevice(long IOS_ACCESSID,String IOS_SECRETKEY,String token,String content,String type,int environment){
		XingeApp push = new XingeApp(IOS_ACCESSID, IOS_SECRETKEY);
		MessageIOS mess = new MessageIOS(); //$mess = new MessageIOS();
		//完善Message消息
		mess.setAlert(content);
		mess.setCategory(type);
		JSONObject ret = push.pushSingleDevice(token, mess, environment);
		return ret;
	} 
	
	/**
	 * 
	 * 信鸽中,安卓和ios的ACCESS ID，SECRET KEY在表lp_message_push_code中配置
	 * */
	public LpMessagePushCode getLpMessagePushCode(){
		IBaseDao baseDao = (IBaseDao) SpringContextUtil.getBean("baseDao");
		LpMessagePushCode lpMessagePushCode = (LpMessagePushCode) baseDao.getObjectById(LpMessagePushCode.class, "message_push");
		return lpMessagePushCode;
	}
}
