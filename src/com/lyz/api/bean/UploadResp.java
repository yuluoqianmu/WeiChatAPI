package com.lyz.api.bean;

public class UploadResp extends BaseResp {
	
	public UploadResp(int rCode) {
		super(rCode);
		// TODO Auto-generated constructor stub
	}

	/*存放地址*/
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
