package com.sanluan.cms.logic.component;

import static com.sanluan.common.constants.CommonConstants.NUMBER_PATTERN;
import static com.sanluan.common.constants.CommonConstants.PATH_DELIMITER;
import static com.sanluan.common.constants.FreeMakerConstants.CACHE_VAR;
import static com.sanluan.common.constants.FreeMakerConstants.CONTEXT_BASE;
import static com.sanluan.common.constants.FreeMakerConstants.CONTEXT_INCLUDE;
import static com.sanluan.common.constants.FreeMakerConstants.TEMPLATE_SUFFIX;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.sanluan.common.tools.FreeMarkerUtils;

import freemarker.ext.servlet.IncludePage;

/**
 * @author zhangxd
 * 
 */
@Component
public class CacheComponent {
	private String cacheFileDirectory;
	private Map<Integer, String[]> cacheFilePaths = new HashMap<Integer, String[]>();

	private String basePath;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * @param path
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	public String getFilePath(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String templatePath = path;
		int dirEndIndex = path.lastIndexOf(PATH_DELIMITER);
		String lastPath = path.substring(dirEndIndex + PATH_DELIMITER.length(), path.length());
		if (dirEndIndex > 0) {
			if (virifyNotNumber(lastPath)) {
				templatePath = path;
			} else {
				model.addAttribute("id", lastPath);
				templatePath = path.substring(0, dirEndIndex);
			}
		}
		model = (ModelMap) model.clone();

		Enumeration<String> parameters = request.getParameterNames();
		StringBuilder sb = new StringBuilder();
		while (parameters.hasMoreElements()) {
			String paramterName = parameters.nextElement();
			if (!paramterName.startsWith("spm")) {
				if (0 == sb.length()) {
					sb.append(PATH_DELIMITER);
				} else {
					sb.append("&");
				}
				sb.append(paramterName);
				sb.append("=");
				sb.append(request.getParameter(paramterName));
				model.put(paramterName, request.getParameter(paramterName));
			}
		}
		path += sb.toString();
		model.put(CONTEXT_INCLUDE, new IncludePage(request, response));
		model.put(CACHE_VAR, true);
		model.put(CONTEXT_BASE, request.getContextPath());
		return createCache(templatePath, path, getBasePath(request), model, response);
	}

	public void deleteCacheFile(HttpServletRequest request, String path) {
		String htmlFilePath = getHtmlFilePath(getBasePath(request), path);
		File cacheFile = new File(htmlFilePath);
		if (cacheFile.exists()) {
			cacheFile.delete();
		}
		File cacheDir = new File(htmlFilePath.substring(0, htmlFilePath.lastIndexOf(TEMPLATE_SUFFIX)));
		if (cacheDir.isDirectory()) {
			cacheFile.delete();
		}
	}

	public void clearTemplateCache() {
		freeMarkerConfigurer.getConfiguration().clearTemplateCache();
	}

	private String getBasePath(HttpServletRequest request) {
		return null == basePath ? (basePath = request.getSession().getServletContext().getRealPath("/")) : basePath;
	}

	private String createCache(String templatePath, String path, String basePath, ModelMap model, HttpServletResponse response) {
		int time = verify(templatePath);
		if (0 != time) {
			String htmlPath = getHtmlFilePath(basePath, path);
			String cachePath = cacheFileDirectory + path;
			if (check(htmlPath, time)) {
				return cachePath;
			} else {
				response.setCharacterEncoding("UTF-8");
				try {
					FreeMarkerUtils.makeFileByFile(getTemplateFilePath(templatePath), htmlPath,
							freeMarkerConfigurer.getConfiguration(), model);
					return cachePath;
				} catch (Exception e) {
					return templatePath;
				}
			}
		} else {
			return templatePath;
		}
	}

	private boolean check(String templatePath, int time) {
		File dest = new File(templatePath);
		if (dest.exists()) {
			if ((new Date(dest.lastModified())).after(DateUtils.addSeconds(new Date(), -time))) {
				return true;
			}
		}
		return false;
	}

	private boolean virifyNotNumber(String value) {
		Pattern p = Pattern.compile(NUMBER_PATTERN);
		Matcher m = p.matcher(value);
		if (!m.matches()) {
			return true;
		}
		return false;
	}

	private int verify(String path) {
		for (Integer time : cacheFilePaths.keySet()) {
			for (String cachePath : cacheFilePaths.get(time)) {
				if (path.startsWith(cachePath)) {
					return time;
				}
			}
		}
		return 0;
	}

	private String getHtmlFilePath(String realPath, String path) {
		return realPath + cacheFileDirectory + path + TEMPLATE_SUFFIX;
	}

	private String getTemplateFilePath(String path) {
		return path + TEMPLATE_SUFFIX;
	}

	/**
	 * @param cacheFileDirectory
	 *            the cacheFileDirectory to set
	 */
	public void setCacheFileDirectory(String cacheFileDirectory) {
		this.cacheFileDirectory = cacheFileDirectory;
	}

	/**
	 * @param cachePaths
	 *            the cachePaths to set
	 */
	public void setCachePaths(Map<Integer, String> cachePaths) {
		if (null != cachePaths) {
			for (Integer time : cachePaths.keySet()) {
				String value = cachePaths.get(time);
				if (StringUtils.isNotBlank(value)) {
					cacheFilePaths.put(time, StringUtils.split(value, ","));
				}
			}
		}
	}
}
