package com.laipai.subject.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.laipai.base.action.BaseAction;
import com.laipai.base.util.ImageCut;
import com.laipai.base.util.ImageCutTwo;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.tags.PageController;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.subject.bean.GalaryClientShow;
import com.laipai.subject.bean.SelectGalary;
import com.laipai.subject.pojo.Subject;
import com.laipai.subject.pojo.SubjectDetail;
import com.laipai.subject.pojo.VLpSubjectDetailBack;
import com.laipai.subject.service.ISubjectService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 
 * @Description:用于专题后台的管理
 * @author:朱鑫
 * @time:2014-12-16 下午2:34:41
 */
public class SubjectBackAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	
	private Subject subject;
	
	private File subjectimg;
	/** 
	 * @Fields imageName : 文件名
	 */  
	private String subjectimgFileName;
	/** 
	 * @Fields fileContentType : 文件类型 
	 */  
	private String subjectimgContentType;
	
	public File getSubjectimg() {
		return subjectimg;
	}
	public void setSubjectimg(File subjectimg) {
		this.subjectimg = subjectimg;
	}
	public String getSubjectimgFileName() {
		return subjectimgFileName;
	}
	public void setSubjectimgFileName(String subjectimgFileName) {
		this.subjectimgFileName = subjectimgFileName;
	}
	public String getSubjectimgContentType() {
		return subjectimgContentType;
	}
	public void setSubjectimgContentType(String subjectimgContentType) {
		this.subjectimgContentType = subjectimgContentType;
	}
	@Resource
	private ISubjectService iSubjectService;
	
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService gallaryService;
	
	/**
	 * 
	 * @Description:用于发现所有专题，返回专题集合
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-16 下午2:38:32
	 */
	public String subjectList()
	{
		
		try
		{		
			List<Subject> list=iSubjectService.findSubjectList(request,"from Subject order by subject_location",20);
			List<Subject> newList = new ArrayList<Subject>();
			if(list!=null && list.size()!=0){
				
				for(int i=0;i<list.size();i++){
					Subject sub= list.get(i);
					List<LpGalary> slectNum = iSubjectService.selectedGalaryList(sub.getSubject_id());
					sub.setGallery_number(0);
					if(slectNum != null && !"".equals(slectNum))
					{
						sub.setGallery_number(slectNum.size());
					}
					if(sub.getSubject_img() != null)
					{
						sub.setSubject_img(ImgUtil.getImgUrl(sub.getSubject_img()));
					}
					newList.add(sub);
				}
			}
			int location=iSubjectService.getMaxLocation();
			request.setAttribute("subjectList", newList);
			request.setAttribute("maxLocation", location);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return "success";
	}
	/**
	 * 
	 * @Description:添加主题
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @throws Exception 
	 * @time:2014-12-17 上午11:48:37
	 */
	public String addSubject() throws Exception 
	{
		LpUser user = getCurrentUser();
		  //获得文件
		/*if(getSubjectimg() != null)
		{
		File filePath = updateFileNameAndPath();
		String coverUrl = filePath.getPath().replace("\\", "/");
		uploadImage(subjectimg, filePath);
	    subject.setSubject_img(coverUrl);
		}*/
		String bigImage = request.getParameter("coverPath");
		 /*String xstring=request.getParameter("x");
		 if( !("".equals(xstring)) && xstring != null)
		 {
          int x = Integer.parseInt(xstring);  
          int y = Integer.parseInt(request.getParameter("y"));  
          int w = Integer.parseInt(request.getParameter("w"));  
          int h = Integer.parseInt(request.getParameter("h"));  
                    
        String imagePath=bigImage;
        String windPath=bigImage.replace("/", "\\");
        ImageCutTwo imageCut = new ImageCutTwo();  
        imageCut.cutImage(imagePath, x, y, w, h);
		 }*/
        //获取文件真实路径  
        //获取文件名  
      /*  String[] imageNameS = bigImage.split("/");  
        String imageName = imageNameS[imageNameS.length-1];*/  
        //文件正式路径  
         // imagePath = "D:"+windPath;  
     
        //切割图片  
        
        subject.setSubject_img(bigImage);
		if(iSubjectService.saveOrUpdateSubject(subject))
		{
			return "ISOK";
		}
		return "error";
	}
	/**
	 * @Description:专题上线下线操作
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-17 下午3:10:23
	 */
	public String changeStatus()
	{
		String subject_id = request.getParameter("id");
		Integer subject_status =  Integer.parseInt(request.getParameter("subject_status").trim());
		if(subject_status == 0)
		{
			subject_status = 1;
		}
		else
		{
			subject_status = 0;
		}
		iSubjectService.updateStatus(subject_id, subject_status);
		return "ISOK";
	}
	/**
	 * 
	 * @Description:删除专题操作
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-17 下午8:52:35
	 */
	public String deleteSubject()
	{
		String subject_id = request.getParameter("subject_id");
		iSubjectService.deleteSubject(subject_id);
		return "ISOK";
	}
	/**
	 * 
	 * @Description:根据主键查找专题
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-17 下午8:52:41
	 */
	public String findSubjectById()
	{
		String subject_id = request.getParameter("subject_id").trim();
		Subject subject = iSubjectService.findById(subject_id);
		
		List<LpGalary> galleryList = iSubjectService.findAllGallery(null);
		List<SubjectDetail> detailList = iSubjectService.findDetailList(subject_id);
		request.setAttribute("subject", subject);
		
		request.setAttribute("galleryList", galleryList);
		request.setAttribute("detailList", detailList);
		return "FIND";
	}
	/**
	 * 
	 * @Description:查找所有作品集
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-18 下午8:24:53
	 */
	public String galleryList()
	{
		String where = null;
		String subject_id = request.getParameter("subject_id");
		String scanType = request.getParameter("scanType");
		if(scanType != null) {
			if("1".equals(scanType)){where="order by creat_time";}
			if("2".equals(scanType)){where="order by galary_id";}
			if("3".equals(scanType)){where="order by user_id";}
		}
		//findDetailList
		List<SubjectDetail> detailList = iSubjectService.findDetailList(subject_id);
		List<LpGalary> galleryList = iSubjectService.findAllGallery(where);
		request.setAttribute("detailList", detailList);
		request.setAttribute("galleryList", galleryList);
		request.setAttribute("subject_id", subject_id);
		return "toGalaryList";
	}
	/**
	 * 
	 * @Description: 编辑专题
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-18 上午10:31:19
	 */
	public String modifySubject()
	{
		LpUser user = getCurrentUser();
		 //获得文件
		/*if(getSubjectimg() != null)
		{
			File filePath = updateFileNameAndPath();
			String coverUrl = filePath.getPath().replace("\\", "/");
			uploadImage(subjectimg, filePath);
		    subject.setSubject_img(coverUrl);
		}*/
		String bigImage = request.getParameter("coverPath");
		/* String xstring=request.getParameter("x");*/
		/* if( !("".equals(xstring)) && xstring != null)
		 {
         int x = Integer.parseInt(xstring);  
         int y = Integer.parseInt(request.getParameter("y"));  
         int w = Integer.parseInt(request.getParameter("w"));  
         int h = Integer.parseInt(request.getParameter("h"));  
                   
         	String coverPath=bigImage;
         	String windPath=bigImage.replace("/", "\\");
         	ImageCutTwo imageCut = new ImageCutTwo();  
         	imageCut.cutImage(imagePath, x, y, w, h);
         	subject.setSubject_img(bigImage);
		 }*/
		 
		 if(bigImage != null && !"".equals(bigImage))
		 {
			 subject.setSubject_img(bigImage);
		 }
		List<LpGalary> selectGallery = new ArrayList<LpGalary>();
		List<LpGalary> galleryList = iSubjectService.findAllGallery(null);
		iSubjectService.saveOrUpdateSubject(subject);
		return "ISOK";
	}
	/**
	 * 添加或修改作品集
	 * @Description:添加或修改作品集
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-23 上午8:57:05
	
	public String addGallery()
	{
		String subject_id = request.getParameter("subject_id");
		String gallery_ids = request.getParameter("ids");
		String[] galleryss = gallery_ids.split(",");
		List<LpGalary> selectGallery = new ArrayList<LpGalary>();
		List<LpGalary> galleryList = iSubjectService.findAllGallery(null);
		Subject subject =  iSubjectService.findById(subject_id);
		for (int i = 0; i < galleryss.length; i++) 
		{
			for (LpGalary gallery : galleryList)
			{
				if(gallery.getGalaryId().equals(galleryss[i]))
				{
					selectGallery.add(gallery);
				}
			}
		}
//		iSubjectService.addGallery(subject, selectGallery);
		iSubjectService.addGallery(subject_id, galleryss);
		List<SubjectDetail> detailList = iSubjectService.findDetailList(subject_id);
		List<LpGalary> selectGalary = new ArrayList<LpGalary>();
		if(detailList != null && detailList.size() != 0)
		{
			for (SubjectDetail subjectDetail : detailList)
			{
				for (LpGalary galary : galleryList)
				{
					if(subjectDetail.getGallery_id().equals(galary.getGalaryId()))
					{
						selectGalary.add(galary);
					}
				}
			}
		}
		int maxLocation =0;
		if(detailList.size() != 0)
		{
			maxLocation = detailList.get(detailList.size() - 1).getSubjectGalaryLocation();
		}
		request.setAttribute("subject", subject);
		request.setAttribute("selectGalary", selectGalary);
		request.setAttribute("detailList", detailList);
		request.setAttribute("maxLocation", maxLocation);
		return "TOJSP";
	}
	 */
	
	public String addGallery() {
		try {
			String subject_id = request.getParameter("subject_id");
			String gallery_ids = request.getParameter("ids");
			String[] galleryss = gallery_ids.split(",");
			//保存作品集到详情表
			if(gallery_ids !=null && gallery_ids.length()>0){
				iSubjectService.addGallery(subject_id, galleryss,gallery_ids);
			}
			//返回上一个页面，重新查询一遍作品集
			Subject subject = iSubjectService.findById(subject_id);
			//使用视图查询，见data目录下的视图v_lp_subject_detail_back
			List<VLpSubjectDetailBack> selectGalary = iSubjectService.getVLpSubjectDetailBackBySubjectId(subject_id);
			int maxLocation = iSubjectService.getMaxLocationBySubjectId(subject_id);
			request.setAttribute("maxLocation", maxLocation);
			request.setAttribute("subject", subject);
			request.setAttribute("selectGalary", selectGalary);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "TOJSP";
	}
	
	private JSONObject jsonObject=new JSONObject();
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	/**
	 * 
	 * @Description:首页位置异步交互
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-24 下午4:03:34
	 */
	public String validateInputData() throws Exception
	{
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		int index_location= Integer.parseInt(request.getParameter("index_location"));
		List<Subject>  list =iSubjectService.findIndexList();
		jsonObject.put("result", true);
		for (Subject subject : list) 
		{
			if(index_location == subject.getIndex_location())
			{
				jsonObject.put("result", false);
				jsonObject.put("subjectName", subject.getSubject_name());
				jsonObject.put("subjectId", subject.getSubject_id());
				break;
			}
		}
		return SUCCESS;
	}
	/**
	 * @Description:专题位置异步交互
	 * @return
	 * @throws Exception
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-24 下午4:05:34
	 */
	public String validateSubjectData() throws Exception
	{
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		int subject_location= Integer.parseInt(request.getParameter("subject_location"));
		List<Subject>  list =iSubjectService.findIndexList();
		jsonObject.put("result", true);
		for (Subject subject : list) 
		{
			if(subject_location == subject.getSubject_location())
			{
				jsonObject.put("result", false);
				jsonObject.put("subjectName", subject.getSubject_name());
				jsonObject.put("subjectId", subject.getSubject_id());
				break;
			}
		}
		return SUCCESS;
	}
	
	public String modifyInputData() throws Exception
	{
		String index_subject_id = request.getParameter("index_subject_id");
		
		String subject_subject_id = request.getParameter("subject_subject_id");
		
		String nowLocation = request.getParameter("nowSubject_id");
		if(index_subject_id != null && nowLocation != null)
		{
			try {
				iSubjectService.updateIndexLocation(index_subject_id, null,nowLocation);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(subject_subject_id != null && nowLocation != null)
		{
			try {
				iSubjectService.updateLocation(null, subject_subject_id,nowLocation);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @Description:处理文件名和文件路径
	 * @return
	 * File
	 * @exception:
	 * @author: zhuxin
	 * @time:2014-12-26 下午2:35:37
	 */
//	private File updateFileNameAndPath(){
//		 //上传文件扩展名
//		 String type=subjectimgFileName.substring(subjectimgFileName.lastIndexOf("."));
//		 //新文件名
//		 String newImgName="lpSubject_Cover"+System.currentTimeMillis() + type;
//		 //获取图片上传路径，若磁盘不存在此路径，创建
//		 File filePath = new File(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+"/lpSubjectImg");
//		 if(!filePath.exists()){
//			filePath.mkdirs();
//			System.out.println("---------文章标题图片上传文件夹创建成功");
//		 }
//		 //路径+新文件名=文件保存的完整路径
//		 filePath = new File(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+"/lpSubjectImg"+"/"+newImgName);
//		 return filePath;
//	}
/**
 * 	
 * @Description:将图片写入
 * @param in
 * @param out
 * @return
 * boolean
 * @exception:
 * @author: zhuxin
 * @time:2014-12-26 下午3:05:04
 */
	/*public boolean uploadImage(File in, File out){
		  try {
	    	  //以服务起器文件保存地址和原文件名建立上传文件输出流
	    	   FileOutputStream fos = new FileOutputStream(out);
	    	   //以上传文件建立文件上传流
	    	   FileInputStream fis = new FileInputStream(in);
	    	   //将上传文件写到服务器
	    	   byte[] buffer = new byte[1024];
	    	   int len = 0;
	    	   while((len = fis.read(buffer))>0){
	    	    fos.write(buffer,0,len);
	    	   }
	    	   fos.close();
	    	   fis.close();
	    	   return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		}*/
/**
 *得到专题类，实现ModelDriven的方法 
 */
	@Override
	public Object getModel() {
		if(subject ==null)
		{
			subject = new Subject();
		}
		return subject;
	}
	/**
	 * 
	 * @Description:编辑专题并进行作品集的添加 
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2015-1-12 下午6:15:49
	 
	public String editGalary()
	{
		String subject_id = request.getParameter("subject_id").trim();
		Subject subject = iSubjectService.findById(subject_id);
		List<LpGalary> galleryList = iSubjectService.findAllGallery(null);
		List<SubjectDetail> detailList = null; 
		try {
			 detailList = iSubjectService.findDetailList(subject_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<LpGalary> selectGalary = new ArrayList<LpGalary>();
		if(detailList != null && detailList.size() != 0) {
			for (SubjectDetail subjectDetail : detailList) {
				for (LpGalary galary : galleryList) {
					if(subjectDetail.getGallery_id().equals(galary.getGalaryId())) {
						selectGalary.add(galary);
					}
				}
			}
		}
		int maxLocation =0;
		if(detailList.size() != 0)
		{
			maxLocation = detailList.get(detailList.size() - 1).getSubjectGalaryLocation();
		}
		
		request.setAttribute("subject", subject);
		request.setAttribute("galleryList", galleryList);
		request.setAttribute("detailList", detailList);
		request.setAttribute("selectGalary", selectGalary);
		request.setAttribute("maxLocation", maxLocation);
		return "TOJSP";
	}
	*/
	
	public String editGalary() {
		String subject_id = request.getParameter("subject_id").trim();
		Subject subject = iSubjectService.findById(subject_id);
		List<VLpSubjectDetailBack> selectGalary = iSubjectService.getVLpSubjectDetailBackBySubjectId(subject_id);
		int maxLocation = iSubjectService.getMaxLocationBySubjectId(subject_id);
		request.setAttribute("maxLocation", maxLocation);
		request.setAttribute("subject", subject);
		request.setAttribute("selectGalary", selectGalary);
		return "TOJSP";
	}
	
	/**
	public String deleteGalary()
	{
		String subjectId = request.getParameter("subjectId");
		String galaryId = request.getParameter("galaryId");
		try {
			iSubjectService.deleteGalary(subjectId, galaryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<SubjectDetail> detailList = iSubjectService.findDetailList(subjectId);
		List<LpGalary> selectGalary = selectGalary(subjectId);
		Subject subject = getSubject(subjectId);
		int maxLocation =0;
		if(detailList.size() != 0)
		{
			maxLocation = detailList.get(detailList.size() - 1).getSubjectGalaryLocation();
		}
		request.setAttribute("subject", subject);
		request.setAttribute("selectGalary", selectGalary);
		request.setAttribute("detailList", detailList);
		request.setAttribute("maxLocation", maxLocation);
		return "TOJSP";
	}
	*/
	public String deleteGalary()
	{
		String subjectId = request.getParameter("subjectId");
		String galaryId = request.getParameter("galaryId");
		try {
			iSubjectService.deleteGalary(subjectId, galaryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Subject subject = iSubjectService.findById(subjectId);
		List<VLpSubjectDetailBack> selectGalary = iSubjectService.getVLpSubjectDetailBackBySubjectId(subjectId);
		request.setAttribute("subject", subject);
		request.setAttribute("selectGalary", selectGalary);
		return "TOJSP";
	}
	
	
	public String changeStatus2()
	{
		String subject_id = request.getParameter("id");
		Integer subject_status =  Integer.parseInt(request.getParameter("subject_status").trim());
		List<SubjectDetail> detailList = iSubjectService.findDetailList(subject_id);
		if(subject_status == 0)
		{
			subject_status = 1;
		}
		else
		{
			subject_status = 0;
		}
		iSubjectService.updateStatus(subject_id, subject_status);
		List<VLpSubjectDetailBack> selectGalary = iSubjectService.getVLpSubjectDetailBackBySubjectId(subject_id);
		Subject subject = getSubject(subject_id);
		int maxLocation =0;
		if(detailList.size() != 0)
		{
			maxLocation = detailList.get(detailList.size() - 1).getSubjectGalaryLocation();
		}
		request.setAttribute("subject", subject);
		request.setAttribute("detailList", detailList);
		request.setAttribute("maxLocation", maxLocation);
		request.setAttribute("selectGalary", selectGalary);
		return "TOJSP";
	}
	
	public String updateGalaryLocation()
	{
		int newLocation = Integer.parseInt(request.getParameter("newLocation").trim());
		String subject_id = request.getParameter("subjectId");
		String galary_id = request.getParameter("gallaryId");
		int oldLocation = Integer.parseInt(request.getParameter("oldLocation").trim());
		SubjectDetail subjectDetail = new SubjectDetail();
		subjectDetail.setSubject_id(subject_id);
		subjectDetail.setGallery_id(galary_id);
		subjectDetail.setSubjectGalaryLocation(newLocation);
		iSubjectService.updateSubjectDetailLocation(subjectDetail,oldLocation);
		List<SubjectDetail> detailList = iSubjectService.findDetailList(subject_id);
		
		List<VLpSubjectDetailBack> selectGalary = iSubjectService.getVLpSubjectDetailBackBySubjectId(subject_id);
		Subject subject = getSubject(subject_id);
		int maxLocation =0;
		if(detailList.size() != 0)
		{
			maxLocation = detailList.get(detailList.size() - 1).getSubjectGalaryLocation();
		}
		request.setAttribute("subject", subject);
		request.setAttribute("selectGalary", selectGalary);
		request.setAttribute("detailList", detailList);
		request.setAttribute("maxLocation", maxLocation);
		return "TOJSP";
	}
	/**
	 * 
	 * @Description:异步图片上传
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @throws IOException 
	 * @time:2015-1-19 下午3:30:46
	 */
	public void subjectImgUpload() throws IOException
	{
		
		  HttpServletResponse resp=ServletActionContext.getResponse();
	       resp.setContentType("text/html;charset=utf-8");
	       resp.setCharacterEncoding("utf-8");
	       PrintWriter out = resp.getWriter();
	       
	       File file=null;
	       String fileName=null;
	  
	    	    file=this.getSubjectimg(); 
	    	    fileName=this.getSubjectimgFileName();    	   
	       
//		   //获得文件
//	       File newFile=this.uploadImage(filename);
//	       //通过流保存图片
//	       uploadImage(file, newFile);
	     //保存路径   
	   	String realPath = ImgUtil.saveImg(file, fileName, LaipaiConstants.UPLOAD_SUBJECT_PATH);
	   	String savePath=realPath.replace("\\", "/");
	   	String viePath=ImgUtil.getImgUrl(savePath);
		try{
			JSONObject jo = new JSONObject();
			jo.accumulate("realPath", viePath);
			jo.accumulate("relativePath", savePath);
			String result = jo.toString();
			out.println(result);
			
		}
		catch(Exception e){
					
		}finally {
	        out.flush();
	        out.close();
	    }
		
	}
	private List<LpGalary> getAllGalary()
	{
		List<LpGalary> galleryList = iSubjectService.findAllGallery(null);
		return galleryList;
	}
	private Subject getSubject(String subject_id)
	{
		Subject subject =  iSubjectService.findById(subject_id);
		return subject;
	}
	private List<LpGalary> selectGalary(String subject_id)
	{
		List<LpGalary> galleryList = iSubjectService.findAllGallery(null);
		List<SubjectDetail> detailList = iSubjectService.findDetailList(subject_id);
		List<LpGalary> selectGalary = new ArrayList<LpGalary>();
		if(detailList != null && detailList.size() != 0)
		{
			for (SubjectDetail subjectDetail : detailList)
			{
				for (LpGalary galary : galleryList)
				{
					if(subjectDetail.getGallery_id().equals(galary.getGalaryId()))
					{
						selectGalary.add(galary);
					}
				}
			}
		}
		return selectGalary;
	}
	private boolean uploadImage(File in, File out){
		  try {
	    	  //以服务起器文件保存地址和原文件名建立上传文件输出流
	    	   FileOutputStream fos = new FileOutputStream(out);
	    	   //以上传文件建立文件上传流
	    	   FileInputStream fis = new FileInputStream(in);
	    	   //将上传文件写到服务器
	    	   byte[] buffer = new byte[1024];
	    	   int len = 0;
	    	   while((len = fis.read(buffer))>0){
	    	    fos.write(buffer,0,len);
	    	   }
	    	   fos.close();
	    	   fis.close();
	    	   return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		}
	/**
	 * 上传图片，并返回图片路径
	 * */
//	public File uploadImage(String fileName){
//
//		// 修改上传的文件名，并返回路径
//		String imgType = fileName.substring(
//				fileName.lastIndexOf("."));
//		// 新文件名
//		String newImgName = System.currentTimeMillis()
//				+ UUID.randomUUID().toString() + imgType;
//		// 获取图片上传路径，若磁盘不存在此路径，创建
//		File filePath = new File(LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ "/lpSubjectImg");
//		if (!filePath.exists()) {
//			filePath.mkdirs();
//			System.out.println("---------上传文件夹创建成功");
//		}
//		// 路径+新文件名=文件保存的完整路径
//		String path = LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ "/lpSubjectImg" + "/" + newImgName; 
//		File newFile=new File(path);
////		file.renameTo(newFile);
//		return newFile;
//	  }
	/**
	 * 
	 * @Description:列表页面修改专题位置
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2015-1-21 下午6:02:37
	 */
	public String updateSubjectLocation()
	{
		String subject_id = request.getParameter("subject_id");
		int newLocation = Integer.parseInt(request.getParameter("newLocation").trim());
		iSubjectService.updateSubjectLocation(subject_id, newLocation);
		return "ISOK";
	}
	
	private JSONArray resultTree;//我要返回给页面的List
	
	public JSONArray getResultTree() {
		return resultTree;
	}
	public void setResultTree(JSONArray resultTree) {
		this.resultTree = resultTree;
	}
	List<LpGalary> allGalary;
	
	
	public void setAllGalary(List<LpGalary> allGalary) {
		this.allGalary = allGalary;
	}
	/**
	 * 
	 * @Description:ajax：查询所有作品集，并进行分页操作
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2015-1-26 下午3:24:35
	 */
	public String  galaryAjaxPaging()
	{
		int pageCount = 0;
		int pageSize = 8;
		int scanType = Integer.parseInt(request.getParameter("scanType").trim());
		//int pageSize = Integer.parseInt(request.getParameter("pageSize").trim());
		int nowPage = Integer.parseInt(request.getParameter("nowPage").trim());
		long rowCount = iSubjectService.countGalarySum();
		pageCount = (int)(rowCount/8);
		if(rowCount % 8 != 0)
		{
			pageCount++;
		}
		List<LpGalary> galleryList = null;
		try {
			List<GalaryClientShow> array=new ArrayList<GalaryClientShow>();
			try {
				galleryList = iSubjectService.allGalaryList(nowPage, pageSize, scanType);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(galleryList != null && galleryList.size() != 0)
			{
				for(LpGalary lpGalary:galleryList){
					GalaryClientShow pojo = new GalaryClientShow();
					pojo.setGalaryId(lpGalary.getGalaryId());
					pojo.setSubjectName(lpGalary.getSubjectName());
					pojo.setImgCover(ImgUtil.getImgUrl(lpGalary.getGalaryCover()));
					if(lpGalary.getLikeNumber() != null)
					{
						pojo.setLikeNumber(lpGalary.getLikeNumber());
					}
					else
					{
						pojo.setLikeNumber(0);
					}
					if(lpGalary.getViewNumber() != null)
					{
						pojo.setVierNumber(lpGalary.getViewNumber());
					}
					else
					{
						pojo.setVierNumber(0);
					}
					if(lpGalary.getCommentNumber() != null)
					{
						pojo.setCommentNumber(lpGalary.getCommentNumber());
					}
					else
					{
						pojo.setCommentNumber(0);
					}
					/*用户不存在，则忽略*/
					if(lpGalary.getLpUser()==null){
						continue;
					}
					pojo.setAuthor(lpGalary.getLpUser().getNickName());
					if(lpGalary.getLpService() != null){
						pojo.setPrice(lpGalary.getLpService().getLpServiceDetail().getPrice());
					}
					array.add(pojo);
				}
			}
			int gallaryCount= gallaryService.getGalleryCount("from VLpGalaryBackinfo where status=0 and galaryStatus=0");
			
			JSONArray ar=JSONArray.fromObject(array);
			jsonObject.put("list",ar);
			jsonObject.put("gallaryCount",gallaryCount);
			jsonObject.put("pageCount",pageCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 
	 * @Description:根据专题编号得到该专题的所有作品集
	 * @return
	 * String
	 * @exception:
	 * @author: zhuxin
	 * @time:2015-1-26 下午3:25:18
	
	public String getSelectGalary()
	{
		String subject_id = request.getParameter("subject_id");
		List<LpGalary> galleryList = iSubjectService.findAllGallery(null);
		List<SubjectDetail> detailList = iSubjectService.findDetailList(subject_id);
		List<SelectGalary> selectGalaryList = new ArrayList<SelectGalary>();
		if(detailList != null && detailList.size() != 0)
		{
			for (SubjectDetail subjectDetail : detailList)
			{
				for (LpGalary galary : galleryList)
				{
					if(subjectDetail.getGallery_id().equals(galary.getGalaryId()))
					{
						SelectGalary selectGalary = new SelectGalary();
						selectGalary.setGalaryId(galary.getGalaryId());
						selectGalary.setImgCover(galary.getGalaryCover());
						selectGalaryList.add(selectGalary);
					}
				}
			}
		}
		JSONArray list = JSONArray.fromObject(selectGalaryList);
		jsonObject.put("list", list);
		return SUCCESS;
	}
	 */
	
	
	public String getSelectGalary() {
		String subject_id = request.getParameter("subject_id");
		List<SelectGalary> selectGalaryList = new ArrayList<SelectGalary>();
		List<VLpSubjectDetailBack> list = iSubjectService.getVLpSubjectDetailBackBySubjectId(subject_id);
		if(list!=null && !list.isEmpty()){
			for(VLpSubjectDetailBack detail: list){
				SelectGalary selectGalary = new SelectGalary();
				selectGalary.setGalaryId(detail.getGalaryId());
				selectGalary.setImgCover(detail.getGalaryCover());
				selectGalaryList.add(selectGalary);
			}
		}
		JSONArray json = JSONArray.fromObject(selectGalaryList);
		jsonObject.put("list", json);
		return SUCCESS;
	}
	
    /** 
     * 裁剪头像 
     * @throws IOException 
     */  
    public void cutSubjectImage() throws IOException{  
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
        String bigImage = request.getParameter("imagePath");            
        String imagePath=bigImage;
        System.out.println(System.getProperty("os.name"));
        if((System.getProperty("os.name").contains("Windows"))){ 
        String windPath=bigImage.replace("/", "\\"); 
        //获取文件真实路径  
        //获取文件名  
      /*  String[] imageNameS = bigImage.split("/");  
        String imageName = imageNameS[imageNameS.length-1];*/  
        //文件正式路径  
          imagePath = "D:"+windPath;  
        }  
        //切割图片  
        ImageCut imageCut = new ImageCut();  
        imageCut.cutImage(imagePath, x, y, w, h,divw,divh);  
        String savePath=bigImage.replace("\\", "/");
    	String viePath=ImgUtil.getImgUrl(savePath);
    	try{
    		JSONObject jo = new JSONObject();
    		jo.accumulate("realPath", viePath);
    		jo.accumulate("relativePath", savePath);
    		//{"realPath":"XXX", "relativePath":"xxx"}
    		String result = jo.toString();
    		out.println(result);
    		
    	}
    	catch(Exception e){
    				
    	}finally {
            out.flush();
            out.close();
        }
    } 
	
}
