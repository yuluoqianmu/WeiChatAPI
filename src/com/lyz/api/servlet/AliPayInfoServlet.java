package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import com.alipay.config.AlipayConfig;
import com.lyz.api.bean.addOrderInfoReq;
import com.lyz.api.bean.AliPrePayInfoResp;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.CodeUtil;
import com.lyz.util.LaiPaiEntityIdGenerator;
/**
 * 废弃
 * @author luyongzhao
 *
 */
public class AliPayInfoServlet extends BaseServlet {

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
		
		/*根据服务id获取服务相关信息*/
		
		/*测试使用*/
		String url = getOrderInfo("婚礼跟拍", "测试服务", "0.01", LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeOrder));
		
		AliPrePayInfoResp resp = new AliPrePayInfoResp();
		resp.setResult(CodeUtil.SUCCESS);
		resp.setUrl(url);
		
		return resp;
	}
	/**
	 * 
	 * @param subject 商品名称
	 * @param body 商品详情
	 * @param price 商品金额
	 * @param outTradeNo 订单号
	 * @return
	 */
	public String getOrderInfo(String subject, String body, String price, long outTradeNo) {
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
