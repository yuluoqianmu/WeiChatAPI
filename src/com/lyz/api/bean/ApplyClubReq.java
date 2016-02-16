package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidatePattern;
import com.lyz.validate.ValidateSize;
import com.lyz.validate.ValidateStringIn;

public class ApplyClubReq extends BaseReq{
	@ValidateNotNull(message="姓名")
	private String userName;
	
	@ValidateNotNull(message="性别")
	@ValidateStringIn(value="0,1",message="性别")
	private String sex;
	
	@ValidateNotNull(message="电话号码")
	@ValidatePattern(pattern="^(\\d{8}|\\d{11})$",message="电话号码")
	private String phoneNum;
	
	private String wechat;
	
	@ValidateNotNull(message="城市")
	private String city;
	
	@ValidateNotNull(message="设备型号")
	private String cameraType;
	
	@ValidateNotNull(message="拍摄风格")
	private String chk_value;

	public ApplyClubReq() {
		super();
		this.setKey("test");
		this.setVer("0.9");
		this.setId("deviceId");
		this.setPt("androidphone");
		this.setR("28260C1909A306979BBA5F56F31742E9");
		this.setT(Long.parseLong("1430151248768"));
		this.setUid("8a2a76634c7e5e72014c7fb2a6da1c62");
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCameraType() {
		return cameraType;
	}

	public void setCameraType(String cameraType) {
		this.cameraType = cameraType;
	}

	public String getChk_value() {
		return chk_value;
	}

	public void setChk_value(String chk_value) {
		this.chk_value = chk_value;
	}
	
	
}
