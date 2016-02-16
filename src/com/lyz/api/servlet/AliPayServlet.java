package com.lyz.api.servlet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alipay.util.AlipayNotify;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.util.BaseTypeUtil;
import com.lyz.api.bean.AliPayReq;
import com.lyz.api.bean.BaseReq;
import com.lyz.db.bean.LpPayOperation;
import com.lyz.db.bean.LpPayOrder;
import com.lyz.labelData.json.GsonExt;
import com.lyz.pay.Body;
import com.lyz.pay.OrderStat;
import com.lyz.pay.PayAttachData;
import com.lyz.pay.PayType;
import com.lyz.service.MessageService;
import com.lyz.service.OrderService;

/**
 * 支付宝回调接口
 * @author luyongzhao
 *
 */
public class AliPayServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(AliPayServlet.class);
	
	private static final GsonExt gson = new GsonExt();

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
		
		return AliPayReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		AliPayReq req = (AliPayReq)param;
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号
		long orderId = com.lyz.util.BaseTypeUtil.getLong(req.getOut_trade_no(), -1);
		if(orderId <= 0){
			logger.error("invalid orderId:"+req.getOut_trade_no());
			return "fail";
		}
		//支付宝交易号
		String trade_no = req.getTrade_no();
		//交易状态
		String trade_status = req.getTrade_status();
		/*订单额外信息*/
		Body body = gson.fromJson(req.getBody(), Body.class);
		
		if(body == null){
			
			logger.error("unknown pay type:"+req.getBody());
			return "fail";
		}

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		/*验证是否是支付宝的异步通知*/
		if(AlipayNotify.verify(params)){//验证成功

			if(trade_status.equals("TRADE_FINISHED")){
			
				logger.info("trade finish!orderId:"+orderId+",tradeNo:"+trade_no);
				//判断该笔订单是否在商户网站中已经做过处理
				return doOper(body, req, orderId);
				//注意：
				//该种交易状态只在两种情况下出现
				//1、开通了普通即时到账，买家付款成功后。
				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					logger.info("trade success, orderId："+orderId+",tradeNo："+trade_no);
					return doOper(body, req, orderId);
				//判断该笔订单是否在商户网站中已经做过处理
				//注意：
				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
			} else{
				logger.info("other status, orderId："+orderId+",tradeNo："+trade_no+",tradeStatus："+trade_status);
			}
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			return "success";	//请不要修改或删除

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			logger.info("fail to verify!");
			return "fail";
		}
	}
	/**
	 * 覆盖父类的方法，获取post请求的参数对
	 */
	public BaseReq getJsonData(HttpServletRequest req, Class clazz){
		
		return initParam4Post(req, clazz);
	}
	
	@Override
	public String getNeed2Md5(BaseReq param) {
		
		return null;
	}
	/**
	 * 执行操作
	 * @param body
	 * @param req
	 * @param orderId
	 * @return
	 */
	private String doOper(Body body, AliPayReq req, long orderId){
		OrderStat orderStatus = null;
		if(body.getPt() == PayType.PAY_ALL){
			orderStatus = OrderStat.SUC_PAY_FULL;
		}else if(body.getPt() == PayType.PAY_BARGAIN){
			orderStatus = OrderStat.SUC_PAY_BARGAIN;
		}else{
			logger.error("invalid payType:"+body.getPt());
			return "fail";
		}
		/*组装买家信息*/
		PayAttachData data = new PayAttachData("zhifubao", req.getBuyer_email(), req.getBuyer_id(), req.getNotify_id(),(int)(req.getTotal_fee()*100),req.getTrade_no(),body.getPt());
		 /*根据*/
	  	 boolean isFlag = OrderService.insertOperation(new LpPayOperation(orderId, orderStatus.name(), orderStatus.getName(), orderStatus.getPreOper().getRole(), gson.toJson(data)));
	  	 
	  	 if(isFlag){
	  		 
	  		 /*支付成功，短信通知*/
	  		 LpPayOrder order = OrderService.getOrderInfo(orderId);
	  		 if(order != null){
	  			MessageService.sendMsg4AddOrderOnline(order.getBuyerName(), order.getCameraId(), body.getPt(), req.getTotal_fee());
	  		 }
	  		 	  		 
	  		 return "success";
	  	 }
	  	 
	  	 return "fail";
	}
	
}
