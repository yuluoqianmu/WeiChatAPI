package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 找回密码
 * @author luzi
 *
 */
public class FindPwdReq extends BaseReq {
	@ValidateNotNull(message="手机号")
	@ValidateSize(message="手机号",minSize="1",maxSize="100")
	private String userName;
	@ValidateNotNull(message="密码")
	@ValidateSize(message="密码",minSize="1",maxSize="100")
	private String pwd;
	@ValidateNotNull(message="验证码")
	@ValidateSize(message="验证码",minSize="1",maxSize="10")
	private String checkCode;
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
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	
	
	
	
}
