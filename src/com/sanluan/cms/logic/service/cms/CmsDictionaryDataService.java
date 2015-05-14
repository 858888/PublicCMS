package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsDictionaryData;
import com.sanluan.cms.logic.dao.cms.CmsDictionaryDataDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsDictionaryDataService extends BaseService<CmsDictionaryData, CmsDictionaryDataDao> {

	@Autowired
	private CmsDictionaryDataDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(String name, Integer dictionaryId, int pageNo, int pageSize) {
		return dao.getPage(name, dictionaryId, pageNo, pageSize);
	}

	@Override
	protected CmsDictionaryDataDao getDao() {
		return dao;
	}
}