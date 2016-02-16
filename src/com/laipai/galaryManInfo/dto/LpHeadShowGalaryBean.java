package com.laipai.galaryManInfo.dto;

import java.io.File;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.subject.pojo.Subject;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * LpGalary作品集首页展示的bean
 * 用于app转json
 */
public class LpHeadShowGalaryBean implements java.io.Serializable {
	//作品集主题
    private String subjectName;
    //作品集id
	private String galaryId;
	//作品集封面图片url
	private String galaryCover;
	//浏览数量
	private Integer viewNumber;
	//赞数量
	private Integer likeNumber;
	//是否已经点过赞  1没点过赞  0点过赞
	private Integer isLike;
	//评论数量
	private Integer commentNumber;
	//价格
	private Integer price;
	
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	//摄影师昵称
	private String nickName;
	//摄影师头像图片路径
	private String headImage;
	//用户类型  0普通用户  1摄影师
//	private Integer userType;
	//城市
	private String cityName;
	//风格名称
	private String styleName;
//	//首页显示的类型：0 作品集 ,1专题,默认为0
//	private int showType = 0;
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	 
	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
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
		return this.likeNumber;
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public Integer getIsLike() {
		return isLike;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

}