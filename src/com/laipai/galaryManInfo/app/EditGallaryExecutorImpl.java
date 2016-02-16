package com.laipai.galaryManInfo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.Tools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.GallerySort;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.pojo.LpGalaryDetail;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.img.ImgUtil;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceStyle;
import com.laipai.userManInfo.pojo.LpUser;

@NotLogin
@Service("EditGallaryExecutorImpl")
public class EditGallaryExecutorImpl implements RequestExecutor {

	private static final Logger logger = Logger
			.getLogger(EditGallaryExecutorImpl.class);

	@Autowired
	private IBaseDao baseDao;
	@Resource(name = IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;

	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();

		// 存储9张作品内容的图片路径
		StringBuffer sb = new StringBuffer();
		// 存封面图片路径
		List<String> newCoverName = new ArrayList<String>();
		String gallaryId = json.getString("galaryId");
		// 保存图片到磁盘
		uploadImgFile(sb, newCoverName, parameters, gallaryId);

		/** 上传完之后的图片路径 */
		String[] imagePathArray = sb.toString().split(";");
		// 服务id
		String serviceId = json.getString("serviceId");
		// 摄影师的id
		// String userId = json.getString("userId");
		// 作品集描述
		String galaryDesc = json.getString("galaryDesc");
		// 作品集主题
		String subjectName = json.getString("subjectName");
		String photoIds = json.getString("photoIds");
		String newCoverId = JSONTools.getString(json, "newCoverId");

		// 删除ID不在photoIds的照片
		if (StringUtils.isNotEmpty(photoIds)) {
			// photoIds= photoIds.substring(0,photoIds.length()-1);
			String[] photo_ids = photoIds.split(",");
			for (int i = 0; i < photo_ids.length; i++) {
				String photo_id = photo_ids[i];
				String deleteSql = "delete  from lp_galary_detail where photo_id ='"
						+ photo_id + "'";
				baseDao.executeSql(deleteSql);
			}

		}
		// newCoverId不为空，更新封面还是原图中选的
		if (StringUtils.isNotBlank(newCoverId)) {
			List<LpGalaryDetail> list = baseDao
					.queryListObjectAll("from LpGalaryDetail where lpGalary.galaryId='"
							+ gallaryId + "'");
			if (list != null && list.size() > 0) {
				for (LpGalaryDetail detail : list) {
					if (newCoverId.equals(detail.getPhotoId())) {
						detail.setGalaryConver(true);
						baseDao.updateObject(detail);
					}
				}
			}

		}

		LpGalary gallery = (LpGalary) baseDao.getObjectById(LpGalary.class,
				gallaryId);

		// gallery.setCreatTime(new Timestamp(new java.util.Date().getTime()));
		gallery.setSubjectName(subjectName);
		gallery.setGalaryDesc(galaryDesc);
		// 保存作品集到数据库
		updateGallery(gallery, serviceId, imagePathArray, newCoverName);

		// 保存成功,返回galaryId给app
		JSONObject baseJson = new JSONObject();
		baseJson.put("resulteMessage", "编辑成功");
		return baseJson;
	}

	/**
	 * 保存图片到磁盘 sb : 保存作品集9张内容图片 newCoverName : 保存作品集封面图片 parameters :
	 * 临时保存的参数,从中可以取出文件对象
	 * */
	private void uploadImgFile(StringBuffer sb, List<String> newCoverName,
			BaseRequestParameters parameters, String gallaryId) {
		// 作品集上传目录
//		String filePath = LaipaiConstants.UPLOAD_ABSOLUTE_IMG
//				+ SimpleImage.LP_GALLERY_IMGURL;
		// 文件列表
		Set<Map.Entry<String, FileItem>> set = parameters.getFileMap()
				.entrySet();
		if (set != null && set.size() > 0) {
			try {
				for (Map.Entry<String, FileItem> f : set) {
					String filename = f.getKey();
					String absFilePath = ImgUtil.saveImg(f, LaipaiConstants.UPLOAD_GALARY_PATH);
					// 判断图片是否是封面
					if (filename.contains("cover")) {
						newCoverName.add(absFilePath);
					} else if (filename.contains("original")) {

					}
					sb.append(absFilePath + ";");
				}
				/* 如果有新的封面，则将原来的设置为非封面 */
				if (!newCoverName.isEmpty()) {
					/* 如果有新的封面图，则将原来的封面图设置成false */
					baseDao
							.executeSql("UPDATE lp_galary_detail SET galary_conver=0 WHERE galary_id='"
									+ gallaryId + "' AND galary_conver=1");
				}

			} catch (Exception e) {
				logger.error("fail to upload galary!", e);
			}
		}
	}

