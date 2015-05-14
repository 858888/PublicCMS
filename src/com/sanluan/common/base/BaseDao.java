package com.sanluan.common.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sanluan.common.handler.PageHandler;
import com.sanluan.common.handler.QueryHandler;

/**
 * @author zhangxd
 * 
 * @param <E>
 */
public abstract class BaseDao<E> {
	protected Logger log = LoggerFactory.getLogger(getClass());

	public QueryHandler getQueryMaker(String sql) {
		return new QueryHandler(sql);
	}

	public QueryHandler getQueryMaker() {
		return new QueryHandler();
	}

	public Date getDate() {
		return new Date();
	}

	public Date tomorrow(Date date) {
		return DateUtils.addDays(date, 1);
	}

	public static boolean notEmpty(String var) {
		return StringUtils.isNoneBlank(var);
	}

	public static boolean notEmpty(Short var) {
		return null != var;
	}

	public static boolean notEmpty(String[] var) {
		return null != var && 0 < var.length;
	}

	public static boolean notEmpty(Integer var) {
		return null != var;
	}

	public static boolean notEmpty(Boolean var) {
		return null != var;
	}

	public static boolean notEmpty(Date var) {
		return null != var;
	}

	public static boolean notEmpty(Long var) {
		return null != var;
	}

	public String like(String var) {
		return "%" + var + "%";
	}

	public String likeEnd(String var) {
		return "%" + var;
	}

	public String likeStart(String var) {
		return var + "%";
	}

	/**
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E getEntity(Serializable id) {
		return (E) getSession().get(getEntityClass(), id);
	}

	protected abstract E init(E entity);

	/**
	 * @param entity
	 * @return
	 */
	public E save(E entity) {
		getSession().save(init(entity));
		return entity;
	}

	/**
	 * @param id
	 * @return
	 */
	public E delete(Serializable id) {
		E entity = getEntity(id);
		if (null != entity)
			getSession().delete(entity);
		return entity;
	}

	/**
	 * @param queryMaker
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected E getEntity(QueryHandler queryMaker) {
		try {
			return (E) getQuery(queryMaker).uniqueResult();
		} catch (Exception e) {
			return (E) getQuery(queryMaker).list().get(0);
		}
	}

	/**
	 * @param query
	 * @return
	 */
	protected int update(QueryHandler queryMaker) {
		return getQuery(queryMaker).executeUpdate();
	}

	/**
	 * @param query
	 * @return
	 */
	protected int delete(QueryHandler queryMaker) {
		return update(queryMaker);
	}

	/**
	 * @param query
	 * @return
	 */
	protected List<?> getList(QueryHandler queryMaker) {
		List<?> list = getQuery(queryMaker).list();
		return list;
	}

	/**
	 * @param queryMaker
	 * @param pageNo
	 * @param pageSize
	 * @param maxResults
	 * @return
	 */
	protected PageHandler getPage(QueryHandler queryMaker, int pageNo, int pageSize, Integer maxResults) {
		int totalCount = countResult(queryMaker);
		PageHandler pagination = new PageHandler(pageNo, pageSize, totalCount, maxResults);
		queryMaker.setFirstResult(pagination.getFirstResult());
		queryMaker.setMaxResults(pagination.getPageSize());
		queryMaker.setCacheable(true);
		pagination.setList(getList(queryMaker));
		return pagination;
	}

	/**
	 * @param queryMaker
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	protected PageHandler getPage(QueryHandler queryMaker, int pageNo, int pageSize) {
		int totalCount = countResult(queryMaker);
		PageHandler pagination = new PageHandler(pageNo, pageSize, totalCount);
		queryMaker.setFirstResult(pagination.getFirstResult());
		queryMaker.setMaxResults(pagination.getPageSize());
		queryMaker.setCacheable(true);
		pagination.setList(getList(queryMaker));
		return pagination;
	}

	/**
	 * @param query
	 * @return
	 */
	protected int countResult(QueryHandler queryMaker) {
		return ((Number) getCountQuery(queryMaker).iterate().next()).intValue();
	}

	/**
	 * @param query
	 * @return
	 */
	protected int count(QueryHandler queryMaker) {
		return ((Number) getQuery(queryMaker).iterate().next()).intValue();
	}

	private Query getQuery(QueryHandler queryMaker) {
		return queryMaker.getQuery(getSession());
	}

	private Query getCountQuery(QueryHandler queryMaker) {
		return queryMaker.getCountQuery(getSession());
	}

	@Autowired
	protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected abstract Class<E> getEntityClass();
}
