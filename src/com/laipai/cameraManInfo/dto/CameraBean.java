package com.laipai.cameraManInfo.dto;

import javax.persistence.Entity;

@Entity
public class CameraBean implements java.io.Serializable{
	//用户Id
		private String userId;
	   //用户账号
		private String userName;
		//用户昵称
		private String nickName;
		
		private String realName; 
		//用户性别
		private String userGender;
		//用户手机
		private String mobile;
	    private String grapherCarmer;	
		private String grapherDesc;
		 public String getGrapherDesc() {
			return grapherDesc;
		}
		public void setGrapherDesc(String grapherDesc) {
			this.grapherDesc = grapherDesc;
		}
		//城市
	    private String city;
	    private String cityId;
	    //用户类型
	    private Integer userType;
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
		public String getNickName() {
			return nickName;
		}
		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getUserGender() {
			return userGender;
		}
		public void setUserGender(String userGender) {
			this.userGender = userGender;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getGrapherCarmer() {
			return grapherCarmer;
		}
		public void setGrapherCarmer(String grapherCarmer) {
			this.grapherCarmer = grapherCarmer;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public Integer getUserType() {
			return userType;
		}
		public void setUserType(Integer userType) {
			this.userType = userType;
		}
		public String getCityId() {
			return cityId;
		}
		public void setCityId(String cityId) {
			this.cityId = cityId;
		}
		
		
}
