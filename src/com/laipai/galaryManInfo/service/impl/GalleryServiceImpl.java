package com.laipai.galaryManInfo.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.FileUploadUtils;
import com.laipai.base.util.GallerySort;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.tags.PageController;
import com.laipai.galaryManInfo.dao.IGalleryDao;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.pojo.LpGalaryExtend;
import com.laipai.galaryManInfo.pojo.VLpGalaryBackinfo;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.operationManage.dao.IOperationManageDao;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.orderInfo.dao.OrderDao;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.serviceInfo.dao.ServiceDao;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.dao.ICommentDao;
import com.laipai.userManInfo.dao.IUserManInfoDao;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;
@Transactional
@Service(IGalleryService.SERVICE_NAME)
public class GalleryServiceImpl extends BaseServiceImpl implements IGalleryService {
    @Resource(name=IGalleryDao.DAO_NAME)
	private IGalleryDao galleryDao;
    @Resource(name=IOperationManageDao.DAO_NAME)
    private IOperationManageDao perationManageDao;
    @Resource(name="serviceDao")
    private ServiceDao serviceDao;
    @Resource(name=ICommentDao.COMENTDAO_NAME)
    private ICommentDao commentDao;
    @Resource(name=IUserManInfoDao.IUSERMANINFODAO_NAME)
    private IUserManInfoDao  userManInfoDao;
    @Autowired
	private IBaseDao baseDao;
    @Autowired
    private OrderDao orderDao;
     /**
      * 查询所有作品集
      */
	@Override
	public List queryall() {
		String sqlString="SELECT * FROM lp_galary ";
		@SuppressWarnings("rawtypes")
		List list=  galleryDao.getListBySql(sqlString,null);
		return list;
	}
	/**
	 * 查询所有的主题
	 */
	@Override
	public List queryallStyle() {
		List list= perationManageDao.queryAllStyle();
		return list;
	}
	/***
	 * 添加作品集
	 */
	@Override
	public void saveGallery(LpGalary galaryBean) {
		try {
			LpGalary galary = new LpGalary();
			BeanUtils.copyProperties(galaryBean, galary);
			String galleryID = galary.getGalaryId();
			if (StringUtils.isNotBlank(galleryID)) {
//				baseDao.updateObject(galary);
				String sql = "update lp_galary set galary_cover='"
						+galaryBean.getGalaryCover()+"',subject_name='"
						+galaryBean.getSubjectName()+"',galary_desc='"+galaryBean.getGalaryDesc()
						+"' where galary_id='"+galleryID+"'";
				System.out.println("后台修改作品集sql："+sql);
				baseDao.executeSql(sql);
			} else {
				// 2：向作品集表中插入数据
				galleryDao.save(galaryBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	
	 * @Description:保存照片
	
	 * @param galaryBean
	
	 * void
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-19 上午9:45:54
	 */
	private void saveGalleryPhotos(LpGalary galaryBean,String[] imageNameArray, String converName) {
		//上传时间（当前时间）
		Timestamp progressTime = new Timestamp(new java.util.Date().getTime());
				if(imageNameArray!=null && imageNameArray.length>0){
					for(int i=0;i<imageNameArray.length;i++){
						//组织用户附件的PO对象对象
						LpGalaryDetail photo = new LpGalaryDetail();
						//文件名
						photo.setPhotoName(imageNameArray[i]);
						//完成文件上传的同时，返回路径path（用作下载）
						String imagePath=  imageNameArray[i];
						//String fileURL = FileUploadUtils.fileUpload(imsgsFile[i],imsgsFileNames[i],"GalerryManage");
//						String sourcePath=  imageNameArray[i];
						
						if(imageNameArray[i].equals(converName)){		 
							photo.setGalaryConver(true); 
						 }else{
							 
							 photo.setGalaryConver(false); 
						 }
						//文件路径
						photo.setPhotoSrc(imagePath);
//						photo.setPhotoSource(sourcePath);
						//上传时间
						photo.setCreatTime(progressTime);
						//附件对象关联用户对象（多的一端关联一的一端），如果不关联对象多的一端的外键列为null
						photo.setLpGalary(galaryBean);
						photo.setStatus(0);
						//保存
						baseDao.save(photo);
					}
				}
		
	}
     /**
      * 根据Id查询作品集
      */
	@Override
	public LpGalary getgalleryByID(String galleryId) {	
		return galleryDao.getgalleryByID(galleryId);
	}
	/**
	 * 根据作品集Id查询照片
	 */
	@Override
	public List<LpGalaryDetail> getphotosByGalleryID(String galleryId) {
		List<LpGalaryDetail> list= galleryDao.getphotosByGalleryID(galleryId);
		List newList = null;
		if(list!=null&&list.size()>0){
			newList=new ArrayList();
			for(int i=0;i<list.size();i++){
				LpGalaryDetail detail = (LpGalaryDetail) list.get(i);
				detail.setPhotoSrc(ImgUtil.getImgUrl(detail.getPhotoSrc()));
				newList.add(detail);
			}
		}
		return newList;
	}
	
	/**
	 * 根据作品集Id查询照片
	 */
	@Override
	public List<LpGalaryDetail> getphotosUpdateByGalleryID(String galleryId) {
		List<LpGalaryDetail> list= galleryDao.getphotosUpdateByGalleryID(galleryId);
		List newList = null;
		if(list!=null&&list.size()>0){
			newList=new ArrayList();
			for(int i=0;i<list.size();i++){
				LpGalaryDetail detail = (LpGalaryDetail) list.get(i);
				detail.setPhotoSrc(ImgUtil.getImgUrl(detail.getPhotoSrc()));
				newList.add(detail);
			}
		}
		return newList;
	}
	/**
	 * 给作品
	 * 
	 * 
	 * 添加评论
	 */
	@Override
	public void saveComment(LpComment comment) {
		commentDao.saveComment(comment);
		
	}
	/**
	 * 根据作品集Id查询的评论
	 */
	@Override
	public List<LpComment> queryCommentByGalleryID(String galleryId) {
		String sql ="SELECT * FROM lp_comment WHERE galary_id=?";
		List<LpComment> list=new ArrayList<LpComment>();
		list= commentDao.getCommentBySql(sql, galleryId);
		return list;
	}
	/**
	 * 根据照片Id删除照片
	 */
	@Override
	public void deletephotoById(String photoid) {
		LpGalaryDetail photo=(LpGalaryDetail) galleryDao.getObjectById(LpGalaryDetail.class, photoid);
//		 String savePath=photo.getPhotoSrc();
//
//		 File file = new File("D:\\"+savePath);
//
//		 //虚拟目录替换为目录
//		 savePath.replace(LaipaiConstants.UPLOAD_VIRTUAL_IMG+SimpleImage.LP_GALLERY_IMGURL, LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL);
//		 //从磁盘删除此文件
//		 
//
//		 if(file.exists()){
//		     file.delete();
//
//		    }		
//		    galleryDao.delete(photo);
		boolean iscover=  photo.getGalaryConver();
		if(iscover==true){
			LpGalary gallary= photo.getLpGalary();
			gallary.setGalaryCover("");
			galleryDao.update(gallary);
			
			
		}
		  photo.setStatus(1);
		galleryDao.updateObject(photo);
         if(photo.getPhotoSrc()!=null){
        	 
        	 this.deleteimg(photo.getPhotoSrc());
         };
	}		
		//从数据库删除本条数据    
		


	@Override
	public void deleteGalleryById(String galleryId) {
		//查看该作品集是否有订单
		List<LpOrder> orderList=orderDao.queryOrderById(galleryId);
		if(orderList!=null&&orderList.size()>0){
			//更新订单
			for(LpOrder order:orderList){
				order.setLpGalary(null);
				orderDao.updateOrder(order);
			}
		}
//		galleryDao.deletegalleryById(galleryId);
		LpGalary gallary= (LpGalary) baseDao.getObjectById(LpGalary.class, galleryId);
		baseDao.delete(gallary);
		if(gallary.getGalaryCover()!=null){
			//删除服务器上的照片
			deleteimg(gallary.getGalaryCover());
			
		}
		
	}
	@Override
	public void deleteCommentById(String commentID) {
		commentDao.deleteById(commentID);
		
	}
	/**
	 * add by LXD on$2014-12-22
	 * 分页查询文章列表
	 * */
	public List getAllBypage(HttpServletRequest request) {
		String hql = "from VLpGalaryBackinfo order by galaryScores DESC";
		List<VLpGalaryBackinfo> controllist=null;
		int page=1;
		try {
			List<VLpGalaryBackinfo> list = querylistForPage(request, hql, 20);
			String Stringpage=request.getParameter("page"); 
			if (Stringpage != null) {
				page = Integer.parseInt(Stringpage);
			} 
			//该页的起始位置
			int begin =(page-1)*20+1;
			//该页的结束位置
			int end=page*20;
			String controlHql="from VLpGalaryBackinfo where controlIndex=1 and galaryIndex between ? and ? order by galaryIndex";
			controllist=galleryDao.getVLpGalaryBackinfo(controlHql,begin,end);
			//去除list中手工排序的作品集
			if(list!=null&&list.size()>0){								
				 Iterator<VLpGalaryBackinfo> sourceIt=list.iterator();
			     while(sourceIt.hasNext()){
			    	 VLpGalaryBackinfo tmpSharedBoardSmsWrapper=sourceIt.next();
			    	 if(tmpSharedBoardSmsWrapper.getControlIndex()==1){
			    		 sourceIt.remove(); 
			    	 }
			    }
			}			
			 //把[begin,end]的手工数据插入到list中
			if(controllist!=null&&controllist.size()>0){								
				for(VLpGalaryBackinfo gallery:controllist){
					int index=gallery.getGalaryIndex();
					if(index <= 0){
						index = 1;
					}else if(index >= list.size()){
						list.add(gallery);
					}else{
						list.add(index-1, gallery);
					}
					
				}				
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void saveGallery(LpGalary gallery,
			String serviceId, String userAccount,String[] imageNameArray,String converName) {
		//1：向details表中插入数据
	     this.saveGalleryPhotos(gallery,imageNameArray,converName); 	   
	     //3：和服务关联
	     if(serviceId!=null&&!"private".equals(serviceId)){
	     LpService lpService= serviceDao.queryService(serviceId);
	     gallery.setLpService(lpService);
	     }
/*	    //4：和风格关联
	     if(styleId!=null&&styleId.length>0){
	    	 Set<LpStyle> styles=new HashSet<LpStyle>();
	    	 for(int i=0;i<styleId.length;i++){
	    		  String id=styleId[i];
	    		 LpStyle style =(LpStyle) perationManageDao.getObjectById(LpStyle.class, id);
	    		 styles.add(style);
	    	 }
	    	 gallery.setLpStyle(styles); 
	     }*/
	    //完成文件上传的同时，返回路径path（用作下载）
//		String imagePath=  this.getRelativePath(converName);
//		System.out.println("--------------后台上传作品集封面imagePath："+imagePath);
		gallery.setGalaryCover(converName);
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

		gallery.setStatus(0);

		baseDao.save(gallery);
	}
	public String getRelativePath(String fileName){
		//String datePath = DateUtils.dateToStringPath(new Date());
		String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG
				+ SimpleImage.LP_GALLERY_IMGURL+"/"+fileName;		
		return savePath;
	}
//	public String getSourcePath(String fileName){
//		//String datePath = DateUtils.dateToStringPath(new Date());
//		String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ SimpleImage.LP_GALLERY_SOURCE+"/"+fileName;		
//		return savePath;
//	}	
	
	
	@Override
	public List<LpService> getServiceByAcount(String account) {
		List<LpService> list =new ArrayList<LpService>();
		String sql="SELECT b.* FROM lp_user a ,lp_service b WHERE a.user_id=b.user_id AND a.user_name='"+account+"'";
		list= serviceDao.getListBySql(sql);
		return list;
	}
	
	public void updateGalleryDetail(String galaryId, String[] imageNameArray){
		LpGalary gallery = (LpGalary) baseDao.getObjectById(LpGalary.class, galaryId);
		//1：向details表中插入数据
	     this.saveGalleryPhotos(gallery,imageNameArray,null); 
	}
	
	/**
	 * 根据作品Id操作作品
	 */
	@Override
	public void updateGalleryStatus(String galaryId, int status) {
		LpGalary gallery = (LpGalary) baseDao.getObjectById(LpGalary.class, galaryId);

			gallery.setGalaryStatus(status);


		baseDao.updateObject(gallery);
		
	}
	/**
	 * 根据作品Id查询作品级的扩展表
	 */
	@Override
	public LpGalaryExtend getExtendbyGalleryId(String galleryId) {
		LpGalaryExtend extend =new LpGalaryExtend();
		String sql="select * from lp_galary_extend l where l.galary_id=?";
		 List<LpGalaryExtend> list=galleryDao.getExtend(sql,galleryId);
      if(list!=null&&list.size()>0){
    	  extend =list.get(0);
    	  
      }
      return extend;
	}

	/**
	 * 修改评论数,赞数
	 * */
	public void updateGalaryExt(String galleryId, int vieNum, int likeNum) {
		List<LpGalaryExtend> list = baseDao.queryListObjectAll("from LpGalaryExtend where lpGalary.galaryId='"+galleryId+"'");
		LpGalaryExtend extend = null;
		if(list !=null && !list.isEmpty()){
			extend = list.get(0);
		}
		if(extend!=null){
	        if(vieNum!=0){
	        	extend.setViewNumber(vieNum);
	        }
	        if(likeNum!=0){
	        	extend.setLikeNumber(likeNum);
	        }
			baseDao.updateObject(extend);
		}else{
			LpGalaryExtend newextend =new LpGalaryExtend();
			if(vieNum!=0){
				newextend.setViewNumber(vieNum);
	        }
			if(likeNum!=0){
	        	newextend.setLikeNumber(likeNum);
	        }
	        LpGalary gallery=this.getgalleryByID(galleryId);
	        gallery.setControlSource(1);
	        newextend.setLpGalary(gallery);
	        baseDao.save(newextend);
		}
	}
	@Override
	public LpService getServiceById(String serviceId) {
		LpService service= (LpService) baseDao.getObjectById(LpService.class,serviceId);
		return service;
		
	}
	@Override
	public List<LpGalary> getGallery(String string, String userId, int i) {
		List<LpGalary> list=new ArrayList<LpGalary>();
		list=galleryDao.getGallery(string, userId,i);
		return list;
	}
	
	/**
	 * 根据作品集其中一张照片id查询此照片对象
	 * */
	public LpGalaryDetail getGalaryDetailById(String photoid){
		try {
			LpGalaryDetail photo=(LpGalaryDetail) galleryDao.getObjectById(LpGalaryDetail.class, photoid);
			return photo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void minusOneCommentByGalary(String galaryId){
		baseDao.executeSql("update lp_galary set comment_number=comment_number-1 where galary_id='"+galaryId+"'");
	}
	/**
	 * 手工控制作品集排序
	 */
	@Override
	public void updateGalleryIndex(String galleryId, int index) {
       

		LpGalary gallery= (LpGalary) galleryDao.getObjectById(LpGalary.class, galleryId);
		int controlIndex= gallery.getControlIndex();
		//如果之前没有手工排序
		if(controlIndex==0){
			String hql=" FROM LpGalary L where L.status=0 and L.galaryIndex="+index+" and L.controlIndex=1"; 
			List<LpGalary> checklist= galleryDao.queryListObjectAll(hql);
			//如果该位置没有被其他作品集占位
			if(checklist==null||checklist.size()==0){
				//加手工排序的标记
			gallery.setControlIndex(1);
			gallery.setGalaryIndex(index);
			 
			galleryDao.update(gallery);
			}else{
			//如果被占位,后续位置累加1
			String followhql="from LpGalary where status=0 and controlIndex=1 and galaryIndex >=? order by galaryIndex";	
		    List<LpGalary>	list=galleryDao.getGallery(followhql,index);	
				if(list!=null&&list.size()>0){
					for(LpGalary galleryFollow :list){
						galleryFollow.setGalaryIndex(galleryFollow.getGalaryIndex()+1);
						
						galleryDao.updateObject(galleryFollow);
					}
					
					
				}
				gallery.setControlIndex(1);
				gallery.setGalaryIndex(index);
				galleryDao.update(gallery);
			}
		}else{
		//如果没有占位,
			String hql=" FROM LpGalary L where status=0 and L.galaryIndex="+index+" and L.controlIndex=1"; 
			List<LpGalary> checklist= galleryDao.queryListObjectAll(hql);
			if(checklist==null||checklist.size()==0){
				
				gallery.setGalaryIndex(index);
				galleryDao.update(gallery);
				
			}
			else{
		//之前排过序,index后面的位置号累加1,再更新要改变位置的作品集的位置号
			int galleryIndex=gallery.getGalaryIndex();
			 if(galleryIndex!=index){
				 String followhql="from LpGalary where status=0 and controlIndex=1 and galaryIndex >=? and galaryId<>? order by galaryIndex";	
				    List<LpGalary>	list=galleryDao.getGallery(followhql,index,galleryId);	
						if(list!=null&&list.size()>0){
							for(LpGalary galleryFollow :list){
								galleryFollow.setGalaryIndex(galleryFollow.getGalaryIndex()+1);				
								galleryDao.updateObject(galleryFollow);
							}
							
							
						} 
						gallery.setGalaryIndex(index);
						galleryDao.update(gallery);
				 
			 }
			
		}
			
			
			
			
		}
		
		//加手工排序的标记
      
		

	    	//oldGallery.setGalaryIndex(galaryIndex)
	    	
	    
	}
	/**
	 * 智能排序（去掉作品集的手工标记）
	 */
	@Override
	public void updateControlIndex() {
		String hql="from LpGalary L where L.controlIndex=1 and status=0";
		List<LpGalary> list= galleryDao.queryListObjectAll(hql);
		if(list!=null&&list.size()>0){
		 for(LpGalary gallery:list){	 
			 gallery.setControlIndex(0);
			 gallery.setGalaryIndex(null);
			 galleryDao.update(gallery);
		 }
		}
	}
	@Override
	public int GalleryCount() {
		
		return galleryDao.getGalleryCount();
	}
	public int getGalleryCount(String hql) {
		
		return galleryDao.getGalleryCount(hql);
	}
	/**
	 * 
	
	 * @Description:作品集的分页查询
	
	 * @param request
	 * @param hql
	 * @param pageSize
	 * @return
	 * @throws Exception
	
	 * List
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2015-1-25 下午2:20:09
	 */
	public List queryGalaryForPage(HttpServletRequest request, String hql,
			int pageSize) throws Exception {
			int count=this.GalleryCount();
			int page=PageController.initPage(request);
			if(pageSize<1||"".equals(pageSize)) {
				//每页显示 默认10条
				pageSize=10;
			}
			PageController pc=new PageController(request, "pageController", count, pageSize, page);
			request.setAttribute("pageController", pc);
			List list=this.baseDao.queryListObjectAllForPage(hql, pageSize, page);
			return list;
	}
	
	private void screenBlackNameList(List<LpGalary> source, List<LpGalary> blackNameList){

	    Iterator<LpGalary> sourceIt=source.iterator();


	    while(sourceIt.hasNext()){
	    	

	    	LpGalary tmpSharedBoardSmsWrapper=sourceIt.next();

	        Iterator<LpGalary> blackNameListIt=blackNameList.iterator();

	        while(blackNameListIt.hasNext()){

	        	LpGalary tmpBlackNameListModel=blackNameListIt.next();

	           if(tmpSharedBoardSmsWrapper.getGalaryId().equals(tmpBlackNameListModel.getGalaryId())){

	               sourceIt.remove();

	               break;

	            }

	           

	        }

	        
          
	    }

	    


	    } 
	public void deleteimg(String imgPath){
/*		String winImg=imgPath.replace("/", "\\");*/
		File file=new File(imgPath);
		if(file.exists()){
			file.delete();
			
		}
		
	}

	    
	@Override
	public <T> List getGalleryTop(String hql,int top,T t) {
		return this.baseDao.queryListObjectByTopNum(hql, top);
	}
	@Override
	public List<LpGalaryExtend> getExebygalleryId(String galaryId) {
		List<LpGalaryExtend> listExe = baseDao.queryListObjectAll("from LpGalaryExtend where lpGalary.galaryId='"+galaryId+"'");
		return listExe;
	}
	@Override
	public void updateExt(LpGalaryExtend extend) {
		
		baseDao.updateObject(extend);
	}
	@Override
	public void updateDetail(LpGalaryDetail detail) {
		baseDao.updateObject(detail);
		
	}  

	
}
