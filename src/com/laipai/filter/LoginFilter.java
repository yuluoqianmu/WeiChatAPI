package com.laipai.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LoginFilter implements Filter {

	private Logger logger = Logger.getLogger(getClass());
    private static  FilterConfig config=null;
    private static  Set<String> passRequest=new HashSet<String>();
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LoginFilter.config=filterConfig;
		if(filterConfig.getInitParameter("pass")!=null){
			String [] pass=filterConfig.getInitParameter("pass").replaceAll(";", ",").split(",");
			for(String p:pass){
				passRequest.add(p);
			}
			
		}
	}
    public static FilterConfig getFilterConfig(){
    	return config;
    }
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest=(HttpServletRequest) request;
		HttpServletResponse servletResponse=(HttpServletResponse)response;
		String userKey=config.getInitParameter("user");
		String errorJsp=config.getInitParameter("error");
		Object userObject=servletRequest.getSession().getAttribute(userKey);
		servletResponse.setHeader("Cache-Control","no-cache"); 
		servletResponse.setDateHeader("Expires", 0);
		servletResponse.setHeader("Pragma","no-cache");
		if(passRequest.contains(servletRequest.getServletPath())){
			chain.doFilter(servletRequest,servletResponse);
		}
		else if(userObject==null){
			if("/login.jsp".equals(servletRequest.getServletPath())){
				chain.doFilter(servletRequest,servletResponse);
			}
			else if("/priveliegeLogin.action".equals(servletRequest.getServletPath())){
				if(servletRequest.getParameter("userName")==null){
					servletResponse.sendRedirect(servletRequest.getContextPath()+errorJsp);
				}else{
					String userName=servletRequest.getParameter("userName");
					logger.info(getIpAddr(servletRequest)+">>try to login,userName:"+userName);
					chain.doFilter(servletRequest,servletResponse);
				}
			}else{
				servletResponse.sendRedirect(servletRequest.getContextPath()+errorJsp);
			}
		}else{
			chain.doFilter(servletRequest,servletResponse);
		}
	}

	@Override
	public void destroy() {
	}

	/* 获取客户端Ip */

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("http_client_ip");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		// 如果是多级代理，那么取第一个ip为客户ip
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
		}
		return ip;
	}
}
