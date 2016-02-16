package com.laipai.userManInfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.laipai.base.service.IBaseService;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFeedback;
import com.laipai.userManInfo.pojo.LpFeedbackView;
import com.laipai.userManInfo.pojo.LpFollow;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;
/**
 * 用户管理的服务类

 * @Description:用户管理的服务类接口

 * @author:lxd

 * @time:2014-12-16 下午4:03:54
 */
public interface IUserManInfoService extends IBaseService{
	public static final String SERVICE_NAME = "com.laipai.userManInfo.service.IUserManInfoService";
	public List<LpUser> queryAll();
	public List<LpUserBean> queryAllBypage(HttpServletRequest request);
	public void saveOrUpdateser(LpUser userInfoBean);
	public LpUser queryUserById(String userId);
	public LpUser queryUserByName(String userName);
	public void deleteUserById(String mmsBillId);
	public List<LpComment> queryComment(HttpServletRequest request,String userId);
	public void deleteCommentById(String commentId);
	public List<LpFollow> queryFollow(HttpServletRequest request,String userId);
	public void deleteFollowById(String followId);
	public List<LpLike> queryLike(HttpServletRequest request,String userId);
	public void deleteLikeById(String likeId);
	public List<LpGalary> queryGalary(HttpServletRequest request,String userId);
	public List<LpFollow> queryFans(HttpServletRequest request, String cameraId);
	public String checkUser(String userName);
	public List<String> getUserAccount(String account, String userType);
	public List<LpFeedbackView> queryFeedBackBypage(HttpServletRequest request);
	public List queryUserByNickName(String sql);
	public int getCount(String string);
}
