package com.laipai.message.pojo;

import java.sql.Timestamp;

/**
 * LpMessageDetail entity. @author MyEclipse Persistence Tools
 */

public class LpMessageDetail implements java.io.Serializable {

	// Fields

	private String mesdetailId;
	private String messageId;
	private String messageContent;
	private Timestamp createTime;
	private String messageTitle;
	private Integer messageSendPerson;
	private Integer messageStatus;
	private String recieveUserId;
	// Constructors

	/** default constructor */
	public LpMessageDetail() {
	}

	public Integer getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(Integer messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getRecieveUserId() {
		return recieveUserId;
	}

	public void setRecieveUserId(String recieveUserId) {
		this.recieveUserId = recieveUserId;
	}

	/** full constructor */
	public LpMessageDetail(String messageId, String messageContent,
			Timestamp createTime, String messageTitle, Integer messageSendPerson) {
		this.messageId = messageId;
		this.messageContent = messageContent;
		this.createTime = createTime;
		this.messageTitle = messageTitle;
		this.messageSendPerson = messageSendPerson;
	}

	// Property accessors

	public String getMesdetailId() {
		return this.mesdetailId;
	}

	public void setMesdetailId(String mesdetailId) {
		this.mesdetailId = mesdetailId;
	}

	public String getMessageId() {
		return this.messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageContent() {
		return this.messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getMessageTitle() {
		return this.messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public Integer getMessageSendPerson() {
		return this.messageSendPerson;
	}

	public void setMessageSendPerson(Integer messageSendPerson) {
		this.messageSendPerson = messageSendPerson;
	}

}