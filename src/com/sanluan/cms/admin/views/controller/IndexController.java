package com.sanluan.cms.admin.views.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UrlPathHelper;

import com.sanluan.common.base.BaseController;

@Controller
public class IndexController extends BaseController {
	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	@RequestMapping(value = { "/**" })
	public String page(HttpServletRequest request, ModelMap model) {
		String path = urlPathHelper.getLookupPathForRequest(request);
		if (StringUtils.isNoneBlank(path)) {
			path = path.substring(0, path.lastIndexOf("."));
		}
		model.addAttribute("path", path);
		return path;
	}
}
