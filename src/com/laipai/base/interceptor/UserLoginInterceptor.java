package com.laipai.base.interceptor;



import java.util.Map;

import com.laipai.privilege.action.PrivilegeAction;
import com.laipai.userInfo.pojo.UserInfo;
import com.laipai.userManInfo.pojo.LpUser;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class UserLoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try{
			 //AbstractInterceptor是针对action的拦截，如果是登录action(UserLoginAction)，则放行。
			if(PrivilegeAction.class==invocation.getAction().getClass())
			{
				return invocation.invoke();
			}
			// 取得请求相关的ActionContext实例
			ActionContext ctx = invocation.getInvocationContext();
			Map session = ctx.getSession();
			LpUser userInfo = (LpUser) session.get("user");
			if (userInfo != null) {//用户登录则放行
				return invocation.invoke();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return Action.LOGIN;
	
		
	}
}

