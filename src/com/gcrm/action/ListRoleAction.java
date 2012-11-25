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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.domain.Permission;
import com.gcrm.domain.Role;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Role
 * 
 */
public class ListRoleAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Role> baseService;
	private IBaseService<Permission> permissionService;
	private Role role;
	private Integer id;

	private static final String CLAZZ = Role.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */	
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Role> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		List<Role> roles = result.getResult();

		long totalRecords = result.getTotalRecords();

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = roles.size();
		for (int i = 0; i < size; i++) {
			Role instance = (Role) roles.get(i);
			int id = instance.getId();
			String name = instance.getName();
			int sequence = 0;
			if (instance.getSequence() != null){
			 sequence = instance.getSequence();
			}
			
			json += "{\"id\":\"" + id + "\",\"name\":\"" + name
					+ "\",\"sequence\":\"" + sequence + "\"}";
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
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */		
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Role.class, this.getSeleteIDs());
		return SUCCESS;
	}

    /**
     * Copies the selected entities
     * 
     * @return the SUCCESS result
     */	
	public String copy() throws ServiceException {
		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String copyid = ids[i];
				Role oriRecord = baseService.getEntityById(Role.class,
						Integer.valueOf(copyid));
				Role targetRecord = oriRecord.clone();
				targetRecord.setId(null);
				this.baseService.makePersistent(targetRecord);
			}
		}
		return SUCCESS;
	}

    /**
     * Exports the entities
     * 
     * @return the exported entities inputStream
     */	
	public InputStream getInputStream() throws Exception {
		File file = new File(CLAZZ + ".csv");
		ICsvMapWriter writer = new CsvMapWriter(new FileWriter(file),
				CsvPreference.EXCEL_PREFERENCE);
		try {
			final String[] header = new String[] { "ID", "Name",
					"Sequence", "Permissions"};
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Role role = baseService.getEntityById(Role.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], role.getId());
				data1.put(header[1], CommonUtil.fromNullToEmpty(role.getName()));
				data1.put(header[2], role.getSequence());
				Set<Permission> permissions = role.getPermissions();
				String permissionIDs = "";
				for (Permission permission: permissions){
					if (permissionIDs.length() > 0){
						permissionIDs += ",";
					}
					permissionIDs += String.valueOf(permission.getId());
				}
				data1.put(header[3],permissionIDs);
				writer.write(data1, header);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			writer.close();
		}

		InputStream in = new FileInputStream(file);
		this.setFileName(CLAZZ + ".csv");
		return in;
	}

    /**
     * Imports the entities
     * 
     * @return the SUCCESS result
     */	
	public String importCSV() throws Exception {
		File file = this.getUpload();
		CsvListReader reader = new CsvListReader(new FileReader(file),
				CsvPreference.EXCEL_PREFERENCE);
		int failedNum = 0;
		int successfulNum = 0;
		try {
			final String[] header = reader.getCSVHeader(true);

			List<String> line = new ArrayList<String>();
			Map<String, String> failedMsg = new HashMap<String, String>();
			while ((line = reader.read()) != null) {

				Map<String, String> row = new HashMap<String, String>();
				for (int i = 0; i < line.size(); i++) {
					row.put(header[i], line.get(i));
				}

				Role role = new Role();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						role.setId(Integer.parseInt(id));
					}				
					role.setName(CommonUtil.fromNullToEmpty(row.get("Role Name")));
					String sequence = row.get("Sequence");
					if (CommonUtil.isNullOrEmpty(sequence)) {
						role.setSequence(0);
					} else {
						role.setSequence(Integer.parseInt(sequence));
					}
					String permissions = row.get("Permissions");
					if (!CommonUtil.isNullOrEmpty(permissions)) {
						String[] ids = permissions.split(",");
						Set<Permission> permissionSet = new HashSet<Permission>(0);
						for (int i = 0; i < ids.length; i++) {
							String permissionID = ids[i];
							Permission permission = permissionService.getEntityById(Permission.class,
									Integer.parseInt(permissionID));
							permissionSet.add(permission);
						}		
						role.setPermissions(permissionSet);
					}
					
					baseService.makePersistent(role);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					failedMsg.put(role.getName(), e.getMessage());
				}

			}

			this.setFailedMsg(failedMsg);
			this.setFailedNum(failedNum);
			this.setSuccessfulNum(successfulNum);
			this.setTotalNum(successfulNum + failedNum);
		} finally {
			reader.close();
		}
		return SUCCESS;
	}

	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * @return the baseService
	 */
	public IBaseService<Role> getBaseService() {
		return baseService;
	}

	/**
	 * @param baseService
	 *            the baseService to set
	 */
	public void setBaseService(IBaseService<Role> baseService) {
		this.baseService = baseService;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the permissionService
	 */
	public IBaseService<Permission> getPermissionService() {
		return permissionService;
	}

	/**
	 * @param permissionService the permissionService to set
	 */
	public void setPermissionService(IBaseService<Permission> permissionService) {
		this.permissionService = permissionService;
	}

}
