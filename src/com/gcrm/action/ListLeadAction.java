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
import com.gcrm.domain.Call;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Lead;
import com.gcrm.domain.LeadSource;
import com.gcrm.domain.LeadStatus;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Lead
 * 
 */
public class ListLeadAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Lead> baseService;
	private IBaseService<Account> accountService;
	private IBaseService<LeadStatus> leadStatusService;
	private IBaseService<LeadSource> leadSourceService;
	private IBaseService<User> userService;
	private IBaseService<Campaign> campaignService;
	private IBaseService<Contact> contactService;
	private IBaseService<Opportunity> opportunityService;
	private IBaseService<TargetList> targetListService;
	private IBaseService<Call> callService;
	private IBaseService<Meeting> meetingService;
	private Lead lead;

	private static final String CLAZZ = Lead.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */	
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Lead> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<Lead> leads = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(leads,totalRecords);
		return null;
	}

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */	
	public static void getListJson(Iterator<Lead> leads,long totalRecords) throws Exception {

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";

		String userName = null;
		String accountName = null;
		while (leads.hasNext()) {
			Lead instance = leads.next();
			int id = instance.getId();
			String name = CommonUtil.fromNullToEmpty(instance.getFirst_name()) + " "
					+ CommonUtil.fromNullToEmpty(instance.getLast_name());
			String title = CommonUtil.fromNullToEmpty(instance.getTitle());

			Account account = instance.getAccount();
			if (account != null) {
				accountName = CommonUtil.fromNullToEmpty(account.getName());
			} else {
				accountName = "";
			}
			String email = CommonUtil.fromNullToEmpty(instance.getEmail());
			String officePhone = CommonUtil.fromNullToEmpty(instance.getOffice_phone());
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
			if (leads.hasNext()) {
				json += ",";
			}
		}
		json += "]}";

		// Returns JSON data back to pagee
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().write(json);
	}		

    /**
     * Selects the entities
     * 
     * @return the SUCCESS result
     */		
	public String select() throws ServiceException {
		Contact contact = null;
		Opportunity opportunity = null;
		TargetList targetList = null;
		Call call = null;
		Meeting meeting = null;
		Set<Lead> leads = null;
		
		if ("Contact".equals(this.getRelationKey())) {
			contact = contactService.getEntityById(Contact.class,
					Integer.valueOf(this.getRelationValue()));
			leads= contact.getLeads();
		} else if ("Opportunity".equals(this.getRelationKey())) {
			opportunity = opportunityService.getEntityById(Opportunity.class,
					Integer.valueOf(this.getRelationValue()));
			leads= opportunity.getLeads();		
		} else if ("TargetList".equals(this.getRelationKey())) {
			targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
			leads= targetList.getLeads();		
		} else if ("Call".equals(this.getRelationKey())) {
			call = callService.getEntityById(Call.class,
					Integer.valueOf(this.getRelationValue()));
			leads= call.getLeads();		
		} else if ("Meeting".equals(this.getRelationKey())) {
			meeting = meetingService.getEntityById(Meeting.class,
					Integer.valueOf(this.getRelationValue()));
			leads= meeting.getLeads();		
		}
		
		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String selectId = ids[i];
				lead = baseService.getEntityById(Lead.class,
						Integer.valueOf(selectId));
				leads.add(lead);
			}
		}
		if ("Contact".equals(super.getRelationKey())) {
			contactService.makePersistent(contact);
		} else if ("Opportunity".equals(this.getRelationKey())) {
			opportunityService.makePersistent(opportunity);
		} else if ("TargetList".equals(this.getRelationKey())) {
			targetListService.makePersistent(targetList);
		} else if ("Call".equals(this.getRelationKey())) {
			callService.makePersistent(call);
		} else if ("Meeting".equals(this.getRelationKey())) {
			meetingService.makePersistent(meeting);
		}
		return SUCCESS;
	}	
	
    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */		
	public String unselect() throws ServiceException {
		Contact contact = null;
		Opportunity opportunity = null;
		TargetList targetList = null;
		Call call = null;
		Meeting meeting = null;
		Set<Lead> leads = null;
		if ("Contact".equals(this.getRelationKey())) {
			contact = contactService.getEntityById(Contact.class,
					Integer.valueOf(this.getRelationValue()));
			leads= contact.getLeads();
		}else if ("Opportunity".equals(this.getRelationKey())) {
			opportunity = opportunityService.getEntityById(Opportunity.class,
					Integer.valueOf(this.getRelationValue()));
			leads= opportunity.getLeads();				
		}else if ("TargetList".equals(this.getRelationKey())) {
			targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
			leads= targetList.getLeads();				
		}else if ("Call".equals(this.getRelationKey())) {
			call = callService.getEntityById(Call.class,
					Integer.valueOf(this.getRelationValue()));
		}else if ("Meeting".equals(this.getRelationKey())) {
			meeting = meetingService.getEntityById(Meeting.class,
					Integer.valueOf(this.getRelationValue()));
			leads= meeting.getLeads();				
		}
		
		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			Collection<Lead> selectedLeads = new ArrayList<Lead>(); 
			for (int i = 0; i < ids.length; i++) {
				Integer selectId = Integer.valueOf(ids[i]);
				A:for (Lead lead : leads){
					if (lead.getId().intValue() == selectId.intValue()){
						selectedLeads.add(lead);
				       break A;
					}
				}				
			}
			leads.removeAll(selectedLeads);
		}
		if ("Contact".equals(super.getRelationKey())) {
			contactService.makePersistent(contact);
		}else if ("Opportunity".equals(this.getRelationKey())) {
			opportunityService.makePersistent(opportunity);
		}else if ("TargetList".equals(this.getRelationKey())) {
			targetListService.makePersistent(targetList);
		}else if ("Call".equals(this.getRelationKey())) {
			callService.makePersistent(call);
		}else if ("Meeting".equals(this.getRelationKey())) {
			meetingService.makePersistent(meeting);
		}
		return SUCCESS;
	}		
	
    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */	
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Lead.class, this.getSeleteIDs());
		return SUCCESS;
	}

    /**
     * Removes the related entities
     * 
     * @return the SUCCESS result
     */	
	public String remove() throws ServiceException {
		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String removeId = ids[i];
				lead = baseService.getEntityById(Lead.class,
						Integer.valueOf(removeId));
				if ("Account".endsWith(super.getRemoveKey())) {
					lead.setAccount(null);
				}
				this.baseService.makePersistent(lead);
			}
		}
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
				Lead oriRecord = baseService.getEntityById(Lead.class, Integer.valueOf(copyid));
				Lead targetRecord = oriRecord.clone();
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
					"Email", "Description", "Status", "Status Description",
					"Lead Source", "Lead Source Description",
					"Opportunity Amount", "Referred By", "Campaign",
					"Do Not Call", "Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Lead lead = baseService.getEntityById(Lead.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], lead.getId());
				data1.put(header[1],
						CommonUtil.fromNullToEmpty(lead.getFirst_name()));
				data1.put(header[2],
						CommonUtil.fromNullToEmpty(lead.getLast_name()));
				data1.put(header[3],
						CommonUtil.fromNullToEmpty(lead.getOffice_phone()));
				data1.put(header[4],
						CommonUtil.fromNullToEmpty(lead.getTitle()));
				data1.put(header[5],
						CommonUtil.fromNullToEmpty(lead.getMobile()));
				data1.put(header[6],
						CommonUtil.fromNullToEmpty(lead.getDepartment()));
				data1.put(header[7],
						CommonUtil.fromNullToEmpty(lead.getFax()));
				if (lead.getAccount() != null) {
					data1.put(header[8], lead.getAccount().getId());
				} else {
					data1.put(header[8], "");
				}
				data1.put(header[9], CommonUtil.fromNullToEmpty(lead
						.getPrimary_address()));
				data1.put(header[10],
						CommonUtil.fromNullToEmpty(lead.getPrimary_city()));
				data1.put(header[11],
						CommonUtil.fromNullToEmpty(lead.getPrimary_state()));
				data1.put(header[12], CommonUtil.fromNullToEmpty(lead
						.getPrimary_postal_code()));
				data1.put(header[13], CommonUtil.fromNullToEmpty(lead
						.getPrimary_country()));
				data1.put(header[14],
						CommonUtil.fromNullToEmpty(lead.getOther_address()));
				data1.put(header[15],
						CommonUtil.fromNullToEmpty(lead.getOther_city()));
				data1.put(header[16],
						CommonUtil.fromNullToEmpty(lead.getOther_state()));
				data1.put(header[17], CommonUtil.fromNullToEmpty(lead
						.getOther_postal_code()));
				data1.put(header[18],
						CommonUtil.fromNullToEmpty(lead.getOther_country()));
				data1.put(header[19],
						CommonUtil.fromNullToEmpty(lead.getEmail()));
				data1.put(header[20],
						CommonUtil.fromNullToEmpty(lead.getDescription()));
				if (lead.getStatus() != null) {
					data1.put(header[21], lead.getStatus().getId());
				} else {
					data1.put(header[21], "");
				}
				data1.put(header[22], CommonUtil.fromNullToEmpty(lead.getStatus_description()));
				if (lead.getLead_source() != null) {
					data1.put(header[23], lead.getLead_source().getId());
				} else {
					data1.put(header[23], "");
				}
				data1.put(header[24], CommonUtil.fromNullToEmpty(lead.getLead_source_description()));	
				data1.put(header[25], CommonUtil.fromNullToEmpty(lead.getOpportunity_amount()));
				data1.put(header[26], CommonUtil.fromNullToEmpty(lead.getReferred_by()));
				if (lead.getCampaign() != null) {
					data1.put(header[27], lead.getCampaign().getId());
				} else {
					data1.put(header[27], "");
				}
				data1.put(header[28],lead.isNot_call());
				if (lead.getAssigned_to() != null) {
					data1.put(header[29], lead.getAssigned_to().getId());
				} else {
					data1.put(header[29], "");
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

				Lead lead = new Lead();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						lead.setId(Integer.parseInt(id));
					}
					lead.setFirst_name(CommonUtil.fromNullToEmpty(row
							.get("First Name")));
					lead.setLast_name(CommonUtil.fromNullToEmpty(row
							.get("Last Name")));
					lead.setOffice_phone(CommonUtil.fromNullToEmpty(row
							.get("Office Phone")));
					lead.setTitle(CommonUtil.fromNullToEmpty(row
							.get("Title")));
					lead.setMobile(CommonUtil.fromNullToEmpty(row
							.get("Mobile")));
					lead.setDepartment(CommonUtil.fromNullToEmpty(row
							.get("Department")));
					lead.setFax(CommonUtil.fromNullToEmpty(row.get("Fax")));
					String accountID = row.get("Account");
					if (CommonUtil.isNullOrEmpty(accountID)) {
						lead.setAccount(null);
					} else {
						Account account = accountService.getEntityById(
								Account.class, Integer.parseInt(accountID));
						lead.setAccount(account);
					}
					lead.setPrimary_address(CommonUtil.fromNullToEmpty(row
							.get("Primary Address")));
					lead.setPrimary_city(CommonUtil.fromNullToEmpty(row
							.get("Primary City")));
					lead.setPrimary_state(CommonUtil.fromNullToEmpty(row
							.get("Primary State")));
					lead.setPrimary_postal_code(CommonUtil
							.fromNullToEmpty(row.get("Primary Postal Code")));
					lead.setPrimary_country(CommonUtil.fromNullToEmpty(row
							.get("Primary Country")));
					lead.setOther_address(CommonUtil.fromNullToEmpty(row
							.get("Other Address")));
					lead.setOther_city(CommonUtil.fromNullToEmpty(row
							.get("Other City")));
					lead.setOther_state(CommonUtil.fromNullToEmpty(row
							.get("Other State")));
					lead.setOther_postal_code(CommonUtil.fromNullToEmpty(row
							.get("Other Postal Code")));
					lead.setOther_country(CommonUtil.fromNullToEmpty(row
							.get("Other Country")));
					lead.setEmail(CommonUtil.fromNullToEmpty(row
							.get("Email")));
					lead.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					String statusID = row.get("Status");
					if (CommonUtil.isNullOrEmpty(statusID)) {
						lead.setStatus(null);
					} else {
						LeadStatus leadStatus = leadStatusService.getEntityById(
								LeadStatus.class, Integer.parseInt(statusID));
						lead.setStatus(leadStatus);
					}
					lead.setStatus_description(CommonUtil.fromNullToEmpty(row
							.get("Status Description")));
					String leadSourceID = row.get("Lead Source");
					if (CommonUtil.isNullOrEmpty(leadSourceID)) {
						lead.setLead_source(null);
					} else {
						LeadSource leadSource = leadSourceService.getEntityById(
								LeadSource.class, Integer.parseInt(leadSourceID));
						lead.setLead_source(leadSource);
					}
					lead.setLead_source_description(CommonUtil.fromNullToEmpty(row
							.get("Lead Source Description")));					
					lead.setOpportunity_amount(CommonUtil.fromNullToEmpty(row
							.get("Opportunity Amount")));					
					lead.setReferred_by(CommonUtil.fromNullToEmpty(row
							.get("Referred By")));	
					String campaignID = row.get("Campaign");
					if (CommonUtil.isNullOrEmpty(campaignID)) {
						lead.setCampaign(null);
					} else {
						Campaign campaign = campaignService.getEntityById(
								Campaign.class, Integer.parseInt(campaignID));
						lead.setCampaign(campaign);
					}					
					String doNotCall = row.get("Do Not Call");
					if (CommonUtil.isNullOrEmpty(doNotCall)) {
						lead.setNot_call(false);
					} else {
						lead.setNot_call(Boolean.parseBoolean(doNotCall));
					}
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						lead.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						lead.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(lead);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					String firstName = CommonUtil.fromNullToEmpty(lead
							.getFirst_name());
					String lastName = CommonUtil.fromNullToEmpty(lead
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

	public IBaseService<Lead> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Lead> baseService) {
		this.baseService = baseService;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
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
	 * @return the leadStatusService
	 */
	public IBaseService<LeadStatus> getLeadStatusService() {
		return leadStatusService;
	}

	/**
	 * @param leadStatusService
	 *            the leadStatusService to set
	 */
	public void setLeadStatusService(IBaseService<LeadStatus> leadStatusService) {
		this.leadStatusService = leadStatusService;
	}

	/**
	 * @return the leadSourceService
	 */
	public IBaseService<LeadSource> getLeadSourceService() {
		return leadSourceService;
	}

	/**
	 * @param leadSourceService
	 *            the leadSourceService to set
	 */
	public void setLeadSourceService(IBaseService<LeadSource> leadSourceService) {
		this.leadSourceService = leadSourceService;
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
	 * @return the campaignService
	 */
	public IBaseService<Campaign> getCampaignService() {
		return campaignService;
	}

	/**
	 * @param campaignService
	 *            the campaignService to set
	 */
	public void setCampaignService(IBaseService<Campaign> campaignService) {
		this.campaignService = campaignService;
	}

	/**
	 * @return the contactService
	 */
	public IBaseService<Contact> getContactService() {
		return contactService;
	}

	/**
	 * @param contactService the contactService to set
	 */
	public void setContactService(IBaseService<Contact> contactService) {
		this.contactService = contactService;
	}

	/**
	 * @return the opportunityService
	 */
	public IBaseService<Opportunity> getOpportunityService() {
		return opportunityService;
	}

	/**
	 * @param opportunityService the opportunityService to set
	 */
	public void setOpportunityService(IBaseService<Opportunity> opportunityService) {
		this.opportunityService = opportunityService;
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

	/**
	 * @return the callService
	 */
	public IBaseService<Call> getCallService() {
		return callService;
	}

	/**
	 * @param callService the callService to set
	 */
	public void setCallService(IBaseService<Call> callService) {
		this.callService = callService;
	}

	/**
	 * @return the meetingService
	 */
	public IBaseService<Meeting> getMeetingService() {
		return meetingService;
	}

	/**
	 * @param meetingService the meetingService to set
	 */
	public void setMeetingService(IBaseService<Meeting> meetingService) {
		this.meetingService = meetingService;
	}

}
