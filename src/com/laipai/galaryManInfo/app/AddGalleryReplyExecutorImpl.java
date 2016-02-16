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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
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

/**
 * 作品集详情--添加回复
 * 
 * @author zhh
 */
@NotLogin
@Service("AddGalleryReplyExecutorImpl")
public class AddGalleryReplyExecutorImpl implements RequestExecutor {

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
		//摄影师的id(回复人userId)
		String userId = json.getString("userId");
		LpUser user= null;
		//作品集id
		String galaryId = json.getString("galaryId");
		//评论内容
		String replyContext=json.getString("replyContext");
		/*//原评论人(接收回复的人userId)
		String replayToId=json.getString("replayToId");*/
		String oldCommentId=json.getString("commentId");
		
		LpGalary gallery=galleryService.getgalleryByID(galaryId);
		LpComment comment=new LpComment();
		comment.setCommentDetail(replyContext);
		Timestamp creatTime =new Timestamp(new java.util.Date().getTime());
		comment.setCreateTime(creatTime);
		//每次评论和回复之后要计作品的分数（排序用）
		Double galleryScores= GallerySort.getHotsource(gallery.getCommentNumber(), gallery.getViewNumber(), gallery.getLikeNumber(), gallery.getCreatTime());
		gallery.setGalaryScores(galleryScores);
		comment.setReplayToId(oldCommentId);
		comment.setLpGalary(gallery);
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
		
		
		//评论类型为4（作品集的回复）
		comment.setCommentType(4);
	    if(StringUtils.isNotBlank(userId)){
	    	user= (LpUser) baseDao.getObjectById(LpUser.class, userId);
			if(user == null){
				return JSONTools.newAPIResult(1, "无效的用户(the userId is not validate)");
			}
			if(user.getHeadImage() == null){
				return JSONTools.newAPIResult(1, "该用户未上传头像");
			}
			comment.setLpUser(user);
			returnJson.put("userId", user.getUserId());
			returnJson.put("nickName", user.getNickName());
//			if(user.getAccountSource()==0){
//				returnJson.put("headImage", serverName + user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//			}else{
//				
//				returnJson.put("headImage", user.getHeadImage());
//			}
			returnJson.put("headImage", ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
		}
	    Serializable commentId= baseDao.saveObjectReturnId(comment);		
	   
		returnJson.put("commentId", commentId);
		returnJson.put("resultMessage", "回复成功");
		
		//推送回复通知
		addPushCommentAlert(comment,oldCommentId, user, parameters,commentId.toString());
		return returnJson;
	}
	
	/**
	 * 推送回复通知
	 * 摄影师自己给自己回复，不用推送通知
	 * */
	private void addPushCommentAlert(LpComment comment, String oldCommentId,LpUser user,BaseRequestParameters parameters,String commentId){
		try {
			LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_reply");
			LpComment oldComment = (LpComment) baseDao.getObjectById(LpComment.class, oldCommentId);
			
			//回复的摄影师
			String userId = user.getUserId();
			//摄影师
			String cameraId = oldComment.getLpUser().getUserId();
			
			if(m!=null && m.getNoticeAlert()==0 && !userId.equals(cameraId)){
				LpUser cameraUser= oldComment.getLpUser();
				String title="回复";
				String content = "您好,用户"+user.getNickName()+"回复了你的评论:"+comment.getCommentDetail();
				new PushNoticeMessage().pushOrderAlert(cameraUser, title, content,"","2");
				addMessageLog(cameraUser.getUserId(), commentId, 2);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
