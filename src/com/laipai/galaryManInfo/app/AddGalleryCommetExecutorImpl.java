package com.laipai.galaryManInfo.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.Demo;
import com.laipai.base.util.GallerySort;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.SaveMessage;
import com.laipai.galaryManInfo.bean.NewIntroducedBean;
import com.laipai.galaryManInfo.dto.LpHeadShowGalaryBean;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.galaryManInfo.pojo.LpNewintroduce;
import com.laipai.galaryManInfo.pojo.VLpGalary;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.message.pojo.LpMessageMapping;
import com.laipai.message.util.PushNoticeMessage;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.util.EmojiFilter;
import com.tencent.xinge.XingeApp;

/**
 * 作品集详情--添加评论
 * 
 * @author zhh
 */
@NotLogin
@Service("AddGalleryCommetExecutorImpl")
public class AddGalleryCommetExecutorImpl implements RequestExecutor {
	
	private static final Logger logger = Logger.getLogger(AddGalleryCommetExecutorImpl.class);

	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService  userManInfoService;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		JSONObject baseJson = new JSONObject();
		JSONObject returnJson = new JSONObject();
		//获取服务器域名
		String serverName = json.getString("serverName");
		//登录用户的id
		String userId = json.getString("userId");
		LpUser user= null;
		//作品集id
		String galaryId = json.getString("galaryId");
		//评论内容
		String commentText = EmojiFilter.filterEmoji(json.getString("comment"));
		
		if(commentText==null || "".equals(commentText.trim())){
			return JSONTools.newAPIResult(0, "内容不允许包含表情！");
		}
		
		logger.info("commentText:"+commentText);
		
		LpGalary gallery=galleryService.getgalleryByID(galaryId);
		LpComment comment=new LpComment();
		comment.setCommentDetail(commentText);
		//评论的类型0（作品集的评论）
		comment.setCommentType(0);
		Timestamp creatTime =new Timestamp(new java.util.Date().getTime());
		comment.setCreateTime(creatTime);
		//每次评论和回复之后要计作品的分数（排序用）
		Double galleryScores= GallerySort.getHotsource(gallery.getCommentNumber()+1, gallery.getViewNumber(), gallery.getLikeNumber(), gallery.getCreatTime());
		gallery.setGalaryScores(galleryScores);
		gallery.setCommentNumber(gallery.getCommentNumber()+1);
		//修改假数据
		if(gallery.getControlSource() ==1){
			List<LpGalaryExtend> listExe = baseDao.queryListObjectAll("from LpGalaryExtend where lpGalary.galaryId='"+galaryId+"'");
			if(listExe!=null && listExe.size()>0){
				LpGalaryExtend extend = listExe.get(0);
				if(extend.getCommentNumber() == null){
					extend.setCommentNumber(1);
				}else{
					extend.setCommentNumber(extend.getCommentNumber()+1);
				}
				baseDao.updateObject(extend);
			}
		}
		
		
		if(StringUtils.isNotBlank(userId)){
			user= (LpUser) baseDao.getObjectById(LpUser.class, userId);
			if(user == null){
				return JSONTools.newAPIResult(1, "无效的用户(the userId is not validate)");
			}
			comment.setLpUser(user);
			returnJson.put("userId", user.getUserId());
			returnJson.put("nickName", user.getNickName());
//		    if(user.getHeadImage()!=null){
//			if(user.getAccountSource()==0){
//				returnJson.put("headImage", serverName + user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//			}else{
//				returnJson.put("headImage",user.getHeadImage());
//			}
//		    }else{
//		    	
//		    	returnJson.put("headImage","");	
//		    }
			returnJson.put("headImage",ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
		}
		if(gallery!=null){
			comment.setLpGalary(gallery);
		}
		/*galleryService.saveComment(comment);*/
		Serializable commentId= baseDao.saveObjectReturnId(comment);		
		
		returnJson.put("commentId", commentId);
		
		returnJson.put("resultMessage", "评论成功");
      
		//推送评论通知
		addPushCommentAlert(gallery, user, parameters,commentId.toString());
//		XingeApp.pushTokenAndroid(LaipaiConstants.ANDROID_ACCESSID, LaipaiConstants.ANDROID_SECRETKEY, "ceshi推送", "拍的照片很好啊", "ca736c957833a93c421bb1e72981fcb69169b3f4");
//		XingeApp.pushAllAndroid(LaipaiConstants.ANDROID_ACCESSID, LaipaiConstants.ANDROID_SECRETKEY, "ceshi推送", "拍的照片很好啊");
//		XingeApp.pushAllIos(LaipaiConstants.IOS_ACCESSID, LaipaiConstants.IOS_SECRETKEY, "来拍测试,拍的照片很好hhhhhhhhhh", XingeApp.IOSENV_DEV);
//		XingeApp.pushTokenIos(LaipaiConstants.IOS_ACCESSID, LaipaiConstants.IOS_SECRETKEY, "来拍测试,拍的照片很好zzzzzzzzzzzzz", "79cb860273d781f8bc4e4a0b556a4db1d076b4d4becafc8b5a27692046229e3c", XingeApp.IOSENV_DEV);
		return returnJson;
	}
	
	/**
	 * 推送评论通知
	 * 摄影师自己给自己评论，不用推送通知
	 * */
	private void addPushCommentAlert(LpGalary gallery,LpUser user,BaseRequestParameters parameters,String commentId){
		try {
			//评论的用户
			String userId = user.getUserId();
			//摄影师
			String cameraId = gallery.getLpUser().getUserId();
			
			LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_comment");
			if(m!=null && m.getNoticeAlert()==0 && !userId.equals(cameraId)){
				logger.info("prepare to push message for comment!");
				LpUser cameraUser = (LpUser) baseDao.getObjectById(LpUser.class, gallery.getLpUser().getUserId());
				String title="评论";
				String content = "您好,用户"+user.getNickName()+"评论了您的作品集《"+gallery.getSubjectName()+"》";
				new PushNoticeMessage().pushOrderAlert(cameraUser, title, content,"","2");
				addMessageLog(cameraUser.getUserId(), commentId, 2);
			}else{
				logger.info("no need to push message!m="+m+",noticeAlert="+m.getNoticeAlert()+",userId="+userId+",cameraId="+cameraId);
			}
		} catch (Exception e) {
			logger.error("fail to push message for comment!",e);
		}
	}
	public  void addMessageLog(String userId,String mapingId,int type){
		LpMessageMapping message=new LpMessageMapping();
		message.setCreateTime(new Timestamp(new Date().getTime()));
		switch (type) {
		case 1:
			message.setOrderId(mapingId);
			message.setMessageType(1);
			break;

		case 2:
			message.setCommentId(mapingId);
			message.setMessageType(2);
			break;
		case 3:
			message.setLikeId(mapingId);
			message.setMessageType(3);
			break;
	
	     case 4:
	 	message.setFollowId(mapingId);
	 	message.setMessageType(4);
		break;
         default:
        	 break;
}
		message.setStatus(0);
		message.setReceiveUserId(userId);
		
		baseDao.saveOrUpdate(message);
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
