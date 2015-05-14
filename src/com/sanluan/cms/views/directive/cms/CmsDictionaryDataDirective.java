package com.sanluan.cms.views.directive.cms;

// Generated 2015-5-10 17:54:56 by SourceMaker

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.entities.cms.CmsDictionaryData;
import com.sanluan.cms.logic.service.cms.CmsDictionaryDataService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;

import freemarker.template.TemplateException;

@Component
public class CmsDictionaryDataDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		Integer id = handler.getInteger("id");
		if (null != id) {
			CmsDictionaryData bean = service.getEntity(id);
			handler.put("bean", bean).renderIfNotNull(bean);
		}
	}

	@Autowired
	private CmsDictionaryDataService service;

}
