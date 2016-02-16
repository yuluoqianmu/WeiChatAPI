package com.laipai.laiPaiClubInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.pojo.VLpClub;
import com.laipai.laiPaiClubInfo.service.ILaiPaiClubService;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 来拍社管理
 * */
@Controller("laiPaiClubInfoAction")
public class LaiPaiClubInfoAction extends BaseAction implements ModelDriven<LpClubBean>{
	 
	@Autowired
	private ILaiPaiClubService laiPaiClubService;
	
	private LpClubBean lpClubBean=new LpClubBean();
	public LpClubBean getModel() {
		return lpClubBean;
	}
	
	private  String savePath;//图片保存的位置
	private File file;
	private  String fileContentType;
	private  String fileFileName;
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	
	public String  queryAllArticle() throws Exception{
		try {
			 List list = laiPaiClubService.queryAllArticle(request);
			 /*不需要文章内容图给前端*/
			 for(Object obj : list){
				 
				 VLpClub clubArt = (VLpClub)obj;
				 clubArt.setCoverUrl(ImgUtil.getImgUrl(clubArt.getCoverUrl()));
				 clubArt.setContent(null);
			 }
			 request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	 }
	
	public String toEdit(){
		String laipaiId = request.getParameter("laipaiId");
		VLpClub club = laiPaiClubService.getArticleViewDetail(laipaiId);
 		request.setAttribute("club", club);
		return SUCCESS;
	}
	
	/**
	 * 保存文章
	 * 对于上传文章封面，为防止出现重名，对上传的图片，都重新赋予一个文件名,
	 * 格式：lpClub_artiCover+当前毫秒数   ，如 lpClub_artiCover201412271231231.jpeg
	 * fileFlag : 标记位，用于标记新建、修改时，是否有上传文章封面图片     fileNotNull有图片文件，null没有
	 * */
	public String saveArticle(){
		try {
			String fileFlag = request.getParameter("fileFlag");
			 if("fileNotNull".equals(fileFlag)){
				 //修改上传的文件名，并返回路径
//				 File in = updateFileNameAndPath();
				 //上传图片到设置的固定目录
				 String imgUrl = ImgUtil.saveImg(file, fileFileName, LaipaiConstants.UPLOAD_CLUB_PATH);
				 //图片上传成功，保存文章其他属性
//				 String coverUrl = imgUrl.replace("\\", "/");
//				 coverUrl.replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL);
//				 imgUrl = ImgUtil.getImgUrl(imgUrl);
				 lpClubBean.setCoverUrl(imgUrl);
			 }else{
				 lpClubBean.setCoverUrl(null);
			 }
			 laiPaiClubService.saveArticle(lpClubBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 处理文件名，和文件路径
	 *   LaipaiConstants.UPLOAD_ABSOLUTE_IMG  : 全局物理路径
	 *   LpClubBean.LP_CLUB_IMGURL            : 来拍社业务处理路径
	 * */
//	private File updateFileNameAndPath(){
//		 //上传文件扩展名
//		 String type=fileFileName.substring(fileFileName.lastIndexOf("."));
//		 //新文件名
//		 String newImgName="lpClub_artiCover"+System.currentTimeMillis() + type;
//		 //获取图片上传路径，若磁盘不存在此路径，创建//UPLOAD_ABSOLUTE_IMG
//		 File filePath = new File(LaipaiConstants.UPLOAD_CLUB_PATH);
//		 if(!filePath.exists()){
//			filePath.mkdirs();
//			System.out.println("---------文章标题图片上传文件夹创建成功");
//		 }
//		 //路径+新文件名=文件保存的完整路径
//		 filePath = new File(LaipaiConstants.UPLOAD_CLUB_PATH+"/"+newImgName);
//		 return filePath;
//	}
	
	
	public String editArticle(){
		
		return SUCCESS;
	}
	
	/**
	 * 修改文章状态
	 * 1 上线   0下线
	 * */
	public String updateArticleOnline(){
		String laipaiId = request.getParameter("laipaiId");
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		laiPaiClubService.updateArticleOnline(laipaiId,status);
		if("list".equals(flag)){  //列表页面修改状态
			return "list";
		}else{ //详情页修改状态
			LpClub club = laiPaiClubService.getArticleDetail(laipaiId);
			//查询评论列表
			List<LpComment> commentList = laiPaiClubService.queryCommentList(request,laipaiId);
	 		request.setAttribute("club", club);
	 		request.setAttribute("commentList", commentList);
			return "detail";
		}
		
	}
	
	/**
	 * 上传图片到临时目录，并返回图片路径
	 * */
	public String uploadImage(){
		  try {
			  if(lpClubBean.getUpload()!=null){
//				  //修改上传的文件名，并返回路径
//				  String imgType=lpClubBean.getUploadFileName().substring(lpClubBean.getUploadFileName().lastIndexOf("."));
//				  //新文件名
//				  String newImgName="lpClub_content"+System.currentTimeMillis() + imgType;
//				 //获取图片上传路径，若磁盘不存在此路径，创建
//				  File filePath = new File(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL);
//				  if(!filePath.exists()){
//					 filePath.mkdirs();
//					 System.out.println("---------文章内容图片上传文件夹"+filePath.getPath()+"创建成功");
//				  }
				  //路径+新文件名=文件保存的完整路径
//				  String path=LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL+"/"+newImgName;
//				  filePath = new File(path);
//				  boolean result=laiPaiClubService.uploadImage(lpClubBean.getUpload(), filePath);
				  String filePath = ImgUtil.saveImg(lpClubBean.getUpload(), lpClubBean.getUploadFileName(), LaipaiConstants.UPLOAD_CLUB_PATH);
//				  String filePath2=LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL+"/"+newImgName;
				  if(filePath != null){
					  request.setAttribute("imgPath",filePath);
				  }
			  }
			  return SUCCESS;
		  } catch (Exception e) {
	           e.printStackTrace();
	           return ERROR;
		  }
	  }
	
	/**
	 * 删除文章
	 * */
	public String deleteArticle(){
		String laiPaiId = request.getParameter("laiPaiId");
		laiPaiClubService.deleteArticle(laiPaiId);
		return SUCCESS;
	}
	
	/**
	 * 查看详情
	 * */
	public String getArticleDetail(){
		String laiPaiId = request.getParameter("laipaiId");
		//查询评论列表
		List<LpComment> commentList = laiPaiClubService.queryCommentList(request,laiPaiId);
		request.setAttribute("commentList", commentList);
		VLpClub club = laiPaiClubService.getArticleViewDetail(laiPaiId);
 		request.setAttribute("club", club);
		return SUCCESS;
	}
	
	/**
	 * 添加评论
	 * */
	public String addLpComment(){
		String commenUserName = request.getParameter("commenUserName");
		LpUser user = laiPaiClubService.getUserByName(commenUserName);
		String laiPaiId = request.getParameter("laipaiId");
		String commentDetail = request.getParameter("commentDetail");
		//添加详情信息
		laiPaiClubService.addLpComment(laiPaiId,commentDetail,user);
		//查询评论列表
		List<LpComment> commentList = laiPaiClubService.queryCommentList(request,laiPaiId);
		request.setAttribute("commentList", commentList);
		//跳转到详情页需要查询的内容
		VLpClub club = laiPaiClubService.getArticleViewDetail(laiPaiId);
 		request.setAttribute("club", club);
		return SUCCESS;
	}
	
	/**
	 * 删除评论
	 * */
	public String deleteLpComment(){
		LpUser user = getCurrentUser();
		String laiPaiId = request.getParameter("laipaiId");
		String commentId = request.getParameter("commentId");
		//查询详情信息
		laiPaiClubService.deleteLpComment(commentId,laiPaiId);
		//查询评论列表
		List<LpComment> commentList = laiPaiClubService.queryCommentList(request,laiPaiId);
		request.setAttribute("commentList", commentList);
		//跳转到详情页需要查询的内容
		VLpClub club = laiPaiClubService.getArticleViewDetail(laiPaiId);
		request.setAttribute("club", club);
		return SUCCESS;
	}
	
	
}
