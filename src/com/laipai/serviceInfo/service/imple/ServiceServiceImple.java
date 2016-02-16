package com.laipai.serviceInfo.service.imple;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.galaryManInfo.dao.IGalleryDao;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.orderInfo.dao.OrderDao;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.serviceInfo.dto.ServiceInfoBean;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.pojo.VLpService;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:29:34
 */
@Transactional
@Service("serviceService")
public class ServiceServiceImple extends BaseServiceImpl implements IServiceService{
	@Resource(name="serviceDao")
	private ServiceDao serviceDao;
	@Resource(name=IUserManInfoDao.IUSERMANINFODAO_NAME)
	private IUserManInfoDao  userManInfoDao;
	@Resource(name="orderDao")
	private OrderDao orderDao;
	@Resource(name=IGalleryDao.DAO_NAME)
	private IGalleryDao galleryDao;
	@Resource(name="baseDao")
	private IBaseDao baseDao;

	public List queryAllService(HttpServletRequest request) {
	     List<LpService> list=new ArrayList<LpService>();
	 //    String hql="from LpService order by createTime desc";
	     String hql="from VLpService order by createTime desc";
	     try {
			list=querylistForPage(request, hql, 20);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	     return list;
	}
	
	@Override
	public LpService queryById(String serviceId) {
	 	LpService service=serviceDao.queryService(serviceId);
	 	if(service ==null){
	 		return null;
	 	}
	 	List<LpStyle> style=service.getLpServiceDetail().getLpStyle();
		for(LpStyle lpstyle:style){
			lpstyle.getStyleId();
		}
		return service;
	}

	@Override
	public void addService(ServiceInfoBean serviceInfoBean) {
//		UUID uuid = UUID.randomUUID();
		LpService service=getService(null,serviceInfoBean);
		serviceDao.addService(service);
	}

	private LpService getService(String serviceId,ServiceInfoBean serviceInfoBean) {
		/**
		 * 服务设置
		 */
		LpService service= new LpService();
		try {
			if(serviceId != null && !"".equals(serviceId.trim())){
				service.setServiceId(serviceId);
			}		
			service.setServiceName(serviceInfoBean.getServiceName());
			service.setServiceStatus(serviceInfoBean.isServiceStatus());
			LpUser user=userManInfoDao.queryUserByName(serviceInfoBean.getCameraName());
			service.setLpUser(user);
			if(serviceInfoBean.getCreateTime()!=null){
				service.setCreateTime(serviceInfoBean.getCreateTime());
			}else{
				Date date=new Date();
				Timestamp ts=new Timestamp(date.getTime());
				service.setCreateTime(ts);
			}
			System.out.println("--------------服务详情设置");
			/**
			 * 服务详情设置
			 */
			LpServiceDetail serviceDetail=new LpServiceDetail();
			String styleId[]=serviceInfoBean.getStyleId();
			List<LpStyle> list=getStyle(user.getUserId());
			for(int i=0;i<styleId.length;i++){
				for(LpStyle style:list){
					if(styleId[i].equals(style.getStyleId())){
						serviceDetail.getLpStyle().add(style);
						break;
					}
				}
			}
			if(serviceInfoBean.getDetailId()!=null){
				serviceDetail.setDetailId(serviceInfoBean.getDetailId());
			}
			serviceDetail.setPrice(serviceInfoBean.getPrice()/100);
			serviceDetail.setPriceUnit(serviceInfoBean.getPriceUnit());
			serviceDetail.setPictureNum(serviceInfoBean.getPicture_num());
			serviceDetail.setTruingNum(serviceInfoBean.getTruing_num());
			serviceDetail.setShootTime(serviceInfoBean.getShoot_time());
			serviceDetail.setInstructions(serviceInfoBean.getInstructions());
			serviceDetail.setClothes(serviceInfoBean.getClothes());
			serviceDetail.setFacepaint(serviceInfoBean.getFacepaint());
			serviceDetail.setLpService(service);
			serviceDetail.setInstructions(serviceInfoBean.getInstructions());
			service.setLpServiceDetail(serviceDetail);
			return service;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteById(String serviceId_All) {
		String[] serviceId=serviceId_All.split(";");
		for(int i=0;i<serviceId.length;i++){
			LpService service=queryById(serviceId[i]);
			if(service ==null){
				continue;
			}
			//更新作品集
			List<LpGalary> galaryList=new ArrayList<LpGalary>(service.getLpGalaries());
			for(LpGalary galary:galaryList){
				galary.setLpService(null);
				galleryDao.update(galary);
			}
			//更新订单
			List<LpOrder> orderList=new ArrayList<LpOrder>(service.getLpOrders());
			for(LpOrder order:orderList){
				order.setLpService(null);
				orderDao.updateOrder(order);
			}
			serviceDao.deleteService(service);
 		}
	}

	@Override
	public void modifyService(ServiceInfoBean serviceInfoBean) {
		LpService service=getService(serviceInfoBean.getServiceId(),serviceInfoBean);
		serviceDao.modifyService(service);
	}

	@Override
	public void on_off_Service(String serviceId,String status) {
		LpService service=queryById(serviceId);
		if(status=="on"&&status.equals("on")){
			service.setServiceStatus(true);
		}else{
			service.setServiceStatus(false);
		}
		serviceDao.on_off_Service(service);
	}

	@Override
	public List<LpStyle> getStyle(String userId) {
		return serviceDao.getStyle(userId);
	}

	@Override
	public List<VLpService> queryServiceByUserId(HttpServletRequest request, String userId) {
//		String hql=" from LpService ls where ls.lpUser.userId='"+userId+"'";
		String hql="from VLpService where userId='"+userId+"'";
		try {
			List<VLpService> list=querylistForPage(request, hql, 10);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public LpUser queryUserByName(String cameraName) {
		try {
			LpUser user=serviceDao.queryUserByName(cameraName);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LpUser> queryAllCamera() {
		String hql="from LpUser where userType='1'";
		try {
			List<LpUser> cameraList=baseDao.queryListObjectAll(hql);
			return cameraList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LpStyle> getUserStyle(String userId) {
		try {
			List<LpStyle> list=baseDao.queryListObjectAll("from LpStyle where styleStatus=1 and createUserId='"+userId+"'");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getCount(String gallaryId) {
		  String hql = "from LpComment  where lpGalary.galaryId='"+gallaryId+"'";  
		     int countNumber= baseDao.getCount(hql);
		     
		     return countNumber;
		
	}

}
