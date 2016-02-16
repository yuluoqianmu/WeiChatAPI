package com.laipai.subject.bean;

import java.util.Date;
import javax.persistence.Entity;

/**
 * 

 * @Description:主题实体类

 * @author:朱鑫

 * @time:2014-12-16 下午5:17:42
 */
@Entity
public class SubjectBean implements java.io.Serializable{
	
	/** 
	 * @Fields serialVersionUID : 
	 */  
	private static final long serialVersionUID = 1L;

	/** 
	 * @Fields subject_id : 专题编号
	 */  
	private String subject_id; 
	
	/** 
	 * @Fields subject_name : 专题名称
	 */  
	private String subject_name;
	
	/** 
	 * @Fields subject_img : 专题图片 
	 */  
	private String subject_img;
	
	/** 
	 * @Fields create_time : 创建时间 
	 */  
	
	/** 
	 * @Fields create_user_name : 创建人
	 */  
	
	/** 
	 * @Fields modify_user_name : 修改人 
	 */  
	/** 
	 * @Fields modify_time : 修改时间 
	 */  
	
	/** 
	 * @Fields gallery_numbe : 作品集数量 
	 */  
	private Integer gallery_number;
	
	/** 
	 * @Fields access_number : 访问量 
	 */  
	private Integer access_number;
	
	/** 
	 * @Fields like_number : 点赞量 
	 */  
	private String like_number;
	
	/** 
	 * @Fields subject_status : 上线状态
	 */  
	private Integer subject_status;
	
	/** 
	 * @Fields index_location : 首页位置
	 */  
	private Integer index_location;
	
	/** 
	 * @Fields subject_location : 专题页面位置
	 */  
	private Integer subject_location;
	
	/** 
	 * @Fields online_time : 上线时间 
	 */  
	
	/** 
	 * @Fields seq_number : 排列序号
	 */  
	
	public String getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(String subject_id) {
		this.subject_id = subject_id;
	}

	public String getSubject_name() {
		return subject_name;
	}

	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public String getSubject_img() {
		return subject_img;
	}

	public void setSubject_img(String subject_img) {
		this.subject_img = subject_img;
	}

	

	public Integer getGallery_number() {
		return gallery_number;
	}

	public void setGallery_number(Integer gallery_number) {
		this.gallery_number = gallery_number;
	}

	public Integer getAccess_number() {
		return access_number;
	}

	public void setAccess_number(Integer access_number) {
		this.access_number = access_number;
	}

	public String getLike_number() {
		return like_number;
	}

	public void setLike_number(String like_number) {
		this.like_number = like_number;
	}

	public Integer getSubject_status() {
		return subject_status;
	}

	public void setSubject_status(Integer subject_status) {
		this.subject_status = subject_status;
	}

	public Integer getIndex_location() {
		return index_location;
	}

	public void setIndex_location(Integer index_location) {
		this.index_location = index_location;
	}

	public Integer getSubject_location() {
		return subject_location;
	}

	public void setSubject_location(Integer subject_location) {
		this.subject_location = subject_location;
	}

	public SubjectBean() {
		super();
	}

	public SubjectBean(String subject_id, String subject_name,
			Integer index_location, Integer subject_location) {
		super();
		this.subject_id = subject_id;
		this.subject_name = subject_name;
		this.index_location = index_location;
		this.subject_location = subject_location;
	}

}
