package com.laipai.subject.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.UserUtil;
import com.laipai.galaryManInfo.bean.LpGalaryBean;
import com.laipai.galaryManInfo.dto.GalleryCommentBean;
import com.laipai.galaryManInfo.dto.GalleryDetail;
import com.laipai.galaryManInfo.dto.LpGalleryBean;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.logs.pojo.LpGalaryLog;
import com.laipai.logs.pojo.LpGalarydetailLog;
import com.laipai.logs.pojo.LpGalarydetailShowlog;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.pojo.LpServiceStyle;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userManInfo.dto.LpCommentBean;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;

@NotLogin
@Service("GalaryExecutorImpl")
public class GalaryExecutorImpl implements RequestExecutor{

	/** 
	 * @Fields mobileDeviceDAO : 处理手机是什么类型的（android,苹果） 
	 */  
	@Resource
	private IMobileDeviceDAO mobileDeviceDAO;	
	
	@Resource
	private IBaseDao baseDao;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	@Autowired
	private IServiceService  serviceService;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		Map map=new HashMap();
		JSONObject baseJson = new JSONObject();
		JSONObject json = parameters.getJson();
		String serverName = json.getString("serverName");
		String galaryId = JSONTools.getString(json, "galaryId");
		String userId = JSONTools.getString(json, "userId");
		String page=JSONTools.getString(json,"page");
		String machineId=JSONTools.getString(json,"imei");
		String enterType=JSONTools.getString(json,"enterType");
/*		 if(galaryId==null||userId==null){
			 return JSONTools.newAPIResult(1, "参数不全"); 			 
		 }*/
		
		List<GalleryDetail> list =new ArrayList<GalleryDetail>();
		
