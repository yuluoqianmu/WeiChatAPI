package com.laipai.userManInfo.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.MD5keyBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户找回密码
 * 
 * @author zhh
 * 
 */
@NotLogin
@Service("RetrievePasswordExecutorImpl")
public class RetrievePasswordExecutorImpl implements RequestExecutor {

	@Autowired
	private IMobileDeviceDAO mobileDeviceDAO;
	
	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		String userName = JSONTools.getString(json, "userName"); //用户名即手机号(用户使用手机号注册，手机号即为userName)
		String vercode = JSONTools.getString(json, "vercode"); //验证码
		String password = JSONTools.getString(json, "password"); //新密码

		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(vercode)) {
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		
		//找回密码的验证码是否有效
		try {
			int count = baseDao.getCount("from LpVercode where vercode='"+vercode+"' and mobileTo='"+userName+"' and status=0 and vercodeType=1");
			if(count <= 0){
				return 	JSONTools.newAPIResult(1, "此验证码无效");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MD5keyBean bean = new MD5keyBean();
		password = bean.getkeyBeanofStr(password);
		LpUser user = null;
		//根据手机号查询用户(用户登录时必须填手机号,密码)
		List<LpUser> userList = baseDao.queryListObjectByTopNum("from LpUser u where u.userName='"+userName+"'", 1);
		if(userList!=null && !userList.isEmpty()){
			user = userList.get(0);
			if (user == null) {
				return JSONTools.newAPIResult(1, "手机号错误");
			}
		}else{
			return JSONTools.newAPIResult(1, "手机号错误");
		}
		
		updateUser(user,password);
		return JSONTools.newAPIResult(0, "找回密码成功");
	}
	
	/**
	 * 修改用户密码
	 * user : 登录的用户对象
	 * */
	private void updateUser(LpUser user,String password) {
		user.setUserPassword(password);
		user.setUserStatus(0);
		user.setLastActivityTime(DateUtils.dateToTimestamp(new Date())); //用户最后一次活动时间
		baseDao.updateObject(user);
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
