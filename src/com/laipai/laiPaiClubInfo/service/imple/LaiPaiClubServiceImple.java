package com.laipai.laiPaiClubInfo.service.imple;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.base.dao.IBaseDao;
import com.laipai.base.service.imple.BaseServiceImpl;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.pojo.LpClubExtend;
import com.laipai.laiPaiClubInfo.pojo.VLpClub;
import com.laipai.laiPaiClubInfo.service.ILaiPaiClubService;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpUser;

@Service("laiPaiClubService")
public class LaiPaiClubServiceImple extends BaseServiceImpl implements ILaiPaiClubService {
	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * add by zhanhh on$2014-12-22
	 * 分页查询文章列表
	 * */
	public List queryAllArticle(HttpServletRequest request) throws Exception{
		String hql = "from VLpClub order by laipaiClubIndex";
		try {
			//hql分页查询
			List list = querylistForPage(request, hql, 20);
			List newList = null;
			if(list!=null && list.size()>0){
				newList = new ArrayList();
				for(int i=0;i<list.size();i++){
					VLpClub club = (VLpClub) list.get(i);
					club.setCoverUrl(ImgUtil.getImgUrl(club.getCoverUrl()));
					newList.add(club);
				}
			}
			return newList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 保存文章
	 * */
	public void saveArticle(LpClubBean lpClubBean){
		LpClub club = new LpClub();
		if(StringUtils.isNotEmpty(lpClubBean.getLaipaiId())){
			club = (LpClub) baseDao.getObjectById(LpClub.class, lpClubBean.getLaipaiId());
			//修改了位置
			if(club.getLaipaiClubIndex() != lpClubBean.getLaipaiClubIndex()){
				String sql = "update lp_club set laipai_club_index =laipai_club_index +1 where laipai_club_index <"+club.getLaipaiClubIndex()+" and laipai_club_index>="+ lpClubBean.getLaipaiClubIndex();
				if(lpClubBean.getLaipaiClubIndex() > club.getLaipaiClubIndex()){
					sql = "update lp_club set laipai_club_index =laipai_club_index -1 where laipai_club_index <="+lpClubBean.getLaipaiClubIndex()+" and laipai_club_index>"+club.getLaipaiClubIndex();
				}
				baseDao.executeSql(sql);
				club.setLaipaiClubIndex(lpClubBean.getLaipaiClubIndex());
			}
			//有手工编辑浏览量,赞总量
			//LpClubExtend已经有一条假数据,则修改，否则添加
			List<LpClubExtend> clubExtendList = baseDao.queryListObjectAll("from LpClubExtend where laipaiId='"+lpClubBean.getLaipaiId()+"'");
			LpClubExtend clubExtend = new LpClubExtend();
			if(clubExtendList!=null && !clubExtendList.isEmpty()){
				clubExtend = clubExtendList.get(0);
			}
			
			if(club.getLikeNumber() != lpClubBean.getLikeNumber()
				|| club.getViewNumber() != lpClubBean.getViewNumber()){
				clubExtend.setLaipaiId(lpClubBean.getLaipaiId());
				clubExtend.setLikeNumber(lpClubBean.getLikeNumber());
				clubExtend.setViewNumber(lpClubBean.getViewNumber());
				clubExtend.setModifyTime(DateUtils.dateToTimestamp(new Date()));
				baseDao.saveOrUpdate(clubExtend);
				club.setControlSource(1);
			}
			club.setTile(lpClubBean.getTile());
			if(StringUtils.isNotEmpty(lpClubBean.getCoverUrl())){
				club.setCoverUrl(lpClubBean.getCoverUrl());
			}
			club.setContent(lpClubBean.getContent().replace(LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL));
			baseDao.saveOrUpdate(club);
		}else{//新建文章
			club.setStatus(0); // 状态  0下线  1上线(文章刚新建时，默认下线)
			club.setCreateDate(DateUtils.dateToTimestamp(new Date()));
			//新建文章时,并未输入位置,则位置往下顺序加一
			if(lpClubBean.getLaipaiClubIndex() == null){
				try {
					int i = baseDao.getCount("from LpClub");
					club.setLaipaiClubIndex(i+1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{//新建文章时，即指定位置，则修改其他文章的位置
				String sql = "update lp_club set laipai_club_index =laipai_club_index +1 where laipai_club_index>= "+lpClubBean.getLaipaiClubIndex();
				baseDao.executeSql(sql);
			}
			//保存文章标题,封面
			club.setTile(lpClubBean.getTile());
			if(StringUtils.isNotEmpty(lpClubBean.getCoverUrl())){
				club.setCoverUrl(lpClubBean.getCoverUrl());
			}
			club.setContent(lpClubBean.getContent().replace(LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL));
			if((lpClubBean.getLikeNumber() !=null && lpClubBean.getLikeNumber()>0)
				|| (lpClubBean.getViewNumber() !=null && lpClubBean.getViewNumber()>0)){
				
				club.setControlSource(1);
			}else{
				club.setControlSource(0);
			}
			Serializable laipaiId = baseDao.saveObjectReturnId(club);
			//新建文章时,即指定喜欢量,赞总量
			if((lpClubBean.getLikeNumber() !=null && lpClubBean.getLikeNumber()>0)
				|| (lpClubBean.getViewNumber() !=null && lpClubBean.getViewNumber()>0)){
				LpClubExtend clubExtend = new LpClubExtend();
				clubExtend.setLaipaiId(laipaiId.toString());
				clubExtend.setLikeNumber(lpClubBean.getLikeNumber());
				clubExtend.setViewNumber(lpClubBean.getViewNumber());
				clubExtend.setModifyTime(DateUtils.dateToTimestamp(new Date()));
				baseDao.saveOrUpdate(clubExtend);
			}
		}
	}
	
	/**
	 * 修改文章上线状态
	 * */
	public void updateArticleOnline(String laipaiId,String status){
		LpClub club = (LpClub) baseDao.getObjectById(LpClub.class, laipaiId);
		club.setStatus(Integer.parseInt(status));
		if("1".equals(status)){
			club.setOnlineDate(DateUtils.dateToTimestamp(new Date()));
		}
		baseDao.updateObject(club);
	}
	
	/**
	 * 删除文章
	 * */
	public void deleteArticle(String laiPaiId){
		try {
			LpClub club = null;
			String[] arryId = laiPaiId.split(",");
			for(int i=0;i<arryId.length;i++){
				club = (LpClub) baseDao.getObjectById(LpClub.class, arryId[i]);
				if(club !=null){
					club.setStatus(2);
					baseDao.updateObject(club);
//					baseDao.delete(club);
//					baseDao.executeSql("delete from lp_club_extend where laipai_id='"+arryId[i]+"'");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查看详情
	 * */
	public LpClub getArticleDetail(String laiPaiId){
		LpClub club = new LpClub();
		try {
			if(StringUtils.isNotEmpty(laiPaiId)){
				club = (LpClub) baseDao.getObjectById(LpClub.class, laiPaiId);
//				club.setCoverUrl(club.getCoverUrl().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL));
//				club.setContent(club.getContent().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL));
				club.setCoverUrl(ImgUtil.getImgUrl(club.getCoverUrl()));
				club.setContent(ImgUtil.getImgUrl(club.getContent()));
			}
			return club;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查看详情
	 * */
	public VLpClub getArticleViewDetail(String laiPaiId){
		VLpClub club = new VLpClub();
		try {
			if(StringUtils.isNotEmpty(laiPaiId)){
				club = (VLpClub) baseDao.getObjectById(VLpClub.class,laiPaiId);
//				club.setCoverUrl(club.getCoverUrl().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL));
//				club.setContent(club.getContent().replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL, LaipaiConstants.UPLOAD_VIRTUAL_IMG+LpClubBean.LP_CLUB_IMGURL));
				club.setCoverUrl(ImgUtil.getImgUrl(club.getCoverUrl()));
				club.setContent(ImgUtil.getImgUrl(club.getContent()));
			}
			return club;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<LpComment> queryCommentList(HttpServletRequest request,String laiPaiId){
		String hql = "from LpComment where newsId = '"+laiPaiId+"'";
		try {
			//hql分页查询
			List<LpComment> list = querylistForPage(request, hql, 10);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean uploadImage(File in, File out){
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
	 * 添加评论
	 * */
	public void addLpComment(String laiPaiId, String commentDetail,LpUser user){
		LpComment lpComment = new LpComment();
		lpComment.setLpUser(user);
		lpComment.setCommentDetail(commentDetail);
		lpComment.setNewsId(laiPaiId);
		lpComment.setCreateTime(DateUtils.dateToTimestamp(new Date()));
		baseDao.save(lpComment);
		//修改评论量
		String sql = "update lp_club set comment_number =comment_number +1 where laipai_id ='"+laiPaiId+"'";
		baseDao.executeSql(sql);
	}
	
	public void deleteLpComment(String commentId, String laiPaiId){
		LpComment lpComment = (LpComment) baseDao.getObjectById(LpComment.class, commentId);
		baseDao.delete(lpComment);
		//修改评论量
		String sql = "update lp_club set comment_number = comment_number -1 where laipai_id ='"+laiPaiId+"'";
		baseDao.executeSql(sql);
	}
	
	public LpUser getUserByName(String commenUserName){
		List<LpUser> list = baseDao.queryListObjectAll("from LpUser where userName='"+commenUserName+"'");
		if(list!=null && !list.isEmpty()){
			LpUser user = list.get(0);
			return user;
		}
		return null;
	}
}
