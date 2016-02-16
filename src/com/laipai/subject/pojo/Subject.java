package com.laipai.subject.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * 

 * @Description:主题实体类

 * @author:朱鑫

 * @time:2014-12-16 下午5:17:42
 */
@Entity
public class Subject implements java.io.Serializable{
	
	/** 
	 * @Fields serialVersionUID : TODO 
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
	 * @Fields subject_img : 专题横图
	 */  
	private String subject_img2;
	
	/** 
	 * @Fields create_time : 创建时间 
	 */  
	private Date create_time;
	
	/** 
	 * @Fields create_user_name : 创建人
	 */  
	private String create_user_name;
	
	/** 
	 * @Fields modify_user_name : 修改人 
	 */  
	private String modify_user_name;
	/** 
	 * @Fields modify_time : 修改时间 
	 */  
	private Date modify_time;
	
	/** 
	 * @Fields gallery_numbe : 作品集数量 
	 */  
	private Integer gallery_number;
	
	/** 
	 * @Fields access_number : 访问量 
	 */  
	private BigDecimal access_number;
	
	/** 
	 * @Fields like_number : 点赞量 
	 */  
	private BigDecimal like_number;
	
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
	private Date online_time;
	
	/** 
	 * @Fields seq_number : 排列序号
	 */  
	private Integer seq_number;
	
	/** 
	 * @Fields set : 专题与作品集为多对多的关系 
	 */  
	private Set<LpGalary> set = new HashSet<LpGalary>();

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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}


	public Date getModify_time() {
		return modify_time;
	}

	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}

	public Integer getGallery_number() {
		return gallery_number;
	}

	public void setGallery_number(Integer gallery_number) {
		this.gallery_number = gallery_number;
	}

	public BigDecimal getAccess_number() {
		return access_number;
	}

	public void setAccess_number(BigDecimal access_number) {
		this.access_number = access_number;
	}

	public BigDecimal getLike_number() {
		return like_number;
	}

	public void setLike_number(BigDecimal like_number) {
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

	public Date getOnline_time() {
		return online_time;
	}

	public void setOnline_time(Date online_time) {
		this.online_time = online_time;
	}

	public Integer getSeq_number() {
		return seq_number;
	}

	public void setSeq_number(Integer seq_number) {
		this.seq_number = seq_number;
	}

	public Set<LpGalary> getSet() {
		return set;
	}

	public void setSet(Set<LpGalary> set) {
		this.set = set;
	}

	public Subject() {
		super();
		this.subject_id = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeSubject)+"";
	}

//	public Subject(String subject_id, String subject_name,
//			Integer index_location, Integer subject_location) {
//		super();
//		this.subject_id = subject_id;
//		this.subject_name = subject_name;
//		this.index_location = index_location;
//		this.subject_location = subject_location;
//	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public String getModify_user_name() {
		return modify_user_name;
	}

	public void setModify_user_name(String modify_user_name) {
		this.modify_user_name = modify_user_name;
	}

	public String getSubject_img2() {
		return subject_img2;
	}

	public void setSubject_img2(String subject_img2) {
		this.subject_img2 = subject_img2;
	}
	
	
}
