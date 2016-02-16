package com.laipai.userManInfo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraCheck.app.CameramanAppformPicOne;
import com.laipai.img.ImgUtil;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
/**
 * 

 * @Description:更换头像

 * @author:lxd

 * @time:2015-1-27 下午2:22:31
 */
@NotLogin
@Service("ChangeHeadImgImpl")
public class ChangeHeadImgImpl implements RequestExecutor {
	private static final Logger logger = Logger.getLogger(ChangeHeadImgImpl.class);
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService; 
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		//文件保存目录
		String filePath = LaipaiConstants.UPLOAD_USER_PATH;
		File imgFile=new File(filePath);
		if(!imgFile.exists()){
			imgFile.mkdirs();
			logger.info("已创建保存图片的文件夹,路径>>"+imgFile.getAbsolutePath());
		}
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
	    //身份证
        String headImg=json.getString("headImg");
        if(userId==null||headImg==null){
	    	
	    	return JSONTools.newAPIResult(1, "参数不全！");
	    	
	    }
        LpUser user= userManInfoService.queryUserById(userId);
	    if(StringUtils.isNotEmpty(headImg)){
		String IdsavePath = getImgPath(filePath, headImg);
		user.setHeadImage(IdsavePath);
		userManInfoService.saveOrUpdateser(user);

}
	    JSONObject resulejson = new JSONObject();
    	resulejson.put("resultMessage", "修改成功");		
    	return resulejson;
			    
}
	
	private String getImgPath(String filePath, String idPhoto) {
		String[] oneImgArr =  idPhoto.split("\\.");
		String base64Code = oneImgArr[0];
		String fileType = oneImgArr[1];
		
		return ImgUtil.saveImg(filePath, fileType, base64Code);
		//**以日期作为图片名*//*
//		String newImageName = UUID.randomUUID() + "." + fileType;
//		try {
//			generateImage(base64Code , filePath +"/" + newImageName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String IdsavePath= this.getRelativePath(newImageName);
//		return IdsavePath;
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	private String getRelativePath(String fileName){
//		String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG + LpUserBean.LP_USER_IMGURL+"/"+fileName;		
//		return savePath;
//	}
}
