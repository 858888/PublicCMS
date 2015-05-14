package com.sanluan.cms.logic.dao.log;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.log.LogPassword;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

/**
 * @author zhangxd
 * 
 */
@Repository
public class LogPasswordDao extends BaseDao<LogPassword> {
	public PageHandler getPage(Integer userId, Date startCreateDate, Date endCreateDate, String ip, String orderField,
			String orderType, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from LogPassword bean");
		if (notEmpty(userId)) {
			queryMaker.condition("bean.userId = :userId").setParameter("userId", userId);
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
	protected LogPassword init(LogPassword entity) {
		if (!notEmpty(entity.getCreateDate())) {
			entity.setCreateDate(getDate());
		}
		return entity;
	}

	@Override
	protected Class<LogPassword> getEntityClass() {
		return LogPassword.class;
	}

}
