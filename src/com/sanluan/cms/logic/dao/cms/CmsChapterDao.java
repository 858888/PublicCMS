package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsChapter;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsChapterDao extends BaseDao<CmsChapter> {
	public PageHandler getPage(String modelExtend3, String modelExtend4, String title, String extend1, String extend3,
			String extend2, String image, String extend4, String modelExtend2, Integer contentId, String modelExtend1,
			int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsChapter bean");
		if (notEmpty(modelExtend3)) {
			queryMaker.condition("bean.modelExtend3 = :modelExtend3").setParameter("modelExtend3", modelExtend3);
		}
		if (notEmpty(modelExtend4)) {
			queryMaker.condition("bean.modelExtend4 = :modelExtend4").setParameter("modelExtend4", modelExtend4);
		}
		if (notEmpty(title)) {
			queryMaker.condition("bean.title like :title").setParameter("title", like(title));
		}
		if (notEmpty(extend1)) {
			queryMaker.condition("bean.extend1 = :extend1").setParameter("extend1", extend1);
		}
		if (notEmpty(extend3)) {
			queryMaker.condition("bean.extend3 = :extend3").setParameter("extend3", extend3);
		}
		if (notEmpty(extend2)) {
			queryMaker.condition("bean.extend2 = :extend2").setParameter("extend2", extend2);
		}
		if (notEmpty(image)) {
			queryMaker.condition("bean.image = :image").setParameter("image", image);
		}
		if (notEmpty(extend4)) {
			queryMaker.condition("bean.extend4 = :extend4").setParameter("extend4", extend4);
		}
		if (notEmpty(modelExtend2)) {
			queryMaker.condition("bean.modelExtend2 = :modelExtend2").setParameter("modelExtend2", modelExtend2);
		}
		if (notEmpty(contentId)) {
			queryMaker.condition("bean.contentId = :contentId").setParameter("contentId", contentId);
		}
		if (notEmpty(modelExtend1)) {
			queryMaker.condition("bean.modelExtend1 = :modelExtend1").setParameter("modelExtend1", modelExtend1);
		}
		queryMaker.append("order by bean.id desc");
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsChapter init(CmsChapter entity) {
		return entity;
	}

	@Override
	protected Class<CmsChapter> getEntityClass() {
		return CmsChapter.class;
	}

}