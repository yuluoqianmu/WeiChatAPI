package com.laipai.laiPaiClubInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * VLpClubId entity. @author MyEclipse Persistence Tools
 */
@Entity
public class VLpClub implements java.io.Serializable {

	private String laipaiId;
	private String tile;
	private String content;
	private String coverUrl;
	private Integer status;
	private Timestamp onlineDate;
	private Integer laipaiClubIndex;
	private Integer viewNumber;
	private Integer likeNumber;
	private Integer commentNumber;

	// Constructors

	/** default constructor */
	public VLpClub() {
	}

	/** minimal constructor */
	public VLpClub(String laipaiId) {
		this.laipaiId = laipaiId;
	}
	// Property accessors

	public String getLaipaiId() {
		return this.laipaiId;
	}

	public void setLaipaiId(String laipaiId) {
		this.laipaiId = laipaiId;
	}

	public String getTile() {
		return this.tile;
	}

	public void setTile(String tile) {
		this.tile = tile;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCoverUrl() {
		return this.coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLaipaiClubIndex() {
		return this.laipaiClubIndex;
	}

	public void setLaipaiClubIndex(Integer laipaiClubIndex) {
		this.laipaiClubIndex = laipaiClubIndex;
	}

	public Integer getCommentNumber() {
		return this.commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Integer getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}

	public Integer getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(Integer likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Timestamp getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Timestamp onlineDate) {
		this.onlineDate = onlineDate;
	}

	

}