package com.sanluan.cms.admin.views.controller.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.cms.common.tools.UserUtils;
import com.sanluan.cms.entities.cms.CmsCategory;
import com.sanluan.cms.entities.cms.CmsCategoryModel;
import com.sanluan.cms.entities.cms.CmsContent;
import com.sanluan.cms.entities.cms.CmsContentAttribute;
import com.sanluan.cms.logic.component.StaticComponent;
import com.sanluan.cms.logic.service.cms.CmsCategoryModelService;
import com.sanluan.cms.logic.service.cms.CmsCategoryService;
import com.sanluan.cms.logic.service.cms.CmsContentAttributeService;
import com.sanluan.cms.logic.service.cms.CmsContentService;
import com.sanluan.common.base.BaseController;

@Controller
@RequestMapping("cmsContent")
public class CmsContentController extends BaseController {
	@Autowired
	private CmsContentService service;
	@Autowired
	private CmsCategoryModelService categoryModelService;
	@Autowired
	private CmsCategoryService categoryService;
	@Autowired
	private CmsContentAttributeService attributeService;
	@Autowired
	private StaticComponent staticComponent;

	@RequestMapping(value = { "save" + DO_SUFFIX })
	public String save(CmsContent entity, String txt, Boolean draft, HttpServletRequest request, ModelMap model) {
		entity.setUserId(UserUtils.getAdminFromSession(request).getId());
		if (null != draft && draft) {
			entity.setStatus(0);
		} else {
			entity.setStatus(2);
		}
		if (null != entity.getId()) {
			service.update(entity.getId(), entity, new String[] { "userId", "createDate", "clicks", "comments", "scores",
					"chapters" });
		} else {
			entity = service.save(entity);
		}
		CmsContentAttribute attribute = attributeService.getEntity(entity.getId());
		if (null != attribute) {
			attributeService.updateTxt(attribute.getContentId(), txt);
		} else {
			attribute = new CmsContentAttribute();
			attribute.setContentId(entity.getId());
			attribute.setText(txt);
			attribute.setWordCount(null == txt ? 0 : txt.length());
			attributeService.save(attribute);
		}
		return publish(entity.getId(), request, model);
	}

	@RequestMapping(value = { "static" + DO_SUFFIX })
	public String publish(Integer id, HttpServletRequest request, ModelMap model) {
		CmsContent entity = service.getEntity(id);
		if (null != entity && 1 == entity.getStatus()) {
			model.put("content", entity);
			CmsCategoryModel categoryModel = categoryModelService.getEntity(entity.getModelId(), entity.getCategoryId());
			CmsCategory cmsCategory = categoryService.getEntity(entity.getCategoryId());
			if ((virifyNotEmpty("categoryModel", categoryModel, model) || virifyCustom("static",
					!staticComponent.createStaticFile(categoryModel.getTemplatePath(), cmsCategory.getContentPath(), model),
					model))) {
				return "common/ajaxError";
			}
		}
		return "common/ajaxDone";
	}

	@RequestMapping(value = { "check" + DO_SUFFIX })
	public String check(Integer id, HttpServletRequest request, ModelMap model) {
		CmsContent entity = service.getEntity(id);
		if (null != entity && 2 == entity.getStatus()) {
			service.check(entity.getId());
			return publish(entity.getId(), request, model);
		}
		return "common/ajaxDone";
	}

	@RequestMapping(value = { "delete" + DO_SUFFIX })
	public String delete(Integer id) {
		service.delete(id);
		return "common/ajaxDone";
	}
}