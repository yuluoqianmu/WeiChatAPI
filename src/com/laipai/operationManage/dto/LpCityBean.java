package com.laipai.operationManage.dto;

public class LpCityBean {
	
	public static final String LP_CLUB_IMGURL = "/newIntroduce";
	/** 
	 * @Fields cityId : 城市编号 
	 */  
	private String cityId;
	
	/** 
	 * @Fields cityName : 城市名称 
	 */  
	private String cityName;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
