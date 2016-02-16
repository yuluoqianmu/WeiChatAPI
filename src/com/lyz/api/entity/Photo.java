package com.lyz.api.entity;

import com.lyz.labelData.bean.BaseData;
/**
 * 图片
 * @author luzi
 *
 */
public class Photo extends BaseData {
	
	/*图片id*/
	private String photoId;

	/*作品ID*/
	private String galaryId;

	/*图片地址*/
	private String photoSrc;

	/*此照片在作品集中的排序数*/
//	private int photoIndex;

	/*照片是否有效，可以使用 0：有效；1：无效(已被删除)*/
	private int status;

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String getGalaryId() {
		return galaryId;
	}

	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}

	public String getPhotoSrc() {
		return photoSrc;
	}

	public void setPhotoSrc(String photoSrc) {
		this.photoSrc = photoSrc;
	}

//	public int getPhotoIndex() {
//		return photoIndex;
//	}
//
//	public void setPhotoIndex(int photoIndex) {
//		this.photoIndex = photoIndex;
//	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
