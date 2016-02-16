package com.lyz.api.bean;

import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;
/**
 * 验证码请求
 * @author luzi
 *
 */
public class CheckCodeReq extends BaseReq {
	
	@ValidateNotNull(message="手机号")
	@ValidateSize(message="手机号",minSize="1",maxSize="100")
	private String userName;
	
	@ValidateLong(min=1,max=2,message="验证码类型：1-注册，2-找回密码")
	private int checkType;

	public int getCheckType() {
		return checkType;
	}

	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
