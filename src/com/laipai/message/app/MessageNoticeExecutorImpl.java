package com.laipai.message.app;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.img.ImgUtil;
import com.laipai.message.pojo.LpMessageMapping;
import com.laipai.message.pojo.VLpMessageDetail;
import com.laipai.message.service.IMessageService;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 作品集首页,左侧上方,消息列表
 * */
@NotLogin
@Service("MessageNoticeExecutorImpl")
public class MessageNoticeExecutorImpl implements RequestExecutor {
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private IMessageService messageService;

	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		// 获取服务器域名
		String serverName = json.getString("serverName");

		String userId = json.getString("userId");
		// 新消息类型1、 预约，2、评论(所有评论和回复)，3、赞，4、关注，5、系统消息
		String messageType = json.getString("messageType");
		if (StringUtils.isEmpty(userId)) {
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 预约
			if ("1".equals(messageType)) {
				List<Map> orderList = getOrderArray(parameters,userId, serverName);
				map.put("order", orderList);
			}
			// 作品集评论,回复
			if ("2".equals(messageType)) {
				List<Map> commentList = getCommentArray(parameters,userId, serverName);
				map.put("comment", commentList);
			}
			// 赞
			if ("3".equals(messageType)) {
				List<Map> likeList = getLikeArray(parameters,userId, serverName);
				map.put("like", likeList);
			}
			// 关注
			if ("4".equals(messageType)) {
				List<Map> followList = getFollowArray(parameters,userId, serverName);
				map.put("follow", followList);
			}
			// 系统消息（认证摄影师通过/不通过,来拍社消息）
			if ("5".equals(messageType)) {
				List<Map> systemList = getNoticeArray(userId, serverName);
				map.put("system", systemList);
			}
			return JSONObject.fromObject(map);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "查询消息列表失败");
		}
	}

	/**
	 * 预约
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	private List<Map> getOrderArray(BaseRequestParameters parameters,String userId, String serverName) {
		// List<VLpMessageDetail>
		// messageList=baseDao.queryListObjectAll("from VLpMessageDetail where userId='"+userId+"' order by createTime desc");
		List<LpOrder> orderList = baseDao
				.queryListObjectAll("from LpOrder lo where lo.lpCamera.userId='"
						+ userId
						+ "' and lo.lpGalary.galaryId is not null  and orderStatus=3 order by createTime desc,orderTime desc");
		List<Map> ls = new ArrayList<Map>();
		for (LpOrder order : orderList) {
			Map<String, Object> map = new HashMap<String, Object>();
			// 预约作品集主题
			List<LpMessageMapping> messageList = baseDao
					.queryListObjectAll("from LpMessageMapping where messageType=1 and orderId='"
							+ order.getOrderId() + "'");
			if (messageList != null && messageList.size() > 0) {
				map.put("messageId", messageList.get(0).getMessageId());
				map.put("messageStatus", messageList.get(0).getStatus());
			} else {
				map.put("messageId", "");
				map.put("messageStatus", 1);

			}
			map.put("orderId", order.getOrderId());
			map.put("subjectName", order.getLpGalary().getSubjectName());
			// 预约用户
			map.put("userId", order.getLpUser().getUserId());
			map.put("nickName", order.getLpUser().getNickName());
			map.put("userType", order.getLpUser().getUserType());
			if (order.getLpUser() != null) {
				// if(order.getLpUser().getHeadImage()!=null){
				// if(order.getLpUser().getAccountSource()==0){
				//					
				// map.put("headImage", serverName +
				// order.getLpUser().getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL,
				// LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
				// }else{
				//						
				// map.put("headImage", order.getLpUser().getHeadImage());
				// }
				// }
				//				
				// else{
				//					
				// map.put("headImage","");
				// }
				map.put("headImage", order.getLpUser().getHeadImage());
			}
			// 预约时间
			Timestamp createTime = order.getCreateTime();
			DateFormat date = new SimpleDateFormat("yyyy年MM月dd日");
			String dateToString = "";
			try {
				dateToString = date.format(createTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("orderTime", dateToString);
			// 作品集封面
			// String
			// cover=order.getLpGalary().getGalaryCover().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL,
			// LaipaiConstants.UPLOAD_VIRTUAL_IMG+SimpleImage.LP_GALLERY_IMGURL);
			map.put("galaryCover", ImgUtil.getImgUrl(order.getLpGalary()
					.getGalaryCover(),parameters.getPicType()));
			// 作品集ID
			map.put("galaryId", order.getLpGalary().getGalaryId());
			ls.add(map);
		}
		return ls;
	}

	/**
	 * 作品集评论,评论类型(0:作品集的评论 1：来拍社评论 2：留言 3:来拍社回复 4:回复作品集评论)
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	private List<Map> getCommentArray(BaseRequestParameters parameters,String userId, String serverName) {
		String hql = "from LpComment lc where lc.lpGalary.lpUser.userId='"
				+ userId
				+ "' or lc.replayToId in "
				+ "(select lcc.commentId from LpComment lcc where lcc.commentId=lc.replayToId and lcc.lpUser.userId='"
				+ userId + "') order by lc.createTime desc";
		List<Map> ls = new ArrayList<Map>();
		if (StringUtils.isNotEmpty(userId)) {
			LpUser user = (LpUser) baseDao.getObjectById(LpUser.class, userId);

			List<LpComment> commentList = baseDao.queryListObjectAll(hql);
			for (LpComment comment : commentList) {
				Map<String, Object> map = new HashMap<String, Object>();
				// 评论人
				if (comment.getLpUser() == null) {
					continue;
				}
				map.put("userId", comment.getLpUser().getUserId());
				map.put("nickName", comment.getLpUser().getNickName());
				map.put("userType", comment.getLpUser().getUserType());
				if (comment.getLpUser() != null) {
					// if(comment.getLpUser().getHeadImage()!=null){
					// if(comment.getLpUser().getAccountSource()==0){
					// map.put("headImage", serverName +
					// comment.getLpUser().getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL,
					// LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
					// }
					// else{
					// map.put("headImage", comment.getLpUser().getHeadImage());
					// }
					// }else{
					// map.put("headImage", "");
					//					
					// }
					map.put("headImage", ImgUtil.getImgUrl(comment.getLpUser()
							.getHeadImage(),parameters.getPicType()));
				}
				String replayToId = comment.getReplayToId();
				if (StringUtils.isEmpty(replayToId)) {
					map.put("type", "comment");

				} else {
					map.put("type", "reply");
				}
				List<LpMessageMapping> messageList = baseDao
						.queryListObjectAll("from LpMessageMapping where messageType=2 and commentId='"
								+ comment.getCommentId() + "'");
				if (messageList != null && messageList.size() > 0) {
					map.put("messageId", messageList.get(0).getMessageId());
					map.put("messageStatus", messageList.get(0).getStatus());
				} else {
					map.put("messageId", "");
					map.put("messageStatus", 1);

				}
				// 评论内容
				map.put("commentDetail", comment.getCommentDetail());
				// 评论时间
				Timestamp createTime = comment.getCreateTime();
				DateFormat date = new SimpleDateFormat("yyyy年MM月dd日");
				String dateToString = "";
				try {
					dateToString = date.format(createTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
				map.put("commentTime", dateToString);
				// 作品集主题
				map.put("subjectName", comment.getLpGalary().getSubjectName());
				// 作品集封面
				// String
				// cover=comment.getLpGalary().getGalaryCover().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL,
				// LaipaiConstants.UPLOAD_VIRTUAL_IMG+SimpleImage.LP_GALLERY_IMGURL);
				map.put("galaryCover", ImgUtil.getImgUrl(comment.getLpGalary()
						.getGalaryCover(),parameters.getPicType()));
				// 作品集ID
				map.put("galaryId", comment.getLpGalary().getGalaryId());
				ls.add(map);
			}

		}
		return ls;
	}

	/**
	 * 关注
	 */
	private List<Map> getFollowArray(BaseRequestParameters parameters,String userId, String serverName) {
		List<LpFollow> followList = baseDao
				.queryListObjectAll("from LpFollow lp where lp.lpUserByCameraId.userId ='"
						+ userId + "' order by followTime desc");
		List<Map> ls = new ArrayList<Map>();
		for (LpFollow follow : followList) {
			LpUser user = follow.getLpUserByUserId();
			if (user == null) {
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			// 普通用户

			List<LpMessageMapping> messageList = baseDao
					.queryListObjectAll("from LpMessageMapping where messageType=4 and followId='"
							+ follow.getFollowId() + "'");
			if (messageList != null && messageList.size() > 0) {
				map.put("messageId", messageList.get(0).getMessageId());
				map.put("messageStatus", messageList.get(0).getStatus());

			} else {
				map.put("messageId", "");
				map.put("messageStatus", 1);

			}
			map.put("userId", user.getUserId());
			map.put("nickName", user.getNickName());
			map.put("userType", user.getUserType());
			// if(StringUtils.isNotBlank(user.getHeadImage())){
			// if(user.getAccountSource()==0){
			// map.put("headImage", serverName +
			// user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL,
			// LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
			// }else{
			// map.put("headImage",user.getHeadImage());
			// }
			// }else{
			// map.put("headImage","");
			//	    		
			// }
			map.put("headImage", ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
			// 关注时间
			DateFormat date = new SimpleDateFormat("yyyy年MM月dd日");
			String dateToString = "";
			try {
				dateToString = date.format(follow.getFollowTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("followTime", dateToString);
			ls.add(map);
		}
		return ls;
	}

	/**
	 * 系统消息
	 */
	private List<Map> getNoticeArray(String userId, String serverName) {
		List<VLpMessageDetail> messageList = baseDao
				.queryListObjectAll("from VLpMessageDetail where userId='"
						+ userId + "' order by createTime desc");
		List<Map> ls = new ArrayList<Map>();
		for (VLpMessageDetail message : messageList) {
			Map<String, Object> map = new HashMap<String, Object>();
			String content = message.getMessageContent();
			if (content.contains("恭喜")) {
				map.put("type", "checkpass");

			} else if (content.contains("审核未通过")) {

				map.put("type", "checkfail");

			} else {
				// content =
				// content.replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG,
				// serverName + LaipaiConstants.UPLOAD_VIRTUAL_IMG);
//				content = ImgUtil.getImgUrl(content);
				map.put("type", "system");

			}
			map.put("messageId", message.getMesDetailId());
			map.put("messageStatus", message.getMessageStatus());
			// 消息标题
			map.put("messageTitle", message.getMessageTitle());
			// 消息内容
			map.put("messageContent", content);
			// 消息时间
			DateFormat date = new SimpleDateFormat("yyyy年MM月dd日");
			String dateToString = "";
			try {
				dateToString = date.format(message.getCreateTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("createTime", dateToString);
			// 消息状态
			map.put("messageStatus", message.getMessageStatus());

			ls.add(map);
		}
		return ls;
	}

	/**
	 * 赞
	 * */
	private List<Map> getLikeArray(BaseRequestParameters parameters,String userId, String serverName) {
		List<LpLike> likeList = baseDao
				.queryListObjectAll("from LpLike where lpGalary.lpUser.userId='"
						+ userId + "' order by likeTime desc");
		List<Map> ls = new ArrayList<Map>();
		for (LpLike like : likeList) {
			Map<String, Object> map = new HashMap<String, Object>();
			// 用户id
			List<LpMessageMapping> messageList = baseDao
					.queryListObjectAll("from LpMessageMapping where messageType=3 and likeId='"
							+ like.getLikeId() + "'");
			if (messageList != null && messageList.size() > 0) {
				map.put("messageId", messageList.get(0).getMessageId());
				map.put("messageStatus", messageList.get(0).getStatus());

			} else {
				map.put("messageId", "");
				map.put("messageStatus", 1);

			}
			LpUser user = like.getLpUser();
			map.put("userId", like.getLpUser().getUserId());
			// 用户昵称
			map.put("nickName", like.getLpUser().getNickName());
			map.put("userType", like.getLpUser().getUserType());
			// 用户头像
			if (StringUtils.isNotBlank(user.getHeadImage())) {
				// if(user.getAccountSource()==0){
				// map.put("headImage", serverName +
				// user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL,
				// LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
				// }else{
				// map.put("headImage",user.getHeadImage());
				// }
				// }else{
				// map.put("headImage","");
				//			    		
				// }
				map.put("headImage", ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
				// 关注时间
				map.put("likeTime", DateUtils.parseTimestampToString(like
						.getLikeTime(), "yyyy.MM.dd"));
				// 赞的作品集
				map.put("galaryId", like.getLpGalary().getGalaryId());
				// 赞的作品集封面
				map.put("galaryCover", serverName
						+ like.getLpGalary().getGalaryCover().replace(
								LaipaiConstants.UPLOAD_ABSOLUTE_IMG
										+ SimpleImage.LP_GALLERY_IMGURL,
								LaipaiConstants.UPLOAD_VIRTUAL_IMG
										+ SimpleImage.LP_GALLERY_IMGURL));
				ls.add(map);
			}
			

		}
		return ls;
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
}
