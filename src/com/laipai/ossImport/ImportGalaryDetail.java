package com.laipai.ossImport;

import java.io.File;
import java.util.List;

import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.laipai.ossImport.ImportGalaryImg.Galary;
import com.lyz.db.IBaseDao;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.util.LaiPaiEntityIdGenerator;

public class ImportGalaryDetail {
	
	private static final IBaseDao<GalaryDetail> galaryDetailDao = new BaseDaoImpl<GalaryDetail>();
	
	
	/**
	 * 获取图片的绝对路径
	 * @param path
	 * @return
	 */
	public static String getAbsImgPath(String path){
		
		if(path == null){
			
			return null;
		}
		/*头像中包含http的不是注册用户*/
		if(path.startsWith("http") && !path.contains("upload")){
			return null;
		}
		String fileName = ImgUtil.getFileName(path);
		
		return LaipaiConstants.UPLOAD_GALARY_PATH+"/"+fileName;
//		return "d://"+fileName;
	}
	
	public static void importGalaryDetail(){
		
		
			
			String sql = "select photo_id,photo_src from lp_galary_detail";
			List<GalaryDetail> galaryList = galaryDetailDao.queryObjects(sql, GalaryDetail.class);
			if(galaryList==null || galaryList.isEmpty()){
				return;
			}
			
			/*获取头像路径*/
			for(GalaryDetail galary : galaryList){
				
				/*获取图片的绝对路径*/
				String absPhotoPath = getAbsImgPath(galary.getPhotoSrc());
				if(absPhotoPath != null){
					
					File file = new File(absPhotoPath);
					System.out.println(absPhotoPath);
					if(file.exists()){
						
						/*替换封面*/
						String photoKey = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
						/*上传头像*/
						BucketTest.putImg(photoKey, file);
						/*更新数据库*/
						String upSql = "update lp_galary_detail set photo_src='"+photoKey+"',photo_name='"+photoKey+"' where photo_id='"+galary.getPhotoId()+"'";
						galaryDetailDao.upsertObject(upSql);
					}
					
				}
				
			}
		}
	
	public static void main(String args[]){
		
		ImportGalaryDetail.importGalaryDetail();
	}

public static class GalaryDetail{
		
		private String photoId;
		
		private String photoSrc;

		public String getPhotoId() {
			return photoId;
		}

		public void setPhotoId(String photoId) {
			this.photoId = photoId;
		}

		public String getPhotoSrc() {
			return photoSrc;
		}

		public void setPhotoSrc(String photoSrc) {
			this.photoSrc = photoSrc;
		}
		
		
	}
	
}
