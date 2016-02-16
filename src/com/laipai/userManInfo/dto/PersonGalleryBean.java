package com.laipai.userManInfo.dto;

public class PersonGalleryBean implements java.io.Serializable {
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
	//评论数量
	private Integer commentNumber;
	//价格
    private double price;
    //城市
    private String cityName;
    //头像
    private String headImage;
    //用户账号
    private String userName;
    //昵称
    private String nickName;
    
    private Integer userType;
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getGalaryId() {
		return galaryId;
	}
	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}
	public String getGalaryCover() {
		return galaryCover;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
    
}
