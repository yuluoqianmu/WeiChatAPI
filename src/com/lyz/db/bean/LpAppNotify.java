package com.lyz.db.bean;

public class LpAppNotify {
	/*主键id*/
	private int notifyId;

	/*客户端版本*/
	private String appVersion;

	/*客户端类型：iphone,ipad,androidphone,andoirdpad,winpad,winphone*/
	private String appType;

	/*通知内容*/
	private String notifyContent;

	/*升级类型：0-不升级；1-建议升级；2-强制升级*/
	private int upType;

	/*下载地址*/
	private String url;

	/*是否有效：0-无效；1-有效*/
	private int effective;

	/*通知类型：0-升级信息*/
	private int notifyType;

	public int getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getNotifyContent() {
		return notifyContent;
	}

	public void setNotifyContent(String notifyContent) {
		this.notifyContent = notifyContent;
	}

	public int getUpType() {
		return upType;
	}

	public void setUpType(int upType) {
		this.upType = upType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getEffective() {
		return effective;
	}

	public void setEffective(int effective) {
		this.effective = effective;
	}

	public int getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(int notifyType) {
		this.notifyType = notifyType;
	}
	
	
}
