package com.laipai.galaryManInfo.pojo;

import javax.persistence.Entity;

/**
 * VLpGalaryId entity. @author MyEclipse Persistence Tools
 */
@Entity
public class VLpGalary implements java.io.Serializable {

	// Fields

	private String galaryId;
	private String subjectName;
	private String galaryCover;
	private Integer viewNumber;
	private Integer likeNumber;
	private Integer commentNumber;
	private String userId;
	private String userName;
	private String nickName;
	private String headImage;
	private String cityId;
	private String cityName;
	private String serviceId;
	private Long price;

	// Constructors

	/** default constructor */
	public VLpGalary() {
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGalaryCover() {
		return this.galaryCover;
	}

	public void setGalaryCover(String galaryCover) {
		this.galaryCover = galaryCover;
	}

	public Integer getViewNumber() {
		return viewNumber;
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
		return commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImage() {
		return this.headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}