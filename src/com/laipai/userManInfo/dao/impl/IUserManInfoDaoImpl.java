package com.laipai.userManInfo.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;
@Transactional
@Repository(IUserManInfoDao.IUSERMANINFODAO_NAME)
public class IUserManInfoDaoImpl extends BaseDaoImple implements IUserManInfoDao {
	 private static final Logger logger=Logger.getLogger(ServiceDao.class);
	@Override
	public List<LpUser> queryAllUser() {
		return this.queryListObjectAll("from LpUser");
	}
	@Override
	public void update(LpUser userInfoBean){
		this.updateObject(userInfoBean);
	}
	@Override
	public void save(LpUser userInfoBean) {
		this.save(userInfoBean);
	}
	/**
	 * 根据用户Id查询用户
	 */
	@Override
	public LpUser queryUserById(String userId) {
		LpUser user=null;
		try {
			user=(LpUser)this.getSession().get(LpUser.class, userId);
		}catch(RuntimeException re){
			logger.error("select failed",re);
			re.printStackTrace();
			
		}
		return user;
	}
	/**
	 * 根据用户名查询用户
	 */
	@Override
	public LpUser queryUserByName(String userName) {
		//LpUser user=new LpUser();
		String hql = "from LpUser where userName ='" + userName + "'";
		List<LpUser> users = this.getSession().createQuery(hql).list();
		
		if(users != null && users.size() > 0)
		{
			return users.get(0);
		}
		
		return null;
	}
	@Override
	public void deleteUserById(String userId) {
		try {
		LpUser	user=this.queryUserById(userId);
		if(user!=null){		
			this.getSession().delete(user);	
		}
		}catch(RuntimeException re){
			logger.error("select failed",re);
			re.printStackTrace();
			
		}
		
	}
	@Override
	public void saveCamera(LpUser user) {
		try {
		    Session session = this.getSession();
				//beginTransaction(session);
			 this.getSession().save(user);
			}catch(RuntimeException re){
				logger.error("select failed",re);
				re.printStackTrace();
				
			}
		
	}
	@Override
	public void deleteCommentById(String commentId) {
		try {
			Session session=this.getSession();
			LpComment comment=(LpComment) session.get(LpComment.class,commentId);
			session.delete(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void deleteFollowById(String followId) {
		try {
			LpFollow follow=(LpFollow) this.getSession().get(LpFollow.class,followId);
			this.getSession().delete(follow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void deleteLikeById(String likeId) {
		try {
			LpLike like=(LpLike)this.getSession().get(LpLike.class, likeId);
			this.getSession().delete(like);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List getstyleByuser(String userId) {
		List list=null;
		try {
			String sql= "select * from lp_style l where l.style_id in (SELECT style_id FROM lp_cameraman_style l WHERE l.user_id='"+userId+"')";
           Query query= this.getSession().createSQLQuery(sql).addEntity(LpStyle.class);
			list= query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<LpUser> queryListByUserName(String sql,Object... values) {
		List list=null;
		try {
           Query query= this.getSession().createSQLQuery(sql).addEntity(LpUser.class);
           if (values != null)
	        {
	            for (int i = 0; i < values.length; i++)
	            {
	                query.setParameter(i, values[i]);
	            }
	        }
			list= query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List queryListByNickName(String sql) {
		try {
			Query query=this.getSession().createSQLQuery(sql).addEntity(LpUser.class);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
