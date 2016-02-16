package com.lyz.db.bean;

import java.sql.Date;

public class LpComment {
	
	/**/
	private String commentId;

	/*用户Id*/
	private String userId;

	/*作品ID*/
	private String galaryId;

	/*帖子ID*/
	private String laipaiId;

	/*帖子Id*/
	private String newsId;

	/*评论内容*/
	private String commentDetail;

	/*被回复的评论Id*/
	private String replayToId;

	/*评论时间*/
	private Date createTime;

	/*修改时间*/
	private Date modifyTime;

	/*修改人*/
	private String modifyName;

	/*订单ID*/
	private String orderId;

	/*评论类型(0:作品评论 1：来拍社评论 2：留言)*/
	private int commentType;

	/*评论状态(0:有效 1：无效)*/
	private int commentStatus;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGalaryId() {
		return galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getLaipaiId() {
		return laipaiId;
	}

	public void setLaipaiId(String laipaiId) {
		this.laipaiId = laipaiId;
	}

	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getCommentDetail() {
		return commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}

	public String getReplayToId() {
		return replayToId;
	}

	public void setReplayToId(String replayToId) {
		this.replayToId = replayToId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getCommentType() {
		return commentType;
	}

	public void setCommentType(int commentType) {
		this.commentType = commentType;
	}

	public int getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(int commentStatus) {
		this.commentStatus = commentStatus;
	}

	
	
}
