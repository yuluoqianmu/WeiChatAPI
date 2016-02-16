package com.laipai.galaryManInfo.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.bean.NewIntroducedBean;
import com.laipai.galaryManInfo.dao.IGalleryDao;
import com.laipai.galaryManInfo.dto.LpHeadShowGalaryBean;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpNewintroduce;
import com.laipai.galaryManInfo.pojo.VLpGalary;
import com.laipai.galaryManInfo.pojo.VLpGalaryAppShow;
import com.laipai.galaryManInfo.pojo.VLpGalaryBackinfo;
import com.laipai.img.ImgUtil;
import com.laipai.logs.pojo.LpGalaryLog;
import com.laipai.logs.pojo.LpGalaryShowlog;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.pojo.LpServiceStyle;
import com.laipai.subject.pojo.Subject;
import com.laipai.userManInfo.action.UserManageAction;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.lyz.service.CommentService;
import com.lyz.service.GallaryService;
import com.lyz.service.LikeService;
import com.lyz.service.MessageService;
import com.lyz.util.MD5Generator;

/**
 * 首页作品集展示
 * 
 * @author zhh
 */
@NotLogin
@Service("GalleryShowExecutorImpl")
public class GalleryShowExecutorImpl implements RequestExecutor {
	
	private static Logger logger = Logger.getLogger(GalleryShowExecutorImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IGalleryDao.DAO_NAME)
	private IGalleryDao galleryDao;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		long startTime = System.currentTimeMillis();
		JSONObject json = parameters.getJson();
		JSONObject baseJson = new JSONObject();
		//登录用户的id
		String userId = json.getString("userId");
		LpUser user  = null;
		//获取服务器域名
		String serverName = json.getString("serverName");
		String cityId = json.getString("cityId");
		String styleId = json.getString("styleId");
		String isSearch=JSONTools.getString(json,"isSearch");
		String page = json.getString("page");
		String machineId=JSONTools.getString(json,"imei");
		int osType = json.getInt("osType");
		
		List<VLpGalaryAppShow> galaryList = getAllBypage(parameters,serverName,cityId,styleId,page,userId);
		 //如果不是搜索
		if(!"TRUE".equalsIgnoreCase(isSearch)){
			
			if(galaryList!=null&&galaryList.size()>0 && StringUtils.isEmpty(cityId) && StringUtils.isEmpty(styleId)){
				this.insertSubject(parameters, galaryList, serverName,page);
			}
			
		}
		
		if(StringUtils.isNotEmpty(styleId)){
			 LpStyle style= (LpStyle) baseDao.getObjectById(LpStyle.class, styleId);
			 String styleName="";
			 if(style!=null){				 
				 styleName=style.getStyleName();
			 }
			 if(galaryList!=null&&galaryList.size()>0){
				  for(int i=0;i<galaryList.size();i++){
					  VLpGalaryAppShow  galary=  galaryList.get(i);
					  galary.setStyleName(styleName);
					 
				  }
				 
			 }			 
		 }
		 
		 if(galaryList!=null&&galaryList.size()>0){
			  for(int i=0;i<galaryList.size();i++){
				  VLpGalaryAppShow  galary=  galaryList.get(i);
				  String galaryId=galary.getGalaryId();
				  if(StringUtils.isNotBlank(galaryId)){
				  int countNumber= this.getCommentCount(galaryId);
				  galary.setCommentNumber(countNumber);
				  if(galary.getHeadImage()!=null){
					  galary.setHeadImage(ImgUtil.getImgUrl(galary.getHeadImage(), "webp", 150));					  
				  }
//				  	inserLog(galary.getGalaryId(), userId, machineId);
				  }
			  }
			 
		 } 
		 
