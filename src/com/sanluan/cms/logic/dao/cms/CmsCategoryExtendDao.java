package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsCategoryExtend;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsCategoryExtendDao extends BaseDao<CmsCategoryExtend> {
	public PageHandler getPage(Integer extendType, Integer cateoryId, Short type, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsCategoryExtend bean");
		if (notEmpty(extendType)) {
			queryMaker.condition("bean.extendType = :extendType").setParameter("extendType", extendType);
		}
		if (notEmpty(cateoryId)) {
			queryMaker.condition("bean.cateoryId = :cateoryId").setParameter("cateoryId", cateoryId);
		}
		if (notEmpty(type)) {
			queryMaker.condition("bean.type = :type").setParameter("type", type);
		}
		queryMaker.append("order by bean.id desc");
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsCategoryExtend init(CmsCategoryExtend entity) {
		return entity;
	}

	@Override
	protected Class<CmsCategoryExtend> getEntityClass() {
		return CmsCategoryExtend.class;
	}

}