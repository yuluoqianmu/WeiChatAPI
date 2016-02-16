package com.laipai.userManInfo.app;

import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.pojo.LpVercode;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.SendMessageTool;
import com.laipai.base.util.ShortMessageUtil;

import net.sf.json.JSONObject;

/**
 * 发送注册短信验证码
 * 
 * @author zhh
 * 
 */
@NotLogin
@Service("SendVercodeSMSExecutorImpl")
public class SendVercodeSMSExecutorImpl implements RequestExecutor {
	
	private static final Logger logger = Logger.getLogger(SendVercodeSMSExecutorImpl.class);

	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		System.out.println("--------------发送验证码");
		JSONObject json = parameters.getJson();
		String mobile = JSONTools.getString(json, "userName");
		String vercodeType = JSONTools.getString(json, "vercodeType"); //验证码类型  0注册  1找回密码
		
		if (StringUtils.isEmpty(mobile)) {
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		
		//插入新验证码前，把之前的验证码置为无效
		baseDao.executeSql("update lp_vercode lv set lv.status=1 where lv.vercode_type=0"
			+" and lv.mobile_to='" +mobile+"' and date_format(lv.send_time,'%Y%m%d')=date_format(now(),'%Y%m%d')");
		
		//生成验证码
		String vercode = SendMessageTool.getRandomNum();
		String sendMsg = "用户您好，您本次注册来拍的验证码是 " + vercode;
		boolean sendResult = false;
		if("1".equals(vercodeType)){
			sendMsg = "来拍用户您好，您本次找回密码的验证码是 " + vercode;
			int userNum = 0;
			try {
				userNum = baseDao.getCount("from LpUser where userName='"+mobile+"'");
			} catch (Exception e) {
				logger.error("fail to get user!",e);
			}
			if(userNum <= 0){
				return 	JSONTools.newAPIResult(1, "此手机号未注册,请输入正确手机号");
			}
			sendResult = ShortMessageUtil.sendFindPwdMessage(mobile, vercode);
		}else{
			sendResult = ShortMessageUtil.sendRegisterMessage(mobile, vercode);
		}
//		boolean sendResult = SendMessageTool.sendSMS(mobile, sendMsg);
//		boolean sendResult = ShortMessageUtil.sendMessage(mobile, sendMsg);
		if(sendResult){
			//保存验证码发送记录
			saveVercodeSMSLog(mobile,vercode,sendMsg,vercodeType);
		}else{
			return JSONTools.newAPIResult(1, "验证码发送失败");
		}
		return JSONTools.newAPIResult(0, "验证码发送成功");
	}
	
	/**
	 * 保存发送验证码短信记录
	 * */
	private void saveVercodeSMSLog(String mobile,String verco,String sendMsg,String vercodeType){
		LpVercode vercode = new LpVercode();
		vercode.setMobileTo(mobile);
		vercode.setSendMsg(sendMsg);
		vercode.setVercode(verco);
		vercode.setSendTime(DateUtils.dateToTimestamp(new Date()));
		vercode.setStatus(0); // 0有效  1无效
		if("1".equals(vercodeType)){
			vercode.setVercodeType(1);  // 0注册码验证短信   1找回密码短信
		}else{
			vercode.setVercodeType(0);  // 0注册码验证短信   1找回密码短信
		}
		vercode.setRemark("用户注册验证码短信");
		baseDao.save(vercode);
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
