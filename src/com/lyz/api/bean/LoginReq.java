package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

public class LoginReq extends BaseReq{
	
	@ValidateNotNull(message="用户名")
	@ValidateSize(message="用户名", minSize="1",maxSize="50")
	private String userName;
	@ValidateNotNull(message="密码")
	@ValidateSize(message="密码", minSize="6",maxSize="50")
	private String pwd;
	/*推送使用的token*/
	@ValidateNotNull(message="用户token")
	@ValidateSize(message="用户token", minSize="6",maxSize="100")
	private String token;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	
	
}
