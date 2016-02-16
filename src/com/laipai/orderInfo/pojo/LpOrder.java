package com.laipai.orderInfo.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * LpOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpOrder implements java.io.Serializable {

	// Fields

	private String orderId;
	private LpService lpService;
	private LpGalary lpGalary;
	private LpUser lpUser;
	private LpUser lpCamera;
	private Timestamp createTime;
	private Timestamp orderTime;
	private String typeId;
	private Integer orderStatus;
	private String orderMobile;
	private Set lpComments=new HashSet(0);

	// Constructors

	/** default constructor */
	public LpOrder() {
	}

	/** full constructor */
	public LpOrder(LpService lpService, LpUser lpUser, Timestamp createTime,
			Timestamp orderTime, String typeId, Integer orderStatus) {
		this.lpService = lpService;
		this.lpUser = lpUser;
		this.createTime = createTime;
		this.orderTime = orderTime;
		this.typeId = typeId;
		this.orderStatus = orderStatus;
	}

	// Property accessors

	public String getOrderId() {
		return this.orderId;
	}

	public String getOrderMobile() {
		return orderMobile;
	}

	public void setOrderMobile(String orderMobile) {
		this.orderMobile = orderMobile;
	}

	public LpUser getLpCamera() {
		return lpCamera;
	}

	public void setLpCamera(LpUser lpCamera) {
		this.lpCamera = lpCamera;
	}

	public LpGalary getLpGalary() {
		return lpGalary;
	}

	public void setLpGalary(LpGalary lpGalary) {
		this.lpGalary = lpGalary;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public LpService getLpService() {
		return this.lpService;
	}

	public void setLpService(LpService lpService) {
		this.lpService = lpService;
	}


	public LpUser getLpUser() {
		return lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Set getLpComments() {
		return lpComments;
	}

	public void setLpComments(Set lpComments) {
		this.lpComments = lpComments;
	}

}