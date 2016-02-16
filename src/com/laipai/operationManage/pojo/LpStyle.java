    package com.laipai.operationManage.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.userManInfo.pojo.LpUser;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * 
 * @Description:风格表实体类
 * @author:朱鑫
 * @time:2014-12-29 下午4:26:26
 */
@Entity
public class LpStyle implements java.io.Serializable {

	/** 
	 * @Fields styleId : 风格序号 
	 */  
	private String styleId;
	
	/** 
	 * @Fields styleName :风格名称 
	 */  
	private String styleName;
	
	
	/** 
	/** 
	 * @Fields styleStatus : 状态显示 0 不显示 1 显示
	 */  
	private Integer styleStatus;
	
	/** 
	 * @Fields styleLocation : 风格显示位置 
	 */  
	private Integer styleLocation;
	
	/** 
	 * @Fields isOnline : 0下线 1 上线 
	 */  
	private Integer isOnline;
	
	/** 
	 * @Fields cammerManNumber : 摄影师数目 
	 */  
	private Integer cammerManNumber;
	
	/** 
	 * @Fields isTrueDelete : 逻辑删除  0不显示 1显示 
	 */  
	private Integer isTrueDelete;
	
	
	private Timestamp onlineTime;
	/** 
	 * @Fields cityStatus : 城市状态 1开启 2关闭
	 */ 
	public Integer getIsOnline() {
		return isOnline;
	}
	public Timestamp getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(Timestamp onlineTime) {
		this.onlineTime = onlineTime;
	}
	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}
	
	public Integer getIsTrueDelete() {
		return isTrueDelete;
	}
	public void setIsTrueDelete(Integer isTrueDelete) {
		this.isTrueDelete = isTrueDelete;
	}
	public Integer getCammerManNumber() {
		return cammerManNumber;
	}
	public void setCammerManNumber(Integer cammerManNumber) {
		this.cammerManNumber = cammerManNumber;
	}
	public Integer getStyleLocation() {
		return styleLocation;
	}
	public void setStyleLocation(Integer styleLocation) {
		this.styleLocation = styleLocation;
	}

	private String createUserId;
	
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	/** default constructor */
	public LpStyle() {
		
		this.styleId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeShoot)+"";
	}

	public String getStyleId() {
		return this.styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getStyleName() {
		return this.styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public Integer getStyleStatus() {
		return styleStatus;
	}

	public void setStyleStatus(Integer styleStatus) {
		this.styleStatus = styleStatus;
	}


}