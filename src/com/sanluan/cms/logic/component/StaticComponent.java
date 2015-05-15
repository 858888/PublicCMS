package com.sanluan.cms.logic.component;

import static com.sanluan.common.constants.FreeMakerConstants.CONTEXT_BASE;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanluan.cms.entities.cms.CmsSite;
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
	private String templateDataPath;

	private ObjectMapper objectMapper = new ObjectMapper();
	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	private Configuration configuration;

	public boolean createStaticFile(String templatePath, String filePath, CmsSite site, ModelMap model) {
		try {
			if (StringUtils.isNotBlank(filePath)) {
				model = (ModelMap) model.clone();
				model.put(CONTEXT_BASE, site.getSitePath());
				model.put("site", site);
				FreeMarkerUtils.makeFileByFile(templatePath, getHtmlFilePath(site.getHtmlPath() + filePath),
						freeMarkerConfigurer.getConfiguration(), model);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String getHtmlFilePath(String filePath) {
		if (StringUtils.isBlank(staticFileDirectory)) {
			staticFileDirectory = resourceLoader.getResource("/static/").getFilename();
		}
		return staticFileDirectory + filePath;
	}

	public static void main(String arg[]) {
		StaticComponent s = new StaticComponent();
		// Map map = new HashMap();
		// Map map1 = new HashMap();
		// map1.put("description", "eclipse工程");
		// map.put("eclipse", map1);
		// try {
		// s.objectMapper.writeValue(new File("e:/metadata.data"), map);
		// } catch (JsonGenerationException e) {
		// e.printStackTrace();
		// } catch (JsonMappingException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		List<FileInfo> list = s.getFileList("e:/");
		for (FileInfo f : list) {
			System.out.println(f.getFileName() + " " + f.getDescription());
		}
	}

	public List<FileInfo> getFileList(String dirPath) {
		List<FileInfo> dirList = new ArrayList<FileInfo>();
		List<FileInfo> fileList = new ArrayList<FileInfo>();
		Map<?, ?> map = null;
		try {
			map = objectMapper.readValue(new File(dirPath + "/metadata.data"), Map.class);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		try {
			DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirPath));
			for (Path entry : stream) {
				BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
				String fileName = entry.getFileName().toString();
				String description = null;
				if (null != map) {
					Map<?, ?> infoMap = (Map<?, ?>) map.get(fileName);
					if (null != infoMap)
						description = (String) infoMap.get("description");
				}
				if (attrs.isDirectory()) {
					dirList.add(new FileInfo(fileName, description, true, attrs));
				} else {
					if (!"metadata.data".equalsIgnoreCase(fileName))
						fileList.add(new FileInfo(fileName, description, false, attrs));
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());
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
		if (StringUtils.isBlank(templateDataPath)) {
			templateDataPath = resourceLoader.getResource("/data/").getFilename();
		}
		return templateDataPath + templatePath + ".data";
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

	/**
	 * @param templateDataPath
	 *            the templateDataPath to set
	 */
	public void setTemplateDataPath(String templateDataPath) {
		this.templateDataPath = templateDataPath;
	}

}

class FileInfo {
	private String fileName;
	private String description;
	private boolean isDirectory;
	private Date lastModifiedTime;
	private Date lastAccessTime;
	private Date creationTime;
	private long size;

	public FileInfo(String fileName, String description, boolean isDirectory) {
		this.fileName = fileName;
		this.description = description;
		this.isDirectory = isDirectory;
	}

	public FileInfo(String fileName, String description, boolean isDirectory, BasicFileAttributes attrs) {
		this.fileName = fileName;
		this.description = description;
		this.isDirectory = isDirectory;
		this.lastModifiedTime = new Date(attrs.lastModifiedTime().toMillis());
		this.lastAccessTime = new Date(attrs.lastAccessTime().toMillis());
		this.creationTime = new Date(attrs.creationTime().toMillis());
		this.size = attrs.size();
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the isDirectory
	 */
	public boolean isDirectory() {
		return isDirectory;
	}

	/**
	 * @param isDirectory
	 *            the isDirectory to set
	 */
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	/**
	 * @return the lastModifiedTime
	 */
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	/**
	 * @param lastModifiedTime
	 *            the lastModifiedTime to set
	 */
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	/**
	 * @return the lastAccessTime
	 */
	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	/**
	 * @param lastAccessTime
	 *            the lastAccessTime to set
	 */
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	/**
	 * @param creationTime
	 *            the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
