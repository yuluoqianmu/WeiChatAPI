package com.laipai.userManInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.FileUploadUtils;
import com.laipai.base.util.SpringContextUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.privilege.service.PrivilegeService;
import com.laipai.userInfo.pojo.UserInfo;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserFollowService;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 关注
 * @author xbl
 *
 */
@Service("userFollowAction")
public class UserFollowAction extends BaseAction {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserFollowAction.class);

	@Autowired
	private IUserFollowService userFollowService;
	
	private String result;  

	
	public String getUserId() {
		return userId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getCameraId() {
		return cameraId;
	}



	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}



	public String userId;
	public String cameraId;
	public String followId;

	
	
	public String followList()
	{

		String hql = "from LpFollowView";
		/*
		if(userId != null && userId.trim().length() > 0)
		{
			hql = hql + " where user_id = '" + userId + "'";
		}
		
		if(cameraId != null && cameraId.trim().length() > 0)
		{
			if(userId != null && userId.trim().length() > 0)
			{
				hql = hql + " and camera_id = '" + cameraId + "'";
			}
			else
			{
				hql = hql + " where camera_id = '" + cameraId + "'";
			}
		}
		*/
		
		try {
			List<LpFollowView> follows = userFollowService.querylistForPage(request, hql, 20);
			
			logger.info("search LpFollowView hql = " + follows + ", result size = " + (follows != null ? follows.size():0));
			
			request.setAttribute("followList", follows);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("search LpFollowView error", e);
			
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String followDelete()
	{
		
		LpUser user = (LpUser) request.getSession().getAttribute("user");
		
		String[] followIds = request.getParameterValues("followId");
		
		logger.info("deleteFollow followIds = " + Arrays.asList(followIds));
		
		userFollowService.deleteFollowByIds(followIds, user);
		
		return SUCCESS;
	}

	public String searchUserInfoByUserName(){
		String userName = request.getParameter("userName");

		logger.info("userName = " + userName);
		
		StringBuffer sb = new StringBuffer();
		
		try {

			List<LpUser> users = userFollowService.querylistForPage(request, "from LpUser where user_name like '%" + userName + "%'", 20);
			logger.info("users = " + (users==null?0:users.size()));
			//net.sf.json.JSONException: There is a cycle in the hierarchy!
			//JSONArray obj = JSONArray.fromObject(users);
			if(users != null && users.size() > 0)
			{
				List<Map> ul = new ArrayList<Map>();
				
				for(LpUser u : users)
				{
					/*
					Map m = new HashMap();
					m.put("userId", u.getUserId());
					m.put("userName", u.getUserName());
					ul.add(m);
					*/
					//名称|值 + 回车
					sb.append(u.getUserName() + "|" + u.getUserId() + "\n");
					
					
				}
				
				//JSONArray obj = JSONArray.fromObject(ul);
				//String jsonResult = "{" + obj.toString() + "}";
				//logger.info("json users = " + jsonResult);
				try {
					
//					getResponse().setCharacterEncoding("UTF-8");
//					PrintWriter pw = getResponse().getWriter();
//					pw.println("{" + obj.toString() + "}");
//					pw.flush();
//					pw.close();
					
					/*
					response.setContentType("application/json;charset=UTF-8");     
					response.setCharacterEncoding("UTF-8");     
					    
					// 设置浏览器不要缓存     
					response.setHeader("Pragma", "No-cache");     
					response.setHeader("Cache-Control", "no-cache");     
					response.setDateHeader("Expires", 0);  
					response.getWriter().println(jsonResult);  	
					response.getWriter().flush();
					response.getWriter().close();
					*/
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			
			getResponse().setCharacterEncoding("UTF-8");
			PrintWriter pw = getResponse().getWriter();
			pw.println(sb.toString());
			pw.flush();
			pw.close();
			
			/*
			response.setContentType("application/json;charset=UTF-8");     
			response.setCharacterEncoding("UTF-8");     
			    
			// 设置浏览器不要缓存     
			response.setHeader("Pragma", "No-cache");     
			response.setHeader("Cache-Control", "no-cache");     
			response.setDateHeader("Expires", 0);  
			response.getWriter().println(jsonResult);  	
			response.getWriter().flush();
			response.getWriter().close();
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("response string = " + sb.toString());
		
		return SUCCESS;
	}
	
	/**
	 * 添加和编辑关注
	 * @return
	 */
	public String editFollow()
	{
		logger.info("LpFollow followId = " + followId + ", userId = " + userId + ", cameraId = " + cameraId);
		String followTime = request.getParameter("followTime");
		
		//userFollowService.saveFollow(followId, userId, cameraId, followTime, getCurrentUser());
		userFollowService.saveFollow2(userId, cameraId);
		
		return SUCCESS;
	}

	public String userFollowGetFollowById()
	{
		String followId_ = request.getParameter("followId"); 
		logger.info("followId = " + followId_);
		try
		{
			LpFollowView f = userFollowService.getFollowViewById(followId_);
			logger.info(" LpFollowView = " + f);
			
			Map m = new HashMap();
			m.put("followId", f.getFollowId());
			m.put("userId", f.getUserId());
			m.put("userName", f.getUserName());

			m.put("cameraId", f.getCameraId());
			m.put("cameraName", f.getCameraName());
			m.put("followTime", DateUtils.dateToString(f.getFollowTime()));

			logger.info(" LpFollowView map = " + m);
			
			JSONObject jo = JSONObject.fromObject(m);
			logger.info(" LpFollowView json = " + jo.toString());
			this.result = jo.toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	

  public void checkFollow() throws IOException{
	  HttpServletResponse resp=ServletActionContext.getResponse();
      resp.setContentType("text/html;charset=utf-8");
      resp.setCharacterEncoding("utf-8");
      PrintWriter out = resp.getWriter();

	 /* String userId=request.getParameter("userId");
	  String cameraId=request.getParameter("cameraId");*/
	  String result="";
	  List<LpFollow> followlist=userFollowService.checkFollow(userId,cameraId);
	  if(followlist!=null&&followlist.size()>0){
		  
		  result="YES";
	  }else{
		  
		  result="NO";  
	  }
	  try{
			out.print(result);
			
		}
		catch(Exception e){
			logger.error(e);		
		}finally {
	        out.flush();
	        out.close();
	    }
	  
  } 
  
  public String deletefollow(){
	  
	  
	  String[] ids = request.getParameterValues("followId");
		if(ids != null && ids.length > 0)
		{
			for(int i = 0; i < ids.length; i++)
			{
				String followId = ids[i];
				userFollowService.deleteFollow(followId);
				
			}
		}
		

		
		return SUCCESS;
  }

}

