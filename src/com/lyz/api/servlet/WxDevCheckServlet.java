package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.WxDevCheckReq;
/**
 * 微信开发者回调接口
 * @author luyongzhao
 *
 */
public class WxDevCheckServlet extends BaseServlet {

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
		
		return WxDevCheckReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		WxDevCheckReq req = (WxDevCheckReq)param;
		
		return req.getEchostr();
	}
	
	@Override
	public String getNeed2Md5(BaseReq param) {
		
		return null;
	}


}
