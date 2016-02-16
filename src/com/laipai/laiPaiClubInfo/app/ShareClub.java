package com.laipai.laiPaiClubInfo.app;

import java.sql.Timestamp;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.logs.pojo.LpClubSharelog;
import com.laipai.logs.pojo.LpSubjectLog;
@NotLogin
@Service("ShareClub")
 public class ShareClub implements RequestExecutor {
	 @Autowired
		private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
		String shareType=JSONTools.getString(json,"shareType");
		String laipaiId=JSONTools.getString(json,"laipaiId");
		LpClubSharelog subjectLog=new LpClubSharelog();
		subjectLog.setUserId(userId);
		subjectLog.setShareArticleId(laipaiId);
		subjectLog.setShareTime(new Timestamp(new java.util.Date().getTime()));
		
		baseDao.save(subjectLog);
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
