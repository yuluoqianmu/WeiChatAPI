package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.FindPwdReq;
import com.lyz.service.CheckCodeService;
import com.lyz.service.UserService;
/**
 *  找回密码
 * @author luyongzhao
 *
 */
public class FindPwdServlet extends BaseServlet {

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
		
		return FindPwdReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		FindPwdReq req = (FindPwdReq)param;
		
		/*验证验证码是否正确*/
		String checkCode = CheckCodeService.getCheckCode(req.getUserName(), CheckCodeService.type_find_pwd);
		if(checkCode == null || !checkCode.equals(req.getCheckCode())){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR, "验证码过期或者错误，请稍后重试");
		}
		
		/*验证通过，更新密码*/
		int count = UserService.modifyPwd(req.getUserName(), req.getPwd());
		/*更新失败*/
		if(count == 0){
			
			return new BaseResp(CodeUtil.SERVER_ERROR, "修改密码失败，请稍后重试");
		}
		
		return new BaseResp(CodeUtil.SUCCESS);
		
	}

}
