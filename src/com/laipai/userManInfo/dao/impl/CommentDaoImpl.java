package com.laipai.userManInfo.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.userManInfo.dao.ICommentDao;
import com.laipai.userManInfo.pojo.LpComment;

@Repository(ICommentDao.COMENTDAO_NAME)
public class CommentDaoImpl extends BaseDaoImple implements ICommentDao {
	 private static final Logger logger=Logger.getLogger(ServiceDao.class);
	 
	 
	@Override
	public List<LpComment> getCommentBySql(String sqlString, Object... values) {
			 Query query = this.getSession().createSQLQuery(sqlString).addEntity(LpComment.class);
		        if (values != null)
		        {
		            for (int i = 0; i < values.length; i++)
		            {
		                query.setParameter(i, values[i]);
		            }
		        }
		        List<LpComment> list=query.list();
		        return list;
	

	}
     /**
      * 保存评论
      */
	@Override
	public void saveComment(LpComment comment) {
		try {
			this.getSession().save(comment);
		}catch(RuntimeException re){
			logger.error("select failed",re);
			re.printStackTrace();
			
		}
		
	}
	@Override
	public void deleteById(String commentID) {
		try {
		   LpComment comment= (LpComment) this.getObjectById(LpComment.class, commentID);
		   if(comment!=null){
			   this.getSession().delete(comment);
		   }
		}catch(RuntimeException re){
			logger.error("select failed",re);
			re.printStackTrace();
			
		}
		
	}
	


   
 
	
}
