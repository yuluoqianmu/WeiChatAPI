package com.lyz.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laipai.img.ImgUtil;
import com.laipai.operationManage.pojo.LpCity;
import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpGalary;
import com.lyz.db.bean.LpServiceDetail;
import com.lyz.db.bean.LpStyle;
import com.lyz.db.bean.LpUser;
import com.lyz.db.bean.VLpGalaryAppShow;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.util.ClassUtil;

/**
 * 作品集服务
 * @author luyongzhao
 *
 */
public class GallaryService {
	
	private static final Logger logger = Logger.getLogger(GallaryService.class);
	
	private static IBaseDao<LpCity> cityDao = new BaseDaoImpl<LpCity>();
	
	private static final IBaseDao<LpGalary> galaryDao = new BaseDaoImpl<LpGalary>();
	
	private static IBaseDao<LpServiceDetail> detailDao = new BaseDaoImpl<LpServiceDetail>();
	
	private static IBaseDao<LpStyle> styleDao = new BaseDaoImpl<LpStyle>();
	/**
	 * 获取作品集数量
	 * @param userId
	 * @return
	 */
	public static int getGalaryCount(String userId){
		
		String sql = "select count(*) from lp_galary where user_id=? and status=0";
		
		return galaryDao.count(sql, userId);
	}
	
	/**
	 * 获取用户作品
	 * @param userId
	 * @return
	 */
	public static List<LpGalary> getGalaryList4User(String userId, int pageNo, int pageSize){
		
		if(userId == null || "".equals(userId)){
			return null;
		}
		if(pageNo <= 0){
			pageNo = 1;
		}
		int offSize = (pageNo-1)*pageSize;
		String sql = "select galary_id,user_id,service_id,subject_name,galary_desc,galary_cover,view_number,like_number,comment_number,galary_status,galary_index " +
				"from lp_galary " +
				"where status=0 and user_id=? " +
				"order by galary_scores desc " +
				"limit "+offSize+","+pageSize;
		
		logger.info("galary sql:"+sql+"===userId="+userId);
		
		return galaryDao.queryObjects(sql, LpGalary.class, userId);
				
	}
	
	public static List<LpGalary> getGalaryList4User(String userId){
		
		if(userId == null || "".equals(userId)){
			return null;
		}
		String sql = "select galary_id,user_id,service_id,subject_name,galary_desc,galary_cover,view_number,like_number,comment_number,galary_status,galary_index " +
				"from lp_galary " +
				"where status=0 and user_id=? " +
				"order by galary_scores desc ";
		
		logger.info("galary sql:"+sql+"===userId="+userId);
		
		return galaryDao.queryObjects(sql, LpGalary.class, userId);
				
	}
	
//	public static List<LpGalary> getGalaryList(int pageNo, int pageSize){
//		
//		if(userId == null || "".equals(userId)){
//			return null;
//		}
//		int offSize = (pageNo-1)*pageSize;
//		String sql = "select galary_id,user_id,service_id,subject_name,galary_desc,galary_cover,view_number,like_number,comment_number,galary_status,galary_index " +
//				"from lp_galary " +
//				"where status=0 and user_id=? " +
//				"order by galary_scores desc " +
//				"limit "+offSize+","+pageSize;
//		
//		return galaryDao.queryObjects(sql, LpGalary.class, userId);
//				
//	}
	
	public static List<VLpGalaryAppShow> getGalaryShowList(List<LpGalary> galaryList){
		
		if(galaryList == null){
			
			return null;
		}
		
		List<VLpGalaryAppShow> gaList = new ArrayList<VLpGalaryAppShow>();
		
		for(LpGalary galary : galaryList){
			
			VLpGalaryAppShow show = (VLpGalaryAppShow)ClassUtil.copyAttr(VLpGalaryAppShow.class, galary);
			
			/*获取用户信息*/
			LpUser user = UserService.getUserInfo(galary.getUserId());
			if(user != null){
				show.setNickName(user.getNickName());
				show.setHeadImage(ImgUtil.getImgUrl(user.getHeadImage(), "webp", 100));
				/*获取城市信息*/
				String citySql = "select city_name from lp_city where city_id=?";
				LpCity city = cityDao.queryObject(citySql, LpCity.class, user.getCityId());
				
				if(city != null){
					show.setCityName(city.getCityName());
				}
			}
			
			/*获取服务价格*/
			if(galary.getServiceId() != null){
				
				String sql = "select price " +
						"from lp_service_detail where service_id=?";
				
				LpServiceDetail detail = detailDao.queryObject(sql, LpServiceDetail.class, galary.getServiceId());
				if(detail != null){
					show.setPrice(((double)detail.getPrice())/100);
				}
			}
			
			/*获取拍摄风格*/
			String styleSql = "select style_name from lp_style sty, lp_gallery_style gs where gs.style_id=sty.style_id and gs.galary_id=?";
			
			LpStyle style = styleDao.queryObject(styleSql, LpStyle.class, galary.getGalaryId());
			
			if(style != null){
				show.setStyleName(style.getStyleName());
			}
			
			gaList.add(show);
			
		}
		
		return gaList;
	}
	
	
	/**
	 * 获取作品集id列表
	 * @param styleIds
	 * @return
	 */
	public static List<LpGalary> getGalaryIdList(String styleIds){
				
		String sql = "select galary_id from lp_gallery_style where style_id in ("+styleIds+")";
		
		return galaryDao.queryObjects(sql, LpGalary.class);
		
	}
	
	public static void main(String args[]){
//		
//		List<LpGalary> list = GallaryService.getGalaryIdList("8a2a76634c564029014c5679b7820010");
//		System.out.println(list.get(0).getGalaryId());
		
		List<VLpGalaryAppShow> showList = GallaryService.getGalaryShowList(GallaryService.getGalaryList4User("8a2a76634c5af695014c5b3f9f570043"));
		System.out.println(showList.size());
		for(VLpGalaryAppShow show : showList){
			System.out.println(show.getGalaryId()+"\t"+show.getCityName()+"\t"+show.getSubjectName()+"\t"+show.getPrice()+"\t"+show.getHeadImage()+"\t"+show.getStyleName());
		}
	}
}
