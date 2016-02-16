package com.laipai.galaryManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpGalaryExtend entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpGalaryExtend implements java.io.Serializable {

	// Fields

	private String extendId;
	private LpGalary lpGalary;
	private Integer viewNumber;
	private Integer likeNumber;
	private Integer commentNumber;
	private Timestamp modifyTime;
	private String modifyName;

	// Constructors

	/** default constructor */
	public LpGalaryExtend() {
	}

	/** full constructor */
	public LpGalaryExtend(LpGalary lpGalary, Integer viewNumber,
			Integer likeNumber, Integer commentNumber, Timestamp modifyTime,
			String modifyName) {
		this.lpGalary = lpGalary;
		this.viewNumber = viewNumber;
		this.likeNumber = likeNumber;
		this.commentNumber = commentNumber;
		this.modifyTime = modifyTime;
		this.modifyName = modifyName;
	}

	// Property accessors

	public String getExtendId() {
		return this.extendId;
	}

	public void setExtendId(String extendId) {
		this.extendId = extendId;
	}

	public LpGalary getLpGalary() {
		return this.lpGalary;
	}

	public void setLpGalary(LpGalary lpGalary) {
		this.lpGalary = lpGalary;
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

	public String getModifyName() {
		return this.modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

}