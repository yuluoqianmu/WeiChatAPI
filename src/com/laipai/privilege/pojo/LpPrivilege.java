package com.laipai.privilege.pojo;

/**
 * LpPrivilege entity. @author MyEclipse Persistence Tools
 */

public class LpPrivilege implements java.io.Serializable {

	// Fields

	private LpPrivilegeId id;

	// Constructors

	/** default constructor */
	public LpPrivilege() {
	}

	/** full constructor */
	public LpPrivilege(LpPrivilegeId id) {
		this.id = id;
	}

	// Property accessors

	public LpPrivilegeId getId() {
		return this.id;
	}

	public void setId(LpPrivilegeId id) {
		this.id = id;
	}

}