package com.laipai.serviceInfo.app;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.userManInfo.service.IUserManInfoService;
@NotLogin
@Service("AddServiceStyleExecutorImpl")
public class AddServiceStyleExecutorImpl implements RequestExecutor{
	@Resource(name="baseDao")
	private IBaseDao baseDao;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json=parameters.getJson();
		String styleName_add=json.getString("styleName_add");
		if(StringUtils.isBlank(styleName_add)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		String userId=json.getString("userId");
		try {
			LpStyle style=new LpStyle();
			style.setStyleName(styleName_add);
//			style.setStyleType(2);//风格类型（0:用户风格，1:作品集风格2:服务风格）
			style.setStyleStatus(1);//显示（0:全局显示，1:摄影师显示）
			style.setCreateUserId(userId);
		    baseDao.save(style);
			return JSONTools.newAPIResult(0, "添加服务风格标签成功");
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "添加服务风格标签失败");
		}
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
