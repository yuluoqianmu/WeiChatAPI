package com.laipai.userManInfo.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.pojo.MobileDevice;
import com.laipai.base.util.DateUtils;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.util.MD5keyBean;


/**
 * 用户注册
 * 1101
 * @author zhanhh
 */
@NotLogin
@Service("RegisterExecutorImpl")
public class RegisterExecutorImpl implements RequestExecutor {

	@Autowired
	private IBaseDao baseDao;
	
	@Override
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		String userName = JSONTools.getString(json, "userName"); //用户名即手机号
		String password = JSONTools.getString(json, "password"); //密码
		String vercode = JSONTools.getString(json, "vercode"); //手机验证码
		String nickName = JSONTools.getString(json, "nickName"); //昵称
		String token = JSONTools.getString(json, "token"); // 用户手机设备token号
		int osType = parameters.getOsType();  //系统类型 0安卓手机   1iphone 2安卓pad  3ipad
		
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(vercode)){
			return 	JSONTools.newAPIResult(1, "参数不能为空");
		}
		MD5keyBean bean = new MD5keyBean();
		password = bean.getkeyBeanofStr(password);
		userName = userName.trim();
		//校验手机号是否已经被注册
		int userNum = 0;
		try {
			userNum = baseDao.getCount("from LpUser where userName='"+userName+"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(userNum > 0){
			return 	JSONTools.newAPIResult(1, "此手机号已经被注册");
		}
		//验证验证码是否有效
		try {
			int count = baseDao.getCount("from LpVercode where vercode='"+vercode+"' and mobileTo='"+userName+"' and status=0 and vercodeType=0");
			if(count <= 0){
				return 	JSONTools.newAPIResult(1, "此验证码无效");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//保存用户入库
		LpUser user = new LpUser();
		user.setUserName(userName);
		user.setUserPassword(password);
		user.setNickName(nickName);
		user.setUserType(0); //用户类型  0普通用户
		user.setUserStatus(0); //用户状态  0有效
		user.setLoginStatus(1); //登录状态 0已登录  1未登录
		user.setRegisterTime(new Timestamp(new java.util.Date().getTime()));
		user.setAccountSource(0); //账号来源0：注册；1：微博；2：微信
		Serializable userId = baseDao.saveObjectReturnId(user);
		user.setUserId(userId.toString());
		
		//保存用户设备的token
		saveMobileDevice(userId.toString(),token,osType);
		
		JSONObject jsonUser = userToJSON(user);
		return jsonUser;
	}
	
	private JSONObject userToJSON(LpUser user){
		JSONObject json = new JSONObject();
		json.put("userId", user.getUserId());
		json.put("userName", user.getUserName());
		json.put("userPassword", user.getUserPassword());
		json.put("mobile", user.getMobile());
		json.put("userRemark", user.getUserRemark());
		json.put("userType", user.getUserType());
		return json;
	}
	
	private void saveMobileDevice(String userId,String token,int osType){
		MobileDevice device = new MobileDevice();
		device.setInsertTime(System.currentTimeMillis());
		device.setToken(token);
		device.setUserId(userId);
		device.setIsExit(0);
		device.setMobileOsType(osType);
		baseDao.save(device);
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
