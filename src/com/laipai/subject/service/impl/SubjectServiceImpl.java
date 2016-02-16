package com.laipai.subject.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.base.util.tags.PageController;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.subject.dao.ISubjectDAO;
import com.laipai.subject.pojo.Subject;
import com.laipai.subject.pojo.SubjectDetail;
import com.laipai.subject.pojo.VLpSubjectDetailBack;
import com.laipai.subject.service.ISubjectService;

@Service("subjectServiceImpl")
public class SubjectServiceImpl extends BaseServiceImpl implements ISubjectService {

   @Resource
	private ISubjectDAO iSubjectDAO;

	@Autowired
	private IBaseDao baseDao;
	
	public List<Subject> findSubjectList(HttpServletRequest request,String hql,int pageSize) {
		try {
			return this.querylistForPage(request,hql,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean addGallery(Subject subject, List<LpGalary> selectGallery) {
		return iSubjectDAO.addGallery(subject, selectGallery);
	}

	@Override
	public boolean updateStatus(String subject_id, Integer subject_status) {
		return iSubjectDAO.updateStatus(subject_id, subject_status);
	}

	@Override
	public boolean deleteSubject(String subject_id) {

		return iSubjectDAO.deleteSubject(subject_id);
	}

	@Override
	public Subject findById(String subject_id) {
		
		return iSubjectDAO.findById(subject_id);
	}

	@Override
	public void modifySubject(Subject subject, List<LpGalary> selectGallery) {
		iSubjectDAO.modifySubject(subject, selectGallery);
	}

	@Override
	public List<LpGalary> findAllGallery(String where) {
		return iSubjectDAO.findAllGallery(where);
	}

	@Override
	public List<SubjectDetail> findDetailList(String subject_id) {
		return iSubjectDAO.findDetailList(subject_id);
	}

	@Override
	public long getSubjectCount(int pageSize) {
		return iSubjectDAO.getSubjectCount(pageSize);
	}

	@Override
	public long countSum() {
		return iSubjectDAO.countSum();
	}

	@Override
	public boolean saveOrUpdateSubject(Subject subject) {
		return iSubjectDAO.saveOrUpdateSubject(subject);
	}

	@Override
	public List<Subject> findIndexList() {
		return iSubjectDAO.findIndexList();
	}

	@Override
	public void updateLocation(String index_subject_id,
			String subject_subject_id, String nowLocation) {
		iSubjectDAO.updateLocation(null, subject_subject_id, nowLocation);
	}

	@Override
	public void updateIndexLocation(String index_subject_id,
			String subject_subject_id, String nowLocation) {
		iSubjectDAO.updateIndexLocation(index_subject_id, null, nowLocation);
	}

	@Override
	public void deleteGalary(String subjectId, String galaryId) {
		iSubjectDAO.deleteGalary(subjectId, galaryId);
	}

	@Override
	public void updateSubjectDetailLocation(SubjectDetail subjectDetail,int oldLocation) {
		iSubjectDAO.updateSubjectDetailLocation(subjectDetail,oldLocation);
	}

	@Override
	public int getMaxLocation() {
		return iSubjectDAO.getMaxLocation();
	}

	@Override
	public void updateSubjectLocation(String subject_id, int newLocation) {
		iSubjectDAO.updateSubjectLocation(subject_id, newLocation);
	}

	@Override
	public List<LpGalary> allGalaryList(int nowPage, int pageSize,  int scanType) {
		return iSubjectDAO.allGalaryList(nowPage, pageSize, scanType);
	}

	@Override
	public long countGalarySum() {
		return iSubjectDAO.countGalarySum();
	}
	
	public void addGallery(String subject_id, String[] galleryss,String gallery_ids){
		try {
			//查询专题详情表,是否有作品集已经添加到了专题中
			if(gallery_ids.endsWith(",")){
				gallery_ids = gallery_ids.substring(0,gallery_ids.length()-1);
			}
			gallery_ids= gallery_ids.replace(",", "','");
			gallery_ids = "'" + gallery_ids + "'";
			List<SubjectDetail> list = baseDao.queryListObjectAll("from SubjectDetail where subject_id='"+subject_id+"' and  gallery_id in ("+gallery_ids+")");
			//把本专题已经添的作品集id连起来，便于下边比较
			String alreadyAdd = "";
			if(list !=null && !list.isEmpty()){
				for(SubjectDetail sd: list){
					alreadyAdd +=sd.getGallery_id();
				}
			}
			
			int location = getMaxLocationBySubjectId(subject_id);
			for(int i=0;i<galleryss.length;i++){
				//如果已经添进去了,继续下一个
				if(alreadyAdd.contains(galleryss[i])){
					continue;
				}
				location++;
				SubjectDetail detail = new SubjectDetail();
				detail.setGallery_id(galleryss[i]);
				detail.setSubject_id(subject_id);
				detail.setSubjectGalaryLocation(location);
				baseDao.save(detail);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<VLpSubjectDetailBack> getVLpSubjectDetailBackBySubjectId(String subject_id){
		try {
			List<VLpSubjectDetailBack> selectGalary = baseDao.queryListObjectAll("from VLpSubjectDetailBack where subjectId='"+subject_id+"'");
			return selectGalary;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getMaxLocationBySubjectId(String subject_id){
		String hql = "from SubjectDetail where subject_id='"+subject_id+"' order by subjectGalaryLocation desc";
		List<SubjectDetail> list = baseDao.queryListObjectByTopNum(hql, 1);
		if(list !=null && !list.isEmpty()){
			SubjectDetail detail =  list.get(0);
			return detail.getSubjectGalaryLocation();
		}
		return 0;
	}

	@Override
	public List<LpGalary> selectedGalaryList(String subject_id) {
		return iSubjectDAO.selectedGalaryList(subject_id);
	}
	
	
}
