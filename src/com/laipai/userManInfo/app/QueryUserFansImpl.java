package com.laipai.userManInfo.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.pojo.VLpGalaryAppShow;
import com.laipai.img.ImgUtil;
import com.laipai.userManInfo.dao.IUserFollowDao;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.dto.UserBean;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.service.IUserFollowService;
/**
 * 

 * @Description:查询用户的粉丝

 * @author:lxd

 * @time:2015-1-7 下午2:44:00
 */
@NotLogin
@Service("QueryUserFansImpl")
public class QueryUserFansImpl implements RequestExecutor {
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private IUserFollowDao userFollowDao;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
		//获取服务器域名
	    String serverName = json.getString("serverName");
	    String page=JSONTools.getString(json,"page");
		if(userId==null&&"".equals(userId)){
			return JSONTools.newAPIResult(1, "参数不能为空");				
		}
		//List<LpFollowView>  followlist= userFollowDao.queryFansByuserId("select * from lp_follow_view l where l.camera_id='"+userId+"'");
		
		List<LpFollowView>  followlist=this.getfanslist(userId, page);
		List<UserBean> list=new ArrayList<UserBean>();
		if(followlist!=null&&followlist.size()>0){
		for(int i=0;i<followlist.size();i++){
			 UserBean userBean =new UserBean();
			 //LpFollowViewId followID= followView;
			 userBean.setUserId(followlist.get(i).getUserId());
			 userBean.setUserName(followlist.get(i).getUserName());
			 userBean.setNickName(followlist.get(i).getUserNickName());
			 userBean.setUserType(followlist.get(i).getUserType());
			 userBean.setFollowId(followlist.get(i).getFollowId());
			 String headImage=followlist.get(i).getUserHeadImage();
//			 if(headImage!=null && headImage.contains("/upload/lpUserImg")){				 
//				 userBean.setHeadImage(serverName + headImage.replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL)); 
//			 }else{
//				 
//				 userBean.setHeadImage(headImage);
//			 }
			 userBean.setHeadImage(ImgUtil.getImgUrl(headImage,parameters.getPicType()));
			 list.add(userBean);
		 }
		}
		return list;
	}
	
	
	
	public List<LpFollowView> getfanslist(String userId,String pageStr){
		int page=1;
		String hql = "from LpFollowView where 1=1 and cameraId='"+userId+"' order by followTime DESC";
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
