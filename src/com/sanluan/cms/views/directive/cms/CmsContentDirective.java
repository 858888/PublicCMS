package com.sanluan.cms.views.directive.cms;

// Generated 2015-5-10 17:54:56 by SourceMaker

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.entities.cms.CmsContent;
import com.sanluan.cms.logic.service.cms.CmsContentService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;

import freemarker.template.TemplateException;

@Component
public class CmsContentDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		Integer id = handler.getInteger("id");
		if (null != id) {
			CmsContent bean = service.getEntity(id);
			handler.put("bean", bean).renderIfNotNull(bean);
		} else {
			Integer[] ids = handler.getIntegerArray("ids");
			if (null != ids && 0 < ids.length) {
				List<CmsContent> beanList = service.getEntitys(ids);
				Map<String, CmsContent> map = new HashMap<String, CmsContent>();
				for (CmsContent bean : beanList) {
					map.put(String.valueOf(bean.getId()), bean);
				}
				handler.put("map", map).renderIfNotNull(map);
			}
		}
	}

	@Autowired
	private CmsContentService service;

}
