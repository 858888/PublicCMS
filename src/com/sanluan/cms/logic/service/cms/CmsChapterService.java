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
	public PageHandler getPage(Integer extendNumber3, Integer extendNumber4, 
				Integer parentId, String title, String extend1, 
				String extend3, String extend2, String image, 
				String extend4, Integer extendNumber2, Integer contentId, 
				Integer extendNumber1, 
				int pageNo, int pageSize) {
		return dao.getPage(extendNumber3, extendNumber4, 
				parentId, title, extend1, 
				extend3, extend2, image, 
				extend4, extendNumber2, contentId, 
				extendNumber1, 
				pageNo, pageSize);
	}

	@Override
	protected CmsChapterDao getDao() {
		return dao;
	}
}