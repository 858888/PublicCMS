package com.sanluan.common.source;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.sanluan.common.source.entity.EntityColumn;
import com.sanluan.common.source.entity.EntityCondition;
import com.sanluan.common.source.entity.MyColumn;
import com.sanluan.common.tools.FreeMarkerUtils;
import com.sanluan.common.tools.MyClassUtils;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class Maker {
	public static final String ENTITY_BASE_PACKAGE = "entities";
	public static final String DAO_BASE_PACKAGE = "logic.dao";
	public static final String DAO_SUFFIX = "Dao";
	public static final String SERVICE_BASE_PACKAGE = "logic.service";
	public static final String SERVICE_SUFFIX = "Service";
	public static final String DIRECTIVE_BASE_PACKAGE = "views.directive";
	public static final String DIRECTIVE_SUFFIX = "Directive";
	public static final String CONTROLLER_BASE_PACKAGE = "admin.views.controller";
	public static final String CONTROLLER_SUFFIX = "Controller";
	public static final String WEB_BASE_PATH = "WebRoot/WEB-INF/template/admin/bak/";

	public static void main(String[] args) {
		Maker sourceMaker = new Maker();
		boolean overwrite = false;
		sourceMaker.make("com.sanluan.cms", "cmsbak", overwrite);
	}

	public void make(String basePackage) {
		make(basePackage, true);
	}

	public void make(String basePackage, boolean overwrite) {
		String entitiesFullPackage = basePackage + "." + ENTITY_BASE_PACKAGE;
		for (Class<?> c : MyClassUtils.getClasses(entitiesFullPackage)) {
			make(c, basePackage, overwrite);
		}
	}

	public void make(String basePackage, String entityPackage) {
		make(basePackage, entityPackage, true);
	}

	public void make(String basePackage, String entityPackage, boolean overwrite) {
		String entitiesFullPackage = basePackage + "." + ENTITY_BASE_PACKAGE + "." + entityPackage;
		for (Class<?> c : MyClassUtils.getClasses(entitiesFullPackage)) {
			make(c, basePackage, overwrite);
		}
	}

	public void make(Class<?> c, String basePackage, boolean overwrite) {
		String name = c.getSimpleName();
		String base = basePackage + "." + ENTITY_BASE_PACKAGE + ".";
		String entitiesPackage = c.getPackage().getName().substring((base).length(), c.getPackage().getName().length());

		System.out.println("entity:" + base + entitiesPackage + "." + name);
		Map<String, Object> model = new HashMap<String, Object>();

		String entityPack = ENTITY_BASE_PACKAGE + "." + entitiesPackage;
		String daoPack = DAO_BASE_PACKAGE + "." + entitiesPackage;
		String servicePack = SERVICE_BASE_PACKAGE + "." + entitiesPackage;
		String directivePack = DIRECTIVE_BASE_PACKAGE + "." + entitiesPackage;
		String controllerPack = CONTROLLER_BASE_PACKAGE + "." + entitiesPackage;

		model.put("base", basePackage);
		model.put("entityPack", entityPack);
		model.put("daoPack", daoPack);
		model.put("daoSuffix", DAO_SUFFIX);
		model.put("servicePack", servicePack);
		model.put("serviceSuffix", SERVICE_SUFFIX);
		model.put("directivePack", directivePack);
		model.put("directiveSuffix", DIRECTIVE_SUFFIX);
		model.put("controllerPack", controllerPack);
		model.put("controllerSuffix", CONTROLLER_SUFFIX);
		model.put("entityName", name);

		Set<String> imports = new HashSet<String>();
		Map<String, EntityCondition> conditionMap = new HashMap<String, EntityCondition>();
		List<EntityColumn> columnList = new ArrayList<EntityColumn>();

		for (Field field : c.getDeclaredFields()) {
			if ((field.getModifiers() & Modifier.PRIVATE) != 0 && (field.getModifiers() & Modifier.FINAL) == 0) {
				String typeName = field.getType().getName();
				if ("int" == typeName) {
					typeName = "java.lang.Integer";
				} else if ("short" == typeName) {
					typeName = "java.lang.Short";
				} else if ("long" == typeName) {
					typeName = "java.lang.Long";
				} else if ("byte" == typeName) {
					typeName = "java.lang.Byte";
				} else if ("char" == typeName) {
					typeName = "java.lang.Charactor";
				} else if ("double" == typeName) {
					typeName = "java.lang.Double";
				} else if ("float" == typeName) {
					typeName = "java.lang.Float";
				} else if ("boolean" == typeName) {
					typeName = "java.lang.Boolean";
				}

				MyColumn column = field.getAnnotation(MyColumn.class);
				if (null != column) {
					String shortTypeName = typeName.substring(typeName.lastIndexOf(".") + 1, typeName.length());
					columnList.add(new EntityColumn(field.getName(), shortTypeName, column.order(), column.title()));
					if (column.condition()) {
						if (!typeName.startsWith("java.lang"))
							imports.add(typeName);
						String key;
						if (StringUtils.isNoneBlank(column.name())) {
							key = column.name();
						} else {
							key = field.getName();
						}
						EntityCondition condition = conditionMap.get(key);
						if (null == condition) {
							condition = new EntityCondition(key, shortTypeName, column.title(), column.or(), column.like());
						}
						condition.getNameList().add(field.getName());
						conditionMap.put(key, condition);
					}
				}
			}
		}
		model.put("imports", imports);
		model.put("columnList", columnList);
		model.put("conditionList", conditionMap.values());

		String daoPath = "src/" + basePackage.replace('.', '/') + "/" + daoPack.replace('.', '/') + "/";
		String servicePath = "src/" + basePackage.replace('.', '/') + "/" + servicePack.replace('.', '/') + "/";
		String directivePath = "src/" + basePackage.replace('.', '/') + "/" + directivePack.replace('.', '/') + "/";
		String controllerPath = "src/" + basePackage.replace('.', '/') + "/" + controllerPack.replace('.', '/') + "/";

		try {
			FreeMarkerUtils.makeFileByFile("java/dao.ftl", daoPath + name + DAO_SUFFIX + ".java", config, model, overwrite);
			FreeMarkerUtils.makeFileByFile("java/service.ftl", servicePath + name + SERVICE_SUFFIX + ".java", config, model,
					overwrite);
			FreeMarkerUtils.makeFileByFile("java/directive.ftl", directivePath + name + DIRECTIVE_SUFFIX + ".java", config,
					model, overwrite);
			FreeMarkerUtils.makeFileByFile("java/directiveList.ftl", directivePath + name + "List" + DIRECTIVE_SUFFIX + ".java",
					config, model, overwrite);
			FreeMarkerUtils.makeFileByFile("java/controller.ftl", controllerPath + name + CONTROLLER_SUFFIX + ".java", config,
					model, overwrite);
			FreeMarkerUtils.makeFileByFile("html/list.ftl", WEB_BASE_PATH + StringUtils.uncapitalize(name) + "/list.html",
					config, model, overwrite);
			FreeMarkerUtils.makeFileByFile("html/add.ftl", WEB_BASE_PATH + StringUtils.uncapitalize(name) + "/add.html", config,
					model, overwrite);
			FreeMarkerUtils.makeFileByFile("html/doc.ftl", WEB_BASE_PATH + "doc.txt", config, model, overwrite, true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		System.out.println("-------------------------------");
	}

	private Configuration config;

	public Maker() {
		config = new freemarker.template.Configuration(Configuration.getVersion());
		config.setClassForTemplateLoading(this.getClass(), "");
		config.setDefaultEncoding("utf-8");
	}

}
