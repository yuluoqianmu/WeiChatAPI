package com.laipai.message.app;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.message.pojo.LpMessageDetail;
import com.laipai.message.pojo.LpMessageMapping;
@NotLogin
@Service("UpdateMessageStatus")
public class UpdateMessageStatus implements RequestExecutor {
    @Autowired
    private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json=parameters.getJson();
		String messageId=JSONTools.getString(json,"messageId");
		String systemMessage=JSONTools.getString(json,"systemMesage");
		if("system".equals(systemMessage)){
			LpMessageDetail message= (LpMessageDetail) baseDao.getObjectById(LpMessageDetail.class, messageId);
			if(message!=null){
				message.setMessageStatus(1);
				baseDao.updateObject(message);
			}	
			
		
		
		}else{
			LpMessageMapping message= (LpMessageMapping) baseDao.getObjectById(LpMessageMapping.class, messageId);
			if(message!=null){
				message.setStatus(1);
				baseDao.updateObject(message);
			}		
		}
		JSONObject resulejson = new JSONObject();
		resulejson.put("resultMessae", "read");
		return resulejson;
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
