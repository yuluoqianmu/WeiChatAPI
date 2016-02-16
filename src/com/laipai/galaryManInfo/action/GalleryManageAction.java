package com.laipai.galaryManInfo.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.FileUploadUtils;
import com.laipai.base.util.GallerySort;
import com.laipai.base.util.ImageCut;
import com.laipai.base.util.ImageUtil;
import com.laipai.base.util.ImgCompressPic;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.SpringContextUtil;
import com.laipai.galaryManInfo.dto.LpGalaryBackBean;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.galaryManInfo.pojo.VLpGalaryBackinfo;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userInfo.pojo.UserInfo;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 

 * @Description:作品集管理类

 * @author:lxd

 * @time:2014-12-16 下午2:12:44
 */
@Scope("prototype")
@Controller("galleryManAction")
public class GalleryManageAction extends BaseAction implements ModelDriven<LpGalaryBackBean>{	
	/**
	 * Logger for this class
	 */
	private JSONArray resultTree;//我要返回给页面的List
	public JSONArray getResultTree() {
		return resultTree;
	}
	public void setResultTree(JSONArray resultTree) {
		this.resultTree = resultTree;
	}
	
	private String jsp;
    public String getJsp() {
		return jsp;
	}
	public void setJsp(String jsp) {
		this.jsp = jsp;
	}

	/*	private String galleryId;

	public String getGalleryId() {
		return galleryId;
	}
	public void setGalleryId(String galleryId) {
		this.galleryId = galleryId;
	}*/
	private static final Logger logger = Logger.getLogger(GalleryManageAction.class);
	public static final String TEMPIMAGES="tempImages";
	public LpGalaryBackBean  galaryBean=new LpGalaryBackBean();
	public LpGalaryBackBean getModel() {
		return this.galaryBean;
	}
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	@Autowired
	private IServiceService  serviceService;
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService  userManInfoService;
	// Fields
	
	@Autowired
	private IBaseDao baseDao; 
	
	/**
	 * TODO 分页和按条件查询(作品集)
	
	 * @Description:查询所有作品集
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-16 下午2:57:30
	 */
	public  String queryall(){
		try{
		List list =new ArrayList();
		list=galleryService.getAllBypage(request);
		 
		if(list!=null&&list.size()>0){
			
				for(int i=0;i<list.size();i++){
					VLpGalaryBackinfo 	gallary=(VLpGalaryBackinfo) list.get(i);
				int commentCount= serviceService.getCount(gallary.getGalaryId());
				 gallary.setCommentNumber(commentCount);
				 
				 gallary.setGalaryCover(ImgUtil.getImgUrl(gallary.getGalaryCover()));
				 
				
				}
			
			
		}
		request.setAttribute("galaryList", list);
		String hql ="from VLpGalaryBackinfo where status=0";
		Integer galleryCount=galleryService.getGalleryCount(hql);
		request.setAttribute("galleryCount", galleryCount);
		return "galaryList";
		}catch(Exception e){
			
			e.printStackTrace();	
		}
		return null;
	}
	/**
	 * 
	
	 * @Description:添加作品集的准备数据
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-17 下午9:10:46
	 */
	 public String toAddGallery(){
		 //HttpServletRequest requ=ServletActionContext.getRequest();
         String serviceId=request.getParameter("serviceId");
         if(StringUtils.isNotBlank(serviceId)){        	 
        	 LpService service= galleryService.getServiceById(serviceId);
        	 request.setAttribute("service", service);
         }
		 List styleList =new ArrayList(); 
		 styleList=galleryService.queryallStyle();
		 request.setAttribute("stylelist", styleList);
		 List serviceList= new ArrayList();
		  //LpUser user= (LpUser) requ.getSession().getAttribute("user");
		 //判断是否在摄影师管理-服务中添加作品集
		 String jsp=request.getParameter("cameraServiceJSP");
		 if(StringUtils.isNotBlank(jsp)){
			 request.setAttribute("cameraServiceJSP", "is");
		 }
		 
		 return "toaddgallery";
	 }
	
