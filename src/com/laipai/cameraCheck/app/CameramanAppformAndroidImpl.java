package com.laipai.cameraCheck.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.cameraCheck.service.ICameraCheckservice;
import com.laipai.galaryManInfo.app.AddGallaryForIOSExecutorImpl;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
/***
 * 安卓版申请成为摄影师

 * @Description:安卓版申请成为摄影师

 * @author:lxd

 * @time:2015-1-20 下午5:20:03
 */
@Service("CameramanAppformAndroidImpl")
public class CameramanAppformAndroidImpl implements RequestExecutor {

	private static final Logger logger = Logger.getLogger(CameramanAppformImpl.class);
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService; 
	@Autowired
    private IOperationManagerService operationManagerService;

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
		String realName= JSONTools.getString(json,"realName");
		String userGender=JSONTools.getString(json,"userGender");
		String cityId=JSONTools.getString(json,"cityId");
		String mobile=JSONTools.getString(json,"mobile");
		String grapherCarmer=JSONTools.getString(json,"grapherCarmer");
		String grapherDesc=JSONTools.getString(json,"grapherDesc");
		//风格
		String styleIdAll=json.getString("styleIds");
				
		if(userId==null||
				realName==null||
				userGender==null||
				cityId==null||
				mobile==null||
				grapherCarmer==null||
				grapherDesc==null||
						styleIdAll==null){
		return JSONTools.newAPIResult(1, "参数不全！");
			
		}
		String[] styleIds=styleIdAll.split(",");
		
		LpUser user= userManInfoService.queryUserById(userId);
		if(user!=null){
		 if("男".equals(userGender)){
			 user.setUserGender(0);
			 
		 }else{
			 user.setUserGender(1);
			 
		 }
		  user.setRealName(realName);
		 
		  user.setMobile(mobile);
		  user.setGrapherCarmer(grapherCarmer);
		  user.setGrapherDesc(grapherDesc);
		  LpCity city= operationManagerService.getCityById(cityId);
		  user.setLpCity(city);         
		  if(styleIds!=null&&styleIds.length>0){
			  Set<LpStyle> styles=new HashSet<LpStyle>();
			  for(int i=0;i<styleIds.length;i++){
			   LpStyle style= operationManagerService.findTheStyle(styleIds[i]);
			styles.add(style);   			   
		  user.setLpStyle(styles);
		  userManInfoService.saveOrUpdateser(user);
	  }
	
		}
			
		/*//获取3张作品照片的base64编码,并解码保存到磁盘
				String[] imgArr = threePhotos.split(",");
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
						sb.append(IdsavePath+";");
						try {
							generateImage(base64Code , filePath +"/" + newImageName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			String [] imageNameArray=sb.toString().split(";");
			appform.setPhotoOne(imageNameArray[0]);
			appform.setPhotoOne(imageNameArray[1]);
			appform.setPhotoOne(imageNameArray[2]);*/
			
		//cameraCheckservice.saveAppform(appform);
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
