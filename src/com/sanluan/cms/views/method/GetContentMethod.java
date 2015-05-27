package com.sanluan.cms.views.method;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.entities.cms.CmsContentAttribute;
import com.sanluan.cms.logic.service.cms.CmsContentAttributeService;
import com.sanluan.common.base.BaseMethod;

import freemarker.template.TemplateModelException;

/**
 * @author zhangxd
 *
 */
@Component
public class GetContentMethod extends BaseMethod {

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		CmsContentAttribute bean = service.getEntity(getInt(0, arguments));
		if (null != bean)
			return bean.getText();
		else
			return null;
	}

	@Autowired
	private CmsContentAttributeService service;
}
