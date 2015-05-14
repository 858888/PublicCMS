package com.sanluan.cms.admin.views.controller.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.cms.entities.cms.CmsCategory;
import com.sanluan.cms.entities.cms.CmsContent;
import com.sanluan.cms.entities.cms.CmsModel;
import com.sanluan.cms.logic.component.StaticComponent;
import com.sanluan.cms.logic.service.cms.CmsCategoryService;
import com.sanluan.cms.logic.service.cms.CmsContentService;
import com.sanluan.cms.logic.service.cms.CmsModelService;
import com.sanluan.common.base.BaseController;

@Controller
@RequestMapping("cmsContent")
public class CmsContentController extends BaseController {
	@Autowired
	private CmsContentService service;
	@Autowired
	private CmsModelService modelService;
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
		CmsModel cmsModel = modelService.getEntity(entity.getModelId());
		CmsCategory cmsCategory = categoryService.getEntity(entity.getCategoryId());
		if (virifyCustom("static",
				!staticComponent.createStaticFile(cmsModel.getTemplatePath(), cmsCategory.getContentPath(), request, model),
				model)
				|| virifyCustom("static1", !staticComponent.createStaticFile(cmsModel.getTemplatePath1(),
						cmsCategory.getContentPath1(), request, model), model)) {
			return "common/ajaxError";
		}
		return "common/ajaxDone";
	}

	@RequestMapping(value = { "static" + DO_SUFFIX })
	public String publish(Integer id, HttpServletRequest request, ModelMap model) {
		CmsContent entity = service.getEntity(id);
		if (null != entity) {
			CmsModel cmsModel = modelService.getEntity(entity.getModelId());
			CmsCategory cmsCategory = categoryService.getEntity(entity.getCategoryId());
			if (virifyCustom("static",
					!staticComponent.createStaticFile(cmsModel.getTemplatePath(), cmsCategory.getContentPath(), request, model),
					model)
					|| virifyCustom("static1", !staticComponent.createStaticFile(cmsModel.getTemplatePath1(),
							cmsCategory.getContentPath1(), request, model), model)) {
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