		//作品集
		baseJson.put("lpGalary", JSONConvertUtil.allObjectToJSON(galaryList));
		//新晋摄影师介绍
		/*ios 0.9版本有问题，屏蔽*/
		if(osType==1 && "1.0".compareTo(parameters.getVersion())>0){
			
		}else{
			List<LpNewintroduce> list = baseDao.queryListObjectByTopNum("from LpNewintroduce where status=1", 1); 
			if(list !=null && !list.isEmpty()){
				LpNewintroduce introduce = list.get(0);
				NewIntroducedBean inroduceBean = new NewIntroducedBean();
				BeanUtils.copyProperties(introduce, inroduceBean);
				//修改图片路径,加上服务器域名,并把实际路径替换为虚拟路径
				inroduceBean.setImgUrl(ImgUtil.getImgUrl(inroduceBean.getImgUrl(),parameters.getPicType()));
				inroduceBean.setContent(ImgUtil.getImgUrl(inroduceBean.getContent(),parameters.getPicType()));
				//新建摄影师介绍
				baseJson.put("newIntroduce", JSONObject.fromObject(inroduceBean));
			}
		}
		//用户类型 0普通用户  1摄影师 (因普通用户和摄影师的显示界面不一样)
		if(StringUtils.isNotBlank(userId)){//已登录
			user = (LpUser) baseDao.getObjectById(LpUser.class, userId);
			if(user !=null){//用户id不存在，则以普通用户身份登录
				baseJson.put("userType", user.getUserType());  
				//消息数量(messageNumber)
				try {
//					int messageNumber = baseDao.getCount("from LpMessageDetail where messageStatus=0 and recieveUserId='"+userId+"'");
					int messageNumber = MessageService.getMessageCount(userId);
					baseJson.put("messageNumber", messageNumber);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				baseJson.put("userType", 0);
				baseJson.put("messageNumber", 0);
			}
		}else{//未登录
			baseJson.put("userType", 0);
			baseJson.put("messageNumber", 0);
		}
		//查询城市
		String citySql =" SELECT "
						+" lc.city_id as cityId, "
						+" lc.city_name as cityName, "
						+" lc.city_location as cityLocation "
						+" FROM "
						+" lp_city lc "
						+" where 1=1 "
						+" and lc.city_status=1 " 
						+" and lc.is_true_delete=1";
		if(StringUtils.isNotEmpty(userId) && user !=null){
			citySql += " union "
					+" SELECT "
					+" lc.city_id as cityId, "
					+" lc.city_name as cityName, "
					+" lc.city_location as cityLocation "
					+" FROM "
					+" lp_city lc "
					+" left join lp_user lu on lu.city_id=lc.city_id "
					+" where 1=1 "
					+" and lu.user_id='"+userId+"' ";
		}
		citySql = " select a.cityId,a.cityName from ("+citySql+") a order by a.cityLocation ";
		List cityList = baseDao.querySqlObject(citySql);
		baseJson.put("city", JSONConvertUtil.allObjectToJSON(cityList));
		//查询风格
		List styleList = baseDao.querySqlObject("select style_id styleId,style_name styleName from lp_style where is_online=1 and is_true_delete=1");
		baseJson.put("style", JSONConvertUtil.allObjectToJSON(styleList));
		baseJson.put("page", page); //app返回的页数
		long endTime = System.currentTimeMillis();

		return baseJson;
	}
	
