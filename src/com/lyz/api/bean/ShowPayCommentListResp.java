package com.lyz.api.bean;

import java.util.List;

import com.laipai.img.ImgUtil;
import com.laipai.util.EmojiFilter;
import com.lyz.db.bean.LpPayComment;

public class ShowPayCommentListResp extends BaseResp {
	
	/*评论列表*/
	private List<Cmt> cmtList;

	
	
	public List<Cmt> getCmtList() {
		return cmtList;
	}



	public void setCmtList(List<Cmt> cmtList) {
		this.cmtList = cmtList;
	}



	public static class Cmt{
		
		/*评论者姓名*/
		private String userName;

		/*评论者头像*/
		private String userHeadImg;

		/*服务名称*/
		private String serviceName;

		/*评分，1-5*/
		private int cmtScore;

		/*评论内容*/
		private String cmtContent;
		
		/*评论时间*/
		private String time;
		

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserHeadImg() {
			return userHeadImg;
		}

		public void setUserHeadImg(String userHeadImg) {
			this.userHeadImg = ImgUtil.getImgUrl(userHeadImg, "webp", 100);
		}

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		public int getCmtScore() {
			return cmtScore;
		}

		public void setCmtScore(int cmtScore) {
			this.cmtScore = cmtScore;
		}

		public String getCmtContent() {
			return cmtContent;
		}

		public void setCmtContent(String cmtContent) {
			this.cmtContent = EmojiFilter.filterEmoji(cmtContent);
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
		
		
		
		
	}
}
