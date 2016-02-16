package com.laipai.galaryManInfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.galaryManInfo.dao.IGalleryDao;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.galaryManInfo.pojo.VLpGalaryAppShow;
import com.laipai.galaryManInfo.pojo.VLpGalaryBackinfo;
import com.laipai.orderInfo.dao.OrderDao;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.userManInfo.pojo.LpComment;

@Repository(IGalleryDao.DAO_NAME)
public class GalleryDaoImpl extends BaseDaoImple implements IGalleryDao {
  
	 private static final Logger logger=Logger.getLogger(ServiceDao.class);
	 
	@Override
	public List queryall() {
		return this.queryListObjectAll("from LpGalary");
	}
	/**
	 * 更新作品集
	 */
	@Override
	public void update(LpGalary galaryBean) {
		    updateObject(galaryBean);
	}
	/**
	 * 保存作品集
	 */
	@Override
	public void save(LpGalary galaryBean) {
			save(galaryBean);
	}
	/**
	 * 保存照片
	 */
	@Override
	public void save(LpGalaryDetail photo) {
			this.saveOrUpdate(photo);
	}
	@Override
	public LpGalary getgalleryByID(String galleryId) {
			return (LpGalary) this.getObjectById(LpGalary.class, galleryId);
	}
	@Override
	public List<LpGalaryDetail> getphotosByGalleryID(String galleryId) {
		List<LpGalaryDetail> listphoto =new ArrayList<LpGalaryDetail>();
		String sql=" SELECT * FROM lp_galary_detail l where l.galary_id=? and l.status=0";
		try{
			listphoto=this.SQLQueryListObjectByCondition(sql, new String[]{galleryId}, LpGalaryDetail.class);	
		}catch(Exception e){
			e.printStackTrace();
		}
		return listphoto;
	}
	
	public List<LpGalaryDetail> getphotosUpdateByGalleryID(String galleryId) {
		List<LpGalaryDetail> listphoto =new ArrayList<LpGalaryDetail>();
		String sql=" SELECT * FROM lp_galary_detail l where l.galary_id=? and l.status=0";
		try{
			listphoto=this.SQLQueryListObjectByCondition(sql, new String[]{galleryId}, LpGalaryDetail.class);	
		}catch(Exception e){
			e.printStackTrace();
		}
		return listphoto;
	}
	
   /**
    * 根据Id删除照片
    */
	public void deletephotoById(String photoid) {
		try {
		 LpGalaryDetail galleryDetail=this.getPhotoById(photoid);
		 this.delete(galleryDetail);
		}catch(RuntimeException re){
			logger.error("select failed",re);
			re.printStackTrace();			
		}
		
	}
	/**
	 * 
	
	 * @Description:根据ID查询照片
	
	 * @param photoid
	 * @return
	
	 * LpGalaryDetail
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-20 下午7:12:14
	 */
    private LpGalaryDetail getPhotoById(String photoid) {
	  return  (LpGalaryDetail) this.getObjectById(LpGalaryDetail.class,photoid);
}
	@Override
	public void deletegalleryById(String galleryId) {
		try {
			LpGalary gallery=this.getgalleryByID(galleryId);
			 if(gallery!=null){
//				 sessionFactory.getCurrentSession().delete(gallery);
				 gallery.setStatus(1); //状态  0有效  1无效
				 this.update(gallery);
				 /*注：要修改请通知发现模块的开发人员   如果专题状态设为无效，相应的删除lp_subject_detail表相信id的数据*/
				 String sql = "delete from lp_subject_detail where gallery_id = '"+galleryId+"'";
				 System.out.println("------------"+sql);
				 executeSql(sql);
			 }
			}catch(RuntimeException re){
				logger.error("select failed",re);
				re.printStackTrace();			
			}
		
	}
	/**
	 * 根据hql查询list
	 */
	@Override
	public List getListBySql(String hqlString, Object... values) {
	        return this.SQLQueryListObjectByCondition(hqlString, values, LpGalary.class);
	}
	@Override
	public List<LpGalaryExtend> getExtend(String sql,Object... values) {
		return this.SQLQueryListObjectByCondition(sql, values, LpGalaryExtend.class);
	}
	
	public List<VLpGalaryBackinfo> getVLpGalaryBackinfo(String hql, Object... values) {
		return this.queryListObjectByCondition(hql, values);
	}
	
	public List<VLpGalaryAppShow> getVLpGalaryAppShow(String hql, Object... values) {
		return this.queryListObjectByCondition(hql, values);
	}
	public List<LpGalary> getGallery(String hql, Object... values) {
		return this.queryListObjectByCondition(hql, values);
	}
	@Override
	public LpGalary getGalleryByHql(String hql) {
		LpGalary l=null;
		List<LpGalary> array=this.queryListObjectAll(hql);
		if(array!=null&&array.size()>0){
			l=array.get(0);
		}
		return l;
	}
	@Override
	public List<LpGalary> getGalleryTop(String Hql,int first, int topNumber) {
		
		return this.queryListObjectByTopNum(Hql, topNumber);
	}
	@Override
	public Integer getGalleryCount() {
		return this.getCount("from LpGalary where status=0");
	}
	@Override
	public int getGalleryCount(String hql) {
		return this.getCount(hql);
	}
	

}
