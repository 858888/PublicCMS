package com.sanluan.cms.admin.views.controller.cms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.cms.entities.cms.CmsCategory;
import com.sanluan.cms.entities.cms.CmsCategoryModel;
import com.sanluan.cms.entities.cms.CmsChapter;
import com.sanluan.cms.entities.cms.CmsChapterAttribute;
import com.sanluan.cms.entities.cms.CmsContent;
import com.sanluan.cms.logic.component.StaticComponent;
import com.sanluan.cms.logic.service.cms.CmsCategoryModelService;
import com.sanluan.cms.logic.service.cms.CmsCategoryService;
import com.sanluan.cms.logic.service.cms.CmsChapterAttributeService;
import com.sanluan.cms.logic.service.cms.CmsChapterService;
import com.sanluan.cms.logic.service.cms.CmsContentService;
import com.sanluan.common.base.BaseController;

@Controller
@RequestMapping("cmsChapter")
public class CmsChapterController extends BaseController {
	@Autowired
	private CmsChapterService service;
	@Autowired
	private CmsContentService contentService;
	@Autowired
	private CmsCategoryModelService categoryModelService;
	@Autowired
	private CmsCategoryService categoryService;
	@Autowired
	private CmsChapterAttributeService attributeService;
	@Autowired
	private StaticComponent staticComponent;

	@RequestMapping(value = { "save" + DO_SUFFIX })
	public String save(CmsChapter entity, String txt, HttpServletRequest request, ModelMap model) {
		if (null != entity.getId()) {
			service.update(entity.getId(), entity);
		} else {
			service.save(entity);
		}
		CmsChapterAttribute attribute = attributeService.getEntity(entity.getId());
		if (null != attribute) {
			attribute.setText(txt);
			attributeService.update(attribute.getChapterId(), attribute);
		} else {
			attribute = new CmsChapterAttribute();
			attribute.setChapterId(entity.getId());
			attribute.setText(txt);
			attributeService.save(attribute);
		}
		CmsContent cmsContent = contentService.getEntity(entity.getContentId());
		CmsCategory cmsCategory = categoryService.getEntity(cmsContent.getCategoryId());
		CmsCategoryModel categoryModel = categoryModelService.getEntity(cmsContent.getModelId(), cmsContent.getCategoryId());
		if (virifyNotEmpty("categoryModel", categoryModel, model)
				|| (StringUtils.isNotBlank(categoryModel.getChapterTemplatePath()) || StringUtils.isNotBlank(cmsCategory
						.getChapterPath()))
				&& virifyCustom("static", !staticComponent.createStaticFile(categoryModel.getChapterTemplatePath(),
						cmsCategory.getContentPath(), model), model)) {
			return "common/ajaxError";
		}
		return "common/ajaxDone";
	}

	@RequestMapping(value = { "delete" + DO_SUFFIX })
	public String delete(Integer id) {
		service.delete(id);
		return "common/ajaxDone";
	}
}