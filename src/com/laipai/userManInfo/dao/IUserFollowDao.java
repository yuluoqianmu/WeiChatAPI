package com.laipai.userManInfo.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.laipai.base.dao.IBaseDao;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:xbl

 * @time:2014-12-15 下午1:30:04
 */
public interface IUserFollowDao extends IBaseDao {

	public boolean deleteFollowByIds(String[] followIds, LpUser operator);

	public List<LpFollowView> getFollowbyUserId(String userId);

	public List<LpFollowView> queryFansByuserId(String string);
}
