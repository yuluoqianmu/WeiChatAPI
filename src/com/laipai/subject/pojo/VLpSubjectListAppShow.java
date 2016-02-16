package com.laipai.subject.pojo;

import java.sql.Timestamp;

/**
 * VLpSubjectListAppShowId entity. @author MyEclipse Persistence Tools
 */

public class VLpSubjectListAppShow implements java.io.Serializable {

	// Fields

	private String subjectId;
	private String subjectName;
	private String subjectImg;
	private Timestamp createTime;
	private String createUserName;
	private String modifyUserName;
	private Timestamp modifyTime;
	private Integer galleryNumber;
	private Double accessNumber;
	private Double likeNumber;
	private Integer subjectStatus;
	private Integer indexLocation;
	private Integer subjectLocation;
	private Timestamp onlineTime;
	private Integer seqNumber;

	// Constructors

	/** default constructor */
	public VLpSubjectListAppShow() {
	}


	// Property accessors

	public String getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectImg() {
		return this.subjectImg;
	}

	public void setSubjectImg(String subjectImg) {
		this.subjectImg = subjectImg;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return this.modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getGalleryNumber() {
		return this.galleryNumber;
	}

	public void setGalleryNumber(Integer galleryNumber) {
		this.galleryNumber = galleryNumber;
	}

	public Double getAccessNumber() {
		return this.accessNumber;
	}

	public void setAccessNumber(Double accessNumber) {
		this.accessNumber = accessNumber;
	}

	public Double getLikeNumber() {
		return this.likeNumber;
	}

	public void setLikeNumber(Double likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Integer getSubjectStatus() {
		return this.subjectStatus;
	}

	public void setSubjectStatus(Integer subjectStatus) {
		this.subjectStatus = subjectStatus;
	}

	public Integer getIndexLocation() {
		return this.indexLocation;
	}

	public void setIndexLocation(Integer indexLocation) {
		this.indexLocation = indexLocation;
	}

	public Integer getSubjectLocation() {
		return this.subjectLocation;
	}

	public void setSubjectLocation(Integer subjectLocation) {
		this.subjectLocation = subjectLocation;
	}

	public Timestamp getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(Timestamp onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Integer getSeqNumber() {
		return this.seqNumber;
	}

	public void setSeqNumber(Integer seqNumber) {
		this.seqNumber = seqNumber;
	}

	
}