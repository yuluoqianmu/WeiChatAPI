package com.laipai.userManInfo.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.Global;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.TokenUtil;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.pojo.MobileDevice;
import com.laipai.base.util.DateUtils;
import com.laipai.privilege.service.PrivilegeService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.MD5keyBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户退出登录
 * 
 * @author zhh
 * 
 */
@NotLogin
@Service("UserLogoutExecutorImpl")
public class UserLogoutExecutorImpl implements RequestExecutor {

	@Autowired
	private IMobileDeviceDAO mobileDeviceDAO;
	
	@Autowired
	private IBaseDao baseDao;
	
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userService;
	public IUserManInfoService getUserService() {
		return userService;
	}
	public void setUserService(IUserManInfoService userService) {
		this.userService = userService;
	}
	
	@Autowired
	private PrivilegeService privilegeService;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		String userId = JSONTools.getString(json, "userId"); //用户名即手机号(用户使用手机号注册，手机号即为userName)
		
		if (userId == null) {
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		LpUser user = (LpUser) baseDao.getObjectById(LpUser.class, userId);
		if(user ==null){
			return JSONTools.newAPIResult(1, "userId is error");
		}
		user.setLoginStatus(1);
		baseDao.updateObject(user);
		
		return JSONTools.newAPIResult(0, "退出成功");
	}
	
	/**
	 * 修改用户登录信息
	 * user : 登录的用户对象
	 * device : 用户登录使用的设备(Android,iPhone,ipad,Android Pad)
	 * */
	private void login(LpUser user) {
		user.setLastActivityTime(DateUtils.dateToTimestamp(new Date())); //用户最后一次活动时间
		user.setLoginStatus(0); //登录状态: 0已登录  1未登录
		baseDao.updateObject(user);
		
		//记录退出事件
		privilegeService.logLogout(user);
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
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
