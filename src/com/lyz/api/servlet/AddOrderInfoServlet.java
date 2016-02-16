package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alipay.config.AlipayConfig;
import com.laipai.message.util.PushNoticeMessage;
import com.lyz.api.bean.AddOrderInfoResp;
import com.lyz.api.bean.AliPrePayInfoResp;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.WxPrePayInfoResp;
import com.lyz.api.bean.addOrderInfoReq;
import com.lyz.db.bean.LpServiceDetail;
import com.lyz.db.bean.LpUser;
import com.lyz.labelData.json.GsonExt;
import com.lyz.pay.Body;
import com.lyz.pay.PayType;
import com.lyz.service.MessageService;
import com.lyz.service.OrderService;
import com.lyz.service.ServiceService;
import com.lyz.service.UserService;
import com.lyz.util.LaiPaiEntityIdGenerator;
import com.tencent.WXPay;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.pre_pay_protocol.PrePayReqData;
import com.tencent.protocol.pre_pay_protocol.PrePayRespData;
/**
 * 提交订单
 * @author luyongzhao
 *
 */
public class AddOrderInfoServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(AddOrderInfoServlet.class);
	
	private static final GsonExt gson = new GsonExt();
	
	private static final PushNoticeMessage pusher = new PushNoticeMessage();
	
	private static final int ONE_HOUR = 1000*60*60;
	
	

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
		
		return addOrderInfoReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		addOrderInfoReq req = (addOrderInfoReq)param;
		
		if(req.getServicePrice() < 0){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR,"服务金额不能小于0");
		}
		
		/*一小时之内同一个用户对同一个同一个摄影师的同一个服务只能下单一次*/
//		if(OrderService.getOrderCount(req, ONE_HOUR) > 0){
//			
//			return new BaseResp(CodeUtil.CLIENT_ERROR, "1小时内不能重复预约，请前往您的个人主页查看您的预约详情。");
//		}
		
		/*添加订单信息*/
		long orderId = OrderService.addOrder(req);
		
		if(orderId < 0){
			return new BaseResp(CodeUtil.SERVER_ERROR, "保存订单失败!");
		}
		/*线下支付发送提示信息*/
		if(req.getPayType() == PayType.PAY_OFFLINE){
			MessageService.sendMsg4AddOrderOffline(req.getBuyerId(), req.getCameraId());
		}
				
		/*返回订单号*/
		return new AddOrderInfoResp(CodeUtil.SUCCESS, orderId+"");

	}
	
	

}
