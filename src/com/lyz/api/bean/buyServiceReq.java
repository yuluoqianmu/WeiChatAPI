package com.lyz.api.bean;

import com.lyz.validate.ValidateDigit;
import com.lyz.validate.ValidateLong;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 预约摄影师，购买服务
 * 
 * @author luyongzhao
 *
 */
public class buyServiceReq extends BaseReq {
	
	@ValidateNotNull(message="买家id")
	@ValidateSize(message="买家id",minSize="10",maxSize="50")
	private String buyerId;
	
	@ValidateNotNull(message="买家姓名")
	@ValidateSize(message="买家姓名",minSize="1",maxSize="30")
	private String buyerName;	
	
	@ValidateNotNull(message="预留手机号")
	@ValidateSize(message="手机号",minSize="8",maxSize="100")
	private String buyerPhone;
	
	
	/*备注信息*/
	private String buyerMsg;
	
	@ValidateNotNull(message="摄影师id")
	@ValidateSize(message="摄影师id",minSize="10",maxSize="50")
	private String cameraId;
	
	@ValidateNotNull(message="摄影师姓名")
	@ValidateSize(message="摄影师姓名",minSize="1",maxSize="30")
	private String cameraName;
	
	@ValidateNotNull(message="摄影师头像")
	@ValidateSize(message="摄影师头像",minSize="20",maxSize="1000")
	private String cameraHeadImg;
	
	@ValidateNotNull(message="摄影师城市")
	@ValidateSize(message="摄影师城市",minSize="20",maxSize="1000")
	private String cameraCity;
	
	@ValidateNotNull(message="服务id")
	@ValidateSize(message="服务id",minSize="10",maxSize="50")
	private String serviceId;
	
	@ValidateNotNull(message="服务名称")
	@ValidateSize(message="服务名称",minSize="10",maxSize="50")
	private String serviceName;
	
	/*服务金额，以分为单位*/
	@ValidateDigit(message="服务金额")
	@ValidateLong(message="服务金额",min=1)
	private int servicePrice;
	
	/*支付类型（1-全款，2-支付定金，3-线下支付）*/
	@ValidateDigit(message="支付类型")
	@ValidateLong(message="支付类型",min=1,max=3)
	private int payType;

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerMsg() {
		return buyerMsg;
	}

	public void setBuyerMsg(String buyerMsg) {
		this.buyerMsg = buyerMsg;
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

	public int getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(int servicePrice) {
		this.servicePrice = servicePrice;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getCameraHeadImg() {
		return cameraHeadImg;
	}

	public void setCameraHeadImg(String cameraHeadImg) {
		this.cameraHeadImg = cameraHeadImg;
	}

	public String getCameraCity() {
		return cameraCity;
	}

	public void setCameraCity(String cameraCity) {
		this.cameraCity = cameraCity;
	}

	
	
	
}
