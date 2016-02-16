package com.laipai.galaryManInfo.app;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.base.dao.IBaseDao;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.galaryManInfo.bean.NewIntroducedBean;
import com.laipai.galaryManInfo.pojo.LpNewintroduce;
import com.laipai.img.ImgUtil;

/**
 * 新晋摄影师介绍
 * 
 * @author zhh
 */
@NotLogin
@Service("NewCamerManIntoduceExecutorImpl")
public class NewCamerManIntoduceExecutorImpl implements RequestExecutor {

	@Autowired
	private IBaseDao baseDao;
	
	/**
	 * 重写http调用方法
	 * */
	public Object execute(BaseRequestParameters parameters, Object... arg) {
		JSONObject json = parameters.getJson();
		//获取服务器域名
		String serverName = json.getString("serverName");
		String introduceId = json.getString("introduceId");
		if(StringUtils.isBlank(introduceId)){
			return JSONTools.newAPIResult(1, "参数不能为空");
		}
		//新晋摄影师介绍
		LpNewintroduce introduce = (LpNewintroduce) baseDao.getObjectById(LpNewintroduce.class, introduceId);
		if(introduce ==null){
			return JSONTools.newAPIResult(1, "此推荐文章不存在");
		}
		NewIntroducedBean inroduceBean = new NewIntroducedBean();
		BeanUtils.copyProperties(introduce, inroduceBean);
		//首页广告图片,修改图片路径,加上服务器域名,并把实际路径替换为虚拟路径
		inroduceBean.setImgUrl(ImgUtil.getImgUrl(inroduceBean.getImgUrl(),parameters.getPicType()));
		//推荐内容中的图片,替换路径
		inroduceBean.setContent("<p>"+introduce.getTitle()+"</p><p>"+introduce.getCreateTime()+"</p>" + ImgUtil.getImgUrl(inroduceBean.getContent(),parameters.getPicType()));
		return JSONObject.fromObject(inroduceBean);
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
