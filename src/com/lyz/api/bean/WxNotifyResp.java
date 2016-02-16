package com.lyz.api.bean;

public class WxNotifyResp {
	
	private String return_code;
	
	private String return_msg;
	
	public WxNotifyResp(String returnCode, String returnMsg){
		
		this.return_code = returnCode;
		this.return_msg = returnMsg;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	
	
}
