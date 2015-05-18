package com.sanluan.cms.views.directive.cms;

// Generated 2015-5-10 17:54:56 by SourceMaker

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sanluan.cms.common.bean.FileInfo;
import com.sanluan.cms.logic.component.StaticComponent;
import com.sanluan.common.base.BaseDirective;
import com.sanluan.common.handler.DirectiveHandler;

import freemarker.template.TemplateException;

@Component
public class CmsTemplateTreeDirective extends BaseDirective {
	@Autowired
	private StaticComponent staticComponent;

	@Override
	public void execute(DirectiveHandler handler) throws TemplateException, IOException {
		List<FileInfo> list = staticComponent.getFileList(staticComponent.getTemplateFilePath(handler.getString("path","/")));
		handler.put("list", list).render();
	}
}