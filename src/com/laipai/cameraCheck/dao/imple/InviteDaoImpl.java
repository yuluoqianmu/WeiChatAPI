package com.laipai.cameraCheck.dao.imple;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.cameraCheck.dao.IInviteDao;
import com.laipai.cameraCheck.pojo.LpInvite;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.userManInfo.pojo.LpComment;
@Repository(IInviteDao.IINVITEDAO_NAME)
public class InviteDaoImpl extends BaseDaoImple implements IInviteDao {
	 private static final Logger logger=Logger.getLogger(ServiceDao.class);
	/**
	 * 校验验证码
	 */
	@Override
	public LpInvite getInviteBycode(String sql,Object... values) {
		
		LpInvite invite=null;
		try{
		Query query=this.getSession().createSQLQuery(sql).addEntity(LpInvite.class);
		 if(values!=null){
			 for(int i=0;i<values.length;i++){
				 query.setParameter(i, values[i]);				 
			 }
		 }
		  List<LpInvite> list=query.list();
		     if(list!=null&&list.size()>0){
		    invite= list.get(0); 
		    	
		     }
		}catch(Exception e){
			logger.error(e);	

		}
		 return invite;
	}
 
}

