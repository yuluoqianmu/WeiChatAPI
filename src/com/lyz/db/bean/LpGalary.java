package com.lyz.db.bean;
/**
 * 作品集
 * @author luyongzhao
 *
 */
public class LpGalary {
	
	/**/
	private String galaryId;

	/*用户Id*/
	private String userId;

	/*服务序号*/
	private String serviceId;

	/*作品集的主题名称*/
	private String subjectName;

	/*描述*/
	private String galaryDesc;

	/*作品集封面*/
	private String galaryCover;

	/*创建时间*/
	private String creatTime;

	/*控制相关数据来源-TRUE：实际数据；FALSE：为手工数据*/
	private int controlSource;

	/*观看次数*/
	private int viewNumber;

	/*喜欢次数*/
	private String likeNumber;

	/*评论次数*/
	private int commentNumber;

	/*作品集状态 0：开启；1：隐藏*/
	private int galaryStatus;

	/*排序字段*/
	private int galaryIndex;

	/*修改时间*/
	private String modifyTime;

	/*修改人*/
	private String modifyName;

	/*作品集是否有效，可以使用 0：有效；1：无效(已被删除)*/
	private int status;

	/*排序控制 0：正常排序；1：手工控制*/
	private int indexControl;

	/*根据评论量、喜欢量、浏览量计算的作品集热度分数（排序依据）*/
	private double galaryScores;

	/*封面原图*/
	private String coverSource;

	public String getGalaryId() {
		return galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getGalaryDesc() {
		return galaryDesc;
	}

	public void setGalaryDesc(String galaryDesc) {
		this.galaryDesc = galaryDesc;
	}

	public String getGalaryCover() {
		return galaryCover;
	}

	public void setGalaryCover(String galaryCover) {
		this.galaryCover = galaryCover;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public int getControlSource() {
		return controlSource;
	}

	public void setControlSource(int controlSource) {
		this.controlSource = controlSource;
	}

	public int getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(int viewNumber) {
		this.viewNumber = viewNumber;
	}

	public String getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(String likeNumber) {
		this.likeNumber = likeNumber;
	}

	public int getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	public int getGalaryStatus() {
		return galaryStatus;
	}

	public void setGalaryStatus(int galaryStatus) {
		this.galaryStatus = galaryStatus;
	}

	public int getGalaryIndex() {
		return galaryIndex;
	}

	public void setGalaryIndex(int galaryIndex) {
		this.galaryIndex = galaryIndex;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIndexControl() {
		return indexControl;
	}

	public void setIndexControl(int indexControl) {
		this.indexControl = indexControl;
	}

	public double getGalaryScores() {
		return galaryScores;
	}

	public void setGalaryScores(double galaryScores) {
		this.galaryScores = galaryScores;
	}

	public String getCoverSource() {
		return coverSource;
	}

	public void setCoverSource(String coverSource) {
		this.coverSource = coverSource;
	}
	
	
}
