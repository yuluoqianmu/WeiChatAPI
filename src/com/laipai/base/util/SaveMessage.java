package com.laipai.base.util;


import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.laipai.base.dao.IBaseDao;

import com.laipai.message.pojo.LpMessageMapping;


//0：摄影师+用户 1：全体用户 2：全体摄影师 3：单个用户 4：单个摄影师
//新消息类型1、 预约，2、评论(所有评论和回复)，3、赞，4、关注，5、系统消息
@Transactional
public class SaveMessage {
	@Autowired
    private static IBaseDao baseDao;	
	public static void Log(String userId,String mapingId,int type){
		LpMessageMapping message=new LpMessageMapping();
		message.setCreateTime(new Timestamp(new Date().getTime()));
		switch (type) {
		case 1:
			message.setOrderId(mapingId);
			message.setMessageType(1);
			break;

		case 2:
			message.setCommentId(mapingId);
			message.setMessageType(2);
			break;
		case 3:
			message.setLikeId(mapingId);
			message.setMessageType(3);
			break;
	
	     case 4:
	 	message.setFollowId(mapingId);
	 	message.setMessageType(4);
		break;
         default:
        	 break;
}
		message.setStatus(0);
		message.setReceiveUserId(userId);
		
		baseDao.saveOrUpdate(message);
	}

}
