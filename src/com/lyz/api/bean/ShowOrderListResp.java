package com.lyz.api.bean;

import java.util.List;

import com.laipai.img.ImgUtil;
import com.lyz.api.bean.OrderStatus.Operation;

/**
 * 展示订单列表
 * @author luyongzhao
 *
 */
public class ShowOrderListResp extends BaseResp {
	
	/*订单统计列表*/
	private List<OrderStatis> statList;
	
	/*订单列表*/
	private List<Order> orderList;
	
	
	
	public List<Order> getOrderList() {
		return orderList;
	}



	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	
	

	public List<OrderStatis> getStatList() {
		return statList;
	}



	public void setStatList(List<OrderStatis> statList) {
		this.statList = statList;
	}



	public static class OrderStatis{
		/*订单状态*/
		private String status;
		/*订单数量*/
		private int count;
		/*订单状态码：finished,unfinished,cancel*/
		private String statusCode;
		
		public OrderStatis(String status, int count, String statusCode){
			
			this.status = status;
			this.count = count;
			this.statusCode = statusCode;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}
		
		
		
		
	}

	public static class Order{
		/*买家或者摄影师id*/
		private String userId;
		/*买家或者头像*/
		private String userHeadImg;
		/*买家或者姓名*/
		private String userName;
		/*服务id*/
		private String serviceId;
		/*服务名称*/
		private String serviceName;
		/*服务价格*/
		private double servicePrice;
		/*订单id*/
		private String orderId;
		/*需要付多少钱，去支付使用*/
		private double payPrice;
		/*支付类型（1-全款，2-支付定金，3-线下支付）*/
		private int payType;
		/*摄影师id*/
		private String cameraId;
		/*摄影师城市*/
		private String city;
		
		/*最新订单状态*/
		private OrderStatus latestStatus;
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
			if(serviceName!=null && serviceName.length()>7){
				serviceName = serviceName.substring(0, 6)+"...";
			}
			this.serviceName = serviceName;
		}
		public double getServicePrice() {
			return servicePrice;
		}
		public void setServicePrice(double servicePrice) {
			this.servicePrice = ((double)servicePrice)/100;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public OrderStatus getLatestStatus() {
			return latestStatus;
		}
		public void setLatestStatus(OrderStatus latestStatus) {
			this.latestStatus = latestStatus;
		}
		public double getPayPrice() {
			return payPrice;
		}
		public void setPayPrice(double payPrice) {
			this.payPrice = payPrice/100;
		}
		public int getPayType() {
			return payType;
		}
		public void setPayType(int payType) {
			this.payType = payType;
		}
		public String getServiceId() {
			return serviceId;
		}
		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}
		public String getCameraId() {
			return cameraId;
		}
		public void setCameraId(String cameraId) {
			this.cameraId = cameraId;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}		
		
		
		
	}
}
