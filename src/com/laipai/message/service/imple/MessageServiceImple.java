package com.laipai.message.service.imple;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.galaryManInfo.pojo.LpMessage;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.message.dao.MessageDao;
import com.laipai.message.dto.MessageBean;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.message.pojo.LpMessageDetail;
import com.laipai.message.pojo.LpUpdateVersion;
import com.laipai.message.service.IMessageService;
import com.laipai.message.util.PushNoticeMessage;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;


@Repository("messageService")
public class MessageServiceImple extends BaseServiceImpl implements IMessageService{
	@Resource(name="baseDao")
	private IBaseDao baseDao;
	@Resource(name=IUserManInfoDao.IUSERMANINFODAO_NAME)
	private IUserManInfoDao  userManInfoDao;
	@Resource(name="messageDao")
	private MessageDao messageDao;
	private Logger logger=Logger.getLogger(IMessageService.class);
	
	@Override
	public void addMessage(MessageBean messageBean) {
		//通过消息ID得到消息类型
//		LpMessage lpMessage=(LpMessage) baseDao.getObjectById(LpMessage.class, messageBean.getMessageId());
		//消息主表，存入数据
		LpMessage m = new LpMessage();
		m.setCreateTime(new Timestamp(new Date().getTime()));
		if(messageBean.getMessageType()==messageBean.VERSIONTYPE){
			m.setMessageType(5);
		}else{
			m.setMessageType(9);
		}
		m.setOnLine(0);
		m.setMessageStatus(0);
		Serializable messageId = baseDao.saveObjectReturnId(m);
		messageBean.setMessageId(messageId.toString());
		
		//判断消息类型-系统版本升级
		if(messageBean.getMessageType()==messageBean.VERSIONTYPE){
			LpUpdateVersion version=new LpUpdateVersion();
			version.setVersionNum(messageBean.getVersionNum());
			version.setVersionCreateTime(new Timestamp(new Date().getTime()));
			baseDao.save(version);
		}
		
		//消息详情表，存入数据
		//消息接收人——全体摄影师和用户
		int sendPerson=0;
		List<LpUser> sendUsers=new ArrayList<LpUser>();
		if("all".equals(messageBean.getPerson())){
			//得到所有摄影师和普通用户
			sendUsers=baseDao.queryListObjectAll("from LpUser");
			sendPerson=0;
		}else if("all_userman".equals(messageBean.getPerson())){
			//得到所有普通用户
			sendUsers=baseDao.queryListObjectAll("from LpUser where userType=0");
			sendPerson=1;
		}else if("all_cameraman".equals(messageBean.getPerson())){
			//得到所有摄影师
			sendUsers=baseDao.queryListObjectAll("from LpUser where userType=1");
			sendPerson=2;
		}
		if(sendUsers.size()>0){
			try {
				for(LpUser user:sendUsers){
					LpMessageDetail message=new LpMessageDetail();
					message.setCreateTime(new Timestamp(new Date().getTime()));
					message.setMessageContent(messageBean.getContent());
					//消息状态（未读）
					message.setMessageStatus(0);
					message.setRecieveUserId(user.getUserId());
					message.setMessageTitle(messageBean.getTitle());
					message.setMessageSendPerson(sendPerson);
					//关联消息主表id
					message.setMessageId(messageBean.getMessageId());
					baseDao.save(message);
					//推送系统消息通知
					addPushCommentAlert(user);
				}
			} catch (Exception e) {
				logger.error("发送系统消息失败");
				e.printStackTrace();
			}
		}else{
			try {
				//单个用户
				LpUser user=new LpUser();
				if("one_userman".equals(messageBean.getPerson())){
					//单个普通用户
					user=userManInfoDao.queryUserByName(messageBean.getUserman());
					sendPerson=3;
				}else if("one_cameraman".equals(messageBean.getPerson())){
					//单个摄影师
					user=userManInfoDao.queryUserByName(messageBean.getCameraman());
					sendPerson=4;
				}
				LpMessageDetail message=new LpMessageDetail();
				message.setCreateTime(new Timestamp(new Date().getTime()));
				message.setMessageContent(messageBean.getContent());
				//关联消息主表id
				message.setMesdetailId(messageBean.getMessageId());
				//消息状态（未读）
				message.setMessageStatus(0);
				message.setRecieveUserId(user.getUserId());
				message.setMessageTitle(messageBean.getTitle());
				message.setMessageSendPerson(sendPerson);
				baseDao.save(message);
				
				//推送系统消息通知
				addPushCommentAlert(user);
			} catch (Exception e) {
				logger.error("发送系统消息失败");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 推送系统消息通知
	 * @param user 
	 * */
	private void addPushCommentAlert(LpUser user){
		
		LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_systemMsg");
		if(m!=null && m.getNoticeAlert()==0){
			String title="系统消息";
			String content = "您好，您收到一条新的来拍系统通知";
			new PushNoticeMessage().pushOrderAlert(user, title, content,"","5");
		}
	}

	public LpMessageCode queryHql(String messageCodeId) {
		return messageDao.queryHql(messageCodeId);
	}

	@Override
	public void updateMessageDetail(int messageType, int onLine) {
		String sql="update lp_message set on_line="+onLine+" where message_type="+messageType;
		try {
			baseDao.executeSql(sql);
		} catch (Exception e) {
			logger.error("上线下线失败");
			e.printStackTrace();
		}
	}

	@Override
	public boolean openNotice(int messageType, int messageNotice) {
		String sql="update lp_message set message_notice="+messageNotice+" where message_type="+messageType;
		try {
			baseDao.executeSql(sql);
			return true;
		} catch (Exception e) {
			logger.error("通知开启-关闭失败");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean openPush(int messageType, int messagePush) {
		String sql="update lp_message set message_push="+messagePush+" where message_type="+messageType;
		try {
			baseDao.executeSql(sql);
			return true;
		} catch (Exception e) {
			logger.error("红泡开启-通知失败");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public LpClub queryClubByComment(String newsId) {
		try {
			LpClub club=(LpClub)baseDao.getObjectById(LpClub.class, newsId);
			return club;
		} catch (Exception e) {
			logger.error("消息管理-根据评论ID查看来拍社失败");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public LpComment queryCommentById(String replyToId) {
		try {
			LpComment comment=(LpComment)baseDao.getObjectById(LpComment.class, replyToId);
			return comment;
		} catch (Exception e) {
			logger.error("消息管理-查询被回复的帖子失败");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List queryAllData(String hql,HttpServletRequest request) {
		try {
			List list=querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("消息管理-执行'"+hql+"'失败");
		}
		return null;
	}
}
