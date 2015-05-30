package com.sanluan.cms.admin.views.controller.cms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.cms.entities.cms.CmsCategory;
import com.sanluan.cms.entities.cms.CmsCategoryModel;
import com.sanluan.cms.entities.cms.CmsModel;
import com.sanluan.cms.logic.service.cms.CmsCategoryModelService;
import com.sanluan.cms.logic.service.cms.CmsCategoryService;
import com.sanluan.cms.logic.service.cms.CmsModelService;
import com.sanluan.common.base.BaseController;

@Controller
@RequestMapping("cmsCategory")
public class CmsCategoryController extends BaseController {
	@Autowired
	private CmsCategoryService service;
	@Autowired
	private CmsModelService modelService;
	@Autowired
	private CmsCategoryModelService categoryModelService;

	@RequestMapping(value = { "save" + DO_SUFFIX })
	public String save(CmsCategory entity, HttpServletRequest request) {
		if (null != entity.getId()) {
			service.update(entity.getId(), entity, new String[] { "childIds", "isDisable", "contents" });
		} else {
			entity = service.save(entity);
			if (null != entity.getParentId()) {
				CmsCategory parent = service.getEntity(entity.getParentId());
				if (null != parent) {
					String childIds;
					if (StringUtils.isBlank(parent.getChildIds())) {
						childIds = String.valueOf(entity.getId());
					} else {
						childIds = "," + String.valueOf(entity.getId());
					}
					service.updateChildIds(parent.getId(), childIds);
				}
			}
		}
		@SuppressWarnings("unchecked")
		List<CmsModel> modelList = (List<CmsModel>) modelService.getPage(false, 1, 20).getList();
		for (CmsModel model : modelList) {
			String flag = request.getParameter("model_" + model.getId());
			CmsCategoryModel categoryModel = categoryModelService.getEntity(model.getId(), entity.getId());
			if (null != categoryModel && null != flag) {
				categoryModelService.update(categoryModel.getId(), request.getParameter("templatePath_" + model.getId()),
						request.getParameter("chapterTemplatePath_" + model.getId()));
			} else if (null != categoryModel && null == flag) {
				categoryModelService.delete(categoryModel.getId());
			} else if (null == categoryModel && null != flag) {
				categoryModel = new CmsCategoryModel();
				categoryModel.setCategoryId(entity.getId());
				categoryModel.setModelId(model.getId());
				categoryModel.setTemplatePath(request.getParameter("templatePath_" + model.getId()));
				categoryModel.setChapterTemplatePath(request.getParameter("chapterTemplatePath_" + model.getId()));
				categoryModelService.save(categoryModel);
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