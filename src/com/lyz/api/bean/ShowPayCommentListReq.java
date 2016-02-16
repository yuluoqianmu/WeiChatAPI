package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 展示支付评论列表
 * @author luyongzhao
 *
 */
public class ShowPayCommentListReq extends BaseReq {
	
	@ValidateNotNull(message="摄影师id")
	@ValidateSize(message="摄影师id",minSize="10",maxSize="50")
	private String cameraId;
	
	/*每页展示的数量*/
	private int pageSize = 10;
	
	/*页码*/
	private int pageNo = 1;

	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
}
