package com.laipai.galaryManInfo.bean;

public class NewIntroducedBean implements java.io.Serializable {
	
	/*-- 新晋摄影师背景图片业务路径-- */
	public static final String LP_NEWINTRODUCE_IMGURL = "/newIntroduce";
	
	private String introduceId;
	private String title;
	private String createTime;
	private String content;
	private String imgUrl;
	
	public String getIntroduceId() {
		return introduceId;
	}
	public void setIntroduceId(String introduceId) {
		this.introduceId = introduceId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
