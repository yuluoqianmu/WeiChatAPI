package com.lyz.api.bean;

public class ApplyClubResp extends BaseResp{
	
	
	private int count;
	private String username;

	public ApplyClubResp(int code, int count, String username, String message) {
		super(code);
		this.count = count;
		this.username = username;
		setMessage(message);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
