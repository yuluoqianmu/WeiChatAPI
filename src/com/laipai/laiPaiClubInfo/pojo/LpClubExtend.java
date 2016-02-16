package com.laipai.laiPaiClubInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpClubExtend entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpClubExtend implements java.io.Serializable {

	// Fields
	private String clubExtendId;
	private String laipaiId;
	private Integer viewNumber;
	private Integer likeNumber;
	private Integer commentNumber;
	private Timestamp modifyTime;
	private String modifyUserId;

	// Constructors

	/** default constructor */
	public LpClubExtend() {
	}

	public String getClubExtendId() {
		return this.clubExtendId;
	}

	public void setClubExtendId(String clubExtendId) {
		this.clubExtendId = clubExtendId;
	}

	public String getLaipaiId() {
		return this.laipaiId;
	}

	public void setLaipaiId(String laipaiId) {
		this.laipaiId = laipaiId;
	}

	public Integer getViewNumber() {
		return this.viewNumber;
	}

	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}

	public Integer getLikeNumber() {
		return this.likeNumber;
	}

	public void setLikeNumber(Integer likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Integer getCommentNumber() {
		return this.commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUserId() {
		return this.modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

}