package com.sanluan.common.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtils {
	private static Logger log = LoggerFactory.getLogger(FreeMarkerUtils.class);

	public static void makeFileByFile(String templateFilePath, String destFilePath, Configuration config,
			Map<String, Object> model) throws IOException, TemplateException {
		makeFileByFile(templateFilePath, destFilePath, config, model, true, false);
	}

	public static void makeFileByFile(String templateFilePath, String destFilePath, Configuration config,
			Map<String, Object> model, boolean override) throws IOException, TemplateException {
		makeFileByFile(templateFilePath, destFilePath, config, model, override, false);
	}

	public static void makeFileByFile(String templateFilePath, String destFilePath, Configuration config,
			Map<String, Object> model, boolean override, boolean append) throws IOException, TemplateException {
		Template t = config.getTemplate(templateFilePath);
		File destFile = new File(destFilePath);
		if (override || append || !destFile.exists()) {
			File parent = destFile.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFile, append), "utf-8"));
			t.process(model, out);
			out.close();
			log.info(destFilePath + "	保存成功！");
		} else {
			log.error(destFilePath + "	已经存在！");
		}
	}

	public String makeStringByFile(String template, Configuration configuration) {
		return makeStringByFile(template, configuration, new ModelMap());
	}

	public String makeStringByFile(String template, Configuration configuration, ModelMap model) {
		try {
			Template tpl = configuration.getTemplate(template);
			return FreeMarkerTemplateUtils.processTemplateIntoString(tpl, model);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "";
		}
	}

	public static String makeStringByString(String templateContent, Configuration config, Map<String, Object> model)
			throws IOException, TemplateException {
		Template t = new Template(String.valueOf(templateContent.hashCode()), templateContent, config);
		java.io.StringWriter writer = new java.io.StringWriter();
		t.process(null, writer);
		return writer.toString();
	}
}
