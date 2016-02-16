package com.laipai.userManInfo.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.UserUtil;
import com.laipai.img.ImgUtil;
import com.laipai.userManInfo.dao.IUserFollowDao;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.dto.UserBean;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserFollowService;
import com.laipai.userManInfo.service.IUserManInfoService;
/**
 * 

 * @Description:查询用户关注

 * @author:lxd

 * @time:2015-1-7 上午10:42:21
 */
@NotLogin
@Service("QueryUserFollowImpl")
public class QueryUserFollowImpl implements RequestExecutor {
    
	@Autowired
	private IUserFollowDao userFollowDao;
	
	@Autowired
	private IBaseDao baseDao;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
		//获取服务器域名
	    String serverName = json.getString("serverName");
	    String page=JSONTools.getString(json,"page");
	    //List<LpFollowView>  followlist= baseDao.queryListObjectAll("from LpFollowView where userId='"+userId+"'");
	    List<LpFollowView>  followlist=this.getfanslist(userId, page);
	    List<UserBean> list=new ArrayList<UserBean>();
		if(followlist!=null&&followlist.size()>0){
		 for(int i=0;i<followlist.size();i++){
			 UserBean userBean =new UserBean();
			 //LpFollowViewId followID= followView;
			 userBean.setUserId(followlist.get(i).getCameraId());
			 userBean.setUserName(followlist.get(i).getCameraName());
			 userBean.setNickName(followlist.get(i).getCameraNickName());
			 userBean.setNickName2(UserUtil.getUserName(followlist.get(i).getCameraNickName(), 1));
			 userBean.setUserType(followlist.get(i).getCameraType());
			 userBean.setFollowId(followlist.get(i).getFollowId());
			 String headImage=followlist.get(i).getCameraHeadImage();
//			 if(headImage.contains("/upload/lpUserImg")){				 
//				 userBean.setHeadImage(serverName + headImage.replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL)); 
//			 }else{
//				 
//				 userBean.setHeadImage(headImage);
//			 }
			 userBean.setHeadImage(ImgUtil.getImgUrl(headImage,parameters.getPicType()));
			 list.add(userBean);
		 }
		}
		 //JSONArray jsonArray = JSONArray.fromObject(list);
			return list;
		
	}
	
/*	private JSONObject userToJSON(LpUser user){
		JSONObject json = new JSONObject();
		json.put("userId", user.getUserId());
		json.put("userName", user.getUserName());
		json.put("nickName", user.getNickName());
		return json;
	}*/
	
	public List<LpFollowView> getfanslist(String userId,String pageStr){
		int page=1;
		String hql = "from LpFollowView where 1=1 and userId='"+userId+"' order by followTime DESC";
		List<LpFollowView> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 10, page);
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
