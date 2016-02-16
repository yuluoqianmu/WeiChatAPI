package com.laipai.galaryManInfo.dto;

public class SimpleImage {
	public static final String LP_GALLERY_IMGURL="/lpGalleryImg";
	public static final String LP_GALLERY_SOURCE="/gallarySource";
	public static final int LP_GALLERY_TOPNUMBER=6;
	
	public static final int LP_GALLERY_PIC_WITH=1024;
	public static final int LP_GALLERY_PIC_HEIGHT=700;
	public SimpleImage(byte[] img,String contentType,String suffix,String partNumber){
		this.img=img;
		this.contentType=contentType;
		this.suffix=suffix;
		this.partNumber=partNumber;
	
	}
	public SimpleImage(){}
	private byte[] img;
	private String contentType;
	private String suffix;
	private String partNumber;
	
	public String getPartNumber() {
		return partNumber;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
