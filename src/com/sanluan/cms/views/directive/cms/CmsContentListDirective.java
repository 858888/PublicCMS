package com.sanluan.cms.views.directive.cms;

// Generated 2015-5-10 17:54:56 by SourceMaker

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.logic.service.cms.CmsContentService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;
import com.sanluan.common.handler.PageHandler;

import freemarker.template.TemplateException;

@Component
public class CmsContentListDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		PageHandler page = service.getPage(handler.getString("modelExtend3"), handler.getString("modelExtend4"),
				handler.getInteger("status", 1), handler.getString("extend1"), handler.getInteger("categoryId"),
				handler.getString("extend3"), handler.getString("extend2"), handler.getString("extend4"),
				handler.getBoolean("isDisable", false), handler.getInteger("modelId"), handler.getString("title"),
				handler.getInteger("userId"), handler.getString("modelExtend2"), handler.getDate("startPublishDate"),
				handler.getDate("endPublishDate"), handler.getString("modelExtend1"), handler.getString("orderField"),
				handler.getString("orderType"), handler.getInteger("pageNo", 1), handler.getInteger("count", 20));
		handler.put("page", page).render();
	}

	@Autowired
	private CmsContentService service;

}