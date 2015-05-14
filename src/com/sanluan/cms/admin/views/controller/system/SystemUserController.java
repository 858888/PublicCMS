package com.sanluan.cms.admin.views.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sanluan.cms.common.tools.UserUtils;
import com.sanluan.cms.entities.system.SystemUser;
import com.sanluan.cms.logic.service.system.SystemUserService;
import com.sanluan.common.base.BaseController;

/**
 * @author zhangxd
 * 
 */
@Controller
@RequestMapping("systemuser")
public class SystemUserController extends BaseController {
	@Autowired
	private SystemUserService service;

	@RequestMapping(value = { "enable" + DO_SUFFIX }, method = RequestMethod.POST)
	public String enable(HttpServletRequest request, Integer id, String repassword, ModelMap model) {
		if (virifyEquals("admin.operate", UserUtils.getAdminFromSession(request), id, model)) {
			return "common/ajaxError";
		}
		service.updateStatus(id, false);
		return "common/ajaxDone";
	}

	@RequestMapping(value = { "disable" + DO_SUFFIX }, method = RequestMethod.POST)
	public String disable(HttpServletRequest request, Integer id, String repassword, ModelMap model) {
		if (virifyEquals("admin.operate", UserUtils.getAdminFromSession(request), id, model)) {
			return "common/ajaxError";
		}
		service.updateStatus(id, true);
		return "common/ajaxDone";
	}

	protected boolean virifyEquals(String field, SystemUser user, Integer value2, ModelMap model) {
		if (null != user && user.getId().equals(value2)) {
			model.addAttribute(ERROR, "verify.equals." + field);
			return true;
		}
		return false;
	}


}
