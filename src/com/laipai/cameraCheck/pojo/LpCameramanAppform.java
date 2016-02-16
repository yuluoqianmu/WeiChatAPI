package com.laipai.cameraCheck.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.userManInfo.pojo.LpUser;

/**
 * LpCameramanAppform entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpCameramanAppform implements java.io.Serializable {

	// Fields

	private String appformId;
	private LpUser lpUser;
	private Timestamp applyTime;
	private Integer checkStatus;
	private String photoOne;
	private String photoTwo;
	private String photoThree;
	private Timestamp effectiveDate;
	private Timestamp checkTime;
	private String  rejectReason; 
    private String idcardImg; 
	// Constructors



	public Timestamp getCheckTime() {
		return checkTime;
	}

	public String getIdcardImg() {
		return idcardImg;
	}

	public void setIdcardImg(String idcardImg) {
		this.idcardImg = idcardImg;
	}

	public void setCheckTime(Timestamp checkTime) {
		this.checkTime = checkTime;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/** default constructor */
	public LpCameramanAppform() {
	}

	/** full constructor */
	public LpCameramanAppform(LpUser lpUser, Timestamp applyTime,
			Integer checkStatus, String photoOne, String photoTwo,
			String photoThree, Timestamp effectiveDate,
			Set lpCamermanAppformLogs) {
		this.lpUser = lpUser;
		this.applyTime = applyTime;
		this.checkStatus = checkStatus;
		this.photoOne = photoOne;
		this.photoTwo = photoTwo;
		this.photoThree = photoThree;
		this.effectiveDate = effectiveDate;
		
	}

	// Property accessors

	public String getAppformId() {
		return this.appformId;
	}

	public void setAppformId(String appformId) {
		this.appformId = appformId;
	}

	public LpUser getLpUser() {
		return this.lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	public Timestamp getApplyTime() {
		return this.applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getPhotoOne() {
		return this.photoOne;
	}

	public void setPhotoOne(String photoOne) {
		this.photoOne = photoOne;
	}

	public String getPhotoTwo() {
		return this.photoTwo;
	}

	public void setPhotoTwo(String photoTwo) {
		this.photoTwo = photoTwo;
	}

	public String getPhotoThree() {
		return this.photoThree;
	}

	public void setPhotoThree(String photoThree) {
		this.photoThree = photoThree;
	}

	public Timestamp getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	

}