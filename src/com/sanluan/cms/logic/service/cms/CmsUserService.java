package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsUser;
import com.sanluan.cms.logic.dao.cms.CmsUserDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsUserService extends BaseService<CmsUser, CmsUserDao> {

	@Autowired
	private CmsUserDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(int pageNo, int pageSize) {
		return dao.getPage(pageNo, pageSize);
	}

	@Override
	protected CmsUserDao getDao() {
		return dao;
	}
}