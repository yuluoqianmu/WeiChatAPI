package com.laipai.userManInfo.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.laipai.privilege.service.PrivilegeService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.MD5keyBean;
import net.sf.json.JSONObject;

/**
 * 用户登录
 * 
 * @author zhh
 * 
 */
@NotLogin
@Service("UserLoginExecutorImpl")
public class UserLoginExecutorImpl implements RequestExecutor {

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
		//获取服务器域名
		String serverName = json.getString("serverName");

		String userName = JSONTools.getString(json, "userName"); //用户名即手机号(用户使用手机号注册，手机号即为userName)
		String password = JSONTools.getString(json, "password");
		String mobile = JSONTools.getString(json, "mobile");
		String token = JSONTools.getString(json, "token"); // 用户手机设备token号
 
		if (userName == null || password == null) {
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		MD5keyBean bean = new MD5keyBean();
		password = bean.getkeyBeanofStr(password);
		LpUser user = null;
		//根据手机号查询用户(用户登录时必须填手机号,密码)
		List<LpUser> userList = baseDao.queryListObjectByTopNum("from LpUser u where u.userName='"+userName+"' and u.userPassword='"+password+"'" , 1);
		if(userList!=null && !userList.isEmpty()){
			user = userList.get(0);
		}

		if (user == null || !user.getUserPassword().equals(password)) {
			return JSONTools.newAPIResult(1, "手机号/密码错误");
		}
		
		login(user,token);
		JSONObject jsonUser = userToJSON(parameters,user,serverName);
		return jsonUser;
	}
	
	/**
	 * 修改用户登录信息
	 * user : 登录的用户对象
	 * device : 用户登录使用的设备(Android,iPhone,ipad,Android Pad)
	 * */
	private void login(LpUser user,String token) {
		user.setLastActivityTime(DateUtils.dateToTimestamp(new Date())); //用户最后一次活动时间
		user.setLoginStatus(0); //登录状态: 0已登录  1未登录
		user.setLastMobileToken(token);
		baseDao.updateObject(user);
		
		//增加登录日志
		//privilegeService.logLogin(user);
	}
	
	private JSONObject userToJSON(BaseRequestParameters parameters,LpUser user,String serverName){
		JSONObject json = new JSONObject();
		json.put("userId", user.getUserId());
		json.put("userName", user.getUserName());
		json.put("userPassword", user.getUserPassword());
		json.put("mobile", user.getMobile());
		json.put("userRemark", user.getUserRemark());
		json.put("userType", user.getUserType());
		json.put("userStatus", user.getUserStatus());
//		if(StringUtils.isNotEmpty(user.getHeadImage())){
//			if(user.getHeadImage().contains("/upload/lpUserImg")){
//			json.put("headImage", user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG, serverName + LaipaiConstants.UPLOAD_VIRTUAL_IMG));
//			}else{
//				
//				json.put("headImage", user.getHeadImage());	
//			}
//		}else{
//			json.put("headImage","");
//		}
		json.put("headImage", ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
		json.put("nickName",user.getNickName());
		

		return json;
	}

	public static void main(String[] args) {
		String password ="laipai2015server";
		MD5keyBean bean = new MD5keyBean();
		password = bean.getkeyBeanofStr(password);
		System.out.println(password);
		
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
