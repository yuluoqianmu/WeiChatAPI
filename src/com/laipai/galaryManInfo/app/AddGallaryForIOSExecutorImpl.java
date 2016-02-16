package com.laipai.galaryManInfo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.laipai.base.util.GallerySort;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.action.GalleryManageAction;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceStyle;
import com.laipai.userManInfo.pojo.LpUser;
import com.lyz.util.ScoreUtil;

/**
 * 添加作品集(ios客户端)
 * 
 * @author zhh
 */
@NotLogin
@Service("AddGallaryForIOSExecutorImpl")
public class AddGallaryForIOSExecutorImpl implements RequestExecutor {

	private static final Logger logger = Logger.getLogger(AddGallaryForIOSExecutorImpl.class);
	
	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {

		JSONObject json = parameters.getJson();
		//文件保存目录
//		String filePath = LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL;
//		File imgFile=new File(LaipaiConstants.UPLOAD_GALARY_PATH);
//		if(!imgFile.exists()){
//			imgFile.mkdirs();
//			logger.info("已创建保存图片的文件夹,路径>>"+imgFile.getAbsolutePath());
//		}
		
		//服务id
		String serviceId = json.getString("serviceId");
		//摄影师的id
		String userId = json.getString("userId");
		//作品集描述
		String galaryDesc = json.getString("galaryDesc");
		//作品集主题
		String subjectName = json.getString("subjectName");
		//9张作品集照片的编码
		String imgbase64 = json.getString("contentPhoto");
		//String page=JSONTools.getString(json,"galaryCover");
		String cover = JSONTools.getString(json,"galaryCover");
		
		if(StringUtils.isEmpty(userId)
			|| StringUtils.isEmpty(subjectName)
			|| StringUtils.isEmpty(imgbase64)){
			JSONTools.newAPIResult(1, "参数不能为空");
		}
		
//		String coverName = "";
		String coverSavePath  = "";
		//获取封面照片的base64编码,并解码保存到磁盘
		if(StringUtils.isNotEmpty(cover)){
			String[] oneImgArr =  cover.split("\\.");
			String base64Code = oneImgArr[0];
			String fileType = oneImgArr[1];
			//**以日期作为图片名*//*
//			coverName = UUID.randomUUID() + "." + fileType;
//			try {
//				generateImage(base64Code , filePath +"/" + coverName);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			coverSavePath = ImgUtil.saveImg(LaipaiConstants.UPLOAD_GALARY_PATH, fileType, base64Code);
		}		
		
		//获取9张作品照片的base64编码,并解码保存到磁盘
		String[] imgArr = imgbase64.split(",");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<imgArr.length;i++){
			String oneImg = imgArr[i];
			if(StringUtils.isNotEmpty(oneImg)){
				String[] oneImgArr =  oneImg.split("\\.");
				String base64Code = oneImgArr[0];
				String fileType = oneImgArr[1];
				
				String galaryImgPath = ImgUtil.saveImg(LaipaiConstants.UPLOAD_GALARY_PATH, fileType, base64Code);
				//**以日期作为图片名*//*
//				String newImageName = UUID.randomUUID() + "." + fileType;
				sb.append(galaryImgPath+";");
//				try {
//					generateImage(base64Code , filePath +"/" + newImageName);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		}
		/**上传完之后的图片名称*/
		String [] imagePathArray=sb.toString().split(";");
		LpGalary gallery =new LpGalary();
		gallery.setCreatTime(new Timestamp(System.currentTimeMillis()));
	    gallery.setSubjectName(subjectName);
	    gallery.setGalaryDesc(galaryDesc);
//	    String coverPath=  this.getRelativePath(coverName);
	    gallery.setGalaryCover(coverSavePath);
	    
	    
	    //保存作品集
	    String galaryId = saveGallery(gallery,serviceId,userId,imagePathArray);
	    if(StringUtils.isEmpty(galaryId)){
	    	return JSONTools.newAPIResult(1, "保存失败");
	    }else if("nullUser".equals(galaryId)){
	    	return JSONTools.newAPIResult(1, "save error! the user is not exist!(保存失败，此摄影师不存在)");
	    }
		//保存成功,返回galaryId给app
	    JSONObject baseJson = new JSONObject();
	    baseJson.put("galaryId", galaryId);
	    return baseJson;
	}
	
	/**
	 * 保存作品集
	 * */
	private String saveGallery(LpGalary gallery,String serviceId, String userAccount,String[] imagePathArray) {
		try {
		     //2:和用户关联
		     LpUser user= (LpUser) baseDao.getObjectById(LpUser.class, userAccount);
		     if(user == null){
		    	 user= queryUserByName(userAccount);
		     }
		     if(user == null){
		    	 return "nullUser";
		     }
		     gallery.setLpUser(user);	     
		     //3：和服务关联
		     if(serviceId!=null&&!"private".equals(serviceId)){
		    	 System.out.println("##########################"+serviceId);
			      LpService lpService= (LpService) baseDao.getObjectById(LpService.class,serviceId);
			      System.out.println("##########################"+(lpService ==null));
			      gallery.setLpService(lpService);
			      //设置风格style
			      if(lpService !=null){
			    	   if(lpService.getLpServiceDetail() !=null){
			    		  List<LpServiceStyle> serviceStyleList = baseDao.queryListObjectAll("from LpServiceStyle where detailId='"+lpService.getLpServiceDetail().getDetailId()+"'");
						  String styleIds = "";
						  for(LpServiceStyle s : serviceStyleList){
							  styleIds += s.getStyleId() +",";
						  }
						  if(styleIds.endsWith(",")){
							  styleIds = styleIds.substring(0,styleIds.length()-1);
						  }
						  String[] styleId = styleIds.split(",");
						  //4：和风格关联
						  if(styleId!=null&&styleId.length>0){
					    	 Set<LpStyle> styles=new HashSet<LpStyle>();
					    	 for(int i=0;i<styleId.length;i++){
					    		 String id=styleId[i];
					    		 LpStyle style =(LpStyle) baseDao.getObjectById(LpStyle.class, id);
					    		 styles.add(style);
					    	 }
					    	 gallery.setLpStyle(styles); 
						 }
			    	   }
			      }
		     }else{
		    	  
		     }
		    
		    //获得第一张图片,作为封面
//			String imagePath= imagePathArray[0];
			//gallery.setGalaryCover(imagePath);
			gallery.setGalaryStatus(0);
			//评论,赞,浏览量数量默认0
			gallery.setLikeNumber(0);
			gallery.setCommentNumber(0);
			gallery.setViewNumber(0);
			gallery.setControlSource(0);
			//默认智能排序标记
			gallery.setControlIndex(0);
			Timestamp creatTime =new Timestamp(new java.util.Date().getTime());
			gallery.setCreatTime(creatTime);
			//计算该作品的分数（排序用）
			Double galleryScores= GallerySort.getHotsource(0, 0, 0, creatTime);
			gallery.setGalaryScores(galleryScores);
			gallery.setZhScore(ScoreUtil.getScore());

			gallery.setStatus(0);
			Serializable galaryId = baseDao.saveObjectReturnId(gallery);
			//1：向galleryDetails表中插入照片数据
			gallery.setGalaryId(galaryId.toString());
		    this.saveGalleryPhotos(gallery,imagePathArray); 
			return galaryId.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据用户名查询用户
	 */
	private LpUser queryUserByName(String userName) {
		String hql = "from LpUser where user_name ='" + userName + "'";
		List<LpUser> users = baseDao.queryListObjectByTopNum(hql, 1);
		if(users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}
	
	/**
	 * @Description:保存照片入库
	 */
	private void saveGalleryPhotos(LpGalary galaryBean,String[] imageNameArray) {
		try {
			if(imageNameArray!=null && imageNameArray.length>0){
				for(int i=0;i<imageNameArray.length;i++){
					//组织用户附件的PO对象对象
					LpGalaryDetail photo = new LpGalaryDetail();
					photo.setStatus(0);
					//文件名
					photo.setPhotoName(imageNameArray[i]);
					//完成文件上传的同时，返回路径path（用作下载）
					String imagePath=  imageNameArray[i];
					if(i==0){
						photo.setGalaryConver(true); 
					}
					//文件路径
					photo.setPhotoSrc(imagePath);
					//上传时间,当前时间
					photo.setCreatTime(new Timestamp(new java.util.Date().getTime()));
					//附件对象关联用户对象（多的一端关联一的一端），如果不关联对象多的一端的外键列为null
					photo.setLpGalary((LpGalary) galaryBean.clone());
					//保存
					baseDao.save(photo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
