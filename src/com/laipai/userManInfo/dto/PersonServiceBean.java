package com.laipai.userManInfo.dto;

public class PersonServiceBean implements java.io.Serializable {
	private String serviceId;
	private String serviceName;
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
}
