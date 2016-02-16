//package com.lyz.api.servlet;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.log4j.Logger;
//
//import com.google.gson.Gson;
//import com.lyz.api.bean.BaseReq;
//import com.lyz.api.bean.BaseResp;
//import com.lyz.api.bean.CodeUtil;
//import com.lyz.api.bean.PrePayInfoReq;
//import com.lyz.api.bean.WxPrePayInfoResp;
//import com.lyz.util.LaiPaiEntityIdGenerator;
//import com.tencent.WXPay;
//import com.tencent.common.Configure;
//import com.tencent.common.Signature;
//import com.tencent.common.Util;
//import com.tencent.protocol.pre_pay_protocol.PrePayReqData;
//import com.tencent.protocol.pre_pay_protocol.PrePayRespData;
///**
// * 微信支付之前获取微信生成的id
// * @author luyongzhao
// *
// */
//public class WxPrePayInfoServlet extends BaseServlet {
//	
//	private static final Logger logger = Logger.getLogger(WxPrePayInfoServlet.class);
//
//	@Override
//	public String getStringToFile(BaseReq req, HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getTag() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Class getParamClass() {
//		
//		return PrePayInfoReq.class;
//	}
//
//	@Override
//	public Object handler(BaseReq param, HttpServletRequest request) {
//		
//		try {
//			PrePayInfoReq req = (PrePayInfoReq)param;
//			
////			/*组装数据*/
//			String orderId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeOrder)+"";
//			PrePayReqData data = new PrePayReqData(Configure.getAppid(), Configure.getMchid(), System.currentTimeMillis()+"", null, "测试支付", orderId, 1, Configure.getIP(), Configure.CALL_BACK_URL, "APP");
//			String sign = Signature.getSign(data);
//			data.setSign(sign);
//			
//			/*调用服务*/
//			String retStr = WXPay.requestPrePayService(data);
//			
//			PrePayRespData returnData = (PrePayRespData)Util.getObjectFromXML(retStr, PrePayRespData.class);
//			
//		
//			
////			PrePayRespData returnData = WXPay.getPrePayRespData();
//			
//			
//			if("SUCCESS".equals(returnData.getReturn_code()) && "SUCCESS".equals(returnData.getResult_code())){
//				
//				/*保证返回给客户端的是原始的加密类*/
//				data.setSign("");
//				return new WxPrePayInfoResp(CodeUtil.SUCCESS, returnData.getPrepay_id(),orderId,sign,data);
//			}else{
//				return new BaseResp(CodeUtil.SERVER_ERROR,returnData.getReturn_msg());
//			}
//			
//		} catch (Exception e) {
//			logger.error("fail to call weixin pre pay interface!",e);
//			return new BaseResp(CodeUtil.SERVER_ERROR,"获取信息失败！");
//		}
//		
//		
//	}
//	
//	public static void main(String args[]){
//		
//		WxPrePayInfoServlet servlet = new WxPrePayInfoServlet();
//		System.out.println(new Gson().toJson(servlet.handler(new PrePayInfoReq(), null)));
//	}
//
//}
