package com.laipai.privilege.action;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.privilege.pojo.LpResource;
import com.laipai.privilege.service.PrivilegeService;
import com.laipai.privilege.service.ResourceService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.MD5keyBean;

@Service("privilegeAction")
public class PrivilegeAction extends BaseAction{

	public Logger logger = Logger.getLogger(PrivilegeAction.class);
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	@Autowired
	private IBaseDao baseDao;
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	private String userName;
	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	private String userPassword;
	
	private String verifyCode;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String userId;
	
	private String result;  
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private ResourceService resourceService;

	public  String userList(){
		
		System.out.println("$$ ===================== session user = " + request.getSession().getAttribute("user"));
		
		try
		{
		
		/*	List<LpUserBean> userList= privilegeService.queryUser(null);*/
			List<LpUserBean> userList= privilegeService.newQuseryUser(request);
			
			System.out.println("userList = " + userList);
			
			request.setAttribute("userList", userList);
			
			List<LpResource> resourceList = resourceService.queryAllResource();
			System.out.println("resourceList = " + resourceList);
			request.setAttribute("resourceList", resourceList);
			
			List<LpResource> resourceList2 = resourceService.queryAllResource();
			request.setAttribute("resourceList2", resourceList2);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public  String change2Admin(){
		System.out.println("=================== userId = " + userId);
		
		boolean result = privilegeService.change2Admin(userId);
		
		if(result == true)
		{
			return SUCCESS;
		}
		else
		{
			return ERROR;
		}		
	}
	
	public  String addPrivilege(){
		

		String[] resourceIds = request.getParameterValues("ResourceId");
		
		System.out.println("userId = " + userId);
		System.out.println("resourceId = " + Arrays.asList(resourceIds));
		
		boolean result = privilegeService.addPrivilege(resourceIds, userId, null);
		
		if(result == true)
		{
			return SUCCESS;
		}
		else
		{
			return ERROR;
		}		
	}
	
	public String getResourceByUserId()
	{
		System.out.println("1.userId = " + userId + ", json = " + this.result);
		
		try
		{
		
		List<String> resourceIds = privilegeService.getResourceByUserId(userId);
		JSONArray obj = JSONArray.fromObject(resourceIds);  
		this.result = obj.toString();
		
		System.out.println("2.userId = " + userId + ", json = " + this.result);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	public String login()
	{
		System.out.println("login userName = " + userName + ", psw = " + this.userPassword + ", verifyCode = " + verifyCode);
		try
		{
			LpUser result = privilegeService.verifyUserLogin(userName, userPassword, verifyCode, request);
			request.getSession().setAttribute("admin", result);
			System.out.println("login result = " + result);
			
			if(result == null)
			{
				System.out.println("login result = false");
				return ERROR;
			}else{
				privilegeService.logLogin(result);
				setCurrentUser(result);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String logout()
	{
		
		
		HttpSession session=getSession();
		logger.info("退出登录, session id = " + (session!= null? session.getId():""));
		try{
			
			LpUser  user = (LpUser)getCurrentUser();
			if(user != null)
			{
				//TODO 写日志
				logger.info("退出登录, session id = " + (session!= null? session.getId():"" + ", userId = " + user.getUserId()));
				privilegeService.logLogout(user);
			}
			clearCurrentUser();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(session != null)
		{
			session.invalidate();
		}
		
		return SUCCESS;
	}
	   /**
	    *  
	   
	    * @Description:添加管理员
	   
	    * @return
	   
	    * String
	   
	    * @exception:
	   
	    * @author: lxd
	   
	    * @time:2015-3-23 下午10:34:07
	    */
	   public String addEmployee(){
		   String userName=request.getParameter("userName"); 
		   String userPassword=request.getParameter("userPassword"); 
		   String userEmail=request.getParameter("userEmail");
		   String realName=request.getParameter("realName");
		   MD5keyBean bean = new MD5keyBean();
		   String md5LogonPwd = bean.getkeyBeanofStr(userPassword);
		   LpUser user=new LpUser();
		   user.setUserName(userName);
		   user.setUserPassword(md5LogonPwd);
		   user.setUserEmail(userEmail);
		   user.setRealName(realName);
		   user.setUserType(2);
		   userManService.saveOrUpdateser(user);
		   return "toqueryall";
	   } 
	   public  String toeditUser(){
			String userId=request.getParameter("userId");
			LpUser user= userManService.queryUserById(userId);
			request.setAttribute("user",user);
			return "toedit";
		}
	   
	   public  String changeemployee(){
				String userId=request.getParameter("userId");
				LpUser user= userManService.queryUserById(userId);
				String userEmail=request.getParameter("userEmail");
				String realName=request.getParameter("realName");
				user.setUserEmail(userEmail);
				user.setRealName(realName);
				 userManService.saveOrUpdateser(user);
				return "toqueryall";
			}
	   
	   public String deleteEmployee(){
		   String userId=request.getParameter("userId");
		   LpUser user= (LpUser) baseDao.getObjectById(LpUser.class, userId);
			
		   baseDao.delete(user);
		   return "toqueryall";
	   }
}
