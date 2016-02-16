package com.laipai.operationManage.pojo;

import java.sql.Timestamp;

/**
 * VLpStyleListBackId entity. @author MyEclipse Persistence Tools
 */

public class VLpStyleListBack implements java.io.Serializable {

	// Fields
	private String styleId;
	private String styleName;
	private String styleType;
	private Integer styleStatus;
	private String createUserId;
	private Timestamp onlineTime;
	private Integer styleLocation;
	private Integer isOnline;
	private Integer manNumber;
	private Integer isTrueDelete;
	private Long cammerManNumber;

	/** default constructor */
	public VLpStyleListBack() {
	}

	public String getStyleId() {
		return this.styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getStyleName() {
		return this.styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getStyleType() {
		return this.styleType;
	}

	public void setStyleType(String styleType) {
		this.styleType = styleType;
	}

	public Integer getStyleStatus() {
		return this.styleStatus;
	}

	public void setStyleStatus(Integer styleStatus) {
		this.styleStatus = styleStatus;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(Timestamp onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Integer getStyleLocation() {
		return this.styleLocation;
	}

	public void setStyleLocation(Integer styleLocation) {
		this.styleLocation = styleLocation;
	}

	public Integer getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getManNumber() {
		return this.manNumber;
	}

	public void setManNumber(Integer manNumber) {
		this.manNumber = manNumber;
	}

	public Integer getIsTrueDelete() {
		return this.isTrueDelete;
	}

	public void setIsTrueDelete(Integer isTrueDelete) {
		this.isTrueDelete = isTrueDelete;
	}

	public Long getCammerManNumber() {
		return this.cammerManNumber;
	}

	public void setCammerManNumber(Long cammerManNumber) {
		this.cammerManNumber = cammerManNumber;
	}

	

}