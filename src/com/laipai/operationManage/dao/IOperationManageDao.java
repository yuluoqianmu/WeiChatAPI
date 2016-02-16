package com.laipai.operationManage.dao;

import java.util.List;

import com.laipai.base.dao.IBaseDao;
import com.laipai.operationManage.pojo.LpCity;
import com.laipai.operationManage.pojo.LpStyle;
import com.laipai.userManInfo.pojo.LpUser;

public interface IOperationManageDao extends IBaseDao {
 public static String DAO_NAME="com.laipai.operationManage.dao.impl.OperationManageDaoImpl";

 public List queryAllStyle();
 
 public List<LpCity> queryAllCity() ;
 
 public List<LpCity> getAllCity(int nowPage, int pageSize);
 
 public void deleteCity(String cityId);
 
 public void saveOrUpdateCity(LpCity lpCity);
 
 public void changeStatus(boolean status, String cityId);
 
 public long getCount(int pageSize, Class clazz);
	
 public long countSum(Class clazz);
 
 public void deleteStyle(String styleId);
 
 public List<LpStyle> getAllStyle(int nowPage, int pageSize);
 
 public void addStyle(LpStyle lpStyle);
 
 public LpStyle findTheStyle(String styleId);
 
 public void updateStyle(LpStyle lpStyle);
 
 public LpCity findTheCity(String cityName);

public List<LpStyle> queryAllEnableStyle();

public List<LpCity> getAllOnlineCity(String sql);

public List<LpUser> getAllMan(String cityId);

public void updateCityLocation(String cityId, int newLocation);

public void updateStyleLocation(String styleId, int newLocation);

public void changeStyleStatus(String styleId, int status);

public List<LpUser> findAllStyleMan(String styleId);
}
