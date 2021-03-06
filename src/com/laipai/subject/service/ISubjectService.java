package com.laipai.subject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.subject.pojo.Subject;
import com.laipai.subject.pojo.SubjectDetail;
import com.laipai.subject.pojo.VLpSubjectDetailBack;
/**
 * 
 * @Description:Service层接口
 * @author:朱鑫
 * @time:2015-1-26 下午3:26:39
 */
public interface ISubjectService {
	
	public List<Subject> findSubjectList(HttpServletRequest request,String hql,int pageSize);
	public List<LpGalary> selectedGalaryList(String subject_id);
	public boolean saveOrUpdateSubject(Subject subject);
	
	public boolean addGallery(Subject subject, List<LpGalary> selectGallery);
	
	public boolean updateStatus(String subject_id, Integer subject_status);
	
	public boolean deleteSubject(String subject_id);
	
	public Subject findById(String subject_id);
	
	public void modifySubject(Subject subject, List<LpGalary> selectGallery);
	
	public List<LpGalary> findAllGallery(String where);
	
	public List<SubjectDetail> findDetailList(String subject_id);
	
	public List<Subject> findIndexList();
	
	public void updateLocation(String index_subject_id,String subject_subject_id,String nowLocation);
	
	public void updateIndexLocation(String index_subject_id,String subject_subject_id,String nowLocation);
	
	public long getSubjectCount(int pageSize);
	
	public long countSum();
	
	public void deleteGalary(String subjectId, String galaryId);
	
	public void updateSubjectDetailLocation(SubjectDetail subjectDetail,int oldLocation);
	
	public int getMaxLocation();
	
	public void updateSubjectLocation(String subject_id, int newLocation);
	
	public List<LpGalary> allGalaryList(int nowPage, int pageSize,  int scanType);
	
	public long countGalarySum();

	public void addGallery(String subject_id, String[] galleryss,String gallery_ids);

	public List<VLpSubjectDetailBack> getVLpSubjectDetailBackBySubjectId(String subject_id);

	public int getMaxLocationBySubjectId(String subject_id);
	
}
