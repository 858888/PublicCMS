package com.sanluan.cms.logic.component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanluan.cms.common.bean.FileInfo;
import com.sanluan.common.tools.FreeMarkerUtils;

import freemarker.template.Configuration;

/**
 * @author zhangxd
 * 
 */
public class StaticComponent {
	private String staticFileDirectory;
	private String templateLoaderPath;
	private String templateDataPath;
	private String uploadPath;

	private ObjectMapper objectMapper = new ObjectMapper();
	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	private Configuration configuration;

	public boolean createStaticFile(String templatePath, String filePath, ModelMap model) {
		try {
			if (StringUtils.isNotBlank(filePath)) {
				model = (ModelMap) model.clone();
				filePath = FreeMarkerUtils.makeStringByString(filePath, getConfiguration(), model);
				FreeMarkerUtils.makeFileByFile(templatePath, getHtmlFilePath(filePath), getConfiguration(), model);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<FileInfo> getFileList(String dirPath) {
		List<FileInfo> dirList = new ArrayList<FileInfo>();
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		Map<?, ?> map = null;
		try {
			map = objectMapper.readValue(new File(dirPath + "/metadata.data"), Map.class);
		} catch (IOException e) {
		}
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirPath));
			for (Path entry : stream) {
				BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
				String fileName = entry.getFileName().toString();
				String description = fileName;
				String path = fileName;
				if (null != map) {
					Map<?, ?> infoMap = (Map<?, ?>) map.get(fileName);
					if (null != infoMap) {
						if (null != infoMap.get("description"))
							description = (String) infoMap.get("description");
						if (null != infoMap.get("path"))
							path = (String) infoMap.get("path");
					}
				}
				if (attrs.isDirectory()) {
					if (!fileName.toLowerCase().endsWith(".include"))
						dirList.add(new FileInfo(fileName, path, description, true, attrs));
				} else {
					if (!"metadata.data".equalsIgnoreCase(fileName))
						fileList.add(new FileInfo(fileName, path, description, false, attrs));
				}
			}
		} catch (IOException e) {
		}
		dirList.addAll(fileList);
		return dirList;
	}

	public void saveData(String filePath, String data) throws IOException {
		File file = new File(filePath);
		if (file.exists()) {
			File backFile = new File(filePath + System.currentTimeMillis() + ".bak");
			FileUtils.copyFile(file, backFile);
		} else {
			file.getParentFile().mkdirs();
		}
		FileUtils.writeStringToFile(file, data);
	}

	public String readData(String filePath) throws IOException {
		File file = new File(filePath);
		return FileUtils.readFileToString(file);
	}

	public String getDataFilePath(String templatePath) {
		if (null == templateDataPath) {
			try {
				templateDataPath = resourceLoader.getResource("../data/").getFile().getAbsolutePath() + "/";
			} catch (IOException e) {
			}
		}
		return templateDataPath + templatePath + ".data";
	}

	public String getHtmlFilePath(String filePath) {
		if (null == staticFileDirectory) {
			try {
				staticFileDirectory = resourceLoader.getResource("../../static/").getFile().getAbsolutePath() + "/";
			} catch (IOException e) {
			}
		}
		return staticFileDirectory + filePath;
	}

	public String getTemplateFilePath(String templatePath) {
		if (null == templateLoaderPath) {
			try {
				templateLoaderPath = resourceLoader.getResource("../template/web/").getFile().getAbsolutePath() + "/";
			} catch (IOException e) {
			}
		}
		return templateLoaderPath + templatePath;
	}

	public String getUploadFilePath(String filePath) {
		if (null == uploadPath) {
			try {
				uploadPath = resourceLoader.getResource("../../resource/upload/").getFile().getAbsolutePath() + "/";
			} catch (IOException e) {
			}
		}
		return uploadPath + filePath;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		if (null == configuration) {
			try {
				configuration = freeMarkerConfigurer.getConfiguration();
				configuration = (Configuration) configuration.clone();
				configuration.setDirectoryForTemplateLoading(new File(getTemplateFilePath("")));
				configuration.setAutoImports(new HashMap<String, String>());
				configuration.setAutoIncludes(new ArrayList<String>());
			} catch (IOException e) {
			}
		}
		return configuration;
	}

	/**
	 * @param staticFileDirectory
	 *            the staticFileDirectory to set
	 */
	public void setStaticFileDirectory(String staticFileDirectory) {
		if (null != staticFileDirectory && !(staticFileDirectory.endsWith("/") || staticFileDirectory.endsWith("\\"))) {
			staticFileDirectory += "/";
		}
		this.staticFileDirectory = staticFileDirectory;
	}

	/**
	 * @param templateLoaderPath
	 *            the templateLoaderPath to set
	 */
	public void setTemplateLoaderPath(String templateLoaderPath) {
		if (null != templateLoaderPath && !(templateLoaderPath.endsWith("/") || templateLoaderPath.endsWith("\\"))) {
			templateLoaderPath += "/";
		}
		this.templateLoaderPath = templateLoaderPath;
	}

	/**
	 * @param templateDataPath
	 *            the templateDataPath to set
	 */
	public void setTemplateDataPath(String templateDataPath) {
		if (null != templateDataPath && !(templateDataPath.endsWith("/") || templateDataPath.endsWith("\\"))) {
			templateDataPath += "/";
		}
		this.templateDataPath = templateDataPath;
	}

	/**
	 * @param uploadPath
	 *            the uploadPath to set
	 */
	public void setUploadPath(String uploadPath) {
		if (null != uploadPath && !(uploadPath.endsWith("/") || uploadPath.endsWith("\\"))) {
			uploadPath += "/";
		}
		this.uploadPath = uploadPath;
	}

}
