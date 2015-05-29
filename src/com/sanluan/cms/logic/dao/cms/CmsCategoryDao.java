package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsCategory;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsCategoryDao extends BaseDao<CmsCategory> {
	public PageHandler getPage(Integer parentId, String extend1, String name,
			String extend3, String extend2, String extend4, Boolean isDisable, 
			int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsCategory bean");
		if (notEmpty(parentId)) {
			queryMaker.condition("bean.parentId = :parentId").setParameter("parentId", parentId);
		} else {
			queryMaker.condition("bean.parentId is null");
		}
		if (notEmpty(extend1)) {
			queryMaker.condition("bean.extend1 = :extend1").setParameter("extend1", extend1);
		}
		if (notEmpty(name)) {
			queryMaker.condition("bean.name like :name").setParameter("name", like(name));
		}
		if (notEmpty(extend3)) {
			queryMaker.condition("bean.extend3 = :extend3").setParameter("extend3", extend3);
		}
		if (notEmpty(extend2)) {
			queryMaker.condition("bean.extend2 = :extend2").setParameter("extend2", extend2);
		}
		if (notEmpty(extend4)) {
			queryMaker.condition("bean.extend4 = :extend4").setParameter("extend4", extend4);
		}
		if (notEmpty(isDisable)) {
			queryMaker.condition("bean.isDisable = :isDisable").setParameter("isDisable", isDisable);
		}
		queryMaker.append("order by bean.id desc");
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsCategory init(CmsCategory entity) {
		return entity;
	}

	@Override
	protected Class<CmsCategory> getEntityClass() {
		return CmsCategory.class;
	}

}