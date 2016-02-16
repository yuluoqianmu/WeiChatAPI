package com.laipai.cameraCheck.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.Tools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.cameraCheck.pojo.LpInvite;
import com.laipai.cameraCheck.service.ICameraCheckservice;
import com.laipai.cameraCheck.service.IInviteService;
import com.laipai.img.ImgUtil;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.lyz.service.CameraApplyService;

@NotLogin
@Service("CameramanAppformPicAndroidImpl")
public class CameramanAppformPicAndroidImpl implements RequestExecutor {

	private static final Logger logger = Logger
			.getLogger(CameramanAppformPicAndroidImpl.class);

	@Resource(name = IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService;
	@Resource(name = ICameraCheckservice.ICAMERACHECKSERVICE_NAME)
	private ICameraCheckservice cameraCheckservice;
	@Resource(name = IInviteService.IINVITESERVICE_NAME)
	private IInviteService inviteService;

	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();

		// 文件保存目录
//		String filePath = LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ LpUserBean.LP_USER_IMGURL;
		// 文件列表
		Set<Map.Entry<String, FileItem>> set = parameters.getFileMap()
				.entrySet();
		// 登录人id
		String userId = json.getString("userId");
		String invitecode = JSONTools.getString(json, "invite");
		LpUser user = userManInfoService.queryUserById(userId);

		if (userId == null || invitecode == null) {

			return JSONTools.newAPIResult(1, "参数不全！");

		}
		
		/*判读摄影师是否已经提交申请，防止重复申请*/
		if(CameraApplyService.isExist(userId)){
			JSONObject resulejson = new JSONObject();
			resulejson.put("resultMessage", "已提交过申请");
			return resulejson;
		}

		StringBuffer sb = new StringBuffer();
		if (set != null && set.size() > 0) {

			try {
				for (Map.Entry<String, FileItem> f : set) {
					String filename = f.getKey();
					String absFilePath = ImgUtil.saveImg(f, LaipaiConstants.UPLOAD_USER_PATH);
					if (absFilePath == null) {
						continue;
					}
					/* 身份证 */
					if (filename.contains("idCard")) {
						user.setIdCardImage(absFilePath);
						userManInfoService.saveOrUpdateser(user);

					} else {/* 作品 */
						sb.append(absFilePath + ";");
					}

				}
				LpCameramanAppform appform = new LpCameramanAppform();
				appform.setApplyTime(new Timestamp(new java.util.Date()
						.getTime()));
				appform.setCheckStatus(0);
				appform.setLpUser(user);
				/** 上传完之后的图片名称 */
				String[] imageNameArray = sb.toString().split(";");
				for (int i = 0; i < imageNameArray.length; i++) {

					if (i == 0) {
						appform.setPhotoOne(imageNameArray[0]);
					} else if (i == 1) {
						appform.setPhotoTwo(imageNameArray[1]);
					} else if (i == 2) {
						appform.setPhotoThree(imageNameArray[2]);
					}
				}
				cameraCheckservice.saveAppform(appform);

			} catch (Exception e) {
				logger.error("fail to save apply camera!", e);
			}
		}
		JSONObject resulejson = new JSONObject();

		LpInvite invite = inviteService.getInvite(invitecode);
		if (invite != null) {
			invite.setLpUser(user);
			invite.setInviteStatus(1);
			inviteService.updateInvite(invite);
		}
		resulejson.put("resultMessage", "申请成功");
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
