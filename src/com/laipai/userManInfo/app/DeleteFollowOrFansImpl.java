package com.laipai.userManInfo.app;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.service.IUserFollowService;
/**
 * 

 * @Description:删除关注

 * @author:lxd

 * @time:2015-1-9 下午5:22:53
 */
@NotLogin
@Service("DeleteFollow")
public class DeleteFollowOrFansImpl implements RequestExecutor {
    
	@Autowired
    private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String followId=JSONTools.getString(json,"followId");
		if(followId==null||"".equals(followId)){			
			return JSONTools.newAPIResult(1, "参数不能为空");	
		}
		try{
			LpFollow follow= (LpFollow) baseDao.getObjectById(LpFollow.class, followId);
			
			baseDao.delete(follow);
			
		}catch(Exception e){
			
			return JSONTools.newAPIResult(1, e.toString());
		}
		JSONObject resulejson = new JSONObject();
		resulejson.put("resuleMessae", "删除成功");
		return resulejson;
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
