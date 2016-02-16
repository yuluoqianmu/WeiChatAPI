package com.laipai.userManInfo.dto;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;



/**
 * LpUser entity. @author MyEclipse Persistence Tools
 */
@Entity
public class LpUserBean implements java.io.Serializable {

	/*-- 来拍用户业务路径-- */
	public static final String LP_USER_IMGURL = "/lpUserImg";
    /*--来拍 摄影师业务路径--*/	
	//public static final String LP_CAMERAMAN_IMGURL="/lpCameramanImg";
	
	public static final String LP_CAMERAMAN_PSW="111111";

	
	private String message;
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String userId;

	private String userName;//用户账号
	private Timestamp registerTime;//注册时间
	private String userPassword;
	private String nickName;//昵称
	private String userRemark;
	private Integer userType;
	private Integer userStatus;
	private String mobile;
	private Integer userGender;
	private String headImage;
	private String modifyId;
	private Timestamp validTime;
	private String realName;
	private String grapherCarmer;
	private String grapherDesc;
	private Boolean bindingStatus;
	private Integer accountSource;
	private Integer sequenceNumber;
	private Timestamp modifyTime;
	private Timestamp lastUpdatePwdTime;
    private String userEmail;
    
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	private String idCardImage;
	/*private Set lpGalaries = new HashSet(0);
	private Set lpComments = new HashSet(0);
	private Set lpFollowsForCameraId = new HashSet(0);
	private Set lpLoginHises = new HashSet(0);
	private Set lpLikes = new HashSet(0);
	private Set lpFollowsForUserId = new HashSet(0);
	private Set lpServices = new HashSet(0);
	private Set lpCameramanAppforms = new HashSet(0);
=======
>>>>>>> .r8740
<<<<<<< .mine

	private Set<LpStyle> lpStyle=new HashSet<LpStyle>();*/


	// Constructors

	// Property accessors


	/*public String getIdCardImage() {
		return idCardImage;
	}

	public void setIdCardImage(String idCardImage) {
		this.idCardImage = idCardImage;
	}*/

	public String getIdCardImage() {
		return idCardImage;
	}

	public void setIdCardImage(String idCardImage) {
		this.idCardImage = idCardImage;
	}


	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}



	/*public Set<LpStyle> getLpStyle() {
		return lpStyle;
	}

	public void setLpStyle(Set<LpStyle> lpStyle) {
		this.lpStyle = lpStyle;
	}
*/
/*	public LpCity getLpCity() {
		return this.lpCity;
	}

	public void setLpCity(LpCity lpCity) {
		this.lpCity = lpCity;
	}*/


	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserRemark() {
		return this.userRemark;
	}

	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserStatus() {
		return this.userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getUserGender() {
		return this.userGender;
	}

	public void setUserGender(Integer userGender) {
		this.userGender = userGender;
	}

	public String getHeadImage() {
		return this.headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getModifyId() {
		return this.modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public Timestamp getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Timestamp validTime) {
		this.validTime = validTime;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGrapherCarmer() {
		return this.grapherCarmer;
	}

	public void setGrapherCarmer(String grapherCarmer) {
		this.grapherCarmer = grapherCarmer;
	}

	public String getGrapherDesc() {
		return this.grapherDesc;
	}

	public void setGrapherDesc(String grapherDesc) {
		this.grapherDesc = grapherDesc;
	}

	public Boolean getBindingStatus() {
		return this.bindingStatus;
	}

	public void setBindingStatus(Boolean bindingStatus) {
		this.bindingStatus = bindingStatus;
	}

	public Integer getAccountSource() {
		return this.accountSource;
	}

	public void setAccountSource(Integer accountSource) {
		this.accountSource = accountSource;
	}

	public Integer getSequenceNumber() {
		return this.sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Timestamp getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Timestamp getLastUpdatePwdTime() {
		return this.lastUpdatePwdTime;
	}

	public void setLastUpdatePwdTime(Timestamp lastUpdatePwdTime) {
		this.lastUpdatePwdTime = lastUpdatePwdTime;
	}


	/*public Set getLpGalaries() {
		return this.lpGalaries;
	}

	public void setLpGalaries(Set lpGalaries) {
		this.lpGalaries = lpGalaries;
	}

	public Set getLpComments() {
		return this.lpComments;
	}

	public void setLpComments(Set lpComments) {
		this.lpComments = lpComments;
	}

	public Set getLpFollowsForCameraId() {
		return this.lpFollowsForCameraId;
	}

	public void setLpFollowsForCameraId(Set lpFollowsForCameraId) {
		this.lpFollowsForCameraId = lpFollowsForCameraId;
	}

	public Set getLpLoginHises() {
		return this.lpLoginHises;
	}

	public void setLpLoginHises(Set lpLoginHises) {
		this.lpLoginHises = lpLoginHises;
	}

	public Set getLpLikes() {
		return this.lpLikes;
	}

	public void setLpLikes(Set lpLikes) {
		this.lpLikes = lpLikes;
	}

	public Set getLpFollowsForUserId() {
		return this.lpFollowsForUserId;
	}

	public void setLpFollowsForUserId(Set lpFollowsForUserId) {
		this.lpFollowsForUserId = lpFollowsForUserId;
	}

	public Set getLpServices() {
		return this.lpServices;
	}

	public void setLpServices(Set lpServices) {
		this.lpServices = lpServices;
	}

	public Set getLpCameramanAppforms() {
		return this.lpCameramanAppforms;
=======
	public String getIdCardImage() {
		return idCardImage;
>>>>>>> .r8740
	}

	public void setIdCardImage(String idCardImage) {
		this.idCardImage = idCardImage;
	}

	public String toString()
	{
		return "user : userId = " + userId + ", userName = " + userName;
	}
	*/
}