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
import com.laipai.laiPaiClubInfo.pojo.LpClubExtend;
import com.laipai.laiPaiClubInfo.pojo.VLpClub;
import com.laipai.logs.pojo.LpClubArticlelog;
import com.laipai.orderInfo.pojo.LpOrder;
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
 * 作品集展示
 * 
 * @author zhh
 */
@NotLogin
@Service("LaiPaiClubDetailExecutorImpl")
public class LaiPaiClubDetailExecutorImpl implements RequestExecutor {

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
		String page=JSONTools.getString(json,"page");
		String machineId=JSONTools.getString(json,"imei");
		VLpClub club = (VLpClub) baseDao.getObjectById(VLpClub.class,laipaiId);
		LpClubShowBean bean = new LpClubShowBean();
		BeanUtils.copyProperties(club, bean);
		int likeNum = baseDao.getCount("from LpLike where newsId='"+laipaiId+"' and lpUser.userId='"+userId+"' and likeType=1");
		if(likeNum >0){
			bean.setIsLike(0); //赞过
		}else{
			bean.setIsLike(1); //没有赞过
		}
		int viewNumber=club.getViewNumber();
		//修改文章浏览数量
		LpClub club2 = (LpClub) baseDao.getObjectById(LpClub.class, laipaiId);
		club2.setViewNumber(viewNumber+1);
		baseDao.updateObject(club2);
		
		 if(club2.getControlSource()==1){
			 List<LpClubExtend> list = baseDao.queryListObjectAll("from LpClubExtend where laipaiId='"+laipaiId+"'");
				if(list!=null && list.size()>0){
					LpClubExtend extend = list.get(0);
					extend.setViewNumber(extend.getViewNumber()+1);
					baseDao.updateObject(extend);
				}
			 
			 
		 }
		
		
		//喜欢数量
		bean.setLikeNumber(club.getLikeNumber());
		//评论数量
		int commentNumber=baseDao.getCount("from LpComment WHERE newsId='"+club.getLaipaiId()+"'");
		bean.setCommentNumber(commentNumber);
		//文章标题图片
		bean.setCoverUrl(ImgUtil.getImgUrl(bean.getCoverUrl(),parameters.getPicType()));
//		//文章内容中的图片
//		bean.setContent(ImgUtil.getImgUrl(bean.getContent(),parameters.getPicType()));
		//来拍社文章
		baseJson.put("lpClub", JSONObject.fromObject(bean));
		//评论
		/*List<LpComment> lpComment = baseDao.queryListObjectAll("from LpComment where newsId='"+laipaiId+"'");*/
		List<LpComment> lpComment=this.getfanslist(laipaiId, page);
		List commentList = new ArrayList();
		if(lpComment.size()>0){
			Iterator<LpComment> iterator = lpComment.iterator();
			while(iterator.hasNext()){
				LpComment comment = iterator.next();
				LpUser commentUser = comment.getLpUser();
				if(commentUser !=null){
					Map map = new HashMap();
					map.put("commentId", comment.getCommentId());
					map.put("userId", commentUser.getUserId());
					 if(StringUtils.isNotBlank(commentUser.getNickName())){
					map.put("nickName", commentUser.getNickName());
					 }else{				 
						 map.put("nickName", ""); 
					 }
					map.put("userType", commentUser.getUserType());
//					if(commentUser.getAccountSource()!=null){
//						if(StringUtils.isNotBlank(commentUser.getHeadImage())){
//					if(commentUser.getAccountSource()==0){  
//					map.put("headImage", ImgUtil.getImgUrl(commentUser.getHeadImage()));
//					}else{
//				    map.put("headImage", commentUser.getHeadImage());
//						
//					}
//						}else{
//							
//							 map.put("headImage", "");
//						}
//					}
					map.put("headImage", ImgUtil.getImgUrl(commentUser.getHeadImage(),parameters.getPicType()));
					map.put("commentDetail", comment.getCommentDetail());
					//获取时间
					String createTime = getCreateTime(comment.getCreateTime());
					map.put("createTime", createTime);
					commentList.add(map);
				}
			}
		}
		baseJson.put("lpComment", JSONConvertUtil.allObjectToJSON(commentList));
		if("1".equals(page)){
			LpClubArticlelog  clubArticlelog=new LpClubArticlelog();
			clubArticlelog.setUserId(userId);
			if(StringUtils.isBlank(userId)){
				clubArticlelog.setMachineId(machineId);
				
			}
			
			clubArticlelog.setAccessTime(new Timestamp(new java.util.Date().getTime()));
			clubArticlelog.setArticleId(laipaiId);
			baseDao.save(clubArticlelog);
		}
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
	
	public List<LpComment> getfanslist(String laipaiId,String pageStr){
		int page=1;
		String hql="from LpComment WHERE newsId='"+laipaiId+"' order by createTime desc";
		List<LpComment> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 5, page);
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
