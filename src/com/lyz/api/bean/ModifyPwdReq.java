package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;
/**
 * 修改密码
 * @author luyongzhao
 *
 */
public class ModifyPwdReq extends BaseReq {
	
	@ValidateNotNull(message="用户id")
	@ValidateSize(minSize="10",maxSize="100",message="用户id")
	private String uid;
	@ValidateNotNull(message="原密码")
	@ValidateSize(minSize="6",maxSize="50",message="原密码")
	private String oldPwd;
	@ValidateNotNull(message="新密码")
	@ValidateSize(minSize="6",maxSize="50",message="新密码")
	private String newPwd;
	@ValidateNotNull(message="再一次新密码")
	@ValidateSize(minSize="6",maxSize="50",message="再一次新密码")
	private String newPwdAgain;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOldPwd() {
		return oldPwd;
	}
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getNewPwdAgain() {
		return newPwdAgain;
	}
	public void setNewPwdAgain(String newPwdAgain) {
		this.newPwdAgain = newPwdAgain;
	}
	
	
	
	
}
