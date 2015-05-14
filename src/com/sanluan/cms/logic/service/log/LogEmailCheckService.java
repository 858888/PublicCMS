package com.sanluan.cms.logic.service.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.log.LogEmailCheck;
import com.sanluan.cms.logic.dao.log.LogEmailCheckDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

/**
 * @author zhangxd
 * 
 */
@Service
@Transactional
public class LogEmailCheckService extends BaseService<LogEmailCheck, LogEmailCheckDao> {

	@Autowired
	private LogEmailCheckDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(Integer userId, Date startCreateDate, Date endCreateDate, 
			Boolean checked, 
			String orderField, String orderType, int pageNo, int pageSize) {
		return dao.getPage(userId, startCreateDate, endCreateDate, 
			checked, 
			orderField, orderType, pageNo, pageSize);
	}

	public LogEmailCheck findByCode(String code) {
		return dao.findByCode(code);
	}

	public void checked(Integer id) {
		LogEmailCheck entity = dao.getEntity(id);
		if (null != entity) {
			entity.setChecked(true);
		}
	}

	@Override
	protected LogEmailCheckDao getDao() {
		return dao;
	}
}
