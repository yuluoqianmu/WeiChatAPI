package com.laipai.galaryManInfo.pojo;

import java.sql.Timestamp;

import javax.persistence.Entity;

/**
 * LpGalaryDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpGalaryDetail implements java.io.Serializable {

	// Fields

	private String photoId;
	private LpGalary lpGalary;
	private String photoSrc;
	private String photoName;
	private String photoUploadName;
	private Timestamp creatTime;
	private Integer photoIndex;
	private Integer status;
	private Boolean galaryConver;
	private String photoSource;
	

	// Constructors

	public String getPhotoSource() {
		return photoSource;
	}

	public void setPhotoSource(String photoSource) {
		this.photoSource = photoSource;
	}

	/** default constructor */
	public LpGalaryDetail() {
	}

	// Property accessors

	public String getPhotoId() {
		return this.photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public LpGalary getLpGalary() {
		return this.lpGalary;
	}

	public void setLpGalary(LpGalary lpGalary) {
		this.lpGalary = lpGalary;
	}

	public String getPhotoSrc() {
		return this.photoSrc;
	}

	public void setPhotoSrc(String photoSrc) {
		this.photoSrc = photoSrc;
	}

	public String getPhotoName() {
		return this.photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoUploadName() {
		return this.photoUploadName;
	}

	public void setPhotoUploadName(String photoUploadName) {
		this.photoUploadName = photoUploadName;
	}

	public Timestamp getCreatTime() {
		return this.creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	
	public Integer getPhotoIndex() {
		return photoIndex;
	}

	public void setPhotoIndex(Integer photoIndex) {
		this.photoIndex = photoIndex;
	}

	public Boolean getGalaryConver() {
		return this.galaryConver;
	}

	public void setGalaryConver(Boolean galaryConver) {
		this.galaryConver = galaryConver;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

/*	public LpGalaryDetail clone(){ 
		LpGalaryDetail o = null; 
		try{ 
			o = (LpGalaryDetail)super.clone(); 
		}catch(CloneNotSupportedException e){ 
			e.printStackTrace(); 
		} 
		return o; 
	} */

}