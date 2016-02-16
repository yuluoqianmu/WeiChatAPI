package com.lyz.db.bean;

import java.sql.Date;

public class LpMergedService {
	
	/**/
	private String serviceId;

	/*用户Id*/
	private String userId;

	/*服务主题*/
	private String serviceName;

	/*字段编码（TRUE:上架，FALSE:下架）*/
	private byte serviceStatus;

	/*创建时间*/
	private Date createTime;

	/*订单量*/
	private int orderNum;

	/*0未删除，1已删除*/
	private int status;
	/*服务价格*/
	private long price;
	
	private float shootTime;
	
	/*照片张数*/
	private int pictureNum;

	/*精修张数*/
	private int truingNum;

	/*服装*/
	private String clothes;

	/*化妆*/
	private String facepaint;

	/*附加说明*/
	private String instructions;

	/*价格单位(/天 /套)*/
	private String priceUnit;
	
	

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public byte getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(byte serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getPictureNum() {
		return pictureNum;
	}

	public void setPictureNum(int pictureNum) {
		this.pictureNum = pictureNum;
	}

	public int getTruingNum() {
		return truingNum;
	}

	public void setTruingNum(int truingNum) {
		this.truingNum = truingNum;
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

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public float getShootTime() {
		return shootTime;
	}

	public void setShootTime(float shootTime) {
		this.shootTime = shootTime;
	}
	
	
	
	
}
