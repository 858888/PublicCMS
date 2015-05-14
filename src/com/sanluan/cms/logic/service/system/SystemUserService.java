package com.sanluan.cms.logic.service.system;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.log.LogLogin;
import com.sanluan.cms.entities.log.LogPassword;
import com.sanluan.cms.entities.system.SystemUser;
import com.sanluan.cms.logic.dao.log.LogLoginDao;
import com.sanluan.cms.logic.dao.log.LogPasswordDao;
import com.sanluan.cms.logic.dao.system.SystemUserDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

/**
 * @author zhangxd
 * 
 */
@Service
@Transactional
public class SystemUserService extends BaseService<SystemUser, SystemUserDao> {
	@Autowired
	private SystemUserDao dao;
	@Autowired
	private LogPasswordDao logPasswordDao;
	@Autowired
	private LogLoginDao logLogindao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Date startDateRegistered, Date endDateRegistered, Date startLastLoginDate, Date endLastLoginDate, 
			Boolean superuserAccess, Boolean emailChecked, String name, 
			Boolean disable, 
			String orderField, String orderType, int pageNo, int pageSize) {
		return dao.getPage(startDateRegistered, endDateRegistered, startLastLoginDate, endLastLoginDate, 
				superuserAccess, emailChecked, name, 
				disable, 
				orderField, orderType, pageNo, pageSize);
	}

	public SystemUser findByName(String name) {
		return dao.findByName(name);
	}

	public SystemUser findByNickName(String nickName) {
		return dao.findByNickName(nickName);
	}

	public SystemUser findByEmail(String email) {
		return dao.findByEmail(email);
	}

	public SystemUser findByAuthToken(String authToken) {
		return dao.findByAuthToken(authToken);
	}

	public void updatePassword(Integer id, String ip, String password) {
		SystemUser entity = dao.getEntity(id);
		if (null != entity) {
			LogPassword log = new LogPassword(entity.getId(), ip, dao.getDate(), password, entity.getPassword());
			logPasswordDao.save(log);
			entity.setPassword(password);
		}
	}

	public SystemUser updateLoginStatus(Integer id, String username, String authToken, String ip) {
		SystemUser entity = dao.getEntity(id);
		if (null != entity) {
			LogLogin log = new LogLogin();
			log.setName(username);
			log.setUserId(id);
			log.setResult(true);
			log.setIp(ip);
			log.setErrorPassword(null);
			logLogindao.save(log);
			entity.setAuthToken(authToken);
			entity.setLastLoginDate(dao.getDate());
			entity.setLastLoginIp(ip);
			entity.setLoginCount(entity.getLoginCount() + 1);
		}
		return entity;
	}

	public void checked(Integer userId, String email) {
		SystemUser entity = dao.getEntity(userId);
		if (null != entity) {
			entity.setEmail(email);
			entity.setEmailChecked(true);
		}
	}

	public void updateStatus(Integer id, boolean status) {
		SystemUser entity = dao.getEntity(id);
		if (null != entity) {
			entity.setDisable(status);
		}
	}

	@Override
	protected SystemUserDao getDao() {
		return dao;
	}
}
