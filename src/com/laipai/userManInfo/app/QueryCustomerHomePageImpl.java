package com.laipai.userManInfo.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraManInfo.service.ICameraManService;
import com.laipai.galaryManInfo.dao.IGalleryDao;
import com.laipai.galaryManInfo.dto.LpGalaryBean;
import com.laipai.galaryManInfo.dto.LpHeadShowGalaryBean;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.logs.pojo.LpCamermanLog;
import com.laipai.logs.pojo.LpGalaryShowlog;
import com.laipai.operationManage.dto.LpCityBean;
import com.laipai.operationManage.dto.LpStyleBean;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.orderInfo.dao.OrderDao;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.serviceInfo.dto.ServiceInfoBean;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userManInfo.dto.GeneralUser;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.dto.LpUserShowBean;
import com.laipai.userManInfo.dto.NumberBean;
import com.laipai.userManInfo.dto.PersonGalleryBean;
import com.laipai.userManInfo.dto.PersonServiceBean;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.lyz.api.bean.ServiceListResp;
import com.lyz.db.bean.VLpGalaryAppShow;
import com.lyz.service.CommentService;
import com.lyz.service.FollowService;
import com.lyz.service.GallaryService;
import com.lyz.service.LikeService;
import com.lyz.service.OrderService;
import com.lyz.service.ServiceService;
import com.lyz.util.BaseTypeUtil;
/**
 * 

 * @Description:查询个人主页

 * @author:lxd

 * @time:2015-1-7 下午5:26:23
 */
