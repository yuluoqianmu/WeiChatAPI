package com.laipai.operationManage.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.userManInfo.pojo.LpUser;
import com.lyz.util.LaiPaiEntityIdGenerator;

@Entity
public class LpCity implements java.io.Serializable {

	public LpCity() {
		this.cityId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeCity)+"";
	}
	/** 
	 * @Fields cityId : 城市编号 
	 */  
	private String cityId;
	
	/** 
	 * @Fields cityName : 城市名称 
	 */  
	private String cityName;
	/** 
	 * @Fields onlineTime : 上线时间 
	 */  
	private Timestamp onlineTime;
	/** 
	 * @Fields offlineTime : 下线时间
	 */  
	private Timestamp offlineTime;
	/** 
	 * @Fields cityStatus : 城市状态 1开启 2关闭
	 */  
	private Boolean cityStatus;
	
	/** 
	 * @Fields cityLocation : 城市位置 
	 */  
	private Integer cityLocation;
	
	/** 
	 * @Fields cammerManNumber : 摄影师数目 
	 */  
	private Integer cammerManNumber;
	
	/** 
	 * @Fields isTrueDelete : 逻辑删除 0不显示 1显示 
	 */  
	private Integer isTrueDelete;
	
	public Integer getIsTrueDelete() {
		return isTrueDelete;
	}
	public void setIsTrueDelete(Integer isTrueDelete) {
		this.isTrueDelete = isTrueDelete;
	}
	public Integer getCityLocation() {
		return cityLocation;
	}
	public void setCityLocation(Integer cityLocation) {
		this.cityLocation = cityLocation;
	}
	public Integer getCammerManNumber() {
		return cammerManNumber;
	}
	public void setCammerManNumber(Integer cammerManNumber) {
		this.cammerManNumber = cammerManNumber;
	}
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
	public Timestamp getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Timestamp onlineTime) {
		this.onlineTime = onlineTime;
	}
	public Timestamp getOfflineTime() {
		return offlineTime;
	}
	public void setOfflineTime(Timestamp offlineTime) {
		this.offlineTime = offlineTime;
	}
	public Boolean getCityStatus() {
		return cityStatus;
	}
	public void setCityStatus(Boolean cityStatus) {
		this.cityStatus = cityStatus;
	}
	
	
}