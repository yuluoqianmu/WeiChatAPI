package com.lyz.api.bean;

public class RegisterResp extends BaseResp{
	/*用户id*/
	private String uid;
	/*用户名称*/
	private String userName;
	/*用户昵称*/
	private String nickName;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	
	
}
