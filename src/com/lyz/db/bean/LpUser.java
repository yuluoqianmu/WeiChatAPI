package com.lyz.db.bean;

import java.sql.Date;

public class LpUser {
	/**/
	private String userId;

	/*城市Id*/
	private String cityId;

	/*用户账号*/
	private String userName;

	/*注册时间*/
	private Date registerTime;

	/*密码*/
	private String userPassword;

	/*昵称*/
	private String nickName;

	/*用户备注*/
	private String userRemark;

	/*用户类型：0普通用户，1摄影师,2管理员*/
	private int userType;

	/*用户状态*/
	private int userStatus;

	/*手机号*/
	private String mobile;

	/*性别：0,：男1：女*/
	private int userGender;

	/*头像照片*/
	private String headImage;

	/*头像照片*/
	private String idCardImage;

	/*修改人*/
	private String modifyId;

	/*摄影师认证时间*/
	private Date validTime;

	/*真是姓名*/
	private String realName;

	/*摄影器材?*/
	private String grapherCarmer;

	/*摄影师描述?*/
	private String grapherDesc;

	/*是否解绑微博或微信账号，默认FALSE：不解绑，TRUE：解绑*/
	private byte bindingStatus;

	/*账号来源0：注册；1：微博；2：微信*/
	private int accountSource;

	/*排序Id*/
	private int sequenceNumber;

	/*修改时间*/
	private Date modifyTime;

	/*最后一次修改密码的时间*/
	private Date lastUpdatePwdTime;

	/*最后活动时间*/
	private Date lastActivityTime;

	/*最后登录设备*/
	private String lastMobileDeviceId;

	/*登录状态: 0已登录 1未登录*/
	private int loginStatus;

	/*最后登录设备*/
	private String lastMobileToken;

	/*邮箱*/
	private String userEmail;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserRemark() {
		return userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getUserGender() {
		return userGender;
	}

	public void setUserGender(int userGender) {
		this.userGender = userGender;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getIdCardImage() {
		return idCardImage;
	}

	public void setIdCardImage(String idCardImage) {
		this.idCardImage = idCardImage;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
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

	public String getGrapherDesc() {
		return grapherDesc;
	}

	public void setGrapherDesc(String grapherDesc) {
		this.grapherDesc = grapherDesc;
	}

	public byte getBindingStatus() {
		return bindingStatus;
	}

	public void setBindingStatus(byte bindingStatus) {
		this.bindingStatus = bindingStatus;
	}

	public int getAccountSource() {
		return accountSource;
	}

	public void setAccountSource(int accountSource) {
		this.accountSource = accountSource;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getLastUpdatePwdTime() {
		return lastUpdatePwdTime;
	}

	public void setLastUpdatePwdTime(Date lastUpdatePwdTime) {
		this.lastUpdatePwdTime = lastUpdatePwdTime;
	}

	public Date getLastActivityTime() {
		return lastActivityTime;
	}

	public void setLastActivityTime(Date lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}

	public String getLastMobileDeviceId() {
		return lastMobileDeviceId;
	}

	public void setLastMobileDeviceId(String lastMobileDeviceId) {
		this.lastMobileDeviceId = lastMobileDeviceId;
	}

	public int getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getLastMobileToken() {
		return lastMobileToken;
	}

	public void setLastMobileToken(String lastMobileToken) {
		this.lastMobileToken = lastMobileToken;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	
	
}
