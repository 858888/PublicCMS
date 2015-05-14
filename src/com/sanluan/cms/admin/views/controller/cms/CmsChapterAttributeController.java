package com.sanluan.cms.admin.views.controller.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.common.base.BaseController;
import com.sanluan.cms.entities.cms.CmsChapterAttribute;
import com.sanluan.cms.logic.service.cms.CmsChapterAttributeService;
@Controller
@RequestMapping("cmsChapterAttribute")
public class CmsChapterAttributeController extends BaseController {
	@Autowired
	private CmsChapterAttributeService service;

	@RequestMapping(value = { "save" + DO_SUFFIX })
	public String save(CmsChapterAttribute entity, HttpServletRequest request) {
		if (0 != entity.getChapterId()) {
			service.update(entity.getChapterId(), entity);
		} else {
			service.save(entity);
		}
		return "common/ajaxDone";
	}

	@RequestMapping(value = { "delete" + DO_SUFFIX })
	public String delete(Integer id) {
		service.delete(id);
		return "common/ajaxDone";
	}
}