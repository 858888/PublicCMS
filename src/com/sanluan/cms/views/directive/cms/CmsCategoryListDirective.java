package com.sanluan.cms.views.directive.cms;

// Generated 2015-5-10 17:54:56 by SourceMaker

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.logic.service.cms.CmsCategoryService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;
import com.sanluan.common.handler.PageHandler;

import freemarker.template.TemplateException;

@Component
public class CmsCategoryListDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		PageHandler page = service.getPage(handler.getInteger("parentId"), handler.getString("extend1"),
				handler.getString("name"), handler.getString("extend3"), handler.getString("extend2"),
				handler.getString("extend4"), handler.getBoolean("isDisable", false), handler.getInteger("pageNo", 1),
				handler.getInteger("count", 20));
		handler.put("page", page).render();
	}

	@Autowired
	private CmsCategoryService service;

}