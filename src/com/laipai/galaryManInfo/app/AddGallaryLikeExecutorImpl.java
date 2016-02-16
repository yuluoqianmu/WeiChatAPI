package com.laipai.galaryManInfo.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.Global;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.TokenUtil;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.pojo.MobileDevice;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.GallerySort;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.SaveMessage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.dto.LpClubShowBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.pojo.LpClubExtend;
import com.laipai.laiPaiClubInfo.service.ILaiPaiClubService;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.message.pojo.LpMessageMapping;
import com.laipai.message.util.PushNoticeMessage;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * 增加来拍社文章点赞
 * 
 * @author zhh
 */
@NotLogin
@Service("AddGallaryLikeExecutorImpl")
public class AddGallaryLikeExecutorImpl implements RequestExecutor {

	private static final Logger logger = Logger.getLogger(AddGallaryLikeExecutorImpl.class);

	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		JSONObject baseJson = new JSONObject();
		//获取服务器域名
		String serverName = json.getString("serverName");
		String gallaryId = json.getString("galaryId");
		String userId = json.getString("userId");
		String hql="from LpLike L where L.lpUser.userId='"+userId+"' and L.lpGalary.galaryId='"+gallaryId+"'";
		List<LpLike> list=null;
		LpGalary gallary =null;
		try {
			list=baseDao.queryListObjectAll(hql);
			gallary= (LpGalary) baseDao.getObjectById(LpGalary.class, gallaryId);
			if(gallary==null){
				return JSONTools.newAPIResult(1, "该作品集不存在");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//如果该用户对作品之前点过赞
		if(list!=null&&list.size()>0){				
			LpLike like=list.get(0);
			//如果该条赞的状态为0（再点击的话把状态置为1）
				//like.setLikeStatus(1);
				baseDao.delete(like);
				//作品集赞数减1
				gallary.setLikeNumber(gallary.getLikeNumber()-1);
				baseDao.updateObject(gallary);
				//修改假数据
				if(gallary.getControlSource() ==1){
					List<LpGalaryExtend> listExe = baseDao.queryListObjectAll("from LpGalaryExtend where lpGalary.galaryId='"+gallaryId+"'");
					if(listExe!=null && listExe.size()>0){
						LpGalaryExtend extend = listExe.get(0);
						/*防止出现空指针异常*/
						if(extend.getLikeNumber() == null || extend.getLikeNumber()<=0){
							extend.setLikeNumber(0);
						}else{
							extend.setLikeNumber(extend.getLikeNumber()-1);
						}
						
						baseDao.updateObject(extend);
					}
				}
				return JSONTools.newAPIResult(0, "已取消对该作品的赞");
			
			
		}
		//如果该用户之前没有点过赞（则创建一条赞）
		else{
		LpUser user = (LpUser) baseDao.getObjectById(LpUser.class, userId);	
		if(user==null){
			return JSONTools.newAPIResult(1, "该用户不存在");
			
		}
		LpLike like = new LpLike();
		like.setLikeStatus(0);
		like.setLikeTime(DateUtils.dateToTimestamp(new Date()));
		like.setLikeType(0);

		like.setLpUser(user);
		like.setLpGalary(gallary);
 		//添加赞详情
		Serializable likeId=  baseDao.saveObjectReturnId(like);
		if(gallary!=null){
		int likenumber= gallary.getLikeNumber();
		//作品集赞数加1
				gallary.setLikeNumber(likenumber+1);
				Double galleryScores= GallerySort.getHotsource(gallary.getCommentNumber(), gallary.getViewNumber(), likenumber+1, gallary.getCreatTime());
				gallary.setGalaryScores(galleryScores);
				baseDao.updateObject(gallary);
				//修改假数据
				if(gallary.getControlSource() ==1){
					List<LpGalaryExtend> listExe = baseDao.queryListObjectAll("from LpGalaryExtend where lpGalary.galaryId='"+gallaryId+"'");
					if(listExe!=null && listExe.size()>0){
						LpGalaryExtend extend = listExe.get(0);
						if(extend.getLikeNumber() == null){
							extend.setLikeNumber(1);
						}else{
							extend.setLikeNumber(extend.getLikeNumber()+1);
						}
						
						baseDao.updateObject(extend);
					}
				}
		}
		//推送赞通知
		addPushCommentAlert(gallary, user, parameters,likeId.toString());
		return JSONTools.newAPIResult(0, "点赞成功");
	}
	
}
	
	/**
	 * 推送赞通知
	 * 摄影师自己给自己赞，不用推送通知
	 * */
		private void addPushCommentAlert(LpGalary gallary,LpUser user,BaseRequestParameters parameters,String likeId){
			try {
				//赞的用户
				String userId = user.getUserId();
				//摄影师
				String cameraId = gallary.getLpUser().getUserId();
				
				LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_like");
				if(m!=null && m.getNoticeAlert()==0 && !userId.equals(cameraId)){
					LpUser cameraUser = (LpUser) baseDao.getObjectById(LpUser.class, gallary.getLpUser().getUserId());
					String title="点赞";
					String content = "您好,用户"+user.getNickName()+"赞了您的作品集《"+gallary.getSubjectName()+"》";
					new PushNoticeMessage().pushOrderAlert(cameraUser, title, content,"", "3");
					addMessageLog(cameraUser.getUserId(), likeId, 3);
				}
			} catch (Exception e) {
				logger.error("fail to push like message!",e);
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
