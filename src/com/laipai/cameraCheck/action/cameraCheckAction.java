package com.laipai.cameraCheck.action;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.util.LaipaiConstants;

import com.laipai.cameraCheck.dto.AppformData;
import com.laipai.cameraCheck.pojo.LpCameramanAppform;
import com.laipai.cameraCheck.service.ICameraCheckservice;
import com.laipai.cameraManInfo.service.ICameraManService;
import com.laipai.img.ImgUtil;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;

import com.opensymphony.xwork2.ModelDriven;



@Controller("checkAction")
public class cameraCheckAction extends BaseAction implements ModelDriven<LpCameramanAppform>{
	private static final Logger logger = Logger.getLogger(cameraCheckAction.class);
	LpCameramanAppform appform =new LpCameramanAppform();
	
	@Resource
	private ICameraCheckservice cameraCheckservice;
	@Resource(name=IUserManInfoService.SERVICE_NAME)
	private IUserManInfoService userManInfoService; 
	@Resource(name=ICameraManService.SERVICE_NAME)
	private  ICameraManService cameraManService;
	@Override
	public LpCameramanAppform getModel() {
		return appform;
	}
	
	 private String formId;
	 private String status;
	 private String userId;
	 
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = request.getParameter("status");
	}
	/**
	 * 
	
	 * @Description:分页查询申请信息
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-25 上午11:05:41
	 */
	public String queryAllAppForm(){
		
		try {
			//String checkStatus = request.getParameter("checkStatus");
			if(status==null || status=="")
			{
				List<LpCameramanAppform> appformList=cameraCheckservice.queyallBypage(request);
				request.setAttribute("appformList", appformList);
			}
			else 
			{
				//待审核
				List<LpCameramanAppform> appformList=cameraCheckservice.queyallBypage(request,status);
				request.setAttribute("appformList", appformList);
			}
			
			AppformData appformData=cameraCheckservice.getAppfromDate();
			request.setAttribute("appformData",appformData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "appfromlist";
	}
	/**
	 * 
	
	 * @Description:跳转至申请详情页
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-25 下午4:53:42
	 */
	public  String queryById(){
		String appformId=request.getParameter("appformId");
		LpCameramanAppform appform= cameraCheckservice.getAppformById(appformId);
		if(appform!=null){
				String photoOne=appform.getPhotoOne();
				String photoTwo=appform.getPhotoTwo();
				String photoThree =appform.getPhotoThree();
				if(photoOne!=null){
					appform.setPhotoOne(ImgUtil.getImgUrl(photoOne));	
				}if(photoTwo!=null){
					
					appform.setPhotoTwo(ImgUtil.getImgUrl(photoTwo));	
				}
                if(photoThree!=null){
					
                	appform.setPhotoThree(ImgUtil.getImgUrl(photoThree));		
				}
				
                 LpUser user= appform.getLpUser();
                 String IdcardImg=user.getIdCardImage();
                 if(IdcardImg!=null){
                	 appform.setIdcardImg(ImgUtil.getImgUrl(IdcardImg)); 
                	 
                 }
            	 String userId=user.getUserId();
            	 List<LpStyle> listStyle=new ArrayList<LpStyle>();
                 listStyle=cameraManService.getstyleByuser(userId);
                 request.setAttribute("listStyle", listStyle);
			
		}
		request.setAttribute("appform",appform);
		return "checkappform";		
	}
	/**
	 * 
	
	 * @Description:通过用户的申请
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-25 下午6:16:17
	 */
	public String passAppform(){
		String userId=this.getUserId();
		LpUser user= userManInfoService.queryUserById(userId);
		String appformId=this.getFormId();
		String statusString=this.getStatus();
		String rejectReason =request.getParameter("rejectReason");
		int status =Integer.parseInt(statusString);
		//根据Id查出申请
	    LpCameramanAppform appform= (LpCameramanAppform) cameraCheckservice.getappformById(appformId);
		//更改状态。添加认证是时间
	    appform.setCheckTime(new Timestamp(new java.util.Date().getTime()));
	    appform.setCheckStatus(status);
	     if(status==-1){
	    	 user.setUserType(0);
	    	 appform.setRejectReason(rejectReason);
	     }else{
	    	 user.setValidTime(new Timestamp(new java.util.Date().getTime()));
	    	 user.setUserType(1); 
	    appform.setEffectiveDate(new Timestamp(new java.util.Date().getTime()));
	     }
		cameraCheckservice.saveAppformLog(appform,user);
		return SUCCESS; 
		
	}
	/**
	 * 
	
	 * @Description:根据用户Id查询用户的申请记录
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2014-12-26 上午11:18:40
	 */
	public String queryappformLog(){
		String userId= request.getParameter("userId");
		List<LpCameramanAppform> appforlist=new ArrayList<LpCameramanAppform>();
		appforlist=cameraCheckservice.getappformHisByuserId(userId);
		request.setAttribute("appforlist", appforlist);
		return "appformlog";
	}
	
	
	/**
	 * 
	
	 * @Description:删除申请
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2015-1-4 下午7:24:04
	 */
	public String deleteAppform(){
		String[] ids = request.getParameterValues("appformId");
		if(ids != null && ids.length > 0)
		{
			for(int i = 0; i < ids.length; i++)
			{
				String appformId = ids[i];
				cameraCheckservice.deleteAppformById(appformId);
				
			}
		}
		return "toappformlist";

		
	}
	


	
}

