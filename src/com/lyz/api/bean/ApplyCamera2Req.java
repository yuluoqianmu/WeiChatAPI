package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 申请成为摄影师2：上传作品和身份证
 * @author luzi
 *
 */
public class ApplyCamera2Req extends BaseReq {
	@ValidateNotNull(message="用户id")
	@ValidateSize(message="用户id",minSize="10",maxSize="50")
	private String userId;
	@ValidateNotNull(message="省份证图片id")
	@ValidateSize(message="省份证图片id",minSize="10",maxSize="50")
	private String personId;
	/*多张作品通过逗号分割*/
	@ValidateNotNull(message="作品图片id")
	@ValidateSize(message="作品图片id",minSize="30",maxSize="150")
	private String galaryIds;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getGalaryIds() {
		return galaryIds;
	}
	public void setGalaryIds(String galaryIds) {
		this.galaryIds = galaryIds;
	}
	
	
	
}
