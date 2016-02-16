package com.laipai.message.dao.imple;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.laipai.base.dao.imple.BaseDaoImple;
import com.laipai.galaryManInfo.pojo.LpMessage;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.message.dao.MessageDao;
import com.laipai.message.pojo.LpMessageCode;

@Repository("messageDao")
public class MessageDaoImple extends BaseDaoImple implements MessageDao{

	private static final Logger logger=Logger.getLogger(MessageDao.class);

	public LpMessageCode queryHql(String messageCodeId) {
		try {
			LpMessageCode messageCode = (LpMessageCode) getObjectById(LpMessageCode.class, messageCodeId);
			return messageCode;
		} catch (Exception e) {
			logger.error("查询消息列表失败");
			e.printStackTrace();
		}
		return null;
	}


}
