package com.laipai.privilege.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.privilege.dao.PrivilegeDao;
import com.laipai.privilege.pojo.LpResource;
import com.laipai.privilege.service.PrivilegeService;
import com.laipai.privilege.service.ResourceService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpLoginHis;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.MD5keyBean;

@Service("privilegeService")
public class PrivilegeServiceImpl extends BaseServiceImpl implements PrivilegeService {

	private static final Logger logger=Logger.getLogger(PrivilegeServiceImpl.class);
	
	private String loginUserSessionKey = PrivilegeServiceImpl.class.getName() + ".LOGIN_USER";
	
	@Autowired
	private IBaseDao baseDao;
	
	@Autowired
	private PrivilegeDao privilegeDao;
	
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userService;

	@Autowired
	private ResourceService resourceService;

	
	
	@Override
	public List<LpUser> queryUser(String userName) {
		return userService.queryAll();
	}

	@Override
	public boolean change2Admin(String userId) {
		
		privilegeDao.change2Admin(userId);
		
		return true;
	}

	@Override
	public boolean addPrivilege(String[] resourceIds, String userId, LpUser operatpr) {
		//删除原有权限		
		//插入新权限
		
		return privilegeDao.addPrivilege(resourceIds, userId, operatpr);
	}

	@Override
	public List<String> getResourceByUserId(String userId) {
		return privilegeDao.getResourceByUserId(userId);
	}
	
	public LpUser verifyUserLogin(String userName, String password, String verifyCode, HttpServletRequest request){
		
		LpUser result = null;
		String v = (String) request.getSession().getAttribute("rand");
		
		if(verifyCode.equals(v) == false)
		{
			logger.error("验证码错误, user = " + userName);	
			request.setAttribute("loginerror", "验证码错误");
			return result;
		}
		
		
		LpUser user = userService.queryUserByName(userName);
		if(user == null)
		{
			logger.error("nouser = " + userName);	
			request.setAttribute("loginerror", "用戶名或密码错误");
			return result;
		}		
		
		//用户类型为2或者3的用户才允许后台登录
		if(user.getUserType() != 2 && user.getUserType() != 3)
		{
			logger.error("user " + userName + " userType = " + user.getUserType() + " can not login from Browser");	
			request.setAttribute("loginerror", "不允许该用户登录");
			return null;
		}
		
		//logger.info(" login user = " + user + ", userType = " + user.getUserType());
		MD5keyBean bean = new MD5keyBean();
		String md5LogonPwd = bean.getkeyBeanofStr(password);
		//logger.info("md5 input = " + md5LogonPwd);
		//logger.info("md5 user = " + user.getUserPassword());
		
		if(user != null && user.getUserPassword().equals(md5LogonPwd))
		{
			
			if(user.getUserType() == 2)
			{
				result=user;
				
				//查询所有权限
				List<String> userMenuList = getResourceByUserId(user.getUserId());
				if(userMenuList != null && userMenuList.size() > 0)
				{
					//logger.info(" $$ userMenu: size = " + userMenuList.size() + ", menu = " + Arrays.asList(userMenuList));
				}
				else
				{
					//logger.info(" $$ userMenu is null ");
				}
				
				
				if(userMenuList == null || userMenuList.size() == 0)
				{
					request.setAttribute("loginerror", "对不起您没有权限，不能使用本系统");
					return null;
				}
				
				List<LpResource> allMenu = resourceService.queryAllResource();
				//logger.info(" $$ allMenu = " + allMenu);			
				if(allMenu != null && allMenu.size() > 0)
				{
					
					List<LpResource> userMenu = new ArrayList<LpResource>();
					
					for(LpResource resource: allMenu)
					{
						if(userMenuList.contains(resource.getResourceId()))
						{
							userMenu.add(resource);
						}
					}
					
					//logger.info(" $$ userMenu = " + userMenu);			
					request.getSession().setAttribute("usermenu", userMenu);
				}
				else
				{
					//logger.info("您没有权限，无法登录");
					request.setAttribute("loginerror", "您没有权限，无法登录");
					return null;
				}
			}			
			else if(user.getUserType() == 3)
			{
				//超级管理员
				
				result=user;
				
				
				
				List<LpResource> allMenu = resourceService.queryAllResource();
				if(allMenu != null && allMenu.size() > 0)
				{
					//logger.info(" $$ allMenu = " + allMenu);	
					request.getSession().setAttribute("usermenu", allMenu);
				}
				else
				{
					logger.info(" $$ allMenu is null ");
				}
			}
			
			return result;
			
		}
		else
		{
			
			logger.info(" login failed : user = " + user + ", verifyCode = " + verifyCode + ", md5 = " + md5LogonPwd + ", usermd5 = " + user.getUserPassword());
			request.setAttribute("loginerror", "用户名或密码错误");
			return null;
		}
		
		
	}

	@Override
	public List<LpUserBean> newQuseryUser(HttpServletRequest request) {
		/**
		 * add by LXD on$2014-12-22
		 * 分页查询文章列表
		 * */
		String hql = "from LpUser L where L.userType=2 order by L.registerTime desc";
		try {
			List list = querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void logLogin(LpUser user) {
		LpLoginHis his = new LpLoginHis();
		his.setLpUser(user);
		his.setLoginTime(new Timestamp(System.currentTimeMillis()));
		baseDao.save(his);
	}

	@Override
	public void logLogout(LpUser user) {
		LpLoginHis his = new LpLoginHis();
		his.setLpUser(user);
		his.setLoginTime(user.getLastActivityTime());
		his.setLogoutTime(new Timestamp(System.currentTimeMillis()));
		baseDao.save(his);
	}

}
