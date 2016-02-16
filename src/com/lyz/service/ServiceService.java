package com.lyz.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.laipai.operationManage.pojo.LpCity;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.ServiceListResp;
import com.lyz.api.bean.ShowServiceDetailResp;
import com.lyz.db.IBaseDao;
import com.lyz.db.bean.LpPayOrder;
import com.lyz.db.bean.LpMergedService;
import com.lyz.db.bean.LpServiceDetail;
import com.lyz.db.bean.LpUser;
import com.lyz.db.dao.BaseDaoImpl;

public class ServiceService {
	
	private static final Logger logger = Logger.getLogger(ServiceService.class);
	
	private static IBaseDao<LpMergedService> serviceDao = new BaseDaoImpl<LpMergedService>();
	
	private static IBaseDao<LpServiceDetail> detailDao = new BaseDaoImpl<LpServiceDetail>();
	
	private static IBaseDao<LpPayOrder> orderDao = new BaseDaoImpl<LpPayOrder>();
	
	private static IBaseDao<LpUser>  userDao = new BaseDaoImpl<LpUser>();
	
	private static IBaseDao<LpCity> cityDao = new BaseDaoImpl<LpCity>();
	
	/**
	 * 获取服务数量
	 * @param userId
	 * @return
	 */
	public static int getServiceCount(String userId){
		
		String sql = "select count(*) from lp_service where service_status=1 and user_id=?";
		
		return serviceDao.count(sql, userId);
	}
	/**
	 * 获取服务列表
	 * @param uid
	 * @return
	 */
	public static List<LpMergedService> getServiceList2(String uid){
		
		if(uid == null){
			
			return null;
		}
		
		String serviceSql = "select ser.service_id service_id,service_name,price,price_unit " +
				"from lp_service ser,lp_service_detail det " +
				"where ser.service_id=det.service_id and service_status=1 and user_id=?";
		
		return serviceDao.queryObjects(serviceSql, LpMergedService.class,uid);

	}
	
	/**
	 * 获取服务列表
	 * @param uid
	 * @return
	 */
	public static List<ServiceListResp> getServiceList(String uid){
		
		if(uid == null){
			
			return null;
		}
		
		String serviceSql = "select ser.service_id service_id,service_name,price " +
				"from lp_service ser,lp_service_detail det " +
				"where ser.service_id=det.service_id and service_status=1 and user_id=?";
		
		List<LpMergedService> serviceList = serviceDao.queryObjects(serviceSql, LpMergedService.class,uid);
		if(serviceList == null || serviceList.isEmpty()){
			return null;
		}
		
		/*遍历列表，组装对象*/
		List<ServiceListResp> list = new ArrayList<ServiceListResp>();
		for(LpMergedService ser : serviceList){
			/*获取服务的订单数*/
			String countSql = "select count(*) from lp_pay_order where service_id=?";
			int count = orderDao.count(countSql,ser.getServiceId());
			ServiceListResp resp = new ServiceListResp(ser.getServiceId(),ser.getServiceName(), ((double)ser.getPrice()/100)+"", count+"");
			/*加入列表*/
			list.add(resp);
		}
		
		
		
		return list;
	}
	
	public static ShowServiceDetailResp getServiceShowDetail(String serviceId, String cameraId){
		
		if(serviceId == null){
			return null;
		}
		
		String serviceSql = "select user_id,service_name,price,shoot_time,picture_num,truing_num,clothes,facepaint,instructions,price_unit " +
				"from lp_service ser,lp_service_detail det " +
				"where ser.service_id=det.service_id and ser.service_id=?";
		LpMergedService service = null;
		
		/*如果没有指定服务，默认私人定制服务*/
		if("1111111111081".equals(serviceId)){
			service = new LpMergedService();
			service.setUserId(cameraId);
			service.setServiceName("私人定制");
			service.setPrice(0);
			
		}else{
			service = serviceDao.queryObject(serviceSql, LpMergedService.class, serviceId);
		}
		
		
		
		if(service == null){
			logger.error("service no exist, serviceId="+serviceId);
			return null;
		}
		
		/*查询用户信息*/
		String userSql = "select nick_name,head_image,city_id,mobile from lp_user " +
				"where user_id=?";
		/*获取摄影师信息*/
		LpUser user = userDao.queryObject(userSql, LpUser.class, service.getUserId());		
		if(user == null){
			
			logger.error("uesr no exist, userId="+service.getUserId());
			return null;
		}		
		/*获取城市名称*/
		String citySql = "select city_name from lp_city where city_id=?";
		LpCity city = cityDao.queryObject(citySql, LpCity.class, user.getCityId());
		/*获取服务的订单数*/
		String countSql = "select count(*) from lp_pay_order where service_id=?";
		int count = orderDao.count(countSql,serviceId);
		/**/
		
		ShowServiceDetailResp resp = new ShowServiceDetailResp();
		resp.setCameraCity(city.getCityName());
		resp.setCameraHeadImg(user.getHeadImage());
		resp.setCameraId(service.getUserId());
		resp.setCameraName(user.getNickName());
		resp.setCameraPhone(user.getMobile());
		resp.setOrderNum(count+"");
		resp.setServiceId(serviceId);
		resp.setServiceName(service.getServiceName());
		resp.setServicePrice(((double)service.getPrice())/100);
		resp.setShootTime(service.getShootTime());
		resp.setPicNum(service.getPictureNum());
		resp.setTruingNum(service.getTruingNum());
		resp.setClothes(service.getClothes());
		resp.setFacePaint(service.getFacepaint());
		resp.setDesc(service.getInstructions());
		resp.setPriceUnit(service.getPriceUnit());
		resp.setResult(CodeUtil.SUCCESS);
		
		return resp;
		
	}

	
	/**
	 * 获取服务详情
	 * @param serviceId
	 * @return
	 */
	public static LpServiceDetail getServiceDetail(String serviceId){
		
		if(serviceId == null){
			return null;
		}
		
		String sql = "select price,shoot_time,picture_num,truing_num,clothes,facepaint,instructions,price_unit " +
				"from lp_service_detail where service_id=?";
		
		LpServiceDetail detail = detailDao.queryObject(sql, LpServiceDetail.class, serviceId);
		
		return detail;
	}
	
	public static void main(String args[]){
		
//		List<ServiceListResp> list = ServiceService.getServiceList("8a2a76634c5af695014c5b3f9f570043");
//		
//		System.out.println(list.size());
		
//		ServiceService.getServiceShowDetail("8a2a76634c570cb9014c570f8e890003","");
//		List<LpMergedService> list = ServiceService.getServiceList2("8a2a76634c5af695014c5b3f9f570043");
//		System.out.println(list.size());
		
		ShowServiceDetailResp resp = ServiceService.getServiceShowDetail("8a2a76634c5b7285014c5bfbd26303fa", "8a2a76634c5af695014c5b3f9f570043");
		
		System.out.println(resp.getOrderNum());
	}
}
