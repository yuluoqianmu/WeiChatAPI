package com.laipai.laiPaiClubInfo.dto;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpLike;

/**
 * LpClub entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpClubShowBean implements java.io.Serializable {
	// Fields
	//来拍社文章id
	private String laipaiId;
	//文章标题
	private String tile;
	//文章内容
	private String content;
	//标题图片
	private String coverUrl;
	//浏览数量
	private Integer viewNumber;
	//赞数量
	private Integer likeNumber;
	//评论数量
	private Integer commentNumber;
	//是否赞过 0赞过   1没赞过
	private Integer isLike;

	// Constructors

	/** default constructor */
	public LpClubShowBean() {
	}

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

	public Integer getViewNumber() {
		return this.viewNumber;
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

	public Integer getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}
	public Integer getIsLike() {
		return isLike;
	}
	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}
	
	
}