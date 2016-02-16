package com.laipai.message.dao;

import com.laipai.galaryManInfo.pojo.LpMessage;
import com.laipai.laiPaiClubInfo.pojo.LpClub;
import com.laipai.message.pojo.LpMessageCode;

public interface MessageDao {

	public LpMessageCode queryHql(String messageCodeId);

}
