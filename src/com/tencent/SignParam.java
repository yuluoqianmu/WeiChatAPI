package com.tencent;

public class SignParam{
	
	private String appId;
	
	private String nonceStr;
	
	private String timestamp;
	
	private String sign;
	
	public SignParam(String appId, String nonceStr, String timestamp, String sign){
		
		this.appId = appId;
		this.nonceStr = nonceStr;
		this.timestamp = timestamp;
		this.sign = sign;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
