package com.lyz.db.bean;

public class LPApplyClub {
	private String applyId;//id主键
	private String cameraName;//摄影师姓名
	private Integer gender;//性别
	private String phoneNum;//电话号
	private String wechat;//微信号
	private String cityName;//所在城市
	private String grapherCarmer;//相机型号
	private String grapherDesc;//拍摄风格
	private long createTime;//报名时间
	private int check;//审核结果：0-未审核，1-审核通过；2-审核未通过
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getCameraName() {
		return cameraName;
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getGrapherCarmer() {
		return grapherCarmer;
	}
	public void setGrapherCarmer(String grapherCarmer) {
		this.grapherCarmer = grapherCarmer;
	}
	public String getGrapherDesc() {
		return grapherDesc;
	}
	public void setGrapherDesc(String grapherDesc) {
		this.grapherDesc = grapherDesc;
	}
	public int getCheck() {
		return check;
	}
	public void setCheck(int check) {
		this.check = check;
	}
	
	
	
}
