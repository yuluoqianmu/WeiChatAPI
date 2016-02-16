package com.lyz.api.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.BaseResp;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.Param;
import com.lyz.api.cache.ICache;
import com.lyz.api.config.ApiConfig;
import com.lyz.api.config.SystemConfig;
import com.lyz.api.file.FileManager;
import com.lyz.util.BaseTypeUtil;
import com.lyz.util.ClassUtil;
import com.lyz.util.MD5Generator;
import com.lyz.validate.ValidateException;
import com.lyz.validate.Validator;
/**
 * 基础处理类
 * @author luyongzhao
 *
 */
public abstract class BaseServlet extends HttpServlet{
	
	private static final Logger logger = Logger.getLogger(BaseServlet.class);
	
	protected static final Gson gson = new Gson();
	/*标签，写入文件的标识，需要从配置文件中读取文件的相关属性*/
	protected static String tag;
	
	private static final FileManager fileManager = FileManager.getIntance();
	/*配置对象*/
	protected static ApiConfig config = ApiConfig.getInstance();
	/*缓存操作对象*/
	protected static ICache cache = config.getCacheInstance();
	
	private static Validator validator =  Validator.getInstance();
	/*对于需要md5校验的，请求url设置的过期时间*/
	private static final long reqTimeout = 1000*60*5;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*标识是否发出了响应*/
		boolean isSent = false;
		try {
			/*只适用于设置post提交的request body的编码而不是设置get方法提交的queryString的编码*/
//			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Content-Type", "text/html; charset=utf-8");
			logger.info(req.getQueryString());
			/*注射请求参数*/
			BaseReq param = initParam4Get(req, getParamClass());
			/*返回响应数据，并对请求参数做校验*/
			String data = getRetData(param,req.getRequestURI(),req.getQueryString(),req);
			
			if(data == null){
				logger.warn("return data is null!");
				return;
			}
//			logger.info("resp:"+data);
			/*返回给客户端*/
			resp.getWriter().print(data);
			isSent = true;
			/*如果字符串不为空，计入日志文件，对于不希望计入文件的数据可置空*/
			String line = getStringToFile(param, req);
			if(line != null){
				logger.info(line);
				fileManager.writeLog(line, getTag());
			}
		} catch (Exception e) {
			/*捕获处理异常*/
			if(!isSent){
				resp.getWriter().print(gson.toJson(new BaseResp(CodeUtil.SERVER_ERROR)));
			}	
			logger.error("ErrorCode:"+CodeUtil.SERVER_ERROR,e);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		/*标识是否发出了响应*/
		boolean isSent = false;
		try {
			/*只适用于设置post提交的request body的编码而不是设置get方法提交的queryString的编码*/
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Content-Type", "text/html; charset=utf-8");
			/*注射请求参数*/
			BaseReq param = getJsonData(req, getParamClass());
			logger.info("request data:"+req.getRequestURL());
			/*获取json数据*/
			String data = getRetData(param,req);
			if(data == null){
				logger.warn("return data is null!");
				return;
			}
//			logger.info("resp:"+data);
			/*返回给客户端*/
			resp.getWriter().print(data);
			isSent = true;
			/*如果字符串不为空，计入日志文件，对于不希望计入文件的数据可置空*/
			String line = getStringToFile(param, req);
			if(line != null){
//				logger.info(param.toString());
				fileManager.writeLog(line, getTag());
			}
		} catch (Exception e) {
			/*捕获处理异常*/
			if(!isSent){
				resp.getWriter().print(gson.toJson(new BaseResp(CodeUtil.SERVER_ERROR)));
			}	
			logger.error("ErrorCode:"+CodeUtil.SERVER_ERROR,e);
		}
	}
	/**
	 * 校验请求参数
	 * @param req
	 * @return 校验通过返回null，不通过返回响应对象
	 */
	protected BaseResp validateParam(BaseReq req){
		
		try {
			validator.validate(req);
		} catch (ValidateException e) {
			return new BaseResp(CodeUtil.CLIENT_ERROR, e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 子类方法中可调用该方法用以指定缓存操作类
	 * 该方法的调用需要在执行post或者get请求之前方可生效
	 * @param cache
	 */
	protected void setCache(ICache cache){
		
		this.cache = cache;
	}
	
	/**
	 * 不带有缓存功能，适用于post
	 * @param param
	 * @return
	 */
	private String getRetData(BaseReq param, HttpServletRequest request){
		
		/*校验请求参数*/
		BaseResp resp = validateParam(param);
		if(resp != null){
			return gson.toJson(resp);
		}
		
		/*获取需要md5加密的串*/
		String needMd5 = this.getNeed2Md5(param);
		/*需要返回给客户端的对象*/
		Object retVal = null;
		/*公共数据校验*/
		BaseResp check = this.check(param, needMd5);
				
		/*如果校验通过，则执行请求*/
		if(check.getResult() == CodeUtil.SUCCESS){
			
			retVal = handler(param,request);	
			
		}else{//if rCode
			retVal = check;
		}
		
		if(retVal == null){			
			return null;
		}
		/*如果是字符串则直接返回，无需进行json转换*/
		if(retVal instanceof String){
			
			return (String)retVal;
		}
		
		return gson.toJson(retVal);
	}
	/**
	 * 拼装需要写入文件的数据字符串
	 * @param req
	 * @param request
	 * @return 如果不需要写入文件，直接返回null
	 */
	public abstract String getStringToFile(BaseReq req, HttpServletRequest request);
	
	
	/**
	 * 增加缓存处理
	 * @param retVal
	 * @return
	 */
	private String getRetData(BaseReq param, String uri,String queryString, HttpServletRequest request){
		
		/*校验请求参数*/
		BaseResp resp = validateParam(param);
		if(resp != null){
			return gson.toJson(resp);
		}
		
		/*获取需要md5加密的串*/
		String needMd5 = this.getNeed2Md5(param);
		/*需要返回给客户端的对象*/
		Object retVal = null;
		/*公共数据校验*/
		BaseResp check = this.check(param, needMd5);
				
		/*如果校验通过，则执行请求*/
		if(check.getResult() == CodeUtil.SUCCESS){
			
			/*判断是否需要缓存*/
			String turi = getUri4Config(uri);
			int cacheTime = config.getCacheTime(turi);
			if(cacheTime <= 0){/*不需要缓存的直接请求数据*/
				retVal = handler(param, request);
				logger.info("data from db:"+uri);
			}else{
				
				/*获取缓存数据*/
				String key = getKey4Cache(uri, queryString);
				String data = cache.getData(key);
				if(data == null){
					logger.info("data from db:"+uri);
					/*缓存中没有，则去处理请求*/
					retVal = handler(param, request);
					/*同时将数据缓存*/
					cache.setData(key, gson.toJson(retVal), cacheTime);
				}else{
					retVal = data;
				}
			}//else cahcheTime			
			
		}else{//if rCode
			retVal = check;
		}
		
		if(retVal == null){			
			return null;
		}
		/*如果是字符串则直接返回，无需进行json转换*/
		if(retVal instanceof String){
			
			return (String)retVal;
		}
		
		return gson.toJson(retVal);
	}
	/**
	 * 将其中的所有“/”替换成“_”
	 * @param uri
	 * @return
	 */
	private String getUri4Config(String uri){
		
		return uri.replaceAll("/", "_");

	}
	/**
	 * 获取用于缓存的key
	 * @param uri
	 * @param queryString
	 * @return
	 */
	public String getKey4Cache(String uri, String queryString){
		
		return MD5Generator.MD5(uri+"?"+queryString);
	}
	/**
	 * 请求的公共数据校验可防止在此处
	 * @param param
	 * @param needMd5
	 * @return
	 */
	private BaseResp check(BaseReq param, String needMd5){
		/*不需要校验，则忽略*/
		if(needMd5 == null){
			return new BaseResp(CodeUtil.SUCCESS);
		}
		/*校验码不存在，或者不正确则返回错误*/
		if(param.getR()==null || "".equals(param.getR()) || !needMd5.equals(param.getR())){
			return new BaseResp(CodeUtil.CLIENT_ERROR,"校验码错误！");
		}
		/*如果请求过期，则给出提示*/
		if(SystemConfig.isReqExpired && System.currentTimeMillis()-param.getT()>reqTimeout){
			
			return new BaseResp(CodeUtil.CLIENT_ERROR,"请求过期！");
		}
		return new BaseResp(CodeUtil.SUCCESS);
	}
	/**
	 * 设置标签，以便请求日志写入文件，如果不需要写日志可设置为null
	 * @return
	 */
	public abstract String getTag();
	/**
	 * 获取需要md5加密的串，如果不需要md5校验可直接返回null
	 * @return
	 */
	public String getNeed2Md5(BaseReq param){
		
		return MD5Generator.MD5(SystemConfig.key+param.getT());
	}
	/**
	 * 子类需要实现的方法，返回参数类
	 * @return 参数类，不能返回null
	 */
	public abstract Class getParamClass();
	/**
	 * 子类中需要实现的实际处理的方法
	 * @param param
	 * @return json格式字符串，或者未转换成json格式字符串的对象
	 */
	public abstract Object handler(BaseReq param, HttpServletRequest request);
	
	/**
	 * 实例化的类需要有不带有参数的构造方法
	 * @param req
	 * @param clazz
	 * @return
	 */
	public BaseReq initParam(HttpServletRequest req, Class clazz){
		
		/*实例化对象*/
		Object obj = ClassUtil.newInstance(clazz.getName(), null);
		
		/*判断是否为BaseReq的子类*/
		if(!(obj instanceof BaseReq)){
			logger.error("request class is not sub class of BaseReq");
			return null;
		}
		Method[] methods = clazz.getMethods();
		if(methods==null || methods.length==0){
			logger.warn("no method in class:"+clazz.getName());
		}
		/*遍历方法*/
		try {
			for(Method method : methods){
				/*如果set开头的函数，则调用*/
				if(method.getName().startsWith("set")){
					String fieldName = getFieldName(method.getName());
					Object attr = req.getParameter(fieldName);
					/*如果属性不存在，则跳过*/
					if(attr == null){
						continue;
					}
					Class<?>[] paramTypes = method.getParameterTypes();
					
					if(paramTypes[0] == int.class){
						attr = Integer.parseInt((String)attr);
					}else if(paramTypes[0] == float.class){
						attr = Float.parseFloat((String)attr);
					}else if(paramTypes[0] == double.class){
						attr = Double.parseDouble((String)attr);
					}else if(paramTypes[0] == boolean.class){
						attr = Boolean.parseBoolean((String)attr);
					}else if(paramTypes[0] == long.class){
						attr = Long.parseLong((String)attr);
					}
					method.invoke(obj, attr);
				}
			}
		} catch (Exception e) {
			logger.error("fail to init parameter", e);
		}
		
		return (BaseReq)obj;
	}
	
	private Param getParamFromQueryString(String queryString){
		
		Param param = new Param();
		
		if(queryString == null){
			return param;
		}
		
		String[] keyAndVals = queryString.trim().split("&");
		for(String kv : keyAndVals){
			String[] keyAndVal = kv.split("=");
			if(keyAndVal.length == 2){
				param.setParameter(keyAndVal[0], keyAndVal[1]);
			}else{
				logger.warn("invalid param:"+kv);
			}
		}
		
		return param;		
	}
	
	public BaseReq initParam4Get(HttpServletRequest req, Class clazz){
		
//		Param req = getParamFromQueryString(queryString);
		/*实例化对象*/
		Object obj = ClassUtil.newInstance(clazz.getName(), null);
		
		/*判断是否为BaseReq的子类*/
		if(!(obj instanceof BaseReq)){
			logger.error("request class is not sub class of BaseReq");
			return null;
		}
		Method[] methods = clazz.getMethods();
		if(methods==null || methods.length==0){
			logger.warn("no method in class:"+clazz.getName());
		}
		/*遍历方法*/
		try {
			for(Method method : methods){
				/*如果set开头的函数，则调用*/
				if(method.getName().startsWith("set")){
					String fieldName = getFieldName(method.getName());
					Object attr = req.getParameter(fieldName);
					/*如果属性不存在，则跳过*/
					if(attr == null){
						continue;
					}
					Class<?>[] paramTypes = method.getParameterTypes();
					
					if(paramTypes[0] == int.class){
						attr = BaseTypeUtil.getInteger((String)attr, -1);
					}else if(paramTypes[0] == float.class){
						attr = BaseTypeUtil.getFloat((String)attr, -1);
					}else if(paramTypes[0] == double.class){
//						attr = Double.parseDouble((String)attr);
						attr = BaseTypeUtil.getDouble((String)attr, -1);
					}else if(paramTypes[0] == boolean.class){
//						attr = Boolean.parseBoolean((String)attr);
						attr = BaseTypeUtil.getBoolean((String)attr, false);
					}else if(paramTypes[0] == long.class){
						attr = BaseTypeUtil.getLong((String)attr, -1);
					}
					method.invoke(obj, attr);
				}
			}
		} catch (Exception e) {
			logger.error("fail to init parameter", e);
		}
		
		return (BaseReq)obj;
	}
	/**
	 * 获取post请求上传的json格式数据
	 * @param req
	 * @param clazz
	 * @return
	 */
	public BaseReq getJsonData(HttpServletRequest req, Class clazz){
		
		String value = null;
		try {
			InputStream in = req.getInputStream();

			int len = -1;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((len = in.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}

			byte[] data = baos.toByteArray();

			value = new String(data, "utf-8");
			if(value==null || "".equals(value.trim())){
				return null;
			}
			return gson.fromJson(value, clazz);
		} catch (Exception e) {
			logger.error("fail to parse post req to json data:"+value,e);
		}
		return null;
	}
	/**
	 * 获取post请求上传的参数对
	 * @param req
	 * @param clazz
	 * @return
	 */
	public BaseReq initParam4Post(HttpServletRequest req, Class clazz){
		
//		Param req = getParamFromQueryString(queryString);
		/*实例化对象*/
		Object obj = ClassUtil.newInstance(clazz.getName(), null);
		
		/*判断是否为BaseReq的子类*/
		if(!(obj instanceof BaseReq)){
			logger.error("request class is not sub class of BaseReq");
			return null;
		}
		Method[] methods = clazz.getMethods();
		if(methods==null || methods.length==0){
			logger.warn("no method in class:"+clazz.getName());
		}
		/*遍历方法*/
		try {
			for(Method method : methods){
				/*如果set开头的函数，则调用*/
				if(method.getName().startsWith("set")){
					String fieldName = getFieldName(method.getName());
					Object attr = req.getParameter(fieldName);
					/*如果属性不存在，则跳过*/
					if(attr == null){
						continue;
					}
					Class<?>[] paramTypes = method.getParameterTypes();
					
					if(paramTypes[0] == int.class){
						attr = BaseTypeUtil.getInteger((String)attr, -1);
					}else if(paramTypes[0] == float.class){
						attr = BaseTypeUtil.getFloat((String)attr, -1);
					}else if(paramTypes[0] == double.class){
//						attr = Double.parseDouble((String)attr);
						attr = BaseTypeUtil.getDouble((String)attr, -1);
					}else if(paramTypes[0] == boolean.class){
//						attr = Boolean.parseBoolean((String)attr);
						attr = BaseTypeUtil.getBoolean((String)attr, false);
					}else if(paramTypes[0] == long.class){
						attr = BaseTypeUtil.getLong((String)attr, -1);
					}
					method.invoke(obj, attr);
				}
			}
		} catch (Exception e) {
			logger.error("fail to init parameter", e);
		}
		
		return (BaseReq)obj;
	}
	
	/**
	 * 获取字段名称，字段必须首字母小写
	 * @param setMethodName
	 * @return
	 */
	public String getFieldName(String setMethodName){
		
		String tmpName = setMethodName.substring(3);
		/*将第一个大写字母小写*/
		String firstChar = (tmpName.charAt(0)+"").toLowerCase();
		
		return firstChar+tmpName.substring(1);
	}
	
	public static void main(String args[]){
//		System.out.println(BaseServlet.class.getName());
//		System.out.println(BaseServlet.getFieldName("getFieldName"));
	}
	
	

}
