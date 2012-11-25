/**
 * Copyright (C) 2012, Grass CRM Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gcrm.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gcrm.dao.IBaseDao;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Base service
 */
@Transactional
public class BaseService<T extends Serializable> implements IBaseService<T> {

	private IBaseDao<T> baseDao;
	
	/* (non-Javadoc)
	 * @see com.gcrm.service.IBaseService#getAllObjects(java.lang.String)
	 */
	public List<T> getAllObjects(String clazz){
		return baseDao.getAllObjects(clazz);
	}

	/* (non-Javadoc)
	 * @see com.gcrm.service.IBaseService#makePersistent(java.io.Serializable)
	 */
	public void makePersistent(T entity) {
		baseDao.makePersistent(entity);
	}

	/* (non-Javadoc)
	 * @see com.gcrm.service.IBaseService#deleteEntity(java.lang.Class, java.lang.Integer)
	 */
	public void deleteEntity(Class<T> entityClass, Integer id) {
		baseDao.deleteEntity(entityClass, id);
	}
	
	/* (non-Javadoc)
	 * @see com.gcrm.service.IBaseService#batchDeleteEntity(java.lang.Class, java.lang.String)
	 */
	public void batchDeleteEntity(Class<T> entityClass, String seleteIDs) {
		if (seleteIDs != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String deleteid = ids[i];
				baseDao.deleteEntity(entityClass,
						Integer.valueOf(deleteid));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.gcrm.service.IBaseService#getEntityById(java.lang.Class, java.lang.Integer)
	 */
	public T getEntityById(Class<T> entityClass,Integer id) {
		return baseDao.getEntityById(entityClass,id);
	}

	/* (non-Javadoc)
	 * @see com.gcrm.service.IBaseService#getObjectsCount(java.lang.String)
	 */
	public long getObjectsCount(String clazz) {
		return baseDao.getObjectsCount(clazz);
	}

	/* (non-Javadoc)
	 * @see com.gcrm.service.IBaseService#getPaginationObjects(java.lang.String, com.gcrm.vo.SearchCondition)
	 */
	public SearchResult<T> getPaginationObjects(String clazz,
			final SearchCondition searchCondition) {

		return baseDao.getPaginationObjects(clazz,searchCondition);
	}

	public IBaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

}
