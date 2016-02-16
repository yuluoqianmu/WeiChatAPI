package com.laipai.serviceInfo.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
@NotLogin
@Service("ServiceExecutorImpl")
public class QueryServiceExecutorImpl implements RequestExecutor {

	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	
	/**
	 * 根据摄影师ID查询摄影师的服务
	 */
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json=parameters.getJson();
		String cameraId=json.getString("userId");
		if(StringUtils.isEmpty(cameraId)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		String hql="from LpService ls where ls.lpUser.userId='"+cameraId+"'";
		List<LpService> serviceList=baseDao.queryListObjectAll(hql);
		if(serviceList==null || serviceList.isEmpty()){
			return JSONTools.newAPIResult(1, "--快来创建服务吧--");
		}
		JSONArray service=serviceListToJson(serviceList);
		return service;
	}

	private JSONArray serviceListToJson(List<LpService> serviceList) {
		List<Map> list=new ArrayList<Map>();
		Map<String,Object> sizeMap=new HashMap<String,Object>();
		for(LpService service:serviceList){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("serviceId", service.getServiceId());
			map.put("serviceName", service.getServiceName());
			map.put("pictureNum", service.getLpServiceDetail().getPictureNum());
			map.put("price", ((double)service.getLpServiceDetail().getPrice())/100);
			map.put("priceUnit", service.getLpServiceDetail().getPriceUnit());
			list.add(map);
		}
		list.add(sizeMap);
		JSONArray array=JSONArray.fromObject(list);
		return array;
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
