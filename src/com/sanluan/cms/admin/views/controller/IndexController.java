package com.sanluan.cms.admin.views.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UrlPathHelper;

import com.sanluan.common.base.BaseController;

@Controller
public class IndexController extends BaseController {
	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	@RequestMapping(value = { "/*" })
	public String page(HttpServletRequest request, ModelMap model) {
		return urlPathHelper.getLookupPathForRequest(request);
	}
}
