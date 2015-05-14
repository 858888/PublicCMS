package com.sanluan.cms.views.directive.system;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.entities.system.SystemUser;
import com.sanluan.cms.logic.service.system.SystemUserService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;

import freemarker.template.TemplateException;

/**
 * @author zhangxd
 * 
 */
@Component
public class SystemUserDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		Integer id = handler.getInteger("id");
		if (null != id) {
			SystemUser bean = service.getEntity(id);
			bean.setPassword(null);
			bean.setAuthToken(null);
			handler.put("bean", bean).renderIfNotNull(bean);
		}
	}

	@Autowired
	private SystemUserService service;

}
