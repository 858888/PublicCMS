package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsSite;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsSiteDao extends BaseDao<CmsSite> {
	public PageHandler getPage(Date startCreateDate, Date endCreateDate, Boolean isDisable, String orderField, String orderType,
			int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsSite bean");
		if (notEmpty(startCreateDate)) {
			queryMaker.condition("bean.createDate >= :startCreateDate").setParameter("startCreateDate", startCreateDate);
		}
		if (notEmpty(endCreateDate)) {
			queryMaker.condition("bean.createDate < :endCreateDate").setParameter("endCreateDate", tomorrow(endCreateDate));
		}
		if (notEmpty(isDisable)) {
			queryMaker.condition("bean.isDisable = :isDisable").setParameter("isDisable", isDisable);
		}
		if ("asc".equals(orderType)) {
			orderType = "asc";
		} else {
			orderType = "desc";
		}
		if (!notEmpty(orderField)) {
			orderField = "";
		}
		switch (orderField) {
		case "createDate":
			queryMaker.append("order by bean.createDate " + orderType);
			break;
		default:
			queryMaker.append("order by bean.id " + orderType);
		}
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsSite init(CmsSite entity) {
		return entity;
	}

	@Override
	protected Class<CmsSite> getEntityClass() {
		return CmsSite.class;
	}

}