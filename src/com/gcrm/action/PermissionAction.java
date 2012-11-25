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

import com.gcrm.domain.Permission;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Manages the Permission dropdown list
 * 
 */
public class PermissionAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Permission> baseService;
	private Permission permission;

	private static final String CLAZZ = Permission.class.getSimpleName();

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Permission> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		List<Permission> permissions = result.getResult();

		long totalRecords = result.getTotalRecords();

		// Constructs the JSON data
		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = permissions.size();
		for (int i = 0; i < size; i++) {
			Permission instance = (Permission) permissions.get(i);
			Integer id = instance.getId();
			String name = instance.getName();
			String url = instance.getUrl();
			int sequence = instance.getSequence();

			json += "{\"id\":\"" + id + "\",\"permission.id\":\""
					+ id + "\",\"permission.name\":\""
					+ name + "\",\"permission.url\":\"" + url
					+ "\",\"permission.sequence\":\"" + sequence + "\"}";
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
		getbaseService().makePersistent(permission);
		return SUCCESS;
	}

    /**
     * Deletes the selected entity.
     * 
     * @return the SUCCESS result
     */	
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Permission.class, this.getSeleteIDs());
		return SUCCESS;
	}

	public IBaseService<Permission> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Permission> baseService) {
		this.baseService = baseService;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

}
