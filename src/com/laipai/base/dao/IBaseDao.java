package com.laipai.base.dao;
 
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

/**
 * @ClassName: IBaseDao
 * @Description: Dao封装接口
 * @author zhanhh on$2014-12-15
 */
public interface IBaseDao {
     
    /**
     * <保存实体>
     * <完整保存实体>
     * @param Object 实体参数
     */
	public void save(Object objBean);
	public Session getSession();
	public void updateObject(Object objBean);
 
    /**
     * <保存或者更新实体>
     * @param Object 实体
     */
	public void saveOrUpdate(Object objBean);
	 /**
     * <delete>
     * <删除表中的t数据>
     * @param t 实体
     */
    
    public void delete(Object objBean);

    public int getCount(String hql);
    
    public List queryListObjectAllForPage(String queryString, int pageSize, int page);
    
    public List queryListObjectAll(String hql);
    /**
	 * 
	 * 描述：执行普通sql
	 *
	 * @Objectitle: querySqlObject  
	 * @param sql
	 * @return
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public List querySqlObject(String sql);
	/**
	 * 
	 * 描述：保存对象，并返回主键
	 *
	 * @Objectitle: saveObjectReturnId  
	 * @param Object
	 * @return
	 * @author:  zhanhh
	 * @date: 2014-12-23
	 */
	public Serializable saveObjectReturnId(Object objBean);
	/**
	 * 批量删除对象
	 * */
	public void deleteMultipleObject(List<Object> list);
	
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
	public Object getObjectById(Class cla, String id);
	/**
	 * 查询前边第N条数据
	 * */
	public List queryListObjectByTopNum(String queryString,int number);
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
	public boolean executeSql(String SQLStr);
	
	public List queryListObjectByCondition(String queryString,Object[] args);
	public List SQLQueryListObjectByCondition(String sql,Object[] args,Class c);
	public List SQLQueryListByJoin(String sql,Class object);
	public List SQLQueryListByJoinForPage(String sql,Class object,int pageSize,int page);
 
}