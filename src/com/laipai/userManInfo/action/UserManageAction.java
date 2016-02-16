package com.laipai.userManInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.FileUploadUtils;
import com.laipai.base.util.ImageCut;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.SpringContextUtil;
import com.laipai.cameraManInfo.service.ICameraManService;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.VLpService;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userInfo.pojo.UserInfo;
import com.laipai.userManInfo.dto.FeedBackBean;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFeedback;
import com.laipai.userManInfo.pojo.LpFeedbackView;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpLoginHis;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.laipai.userManInfo.util.MD5keyBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 

 * @Description:用户管理类

 * @author:lxd

 * @time:2014-12-16 下午2:12:44
 */
@Scope("prototype")
@Controller("userManAction")
public class UserManageAction extends BaseAction implements ModelDriven<LpUserBean>{
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserManageAction.class);
	public LpUserBean  userInfoBean=new LpUserBean();
	public LpUserBean getModel() {
		return this.userInfoBean;
	}
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	
	@Resource(name="serviceService")
	private IServiceService serviceService;
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	public IUserManInfoService getUserManService() {
		return userManService;
	}
	
	private IBaseDao baseDao;
	
	public void setUserManService(IUserManInfoService userManService) {
		this.userManService = userManService;
	}
	
	@Resource(name=ICameraManService.SERVICE_NAME)
	private  ICameraManService cameraManService; 

	
	// Fields
		private File  imgsFile;
		//文件类型
		private String  imgsFileContentType;
		//文件名
		private String  imgsFileFileName;
		//保存路径
		private String savePath;
		
	public String getSavePath() {
			return savePath;
		}
		public void setSavePath(String savePath) {
			this.savePath = savePath;
		}
	public File getImgsFile() {
			return imgsFile;
		}
		public void setImgsFile(File imgsFile) {
			this.imgsFile = imgsFile;
		}
		public String getImgsFileContentType() {
			return imgsFileContentType;
		}
		public void setImgsFileContentType(String imgsFileContentType) {
			this.imgsFileContentType = imgsFileContentType;
		}
		public String getImgsFileFileName() {
			return imgsFileFileName;
		}
		public void setImgsFileFileName(String imgsFileFileName) {
			this.imgsFileFileName = imgsFileFileName;
		}

    private List<String> account;  //传到前台显示用
	
	public List<String> getAccount() {
		return account;
	}
	public void setAccount(List<String> account) {
		this.account = account;
	}
	/**
	 * TODO 分页和按条件查询
	 * @Description:查询所有用户
	 * @return
	 * @author: lxd
	 * @time:2014-12-16 下午2:57:30
	 */
	public  String queryall(){
		
		List<LpUserBean> listuserInfos=userManService.queryAllBypage(request);
		request.setAttribute("userList", listuserInfos);
		return "jsp/usermanage/userList";
	}
	/**
	 * 
	   TODO　与AAP接口未考虑
	 * @Description:添加用户
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-16 下午7:12:07
	 */
	public String adduser(){
		LpUser user =new LpUser();
		String userName= userInfoBean.getUserName();
		String newUserName=userName.trim();
	    String userPassword=userInfoBean.getUserPassword();
	    
	    MD5keyBean bean = new MD5keyBean();
		String md5LogonPwd = bean.getkeyBeanofStr(userPassword);
	    String nickName= userInfoBean.getNickName();
	    String headImage= userInfoBean.getHeadImage();
	    user.setUserName(newUserName);
	    user.setUserPassword(md5LogonPwd);
	    user.setNickName(nickName);
	    user.setHeadImage(headImage);
	    user.setAccountSource(0);
	    user.setUserType(0);
	    user.setUserStatus(0);
	    user.setLoginStatus(1);
	    user.setRegisterTime(new Timestamp(new java.util.Date().getTime()));
		this.userManService.saveOrUpdateser(user);	
		return SUCCESS;
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
	   //获得文件
         File file=this.getImgsFile();
	     String fileName=this.getImgsFileFileName();
	     //String savePath=this.getSavePath();
//	  
//	     File  newFile=this.uploadImage(filaname);
//
//	     //通过流保存图片
//	       uploadImage(file, newFile);
	//保存路径   
	       String realPath = ImgUtil.saveImg(file, fileName, LaipaiConstants.UPLOAD_USER_PATH);

	//String realPath=filePath.getPath();
	String savePath=realPath.replace("\\", "/");
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
   private void uploadImage(File in, File out) {
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
	    	   
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
/**
    * 
   
    * @Description:根据ID查询用户
   
    * @return
   
    * String
   
    * @exception:
   
    * @author: lxd
   
    * @time:2014-12-17 下午5:13:52
    */
	public  String queryUserById(){
		String userId=request.getParameter("userId");
		LpUser user= userManService.queryUserById(userId);
		if(user.getHeadImage()!=null){
	    user.setImagePath(user.getHeadImage());
        user.setHeadImage(ImgUtil.getImgUrl(user.getHeadImage()));
		}
        request.setAttribute("user", user);
        
        
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
        
        
		return "lisview";
	}
	
	/**
	 * 用户管理，删除用户
	 * @return
	 */
	public String deleteUser(){
		String[] ids = request.getParameterValues("userId");
		if(ids != null && ids.length > 0)
		{
			for(int i = 0; i < ids.length; i++)
			{
				String userId = ids[i];
				userManService.deleteUserById(userId);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:根据用户ID查询评论
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-24 下午3:34:25
	 */
	public String queryComment(){
		//LpUser user=(LpUser)request.getSession().getAttribute("user");
		String userID=request.getParameter("userId");
		List<LpComment> list=userManService.queryComment(request,userID);
		request.setAttribute("commentInfo", list);
		request.setAttribute("userId",userID);
		return SUCCESS;
	}
	/**
	 * 
	
	 * @Description:根据评论ID删除评论
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-24 下午4:20:40
	 */
	public String deleteCommentById(){
		String commentId= request.getParameter("commentId");
		userManService.deleteCommentById(commentId);
        return SUCCESS;
		
	}
	/**
	 * 
	
	 * @Description:根据用户ID查询关注
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-24 下午3:32:01
	 */
	public String queryFollow(){
		//LpUser user=(LpUser)request.getSession().getAttribute("user");
		String userId=request.getParameter("userId");
		List list=userManService.queryFollow(request,userId);
		request.setAttribute("followInfo", list);
		request.setAttribute("userId",userId);
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:根据关注ID删除关注
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 上午9:55:10
	 */
	public String deleteFollowById(){
		String followId =request.getParameter("followId"); 
		userManService.deleteFollowById(followId);
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:根据用户ID查询喜欢
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 上午9:55:47
	 */
	public String queryLike(){
		String userId=request.getParameter("userId");
		List<LpLike> list=userManService.queryLike(request,userId);
		request.setAttribute("likeInfo", list);
		request.setAttribute("userId",userId);
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:根据用户ID删除喜欢
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 上午11:21:30
	 */
	public String deleteLikeById(){
		String likeId= request.getParameter("likeId");
		userManService.deleteLikeById(likeId);
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:根据摄影师ID查询作品集
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 上午11:28:01
	 */
	public String queryGalary(){
		String userId=request.getParameter("userId");
		String galaryId=request.getParameter("galaryId");
		if(galaryId!=null){
			LpGalary gallary= galleryService.getgalleryByID(galaryId);
			if(gallary.getLpUser()!=null){
				userId=gallary.getLpUser().getUserId();
			}
		}
		List<LpGalary> list=userManService.queryGalary(request,userId);
		 for(LpGalary gallery:list){
			 String gallaryId=gallery.getGalaryId();
			 gallery.setGalaryCover(ImgUtil.getImgUrl(gallery.getGalaryCover())); 
			 int commentCount= serviceService.getCount(gallaryId);
			 gallery.setCommentNumber(commentCount);
		 }
		 
		 
		request.setAttribute("galaryInfo", list);
		request.setAttribute("userId",userId);
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:根据摄影师ID查询粉丝
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 上午11:53:30
	 */
	public String queryFans(){
		String userId=request.getParameter("userId");
		List<LpFollow> list=userManService.queryFans(request,userId);
		request.setAttribute("fansInfo", list);
		request.setAttribute("userId",userId);
		return this.queryFollow();
	}

	/**
	 * 
	
	 * @Description:根据关注ID删除粉丝
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 下午1:47:23
	 */
	public String deleteFansById(){
		userManService.deleteFollowById(request.getParameter("followId"));
		return this.queryFans();
	}
	
	/**
	 * 
	
	 * @Description:根据摄影师ID查询服务
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 下午2:18:17
	 */
	public String queryService(){
		String userId=request.getParameter("userId");
		List<VLpService> list=serviceService.queryServiceByUserId(request,userId);
		request.setAttribute("serviceInfo", list);
		request.setAttribute("userId",userId);
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:摄影师管理——下架服务
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 下午2:20:58
	 */
	public String offService(){
		serviceService.on_off_Service(request.getParameter("serviceId"), "off");
		return this.queryService();
	}
	
	/**
	 * 
	
	 * @Description:摄影师管理——上架服务
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 下午2:22:05
	 */
	public String onService(){
		serviceService.on_off_Service(request.getParameter("serviceId"), "on");
		return this.queryService();
	}
	
	/**
	 * 
	
	 * @Description:摄影师管理——删除服务
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-25 下午2:23:09
	 */
	public String deleteServiceById(){
		serviceService.deleteById(request.getParameter("serviceId"));
		return this.queryService();
	}
	
	
    public String checkUser(){
    	//获取页面传递的登录名
    String userName=request.getParameter("userAccount");
    //使用登录名作为查询条件，判断当前输入的登录名是否在数据库中存在，返回message
    	/**
    			 * 	   * message=1：表示登录名为空，不可以【保存】
    				   * message=2：表示登录名出现重复，不可以【保存】
    				   * message=3：表示登录名没有出现重复，可以【保存】
    	*/
    	String message = userManService.checkUser(userName);
    			//放置到栈顶
    	userInfoBean.setMessage(message);
    	return "checkUser";
    	
    	
    }
	/**
	 * 
	
	 * @Description:修改用户昵称
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-31 下午1:51:17
	 */
	public String changeNick(){
		String userId=request.getParameter("userId");
		String nickName =request.getParameter("nickName");
		LpUser user=userManService.queryUserById(userId);
		if(user!=null){
			
			user.setNickName(nickName);
			userManService.saveOrUpdateser(user);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	
	 * @Description:修改用户手机号
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: xbl
	
	 * @time:2015-3-24 上午0:36:17
	 */
	public String changeMobile(){
		String userId=request.getParameter("userId");
		String mobile =request.getParameter("mobile");
		LpUser user=userManService.queryUserById(userId);
		if(user!=null){
			
			user.setMobile(mobile);
			userManService.saveOrUpdateser(user);
		}
		return SUCCESS;
	}
	
	public String toTop(){
		String userId=request.getParameter("userId");
		/*LpUser user= (LpUser) baseDao.getObjectById(LpUser.class, userId);*/
		LpUser user=userManService.queryUserById(userId);
		request.setAttribute("user",user);
		int userOrderNumber=userManService.getCount("from VLpOrder WHERE userId='"+userId+"' ");
		int userlikeNumber=userManService.getCount("from VLpLike WHERE userId='"+userId+"' and likeStatus=0 and likeType=0");
		int userfollowNumber=userManService.getCount("from LpFollowView WHERE userId='"+userId+"'");
		int commentNumber=userManService.getCount("from LpComment WHERE lpUser.userId='"+userId+"' and commentType=0 or commentType=4");
		request.setAttribute("orderNumber",userOrderNumber);
		request.setAttribute("likeNumber",userlikeNumber);
		request.setAttribute("followNumber",userfollowNumber);
		request.setAttribute("commentNumber",commentNumber);
		return "totop";
	}
	
	public  String toeditUser(){
		String userId=request.getParameter("userId");
		LpUser user= userManService.queryUserById(userId);
		request.setAttribute("user",user);
		return "touserInfo";
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
//			System.out.println("---------用户头像图片上传文件夹创建成功");
//		}
//		// 路径+新文件名=文件保存的完整路径
//		String path = LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ LpUserBean.LP_USER_IMGURL + "/" + newImgName; 
//		File newFile=new File(path);
//		//file.renameTo(newFile);
//		//String filePath2=LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL+"/"+newImgName;
//		//return filePath2;
//		return newFile;
//
//	  }
	/**
	 * 
	
	 * @Description:修改用户头像
	
	 * @throws IOException
	
	 * void
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2015-1-8 下午5:54:51
	 */
	 public void  changeHeadImg() throws IOException{
	       HttpServletResponse resp=ServletActionContext.getResponse();
	       resp.setContentType("text/html;charset=utf-8");
	       resp.setCharacterEncoding("utf-8");
	       PrintWriter out = resp.getWriter();
		   //获得文件
	         File file=this.getImgsFile();
		     String filaName=this.getImgsFileFileName();
		     //String savePath=this.getSavePath();
		     String realPath = ImgUtil.saveImg(file, filaName, LaipaiConstants.UPLOAD_USER_PATH);
//		     File  newFile=this.uploadImage(filaname);
//
//		     //通过流保存图片
//		       uploadImage(file, newFile);
		//保存路径   
//		String realPath = savePath;

		//String realPath=filePath.getPath();
		String savePath=realPath.replace("\\", "/");
		String viePath=ImgUtil.getImgUrl(savePath);
		String userId=request.getParameter("userId");
		String nickName =request.getParameter("nickName");
		LpUser user=userManService.queryUserById(userId);
		if(user!=null){		
			user.setHeadImage(savePath);
			userManService.saveOrUpdateser(user);
		}
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
	 
	 /**
	  * 
	 
	  * @Description:ajax 自动补全用户名
	 
	  * @return
	 
	  * String
	 
	  * @exception:
	 
	  * @author: lxd
	 
	  * @time:2015-1-11 下午3:05:24
	  */
	 public String findUserByAccount() {
			try {
				String account =request.getParameter("ajaxAccount");
				String userType =request.getParameter("userType");
				List<String> list =userManService.getUserAccount(account,userType);
				this.setAccount(list);
				//ServletActionContext.getContext().getValueStack().push(list);
			} catch (Exception e) {
				logger.error("findUserByAccount error", e);
			}

			return "getUserByaccount";

		}
	 /**
	  * 
	 
	  * @Description:查询用户反馈
	 
	  * @return
	 
	  * String
	 
	  * @exception:
	 
	  * @author: lxd
	 
	  * @time:2015-1-12 下午2:31:58
	  */
	 public String queryFeedBack(){
		 try{
		 List<LpFeedbackView> list=userManService.queryFeedBackBypage(request);
		 request.setAttribute("FeedbackView",list );
		 }catch(Exception e){			 
			 logger.error(e); 
		 }
		
		 return "feedBacklist";
	 }
	 
	    /** 
	     * 裁剪头像 
	     * @throws IOException 
	     */  
	    public String cutImage() throws IOException{  
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
	        if("Windows".contains((System.getProperty("os.name")))){ ;
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
	    	String viePath=savePath.replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpUserBean.LP_USER_IMGURL);
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
	        return SUCCESS;  
	    }
	    

	    
}

