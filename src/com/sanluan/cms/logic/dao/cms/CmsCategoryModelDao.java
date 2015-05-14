package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsCategoryModel;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsCategoryModelDao extends BaseDao<CmsCategoryModel> {
	public PageHandler getPage(Integer modelId, Integer categoryId, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsCategoryModel bean");
		if (notEmpty(modelId)) {
			queryMaker.condition("bean.modelId = :modelId").setParameter("modelId", modelId);
		}
		if (notEmpty(categoryId)) {
			queryMaker.condition("bean.categoryId = :categoryId").setParameter("categoryId", categoryId);
		}
		queryMaker.append("order by bean.id desc");
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsCategoryModel init(CmsCategoryModel entity) {
		return entity;
	}

	@Override
	protected Class<CmsCategoryModel> getEntityClass() {
		return CmsCategoryModel.class;
	}

}