package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.lyz.util.SendMessageTool;
import com.lyz.util.ShortMessageUtil;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CheckCodeReq;
import com.lyz.api.bean.CodeUtil;
import com.lyz.service.CheckCodeService;
/**
 * 验证码类型
 * @author luzi
 *
 */
public class CheckCodeServlet extends BaseServlet {
	
	private static final int TWO_MINUTE = 120;
	
	private static final Logger logger = Logger.getLogger(CheckCodeServlet.class);

	@Override
	public String getNeed2Md5(BaseReq param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getParamClass() {
		
		return CheckCodeReq.class;
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
		
		CheckCodeReq req = (CheckCodeReq)param;
		
		/*生成注册码*/
		String checkCode = SendMessageTool.getRandomNum();
		/*保存注册码,有效时间2分钟*/
		boolean flag = CheckCodeService.saveCheckCode(req.getUserName(), req.getCheckType(), checkCode, TWO_MINUTE);
		/*保存失败，返回服务器错误*/
		if(!flag){
			logger.error("fail to save check code!");
			return new BaseResp(CodeUtil.SERVER_ERROR, "验证码获取失败，请稍后重试");
		}
		/*发送短信给用户*/
		boolean isSuc = ShortMessageUtil.sendRegisterMessage(req.getUserName(), checkCode);
		/*发送失败*/
		if(!isSuc){
			return new BaseResp(CodeUtil.SERVER_ERROR,"验证码发送失败，请稍后重试");
		}
		
		return new BaseResp(CodeUtil.SUCCESS);
	}

}
