package com.laipai.privilege.pojo;

/**
 * LpResource entity. @author MyEclipse Persistence Tools
 */

public class LpResource implements java.io.Serializable {

	// Fields

	private String resourceId;
	private String resourceName;
	private String resourceStatus;
	private String resourceContent;
	private String resourcePid;
	private String seqId;

	// Constructors

	/** default constructor */
	public LpResource() {
	}

	/** minimal constructor */
	public LpResource(String resourceId) {
		this.resourceId = resourceId;
	}

	/** full constructor */
	public LpResource(String resourceId, String resourceName, String resourceStatus, String resourceContent, String resourcePid, String seqId) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.resourceStatus = resourceStatus;
		this.resourceContent = resourceContent;
		this.resourcePid = resourcePid;
		this.seqId = seqId;
	}

	// Property accessors

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceStatus() {
		return this.resourceStatus;
	}

	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public String getResourceContent() {
		return this.resourceContent;
	}

	public void setResourceContent(String resourceContent) {
		this.resourceContent = resourceContent;
	}

	public String getResourcePid() {
		return this.resourcePid;
	}

	public void setResourcePid(String resourcePid) {
		this.resourcePid = resourcePid;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

}