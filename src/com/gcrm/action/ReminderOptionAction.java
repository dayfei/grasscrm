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

import com.gcrm.domain.ReminderOption;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Manages the Reminder Option dropdown list
 * 
 */
public class ReminderOptionAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<ReminderOption> baseService;
	private ReminderOption reminderOption;

	private static final String CLAZZ = ReminderOption.class.getSimpleName();

	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<ReminderOption> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		List<ReminderOption> reminderOptions = result.getResult();

		long totalRecords = result.getTotalRecords();

		// Constructs the JSON data
		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = reminderOptions.size();
		for (int i = 0; i < size; i++) {
			ReminderOption instance = (ReminderOption) reminderOptions.get(i);
			Integer id = instance.getId();
			String name = instance.getName();
			int sequence = instance.getSequence();

			json += "{\"id\":\"" + id + "\",\"reminderOption.id\":\"" + id
					+ "\",\"reminderOption.name\":\"" + name
					+ "\",\"reminderOption.sequence\":\"" + sequence + "\"}";
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
		getbaseService().makePersistent(reminderOption);
		return SUCCESS;
	}

	/**
	 * Deletes the selected entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String delete() throws ServiceException {
		baseService
				.batchDeleteEntity(ReminderOption.class, this.getSeleteIDs());
		return SUCCESS;
	}

	public IBaseService<ReminderOption> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<ReminderOption> baseService) {
		this.baseService = baseService;
	}

	public ReminderOption getReminderOption() {
		return reminderOption;
	}

	public void setReminderOption(ReminderOption reminderOption) {
		this.reminderOption = reminderOption;
	}

}
