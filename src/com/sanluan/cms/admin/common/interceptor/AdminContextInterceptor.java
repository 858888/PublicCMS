package com.sanluan.cms.admin.common.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.UrlPathHelper;

import com.sanluan.cms.common.tools.UserUtils;
import com.sanluan.cms.entities.system.SystemUser;
import com.sanluan.common.base.BaseInterceptor;

/**
 * @author zhangxd
 * 
 */
public class AdminContextInterceptor extends BaseInterceptor {
	private String[] needNotLoginUrls;
	private String loginUrl;
	private String loginJsonUrl;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		if (verify(getURL(request))) {
			SystemUser user = UserUtils.getAdminFromSession(request);
			UrlPathHelper helper = new UrlPathHelper();
			if (null == user) {
				try {
					if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
						response.sendRedirect(helper.getOriginatingContextPath(request) + loginJsonUrl);
					} else {

						response.sendRedirect(helper.getOriginatingContextPath(request) + loginUrl + "?returnUrl="
								+ getURL(request) + getEncodeQueryString(request.getQueryString()));
					}
					return false;
				} catch (IllegalStateException e) {
				} catch (IOException e) {
				}
			} else if (!user.isSuperuserAccess()) {
				try {
					response.sendRedirect(helper.getOriginatingContextPath(request) + loginUrl + "?returnUrl=" + getURL(request)
							+ getEncodeQueryString(request.getQueryString()));
				} catch (IllegalStateException e) {
				} catch (IOException e) {
				}
				return false;
			}
		}
		return true;
	}

	private boolean verify(String url) {
		if (null == loginUrl) {
			return false;
		} else if (null != needNotLoginUrls && null != url) {
			for (String needNotLoginUrl : needNotLoginUrls) {
				if (null != needNotLoginUrl) {
					if (url.startsWith(needNotLoginUrl)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * @param loginUrl
	 *            the loginUrl to set
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * @param needNotLoginUrls
	 *            the needNotLoginUrls to set
	 */
	public void setNeedNotLoginUrls(String[] needNotLoginUrls) {
		this.needNotLoginUrls = needNotLoginUrls;
	}

	/**
	 * @param loginJson
	 *            the loginJson to set
	 */
	public void setLoginJsonUrl(String loginJsonUrl) {
		this.loginJsonUrl = loginJsonUrl;
	}
}
