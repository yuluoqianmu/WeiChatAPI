package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.ModifyPwdReq;
import com.lyz.db.bean.LpUser;
import com.lyz.service.UserService;
/**
 * 修改密码
 * @author luyongzhao
 *
 */
public class ModifyPwdServlet extends BaseServlet {

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
		
		return ModifyPwdReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		ModifyPwdReq req = (ModifyPwdReq)param;
		
		if(req.getOldPwd().equals(req.getNewPwd())){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR, "新密码与老密码不能相同");
		}
		
		if(!req.getNewPwd().equals(req.getNewPwdAgain())){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR,"两次输入新密码不一致");
		}
		
		
		LpUser user = UserService.getUserInfo(req.getUid());
		/*用户不存在*/
		if(user == null){
			return new BaseResp(CodeUtil.CLIENT_ERROR, "用户不存在");
		}
		/*旧密码不正确*/
		if(!req.getOldPwd().equals(user.getUserPassword())){
			return new BaseResp(CodeUtil.CLIENT_ERROR,"老密码错误");
		}
		/*修改用户密码*/
		int count = UserService.modifyPwdByUserId(req.getUid(), req.getNewPwd());
		if(count <= 0){
			return new BaseResp(CodeUtil.SERVER_ERROR,"修改密码失败，请稍后重试");
		}
		
		return new BaseResp(CodeUtil.SUCCESS);
	}

}