	/**
	 * 
	   TODO　与AAP接口未考虑
	 * @Description:添加作品集
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-16 下午7:12:07
	 */
	public String addGallery(){
		LpGalary gallery =new LpGalary();
		gallery.setImgsFile(galaryBean.getImgsFile());
		gallery.setImgsFileFileName(galaryBean.getImgsFileFileName());
		gallery.setImgsFileContentType(galaryBean.getImgsFileContentType());
		gallery.setLpGalaryDetails(galaryBean.getLpGalaryDetails());
		gallery.setCreatTime(new Timestamp(new java.util.Date().getTime()));
		gallery.setLikeNumber(0);
		gallery.setCommentNumber(0);
		gallery.setViewNumber(0);
		gallery.setControlSource(0);
		galleryService.saveGallery(gallery);		
		return "toqueryallAction";
	}
	/**
	 * 
	
	 * @Description:跳转到详情页面
	
	 * @exception:
	
	 * @author: lxd
	 * @throws IOException 
	
	 * @time:2014-12-17 上午10:09:03
	 */
   public String  toEdit() throws IOException{
	   try {
		  String userjsp=request.getParameter("JSP");
		   String jsp=this.getJsp();
	   	  String galleryId=galaryBean.getGalaryId();
	      LpGalary gallery=galleryService.getgalleryByID(galleryId);
	      if(gallery!=null){
	    	  LpService service= gallery.getLpService();
	    	  if(service!=null){
	    	  String serviceid=service.getServiceId();
//	    	  Map map= serviceService.queryById(serviceid);
//	    	  request.setAttribute("serviceDetail", (LpServiceDetail)map.get("serviceDetail"));
	    	  LpService lpService=serviceService.queryById(serviceid);
	    	  request.setAttribute("serviceDetail", lpService.getLpServiceDetail());
	    	  }
	      }
	      request.setAttribute("gallery", gallery);
	      List<LpGalaryDetail> listphoto=new ArrayList<LpGalaryDetail>();
	      listphoto=galleryService.getphotosByGalleryID(galleryId);
	      request.setAttribute("listphoto", listphoto); 
	      LpGalaryExtend extend =new LpGalaryExtend();
	      extend=galleryService.getExtendbyGalleryId(galleryId);
	      request.setAttribute("extend", extend);
	      
	      List<LpComment> listcomment= galleryService.queryCommentByGalleryID(galleryId);
		  request.setAttribute("listcomment", listcomment);
		  if(StringUtils.isNotBlank(jsp)){
			 request.setAttribute("JSP", "YES");
			 return "touserEdit";
		  }
		  if(StringUtils.isNotBlank(userjsp)){
			  
			  request.setAttribute("JSP", "YES");  
		  }
		} catch (Exception e) {
			e.printStackTrace();
		}
      
	  return "toEdit";
   }
   
