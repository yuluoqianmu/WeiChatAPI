package com.laipai.cameraManInfo.pojo;

import java.io.Serializable;

public class LpCameramanStyle implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userId;
	private String styleId;
	

	

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStyleId() {
		return styleId;
	}
	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}
}
