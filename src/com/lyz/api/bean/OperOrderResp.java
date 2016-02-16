package com.lyz.api.bean;

import java.util.List;

import com.laipai.img.ImgUtil;
import com.lyz.api.bean.OrderStatus.Operation;

/**
 * 展示订单列表
 * @author luyongzhao
 *
 */
public class OperOrderResp extends BaseResp {
	
	/*买家id*/
	private String userId;
	/*买家头像*/
	private String userHeadImg;
	/*买家姓名*/
	private String userName;
	/*买家电话*/
	private String userPhone;
	/*买家消息*/
	private String userMsg;
	
	/*摄影师id*/
	private String cameraId;
	/*摄影师头像*/
	private String cameraHeadImg;
	/*摄影师姓名*/
	private String cameraName;
	/*摄影师所在城市*/
	private String cameraCity;
	/*服务id*/
	private String serviceId;
	/*服务名称*/
	private String serviceName;
	/*服务价格*/
	private double servicePrice;
	/*订单id*/
	private String orderId;
	/*下单时间*/
	private String orderTime;
	/*支付类型*/
	private int payType;
	/*订单操作明细列表*/
	private List<OrderStatus> statList;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserHeadImg() {
		return userHeadImg;
	}
	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = ImgUtil.getImgUrl(userHeadImg, "webp", 100);
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public double getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice/100;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public List<OrderStatus> getStatList() {
		return statList;
	}
	public void setStatList(List<OrderStatus> statList) {
		this.statList = statList;
	}
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
		this.cameraHeadImg = ImgUtil.getImgUrl(cameraHeadImg, "webp", 100);
	}
	public String getCameraName() {
		return cameraName;
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(String userMsg) {
		this.userMsg = userMsg;
	}
	public String getCameraCity() {
		return cameraCity;
	}
	public void setCameraCity(String cameraCity) {
		this.cameraCity = cameraCity;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	
	
}
