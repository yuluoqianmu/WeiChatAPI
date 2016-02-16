package com.lyz.db.bean;

public class LpPayOrder {
	/*订单id*/
	private long orderId;

	/*买家id*/
	private String buyerId;

	/*买家姓名*/
	private String buyerName;

	/*预留电话*/
	private String buyerPhone;

	/*买家留言，备注信息*/
	private String buyerMsg;

	/*下单时间戳*/
	private long orderTime;

	/*摄影师id*/
	private String cameraId;

	/*摄影师姓名*/
	private String cameraName;

	/*服务id*/
	private String serviceId;

	/*服务名称*/
	private String serviceName;

	/*服务金额，以分为单位*/
	private int servicePrice;

	/*支付类型（1-全款，2-支付定金，3-线下支付）*/
	private int payType;

	/*支付状态（0-未完成，1-完成，2-退款，3-取消）*/
	private int payStatus;

	/*订单更新时间*/
	private long uptime;

	/*摄影师头像*/
	private String cameraHeadImg;

	/*摄影师所在城市*/
	private String cameraCity;
	
	/*买家头像*/
	private String buyerHeadImg;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

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

	public long getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(long orderTime) {
		this.orderTime = orderTime;
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
	/**
	 * 返回以元为单位
	 * @return
	 */
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

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public long getUptime() {
		return uptime;
	}

	public void setUptime(long uptime) {
		this.uptime = uptime;
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

	public String getBuyerHeadImg() {
		return buyerHeadImg;
	}

	public void setBuyerHeadImg(String buyerHeadImg) {
		
		this.buyerHeadImg = buyerHeadImg;
	}
	
	
}
