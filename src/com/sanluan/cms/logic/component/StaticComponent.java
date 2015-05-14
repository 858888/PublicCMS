package com.sanluan.cms.logic.component;

import static com.sanluan.common.constants.FreeMakerConstants.CONTEXT_BASE;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.sanluan.common.tools.FreeMarkerUtils;

import freemarker.template.Configuration;

/**
 * @author zhangxd
 * 
 */
@Component
public class StaticComponent {
	private String staticFileDirectory;
	private String templateLoaderPath;

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	private Configuration configuration;

	public boolean createStaticFile(String templatePath, String filePath, HttpServletRequest request, ModelMap model) {
		try {
			if (StringUtils.isNotBlank(filePath)) {
				model = (ModelMap) model.clone();
				model.put(CONTEXT_BASE, request.getContextPath());
				FreeMarkerUtils.makeFileByFile(templatePath, getHtmlFilePath(request, filePath),
						freeMarkerConfigurer.getConfiguration(), model);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String getHtmlFilePath(HttpServletRequest request, String filePath) {
		if (StringUtils.isBlank(staticFileDirectory)) {
			staticFileDirectory = request.getSession().getServletContext().getRealPath("/static/");
		}
		return staticFileDirectory + filePath;
	}

	/**
	 * @param staticFileDirectory
	 *            the staticFileDirectory to set
	 */
	public void setStaticFileDirectory(String staticFileDirectory) {
		this.staticFileDirectory = staticFileDirectory;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		if (null == configuration) {
			try {
				configuration = freeMarkerConfigurer.getConfiguration();
				if (StringUtils.isNotBlank(templateLoaderPath)) {
					configuration = (Configuration) configuration.clone();
					configuration.setDirectoryForTemplateLoading(new File(templateLoaderPath));
				}
			} catch (IOException e) {
			}
		}
		return configuration;
	}

	/**
	 * @param templateLoaderPath
	 *            the templateLoaderPath to set
	 */
	public void setTemplateLoaderPath(String templateLoaderPath) {
		this.templateLoaderPath = templateLoaderPath;
	}

}
