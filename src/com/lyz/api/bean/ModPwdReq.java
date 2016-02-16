package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 修改密码，密码都需要md5加密
 * @author luzi
 *
 */
public class ModPwdReq extends BaseReq {
	@ValidateNotNull(message="用户id")
	@ValidateSize(message="用户id",minSize="1",maxSize="100")
	private String userId;
	@ValidateNotNull(message="旧密码")
	@ValidateSize(message="旧密码",minSize="1",maxSize="100")
	private String oldPassword;
	@ValidateNotNull(message="新密码")
	@ValidateSize(message="新密码",minSize="1",maxSize="100")
	private String newPassword;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	
}
