package com.laipai.cameraCheck.service.imple;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.base.util.SendMessageTool;
import com.laipai.base.util.ShortMessageUtil;
import com.laipai.cameraCheck.dao.ICameraCheckDao;
import com.laipai.cameraCheck.dto.AppformData;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.cameraCheck.service.ICameraCheckservice;
import com.laipai.galaryManInfo.pojo.LpMessage;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.message.pojo.LpMessageDetail;
import com.laipai.message.util.PushNoticeMessage;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.pojo.LpUser;
@Transactional
@Service(ICameraCheckservice.ICAMERACHECKSERVICE_NAME)
public class CameraCheckService extends BaseServiceImpl implements ICameraCheckservice {
    @Resource(name=ICameraCheckDao.ICAMERAMANDAO_NAME)
	private ICameraCheckDao cameraCheckDao;
	@Resource(name=IUserManInfoDao.IUSERMANINFODAO_NAME)
    private IUserManInfoDao userManInfoDao;
	@Autowired
    private IBaseDao baseDao;
	
	@Override
	public List<LpCameramanAppform> queyallBypage(HttpServletRequest request) {
		String status= request.getParameter("status");
		 String hql="from LpCameramanAppform order by applyTime desc";
		if(StringUtils.isNotBlank(status)){
			 hql = "from LpCameramanAppform where checkStatus="+status+" order by applyTime desc";	
			
		}else{
			
			hql = "from LpCameramanAppform order by applyTime desc";
		}
		try {
			List list = querylistForPage(request, hql, 20);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
     /**
      * 查询申请的统计数据
      */
	@Override
	public AppformData getAppfromDate() {
		String sql="SELECT "+ 
            "SUM(CASE check_status WHEN 0 THEN 1 ELSE 0 END) await, "+ 
            "SUM(CASE check_status WHEN 1 THEN 1 ELSE 0 END) pass, "+ 
            "SUM(CASE check_status WHEN -1 THEN 1 ELSE 0 END)  fail "+
             "FROM lp_cameraman_appform";
		@SuppressWarnings("rawtypes")
		List list= cameraCheckDao.querySqldata(sql);
		if(list!=null&&list.size()>0){
			 AppformData appformdata= (AppformData) list.get(0);	
			  return appformdata;
			
		}
		
		return null;
	}
	@Override
	public LpCameramanAppform getAppformById(String appformId) {
		LpCameramanAppform appform= (LpCameramanAppform) cameraCheckDao.getObjectById(LpCameramanAppform.class, appformId);
		return appform;
	}
	/**
	 * 通过申请
	 */
	@Override
	public void saveAppformLog(LpCameramanAppform appform,LpUser user) {
		userManInfoDao.updateObject(user);
		cameraCheckDao.updateObject(appform);
		
		//记录日志
		addMessageLog(appform,user);
		//推送系统消息通知
		addPushCommentAlert(appform,user);
		//发送短信通知
		sendMessageAlert(appform,user);
	}
	
	/**
	 * 发送短信通知
	 * */
	private void sendMessageAlert(LpCameramanAppform appform,LpUser user){
		String mobile = user.getMobile();
		if(StringUtils.isEmpty(mobile)){
			mobile = user.getUserName();
		}
		
		if(appform.getCheckStatus()==-1){
			LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_check_failure");
			if(m!=null && m.getMessageAlert()==0 && StringUtils.isNotEmpty(mobile)){
				String content = "您的申请由于"+appform.getRejectReason()+"被驳回，未能成为来拍认证摄影师，请前往来拍修改信息吧。";
				ShortMessageUtil.sendCommonMessage(mobile, content,"");
			}
		}else if(appform.getCheckStatus() ==1){
			LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_check_success");
			if(m!=null && m.getMessageAlert()==0 && StringUtils.isNotEmpty(mobile)){
				String content = "恭喜您，通过了来拍的摄影师认证，请前往来拍发布您的作品吧！";
				ShortMessageUtil.sendCommonMessage(mobile, content,"");
			}
		}
	}
	
	/**
	 * 推送系统消息通知
	 * */
	private void addPushCommentAlert(LpCameramanAppform appform,LpUser user){
		if(appform.getCheckStatus()==-1){
			LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_check_failure");
			if(m!=null && m.getNoticeAlert()==0){
				String title="系统消息";
				String content = "您的申请由于"+appform.getRejectReason()+"被驳回，未能成为来拍认证摄影师，请前往来拍修改信息吧。";
				new PushNoticeMessage().pushOrderAlert(user, title, content,"","5");
			}
		}else if(appform.getCheckStatus() ==1){
			LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_check_success");
			if(m!=null && m.getNoticeAlert()==0){
				String title="系统消息";
				String content = "恭喜您，通过了来拍的摄影师认证，请前往来拍发布您的作品吧！";
				new PushNoticeMessage().pushOrderAlert(user, title, content,"","5");
			}
		
		}
		
	}
	
	/**
	 * 增加消息提醒
	 * */
	private void addMessageLog(LpCameramanAppform appform,LpUser user){
		//消息主表，存入数据
		LpMessage m = new LpMessage();
		m.setCreateTime(new Timestamp(new Date().getTime()));
		m.setMessageType(5);
		m.setOnLine(0);
		m.setMessageStatus(0);
		Serializable messageId = baseDao.saveObjectReturnId(m);
		//详情表
		LpMessageDetail message=new LpMessageDetail();
		message.setCreateTime(new Timestamp(new Date().getTime()));
		if(appform.getCheckStatus()==-1){
			message.setMessageTitle("您的摄影师认证审核未通过");
			message.setMessageContent("由于"+appform.getRejectReason()+"原因，您的摄影师认证审核未通过，请您前往认证界面修改。");
		}else{
			message.setMessageTitle("恭喜您成为来拍认证摄影师!请查看权限介绍");
			message.setMessageContent("恭喜您成为来拍的认证摄影师，您在来拍平台上将拥有以下权限");
		}
		//消息状态（未读）
		message.setMessageStatus(0);
		message.setRecieveUserId(user.getUserId());
		message.setMessageSendPerson(3);
		//关联消息主表id
		message.setMessageId(messageId.toString());
		baseDao.save(message);
	}
	
	public LpCameramanAppform getappformById(String appformId) {
		
		return (LpCameramanAppform) cameraCheckDao.getObjectById(LpCameramanAppform.class, appformId);
	}
	/**
	 * 根据用户Id查询用户申请的历史记录
	 */
	@Override
	public List<LpCameramanAppform> getappformHisByuserId(String userId) {
		String hql="from LpCameramanAppform l where l.lpUser.userId='"+userId+"' and l.checkStatus=-1";
		List<LpCameramanAppform> list=cameraCheckDao.queryListObjectAll(hql);
		return list;
	}
	/**
	 * 根据Id删除申请
	 */
	@Override
	public void deleteAppformById(String appformId) {
		LpCameramanAppform appform= this.getappformById(appformId);
		if(appform!=null){
			cameraCheckDao.delete(appform);
			
		}
		
	}
	@Override
	public void saveAppform(LpCameramanAppform appform) {
		cameraCheckDao.save(appform);
		
	}

	@Override
	public List<LpCameramanAppform> queyallBypage(HttpServletRequest request,
			String checkStatus) {
		int checkStatu = Integer.parseInt(checkStatus);
		String hql = "from LpCameramanAppform where checkStatus="+checkStatu+" order by applyTime desc";
		try {
			List list = querylistForPage(request, hql, 10);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	@Override
//	public List<LpCameramanAppform> queyBystatus(HttpServletRequest request) {
//		String status= request.getParameter("status");
//		String hql = "from LpCameramanAppform where checkStatus="+status+" order by applyTime desc";	
///*		if(StringUtils.isNotBlank(status)){
//		 	
//			
//		}*/
//		
//		try {
//			List list = querylistForPage(request, hql, 10);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	
   

	
}
