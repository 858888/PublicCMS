package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsSiteUser;
import com.sanluan.cms.logic.dao.cms.CmsSiteUserDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsSiteUserService extends BaseService<CmsSiteUser, CmsSiteUserDao> {

	@Autowired
	private CmsSiteUserDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Integer siteId, Integer userId, Boolean isAdmin, Boolean isDisable, int pageNo, int pageSize) {
		return dao.getPage(siteId, userId, isAdmin, isDisable, pageNo, pageSize);
	}

	@Override
	protected CmsSiteUserDao getDao() {
		return dao;
	}
}