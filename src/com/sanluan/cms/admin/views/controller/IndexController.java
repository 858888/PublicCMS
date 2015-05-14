package com.sanluan.cms.admin.views.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.common.base.BaseController;

@Controller
public class IndexController extends BaseController {
	@RequestMapping(value = { "{path}" + PAGE_SUFFIX })
	public String page(HttpServletRequest request, @PathVariable String path, ModelMap model) {
		return path;
	}

	@RequestMapping(value = { DELIMITER })
	public String page(HttpServletRequest request, ModelMap model) {
		return page(request, TEMPLATE_INDEX, model);
	}

	@RequestMapping(value = { "{dir}", "{dir}" + DELIMITER })
	public String domainAndPage(HttpServletRequest request, @PathVariable String dir, ModelMap model) {
		return domainAndPage(request, dir, TEMPLATE_INDEX, model);
	}

	@RequestMapping(value = { "{dir}" + DELIMITER + "{path}" + PAGE_SUFFIX })
	public String domainAndPage(HttpServletRequest request, @PathVariable String dir, @PathVariable String path, ModelMap model) {
		return dir + DELIMITER + page(request, path, model);
	}
}