	/**
	 * 查询作品集
	 * 使用视图v_lp_galary_app_show查询
	 * 按分页方式查询,并区分先后顺序
	 * */
	public List<VLpGalaryAppShow> getAllBypage(BaseRequestParameters parameters,String serverName,String cityId,String styleId,String pageStr,String userId) {
		String serviceId = "";
		String hql = "from VLpGalaryAppShow where 1=1 and galary_status=0 ";
		if(StringUtils.isNotEmpty(cityId)){
			cityId = cityId.replace(",", "','");
			cityId = "'" + cityId + "'";
			hql += " and cityId in (" +cityId+")";
		}
		if(StringUtils.isNotEmpty(styleId)){
			styleId = styleId.replace(",", "','");
			styleId = "'" + styleId + "'";
						
			List<com.lyz.db.bean.LpGalary> gList = GallaryService.getGalaryIdList(styleId);
			/*作品集不存在这种拍摄风格的，直接返回空*/
			if(gList == null || gList.size() == 0){
				List<VLpGalaryAppShow> newList = new ArrayList<VLpGalaryAppShow>();
				return newList;
			}
			StringBuffer sId = new StringBuffer("");
			for(int i=0; i<gList.size(); i++){
				
				sId.append("'").append(gList.get(i).getGalaryId()).append("'");
				/*最后一个不加逗号*/
				if(i != gList.size()-1){
					sId.append(",");
				}
			}
			hql = hql+" and galaryId in ("+sId+")";
		}//end if StringUtils.isNotEmpty(styleId)
		hql +=" order by galaryScores DESC";
		List<VLpGalaryAppShow> controllist=null;
		int page = 1;
		try {
			List<VLpGalaryAppShow> list = null;
			if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
				list = this.baseDao.queryListObjectAll(hql);
			}else{
				page = Integer.parseInt(pageStr);
				list = this.baseDao.queryListObjectAllForPage(hql, 10, page);
			}
			
			/*如果不进行任何筛选，则加入人工控制的逻辑*/
			if(StringUtils.isEmpty(styleId) && StringUtils.isEmpty(cityId)){
				//该页的起始位置
				int begin =(page-1)*10+1;
				//该页的结束位置
				int end=page*10;
				String controlHql="from VLpGalaryAppShow where indexControl=1 and galary_status=0 ";
				if(StringUtils.isNotEmpty(cityId)){
					controlHql += " and cityId in (" +cityId+") ";
				}
				if(StringUtils.isNotEmpty(serviceId)){
					controlHql += " and serviceId in ("+serviceId+") ";
				}
				controlHql += " and galaryIndex between " + begin + " and " + end + " order by galaryIndex ";
				controllist=baseDao.queryListObjectAll(controlHql);
				//去除list中手工排序的作品集
				if(list!=null&&list.size()>0){								
					 Iterator<VLpGalaryAppShow> sourceIt=list.iterator();
				     while(sourceIt.hasNext()){
				    	 VLpGalaryAppShow tmpSharedBoardSmsWrapper=sourceIt.next();
				    	 if(tmpSharedBoardSmsWrapper.getIndexControl()==1){
				    		 sourceIt.remove(); 
				    	 }
				    }
				}			
				 //把[begin,end]的手工数据插入到list中
				if(controllist!=null&&controllist.size()>0){								
					for(VLpGalaryAppShow gallery:controllist){
						//手工
						int index=gallery.getGalaryIndex()-begin+1;
						if(index < 1){
							index = 1;
						}
						if(index-1<list.size()){
						list.add(index-1, gallery);
						}else{
							list.add(gallery);
							
						}
					}		
				}
			}
			
			
			
			List<VLpGalaryAppShow> newList = new ArrayList<VLpGalaryAppShow>();
			for(int i=0;i<list.size();i++){
				VLpGalaryAppShow  galary= list.get(i);
				galary.setGalaryCover(ImgUtil.getImgUrl(galary.getGalaryCover(),parameters.getPicType()));
				galary.setHeadImage(ImgUtil.getImgUrl(galary.getHeadImage(),parameters.getPicType()));
				
//				int likeNum = baseDao.getCount("from LpLike where lpGalary.galaryId='"+galary.getGalaryId()+"' and lpUser.userId='"+userId+"' and likeStatus=0 and likeType=0");
				int likeNum = LikeService.getLikeCount(galary.getGalaryId(), userId);
				if(likeNum >0){
					galary.setIsLike(0); //赞过
				}else{
					galary.setIsLike(1); //没有赞过
				}
				/*不保存是否赞过，默认都是没有赞过*/
//				galary.setIsLike(1); //没有赞过
				newList.add(galary);
			}
			return newList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//查作品集的评论数
    public int getCommentCount(String gallaryid){
//    	 String hql = "from LpComment lc where lc.commentType in(0,4) and lc.lpGalary.galaryId='"+gallaryid
// 				+"' and lc.lpUser.userId=(select lu.userId from LpUser lu " +
// 				"where lc.lpUser.userId=lu.userId and lu.userStatus=0) order by lc.createTime desc";
//     int countNumber= baseDao.getCount(hql);     
//     return countNumber;
    	
    	return CommentService.getCommentCount(gallaryid);
   }
    /**
     * 
    
     * @Description:插入专题
    
     * @param list
    
     * void
    
     * @exception:
    
     * @author: lxd
    
     * @time:2015-3-20 下午6:55:45
     */
    public void insertSubject(BaseRequestParameters parameters, List<VLpGalaryAppShow> list,String serverName,String pageStr){
    	
    	int page = 1;
    	
    	if(StringUtils.isNotBlank(pageStr)){
    		
    		page = Integer.parseInt(pageStr);
    		
    	}   	
    	//该页的起始位置
		int begin =(page-1)*10+1;
		//该页的结束位置
		int end=page*10;
		
		String controlHql="from Subject where  index_location between " + begin + " and " + end + " and subject_status=1 order by index_location";
		List<Subject> subjectList=new ArrayList<Subject>();
		subjectList=baseDao.queryListObjectAll(controlHql);
		 //把[begin,end]的手工数据插入到list中
		if(subjectList!=null&&subjectList.size()>0){								
			for(Subject subject:subjectList){
				//手工
				String subjectId=subject.getSubject_id();
				int index=subject.getIndex_location()-begin+1;
				VLpGalaryAppShow search =new VLpGalaryAppShow();
				search.setSubjectId(subjectId);
				search.setSubjectCover(ImgUtil.getImgUrl(subject.getSubject_img(),parameters.getPicType()));
				String hql = "from VLpSubjectDetailApp where subjectId='"+subjectId+"'";
				int gallaryNum= baseDao.getCount(hql);
				search.setGallaryNum(gallaryNum);
				search.setDiscoverName(subject.getSubject_name());
				search.setIsSubject(1);
				if(index < 1){
					index = 1;
				}
				if(index-1<list.size()){
				list.add(index-1, search);
				}else{
					
					list.add(search);
				}
			}		
		}
    	
    	
    }
    /**
     * 
    
     * @Description:计算专题的总赞数
    
     * @param subjectId
     * @return
    
     * String
    
     * @exception:
    
     * @author: lxd
    
     * @time:2015-3-20 下午7:39:15
     */
   
    public String getlikeNumber(String subjectId){
		//重新计算点赞数
		String likeNum = "0";
		try {
			String sql  = "select ifnull(sum(a.like_number),0) like_number from ( "
						 +" SELECT sum(ifnull(lge.like_number,0))  like_number "  
						 +" FROM  lp_galary lg "
						 +" left join  lp_galary_extend lge on lge.galary_id = lg.galary_id "  
						+"  where lg.control_source = 1 "
						+" and lg.galary_status=0 "
						+" and lg.status=0 "
						+"  and lge.extend_id is not null "
						+"  and lg.galary_id in (SELECT lp_subject_detail.gallery_id FROM lp_subject_detail where lp_subject_detail.subject_id ='"+subjectId+"') "
						+" union all "
						+"  SELECT sum(ifnull(lg.like_number,0))  like_number "
						+"  FROM  lp_galary lg    "
						+"  where  lg.galary_status=0 "
						+" and lg.status=0 "
						+" and  lg.control_source = 0 "
						+"  and  lg.galary_id in (SELECT lp_subject_detail.gallery_id FROM lp_subject_detail where lp_subject_detail.subject_id ='"+subjectId+"') "
						+" ) a ";
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
    	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return likeNum;
    }
    
    
    public void inserLog(String gallaryId,String userId,String machineId){
    	
    	LpGalaryShowlog showLog=new LpGalaryShowlog();
    	showLog.setDetailId(gallaryId);
    	if(StringUtils.isBlank(userId)){
    		showLog.setMachineId(machineId);
    		
    	}
    	showLog.setShowTime(new Timestamp(new java.util.Date().getTime()));
    	baseDao.save(showLog);
     }

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String cityId = json.getString("cityId");
		String styleId = json.getString("styleId");
		String isSearch=JSONTools.getString(json,"isSearch");
		String page = json.getString("page");
		int osType = json.getInt("osType");
		
		StringBuilder builder = new StringBuilder();
		builder.append("cityId=").append(cityId).append("&")
		.append("styleId=").append(styleId).append("&")
		.append("isSearch=").append(isSearch).append("&")
		.append("page=").append(page).append("&")
		.append("osType=").append(osType).append("&")
		.append("ver=").append(parameters.getVersion());
		
		return "home:"+MD5Generator.MD5(builder.toString());
	}
    	
    } 


