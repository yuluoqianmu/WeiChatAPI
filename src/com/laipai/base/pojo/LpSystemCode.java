package com.laipai.base.pojo;

/**
 * LpSystemCode entity. @author MyEclipse Persistence Tools
 */

public class LpSystemCode implements java.io.Serializable {

	// Fields

	private String systemCodeId;
	private String standCode;
	private String displayValue;
	private String typeId;
	private String typeName;
	private String status;
	private String remark;

	// Constructors

	/** default constructor */
	public LpSystemCode() {
	}

	// Property accessors

	public String getSystemCodeId() {
		return this.systemCodeId;
	}

	public void setSystemCodeId(String systemCodeId) {
		this.systemCodeId = systemCodeId;
	}

	public String getStandCode() {
		return this.standCode;
	}

	public void setStandCode(String standCode) {
		this.standCode = standCode;
	}

	public String getDisplayValue() {
		return this.displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}