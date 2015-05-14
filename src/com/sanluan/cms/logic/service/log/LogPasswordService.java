package com.sanluan.cms.logic.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.log.LogPassword;
import com.sanluan.cms.logic.dao.log.LogPasswordDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

/**
 * @author zhangxd
 * 
 */
@Service
@Transactional
public class LogPasswordService extends BaseService<LogPassword, LogPasswordDao> {

	@Autowired
	private LogPasswordDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Integer userId, Date startCreateDate, Date endCreateDate, 
				String ip, 
				String orderField, String orderType, int pageNo, int pageSize) {
		return dao.getPage(userId, startCreateDate, endCreateDate, 
				ip, 
				orderField, orderType, pageNo, pageSize);
	}

	@Override
	protected LogPasswordDao getDao() {
		return dao;
	}
}
