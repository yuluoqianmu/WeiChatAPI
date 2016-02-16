package com.laipai.galaryManInfo.bean;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.subject.pojo.Subject;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * LpGalary entity. @author MyEclipse Persistence Tools
 */
public class LpGalaryBean implements java.io.Serializable,Cloneable {
	// Fields
    private String subjectName;
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	private String galaryId;
	/** 
	 * @Fields userName : 摄影师名称 
	 */  
	private String userName;
	
	/** 
	 * @Fields serviceMoney : 服务价格 
	 */  
	private long serviceMoney;
	
	//private LpService lpService;
	private String galaryDesc;
	private String galaryCover;
	private Integer viewNumber;
	private Integer likeNumber;
	private Integer commentNumber;
	private String headImage; //摄影师头像
	private String cameramanId;
	private Integer userType;
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getCameramanId() {
		return cameramanId;
	}

	public void setCameramanId(String cameramanId) {
		this.cameramanId = cameramanId;
	}
	private int isLike;

	public int getIsLike() {
		return isLike;
	}

	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getUserName() {
		return userName;
	}
	
	public long getServiceMoney() {
		return serviceMoney;
	}

	public void setServiceMoney(long serviceMoney) {
		this.serviceMoney = serviceMoney;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getGalaryDesc() {
		return this.galaryDesc;
	}

	public void setGalaryDesc(String galaryDesc) {
		this.galaryDesc = galaryDesc;
	}

	public String getGalaryCover() {
		return this.galaryCover;
	}

	public void setGalaryCover(String galaryCover) {
		this.galaryCover = galaryCover;
	}

	public Integer getViewNumber() {
		return this.viewNumber;
	}

	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}

	public Integer getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(Integer likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Integer getCommentNumber() {
		return this.commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}


	

}