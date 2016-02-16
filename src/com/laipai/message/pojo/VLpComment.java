package com.laipai.message.pojo;

import java.sql.Timestamp;

/**
 * VLpCommentId entity. @author MyEclipse Persistence Tools
 */

public class VLpComment implements java.io.Serializable {

	// Fields

	private String commentId;
	private String commentDetail;
	private String userId;
	private String nickName;
	private String replyToId;
	private String replyCommentDetail;
	private String replyUserId;
	private String replyNickName;
	private Integer commentType;
	private Timestamp createTime;

	// Constructors

	/** default constructor */
	public VLpComment() {
	}

	/** minimal constructor */
	public VLpComment(String commentId, String userId) {
		this.commentId = commentId;
		this.userId = userId;
	}

	/** full constructor */
	public VLpComment(String commentId, String commentDetail, String userId,
			String nickName, String replyToId, String replyCommentDetail,
			String replyUserId, String replyNickName, Integer commentType,
			Timestamp createTime) {
		this.commentId = commentId;
		this.commentDetail = commentDetail;
		this.userId = userId;
		this.nickName = nickName;
		this.replyToId = replyToId;
		this.replyCommentDetail = replyCommentDetail;
		this.replyUserId = replyUserId;
		this.replyNickName = replyNickName;
		this.commentType = commentType;
		this.createTime = createTime;
	}

	// Property accessors

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentDetail() {
		return this.commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
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

	public String getReplyToId() {
		return this.replyToId;
	}

	public void setReplyToId(String replyToId) {
		this.replyToId = replyToId;
	}

	public String getReplyCommentDetail() {
		return this.replyCommentDetail;
	}

	public void setReplyCommentDetail(String replyCommentDetail) {
		this.replyCommentDetail = replyCommentDetail;
	}

	public String getReplyUserId() {
		return this.replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getReplyNickName() {
		return this.replyNickName;
	}

	public void setReplyNickName(String replyNickName) {
		this.replyNickName = replyNickName;
	}

	public Integer getCommentType() {
		return this.commentType;
	}

	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VLpComment))
			return false;
		VLpComment castOther = (VLpComment) other;

		return ((this.getCommentId() == castOther.getCommentId()) || (this
				.getCommentId() != null && castOther.getCommentId() != null && this
				.getCommentId().equals(castOther.getCommentId())))
				&& ((this.getCommentDetail() == castOther.getCommentDetail()) || (this
						.getCommentDetail() != null
						&& castOther.getCommentDetail() != null && this
						.getCommentDetail()
						.equals(castOther.getCommentDetail())))
				&& ((this.getUserId() == castOther.getUserId()) || (this
						.getUserId() != null && castOther.getUserId() != null && this
						.getUserId().equals(castOther.getUserId())))
				&& ((this.getNickName() == castOther.getNickName()) || (this
						.getNickName() != null
						&& castOther.getNickName() != null && this
						.getNickName().equals(castOther.getNickName())))
				&& ((this.getReplyToId() == castOther.getReplyToId()) || (this
						.getReplyToId() != null
						&& castOther.getReplyToId() != null && this
						.getReplyToId().equals(castOther.getReplyToId())))
				&& ((this.getReplyCommentDetail() == castOther
						.getReplyCommentDetail()) || (this
						.getReplyCommentDetail() != null
						&& castOther.getReplyCommentDetail() != null && this
						.getReplyCommentDetail().equals(
								castOther.getReplyCommentDetail())))
				&& ((this.getReplyUserId() == castOther.getReplyUserId()) || (this
						.getReplyUserId() != null
						&& castOther.getReplyUserId() != null && this
						.getReplyUserId().equals(castOther.getReplyUserId())))
				&& ((this.getReplyNickName() == castOther.getReplyNickName()) || (this
						.getReplyNickName() != null
						&& castOther.getReplyNickName() != null && this
						.getReplyNickName()
						.equals(castOther.getReplyNickName())))
				&& ((this.getCommentType() == castOther.getCommentType()) || (this
						.getCommentType() != null
						&& castOther.getCommentType() != null && this
						.getCommentType().equals(castOther.getCommentType())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCommentId() == null ? 0 : this.getCommentId().hashCode());
		result = 37
				* result
				+ (getCommentDetail() == null ? 0 : this.getCommentDetail()
						.hashCode());
		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getNickName() == null ? 0 : this.getNickName().hashCode());
		result = 37 * result
				+ (getReplyToId() == null ? 0 : this.getReplyToId().hashCode());
		result = 37
				* result
				+ (getReplyCommentDetail() == null ? 0 : this
						.getReplyCommentDetail().hashCode());
		result = 37
				* result
				+ (getReplyUserId() == null ? 0 : this.getReplyUserId()
						.hashCode());
		result = 37
				* result
				+ (getReplyNickName() == null ? 0 : this.getReplyNickName()
						.hashCode());
		result = 37
				* result
				+ (getCommentType() == null ? 0 : this.getCommentType()
						.hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		return result;
	}

}