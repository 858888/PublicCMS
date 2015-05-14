package com.sanluan.cms.logic.dao.log;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.log.LogOperate;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class LogOperateDao extends BaseDao<LogOperate> {
	public PageHandler getPage(String content, String operate, Integer userId, Date startCreateDate, Date endCreateDate,
			String orderField, String orderType, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from LogOperate bean");
		if (notEmpty(content)) {
			queryMaker.condition("bean.content like :content").setParameter("content", like(content));
		}
		if (notEmpty(operate)) {
			queryMaker.condition("bean.operate = :operate").setParameter("operate", operate);
		}
		if (notEmpty(userId)) {
			queryMaker.condition("bean.userId = :userId").setParameter("userId", userId);
		}
		if (notEmpty(startCreateDate)) {
			queryMaker.condition("bean.createDate >= :startCreateDate").setParameter("startCreateDate", startCreateDate);
		}
		if (notEmpty(endCreateDate)) {
			queryMaker.condition("bean.createDate < :endCreateDate").setParameter("endCreateDate", tomorrow(endCreateDate));
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
	protected LogOperate init(LogOperate entity) {
		return entity;
	}

	@Override
	protected Class<LogOperate> getEntityClass() {
		return LogOperate.class;
	}

}