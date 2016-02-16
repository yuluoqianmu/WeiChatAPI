package com.laipai.app.process.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.annotation.RequestParameters;
import com.laipai.app.common.Global;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.Tools;
import com.laipai.app.process.ProcessRequest;
import com.laipai.app.process.RequestStream2StringUtil;
import com.laipai.app.process.app.binder.UserAppRequestBinderRegister;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.pojo.MobileDevice;
import com.laipai.base.util.ApplicationContextUtil;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.base.util.SpringContextUtil;
import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.userManInfo.pojo.LpUser;
import com.lyz.api.cache.ICache;
import com.lyz.api.cache.OcsCache;
import com.lyz.api.config.SystemConfig;

@Service("processRequest")
public class ProcessRequestImpl implements ProcessRequest {
	
	private static final ICache cache = OcsCache.getInstance();

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private IMobileDeviceDAO mobileDeviceDAO;
	
	@Autowired
	private IBaseDao baseDao;
	
	public static final String TEMPIMAGES="temp";

	public String process(HttpServletRequest request) throws IOException {
		
		String contentType = request.getHeader("content-type");
		String requestText = null;
		Map<String, FileItem> fileMap =  new HashMap<String, FileItem>();
		/*处理非上传文件请求*/
		if (contentType == null || !contentType.startsWith("multipart/form-data")) {
			requestText = RequestStream2StringUtil.conver(request);
		} else {// 文件上传
			
			JSONObject json = new JSONObject();
			try {
				// 创建文件处理工厂，它用于生成FileItem对象。
		        DiskFileItemFactory difactory = new DiskFileItemFactory();
		        //设置缓存大小，如果上传文件超过缓存大小，将使用临时目录做为缓存。
		        difactory.setSizeThreshold(1024 * 1024 * 50);
		        // 设置处理工厂缓存的临时目录，此目录下的文件需要手动删除。
		        String dir = TEMPIMAGES;
		        File filedir = new File(dir);
		        if (!filedir.exists()){
		            filedir.mkdir();
		        }
		        difactory.setRepository(filedir);
		        // 创建request的解析器，它会将数据封装到FileItem对象中。
		        ServletFileUpload sfu = new ServletFileUpload(difactory);
		        // 解析保存在request中的数据并返回list集合
		        List<FileItem> list = null;
		        try {
		            list = sfu.parseRequest(request);
		        } catch (FileUploadException e) {
		            logger.error("fail to upload file!",e);
		        }
		        // 遍历list集合，取出每一个输入项的FileItem对象，并分别获取数据
		        if(list!=null && list.size()>0){
		        	for (Iterator<FileItem> it = list.iterator(); it.hasNext();) {
			            FileItem fi =  it.next();
			            if (fi.isFormField()) {
			               if("json".equals(fi.getFieldName())){
			            		requestText = fi.getString();
			               }
			            } else {
			               //由于客户端向服务器发送的文件是客户端的全路径，在这我们只需要文件名即可
			               String filename = fi.getName();
			               //fileMap<文件名,输出流>，存储输出流对象，在业务类中，直接输出
			               fileMap.put(filename, fi);
			            }
			        }
		        }
		        
		        String result = validateFileType(fileMap);
				if (result != null) return result;
			} catch (Exception e) {
				logger.error("fail to upload file..!",e);
			}
		}//else 文件上传

		if (Global.DEBUG) System.out.println(requestText);
		if (requestText == null || requestText.length() == 0){
			return null;
		}
		/*集中处理文件上传和客户端请求*/
		logger.info("request\t"+requestText);
		logger.info("post length body:"+requestText.length());
		String result = processRequest(request,requestText, fileMap);
		logger.info("response\t"+result);
		return result;
	}

