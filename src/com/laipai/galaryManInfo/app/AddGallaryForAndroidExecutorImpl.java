package com.laipai.galaryManInfo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Resource;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
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
 * 添加作品集(Android客户端)
 * 
 * @author zhh
 */
@NotLogin
@Service("AddGallaryForAndroidExecutorImpl")
public class AddGallaryForAndroidExecutorImpl implements RequestExecutor {

	private static final Logger logger = Logger.getLogger(AddGallaryForAndroidExecutorImpl.class);
	@Autowired
	private IBaseDao baseDao;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {

		JSONObject json = parameters.getJson();
		
		//存储9张作品内容的图片路径
		StringBuffer sb=new StringBuffer();
		//存封面图片路径
		List<String> newCoverName = new ArrayList<String>(); 
		List<String> originalName = new ArrayList<String>();
		//保存图片到磁盘
		uploadImgFile(sb,newCoverName,originalName,parameters);
		
		/**上传完之后的图片名称*/
		String [] imageNameArray=sb.toString().split(";");
		//服务id
		String serviceId = json.getString("serviceId");
		//摄影师的id
		String userId = json.getString("userId");
		//作品集描述
		String galaryDesc = json.getString("galaryDesc");
		//作品集主题
		String subjectName = json.getString("subjectName");

		LpGalary gallery =new LpGalary();
		gallery.setCreatTime(new Timestamp(new java.util.Date().getTime()));
	    gallery.setSubjectName(subjectName);
	    gallery.setGalaryDesc(galaryDesc);
	    //保存作品集到数据库
	    String galaryId = saveGallery(gallery,serviceId,userId,imageNameArray,newCoverName,originalName);
		
	    //保存成功,返回galaryId给app
	    JSONObject baseJson = new JSONObject();
	    baseJson.put("galaryId", galaryId);
	    return baseJson;
	}
	
	/**
	 * 保存图片到磁盘
	 * sb : 保存作品集9张内容图片
	 * newCoverName : 保存作品集封面图片
	 * parameters : 临时保存的参数,从中可以取出文件对象
	 * */
	private void uploadImgFile(StringBuffer sb, List<String> newCoverName,List<String> originalName,BaseRequestParameters parameters) {
		//作品集上传目录
//		String filePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG + SimpleImage.LP_GALLERY_IMGURL;
		//文件列表
		Set<Map.Entry<String, FileItem>> set = parameters.getFileMap().entrySet();
		
		if(set!=null && set.size() > 0){
			try {
				for (Map.Entry<String, FileItem> f : set) {
					String filename = f.getKey();
					/*保存图片*/
					String absFilePath = ImgUtil.saveImg(f, LaipaiConstants.UPLOAD_GALARY_PATH);  
					if(absFilePath == null){
						continue;
					}
					//判断图片是否是封面
					if(filename.contains("cover")){
						newCoverName.add(absFilePath);
					}else if(filename.contains("original")){/*封面图对应的原图*/
						originalName.add(absFilePath);
						sb.append(absFilePath+";");
					}else{
						sb.append(absFilePath+";");
					}
					
				}
			}catch (Exception e) {
				logger.error("fail to save gallary!",e);
			}
		}
	}

	/**
	 * 保存作品集
	 * */
	private String saveGallery(LpGalary gallery,String serviceId, String userAccount,String[] imageNameArray,List<String> newCoverName,List<String> originalName) {
		try {
		     //2:和用户关联
		     LpUser user= (LpUser) baseDao.getObjectById(LpUser.class, userAccount);
		     if(user == null){
		    	 user= queryUserByName(userAccount);
		     }
		     gallery.setLpUser(user);	     
		     //3：和服务关联
		     if(serviceId!=null&&!"private".equals(serviceId)){
			      LpService lpService= (LpService) baseDao.getObjectById(LpService.class,serviceId);
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

		    //如果有修改封面,则使用修改的封面,否则9张图片的第一张,作为封面
			String imagePath = imageNameArray[0];
			if(newCoverName !=null && !newCoverName.isEmpty()){
				String coverPath = newCoverName.get(0);
				imagePath = coverPath;
			}
			gallery.setGalaryCover(imagePath);
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
		    this.saveGalleryPhotos(gallery,imageNameArray,originalName); 
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
	private void saveGalleryPhotos(LpGalary galaryBean,String[] imageNameArray,List<String> newCoverName) {
		
		String coverName = null;
		if(newCoverName!=null && newCoverName.size()>0){
			coverName = newCoverName.get(0);
		}
		try {
			if(imageNameArray!=null && imageNameArray.length>0){
				for(int i=0;i<imageNameArray.length;i++){
					if(StringUtils.isNotEmpty(imageNameArray[i])){
						//组织用户附件的PO对象对象
						LpGalaryDetail photo = new LpGalaryDetail();
						//文件名
						photo.setPhotoName(imageNameArray[i]);
						//完成文件上传的同时，返回路径path（用作下载）
//						String imagePath=  this.getRelativePath(imageNameArray[i]);
						//保存原图入库
					
						   photo.setPhotoSrc(imageNameArray[i]);
						   //根据图片名字判断封面原图
						   if(imageNameArray[i].equals(coverName)){							   
							   photo.setGalaryConver(true); 
						   }else{ 
							   photo.setGalaryConver(false); 
						   }
						
						//上传时间,当前时间
						photo.setCreatTime(new Timestamp(new java.util.Date().getTime()));
						//附件对象关联用户对象（多的一端关联一的一端），如果不关联对象多的一端的外键列为null
						photo.setLpGalary((LpGalary) galaryBean.clone());
						photo.setStatus(0);
						//保存
						baseDao.save(photo);
					}
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
	
    public static void main(String[] args) {
		String a[] = {"1","2"};
		update(a);
		System.out.println("---------"+a[0]);
	}
    
    private static void update(String[] a){
    	a[0] = "3";
    	a[1] = "4";
    }

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
