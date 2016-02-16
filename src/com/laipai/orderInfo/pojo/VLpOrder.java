package com.laipai.orderInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * VLpOrderId entity. @author MyEclipse Persistence Tools
 */
@Entity
public class VLpOrder implements java.io.Serializable {

	// Fields
	
	private String orderId;
	private String cameraId;
	private String cameraNickName;
	private String userId;
	private String userNickName;
	private String orderMobile;
	private Timestamp createTime;
	private String subjectName;
	private String galaryId;
	private Integer orderStatus;
	private String commentDetail;

	// Constructors

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public String getCommentDetail() {
		return commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	/** default constructor */
	public VLpOrder() {
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/** full constructor */
	public VLpOrder(String cameraId, String cameraNickName, String userId,
			String userNickName, String orderMobile, Timestamp createTime,
			String subjectName, String galaryId) {
		this.cameraId = cameraId;
		this.cameraNickName = cameraNickName;
		this.userId = userId;
		this.userNickName = userNickName;
		this.orderMobile = orderMobile;
		this.createTime = createTime;
		this.subjectName = subjectName;
		this.galaryId = galaryId;
	}

	// Property accessors

	public String getCameraId() {
		return this.cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public String getCameraNickName() {
		return this.cameraNickName;
	}

	public void setCameraNickName(String cameraNickName) {
		this.cameraNickName = cameraNickName;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNickName() {
		return this.userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getOrderMobile() {
		return this.orderMobile;
	}

	public void setOrderMobile(String orderMobile) {
		this.orderMobile = orderMobile;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VLpOrder))
			return false;
		VLpOrder castOther = (VLpOrder) other;

		return ((this.getCameraId() == castOther.getCameraId()) || (this
				.getCameraId() != null && castOther.getCameraId() != null && this
				.getCameraId().equals(castOther.getCameraId())))
				&& ((this.getCameraNickName() == castOther.getCameraNickName()) || (this
						.getCameraNickName() != null
						&& castOther.getCameraNickName() != null && this
						.getCameraNickName().equals(
								castOther.getCameraNickName())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())))
				&& ((this.getUserNickName() == castOther.getUserNickName()) || (this
						.getUserNickName() != null
						&& castOther.getUserNickName() != null && this
						.getUserNickName().equals(castOther.getUserNickName())))
				&& ((this.getOrderMobile() == castOther.getOrderMobile()) || (this
						.getOrderMobile() != null
						&& castOther.getOrderMobile() != null && this
						.getOrderMobile().equals(castOther.getOrderMobile())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())))
				&& ((this.getSubjectName() == castOther.getSubjectName()) || (this
						.getSubjectName() != null
						&& castOther.getSubjectName() != null && this
						.getSubjectName().equals(castOther.getSubjectName())))
				&& ((this.getGalaryId() == castOther.getGalaryId()) || (this
						.getGalaryId() != null
						&& castOther.getGalaryId() != null && this
						.getGalaryId().equals(castOther.getGalaryId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCameraId() == null ? 0 : this.getCameraId().hashCode());
		result = 37
				* result
				+ (getCameraNickName() == null ? 0 : this.getCameraNickName()
						.hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37
				* result
				+ (getUserNickName() == null ? 0 : this.getUserNickName()
						.hashCode());
		result = 37
				* result
				+ (getOrderMobile() == null ? 0 : this.getOrderMobile()
						.hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		result = 37
				* result
				+ (getSubjectName() == null ? 0 : this.getSubjectName()
						.hashCode());
		result = 37 * result
				+ (getGalaryId() == null ? 0 : this.getGalaryId().hashCode());
		return result;
	}

}