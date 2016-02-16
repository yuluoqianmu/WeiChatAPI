package com.lyz.api.bean;

import com.lyz.validate.ValidateNotEmpty;
import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 详情处理类
 * 详情处理一般为详情内容，但也有可能关联其他一些东西
 * @author luzi
 *
 */
public class DetailReq extends BaseReq {
	/*view key id:视图规则id*/
	@ValidateNotNull(message="视图id")
	@ValidateSize(minSize="1",maxSize="100",message="视图id")
	private String vid;
	/*single data key:单个数据请求的key*/
	@ValidateNotNull(message="实体id")
	@ValidateSize(minSize="1",maxSize="100",message="实体id")
	private String sdk;
	/*groupName*/
	@ValidateNotNull(message="组名")
	@ValidateSize(minSize="1",maxSize="100",message="组名")
	private String gn;

	public String getSdk() {
		return sdk;
	}

	public void setSdk(String sdk) {
		this.sdk = sdk;
	}

	public String getGn() {
		return gn;
	}

	public void setGn(String gn) {
		this.gn = gn;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}
	
	
	
	
	
}