	/**
	 * 保存作品集
	 * */
	private void updateGallery(LpGalary gallery, String serviceId,
			String[] imageNameArray, List<String> newCoverName) {
		try {
			// 3：和服务关联
			if (serviceId != null && !"private".equals(serviceId)) {
				LpService lpService = (LpService) baseDao.getObjectById(
						LpService.class, serviceId);
				gallery.setLpService(lpService);
				// 设置风格style
				if (lpService != null) {
					if (lpService.getLpServiceDetail() != null) {
						List<LpServiceStyle> serviceStyleList = baseDao
								.queryListObjectAll("from LpServiceStyle where detailId='"
										+ lpService.getLpServiceDetail()
												.getDetailId() + "'");
						String styleIds = "";
						for (LpServiceStyle s : serviceStyleList) {
							styleIds += s.getStyleId() + ",";
						}
						if (styleIds.endsWith(",")) {
							styleIds = styleIds.substring(0,
									styleIds.length() - 1);
						}
						String[] styleId = styleIds.split(",");
						// 4：和风格关联
						if (styleId != null && styleId.length > 0) {
							Set<LpStyle> styles = new HashSet<LpStyle>();
							for (int i = 0; i < styleId.length; i++) {
								String id = styleId[i];
								LpStyle style = (LpStyle) baseDao
										.getObjectById(LpStyle.class, id);
								styles.add(style);
							}
							gallery.setLpStyle(styles);
						}
					}
				}
			} else {
				gallery.setLpService(null);
			}

			/* 更新作品集封面 */
			if (newCoverName.size() > 0) {
				gallery.setGalaryCover(newCoverName.get(0));
			}

			baseDao.updateObject(gallery);
			// 1：向galleryDetails表中插入照片数据
			this.saveGalleryPhotos(gallery, imageNameArray, newCoverName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据用户名查询用户
	 */
//	private LpUser queryUserByName(String userName) {
//		String hql = "from LpUser where user_name ='" + userName + "'";
//		List<LpUser> users = baseDao.queryListObjectByTopNum(hql, 1);
//		if (users != null && users.size() > 0) {
//			return users.get(0);
//		}
//		return null;
//	}

	/**
	 * @Description:保存照片入库
	 */
	private void saveGalleryPhotos(LpGalary galaryBean,
			String[] imageNameArray, List<String> newCoverNameList) {
		try {
			String newCoverPath = null;
			if (newCoverNameList.size() > 0) {
				newCoverPath = newCoverNameList.get(0);
			}
			if (imageNameArray != null && imageNameArray.length > 0) {
				for (int i = 0; i < imageNameArray.length; i++) {
					// if(StringUtils.isNotEmpty(imageNameArray[i])&&!imageNameArray[i].contains("galaryCover")){
					// 组织用户附件的PO对象对象
					LpGalaryDetail photo = new LpGalaryDetail();
					// 文件名
					photo.setPhotoName(imageNameArray[i]);
					// 保存原图入库
					photo.setPhotoSrc(imageNameArray[i]);
					// 判断是否为封面原图
					if (imageNameArray[i].equals(newCoverPath)) {
						photo.setGalaryConver(true);
					} else {
						photo.setGalaryConver(false);
					}

					// 上传时间,当前时间
					photo.setCreatTime(new Timestamp(new java.util.Date()
							.getTime()));
					// 附件对象关联用户对象（多的一端关联一的一端），如果不关联对象多的一端的外键列为null
					photo.setLpGalary((LpGalary) galaryBean.clone());
					photo.setStatus(0);
					// 保存
					baseDao.save(photo);
				}
				// }
			}
		} catch (Exception e) {
			logger.error("fail to saveGalleryPhotos!", e);
		}
	}

	// private String getRelativePath(String fileName){
	// String savePath=LaipaiConstants.UPLOAD_ABSOLUTE_IMG +
	// SimpleImage.LP_GALLERY_IMGURL+"/"+fileName;
	// return savePath;
	// }

	/**
	 * 将字符串转为图片
	 * 
	 * @param imgStr
	 * @return
	 */
	private static boolean generateImage(String imgStr, String imgFile)
			throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = imgFile;// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
