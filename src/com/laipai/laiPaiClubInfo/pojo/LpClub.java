package com.laipai.laiPaiClubInfo.pojo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpLike;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * LpClub entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpClub implements java.io.Serializable {

	// Fields

	private String laipaiId;
	private String tile;
	private String content;
	private String coverUrl;
	private Timestamp onlineDate;
	private Timestamp createDate;
	private Integer status;
	private Integer viewNumber =0;
	private String likeId;
	private String commentId;
	private Integer likeNumber =0;
	private Integer commentNumber =0;
	private Integer controlSource =0;
	private Integer laipaiClubIndex;
	private Set<LpLike> lpLike = new HashSet(0);
	private Set<LpComment> lpComment = new HashSet(0);
	
	// Constructors

	/** default constructor */
	public LpClub() {
		this.laipaiId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeClub)+"";
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
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public Timestamp getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Timestamp onlineDate) {
		this.onlineDate = onlineDate;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getViewNumber() {
		return this.viewNumber;
	}

	public void setViewNumber(Integer viewNumber) {
		this.viewNumber = viewNumber;
	}

	public String getLikeId() {
		return this.likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Set<LpLike> getLpLike() {
		return lpLike;
	}

	public void setLpLike(Set<LpLike> lpLike) {
		this.lpLike = lpLike;
	}

	public Set<LpComment> getLpComment() {
		return lpComment;
	}

	public void setLpComment(Set<LpComment> lpComment) {
		this.lpComment = lpComment;
	}

	public Integer getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(Integer likeNumber) {
		this.likeNumber = likeNumber;
	}

	public Integer getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Integer getControlSource() {
		return controlSource;
	}

	public void setControlSource(Integer controlSource) {
		this.controlSource = controlSource;
	}

	public Integer getLaipaiClubIndex() {
		return laipaiClubIndex;
	}

	public void setLaipaiClubIndex(Integer laipaiClubIndex) {
		this.laipaiClubIndex = laipaiClubIndex;
	}
	
	
}