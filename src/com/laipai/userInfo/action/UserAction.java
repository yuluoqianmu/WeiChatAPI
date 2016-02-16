package com.laipai.userInfo.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.SpringContextUtil;
import com.laipai.userInfo.dto.UserInfoBean;
import com.laipai.userInfo.pojo.UserInfo;
import com.laipai.userInfo.service.IUserService;
import com.opensymphony.xwork2.ModelDriven;


/*@Results( { @Result(name = "success", location = "/mainFrame.jsp"),
        @Result(name = "error", location = "/hello.jsp") })
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") }) */
@Controller("userAction")
public class UserAction extends BaseAction implements ModelDriven<UserInfoBean>{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserAction.class);
	@Autowired
	private IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}
	
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public UserInfoBean  userInfoBean=new UserInfoBean();
	public UserInfoBean getModel() {
		return this.userInfoBean;
	}
	
	public String test(){
		logger.info("进入 test");
		userService.test();
		return SUCCESS;   
	}

	public String login(){
		IBaseDao baseDao = (IBaseDao) SpringContextUtil.getBean("baseDao");
		UserInfo userInfo = new UserInfo();
//		baseDao.delete(userInfo);
		String userName = userInfoBean.getUserName();
		userInfo.setUserName(userName);
		userInfo.setUserPass(userInfoBean.getUserPass());
//		List  user =  baseDao.getListByHQL("from UserInfo ", null);
//		String userName = request.getParameter("userName");
		String userPass = request.getParameter("userPass");
		request.setAttribute("userName", userName);
		request.setAttribute("userPass", userPass);
		return SUCCESS;
	}
	
}

