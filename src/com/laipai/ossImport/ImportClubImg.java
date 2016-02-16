package com.laipai.ossImport;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.laipai.base.util.LaipaiConstants;
import com.laipai.img.ImgUtil;
import com.lyz.db.IBaseDao;
import com.lyz.db.dao.BaseDaoImpl;
import com.lyz.util.BaseTypeUtil;
import com.lyz.util.LaiPaiEntityIdGenerator;

public class ImportClubImg {
	
private static final IBaseDao<Club> clubDao = new BaseDaoImpl<Club>();
	
	
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
		
		return LaipaiConstants.UPLOAD_CLUB_PATH+"/"+fileName;
//		return "d://"+fileName;
	}
	
	public static void importClub(){
		
					
			String sql = "select laipai_id,content,cover_url from lp_club";
			List<Club> clubList = clubDao.queryObjects(sql, Club.class);
			if(clubList==null || clubList.isEmpty()){
				return;
			}
			
			/*获取路径*/
			for(Club club : clubList){
				
				/*获取图片的绝对路径*/
				String absPhotoPath = getAbsImgPath(club.getCoverUrl());
				if(absPhotoPath != null){
					
					File file = new File(absPhotoPath);
					System.out.println(absPhotoPath);
					if(file.exists()){
						
						/*替换封面*/
						String photoKey = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
						/*上传头像*/
						BucketTest.putImg(photoKey, file);
						/*更新数据库*/
						String upSql = "update lp_club set cover_url='"+photoKey+"' where laipai_id='"+club.getLaipaiId()+"'";
						clubDao.upsertObject(upSql);
					}
					
				}//end if
				
				/*获取内容中的图片*/
				if(club.getContent()!=null){
					String content = convertContent(club.getContent());
					String upSql = "update lp_club set content='"+content+"' where laipai_id='"+club.getLaipaiId()+"'";
					clubDao.upsertObject(upSql);
				}
				
			}//end for
		}
	/**
	 * 返回转换之后的内容
	 * @param content
	 * @return
	 */
	public static String convertContent(String content){
		
		StringBuilder builder = null;
		try {
			if(content==null || "".equals(content.trim())){
				return content;
			}
			
			ByteArrayInputStream bis = new ByteArrayInputStream(content.getBytes("utf-8"));
			InputStreamReader isr = new InputStreamReader(bis,"utf-8");
			BufferedReader br = new BufferedReader(isr);
			String line = br.readLine();
			builder = new StringBuilder();
			while(line != null){
				
				builder.append(replace(line)).append("\n");
				line = br.readLine();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static String replace(String line){
		
		if(line==null || !line.contains("<img")){
			return line;
		}
		
		/*获取图片路径*/
		String imgPath = BaseTypeUtil.getSubString(line, "src=\"", "\"");
		System.out.println(line);
		/*获取绝对路径*/
		String absPath = getAbsImgPath(imgPath);
		File file = new File(absPath);
		if(file.exists()){
			/*替换封面*/
			String photoKey = LaiPaiEntityIdGenerator.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
			/*上传图片*/
			BucketTest.putImg(photoKey, file);
			line = line.replace(imgPath, "http://img.ilaipai.com/"+photoKey+"@.webp");
			
		}
		
		return line;
	}
	
	public static class Club{
		
		private String laipaiId;
		
		private String content;
		
		private String coverUrl;

		public String getLaipaiId() {
			return laipaiId;
		}

		public void setLaipaiId(String laipaiId) {
			this.laipaiId = laipaiId;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getCoverUrl() {
			return coverUrl;
		}

		public void setCoverUrl(String coverUrl) {
			this.coverUrl = coverUrl;
		}
		
		
	}
	
	public static void main(String args[]){
		
//		try {
//			FileReader fr = new FileReader(new File("d://test.txt"));
//
//			BufferedReader br= new BufferedReader(fr);
//			String line = br.readLine();
//			
//			while(line != null){
//				
//				System.out.println("========="+line);
//				line = br.readLine();
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		String line = ImportClubImg.replace("<img src=\"d://1.jpg\" alt=\"\" height=\"214\" width=\"320\" /> ");
//		System.out.println(line);
		
		ImportClubImg.importClub();
	}
}
