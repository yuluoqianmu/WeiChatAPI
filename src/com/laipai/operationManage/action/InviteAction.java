package com.laipai.operationManage.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ExceptionMapping;
import org.apache.struts2.convention.annotation.ExceptionMappings;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.laipai.base.action.BaseAction;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.SpringContextUtil;
import com.laipai.cameraCheck.dto.AppformData;
import com.laipai.cameraCheck.pojo.LpInvite;
import com.laipai.operationManage.service.IOperationManagerService;
import com.laipai.userInfo.dto.UserInfoBean;
import com.laipai.userInfo.pojo.UserInfo;
import com.laipai.userInfo.service.IUserService;
import com.laipai.userManInfo.pojo.LpFeedbackView;
import com.opensymphony.xwork2.ModelDriven;


/*@Results( { @Result(name = "success", location = "/mainFrame.jsp"),
        @Result(name = "error", location = "/hello.jsp") })
@ExceptionMappings( { @ExceptionMapping(exception = "java.lange.RuntimeException", result = "error") }) */
@Controller("inviteAction")
public class InviteAction extends BaseAction implements ModelDriven<LpInvite>{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(InviteAction.class);
    
	@Autowired
	private IOperationManagerService operationManagerService;
	@Autowired
	private IBaseDao baseDao;
	
	public LpInvite  invite=new LpInvite();
	public LpInvite getModel() {
		return this.invite;
	}
	private String status;//用来查询邀请码的状态
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = request.getParameter("status");
	}
	/**
	 * 
	
	 * @Description:查询所有邀请码
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2015-1-20 上午11:34:14
	 */
	public String queryAllCode(){
		//status:0已发未用  1:已发已用  2:未发未用
		try{
			 if(status==null || ("").equals(status))
			 {
				 List<LpInvite> list=operationManagerService.queryCodeBypage(request);
				 request.setAttribute("invitelist",list );
			 }
			 else
			 {
				 List<LpInvite> list=operationManagerService.queryCodeBypage(request,status);
				 request.setAttribute("invitelist",list );
			 }
			 }catch(Exception e){			 
				 logger.error(e); 
			 }
		 int sendUnused = 0;
		 int sendUsed = 0;
		 int unsendUnused = 0;
		 sendUnused = operationManagerService.getSendUnused();
		 sendUsed = operationManagerService.getSendUsed();
		 unsendUnused = operationManagerService.getUnsendUnused();
		 request.setAttribute("sendUnused", sendUnused);
		 request.setAttribute("sendUsed", sendUsed);
		 request.setAttribute("unsendUnused", unsendUnused);
		return "codelist";
	}
	/**
	 * 
	
	 * @Description:按位数和个数生成邀请码
	
	 * @return
	
	 * String
	
	 * @exception:
	
	 * @author: lxd
	
	 * @time:2015-1-20 上午11:34:35
	 */
	public String addInviteCode(){
		
		String codeDigits =request.getParameter("codeDigits");
		String codeNumber=request.getParameter("codeNumber");
		int Digits=0;
		int Number = 0;
		if(StringUtils.isNotBlank(codeDigits)){
		 Digits=Integer.parseInt(codeDigits);
		 if(Digits>6){
			 Digits=6 ;
			 
		 }
		}
		if(StringUtils.isNotBlank(codeNumber)){
		  Number=Integer.parseInt(codeNumber);
		  if(Number>5000){
			  Number=5000;  
		  }
		}
		 Set<String> newSet=new HashSet<String>();
		for(int i=0;i<Number;i++){
			String code=getRandomString(Digits);
			newSet.add(code);
		}
		
	operationManagerService.saveInvite(newSet);	
		return "toActionlist";
	}
	
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 } 
	
	
	public String sendCode(){
		 String codeId=request.getParameter("codeId");
		try{
			LpInvite invite =(LpInvite) baseDao.getObjectById(LpInvite.class, codeId);
			invite.setIsSend(1);
			invite.setSendtime(new Timestamp(new java.util.Date().getTime()));
			baseDao.updateObject(invite);
			 }catch(Exception e){			 
				 logger.error(e); 
			 }
		
		return SUCCESS;
	}
}

