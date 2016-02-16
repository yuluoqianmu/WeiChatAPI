package com.laipai.galaryManInfo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.Tools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;

/**
 * 修改作品集封面
 * 
 * @author zhh
 */
@NotLogin
@Service("UpdateGalaryCoverExecutorImpl")
public class UpdateGalaryCoverExecutorImpl implements RequestExecutor {

	private static Logger logger = Logger.getLogger(UpdateGalaryCoverExecutorImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		try {
			//文件保存目录
//			String filePath = LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL;
			File imgFile=new File(LaipaiConstants.UPLOAD_USER_PATH);
			if(!imgFile.exists()){
				imgFile.mkdirs();
				logger.info("已创建保存图片的文件夹,路径>>"+imgFile.getAbsolutePath());
			}
			
			//服务id
			//摄影师的id
			String galaryId = json.getString("galaryId");
			//9张作品集照片的编码
			String imgbase64 = json.getString("galaryCover");
			
			if(StringUtils.isEmpty(galaryId)
				|| StringUtils.isEmpty(imgbase64)){
				JSONTools.newAPIResult(1, "参数不能为空");
			}
			String imgPathName = "";
			//获取封面照片的base64编码,并解码保存到磁盘
			if(StringUtils.isNotEmpty(imgbase64)){
				String[] oneImgArr =  imgbase64.split("\\.");
				String base64Code = oneImgArr[0];
				String fileType = oneImgArr[1];
				imgPathName = ImgUtil.saveImg(LaipaiConstants.UPLOAD_GALARY_PATH, fileType, base64Code);
//				//**以日期作为图片名*//*
//				newImageName = UUID.randomUUID() + "." + fileType;
//				try {
//					generateImage(base64Code , filePath +"/" + newImageName);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
			
			LpGalary galary = (LpGalary) baseDao.getObjectById(LpGalary.class, galaryId);
			if(galary == null){
				return JSONTools.newAPIResult(1, "保存失败,galaryId错误 ");
			}
			//封面
			galary.setGalaryCover(imgPathName);
		    //保存作品集
			baseDao.updateObject(galary);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONTools.newAPIResult(1, "封面修改失败");
		}
	    return JSONTools.newAPIResult(0, "封面修改成功");
	}
	
//	private String getRelativePath(String fileName){
//		String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG + SimpleImage.LP_GALLERY_IMGURL+"/"+fileName;		
//		return savePath;
//	}

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

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
