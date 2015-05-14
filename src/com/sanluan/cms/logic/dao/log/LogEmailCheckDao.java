package com.sanluan.cms.logic.dao.log;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.log.LogEmailCheck;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class LogEmailCheckDao extends BaseDao<LogEmailCheck> {
	public PageHandler getPage(Integer userId, Date startCreateDate, Date endCreateDate, Boolean checked, String orderField,
			String orderType, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from LogEmailCheck bean");
		if (notEmpty(userId)) {
			queryMaker.condition("bean.userId = :userId").setParameter("userId", userId);
		}
		if (notEmpty(startCreateDate)) {
			queryMaker.condition("bean.createDate >= :startCreateDate").setParameter("startCreateDate", startCreateDate);
		}
		if (notEmpty(endCreateDate)) {
			queryMaker.condition("bean.createDate < :endCreateDate").setParameter("endCreateDate", tomorrow(endCreateDate));
		}
		if (notEmpty(checked)) {
			queryMaker.condition("bean.checked = :checked").setParameter("checked", checked);
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

	public LogEmailCheck findByCode(String code) {
		QueryHandler queryMaker = getQueryMaker("from LogEmailCheck bean where bean.code = :code and bean.checked = :checked")
				.setParameter("code", code).setParameter("checked", false);
		return getEntity(queryMaker);
	}

	@Override
	protected LogEmailCheck init(LogEmailCheck entity) {
		if (!notEmpty(entity.getCreateDate())) {
			entity.setCreateDate(getDate());
		}
		return entity;
	}

	@Override
	protected Class<LogEmailCheck> getEntityClass() {
		return LogEmailCheck.class;
	}

}