package com.laipai.message.pojo;

/**
 * LpMessageCode entity. @author MyEclipse Persistence Tools
 */

public class LpMessageCode implements java.io.Serializable {

	// Fields

	private String messageCodeId;
	private String name;
	private String onlineStatus;
	private Integer noticeAlert;
	private Integer redAlert;
	private Integer messageAlert;
	private String remark;
	private String messageTemplate;

	// Constructors

	/** default constructor */
	public LpMessageCode() {
	}

	/** minimal constructor */
	public LpMessageCode(String messageCodeId) {
		this.messageCodeId = messageCodeId;
	}

	/** full constructor */
	public LpMessageCode(String messageCodeId, String name,
			String onlineStatus, Integer noticeAlert, Integer redAlert,
			Integer messageAlert, String remark, String messageTemplate) {
		this.messageCodeId = messageCodeId;
		this.name = name;
		this.onlineStatus = onlineStatus;
		this.noticeAlert = noticeAlert;
		this.redAlert = redAlert;
		this.messageAlert = messageAlert;
		this.remark = remark;
		this.messageTemplate = messageTemplate;
	}

	// Property accessors

	public String getMessageCodeId() {
		return this.messageCodeId;
	}

	public void setMessageCodeId(String messageCodeId) {
		this.messageCodeId = messageCodeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnlineStatus() {
		return this.onlineStatus;
	}

	public void setOnlineStatus(String onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Integer getNoticeAlert() {
		return this.noticeAlert;
	}

	public void setNoticeAlert(Integer noticeAlert) {
		this.noticeAlert = noticeAlert;
	}

	public Integer getRedAlert() {
		return this.redAlert;
	}

	public void setRedAlert(Integer redAlert) {
		this.redAlert = redAlert;
	}

	public Integer getMessageAlert() {
		return this.messageAlert;
	}

	public void setMessageAlert(Integer messageAlert) {
		this.messageAlert = messageAlert;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMessageTemplate() {
		return this.messageTemplate;
	}

	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

}