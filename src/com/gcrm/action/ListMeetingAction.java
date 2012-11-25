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

import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.MeetingStatus;
import com.gcrm.domain.ReminderOption;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Meeting
 * 
 */
public class ListMeetingAction extends BaseListAction{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Meeting> baseService;
	private IBaseService<MeetingStatus> meetingStatusService;
	private IBaseService<ReminderOption> reminderOptionService;
	private IBaseService<User> userService;	
	private Meeting meeting;

	private static final String CLAZZ = Meeting.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */	
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Meeting> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		List<Meeting> meetings = result.getResult();

		long totalRecords = result.getTotalRecords();

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = meetings.size();

		String statusName = null;
		String userName = null;
		for (int i = 0; i < size; i++) {
			Meeting instance = (Meeting) meetings.get(i);
			int id = instance.getId();
			String subject = CommonUtil.fromNullToEmpty(instance.getSubject());
			MeetingStatus status = instance.getStatus();
			if (status != null) {
				statusName = CommonUtil.fromNullToEmpty(status.getName());
			} else {
				statusName = "";
			}
			Date startDate = instance.getStart_date();
			Date endDate = instance.getEnd_date();
			String location = instance.getLocation();
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
			
			json += "{\"id\":\"" + id + "\",\"subject\":\"" + subject
					+ "\",\"status\":\"" + statusName + "\",\"startDate\":\""
					+ startDate + "\",\"end_date\":\"" + endDate
					+ "\",\"location\":\"" + location
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
     * Gets the related leads
     * 
     * @return null
     */		
	public String filterMeetingLead() throws Exception {
		meeting = baseService.getEntityById(Meeting.class, id);
		Set<Lead> leads = meeting.getLeads();
		Iterator<Lead> leadIterator = leads.iterator();
		long totalRecords = leads.size();
		ListLeadAction.getListJson(leadIterator, totalRecords);
		return null;
	}
	
    /**
     * Gets the related contacts
     * 
     * @return null
     */		
	public String filterMeetingContact() throws Exception {
		meeting = baseService.getEntityById(Meeting.class, id);
		Set<Contact> contacts = meeting.getContacts();
		Iterator<Contact> contactIterator = contacts.iterator();
		long totalRecords = contacts.size();
		ListContactAction.getListJson(contactIterator, totalRecords);
		return null;
	}	
	
    /**
     * Gets the related users
     * 
     * @return null
     */		
	public String filterMeetingUser() throws Exception {
		meeting = baseService.getEntityById(Meeting.class, id);
		Set<User> users = meeting.getUsers();
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
		baseService.batchDeleteEntity(Meeting.class, this.getSeleteIDs());
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
				Meeting oriRecord = baseService.getEntityById(Meeting.class, Integer.valueOf(copyid));
				Meeting targetRecord = oriRecord.clone();
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
					"Status", "Start Date", "End Date", "Related Object",
					"Related Record","Location","Reminder Way Popup", "Reminder Option Popup",
					"Reminder Way Email", "Reminder Option Email",
					"Description", "Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Meeting meeting = baseService.getEntityById(Meeting.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], meeting.getId());
				data1.put(header[1],
						CommonUtil.fromNullToEmpty(meeting.getSubject()));
				if (meeting.getStatus() != null) {
					data1.put(header[2], meeting.getStatus().getId());
				} else {
					data1.put(header[2], "");
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy HH:mm:ss");
				Date startDate = meeting.getStart_date();
				if (startDate != null) {
					data1.put(header[3], dateFormat.format(startDate));
				} else {
					data1.put(header[3], "");
				}
				Date endDate = meeting.getEnd_date();
				if (endDate != null) {
					data1.put(header[4], dateFormat.format(endDate));
				} else {
					data1.put(header[4], "");
				}	
				data1.put(header[5],
						CommonUtil.fromNullToEmpty(meeting.getRelated_object()));					
				data1.put(header[6], meeting.getRelated_record());
				data1.put(header[7],
						CommonUtil.fromNullToEmpty(meeting.getLocation()));					
				data1.put(header[8], meeting.isReminder_pop());	
				if (meeting.getReminder_option_pop() != null) {
					data1.put(header[9], meeting.getReminder_option_pop().getId());
				} else {
					data1.put(header[9], "");
				}
				data1.put(header[10], meeting.isReminder_email());	
				if (meeting.getReminder_option_email() != null) {
					data1.put(header[11], meeting.getReminder_option_email().getId());
				} else {
					data1.put(header[11], "");
				}				
				data1.put(header[12],
						CommonUtil.fromNullToEmpty(meeting.getDescription()));
				if (meeting.getAssigned_to() != null) {
					data1.put(header[13], meeting.getAssigned_to().getId());
				} else {
					data1.put(header[13], "");
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
				Meeting meeting = new Meeting();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						meeting.setId(Integer.parseInt(id));
					}
					meeting.setSubject(CommonUtil.fromNullToEmpty(row.get("Subject")));													
					String statusID = row.get("Status");
					if (CommonUtil.isNullOrEmpty(statusID)) {
						meeting.setStatus(null);
					} else {
						MeetingStatus status = meetingStatusService
								.getEntityById(MeetingStatus.class,
										Integer.parseInt(statusID));
						meeting.setStatus(status);
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"M/d/yyyy HH:mm:ss");
					String startDateS = row.get("Start Date");
					if (startDateS != null) {
						Date startDate = dateFormat.parse(startDateS);
						meeting.setStart_date(startDate);
					} else {
						meeting.setStart_date(null);
					}
					String endDateS = row.get("End Date");
					if (endDateS != null) {
						Date endDate = dateFormat.parse(endDateS);
						meeting.setEnd_date(endDate);
					} else {
						meeting.setEnd_date(null);
					}				
					meeting.setRelated_object(CommonUtil.fromNullToEmpty(row
							.get("Related Object")));									
					String relatedRecord = row.get("Related Record");
					if (CommonUtil.isNullOrEmpty(relatedRecord)) {
						meeting.setRelated_record(0);
					} else {
						meeting.setRelated_record(Integer.parseInt(relatedRecord));
					}	
					meeting.setLocation(CommonUtil.fromNullToEmpty(row
							.get("Location")));									
					String reminderWayPop = row.get("Reminder Way Popup");
					if (CommonUtil.isNullOrEmpty(reminderWayPop)) {
						meeting.setReminder_pop(false);
					} else {
						meeting.setReminder_pop(Boolean.parseBoolean(reminderWayPop));
					}						
					String reminderOptionPopID = row.get("Reminder Option Popup");
					if (CommonUtil.isNullOrEmpty(reminderOptionPopID)) {
						meeting.setReminder_option_pop(null);
					} else {
						ReminderOption reminderOption = reminderOptionService
								.getEntityById(ReminderOption.class,
										Integer.parseInt(reminderOptionPopID));
						meeting.setReminder_option_pop(reminderOption);
					}
					String reminderWayEmail = row.get("Reminder Way Email");
					if (CommonUtil.isNullOrEmpty(reminderWayEmail)) {
						meeting.setReminder_email(false);
					} else {
						meeting.setReminder_email(Boolean.parseBoolean(reminderWayEmail));
					}						
					String reminderOptionEmailID = row.get("Reminder Option Email");
					if (CommonUtil.isNullOrEmpty(reminderOptionEmailID)) {
						meeting.setReminder_option_email(null);
					} else {
						ReminderOption reminderOption = reminderOptionService
								.getEntityById(ReminderOption.class,
										Integer.parseInt(reminderOptionEmailID));
						meeting.setReminder_option_email(reminderOption);
					}
					meeting.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						meeting.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						meeting.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(meeting);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					failedMsg.put(meeting.getSubject(), e.getMessage());
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
	
	public IBaseService<Meeting> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Meeting> baseService) {
		this.baseService = baseService;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
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
	 * @return the meetingStatusService
	 */
	public IBaseService<MeetingStatus> getMeetingStatusService() {
		return meetingStatusService;
	}



	/**
	 * @param meetingStatusService the meetingStatusService to set
	 */
	public void setMeetingStatusService(
			IBaseService<MeetingStatus> meetingStatusService) {
		this.meetingStatusService = meetingStatusService;
	}



	/**
	 * @return the reminderOptionService
	 */
	public IBaseService<ReminderOption> getReminderOptionService() {
		return reminderOptionService;
	}



	/**
	 * @param reminderOptionService the reminderOptionService to set
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
	 * @param userService the userService to set
	 */
	public void setUserService(IBaseService<User> userService) {
		this.userService = userService;
	}

}
