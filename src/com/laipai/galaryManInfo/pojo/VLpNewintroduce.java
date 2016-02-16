package com.laipai.galaryManInfo.pojo;

import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * VLpNewintroduceId entity. @author MyEclipse Persistence Tools
 */

public class VLpNewintroduce implements java.io.Serializable {

	// Fields

	private String introduceId;
	private String title;
	private String createTime;
	private String onLineTime;
	private String offLineTime;
	private String content;
	private String imgUrl;
	private Integer status;
	private Integer viewNumber;

	// Constructors

	/** default constructor */
	public VLpNewintroduce() {
		this.introduceId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeNewIntroduce)+"";
	}
	// Property accessors

	public String getIntroduceId() {
		return this.introduceId;
	}

	public void setIntroduceId(String introduceId) {
		this.introduceId = introduceId;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOnLineTime() {
		return onLineTime;
	}

	public void setOnLineTime(String onLineTime) {
		this.onLineTime = onLineTime;
	}

	public String getOffLineTime() {
		return offLineTime;
	}

	public void setOffLineTime(String offLineTime) {
		this.offLineTime = offLineTime;
	}

	public Integer getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}

}