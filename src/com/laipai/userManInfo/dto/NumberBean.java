package com.laipai.userManInfo.dto;

public class NumberBean implements java.io.Serializable {
    //用户预约数
	private int userOrderNumber;
    //摄影师订单数
	private int cameramanOrder;
	//关注数
    private int followNumber;
    //喜欢数
    private int likeNumber;
    //粉丝数
    private int fansNumber;
	public int getUserOrderNumber() {
		return userOrderNumber;
	}
	public void setUserOrderNumber(int userOrderNumber) {
		this.userOrderNumber = userOrderNumber;
	}
	public int getCameramanOrder() {
		return cameramanOrder;
	}
	public void setCameramanOrder(int cameramanOrder) {
		this.cameramanOrder = cameramanOrder;
	}
	public int getFollowNumber() {
		return followNumber;
	}
	public void setFollowNumber(int followNumber) {
		this.followNumber = followNumber;
	}
	public int getLikeNumber() {
		return likeNumber;
	}
	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}
	public int getFansNumber() {
		return fansNumber;
	}
	public void setFansNumber(int fansNumber) {
		this.fansNumber = fansNumber;
	}
    
    
     
}
