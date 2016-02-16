package com.laipai.galaryManInfo.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.orderInfo.pojo.LpOrder;


/**
 * LpComment entity. @author MyEclipse Persistence Tools
 */
@Entity
public class GalleryCommentBean implements java.io.Serializable {

	// Fields

	private String commentId;
	private String userId;
	  
	private String nickName;
	private String headImg;
	private String commentDetail;
	private String replayId;
	private String replayNickName;
	private String createTime;
	private Integer commentType;
	private Integer userType;
    
	private Integer replayUserType;
	// Constructors

	public Integer getReplayUserType() {
		return replayUserType;
	}

	public void setReplayUserType(Integer replayUserType) {
		this.replayUserType = replayUserType;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getCommentType() {
		return commentType;
	}

	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}

	/** default constructor */


	// Property accessors

	public String getCommentId() {
		return this.commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}



	public String getCommentDetail() {
		return this.commentDetail;
	}

	public void setCommentDetail(String commentDetail) {
		this.commentDetail = commentDetail;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getReplayId() {
		return replayId;
	}

	public void setReplayId(String replayId) {
		this.replayId = replayId;
	}

	public String getReplayNickName() {
		return replayNickName;
	}

	public void setReplayNickName(String replayNickName) {
		this.replayNickName = replayNickName;
	}

	


}