   /**
    * 
    * @Description:跳转到作品集编辑页面
    * @time:2014-12-17 上午10:09:03
    */
   public String  toTrueEditGalary() throws IOException{
	   try {
		   String galleryId=request.getParameter("galleryId");
		   String jsp=request.getParameter("jsp");
		   LpGalary gallery=galleryService.getgalleryByID(galleryId);
		   gallery.setGalaryCover(ImgUtil.getImgUrl(gallery.getGalaryCover()));
		   if(gallery!=null){
			   LpService service= gallery.getLpService();
			   if(service!=null){
				   String serviceid=service.getServiceId();
				   LpService lpService=serviceService.queryById(serviceid);
				   request.setAttribute("serviceDetail", lpService.getLpServiceDetail());
			   }
		   }
		   request.setAttribute("gallery", gallery);
		   List<LpGalaryDetail> listphoto=new ArrayList<LpGalaryDetail>();
		   listphoto=galleryService.getphotosUpdateByGalleryID(galleryId);
		   request.setAttribute("listphoto", listphoto); 
		   LpGalaryExtend extend =new LpGalaryExtend();
		   extend=galleryService.getExtendbyGalleryId(galleryId);
		   request.setAttribute("extend", extend);
		   
		   List<LpComment> listcomment= galleryService.queryCommentByGalleryID(galleryId);
		   request.setAttribute("listcomment", listcomment);
		   
			 if(StringUtils.isNotBlank(jsp)){
				 request.setAttribute("gallaryJSP", "YES");
				 return "toUserEditGalary";
			 }
		   
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   
	   return "toTrueEditGalary";
   }
   
   /**
    * 编辑页面保存
    * */
   public String saveEdit(){
	   String jsp=request.getParameter("gallaryJSP");
	   String galaryCover=request.getParameter("galaryCover");
	   String coverPhotoId=request.getParameter("coverPhotoId");
   try {
	       
	       LpGalary lpGalary =  galleryService.getgalleryByID(galaryBean.getGalaryId());
	  	   lpGalary.setGalaryDesc(galaryBean.getGalaryDesc());
	  	   lpGalary.setSubjectName(galaryBean.getSubjectName());	  	   
	  	   //String galaryCover = galaryBean.getGalaryCover().replace(LaipaiConstants.UPLOAD_VIRTUAL_IMG+SimpleImage.LP_GALLERY_IMGURL, LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL);
	  	   lpGalary.setGalaryCover(galaryCover);
	  	   lpGalary.setModifyTime(DateUtils.dateToTimestamp(new Date()));
	  	   galleryService.saveGallery(lpGalary);
	  	   
	  	  if(StringUtils.isNotBlank(coverPhotoId)){
	  	 List<LpGalaryDetail> details= galleryService.getphotosByGalleryID(galaryBean.getGalaryId());
	  	   for(LpGalaryDetail detail :details){
	  		   
	  		 if(detail.getPhotoId().equals(coverPhotoId)){	  			 
	  			detail.setGalaryConver(true);
	  		 }else{
	  			detail.setGalaryConver(false);
	  			 
	  		 }
	  		galleryService.updateDetail(detail);
	  		 
	  	   }
	  	  } 
	  	   
	  	   
	  	   
	  	   
	  	   if(lpGalary.getLpUser()!=null){
	  	   request.setAttribute("userId",lpGalary.getLpUser().getUserId());
	  	   }
		} catch (Exception e) {
			e.printStackTrace();
		}
	   if("YES".equals(jsp)){
		   
		   return "touserGallary";
	   }
	    return "toqueryallAction";
   }
   /**
    * 
   
    * @Description:添加评论
   
    * @return
   
    * String
   
    * @exception:
   
    * @author: lxd
   
    * @time:2014-12-17 下午5:13:52
    */
	public  String addComment(){
		String commentText=request.getParameter("comment");
		String jsp=request.getParameter("JSP");
		String account=request.getParameter("userName");
		String galleryId=galaryBean.getGalaryId();
		LpGalary gallery=galleryService.getgalleryByID(galleryId);
		//gallery.setCommentNumber(gallery.getCommentNumber()+1);
		LpComment comment=new LpComment();
		comment.setCommentDetail(commentText);
		//评论类型
		comment.setCommentType(0);
		Timestamp creatTime =new Timestamp(new java.util.Date().getTime());
		comment.setCreateTime(creatTime);
		//每次评论和回复之后要计作品的分数（排序用）
		Double galleryScores= GallerySort.getHotsource(gallery.getCommentNumber()+1, gallery.getViewNumber(), gallery.getLikeNumber(), gallery.getCreatTime());
		gallery.setGalaryScores(galleryScores);
		gallery.setCommentNumber(gallery.getCommentNumber());
		//修改手工数据
/*		if(gallery.getControlSource() ==1){
			List<LpGalaryExtend> listExe=galleryService.getExebygalleryId(galleryId);
			if(listExe!=null && listExe.size()>0){
				LpGalaryExtend extend = listExe.get(0);
				int commentNumber= extend.getCommentNumber();
				commentNumber=commentNumber+1;
				extend.setLikeNumber(commentNumber);
				galleryService.updateExt(extend);
			}
		}*/
		
		
		
		
		if(StringUtils.isNotBlank(account)){
			
			LpUser user= userManInfoService.queryUserByName(account);
			comment.setLpUser(user);
			
		}
		 if(gallery!=null){
			 comment.setLpGalary(gallery);
			 //request.setAttribute("galaryId",galleryId);
		 }
		 galleryService.saveComment(comment);
		 if("YES".equals(jsp)){
			   
			   return "touserEditAction";
		   }
		 //return SUCCESS;
		 return "toEditAction";
	}
	
	/**
	 * 添加回复
	 * */
	public  String addReply(){
		String replyContext=request.getParameter("replyContext");
		String replayToId=request.getParameter("replayToId");
		String account=request.getParameter("userName");
		String jsp=request.getParameter("JSP");
		String galleryId=galaryBean.getGalaryId();
		LpGalary gallery=galleryService.getgalleryByID(galleryId);
		//gallery.setCommentNumber(gallery.getCommentNumber()+1);
		LpComment comment=new LpComment();
		comment.setCommentDetail(replyContext);
		Timestamp creatTime =new Timestamp(new java.util.Date().getTime());
		comment.setCreateTime(creatTime);
		//每次评论和回复之后要计作品的分数（排序用）
		Double galleryScores= GallerySort.getHotsource(gallery.getCommentNumber()+1, gallery.getViewNumber(), gallery.getLikeNumber(), gallery.getCreatTime());
		gallery.setGalaryScores(galleryScores);
		
		gallery.setCommentNumber(gallery.getCommentNumber());
		//修改手工数据
		/*if(gallery.getControlSource() ==1){
			List<LpGalaryExtend> listExe=galleryService.getExebygalleryId(galleryId);
			if(listExe!=null && listExe.size()>0){
				LpGalaryExtend extend = listExe.get(0);
				int commentNumber= extend.getCommentNumber();
				commentNumber=commentNumber+1;
				extend.setLikeNumber(commentNumber);
				galleryService.updateExt(extend);
			}
		}*/
		
		comment.setCommentType(4);
		comment.setReplayToId(replayToId);
		comment.setLpGalary(gallery);
	    if(StringUtils.isNotBlank(account)){
			
			LpUser user= userManInfoService.queryUserByName(account);
			comment.setLpUser(user);
			
		}
		galleryService.saveComment(comment);
		 if("YES".equals(jsp)){
			   
			   return "touserEditAction";
		   }
		return "toEditAction";
	}
	
	/**
	 * 删除照片
	
	 * @Description:TODO
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-20 下午6:59:28
	 */
	public String deletePhotoById(){
		String photoid=request.getParameter("photoid");
		//String jsp=request.getParameter("JSP");
		//String galaryId = request.getParameter("galaryId");
		galleryService.deletephotoById(photoid);
/*		if(galaryBean == null){
			galaryBean = new LpGalaryBackBean();
		}*/
		//galaryBean.setGalaryId(galaryId);
		/* if("YES".equals(jsp)){
			   return "touserEditAction";
		   }*/
		return SUCCESS;
	}
  /**
   * 
  
   * @Description:通过作品集Id查询评论
  
   * @return
  
   * String
  
   * @exception:
  
   * @author: lxd
  
   * @time:2014-12-22 上午9:08:49
   */
	public String getCommentsByGalleryId(){
		
		String galleryId=request.getParameter("galleryId");
		 List<LpComment> listcomment= galleryService.queryCommentByGalleryID(galleryId);
	     request.setAttribute("listcomment", listcomment); 
		 return "icommentlist";
	}
	/***
	 * 
	
	 * @Description:根据作品集Id删除作品集
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-22 下午2:37:14
	 */
	public String deleteGalleryById(){
		String basePath = ServletActionContext.getServletContext().getRealPath("");//获得服务器的基路径
		 String jsp=request.getParameter("JSP");
		String galleryId=galaryBean.getGalaryId();
		galleryService.deleteGalleryById(galleryId);
		
//		String[] reallyPath=request.getParameterValues("reallyPath");
//		if(reallyPath!=null&&reallyPath.length>0){
//		for(int i =0;i<reallyPath.length;i++){
//			String conversionPath = reallyPath[i].replaceAll("/", "\\\\");
//			String filePath=basePath+conversionPath;
//			File file = new File(filePath);
//		    if(file.exists()){
//		     file.delete();
//		    }		
//		}
//		}
		   if("YES".equals(jsp)){
			   
			   return "touserGallary";
		   }
		return "toqueryallAction";
		
	}
	
	/**
	 * 根据评论Id删除评论
	
	 * @Description:TODO
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-22 下午4:58:25
	 */
	public String deleteCommentById(){
	String commentID=request.getParameter("commentid");
	String galaryId = request.getParameter("galaryId");
	galaryBean.setGalaryId(galaryId);
	galleryService.deleteCommentById(commentID);
	galleryService.minusOneCommentByGalary(galaryId);
	return "toEditAction";
		
	}
	private JSONObject jsonObject=new JSONObject();
	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	private String savePath;// 保存的位置
	private File imgFile; //上传的文件
	private  String imgFileContentType;//上传的类型
	private String partNumber;//图片存放在session中的key
    //private String[] styleId;
    private String serviceId;
    private String gallerySubject;
    private String galleryDesc;
	public IGalleryService getGalleryService() {
		return galleryService;
	}
	public void setGalleryService(IGalleryService galleryService) {
		this.galleryService = galleryService;
	}
	/*public String[] getStyleId() {
		return styleId;
	}
	public void setStyleId(String[] styleId) {
		this.styleId = styleId;
	}*/
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getGallerySubject() {
		return gallerySubject;
	}
	public void setGallerySubject(String gallerySubject) {
		this.gallerySubject = gallerySubject;
	}
	public String getGalleryDesc() {
		return galleryDesc;
	}
	public void setGalleryDesc(String galleryDesc) {
		this.galleryDesc = galleryDesc;
	}
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public File getImgFile() {
		return imgFile;
	}

	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public String getImgFileContentType() {
		return imgFileContentType;
	}

	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}

