package com.laipai.serviceInfo.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ServiceInfoBean implements Serializable {

	private String serviceId;
	private String detailId;
	private String userId;
	private String galaryId;
	private String serviceName;
	private boolean serviceStatus;
	private Timestamp createTime;
	private Integer orderNum;
	private String[] styleId;
	private Long price;
	private String priceUnit;
	private String shoot_time;
	private Integer picture_num;
	private Integer truing_num;
	private String clothes;
	private String facepaint;
	private String instructions;
	private String cameraName;
	private String cameraServiceJSP;
	

	public String getCameraServiceJSP() {
		return cameraServiceJSP;
	}
	public void setCameraServiceJSP(String cameraServiceJSP) {
		this.cameraServiceJSP = cameraServiceJSP;
	}
	public String getPriceUnit() {
		return priceUnit;
	}
	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}
	public String getCameraName() {
		return cameraName;
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getGalaryId() {
		return galaryId;
	}
	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public boolean isServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(boolean serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	
	public String[] getStyleId() {
		return styleId;
	}
	public void setStyleId(String[] styleId) {
		this.styleId = styleId;
	}
	public String getShoot_time() {
		return shoot_time;
	}
	public void setShoot_time(String shoot_time) {
		this.shoot_time = shoot_time;
	}
	public Integer getPicture_num() {
		return picture_num;
	}
	public void setPicture_num(Integer picture_num) {
		this.picture_num = picture_num;
	}
	public Integer getTruing_num() {
		return truing_num;
	}
	public void setTruing_num(Integer truing_num) {
		this.truing_num = truing_num;
	}
	public String getClothes() {
		return clothes;
	}
	public void setClothes(String clothes) {
		this.clothes = clothes;
	}
	public String getFacepaint() {
		return facepaint;
	}
	public void setFacepaint(String facepaint) {
		this.facepaint = facepaint;
	}
	public String getInstructions() {
		return instructions;
	}
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	
	
}
