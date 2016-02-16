package com.lyz.db.bean;

import java.sql.Date;

public class LpStyle {
	/**/
	private String styleId;

	/*风格*/
	private String styleName;

	/*风格类型（0:用户风格，1:作品集风格2:服务风格）*/
	private int styleType;

	/*字段编码（0:全局显示，1:摄影师显示）*/
	private int styleStatus;

	/*创建风格所属摄影师*/
	private String createUserId;

	/*上线时间*/
	private Date onlineTime;

	/*风格显示位置*/
	private int styleLocation;

	/*0下线 1 上线*/
	private int isOnline;

	/*拥有摄影师的数目*/
	private int manNumber;

	/*逻辑删除0不显示1显示*/
	private int isTrueDelete;

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public int getStyleType() {
		return styleType;
	}

	public void setStyleType(int styleType) {
		this.styleType = styleType;
	}

	public int getStyleStatus() {
		return styleStatus;
	}

	public void setStyleStatus(int styleStatus) {
		this.styleStatus = styleStatus;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}

	public int getStyleLocation() {
		return styleLocation;
	}

	public void setStyleLocation(int styleLocation) {
		this.styleLocation = styleLocation;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public int getManNumber() {
		return manNumber;
	}

	public void setManNumber(int manNumber) {
		this.manNumber = manNumber;
	}

	public int getIsTrueDelete() {
		return isTrueDelete;
	}

	public void setIsTrueDelete(int isTrueDelete) {
		this.isTrueDelete = isTrueDelete;
	}
	
	
}
