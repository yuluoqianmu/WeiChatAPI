package com.laipai.message.dto;

import java.io.Serializable;

public class MessageBean implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String LP_MESSAGE_IMGURL="/lpMessageImg";
	//消息类型-来拍社通知
	public static final int  LPCLUB_TYPE=5;
	//消息类型-系统版本
	public static final int VERSIONTYPE = 9;
	
	private String title;
	private String person;
	private String userman;
	private String cameraman;
	private String content;
	private String messageId;
	private String versionNum;
	private Integer messageType;
	
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public static String getLpMessageImgurl() {
		return LP_MESSAGE_IMGURL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getUserman() {
		return userman;
	}
	public void setUserman(String userman) {
		this.userman = userman;
	}
	public String getCameraman() {
		return cameraman;
	}
	public void setCameraman(String cameraman) {
		this.cameraman = cameraman;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	

}
