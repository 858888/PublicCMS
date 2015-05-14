package com.sanluan.cms.admin.views.controller.log;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.common.base.BaseController;
import com.sanluan.cms.entities.log.LogOperate;
import com.sanluan.cms.logic.service.log.LogOperateService;
@Controller
@RequestMapping("logOperate")
public class LogOperateController extends BaseController {
	@Autowired
	private LogOperateService service;

	@RequestMapping(value = { "save" + DO_SUFFIX })
	public String save(LogOperate entity, HttpServletRequest request) {
		if (null != entity.getId()) {
			service.update(entity.getId(), entity);
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