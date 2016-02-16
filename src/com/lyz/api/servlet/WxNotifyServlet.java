package com.lyz.api.servlet;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.lyz.api.bean.AliPayReq;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.WxNotifyReq;
import com.lyz.api.bean.WxNotifyResp;
import com.lyz.api.file.FileTool;
import com.lyz.db.bean.LpPayOperation;
import com.lyz.db.bean.LpPayOrder;
import com.lyz.pay.Body;
import com.lyz.pay.OrderStat;
import com.lyz.pay.PayAttachData;
import com.lyz.pay.PayType;
import com.lyz.service.MessageService;
import com.lyz.service.OrderService;
import com.lyz.util.BaseTypeUtil;
import com.lyz.validate.ValidateException;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tenpay.RequestHandler;
import com.tenpay.ResponseHandler;
import com.tenpay.client.TenpayHttpClient;
/**
 * 微信支付回调接口
 * @author luyongzhao
 *
 */
public class WxNotifyServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(WxNotifyServlet.class);

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

		return WxNotifyReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		WxNotifyReq req = (WxNotifyReq)param;
		
		/*判断校验值是否正确*/
		String sign = null;
		try {
			sign = Signature.getSign(req);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*如果签名正确*/
		if(sign!=null && sign.equals(req.getSign())){
			
			/*此处需要根据微信支付订单号去微信获取相应的支付信息，验证是否回调来自微信*/
			
			if("SUCCESS".equals(req.getReturn_code()) && "SUCCESS".equals(req.getResult_code())){
				
				logger.info("wexin: success to call back!");
				
				/*订单额外信息*/
				Body body = gson.fromJson(req.getAttach(), Body.class);
				
				if(body == null){					
					logger.error("unknown pay type:"+req.getAttach());
					WxNotifyResp resp = new WxNotifyResp("FAIL", "INVALID SIGN");
					return Util.getXMLFromObject(resp, resp.getClass());
				}
				
				boolean flag = doOper(body, req);
				
				if(!flag){
					logger.error("fail to handle weixin callback!");
					WxNotifyResp resp = new WxNotifyResp("FAIL", "INVALID SIGN");
					return Util.getXMLFromObject(resp, resp.getClass());
				}
				
				WxNotifyResp resp = new WxNotifyResp("SUCCESS", "OK");
				return Util.getXMLFromObject(resp, resp.getClass());
			}else{
				logger.info("wexin: fail to call back,returnCode="+req.getResult_code()+",resultCode="+req.getResult_code());
			}			
			
		}else{
			logger.info("wexin: invalid sign!");
		}
		
		
		WxNotifyResp resp = new WxNotifyResp("FAIL", "INVALID SIGN");
		return Util.getXMLFromObject(resp, resp.getClass());
	}
	
	/**
	 * 执行操作
	 * @param body
	 * @param req
	 * @param orderId
	 * @return
	 */
	private boolean doOper(Body body, WxNotifyReq req){
		OrderStat orderStatus = null;
		if(body.getPt() == PayType.PAY_ALL){
			orderStatus = OrderStat.SUC_PAY_FULL;
		}else if(body.getPt() == PayType.PAY_BARGAIN){
			orderStatus = OrderStat.SUC_PAY_BARGAIN;
		}else{
			logger.error("invalid payType:"+body.getPt());
			return false;
		}
		/*组装买家信息*/
		PayAttachData data = new PayAttachData("weixin", null, req.getOpenid(), null,BaseTypeUtil.getInteger(req.getTotal_fee(), -1),req.getTransaction_id(),body.getPt());
		 /*根据*/
	  	 boolean isFlag = OrderService.insertOperation(new LpPayOperation(BaseTypeUtil.getLong(req.getOut_trade_no(), -1), orderStatus.name(), orderStatus.getName(), orderStatus.getPreOper().getRole(), gson.toJson(data)));
	  	 
	  	 if(isFlag){
	  		 
	  		/*支付成功，短信通知*/
	  		 LpPayOrder order = OrderService.getOrderInfo(BaseTypeUtil.getLong(req.getOut_trade_no(), -1));
	  		 if(order != null){
	  			MessageService.sendMsg4AddOrderOnline(order.getBuyerName(), order.getCameraId(), body.getPt(), BaseTypeUtil.getDouble(req.getTotal_fee(), -1)/100);
	  		 }
	  		 
	  		 return true;
	  	 }
	  	 
	  	 return true;
	}
	/**
	 * 忽略请求参数校验
	 * @param req
	 * @return
	 */
	@Override
	protected BaseResp validateParam(BaseReq req){
				
		return null;
	}
	/**
	 * 忽略校验
	 */
	public String getNeed2Md5(BaseReq param){
		return null;
	}
	@Override
	public BaseReq getJsonData(HttpServletRequest req, Class clazz){
		logger.info("wexin: prepare to call back!");
		String value = null;
		try {
			InputStream in = req.getInputStream();

			int len = -1;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			byte[] data = baos.toByteArray();

			value = new String(data, "utf-8");
			if(value==null || "".equals(value.trim())){
				return null;
			}
			String noFormatVal = value.replace("\n", "");
			/*计入日志*/
			FileTool.logToFile(noFormatVal, Configure.logPath, "wxPay_"+BaseTypeUtil.getStrDateTime(System.currentTimeMillis(), "yyyy-MM-dd")+".txt");
			logger.info("wexin: call back param:"+noFormatVal);
			return (WxNotifyReq)Util.getObjectFromXML(value, WxNotifyReq.class);
		} catch (Exception e) {
			logger.error("fail to parse post req to xml data:"+value,e);
		}
		return null;
	}

}
