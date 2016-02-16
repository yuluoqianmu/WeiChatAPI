package com.laipai.base.service.imple;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.IBaseService;
import com.laipai.base.util.tags.PageController;

@Service("baseService")
public  class BaseServiceImpl implements IBaseService {
	
	private static final Logger logger = Logger.getLogger(BaseServiceImpl.class);
	
	@Autowired
	private IBaseDao baseDao;
	/**
	 * 
	 * 描述：分页通用方法  按条件查询列表
	 *
	 * @Title: queryListObjectAllForPage  
	 * @param queryString
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws Exception
	 * @see com.hjhz.base.service.IBaseService#queryListObjectAllForPage(java.lang.String, int, int)     
	 * @author:  chenguangyuan
	 * @date: 2012-9-24 上午09:39:43 
	 * @throws
	 */
	public List queryListObjectAllForPage(String queryString, int pageSize, int page)
			throws Exception {
		return baseDao.queryListObjectAllForPage(queryString, pageSize, page);
	}
	
	/**
	 * 
	 * 描述： 分页公共方法  分页service专用
	 *
	 * @Title: querylistForPage  
	 * @param request
	 * @param hql
	 * @param pageSize
	 * @return
	 * @throws Exception
	 * @see com.hjhz.base.service.IBaseService#querylistForPage(javax.servlet.http.HttpServletRequest, java.lang.String, int)     
	 * @author:  txh
	 * @date: Oct 18, 2012 3:27:30 PM 
	 * @throws
	 */
	public List querylistForPage(HttpServletRequest request, String hql,
			int pageSize) throws Exception {
			int count=this.baseDao.getCount(hql);
			int page=PageController.initPage(request);
			if(pageSize<1||"".equals(pageSize)) {
				//每页显示 默认10条
				pageSize=10;
			}
			PageController pc=new PageController(request, "pageController", count, pageSize, page);
			request.setAttribute("pageController", pc);
			List list=this.baseDao.queryListObjectAllForPage(hql, pageSize, page);
			logger.debug("count = " + count + ", page = " + page + ", pageSize = " + pageSize + ", sql = " + hql);
			return list;
	}
	
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
			int pageSize) throws Exception {
			int count=criteria.list().size();
			int page=PageController.initPage(request);
			if(pageSize<1||"".equals(pageSize))
			{
				//每页显示 默认10条
				pageSize=10;
			}
			PageController pc=new PageController(request, "pageController", count, pageSize, page);
			request.setAttribute("pageController", pc);
			criteria.setMaxResults(pageSize);
			criteria.setFirstResult((page-1)*pageSize);
			return criteria.list();
	}
	

}
