package com.laipai.userManInfo.app;

import java.sql.Timestamp;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.logs.pojo.LpCamermanSharelog;
@NotLogin
@Service("ShareCameramanImpl")
public class ShareCameramanImpl implements RequestExecutor {
	 @Autowired
		private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
		String cameramanId=JSONTools.getString(json,"cameramanId");
		String shareType=JSONTools.getString(json,"shareType");
		LpCamermanSharelog shareLog=new LpCamermanSharelog();
		shareLog.setUserId(userId);
		if(StringUtils.isNoneBlank(shareType)){
		   int type=Integer.parseInt(shareType);
			shareLog.setShareType(type);
		}
		shareLog.setCamermanId(cameramanId);
		shareLog.setShareTime(new Timestamp(new java.util.Date().getTime()));
		baseDao.save(shareLog);
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
