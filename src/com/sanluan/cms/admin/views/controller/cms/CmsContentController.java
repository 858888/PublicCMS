package com.sanluan.cms.admin.views.controller.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.cms.entities.cms.CmsCategory;
import com.sanluan.cms.entities.cms.CmsCategoryModel;
import com.sanluan.cms.entities.cms.CmsContent;
import com.sanluan.cms.entities.cms.CmsSite;
import com.sanluan.cms.logic.component.StaticComponent;
import com.sanluan.cms.logic.service.cms.CmsCategoryModelService;
import com.sanluan.cms.logic.service.cms.CmsCategoryService;
import com.sanluan.cms.logic.service.cms.CmsContentService;
import com.sanluan.cms.logic.service.cms.CmsModelService;
import com.sanluan.cms.logic.service.cms.CmsSiteService;
import com.sanluan.common.base.BaseController;

@Controller
@RequestMapping("cmsContent")
public class CmsContentController extends BaseController {
	@Autowired
	private CmsContentService service;
	@Autowired
	private CmsSiteService siteService;
	@Autowired
	private CmsModelService modelService;
	@Autowired
	private CmsCategoryModelService categoryModelService;
	@Autowired
	private CmsCategoryService categoryService;
	@Autowired
	private StaticComponent staticComponent;

	@RequestMapping(value = { "save" + DO_SUFFIX })
	public String save(CmsContent entity, HttpServletRequest request, ModelMap model) {
		if (null != entity.getId()) {
			service.update(entity.getId(), entity);
		} else {
			service.save(entity);
		}
		CmsSite cmsSite = siteService.getEntity(entity.getSiteId());
		CmsCategory cmsCategory = categoryService.getEntity(entity.getCategoryId());
		CmsCategoryModel categoryModel = categoryModelService.getEntity(entity.getModelId(), entity.getCategoryId());
		if (virifyCustom("static",
				!staticComponent.createStaticFile(categoryModel.getTemplatePath(), cmsCategory.getContentPath(), cmsSite, model),
				model)
				|| virifyCustom("static1", !staticComponent.createStaticFile(categoryModel.getTemplatePath1(),
						cmsCategory.getContentPath1(), cmsSite, model), model)) {
			return "common/ajaxError";
		}
		return "common/ajaxDone";
	}

	@RequestMapping(value = { "static" + DO_SUFFIX })
	public String publish(Integer id, HttpServletRequest request, ModelMap model) {
		CmsContent entity = service.getEntity(id);
		if (null != entity) {
			CmsSite cmsSite = siteService.getEntity(entity.getSiteId());
			CmsCategoryModel categoryModel = categoryModelService.getEntity(entity.getModelId(), entity.getCategoryId());
			CmsCategory cmsCategory = categoryService.getEntity(entity.getCategoryId());
			if (virifyCustom("static", !staticComponent.createStaticFile(categoryModel.getTemplatePath(),
					cmsCategory.getContentPath(), cmsSite, model), model)
					|| virifyCustom("static1", !staticComponent.createStaticFile(categoryModel.getTemplatePath1(),
							cmsCategory.getContentPath1(), cmsSite, model), model)) {
				return "common/ajaxError";
			}
		}
		return "common/ajaxDone";
	}

	@RequestMapping(value = { "delete" + DO_SUFFIX })
	public String delete(Integer id) {
		service.delete(id);
		return "common/ajaxDone";
	}
}