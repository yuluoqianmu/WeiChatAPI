package com.laipai.subject.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.img.ImgUtil;
import com.laipai.subject.dao.ISubjectDAO;
import com.laipai.subject.pojo.Subject;
import com.laipai.subject.pojo.SubjectDetail;
@Transactional
@Repository
public class SubjectDAOImpl  extends BaseDaoImple implements ISubjectDAO {

@Resource
private SessionFactory sessionFactory;
	
public Session getCurrentSession(){
	return sessionFactory.getCurrentSession();
}
	public List<Subject> findSubjectList(int nowPage, int pageSize) {
		Query query = getCurrentSession().createQuery("from Subject order by subject_location");
		query.setFirstResult((nowPage - 1)*pageSize);
		query.setMaxResults(pageSize);
		List<Subject> list = null;
		try {
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Subject> newList = new ArrayList<Subject>();
		if(list!=null && list.size()!=0){
			
			for(int i=0;i<list.size();i++){
				Subject sub= list.get(i);
				List<LpGalary> slectNum = selectedGalaryList(sub.getSubject_id());
				sub.setGallery_number(0);
				if(slectNum != null && !"".equals(slectNum))
				{
					sub.setGallery_number(slectNum.size());
				}
//				if(sub.getSubject_img() != null)
//				{
//					sub.setSubject_img(sub.getSubject_img().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+"/lpSubjectImg", LaipaiConstants.UPLOAD_VIRTUAL_IMG+"/lpSubjectImg"));
//				}
				sub.setSubject_img(ImgUtil.getImgUrl(sub.getSubject_img()));
				newList.add(sub);
			}
		}
		return newList;
	}
	/**
	 * 多对多 新增或修改作品集
	 */
	@Override
	public boolean addGallery(Subject subject, List<LpGalary> selectGallery) {
			
			Integer viewCount = 0;
			Integer likeCount = 0;
			Integer commentCount = 0;
			if(selectGallery != null && selectGallery.size() != 0)
			{
			for (LpGalary gallery : selectGallery) {
			//executeSql("update  lp_galary set subject_galary_location = "+i+" where galary_id = '"+gallery.getGalaryId()+"'");
				// 插入之前得到UUID，与将要插入的UUID相同
				//UUID uuid = UUID.randomUUID();
				if(gallery.getCommentNumber() != null)
				{
					commentCount += gallery.getCommentNumber();
				}
				if(gallery.getViewNumber() != null)
				{
					viewCount += gallery.getViewNumber();
				}
				if(gallery.getLikeNumber() != null)
				{
					likeCount += gallery.getLikeNumber();
				}
				subject.getSet().add(gallery);
			}
			}
			subject.setAccess_number(new BigDecimal(viewCount));
			subject.setLike_number(new BigDecimal(likeCount));
			subject.setGallery_number(selectGallery.size());
			String subject_id = subject.getSubject_id();
			this.saveOrUpdate(subject);
			List<SubjectDetail> allSubjectDetail = allSubjectDetail(subject_id);
			//int maxLocation = allSubjectDetail.get(0).getSubjectGalaryLocation();
			if(allSubjectDetail != null && allSubjectDetail.size() != 0)
			{
				int i = 0;
				for (SubjectDetail subjectDetail : allSubjectDetail)
				{
					i++;
					subjectDetail.setSubjectGalaryLocation(i);
					getCurrentSession().update(subjectDetail);
				}
			}
			return true;
	}
	public List<SubjectDetail> allSubjectDetail(String subject_id)
	{
		return getCurrentSession().createQuery("from SubjectDetail where subject_id='"+subject_id+"' order by subjectGalaryLocation").list();
	}
	/**
	 * 修改专题状态
	 */
	@Override
	public boolean updateStatus(String subject_id, Integer subject_status) {
		Subject subject = (Subject) getCurrentSession().createQuery("from Subject where subject_id=?").setParameter(0, subject_id).uniqueResult();
		subject.setSubject_id(subject_id);
		subject.setSubject_status(subject_status);
		if(subject_status == 1)
		{
			subject.setOnline_time(new Date());
		}else
		{
			subject.setOnline_time(null);
		}
		getCurrentSession().update(subject);
		return true;
	}
	/**
	 * 删除专题
	 */
	@Override
	public boolean deleteSubject(String subject_id) {
		Subject subject = (Subject) getCurrentSession().createQuery("from Subject where subject_id=?").setParameter(0, subject_id).uniqueResult();
		//修改首页位置；
		if(subject.getIndex_location() != 0)
		{
			executeSql("update  lp_subject set index_location = index_location - 1 where index_location >= "+subject.getIndex_location()+"");
		}
		//修改专题位置
		if(subject.getSubject_location() > 0)
		{
			executeSql("update  lp_subject set subject_location = subject_location - 1 where subject_location >= "+subject.getSubject_location()+"");
		}
		getCurrentSession().createQuery("delete from SubjectDetail where subject_id=?").setParameter(0, subject_id).executeUpdate();
		getCurrentSession().createQuery("delete from Subject where subject_id=?").setParameter(0, subject_id).executeUpdate();
		return true;
	}
	/**
	 * 根据subject_id查询专题
	 */
	@Override
	public Subject findById(String subject_id) {
		Subject subject =(Subject) getCurrentSession().createQuery("from Subject where subject_id=?").setParameter(0, subject_id).uniqueResult();
//		if(subject.getSubject_img() != null)
//		{
//			subject.setSubject_img(subject.getSubject_img().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+"/lpSubjectImg", LaipaiConstants.UPLOAD_VIRTUAL_IMG+"/lpSubjectImg"));
//		}
		subject.setSubject_img(ImgUtil.getImgUrl(subject.getSubject_img()));
		return subject;
	}
	/**
	 * 修改专题
	 */
	@Override
	public void modifySubject(Subject subject, List<LpGalary> selectGallery) {
		subject.setCreate_time(new Date());
		subject.setModify_time(new Date());
		long viewCount = 0l;
		long likeCount = 0l;
		for (LpGalary gallery : selectGallery) 
		{
			subject.getSet().add(gallery);
			viewCount += gallery.getViewNumber();
			likeCount += gallery.getLikeNumber();
		}
		subject.setGallery_number(selectGallery.size());
		BigDecimal viewNumber = new BigDecimal(viewCount);
		BigDecimal likeNumber = new BigDecimal(likeCount);
		subject.setAccess_number(viewNumber);
		subject.setLike_number(likeNumber);
		getCurrentSession().saveOrUpdate(subject);
	}
	/**
	 * 查询所有作品集
	 */
	@Override
	public List<LpGalary> findAllGallery(String where) {
		List<LpGalary> list1 = null;
		if(where != null)
		{
			 list1 = getCurrentSession().createQuery("from LpGalary where galaryStatus = 0 and status = 0 " + where).list();
			List<LpGalary> list2 = new ArrayList<LpGalary>();
			if(list1 != null && list1.size() != 0)
			{
				for (LpGalary lpGalary : list1) 
				{
//					if(lpGalary.getGalaryCover() != null)
//					{
//						lpGalary.setGalaryCover(lpGalary.getGalaryCover().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+SimpleImage.LP_GALLERY_IMGURL));
//					}
					lpGalary.setGalaryCover(ImgUtil.getImgUrl(lpGalary.getGalaryCover()));
					list2.add(lpGalary);
				}
			}
			
			return list2;
		}
		else
		{
			List<LpGalary> list = getCurrentSession().createQuery("from LpGalary  where galaryStatus = 0 and status = 0 ").list();
			List<LpGalary> list3 = new ArrayList<LpGalary>();
			if(list != null && list.size() != 0)
			{
				for (LpGalary lpGalary : list) 
				{
					if(lpGalary.getGalaryCover()!= null)
					{
						lpGalary.setGalaryCover(ImgUtil.getImgUrl(lpGalary.getGalaryCover()));
					}
					
					list3.add(lpGalary);
				}
			}
			return list3;
		}
		
	}
	/**
	 * 对选中的作品根据序号排序
	 */
	@Override
	public List<SubjectDetail> findDetailList(String subject_id) {
		List<SubjectDetail> detailList = getCurrentSession().createQuery("from SubjectDetail where subject_id = ? order by subjectGalaryLocation").setParameter(0, subject_id).list();
		if(detailList != null && detailList.size()!= 0)
		{
		if(detailList.get(0).getSubjectGalaryLocation() == 0)
		{
			detailList = getCurrentSession().createQuery("from SubjectDetail where subject_id = ? ").setParameter(0, subject_id).list();
			int i = 0;
			for (SubjectDetail subjectDetail : detailList)
			{
				i++;
				subjectDetail.setSubjectGalaryLocation(i);
				getCurrentSession().update(subjectDetail);
			}
		}
		}
		return detailList;
	}
	@Override
	public long getSubjectCount(int pageSize) {
		Long count = (Long)getCurrentSession().createQuery("select count(*) from Subject").uniqueResult();
		long pageCount = count / pageSize;
		if(count % pageSize != 0)
		{
			pageCount ++;
		}
		return pageCount;
	}
	@Override
	public int countSum() {
		return this.getCount("from Subject");
	}
	/**
	 * 编辑专题
	 */
	@Override
	public boolean saveOrUpdateSubject(Subject subject) {
		String subject_id = subject.getSubject_id();
		List<SubjectDetail> selectedGalary = null;
		subject.setCreate_time(new Date());
		if(subject.getSubject_status() == 1)
		{
			subject.setOnline_time(new Date());
		}
		if(subject.getSubject_id() != null)
		{
			selectedGalary = getCurrentSession().createQuery("from SubjectDetail where subject_id = '"+subject.getSubject_id()+"' order by subjectGalaryLocation ").list();
			Integer index_location = (Integer)getCurrentSession().createQuery("select index_location from Subject where subject_id = '"+subject.getSubject_id()+"'").uniqueResult();
			//得到修改之前的专题位置
			Integer subject_location = (Integer)getCurrentSession().createQuery("select subject_location from Subject where subject_id = '"+subject.getSubject_id()+"'").uniqueResult();
			//修改首页位置；
			String oldindexId = null;
			if(subject.getIndex_location() != 0)
			{
				oldindexId = (String)getCurrentSession().createQuery("select subject_id from Subject where index_location = "+subject.getIndex_location()+"").uniqueResult();
			}
			String oldsubjectId = (String)getCurrentSession().createQuery("select subject_id from Subject where subject_location = "+subject.getSubject_location()+" ").uniqueResult();
			/*if(index_location != 0)
			{
				executeSql("update  lp_subject set index_location = "+index_location+" where subject_id = '"+oldindexId+"'");
			}*/
			executeSql("update  lp_subject set index_location = "+index_location+" where subject_id = '"+oldindexId+"'");
			executeSql("update  lp_subject set subject_location = "+subject_location+" where subject_id = '"+oldsubjectId+"'");
		}else
		{
			List<Integer> locations = getCurrentSession().createQuery("select subject_location from Subject ").list();
			List<Integer> indexlocations = getCurrentSession().createQuery("select index_location from Subject ").list();
			//判断是否有已存在的专题
			if(locations.contains(subject.getSubject_location()))
			{
				executeSql("update  lp_subject set subject_location = subject_location + 1 where subject_location >= "+subject.getSubject_location()+"");
			}
			if(indexlocations.contains(subject.getIndex_location()))
			{
				if(subject.getIndex_location() != 0)
				{
					executeSql("update  lp_subject set index_location = index_location + 1 where index_location >= "+subject.getIndex_location()+"");
				}	
			}
		}
		
		if(selectedGalary != null)
		{
			for (SubjectDetail subjectDetail : selectedGalary) 
			{
				LpGalary galary = (LpGalary)getCurrentSession().createQuery("from LpGalary where galaryId='"+subjectDetail.getGallery_id()+"' and status = 0 ").uniqueResult();
				try {
					subject.getSet().add(galary);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		try {
			this.getCurrentSession().merge(subject);
			//this.saveOrUpdate(subject);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	/*	List<SubjectDetail> allSubjectDetail = allSubjectDetail(subject_id);
		for (SubjectDetail subjectDetail : allSubjectDetail) 
		{
			System.out.println(subjectDetail.getSubjectGalaryLocation());
		}*/
		return true;
	}
	@Override
	public List<Subject> findIndexList() {
		List<Object[]> list1 = getCurrentSession().createQuery("select subject_id,subject_name,index_location,subject_location from Subject").list();
		List<Subject> list2 = new ArrayList<Subject>();
		Subject subject = null;
		for (Object[] objects : list1) 
		{
			subject = new Subject();
			subject.setSubject_id((String)objects[0]);
			subject.setSubject_name((String)objects[1]);
			subject.setIndex_location((Integer)objects[2]);
			subject.setSubject_location((Integer)objects[3]);
			list2.add(subject);
		}
		return list2;
	}
	@Override
	public void updateLocation(String index_subject_id,
			String subject_subject_id,String nowLocation) {
		Integer subject_location = (Integer)getCurrentSession().createQuery("select subject_location from Subject where subject_id = '"+nowLocation+"'").uniqueResult();
			executeSql("update lp_subject set subject_location = "+subject_location+" where subject_id='"+subject_subject_id+"'");
	}
	@Override
	public void updateIndexLocation(String index_subject_id,
			String subject_subject_id,String nowLocation) {
		Integer index_location = (Integer)getCurrentSession().createQuery("select index_location from Subject where subject_id = '"+nowLocation+"'").uniqueResult();
			executeSql("update lp_subject set index_location = "+index_location+" where subject_id='"+index_subject_id+"'");
	}
	@Override
	public void deleteGalary(String subjectId, String galaryId) {
		try {
			SubjectDetail subjectDetail =  (SubjectDetail)getCurrentSession().createQuery("from SubjectDetail where subject_id = '"+subjectId+"' and gallery_id = '"+galaryId+"'").uniqueResult();
			int location = subjectDetail.getSubjectGalaryLocation();
			getCurrentSession().delete(subjectDetail);
			List<SubjectDetail> subjectDetailList = getCurrentSession().createQuery("from SubjectDetail where subject_id = ?").setParameter(0, subjectId).list();
			for (SubjectDetail subjectDetail2 : subjectDetailList)
			{
				if(subjectDetail2.getSubjectGalaryLocation() > location)
				{
					subjectDetail2.setSubjectGalaryLocation(subjectDetail2.getSubjectGalaryLocation() -1);
					getCurrentSession().update(subjectDetail2);
				}
			}
			
			Integer viewCount = 0;
			Integer likeCount = 0;
			//得到选中专题的作品集合
			List<LpGalary> selectedGalary = selectedGalaryList(subjectId);
			for (LpGalary lpGalary : selectedGalary)
			{
				viewCount += lpGalary.getViewNumber();
				likeCount += lpGalary.getLikeNumber();
			}
			executeSql("update  lp_subject set gallery_number = "+selectedGalary.size()+" where subject_id='"+subjectId+"'");
			executeSql("update  lp_subject set access_number = "+viewCount+" where subject_id='"+subjectId+"'");
			executeSql("update  lp_subject set like_number = "+likeCount+" where subject_id='"+subjectId+"'");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @Description:获取专题选中的作品集
	 * @param subject_id
	 * @return
	 * List<LpGalary>
	 * @exception:
	 * @author: zhuxin
	 * @time:2015-1-15 下午1:43:31
	 */
	public List<LpGalary> selectedGalaryList(String subject_id)
	{
		List<LpGalary> selectGalaryList = new ArrayList<LpGalary>();
		List<SubjectDetail> subjectDetailList = getCurrentSession().createQuery("from SubjectDetail where subject_id = ?").setParameter(0, subject_id).list();
		int i = 0;
		for (SubjectDetail subjectDetail : subjectDetailList) 
		{
			/*String gallery_id = subjectDetail.getGallery_id();
			String isExitGallery = (String)getCurrentSession().createSQLQuery("select galary_id from lp_galary where galary_id = '"+gallery_id+"' and status = 0").uniqueResult();
			if(isExitGallery == null && !"".equals(isExitGallery))
			{
				getCurrentSession().createSQLQuery("delete from lp_subject_detail where gallery_id = '"+gallery_id+"'");
			}*/
			LpGalary galary = (LpGalary)getCurrentSession().createQuery("from LpGalary where galaryId='"+subjectDetail.getGallery_id()+"' and status = 0 ").uniqueResult();
			if(galary != null && !"".equals(galary))
			{
				selectGalaryList.add(galary);
			}
			
		}
		return selectGalaryList;
	}
	/**
	 * 修改专题详情的位置
	 */
	@Override
	public void updateSubjectDetailLocation(SubjectDetail subjectDetail,int oldLocation) {
		SubjectDetail old = (SubjectDetail)getCurrentSession().createQuery("from SubjectDetail where subject_id='"+subjectDetail.getSubject_id()+"' and subjectGalaryLocation = "+subjectDetail.getSubjectGalaryLocation()+"").uniqueResult();
		old.setSubjectGalaryLocation(oldLocation);
		getCurrentSession().update(old);
		try {
			getCurrentSession().update(subjectDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public int getMaxLocation() {
		Query query=this.getCurrentSession().createQuery("from Subject order by subject_location desc");
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<Subject> subjectList=query.list();
		int location = 0;
		if(subjectList != null && subjectList.size() > 0)
		{
			location = subjectList.get(0).getSubject_location();
		}
		return location;
	}
	@Override
	public void updateSubjectLocation(String subject_id, int newLocation) {
		//首先得到旧位置
		Integer oldSubject_location = (Integer)getCurrentSession().createQuery("select subject_location from Subject where subject_id = '"+subject_id+"'").uniqueResult();
		//然后根据新位置得到将要被修改的专题Id
		String willInstead = (String)getCurrentSession().createQuery("select subject_id from Subject where subject_location = "+newLocation+"").uniqueResult();
		if(willInstead != null && !"".equals(willInstead))
		{
			executeSql("update  lp_subject set subject_location = "+oldSubject_location+" where subject_id = '"+willInstead+"'");
		}
		//String oldId = (String) getCurrentSession().createQuery("select subject_id from Subject where subject_location = "+oldSubject_location+"").uniqueResult();
		//将此专题Id的位置修改
		executeSql("update  lp_subject set subject_location = "+newLocation+" where subject_id = '"+subject_id+"'");
		//将原来位置的专题位置修改
	}
	@Override
	public List<LpGalary> allGalaryList(int nowPage, int pageSize, int scanType) {
		String where = null;
		switch (scanType) {
		case 1:
			where = " order by creat_time ";
			break;

		case 2:
			where = " order by galary_id ";
			break;
		case 3:
			where = " order by user_id ";
			break;
		}
		Query query = getCurrentSession().createQuery("from LpGalary where status = 0 and galaryStatus=0 "+ where);
		query.setFirstResult((nowPage - 1)*pageSize);
		query.setMaxResults(pageSize);
		List<LpGalary> list = null;
		try {
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public long countGalarySum() {
		Long count = (Long)getCurrentSession().createQuery("select count(*) from LpGalary where status = 0 ").uniqueResult();
		return count;
	}
}
