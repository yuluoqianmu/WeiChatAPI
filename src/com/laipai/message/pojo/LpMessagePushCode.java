package com.laipai.message.pojo;

/**
 * LpMessagePushCode entity. @author MyEclipse Persistence Tools
 */

public class LpMessagePushCode implements java.io.Serializable {

	// Fields

	private String pushCodeId;
	private Long androidAccessid;
	private String androidSecretkey;
	private Long iosAccessid;
	private String iosSecretkey;
	private String remark;

	// Constructors

	/** default constructor */
	public LpMessagePushCode() {
	}

	public String getPushCodeId() {
		return pushCodeId;
	}

	public void setPushCodeId(String pushCodeId) {
		this.pushCodeId = pushCodeId;
	}

	public Long getAndroidAccessid() {
		return androidAccessid;
	}

	public void setAndroidAccessid(Long androidAccessid) {
		this.androidAccessid = androidAccessid;
	}

	public String getAndroidSecretkey() {
		return androidSecretkey;
	}

	public void setAndroidSecretkey(String androidSecretkey) {
		this.androidSecretkey = androidSecretkey;
	}

	public Long getIosAccessid() {
		return iosAccessid;
	}

	public void setIosAccessid(Long iosAccessid) {
		this.iosAccessid = iosAccessid;
	}

	public String getIosSecretkey() {
		return iosSecretkey;
	}

	public void setIosSecretkey(String iosSecretkey) {
		this.iosSecretkey = iosSecretkey;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}