package com.sanluan.common.base;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author zhangxd
 * 
 * @param <E>
 * @param <D>
 */
@Transactional
public abstract class BaseService<E, D extends BaseDao<E>> {
	protected abstract D getDao();

	/**
	 * @param id
	 * @return
	 */
	public E getEntity(Serializable id) {
		return getDao().getEntity(id);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public E delete(Serializable id) {
		return getDao().delete(id);
	}

	/**
	 * @param id
	 * @param newEntity
	 * @param ignoreProperties
	 * @return
	 */
	public E update(Serializable id, E newEntity, String ignoreProperties[]) {
		E entity = getEntity(id);
		BeanUtils.copyProperties(newEntity, entity, ignoreProperties);
		return entity;
	}

	/**
	 * @param id
	 * @param newEntity
	 * @return
	 */
	public E update(Serializable id, E newEntity) {
		E entity = getEntity(id);
		BeanUtils.copyProperties(newEntity, entity);
		return entity;
	}

	/**
	 * @param entity
	 * @return
	 */
	public E save(E entity) {
		return getDao().save(entity);
	}
}
