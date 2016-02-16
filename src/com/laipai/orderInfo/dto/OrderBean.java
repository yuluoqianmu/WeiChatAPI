package com.laipai.orderInfo.dto;

public class OrderBean {
	private String orderId;
	private String galaryId;
	private String subjectName;
	private String orderStatus;
	private String userName;
	private String comment;
	private String orderMobile;
	
	
	
	public String getOrderMobile() {
		return orderMobile;
	}
	public void setOrderMobile(String orderMobile) {
		this.orderMobile = orderMobile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getGalaryId() {
		return galaryId;
	}
	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
