package com.laipai.privilege.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.privilege.dao.ResourceDao;
import com.laipai.privilege.pojo.LpResource;

@Transactional
@Repository("resourceDao")
public class ResourceDaoImpl implements ResourceDao {

    private static final Logger logger=Logger.getLogger(ResourceDaoImpl.class);
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
	public List<LpResource> queryAllResource() {
		List<LpResource> result = getCurrentSession().createCriteria(LpResource.class).addOrder(Property.forName("seqId").asc() ).list();
		
		logger.info("resources = " + result);
		
		return result;
	}
	
}
