package com.laipai.app.process.app.binder;

import java.util.HashMap;
import java.util.Map;
import com.laipai.orderInfo.app.AddOrderExecutorImpl;
import com.laipai.orderInfo.app.ModifyOrderExecutorImpl;
import com.laipai.orderInfo.app.QueryOrderByIdExecutorImpl;
import com.laipai.orderInfo.app.QueryOrderExecutorImpl;
import com.laipai.orderInfo.app.QueryUserOrderExecutorImpl;
import com.laipai.serviceInfo.app.AddServiceExecutorImpl;
import com.laipai.serviceInfo.app.AddServiceStyleExecutorImpl;
import com.laipai.serviceInfo.app.DeleteServiceExecutorImpl;
import com.laipai.serviceInfo.app.GetStyleExecutorImpl;
import com.laipai.serviceInfo.app.ModifyServiceExecutorImpl;
import com.laipai.serviceInfo.app.QueryServiceDetailExecutorImpl;
import com.laipai.serviceInfo.app.QueryServiceExecutorImpl;

import com.laipai.cameraCheck.app.CameramanAppformAndroidImpl;
import com.laipai.cameraCheck.app.CameramanAppformImpl;
import com.laipai.cameraCheck.app.CameramanAppformPic;
import com.laipai.cameraCheck.app.CameramanAppformPicAndroidImpl;
import com.laipai.cameraCheck.app.CameramanAppformPicOne;
import com.laipai.cameraCheck.app.CheckInviteExecutorImpl;

import com.laipai.cameraCheck.app.QueryAllOnlineCityImpl;

import com.laipai.galaryManInfo.app.AddGallaryForAndroidExecutorImpl;
import com.laipai.galaryManInfo.app.AddGallaryForIOSExecutorImpl;
import com.laipai.galaryManInfo.app.AddGallaryLikeExecutorImpl;
import com.laipai.galaryManInfo.app.AddGallaryPageExecutorImpl;
import com.laipai.galaryManInfo.app.AddGalleryCommetExecutorImpl;
import com.laipai.galaryManInfo.app.AddGalleryReplyExecutorImpl;
import com.laipai.galaryManInfo.app.DeleteCommentExecutorImpl;
import com.laipai.galaryManInfo.app.DeleteGalleryExecutorImpl;
import com.laipai.galaryManInfo.app.EditGallaryExecutorImpl;
import com.laipai.galaryManInfo.app.EditGallaryForIOSExecutorImpl;
import com.laipai.galaryManInfo.app.GalleryShowExecutorImpl;
import com.laipai.galaryManInfo.app.NewCamerManIntoduceExecutorImpl;
import com.laipai.galaryManInfo.app.QueryCommentExexutorImpl;
import com.laipai.galaryManInfo.app.UpdateGalaryCoverExecutorImpl;
import com.laipai.laiPaiClubInfo.app.AddArticleCommentExecutorImpl;
import com.laipai.laiPaiClubInfo.app.AddArticleLikeExecutorImpl;
import com.laipai.laiPaiClubInfo.app.AddArticleReplyExecutorImpl;
import com.laipai.laiPaiClubInfo.app.LaiPaiClubDetailExecutorImpl;
import com.laipai.laiPaiClubInfo.app.LaiPaiClubShowExecutorImpl;
import com.laipai.message.app.AppVersionUpdateExecutorImpl;
import com.laipai.message.app.MessageNoticeExecutorImpl;
import com.laipai.message.app.UpdateMessageStatus;


import com.laipai.subject.app.GalaryExecutorImpl;
import com.laipai.subject.app.SubjectExecutorImpl;
import com.laipai.subject.app.SubjectListExecutorImpl;
import com.laipai.userManInfo.app.AddUserFeedBackImpl;
import com.laipai.userManInfo.app.ChangeHeadImgForAndroid;
import com.laipai.userManInfo.app.ChangeHeadImgImpl;
import com.laipai.userManInfo.app.ChangePasswordImpl;
import com.laipai.userManInfo.app.DeleteFollowOrFansImpl;
import com.laipai.userManInfo.app.FollowCameramanImpl;
import com.laipai.userManInfo.app.QueryCustomerHomePageImpl;
import com.laipai.userManInfo.app.QueryPersonHomePageImpl;
import com.laipai.userManInfo.app.QueryUserFansImpl;
import com.laipai.userManInfo.app.QueryUserFollowImpl;
import com.laipai.userManInfo.app.QueryUserLikeImpl;
import com.laipai.userManInfo.app.QueryuserByNickImpl;
import com.laipai.userManInfo.app.RegisterExecutorImpl;
import com.laipai.userManInfo.app.RetrievePasswordExecutorImpl;
import com.laipai.userManInfo.app.SendVercodeSMSExecutorImpl;
import com.laipai.userManInfo.app.ToEditCameramanImpl;
import com.laipai.userManInfo.app.UpdateCameramanInfoImpl;
import com.laipai.userManInfo.app.UserLoginExecutorImpl;
import com.laipai.userManInfo.app.UserLogoutExecutorImpl;
import com.laipai.userManInfo.app.UserThirdPartyLoginExecutorImpl;
/**
 * 用户请求对像绑定
 * 
 * @author ts
 * @date 2014-11-13
 */
