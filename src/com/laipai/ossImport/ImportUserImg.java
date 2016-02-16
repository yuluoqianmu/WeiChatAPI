package com.laipai.ossImport;

import java.io.File;
import java.util.List;

import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.lyz.db.IBaseDao;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.util.LaiPaiEntityIdGenerator;

/**
 * 导入人员图片，包括头像和身份证图片
 * @author luzi
 *
 */
public class ImportUserImg {
	
	private static final IBaseDao<LpUser> userDao = new BaseDaoImpl<LpUser>();
	
	public static void importUser(){
		
		String sql = "select user_id,head_image, idCard_image from lp_user";
		List<LpUser> userList = userDao.queryObjects(sql, LpUser.class);
		if(userList==null || userList.isEmpty()){
			return;
		}
		
		/*获取头像路径*/
		for(LpUser user : userList){
			
			/*获取头像的绝对路径*/
			String absHeadPath = getAbsImgPath(user.getHeadImage());
			if(absHeadPath != null){
				
				File file = new File(absHeadPath);
				if(file.exists()){
					System.out.println(absHeadPath);
					/*替换头像*/
					String headKey = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
					/*上传头像*/
					BucketTest.putImg(headKey, file);
					/*更新数据库*/
					String upSql = "update lp_user set head_image='"+headKey+"' where user_id='"+user.getUserId()+"'";
					userDao.upsertObject(upSql);
				}
				
			}
			
			/*获取身份证的绝对路径*/
			String absIdCardPath = getAbsImgPath(user.getIdCardImage());
			if(absIdCardPath != null){
				
				File file = new File(absIdCardPath);
				if(file.exists()){
					System.out.println(absIdCardPath);
					/*替换头像*/
					String idCardKey = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
					/*上传头像*/
					BucketTest.putImg(idCardKey, file);
					/*更新数据库*/
					String upSql = "update lp_user set idCard_image='"+idCardKey+"' where user_id='"+user.getUserId()+"'";
					userDao.upsertObject(upSql);
				}
			}
			
			
		}
	}
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
		
		return LaipaiConstants.UPLOAD_USER_PATH+"/"+fileName;
//		return "d://"+fileName;
	}
	
	public static void main(String args[]){
		
		ImportUserImg.importUser();
	}
	
	public static class LpUser{
		
		private String userId;
		
		/*头像照片*/
		private String headImage;

		/*头像照片*/
		private String idCardImage;

		public String getHeadImage() {
			return headImage;
		}

		public void setHeadImage(String headImage) {
			this.headImage = headImage;
		}

		public String getIdCardImage() {
			return idCardImage;
		}

		public void setIdCardImage(String idCardImage) {
			this.idCardImage = idCardImage;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
		
		
	}
}
