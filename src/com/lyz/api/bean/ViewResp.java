package com.lyz.api.bean;

import com.lyz.labelData.bean.ViewData;

/**
 * 视图文件响应类
 * @author luzi
 *
 */
public class ViewResp extends BaseResp {

	private ViewData data;
	
	public ViewResp(int rCode, ViewData data){
		
		super(rCode);
		this.data = data;
	}


	public ViewData getData() {
		return data;
	}

	public void setData(ViewData data) {
		this.data = data;
	}
	
	
}
