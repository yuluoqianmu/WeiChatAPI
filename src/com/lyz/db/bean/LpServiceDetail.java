package com.lyz.db.bean;

public class LpServiceDetail {
	
	/**/
	private String detailId;

	/*服务主题*/
	private String serviceId;

	/*价格*/
	private long price;

	/*拍摄时间*/
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

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public float getShootTime() {
		return shootTime;
	}

	public void setShootTime(float shootTime) {
		this.shootTime = shootTime;
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
	
	
}
