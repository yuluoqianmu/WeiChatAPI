package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.OperOrderReq;
import com.lyz.api.bean.OperOrderResp;
import com.lyz.db.bean.LpPayOperation;
import com.lyz.db.bean.LpPayOrder;
import com.lyz.labelData.util.BaseTypeUtil;
import com.lyz.pay.OrderStat;
import com.lyz.service.MessageService;
import com.lyz.service.OrderService;
/**
 * 操作订单状态
 * @author luyongzhao
 *
 */
public class OperOrderServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(OperOrderServlet.class);

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
		
		return OperOrderReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		OperOrderReq req = (OperOrderReq)param;
		
		long orderId = BaseTypeUtil.getLong(req.getOrderId(), -1);
		if(orderId < 0){
			return new BaseResp(CodeUtil.CLIENT_ERROR,"非法的订单id");
		}
		
		OrderStat stat = OrderStat.getValue(req.getOperStatus());
		if(stat == null){
			return new BaseResp(CodeUtil.CLIENT_ERROR,"非法的状态名称");
		}
		
		
		LpPayOperation oper = new LpPayOperation(orderId, req.getOperStatus(), stat.getName(), stat.getPreOper().getRole(), "");
		/*插入数据库*/
		boolean flag = OrderService.insertOperation(oper);
		
		if(!flag){
			return new BaseResp(CodeUtil.SERVER_ERROR,"服务器繁忙，请稍后重试");
		}
		
		/*发送通知消息*/
		if(OrderStat.ACCEPT_ORDER_BARGAIN.name().equals(req.getOperStatus())
			|| OrderStat.ACCEPT_ORDER_FULL.name().equals(req.getOperStatus())
			|| OrderStat.ACCEPT_ORDER_OFFLINE.name().equals(req.getOperStatus())){
			
				LpPayOrder order = OrderService.getOrderInfo(orderId);
				if(order != null){
					MessageService.sendMsg4AcceptOrder(order);
				}
				
		}else if(OrderStat.CANCLE_ORDER.name().equals(req.getOperStatus())){
			
			LpPayOrder order = OrderService.getOrderInfo(orderId);
			if(order != null){
				MessageService.sendMsg4CancelOrder(order.getBuyerId(), order.getCameraName(), false, order);
			}
		}else if(OrderStat.CANCLE_ORDER_OFFLINE.name().equals(req.getOperStatus())){
			
			LpPayOrder order = OrderService.getOrderInfo(orderId);
			if(order != null){
				MessageService.sendMsg4CancelOrder(order.getBuyerId(), order.getCameraName(), true, order);
			}
		}else if(OrderStat.APPLY_REFUND.name().equals(req.getOperStatus())){
			
			LpPayOrder order = OrderService.getOrderInfo(orderId);
			if(order != null){
				MessageService.sendMsg4ApplyRefund(order.getBuyerName(), order.getCameraId());
			}
		}else if(OrderStat.ACK.name().equals(req.getOperStatus())){
			
			LpPayOrder order = OrderService.getOrderInfo(orderId);
			if(order != null){
				MessageService.sendMsg4FinishShoot(order);
			}
		}
		
		/*插入成功之后，获取订单详情*/
		
		OperOrderResp resp = OrderService.getOrderDetail(orderId, stat.getPreOper().getRole());
		
		if(resp == null){
			return new BaseResp(CodeUtil.SERVER_ERROR,"服务器繁忙，请稍后重试");
		}
		
		return resp;
		
	}

}
