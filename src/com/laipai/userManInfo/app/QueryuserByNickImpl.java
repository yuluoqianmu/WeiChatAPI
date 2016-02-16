package com.laipai.userManInfo.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.UserUtil;
import com.laipai.img.ImgUtil;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.dto.UserShowBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
/**
 * 

 * @Description:根据用户昵称查询用户列表

 * @author:lxd

 * @time:2015-2-13 上午10:43:06
 */
@NotLogin
@Service("QueryuserByNickImpl")
public class QueryuserByNickImpl implements RequestExecutor {
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		//获取服务器域名
	    String serverName = json.getString("serverName");
	    
	    String nickName=JSONTools.getString(json,"nickName");
	    
	    String sql="SELECT * FROM lp_user l WHERE UPPER(l.nick_name) LIKE '%"+nickName+"%' and l.user_status=0 and l.user_type=1";
	    List list= userManInfoService.queryUserByNickName(sql);
	    List<UserShowBean> newlist= new ArrayList<UserShowBean>();
	    if(list!=null&&list.size()>0){
             for(int i=0;i<list.size();i++){
            	 LpUser user=(LpUser) list.get(i);
	    		UserShowBean userBean=new UserShowBean();
	    		userBean.setUserId(user.getUserId());
	    		userBean.setNickName(user.getNickName());
	    		userBean.setNickName2(UserUtil.getUserName(user.getNickName(), 1));
	    		userBean.setUserType(user.getUserType());

	    		userBean.setHeadImage(ImgUtil.getImgUrl(user.getHeadImage(),parameters.getPicType()));
	    		
	    		if(user.getLpCity()!=null){
	    			userBean.setCityName(user.getLpCity().getCityName());	    			
	    		}else{
	    			
	    			userBean.setCityName("");
	    		}
	    		newlist.add(userBean);
             }
	    	
	    }
		return newlist;
	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
