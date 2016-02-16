package com.laipai.serviceInfo.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;


import com.laipai.base.action.BaseAction;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.galaryManInfo.service.IGalleryService;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.orderInfo.pojo.LpOrder;
import com.laipai.serviceInfo.dto.ServiceInfoBean;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.serviceInfo.pojo.LpServiceDetail;
import com.laipai.serviceInfo.pojo.VLpService;
import com.laipai.serviceInfo.service.IServiceService;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:49:43
 */
@Controller("serviceAction")
public class ServiceAction extends BaseAction implements ModelDriven<ServiceInfoBean>{
	@Resource(name="serviceService")
	private IServiceService serviceService;
	
	@Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManService;
	
	private static String onStatus="on";
	private static String offStatus="off";
    public  ServiceInfoBean serviceInfoBean=new ServiceInfoBean();
    
    public ServiceInfoBean getModel() {
		return this.serviceInfoBean;
	}
	/**
	 * 
	
	 * @Description:服务管理——查询所有
	
	 * @return
	
	 * ServiceService
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-15 下午1:53:01
	 */
	public String queryAllService(){
		List list=serviceService.queryAllService(request);
		request.setAttribute("serviceInfo", list);
		return "list";
	}
	
	/**
	 * 
	
	 * @Description:服务管理——服务详情
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-17 下午9:08:35
	 */
	public String queryById(){
		try {
			String serviceId=request.getParameter("serviceId");
			LpService service=serviceService.queryById(serviceId);
			request.setAttribute("service", service);
			//判断是否是摄影师管理JSP进行服务详情查询
			String jsp=request.getParameter("JSP");
			if(StringUtils.isNotBlank(jsp)){
				request.setAttribute("cameraServiceJSP", "is");
				request.setAttribute("userId", service.getLpUser().getUserId());
			}else{
				request.setAttribute("cameraServiceJSP", "");
			}
			
			Set<LpGalary> lpGallaries= service.getLpGalaries();
			if(lpGallaries!=null&&lpGallaries.size()>0){
				
				for(LpGalary gallary:lpGallaries){
					String gallaryId=gallary.getGalaryId();
					int commentCount= serviceService.getCount(gallaryId);
					gallary.setCommentNumber(commentCount);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toServiceDetail";
	}
	/**
	 * 
	
	 * @Description:服务管理——新增服务
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-16 下午5:23:02
	 */
	public String addService(){
		serviceInfoBean.setCreateTime(new Timestamp(new Date().getTime()));
		serviceInfoBean.setDetailId(null);
		serviceService.addService(serviceInfoBean);
		//判断是否实在摄影师管理-服务中新增服务
		if("is".equals(request.getSession().getAttribute("userServiceJSP"))){
			request.getSession().removeAttribute("userServiceJSP");
			String userId=serviceInfoBean.getUserId();
			List<VLpService> list=serviceService.queryServiceByUserId(request,userId);
			request.setAttribute("serviceInfo", list);
			//返回摄影师管理-服务
			return "queryUserServiceList";
		}
		//返回服务管理-服务列表
		return "queryList";
	}

	/**u
	 * 
	
	 * @Description:服务管理——修改前查询
	
	 * @return
	
	 * ServiceService
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-15 下午6:46:45
	 */
	public String modifyService(){
		LpService service=serviceService.queryById(request.getParameter("serviceId"));
		String userId=service.getLpUser().getUserId();
		List<LpStyle> styleSet=service.getLpServiceDetail().getLpStyle();
	 	String styleId="";
	 	for(LpStyle style:styleSet){
	 		styleId+=style.getStyleId()+";";
	 	}
        List<LpStyle> style=serviceService.getStyle(userId);
        List<LpUser> cameraList=serviceService.queryAllCamera();
        request.setAttribute("cameraList", cameraList);
        request.setAttribute("styleId", styleId);
        request.setAttribute("style", style);
        request.setAttribute("clothes", service.getLpServiceDetail().getClothes());
        request.setAttribute("facepaint",service.getLpServiceDetail().getFacepaint());
        request.setAttribute("priceUnit", service.getLpServiceDetail().getPriceUnit());
        request.setAttribute("modifyServiceInfo", service);
        //判断是否是在摄影师管理JSP中进行服务修改
        String jsp=request.getParameter("JSP");
        if(StringUtils.isNotBlank(jsp)){
        	request.setAttribute("cameraServiceJSP", "is");
        }else{
        	request.setAttribute("cameraServiceJSP", "");
        }
        //返回服务管理-修改页面
		return "toModify";
	}
	
	/**
	 * 
	
	 * @Description:服务管理——修改后提交
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	 * @throws UnsupportedEncodingException 
	
	 * @time:2014-12-17 上午11:56:22
	 */
	public String modifyCommitService() throws UnsupportedEncodingException{
		serviceService.modifyService(serviceInfoBean);
		//判断是否是在摄影师管理JSP中进行服务修改提交
		String jsp=serviceInfoBean.getCameraServiceJSP();
		if(StringUtils.isNotBlank(jsp)){
			request.setAttribute("serviceId", serviceInfoBean.getServiceId());
			//根据userID查询该摄影师的所有服务
			String cameraName=serviceInfoBean.getCameraName();
			LpUser user=userManService.queryUserByName(cameraName);
			String userId=user.getUserId();
			List<VLpService> list=serviceService.queryServiceByUserId(request,userId);
			request.setAttribute("serviceInfo", list);
			request.setAttribute("userId",userId);
			//返回摄影师管理-服务
			return "queryUserServiceList";
		}
		//返回服务管理-服务列表
		return "queryList";
	}
	/**
	 * 
	
	 * @Description:服务管理——删除
	
	 * @return
	
	 * ServiceService
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-15 下午6:47:12
	 */
	public String deleteService(){
		String serviceId_All=request.getParameter("serviceId");
		serviceService.deleteById(serviceId_All);
		//判断是否是在摄影师管理JSP中删除服务
		String jsp=request.getParameter("JSP");
		if(StringUtils.isNotBlank(jsp)){
			//根据userID查询该摄影师的所有服务
			String userId=request.getParameter("userId");
			List<VLpService> list=serviceService.queryServiceByUserId(request,userId);
			request.setAttribute("serviceInfo", list);
			request.setAttribute("userId",userId);
			//返回摄影师管理-服务
			return "queryUserServiceList";
		}
		//返回服务管理-服务列表
		return "queryList";
		
	}

	/**
	 * 
	
	 * @Description:服务管理——上架
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-17 下午7:55:42
	 */
	public String onService(){
		String serviceId=request.getParameter("serviceId");
		serviceService.on_off_Service(serviceId,onStatus);
		//判断是否在服务管理-服务列表上架服务
		String jsp=request.getParameter("serviceListJSP");
		if(StringUtils.isNotBlank(jsp)){
			//返回服务列表
			return this.queryAllService();
		}
		//服务详情页或者摄影师管理中上架服务
		return this.queryById();
	}
	
	/**
	 * 
	
	 * @Description:服务管理——下架
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-17 下午8:14:10
	 */
	public String offService(){
		String serviceId=request.getParameter("serviceId");
		serviceService.on_off_Service(serviceId,offStatus);
		//判断是否在服务管理-服务列表下架服务
		String jsp=request.getParameter("serviceListJSP");
		if(StringUtils.isNotBlank(jsp)){
			//返回服务列表
			return this.queryAllService();
		}
		//服务详情页或者摄影师管理中下架服务
		return this.queryById();
	}
	
	/**
	 * 
	
	 * @Description:服务管理——所有风格查询
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-18 上午10:48:34
	 */
	public String getStyle(){
		String userId=request.getParameter("userId");
		//查询所有摄影师
		List<LpUser> cameraList=serviceService.queryAllCamera();
		request.setAttribute("cameraList", cameraList);
		request.setAttribute("userId",userId );
		//摄影师管理-服务中新增服务前，风格查询
		if(StringUtils.isNotBlank(userId)){
			LpUser user=userManService.queryUserById(userId);
			List<LpStyle> list=serviceService.getStyle(user.getUserId());
			request.setAttribute("styleInfo", list);
			request.setAttribute("user", user);
			request.getSession().setAttribute("userServiceJSP", "is");
			return "toAddUserService";
		}else{
			List<LpStyle> list=serviceService.getStyle("");
			request.setAttribute("styleInfo", list);
		}	
		//服务管理-新增服务前，风格查询
		return "toAdd";
	}
	
	/**
	 * 
	
	 * @Description:服务管理——删除作品集
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-23 下午1:46:17
	 */
	public String deleteGalleryById(){
		//作品集信息
		String galaryId=serviceInfoBean.getGalaryId();
		LpGalary galary=galleryService.getgalleryByID(galaryId);
		galleryService.deleteGalleryById(galaryId);
		
		//服务
		String serviceId=serviceInfoBean.getServiceId();
		LpService service=serviceService.queryById(serviceId);
		service.getLpGalaries().remove(galary);
		request.setAttribute("service", service);
		//判断是否在摄影师管理-服务页面
		String jsp=request.getParameter("cameraServiceJSP");
		if(StringUtils.isNotBlank(jsp)){
			request.setAttribute("cameraServiceJSP", "is");
		}
		return "toServiceDetail";
	}
	
	/**
	 * 
	
	 * @Description:服务管理——判断摄影师是否存在
	
	
	 * void
	
	 * @exception:
	
	 * @author: zhangfengjiao
	 * @throws UnsupportedEncodingException 
	
	 * @time:2014-12-30 上午9:06:12
	 */
	public void queryUserByName() throws UnsupportedEncodingException{
		String cameraName=new String(request.getParameter("cameraName").getBytes("ISO8859-1"),"GBK");  
		LpUser user=serviceService.queryUserByName(cameraName);
		if(user==null){
			getPrintWriter().write("no");
		}
	}
	private JSONArray resultList;
	
	public JSONArray getResultList() {
		return resultList;
	}
	public void setResultList(JSONArray resultList) {
		this.resultList = resultList;
	}
	/**
	 * 
	
	 * @Description:服务管理-添加服务时得到用户自己的风格
	
	
	 * void
	
	 * @exception:
	
	 * @author: zhangfengjiao
	
	 * @time:2014-12-23 上午11:48:21
	 */
	public String getUserStyle(){
		String userName=request.getParameter("userName");
		LpUser user=userManService.queryUserByName(userName);
		List<LpStyle> userStyleList=serviceService.getUserStyle(user.getUserId());
		List<JSONObject> list=new ArrayList<JSONObject>();
		for(LpStyle style:userStyleList){
			JSONObject json=new JSONObject();
			json.put("styleId", style.getStyleId());
			json.put("styleName", style.getStyleName());
			list.add(json);
		}
		try {
			resultList=JSONArray.fromObject(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
