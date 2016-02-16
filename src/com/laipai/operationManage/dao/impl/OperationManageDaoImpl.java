package com.laipai.operationManage.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.pool.GetConnectionTimeoutException;
import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.cameraManInfo.pojo.LpCameramanStyle;
import com.laipai.operationManage.dao.IOperationManageDao;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.subject.pojo.Subject;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
/**
 * 
 * @Description:城市与风格管理
 * @author:朱鑫
 * @time:2014-12-26 上午10:27:43
 */
@Transactional
@Repository(IOperationManageDao.DAO_NAME)
public class OperationManageDaoImpl extends BaseDaoImple implements IOperationManageDao {
	 private static final Logger logger=Logger.getLogger(ServiceDao.class);
	 
	 @Resource
	 private SessionFactory sessionFactory;
	 	
	 public Session getCurrentSession(){
	 	return sessionFactory.getCurrentSession();
	 }
	/**
	 * 查找所有城市
	 */
	 public List<LpCity> queryAllCity() {
			List<LpCity> list=new ArrayList<LpCity>();
			try {
				list=this.getSession().createCriteria(LpCity.class).list();
			}catch(RuntimeException re){
				logger.error("select failed",re);
				re.printStackTrace();
				
			}
			return list;
			}
	 
		@Override
		public List<LpCity> getAllCity(int nowPage, int pageSize) {
			Query query = null;
			try {
				query = getSession().createQuery("from LpCity where isTrueDelete = 1 order by cityLocation ");	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			query.setFirstResult((nowPage - 1)*pageSize);
			query.setMaxResults(pageSize);
			List<LpCity> list = list = query.list();
			for (LpCity lpCity : list)
			{
				Integer manCount =0;
				try {
					Object o = getSession().createSQLQuery("select count(user_id) from lp_user where user_type = 1 and city_id = '"+lpCity.getCityId()+"'").uniqueResult();
					manCount = Integer.valueOf(o.toString());	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				lpCity.setCammerManNumber(manCount);
				//this.getCurrentSession().update(lpCity);
			}
			return list;
		}
	 /**
	  * 删除城市
	  */
		public void deleteCity(String cityId) 
		{
			try {
				LpCity lpCity = (LpCity)getCurrentSession().createQuery("from LpCity where cityId = ?").setParameter(0, cityId).uniqueResult();
				int nowLocation = lpCity.getCityLocation();
				this.getSession().createSQLQuery("update lp_city set is_true_delete = 0,city_location = 0 where city_id = ?").setParameter(0, cityId).executeUpdate();
				getCurrentSession().createSQLQuery("update lp_city set city_location = city_location - 1 where city_location > "+nowLocation+"").executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
	/**
	 * 城市的添加
	 */
		public void saveOrUpdateCity(LpCity lpCity)
		{
			List<LpCity> cityList = getCurrentSession().createQuery("from LpCity where isTrueDelete = 1 order by cityLocation desc").list();
			int location = 1;
			if(cityList != null && cityList.size() != 0)
			{
				try {
					location = cityList.get(0).getCityLocation();	
					System.out.println(location);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				location = location + 1;
				lpCity.setCityLocation(location);
			}else
			{
				lpCity.setCityLocation(1);
			}
			
			if(lpCity.getCityStatus() == true)
			{
				lpCity.setOnlineTime(new Timestamp(new Date().getTime()));
				lpCity.setOfflineTime(null);
			}
			if(lpCity.getCityStatus() == false)
			{
				lpCity.setOnlineTime(null);
				lpCity.setOfflineTime(new Timestamp(new Date().getTime()));
			}
			this.save(lpCity);
		} 
		/**
		 * 总页数
		 */
		@Override
		public long getCount(int pageSize, Class clazz) {
			Long count = (Long)getCurrentSession().createQuery("select count(*) from "+clazz.getSimpleName()+"  where isTrueDelete = 1 ").uniqueResult();
			long pageCount = count / pageSize;
			
			if(count % pageSize != 0)
			{
				pageCount ++;
			}
			return pageCount;
		}
		/**
		 * 总记录数
		 */
		@Override
		public long countSum(Class clazz) {
			Long count = (Long)getCurrentSession().createQuery("select count(*) from "+clazz.getSimpleName()+" where isTrueDelete = 1 ").uniqueResult();
			return count;
		}
		/**
		 * 上线状态修改
		 */
		@Override
		public void changeStatus(boolean status, String cityId) {
			LpCity lpCity = (LpCity)getCurrentSession().createQuery("from LpCity where cityId = ?").setParameter(0, cityId).uniqueResult();
			if(status == true)
			{
				try {
					lpCity.setCityStatus(false);
					lpCity.setOfflineTime(new Timestamp(new Date().getTime()));
					this.getCurrentSession().update(lpCity);
					//executeSql("update lp_city set city_status = 0 where city_id = '"+cityId+"'");	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else
			{
				try {
					lpCity.setCityStatus(true);
					lpCity.setOnlineTime(new Timestamp(new Date().getTime()));
					this.getCurrentSession().update(lpCity);
					//executeSql("update lp_city set city_status = 1 where city_id = '"+cityId+"'");	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		@Override
		public List<LpStyle> getAllStyle(int nowPage, int pageSize) {
			Query query = getSession().createQuery("from LpStyle  where isTrueDelete = 1 order by styleLocation ");
			query.setFirstResult((nowPage - 1)*pageSize);
			query.setMaxResults(pageSize);
			List<LpStyle> list = null;
			try {
				list = query.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (LpStyle lpStyle : list)
			{
				Integer manCount =0;
				
				try {
					Object o = getSession().createSQLQuery("select count(user_id) from lp_cameraman_style where style_id ='"+lpStyle.getStyleId()+"'").uniqueResult();
					manCount = Integer.valueOf(o.toString());	
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				lpStyle.setCammerManNumber(manCount);
				this.getCurrentSession().update(lpStyle);
			}
			return list;
		}
		
		
		public List queryAllStyle() {
			String hql="from LpStyle where isTrueDelete=1 and isOnline=1";
			List list=new ArrayList();
			try {
				list=getSession().createQuery(hql).list();
			}catch(RuntimeException re){
				logger.error("select failed",re);
				re.printStackTrace();
				
			}
			return list;
			}
		/**
		 * 删除风格
		 */
		@Override
		public void deleteStyle(String styleId) {
			//this.getSession().createQuery("delete from LpStyle where styleId = ?").setParameter(0, styleId).executeUpdate();
			LpStyle lpStyle =(LpStyle)getCurrentSession().createQuery("from LpStyle where styleId = ?").setParameter(0, styleId).uniqueResult();
			int nowLocation = lpStyle.getStyleLocation();
			this.getSession().createSQLQuery("update lp_style set is_true_delete = 0, style_location = 0 where style_id = ?").setParameter(0, styleId).executeUpdate();
			getCurrentSession().createSQLQuery("update lp_style set style_location = style_location - 1 where style_location > "+nowLocation+"").executeUpdate();
		}
		/**
		 * 增加风格
		 */
		@Override
		public void addStyle(LpStyle lpStyle) {
			List<LpStyle> cityList = getCurrentSession().createQuery("from LpStyle where isTrueDelete = 1 order by styleLocation desc").list();
			int location = 1;
			if(cityList != null && cityList.size() != 0)
			{
				try {
					location = cityList.get(0).getStyleLocation();	
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				location = location + 1;
				lpStyle.setStyleLocation(location);
			}else
			{
				lpStyle.setStyleLocation(1);
			}
			if(lpStyle.getIsOnline() == 1)
			{
				lpStyle.setOnlineTime(new Timestamp(new Date().getTime()));
			}else
			{
				lpStyle.setOnlineTime(null);
			}
			this.save(lpStyle);
		}
		/**
		 * 根据id发现风格
		 */
		@Override
		public LpStyle findTheStyle(String styleId) {
			return (LpStyle)this.getSession().createQuery("from LpStyle where styleId=?").setParameter(0, styleId).uniqueResult();
		}
		/**
		 * 修改风格
		 */
		@Override
		public void updateStyle(LpStyle lpStyle) {
			try {
				this.saveOrUpdate(lpStyle);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 根据城市名称查找城市
		 */
		@Override
		public LpCity findTheCity(String cityName) {
			return (LpCity)this.getSession().createQuery("from LpCity where cityName=? and isTrueDelete = 1 ").setParameter(0, cityName).uniqueResult();
		}
		@Override
		public List<LpStyle> queryAllEnableStyle() {
			List<LpStyle> liststyle=new ArrayList<LpStyle>();
			String sql="select * from lp_style where is_true_delete=1 and style_status=0 order by style_location";
			try {
				Query query= this.getSession().createSQLQuery(sql).addEntity(LpStyle.class);
				liststyle=query.list();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return liststyle;
		}
		@Override
		public List<LpCity> getAllOnlineCity(String sql) {
			List<LpCity> list=new ArrayList<LpCity>();
			try{
				Query query= this.getSession().createSQLQuery(sql).addEntity(LpCity.class);
				list =query.list();
			}catch(Exception e){
				logger.error(e);	
			}
			return list;
		}
		@Override
		public List<LpUser> getAllMan(String cityId) {
			LpCity lpCity = new LpCity();
			lpCity.setCityId(cityId);
			//List<Object[]> objectList = getCurrentSession().createSQLQuery("select * from lp_user where city_id = '"+cityId+"' and user_type = 1").list();
			List<LpUser> userList = getCurrentSession().createQuery("from LpUser where lpCity = ? and userType = 1").setParameter(0, lpCity).list();
			return userList;
		}
		/**
		 * 修改城市位置
		 */
		@Override
		public void updateCityLocation(String cityId, int newLocation) {
			LpCity upCity = (LpCity)getCurrentSession().createQuery("from LpCity where cityLocation = ? and  isTrueDelete = 1").setParameter(0, newLocation).uniqueResult();
			LpCity lpCity = (LpCity)getCurrentSession().createQuery("from LpCity where cityId = ? and  isTrueDelete = 1").setParameter(0, cityId).uniqueResult();
			int oldLocation = lpCity.getCityLocation();
			lpCity.setCityLocation(newLocation);
			try {
				getCurrentSession().update(lpCity);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			upCity.setCityLocation(oldLocation);
			try {
				getCurrentSession().update(upCity);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		/**
		 * 修改风格位置
		 */
		@Override
		public void updateStyleLocation(String styleId, int newLocation) {
			LpStyle upStyle = (LpStyle)getCurrentSession().createQuery("from LpStyle where styleLocation = ? and  isTrueDelete = 1").setParameter(0, newLocation).uniqueResult();
			LpStyle lpStyle = (LpStyle)getCurrentSession().createQuery("from LpStyle where styleId = ? and  isTrueDelete = 1").setParameter(0, styleId).uniqueResult();
			int oldLocation = lpStyle.getStyleLocation();
			lpStyle.setStyleLocation(newLocation);
			try {
				getCurrentSession().update(lpStyle);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			upStyle.setStyleLocation(oldLocation);
			try {
				getCurrentSession().update(upStyle);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		@Override
		public void changeStyleStatus(String styleId, int status) {
			LpStyle lpStyle = (LpStyle)getCurrentSession().createQuery("from LpStyle where styleId = ?").setParameter(0, styleId).uniqueResult();
			if(status == 0)
			{
				try {
					lpStyle.setIsOnline(1);
					lpStyle.setOnlineTime(new Timestamp(new Date().getTime()));
					
					this.getCurrentSession().update(lpStyle);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else
			{
				try {
					lpStyle.setIsOnline(0);
					lpStyle.setOnlineTime(null);
					this.getCurrentSession().update(lpStyle);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		@Override
		public List<LpUser> findAllStyleMan(String styleId) {
			List<LpCameramanStyle> list = getCurrentSession().createQuery("from LpCameramanStyle where styleId = ?").setParameter(0, styleId).list();
			List<LpUser> lpUsers = new ArrayList<LpUser>();
			for (LpCameramanStyle lpCameramanStyle : list) 
			{
				LpUser lpUser = (LpUser)getCurrentSession().createQuery("from LpUser where userId = ?").setParameter(0, lpCameramanStyle.getUserId()).uniqueResult();
				lpUsers.add(lpUser);
			}
			return lpUsers;
		}

}
