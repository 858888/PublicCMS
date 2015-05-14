package com.sanluan.cms.web.views.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.cms.logic.component.CacheComponent;
import com.sanluan.common.base.BaseController;

/**
 * @author zhangxd
 * 
 */
@Controller
public class Index extends BaseController {
	@Autowired
	private CacheComponent cacheComponent;

	/**
	 * 一级页面映射
	 * 
	 */
	@RequestMapping(value = { "{path}" + PAGE_SUFFIX })
	public String page(@PathVariable String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return cacheComponent.getFilePath(path, request, response, model);
	}

	/**
	 * 首页映射
	 * 
	 */
	@RequestMapping(value = { DELIMITER, TEMPLATE_INDEX + PAGE_SUFFIX })
	public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return page(TEMPLATE_INDEX, request, response, model);
	}

	/**
	 * 二级页面映射
	 * 
	 */
	@RequestMapping(value = { "{dir}" + DELIMITER + "{path}" + PAGE_SUFFIX })
	public String page(@PathVariable String dir, @PathVariable String path, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return page(dir + DELIMITER + path, request, response, model);
	}

	/**
	 * 二级页面首页映射
	 * 
	 */
	@RequestMapping(value = { "{dir}", "{dir}" + DELIMITER })
	public String index(@PathVariable String dir, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return page(dir + DELIMITER + TEMPLATE_INDEX, request, response, model);
	}

	/**
	 * 三级页面映射
	 * 
	 */
	@RequestMapping(value = { "{parentDir}" + DELIMITER + "{dir}" + DELIMITER + "{path}" + PAGE_SUFFIX })
	public String page(@PathVariable String parentDir, @PathVariable String dir, @PathVariable String path,
			HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return page(parentDir + DELIMITER + dir + DELIMITER + path, request, response, model);
	}

	/**
	 * 三级页面首页映射
	 * 
	 */
	@RequestMapping(value = { "{parentDir}" + DELIMITER + "{dir}", "{parentDir}" + DELIMITER + "{dir}" + DELIMITER })
	public String index(@PathVariable String parentDir, @PathVariable String dir, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return page(parentDir + DELIMITER + dir + DELIMITER + TEMPLATE_INDEX, request, response, model);
	}

}
