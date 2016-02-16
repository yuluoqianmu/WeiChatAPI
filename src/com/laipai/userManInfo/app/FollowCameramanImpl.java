package com.laipai.userManInfo.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.SaveMessage;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.message.pojo.LpMessageMapping;
import com.laipai.message.util.PushNoticeMessage;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserFollowService;
import com.laipai.userManInfo.service.IUserManInfoService;
/**
 * 

 * @Description:关注摄影师

 * @author:lxd

 * @time:2015-1-9 下午5:54:25
 */
@NotLogin
@Service("FollowCameramanImpl")
public class FollowCameramanImpl implements RequestExecutor {
    
    @Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	
    @Autowired
    private IBaseDao baseDao; 
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		JSONObject resulejson = new JSONObject();
		String userId=JSONTools.getString(json,"userId");
		String cameramanId=JSONTools.getString(json,"cameramanId");
		if(userId==null||cameramanId==null){			
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		List<LpFollow> list= baseDao.queryListObjectAll("from LpFollow L where L.lpUserByUserId.userId='"+userId+"' and lpUserByCameraId.userId='"+cameramanId+"'");
		if(list!=null&&list.size()>0){
			LpFollow follow =list.get(0);
			baseDao.delete(follow);
			resulejson.put("resuleMessae", "已取消关注");
			resulejson.put("followId", "");
		}else{
		LpUser user= userManService.queryUserById(userId);
		if(user==null){
			return JSONTools.newAPIResult(1, "该用户不存在");
			
		}
		LpUser camera= userManService.queryUserById(cameramanId);
		if(camera==null){
			
			return JSONTools.newAPIResult(1, "该用户不存在");
		}
		LpFollow follow =new LpFollow();
		follow.setLpUserByUserId(user);
		follow.setLpUserByCameraId(camera);
		follow.setFollowTime(new Timestamp(new java.util.Date().getTime()));
		Serializable followId= baseDao.saveObjectReturnId(follow);			
		resulejson.put("resuleMessae", "已关注");
		resulejson.put("followId", followId);
		
		//推送关注通知
		addPushCommentAlert(camera, user, parameters,followId.toString());
		}
		return resulejson;
	}

	/**
	 * 推送关注通知
	 * @param parameters 
	 * @param user 
	 * @param camera 
	 * */
	private void addPushCommentAlert(LpUser camera, LpUser user, BaseRequestParameters parameters,String followId){
		
		LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_follow");
		if(m!=null && m.getNoticeAlert()==0){
			String title="关注";
			String content = "您好,用户"+user.getNickName()+"关注了你";
			new PushNoticeMessage().pushOrderAlert(camera, title, content,"","4");
			addMessageLog(camera.getUserId(), followId, 4);
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
