package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.RegisterReq;
import com.lyz.api.bean.RegisterResp;
import com.lyz.api.config.SystemConfig;
import com.lyz.service.CheckCodeService;
import com.lyz.service.UserService;
import com.lyz.util.MD5Generator;
/**
 * 注册接口
 * 判断用户上传的验证码是否跟数据库中相同
 * @author luzi
 *
 */
public class RegisterServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(RegisterServlet.class);

	@Override
	public String getNeed2Md5(BaseReq param) {
		
		return MD5Generator.MD5(SystemConfig.key+param.getT());
	}

	@Override
	public Class getParamClass() {
		
		return RegisterReq.class;
	}

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
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		RegisterReq req = (RegisterReq)param;
		
		/*校验用户输入的验证码错误，直接返回*/
		String checkCode = CheckCodeService.getCheckCode(req.getUserName(), CheckCodeService.type_register);
		if(checkCode == null || !checkCode.equals(req.getCheckCode())){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR, "验证码过期或者输入错误！");
		}
		/*判断用户是否存在*/
		if(UserService.exist(req.getUserName())){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR,"该用户已存在");
		}
		
		/*保存用户信息*/
		String uid = UserService.saveRegisterUser(req.getUserName(), req.getPwd(), req.getNickName());
		if(uid == null){
			logger.error("register for user:"+req.getUserName());
			return new BaseResp(CodeUtil.SERVER_ERROR,"注册失败，请稍后重试");
		}
		RegisterResp resp = new RegisterResp();
		resp.setResult(CodeUtil.SUCCESS);
		resp.setUid(uid);
		resp.setUserName(req.getUserName());
		resp.setNickName(req.getNickName());
		
		return resp;
	}

}
