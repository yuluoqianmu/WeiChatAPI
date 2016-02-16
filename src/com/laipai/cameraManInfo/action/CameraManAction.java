package com.laipai.cameraManInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.cameraManInfo.service.ICameraManService;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpLoginHis;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.MD5keyBean;
import com.lyz.api.cache.ICache;
import com.lyz.api.cache.OcsCache;
import com.lyz.api.config.SystemConfig;
import com.lyz.labelData.json.GsonExt;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 

 * @Description:用户管理类

 * @author:lxd

 * @time:2014-12-16 下午2:12:44
 */

@Controller("cameraManAction")
@Scope("prototype")
public class CameraManAction extends BaseAction implements ModelDriven<LpUserBean>{
	
	private static final OcsCache cache = new OcsCache();
	
//	private static final GsonExt gson = new GsonExt();
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CameraManAction.class);
	private static String dest="";
	public LpUserBean  userInfoBean=new LpUserBean();
	public LpUserBean getModel() {
		return this.userInfoBean;
	}
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	@Resource(name=ICameraManService.SERVICE_NAME)
	private  ICameraManService cameraManService; 
	@Autowired
	private IBaseDao baseDao;
	@Autowired
	private IOperationManagerService operationManagerService;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	// Fields
		private File  headimgsFile;
		//文件类型
		private String  headimgsFileContentType;
		//文件名
		private String  headimgsFileFileName;
		
		
		private File idimgsFile;
		private String idimgsFileContenttype;
		private String idimgsFileFileName;
	    
	public File getIdimgsFile() {
			return idimgsFile;
		}

		public void setIdimgsFile(File idimgsFile) {
			this.idimgsFile = idimgsFile;
		}

		public String getIdimgsFileContenttype() {
			return idimgsFileContenttype;
		}

		public void setIdimgsFileContenttype(String idimgsFileContenttype) {
			this.idimgsFileContenttype = idimgsFileContenttype;
		}

		public String getIdimgsFileFileName() {
			return idimgsFileFileName;
		}

		public void setIdimgsFileFileName(String idimgsFileFileName) {
			this.idimgsFileFileName = idimgsFileFileName;
		}

	public File getHeadimgsFile() {
			return headimgsFile;
		}

		public void setHeadimgsFile(File headimgsFile) {
			this.headimgsFile = headimgsFile;
		}

		public String getHeadimgsFileContentType() {
			return headimgsFileContentType;
		}

		public void setHeadimgsFileContentType(String headimgsFileContentType) {
			this.headimgsFileContentType = headimgsFileContentType;
		}

		public String getHeadimgsFileFileName() {
			return headimgsFileFileName;
		}

		public void setHeadimgsFileFileName(String headimgsFileFileName) {
			this.headimgsFileFileName = headimgsFileFileName;
		}

		private JSONObject jsonObject=new JSONObject();

	
	public JSONObject getJsonObject() {
			return jsonObject;
		}

		public void setJsonObject(JSONObject jsonObject) {
			this.jsonObject = jsonObject;
		}

	/**
	 * TODO 分页和按条件查询
	 * @Description:查询摄影师
	 * @return
	 * @author: lxd
	 * @time:2014-12-16 下午2:57:30
	 */
	public  String queryall(){
		
		List<LpUserBean> listuserInfos=cameraManService.queyallBypage(request);
		request.setAttribute("userList", listuserInfos);
		return "jsp/cameraMan/cameraManList";
	}
	
	/**
	 * 
	
	 * @Description:添加摄影师准备数据
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-23 下午2:35:51
	 */
	 public  String toAddCameraman(){
         String userId=request.getParameter("userId");
         if(userId!=null){
        	 LpUser user= userManService.queryUserById(userId);
        	 if(StringUtils.isNotBlank(user.getHeadImage())){        		 
        		 user.setHeadImage(ImgUtil.getImgUrl(user.getHeadImage()));  
        	 }
        	 request.setAttribute("user", user);
        	 
         }
		 List citylist= operationManagerService.getAllOnlineCity();
		 request.setAttribute("citylist", citylist);
		 List stylelist =cameraManService.getSysStyle();
		 request.setAttribute("stylelist", stylelist);
		 return "jsp/cameraMan/addCameraMan";
	 }
	
	
	
	/**
	 * 
	   TODO　
	 * @Description:添加用户
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-16 下午7:12:07
	 */
	public String addCameraman(){
		String userId=request.getParameter("userId");
		 String cityId=request.getParameter("cityID");
		 String [] styleId=request.getParameterValues("styleId");
		//用户类型
		 int userType= userInfoBean.getUserType();
		 
		    //身份证照片
		    String idCardImage=userInfoBean.getIdCardImage();
		    //真实姓名
		    String realName= userInfoBean.getRealName();
		    //性别
		    String genderString=request.getParameter("userGender");
		    int gender=Integer.parseInt(genderString);
		    //器材
		    String grapherCarmer= userInfoBean.getGrapherCarmer();
		    //描述
		    String grapherDesc= userInfoBean.getGrapherDesc();
		    //手机号
		    String mobile = userInfoBean.getMobile();
		    
		if(userId!=null&&!"".equals(userId)){
		  LpUser user= userManService.queryUserById(userId);
		  user.setUserType(userType);
		  user.setUserGender(gender);
		  //user.setHeadImage(headImage);
		  user.setIdCardImage(idCardImage);
		  user.setRealName(realName);
		  user.setGrapherCarmer(grapherCarmer);
		  user.setGrapherDesc(grapherDesc);
		  user.setValidTime(new Timestamp(new java.util.Date().getTime()));
		  user.setMobile(mobile);
		  cameraManService.saveCameraman(user,cityId,styleId);
		}
		else{
		LpUser user =new LpUser();
		
		//用户
		 int accountSource= userInfoBean.getAccountSource();
		//头像
		String headImage= userInfoBean.getHeadImage();
		//账号
		String userName= userInfoBean.getUserName();
		String newuserName= userName.trim();
		//密码加密
	    String userPassword=userInfoBean.getUserPassword();
	    MD5keyBean bean = new MD5keyBean();
		String md5LogonPwd = bean.getkeyBeanofStr(userPassword);
	    //昵称
	    String nickName= userInfoBean.getNickName();
	   
	    user.setUserName(newuserName);
	    user.setUserPassword(md5LogonPwd);
	    user.setNickName(nickName);
	    user.setHeadImage(headImage);
	    user.setIdCardImage(idCardImage);
	    user.setUserGender(gender);
	    user.setGrapherCarmer(grapherCarmer);
	    user.setGrapherDesc(grapherDesc);
	    user.setAccountSource(0);
	    user.setRealName(realName);
	    user.setUserType(userType);
	    user.setAccountSource(accountSource);
	    user.setRegisterTime(new Timestamp(new java.util.Date().getTime()));
	    user.setValidTime(new Timestamp(new java.util.Date().getTime()));
	    user.setUserStatus(0);
	    user.setMobile(mobile);
		this.cameraManService.saveCameraman(user,cityId,styleId);	
		 
		}
		 return "tocameraMan";
	}
	/**
	 * 上传图片并JSON返回给页面
	
	 * @Description:TODO
	
	 * @exception:
	
	 * @author: lxd
	 * @throws IOException 
	
	 * @time:2014-12-17 上午10:09:03
	 */
   public void  uploadPic() throws IOException{
       HttpServletResponse resp=ServletActionContext.getResponse();
       resp.setContentType("text/html;charset=utf-8");
       resp.setCharacterEncoding("utf-8");
       PrintWriter out = resp.getWriter();
       String isHeadImg= request.getParameter("ishead");
       File file=null;
       String fileName=null;
       if("YES".equalsIgnoreCase(isHeadImg)){
    	    file=this.getHeadimgsFile(); 
    	    fileName=this.getHeadimgsFileFileName();    	   
       }else{
    	    file=this.getIdimgsFile();
    	    fileName=this.getIdimgsFileFileName();
    	   
       }
//       System.out.println("----------摄影师上传图片路径:"+file.getPath());
//	   //获得文件
//       File newFile=this.uploadImage(filename);
//       //通过流保存图片
//       uploadImage(file, newFile);
     //保存路径   
    String realPath = ImgUtil.saveImg(file, fileName, LaipaiConstants.UPLOAD_USER_PATH);
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
    * 
   
    * @Description:根据ID查询摄影师信息
   
    * @return
   
    * String
   
    * @exception:
   
    * @author: lxd
   
    * @time:2014-12-17 下午5:13:52
    */
	public  String getCameraman(){
		String userId=request.getParameter("userId");
		LpUser user= userManService.queryUserById(userId);
		LpUser camera =(LpUser) user.clone();
//		if(StringUtils.isNotEmpty(camera.getHeadImage())){
//			camera.setImagePath(camera.getHeadImage());
//			camera.setHeadImage(camera.getHeadImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//		
//		}
		camera.setHeadImage(ImgUtil.getImgUrl(camera.getHeadImage()));
//		if(camera.getIdCardImage()!=null){
//			camera.setIdCardImage(camera.getIdCardImage().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL));
//		}
		camera.setIdCardImage(ImgUtil.getImgUrl(camera.getIdCardImage()));
		request.setAttribute("cameraman",camera);
        List<LpStyle> listStyle=new ArrayList<LpStyle>();
        listStyle=cameraManService.getstyleByuser(userId);
         request.setAttribute("listStyle", listStyle);
         List<LpStyle> allStyle= new ArrayList<LpStyle>();
         allStyle = cameraManService.getallStyle();
         request.setAttribute("allStyle", allStyle);
         List<LpCity> listCity =new ArrayList<LpCity>();
         
         listCity=cameraManService.getallcity();
         request.setAttribute("listCity", listCity);
         
         //登录日志
         List<LpLoginHis> loginList = cameraManService.getLoginHis(userId, 0, 20);
         
         if(loginList != null && loginList.size() > 0)
         {
        	 List<Map> ms = new ArrayList<Map>();
        	 for(LpLoginHis his : loginList)
        	 {
        		 Map m = new HashMap();
        		 m.put("loginTime", his.getLoginTime());
        		 long time = 0;
        		 if(his.getLoginTime() != null && his.getLogoutTime() != null)
        		 {
        			 time = his.getLogoutTime().getTime() - his.getLoginTime().getTime();
        			 String du = org.apache.commons.lang.time.DurationFormatUtils.formatDuration(time, "H小时m分s秒");
        			 m.put("loginDuration", du);
        		 }
        		 else
        		 {
        			 m.put("loginDuration", "0");
        		 }
        		 ms.add(m);
        		 
        	 }
        	 request.setAttribute("loginList", ms);
         }
         
         
         
         
		return "cameramanInfo";
	}
	
	public String toTop(){
		String userId=request.getParameter("userId");
		LpUser user= userManService.queryUserById(userId);
		int userOrderNumber=userManService.getCount("from VLpOrder WHERE cameraId='"+userId+"' ");
		int userlikeNumber=userManService.getCount("from VLpLike WHERE userId='"+userId+"' and likeStatus=0 and likeType=0");
		int userfollowNumber=userManService.getCount("from LpFollowView WHERE userId='"+userId+"'");
		int fansNumber=userManService.getCount("from LpFollowView WHERE cameraId='"+userId+"'");
		int commentNumber=userManService.getCount("from LpComment WHERE lpUser.userId='"+userId+"' and commentType=0 and commentType=4");
		int serviceNumber=userManService.getCount("from VLpService where userId='"+userId+"'");
		int gallaryNumber=userManService.getCount("from LpGalary WHERE lpUser.userId='"+userId+"' and status=0 and galaryStatus=0");
		request.setAttribute("orderNumber",userOrderNumber);
		request.setAttribute("likeNumber",userlikeNumber);
		request.setAttribute("followNumber",userfollowNumber);
	/*	request.setAttribute("commentNumber",commentNumber);*/
		request.setAttribute("fansNumber",fansNumber);
		request.setAttribute("serviceNumber",serviceNumber);
		request.setAttribute("gallaryNumber",gallaryNumber);
		
		request.setAttribute("cameraman",user);
		return "top";
	}
	
	public  String toeditCameraman(){
		String userId=request.getParameter("userId");
		LpUser user= userManService.queryUserById(userId);
		request.setAttribute("cameraman",user);
		return "toCamermanInfo";
	}
	/**
	 * 
	
	 * @Description:编辑用户信息
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-30 上午9:07:29
	 */
	public String editCameraman(){
		String userId=request.getParameter("userId");
		String nickName=request.getParameter("nickName");
		String genderString=request.getParameter("userGender");
		int gender= Integer.parseInt(genderString);
		String mobile =request.getParameter("mobile");
		String cityId =request.getParameter("cityId");
		String [] styleids=  request.getParameterValues("styleId");
		String grapherCarmer=request.getParameter("grapherCarmer");
		String personDesc=request.getParameter("grapherDesc");
		
		LpUser user=userManService.queryUserById(userId);
		if(user!=null){
			user.setNickName(nickName);
			user.setUserGender(gender);
			user.setMobile(mobile);
			user.setGrapherCarmer(grapherCarmer);
			user.setGrapherDesc(personDesc);
			LpCity city= (LpCity) baseDao.getObjectById(LpCity.class, cityId);
			if(city!=null){				
				user.setLpCity(city);
			}
			 if(styleids!=null&&styleids.length>0){
		   Set<LpStyle> styles=new HashSet<LpStyle>();
			for(int i=0;i<styleids.length;i++){
				LpStyle style=(LpStyle) baseDao.getObjectById(LpStyle.class, styleids[i]);
				styles.add(style);
				
			}
			user.setLpStyle(styles);
			 }
			userManService.saveOrUpdateser(user);	
		}

		
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:禁用账号
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-30 下午8:49:00
	 */
	public String disableCamera(){
		String userId=request.getParameter("userId");
		LpUser user=userManService.queryUserById(userId);
		
		if(user!=null){
			user.setUserStatus(1);
			userManService.saveOrUpdateser(user);	
		}
		return SUCCESS;
	}
	/**
	 * 
	
	 * @Description:解封账号
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-30 下午8:48:39
	 */
	public String enableCamera(){
		String userId=request.getParameter("userId");
		LpUser user=userManService.queryUserById(userId);
		
		if(user!=null){
			user.setUserStatus(0);
			userManService.saveOrUpdateser(user);	
		}
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:重置摄影师的密码
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-30 下午8:57:43
	 */
	public String changePSW(){
		String userId=request.getParameter("userId");
		LpUser user=userManService.queryUserById(userId);
		
		if(user!=null){
			MD5keyBean bean = new MD5keyBean();
			String md5LogonPwd = bean.getkeyBeanofStr(LpUserBean.LP_CAMERAMAN_PSW);
			user.setUserPassword(md5LogonPwd);
			userManService.saveOrUpdateser(user);	
		}
		return SUCCESS;
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
//				+ LpUserBean.LP_USER_IMGURL);
//		if (!filePath.exists()) {
//			filePath.mkdirs();
//			System.out.println("---------上传文件夹创建成功");
//		}
//		// 路径+新文件名=文件保存的完整路径
//		String path = LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ LpUserBean.LP_USER_IMGURL + "/" + newImgName; 
//		File newFile=new File(path);
////		file.renameTo(newFile);
//		return newFile;
//
//	  }
	
	public String deleteUser(){
		
		String[] ids = request.getParameterValues("userId");
		if(ids != null && ids.length > 0)
		{
			for(int i = 0; i < ids.length; i++)
			{
				String userId = ids[i];
				cameraManService.deleteUserById(userId);
				
			}
		}
		

		
		return "tocameraMan";
		
	}
	
	public String userAddGallery(){
		 String userId=request.getParameter("userId");
		 List styleList =new ArrayList(); 
		 styleList=galleryService.queryallStyle();
		 request.setAttribute("stylelist", styleList);
		 String hql="from LpService L where L.lpUser.userId='"+userId+"'";
		 LpUser user= userManService.queryUserById(userId);
		 List<LpService> services= baseDao.queryListObjectAll(hql);
		 request.setAttribute("servicelist", services);
		 request.setAttribute("cameraman", user);
		return "touserAddG";
	}
	private String ids="";
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String share(){
		
		
		int pageSize=10;
		String userId=request.getParameter("userId");
		if(StringUtils.isEmpty(userId)){
			return ERROR;
		}
		
		/*查询缓存*/
		String key = "shareCam:"+userId;
		Object obj = cache.getObjectData(key);
		if(obj != null && SystemConfig.openCache){
			CacheData cacheData = (CacheData)obj;
			cacheData.getUser().setLpStyle(cacheData.getStyle());
			if(cacheData != null){
				request.setAttribute("count", cacheData.getCount());
				request.setAttribute("lpUser", cacheData.getUser());
				request.setAttribute("pageSize", cacheData.getPageSize());
				request.setAttribute("ids", cacheData.getIds());
				request.setAttribute("lpGalarys",cacheData.getLpGalarys());
				return SUCCESS;
			}
			
		}
		logger.info("camera share from db:"+userId);
		try{
//		logger.info("from db:0");
		LpUser user=userManService.queryUserById(userId);
//		logger.info("from db:1");
		if(user==null){
			return ERROR;
		}
//		logger.info("from db:2");
		/**作品集总数*/
		int count=this.galleryService.getGalleryCount("from LpGalary where lpUser.userId='"+user.getUserId()+"' and status=0");
//		logger.info("from db:3");
		/**查作品集前两条*/
		@SuppressWarnings("unchecked")
		List<LpGalary> lpGalarys=this.galleryService.getGalleryTop("from LpGalary where lpUser.userId='"+user.getUserId()+"' and status=0",pageSize,LpGalary.class);
//		logger.info("from db:4");
		StringBuffer sb=new StringBuffer();
		for(LpGalary l:lpGalarys){
			sb.append(l.getGalaryId()+",");
			l.setGalaryCover(ImgUtil.getImgUrl(l.getGalaryCover()));
		}
//		logger.info("from db:5");
		if(StringUtils.isNotEmpty(user.getHeadImage())){
			user.setHeadImage(ImgUtil.getImgUrl(user.getHeadImage()));
		}
//		logger.info("from db:6");
		/*把数据加入缓存*/
		if(SystemConfig.openCache){
			Set<LpStyle> style = user.getLpStyle();
			for(LpStyle s : style){
				//遍历，防止延迟加载
			}
//			logger.info("set galary into cache...from db");
			CacheData cacheData = new CacheData(count, user, pageSize, sb.toString(), lpGalarys);
			cacheData.setStyle(style);
			cache.setObjectData(key, cacheData, ICache.EXPIRED_ONE_MINUTE);
//			logger.info("set galary into cache finish...from db");
		}
//		logger.info("from db:7");
		/*防止延迟加载*/
		request.setAttribute("count", count);
		request.setAttribute("lpUser", user);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("ids", sb.toString());
		request.setAttribute("lpGalarys",lpGalarys);
		
		
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public static class CacheData implements Serializable{
		
		private int count;
		
		private LpUser user;
		
		private int pageSize;
		
		private String ids;
		
		private List<LpGalary> lpGalarys;
		
		private Set<LpStyle> style;
		
		public CacheData(int count, LpUser user, int pageSize, String ids, List<LpGalary> lpGalarys){
			
			this.count = count;
			this.user = user;
			this.pageSize = pageSize;
			this.ids = ids;
			this.lpGalarys = lpGalarys;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public LpUser getUser() {
			return user;
		}

		public void setUser(LpUser user) {
			this.user = user;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public String getIds() {
			return ids;
		}

		public void setIds(String ids) {
			this.ids = ids;
		}

		public List<LpGalary> getLpGalarys() {
			return lpGalarys;
		}

		public void setLpGalarys(List<LpGalary> lpGalarys) {
			this.lpGalarys = lpGalarys;
		}

		public Set<LpStyle> getStyle() {
			return style;
		}

		public void setStyle(Set<LpStyle> style) {
			this.style = style;
		}

		
		
		
	}
	public String loadMore(){
		String userId=request.getParameter("userId");
		logger.debug(userId);
		if(StringUtils.isEmpty(ids)||StringUtils.isEmpty(userId)){
			return ERROR;
		}
		String[] array_ids=ids.split(",");
		StringBuffer sb=new StringBuffer();
		for(String str:array_ids){
			sb.append("'"+str+"'"+",");
		}
		String in=sb.substring(0, sb.length()-1);
		logger.debug(in);
		try{
		List<LpGalary> lpGalarys=this.galleryService.getGalleryTop("from LpGalary where lpUser.userId='"+userId+"' and status=0 and galaryId not in("+in+")",2,LpGalary.class);
	    logger.info(lpGalarys.size());
		List<Map<String,Object>> lists=new ArrayList<Map<String,Object>>();
		for(LpGalary lg:lpGalarys){
			Map<String,Object> map=new HashMap<String,Object>();
			String styleName="";
			if(lg.getLpService()!=null&&lg.getLpService().getLpServiceDetail()!=null&&lg.getLpService().getLpServiceDetail().getLpStyle()!=null){
				List<LpStyle> set=lg.getLpService().getLpServiceDetail().getLpStyle();
				if(set!=null&&set.size()>0){
					styleName=set.iterator().next().getStyleName();
				}
			}
			map.put("styleName", styleName);
			map.put("galaryId", lg.getGalaryId());
			map.put("galaryCover", ImgUtil.getImgUrl(lg.getGalaryCover()));
			map.put("subjectName", lg.getSubjectName());
			map.put("viewNumber", lg.getViewNumber());
			map.put("commentNumber", lg.getCommentNumber());
			if(lg.getLpService()!=null&&lg.getLpService().getLpServiceDetail()!=null&&lg.getLpService().getLpServiceDetail().getPrice()!=null&&lg.getLpService().getLpServiceDetail().getPrice()>0){
			   map.put("price", lg.getLpService().getLpServiceDetail().getPrice());
			}else{
				map.put("price","0");
			}
			lists.add(map);
		}
		JSONArray json=JSONArray.fromObject(lists);
		jsonObject.put("results", json);
		logger.debug(jsonObject);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String getpictures(){
		int pageSize=2;
		String galaryId=request.getParameter("galaryId");
		String userId=request.getParameter("userId");
		if(StringUtils.isEmpty(galaryId)||StringUtils.isEmpty(userId)){
			return ERROR;
		}
		try{
		LpUser user=userManService.queryUserById(userId);
		LpGalary lgGalary=this.galleryService.getgalleryByID(galaryId);
		int count=this.galleryService.getGalleryCount("from LpGalaryDetail where status=0 and lpGalary.galaryId='"+galaryId+"'");
		List<LpGalaryDetail> photos=this.galleryService.getGalleryTop("from LpGalaryDetail where status=0 and lpGalary.galaryId='"+galaryId+"'", pageSize,LpGalaryDetail.class);
		StringBuffer sb=new StringBuffer();
		for(LpGalaryDetail lg:photos){
			sb.append(lg.getPhotoId()+",");
			lg.setPhotoSrc(ImgUtil.getImgUrl(lg.getPhotoSrc()));
		}
		if(StringUtils.isNotEmpty(user.getHeadImage())){
			user.setHeadImage(ImgUtil.getImgUrl(user.getHeadImage()));
		}
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
		request.setAttribute("lpUser", user);
		request.setAttribute("photos", photos);
		request.setAttribute("ids", sb.toString());
		request.setAttribute("lgGalary",lgGalary);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return "pictures";
	}
	public String loadMorePictures(){
		String galaryId=request.getParameter("galaryId");
		logger.debug(galaryId);
		if(StringUtils.isEmpty(ids)||StringUtils.isEmpty(galaryId)){
			return ERROR;
		}
		String[] array_ids=ids.split(",");
		StringBuffer sb=new StringBuffer();
		for(String str:array_ids){
			sb.append("'"+str+"'"+",");
		}
		String in=sb.substring(0, sb.length()-1);
		logger.debug(in);
		try{
		List<LpGalaryDetail> photos=this.galleryService.getGalleryTop("from LpGalaryDetail where status=0 and photoId not in("+in+") and lpGalary.galaryId='"+galaryId+"'", 2,LpGalaryDetail.class);
		List<Map<String,String>> lists=new ArrayList<Map<String,String>>();
		for(LpGalaryDetail ld:photos){
			Map<String,String> map=new HashMap<String, String>();
			map.put("photoSrc", dest+ld.getPhotoSrc().replaceAll(LaipaiConstants.UPLOAD_ABSOLUTE_IMG, LaipaiConstants.UPLOAD_VIRTUAL_IMG));
			map.put("photoId", ld.getPhotoId());
			lists.add(map);
		}
		JSONArray json=JSONArray.fromObject(lists);
		jsonObject.put("results", json);
		logger.debug(jsonObject);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	public String shareShowreel(){
		int pageSize=10;
		String galaryId=request.getParameter("galaryId");
		
		if(StringUtils.isEmpty(galaryId)){
			return ERROR;
		}
		
		/*尝试从缓存中获取内容*/
		String key = "shareGa:"+galaryId;
		Object obj = cache.getObjectData(key);
		if(obj != null && SystemConfig.openCache){
			GalaryCacheData cacheData = (GalaryCacheData)obj;
			if(cacheData != null){
				
				request.setAttribute("photos", cacheData.getPhotos());
				request.setAttribute("count", cacheData.getCount());
				request.setAttribute("lgGalary",cacheData.getLgGalary());
				request.setAttribute("pageSize", cacheData.getPageSize());
				request.setAttribute("ids", cacheData.getIds());
				
				return "shareShowreel";
			}
		}
		
		logger.info("galary share from db:"+galaryId);
		
		int count=this.galleryService.getGalleryCount("from LpGalaryDetail where status=0 and lpGalary.galaryId='"+galaryId+"'");
//		logger.info("from db:1");
		if(StringUtils.isEmpty(galaryId)){
			return ERROR;
		}
//		logger.info("from db:2");
		try{
			LpGalary lgGalary=this.galleryService.getgalleryByID(galaryId);
//			logger.info("from db:3");
			if(lgGalary==null){
				return ERROR;
			}
//			logger.info("from db:4");
			List<LpGalaryDetail> photos=this.galleryService.getGalleryTop("from LpGalaryDetail where status=0  and lpGalary.galaryId='"+galaryId+"'",pageSize,LpGalaryDetail.class);
//			logger.info("from db:5");
			StringBuffer sb=new StringBuffer();
			for(LpGalaryDetail lg:photos){
				sb.append(lg.getPhotoId()+",");
				lg.setPhotoSrc(ImgUtil.getImgUrl(lg.getPhotoSrc()));
			}
//			logger.info("from db:6");
			if(lgGalary.getLpUser()!=null && StringUtils.isNotEmpty(lgGalary.getLpUser().getHeadImage())){
				lgGalary.getLpUser().setHeadImage(ImgUtil.getImgUrl(lgGalary.getLpUser().getHeadImage()));
			}
//			logger.info("from db:7");
			lgGalary.setGalaryCover(ImgUtil.getImgUrl(lgGalary.getGalaryCover()));
			
			/*加入缓存*/
			if(SystemConfig.openCache){
//				logger.info("from db:set cache");
				GalaryCacheData cacheData = new GalaryCacheData(photos, count, lgGalary, pageSize, sb.toString());
//				logger.info("from db:data");
//				logger.info("from db:data to gson");
				cache.setObjectData(key, cacheData, ICache.EXPIRED_ONE_MINUTE);
//				logger.info("from db:set cache finish!");
			}
//			logger.info("from db:8");
			request.setAttribute("photos", photos);
			request.setAttribute("count", count);
			request.setAttribute("lgGalary",lgGalary);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("ids", sb.toString());
			
			
		}catch(Exception e){
			logger.error(e);
			return ERROR;
		}
		return "shareShowreel";
	}
	
	public static class GalaryCacheData implements Serializable{
		
		private List<LpGalaryDetail> photos;
		
		private int count;
		
		private LpGalary lgGalary;
		
		private int pageSize;
		
		private String ids;
		
		public GalaryCacheData(List<LpGalaryDetail> photos, int count, LpGalary lgGalary, int pageSize, String ids){
			
			this.photos = photos;
			this.count = count;
			this.lgGalary = lgGalary;
			this.pageSize = pageSize;
			this.ids = ids;
		}

		public List<LpGalaryDetail> getPhotos() {
			return photos;
		}

		public void setPhotos(List<LpGalaryDetail> photos) {
			this.photos = photos;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public LpGalary getLgGalary() {
			return lgGalary;
		}

		public void setLgGalary(LpGalary lgGalary) {
			this.lgGalary = lgGalary;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public String getIds() {
			return ids;
		}

		public void setIds(String ids) {
			this.ids = ids;
		}
		
		
	}
	
	 public void  changeIdImg() throws IOException{
	       HttpServletResponse resp=ServletActionContext.getResponse();
	       resp.setContentType("text/html;charset=utf-8");
	       resp.setCharacterEncoding("utf-8");
	       PrintWriter out = resp.getWriter();
	       String userId=request.getParameter("userId");
//	       File file=null;
//	       String fileName=null;
////
//	    	    file=this.getIdimgsFile();
//	    	    filename=this.getIdimgsFileFileName();
//	       System.out.println("----------摄影师上传图片路径:"+file.getPath());
//		   //获得文件
//	       File newFile=this.uploadImage("");
//	       //通过流保存图片
//	       uploadImage(file, newFile);
//	     //保存路径   
//	   	String realPath = newFile.getPath();
//	   	String savePath=realPath.replace("\\", "/");
	    String savePath = ImgUtil.saveImg(getIdimgsFile(), getIdimgsFileFileName(), LaipaiConstants.UPLOAD_USER_PATH);
	   	String viePath=ImgUtil.getImgUrl(savePath);
	   	LpUser cameraman= userManService.queryUserById(userId);
	   	cameraman.setIdCardImage(savePath);
	   	userManService.saveOrUpdateser(cameraman);
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
}

