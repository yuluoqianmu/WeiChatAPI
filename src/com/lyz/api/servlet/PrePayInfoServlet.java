package com.lyz.api.servlet;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.alipay.config.AlipayConfig;
import com.alipay.util.SignUtils;
import com.lyz.api.bean.AliPrePayInfoResp;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.PrePayInfoReq;
import com.lyz.api.bean.WxPrePayInfoResp;
import com.lyz.pay.Body;
import com.lyz.util.LaiPaiEntityIdGenerator;
import com.tencent.WXPay;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.pre_pay_protocol.PrePayReqData;
import com.tencent.protocol.pre_pay_protocol.PrePayRespData;
/**
 * 支付前需要获取的支付参数
 * @author luyongzhao
 *
 */
public class PrePayInfoServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(PrePayInfoReq.class);
	
	/*阿里支付（支付宝）*/
	private static final int PAY_TOOL_ALIPAY = 1;
	/*微信支付*/
	public static final int PAY_TOOL_WEIXIN = 2;

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
		
		return PrePayInfoReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		PrePayInfoReq req = (PrePayInfoReq)param;
		
//		LpServiceDetail detail = ServiceService.getServiceDetail(req.getServiceId());
//		String serviceDetail = detail==null?"":gson.toJson(detail);
		
		if(req.getServicePrice() <= 0){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR, "服务金额不能小于0");
		}
		Body body = new Body(req.getPayType());
		/*根据支付工具类型返回相应的参数*/
		if(req.getPayToolType() == PAY_TOOL_ALIPAY){
			
			String url = getOrderInfo(req.getServiceName(), gson.toJson(body), req.getServicePrice()+"", req.getOrderId());

			// 对订单做RSA 签名
				String sign = sign(url);
				
				try {
					// 仅需对sign 做URL编码
					sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error("unknow encode!",e);
				}

				// 完整的符合支付宝参数规范的订单信息
			final String payInfo = url + "&sign=\"" + sign + "\"&"
						+ getSignType();
			AliPrePayInfoResp resp = new AliPrePayInfoResp();
			resp.setResult(CodeUtil.SUCCESS);
			resp.setUrl(payInfo);
			
			return resp;
			
		}else if(req.getPayToolType() == PAY_TOOL_WEIXIN){
			
			return getWxPrePay(req.getOrderId(), req.getServiceName(), (int)(req.getServicePrice()*100),req.getPayType(),req.getPt());
			
		}else{
			return new BaseResp(CodeUtil.SUCCESS);
		}
	}
	
	public String getSignType() {
		return "sign_type=\""+AlipayConfig.sign_type+"\"";
	}
	/**
	 * 获取微信预支付信息
	 * @param orderId
	 * @param serviceName
	 * @param price
	 * @return
	 */
	public BaseResp getWxPrePay(String orderId,String serviceName, int price, int payType, String pf){
		
		try{
		PrePayReqData data = new PrePayReqData(Configure.getAppid(), Configure.getMchid(), System.currentTimeMillis()+"", null, serviceName, orderId+"", price, Configure.getIP(), Configure.CALL_BACK_URL, "APP");
		data.setAttach(gson.toJson(new Body(payType)));
		String sign = Signature.getSign(data);
		data.setSign(sign);
		
		/*调用服务*/
		String retStr = WXPay.requestPrePayService(data);
		
		PrePayRespData returnData = (PrePayRespData)Util.getObjectFromXML(retStr, PrePayRespData.class);
		
		if("SUCCESS".equals(returnData.getReturn_code()) && "SUCCESS".equals(returnData.getResult_code())){
			/*组装客户端调起微信支付的相关参数*/
			String noncestr = System.currentTimeMillis()%1000*719759154+"";
			String pck = null;
			if("androidphone".equals(pf)){
				pck = "prepay_id="+returnData.getPrepay_id();
			}else{/*ios*/
				pck = "Sign=WXPay";
			}
				
			String time = String.valueOf(System.currentTimeMillis()/1000);
			Map<String,Object> kv = new HashMap<String, Object>();
			kv.put("appid", Configure.appID);
			kv.put("noncestr", noncestr);
			kv.put("package", pck);
			kv.put("partnerid", Configure.mchID);
			kv.put("prepayid", returnData.getPrepay_id());
			kv.put("timestamp", time);
			String clientSign = Signature.getSign(kv);
			return new WxPrePayInfoResp(CodeUtil.SUCCESS, returnData.getPrepay_id(),orderId+"",clientSign,noncestr,pck,time);
		}else{
			return new BaseResp(CodeUtil.SERVER_ERROR,returnData.getReturn_msg());
		}
		
	} catch (Exception e) {
		logger.error("fail to call weixin pre pay interface!",e);
		return new BaseResp(CodeUtil.SERVER_ERROR,"获取信息失败！");
	}
	}
	
	
	public String sign(String content) {
		return SignUtils.sign(content, AlipayConfig.private_key_client);   //商户私钥
	}
	
	/**
	 * 
	 * @param subject 商品名称
	 * @param body 商品详情
	 * @param price 商品金额 以分为单位
	 * @param outTradeNo 订单号
	 * @return
	 */
	public String getOrderInfo(String subject, String body, String price, String outTradeNo) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + AlipayConfig.partner + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller=" + "\"" + AlipayConfig.seller + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径 
		orderInfo += "&notify_url=" + "\"" + AlipayConfig.callBackUrl
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

}
