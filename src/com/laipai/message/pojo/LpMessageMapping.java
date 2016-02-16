package com.laipai.message.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpMessageMapping entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpMessageMapping implements java.io.Serializable {

	// Fields

	private String messageId;
	private String orderId;
	private String commentId;
	private String likeId;
	private String followId;
	private String receiveUserId;
	private Integer messageType;
	private Integer status;
	private Timestamp createTime;
	

	// Constructors

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/** default constructor */
	public LpMessageMapping() {
	}

	/** minimal constructor */
	public LpMessageMapping(String messageId) {
		this.messageId = messageId;
	}

	/** full constructor */
	public LpMessageMapping(String messageId, String orderId, String commentId,
			String likeId, String followId, String receiveUserId,
			Integer messageType, Integer sttatus) {
		this.messageId = messageId;
		this.orderId = orderId;
		this.commentId = commentId;
		this.likeId = likeId;
		this.followId = followId;
		this.receiveUserId = receiveUserId;
		this.messageType = messageType;
		this.status = sttatus;
	}

	// Property accessors

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getLikeId() {
		return this.likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	public String getFollowId() {
		return this.followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public String getReceiveUserId() {
		return this.receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}

	public Integer getMessageType() {
		return this.messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


}