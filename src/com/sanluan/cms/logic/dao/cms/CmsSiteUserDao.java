package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsSiteUser;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsSiteUserDao extends BaseDao<CmsSiteUser> {
	public PageHandler getPage(Integer siteId, Integer userId, Boolean isAdmin, Boolean isDisable, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsSiteUser bean");
		if (notEmpty(siteId)) {
			queryMaker.condition("bean.siteId = :siteId").setParameter("siteId", siteId);
		}
		if (notEmpty(userId)) {
			queryMaker.condition("bean.userId = :userId").setParameter("userId", userId);
		}
		if (notEmpty(isAdmin)) {
			queryMaker.condition("bean.isAdmin = :isAdmin").setParameter("isAdmin", isAdmin);
		}
		if (notEmpty(isDisable)) {
			queryMaker.condition("bean.isDisable = :isDisable").setParameter("isDisable", isDisable);
		}
		queryMaker.append("order by bean.id desc");
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsSiteUser init(CmsSiteUser entity) {
		return entity;
	}

	@Override
	protected Class<CmsSiteUser> getEntityClass() {
		return CmsSiteUser.class;
	}

}