package com.laipai.cameraCheck.dao.imple;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.cameraCheck.dao.ICameraCheckDao;
import com.laipai.cameraCheck.dto.AppformData;
import com.laipai.serviceInfo.dao.ServiceDao;
@Repository(ICameraCheckDao.ICAMERAMANDAO_NAME)
public class CameraCheckDaoImpl extends BaseDaoImple implements ICameraCheckDao {
	 private static final Logger logger=Logger.getLogger(ServiceDao.class);
	@Override
	public List querySqldata(String sql) {
		try{
	 SQLQuery query=	this.getSession().createSQLQuery(sql);
	  query.addScalar("await", StandardBasicTypes.INTEGER);
	  query.addScalar("pass",StandardBasicTypes.INTEGER);
	  query.addScalar("fail",StandardBasicTypes.INTEGER);
	  query.setResultTransformer(Transformers.aliasToBean(AppformData.class));
	  return query.list();
		}catch(RuntimeException re){
			logger.error("select failed",re);
			re.printStackTrace();
			
		}
		return null;
	}

	
}
