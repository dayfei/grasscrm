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
package com.gcrm.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.SalesStage;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Manages the Sales Stage dropdown list
 * 
 */
public class SalesStageAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<SalesStage> baseService;
	private SalesStage salesStage;

	private static final String CLAZZ = SalesStage.class.getSimpleName();

	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<SalesStage> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		List<SalesStage> salesStages = result.getResult();

		long totalRecords = result.getTotalRecords();

		// Constructs the JSON data
		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = salesStages.size();
		for (int i = 0; i < size; i++) {
			SalesStage instance = (SalesStage) salesStages.get(i);
			Integer id = instance.getId();
			String name = instance.getName();
			int sequence = instance.getSequence();

			json += "{\"id\":\"" + id + "\",\"salesStage.id\":\"" + id
					+ "\",\"salesStage.name\":\"" + name
					+ "\",\"salesStage.sequence\":\"" + sequence + "\"}";
			if (i < size - 1) {
				json += ",";
			}
		}
		json += "]}";

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().write(json);
		return null;
	}

	/**
	 * Saves the entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String save() throws Exception {
		getbaseService().makePersistent(salesStage);
		return SUCCESS;
	}

	/**
	 * Deletes the selected entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(SalesStage.class, this.getSeleteIDs());
		return SUCCESS;
	}

	public IBaseService<SalesStage> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<SalesStage> baseService) {
		this.baseService = baseService;
	}

	public SalesStage getSalesStage() {
		return salesStage;
	}

	public void setSalesStage(SalesStage salesStage) {
		this.salesStage = salesStage;
	}

}
