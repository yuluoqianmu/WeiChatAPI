package com.laipai.app.process;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理请求
 * 
 * @author lts
 */
public interface ProcessRequest {

	String process(HttpServletRequest request) throws IOException;

}
