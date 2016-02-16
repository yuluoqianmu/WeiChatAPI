package com.laipai.subject.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.img.ImgUtil;
import com.laipai.logs.pojo.LpFindLog;
import com.laipai.subject.bean.SubjectBean;
import com.laipai.subject.pojo.Subject;
import com.laipai.subject.pojo.VLpSubjectDetailApp;
import com.laipai.subject.pojo.VLpSubjectListAppShow;
import com.lyz.util.BaseTypeUtil;
import com.lyz.util.MD5Generator;

@NotLogin
@Service("SubjectListExecutorImpl")
public class SubjectListExecutorImpl implements RequestExecutor{

	/** 
	 * @Fields mobileDeviceDAO : 处理手机是什么类型的（android,苹果） 
	 */  
	@Resource
	private IMobileDeviceDAO mobileDeviceDAO;
	
	@Resource
	private IBaseDao baseDao;
	
	
	/*public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String serverName = json.getString("serverName");
		String listSubject = JSONTools.getString(json, "listSubject");
		List<Subject> subjectList = null;		
		if(listSubject == null)
		{
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		if("subjectLocation".equals(listSubject))
		{
			subjectList = baseDao.queryListObjectAll("from Subject where subject_location < 99 and subject_status = 1 order by subject_location");
		}
		if("indexLocation".equals(listSubject))
		{
			subjectList = baseDao.queryListObjectAll("from Subject where index_location > 0 and subject_status = 1 order by index_location");
		}
		List<SubjectBean> list = new ArrayList<SubjectBean>();
		if(subjectList.size() % 2 != 0)
		{
			subjectList.remove(subjectList.size()-1);
		}
		for (Subject subject : subjectList) 
		{
			SubjectBean bean = new SubjectBean();
			bean.setSubject_id(subject.getSubject_id());
			bean.setSubject_name(subject.getSubject_name());
			bean.setIndex_location(subject.getIndex_location());
			bean.setSubject_location(subject.getSubject_location());
			bean.setSubject_img(subject.getSubject_img());
			bean.setSubject_img(serverName + bean.getSubject_img().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+"/lpSubjectImg", LaipaiConstants.UPLOAD_VIRTUAL_IMG+"/lpSubjectImg"));
			if(subject.getAccess_number() == null)
			{
				bean.setAccess_number(0);
			}else
			{
				bean.setAccess_number(subject.getAccess_number().intValue());
			}
//			if(subject.getLike_number() == null)
//			{
//				bean.setLike_number(0);
//			}
//			else
//			{
//				bean.setLike_number(subject.getLike_number().intValue());
//			}
			bean.setLike_number("1K");
			list.add(bean);
		}*/
		
		
		public Object execute(BaseRequestParameters parameters,
				Object... requestObjects) {
			JSONObject json = parameters.getJson();
//			String serverName = json.getString("serverName");
			String listSubject = JSONTools.getString(json, "listSubject");
			 String page=JSONTools.getString(json,"page");
//			 String userId=JSONTools.getString(json,"userId");
//			 String machineId=JSONTools.getString(json,"imei");
			List<Subject> subjectList = null;
			String hql="";
			if(listSubject == null) {
				return JSONTools.newAPIResult(1, "参数不能为空");
			}
			if("subjectLocation".equals(listSubject)) {
				hql="from Subject where subject_status = 1 order by subject_location";
				subjectList = this.getVLpSubjectDetailAppBySubjectId(hql, page);
			}
			if("indexLocation".equals(listSubject)) {
				hql="from VLpSubject where index_location > 0 and subject_status = 1 order by index_location";
				subjectList = getVLpSubjectDetailAppBySubjectId(hql,page);
			}
			List<SubjectBean> list = new ArrayList<SubjectBean>();
//			if(subjectList.size() % 2 != 0) {
//				subjectList.remove(subjectList.size()-1);
//			}
			for (Subject subject : subjectList) {
				SubjectBean bean = new SubjectBean();
				bean.setSubject_id(subject.getSubject_id());
				bean.setSubject_name(subject.getSubject_name());
				bean.setIndex_location(subject.getIndex_location());
				bean.setSubject_location(subject.getSubject_location());
//				bean.setSubject_img(subject.getSubject_img());
				bean.setSubject_img(ImgUtil.getImgUrl(subject.getSubject_img2(),parameters.getPicType()));
				if(subject.getAccess_number()== null) {
					bean.setAccess_number(0);
				} else {
					bean.setAccess_number(subject.getAccess_number().intValue());
				}
				
				//重新计算点赞数
				String likeNum = "0";
				try {
					String sql  = "select ifnull(sum(a.like_number),0) like_number from ( "
								 +" SELECT sum(ifnull(lge.like_number,0))  like_number "  
								 +" FROM  lp_galary lg "
								 +" left join  lp_galary_extend lge on lge.galary_id = lg.galary_id "  
								+"  where lg.control_source = 1 "
								+" and lg.galary_status=0 "
								+" and lg.status=0 "
								+"  and lge.extend_id is not null "
								+"  and lg.galary_id in (SELECT lp_subject_detail.gallery_id FROM lp_subject_detail where lp_subject_detail.subject_id ='"+subject.getSubject_id()+"') "
								+" union all "
								+"  SELECT sum(ifnull(lg.like_number,0))  like_number "
								+"  FROM  lp_galary lg    "
								+"  where  lg.galary_status=0 "
								+" and lg.status=0 "
								+" and  lg.control_source = 0 "
								+"  and  lg.galary_id in (SELECT lp_subject_detail.gallery_id FROM lp_subject_detail where lp_subject_detail.subject_id ='"+subject.getSubject_id()+"') "
								+" ) a ";
					List LikeList = baseDao.querySqlObject(sql);
					if(LikeList!=null && !LikeList.isEmpty()){
						Map map = (Map) LikeList.get(0);
						int num = ((BigDecimal) map.get("like_number")).intValue();
						if(num >999){
							likeNum = (int)Math.floor(num/1000)+"K";
						}else{
							likeNum = num+"";
						}
					}
					bean.setLike_number(likeNum);
				} catch (Exception e) {
					e.printStackTrace();
				}
				list.add(bean);
			}
		//保存日志
//		if("1".equals(page)){
//			LpFindLog findLog=new LpFindLog();
//			findLog.setUserId(userId);
//			findLog.setAccessTime(new Timestamp(new java.util.Date().getTime()));
//			 if(StringUtils.isBlank(userId)){
//				 
//				 findLog.setMachineId(machineId); 
//			 }
//			
//			baseDao.save(findLog);
//			
//		}	
			
			
		return list;
	}
 private List<Subject> getVLpSubjectDetailAppBySubjectId(String hql,String pageStr ){		
//			int page=1;
			//String hql = "from VLpSubjectDetailApp where subjectId='"+subjectId+"'";
			List<Subject> list = null;
			if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
				list = this.baseDao.queryListObjectAll(hql);
			}else{
//				page = Integer.parseInt(pageStr);
				list = this.baseDao.queryListObjectAllForPage(hql, 10,  BaseTypeUtil.getInteger(pageStr,1));
			}
			
			return list;
		}
@Override
public String getCacheKey(BaseRequestParameters parameters,
		Object... requestObjects) {
	
	JSONObject json = parameters.getJson();
//	String serverName = json.getString("serverName");
	String listSubject = JSONTools.getString(json, "listSubject");
	String page=JSONTools.getString(json,"page");
	
	StringBuilder builder = new StringBuilder();
	builder.append("listSubject=").append(listSubject).append("&")
	.append("page=").append(page);
	
	return "subjectList:"+MD5Generator.MD5(builder.toString());
}
}
