package com.laipai.galaryManInfo.app;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.laipai.app.annotation.NotLogin;
import com.laipai.app.common.JSONTools;
import com.laipai.app.process.app.RequestExecutor;
import com.laipai.app.process.app.dataobj.BaseRequestParameters;
import com.laipai.galaryManInfo.service.IGalleryService;
/**
 * 

 * @Description:删除评论
 * @author:lxd

 * @time:2015-1-26 下午8:24:00
 */
@NotLogin
@Service("DeleteCommentExecutorImpl")
public class DeleteCommentExecutorImpl implements RequestExecutor {
    @Resource(name=IGalleryService.SERVICE_NAME)
	private IGalleryService galleryService;
	
	@Override
	public Object execute(BaseRequestParameters parameters,
			Object... requestObjects) {
		JSONObject json = parameters.getJson();
		JSONObject baseJson = new JSONObject();
		//摄影师的id(回复人userId)
		String commentId = json.getString("commentId");
		galleryService.deleteCommentById(commentId);
		return  JSONTools.newAPIResult(0, "删除成功");
		
	}

	@Override
	public String getCacheKey(BaseRequestParameters parameters,
			Object... requestObjects) {
		// TODO Auto-generated method stub
		return null;
	}

}
