package com.lyz.api.bean;

public class ServiceListResp {
	
	private String serviceId;
	/*服务名称*/
	private String serviceName;
	/*服务价格*/
	private String servicePrice;
	/*订单数量*/
	private String orderNum;
	
	public ServiceListResp(String serviceId, String serviceName, String servicePrice, String orderNum){
		
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.servicePrice = servicePrice;
		this.orderNum = orderNum;
	
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	
}
