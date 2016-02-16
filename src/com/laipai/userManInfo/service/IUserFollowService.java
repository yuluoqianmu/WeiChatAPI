package com.laipai.userManInfo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.laipai.base.service.IBaseService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpUser;
/**
 * 用户关注的服务类

 * @Description:用户关注的服务类接口

 * @author:xbl

 * @time:2014-12-16 下午4:03:54
 */
public interface IUserFollowService extends IBaseService{

	public boolean deleteFollowByIds(String[] followIds, LpUser operator);
	
	public boolean saveFollow(String followId, String userId, String cameraId, String followTime, LpUser operator);
	
	public LpFollowView getFollowViewById(String followId);

	public List<LpFollowView> getFollowByuserId(String userId);

	public boolean saveFollow2(String userId, String cameraId);

	public List<LpFollow> checkFollow(String userId, String cameraId);

	public void deleteFollow(String followId);
}
