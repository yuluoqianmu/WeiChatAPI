package com.laipai.operationManage.action;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.VLpNewintroduceBean;
import com.laipai.galaryManInfo.pojo.VLpNewintroduce;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.pojo.VLpClub;
import com.laipai.laiPaiClubInfo.service.ILaiPaiClubService;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.userManInfo.pojo.LpComment;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 首页推广
 * */
@Controller("homePageIntoduceAction")
public class HomePageIntoduceAction extends BaseAction implements ModelDriven<VLpNewintroduceBean>{
	
	@Autowired
	private IOperationManagerService operationManagerService;
	@Autowired
	private ILaiPaiClubService laiPaiClubService;
	
	private VLpNewintroduceBean introduceBean=new VLpNewintroduceBean();
	public VLpNewintroduceBean getModel() {
		return introduceBean;
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

	
	public String queryHomePageIntroduce() throws Exception{
		try {
			List<VLpNewintroduce> list = operationManagerService.queryHomePageIntroduce(request);
			 if(list!=null&&list.size()>0){
				 
				 for(VLpNewintroduce club:list){
					 
					 club.setImgUrl(ImgUtil.getImgUrl(club.getImgUrl())); 
					 
				 }
				 
			 }
			
			
			boolean hasOtherOnline = operationManagerService.hasOtherOnline();
			
			request.setAttribute("hasOtherOnline", hasOtherOnline);
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 修改推广文章状态
	 * 1 上线   0下线
	 * */
	public String updateHomePageArticleOnline(){
		String introduceId = request.getParameter("introduceId");
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		operationManagerService.updateArticleOnline(introduceId,status);
		return SUCCESS;
	}
	
	/**
	 * 删除文章
	 * */
	public String deleteHomePageArticle(){
		String introduceId = request.getParameter("introduceId");
		operationManagerService.deleteArticle(introduceId);
		return SUCCESS;
	}
	
	/**
	 * 查看详情
	 * @throws Exception 
	 * */
	public String getHomePageArticleDetail() throws Exception{
		try {
			String introduceId = request.getParameter("introduceId");
			VLpNewintroduce introduce = operationManagerService.getArticleViewDetail(introduceId);
	 		request.setAttribute("introduce", introduce);
	 		boolean hasOtherOnline = operationManagerService.hasOtherOnline();
			request.setAttribute("hasOtherOnline", hasOtherOnline);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String toHomePageEdit(){
		String introduceId = request.getParameter("introduceId");
		VLpNewintroduce introduce = operationManagerService.getArticleViewDetail(introduceId);
 		request.setAttribute("introduce", introduce);
		return SUCCESS;
	}
	
	public String saveHomePageArticle(){
		try {
			String fileFlag = request.getParameter("fileFlag");
			 if("fileNotNull".equals(fileFlag)){
				 //修改上传的文件名，并返回路径
//				 File filePath = updateFileNameAndPath();
				 //上传图片到设置的固定目录
//				 boolean uploadResult = laiPaiClubService.uploadImage(file, filePath);
				 String imgUrl = ImgUtil.saveImg(file, fileFileName, LaipaiConstants.UPLOAD_CLUB_PATH);
				 //图片上传成功，保存文章其他属性
//				 String coverUrl = filePath.getPath().replace("\\", "/");
				 introduceBean.setImgUrl(imgUrl);
			 }else{
				 introduceBean.setImgUrl(null);
			 }
			 operationManagerService.saveArticle(introduceBean);
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
//		 String newImgName="homePage_introduceImg"+System.currentTimeMillis() + type;
//		 //获取图片上传路径，若磁盘不存在此路径，创建
//		 File filePath = new File(LaipaiConstants.UPLOAD_CLUB_PATH);
//		 if(!filePath.exists()){
//			filePath.mkdirs();
//			System.out.println("---------文章标题图片上传文件夹创建成功");
//		 }
//		 //路径+新文件名=文件保存的完整路径
//		 filePath = new File(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL+"/"+newImgName);
//		 return filePath;
//	}
	
}
