package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsModelExtend;
import com.sanluan.cms.logic.dao.cms.CmsModelExtendDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsModelExtendService extends BaseService<CmsModelExtend, CmsModelExtendDao> {

	@Autowired
	private CmsModelExtendDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Integer modelId, Integer extendType, Short type, int pageNo, int pageSize) {
		return dao.getPage(modelId, extendType, type, pageNo, pageSize);
	}

	@Override
	protected CmsModelExtendDao getDao() {
		return dao;
	}
}