	public String processRequest(HttpServletRequest request,String requestText, Map<String, FileItem> fileMap) {
		JSONObject json = null;
		try {
			json = JSONObject.fromObject(requestText);
			json.put("serverName", request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort());
		} catch (Exception e) {
			return JSONTools.newAPIResultToString(1, "基本格式错误");
		}
		try {
			BaseRequestParameters params = new BaseRequestParameters(json, fileMap);
			JSONConvertUtil.jsonToObject(json, params);
			int appType = json.getInt("appType");
			params.setAppType(appType);
			int osType = json.getInt("osType");
			params.setOsType(osType);
			int apiVersion = json.getInt("apiVersion");
			params.setApiVersion(apiVersion);
			int requestId = json.getInt("requestId");
			params.setMobileId("1000000fadfa");
			try {
				String version = json.getString("version");
				params.setVersion(version);
			} catch (Exception e1) {
				logger.error("no version!");
				params.setVersion("0.0");
			}
			try {
				params.setPicType(json.getString("picType"));
			} catch (Exception e) {
				logger.error("no picType!");
			}
			
//			new UserAppRequestBinderRegister();
//			new RequestBinderRegister();
			Class<?> execClass = RequestBinderRegister.getClass(appType,requestId);

			if (execClass == null) return JSONTools.newAPIResultToString(1, "API不存在");
			
			//对于必须登录才能操作的接口，查询用户登录状态
			NotLogin notLogin = execClass.getAnnotation(NotLogin.class);
			if (notLogin == null) {
				//用于查询用户是否已登录
				String userId = json.getString("userId");
				//需要检测登录状态时，却没传userId
				if(StringUtils.isEmpty(userId)){
					return JSONTools.newAPIResultToString(1, "the userId is null(userId 不能为空)");
				}
				
				Object obj = baseDao.getObjectById(LpUser.class, userId);
				if (obj == null) {
					return JSONTools.newAPIResultToString(1, "This user does not exist(此用户不存在)");
				}else{
					LpUser user = (LpUser) obj;
					if(user.getLoginStatus()==null || user.getLoginStatus().intValue() == 1){//未登录时，返回错误信息
						return JSONTools.newAPIResultToString(100, "please login system(请登录系统再进行操作)");
					}
				}
			}
			
			/*获取请求执行类*/
			RequestExecutor executor = getInstance(execClass);
			if (executor == null){
				return JSONTools.newAPIResultToString(1, "应用错误，API不存在");
			}
			
			Object[] paramsObjects = paramsToObject(execClass, json);
			String data = null;
			/*尝试从缓存中获取数据,如果缓存中有数据直接返回*/
			String key = executor.getCacheKey(params, paramsObjects);
			if(key != null && SystemConfig.openCache){
				data = cache.getData(key);
				if(data != null){
					logger.debug("data is from cache,requestId="+requestId);
					return data;
				}
			}
			/*对于缓存中没有的数据，直接从数据库中获取*/
			logger.info("data is from db,requestId="+requestId);
			Object object = executor.execute(params, paramsObjects);
			if (object == null) {
				data = createJSON(0, null);
			} else if (object instanceof JSONObject) {
				if (((JSONObject) object).get("result") != null){
					data = object.toString();
				}else{
					data = createJSON(0, object);
				}				
			} else if (object instanceof JSONArray) {
				data = createJSON(0, object);
			} else{
				Object jsonObject = JSONConvertUtil.allObjectToJSON(object);
				data = createJSON(0, jsonObject);
			}
			/*将数据加入缓存，过期时间*/
			if(key !=null && data != null){
				cache.setData(key, data, ICache.EXPIRED_ONE_MINUTE);
			}			
			/*返回结果*/
			return data;
		}catch(JSONException e){
			logger.warn("error param:"+requestText,e);
			return JSONTools.newAPIResultToString(1, "API基本参数传递错误");			
		}catch (Exception e) {
			logger.error("", e);
			return JSONTools.newAPIResultToString(1, "applicationError应用错误，请与管理员联系 \r\n" + (Global.DEBUG ? Tools.exceptionToString(e) : ""));
		}

	}

	private String validateFileType(Map<String, FileItem> fileMap){
		String[] uploadTypeArr = new String[] { ".jpg", ".gif", ".png",".jpeg" };
		Set<Map.Entry<String, FileItem>> set = fileMap.entrySet();
		for (Map.Entry<String, FileItem> f : set) {
			String name = f.getKey().toLowerCase();
			boolean _isSuccess = false;
			for (String suffix : uploadTypeArr) {
				if (name.endsWith(suffix)) {
					_isSuccess = true;
					break;
				}
			}
			if (!_isSuccess) {
				return JSONTools.newAPIResultToString(1, "文件类型上传错误,只能上传JPG/JPEG/GIF/PNG");
			}
		}
		return null;
	
	}
	
	private String validateFile(Map<String, MultipartFile> fileMap) {
		String[] uploadTypeArr = new String[] { ".jpg", ".gif", ".png",".jpeg" };
		Set<Map.Entry<String, MultipartFile>> set = fileMap.entrySet();
		for (Map.Entry<String, MultipartFile> f : set) {
			MultipartFile file = f.getValue();
			String name = file.getOriginalFilename().toLowerCase();
			boolean _isSuccess = false;
			for (String suffix : uploadTypeArr) {
				System.out.println("------------------检查文件扩展名$$$$$$$$$$$$");
				if (name.endsWith(suffix)) {
					_isSuccess = true;
					break;
				}
			}
			if (!_isSuccess) {
				return JSONTools.newAPIResultToString(1, "文件类型上传错误,只能上传JPG/GIF/PNG");
			}
		}
		return null;
	}

	/**
	 * json to object
	 * 
	 * @param execClass
	 * @param json
	 * @return
	 */
	private Object[] paramsToObject(Class<?> execClass, JSONObject json) {
		RequestParameters params = execClass.getAnnotation(RequestParameters.class);
		if (params == null) return new Object[0];
		Class<?>[] classArr = params.value();
		Object[] objArr = new Object[classArr.length];
		int index = 0;
		for (Class<?> _class : classArr) {
			String parameterName = _class.getSimpleName();
			parameterName = parameterName.substring(0, 1).toLowerCase() + parameterName.substring(1);

			Object object = null;
			try {
				Object obj = json.get(parameterName);
				if (obj != null && obj instanceof JSONObject) {
					JSONObject data = (JSONObject) obj;
					if (data != null) {
						object = newInstance(_class);
						JSONConvertUtil.jsonToObject(data, object);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			objArr[index] = object;
			index++;
		}

		return objArr;
	}

	private static Object newInstance(Class<?> classzz) {
		try {
			return classzz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <T> T getInstance(Class<?> classzz) {
		try {
			return (T) ApplicationContextUtil.getBean(classzz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String createJSON(int result, Object data) {
		JSONObject object = new JSONObject();
		object.put("result", result);
		if (data != null) object.put("data", data);

		return object.toString();

	}

}
