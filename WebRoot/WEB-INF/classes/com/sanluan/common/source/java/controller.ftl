package ${base}.${controllerPack};

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanluan.common.base.BaseController;
<#include "../include_imports/entity.ftl">

<#include "../include_imports/service.ftl">

@Controller
@RequestMapping("${entityName?uncap_first}")
public class ${entityName}Controller extends BaseController {
	@Autowired
	private ${entityName}Service service;

	@RequestMapping(value = { "save" + DO_SUFFIX })
	public String save(${entityName} entity, HttpServletRequest request) {
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