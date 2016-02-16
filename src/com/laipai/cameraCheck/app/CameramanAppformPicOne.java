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
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.cameraCheck.service.ICameraCheckservice;
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
@Service("CameramanAppformPicOne")
public class CameramanAppformPicOne implements RequestExecutor {
	private static final Logger logger = Logger.getLogger(CameramanAppformPicOne.class);
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService; 
	@Resource(name=ICameraCheckservice.ICAMERACHECKSERVICE_NAME)
	private ICameraCheckservice cameraCheckservice;
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		long time1 = System.currentTimeMillis();
		//文件保存目录
//		String filePath = LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL;
		File imgFile=new File(LaipaiConstants.UPLOAD_USER_PATH);
		if(!imgFile.exists()){
			imgFile.mkdirs();
			logger.info("已创建保存图片的文件夹,路径>>"+imgFile.getAbsolutePath());
		}
		JSONObject json = parameters.getJson();
		String userId=JSONTools.getString(json,"userId");
	    //身份证
        String idPhoto=json.getString("idphoto");
		//3张作品
	    String pictureOne = json.getString("pictureOne");
	    String pictureTwo = json.getString("pictureTwo");
	    String pictureThree = json.getString("pictureThree");

	    
	    if(userId == null){
	    	
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
		String IdsavePath = getImgPath(LaipaiConstants.UPLOAD_USER_PATH, idPhoto);
		user.setIdCardImage(IdsavePath);
		userManInfoService.saveOrUpdateser(user);
	}
	    long time2 = System.currentTimeMillis();
	    System.out.println("保存第一张图片的时间:"+(time2-time1)+"ms");
	    
	    LpCameramanAppform appform =new LpCameramanAppform();
		appform.setApplyTime(new Timestamp(new java.util.Date().getTime()) );
		appform.setCheckStatus(0);
		appform.setLpUser(user);
		String onesavePath = getImgPath(LaipaiConstants.UPLOAD_USER_PATH, pictureOne);
		long time3 = System.currentTimeMillis();
		System.out.println("保存第2张图片的时间:"+(time3-time2)+"ms");
		String twosavePath = getImgPath(LaipaiConstants.UPLOAD_USER_PATH, pictureTwo);
		long time4 = System.currentTimeMillis();
		System.out.println("保存第3张图片的时间:"+(time4-time3)+"ms");
		String threesavePath = getImgPath(LaipaiConstants.UPLOAD_USER_PATH, pictureThree);
		long time5 = System.currentTimeMillis();
		System.out.println("保存第4张图片的时间:"+(time5-time4)+"ms");
		appform.setPhotoOne(onesavePath);
		appform.setPhotoTwo(twosavePath);
    	appform.setPhotoThree(threesavePath);
    	cameraCheckservice.saveAppform(appform);
    	JSONObject resulejson = new JSONObject();
    	resulejson.put("resultMessage", "申请提交成功");
    	long time6 = System.currentTimeMillis();
		//获取3张作品照片的base64编码,并解码保存到磁盘
/*		String[] imgArr = threePhotos.split(",");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<imgArr.length;i++){
			String oneImg = imgArr[i];
			if(StringUtils.isNotEmpty(oneImg)){
				String[] oneImgArr =  oneImg.split("\\.");
				String base64Code = oneImgArr[0];
				String fileType = oneImgArr[1];
				//**以日期作为图片名*//*
				String newImageName = UUID.randomUUID() + "." + fileType;
				String IdsavePath= this.getRelativePath(newImageName);
//				sb.append(IdsavePath+";");
				if(i==0){
					appform.setPhotoOne(IdsavePath);
				}else if(i==1){
					appform.setPhotoTwo(IdsavePath);
				}else if(i==2){
					appform.setPhotoThree(IdsavePath);
				}
				try {
					generateImage(base64Code , filePath +"/" + newImageName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
//	String [] imageNameArray=sb.toString().split(";");
//	appform.setPhotoOne(imageNameArray[0]);
//	appform.setPhotoTwo(imageNameArray[1]);
//	appform.setPhotoThree(imageNameArray[2]);
	
	
	return resulejson;
	}
	private String getImgPath(String filePath, String idPhoto) {
		String[] oneImgArr =  idPhoto.split("\\.");
		String base64Code = oneImgArr[0];
		String fileType = oneImgArr[1];
		String idSavePath = ImgUtil.saveImg(filePath, fileType, base64Code);
		return idSavePath;
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
