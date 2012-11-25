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

import com.gcrm.domain.OpportunityType;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Manages the Opportunity Type dropdown list
 * 
 */
public class OpportunityTypeAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<OpportunityType> baseService;
	private OpportunityType opportunityType;

	private static final String CLAZZ = OpportunityType.class.getSimpleName();

	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<OpportunityType> result = baseService
				.getPaginationObjects(CLAZZ, searchCondition);
		List<OpportunityType> opportunityTypes = result.getResult();

		long totalRecords = result.getTotalRecords();

		// Constructs the JSON data
		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = opportunityTypes.size();
		for (int i = 0; i < size; i++) {
			OpportunityType instance = (OpportunityType) opportunityTypes
					.get(i);
			Integer id = instance.getId();
			String name = instance.getName();
			int sequence = instance.getSequence();

			json += "{\"id\":\"" + id + "\",\"opportunityType.id\":\"" + id
					+ "\",\"opportunityType.name\":\"" + name
					+ "\",\"opportunityType.sequence\":\"" + sequence + "\"}";
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
		getbaseService().makePersistent(opportunityType);
		return SUCCESS;
	}

	/**
	 * Deletes the selected entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(OpportunityType.class,
				this.getSeleteIDs());
		return SUCCESS;
	}

	public IBaseService<OpportunityType> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<OpportunityType> baseService) {
		this.baseService = baseService;
	}

	public OpportunityType getOpportunityType() {
		return opportunityType;
	}

	public void setOpportunityType(OpportunityType opportunityType) {
		this.opportunityType = opportunityType;
	}

}
