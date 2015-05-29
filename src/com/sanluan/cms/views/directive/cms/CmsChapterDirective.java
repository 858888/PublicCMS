package com.sanluan.cms.views.directive.cms;

// Generated 2015-5-10 17:54:56 by SourceMaker

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.entities.cms.CmsChapter;
import com.sanluan.cms.logic.service.cms.CmsChapterService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;

import freemarker.template.TemplateException;

@Component
public class CmsChapterDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		Integer id = handler.getInteger("id");
		if (null != id) {
			CmsChapter bean = service.getEntity(id);
			handler.put("bean", bean).renderIfNotNull(bean);
		} else {
			Integer[] ids = handler.getIntegerArray("ids");
			if (null != ids && 0 < ids.length) {
				List<CmsChapter> beanList = service.getEntitys(ids,"contentId");
				Map<String, CmsChapter> map = new HashMap<String, CmsChapter>();
				for (CmsChapter bean : beanList) {
					map.put(String.valueOf(bean.getContentId()), bean);
				}
				handler.put("map", map).renderIfNotNull(map);
			}
		}
	}

	@Autowired
	private CmsChapterService service;

}
