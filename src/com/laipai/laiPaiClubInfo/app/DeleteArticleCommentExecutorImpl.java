package com.laipai.laiPaiClubInfo.app;

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
 * 删除来拍社文章评论
 * 
 * @author zhh
 */
@Service("DeleteArticleCommentExecutorImpl")
public class DeleteArticleCommentExecutorImpl implements RequestExecutor {

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
		String commentId = json.getString("commentId");
		LpComment comm= (LpComment) baseDao.getObjectById(LpComment.class, commentId);
		baseDao.delete(comm);
		
		LpClub club = (LpClub) baseDao.getObjectById(LpClub.class, laipaiId);
		LpClubShowBean bean = new LpClubShowBean();
		BeanUtils.copyProperties(club, bean);
		Set<LpLike> lpLike = club.getLpLike();
		Set<LpComment> lpComment = club.getLpComment();
		//喜欢数量
		bean.setLikeNumber(lpLike.size());
		//回复数量
		bean.setCommentNumber(lpComment.size());
		//文章标题图片
		bean.setCoverUrl(ImgUtil.getImgUrl(bean.getCoverUrl(),parameters.getPicType()));
		//文章内容中的图片
//		bean.setContent(ImgUtil.getImgUrl(bean.getContent(),parameters.getPicType()));
		//来拍社文章
		baseJson.put("lpClub", JSONObject.fromObject(bean));
		//评论
		List commentList = new ArrayList();
		if(lpComment.size()>0){
			Iterator<LpComment> iterator = lpComment.iterator();
			while(iterator.hasNext()){
				LpComment comment = iterator.next();
				LpUser commentUser = comment.getLpUser();
				if(commentUser==null){
					continue;
				}
				Map map = new HashMap();
				map.put("commentId", comment.getCommentId());
				map.put("userId", comment.getLpUser().getUserId());
				map.put("nickName", comment.getLpUser().getNickName());
				map.put("commentDetail", comment.getCommentDetail());
				
					map.put("headImage", ImgUtil.getImgUrl(comment.getLpUser().getHeadImage(),parameters.getPicType()));
				/*else{
					map.put("headImage", serverName + comment.getLpUser().getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_CAMERAMAN_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_CAMERAMAN_IMGURL));
				}*/
				//获取时间
				String createTime = getCreateTime(comment.getCreateTime());
				map.put("createTime", createTime);
				commentList.add(map);
			}
		}
		baseJson.put("lpComment", JSONConvertUtil.allObjectToJSON(commentList));
		
		return baseJson;
	}
	
	/**
	 * 查询评论时间
	 * */
	private String getCreateTime(Timestamp createTime){
		int commentYear = createTime.getYear()+1900;
		int commentMonth = createTime.getMonth()+ 1;
		int commentDay = createTime.getDate();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int date = c.get(Calendar.DATE);
		System.out.println("commentYear="+commentYear+"  commentMonth="+commentMonth +" commentDay="+commentDay);
		System.out.println("Year="+year+"  Month="+month +" Day="+date);
		if(commentYear == year
		   && commentMonth == month
		   && commentDay == date){
			return DateUtils.parseTimestampToString(createTime, "HH:mm");
		}else if(commentYear == year
				   && commentMonth == month
				   && (date - commentDay==1)){
			return "昨天";
		}else if(commentYear == year
				   && commentMonth == month
				   && (date - commentDay==2)){
			return "前天";
		}
		return "N天前";
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
