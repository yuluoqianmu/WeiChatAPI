package com.lyz.api.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tencent.Sign;
import com.tencent.SignParam;


public class WeChatShareServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(WeChatShareServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		SignParam param = Sign.signWithCache("wx68e026f2c32b6b87", "7820a45d13e61117fa124a61f939477e", "http://test.ilaipai.com/LaiPai/jsp/activity/ApplyClub.jsp");
    	String timestamp = param.getTimestamp();
    	String nonceStr = param.getNonceStr();
    	String signature = param.getSign();
    	
    	request.setAttribute("timestamp", timestamp);
    	request.setAttribute("nonceStr", nonceStr);
    	request.setAttribute("signature", signature);
    	
    	System.out.println("timestamp:"+param.getTimestamp());
    	System.out.println("nonceStr:"+param.getNonceStr());
    	System.out.println("signature:"+param.getSign());
    	
		request.getRequestDispatcher("/jsp/activity/ApplyClub.jsp").forward(request, response);
	}

}
