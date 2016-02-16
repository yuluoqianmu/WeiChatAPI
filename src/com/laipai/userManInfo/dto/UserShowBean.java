package com.laipai.userManInfo.dto;

public class UserShowBean {
    //用户Id
  private String userId;
   //用户昵称
  private String nickName;
  
  private String nickName2;
   //用户头像
	private String headImage;
	
	//用户类型
    private Integer userType;
    
    private String cityName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getNickName2() {
		return nickName2;
	}

	public void setNickName2(String nickName2) {
		this.nickName2 = nickName2;
	}
    
  
  
}
