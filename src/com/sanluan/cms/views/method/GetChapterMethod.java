package com.sanluan.cms.views.method;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.entities.cms.CmsChapterAttribute;
import com.sanluan.cms.logic.service.cms.CmsChapterAttributeService;
import com.sanluan.common.base.BaseMethod;

import freemarker.template.TemplateModelException;

/**
 * @author zhangxd
 *
 */
@Component
public class GetChapterMethod extends BaseMethod {

	/*
	 * (non-Javadoc)
	 * 
	 * @see freemarker.template.TemplateMethodModelEx#exec(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments) throws TemplateModelException {
		Integer id = getInteger(0, arguments);
		if (null != id) {
			CmsChapterAttribute bean = service.getEntity(id);
			if (null != bean)
				return bean.getText();
		}
		return null;
	}

	@Autowired
	private CmsChapterAttributeService service;
}
