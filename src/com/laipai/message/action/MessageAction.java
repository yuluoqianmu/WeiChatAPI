package com.laipai.message.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.galaryManInfo.pojo.LpMessage;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.message.dto.MessageBean;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.message.pojo.LpMessageDetail;
import com.laipai.message.pojo.LpUpdateVersion;
import com.laipai.message.service.IMessageService;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.opensymphony.xwork2.ModelDriven;

@Controller("messageAction")
public class MessageAction extends BaseAction implements ModelDriven<MessageBean>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private IMessageService messageService;
	@Resource(name="baseDao")
	private IBaseDao baseDao;
	
	public IMessageService getMessageService() {
		return messageService;
	}
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	public MessageBean getMessageBean() {
		return messageBean;
	}
	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private MessageBean messageBean=new MessageBean();
	@Override
	public MessageBean getModel() {
		return messageBean;
	}
	/**
	 * 
	
	 * @Description:消息列表
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-21 上午10:22:05
	 */
	public String queryMessageCount(){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			//预约
			int orderCount=baseDao.getCount("from VLpOrder");
			request.setAttribute("orderCount", orderCount);
			request.setAttribute("orderMessage",returnMessage("messageType_order"));
			//评论
			int commentCount=baseDao.getCount("from LpComment where commentType in (0,1)");
			request.setAttribute("commentCount", commentCount);
			request.setAttribute("commentMessage", returnMessage("messageType_comment"));
			//回复
			int replyCount=baseDao.getCount("from VLpComment where commentType=3");
			request.setAttribute("replyCount", replyCount);
			request.setAttribute("replyMessage", returnMessage("messageType_reply"));
			//关注
			int followCount=baseDao.getCount("from LpFollow");
			request.setAttribute("followCount", followCount);
			request.setAttribute("followMessage", returnMessage("messageType_follow"));
			//系统消息
			int laipaiCount=baseDao.getCount("from VLpMessageDetail");
			request.setAttribute("laipaiCount", laipaiCount);
			request.setAttribute("messageMessage", returnMessage("messageType_systemMsg"));
			//审核通过
			int auditPassCount=baseDao.getCount("from LpCameramanAppform where checkStatus=1");
			request.setAttribute("auditPassCount", auditPassCount);
			request.setAttribute("auditMessage", returnMessage("messageType_check_success"));
			//审核未通过
			int auditNotPassCount=baseDao.getCount("from LpCameramanAppform where checkStatus=-1");
			request.setAttribute("auditNotPassCount", auditNotPassCount);
			request.setAttribute("auditNotMessage", returnMessage("messageType_check_failure"));
			//系统版本push
			int versionCount=baseDao.getCount("from LpUpdateVersion");
			request.setAttribute("versionCount", versionCount);
			request.setAttribute("versionMessage", returnMessage("messageType_system_push"));
			//赞
			int likeCount=baseDao.getCount("from LpLike where likeType=0");
			request.setAttribute("likeCount", likeCount);
			request.setAttribute("likeMessage", returnMessage("messageType_like"));
			//通知或红泡
			List<Integer> list=new ArrayList<Integer>();
			list.add(0);//显示
			list.add(1);//不显示
			request.setAttribute("noticeOrPush", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "queryMessageCount";
	}
	private LpMessageCode returnMessage(String messageCodeId) {
		LpMessageCode message=messageService.queryHql(messageCodeId);
		return message;
	}
	/**
	 * 
	
	 * @Description:消息管理——发送消息
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-21 上午11:59:33
	 */
	public String addMessage(){
		String content = request.getParameter("content");
//		content= content.replace(LaipaiConstants.UPLOAD_VIRTUAL_IMG, LaipaiConstants.UPLOAD_ABSOLUTE_IMG);
		messageBean.setContent(content);
		messageService.addMessage(messageBean);
 		return "toQueryMessageCount";
	}
	
	/**
	 * 
	
	 * @Description:消息管理——上线下线
	  
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 上午10:11:07
	 */
	public String onOffLine(){
		//消息类型
		int messageType=Integer.parseInt(request.getParameter("messageType"));
		//消息状态
		int onLine=Integer.parseInt(request.getParameter("messageStatus"));
		//更新消息
		messageService.updateMessageDetail(messageType,onLine);
		return this.queryMessageCount();
	}
	
	/**
	 * 
	
	 * @Description:通知功能开启-关闭
	
	
	 * void
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 下午1:24:44
	 */
	public void openNotice(){
		//消息类型
		int messageType=Integer.parseInt(request.getParameter("messageType"));
		//通知状态(0:通知 1：不通知)
		int messageNotice=Integer.parseInt(request.getParameter("messageNotice"));
		boolean success=messageService.openNotice(messageType,messageNotice);
		if(success){
			getPrintWriter().print("success");
		}
	}
	
	/**
	 * 
	
	 * @Description:红泡功能开启-关闭
	
	
	 * void
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 下午1:41:37
	 */
	public void openPush(){
		//消息类型
		int messageType=Integer.parseInt(request.getParameter("messageType"));
		//红泡状态（0：开启 1：关闭）
		int messagePush=Integer.parseInt(request.getParameter("messagePush"));
		boolean success=messageService.openPush(messageType,messagePush);
		if(success){
			getPrintWriter().print("success");
		}
	}
	
	/**
	 * 
	
	 * @Description:消息管理-查询评论
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 下午1:56:32
	 */
	public String queryAllComments(){
		List<Map> listComment=new ArrayList<Map>();
		String hql="from LpComment where commentType in(0,1)";
		List<LpComment> list=messageService.queryAllData(hql, request);
		for(LpComment comment:list){
			Map<String,Object> map=new HashMap<String,Object>();
			//评论ID
			map.put("commentId", comment.getCommentId());
			//评论时间
			DateFormat sdf=new SimpleDateFormat("yyyy/MM/DD HH:mm:ss");
			String createTime=sdf.format(comment.getCreateTime());
			map.put("commentTime", createTime);
			//评论类型
			map.put("commentType", comment.getCommentType());
			if(comment.getCommentType()==0){
				//所属作品集
				if(comment.getLpGalary()!=null){
					map.put("galary", comment.getLpGalary().getSubjectName());
				}else{
					map.put("galary", "抱歉，该作品集已被删除");
				}
				//来拍社设成空
				map.put("club", "");
			}else{
				//来拍社
				String newsId=comment.getNewsId();
				if(StringUtils.isNotBlank(newsId)){
					LpClub club=messageService.queryClubByComment(newsId);
					map.put("club", club.getTile());
				}else{
					map.put("club", "该文章已被删除");
				}
				//作品集设成空
				map.put("galary", "");
			}
			//评论内容
			map.put("commentDetail", comment.getCommentDetail());
			listComment.add(map);
		}
		request.setAttribute("commentInfo", listComment);
		return "commentsList";
	}	
	
	/**
	 * 
	
	 * @Description:消息管理-回复查询
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 下午3:22:36
	 */
	public String queryReply(){
		List replyList=baseDao.queryListObjectAll("from VLpComment where commentType=3");
//		List<Map> replyList=new ArrayList<Map>();
//		String hql="from LpComment where commentType=3";
//		List<LpComment> list=messageService.queryAllData(hql, request);
//		for(LpComment comment:list){
//			Map<String,Object> map=new HashMap<String,Object>();
//			//回复时间
//			map.put("replyTime", comment.getModifyTime());
//			//回复人
//			map.put("user", comment.getLpUser().getNickName());
//			//回复内容
//			map.put("commentDetail", comment.getCommentDetail());
//			//被回复的帖子
//			String replyToId=comment.getReplayToId();
//			LpComment replyComment=messageService.queryCommentById(replyToId);
//			map.put("replyComment", replyComment.getCommentDetail());
//			//发帖人
//			map.put("replyUser", replyComment.getLpUser().getNickName());
//			replyList.add(map);
//		}
 		request.setAttribute("replyInfo", replyList);
		return "replyList";
	}
	
	/**
	 * 
	
	 * @Description:消息管理-查询所有关注
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 下午3:57:45
	 */
	public String queryAllFollows(){
		String hql="from LpFollow";
		List<LpFollow> list=messageService.queryAllData(hql, request);
		request.setAttribute("followInfo", list);
		return "followsList";
	}
	
	/**
	 * 
	
	 * @Description:消息管理——查询所有系统消息
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 下午4:32:54
	 */
	public String queryAllSysMessage(){
		String hql="from VLpMessageDetail order by createTime desc";
		List message=messageService.queryAllData(hql,request);
		request.setAttribute("messageInfo", message);
		return "messagesList";
	}
	
	/**
	 * 
	
	 * @Description:消息管理—审核通过查询
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 下午5:16:11
	 */
	public String queryAllAudits(){
		int checkStatus=Integer.parseInt(request.getParameter("checkStatus"));
		List<LpCameramanAppform> list=null;
		try {
			String hql="from LpCameramanAppform where checkStatus="+checkStatus;
		    list=messageService.queryAllData(hql, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("appformList", list);
		return "auditsList";
	}
	
	/**
	 * 
	
	 * @Description:消息管理——版本查询
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-22 下午8:16:14
	 */
	public String queryAllVersions(){
		List<LpUpdateVersion> list=null;
		try {
			String hql="from LpUpdateVersion";
			list=messageService.queryAllData(hql, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("versionInfo", list);
		return "versionList";
	}
	
	/**
	 * 
	
	 * @Description:消息管理-发送消息前的准备数据
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-28 上午10:09:01
	 */
	public String getMessageId(){
		//得到来拍社消息的消息ID
//		LpMessage lpclub=messageService.queryMessageByType(messageBean.LPCLUB_TYPE);
//		request.setAttribute("lpClub_messageId", lpclub.getMessageId());
//		//得到版本升级的消息ID
//		LpMessage version=messageService.queryMessageByType(messageBean.VERSIONTYPE);
//		if(version!=null){
//			request.setAttribute("version_messageId", version.getMessageId());
//		}
		//得到用户列表
		List userList=baseDao.queryListObjectAll("from LpUser where userType=0");
		request.setAttribute("userList", userList);
		//得到摄影师列表
		List cameraList=baseDao.queryListObjectAll("from LpUser where userType=1");
		request.setAttribute("cameraList", cameraList);
		return "sendMessage";
	}
	
	//修改消息功能的状态
	public String updateMessageCode(){
		String messageCodeId = request.getParameter("messageCodeId");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		String sql = "update lp_message_code set "+type+"="+status+" where message_code_id='"+messageCodeId+"'";
		baseDao.executeSql(sql);
		return "updateMessageCode";
	}
	
	/**
	 * 跳转到修改短信模板页面
	 * */
	public String editMsgTemplate(){
		String messageCodeId = request.getParameter("messageCodeId");
		LpMessageCode messageCode = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, messageCodeId);
		request.setAttribute("messageCode", messageCode);
		return "editMsgTemplate";
	}
	
	/**
	 * 保存短信模板
	 * */
	public String saveMsgTemplate(){
		String messageCodeId = request.getParameter("messageCodeId");
		String messageTemplate = request.getParameter("messageTemplate");
		
		LpMessageCode messageCode = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, messageCodeId);
		messageCode.setMessageTemplate(messageTemplate);
		baseDao.updateObject(messageCode);
		return this.queryMessageCount();
	}

	public String queryAllLikeGallary(){
		List<LpLike> list=null;
		try {
			String hql="from LpLike where likeType=0";
			list=messageService.queryAllData(hql, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("likeList", list);
		return "likeList";
	}
	
}
