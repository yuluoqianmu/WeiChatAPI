package com.laipai.cameraManInfo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import com.laipai.base.service.IBaseService;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpLoginHis;
import com.laipai.userManInfo.pojo.LpUser;

public interface ICameraManService extends IBaseService {
	public static final String SERVICE_NAME = "com.laipai.cameraManInfo.service.imple.CameraManServiceImpl";

	List getallcity();

	List getSysStyle();

	void saveCameraman(LpUser user, String cityId, String[] styleId);

	List<LpUserBean> queyallBypage(HttpServletRequest request);

	List<LpStyle> getstyleByuser(String userId);

	List<LpStyle> getallStyle();

	void deleteUserById(String userId);
	
	List<LpLoginHis> getLoginHis(String userId, int pageSize, int page);


}
