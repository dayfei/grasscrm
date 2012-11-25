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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.domain.Account;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Target;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.TargetListType;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists TargetList
 * 
 */
public class ListTargetListAction extends BaseListAction{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<TargetList> baseService;
	private IBaseService<TargetListType> targetListTypeService;
	private IBaseService<User> userService;	
	private TargetList targetList;

	private static final String CLAZZ = TargetList.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */	
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<TargetList> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		List<TargetList> targetLists = result.getResult();

		long totalRecords = result.getTotalRecords();

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = targetLists.size();

		String userName = null;
		String typeName = null;
		for (int i = 0; i < size; i++) {
			TargetList instance = (TargetList) targetLists.get(i);
			int id = instance.getId();
			String name = CommonUtil.fromNullToEmpty(instance.getName());
			TargetListType type = instance.getType();
			if (type != null) {
				typeName = CommonUtil.fromNullToEmpty(type.getName());
			} else {
				typeName = "";
			}                        
            String description = CommonUtil.fromNullToEmpty(instance.getDescription());
			User user = instance.getAssigned_to();
			if (user != null) {
				userName = user.getName();
			} else {
				userName = "";
			}
			User createdBy = instance.getCreated_by();
			String createdByName = "";
			if (createdBy != null) {
				createdByName = CommonUtil.fromNullToEmpty(createdBy.getName());
			}			
			User updatedBy = instance.getUpdated_by();
			String updatedByName = "";
			if (updatedBy != null) {
				updatedByName = CommonUtil.fromNullToEmpty(updatedBy.getName());
			}			
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_TIME_FORMAT);
			Date createdOn = instance.getCreated_on();
			String createdOnName = "";
			if (createdOn != null) {
				createdOnName = dateFormat.format(createdOn);
			}		
			Date updatedOn = instance.getUpdated_on();
			String updatedOnName = "";
			if (updatedOn != null) {
				updatedOnName = dateFormat.format(updatedOn);
			}	
			
			json += "{\"id\":\"" + id + "\",\"name\":\"" + name
					+ "\",\"type\":\"" + typeName
					+ "\",\"description\":\"" + description
					+ "\",\"user_name\":\"" + userName + 
					"\",\"created_by\":\"" + createdByName + 
					"\",\"updated_by\":\"" + updatedByName + 
					"\",\"created_on\":\"" + createdOnName + 
					"\",\"updated_on\":\"" + updatedOnName + 
					"\"}";
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
     * Gets the related accounts.
     * 
     * @return null
     */	
	public String filterTargetListAccount() throws Exception {
		targetList = baseService.getEntityById(TargetList.class, id);
		Set<Account> accounts = targetList.getAccounts();
		Iterator<Account> accountIterator = accounts.iterator();
		long totalRecords = accounts.size();
		ListAccountAction.getListJson(accountIterator, totalRecords,false);
		return null;
	}
	
    /**
     * Gets the related leads.
     * 
     * @return null
     */		
	public String filterTargetListLead() throws Exception {
		targetList = baseService.getEntityById(TargetList.class, id);
		Set<Lead> leads = targetList.getLeads();
		Iterator<Lead> leadIterator = leads.iterator();
		long totalRecords = leads.size();
		ListLeadAction.getListJson(leadIterator, totalRecords);
		return null;
	}
	
	public String filterTargetListContact() throws Exception {
		targetList = baseService.getEntityById(TargetList.class, id);
		Set<Contact> contacts = targetList.getContacts();
		Iterator<Contact> contactIterator = contacts.iterator();
		long totalRecords = contacts.size();
		ListContactAction.getListJson(contactIterator, totalRecords);
		return null;
	}	
	
	public String filterTargetListTarget() throws Exception {
		targetList = baseService.getEntityById(TargetList.class, id);
		Set<Target> targets = targetList.getTargets();
		Iterator<Target> targetIterator = targets.iterator();
		long totalRecords = targets.size();
		ListTargetAction.getListJson(targetIterator, totalRecords);
		return null;
	}
	
	public String filterTargetListUser() throws Exception {
		targetList = baseService.getEntityById(TargetList.class, id);
		Set<User> users = targetList.getUsers();
		Iterator<User> userIterator = users.iterator();
		long totalRecords = users.size();
		ListUserAction.getListJson(userIterator, totalRecords);
		return null;
	}		

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */	
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(TargetList.class, this.getSeleteIDs());
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
				TargetList oriRecord = baseService.getEntityById(TargetList.class, Integer.valueOf(copyid));
				TargetList targetListRecord = oriRecord.clone();
				targetListRecord.setId(null);
				this.getbaseService().makePersistent(targetListRecord);
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
					"Type", "Description", "Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				TargetList targetList = baseService.getEntityById(TargetList.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], targetList.getId());
				data1.put(header[1],
						CommonUtil.fromNullToEmpty(targetList.getName()));
				if (targetList.getType() != null) {
					data1.put(header[2], targetList.getType().getId());
				} else {
					data1.put(header[2], "");
				}				
				data1.put(header[3],
						CommonUtil.fromNullToEmpty(targetList.getDescription()));
				if (targetList.getAssigned_to() != null) {
					data1.put(header[4], targetList.getAssigned_to().getId());
				} else {
					data1.put(header[4], "");
				}
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

				TargetList targetList = new TargetList();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						targetList.setId(Integer.parseInt(id));
					}				
					targetList.setName(CommonUtil.fromNullToEmpty(row.get("Name")));
					String typeID = row.get("Type");					
					if (CommonUtil.isNullOrEmpty(typeID)) {
						targetList.setType(null);
					} else {
						TargetListType type = targetListTypeService
								.getEntityById(TargetListType.class,
										Integer.parseInt(typeID));
						targetList.setType(type);
					}										
					targetList.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						targetList.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						targetList.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(targetList);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					String Name = CommonUtil.fromNullToEmpty(targetList.getName());
					failedMsg.put(Name , e.getMessage());
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
	
	public IBaseService<TargetList> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<TargetList> baseService) {
		this.baseService = baseService;
	}

	public TargetList getTargetList() {
		return targetList;
	}

	public void setTargetList(TargetList targetList) {
		this.targetList = targetList;
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
	 * @return the userService
	 */
	public IBaseService<User> getUserService() {
		return userService;
	}



	/**
	 * @param userService the userService to set
	 */
	public void setUserService(IBaseService<User> userService) {
		this.userService = userService;
	}



	/**
	 * @return the targetListTypeService
	 */
	public IBaseService<TargetListType> getTargetListTypeService() {
		return targetListTypeService;
	}

	/**
	 * @param targetListTypeService the targetListTypeService to set
	 */
	public void setTargetListTypeService(
			IBaseService<TargetListType> targetListTypeService) {
		this.targetListTypeService = targetListTypeService;
	}

}
