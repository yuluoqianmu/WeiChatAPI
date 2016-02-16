package com.laipai.galaryManInfo.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.GalleryCommentBean;
import com.laipai.img.ImgUtil;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;
/**
 * 

 * @Description:查询评论（作品集）

 * @author:lxd

 * @time:2015-3-10 下午3:30:51
 */
@NotLogin
@Service("QueryCommentExexutorImpl")
public class QueryCommentExexutorImpl implements RequestExecutor {
	@Resource
	private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String gallaryId = json.getString("galaryId");
		String page=JSONTools.getString(json,"page");
		String serverName = json.getString("serverName");
		List<LpComment> listComment  =getfanslist(gallaryId,page);
		List<GalleryCommentBean> listbean=new ArrayList<GalleryCommentBean>();
		if(listComment!=null&&listComment.size()>0){
			for(LpComment comment :listComment){
				
				GalleryCommentBean bean =new GalleryCommentBean();
				bean.setCommentId(comment.getCommentId());
				bean.setCommentDetail(comment.getCommentDetail());
				
				String Stringtime= this.getCreateTime(comment.getCreateTime());
				bean.setCreateTime(Stringtime);
				Integer commentType= comment.getCommentType();
				if(commentType!=null){				
					bean.setCommentType(commentType);	
				}
				LpUser user= comment.getLpUser();
				if(user!=null){
					bean.setUserId(user.getUserId());
					bean.setNickName(user.getNickName());
					bean.setUserType(user.getUserType());
//					if(user.getAccountSource()==0){						
//						bean.setHeadImg(serverName + user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));	
//					}else{
//						
//						bean.setHeadImg(user.getHeadImage());
//					}
					bean.setHeadImg(ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
					
				}
				if(StringUtils.isNotBlank(comment.getReplayToId())){
					LpComment replyComment =  (LpComment)baseDao.getObjectById(LpComment.class, comment.getReplayToId());
					if(replyComment!=null){
					LpUser replayuser= replyComment.getLpUser();
					bean.setReplayId(replayuser.getUserId());
					bean.setReplayNickName(replayuser.getNickName());
					bean.setReplayUserType(replayuser.getUserType());
					}
				}
				listbean.add(bean);
				
			}
			
		}
		return listbean;
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
	
	
	public List<LpComment> getfanslist(String galaryId,String pageStr){
		int page=1;
		String hql="from LpComment WHERE lpGalary.galaryId='"+galaryId+"' and commentType in(0,4) order by createTime desc";
		List<LpComment> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 10, page);
		}
		
		return list;
		
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
