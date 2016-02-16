package com.laipai.userManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.orderInfo.pojo.LpOrder;
import com.lyz.util.LaiPaiEntityIdGenerator;


/**
 * LpComment entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpComment implements java.io.Serializable {

	// Fields

	private String commentId;
	private LpUser lpUser;
	private LpGalary lpGalary;
	private LpOrder lpOrder;
	private String newsId;
	private String commentDetail;
	private int commentType;
	private String replayToId;
	private Timestamp createTime;
	private Timestamp modifyTime;
	private String modifyName;

	// Constructors

	/** default constructor */
	public LpComment() {
		this.commentId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeComment)+"";
	}

	/** minimal constructor */
	public LpComment(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	/** full constructor */
	public LpComment(LpUser lpUser, LpGalary lpGalary, String newsId,
			String commentDetail, String replayToId, Timestamp createTime,
			Timestamp modifyTime, String modifyName) {
		this.lpUser = lpUser;
		this.lpGalary = lpGalary;
		this.newsId = newsId;
		this.commentDetail = commentDetail;
		this.replayToId = replayToId;
		this.createTime = createTime;
		this.modifyTime = modifyTime;
		this.modifyName = modifyName;
	}

	// Property accessors

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public LpUser getLpUser() {
		return this.lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	public LpGalary getLpGalary() {
		return this.lpGalary;
	}

	public void setLpGalary(LpGalary lpGalary) {
		this.lpGalary = lpGalary;
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

	public Timestamp getModifyTime() {
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
	}

	public LpOrder getLpOrder() {
		return lpOrder;
	}

	public void setLpOrder(LpOrder lpOrder) {
		this.lpOrder = lpOrder;
	}

	public int getCommentType() {
		return commentType;
	}

	public void setCommentType(int commentType) {
		this.commentType = commentType;
	}

}