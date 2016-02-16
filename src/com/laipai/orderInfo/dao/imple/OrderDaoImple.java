package com.laipai.orderInfo.dao.imple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.orderInfo.dao.OrderDao;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:50:30
 */
@Transactional
@Repository("orderDao")
public class OrderDaoImple extends BaseDaoImple implements OrderDao{
	private static final Logger logger=Logger.getLogger(OrderDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public LpUser getUserName(String userId) {
		return (LpUser)getCurrentSession().get(LpUser.class,userId);
	}
	
	@Override
	public void cancelOrder(String orderId) {
		try {
			LpOrder order=(LpOrder)getCurrentSession().get(LpOrder.class, orderId);
			order.setOrderStatus(4);
			getCurrentSession().update(order);
		} catch (Exception e) {
			logger.error("取消订单失败");
			e.printStackTrace();
		}
		
	}
	@Override
	public List queryOrderById(String galaryId) {
		String hql=" from LpOrder lo where lo.lpGalary.galaryId=?";
		List list=new ArrayList();
		try {
			Query query=getCurrentSession().createQuery(hql).setParameter(0, galaryId);
			list=(List) query.list();
		} catch (HibernateException e) {
			logger.error("根据作品集ID查询订单失败");
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List queryOrderByUserId(String userId) {
		String hql=" from LpOrder lo where lo.lpUser.userId=?";
		try {
			Query query=getCurrentSession().createQuery(hql).setParameter(0, userId);
			List<LpOrder> list=query.list();
			return list;
		} catch (Exception e) {
			logger.error("查询用户订单失败");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LpOrder> queryOrderByService(String serviceId) {
		String hql=" from LpOrder lo where lo.lpService.serviceId=?";
		try {
			Query query=getCurrentSession().createQuery(hql).setParameter(0, serviceId);
			List<LpOrder> order=query.list();
			return order;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateOrder(LpOrder order) {
		try {
			getCurrentSession().update(order);
		} catch (Exception e) {
			logger.error("跟新订单失败");
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteOrder(LpOrder order) {
		try {
			getCurrentSession().delete(order);
		} catch (Exception e) {
			logger.error("删除订单失败");
		}
	}

	@Override
	public LpOrder queryOrderByOrderId(String orderId) {
		try {
			LpOrder order=(LpOrder) getCurrentSession().get(LpOrder.class, orderId);
			return order;
		} catch (Exception e) {
			logger.error("根据订单ID查询订单失败");
		}
		return null;
	}

	@Override
	public void addOrder(LpOrder order) {
		try {
			getCurrentSession().save(order);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("预约失败");
		}
	}

	@Override
	public int getUserOrderNumber(String hql) {
		try {
			Query query=getCurrentSession().createQuery(hql);
			int count = ((Number)query.uniqueResult()).intValue();  
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
