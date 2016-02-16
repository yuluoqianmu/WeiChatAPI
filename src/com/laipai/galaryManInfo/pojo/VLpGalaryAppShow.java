package com.laipai.galaryManInfo.pojo;

import java.sql.Timestamp;

/**
 * VLpGalaryAppShowId entity. @author MyEclipse Persistence Tools
 */

public class VLpGalaryAppShow implements java.io.Serializable {

	// Fields

	private String galaryId;
	private String userId;
	private String nickName;
	private String headImage;
	private Integer userType;
	private String cityName;
	private String cityId;
	private String serviceId;
	private String subjectName;
	private String galaryCover;
	private Integer viewNumber;
	private String likeNumber;
	private Integer commentNumber;
	private Integer galaryStatus;
	private Integer galaryIndex;
	private Double galaryScores;
	private Integer indexControl;
	private Long price;
	private Integer isLike;
	private String styleName;
    private int isSubject;
    private String subjectId;
   /* private String subjectLikeNum;*/
    private String discoverName;
    private String subjectCover;
    private int gallaryNum;
    
     
    
    
	
	// Constructors

	public int getGallaryNum() {
		return gallaryNum;
	}

	public void setGallaryNum(int gallaryNum) {
		this.gallaryNum = gallaryNum;
	}

	public String getSubjectCover() {
		return subjectCover;
	}

	public void setSubjectCover(String subjectCover) {
		this.subjectCover = subjectCover;
	}

	public int getIsSubject() {
		return isSubject;
	}

	public void setIsSubject(int isSubject) {
		this.isSubject = isSubject;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

    



	public String getDiscoverName() {
		return discoverName;
	}

	public void setDiscoverName(String discoverName) {
		this.discoverName = discoverName;
	}

	/** default constructor */
	public VLpGalaryAppShow() {
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Integer getUserType() {
		return this.userType;
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

	public String getCityName() {
		return this.cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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

	public String getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(String likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Integer getCommentNumber() {
		return this.commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Integer getGalaryStatus() {
		return this.galaryStatus;
	}

	public void setGalaryStatus(Integer galaryStatus) {
		this.galaryStatus = galaryStatus;
	}

	public Integer getGalaryIndex() {
		return this.galaryIndex;
	}

	public void setGalaryIndex(Integer galaryIndex) {
		this.galaryIndex = galaryIndex;
	}

	public Double getGalaryScores() {
		return this.galaryScores;
	}

	public void setGalaryScores(Double galaryScores) {
		this.galaryScores = galaryScores;
	}

	public Integer getIndexControl() {
		return this.indexControl;
	}

	public void setIndexControl(Integer indexControl) {
		this.indexControl = indexControl;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getIsLike() {
		return this.isLike;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

	public String getStyleName() {
		return this.styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

}