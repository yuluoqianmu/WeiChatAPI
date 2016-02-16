package com.laipai.privilege.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.privilege.dao.PrivilegeDao;
import com.laipai.privilege.pojo.LpPrivilege;
import com.laipai.privilege.pojo.LpPrivilegeId;
import com.laipai.userManInfo.pojo.LpUser;

@Transactional
@Repository("privilegeDao")
public class PrivilegeDaoImpl implements PrivilegeDao {

    private static final Logger logger=Logger.getLogger(PrivilegeDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
 		this.sessionFactory = sessionFactory;
	}
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean change2Admin(String userId) {
		LpUser user = (LpUser) getCurrentSession().get(LpUser.class, userId);
		user.setUserType(2);
		getCurrentSession().update(user);
		return true;
	}

	@Override
	public boolean addPrivilege(String[] resourceIds, String userId, LpUser operatpr) {

		Session session = null;
		Transaction tr = null;
		try {
			session = getCurrentSession();
			// 删除原有权限
			String deletePrivilegeHQL = "delete LpPrivilege where user_id=:id";
			Query q = session.createQuery(deletePrivilegeHQL);
			q.setParameter("id", userId);
			q.executeUpdate();
			
			//创建新的权限
			if(resourceIds != null && resourceIds.length > 0)
			{
				List<LpPrivilege> ps = new ArrayList<LpPrivilege>();
				for(String resourceId:resourceIds)
				{
					LpPrivilegeId id = new LpPrivilegeId(resourceId, userId);
					LpPrivilege p = new LpPrivilege();
					p.setId(id);
					
					ps.add(p);
					session.saveOrUpdate(p);
				}
				
				
			}
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

		// 插入新权限

		return false;
	}

	@Override
	public List<String> getResourceByUserId(String userId) {
		List<String> ps = new ArrayList<String>();
		
		Session session = null;
		try {
			session = getCurrentSession();
			
			// 删除原有权限
			String deletePrivilegeHQL = "from LpPrivilege where user_id=:id";
			Query q = session.createQuery(deletePrivilegeHQL);
			q.setParameter("id", userId);
			List<LpPrivilege> l = q.list();
			
			//创建新的权限
			if(l != null && l.size() > 0)
			{
				
				for(LpPrivilege p:l)
				{
					ps.add(p.getId().getResourceId());					
				}
				
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return ps;
	}
	

}
