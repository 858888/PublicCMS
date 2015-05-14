package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsCategoryExtend;
import com.sanluan.cms.logic.dao.cms.CmsCategoryExtendDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsCategoryExtendService extends BaseService<CmsCategoryExtend, CmsCategoryExtendDao> {

	@Autowired
	private CmsCategoryExtendDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Integer extendType, Integer cateoryId, Short type, int pageNo, int pageSize) {
		return dao.getPage(extendType, cateoryId, type, pageNo, pageSize);
	}

	@Override
	protected CmsCategoryExtendDao getDao() {
		return dao;
	}
}