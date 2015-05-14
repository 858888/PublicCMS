package com.sanluan.cms.logic.dao.log;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.log.LogLogin;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class LogLoginDao extends BaseDao<LogLogin> {
	public PageHandler getPage(Boolean result, Integer userId, String name, Date startCreateDate, Date endCreateDate, String ip,
			String orderField, String orderType, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from LogLogin bean");
		if (notEmpty(result)) {
			queryMaker.condition("bean.result = :result").setParameter("result", result);
		}
		if (notEmpty(userId)) {
			queryMaker.condition("bean.userId = :userId").setParameter("userId", userId);
		}
		if (notEmpty(name)) {
			queryMaker.condition("bean.name like :name").setParameter("name", like(name));
		}
		if (notEmpty(startCreateDate)) {
			queryMaker.condition("bean.createDate >= :startCreateDate").setParameter("startCreateDate", startCreateDate);
		}
		if (notEmpty(endCreateDate)) {
			queryMaker.condition("bean.createDate < :endCreateDate").setParameter("endCreateDate", tomorrow(endCreateDate));
		}
		if (notEmpty(ip)) {
			queryMaker.condition("bean.ip like :ip").setParameter("ip", like(ip));
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
	protected LogLogin init(LogLogin entity) {
		if (!notEmpty(entity.getCreateDate())) {
			entity.setCreateDate(getDate());
		}
		return entity;
	}

	@Override
	protected Class<LogLogin> getEntityClass() {
		return LogLogin.class;
	}

}