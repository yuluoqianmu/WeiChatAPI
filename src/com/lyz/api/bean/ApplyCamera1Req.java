package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidatePattern;
import com.lyz.validate.ValidateSize;
import com.lyz.validate.ValidateStringIn;
/**
 * 申请成为摄影师基础信息参数
 * @author luzi
 *
 */
public class ApplyCamera1Req extends BaseReq {
	@ValidateNotNull(message="城市id")
	@ValidateSize(message="城市id",minSize="10",maxSize="50")
	private String cityId;
	@ValidateNotNull(message="性别")
	@ValidateStringIn(value="男,女",message="性别")
	private String userGender;
	@ValidateNotNull(message="个人简介")
	@ValidateSize(message="个人简介",minSize="1",maxSize="100")
	private String grapherDesc;
	/*多种风格通过逗号分隔*/
	@ValidateNotNull(message="风格id")
	@ValidateSize(message="风格id",minSize="10",maxSize="50")
	private String styleIds;
	@ValidateNotNull(message="用户id")
	@ValidateSize(message="用户id",minSize="10",maxSize="50")
	private String userId;
	@ValidateNotNull(message="真实姓名")
	@ValidateSize(message="真实姓名",minSize="1",maxSize="8")
	private String realName;
	@ValidateNotNull(message="所用器材")
	@ValidateSize(message="所用器材",minSize="1",maxSize="50")
	private String grapherCarmer;
	@ValidateNotNull(message="联系电话")
	@ValidateSize(message="联系电话",minSize="7",maxSize="11")
	@ValidatePattern(message="手机号",pattern="^\\d+$")
	private String mobile;
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getGrapherDesc() {
		return grapherDesc;
	}
	public void setGrapherDesc(String grapherDesc) {
		this.grapherDesc = grapherDesc;
	}
	public String getStyleIds() {
		return styleIds;
	}
	public void setStyleIds(String styleIds) {
		this.styleIds = styleIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getGrapherCarmer() {
		return grapherCarmer;
	}
	public void setGrapherCarmer(String grapherCarmer) {
		this.grapherCarmer = grapherCarmer;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
