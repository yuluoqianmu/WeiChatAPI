package com.lyz.api.bean;

import com.lyz.validate.ValidateNotNull;
import com.lyz.validate.ValidateSize;

/**
 * 修改个人信息
 * @author luzi
 *
 */
public class ModPerInfoReq extends BaseReq {
		
	@ValidateNotNull(message="用户id")
	@ValidateSize(message="用户id",minSize="10",maxSize="50")
	private String userId;
	/*城市id*/
	private String cityId;
	/*男、女*/
	private String userGender;
	/*昵称*/
	private String nickName;
	/*描述*/
	private String grapherDesc;
	/*拍摄风格id，多个通过逗号分割*/
	private String styleIds;
	/*拍摄设备*/
	private String grapherCarmer;
	/*联系电话*/
	private String mobile;
	/*用户头像*/
	private String headImage;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	
}
