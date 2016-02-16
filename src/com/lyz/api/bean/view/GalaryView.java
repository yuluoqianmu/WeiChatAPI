package com.lyz.api.bean.view;
/**
 * 作品集
 * @author luzi
 *
 */
public class GalaryView {
	/*城市id*/
	private String cityId;
	/*用户id*/
	private String userId;
	/*封面图地址*/
	private String galaryCover;
	/*评论数*/
	private int commentNumber;
	/*浏览数*/
	private int viewNumber;
	/*点赞数，喜欢数*/
	private int likeNumber;
	/*作品id*/
	private String galaryId;
	/*主题名称*/
	private String subjectName;
	/*用户头像*/
	private String headImage;
	/*用户类型：1-摄影师，0-普通用户*/
	private int userType;
	/*用户昵称*/
	private String nickName;
	/*城市名称*/
	private String cityName;
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGalaryCover() {
		return galaryCover;
	}
	public void setGalaryCover(String galaryCover) {
		this.galaryCover = galaryCover;
	}
	public int getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}
	public int getViewNumber() {
		return viewNumber;
	}
	public void setViewNumber(int viewNumber) {
		this.viewNumber = viewNumber;
	}
	public int getLikeNumber() {
		return likeNumber;
	}
	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}
	public String getGalaryId() {
		return galaryId;
	}
	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
