package com.lyz.api.bean;
/**
 * 登录响应
 * @author luzi
 *
 */
public class LoginResp extends BaseResp{
	
	/*用户id*/
	private String userId;
	/*用户类型，0-普通用户，1-摄影师*/
	private int userType;
	/*userName+pwd的md5加密*/
	private String token;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	
}
