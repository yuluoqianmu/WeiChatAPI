package com.laipai.galaryManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpMessage entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpMessage implements java.io.Serializable {

	// Fields

	private String messageId;
	private Integer messageType;
	private Integer messageStatus;
	private Timestamp createTime;
	private Integer onLine;
	private Integer messageNotice;
	private Integer messagePush;

	// Constructors

	/** default constructor */
	public LpMessage() {
	}

	// Property accessors

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}


	public Integer getMessageNotice() {
		return messageNotice;
	}

	public void setMessageNotice(Integer messageNotice) {
		this.messageNotice = messageNotice;
	}

	public Integer getMessagePush() {
		return messagePush;
	}

	public void setMessagePush(Integer messagePush) {
		this.messagePush = messagePush;
	}

	public Integer getMessageType() {
		return this.messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public Integer getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(Integer messageStatus) {
		this.messageStatus = messageStatus;
	}


	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getOnLine() {
		return onLine;
	}

	public void setOnLine(Integer onLine) {
		this.onLine = onLine;
	}
	

}