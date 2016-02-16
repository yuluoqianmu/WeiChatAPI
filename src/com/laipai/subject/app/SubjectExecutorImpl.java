package com.laipai.subject.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.bean.LpGalaryBean;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.img.ImgUtil;
import com.laipai.logs.pojo.LpGalaryShowlog;
import com.laipai.logs.pojo.LpSubjectLog;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.subject.bean.SubjectBean;
import com.laipai.subject.pojo.Subject;
import com.laipai.subject.pojo.SubjectDetail;
import com.laipai.subject.pojo.VLpSubjectDetailApp;
import com.laipai.subject.pojo.VLpSubjectDetailBack;
import com.laipai.subject.service.ISubjectService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 某一专题的所有作品集列表
 * */
@NotLogin
@Service("SubjectExecutorImpl")
public class SubjectExecutorImpl implements RequestExecutor{
	 private static final Logger logger=Logger.getLogger(ServiceDao.class);
	/** 
	 * @Fields mobileDeviceDAO : 处理手机是什么类型的（android,苹果） 
	 */  
	@Resource
	private IMobileDeviceDAO mobileDeviceDAO;
	
	@Resource
	private IBaseDao baseDao;
	
	@Resource
	private ISubjectService iSubjectService;
	
	/*
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		JSONObject baseJson = new JSONObject();
		
		String serverName = json.getString("serverName");
		String userId = JSONTools.getString(json, "userId");
		String subjectId = JSONTools.getString(json, "subjectId");
		List<LpGalaryBean> galaryList = new ArrayList<LpGalaryBean>();		
		List<SubjectDetail> list = baseDao.queryListObjectAll("from SubjectDetail where subject_id ='"+ subjectId +"' order by subjectGalaryLocation");
		for (SubjectDetail subjectDetail : list) 
		{
			LpGalaryBean lpGalaryBean = new LpGalaryBean();
			String galaryId = subjectDetail.getGallery_id();
			LpGalary lpGalary =  (LpGalary)baseDao.getObjectById(LpGalary.class, galaryId);
			//LpUser lpUser = (LpUser)baseDao.getObjectById(LpUser.class,lpGalary.getLpUser().getUserId());
			if(lpGalary.getGalaryStatus() == 0 && lpGalary.getStatus() == 0) {
				lpGalaryBean.setGalaryId(lpGalary.getGalaryId());
				//主题集主题
				lpGalaryBean.setSubjectName(lpGalary.getSubjectName());
				//摄影师头像
				lpGalaryBean.setHeadImage(lpGalary.getLpUser().getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG, serverName+LaipaiConstants.UPLOAD_VIRTUAL_IMG));
				if(lpGalary.getLikeNumber() == null) {
					lpGalaryBean.setLikeNumber(0);
				}else {
					lpGalaryBean.setLikeNumber(lpGalary.getLikeNumber());
				}
				if(lpGalary.getViewNumber() == null) {
					lpGalaryBean.setViewNumber(0);
				} else {
					lpGalaryBean.setViewNumber(lpGalary.getViewNumber());
				}
				if(lpGalary.getCommentNumber() == null) {
					lpGalaryBean.setCommentNumber(0);
				}else {
					lpGalaryBean.setCommentNumber(lpGalary.getCommentNumber());
				}
				if(lpGalary.getLpUser() != null) {
					System.out.println("========"+lpGalary.getLpUser());
					lpGalaryBean.setUserName(lpGalary.getLpUser().getUserName());
				} else {
					lpGalaryBean.setUserName("无名");
				}
				if(lpGalary.getLpService() != null) {
					System.out.println("=================="+lpGalary.getLpService().getLpServiceDetail());
					LpServiceDetail detail = lpGalary.getLpService().getLpServiceDetail();
					long serviceMoney = 0;
					serviceMoney = detail.getPrice();
					lpGalaryBean.setServiceMoney(serviceMoney);
				} else {
					lpGalaryBean.setServiceMoney(0);
				}
				lpGalaryBean.setGalaryCover(serverName + lpGalary.getGalaryCover().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG + SimpleImage.LP_GALLERY_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG + SimpleImage.LP_GALLERY_IMGURL));
				if(!"".equals(userId)){
					 //判断登录用户是否对改作品集点过赞
						List<LpLike> likelist=baseDao.queryListObjectAll("from  LpLike where lpUser.userId='"+userId+"' and lpGalary.galaryId='"+galaryId+"' and likeStatus=0");
						//如果点过，返回0
						if(likelist!=null&&likelist.size()>0){		
							lpGalaryBean.setIsLike(0);
						}else{
							//否则返回1
							lpGalaryBean.setIsLike(1);
						}
						}else{
							//游客登录
							lpGalaryBean.setIsLike(1);
						}
				lpGalaryBean.setCameramanId(lpGalary.getLpUser().getUserId());
				lpGalaryBean.setUserType(lpGalary.getLpUser().getUserType());
				galaryList.add(lpGalaryBean);
			}
		}
		return galaryList;
	}
	*/
	
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String serverName = json.getString("serverName");
		String userId = JSONTools.getString(json, "userId");
		String subjectId = JSONTools.getString(json, "subjectId");
	    String page=JSONTools.getString(json,"page");
	    String machineId=JSONTools.getString(json,"imei");
/*		if(StringUtils.isEmpty(subjectId) || StringUtils.isEmpty(userId)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}*/
		List<VLpSubjectDetailApp> list = getVLpSubjectDetailAppBySubjectId(subjectId,page);
		if(list!=null&&list.size()>0){
		for (VLpSubjectDetailApp detail : list) {
			/*兼容ios 1.0版本*/
			detail.setUserId(detail.getCameramanId());
			
			detail.setGalaryCover(ImgUtil.getImgUrl(detail.getGalaryCover(),parameters.getPicType()));
			 String galaryId=detail.getGalaryId();
			  int countNumber= this.getCommentCount(galaryId);
			  detail.setCommentNumber(countNumber);
			 String galaryCover= detail.getGalaryCover();
//			 if(galaryCover.startsWith("/upload")){
//				 galaryCover=serverName+galaryCover;
//				 detail.setGalaryCover(galaryCover);		 
//			 }else{
//				 
//				 detail.setGalaryCover(serverName+detail.getGalaryCover().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+SimpleImage.LP_GALLERY_IMGURL)); 
//				 
//			 }
			 
//			 if(detail.getHeadImage().startsWith("/upload")){
//				 detail.setHeadImage(serverName+detail.getHeadImage());
//				}else{
//					
//					detail.setHeadImage(serverName+ detail.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//					
//				}
			 detail.setHeadImage(ImgUtil.getImgUrl(detail.getHeadImage(),parameters.getPicType()));
//			int likeNum = baseDao.getCount("from LpLike where lpGalary.galaryId='"+detail.getGalaryId()+"' and lpUser.userId='"+userId+"'");
			String likeNum = "0";
			String sql  = "select ifnull(sum(a.like_number),0) like_number from ( "
						+" SELECT sum(ifnull(lge.like_number,0))  like_number   "
						+"  FROM  lp_galary lg "
						+"  left join  lp_galary_extend lge on lge.galary_id = lg.galary_id   "
						+"  where lg.control_source = 1 "
						+" and lg.galary_status=0 "
						+" and lg.status=0 "
						+"  and lge.extend_id is not null "
						+"  and lg.galary_id ='"+galaryId+"' "
						+" union all "
						+"  SELECT sum(ifnull(lg.like_number,0))  like_number   "
						+"  FROM  lp_galary lg    "
						+"  where  lg.galary_status=0 "
						+" and lg.status=0 "
						+" and  lg.control_source = 0 "
						+"  and  lg.galary_id ='"+galaryId+"' "
						+" ) a";
			System.out.println("--------------sql ="+sql);
			List LikeList = baseDao.querySqlObject(sql);
			if(LikeList!=null && !LikeList.isEmpty()){
				Map map = (Map) LikeList.get(0);
				int num = ((BigDecimal) map.get("like_number")).intValue();
				if(num >999){
					likeNum = (int)Math.floor(num/1000)+"K";
				}else{
					likeNum = num+"";
				}
			}
			
			int likeNumjudge = baseDao.getCount("from LpLike where lpGalary.galaryId='"+detail.getGalaryId()+"' and lpUser.userId='"+userId+"'");
			if(likeNumjudge >0){
				detail.setIsLike(0); //赞过
			}else{
				detail.setIsLike(1); //没有赞过
			}
			
			inserLog(detail.getGalaryId(), userId, machineId);
		}
		}
	 if("1".equals(page)){
		 LpSubjectLog subjectLog=new LpSubjectLog();
		 subjectLog.setSubjectId(subjectId);
		 subjectLog.setUserId(userId);
		 if(StringUtils.isBlank(userId)){
			 subjectLog.setMachineId(machineId); 
			 
		 }
		 subjectLog.setAccessTime(new Timestamp(new java.util.Date().getTime()));
		 baseDao.save(subjectLog);
	 }	
		
		return list;
	}
	
	private List<VLpSubjectDetailApp> getVLpSubjectDetailAppBySubjectId(String subjectId,String pageStr ){		
		int page=1;
		String hql = "from VLpSubjectDetailApp where subjectId='"+subjectId+"'";
		List<VLpSubjectDetailApp> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 10, page);
		}
		
		return list;
	}
	//查作品集的评论数
    public int getCommentCount(String gallaryid){
    	String hql = "from LpComment lc where lc.commentType in(0,4) and lc.lpGalary.galaryId='"+gallaryid
 				+"' and lc.lpUser.userId=(select lu.userId from LpUser lu " +
 				"where lc.lpUser.userId=lu.userId and lu.userStatus=0) order by lc.createTime desc";
     int countNumber= baseDao.getCount(hql);
     
     return countNumber;
   }	
    
    public void inserLog(String gallaryId,String userId,String machineId){
    	
    	LpGalaryShowlog showLog=new LpGalaryShowlog();
    	showLog.setDetailId(gallaryId);
    	if(StringUtils.isBlank(userId)){
    		showLog.setMachineId(machineId);
    		
    	}
    	showLog.setShowTime(new Timestamp(new java.util.Date().getTime()));
     }

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
