package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.OperOrderResp;
import com.lyz.api.bean.ShowOrderDetailReq;
import com.lyz.labelData.util.BaseTypeUtil;
import com.lyz.service.OrderService;
/**
 * 显示订单详情
 * @author luyongzhao
 *
 */
public class ShowOrderDetailServlet extends BaseServlet {

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
		
		return ShowOrderDetailReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		ShowOrderDetailReq req = (ShowOrderDetailReq)param;
		
		long orderId = BaseTypeUtil.getLong(req.getOrderId(), -1);
		if(orderId < 0){
			return new BaseResp(CodeUtil.CLIENT_ERROR,"非法的订单id");
		}
		
		/*根据用户id和订单id判断是否为合法请求*/
		boolean flag = OrderService.existOrder(orderId, req.getRole(), req.getUserId());
		
		if(!flag){
			return new BaseResp(CodeUtil.CLIENT_ERROR, "不存在指定的订单");
		}
		
		/*获取订单详情*/		
		OperOrderResp resp = OrderService.getOrderDetail(orderId, req.getRole());
		
		if(resp == null){
			return new BaseResp(CodeUtil.SERVER_ERROR,"服务器繁忙，请稍后重试");
		}
		
		return resp;
	}

}
