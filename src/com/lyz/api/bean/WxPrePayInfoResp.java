package com.lyz.api.bean;

import com.tencent.common.Configure;

public class WxPrePayInfoResp extends BaseResp {
	
	public WxPrePayInfoResp(int rCode, String prepayId, String orderId, String sign, String noncestr, String pck, String timestamp){
		
		super(rCode);
		this.prepayId = prepayId;
		this.orderId = orderId;
		this.sign = sign;
		this.noncestr = noncestr;
		this.pck = pck;
		this.timestamp = timestamp;
		this.partnerid = Configure.mchID;
	}
	
	/*微信id返回的订单id*/
	private String prepayId;
	/*订单id*/
	private String orderId;
	/*签名*/
	private String sign;
	/*随机字符串*/
	private String noncestr;
	/*package*/
	private String pck;
	/*商户id*/
	private String partnerid;
	/*时间戳*/
	private String timestamp;
	
	
	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getPck() {
		return pck;
	}

	public void setPck(String pck) {
		this.pck = pck;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
