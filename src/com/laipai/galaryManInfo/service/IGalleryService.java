package com.laipai.galaryManInfo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.laipai.base.service.IBaseService;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.pojo.LpComment;

public interface IGalleryService extends IBaseService {
	public static final String SERVICE_NAME = "com.laipai.galaryManInfo.service.impl.GalleryServiceImpl";

	List queryall();

	List queryallStyle();

	void saveGallery(LpGalary galaryBean);

	LpGalary getgalleryByID(String galleryId);

	List<LpGalaryDetail> getphotosByGalleryID(String galleryId);
	
	List<LpGalaryDetail> getphotosUpdateByGalleryID(String galleryId);

	void saveComment(LpComment comment);

	List<LpComment> queryCommentByGalleryID(String galleryId);

	void deletephotoById(String photoid);

	void deleteGalleryById(String galleryId);

	void deleteCommentById(String commentID);

	List getAllBypage(HttpServletRequest request);

	void saveGallery(LpGalary gallery, String serviceId,String userAccount,
			String[] imageNameArray,String converName);

	List<LpService> getServiceByAcount(String account);

	public void updateGalleryDetail(String galaryId, String[] imageNameArray);

	void updateGalleryStatus(String galaryId, int status);

	LpGalaryExtend getExtendbyGalleryId(String galleryId);

	void updateGalaryExt(String galleryId, int vieNum, int likeNum);

	LpService getServiceById(String serviceId);

	List<LpGalary> getGallery(String string, String userId, int i);
	
	/**
	 * 根据作品集其中一张照片id查询此照片对象
	 * */
	public LpGalaryDetail getGalaryDetailById(String photoid);

	public void minusOneCommentByGalary(String galaryId);

	void updateGalleryIndex(String galleryId, int index);

	void updateControlIndex();

	int GalleryCount();
	public int getGalleryCount(String hql);
	<T> List getGalleryTop(String hql,int top,T t);

	List<LpGalaryExtend> getExebygalleryId(String galaryId);

	void updateExt(LpGalaryExtend extend);

	void updateDetail(LpGalaryDetail detail);
	
}
