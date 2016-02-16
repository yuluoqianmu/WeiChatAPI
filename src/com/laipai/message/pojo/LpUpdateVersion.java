package com.laipai.message.pojo;

import java.sql.Timestamp;

/**
 * LpUpdateVersion entity. @author MyEclipse Persistence Tools
 */

public class LpUpdateVersion implements java.io.Serializable {

	public static final String UPDATE_VERSION = "/appUpdate";
	// Fields

	private String versionId;
	private String downloadUrl;
	private String versionNum;
	private Timestamp versionCreateTime;
	private Integer osType;

	// Constructors

	/** default constructor */
	public LpUpdateVersion() {
	}


	// Property accessors

	public String getVersionId() {
		return this.versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getVersionNum() {
		return this.versionNum;
	}

	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}

	public Timestamp getVersionCreateTime() {
		return this.versionCreateTime;
	}
	public void setVersionCreateTime(Timestamp versionCreateTime) {
		this.versionCreateTime = versionCreateTime;
	}
	public Integer getOsType() {
		return osType;
	}
	public void setOsType(Integer osType) {
		this.osType = osType;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	
}