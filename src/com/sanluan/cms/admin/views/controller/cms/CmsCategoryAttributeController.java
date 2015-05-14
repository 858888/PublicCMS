package com.sanluan.cms.admin.views.controller.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.common.base.BaseController;
import com.sanluan.cms.entities.cms.CmsCategoryAttribute;
import com.sanluan.cms.logic.service.cms.CmsCategoryAttributeService;
@Controller
@RequestMapping("cmsCategoryAttribute")
public class CmsCategoryAttributeController extends BaseController {
	@Autowired
	private CmsCategoryAttributeService service;

	@RequestMapping(value = { "save" + DO_SUFFIX })
	public String save(CmsCategoryAttribute entity, HttpServletRequest request) {
		if (0 != entity.getCategoryId()) {
			service.update(entity.getCategoryId(), entity);
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