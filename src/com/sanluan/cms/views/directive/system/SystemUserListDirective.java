package com.sanluan.cms.views.directive.system;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.logic.service.system.SystemUserService;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;
import com.sanluan.common.handler.PageHandler;

import freemarker.template.TemplateException;

/**
 * @author zhangxd
 * 
 */
@Component
public class SystemUserListDirective extends BaseDirective {

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		PageHandler page = service.getPage(handler.getDate("startDateRegistered"), handler.getDate("endDateRegistered"), handler.getDate("startLastLoginDate"), handler.getDate("endLastLoginDate"), 
				handler.getBoolean("superuserAccess"), handler.getBoolean("emailChecked"), handler.getString("name"), 
				handler.getBoolean("disable"), 
				handler.getString("orderField"), handler.getString("orderType"), handler.getInteger("pageNo",1), handler.getInteger("count",20));
		handler.put("page", page).render();
	}

	@Autowired
	private SystemUserService service;

}
