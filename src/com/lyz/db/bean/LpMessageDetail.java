package com.lyz.db.bean;

import java.sql.Date;

public class LpMessageDetail {
	
	/**/
	private String mesdetailId;

	/*系统消息(消息类型 1预约 2赞 3作品集评论 4关注摄影师 5来拍社通知(系统消息) 6摄影师认证通过7摄影师认证未通过8回复9系统版本)*/
	private String messageId;

	/*消息内容*/
	private String messageContent;

	/*发送时间*/
	private Date createTime;

	/*标题*/
	private String messageTitle;

	/*发送对象（0：摄影师+用户 1：全体用户 2：全体摄影师 3：单个用户 4：单个摄影师）*/
	private int messageSendPerson;

	/*消息状态(0:未读 1：已读)*/
	private int messageStatus;

	/*消息接收人*/
	private String recieveUserId;

	public String getMesdetailId() {
		return mesdetailId;
	}

	public void setMesdetailId(String mesdetailId) {
		this.mesdetailId = mesdetailId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public int getMessageSendPerson() {
		return messageSendPerson;
	}

	public void setMessageSendPerson(int messageSendPerson) {
		this.messageSendPerson = messageSendPerson;
	}

	public int getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(int messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getRecieveUserId() {
		return recieveUserId;
	}

	public void setRecieveUserId(String recieveUserId) {
		this.recieveUserId = recieveUserId;
	}
	
	
}
