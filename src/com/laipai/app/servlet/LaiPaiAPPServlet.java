package com.laipai.app.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import com.laipai.app.common.Global;
import com.laipai.app.process.ProcessRequest;
import com.laipai.base.util.SpringContextUtil;

/**
 * 来拍后台与手机客户端http接口
 * */
public class LaiPaiAPPServlet extends HttpServlet{
	private Logger logger = Logger.getLogger(getClass());
	
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);
	}
	
	/***
	 * post提交方式
	 * */
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
		try {
			response.setContentType("text/html;charset=" + Global.DATA_ENCODE);

			request.setCharacterEncoding(Global.DATA_ENCODE);
			
			ProcessRequest processRequest = (ProcessRequest) SpringContextUtil.getBean("processRequest");
			String result = processRequest.process(request);

			//输出结果
			out(response.getWriter(), result);
		} catch (Exception e) {
			logger.error("app exception!",e);
			out(response.getWriter(), "网络不给力...");
		}
	}

	private void out(Writer out, String result) {
		if(out==null){
			return;
		}
		if(result==null || "".equals(result.trim())){
			result = "网络不给力!";
		}
		try {
			out.write(result);
		} catch (IOException e) {
			logger.error("fail to send message to client!",e);
		}
	}
	
	
	
}
