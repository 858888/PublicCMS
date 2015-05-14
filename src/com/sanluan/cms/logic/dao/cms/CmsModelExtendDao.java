package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsModelExtend;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsModelExtendDao extends BaseDao<CmsModelExtend> {
	public PageHandler getPage(Integer modelId, Integer extendType, Short type, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsModelExtend bean");
		if (notEmpty(modelId)) {
			queryMaker.condition("bean.modelId = :modelId").setParameter("modelId", modelId);
		}
		if (notEmpty(extendType)) {
			queryMaker.condition("bean.extendType = :extendType").setParameter("extendType", extendType);
		}
		if (notEmpty(type)) {
			queryMaker.condition("bean.type = :type").setParameter("type", type);
		}
		queryMaker.append("order by bean.id desc");
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsModelExtend init(CmsModelExtend entity) {
		return entity;
	}

	@Override
	protected Class<CmsModelExtend> getEntityClass() {
		return CmsModelExtend.class;
	}

}