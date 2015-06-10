package com.sanluan.cms.web.views.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UrlPathHelper;

import com.sanluan.cms.logic.component.CacheComponent;
import com.sanluan.common.base.BaseController;

@Controller
public class Index extends BaseController {
	@Autowired
	private CacheComponent cacheComponent;

	private UrlPathHelper urlPathHelper = new UrlPathHelper();

	@RequestMapping(value = { "/**" })
	public String page(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String path = urlPathHelper.getLookupPathForRequest(request);
		if (StringUtils.isNoneBlank(path)) {
			path = path.substring(0, path.lastIndexOf("."));
		}
		model.addAttribute("path", path);
		return cacheComponent.getFilePath(path, request, response, model);
	}
}
