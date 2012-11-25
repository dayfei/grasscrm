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
import java.util.Collection;
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
import com.gcrm.domain.Target;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Target
 * 
 */
public class ListTargetAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Target> baseService;
	private IBaseService<Account> accountService;
	private IBaseService<User> userService;
	private IBaseService<TargetList> targetListService;	
	private Target target;

	private static final String CLAZZ = Target.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */	
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Target> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<Target> targets = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(targets,totalRecords);
		return null;
	}
	
    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */	
	public static void getListJson(Iterator<Target> targets,long totalRecords) throws Exception {

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";

		String userName = null;
		String accountName = null;
		while (targets.hasNext()) {
			Target instance = targets.next();
			int id = instance.getId();
			String name = CommonUtil.fromNullToEmpty(instance.getFirst_name())
					+ " " + CommonUtil.fromNullToEmpty(instance.getLast_name());
			String title = CommonUtil.fromNullToEmpty(instance.getTitle());

			Account account = instance.getAccount();
			if (account != null) {
				accountName = CommonUtil.fromNullToEmpty(account.getName());
			} else {
				accountName = "";
			}
			String email = CommonUtil.fromNullToEmpty(instance.getEmail());
			String officePhone = CommonUtil.fromNullToEmpty(instance
					.getOffice_phone());
			User user = instance.getAssigned_to();
			if (user != null) {
				userName = CommonUtil.fromNullToEmpty(user.getName());
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
					+ "\",\"title\":\"" + title + "\",\"accountName\":\""
					+ accountName + "\",\"email\":\"" + email
					+ "\",\"officePhone\":\"" + officePhone
					+ "\",\"user_name\":\"" + userName + 
					"\",\"created_by\":\"" + createdByName + 
					"\",\"updated_by\":\"" + updatedByName + 
					"\",\"created_on\":\"" + createdOnName + 
					"\",\"updated_on\":\"" + updatedOnName + 
					"\"}";
			if (targets.hasNext()) {
				json += ",";
			}
		}
		json += "]}";

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().write(json);
	}			
	
    /**
     * Selects the entities
     * 
     * @return the SUCCESS result
     */	
	public String select() throws ServiceException {
		TargetList targetList = null;
		Set<Target> targets = null;
		
		if ("TargetList".equals(this.getRelationKey())) {
			targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
			targets= targetList.getTargets();		
		}
		
		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String selectId = ids[i];
				target = baseService.getEntityById(Target.class,
						Integer.valueOf(selectId));
				targets.add(target);
			}
		}
		
		if ("TargetList".equals(this.getRelationKey())) {
			targetListService.makePersistent(targetList);
		}
		return SUCCESS;
	}	
	
    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */	
	public String unselect() throws ServiceException {
		TargetList targetList = null;
		Set<Target> targets = null;
		
		if ("TargetList".equals(this.getRelationKey())) {
			targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
			targets= targetList.getTargets();			
		}
		
		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			Collection<Target> selectedTargets = new ArrayList<Target>(); 
			for (int i = 0; i < ids.length; i++) {
				Integer selectId = Integer.valueOf(ids[i]);
				A:for (Target target : targets){
					if (target.getId().intValue() == selectId.intValue()){
						selectedTargets.add(target);
				       break A;
					}
				}				
			}
			targets.removeAll(selectedTargets);
		}
		
		if ("TargetList".equals(this.getRelationKey())) {
			targetListService.makePersistent(targetList);
		}
		return SUCCESS;
	}			
	
    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */		
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Target.class, this.getSeleteIDs());
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
				Target oriRecord = baseService.getEntityById(Target.class,
						Integer.valueOf(copyid));
				Target targetRecord = oriRecord.clone();
				targetRecord.setId(null);
				this.getbaseService().makePersistent(targetRecord);
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
			final String[] header = new String[] { "ID", "First Name",
					"Last Name", "Office Phone", "Title", "Mobile",
					"Department", "Fax", "Account", "Primary Address",
					"Primary City", "Primary State", "Primary Postal Code",
					"Primary Country", "Other Address", "Other City",
					"Other State", "Other Postal Code", "Other Country",
					"Email", "Description", "Do Not Call", "Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Target target = baseService.getEntityById(Target.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], target.getId());
				data1.put(header[1],
						CommonUtil.fromNullToEmpty(target.getFirst_name()));
				data1.put(header[2],
						CommonUtil.fromNullToEmpty(target.getLast_name()));
				data1.put(header[3],
						CommonUtil.fromNullToEmpty(target.getOffice_phone()));
				data1.put(header[4],
						CommonUtil.fromNullToEmpty(target.getTitle()));
				data1.put(header[5],
						CommonUtil.fromNullToEmpty(target.getMobile()));
				data1.put(header[6],
						CommonUtil.fromNullToEmpty(target.getDepartment()));
				data1.put(header[7],
						CommonUtil.fromNullToEmpty(target.getFax()));
				if (target.getAccount() != null) {
					data1.put(header[8], target.getAccount().getId());
				} else {
					data1.put(header[8], "");
				}
				data1.put(header[9],
						CommonUtil.fromNullToEmpty(target.getPrimary_address()));
				data1.put(header[10],
						CommonUtil.fromNullToEmpty(target.getPrimary_city()));
				data1.put(header[11],
						CommonUtil.fromNullToEmpty(target.getPrimary_state()));
				data1.put(header[12], CommonUtil.fromNullToEmpty(target
						.getPrimary_postal_code()));
				data1.put(header[13],
						CommonUtil.fromNullToEmpty(target.getPrimary_country()));
				data1.put(header[14],
						CommonUtil.fromNullToEmpty(target.getOther_address()));
				data1.put(header[15],
						CommonUtil.fromNullToEmpty(target.getOther_city()));
				data1.put(header[16],
						CommonUtil.fromNullToEmpty(target.getOther_state()));
				data1.put(header[17], CommonUtil.fromNullToEmpty(target
						.getOther_postal_code()));
				data1.put(header[18],
						CommonUtil.fromNullToEmpty(target.getOther_country()));
				data1.put(header[19],
						CommonUtil.fromNullToEmpty(target.getEmail()));
				data1.put(header[20],
						CommonUtil.fromNullToEmpty(target.getDescription()));
				data1.put(header[21], target.isNot_call());
				if (target.getAssigned_to() != null) {
					data1.put(header[22], target.getAssigned_to().getId());
				} else {
					data1.put(header[22], "");
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

				Target target = new Target();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						target.setId(Integer.parseInt(id));
					}
					target.setFirst_name(CommonUtil.fromNullToEmpty(row
							.get("First Name")));
					target.setLast_name(CommonUtil.fromNullToEmpty(row
							.get("Last Name")));
					target.setOffice_phone(CommonUtil.fromNullToEmpty(row
							.get("Office Phone")));
					target.setTitle(CommonUtil.fromNullToEmpty(row.get("Title")));
					target.setMobile(CommonUtil.fromNullToEmpty(row
							.get("Mobile")));
					target.setDepartment(CommonUtil.fromNullToEmpty(row
							.get("Department")));
					target.setFax(CommonUtil.fromNullToEmpty(row.get("Fax")));
					String accountID = row.get("Account");
					if (CommonUtil.isNullOrEmpty(accountID)) {
						target.setAccount(null);
					} else {
						Account account = accountService.getEntityById(
								Account.class, Integer.parseInt(accountID));
						target.setAccount(account);
					}
					target.setPrimary_address(CommonUtil.fromNullToEmpty(row
							.get("Primary Address")));
					target.setPrimary_city(CommonUtil.fromNullToEmpty(row
							.get("Primary City")));
					target.setPrimary_state(CommonUtil.fromNullToEmpty(row
							.get("Primary State")));
					target.setPrimary_postal_code(CommonUtil
							.fromNullToEmpty(row.get("Primary Postal Code")));
					target.setPrimary_country(CommonUtil.fromNullToEmpty(row
							.get("Primary Country")));
					target.setOther_address(CommonUtil.fromNullToEmpty(row
							.get("Other Address")));
					target.setOther_city(CommonUtil.fromNullToEmpty(row
							.get("Other City")));
					target.setOther_state(CommonUtil.fromNullToEmpty(row
							.get("Other State")));
					target.setOther_postal_code(CommonUtil.fromNullToEmpty(row
							.get("Other Postal Code")));
					target.setOther_country(CommonUtil.fromNullToEmpty(row
							.get("Other Country")));
					target.setEmail(CommonUtil.fromNullToEmpty(row.get("Email")));
					target.setEmail(CommonUtil.fromNullToEmpty(row.get("Email")));
					target.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					String doNotCall = row.get("Do Not Call");
					if (CommonUtil.isNullOrEmpty(doNotCall)) {
						target.setNot_call(false);
					} else {
						target.setNot_call(Boolean.parseBoolean(doNotCall));
					}
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						target.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						target.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(target);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					String firstName = CommonUtil.fromNullToEmpty(target
							.getFirst_name());
					String lastName = CommonUtil.fromNullToEmpty(target
							.getLast_name());
					failedMsg.put(firstName + " " + lastName, e.getMessage());
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

	public IBaseService<Target> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Target> baseService) {
		this.baseService = baseService;
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
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
	 * @return the accountService
	 */
	public IBaseService<Account> getAccountService() {
		return accountService;
	}

	/**
	 * @param accountService
	 *            the accountService to set
	 */
	public void setAccountService(IBaseService<Account> accountService) {
		this.accountService = accountService;
	}

	/**
	 * @return the userService
	 */
	public IBaseService<User> getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IBaseService<User> userService) {
		this.userService = userService;
	}

	/**
	 * @return the targetListService
	 */
	public IBaseService<TargetList> getTargetListService() {
		return targetListService;
	}

	/**
	 * @param targetListService the targetListService to set
	 */
	public void setTargetListService(IBaseService<TargetList> targetListService) {
		this.targetListService = targetListService;
	}

}
