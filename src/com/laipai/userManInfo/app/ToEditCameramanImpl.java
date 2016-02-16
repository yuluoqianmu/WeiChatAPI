package com.laipai.userManInfo.app;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.cameraManInfo.dto.CameraBean;
import com.laipai.cameraManInfo.service.ICameraManService;
import com.laipai.operationManage.dto.LpStyleBean;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.userManInfo.dto.LpUserShowBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
/**
 * 

 * @Description:跳转编辑用户信息界面

 * @author:lxd

 * @time:2015-1-9 下午4:15:31
 */
@NotLogin
@Service("ToEditCameramanImpl")
public class ToEditCameramanImpl implements RequestExecutor {
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService;
	
	@Resource(name=ICameraManService.SERVICE_NAME)
	private ICameraManService cameraManService;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		//获取服务器域名
	    String serverName = json.getString("serverName");
		JSONObject resulejson = new JSONObject();
		
		String puserId=JSONTools.getString(json,"userId");
		if(puserId==null||"".equals(puserId)){			
			return JSONTools.newAPIResult(1, "参数不能为空");	
		}
		Map map=new HashMap();
		CameraBean userBean =new CameraBean();
		LpUser user= userManInfoService.queryUserById(puserId);
		if(user!=null){			
		 LpCity city= user.getLpCity();
		 if(city!=null){
			 String cityName=city.getCityName();
			 userBean.setCity(cityName);
			 userBean.setCityId(city.getCityId());
		 }
		 userBean.setUserId(user.getUserId());
		 userBean.setUserName(user.getUserName());
		 userBean.setGrapherCarmer(user.getGrapherCarmer());
		 Integer gender= user.getUserGender();
		 if(gender!=null){
			 if(gender==0){
				 userBean.setUserGender("男");	 
				 
			 }else{
				 userBean.setUserGender("女");	 
				 
			 }
			 
		 }
		 userBean.setUserType(user.getUserType());
		 userBean.setNickName(user.getNickName());
/*		 userBean.setRealName(user.getRealName());*/
		 userBean.setMobile(user.getMobile());
		 userBean.setGrapherDesc(user.getGrapherDesc());
		
		}
		map.put("camera", userBean);		
		List<LpStyle> allStylelist=new ArrayList<LpStyle>();		
		allStylelist = cameraManService.getallStyle();
		List<LpStyleBean> list=new ArrayList<LpStyleBean>();
		if(allStylelist!=null&&allStylelist.size()>0){
			for(LpStyle style:allStylelist){
				LpStyleBean styleBean =new LpStyleBean();
				styleBean.setStyleId(style.getStyleId());
				styleBean.setStyleName(style.getStyleName());
				
				list.add(styleBean);
			}
			
		}
		map.put("allStyle", list);
		
		List<LpStyle> cameraStylelist=new ArrayList<LpStyle>();		
		cameraStylelist = cameraManService.getstyleByuser(puserId);
		List<LpStyleBean> cameralist=new ArrayList<LpStyleBean>();
		if(cameraStylelist!=null&&cameraStylelist.size()>0){
			for(LpStyle style:cameraStylelist){
				LpStyleBean styleBean =new LpStyleBean();
				styleBean.setStyleId(style.getStyleId());
				styleBean.setStyleName(style.getStyleName());				
				cameralist.add(styleBean);
			}
			
		}
		map.put("cameraStyle", cameralist);
		resulejson=JSONObject.fromObject(map);
		return resulejson;
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
