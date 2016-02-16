package com.laipai.userManInfo.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.LpHeadShowGalaryBean;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.userManInfo.dto.LikeGalaryBean;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.dto.PersonGalleryBean;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.pojo.VLpLike;
/***
 * 

 * @Description:查询用户喜欢的作品集
 * 
 * @author:lxd

 * @time:2015-1-11 下午4:38:55
 */
@NotLogin
@Service("QueryUserLikeImpl")
public class QueryUserLikeImpl implements RequestExecutor {
    
	//private IGalleryService galleryService;
	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryservice;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		
		JSONObject json = parameters.getJson();
		//获取服务器域名
		String serverName = json.getString("serverName");
		String puserId=JSONTools.getString(json,"userId");
		String page=JSONTools.getString(json,"page");
		if(puserId==null||"".equals(puserId)){			
			return JSONTools.newAPIResult(1, "参数不能为空");	
		}
		
		List<LpGalary> gallerys=new ArrayList<LpGalary>();
		//List<LpLike> likes= baseDao.queryListObjectAllForPage(hql, 10, page)
		List<VLpLike> likes = this.getfanslist(puserId, page);
		if(likes!=null&&likes.size()>0){
			for(VLpLike like:likes){
				String gallaryId=like.getGallaryId();
				LpGalary gallery=(LpGalary) baseDao.getObjectById(LpGalary.class, gallaryId);
				gallerys.add(gallery);				
			}	
		}
		List<LikeGalaryBean> beanList = new ArrayList<LikeGalaryBean>();
		if(gallerys!=null&&gallerys.size()>0){
			for(LpGalary gallery:gallerys){
				LikeGalaryBean galleryBean =new LikeGalaryBean();
				LpUser user = gallery.getLpUser();
				BeanUtils.copyProperties(gallery, galleryBean);
				
				/*if(galleryBean.getGalaryCover().startsWith("/upload")){
					galleryBean.setGalaryCover(serverName+galleryBean.getGalaryCover());
					}*/
				Integer controlSource= gallery.getControlSource();
			
				 if(controlSource==1){
					//显示手工数据
					  LpGalaryExtend extend= galleryservice.getExtendbyGalleryId(gallery.getGalaryId());
					  if(extend!=null){
					  gallery.setLikeNumber(extend.getLikeNumber());
					  gallery.setViewNumber(extend.getViewNumber());
					  }
					 
				 }
			 
			/*	//用户类型
				galleryBean.setUserType(user.getUserType());*/
				//价格
				LpService service= gallery.getLpService();
				if(service!=null){
					LpServiceDetail detail = gallery.getLpService().getLpServiceDetail();
					galleryBean.setPrice(((double)detail.getPrice())/100);
				}else{
					 galleryBean.setPrice(0);
				}
				 String sql = " select  lst.style_name "
						   +" from lp_style lst "
						   +" left join lp_service_style lss on lss.style_id = lst.style_id "
						   +" left join lp_service_detail lsd on lsd.detail_id = lss.detail_id "
						   +" left join lp_galary lgg on lgg.service_id = lsd.service_id "
						   +" where lgg.galary_id ='"+gallery.getGalaryId()+"' "
						   +" LIMIT 1 ";
				 List list = baseDao.querySqlObject(sql);
				 if(list !=null && !list.isEmpty()){
					 Map map =  (Map) list.get(0);
					 String styleName = (String) map.get("style_name");
					 galleryBean.setStyleName(styleName);
				 }else{
					 galleryBean.setStyleName("");
				 }
				//摄影师登录Id
				 if(user!=null){
				galleryBean.setUserId(user.getUserId());
				 }
				//摄影师昵称
				galleryBean.setNickName(StringUtils.isBlank(user.getNickName())?"":user.getNickName());
				//摄影师城市
				 LpCity city= user.getLpCity();
				 galleryBean.setUserName(user.getUserName());
				 galleryBean.setUserType(user.getUserType());
				if(city!=null){
					String cityName=city.getCityName();
					galleryBean.setCityName(cityName);
				}
				//摄影师头像
//				if(StringUtils.isNotBlank(user.getHeadImage())){
//					if(user.getHeadImage().contains("/upload/lpUserImg")){
//					galleryBean.setHeadImage(serverName + user.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//					}
//				}
				galleryBean.setHeadImage(ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
				//修改图片路径,加上服务器域名,并把实际路径替换为虚拟路径
//				if(galleryBean.getGalaryCover().startsWith("/upload")){
//					galleryBean.setGalaryCover(serverName+galleryBean.getGalaryCover());
//				}else{
//				galleryBean.setGalaryCover(serverName + galleryBean.getGalaryCover().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+SimpleImage.LP_GALLERY_IMGURL));
//				}
				galleryBean.setGalaryCover(ImgUtil.getImgUrl(galleryBean.getGalaryCover(),parameters.getPicType()));
				beanList.add(galleryBean);
			}
	}
		return beanList;
	  
 }
	
	public List<VLpLike> getfanslist(String userId,String pageStr){
		int page=1;
		String hql=" from VLpLike ll where ll.userId="+"'"+userId+"' and ll.likeType =0 and ll.likeStatus=0 order by likeTime DESC";
		List<VLpLike> list = null;
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
