package com.laipai.operationManage.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.laipai.base.service.IBaseService;
import com.laipai.cameraCheck.dto.AppformData;
import com.laipai.cameraCheck.pojo.LpInvite;
import com.laipai.galaryManInfo.dto.VLpNewintroduceBean;
import com.laipai.galaryManInfo.pojo.LpNewintroduce;
import com.laipai.galaryManInfo.pojo.VLpNewintroduce;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.operationManage.pojo.VLpStyleListBack;
import com.laipai.userManInfo.pojo.LpUser;

public interface IOperationManagerService extends IBaseService {
	public List<LpCity> getAllCity(int nowPage, int pageSize);
	public List<LpCity> findLpCityList(HttpServletRequest request,String hql,int pageSize);
	public List<LpCity> getAllCity();
	public void deleteCity(String cityId);
	
	public void saveOrUpdate(LpCity lpCity);
	
	public void changeStatus(boolean status, String cityId);
	
	public long getCount(int pageSize, Class clazz);
	
	public long countSum(Class clazz);
	
	public void deleteStyle(String styleId);
	
	public List<LpStyle> getAllStyle(int nowPage, int pageSize);
	
	public void addStyle(LpStyle lpStyle);
	
	public LpStyle findTheStyle(String styleId);
	
	public void updateStyle(LpStyle lpStyle);
	
	public LpCity findTheCity(String cityName);

	public List<LpStyle> getallEnableStyle();

	public List<LpCity> getAllOnlineCity();

	public LpCity getCityById(String cityId);
	
	public List<LpUser> getAllMan(String cityId);


	public List<LpInvite> queryCodeBypage(HttpServletRequest request);

	public List<LpInvite> queryCodeBypage(HttpServletRequest request,String status);
	public LpInvite getIniBycode(String string);

	public void saveInvite(Set<String> newSet);
	
	public void updateCityLocation(String cityId, int newLocation);
	
	public void updateStyleLocation(String styleId, int newLocation);
	
	public void changeStyleStatus(String styleId, int status);
	
	public List<LpUser> findAllStyleMan(String styleId);

	public List<VLpNewintroduce> queryHomePageIntroduce(HttpServletRequest request) throws Exception;

	public void updateArticleOnline(String introduceId, String status);

	public void deleteArticle(String introduceId);

	public VLpNewintroduce getArticleViewDetail(String introduceId);

	public void saveArticle(VLpNewintroduceBean introduceBean);

	public boolean hasOtherOnline() throws Exception;
	
	public List<VLpStyleListBack> getAllStyleByView(HttpServletRequest request);
	public int getSendUnused();
	public int getSendUsed();
	public int getUnsendUnused();

}
