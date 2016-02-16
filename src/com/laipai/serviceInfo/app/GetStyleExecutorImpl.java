package com.laipai.serviceInfo.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
@NotLogin
@Service("GetStyleExecutorImpl")
public class GetStyleExecutorImpl implements RequestExecutor{

	@Autowired
	private IServiceService serviceService;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json=parameters.getJson();
		String userId=json.getString("userId");
		if(StringUtils.isEmpty(userId)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		List<LpStyle> styleList=serviceService.getStyle(userId);
		if(styleList!=null&&styleList.size()>0){
			JSONArray array=listToJson(styleList);
			return array;
		}else{
			return JSONTools.newAPIResult(1, "查询风格失败");
		}
	}

	private JSONArray listToJson(List<LpStyle> styleList) {
		List<Map> list=new ArrayList<Map>();
		for(LpStyle style:styleList){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("styleId", style.getStyleId());
			map.put("styleName",style.getStyleName());
			list.add(map);
		}
		return JSONArray.fromObject(list);
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
