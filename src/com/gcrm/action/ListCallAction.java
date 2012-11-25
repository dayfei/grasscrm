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

import com.gcrm.domain.Call;
import com.gcrm.domain.CallDirection;
import com.gcrm.domain.CallStatus;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.ReminderOption;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Call
 * 
 */
public class ListCallAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Call> baseService;
	private IBaseService<CallDirection> callDirectionService;
	private IBaseService<CallStatus> callStatusService;
	private IBaseService<ReminderOption> reminderOptionService;
	private IBaseService<User> userService;
	private Call call;

	private static final String CLAZZ = Call.class.getSimpleName();

	/**
	 * Gets the list data.
	 * 
	 * @return null
	 */
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Call> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		List<Call> calls = result.getResult();

		long totalRecords = result.getTotalRecords();

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = calls.size();

		String statusName = null;
		String directionName = null;
		String userName = null;
		for (int i = 0; i < size; i++) {
			Call instance = (Call) calls.get(i);
			int id = instance.getId();
			CallDirection direction = instance.getDirection();
			if (direction != null) {
				directionName = CommonUtil.fromNullToEmpty(direction.getName());
			} else {
				directionName = "";
			}
			String subject = CommonUtil.fromNullToEmpty(instance.getSubject());
			CallStatus status = instance.getStatus();
			if (status != null) {
				statusName = CommonUtil.fromNullToEmpty(status.getName());
			} else {
				statusName = "";
			}
			Date start_date = instance.getStart_date();
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
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					Constant.DATE_TIME_FORMAT);
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

			json += "{\"id\":\"" + id + "\",\"direction\":\"" + directionName
					+ "\",\"subject\":\"" + subject + "\",\"statusName\":\""
					+ statusName + "\",\"start_date\":\"" + start_date
					+ "\",\"user_name\":\"" + userName + "\",\"created_by\":\""
					+ createdByName + "\",\"updated_by\":\"" + updatedByName
					+ "\",\"created_on\":\"" + createdOnName
					+ "\",\"updated_on\":\"" + updatedOnName + "\"}";
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
	 * Gets the related leads.
	 * 
	 * @return null
	 */
	public String filterCallLead() throws Exception {
		call = baseService.getEntityById(Call.class, id);
		Set<Lead> leads = call.getLeads();
		Iterator<Lead> leadIterator = leads.iterator();
		long totalRecords = leads.size();
		ListLeadAction.getListJson(leadIterator, totalRecords);
		return null;
	}

	/**
	 * Gets the related contacts.
	 * 
	 * @return null
	 */
	public String filterCallContact() throws Exception {
		call = baseService.getEntityById(Call.class, id);
		Set<Contact> contacts = call.getContacts();
		Iterator<Contact> contactIterator = contacts.iterator();
		long totalRecords = contacts.size();
		ListContactAction.getListJson(contactIterator, totalRecords);
		return null;
	}

	/**
	 * Gets the related users.
	 * 
	 * @return null
	 */
	public String filterCallUser() throws Exception {
		call = baseService.getEntityById(Call.class, id);
		Set<User> users = call.getUsers();
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
		baseService.batchDeleteEntity(Call.class, this.getSeleteIDs());
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
				Call oriRecord = baseService.getEntityById(Call.class,
						Integer.valueOf(copyid));
				Call targetRecord = oriRecord.clone();
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
			final String[] header = new String[] { "ID", "Subject",
					"Direction", "Status", "Start Date", "Reminder Way Popup",
					"Reminder Option Popup", "Reminder Way Email",
					"Reminder Option Email", "Description", "Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Call call = baseService.getEntityById(Call.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], call.getId());
				data1.put(header[1],
						CommonUtil.fromNullToEmpty(call.getSubject()));
				data1.put(header[2], call.getDirection());
				if (call.getStatus() != null) {
					data1.put(header[3], call.getStatus().getId());
				} else {
					data1.put(header[3], "");
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"M/d/yyyy HH:mm:ss");
				Date startDate = call.getStart_date();
				if (startDate != null) {
					data1.put(header[4], dateFormat.format(startDate));
				} else {
					data1.put(header[4], "");
				}
				data1.put(header[5], call.isReminder_pop());
				if (call.getReminder_option_pop() != null) {
					data1.put(header[6], call.getReminder_option_pop().getId());
				} else {
					data1.put(header[6], "");
				}
				data1.put(header[7], call.isReminder_email());
				if (call.getReminder_option_email() != null) {
					data1.put(header[8], call.getReminder_option_email()
							.getId());
				} else {
					data1.put(header[8], "");
				}
				data1.put(header[9],
						CommonUtil.fromNullToEmpty(call.getDescription()));
				if (call.getAssigned_to() != null) {
					data1.put(header[10], call.getAssigned_to().getId());
				} else {
					data1.put(header[10], "");
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

				Call call = new Call();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						call.setId(Integer.parseInt(id));
					}
					call.setSubject(CommonUtil.fromNullToEmpty(row
							.get("Subject")));
					String directionID = row.get("Direction");
					if (CommonUtil.isNullOrEmpty(directionID)) {
						call.setDirection(null);
					} else {
						CallDirection callDirection = callDirectionService
								.getEntityById(CallDirection.class,
										Integer.parseInt(directionID));
						call.setDirection(callDirection);
					}
					String statusID = row.get("Status");
					if (CommonUtil.isNullOrEmpty(statusID)) {
						call.setStatus(null);
					} else {
						CallStatus status = callStatusService.getEntityById(
								CallStatus.class, Integer.parseInt(statusID));
						call.setStatus(status);
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"M/d/yyyy HH:mm:ss");
					String startDateS = row.get("Start Date");
					if (startDateS != null) {
						Date startDate = dateFormat.parse(startDateS);
						call.setStart_date(startDate);
					} else {
						call.setStart_date(null);
					}
					String reminderWayPop = row.get("Reminder Way Popup");
					if (CommonUtil.isNullOrEmpty(reminderWayPop)) {
						call.setReminder_pop(false);
					} else {
						call.setReminder_pop(Boolean
								.parseBoolean(reminderWayPop));
					}
					String reminderOptionPopID = row
							.get("Reminder Option Popup");
					if (CommonUtil.isNullOrEmpty(reminderOptionPopID)) {
						call.setReminder_option_pop(null);
					} else {
						ReminderOption reminderOption = reminderOptionService
								.getEntityById(ReminderOption.class,
										Integer.parseInt(reminderOptionPopID));
						call.setReminder_option_pop(reminderOption);
					}
					String reminderWayEmail = row.get("Reminder Way Email");
					if (CommonUtil.isNullOrEmpty(reminderWayEmail)) {
						call.setReminder_email(false);
					} else {
						call.setReminder_email(Boolean
								.parseBoolean(reminderWayEmail));
					}
					String reminderOptionEmailID = row
							.get("Reminder Option Email");
					if (CommonUtil.isNullOrEmpty(reminderOptionEmailID)) {
						call.setReminder_option_email(null);
					} else {
						ReminderOption reminderOption = reminderOptionService
								.getEntityById(ReminderOption.class,
										Integer.parseInt(reminderOptionEmailID));
						call.setReminder_option_email(reminderOption);
					}
					call.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						call.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						call.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(call);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					failedMsg.put(call.getSubject(), e.getMessage());
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

	public IBaseService<Call> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Call> baseService) {
		this.baseService = baseService;
	}

	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
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
	 * @return the callStatusService
	 */
	public IBaseService<CallStatus> getCallStatusService() {
		return callStatusService;
	}

	/**
	 * @param callStatusService
	 *            the callStatusService to set
	 */
	public void setCallStatusService(IBaseService<CallStatus> callStatusService) {
		this.callStatusService = callStatusService;
	}

	/**
	 * @return the reminderOptionService
	 */
	public IBaseService<ReminderOption> getReminderOptionService() {
		return reminderOptionService;
	}

	/**
	 * @param reminderOptionService
	 *            the reminderOptionService to set
	 */
	public void setReminderOptionService(
			IBaseService<ReminderOption> reminderOptionService) {
		this.reminderOptionService = reminderOptionService;
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
	 * @return the callDirectionService
	 */
	public IBaseService<CallDirection> getCallDirectionService() {
		return callDirectionService;
	}

	/**
	 * @param callDirectionService
	 *            the callDirectionService to set
	 */
	public void setCallDirectionService(
			IBaseService<CallDirection> callDirectionService) {
		this.callDirectionService = callDirectionService;
	}

}
