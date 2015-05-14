package com.sanluan.cms.web.common.interceptor;

import static com.sanluan.common.constants.CommonConstants.COOKIES_USER;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.util.UrlPathHelper;

import com.sanluan.cms.common.tools.UserUtils;
import com.sanluan.cms.entities.system.SystemUser;
import com.sanluan.cms.logic.service.system.SystemUserService;
import com.sanluan.common.base.BaseInterceptor;
import com.sanluan.common.tools.SpringContextUtils;

/**
 * @author zhangxd
 * 
 */
public class ContextInterceptor extends BaseInterceptor{
	private String[] needLoginUrls;
	private String loginUrl;
	private SystemUserService systemUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		SystemUser user = UserUtils.getUserFromSession(request);
		if (null == user) {
			Cookie userCookie = UserUtils.getCookie(request, COOKIES_USER);
			if (null != userCookie && StringUtils.isNotBlank(userCookie.getValue())) {
				user = getSystemUserService().findByAuthToken(userCookie.getValue());
				if (null != user) {
					UserUtils.setUserToSession(request, user);
				} else {
					UserUtils.cancleCookie(request, response, COOKIES_USER, null);
				}
			}
		} else {
			Date date = UserUtils.getUserTimeFromSession(request);
			if (null == date || date.before(DateUtils.addSeconds(new Date(), -30))) {
				user = getSystemUserService().getEntity(user.getId());
				UserUtils.setUserToSession(request, user);
			}
		}

		if (verify(getURL(request))) {
			UrlPathHelper helper = new UrlPathHelper();
			if (null != user && user.isDisable()) {
				user = null;
				UserUtils.clearUserToSession(request, response);
				UserUtils.cancleCookie(request, response, COOKIES_USER, null);
			}
			if (null == user) {
				try {
					response.sendRedirect(helper.getOriginatingContextPath(request) + loginUrl + "?returnUrl=" + getURL(request)
							+ getEncodeQueryString(request.getQueryString()));
					return false;
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	private SystemUserService getSystemUserService() {
		if (null == systemUserService) {
			systemUserService = SpringContextUtils.getApplicationContext().getBean(SystemUserService.class);
		}
		return systemUserService;
	}

	private boolean verify(String url) {
		if (null != needLoginUrls && null != loginUrl && null != url) {
			for (String needLoginUrl : needLoginUrls) {
				if (null != needLoginUrl) {
					if (url.startsWith(needLoginUrl)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param needLoginUrls
	 *            the needLoginUrls to set
	 */
	public void setNeedLoginUrls(String[] needLoginUrls) {
		this.needLoginUrls = needLoginUrls;
	}

	/**
	 * @param loginUrl
	 *            the loginUrl to set
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
}
