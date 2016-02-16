package com.laipai.userManInfo.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.laipai.base.dao.IBaseDao;
import com.laipai.serviceInfo.pojo.LpService;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;

/**
 * 

 * @Description:TODO

 * @author:zhangfengjiao

 * @time:2014-12-15 下午1:30:04
 */
public interface IUserManInfoDao extends IBaseDao {
	public static final String IUSERMANINFODAO_NAME = "com.laipai.userManInfo.dao.IUserManInfoDao";

	public List<LpUser> queryAllUser();

	public void update(LpUser userInfoBean);

	public void save(LpUser userInfoBean);

	public LpUser queryUserById(String userId);

	public LpUser queryUserByName(String userName);
	
	public void deleteUserById(String userId);

	public void saveCamera(LpUser user);


	public void deleteCommentById(String commentId);

	public void deleteFollowById(String followId);

	public void deleteLikeById(String likeId);

	public List getstyleByuser(String userId);

	public List<LpUser> queryListByUserName(String account,Object... values);

	public List queryListByNickName(String sql);

	//public LpService queryById(String serviceId);


}
