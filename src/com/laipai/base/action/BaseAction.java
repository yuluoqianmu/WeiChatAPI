package com.laipai.base.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.IBaseService;
import com.laipai.filter.LoginFilter;
import com.laipai.userInfo.pojo.UserInfo;
import com.laipai.userManInfo.pojo.LpUser;
import com.opensymphony.xwork2.ActionSupport;


public class BaseAction extends ActionSupport implements ServletRequestAware,
ServletResponseAware {

    public Logger logger = Logger.getLogger(BaseAction.class);
	
	public IBaseService  baseService;
	
	public HttpServletRequest request;
	
	public HttpServletResponse response;
	
	/**
	 * 
	 * @Description: TODO 设值注入
	 *
	 * @Title: setBaseService  
	 * @param @param baseService    
	 * @return void    
	 * @author:  chenguangyuan
	 * @date: Aug 17, 2012 11:08:18 AM 
	 * @throws
	 */
    public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
    /**
     * 
     * @Description: TODO 设置response
     *
     * @Title: getRequest  
     * @param @return    
     * @return HttpServletRequest    
     * @author:  chenguangyuan
     * @date: Aug 17, 2012 10:17:09 AM 
     * @throws
     */
	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
		
	}
	/**
     * 
     * @Description: TODO 设置request
     *
     * @Title: getRequest  
     * @param @return    
     * @return HttpServletRequest    
     * @author:  chenguangyuan
     * @date: Aug 17, 2012 10:17:09 AM 
     * @throws
     */
	
	public void setServletRequest(HttpServletRequest arg0) {
		
		this.request=arg0;
	}
	/**
     * 
     * @Description: TODO 获取request从ServletActionContext
     *
     * @Title: getRequest  
     * @param @return    
     * @return HttpServletRequest    
     * @author:  chenguangyuan
     * @date: Aug 17, 2012 10:17:09 AM 
     * @throws
     */
	public HttpServletRequest getRequest() {
		if(null==this.request){
			this.request = ServletActionContext.getRequest();
		}
		return request;
	}
    /**
     * 
     * @Description: TODO 获取session
     *
     * @Title: getSession  
     * @param @return    
     * @return HttpSession    
     * @author:  chenguangyuan
     * @date: Aug 17, 2012 10:18:09 AM 
     * @throws
     */
	public HttpSession getSession(){
		return this.request.getSession();
	}
	/**
	 * 
	 * @Description: 获取response从ServletActionContext
	 *
	 * @Title: getResponse  
	 * @param @return    
	 * @return HttpServletResponse    
	 * @author:  chenguangyuan
	 * @date: Aug 17, 2012 10:19:06 AM 
	 * @throws
	 */
	public HttpServletResponse getResponse() {
		if(null==this.response){
			this.response = ServletActionContext.getResponse();
		}
		return response;
	}
	/**
	 * 
	 * @Description: TODO 获取pringtWriter
	 *
	 * @Title: getPrintWriter  
	 * @param @return    
	 * @return PrintWriter    
	 * @author:  chenguangyuan
	 * @date: Aug 22, 2012 2:25:48 PM 
	 * @throws
	 */
	public  PrintWriter getPrintWriter() {
		PrintWriter pw = null;
		try {
			pw = this.response.getWriter();
		} catch (Exception e) {
			return pw;
		}
		return pw;
	}
	/**
	 * 
	 * 描述: 获得当前用户
	 *
	 * @Title: getCurrentUser  
	 * @return    
	 * @return UserInfo    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-25 上午9:44:52
	 */
	protected LpUser getCurrentUser(){
		String user=LoginFilter.getFilterConfig().getInitParameter("user");
	     return (LpUser) getSession().getAttribute(user);
	}
	/**
	 * 
	 * 描述: 设置当前用户
	 *
	 * @Title: getCurrentUser  
	 * @return    
	 * @return UserInfo    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-25 上午9:44:52
	 */
	protected void setCurrentUser(LpUser userInfo){
		String user=LoginFilter.getFilterConfig().getInitParameter("user");
		getSession().setAttribute(user, userInfo);
	}
	
	protected void clearCurrentUser(){
		String user=LoginFilter.getFilterConfig().getInitParameter("user");
		getSession().removeAttribute(user);
	}
	
}
