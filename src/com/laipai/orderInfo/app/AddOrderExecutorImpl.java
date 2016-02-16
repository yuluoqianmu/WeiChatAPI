package com.laipai.orderInfo.app;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.pojo.MobileDevice;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.SaveMessage;
import com.laipai.base.util.SendMessageTool;
import com.laipai.base.util.ShortMessageUtil;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.message.pojo.LpMessageMapping;
import com.laipai.message.util.PushNoticeMessage;
import com.laipai.orderInfo.dto.OrderBean;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.orderInfo.service.IOrderService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.tencent.xinge.XingeApp;

@NotLogin
@Service("AddOrderExecutorImpl")
public class AddOrderExecutorImpl implements RequestExecutor {
	
	@Autowired
	private IOrderService orderService;
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService manInfoService;
	@Autowired
	private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		System.out.println("AddOrderExectorImp");
		try {
			JSONObject json=parameters.getJson();
			//作品集ID
			String galaryId=json.getString("galaryId");
			//用户ID(登陆用户)
			String userId=json.getString("userId");
			
			//验证该用户是否预约过该作品
			
/*			List<LpOrder> listorder=baseDao.queryListObjectAll("from LpOrder where lpUser.userId='"+userId+"' and lpGalary.galaryId='"+galaryId+"'");
			
			if(listorder!=null&&listorder.size()>0){				
				return JSONTools.newAPIResult(1, "您已经预约过改作品，请不要重复预约");
			}*/			
			LpUser user=manInfoService.queryUserById(userId);
			//留言
			String comment=json.getString("comment");
			//预留电话
 			String orderMobile=json.getString("orderMobile");
 			if(StringUtils.isEmpty(orderMobile)){
 				//用户注册电话
 				if(StringUtils.isNotEmpty(user.getMobile())){
 					orderMobile=user.getMobile();
 				}else{
 					orderMobile=user.getUserName();
 				}
 			}
 			//参数不能为空
 			if(StringUtils.isEmpty(galaryId)||
 			   StringUtils.isEmpty(userId)){
 				return JSONTools.newAPIResult(1, "参数不能为空");
 			}
 			//添加订单
			OrderBean bean=new OrderBean();
			bean.setGalaryId(galaryId);
			bean.setUserName(user.getUserName());
			bean.setComment(comment);
			bean.setOrderMobile(orderMobile);
			try {
				String orderId= orderService.addOrder(bean);
				//推送预约通知
				addPushOrderAlert(galaryId, user, parameters,orderId);
				//发送短信通知
				LpGalary gallary= (LpGalary) baseDao.getObjectById(LpGalary.class, galaryId);
				LpUser cameraman= gallary.getLpUser();
				 if(cameraman!=null){
				sendMessageAlert(user,cameraman.getMobile());
				 }
				
				return JSONTools.newAPIResult(0, "预约成功");
			} catch (Exception e) {
				e.printStackTrace();
				return JSONTools.newAPIResult(1, "预约失败,程序异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "预约失败,程序异常");
		}
		
	}
	
	/**
	 * 发送短信通知
	 * orderMobile : 预约订单时，预留电话
	 * */
	private void sendMessageAlert(LpUser user,String orderMobile){
		LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_order");
		if(m!=null && m.getMessageAlert()==0 && StringUtils.isNotEmpty(orderMobile)){
			String content = "您好，用户"+user.getNickName()+"预约了您的服务。电话："+orderMobile+"。请前往来拍查看详情。";
			ShortMessageUtil.sendCommonMessage(orderMobile, content,"");
		}
	}
	
	//推送预约通知
	private void addPushOrderAlert(String galaryId,LpUser user,BaseRequestParameters parameters,String orderId){
		
		LpMessageCode m = (LpMessageCode) baseDao.getObjectById(LpMessageCode.class, "messageType_order");
		if(m!=null && m.getNoticeAlert()==0){
			LpGalary lpGalary = (LpGalary) baseDao.getObjectById(LpGalary.class, galaryId);
			LpUser cameraUser = (LpUser) baseDao.getObjectById(LpUser.class, lpGalary.getLpUser().getUserId());
			String title="预约";
			String content = "您好,用户"+user.getNickName()+"预约了您的服务，请查看。";
			new PushNoticeMessage().pushOrderAlert(cameraUser, title, content,"","1");
			addMessageLog(cameraUser.getUserId(), orderId, 1);
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