public class UserAppRequestBinderRegister {
	public static final Map<Integer, Class<?>> REQUEST_MESSAGE_TYPE = new HashMap<Integer, Class<?>>();// 请求进来的绑定类型

	static {
		/**
		 * 此处定义各个业务处理类,编号从1001开始,依次往下定义,
		 * 如    base包   ： 1001,1002,1003,1004...
		 * 	   cameraCheck包: 2001,2002,2003
		 * 	   cameraManInfo包：3001,3002
		 *     ....
		 *     galaryManInfo包：4001,4002,4003...
		 *     ...
		 *     userManInfo包：1101,1102,1103....
		 * */
		
		//service包
		REQUEST_MESSAGE_TYPE.put(9001, QueryServiceExecutorImpl.class);//根据摄影师账号查询服务
		REQUEST_MESSAGE_TYPE.put(9002, QueryServiceDetailExecutorImpl.class);//服务详情查询
		REQUEST_MESSAGE_TYPE.put(9003, AddServiceExecutorImpl.class);//添加服务
		REQUEST_MESSAGE_TYPE.put(9004, DeleteServiceExecutorImpl.class);//删除服务
		REQUEST_MESSAGE_TYPE.put(9005, ModifyServiceExecutorImpl.class);//修改服务
		REQUEST_MESSAGE_TYPE.put(9006, GetStyleExecutorImpl.class);//新增服务时，先查询风格
		REQUEST_MESSAGE_TYPE.put(9007, AddServiceStyleExecutorImpl.class);//添加服务风格标签
		//order包
		REQUEST_MESSAGE_TYPE.put(7001, QueryOrderExecutorImpl.class);// 查看摄影师所有订单
		REQUEST_MESSAGE_TYPE.put(7002, AddOrderExecutorImpl.class);//预约作品集（增加订单）
		REQUEST_MESSAGE_TYPE.put(7003, QueryUserOrderExecutorImpl.class);//查看用户的所有预约
		REQUEST_MESSAGE_TYPE.put(7004, QueryOrderByIdExecutorImpl.class);//根据订单ID查看订单详情
		REQUEST_MESSAGE_TYPE.put(7005, ModifyOrderExecutorImpl.class);//更新订单（回电）
		//message包
	    REQUEST_MESSAGE_TYPE.put(8001, MessageNoticeExecutorImpl.class);//消息列表
	    REQUEST_MESSAGE_TYPE.put(8002, AppVersionUpdateExecutorImpl.class);//验证是否有版本更新，并提供url下载地址	
	    
	    REQUEST_MESSAGE_TYPE.put(8003, UpdateMessageStatus.class);//更新消息状态	
	    
				
		//cameraCheck包
		REQUEST_MESSAGE_TYPE.put(2001, CheckInviteExecutorImpl.class);//验证邀请码
		REQUEST_MESSAGE_TYPE.put(2002, QueryAllOnlineCityImpl.class);//获取所有的已经上线的城市
		REQUEST_MESSAGE_TYPE.put(2003, CameramanAppformImpl.class);//申请成为摄影师（信息界面）		
		REQUEST_MESSAGE_TYPE.put(2004, CameramanAppformPic.class);//申请成为摄影师(上传照片界面)		
		REQUEST_MESSAGE_TYPE.put(2005, CameramanAppformPicOne.class);//申请成为摄影师(。。。)

		REQUEST_MESSAGE_TYPE.put(2006, CameramanAppformAndroidImpl.class);//申请成为摄影师(安卓信息界面)
		
		REQUEST_MESSAGE_TYPE.put(2007, CameramanAppformPicAndroidImpl.class);//申请成为摄影师图片上传（Android）
		//galaryManInfo包
		REQUEST_MESSAGE_TYPE.put(4001, GalleryShowExecutorImpl.class);// 作品集展示
		REQUEST_MESSAGE_TYPE.put(4003, AddGallaryForIOSExecutorImpl.class);// ios添加作品集
		REQUEST_MESSAGE_TYPE.put(4004, AddGalleryCommetExecutorImpl.class);// 评论作品集
		REQUEST_MESSAGE_TYPE.put(4005, AddGalleryReplyExecutorImpl.class);// 摄影师回复评论
		REQUEST_MESSAGE_TYPE.put(4006, NewCamerManIntoduceExecutorImpl.class);// 新晋摄影师介绍
		REQUEST_MESSAGE_TYPE.put(4007, UpdateGalaryCoverExecutorImpl.class);// 修改作品集封面
		REQUEST_MESSAGE_TYPE.put(4008, AddGallaryPageExecutorImpl.class);// 打开上传页面
		REQUEST_MESSAGE_TYPE.put(4009, AddGallaryForAndroidExecutorImpl.class);// Android添加作品集
		REQUEST_MESSAGE_TYPE.put(4010, DeleteCommentExecutorImpl.class);// 删除评论
		REQUEST_MESSAGE_TYPE.put(4011, AddGallaryLikeExecutorImpl.class);// 作品集点赞		
		REQUEST_MESSAGE_TYPE.put(4012, DeleteGalleryExecutorImpl.class);// 删除作品集			
		REQUEST_MESSAGE_TYPE.put(4013, EditGallaryForIOSExecutorImpl.class);//编辑作品集（IOS）
		REQUEST_MESSAGE_TYPE.put(4014, QueryCommentExexutorImpl.class);//查询作品集评论
		REQUEST_MESSAGE_TYPE.put(4015, EditGallaryExecutorImpl.class);//编辑作品集（安卓）
		//laiPaiClubInfo包
		REQUEST_MESSAGE_TYPE.put(5001, LaiPaiClubShowExecutorImpl.class); //来拍社文章展示
		REQUEST_MESSAGE_TYPE.put(5002, LaiPaiClubDetailExecutorImpl.class); //来拍社文章详细信息
		REQUEST_MESSAGE_TYPE.put(5003, AddArticleCommentExecutorImpl.class); //增加评论
		REQUEST_MESSAGE_TYPE.put(5005, AddArticleLikeExecutorImpl.class); //点赞
		//subject包
		REQUEST_MESSAGE_TYPE.put(1199, SubjectListExecutorImpl.class);// 列出所有上线专题列表
		REQUEST_MESSAGE_TYPE.put(1200, SubjectExecutorImpl.class);// 某一专题的所有作品集列表
		REQUEST_MESSAGE_TYPE.put(1201, GalaryExecutorImpl.class);// 某一作品集的详细信息
		//userManInfo包
		REQUEST_MESSAGE_TYPE.put(1101, RegisterExecutorImpl.class);// 注册
		REQUEST_MESSAGE_TYPE.put(1102, UserLoginExecutorImpl.class);// 用户登录
		REQUEST_MESSAGE_TYPE.put(1104, QueryUserFollowImpl.class);// 查询用户的关注
		REQUEST_MESSAGE_TYPE.put(1105, QueryUserFansImpl.class);// 查询用户的粉丝
		REQUEST_MESSAGE_TYPE.put(1106, UpdateCameramanInfoImpl.class);// 更改用户的信息
		REQUEST_MESSAGE_TYPE.put(1107, QueryPersonHomePageImpl.class);// 查询用户个人主页
		
		REQUEST_MESSAGE_TYPE.put(1108, DeleteFollowOrFansImpl.class);// 删除关注或粉丝
		
		REQUEST_MESSAGE_TYPE.put(1109, FollowCameramanImpl.class);// 关注摄影师
		REQUEST_MESSAGE_TYPE.put(1110, QueryUserLikeImpl.class);// 查询用户喜欢的作品集
		REQUEST_MESSAGE_TYPE.put(1111, ChangePasswordImpl.class);// 修改用户密码
		REQUEST_MESSAGE_TYPE.put(1112, AddUserFeedBackImpl.class);// 用户反馈信息

		REQUEST_MESSAGE_TYPE.put(1113, ToEditCameramanImpl.class);// 跳转编辑页面
		REQUEST_MESSAGE_TYPE.put(1114, SendVercodeSMSExecutorImpl.class);//用户注册，发送验证码短信接口
		REQUEST_MESSAGE_TYPE.put(1115, UserLogoutExecutorImpl.class);//退出登录
		
		REQUEST_MESSAGE_TYPE.put(1116, QueryCustomerHomePageImpl.class);//查询主页（客态）
		
		REQUEST_MESSAGE_TYPE.put(1117, ChangeHeadImgImpl.class);//更改头像
		REQUEST_MESSAGE_TYPE.put(1118, RetrievePasswordExecutorImpl.class);//用户找回密码
		REQUEST_MESSAGE_TYPE.put(1119, ChangeHeadImgForAndroid.class);//更改头像(安卓（传文件）)
		
		REQUEST_MESSAGE_TYPE.put(1120, QueryuserByNickImpl.class);//根据昵称搜索用户
		REQUEST_MESSAGE_TYPE.put(1121, UserThirdPartyLoginExecutorImpl.class);//第三方登录
	}

}
