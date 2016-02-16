package com.laipai.cameraCheck.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.cameraCheck.pojo.LpInvite;
import com.laipai.cameraCheck.service.ICameraCheckservice;
import com.laipai.cameraCheck.service.IInviteService;
import com.laipai.galaryManInfo.app.AddGallaryForIOSExecutorImpl;
import com.laipai.img.ImgUtil;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.lyz.service.CameraApplyService;
/**
 * 

 * @Description:申请成为摄影师（传之照片界面）

 * @author:lxd

 * @time:2015-1-21 下午7:45:03
 */
@NotLogin
@Service("CameramanAppformPic")
public class CameramanAppformPic implements RequestExecutor {
	private static final Logger logger = Logger.getLogger(CameramanAppformPic.class);
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService; 
	@Resource(name=ICameraCheckservice.ICAMERACHECKSERVICE_NAME)
	private ICameraCheckservice cameraCheckservice;
	
	 @Resource(name=IInviteService.IINVITESERVICE_NAME)
	    private IInviteService inviteService;
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
		String invitecode=JSONTools.getString(json,"invite");
		//3张作品
	    String threePhotos = json.getString("threephotos");
	    	    //身份证
	    String idPhoto=json.getString("idphoto");
	    
	    if(userId==null||threePhotos==null||idPhoto==null||invitecode==null){
	    	
	    	return JSONTools.newAPIResult(1, "参数不全！");
	    	
	    }
	    
	    /*判读摄影师是否已经提交申请，防止重复申请*/
		if(CameraApplyService.isExist(userId)){
			JSONObject resulejson = new JSONObject();
			resulejson.put("resultMessage", "已提交过申请");
			return resulejson;
		}
		
	    LpUser user= userManInfoService.queryUserById(userId);
	    if(StringUtils.isNotEmpty(idPhoto)){
		String[] oneImgArr =  idPhoto.split("\\.");
		String base64Code = oneImgArr[0];
		String fileType = oneImgArr[1];
		String idCardUrl = ImgUtil.saveImg(LaipaiConstants.UPLOAD_USER_PATH, fileType, base64Code);
//		//**以日期作为图片名*//*
//		String newImageName = UUID.randomUUID() + "." + fileType;
//		try {
//			generateImage(base64Code , filePath +"/" + newImageName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		String IdsavePath= this.getRelativePath(newImageName);
		user.setIdCardImage(idCardUrl);
		userManInfoService.saveOrUpdateser(user);
	}
	    
	    LpCameramanAppform appform =new LpCameramanAppform();
		appform.setApplyTime(new Timestamp(new java.util.Date().getTime()) );
		appform.setCheckStatus(0);
		appform.setLpUser(user);
		//获取3张作品照片的base64编码,并解码保存到磁盘
		String[] imgArr = threePhotos.split(",");
//		StringBuffer sb=new StringBuffer();
		for(int i=0;i<imgArr.length;i++){
			String oneImg = imgArr[i];
			if(StringUtils.isNotEmpty(oneImg)){
				String[] oneImgArr =  oneImg.split("\\.");
				String base64Code = oneImgArr[0];
				String fileType = oneImgArr[1];
				//**以日期作为图片名*//*
//				String newImageName = UUID.randomUUID() + "." + fileType;
//				String IdsavePath= this.getRelativePath(newImageName);
				String idSavePath = ImgUtil.saveImg(LaipaiConstants.UPLOAD_USER_PATH, fileType, base64Code);
//				sb.append(idSavePath+";");
				if(i==0){
					appform.setPhotoOne(idSavePath);
				}else if(i==1){
					appform.setPhotoTwo(idSavePath);
				}else if(i==2){
					appform.setPhotoThree(idSavePath);
				}
//				try {
//					generateImage(base64Code , filePath +"/" + newImageName);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		}
//	String [] imageNameArray=sb.toString().split(";");
//	appform.setPhotoOne(imageNameArray[0]);
//	appform.setPhotoTwo(imageNameArray[1]);
//	appform.setPhotoThree(imageNameArray[2]);
	cameraCheckservice.saveAppform(appform);
//	LpUser user=userManInfoService.queryUserById(userId);
	if(StringUtils.isNoneBlank(invitecode)){
	LpInvite invite=inviteService.getInvite(invitecode);
	if(invite!=null){
	invite.setLpUser(user);
	invite.setInviteStatus(1);
	inviteService.updateInvite(invite);
	}
	}
	JSONObject resulejson = new JSONObject();
	resulejson.put("resultMessage", "申请提交成功");
	
	return resulejson;
	}
	/**
	* 将字符串转为图片
	* @param imgStr
	* @return
	*/
	private static boolean generateImage(String imgStr,String imgFile)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
		return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = imgFile;// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
//	private String getRelativePath(String fileName){
//		String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG + LpUserBean.LP_USER_IMGURL+"/"+fileName;		
//		return savePath;
//	}
	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
}
