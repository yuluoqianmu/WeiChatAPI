package com.laipai.serviceInfo.app;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.serviceInfo.service.IServiceService;
@NotLogin
@Service("DeleteServiceExecutorImpl")
public class DeleteServiceExecutorImpl implements RequestExecutor{

	@Autowired
	private IServiceService serviceService;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json=parameters.getJson();
		String serviceId=json.getString("serviceId");
		if(StringUtils.isEmpty(serviceId)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		try {
			serviceService.deleteById(serviceId);
			return JSONTools.newAPIResult(0, "删除服务成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "删除服务失败");
		}
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