@NotLogin
@Service("QueryCustomerHomePageImpl")
public class QueryCustomerHomePageImpl implements RequestExecutor {
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService;
	@Autowired
	private IBaseDao baseDao;
	@Resource(name=ICameraManService.SERVICE_NAME)
	private ICameraManService cameraManService;
	@Autowired
	private ServiceDao serviceDao;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryservice;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		//获取服务器域名
	    String serverName = json.getString("serverName");
		JSONObject resulejson = new JSONObject();
		//登陆者Id
		String puserId=JSONTools.getString(json,"userId");
		//被访问用户的Id
		String customerId=JSONTools.getString(json,"customerId");
		String enterType=JSONTools.getString(json,"enterType");
		String machineId=JSONTools.getString(json,"imei");
		if(customerId==null){			
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		Map map=new HashMap();
		LpUser user= userManInfoService.queryUserById(customerId);		
		if(user!=null){ 
			int userType=user.getUserType();
			String userId=user.getUserId();
			LpCity city=user.getLpCity();
//			if(user.getAccountSource()==0){
//				user.setHeadImage(serverName + user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//				}
			user.setHeadImage(ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
			if(userType==0){
				//如果是普通用户要显示的数据
				LpUserShowBean userBean=new LpUserShowBean();
 
				BeanUtils.copyProperties(user, userBean);
				userBean.setNickName2(userBean.getNickName());

				try {
					/*int userOrderNumber=baseDao.getCount("from LpOrder WHERE lpUser.userId='"+userId+"'");*/
//					int userlikeNumber=baseDao.getCount("from VLpLike WHERE userId='"+userId+"' and likeStatus=0 and likeType=0");
					int userlikeNumber = LikeService.getLikeCount4User(userId);
//					int userfollowNumber=baseDao.getCount("from LpFollowView WHERE userId='"+userId+"'");
					int userfollowNumber = FollowService.getFollowCount(userId);
					//非游客登录
					if(!"".equals(puserId)){
					List<LpFollow> listfollow= baseDao.queryListObjectAll("from LpFollow where lpUserByUserId.userId='"+puserId+"' and lpUserByCameraId.userId='"+userId+"'");
					if(listfollow!=null&&listfollow.size()>0){
						LpFollow follow=listfollow.get(0);				
						userBean.setFollowId(follow.getFollowId());
					}
					}
					userBean.setUserlikeNumber(userlikeNumber);
					userBean.setFollowNumber(userfollowNumber);
					map.put("user", userBean);	
					List<ServiceListResp> serviceList = ServiceService.getServiceList(userId);
					if(serviceList != null){
						 map.put("services", serviceList);
					 }
				} catch (Exception e) {
					return JSONTools.newAPIResult(1, e.toString());
				}
				resulejson=JSONObject.fromObject(map);
			}else{
				//如果是摄影师要显示的数据
				String page=JSONTools.getString(json,"page");
				LpUserShowBean userBean=new LpUserShowBean();
				BeanUtils.copyProperties(user, userBean);
				/*摄影师要指定角色*/
				if(userType == 1){
					userBean.setNickName2("摄影师:"+userBean.getNickName());
				}else{
					userBean.setNickName2(userBean.getNickName());
				}
				try {
//					int userlikeNumber=baseDao.getCount("from VLpLike WHERE userId='"+userId+"' and likeStatus=0 and likeType=0");
					int userlikeNumber = LikeService.getLikeCount4User(userId);
//					int userfollowNumber=baseDao.getCount("from LpFollowView WHERE userId='"+userId+"'");
					int userfollowNumber = FollowService.getFollowCount(userId);
//					int userfansNumber=baseDao.getCount("from LpFollowView WHERE cameraId='"+userId+"'");
					int userfansNumber = FollowService.getFansCount(userId);
//					int cameramanOrderNumber=baseDao.getCount("from VLpOrder WHERE cameraId='"+userId+"'");
					int cameramanOrderNumber = OrderService.getOrderCount4Camera(userId);
//					int serviceNumber=baseDao.getCount("from LpService WHERE lpUser.userId='"+userId+"' and serviceStatus=0");
					int serviceNumber = ServiceService.getServiceCount(userId);
//					int galleryNumber=baseDao.getCount("from LpGalary WHERE lpUser.userId='"+userId+"' and status=0 ");
					int galleryNumber= GallaryService.getGalaryCount(userId);
					int payCmtNum = CommentService.getPayCmtCount(userId);
					if(!"".equals(puserId)){
					List<LpFollow> listfollow= baseDao.queryListObjectAll("from LpFollow where lpUserByUserId.userId='"+puserId+"' and lpUserByCameraId.userId='"+userId+"'");
					if(listfollow!=null&&listfollow.size()>0){
						LpFollow follow=listfollow.get(0);				
						userBean.setFollowId(follow.getFollowId());
					}
					}
					userBean.setUserlikeNumber(userlikeNumber);
					userBean.setFollowNumber(userfollowNumber);
					userBean.setFansNumber(userfansNumber);
					userBean.setCameramanOrder(cameramanOrderNumber);
					userBean.setServiceNumber(serviceNumber);
					userBean.setGalleryNumber(galleryNumber);
					userBean.setPayCmtNum(payCmtNum);
					if(city!=null){
					userBean.setCity(city.getCityName());
					}
					List<LpStyle> listStyle=cameraManService.getstyleByuser(userId);
					String styles="";
					  if(listStyle!=null&&listStyle.size()>0){
						  for(LpStyle style:listStyle){
							  /*LpStyleBean bean =new LpStyleBean();
								 BeanUtils.copyProperties(style, bean);
								 listStyleBean.add(bean);*/
							  String styleName=style.getStyleName();
							   styles+=styleName+"、";
							 } 
						  
				  styles = styles.substring(0,styles.length()-1); 
					  }
					 
					userBean.setStyles(styles); 
					map.put("user", userBean);
				} catch (Exception e) {
					return JSONTools.newAPIResult(1, e.toString());
				}
				List<ServiceListResp> serviceList = ServiceService.getServiceList(userId);
				 List galaryList = getAllBypage(parameters,serverName, customerId, page);
				 List<VLpGalaryAppShow> newList =new ArrayList<VLpGalaryAppShow>();
				 if(galaryList!=null&&galaryList.size()>0){
					  for(int i=0;i<galaryList.size();i++){
						  VLpGalaryAppShow gallary=(VLpGalaryAppShow) galaryList.get(i);
						  String gallaryId=gallary.getGalaryId();
						  int commentNumber=getCommentCount(gallaryId);
						  gallary.setCommentNumber(commentNumber);
						  
/*						  if(gallary.getGalaryCover().startsWith("/upload")){
							  gallary.setGalaryCover(gallary.getGalaryCover());
							}*/
						  				  
//						  inserLog(gallaryId, userId, machineId);
					  }
					 map.put("GalleryList", galaryList);
				 }else{
					 map.put("GalleryList", newList);
				 }
				 if(serviceList != null){
					 map.put("services", serviceList);
				 }
				 resulejson=JSONObject.fromObject(map);
				 //保存日志
//					if("1".equals(page)){
//					  LpCamermanLog  camermanLog =new LpCamermanLog();
//					  camermanLog.setVistorId(puserId);
//					  camermanLog.setCamermanId(customerId);
//					  camermanLog.setVistedTime(new Timestamp(new java.util.Date().getTime()));
//					   if(enterType!=null){
//					   Integer Type =Integer.parseInt(enterType);
//					   camermanLog.setEnterType(Type);
//					   }
//					 
//					  if(StringUtils.isBlank(puserId)){
//						  camermanLog.setMachineId(machineId);
//						  
//					  }
//					  baseDao.save(camermanLog); 
//					}
			}
		}
		
 
		return resulejson;
	}
  
  private PersonGalleryBean getLpGalaryBean(LpGalary gallery){
	  PersonGalleryBean  lpGalaryBean = new PersonGalleryBean();
	  Integer controlSource= gallery.getControlSource();
	  String galleryId=gallery.getGalaryId();
	    //价格
		 LpService service = gallery.getLpService();
		 if(service!=null){
		 LpServiceDetail detail	=service.getLpServiceDetail();
		 lpGalaryBean.setPrice(((double)detail.getPrice())/100);
		 }
		
		if(controlSource!=null){
	  if(controlSource==1){
		//显示手工数据
		  LpGalaryExtend extend= galleryservice.getExtendbyGalleryId(galleryId);
		  if(extend!=null){
		  gallery.setLikeNumber(extend.getLikeNumber());
		  gallery.setViewNumber(extend.getViewNumber());
		  }
		  
	  }
		}
	  BeanUtils.copyProperties(gallery, lpGalaryBean);
	  
	  return lpGalaryBean;
  }
  private PersonGalleryBean getLpGalaryBean(String serverName, PersonGalleryBean gallery,LpUser user){
	  gallery.setUserName(user.getUserName());
	  gallery.setNickName(user.getNickName());
	  Integer userType=user.getUserType();
	  gallery.setHeadImage(user.getHeadImage());
	  if(userType!=null){
		  gallery.setUserType(userType);  
		  
	  }

	  if(user.getLpCity()!=null){
		  gallery.setCityName(user.getLpCity().getCityName());
		  
	  }
	  return gallery;
  }
  
  
  /**
	 * 查询作品集
	 * 使用视图v_lp_galary_app_show查询
	 * 按分页方式查询,并区分先后顺序
	 * */
	public List getAllBypage(BaseRequestParameters parameters,String serverName,String userId,String pageStr) {
//		String serviceId = "";
//		String hql = "from VLpGalaryAppShow where 1=1 and userId='"+userId+"' order by galaryScores DESC";
		
//		List<VLpGalaryAppShow> controllist=null;
//		int page = 1;
		try {
			List<VLpGalaryAppShow> list = null;
			if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
				list = GallaryService.getGalaryShowList(GallaryService.getGalaryList4User(userId));
			}else{
				list = GallaryService.getGalaryShowList(GallaryService.getGalaryList4User(userId,BaseTypeUtil.getInteger(pageStr, 1),10));
			}
			
			List newList = new ArrayList();
			for(int i=0;i<list.size();i++){
				VLpGalaryAppShow  galary= list.get(i);

				galary.setGalaryCover(ImgUtil.getImgUrl(galary.getGalaryCover(),parameters.getPicType()));
				int likeNum = baseDao.getCount("from LpLike where lpGalary.galaryId='"+galary.getGalaryId()+"' and lpUser.userId='"+userId+"'");
				if(likeNum >0){
					galary.setIsLike(0); //赞过
				}else{
					galary.setIsLike(1); //没有赞过
				}
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
    	 String hql = "from LpComment lc where lc.lpGalary.galaryId='"+gallaryid
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