		String hql=" from LpGalaryDetail where lpGalary.galaryId='"+galaryId+"' and status=0 order by galaryConver desc";
		List<LpGalaryDetail> detaillist=baseDao.queryListObjectAll(hql);
		if("1".equals(page)){
		//insertDetail(detaillist,userId,machineId,enterType);
		}
		GalleryDetail coverDetailBean =new GalleryDetail(); //封面对象
		if(detaillist!=null&&detaillist.size()>0){
			for(LpGalaryDetail detail:detaillist){
				GalleryDetail detailBean =new GalleryDetail();
				BeanUtils.copyProperties(detail, detailBean);
				detailBean.setPhotoSrc(ImgUtil.getImgUrl(detailBean.getPhotoSrc(),parameters.getPicType()));
				if(detail.getGalaryConver()!=null){
					boolean isCover= detail.getGalaryConver();
					if(isCover){
						//该照片是封面（1）
						detailBean.setIsCover(1);
						//给封面对象赋值
						BeanUtils.copyProperties(detailBean, coverDetailBean); 
					}else{
						detailBean.setIsCover(0);					
					}
				}
				//先不放封面原图，放其他8张图片
				if(detailBean.getIsCover() == null || (detailBean.getIsCover()!=null && detailBean.getIsCover()!=1)){
					list.add(detailBean);
				}
				if("1".equals(page)){
					insertDetailShow(detail.getPhotoId(),userId,machineId,enterType);		
					
				}
			}
			//把封面原图插入到第一位
			list.add(0,  coverDetailBean);
		}
		map.put("photos", list);
		LpGalleryBean galleryBean =new LpGalleryBean();
		LpGalary lpGalary =  (LpGalary)baseDao.getObjectById(LpGalary.class, galaryId);
		if(lpGalary==null){			
			return JSONTools.newAPIResult(1, "该作品集不存在");
		}
		if(lpGalary!=null){
			//非游客登录
			if(!"".equals(userId)){
		 //判断登录用户是否对改作品集点过赞
			List<LpLike> likelist=baseDao.queryListObjectAll("from  LpLike where lpUser.userId='"+userId+"' and lpGalary.galaryId='"+galaryId+"'");
			//如果点过，返回0
			if(likelist!=null&&likelist.size()>0){		
				galleryBean.setIsLike(0);
			}else{
				//否则返回1
				galleryBean.setIsLike(1);
			}
			}else{
				//游客登录
				galleryBean.setIsLike(1);
			}
			int viewNumber=lpGalary.getViewNumber();
			lpGalary.setViewNumber(viewNumber+1);
			baseDao.updateObject(lpGalary);
			//修改假数据
			if(lpGalary.getControlSource() ==1){
				List<LpGalaryExtend> listExe = baseDao.queryListObjectAll("from LpGalaryExtend where lpGalary.galaryId='"+galaryId+"'");
				if(listExe!=null && listExe.size()>0){
					LpGalaryExtend extend = listExe.get(0);
					extend.setViewNumber(extend.getViewNumber()+1);
					baseDao.updateObject(extend);
				}
			}
			
			galleryBean.setSubjectName(lpGalary.getSubjectName());
			galleryBean.setGalaryId(galaryId);
			galleryBean.setGalaryDesc(lpGalary.getGalaryDesc());
			galleryBean.setGalaryCover(ImgUtil.getImgUrl(lpGalary.getGalaryCover()));
			
			 LpService service= lpGalary.getLpService();
	    	  if(service!=null){
	    	  /*String serviceid=service.getServiceId();
	    	  LpService lpService=serviceService.queryById(serviceid);*/
	    	  LpServiceDetail detail= service.getLpServiceDetail();
	    	  galleryBean.setPrice(((double)detail.getPrice())/100);
	    	  galleryBean.setShootTime(detail.getShootTime());
	    	  galleryBean.setPictureNum(detail.getPictureNum());
	    	  galleryBean.setClothes(detail.getClothes());
	    	  galleryBean.setFacepaint(detail.getFacepaint());
	    	  galleryBean.setInstructions(detail.getInstructions());
	    	  map.put("serviceid", service.getServiceId());
	    	  }else{
	    		  map.put("serviceid", "");  
	    		  
	    	  }
	    	 
	    	  //取用户手机号
	         if(lpGalary.getLpUser()!=null){
	         galleryBean.setCameramanId(lpGalary.getLpUser().getUserId()); 
	         //如果用户没有留手机号，取用户的账号
	         if(lpGalary.getLpUser().getMobile()==null){
	        	 galleryBean.setMobille(lpGalary.getLpUser().getUserName()); 
	         }	else{
	         //否则取用户的手机号 
	        	 galleryBean.setMobille(lpGalary.getLpUser().getMobile()); 
	         } 
//	         if(lpGalary.getLpUser()!=null && StringUtils.isNotEmpty(lpGalary.getLpUser().getHeadImage())){
//	        	 map.put("cameramanHeadImg", serverName + lpGalary.getLpUser().getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//	         }else{
//	        	 map.put("cameramanHeadImg", "");
//	         }
	         map.put("cameramanHeadImg", ImgUtil.getImgUrl(lpGalary.getLpUser().getHeadImage(),parameters.getPicType()));
	         map.put("cameramanNickName", lpGalary.getLpUser().getNickName());
	         map.put("cameramanNickName2", UserUtil.getUserName(lpGalary.getLpUser().getNickName(), 1));
	       /*  map.put("cameramanId", lpGalary.getLpUser().getUserId());*/
	         
	         if(lpGalary.getLpUser().getLpCity()!=null){
	        	 
	          map.put("cityName", lpGalary.getLpUser().getLpCity().getCityName());
	         }
	         }
	         //点赞数
	         String likeSql = " SELECT "
							+" if((lg.control_source = 0),lg.like_number,lge.like_number) AS like_number  "
							+" FROM  "
							+" lp_galary lg  "
							+" left join lp_galary_extend lge on lge.galary_id = lg.galary_id "
							+" where 1=1 "
							+" and lg.galary_id = '"+lpGalary.getGalaryId()+"' ";
	         
	         
	       //浏览数
	         String viewSql = " SELECT "
							+" if((lg.control_source = 0),lg.view_number,lge.view_number) AS view_number  "
							+" FROM  "
							+" lp_galary lg  "
							+" left join lp_galary_extend lge on lge.galary_id = lg.galary_id "
							+" where 1=1 "
							+" and lg.galary_id = '"+lpGalary.getGalaryId()+"' ";
	         List likeList = baseDao.querySqlObject(likeSql);
	         List viewList = baseDao.querySqlObject(viewSql);
	         if(likeList !=null && !likeList.isEmpty()){
	        	 Map likeMap = (Map) likeList.get(0);
	        	 Integer likeNum =  (Integer) likeMap.get("like_number");
	        	 galleryBean.setLikeNumber(likeNum);
	         }
	         if(viewList !=null && !viewList.isEmpty()){
	        	 Map likeMap = (Map) viewList.get(0);
	        	 Integer viewNum =  (Integer) likeMap.get("view_number");
	        	 galleryBean.setViewNumber(viewNum);
	         }
		}
		
		int countNumber= this.getCommentCount(galaryId);
		galleryBean.setCommentNumber(countNumber);
		map.put("Gallery", galleryBean);
		
		//List<LpComment> listComment =galleryService.queryCommentByGalleryID(galaryId);
		List<LpComment> listComment  =getfanslist(galaryId,page);
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
//					if(StringUtils.isNotBlank(user.getHeadImage())){
//						if(user.getAccountSource()==0){
//						bean.setHeadImg(serverName + user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//						}else{
//							
//							bean.setHeadImg(user.getHeadImage());	
//						}
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
		List<LpFollow> listfollow= baseDao.queryListObjectAll("from LpFollow where lpUserByUserId.userId='"+userId+"' and lpUserByCameraId.userId='"+galleryBean.getCameramanId()+"'");
		map.put("commentlist", listbean);
		map.put("userId", userId);
		String followId="";
		if(listfollow!=null&&listfollow.size()>0){
			LpFollow follow=listfollow.get(0);				
			 followId= follow.getFollowId();
		}		
		map.put("followId", followId);
		
		
		
