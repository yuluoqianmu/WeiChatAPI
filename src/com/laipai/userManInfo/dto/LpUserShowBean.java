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
public class LpUserShowBean implements java.io.Serializable {
   //用户Id
	private String userId;
   //用户账号
	private String userName;
	//用户昵称
	private String nickName;
	/*用户昵称，用户身份角色通过此字段统一控制*/
	private String nickName2;
	
	private String realName;
	
	private int checkStatus;
		
	private String followId;
	
	
	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	//用户头像
	private String headImage;
	//用户性别
	private Integer userGender;
	//用户手机
	private String mobile;
    private String grapherCarmer;	
	private String grapherDesc;
	//用户预约数
	private int userOrderNumber;
   
	//摄影师订单数
	private int cameramanOrder;
	//关注数
    private int followNumber;
    //喜欢数
    private int userlikeNumber;
    //粉丝数
    private int fansNumber;
    /*评论数*/
    private int payCmtNum;
    //城市
    private String city;
    //风格
    private String styles;
    //服务数
    private int serviceNumber;
    //作品集数
    private int galleryNumber;
    //用户类型
    private Integer userType;
    
	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStyles() {
		return styles;
	}

	public void setStyles(String styles) {
		this.styles = styles;
	}

	public int getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(int serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public int getGalleryNumber() {
		return galleryNumber;
	}

	public void setGalleryNumber(int galleryNumber) {
		this.galleryNumber = galleryNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Integer getUserGender() {
		return userGender;
	}

	public void setUserGender(Integer userGender) {
		this.userGender = userGender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
	 public int getUserOrderNumber() {
			return userOrderNumber;
		}

		public void setUserOrderNumber(int userOrderNumber) {
			this.userOrderNumber = userOrderNumber;
		}

		public int getCameramanOrder() {
			return cameramanOrder;
		}

		public void setCameramanOrder(int cameramanOrder) {
			this.cameramanOrder = cameramanOrder;
		}

		public int getFollowNumber() {
			return followNumber;
		}

		public void setFollowNumber(int followNumber) {
			this.followNumber = followNumber;
		}

		public int getUserlikeNumber() {
			return userlikeNumber;
		}

		public void setUserlikeNumber(int userlikeNumber) {
			this.userlikeNumber = userlikeNumber;
		}

		public int getFansNumber() {
			return fansNumber;
		}

		public void setFansNumber(int fansNumber) {
			this.fansNumber = fansNumber;
		}

		public int getCheckStatus() {
			return checkStatus;
		}

		public void setCheckStatus(int checkStatus) {
			this.checkStatus = checkStatus;
		}

		public String getNickName2() {
			return nickName2;
		}

		public void setNickName2(String nickName2) {
			this.nickName2 = nickName2;
		}

		public int getPayCmtNum() {
			return payCmtNum;
		}

		public void setPayCmtNum(int payCmtNum) {
			this.payCmtNum = payCmtNum;
		}

	
}