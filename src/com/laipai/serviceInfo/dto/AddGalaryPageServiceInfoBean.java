package com.laipai.serviceInfo.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class AddGalaryPageServiceInfoBean implements Serializable {

	private String serviceId;
	private String serviceName;
	private String price;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
