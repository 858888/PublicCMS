package com.sanluan.cms.views.directive.system;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		} else {
			Integer[] ids = handler.getIntegerArray("ids");
			if (null != ids && 0 < ids.length) {
				List<SystemUser> beanList = service.getEntitys(ids);
				Map<String, SystemUser> map = new HashMap<String, SystemUser>();
				for (SystemUser bean : beanList) {
					map.put(String.valueOf(bean.getId()), bean);
				}
				handler.put("map", map).renderIfNotNull(map);
			}
		}
	}

	@Autowired
	private SystemUserService service;

}
