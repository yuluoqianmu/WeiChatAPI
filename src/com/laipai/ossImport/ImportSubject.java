package com.laipai.ossImport;

import java.io.File;
import java.util.List;

import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.laipai.ossImport.ImportGalaryDetail.GalaryDetail;
import com.lyz.db.IBaseDao;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * 导入专题图片
 * @author luzi
 *
 */
public class ImportSubject {
	
private static final IBaseDao<Subject> subjectDao = new BaseDaoImpl<Subject>();
	
	
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
		
		return LaipaiConstants.UPLOAD_SUBJECT_PATH+"/"+fileName;
//		return "d://"+fileName;
	}
	
	public static void importSubject(){
		
		
			
			String sql = "select subject_id,subject_img from lp_subject";
			List<Subject> subjectList = subjectDao.queryObjects(sql, Subject.class);
			if(subjectList==null || subjectList.isEmpty()){
				return;
			}
			
			/*获取头像路径*/
			for(Subject subject : subjectList){
				
				/*获取图片的绝对路径*/
				String absPhotoPath = getAbsImgPath(subject.getSubjectImg());
				if(absPhotoPath != null){
					
					File file = new File(absPhotoPath);
					System.out.println(absPhotoPath);
					if(file.exists()){
						
						/*替换封面*/
						String photoKey = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
						/*上传头像*/
						BucketTest.putImg(photoKey, file);
						/*更新数据库*/
						String upSql = "update lp_subject set subject_img='"+photoKey+"' where subject_id='"+subject.getSubjectId()+"'";
						subjectDao.upsertObject(upSql);
					}
					
				}
				
			}
		}
	
	public static void main(String args[]){
		
		ImportSubject.importSubject();
	}
	
	public static class Subject{
		
		private String subjectId;
		
		private String subjectImg;

		public String getSubjectId() {
			return subjectId;
		}

		public void setSubjectId(String subjectId) {
			this.subjectId = subjectId;
		}

		public String getSubjectImg() {
			return subjectImg;
		}

		public void setSubjectImg(String subjectImg) {
			this.subjectImg = subjectImg;
		}
		
		
	}
}
