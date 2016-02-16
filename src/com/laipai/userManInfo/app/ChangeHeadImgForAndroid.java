package com.laipai.userManInfo.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.Tools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;

@NotLogin
@Service("ChangeHeadImgForAndroid")
public class ChangeHeadImgForAndroid implements RequestExecutor {
	
	private static final Logger logger = Logger.getLogger(ChangeHeadImgForAndroid.class);
	
	@Resource(name = IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService;

	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {

		JSONObject resulejson = null;
		try {
			JSONObject json = parameters.getJson();

			// 文件保存目录
//			String filePath = LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//					+ LpUserBean.LP_USER_IMGURL;
			// 文件列表
			Set<Map.Entry<String, FileItem>> set = parameters.getFileMap()
					.entrySet();
			// 登录人id
			String userId = json.getString("userId");
			LpUser user = userManInfoService.queryUserById(userId);
			/* StringBuffer sb=new StringBuffer(); */
			if (set != null && set.size() > 0) {
				for (Map.Entry<String, FileItem> f : set) {
					String absFilePath = ImgUtil.saveImg(f, LaipaiConstants.UPLOAD_USER_PATH);
					if (absFilePath == null) {
						continue;
					}
					user.setHeadImage(absFilePath);
					userManInfoService.saveOrUpdateser(user);
				}

			}

			resulejson = new JSONObject();
			resulejson.put("resultMessage", "修改成功");
		} catch (Exception e) {
			logger.error("fail to change head img!",e);
		}
		return resulejson;
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

//	private String getRelativePath(String fileName) {
//		String savePath = LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ LpUserBean.LP_USER_IMGURL + "/" + fileName;
//		return savePath;
//	}

}
