package com.lyz.db.bean;

public class VLpGalaryAppShow {
	
	/**/
	private String galaryId;

	/*用户Id*/
	private String userId;

	/*昵称*/
	private String nickName;

	/**/
	private String headImage;

	/*用户类型：0普通用户，1摄影师,2管理员*/
	private int userType;

	/*城市名称*/
	private String cityName;

	/**/
	private String cityId;

	/*服务序号*/
	private String serviceId;

	/*作品集的主题名称*/
	private String subjectName;

	/**/
	private String galaryCover;

	/**/
	private long viewNumber;

	/**/
	private String likeNumber;

	/*评论次数*/
	private int commentNumber;

	/*作品集状态 0：开启；1：隐藏*/
	private int galaryStatus;

	/*排序字段*/
	private int galaryIndex;

	/*根据评论量、喜欢量、浏览量计算的作品集热度分数（排序依据）*/
	private double galaryScores;

	/*排序控制 0：正常排序；1：手工控制*/
	private int indexControl;

	/**/
	private double price;

	/**/
	private int isLike;

	/**/
	private String styleName;

	public String getGalaryId() {
		return galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

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

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGalaryCover() {
		return galaryCover;
	}

	public void setGalaryCover(String galaryCover) {
		this.galaryCover = galaryCover;
	}

	public long getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(long viewNumber) {
		this.viewNumber = viewNumber;
	}

	public String getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(String likeNumber) {
		this.likeNumber = likeNumber;
	}

	public int getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	public int getGalaryStatus() {
		return galaryStatus;
	}

	public void setGalaryStatus(int galaryStatus) {
		this.galaryStatus = galaryStatus;
	}

	public int getGalaryIndex() {
		return galaryIndex;
	}

	public void setGalaryIndex(int galaryIndex) {
		this.galaryIndex = galaryIndex;
	}

	public double getGalaryScores() {
		return galaryScores;
	}

	public void setGalaryScores(double galaryScores) {
		this.galaryScores = galaryScores;
	}

	public int getIndexControl() {
		return indexControl;
	}

	public void setIndexControl(int indexControl) {
		this.indexControl = indexControl;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getIsLike() {
		return isLike;
	}

	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	
	
	
	
	
}
