package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.LoginReq;
import com.lyz.api.bean.LoginResp;
import com.lyz.db.bean.LpUser;
import com.lyz.service.UserService;
import com.lyz.util.MD5Generator;
/**
 * 登陆接口
 * @author luyongzhao
 *
 */
public class LoginServlet extends BaseServlet {

	@Override
	public String getStringToFile(BaseReq req, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getParamClass() {
		
		return LoginReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		LoginReq req = (LoginReq)param;
		
		LpUser user = UserService.login(req.getUserName(), req.getPwd(),req.getToken());
		/*登录失败*/
		if(user == null){
			return new BaseResp(CodeUtil.CLIENT_ERROR,"用户名或者密码错误");
		}
		/*返回登录数据*/
		LoginResp resp = new LoginResp();
		resp.setResult(CodeUtil.SUCCESS);
		resp.setUserId(user.getUserId());
		resp.setUserType(user.getUserType());
		resp.setToken(UserService.getToken(req.getUserName(), req.getPwd()));
		return resp;
	}

}
