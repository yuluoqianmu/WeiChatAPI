package com.lyz.api.bean;

import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidatePattern;
import com.lyz.validate.ValidateSize;

/**
 * 验证码请求参数
 * @author luzi
 *
 */
public class VercodeReq extends BaseReq {
	
	@ValidateNotNull(message="手机号")
	@ValidateSize(message="手机号",minSize="11",maxSize="11")
	@ValidatePattern(message="手机号",pattern="^1[3|4|5|7|8][0-9]\\d{4,8}$")
	private String userName;
	/*0-注册；1-找回密码*/
	@ValidateLong(min=0,max=1,message="验证码类型")
	private int vercodeType;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getVercodeType() {
		return vercodeType;
	}
	public void setVercodeType(int vercodeType) {
		this.vercodeType = vercodeType;
	}
	
	
}
