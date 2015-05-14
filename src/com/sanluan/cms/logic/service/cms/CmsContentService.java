package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsContent;
import com.sanluan.cms.logic.dao.cms.CmsContentDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsContentService extends BaseService<CmsContent, CmsContentDao> {

	@Autowired
	private CmsContentDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Integer extendNumber3, Integer extendNumber4, 
				Integer status, String extend1, String categoryId, 
				String extend3, String extend2, String extend4, 
				Boolean isDisable, Integer modelId, String title, 
				Integer userId, Integer extendNumber2, Date startPublishDate, Date endPublishDate, 
				Integer extendNumber1, 
				String orderField, String orderType, int pageNo, int pageSize) {
		return dao.getPage(extendNumber3, extendNumber4, 
				status, extend1, categoryId, 
				extend3, extend2, extend4, 
				isDisable, modelId, title, 
				userId, extendNumber2, startPublishDate, endPublishDate, 
				extendNumber1, 
				orderField, orderType, pageNo, pageSize);
	}

	@Override
	protected CmsContentDao getDao() {
		return dao;
	}
}