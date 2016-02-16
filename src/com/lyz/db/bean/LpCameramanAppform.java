package com.lyz.db.bean;

import java.sql.Date;
/**
 * 摄影师审核申请表
 * @author luyongzhao
 *
 */
public class LpCameramanAppform {
	
	/**/
	private String appformId;

	/*申请用户ID*/
	private String userId;

	/*申请时间*/
	private Date applyTime;

	/*审核状态, -1:驳回，0：待审核，1审核通过*/
	private int checkStatus;

	/*作品1*/
	private String photoOne;

	/*作品2*/
	private String photoTwo;

	/*作品3*/
	private String photoThree;

	/*审核时间*/
	private Date checkTime;

	/*生效时间*/
	private Date effectiveDate;

	/*驳回理由*/
	private String rejectReason;

	public String getAppformId() {
		return appformId;
	}

	public void setAppformId(String appformId) {
		this.appformId = appformId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getPhotoOne() {
		return photoOne;
	}

	public void setPhotoOne(String photoOne) {
		this.photoOne = photoOne;
	}

	public String getPhotoTwo() {
		return photoTwo;
	}

	public void setPhotoTwo(String photoTwo) {
		this.photoTwo = photoTwo;
	}

	public String getPhotoThree() {
		return photoThree;
	}

	public void setPhotoThree(String photoThree) {
		this.photoThree = photoThree;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	
	
	
}
