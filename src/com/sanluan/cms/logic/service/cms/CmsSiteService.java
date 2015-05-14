package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsSite;
import com.sanluan.cms.logic.dao.cms.CmsSiteDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsSiteService extends BaseService<CmsSite, CmsSiteDao> {

	@Autowired
	private CmsSiteDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Date startCreateDate, Date endCreateDate, Boolean isDisable, 
				String orderField, String orderType, int pageNo, int pageSize) {
		return dao.getPage(startCreateDate, endCreateDate, isDisable, 
				orderField, orderType, pageNo, pageSize);
	}

	@Override
	protected CmsSiteDao getDao() {
		return dao;
	}
}