package com.sanluan.cms.logic.service.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanluan.cms.entities.cms.CmsChapter;
import com.sanluan.cms.logic.dao.cms.CmsChapterDao;
import com.sanluan.common.base.BaseService;
import com.sanluan.common.handler.PageHandler;

@Service
@Transactional
public class CmsChapterService extends BaseService<CmsChapter, CmsChapterDao> {

	@Autowired
	private CmsChapterDao dao;

	@Transactional(readOnly = true)
	public PageHandler getPage(String modelExtend3, String modelExtend4, String title, String extend1, String extend3,
			String extend2, String image, String extend4, String modelExtend2, Integer contentId, String modelExtend1,
			int pageNo, int pageSize) {
		return dao.getPage(modelExtend3, modelExtend4, title, extend1, extend3, extend2, image, extend4, modelExtend2, contentId,
				modelExtend1, pageNo, pageSize);
	}

	@Override
	protected CmsChapterDao getDao() {
		return dao;
	}
}