package com.laipai.userManInfo.app;

import java.sql.Timestamp;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.userManInfo.pojo.LpFeedback;
/**
 * 

 * @Description:添加用户反馈

 * @author:lxd

 * @time:2015-1-12 下午1:36:50
 */
@NotLogin
@Service("AddUserFeedBackImpl")
public class AddUserFeedBackImpl implements RequestExecutor {
    @Autowired
	private IBaseDao baseDao;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
		String userQQ=JSONTools.getString(json, "userQQ");
		String feedBackComment=JSONTools.getString(json, "feedBackComment");
		if(userId==null||userQQ==null||feedBackComment==null){			
			return JSONTools.newAPIResult(1, "参数不能为空");	
		}
		LpFeedback feedBack=new LpFeedback();
		feedBack.setUserId(userId);
		feedBack.setUserQq(userQQ);
		feedBack.setUserContent(feedBackComment);
		feedBack.setFeedTime(new Timestamp(new java.util.Date().getTime()));
		baseDao.save(feedBack);
		JSONObject resulejson = new JSONObject();	
		resulejson.put("resultMessage", "提交成功");
		return resulejson;
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
