package com.sanluan.cms.logic.dao.cms;

// Generated 2015-5-8 16:50:23 by SourceMaker

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.sanluan.cms.entities.cms.CmsContent;
import com.sanluan.common.base.BaseDao;
import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

@Repository
public class CmsContentDao extends BaseDao<CmsContent> {
	public PageHandler getPage(String modelExtend3, String modelExtend4, Integer status, String extend1, Integer categoryId,
			String extend3, String extend2, String extend4, Boolean isDisable, Integer modelId, String title, Integer userId,
			String modelExtend2, Date startPublishDate, Date endPublishDate, String modelExtend1, String orderField,
			String orderType, int pageNo, int pageSize) {
		QueryHandler queryMaker = getQueryMaker("from CmsContent bean");
		if (notEmpty(modelExtend3)) {
			queryMaker.condition("bean.modelExtend3 = :modelExtend3").setParameter("modelExtend3", modelExtend3);
		}
		if (notEmpty(modelExtend4)) {
			queryMaker.condition("bean.modelExtend4 = :modelExtend4").setParameter("modelExtend4", modelExtend4);
		}
		if (notEmpty(status)) {
			queryMaker.condition("bean.status = :status").setParameter("status", status);
		}
		if (notEmpty(extend1)) {
			queryMaker.condition("bean.extend1 = :extend1").setParameter("extend1", extend1);
		}
		if (notEmpty(categoryId)) {
			queryMaker.condition("bean.categoryId = :categoryId").setParameter("categoryId", categoryId);
		}
		if (notEmpty(extend3)) {
			queryMaker.condition("bean.extend3 = :extend3").setParameter("extend3", extend3);
		}
		if (notEmpty(extend2)) {
			queryMaker.condition("bean.extend2 = :extend2").setParameter("extend2", extend2);
		}
		if (notEmpty(extend4)) {
			queryMaker.condition("bean.extend4 = :extend4").setParameter("extend4", extend4);
		}
		if (notEmpty(isDisable)) {
			queryMaker.condition("bean.isDisable = :isDisable").setParameter("isDisable", isDisable);
		}
		if (notEmpty(modelId)) {
			queryMaker.condition("bean.modelId = :modelId").setParameter("modelId", modelId);
		}
		if (notEmpty(title)) {
			queryMaker.condition("(bean.title like :title)").setParameter("title", like(title));
		}
		if (notEmpty(userId)) {
			queryMaker.condition("bean.userId = :userId").setParameter("userId", userId);
		}
		if (notEmpty(modelExtend2)) {
			queryMaker.condition("bean.modelExtend2 = :modelExtend2").setParameter("modelExtend2", modelExtend2);
		}
		if (notEmpty(startPublishDate)) {
			queryMaker.condition("bean.publishDate < :startPublishDate").setParameter("startPublishDate", startPublishDate);
		}
		if (notEmpty(endPublishDate)) {
			queryMaker.condition("bean.publishDate <= :endPublishDate").setParameter("endPublishDate", endPublishDate);
		}
		if (notEmpty(modelExtend1)) {
			queryMaker.condition("bean.modelExtend1 = :modelExtend1").setParameter("modelExtend1", modelExtend1);
		}
		if ("asc".equals(orderType)) {
			orderType = "asc";
		} else {
			orderType = "desc";
		}
		if (!notEmpty(orderField)) {
			orderField = "";
		}
		switch (orderField) {
		case "scores":
			queryMaker.append("order by bean.scores " + orderType);
			break;
		case "comments":
			queryMaker.append("order by bean.comments " + orderType);
			break;
		case "clicks":
			queryMaker.append("order by bean.clicks " + orderType);
			break;
		case "publishDate":
			queryMaker.append("order by bean.publishDate " + orderType);
			break;
		default:
			queryMaker.append("order by bean.id " + orderType);
		}
		return getPage(queryMaker, pageNo, pageSize);
	}

	@Override
	protected CmsContent init(CmsContent entity) {
		if (null == entity.getCreateDate()) {
			entity.setCreateDate(getDate());
		}
		return entity;
	}

	@Override
	protected Class<CmsContent> getEntityClass() {
		return CmsContent.class;
	}

}