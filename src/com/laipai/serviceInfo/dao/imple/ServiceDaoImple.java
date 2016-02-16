package com.laipai.serviceInfo.dao.imple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.img.ImgUtil;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:30:11
 */
@Transactional
@Repository("serviceDao")
public class ServiceDaoImple extends BaseDaoImple implements ServiceDao {
    private static final Logger logger=Logger.getLogger(ServiceDao.class);
    
	@Override
	public void addService(LpService service){
		try {
			this.save(service);
		} catch (Exception e) {
			logger.error("保存服务失败");
			e.printStackTrace();
		}
	}
	
	@Override
	public void modifyService(LpService service) {
		try {
			this.updateObject(service);
		} catch (Exception e) {
			logger.error("更新服务失败");
			e.printStackTrace();
		}
		
	}

	@Override
	public void on_off_Service(LpService service) {
		try {
			this.updateObject(service);
		} catch (Exception e) {
			logger.error("更新服务状态失败");
			e.printStackTrace();
		}
	}

	@Override
	public List<LpStyle> getStyle(String userId) {
		List<LpStyle> list= null;
		String hql= "";
		try {
			if(StringUtils.isNotEmpty(userId)){
				hql="from LpStyle where isTrueDelete !=0 and ((styleStatus=0 and isOnline=1) or (styleStatus=1 and createUserId=?))";
				list= this.queryListObjectByCondition(hql, new String[]{userId});
			}else{
				hql="from LpStyle where isTrueDelete !=0 and styleStatus=0 and isOnline=1";
				list= this.queryListObjectAll(hql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void deleteService(LpService service) {
		try {
			this.delete(service);
		} catch (Exception e) {
			logger.error("删除服务失败");
			e.printStackTrace();
		}
	}

	@Override
	public LpService queryService(String serviceId) {
		LpService service=new LpService();
		try {
			
			service=(LpService) this.getObjectById(LpService.class, serviceId);
			Set<LpGalary> set = service.getLpGalaries();
			for (LpGalary lpGalary : set) {
				lpGalary.setGalaryCover(ImgUtil.getImgUrl(lpGalary.getGalaryCover()));
			}
		} catch (Exception e) {
			logger.error("查询单项服务失败");
			e.printStackTrace();
		}
		return service;
	}

	@Override
	public List<LpGalary> queryGalary(String serviceId) {
		String hql=" from LpGalary lg where lg.lpService.serviceId=? ";
		return this.queryListObjectByCondition(hql, new String[]{serviceId});
	}
	
   /**
    * 根据SQL语句查询集合
    */
	@Override
	public List<LpService> getListBySql(String sqlString, Object... values) {
	       Query query= this.getSession().createSQLQuery(sqlString).addEntity(LpService.class);
	       return query.list(); 
	}

	@Override
	public LpUser queryUserByName(String cameraName) {
		String hql=" from LpUser where userName='"+cameraName+"' and userType in (1,3)";
		List<LpUser> array= this.queryListObjectAll(hql);
		LpUser user=null;
		if(array!=null&&array.size()>0){
			user=array.get(0);
		}
		return user;
	}

	@Override
	public List<LpService> queryListByHql(String hql,String userId) {
		return queryListObjectByCondition(hql, new Object[]{userId,true});
	}



}
