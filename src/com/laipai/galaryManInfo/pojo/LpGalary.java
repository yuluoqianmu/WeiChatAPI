package com.laipai.galaryManInfo.pojo;

import java.io.File;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.subject.pojo.Subject;
import com.laipai.userManInfo.pojo.LpUser;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * LpGalary entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpGalary implements java.io.Serializable,Cloneable {
    
	// Fields
    private String subjectName;
	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	private String galaryId;
	private LpUser lpUser;
	private LpService lpService;
	private String galaryDesc;
	private String galaryCover;
	private Timestamp creatTime;
	private Integer controlSource;
	private Integer viewNumber;
	private Integer likeNumber;
	private Integer commentNumber;
	private Integer galaryStatus;

	private double galaryScores;

	private Integer status;


	private Timestamp modifyTime;
	private String modifyName;
    private LpGalaryExtend lpGalaryExtends;
    private Integer orderNumber;
    private Integer controlIndex;
    private Integer galaryIndex;
    private String coverSource;   
    /*综合得分*/
    private double zhScore;
    
    public LpGalary(){
    	
    	this.galaryId = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typeGalary)+"";
    }

	public String getCoverSource() {
		return coverSource;
	}

	public void setCoverSource(String coverSource) {
		this.coverSource = coverSource;
	}

	public Integer getGalaryIndex() {
		return galaryIndex;
	}

	public void setGalaryIndex(Integer galaryIndex) {
		this.galaryIndex = galaryIndex;
	}

	public Integer getControlIndex() {
		return controlIndex;
	}

	public void setControlIndex(Integer controlIndex) {
		this.controlIndex = controlIndex;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public LpGalaryExtend getLpGalaryExtends() {
		return lpGalaryExtends;
	}

	public void setLpGalaryExtends(LpGalaryExtend lpGalaryExtends) {
		this.lpGalaryExtends = lpGalaryExtends;
	}



	private Set lpGalaryDetails = new HashSet(0);
	private Set lpComments = new HashSet(0);
	private Set lpLikes = new HashSet(0);
	private Set<LpStyle> lpStyle=new HashSet<LpStyle>();
	public Set<LpStyle> getLpStyle() {
		return lpStyle;
	}

	public void setLpStyle(Set<LpStyle> lpStyle) {
		this.lpStyle = lpStyle;
	}
	/** 
	 * @Fields set : 与专题是对对多的关系
	 */  
	private Set<Subject> set = new HashSet<Subject>();
	// Constructors

	
	// Property accessors

	public Set<Subject> getSet() {
		return set;
	}

	public void setSet(Set<Subject> set) {
		this.set = set;
	}

	public String getGalaryId() {
		return this.galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public LpUser getLpUser() {
		return this.lpUser;
	}

	public void setLpUser(LpUser lpUser) {
		this.lpUser = lpUser;
	}

	public LpService getLpService() {
		return this.lpService;
	}

	public void setLpService(LpService lpService) {
		this.lpService = lpService;
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
	public double getGalaryScores() {
		return galaryScores;
	}

	public void setGalaryScores(double galaryScores) {
		this.galaryScores = galaryScores;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set getLpGalaryDetails() {
		return this.lpGalaryDetails;
	}

	public void setLpGalaryDetails(Set lpGalaryDetails) {
		this.lpGalaryDetails = lpGalaryDetails;
	}

	public Set getLpComments() {
		return this.lpComments;
	}

	public void setLpComments(Set lpComments) {
		this.lpComments = lpComments;
	}

	public Set getLpLikes() {
		return this.lpLikes;
	}

	public void setLpLikes(Set lpLikes) {
		this.lpLikes = lpLikes;
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
	

	public double getZhScore() {
		return zhScore;
	}

	public void setZhScore(double zhScore) {
		this.zhScore = zhScore;
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