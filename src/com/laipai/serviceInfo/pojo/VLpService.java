package com.laipai.serviceInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * VLpServiceId entity. @author MyEclipse Persistence Tools
 */
@Entity
public class VLpService implements java.io.Serializable {

	// Fields

	private String serviceId;
	private String serviceName;
	private Boolean serviceStatus;
	private Timestamp createTime;
	private String userId;
	private String userName;
	private String nickName;

	// Constructors

	/** default constructor */
	public VLpService() {
	}

	/** minimal constructor */
	public VLpService(String serviceId) {
		this.serviceId = serviceId;
	}

	/** full constructor */
	public VLpService(String serviceId, String serviceName,
			Boolean serviceStatus, Timestamp createTime, String userId,
			String userName, String nickName) {
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceStatus = serviceStatus;
		this.createTime = createTime;
		this.userId = userId;
		this.userName = userName;
		this.nickName = nickName;
	}

	// Property accessors

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Boolean getServiceStatus() {
		return this.serviceStatus;
	}

	public void setServiceStatus(Boolean serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VLpService))
			return false;
		VLpService castOther = (VLpService) other;

		return ((this.getServiceId() == castOther.getServiceId()) || (this
				.getServiceId() != null && castOther.getServiceId() != null && this
				.getServiceId().equals(castOther.getServiceId())))
				&& ((this.getServiceName() == castOther.getServiceName()) || (this
						.getServiceName() != null
						&& castOther.getServiceName() != null && this
						.getServiceName().equals(castOther.getServiceName())))
				&& ((this.getServiceStatus() == castOther.getServiceStatus()) || (this
						.getServiceStatus() != null
						&& castOther.getServiceStatus() != null && this
						.getServiceStatus()
						.equals(castOther.getServiceStatus())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())))
				&& ((this.getUserName() == castOther.getUserName()) || (this
						.getUserName() != null
						&& castOther.getUserName() != null && this
						.getUserName().equals(castOther.getUserName())))
				&& ((this.getNickName() == castOther.getNickName()) || (this
						.getNickName() != null
						&& castOther.getNickName() != null && this
						.getNickName().equals(castOther.getNickName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getServiceId() == null ? 0 : this.getServiceId().hashCode());
		result = 37
				* result
				+ (getServiceName() == null ? 0 : this.getServiceName()
						.hashCode());
		result = 37
				* result
				+ (getServiceStatus() == null ? 0 : this.getServiceStatus()
						.hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getUserName() == null ? 0 : this.getUserName().hashCode());
		result = 37 * result
				+ (getNickName() == null ? 0 : this.getNickName().hashCode());
		return result;
	}

}