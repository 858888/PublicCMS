package com.sanluan.cms.admin.views.controller;

import static com.sanluan.common.constants.CommonConstants.COOKIES_USER;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sanluan.cms.common.tools.UserUtils;
import com.sanluan.cms.entities.log.LogLogin;
import com.sanluan.cms.entities.system.SystemUser;
import com.sanluan.cms.logic.component.CacheComponent;
import com.sanluan.cms.logic.service.log.LogLoginService;
import com.sanluan.cms.logic.service.system.SystemUserService;
import com.sanluan.common.base.BaseController;
import com.sanluan.common.tools.VerificationUtils;

/**
 * @author zhangxd
 * 
 */
@Controller
public class LoginController extends BaseController {
	@Autowired
	private SystemUserService service;
	@Autowired
	private CacheComponent cacheComponent;
	@Autowired
	private LogLoginService logLoginService;

	@RequestMapping(value = { "login" + DO_SUFFIX }, method = RequestMethod.POST)
	public String login(String username, String password, String returnUrl, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if (virifyNotEmpty("username", username, model) || virifyNotEmpty("password", password, model)) {
			model.addAttribute("username", username);
			model.addAttribute("returnUrl", returnUrl);
			return "login";
		}
		SystemUser user = service.findByName(username);
		if (virifyNotEquals("password", VerificationUtils.encode(password), user.getPassword(), model)
				|| virifyNotAdmin(user, model)) {
			model.addAttribute("username", username);
			model.addAttribute("returnUrl", returnUrl);
			LogLogin log = new LogLogin();
			log.setName(username);
			log.setErrorPassword(password);
			log.setIp(UserUtils.getIp(request));
			logLoginService.save(log);
			return "login";
		}
		UserUtils.setAdminToSession(request, user);
		String authToken = UUID.randomUUID().toString();
		UserUtils.addCookie(request, response, COOKIES_USER, authToken, Integer.MAX_VALUE, null);
		service.updateLoginStatus(user.getId(), username, authToken, UserUtils.getIp(request));
		if (StringUtils.isNotBlank(returnUrl)) {
			try {
				returnUrl = URLDecoder.decode(returnUrl, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
			return REDIRECT + returnUrl;
		} else
			return REDIRECT + "index" + PAGE_SUFFIX;
	}

	@RequestMapping(value = { "changePassword" + DO_SUFFIX }, method = RequestMethod.POST)
	public String changePassword(HttpServletRequest request, Integer id, String oldpassword, String password, String repassword,
			ModelMap model) {
		SystemUser user;
		String page = "common/ajaxDone";
		if (null != id) {
			user = service.getEntity(id);
		} else {
			user = UserUtils.getAdminFromSession(request);
			if (virifyNotEquals("password", user.getPassword(), VerificationUtils.encode(oldpassword), model)) {
				return "common/ajaxError";
			} else if (virifyNotEmpty("password", password, model) || virifyNotEquals("repassword", password, repassword, model)) {
				return "common/ajaxError";
			} else {
				UserUtils.clearAdminToSession(request);
				model.addAttribute("message", "common.needReLogin");
				page = "common/ajaxTimeout";
			}
		}
		if (null != user)
			service.updatePassword(user.getId(), UserUtils.getIp(request), VerificationUtils.encode(password));
		return page;
	}

	@RequestMapping(value = { "logout" + DO_SUFFIX }, method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		UserUtils.clearAdminToSession(request);
		return REDIRECT + "index" + PAGE_SUFFIX;
	}
	
	protected boolean virifyNotAdmin(SystemUser user, ModelMap model) {
		if (!user.isSuperuserAccess()) {
			model.addAttribute(ERROR, "verify.user.notAdmin");
			return true;
		}
		return false;
	}

	@RequestMapping(value = { "clearTemplateCache" + DO_SUFFIX })
	public String clearTemplateCache(HttpServletRequest request) {
		cacheComponent.clearTemplateCache();
		return "common/ajaxDone";
	}
}
