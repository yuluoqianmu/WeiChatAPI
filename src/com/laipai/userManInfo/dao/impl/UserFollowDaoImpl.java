package com.laipai.userManInfo.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.userManInfo.dao.IUserFollowDao;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpUser;

@Transactional
@Repository("userFollowDao")
public class UserFollowDaoImpl extends BaseDaoImple implements IUserFollowDao {

	public static Logger logger = Logger.getLogger(UserFollowDaoImpl.class);
	
	@Override
	public boolean deleteFollowByIds(String[] followIds, LpUser operator) {
		
		logger.info("followIds = " + Arrays.asList(followIds));
		
		if(followIds == null || followIds.length == 0)
		{
			return true;
		}
		
		//TODO 批量删除。事物
		for(int i = 0; i < followIds.length; i++)
		{
			String followId = followIds[i];
			logger.info("delete a follow, followId = " + followId);
			
			LpFollow f = new LpFollow();
			f.setFollowId(followId);
			delete(f);
		}
		
		return true;
	}

	@Override
	public List getFollowbyUserId(String userId) {
		String sql="from LpFollowView l where l.userId=?";
		List  list=new ArrayList();
		try{
     	Query query=this.getSession().createSQLQuery(sql).addEntity(LpFollowView.class);
     	query.setParameter(0, userId);
     	list= query.list();
		}catch(Exception e){
			logger.error(e);			
		}
		return list;
	}

	@Override
	public List<LpFollowView> queryFansByuserId(String sql) {
		List  list=new ArrayList();
		try{
     	Query query=this.getSession().createSQLQuery(sql).addEntity(LpFollowView.class);
     	list= query.list();
		}catch(Exception e){
			logger.error(e);			
		}
		return list;
	}


}
