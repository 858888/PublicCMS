package com.sanluan.cms.views.directive.cms;

// Generated 2015-5-10 17:54:56 by SourceMaker

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.entities.cms.CmsUser;
import com.sanluan.cms.logic.service.cms.CmsUserService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;

import freemarker.template.TemplateException;

@Component
public class CmsUserDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		Integer id = handler.getInteger("id");
		if (null != id) {
			CmsUser bean = service.getEntity(id);
			handler.put("bean", bean).renderIfNotNull(bean);
		} else {
			Integer[] ids = handler.getIntegerArray("ids");
			if (null != ids && 0 < ids.length) {
				List<CmsUser> beanList = service.getEntitys(ids, "userId");
				Map<String, CmsUser> map = new HashMap<String, CmsUser>();
				for (CmsUser bean : beanList) {
					map.put(String.valueOf(bean.getUserId()), bean);
				}
				handler.put("map", map).renderIfNotNull(map);
			}
		}
	}

	@Autowired
	private CmsUserService service;

}