	public String getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	private  String imgFileFileName;
	public String getSavePath() {
		/*String basePath = ServletActionContext.getServletContext().getRealPath("/upload");
		//获取当前时间转换成日期格式的文件夹
		String datePath = DateUtils.dateToStringPath(new Date());
		//获取上传文件名的后缀
		String dateModelPath = basePath+"/"+"GalleryManage"+datePath;
		*/
//		 String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//					+ SimpleImage.LP_GALLERY_IMGURL;
		
	    return LaipaiConstants.UPLOAD_GALARY_PATH;
		
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	public String getsourcePath() {
		
//		 String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//					+ SimpleImage.LP_GALLERY_SOURCE;
//		
//	    return savePath;
		
		return LaipaiConstants.UPLOAD_GALARY_SOURCE_PATH;
	}
	
/**
	 * 
	 * 描述: 上传临时的图片，图片保存在session中
	 *
	 * @Title: uploadTmpImg  
	 * @return    
	 * @return String    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-20 下午5:05:26
	 */
	public String uploadTmpImg() {
		Map<String,SimpleImage> tempImages=null;
	    try {
	    	boolean exist=false;
	    	InputStream input = new FileInputStream(this.getImgFile());
	    	String suffix=this.getImgFileFileName().substring(this.getImgFileFileName().lastIndexOf(".")+1,this.getImgFileFileName().length());
	    	byte[] b=getBytes(input);
	        SimpleImage img=new SimpleImage(b,this.getImgFileContentType(),suffix,this.getPartNumber());
	        Object object=getSession().getAttribute(TEMPIMAGES);
	        if(object==null){
	        	tempImages=new HashMap<String, SimpleImage>();
	        	exist=true;
	        }else{
	        	tempImages=(Map<String, SimpleImage>) object;
	        }
	        tempImages.put(this.getPartNumber(), img);
	        getSession().setAttribute(TEMPIMAGES, tempImages);
            if(exist){
	        	if(getSession().getAttribute(TEMPIMAGES)!=null){
	        		logger.info("临时保存图片区已创建");
	        	}
	        }
	        request.setAttribute("result", "success");
		} catch (Exception e) {
			logger.error(e.getMessage());
			request.setAttribute("result", "exception");
		}
		return SUCCESS;
	}
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 
	 * 描述: 从临时保存区移除一张图片
	 *
	 * @Title: delImage  
	 * @return    
	 * @return String    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-24 上午8:40:24
	 */
	public String delImage(){
		if(getSession().getAttribute(TEMPIMAGES)==null){
			jsonObject.put("result", false);
			return SUCCESS;
		}
		try{
		logger.info("图片id>>"+id);
		Map<String,SimpleImage> images=(Map<String, SimpleImage>) getSession().getAttribute(TEMPIMAGES);
		images.remove(id);
		if(images.get(id)==null){
			jsonObject.put("result", true);
			getSession().setAttribute(TEMPIMAGES, images);
			logger.info("图片已删除");
		}else{
			jsonObject.put("result", false);
			logger.info("图片删除失败!");
		}
		}catch(Exception e){
			logger.error(e.getMessage());
			jsonObject.put("result", false);
		}
		return SUCCESS;
	}
	/**
	 * 
	 * 描述: 临时保存的图片持久化到磁盘
	 *
	 * @Title:
	 * @return    
	 * @return String    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-24 上午8:39:57
	 */
	public String persistentImage(){
		if(getSession().getAttribute(TEMPIMAGES)==null||((Map<String, SimpleImage>) getSession().getAttribute(TEMPIMAGES)).size()==0){
			jsonObject.put("result", 1);
			return SUCCESS;
		}
		
		//String [] styleId=this.getStyleId();
		/*String [] styleId=request.getParameterValues("styleId");*/
		String serviceId=this.getServiceId();
		String gallerySubject =this.getGallerySubject();
		String galleryDesc=request.getParameter("galleryDesc");
		String userAccount=request.getParameter("account");
		String converName=request.getParameter("coverName");
		String imgNumber = request.getParameter("imgNumber"); 
		/*String  conver.substring(0, conver.lastIndexOf("."));*/
		LpGalary gallery =new LpGalary();
		gallery.setCreatTime(new Timestamp(new java.util.Date().getTime()));
	    gallery.setSubjectName(gallerySubject);
	    gallery.setGalaryDesc(galleryDesc);
	    //2:和用户关联
	      	LpUser user=  userManInfoService.queryUserByName(userAccount);	    
	    	gallery.setLpUser(user);
		Map<String,SimpleImage> images=(Map<String, SimpleImage>) getSession().getAttribute(TEMPIMAGES);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Map<String,SimpleImage> images2=new HashMap<String, SimpleImage>();
		images2.putAll(images);
		getSession().removeAttribute(TEMPIMAGES);
		try{
		Iterator<SimpleImage> iterator=images2.values().iterator();
//		logger.info("保存目录>>>"+this.getSavePath()+";临时保存区图片总量>>"+images2.size());	
		String filePath=this.getSavePath();
		File imgFile=new File(filePath);
		if(!imgFile.exists()){
			imgFile.mkdirs();
			logger.info("已创建保存图片的文件夹,路径>>"+imgFile.getAbsolutePath());
		}
		String sourcePath=this.getsourcePath();
		File sourceimgFile=new File(sourcePath);
		if(!sourceimgFile.exists()){
			sourceimgFile.mkdirs();
			logger.info("保存原图文件夹>>"+sourceimgFile.getAbsolutePath());
		}
		
		
		
		StringBuffer sb=new StringBuffer();
		ImageUtil imgUtil =new ImageUtil();
		/*封面图*/
		String newCoverName = null;
		/*ImageCut mypic =new ImageCut();*/
		while(iterator.hasNext()){
			SimpleImage image=iterator.next();
			String partNumber=image.getPartNumber();
			
			
			/*图片压缩*/
//			imgUtil.imageCompress(absFilePath,0.4f,0.4f, 0, 0);
			
			/*共享封面图片*/
			if(imgNumber.equals(partNumber)){
				sb.append(converName).append(";");
				
			}else{
				/*把图片写入磁盘*/
				String absFilePath = ImgUtil.saveImgNoName(image.getImg(), image.getSuffix(), filePath);
//				/*保存原始图片*/
//				String absSourceFilePath = ImgUtil.saveImgNoName(image.getImg(), image.getSuffix(), sourcePath);
				/*记录文件名*/
				sb.append(absFilePath).append(";");
			}
			
			
//			//**以日期作为图片名*//*
//			String newImageName=UUID.randomUUID().toString();
//			/*指定第几张为封面图*/
//			if(imgNumber.equals(partNumber)){
//				newImageName=newImageName+"COVER";
//				
//			}
//			newImageName=newImageName+"."+image.getSuffix();
//			sb.append(newImageName+";");
//			File _imgFile=new File(filePath+ "/" +File.separator+newImageName);
//			
//			File source_imgFile=new File(sourcePath+ "/" +File.separator+newImageName);
//
//			persistence(image.getImg(),_imgFile);
//			persistence(image.getImg(),source_imgFile);
//			String imgPath= this.getImgPath(newImageName);
//			//压缩图片
//			 //System.out.println("图片路径>>>>>"+imgPath);
//            //mypic.compressPic(imgPath,SimpleImage.LP_GALLERY_PIC_WITH,SimpleImage.LP_GALLERY_PIC_HEIGHT,true);
//			imgUtil.imageCompress(imgPath,0.4f,0.4f, 0, 0);
//            /*mypic.compressPic(_imgFile.getAbsolutePath());*/
		}
		/**上传完之后的图片名称*/
		String [] imageNameArray=sb.toString().split(";");
		//保存作品集
		galleryService.saveGallery(gallery,serviceId,userAccount,imageNameArray,converName);
		jsonObject.put("result", 0);
		logger.info("图片"+sb.toString()+"已上传到"+imgFile.getAbsolutePath());
		}catch(Exception e){
		e.printStackTrace();
		logger.error(e.getMessage());
		jsonObject.put("result", 2);
		}
		return SUCCESS;
	}
	/**
	 *  修改详情页图片
	 * 描述: 临时保存的图片持久化到磁盘
	 * 
	 * @Title:
	 * @return    
	 * @return String    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-24 上午8:39:57
	 */
	public String persistentImage_updateDetail(){
		if(getSession().getAttribute(TEMPIMAGES)==null||((Map<String, SimpleImage>) getSession().getAttribute(TEMPIMAGES)).size()==0){
			jsonObject.put("result", 1);
			return SUCCESS;
		}
		String galaryId = request.getParameter("galaryId");
		Map<String,SimpleImage> images=(Map<String, SimpleImage>) getSession().getAttribute(TEMPIMAGES);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Map<String,SimpleImage> images2=new HashMap<String, SimpleImage>();
		images2.putAll(images);
		getSession().removeAttribute(TEMPIMAGES);
		try{
			Iterator<SimpleImage> iterator=images2.values().iterator();
			logger.info("保存目录>>>"+this.getSavePath()+";临时保存区图片总量>>"+images2.size());
			String filePath=this.getSavePath();
//			File imgFile=new File(filePath);
//			if(!imgFile.exists()){
//				imgFile.mkdirs();
//				logger.info("已创建保存图片的文件夹,路径>>"+imgFile.getAbsolutePath());
//			}
//			String sourcePath=this.getsourcePath();
//			File sourceimgFile=new File(sourcePath);
//			if(!sourceimgFile.exists()){
//				sourceimgFile.mkdirs();
//				logger.info("保存原图文件夹>>"+sourceimgFile.getAbsolutePath());
//			}
						
			StringBuffer sb=new StringBuffer();
//			ImageUtil imgUtil =new ImageUtil();
			while(iterator.hasNext()){
				SimpleImage image=iterator.next();
				String absFilePath = ImgUtil.saveImgNoName(image.getImg(), image.getSuffix(), filePath);
//				String absSourceFilePath = ImgUtil.saveImgNoName(image.getImg(), image.getSuffix(), sourcePath);
//				imgUtil.imageCompress(absFilePath,0.4f,0.4f, 0, 0);
				sb.append(absFilePath).append(";");
//				//**以日期作为图片名*//*
//				String newImageName=UUID.randomUUID()+"."+image.getSuffix();
//				sb.append(newImageName+";");
//				File _imgFile=new File(filePath+ "/" +File.separator+newImageName);
//				File source_imgFile=new File(sourcePath+ "/" +File.separator+newImageName);
//				persistence(image.getImg(),_imgFile);
//				persistence(image.getImg(),source_imgFile);
//				//压缩图片				
//				String imgPath= this.getImgPath(newImageName);
//				imgUtil.imageCompress(imgPath,0.4f,0.4f, 0, 0);
				
			}
			/**上传完之后的图片名称*/
			String [] imageNameArray=sb.toString().split(";");
			//保存图片到作品集详情表
			galleryService.updateGalleryDetail(galaryId,imageNameArray);
			jsonObject.put("result", 0);
//			logger.info("图片"+sb.toString()+"已上传到"+imgFile.getAbsolutePath());
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonObject.put("result", 2);
		}
		return SUCCESS;
	}
	/**
	 * 
	 * 描述: 持久化byte[]到文件
	 *
	 * @Title: persistence  
	 * @param data
	 * @param file    
	 * @return void    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-24 上午8:39:34
	 */
	public void persistence(byte[] data,File file){
		 try {
	    	  //以服务器文件保存地址和原文件名建立上传文件输出流
	    	   FileOutputStream fos = new FileOutputStream(file);
	    	   //以上传文件建立文件上传流
	    	   ByteArrayInputStream fis=new ByteArrayInputStream(data);
	    	   //将上传文件写到服务器
	    	   byte[] buffer = new byte[1024];
	    	   int len = 0;
	    	   while((len = fis.read(buffer))>0){
	    	    fos.write(buffer,0,len);
	    	   }
	    	   fos.close();
	    	   fis.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 
	 * 描述: 输入流转换成bytep[]
	 *
	 * @Title: getBytes  
	 * @param in
	 * @return    
	 * @return byte[]    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-24 上午8:37:49
	 */
	public static byte[] getBytes(InputStream in)
	{
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		int ch;
		try {
			while((ch = in.read()) != -1)
			{
			   bytestream.write(ch);
			}
			
		    byte imgdata[]=bytestream.toByteArray();
		    in.close();
		    return imgdata;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	/**
	 * 
	 * 描述: 清空临时保存图片的session
	 *
	 * @Title: clearTempImages  
	 * @return    
	 * @return String    
	 * @author:  zhangxiaodi
	 * @date: 2014-12-24 上午8:38:12
	 */
	public String clearTempImages(){
		if(getSession().getAttribute(TEMPIMAGES)!=null){
			getSession().removeAttribute(TEMPIMAGES);
			if(getSession().getAttribute(TEMPIMAGES)==null){
				logger.info("临时保存图片区已移除");
			}else{
				logger.info("临时保存图片区移除失败");
			}
		}
		jsonObject.put("result", true);
		return SUCCESS;
	}
	
	public String getRelativePath(String fileName){
		String datePath = DateUtils.dateToStringPath(new Date());
		String relativePath="/upload/GalleryManage"+datePath+"/"+fileName;		
		return relativePath;
	}
	
	public String queryUserService(){
		String account =request.getParameter("userAccount");
	    LpUser user=userManInfoService.queryUserByName(account);		
		List <LpService> list=new ArrayList<LpService>();
		list=galleryService.getServiceByAcount(account);
		ServletActionContext.getContext().getValueStack().push(list);
		return SUCCESS;
	    
	} 
	

	/**
	 * 
	
	 * @Description:根据作品Id隐藏作品
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2015-1-4 上午10:00:38
	 */
	public String hideGallery(){
		try{
			String galaryId = request.getParameter("galaryId");
			String statusString=request.getParameter("status");
			int status= Integer.parseInt(statusString);
			galleryService.updateGalleryStatus(galaryId,status);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:根据作品集的Id删除作品集
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2015-1-4 上午11:03:39
	 */
	public String deleteGallery(){
		try{
			String[] ids = request.getParameterValues("galleryId");
			if(ids != null && ids.length > 0)
			{
				for(int i = 0; i < ids.length; i++)
				{
					String galleryId = ids[i];
					galleryService.deleteGalleryById(galleryId);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		
		return "toqueryallAction";
		
	}
	
	/**
	 *  修改浏览数,赞数
	 * */
	public String updateNumber(){
		try{
			String viewNumString = request.getParameter("viewNumber");
			String galleryId=request.getParameter("galaryId");
			
			int vieNum=0;
			int likeNum=0;
			if(StringUtils.isNotBlank(viewNumString)){
			vieNum=Integer.parseInt(viewNumString);
			}
			String likeNumString = request.getParameter("likeNumber");
			if(StringUtils.isNotBlank(likeNumString)){
			likeNum =Integer.parseInt(likeNumString);
			}
			galleryService.updateGalaryExt(galleryId,vieNum,likeNum);
			
			if(galaryBean == null ){
				galaryBean = new LpGalaryBackBean();
			}
			galaryBean.setGalaryId(galleryId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		String jsp=request.getParameter("JSP");
		if("YES".equals(jsp)){
			   
			   return "touserEditAction";
		   }
		return "toEditAction";
	}
	
	/**
	 *
	
	 * @Description:ajax 删除作品集
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2015-1-20 下午8:21:24
	 */
	public String deleteGalleryAjax(){
		try{
			String galleryId=request.getParameter("galleryId");
			galleryService.deleteGalleryById(galleryId);
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			jsonObject.put("result", "error");
			
		}
		
		return SUCCESS;
	}
	
   public String changeGalleryIndex(){
	   try{
			String galleryId=request.getParameter("galleryID");
			String galleryIndex=request.getParameter("galleryIndex");
			int topNumber=SimpleImage.LP_GALLERY_TOPNUMBER;
			 if(StringUtils.isNotBlank(galleryIndex)){				 
				 int index =Integer.parseInt(galleryIndex);
				 galleryService.updateGalleryIndex(galleryId,index); 
			 }
			
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			
		}	   
	   return SUCCESS;
   }
	
   public String smartIndex(){
	   
	   galleryService.updateControlIndex();
	   
	   return SUCCESS;
   }
//	public String getImgPath(String fileName){
//		//String datePath = DateUtils.dateToStringPath(new Date());
//		String realPath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ SimpleImage.LP_GALLERY_IMGURL+"/"+fileName;
//		String savePath=realPath.replace("\\", "/");
//		return savePath;
//	}
 /**
  * 	
 
  * @Description:裁剪作品集封面
 
  * @return
 
  * String
 
  * @exception:
 
  * @author: lxd
 * @throws IOException 
 
  * @time:2015-2-10 上午11:20:42
  */
  public String selectCover() throws IOException{
	   
	  HttpServletResponse resp=ServletActionContext.getResponse();
      resp.setContentType("text/html;charset=utf-8");
      resp.setCharacterEncoding("utf-8");
      PrintWriter out = resp.getWriter();
	
	/* 
     * 获取参数 
     * x,y,w,h,bigImage 
     */  
      String xstring=request.getParameter("x");
      String divWS= request.getParameter("divw");
      String divHS= request.getParameter("divh");
      String neww= divWS.substring(0, divWS.length() - 2);
      String newh= divHS.substring(0, divHS.length() - 2);
      int x = Integer.parseInt(xstring);  
      int y = Integer.parseInt(request.getParameter("y"));  
      int w = Integer.parseInt(request.getParameter("w"));  
      int h = Integer.parseInt(request.getParameter("h"));
      int divw=Integer.parseInt(neww);
      int divh=Integer.parseInt(newh);
    String imgNumber = request.getParameter("imgNumber"); 
    
    
    Map<String,SimpleImage> images=(Map<String, SimpleImage>) getSession().getAttribute(TEMPIMAGES);
    String imgPath="";
    String coverName="";
    Iterator i = images.entrySet().iterator();
    while (i.hasNext()) {
    Entry entry = (java.util.Map.Entry)i.next();
    String key = entry.getKey().toString();
    	
    	
     if(key.equals(imgNumber)){   	 
    	/* Iterator<SimpleImage> iterator=images.values().iterator();*/
 		logger.info("保存目录>>>"+this.getSavePath()+";临时保存区图片总量>>"+images.size());
 		String filePath=this.getSavePath();
 		File imgFile=new File(filePath);
 		if(!imgFile.exists()){
 			imgFile.mkdirs();
 			logger.info("已创建保存图片的文件夹,路径>>"+imgFile.getAbsolutePath());
 		} 
 		SimpleImage image=images.get(key);
		
 		imgPath = ImgUtil.saveImgNoName(image.getImg(), image.getSuffix(), filePath);
		coverName = ImgUtil.getFileName(imgPath);
 		//**以日期作为图片名*//*
//		String newImageName=UUID.randomUUID()+"."+image.getSuffix();
//		File _imgFile=new File(filePath+ "/" +File.separator+newImageName);
//		persistence(image.getImg(),_imgFile);
//		imgPath= _imgFile.getAbsolutePath();
//		coverName=newImageName;	 	 
     }
    }    
    	
		
		StringBuffer sb=new StringBuffer();
		if(!"".equals(imgPath)){			
			 //切割图片  
	        ImageCut imageCut = new ImageCut();  
	        imageCut.cutImage(imgPath, x, y, w, h,divw,divh);  
		}
		
    
    try{
		JSONObject jo = new JSONObject();
		jo.accumulate("coverName", coverName);
		//{"realPath":"XXX", "relativePath":"xxx"}
		String result = jo.toString();
		out.println(result);
		
	}
	catch(Exception e){
				
	}finally {
        out.flush();
        out.close();
    }
    return SUCCESS;
  }
  
  
  /**
   * 	
  
   * @Description:裁剪作品集封面
  
   * @return
  
   * String
  
   * @exception:
  
   * @author: lxd
  * @throws IOException 
  
   * @time:2015-2-10 上午11:20:42
   */
   public String selectCover2() throws IOException{
 	   
 	  HttpServletResponse resp=ServletActionContext.getResponse();
       resp.setContentType("text/html;charset=utf-8");
       resp.setCharacterEncoding("utf-8");
       PrintWriter out = resp.getWriter();
 	
 	/* 
      * 获取参数 
      * x,y,w,h,bigImage 
      */  
       String xstring=request.getParameter("x");
       String divWS= request.getParameter("divw");
       String divHS= request.getParameter("divh");
       String neww= divWS.substring(0, divWS.length() - 2);
       String newh= divHS.substring(0, divHS.length() - 2);
       int x = Integer.parseInt(xstring);  
       int y = Integer.parseInt(request.getParameter("y"));  
       int w = Integer.parseInt(request.getParameter("w"));  
       int h = Integer.parseInt(request.getParameter("h"));
       int divw=Integer.parseInt(neww);
       int divh=Integer.parseInt(newh);
    /* String imgNumber = request.getParameter("imgNumber");*/ 
       String photoId = request.getParameter("coverId");
       
       
       LpGalaryDetail photo= (LpGalaryDetail) baseDao.getObjectById(LpGalaryDetail.class, photoId);
       
       String photoSrc=photo.getPhotoSrc();
       
       String filePath=this.getSavePath();
       String newImageName=ImgUtil.generateImgName("jpg");
       String outPath=filePath+"/"+newImageName;
       ImageCut imageCut = new ImageCut();  
       imageCut.cutImage2(photoSrc, x, y, w, h,divw,divh,outPath);  
     
       try{
   		JSONObject jo = new JSONObject();
   		jo.accumulate("coverName", outPath);
   		//{"realPath":"XXX", "relativePath":"xxx"}
   		String result = jo.toString();
   		out.println(result);
   		
   	}
   	catch(Exception e){
   				
   	}finally {
           out.flush();
           out.close();
       }
     return SUCCESS;
   }
}