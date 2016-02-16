package com.laipai.base.dao.imple;
 
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
 
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.IBaseDao;
 
/**
 * @ClassName: BaseDao
 * @Description: baseDao实现
 * @author zhanhh on$2014-12-15
 *
 */
@Transactional
@Repository("baseDao")
public class BaseDaoImple  implements IBaseDao {
     
	@Autowired
     private SessionFactory sessionFactory;
 
    /**
     * <保存实体>
     * <完整保存实体>
     * @param Object 实体参数
     */
    public void save(Object objBean) {
    	this.sessionFactory.getCurrentSession().save(objBean);
    }
    
    /**
     * <保存或者更新实体>
     * @param Object 实体
     */
    public void saveOrUpdate(Object objBean) {
    	this.sessionFactory.getCurrentSession().saveOrUpdate(objBean);
    }
     

     
    /**
     * <contains>
     * @param t 实体
     * @return 是否包含
     */
    
    public boolean contains(Object objBean) {
    	return this.sessionFactory.getCurrentSession().contains(objBean);
    }
 
    /**
     * <delete>
     * <删除表中的t数据>
     * @param t 实体
     */
    
    public void delete(Object objBean) {
    	this.sessionFactory.getCurrentSession().delete(objBean);
    }
    
    public void updateObject(Object objBean) {
    	this.sessionFactory.getCurrentSession().update(objBean);
	}
     
    /**
	 * 
	 * 描述：hql查询总量
	 *
	 * @Title: querySqlObject  
	 * @param sql
	 * @return
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public int getCount(String hql){
		Session session = this.sessionFactory.openSession();
		int count = 0;
		try {
			List list = session.createQuery(hql).list();
			if (list != null && list.size() > 0 && list.get(0)!=null) {
				count = list.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return count;
	}
	
	/**
	 * 
	 * 描述：hql查询分页数据
	 *
	 * @Title: querySqlObject  
	 * @param sql
	 * @return
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public List queryListObjectAllForPage(String queryString,
			int pageSize, int page) {
		Session session = this.sessionFactory.getCurrentSession();
		List list=null;
		try{
		Query   query   =   session.createQuery(queryString); 
		query.setFirstResult((page-1)*pageSize); 
		query.setMaxResults(pageSize); 
		list= query.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * 描述：hql查询所有数据，不分页
	 *
	 * @Title: querySqlObject  
	 * @param sql
	 * @return
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public List queryListObjectAll(String hql) {
		return this.sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	
	/**
	 * 
	 * 描述：执行普通sql
	 *
	 * @Title: querySqlObject  
	 * @param sql
	 * @return
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public List querySqlObject(String sql) {
		List list= null;
		Session session = null;
		try {
			session = this.sessionFactory.openSession();
			Transaction tr=session.beginTransaction();
			Query   query   =   session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
			list=query.list();
			tr.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return list;
	}
	
	/**
	 * 
	 * 描述：保存对象，并返回主键
	 *
	 * @Title: saveObjectReturnId  
	 * @param Object
	 * @return
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public Serializable saveObjectReturnId(Object objBean) {
		return this.sessionFactory.getCurrentSession().save(objBean);
	}
	
	/**
	 * 批量删除对象
	 * */
	public void deleteMultipleObject(List<Object> list) {
		this.sessionFactory.getCurrentSession().delete(list);
	}
	
	/**
	 * 
	 * 描述：通过主键查询对象
	 *
	 * @Title: saveObjectReturnId  
	 * @param Object
	 * @return
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public Object getObjectById(Class cla, String id) {
		
		return this.sessionFactory.getCurrentSession().get(cla, id);
	}
	
	/**
	 * 查询前边top N条数据
	 * */
	public List queryListObjectByTopNum(String queryString,int number){
		Session session = this.sessionFactory.getCurrentSession();
		List list=null;
		try{
		Query   query   =   session.createQuery(queryString); 
		query.setFirstResult(0);
		query.setMaxResults(number); 
		list= query.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * 描述：执行普通sql
	 *
	 * @Title: executeSql  
	 * @param SQLStr
	 * @return
	 * @see com.hjhz.base.dao.IBaseDao#executeSql(java.lang.String)     
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public boolean executeSql(String SQLStr) {
		boolean flag=true;
		Session session =  this.sessionFactory.openSession();
		Transaction t=session.beginTransaction();
		try {
			session.createSQLQuery(SQLStr).executeUpdate();
			t.commit();
		} catch (HibernateException e) {
			flag=false;
			t.rollback();
		}finally{
			session.close();
		}
		return flag;
	}

	@Override
	public List queryListObjectByCondition(String queryString, Object[] args) {
		Query query= this.sessionFactory.getCurrentSession().createQuery(queryString);
		int i=0;
	    for(Object p:args){
	    	query.setParameter(i, p);
	    	i++;
	    }
	    return query.list();
	}
	
	public List SQLQueryListObjectByCondition(String sql,Object[] args,Class c){
		Session session= this.sessionFactory.getCurrentSession();
	    Query query=session.createSQLQuery(sql).addEntity(c);
	    int i=0;
	    for(Object p:args){
	    	query.setParameter(i, p);
	    	i++;
	    }
	    return query.list();
	}
	@Override
	public List SQLQueryListByJoin(String sql,Class object){
		Session session = this.sessionFactory.getCurrentSession();
	    Query query=session.createSQLQuery(sql).addEntity("object",object);
		return query.list();
	}
	@Override
	public List SQLQueryListByJoinForPage(String sql,Class object,int pageSize,int page){
		Session session = this.sessionFactory.getCurrentSession();
	    Query query=session.createSQLQuery(sql).addEntity("object",object);
	    query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(pageSize);
		return query.list();
		
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	
}