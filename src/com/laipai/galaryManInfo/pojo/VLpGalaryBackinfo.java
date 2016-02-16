package com.laipai.galaryManInfo.pojo;

import java.sql.Timestamp;

/**
 * VLpGalaryBackinfoId entity. @author MyEclipse Persistence Tools
 */

public class VLpGalaryBackinfo implements java.io.Serializable,Cloneable {

	// Fields

	private String galaryId;
	private String userId;
	private String nickName;
	private String serviceId;
	private String subjectName;
	private String galaryDesc;
	private String galaryCover;
	private Timestamp creatTime;
	private Integer viewNumber;
	private Integer likeNumber;
	private Integer commentNumber;
	private Integer galaryStatus;
	private Integer galaryIndex;
	private Integer status;
	private Long price;
	private Integer orderNum;
	private Integer controlIndex;
	private double galaryScores;
	// Constructors

	/** default constructor */
	public VLpGalaryBackinfo() {
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getSubjectName() {
		return this.subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGalaryDesc() {
		return this.galaryDesc;
	}

	public void setGalaryDesc(String galaryDesc) {
		this.galaryDesc = galaryDesc;
	}

	public String getGalaryCover() {
		return this.galaryCover;
	}

	public void setGalaryCover(String galaryCover) {
		this.galaryCover = galaryCover;
	}

	public Timestamp getCreatTime() {
		return this.creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getGalaryStatus() {
		return this.galaryStatus;
	}

	public void setGalaryStatus(Integer galaryStatus) {
		this.galaryStatus = galaryStatus;
	}

	public Integer getGalaryIndex() {
		return this.galaryIndex;
	}

	public void setGalaryIndex(Integer galaryIndex) {
		this.galaryIndex = galaryIndex;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPrice() {
		return this.price;
	}

	public void setPrice(Long price) {
		this.price = price;
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

	public Integer getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(Integer commentNumber) {
		this.commentNumber = commentNumber;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getControlIndex() {
		return controlIndex;
	}

	public void setControlIndex(Integer controlIndex) {
		this.controlIndex = controlIndex;
	}

	public double getGalaryScores() {
		return galaryScores;
	}

	public void setGalaryScores(double galaryScores) {
		this.galaryScores = galaryScores;
	}

	public Object clone(){ 
		LpGalary o = null; 
		try{ 
		o = (LpGalary)super.clone(); 
		}catch(CloneNotSupportedException e){ 
		e.printStackTrace(); 
		} 
		return o; 
		} 		
}