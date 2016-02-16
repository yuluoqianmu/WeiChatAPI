package com.laipai.userManInfo.dto;

import javax.persistence.Entity;
/**
 * 

 * @Description:普通用户

 * @author:lxd

 * @time:2015-1-26 下午5:27:01
 */
@Entity
public class GeneralUser implements java.io.Serializable {
	//用户Id
	private String userId;
   //用户账号
	private String userName;
	//用户昵称
	private String nickName;
	//用户头像
    private String headImage;   
  //关注数
    private int followNumber;
    //喜欢数
    private int userlikeNumber;
    
    
    private String followId;
	public String getFollowId() {
		return followId;
	}
	public void setFollowId(String followId) {
		this.followId = followId;
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
    
}
