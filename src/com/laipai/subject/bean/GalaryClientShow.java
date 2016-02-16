package com.laipai.subject.bean;

public class GalaryClientShow {

	private String galaryId;
	
	private String author;
	
	private String subjectName;
	
	private String imgCover;
	
	private int likeNumber;
	
	private int vierNumber;
	
	private int commentNumber;
	
	private long price;
	

	public String getGalaryId() {
		return galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getImgCover() {
		return imgCover;
	}

	public void setImgCover(String imgCover) {
		this.imgCover = imgCover;
	}

	public int getLikeNumber() {
		return likeNumber;
	}

	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}

	public int getVierNumber() {
		return vierNumber;
	}

	public void setVierNumber(int vierNumber) {
		this.vierNumber = vierNumber;
	}

	public int getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(int commentNumber) {
		this.commentNumber = commentNumber;
	}

	public GalaryClientShow() {
		super();
	}

	public GalaryClientShow(String galaryId, String subjectName, String imgCover,
			int likeNumber, int vierNumber, int commentNumber) {
		super();
		this.galaryId = galaryId;
		this.subjectName = subjectName;
		this.imgCover = imgCover;
		this.likeNumber = likeNumber;
		this.vierNumber = vierNumber;
		this.commentNumber = commentNumber;
	}

}
