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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


import com.gcrm.domain.Contact;
import com.gcrm.domain.Task;
import com.gcrm.domain.TaskPriority;
import com.gcrm.domain.TaskStatus;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Task
 * 
 */
public class EditTaskAction extends BaseEditAction implements Preparable {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Task> baseService;
	private IBaseService<TaskStatus> taskStatusService;
	private IBaseService<Contact> contactService;
	private IBaseService<TaskPriority> taskPriorityService;
	private IBaseService<User> userService;
	private Task task = new Task();
	private List<TaskStatus> statuses;
	private List<TaskPriority> priorities;
	private Integer statusID = null;
	private Integer priorityID = null;
	private Integer contactID = null;
	private Integer relatedAccountID = null;
	private Integer relatedCaseID = null;
	private Integer relatedContactID = null;
	private Integer relatedLeadID = null;
	private Integer relatedOpportunityID = null;
	private Integer relatedTargetID = null;
	private Integer relatedTaskID = null;
	private Integer assignedToID = null;
	private String startDate = null;
	private String dueDate = null;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */	
	public String save() throws Exception {
		TaskStatus status = null;
		if (statusID != null) {
			status = taskStatusService
					.getEntityById(TaskStatus.class, statusID);
		}
		task.setStatus(status);
		TaskPriority priority = null;
		if (priorityID != null) {
			priority = taskPriorityService.getEntityById(TaskPriority.class,
					priorityID);
		}
		task.setPriority(priority);
		Contact contact = null;
		if (contactID != null) {
			contact = contactService.getEntityById(Contact.class, contactID);
		}
		task.setContact(contact);
		User assignedTo = null;
		if (assignedToID != null) {
			assignedTo = userService.getEntityById(User.class, assignedToID);
		}
		task.setAssigned_to(assignedTo);
		SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy HH:mm:ss");
		Date start_date = null;
		if (!CommonUtil.isNullOrEmpty(startDate)) {
			start_date = dateFormat.parse(startDate);
		}
		task.setStart_date(start_date);
		Date due_date = null;
		if (!CommonUtil.isNullOrEmpty(dueDate)) {
			due_date = dateFormat.parse(dueDate);
		}
		task.setDue_date(due_date);
		String relatedObject = task.getRelated_object();
		if ("Account".equals(relatedObject)) {
			task.setRelated_record(relatedAccountID);
		} else if ("Case".equals(relatedObject)) {
			task.setRelated_record(relatedCaseID);
		} else if ("Contact".equals(relatedObject)) {
			task.setRelated_record(relatedContactID);
		} else if ("Lead".equals(relatedObject)) {
			task.setRelated_record(relatedLeadID);
		} else if ("Opportunity".equals(relatedObject)) {
			task.setRelated_record(relatedOpportunityID);
		} else if ("Target".equals(relatedObject)) {
			task.setRelated_record(relatedTargetID);
		} else if ("Task".equals(relatedObject)) {
			task.setRelated_record(relatedTaskID);
		}
		
		super.updateBaseInfo(task);
		
		getBaseService().makePersistent(task);
		return SUCCESS;
	}

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		if (this.getId() != null) {
			task = baseService.getEntityById(Task.class, this.getId());
			TaskStatus status = task.getStatus();
			if (status != null) {
				statusID = status.getId();
			}
			TaskPriority priority = task.getPriority();
			if (priority != null) {
				priorityID = priority.getId();
			}
			Contact contact = task.getContact();
			if (contact != null) {
				contactID = contact.getId();
			}
			User assignedTo = task.getAssigned_to();
			if (assignedTo != null) {
				assignedToID = assignedTo.getId();
			}
			Date start_date = task.getStart_date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"M/d/yyyy HH:mm:ss");
			if (start_date != null) {
				startDate = dateFormat.format(start_date);
			}
			Date due_date = task.getDue_date();
			if (due_date != null) {
				dueDate = dateFormat.format(due_date);
			}
			String relatedObject = task.getRelated_object();
			Integer relatedRecord = task.getRelated_record();
			setRelatedRecord(relatedObject, relatedRecord);

		} else {
			ActionContext context = ActionContext.getContext();
			Map<String, Object> session = context.getSession();
			User loginUser = (User) session
					.get(AuthenticationSuccessListener.LOGIN_USER);
			this.assignedToID = loginUser.getId();
			if (!CommonUtil.isNullOrEmpty(this.getRelationKey())) {
				task.setRelated_object(this.getRelationKey());
				setRelatedRecord(this.getRelationKey(),
						Integer.parseInt(this.getRelationValue()));
			}
		}
		return SUCCESS;
	}

    /**
     * Sets the related record ID
     * 
     * @param relatedObject Related Object name
     * @param relatedRecord Related Record ID
     */
	private void setRelatedRecord(String relatedObject, Integer relatedRecord) {
		if ("Account".equals(relatedObject)) {
			this.relatedAccountID = relatedRecord;
		} else if ("Case".equals(relatedObject)) {
			this.relatedCaseID = relatedRecord;
		} else if ("Contact".equals(relatedObject)) {
			this.relatedContactID = relatedRecord;
		} else if ("Lead".equals(relatedObject)) {
			this.relatedLeadID = relatedRecord;
		} else if ("Opportunity".equals(relatedObject)) {
			this.relatedOpportunityID = relatedRecord;
		} else if ("Target".equals(relatedObject)) {
			this.relatedTargetID = relatedRecord;
		} else if ("Task".equals(relatedObject)) {
			this.relatedTaskID = relatedRecord;
		}
	}

    /**
     * Prepares the list
     * 
     */	
	public void prepare() throws Exception {
		this.statuses = taskStatusService.getAllObjects(TaskStatus.class
				.getSimpleName());
		this.priorities = taskPriorityService.getAllObjects(TaskPriority.class
				.getSimpleName());
	}

	/**
	 * @return the baseService
	 */
	public IBaseService<Task> getBaseService() {
		return baseService;
	}

	/**
	 * @param baseService
	 *            the baseService to set
	 */
	public void setBaseService(IBaseService<Task> baseService) {
		this.baseService = baseService;
	}

	/**
	 * @return the taskStatusService
	 */
	public IBaseService<TaskStatus> getTaskStatusService() {
		return taskStatusService;
	}

	/**
	 * @param taskStatusService
	 *            the taskStatusService to set
	 */
	public void setTaskStatusService(IBaseService<TaskStatus> taskStatusService) {
		this.taskStatusService = taskStatusService;
	}

	/**
	 * @return the contactService
	 */
	public IBaseService<Contact> getContactService() {
		return contactService;
	}

	/**
	 * @param contactService
	 *            the contactService to set
	 */
	public void setContactService(IBaseService<Contact> contactService) {
		this.contactService = contactService;
	}

	/**
	 * @return the taskPriorityService
	 */
	public IBaseService<TaskPriority> getTaskPriorityService() {
		return taskPriorityService;
	}

	/**
	 * @param taskPriorityService
	 *            the taskPriorityService to set
	 */
	public void setTaskPriorityService(
			IBaseService<TaskPriority> taskPriorityService) {
		this.taskPriorityService = taskPriorityService;
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
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * @return the statuses
	 */
	public List<TaskStatus> getStatuses() {
		return statuses;
	}

	/**
	 * @param statuses
	 *            the statuses to set
	 */
	public void setStatuses(List<TaskStatus> statuses) {
		this.statuses = statuses;
	}

	/**
	 * @return the priorities
	 */
	public List<TaskPriority> getPriorities() {
		return priorities;
	}

	/**
	 * @param priorities
	 *            the priorities to set
	 */
	public void setPriorities(List<TaskPriority> priorities) {
		this.priorities = priorities;
	}

	/**
	 * @return the statusID
	 */
	public Integer getStatusID() {
		return statusID;
	}

	/**
	 * @param statusID
	 *            the statusID to set
	 */
	public void setStatusID(Integer statusID) {
		this.statusID = statusID;
	}

	/**
	 * @return the priorityID
	 */
	public Integer getPriorityID() {
		return priorityID;
	}

	/**
	 * @param priorityID
	 *            the priorityID to set
	 */
	public void setPriorityID(Integer priorityID) {
		this.priorityID = priorityID;
	}

	/**
	 * @return the contactID
	 */
	public Integer getContactID() {
		return contactID;
	}

	/**
	 * @param contactID
	 *            the contactID to set
	 */
	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

	/**
	 * @return the assignedToID
	 */
	public Integer getAssignedToID() {
		return assignedToID;
	}

	/**
	 * @param assignedToID
	 *            the assignedToID to set
	 */
	public void setAssignedToID(Integer assignedToID) {
		this.assignedToID = assignedToID;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the dueDate
	 */
	public String getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate
	 *            the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the relatedAccountID
	 */
	public Integer getRelatedAccountID() {
		return relatedAccountID;
	}

	/**
	 * @param relatedAccountID
	 *            the relatedAccountID to set
	 */
	public void setRelatedAccountID(Integer relatedAccountID) {
		this.relatedAccountID = relatedAccountID;
	}

	/**
	 * @return the relatedCaseID
	 */
	public Integer getRelatedCaseID() {
		return relatedCaseID;
	}

	/**
	 * @param relatedCaseID
	 *            the relatedCaseID to set
	 */
	public void setRelatedCaseID(Integer relatedCaseID) {
		this.relatedCaseID = relatedCaseID;
	}

	/**
	 * @return the relatedContactID
	 */
	public Integer getRelatedContactID() {
		return relatedContactID;
	}

	/**
	 * @param relatedContactID
	 *            the relatedContactID to set
	 */
	public void setRelatedContactID(Integer relatedContactID) {
		this.relatedContactID = relatedContactID;
	}

	/**
	 * @return the relatedLeadID
	 */
	public Integer getRelatedLeadID() {
		return relatedLeadID;
	}

	/**
	 * @param relatedLeadID
	 *            the relatedLeadID to set
	 */
	public void setRelatedLeadID(Integer relatedLeadID) {
		this.relatedLeadID = relatedLeadID;
	}

	/**
	 * @return the relatedOpportunityID
	 */
	public Integer getRelatedOpportunityID() {
		return relatedOpportunityID;
	}

	/**
	 * @param relatedOpportunityID
	 *            the relatedOpportunityID to set
	 */
	public void setRelatedOpportunityID(Integer relatedOpportunityID) {
		this.relatedOpportunityID = relatedOpportunityID;
	}

	/**
	 * @return the relatedTargetID
	 */
	public Integer getRelatedTargetID() {
		return relatedTargetID;
	}

	/**
	 * @param relatedTargetID
	 *            the relatedTargetID to set
	 */
	public void setRelatedTargetID(Integer relatedTargetID) {
		this.relatedTargetID = relatedTargetID;
	}

	/**
	 * @return the relatedTaskID
	 */
	public Integer getRelatedTaskID() {
		return relatedTaskID;
	}

	/**
	 * @param relatedTaskID
	 *            the relatedTaskID to set
	 */
	public void setRelatedTaskID(Integer relatedTaskID) {
		this.relatedTaskID = relatedTaskID;
	}

}
