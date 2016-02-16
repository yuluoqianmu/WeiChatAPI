package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.ShowOrderListReq;
import com.lyz.api.bean.ShowOrderListResp;
import com.lyz.service.OrderService;
/**
 * 显示订单列表
 * @author luyongzhao
 *
 */
public class ShowOrderListServlet extends BaseServlet {

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
		
		return ShowOrderListReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		ShowOrderListReq req = (ShowOrderListReq)param;
		
		ShowOrderListResp resp = OrderService.getOrderList(req.getUserId(), req.getRole(), req.getOrderStatus(), req.getPageNo(), req.getPageSize(), "1".equals(req.getHasStat()));
		
		if(resp == null){
			
			return new BaseResp(CodeUtil.SUCCESS);
		}
		
		return resp;
	}

}
