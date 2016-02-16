package com.laipai.laiPaiClubInfo.app;

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

import org.apache.commons.lang.StringUtils;
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
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.dto.LpClubShowBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.service.ILaiPaiClubService;
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
 * 增加来拍社文章评论
 * 
 * @author zhh
 */
@NotLogin
@Service("AddArticleCommentExecutorImpl")
public class AddArticleCommentExecutorImpl implements RequestExecutor {

	@Autowired
	private ILaiPaiClubService laiPaiClubService;
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
		String laipaiId = json.getString("laipaiId");
		String userId = json.getString("userId");
		String commentDetail = json.getString("commentDetail");
		LpUser user = (LpUser) baseDao.getObjectById(LpUser.class, userId);
		//添加详情信息
		//laiPaiClubService.addLpComment(laipaiId,commentDetail,user);
		
		LpComment lpComment = new LpComment();
		lpComment.setLpUser(user);
		lpComment.setCommentDetail(commentDetail);
		lpComment.setNewsId(laipaiId);
		lpComment.setCreateTime(DateUtils.dateToTimestamp(new Date()));
		Serializable commentId= baseDao.saveObjectReturnId(lpComment);
		baseJson.put("commentId", commentId);
		baseJson.put("userId", user.getUserId());
		baseJson.put("nickName", user.getNickName());
//		if(user.getAccountSource()==0){
//			baseJson.put("headImage", serverName + user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//		}else{
//			
//			baseJson.put("headImage",  user.getHeadImage());
//		}
		baseJson.put("headImage",  ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
		
		return baseJson;
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
