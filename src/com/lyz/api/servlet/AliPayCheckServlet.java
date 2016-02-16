package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.AliPayCheckReq;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.service.OrderService;
/**
 * 支付宝支付确认
 * @author luyongzhao
 *
 */
public class AliPayCheckServlet extends BaseServlet {

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
		
		return AliPayCheckReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		AliPayCheckReq req = (AliPayCheckReq)param;
		
		boolean flag = OrderService.existPayRecord(req.getOrderId(), req.getPayType());
		
		if(flag){
			
			return new BaseResp(CodeUtil.SUCCESS);
		}
		
		return new BaseResp(CodeUtil.CLIENT_ERROR,"不存在指定的支付记录");
	}

}
