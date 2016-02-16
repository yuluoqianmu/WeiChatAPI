package com.laipai.laiPaiClubInfo.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.Global;
import com.laipai.app.common.JSONConvertUtil;
import com.laipai.app.common.JSONTools;
import com.laipai.app.common.TokenUtil;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.dao.IMobileDeviceDAO;
import com.laipai.base.pojo.MobileDevice;
import com.laipai.base.util.DateUtils;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.pojo.LpGalary;
import com.laipai.img.ImgUtil;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.laiPaiClubInfo.dto.LpClubShowBean;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.laiPaiClubInfo.pojo.VLpClub;
import com.laipai.logs.pojo.LpCamermanLog;
import com.laipai.logs.pojo.LpClubLog;
import com.laipai.userManInfo.dto.LpUserBean;
import com.laipai.userManInfo.pojo.LpComment;
import com.laipai.userManInfo.pojo.LpFollowView;
import com.laipai.userManInfo.pojo.LpLike;
import com.laipai.userManInfo.pojo.LpUser;
import com.laipai.userManInfo.service.IUserManInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * 作品集展示
 * 
 * @author zhh
 */
@NotLogin
@Service("LaiPaiClubShowExecutorImpl")
public class LaiPaiClubShowExecutorImpl implements RequestExecutor {

	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		
		//获取服务器域名
//		String serverName = json.getString("serverName");
		String page=JSONTools.getString(json,"page");
//		String userId=JSONTools.getString(json,"userId");
//		String machineId=JSONTools.getString(json,"imei");
/*		List<VLpClub> list = baseDao.queryListObjectAll("from VLpClub where status=1 order by laipaiClubIndex"); */
		
		List<VLpClub> list=this.getfanslist(page);
		List<LpClubShowBean> beanList = new ArrayList<LpClubShowBean>();
		
		for(VLpClub club : list){
			LpClubShowBean bean = new LpClubShowBean();
			BeanUtils.copyProperties(club, bean);
			//喜欢数量
			bean.setLikeNumber(club.getLikeNumber());
			//回复数量
			/*bean.setCommentNumber(club.getCommentNumber());*/
			int commentNumber=baseDao.getCount("from LpComment WHERE newsId='"+club.getLaipaiId()+"'");
			bean.setCommentNumber(commentNumber);
			
			//文章标题图片
			bean.setCoverUrl(ImgUtil.getImgUrl(bean.getCoverUrl(),parameters.getPicType()));
			//文章内容在列表不展示
			bean.setContent(null);
			beanList.add(bean);
		}
		
		 //保存日志
//		if("1".endsWith(page)){
//		  LpClubLog  clubLog =new LpClubLog();
//		  clubLog.setUserId(userId);
//		  clubLog.setAccessClubTime(new Timestamp(new java.util.Date().getTime()));
//		 
//		  if(StringUtils.isBlank(userId)){
//			  clubLog.setMachineId(machineId);
//			  
//		  }
//		  baseDao.save(clubLog);
//		}
		return beanList;
	}
	
	public List<VLpClub> getfanslist(String pageStr){
		int page=1;
		String hql = "from VLpClub where status=1 order by laipaiClubIndex";
		List<VLpClub> list = null;
		if(StringUtils.isEmpty(pageStr) || "null".equals(pageStr)){
			list = this.baseDao.queryListObjectAll(hql);
		}else{
			page = Integer.parseInt(pageStr);
			list = this.baseDao.queryListObjectAllForPage(hql, 10, page);
		}
		
		return list;
		
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		
		JSONObject json = parameters.getJson();

		String page=JSONTools.getString(json,"page");
		
		return "article:"+page;
	}
	

}
