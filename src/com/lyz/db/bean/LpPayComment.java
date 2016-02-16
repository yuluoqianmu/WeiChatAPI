package com.lyz.db.bean;
/**
 * 支付评价表
 * @author luyongzhao
 *
 */
public class LpPayComment {
	
	/*评价id-时间戳*/
	private long id;

	/*评论者id*/
	private String userId;

	/*评论者姓名*/
	private String userName;

	/*评论者头像*/
	private String userHeadImg;

	/*摄影师id*/
	private String cameraId;

	/*服务id*/
	private String serviceId;

	/*服务名称*/
	private String serviceName;

	/*订单id*/
	private long orderId;

	/*评分，1-5*/
	private int cmtScore;

	/*评论内容*/
	private String cmtContent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserHeadImg() {
		return userHeadImg;
	}

	public void setUserHeadImg(String userHeadImg) {
		this.userHeadImg = userHeadImg;
	}

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
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

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getCmtScore() {
		return cmtScore;
	}

	public void setCmtScore(int cmtScore) {
		this.cmtScore = cmtScore;
	}

	public String getCmtContent() {
		return cmtContent;
	}

	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}
	
	
}
