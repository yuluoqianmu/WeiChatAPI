package com.laipai.serviceInfo.pojo;

import java.sql.Timestamp;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.userManInfo.pojo.LpUser;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * LpService entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpService implements java.io.Serializable {

	// Fields

	private String serviceId;
	private LpUser lpUser;
	private String serviceName;
	private Boolean serviceStatus;
	private Timestamp createTime;
	private Integer orderNum;
	private Set lpOrders = new HashSet(0);
//	private Set lpServiceDetails = new HashSet(0);
	private Set lpGalaries = new HashSet(0);
	
	private LpServiceDetail lpServiceDetail;
	// Constructors

	/** default constructor */
	public LpService() {
		this.serviceId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeService)+"";
	}
	// Property accessors

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public LpUser getLpUser() {
		return this.lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
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

	public Integer getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Set getLpOrders() {
		return this.lpOrders;
	}

	public void setLpOrders(Set lpOrders) {
		this.lpOrders = lpOrders;
	}

	public Set getLpGalaries() {
		return this.lpGalaries;
	}

	public void setLpGalaries(Set lpGalaries) {
		this.lpGalaries = lpGalaries;
	}

	public LpServiceDetail getLpServiceDetail() {
		return lpServiceDetail;
	}

	public void setLpServiceDetail(LpServiceDetail lpServiceDetail) {
		this.lpServiceDetail = lpServiceDetail;
	}

}