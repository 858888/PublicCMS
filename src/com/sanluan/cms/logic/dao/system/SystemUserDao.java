package com.sanluan.cms.logic.dao.system;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.system.SystemUser;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class SystemUserDao extends BaseDao<SystemUser> {
	public PageHandler getPage(Date startDateRegistered, Date endDateRegistered, Date startLastLoginDate, Date endLastLoginDate, 
				Boolean superuserAccess, Boolean emailChecked, String name, 
				Boolean disable, 
				String orderField, String orderType, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from SystemUser bean");
		if (notEmpty(startDateRegistered)) {
			queryMaker.condition("bean.dateRegistered >= :startDateRegistered").setParameter("startDateRegistered", startDateRegistered);
		}
		if (notEmpty(endDateRegistered)) {
			queryMaker.condition("bean.dateRegistered < :endDateRegistered").setParameter("endDateRegistered", tomorrow(endDateRegistered));
		}
		if (notEmpty(startLastLoginDate)) {
			queryMaker.condition("bean.lastLoginDate >= :startLastLoginDate").setParameter("startLastLoginDate", startLastLoginDate);
		}
		if (notEmpty(endLastLoginDate)) {
			queryMaker.condition("bean.lastLoginDate < :endLastLoginDate").setParameter("endLastLoginDate", tomorrow(endLastLoginDate));
		}
		if (notEmpty(superuserAccess)) {
			queryMaker.condition("bean.superuserAccess = :superuserAccess").setParameter("superuserAccess", superuserAccess);
		}
		if (notEmpty(emailChecked)) {
			queryMaker.condition("bean.emailChecked = :emailChecked").setParameter("emailChecked", emailChecked);
		}
		if (notEmpty(name)) {
			queryMaker.condition("(bean.name like :name or bean.nickName like :name or bean.email like :name)").setParameter("name", like(name));
		}
		if (notEmpty(disable)) {
			queryMaker.condition("bean.disable = :disable").setParameter("disable", disable);
		}
		if("asc".equals(orderType)){
			orderType = "asc";
		}else{
			orderType = "desc";
		}
		if(!notEmpty(orderField)){
			orderField="";
		}
		switch(orderField) {
			case "lastLoginDate" : queryMaker.append("order by bean.lastLoginDate " + orderType); break;
			case "loginCount" : queryMaker.append("order by bean.loginCount " + orderType); break;
			case "dateRegistered" : queryMaker.append("order by bean.dateRegistered " + orderType); break;
			default : queryMaker.append("order by bean.id "+orderType);
		}
		return getPage(queryMaker, pageNo, pageSize);
	}

	public SystemUser findByName(String name) {
		QueryHandler queryMaker = getQueryMaker("from SystemUser bean where bean.name = :name").setParameter("name", name);
		return getEntity(queryMaker);
	}

	public SystemUser findByNickName(String nickName) {
		QueryHandler queryMaker = getQueryMaker("from SystemUser bean where bean.nickName = :nickName").setParameter("nickName",
				nickName);
		return getEntity(queryMaker);
	}

	public SystemUser findByEmail(String email) {
		QueryHandler queryMaker = getQueryMaker(
				"from SystemUser bean where bean.email = :email and bean.emailChecked = :emailChecked ").setParameter("email",
				email).setParameter("emailChecked", true);
		return getEntity(queryMaker);
	}

	public SystemUser findByAuthToken(String authToken) {
		QueryHandler queryMaker = getQueryMaker("from SystemUser bean where bean.authToken = :authToken").setParameter("authToken",
				authToken);
		return getEntity(queryMaker);
	}

	@Override
	protected Class<SystemUser> getEntityClass() {
		return SystemUser.class;
	}

	@Override
	protected SystemUser init(SystemUser entity) {
		if (!notEmpty(entity.getDateRegistered())) {
			entity.setDateRegistered(getDate());
		}
		return entity;
	}
}
