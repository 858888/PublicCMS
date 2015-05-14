package com.sanluan.cms.common.servlet.view;

import static com.sanluan.common.constants.FreeMakerConstants.CONTEXT_BASE;
import static com.sanluan.common.constants.FreeMakerConstants.CONTEXT_INCLUDE;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.sanluan.cms.common.tools.UserUtils;

import freemarker.ext.servlet.IncludePage;

/**
 * @author zhangxd
 * 
 */
public class InitializeFreeMarkerView extends FreeMarkerView {
	private static final String CONTEXT_USER = "user";
	private static final String CONTEXT_ADMIN = "admin";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.view.freemarker.FreeMarkerView#exposeHelpers
	 * (java.util.Map, javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exposeHelpers(Map model, HttpServletRequest request) throws Exception {
		super.exposeHelpers(model, request);
		model.put(CONTEXT_USER, UserUtils.getUserFromSession(request));
		model.put(CONTEXT_ADMIN, UserUtils.getAdminFromSession(request));
		model.put(CONTEXT_BASE, request.getContextPath());
		Enumeration<String> parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			String paramterName = parameters.nextElement();
			model.put(paramterName, request.getParameter(paramterName));
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doRender(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		model.put(CONTEXT_INCLUDE, new IncludePage(request, response));
		super.doRender(model, request, response);
	}

}