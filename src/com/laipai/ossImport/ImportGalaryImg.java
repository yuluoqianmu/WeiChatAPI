package com.laipai.ossImport;

import java.io.File;
import java.util.List;

import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.laipai.ossImport.ImportUserImg.LpUser;
import com.lyz.db.IBaseDao;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.util.LaiPaiEntityIdGenerator;

public class ImportGalaryImg {
	
	private static final IBaseDao<Galary> galaryDao = new BaseDaoImpl<Galary>();
	
	public static void importGalaryImg(){
		
		String sql = "select galary_id,galary_cover, cover_source from lp_galary";
		List<Galary> galaryList = galaryDao.queryObjects(sql, Galary.class);
		if(galaryList==null || galaryList.isEmpty()){
			return;
		}
		
		/*获取头像路径*/
		for(Galary galary : galaryList){
			
			/*获取头像的绝对路径*/
			String absCoverPath = getAbsImgPath(galary.getGalaryCover(),false);
			if(absCoverPath != null){
				
				File file = new File(absCoverPath);
				System.out.println(absCoverPath);
				if(file.exists()){
					
					/*替换封面*/
					String coverKey = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
					/*上传头像*/
					BucketTest.putImg(coverKey, file);
					/*更新数据库*/
					String upSql = "update lp_galary set galary_cover='"+coverKey+"' where galary_id='"+galary.getGalaryId()+"'";
					galaryDao.upsertObject(upSql);
				}
				
			}
			
			/*获取身份证的绝对路径*/
			String absCoverSrcPath = getAbsImgPath(galary.getCoverSource(),true);
			if(absCoverSrcPath != null){
				
				File file = new File(absCoverSrcPath);
				System.out.println(absCoverSrcPath);
				if(file.exists()){
					
					/*替换头像*/
					String coverSrcKey = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
					/*上传头像*/
					BucketTest.putImg(coverSrcKey, file);
					/*更新数据库*/
					String upSql = "update lp_galary set cover_source='"+coverSrcKey+"' where galary_id='"+galary.getGalaryId()+"'";
					galaryDao.upsertObject(upSql);
				}
			}
			
			
		}
	}

	/**
	 * 获取图片的绝对路径
	 * @param path
	 * @return
	 */
	public static String getAbsImgPath(String path, boolean isSrc){
		
		if(path == null){
			
			return null;
		}
		/*头像中包含http的不是注册用户*/
		if(path.startsWith("http") && !path.contains("upload")){
			return null;
		}
		String fileName = ImgUtil.getFileName(path);
		
		if(isSrc){
			return LaipaiConstants.UPLOAD_GALARY_SOURCE_PATH+"/"+fileName;
		}
		return LaipaiConstants.UPLOAD_GALARY_PATH+"/"+fileName;
//		return "d://"+fileName;
	}
	
	public static void main(String args[]){
		
		ImportGalaryImg.importGalaryImg();
	}
	
	public static class Galary{
		
		private String galaryId;
		
		private String galaryCover;
		
		private String coverSource;

		public String getGalaryId() {
			return galaryId;
		}

		public void setGalaryId(String galaryId) {
			this.galaryId = galaryId;
		}

		public String getGalaryCover() {
			return galaryCover;
		}

		public void setGalaryCover(String galaryCover) {
			this.galaryCover = galaryCover;
		}

		public String getCoverSource() {
			return coverSource;
		}

		public void setCoverSource(String coverSource) {
			this.coverSource = coverSource;
		}
		
		
	}
}
