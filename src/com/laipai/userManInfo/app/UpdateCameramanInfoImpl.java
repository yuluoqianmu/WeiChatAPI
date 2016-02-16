package com.laipai.userManInfo.app;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserFollowService;
import com.laipai.userManInfo.service.IUserManInfoService;
/**
 * 更改用户的信息

 * @Description:TODO

 * @author:lxd

 * @time:2015-1-7 下午3:53:50
 */
@NotLogin
@Service("UpdateCameramanInfoImpl")
public class UpdateCameramanInfoImpl implements RequestExecutor {
    @Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService;
    @Autowired
    private IOperationManagerService operationManagerService;
    @Autowired
		private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONArray styleIds=null;
		String stringStyles="";
		JSONObject json = parameters.getJson();
		JSONObject resulejson = new JSONObject();	
		System.out.println("访问开始");
		String userId=JSONTools.getString(json,"userId");
		String ChangeNickName=JSONTools.getString(json,"changeNickName");
		String osType=JSONTools.getString(json,"osType");
		String nickName=JSONTools.getString(json,"nickName");		
		LpUser user= userManInfoService.queryUserById(userId);
		if(user!=null){
        if("YES".equalsIgnoreCase( ChangeNickName)){		
		  user.setNickName(nickName);
		  userManInfoService.saveOrUpdateser(user);	
		  resulejson.put("resultMessage", "修该成功");
		  resulejson.put("changeName", nickName);
        }else{
        	//String nickName= JSONTools.getString(json,"nickName");
    		String userGender=JSONTools.getString(json,"userGender");
    		String cityId=JSONTools.getString(json,"cityId");
    		String mobile=JSONTools.getString(json,"mobile");
    		String grapherCarmer=JSONTools.getString(json,"grapherCarmer");
    		String grapherDesc=JSONTools.getString(json,"grapherDesc");

    		int gender= Integer.parseInt(userGender);
  		  user.setNickName(nickName);
  		  user.setUserGender(gender);
  		  user.setMobile(mobile);
  		  user.setGrapherCarmer(grapherCarmer);
  		  user.setGrapherDesc(grapherDesc);
  		  if(cityId!=null){
  		  LpCity city= (LpCity) baseDao.getObjectById(LpCity.class, cityId);
  		  user.setLpCity(city);  
  		  }
  		  
  		if(osType.equals("0")){		
  			Set<LpStyle> styles=new HashSet<LpStyle>();
   		 stringStyles=JSONTools.getString(json,"styleIds");
   		  String[] style_id=stringStyles.split(",");
   		  if(style_id.length>0){
   		  for(int i=0;i<style_id.length;i++){			
   			 LpStyle style= operationManagerService.findTheStyle(style_id[i]);
 			 styles.add(style);  
   		  }
   		user.setLpStyle(styles);
   		  }
   		}else{
   		styleIds=JSONTools.getJSONArray(json, "styleIds");
   		if(styleIds!=null&&styleIds.size()>0){
   			Set<LpStyle> styles=new HashSet<LpStyle>();
			  for(int i=0;i<styleIds.size();i++){
			   LpStyle style= operationManagerService.findTheStyle(styleIds.getString(i));
			styles.add(style);   			   
			  }
		  user.setLpStyle(styles);
		  
		}
   		}
  		 userManInfoService.saveOrUpdateser(user);       	
         }	
		}
		resulejson.put("resultMessage", "修该成功");
		return resulejson;
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
