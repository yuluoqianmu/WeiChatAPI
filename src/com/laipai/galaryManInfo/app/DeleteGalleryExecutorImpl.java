package com.laipai.galaryManInfo.app;

import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.galaryManInfo.service.IGalleryService;

/**
 * 删除作品集
 * 
 * @author zhh
 */
@NotLogin
@Service("DeleteGalleryExecutorImpl")
public class DeleteGalleryExecutorImpl implements RequestExecutor {
	
	private static Logger logger = Logger.getLogger(DeleteGalleryExecutorImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		//作品集id
		String galaryId = json.getString("galaryId");
		if(StringUtils.isEmpty(galaryId)){
			JSONTools.newAPIResult(1, "galaryId can not be null (galaryId不能为空)");
		}
		//数据库删除，修改为无效状态
		galleryService.deleteGalleryById(galaryId);
//		baseDao.executeSql("update lp_galary set status=1 where galary_id='"+galaryId+"'");
		
		return JSONTools.newAPIResult(0, "删除作品集成功");
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
