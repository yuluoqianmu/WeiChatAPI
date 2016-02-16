package com.laipai.userManInfo.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.orderInfo.pojo.LpOrder;


/**
 * LpComment entity. @author MyEclipse Persistence Tools
 */
public class LpCommentBean implements java.io.Serializable {

	// Fields

	private String commentId;
	private String newsId;
	private String commentDetail;
	private int commentType;
	private String replayToId;
	private Timestamp createTime;


	// Constructors

	/** default constructor */
	public LpCommentBean() {
	}

	// Property accessors

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getNewsId() {
		return this.newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getCommentDetail() {
		return this.commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}

	public String getReplayToId() {
		return this.replayToId;
	}

	public void setReplayToId(String replayToId) {
		this.replayToId = replayToId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/*public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyName() {
		return this.modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}*/

	public int getCommentType() {
		return commentType;
	}

	public void setCommentType(int commentType) {
		this.commentType = commentType;
	}

}