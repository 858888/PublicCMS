package com.sanluan.common.base;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.sanluan.common.tools.RequestUtils;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * @author zhangxd
 * 
 */
public abstract class BaseInterceptor extends HandlerInterceptorAdapter {

	protected String getEncodeQueryString(String queryString) {
		String encodeQueryString = "";
		if (StringUtils.isNotBlank(queryString)) {
			try {
				encodeQueryString = URLEncoder.encode("?" + queryString, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		return encodeQueryString;
	}

	/**
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 */
	protected static boolean checkComputer(HttpServletRequest request) {
		UserAgent userAgent = UserAgent.parseUserAgentString(RequestUtils.getUserAgent(request));
		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
		return operatingSystem.getDeviceType() == DeviceType.COMPUTER;
	}

	/**
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 */
	protected static String getURL(HttpServletRequest request) throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String url = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		if (StringUtils.isNotBlank(ctxPath)) {
			url = url.substring(url.indexOf(ctxPath) + ctxPath.length());
		}
		return url;
	}

}
