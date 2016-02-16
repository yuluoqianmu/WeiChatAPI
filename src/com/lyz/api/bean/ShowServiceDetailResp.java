package com.lyz.api.bean;

import com.laipai.img.ImgUtil;
import com.lyz.service.OrderService;

/**
 * 服务详情返回值
 * @author luyongzhao
 *
 */
public class ShowServiceDetailResp extends BaseResp {
	
	/*定金,元为单位，定金默认100*/
	private double bargain = OrderService.bargain;
	/*全款说明*/
	private String fullPayDesc = "支付全款，来拍赠送8寸欧式创意水晶相框一个。\n待摄影师为您拍摄完成，来拍才会将钱支付给摄影师。";
	/*定金说明*/
	private String bargainDesc = "支付订金，摄影师会提前为您安排拍摄档期。";
	/*摄影师头像*/
	private String cameraHeadImg;
	/*摄影师id*/
	private String cameraId;
	/*摄影师昵称*/
	private String cameraName;
	/*摄影师联系电话*/
	private String cameraPhone;
	/*摄影师城市*/
	private String cameraCity;
	/*服务id*/
	private String serviceId;
	/*服务名称*/
	private String serviceName;
	/*价格，元为单位*/
	private double servicePrice;
	/*接单数量*/
	private String orderNum;
	/*拍摄时间*/
	private float shootTime;
	/*照片张数*/
	private int picNum;
	/*精修张数*/
	private int truingNum;
	/*服装*/
	private String clothes;
	/*化妆*/
	private String facePaint;
	/*附加说明*/
	private String desc;
	/*价格单位*/
	private String priceUnit;

	public double getBargain() {
		return bargain;
	}

	public void setBargain(double bargain) {
		this.bargain = bargain;
	}

	public String getCameraHeadImg() {
		return cameraHeadImg;
	}

	public void setCameraHeadImg(String cameraHeadImg) {
		
		this.cameraHeadImg = ImgUtil.getImgUrl(cameraHeadImg, "webp", 150);
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public String getCameraCity() {
		return cameraCity;
	}

	public void setCameraCity(String cameraCity) {
		this.cameraCity = cameraCity;
	}

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

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getCameraPhone() {
		return cameraPhone;
	}

	public void setCameraPhone(String cameraPhone) {
		this.cameraPhone = cameraPhone;
	}

	public String getFullPayDesc() {
		return fullPayDesc;
	}

	public void setFullPayDesc(String fullPayDesc) {
		this.fullPayDesc = fullPayDesc;
	}

	public String getBargainDesc() {
		return bargainDesc;
	}

	public void setBargainDesc(String bargainDesc) {
		this.bargainDesc = bargainDesc;
	}

	public float getShootTime() {
		return shootTime;
	}

	public void setShootTime(float shootTime) {
		this.shootTime = shootTime;
	}

	public int getPicNum() {
		return picNum;
	}

	public void setPicNum(int picNum) {
		this.picNum = picNum;
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

	public String getFacePaint() {
		return facePaint;
	}

	public void setFacePaint(String facePaint) {
		this.facePaint = facePaint;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(String priceUnit) {
		this.priceUnit = priceUnit;
	}

	public double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}

	
	
}