		JSONObject resulejson = new JSONObject();
		resulejson=JSONObject.fromObject(map);
		//作品集访问日志
		
		if("1".equals(page)){
			
         //this.inserLog(lpGalary, userId, machineId);
			
		}
		
		
		
		return resulejson;
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
//		String hql="select lc from LpComment lc, LpUser lu where lc.lpUser.userId=lu.userId and lc.lpGalary.galaryId='"+galaryId+"' and lu.userStatus=0 order by lc.createTime desc";
		String hql = "from LpComment lc where lc.commentType in(0,4) and  lc.lpGalary.galaryId='"+galaryId
				+"' and lc.lpUser.userId=(select lu.userId from LpUser lu " +
				"where lc.lpUser.userId=lu.userId and lu.userStatus=0) order by lc.createTime desc";
		List<LpComment> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 5, 1);
		}
		
		return list;
		
	}
	
	//查作品集的评论数
    public int getCommentCount(String gallaryid){
//     String hql = "from LpComment lc, LpUser lu where lc.lpUser.userId=lu.userId and lc.lpGalary.galaryId='"+gallaryid+"' and lu.userStatus=0";  
    	String hql = "from LpComment lc where lc.commentType in(0,4) and lc.lpGalary.galaryId='"+gallaryid
 				+"' and lc.lpUser.userId=(select lu.userId from LpUser lu " +
 				"where lc.lpUser.userId=lu.userId and lu.userStatus=0) order by lc.createTime desc";
     int countNumber= baseDao.getCount(hql);
     
     return countNumber;
   }
    
/*    public void inserLog(LpGalary lpGalary,String userId,String machineId){
    	
		if(lpGalary.getLpService()!=null){
		     String detailId=lpGalary.getLpService().getLpServiceDetail().getDetailId();
			 List<LpServiceStyle> liststyle=baseDao.queryListObjectAll("from LpServiceStyle where detailId='"+detailId+"'");
			 if(liststyle!=null&&liststyle.size()>0){
				 for(int i=0;i<liststyle.size();i++){
					 String styleId=liststyle.get(i).getStyleId();
					 LpGalaryLog gallaryLog=new LpGalaryLog();
						
						gallaryLog.setUserId(userId);
						if(StringUtils.isBlank(userId)){
							
							gallaryLog.setMachineId(machineId);
							}
						
						gallaryLog.setStyleId(styleId);
						
						gallaryLog.setAccessTime(new Timestamp(new java.util.Date().getTime()));
						gallaryLog.setGalaryId(lpGalary.getGalaryId());
						baseDao.save(gallaryLog);
				 }
				 
			 }
			}
    	
    }
    
     public  void insertDetail(List<LpGalaryDetail> detaillist,String userId,String machineId,String enterType){
    	if(detaillist!=null&&detaillist.size()>0){
    		if(detaillist.size()<=3){
    			for(LpGalaryDetail detail :detaillist){
    				
    				saveDetail(detail.getPhotoId(),userId,machineId,enterType);
    			}
    			
    		} else if(detaillist.size()>3&&detaillist.size()<7){
    			for(int i=0;i<5;i++){
    				saveDetail(detaillist.get(i).getPhotoId(),userId,machineId,enterType);
    				
    			} 
    		    
    		}else{
				
    			for(int i=0;i<4;i++){
    				saveDetail(detaillist.get(i).getPhotoId(),userId,machineId,enterType);
    				
    			} 
			}
          
    }
     }*/
     
    public void saveDetail(String detailId,String userId,String machineId,String enterType){
    	LpGalarydetailLog detailLog=new LpGalarydetailLog();
   	 detailLog.setGalaryDetailId(detailId);
   	 detailLog.setUserId(userId);
   	 if(StringUtils.isBlank(userId)){
   		 detailLog.setMachineId(machineId);
   		 
   	 }
   	 detailLog.setAccessTime(new Timestamp(new java.util.Date().getTime()));
   	 if(StringUtils.isNotBlank(enterType)){
   		 int type=Integer.parseInt(enterType);
   	  detailLog.setEnterType(type);
   	 }
   	 baseDao.save(detailLog);
    	
    } 
    
    
    public void insertDetailShow(String detailId,String userId,String machineId,String enterType){
    	LpGalarydetailShowlog detailshow =new LpGalarydetailShowlog();
    	detailshow.setGalaryDetailId(detailId);
    	detailshow.setUserId(userId);
    	if(StringUtils.isBlank(userId)){
    		detailshow.setMachineId(machineId);
    		
    	}
    	
    	detailshow.setShowTime(new Timestamp(new java.util.Date().getTime()));
    	
    	
    }
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
}
