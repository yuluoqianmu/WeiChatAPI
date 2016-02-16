package com.laipai.serviceInfo.pojo;

import java.io.Serializable;

import javax.persistence.Entity;
@Entity
public class LpServiceStyle implements Serializable{
	private static final long serialVersionUID = 1L;
	private String detailId;
	private String styleId;
	

	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
}
