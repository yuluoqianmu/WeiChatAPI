package com.laipai.privilege.pojo;

/**
 * LpPrivilegeId entity. @author MyEclipse Persistence Tools
 */

public class LpPrivilegeId implements java.io.Serializable {

	// Fields

	private String resourceId;
	private String userId;

	// Constructors

	/** default constructor */
	public LpPrivilegeId() {
	}

	/** full constructor */
	public LpPrivilegeId(String resourceId, String userId) {
		this.resourceId = resourceId;
		this.userId = userId;
	}

	// Property accessors

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof LpPrivilegeId))
			return false;
		LpPrivilegeId castOther = (LpPrivilegeId) other;

		return ((this.getResourceId() == castOther.getResourceId()) || (this.getResourceId() != null && castOther.getResourceId() != null && this.getResourceId().equals(castOther.getResourceId()))) && ((this.getUserId() == castOther.getUserId()) || (this.getUserId() != null && castOther.getUserId() != null && this.getUserId().equals(castOther.getUserId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getResourceId() == null ? 0 : this.getResourceId().hashCode());
		result = 37 * result + (getUserId() == null ? 0 : this.getUserId().hashCode());
		return result;
	}

}