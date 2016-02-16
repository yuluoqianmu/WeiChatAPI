package com.laipai.message.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.laipai.galaryManInfo.pojo.LpMessage;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.message.dto.MessageBean;
import com.laipai.message.pojo.LpMessageCode;
import com.laipai.userManInfo.pojo.LpComment;

public interface IMessageService {

	public void addMessage(MessageBean messageBean);

	public LpMessageCode queryHql(String messageCodeId);

	public void updateMessageDetail(int messageType, int onLine);

	public boolean openNotice(int messageType, int messageNotice);

	public boolean openPush(int messageType, int messagePush);

	public LpClub queryClubByComment(String newsId);

	public LpComment queryCommentById(String replyToId);

	public List queryAllData(String hql,HttpServletRequest request);


}
