package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 注册请求参数
 * @author luzi
 *
 */
public class RegisterReq extends BaseReq {
	
	@ValidateNotNull(message="手机号")
	@ValidateSize(message="手机号",minSize="1",maxSize="100")
	private String userName;
	/*密码需要客户端经过md5（32位小写）加密*/
	@ValidateNotNull(message="密码")
	@ValidateSize(message="密码",minSize="8",maxSize="100")
	private String pwd;
	@ValidateNotNull(message="验证码")
	@ValidateSize(message="验证码",minSize="1",maxSize="10")
	private String checkCode;
	@ValidateNotNull(message="昵称")
	@ValidateSize(message="昵称",minSize="1",maxSize="20")
	private String nickName;


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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
}
