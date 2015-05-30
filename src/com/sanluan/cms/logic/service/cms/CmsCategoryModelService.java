package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsCategoryModel;
import com.sanluan.cms.logic.dao.cms.CmsCategoryModelDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsCategoryModelService extends BaseService<CmsCategoryModel, CmsCategoryModelDao> {

	@Autowired
	private CmsCategoryModelDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Integer modelId, Integer categoryId, int pageNo, int pageSize) {
		return dao.getPage(modelId, categoryId, pageNo, pageSize);
	}

	public CmsCategoryModel update(Serializable id, String templatePath, String chapterTemplatePath) {
		CmsCategoryModel entity = getEntity(id);
		entity.setTemplatePath(templatePath);
		entity.setChapterTemplatePath(chapterTemplatePath);
		return entity;
	}

	@Transactional(readOnly = true)
	public CmsCategoryModel getEntity(int modelId, int categoryId) {
		return dao.getEntity(modelId, categoryId);
	}

	@Override
	protected CmsCategoryModelDao getDao() {
		return dao;
	}
}