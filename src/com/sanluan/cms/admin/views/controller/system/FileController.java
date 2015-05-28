package com.sanluan.cms.admin.views.controller.system;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.sanluan.common.base.BaseController;

@RequestMapping("file")
public class FileController extends BaseController {
	@RequestMapping(value = { "/upload" + DO_SUFFIX }, method = RequestMethod.POST)
	public void upload(MultipartFile file, String filePath, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws IllegalStateException, IOException {
		file.transferTo(new File(filePath));
	}
}
