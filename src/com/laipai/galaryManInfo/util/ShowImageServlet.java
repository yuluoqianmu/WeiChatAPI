package com.laipai.galaryManInfo.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.laipai.galaryManInfo.action.GalleryManageAction;
import com.laipai.galaryManInfo.dto.SimpleImage;

public class ShowImageServlet extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Map<String,SimpleImage> images=(Map<String, SimpleImage>) req.getSession().getAttribute(GalleryManageAction.TEMPIMAGES);
		if(images==null){
			return;
		}
		String partNumber=req.getParameter("partNumber");
		SimpleImage img=images.get(partNumber);
		if(img==null){
			return;
		}
		resp.setContentType(img.getContentType());
		try{
			byte[] b=img.getImg();
			resp.getOutputStream().write(b);
			resp.flushBuffer();
			resp.getOutputStream().close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
