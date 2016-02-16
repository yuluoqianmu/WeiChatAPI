package com.laipai.userManInfo.app;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.util.MD5keyBean;
/**
 * 

 * @Description:修改密码

 * @author:lxd

 * @time:2015-1-12 上午11:02:09
 */
@NotLogin
@Service("ChangePasswordImpl")
public class ChangePasswordImpl implements RequestExecutor {
	@Autowired
	private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
		String oldpsw=JSONTools.getString(json,"oldPassword");
		String newpsw=JSONTools.getString(json,"newPassword");
		if(userId==null||oldpsw==null||newpsw==null){			
			return JSONTools.newAPIResult(1, "参数不能为空");	
		}
		MD5keyBean bean = new MD5keyBean();
		String md5LogonPwd = bean.getkeyBeanofStr(oldpsw);
		
		LpUser user = null;
		//根据手机号查询用户(用户登录时必须填手机号,密码)
				List<LpUser> userList = baseDao.queryListObjectByTopNum("from LpUser u where u.userId='"+userId+"' and u.userPassword='"+md5LogonPwd+"'" , 1);
				if(userList!=null && !userList.isEmpty()){
					user = userList.get(0);
				}

				if (user == null || !user.getUserPassword().equals(md5LogonPwd)) {
					return JSONTools.newAPIResult(1, "密码错误");
				}
				
		String newLogonPwd = bean.getkeyBeanofStr(newpsw);
		user.setUserPassword(newLogonPwd);
		baseDao.updateObject(user);
		JSONObject resulejson = new JSONObject();	
		resulejson.put("resultMessage", "密码成功");
		return resulejson; 
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
