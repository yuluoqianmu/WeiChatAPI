package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 视图数据请求
 * @author luzi
 *
 */
public class ViewReq extends BaseReq {
	/**
	 * 视图id
	 */
	@ValidateNotNull(message="视图id")
	@ValidateSize(minSize="1",maxSize="100",message="视图id")
	private String vid;

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}
	
	
}
