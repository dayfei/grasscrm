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


import com.gcrm.domain.Call;
import com.gcrm.domain.CallDirection;
import com.gcrm.domain.CallStatus;
import com.gcrm.domain.ReminderOption;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Call
 * 
 */
public class EditCallAction extends BaseEditAction implements Preparable{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Call> baseService;
	private IBaseService<CallStatus> callStatusService;
	private IBaseService<CallDirection> callDirectionService;
	private IBaseService<ReminderOption> reminderOptionService;
	private IBaseService<User> userService;
	private Call call;
	private List<CallStatus> statuses;
	private List<CallDirection> directions;
	private List<ReminderOption> reminderOptions;
	private Integer statusID = null;
	private Integer directionID = null;
	private Integer reminderOptionPopID = null;
	private Integer reminderOptionEmailID = null;
	private Integer assignedToID = null;
	private Integer relatedAccountID = null;
	private Integer relatedCaseID = null;
	private Integer relatedContactID = null;
	private Integer relatedLeadID = null;
	private Integer relatedOpportunityID = null;
	private Integer relatedTargetID = null;
	private Integer relatedTaskID = null;	
	private String startDate = null;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */		
	public String save() throws Exception {
		CallDirection direction = null;
		if (directionID != null) {
			direction = callDirectionService.getEntityById(CallDirection.class,
					directionID);
		}
		call.setDirection(direction);		
		CallStatus status = null;
		if (statusID != null) {
			status = callStatusService.getEntityById(CallStatus.class,
					statusID);
		}
		call.setStatus(status);
		ReminderOption reminderOptionPop = null;
		if (reminderOptionPopID != null){		
			reminderOptionPop = reminderOptionService.getEntityById(
				ReminderOption.class, reminderOptionPopID);
		}
		call.setReminder_option_pop(reminderOptionPop);
		ReminderOption reminderOptionEmail = null;
		if (reminderOptionEmailID != null){		
			reminderOptionEmail = reminderOptionService.getEntityById(
				ReminderOption.class, reminderOptionEmailID);
		}
		call.setReminder_option_email(reminderOptionEmail);		
		User user = null;
		if (assignedToID != null){
		 user = userService.getEntityById(User.class, assignedToID);
		}
		call.setAssigned_to(user);
		SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy HH:mm:ss");
		Date start_date = null;
		if (startDate != null){
		start_date = dateFormat.parse(startDate);
		}
		call.setStart_date(start_date);	
		
		String relatedObject = call.getRelated_object();
		if ("Account".equals(relatedObject)) {
			call.setRelated_record(relatedAccountID);
		} else if ("Case".equals(relatedObject)) {
			call.setRelated_record(relatedCaseID);
		} else if ("Contact".equals(relatedObject)) {
			call.setRelated_record(relatedContactID);
		} else if ("Lead".equals(relatedObject)) {
			call.setRelated_record(relatedLeadID);
		} else if ("Opportunity".equals(relatedObject)) {
			call.setRelated_record(relatedOpportunityID);
		} else if ("Target".equals(relatedObject)) {
			call.setRelated_record(relatedTargetID);
		} else if ("Task".equals(relatedObject)) {
			call.setRelated_record(relatedTaskID);
		}
		
		super.updateBaseInfo(call);
		
		getbaseService().makePersistent(call);
		return SUCCESS;
	}

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		if (this.getId() != null) {
			call = baseService.getEntityById(Call.class, this.getId());
			CallStatus status = call.getStatus();
			if (status != null) {
				statusID = status.getId();
			}
			CallDirection direction = call.getDirection();
			if (direction != null) {
				directionID = direction.getId();
			}			
			ReminderOption reminderOptionPop = call.getReminder_option_pop();
			if (reminderOptionPop != null) {
				reminderOptionPopID = reminderOptionPop.getId();
			}
			ReminderOption reminderOptionEmail = call.getReminder_option_email();
			if (reminderOptionEmail != null) {
				reminderOptionEmailID = reminderOptionEmail.getId();
			}			
			User user = call.getAssigned_to();
			if (user != null) {
				assignedToID = user.getId();
			}
			Date start_date = call.getStart_date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy HH:mm:ss");
			if (start_date != null) {
				startDate = dateFormat.format(start_date);
			}	
			String relatedObject = call.getRelated_object();
			Integer relatedRecord = call.getRelated_record();
			setRelatedRecord(relatedObject, relatedRecord);
		} else {
			ActionContext context = ActionContext.getContext();
			Map<String, Object> session = context.getSession();
			User loginUser = (User) session
					.get(AuthenticationSuccessListener.LOGIN_USER);
			this.assignedToID = loginUser.getId();
			if (!CommonUtil.isNullOrEmpty(this.getRelationKey())) {
				call.setRelated_object(this.getRelationKey());
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
		this.statuses = callStatusService.getAllObjects(CallStatus.class
				.getSimpleName());
		this.directions = callDirectionService.getAllObjects(CallDirection.class
				.getSimpleName());		
		this.reminderOptions = reminderOptionService.getAllObjects(ReminderOption.class
				.getSimpleName());		
	}
		
	
	public IBaseService<Call> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Call> baseService) {
		this.baseService = baseService;
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
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IBaseService<User> userService) {
		this.userService = userService;
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
	 * @return the callStatusService
	 */
	public IBaseService<CallStatus> getCallStatusService() {
		return callStatusService;
	}


	/**
	 * @param callStatusService the callStatusService to set
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
	 * @param reminderOptionService the reminderOptionService to set
	 */
	public void setReminderOptionService(
			IBaseService<ReminderOption> reminderOptionService) {
		this.reminderOptionService = reminderOptionService;
	}


	/**
	 * @return the reminderOptions
	 */
	public List<ReminderOption> getReminderOptions() {
		return reminderOptions;
	}


	/**
	 * @param reminderOptions the reminderOptions to set
	 */
	public void setReminderOptions(List<ReminderOption> reminderOptions) {
		this.reminderOptions = reminderOptions;
	}

	/**
	 * @return the call
	 */
	public Call getCall() {
		return call;
	}


	/**
	 * @param call the call to set
	 */
	public void setCall(Call call) {
		this.call = call;
	}


	/**
	 * @return the statuses
	 */
	public List<CallStatus> getStatuses() {
		return statuses;
	}


	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuses(List<CallStatus> statuses) {
		this.statuses = statuses;
	}


	/**
	 * @return the directionID
	 */
	public Integer getDirectionID() {
		return directionID;
	}


	/**
	 * @param directionID the directionID to set
	 */
	public void setDirectionID(Integer directionID) {
		this.directionID = directionID;
	}


	/**
	 * @return the directions
	 */
	public List<CallDirection> getDirections() {
		return directions;
	}


	/**
	 * @param directions the directions to set
	 */
	public void setDirections(List<CallDirection> directions) {
		this.directions = directions;
	}


	/**
	 * @return the callDirectionService
	 */
	public IBaseService<CallDirection> getCallDirectionService() {
		return callDirectionService;
	}


	/**
	 * @param callDirectionService the callDirectionService to set
	 */
	public void setCallDirectionService(
			IBaseService<CallDirection> callDirectionService) {
		this.callDirectionService = callDirectionService;
	}


	/**
	 * @return the baseService
	 */
	public IBaseService<Call> getBaseService() {
		return baseService;
	}


	/**
	 * @param baseService the baseService to set
	 */
	public void setBaseService(IBaseService<Call> baseService) {
		this.baseService = baseService;
	}


	/**
	 * @return the relatedAccountID
	 */
	public Integer getRelatedAccountID() {
		return relatedAccountID;
	}


	/**
	 * @param relatedAccountID the relatedAccountID to set
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
	 * @param relatedCaseID the relatedCaseID to set
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
	 * @param relatedContactID the relatedContactID to set
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
	 * @param relatedLeadID the relatedLeadID to set
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
	 * @param relatedOpportunityID the relatedOpportunityID to set
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
	 * @param relatedTargetID the relatedTargetID to set
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
	 * @param relatedTaskID the relatedTaskID to set
	 */
	public void setRelatedTaskID(Integer relatedTaskID) {
		this.relatedTaskID = relatedTaskID;
	}


	/**
	 * @return the userService
	 */
	public IBaseService<User> getUserService() {
		return userService;
	}


	/**
	 * @return the assignedToID
	 */
	public Integer getAssignedToID() {
		return assignedToID;
	}


	/**
	 * @param assignedToID the assignedToID to set
	 */
	public void setAssignedToID(Integer assignedToID) {
		this.assignedToID = assignedToID;
	}


	/**
	 * @return the reminderOptionPopID
	 */
	public Integer getReminderOptionPopID() {
		return reminderOptionPopID;
	}


	/**
	 * @param reminderOptionPopID the reminderOptionPopID to set
	 */
	public void setReminderOptionPopID(Integer reminderOptionPopID) {
		this.reminderOptionPopID = reminderOptionPopID;
	}


	/**
	 * @return the reminderOptionEmailID
	 */
	public Integer getReminderOptionEmailID() {
		return reminderOptionEmailID;
	}


	/**
	 * @param reminderOptionEmailID the reminderOptionEmailID to set
	 */
	public void setReminderOptionEmailID(Integer reminderOptionEmailID) {
		this.reminderOptionEmailID = reminderOptionEmailID;
	}
}
