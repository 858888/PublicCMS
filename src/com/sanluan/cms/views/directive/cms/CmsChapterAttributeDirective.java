package com.sanluan.cms.views.directive.cms;

// Generated 2015-5-10 17:54:56 by SourceMaker

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.entities.cms.CmsChapterAttribute;
import com.sanluan.cms.logic.service.cms.CmsChapterAttributeService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;

import freemarker.template.TemplateException;

@Component
public class CmsChapterAttributeDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		Integer id = handler.getInteger("id");
		if (null != id) {
			CmsChapterAttribute bean = service.getEntity(id);
			handler.put("bean", bean).renderIfNotNull(bean);
		} else {
			Integer[] ids = handler.getIntegerArray("ids");
			if (null != ids && 0 < ids.length) {
				List<CmsChapterAttribute> beanList = service.getEntitys(ids,"chapterId");
				Map<String, CmsChapterAttribute> map = new HashMap<String, CmsChapterAttribute>();
				for (CmsChapterAttribute bean : beanList) {
					map.put(String.valueOf(bean.getChapterId()), bean);
				}
				handler.put("map", map).renderIfNotNull(map);
			}
		}
	}

	@Autowired
	private CmsChapterAttributeService service;

}
