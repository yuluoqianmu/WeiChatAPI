package com.laipai.galaryManInfo.dto;

import javax.persistence.Entity;

@Entity
public class LpGalleryBean implements java.io.Serializable {
		//作品ID
	    private String galaryId;
	    //描述
		private String galaryDesc;
		//拍摄天数
		private String shootTime;
		//照片张数
		private Integer pictureNum;
		//精修张数
		private Integer truingNum;
		//服装
		private String clothes;
		//化妆
		private String facepaint;
		//附加说明
		private String instructions;
		
		private String subjectName;
		
		private String mobille;
		
		private double price;
				
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public String getMobille() {
			return mobille;
		}
		public void setMobille(String mobille) {
			this.mobille = mobille;
		}
		//是否赞过
		private int isLike;
		//赞数量
		private Integer likeNumber;
		
		private Integer viewNumber;
		
		private Integer commentNumber;
		/*封面图*/
		private String galaryCover;
		
		
		
		public Integer getCommentNumber() {
			return commentNumber;
		}
		public void setCommentNumber(Integer commentNumber) {
			this.commentNumber = commentNumber;
		}
		public Integer getViewNumber() {
			return viewNumber;
		}
		public void setViewNumber(Integer viewNumber) {
			this.viewNumber = viewNumber;
		}
		public int getIsLike() {
			return isLike;
		}
		public void setIsLike(int isLike) {
			this.isLike = isLike;
		}
		private String cameramanId;
		public String getCameramanId() {
			return cameramanId;
		}
		public void setCameramanId(String cameramanId) {
			this.cameramanId = cameramanId;
		}
		public String getGalaryId() {
			return galaryId;
		}
		public void setGalaryId(String galaryId) {
			this.galaryId = galaryId;
		}
		public String getGalaryDesc() {
			return galaryDesc;
		}
		public void setGalaryDesc(String galaryDesc) {
			this.galaryDesc = galaryDesc;
		}
		public String getShootTime() {
			return shootTime;
		}
		public void setShootTime(String shootTime) {
			this.shootTime = shootTime;
		}
		public Integer getPictureNum() {
			return pictureNum;
		}
		public void setPictureNum(Integer pictureNum) {
			this.pictureNum = pictureNum;
		}
		public Integer getTruingNum() {
			return truingNum;
		}
		public void setTruingNum(Integer truingNum) {
			this.truingNum = truingNum;
		}
		public String getClothes() {
			return clothes;
		}
		public void setClothes(String clothes) {
			this.clothes = clothes;
		}
		public String getFacepaint() {
			return facepaint;
		}
		public void setFacepaint(String facepaint) {
			this.facepaint = facepaint;
		}
		public String getInstructions() {
			return instructions;
		}
		public void setInstructions(String instructions) {
			this.instructions = instructions;
		}
		public Integer getLikeNumber() {
			return likeNumber;
		}
		public void setLikeNumber(Integer likeNumber) {
			this.likeNumber = likeNumber;
		}
		public String getSubjectName() {
			return subjectName;
		}
		public void setSubjectName(String subjectName) {
			this.subjectName = subjectName;
		}
		public String getGalaryCover() {
			return galaryCover;
		}
		public void setGalaryCover(String galaryCover) {
			this.galaryCover = galaryCover;
		}
		
		
		
}
