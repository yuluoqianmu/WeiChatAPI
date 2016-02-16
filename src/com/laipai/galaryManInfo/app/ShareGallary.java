package com.laipai.galaryManInfo.app;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import net.sf.json.JSONObject;

import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.logs.pojo.LpGalaryLog;
import com.laipai.logs.pojo.LpGalarySharelog;
import com.laipai.serviceInfo.pojo.LpServiceStyle;

public class ShareGallary  implements RequestExecutor {
	 @Autowired
		private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
		String gallaryId=JSONTools.getString(json,"galaryId");
		LpGalary gallary=  (LpGalary) baseDao.getObjectById(LpGalary.class, gallaryId);
		if(gallary.getLpService()!=null){
		     String detailId=gallary.getLpService().getLpServiceDetail().getDetailId();
			 List<LpServiceStyle> liststyle=baseDao.queryListObjectAll("from LpServiceStyle where detailId='"+detailId+"'");
			 if(liststyle!=null&&liststyle.size()>0){
				 for(int i=0;i<liststyle.size();i++){
					 String styleId=liststyle.get(i).getStyleId();
					 LpGalarySharelog sharalog=new LpGalarySharelog();
						sharalog.setGalaryId(gallaryId);
						sharalog.setUserId(userId);
						
	
		/*				if(StringUtils.isBlank(userId)){
							
							gallaryLog.setMachineId(machineId);
							}*/
						
						sharalog.setStyleId(styleId);
						
						sharalog.setShareTime(new Timestamp(new java.util.Date().getTime()));
						
						baseDao.save(sharalog);
				 }
				 
			 }
			}
		
		JSONObject resulejson = new JSONObject();	
		return resulejson;
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
