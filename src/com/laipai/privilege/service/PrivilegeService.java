package com.laipai.privilege.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.laipai.base.service.IBaseService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
public interface PrivilegeService extends IBaseService {

	public List<LpUser> queryUser(String userName);
	
	public boolean change2Admin(String userId);
	
	public boolean addPrivilege(String[] resourceIds, String userId, LpUser operatpr);
	
	public List<String> getResourceByUserId(String userId);
	
	public LpUser verifyUserLogin(String userName, String password, String verifyCode, HttpServletRequest request);

	public List<LpUserBean> newQuseryUser(HttpServletRequest request);
	
	public void logLogin(LpUser operatpr);
	public void logLogout(LpUser operatpr);
}
