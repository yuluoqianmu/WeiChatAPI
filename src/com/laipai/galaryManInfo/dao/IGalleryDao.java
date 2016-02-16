package com.laipai.galaryManInfo.dao;

import java.io.Serializable;
import java.util.List;

import com.laipai.base.dao.IBaseDao;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.galaryManInfo.pojo.VLpGalaryAppShow;
import com.laipai.galaryManInfo.pojo.VLpGalaryBackinfo;

public interface IGalleryDao extends IBaseDao {
	public static final String DAO_NAME = "com.laipai.galaryManInfo.dao.impl.GalleryDaoImpl";

	List queryall();

	void update(LpGalary galaryBean);

	void save(LpGalary galaryBean);

	void save(LpGalaryDetail photo);

	LpGalary getgalleryByID(String galleryId);

	List<LpGalaryDetail> getphotosByGalleryID(String galleryId);

	List<LpGalaryDetail> getphotosUpdateByGalleryID(String galleryId);
	
	void deletephotoById(String photoid);

	void deletegalleryById(String galleryId);

	List getListBySql(String hqlString, Object... values);

	List<LpGalaryExtend> getExtend(String sql,Object... values);

	List<LpGalary> getGallery(String string, Object... values);
	List<VLpGalaryBackinfo> getVLpGalaryBackinfo(String string, Object... values);
	List<VLpGalaryAppShow> getVLpGalaryAppShow(String string, Object... values);

	LpGalary getGalleryByHql(String hql);

	List<LpGalary> getGalleryTop(String Hql,int first,int topNumber);

	Integer getGalleryCount();
    public int getGalleryCount(String hql);

	


	


}
