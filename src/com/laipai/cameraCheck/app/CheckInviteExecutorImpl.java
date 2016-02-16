package com.laipai.cameraCheck.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.cameraCheck.pojo.LpInvite;
import com.laipai.cameraCheck.service.IInviteService;
import com.laipai.operationManage.dto.LpStyleBean;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
/***
 * 

 * @Description:验证用户的邀请码

 * @author:lxd

 * @time:2015-1-6 下午2:03:42
 */
@NotLogin
@Service("CheckInviteExecutorImpl")
public class CheckInviteExecutorImpl implements RequestExecutor {
    @Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService  userManInfoService;
    @Resource(name=IInviteService.IINVITESERVICE_NAME)
    private IInviteService inviteService;
    @Autowired
    private IOperationManagerService operationManagerService;
    @Autowired
    private IBaseDao baseDao;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		String userId = JSONTools.getString(json, "userId");
        String invitecode=JSONTools.getString(json,"invite");
    	if (userId == null || invitecode == null) {
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		
    	List<LpCameramanAppform> listappform= baseDao.queryListObjectAll("from LpCameramanAppform where lpUser.userId='"+userId+"' and checkStatus=0");
	
    	LpInvite invite =inviteService.getInviteByCode(invitecode);
    	if(invite==null){    		
    		return JSONTools.newAPIResult(1, "该验证码不存在 ");
    	}
    	if(listappform!=null&&listappform.size()>0){   		
    		return JSONTools.newAPIResult(1, "您提交的申请正在审核，请不要重复提交");
  		
    	}
    	invite=inviteService.getInvite(invitecode);
    	if(invite==null){
    		return JSONTools.newAPIResult(1, "该验证码已被使用");
    	}
    	List<LpStyle> liststyle=operationManagerService.getallEnableStyle();
    	List<LpStyleBean> list=new ArrayList<LpStyleBean>();
    	if(liststyle!=null&&liststyle.size()>0){
    		for(LpStyle style:liststyle){
    			LpStyleBean styleBean =new LpStyleBean();
    			styleBean.setStyleId(style.getStyleId());
    			styleBean.setStyleName(style.getStyleName());
    			list.add(styleBean);   			
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
