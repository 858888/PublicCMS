package com.sanluan.common.base;

import java.io.IOException;
import java.util.Map;

import com.sanluan.common.handler.DirectiveHandler;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author zhangxd
 * 
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment environment, @SuppressWarnings("rawtypes") Map parameters, TemplateModel[] templateModel,
			TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
		execute(new DirectiveHandler(environment, parameters, templateDirectiveBody));
	}

	public abstract void execute(DirectiveHandler handler) throws TemplateException, IOException;
}
