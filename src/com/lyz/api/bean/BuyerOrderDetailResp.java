package com.lyz.api.bean;

import java.util.List;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 订单详情响应信息
 * @author luyongzhao
 *
 */
public class BuyerOrderDetailResp extends BaseReq {
	
	/*摄影师id*/
	private String cameraId;
	/*摄影师头像*/
	private String cameraHeadImg;
	/*摄影师姓名*/
	private String cameraName;
	/*摄影师城市*/
	private String cameraCity;
	/*服务名称*/
	private String serviceName;
	/*服务价格*/
	private String servicePrice;
	/*买家id*/
	private String buyerId;
	/*下单时间*/
	private String orderTime;
	/*买家电话*/
	private String buyerPhone;
	/*买家备注*/
	private String buyerMsg;
	/*交易记录列表*/
	private List<OrderStatus> statusList;
	
	

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public String getCameraHeadImg() {
		return cameraHeadImg;
	}

	public void setCameraHeadImg(String cameraHeadImg) {
		this.cameraHeadImg = cameraHeadImg;
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

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
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

	public List<OrderStatus> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<OrderStatus> statusList) {
		this.statusList = statusList;
	}
	
	
}
