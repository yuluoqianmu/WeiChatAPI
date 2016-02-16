package com.laipai.subject.pojo;

import javax.persistence.Entity;


/**
 * 

 * @Description:专题表与作品集表的中间表

 * @author:朱鑫

 * @time:2014-12-18 下午2:05:04
 */
@Entity
public class SubjectDetail implements java.io.Serializable{
	
	/** 
	 * @Fields serialVersionUID : TODO 
	 */  
	private static final long serialVersionUID = 1L;

	/** 
	 * @Fields subject_id : 外键关联专题表 
	 */  
	private String subject_id;
	
	/** 
	 * @Fields gallery_id : 外键关联作品集表
	 */  
	private String gallery_id;
	
	private Integer subjectGalaryLocation;

	public Integer getSubjectGalaryLocation() {
		return subjectGalaryLocation;
	}

	public void setSubjectGalaryLocation(Integer subjectGalaryLocation) {
		this.subjectGalaryLocation = subjectGalaryLocation;
	}

	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getGallery_id() {
		return gallery_id;
	}

	public void setGallery_id(String gallery_id) {
		this.gallery_id = gallery_id;
	}

	public SubjectDetail() {
		super();
	}
	
}
