package com.laipai.privilege.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laipai.privilege.dao.ResourceDao;
import com.laipai.privilege.pojo.LpResource;
import com.laipai.privilege.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public List<LpResource> queryAllResource() {
		return resourceDao.queryAllResource();
	}

}
