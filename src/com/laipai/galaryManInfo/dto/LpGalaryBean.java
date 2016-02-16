package com.laipai.galaryManInfo.dto;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.subject.pojo.Subject;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * LpGalary entity. @author MyEclipse Persistence Tools
 */
public class LpGalaryBean implements java.io.Serializable {
	// Fields
    private String subjectName;
	private String galaryId;
	private String galaryDesc;
	private String galaryCover;
	private Timestamp creatTime;
	private Integer controlSource;
	//浏览数量
	private Integer viewNumber;
	//赞数量
	private Integer likeNumber;
	//评论数量
	private Integer commentNumber;
	private Integer galaryStatus;
	private Integer galaryIndex;
	private Timestamp modifyTime;
	private String modifyName;
	private String userId;
	private String userName;
	
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	 
	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
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

	public Integer getControlSource() {
		return controlSource;
	}

	public void setControlSource(Integer controlSource) {
		this.controlSource = controlSource;
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

	// Fields
	private File  imgsFile[];
	//文件类型
	private String  imgsFileContentType[];
	//文件名
	private String  imgsFileFileName[];

	public File[] getImgsFile() {
		return imgsFile;
	}
	public void setImgsFile(File[] imgsFile) {
		this.imgsFile = imgsFile;
	}
	public String[] getImgsFileContentType() {
		return imgsFileContentType;
	}

	public void setImgsFileContentType(String[] imgsFileContentType) {
		this.imgsFileContentType = imgsFileContentType;
	}

	public String[] getImgsFileFileName() {
		return imgsFileFileName;
	}
	public void setImgsFileFileName(String[] imgsFileFileName) {
		this.imgsFileFileName = imgsFileFileName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}