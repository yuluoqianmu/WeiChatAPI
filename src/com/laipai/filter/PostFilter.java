package com.laipai.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.laipai.app.process.RequestStream2StringUtil;
import com.laipai.file.FileManager;
import com.laipai.util.BaseTypeUtil;
/**
 * 过滤post请求参数，记录到日志文件中
 * @author luzi
 *
 */
public class PostFilter implements Filter{
	
	private static FileManager fileMng = FileManager.getIntance();
	
	private static final String tag = "access";

	@Override
	public void destroy() {
//		System.out.println("filter destroy!");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest)req;
		HttpServletResponse httpResp = (HttpServletResponse)resp;
		
		httpReq.setCharacterEncoding("UTF-8");
		httpResp.setCharacterEncoding("UTF-8");
		httpResp.setHeader("Content-Type", "text/html; charset=utf-8");
		
//		String contentType = httpReq.getHeader("content-type");
//		String requestText = null;
//		if (contentType == null || !contentType.startsWith("multipart/form-data")) {
//			requestText = RequestStream2StringUtil.conver(httpReq);
//		}
		
		Enumeration<String> names = httpReq.getParameterNames();
		
		StringBuilder builder = new StringBuilder();
		builder.append(BaseTypeUtil.getStrDateTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")).append("\t");
		builder.append(req.getRemoteAddr()).append("\t").append(httpReq.getRequestURI()).append("\t");
		/*登录数据不计入日志*/
		String uri = httpReq.getRequestURI();
		if(uri.contains("priveliegeLogin.action") || uri.endsWith(".css")
				||uri.endsWith(".js")
				||uri.endsWith(".png")
				||uri.endsWith(".jpg")
				||uri.endsWith(".jpeg")
				||uri.endsWith(".gif")){
			/*执行之后的请求*/
			chain.doFilter(req, resp);
			return;
		}
		
		while(names.hasMoreElements()){
			String name = names.nextElement();
			/*获取参数名称*/
			Object obj = httpReq.getParameter(name);
			if(obj != null && obj instanceof String){
				builder.append(name).append("=").append(obj);
//				String[] strArray = (String[])obj;
//				int i = 0;
//				for(String str : strArray){
//					if(i > 0){
//						builder.append("|").append(str);
//					}else{
//						builder.append(str);
//					}
//				}
				
				builder.append("\t");
			}
			
			
		}
//		if(requestText != null && !"".equals(requestText.trim())){
//			builder.append(requestText).append("\t");
//		}
		builder.append("\n");
		System.out.println("===:"+builder.toString());
		/*写入文件*/
		fileMng.writeLog(builder.toString(), tag);
		/*执行之后的请求*/
		chain.doFilter(req, resp);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
//		System.out.println("filter init!");
	}

}
