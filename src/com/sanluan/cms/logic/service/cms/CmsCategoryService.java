package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsCategory;
import com.sanluan.cms.logic.dao.cms.CmsCategoryDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsCategoryService extends BaseService<CmsCategory, CmsCategoryDao> {

	@Autowired
	private CmsCategoryDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Integer extendNumber3, Integer extendNumber4, Integer parentId, Integer siteId, String extend1, String name, String extend3, String extend2, String extend4, Integer extendNumber2, Boolean isDisable, Integer extendNumber1, int pageNo, int pageSize) {
		return dao.getPage(extendNumber3, extendNumber4, parentId, siteId, extend1, name, extend3, extend2, extend4, extendNumber2, isDisable, extendNumber1, pageNo, pageSize);
	}

	@Override
	protected CmsCategoryDao getDao() {
		return dao;
	}
}