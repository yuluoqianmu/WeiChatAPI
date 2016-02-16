package com.laipai.privilege.dao;

import java.util.List;

import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpUser;

public interface PrivilegeDao {
	
	public boolean change2Admin(String userId);
	
	public boolean addPrivilege(String[] resourceIds, String userId, LpUser operatpr);
	
	public List<String> getResourceByUserId(String userId);
	
}
