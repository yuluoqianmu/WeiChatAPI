package com.lyz.api.servlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.laipai.util.BaseTypeUtil;
import com.lyz.api.bean.BaseReq;
import com.lyz.api.bean.CodeUtil;
import com.lyz.api.bean.InitReq;
import com.lyz.api.bean.InitResp;
import com.lyz.api.config.SystemConfig;
import com.lyz.db.bean.LpAppNotify;
import com.lyz.db.bean.LpUser;
import com.lyz.service.AppNotifyService;
import com.lyz.service.UserService;
import com.lyz.util.HeadIniterUtil;
import com.lyz.util.MD5Generator;

/**
 * 初始化接口负责客户端启动时一些系统参数的设定
 * 1、用户是否处在登录状态；
 * 2、是否需要升级；
 * 3、加密key
 * 
 * CREATE TABLE `laipai`.`lp_app_notify`(  
  `notify_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `app_version` VARCHAR(40) COMMENT '客户端版本',
  `app_type` VARCHAR(20) COMMENT '客户端类型：iphone,ipad,androidphone,andoirdpad,winpad,winphone',
  `notify_content` VARCHAR(200) COMMENT '通知内容',
  `up_type` INT(5) COMMENT '升级类型：0-不升级；1-建议升级；2-强制升级',
  `url` VARCHAR(100) COMMENT '下载地址',
  `effective` INT(3) DEFAULT 1 COMMENT '是否有效：0-无效；1-有效',
  `notify_type` INT(3) DEFAULT 0 COMMENT '通知类型：0-升级信息',
  PRIMARY KEY (`notify_id`)
) ENGINE=INNODB;

 * @author luyongzhao
 *
 */
public class InitServlet extends BaseServlet{
	
	private static final Logger logger = Logger.getLogger(InitServlet.class);
	

	@Override
	public Class getParamClass() {
		return InitReq.class;
	}

	@Override
	public Object handler(BaseReq param, HttpServletRequest request) {
		
		
		InitReq req = (InitReq)param;
		
		InitResp resp = new InitResp(CodeUtil.SUCCESS);
		resp.setKey(SystemConfig.key);
		/*填充版本更新的相关信息*/
		LpAppNotify notify = AppNotifyService.getAppUpNotify(req.getPt());
		/*如果有更新信息，并且大于客户端当前版本，则赋值*/
		if(notify != null && req.getVer().compareTo(notify.getAppVersion())<0){
			
			resp.setChanges(notify.getNotifyContent());
			resp.setnVer(notify.getAppVersion());
			resp.setUpType(notify.getUpType());
			resp.setUrl(notify.getUrl());
		}
		/*如果用户登录，则添加用户相关信息*/
		if(req.getUid()!=null && !"".equals(req.getUid().trim())){
			
			LpUser user = UserService.getUserInfo(req.getUid());
			if(user != null){
				
				resp.setToken(UserService.getToken(user.getUserName(), user.getUserPassword()));
				/*数据库里边0表示登录*/
				resp.setLogin(user.getLoginStatus()==0);
				resp.setUserType(user.getUserType());
			}
		}
		
		return resp;
		

		
		
//		return dataService.getUpdatedInfo(req.getKey(), req.getVer());				
	}
	
	

	@Override
	public String getNeed2Md5(BaseReq param) {
		
		return null;
	}

	@Override
	public String getTag() {
		return "init";
	}

	@Override
	public String getStringToFile(BaseReq req, HttpServletRequest request) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append(request.getRemoteAddr()).append("\t")
		.append(BaseTypeUtil.getStrDateTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")).append("\t")
		.append(req.getKey()).append("\t")
		.append(req.getOpenudid()).append("\t")
		.append(req.getPt()).append("\t")
		.append(req.getPyId()).append("\t")
		.append(req.getR()).append("\t")
		.append(req.getT()).append("\t")
		.append(req.getUid()).append("\t")
		.append(req.getVer()).append("\n");
		
		return builder.toString();
	}

	
	
}
