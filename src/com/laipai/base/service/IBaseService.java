package com.laipai.base.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;


public interface IBaseService {
	
	/**
	 * 
	 * 描述: 通用分页 查询list列表
	 *
	 * @Title: queryListObjectAllForPage  
	 * @param queryString
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws Exception    
	 * @return List    
	 * @author:  chenguangyuan
	 * @date: 2012-9-24 上午09:31:05 
	 * @throws
	 */
	public List queryListObjectAllForPage(String queryString,int pageSize,int page)throws Exception;
	/**
	 * 
	 * 描述: 分页共用方法  service专业
	 *
	 * @Title: querylistForPage  
	 * @param request
	 * @param hql
	 * @param pageSize
	 * @return
	 * @throws Exception    
	 * @return List    
	 * @author:  txh
	 * @date: Oct 18, 2012 3:25:52 PM 
	 * @throws
	 */
	public List querylistForPage(HttpServletRequest request,String hql,int pageSize)throws Exception;
	/**
	 * 
	 * 描述：Criteria多条件查询使用分页
	 *
	 * @Title: queryForPageByCriteria  
	 * @param request
	 * @param criteria
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * @see com.hjhz.base.service.IBaseService#queryForPageByCriteria(javax.servlet.http.HttpServletRequest, org.hibernate.Criteria, int)     
	 * @author:  zhangxiaodi
	 * @date: 2012-12-5 下午3:10:20
	 */
	public List queryForPageByCriteria(HttpServletRequest request, Criteria criteria,
			int pageSize) throws Exception;
}
