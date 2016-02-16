package com.laipai.cameraCheck.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.operationManage.dto.LpCityBean;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.service.IOperationManagerService;
/**
 * 

 * @Description:获取所有的上线城市

 * @author:lxd

 * @time:2015-1-6 下午5:09:11
 */
@NotLogin
@Service("QueryAllOnlineCityImpl")
public class QueryAllOnlineCityImpl implements RequestExecutor {
    @Autowired
	private IOperationManagerService operationManagerService;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		List<LpCity> listcity=operationManagerService.getAllOnlineCity();
		List<LpCityBean> list=new ArrayList<LpCityBean>();
		if(listcity!=null&&listcity.size()>0){
			for(LpCity city:listcity){
				
				LpCityBean cityBean =new LpCityBean();
				BeanUtils.copyProperties(city, cityBean);
				list.add(cityBean);
			}
			
		}
		
		
		return list;
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
