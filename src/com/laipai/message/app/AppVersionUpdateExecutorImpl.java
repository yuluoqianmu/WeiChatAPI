package com.laipai.message.app;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.img.ImgUtil;
import com.laipai.message.pojo.LpUpdateVersion;
import com.laipai.message.pojo.VLpMessageDetail;
import com.laipai.message.service.IMessageService;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * app版本更新
 * */
@NotLogin
@Service("AppVersionUpdateExecutorImpl")
public class AppVersionUpdateExecutorImpl implements RequestExecutor{
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private IMessageService messageService;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json=parameters.getJson();
		//获取服务器域名
		String serverName = json.getString("serverName");
		
		String appVersion=json.getString("appVersion");
		if(StringUtils.isEmpty(appVersion)){
			return JSONTools.newAPIResult(1, "参数不能为空");	
		}
		try {
			List<LpUpdateVersion> list = baseDao.queryListObjectAll("from LpUpdateVersion where osType="+parameters.getOsType());
			LpUpdateVersion version = null;
			JSONObject baseJson = new JSONObject();
			if(list!=null && !list.isEmpty()){
				version = list.get(0);
				if(Double.valueOf(appVersion)<Double.valueOf(version.getVersionNum())){
					baseJson.put("update", "true"); //是否有更新：true有   false没有
					baseJson.put("downloadUrl", ImgUtil.getImgUrl(version.getDownloadUrl(),parameters.getPicType()));
					return baseJson;
				}
			}
			baseJson.put("update", "false");
			return baseJson;
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "查询消息列表失败");
		}
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 
}
