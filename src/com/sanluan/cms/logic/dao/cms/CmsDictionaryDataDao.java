package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker


import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsDictionaryData;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsDictionaryDataDao extends BaseDao<CmsDictionaryData> {
	public PageHandler getPage(String name, Integer dictionaryId, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsDictionaryData bean");
		if (notEmpty(name)) {
			queryMaker.condition("bean.name = :name").setParameter("name", name);
		}
		if (notEmpty(dictionaryId)) {
			queryMaker.condition("bean.dictionaryId = :dictionaryId").setParameter("dictionaryId", dictionaryId);
		}
		queryMaker.append("order by bean.id desc");
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsDictionaryData init(CmsDictionaryData entity) {
		return entity;
	}

	@Override
	protected Class<CmsDictionaryData> getEntityClass() {
		return CmsDictionaryData.class;
	}

}