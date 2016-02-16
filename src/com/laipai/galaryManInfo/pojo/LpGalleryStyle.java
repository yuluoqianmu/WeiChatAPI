package com.laipai.galaryManInfo.pojo;

import java.io.Serializable;

public class LpGalleryStyle implements Serializable{
	private static final long serialVersionUID = 1L;
	private String galaryId;
	private String styleId;
	

	


	public String getGalaryId() {
		return galaryId;
	}
	public void setGalaryId(String galaryId) {
		this.galaryId = galaryId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
}
