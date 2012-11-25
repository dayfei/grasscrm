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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.gcrm.domain.Account;
import com.gcrm.domain.Call;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Document;
import com.gcrm.domain.LeadSource;
import com.gcrm.domain.Meeting;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Contact
 * 
 */
public class EditContactAction extends BaseEditAction implements Preparable{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Contact> baseService;
	private IBaseService<Account> accountService;
	private IBaseService<LeadSource> leadSourceService;
	private IBaseService<User> userService;
	private IBaseService<Campaign> campaignService;
	private IBaseService<Opportunity> opportunityService;
	private IBaseService<TargetList> targetListService;		
	private IBaseService<Document> documentService;	
	private IBaseService<Call> callService;	
	private IBaseService<Meeting> meetingService;
	private Contact contact;
	private List<LeadSource> leadSources;
	private Integer accountID = null;
	private Integer reportToID = null;
	private Integer leadSourceID = null;
	private Integer campaignID = null;
	private Integer assignedToID = null;
			
    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */	
	public String save() throws Exception {
		Account account = null;
		if (accountID != null) {
			account = accountService.getEntityById(Account.class,
					accountID);
		}
		contact.setAccount(account);

		Contact reportTo = null;
		if (reportToID != null) {
			reportTo = baseService.getEntityById(Contact.class,
					reportToID);
		}
		contact.setReport_to(reportTo);
		
		LeadSource leadSource = null;
		if (leadSourceID != null){		
			leadSource = leadSourceService.getEntityById(
					LeadSource.class, leadSourceID);
		}
		contact.setLeadSource(leadSource);
		
		User user = null;
		if (assignedToID != null){
		 user = userService.getEntityById(User.class, assignedToID);
		}
		contact.setAssigned_to(user);
	
		Campaign campaign = null;
		if (campaignID != null){
			campaign = campaignService.getEntityById(Campaign.class, campaignID);
		}
		contact.setCampaign(campaign);
		
		if ("Opportunity".equals(this.getRelationKey())) {
			Opportunity opportunity = opportunityService.getEntityById(Opportunity.class,
					Integer.valueOf(this.getRelationValue()));
			Set<Opportunity> opportunities = contact.getOpportunities();
			if (opportunities == null){
				opportunities =  new HashSet<Opportunity>();
			}
			opportunities.add(opportunity);
		}	else if ("TargetList".equals(this.getRelationKey())) {
			TargetList targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
			Set<TargetList> targetLists = contact.getTargetLists();
			if (targetLists == null){
				targetLists =  new HashSet<TargetList>();
			}
			targetLists.add(targetList);
		}	else if ("Call".equals(this.getRelationKey())) {
			Call call = callService.getEntityById(Call.class,
					Integer.valueOf(this.getRelationValue()));
			Set<Call> calls = contact.getCalls();
			if (calls == null){
				calls =  new HashSet<Call>();
			}
			calls.add(call);
		}	else if ("Meeting".equals(this.getRelationKey())) {
			Meeting meeting = meetingService.getEntityById(Meeting.class,
					Integer.valueOf(this.getRelationValue()));
			Set<Meeting> meetings = contact.getMeetings();
			if (meetings == null){
				meetings =  new HashSet<Meeting>();
			}
			meetings.add(meeting);
		}	else if ("Document".equals(this.getRelationKey())) {
			Document document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			Set<Document> documents = contact.getDocuments();
			if (documents == null){
				documents =  new HashSet<Document>();
			}
			documents.add(document);
		}			
		
        super.updateBaseInfo(contact);
        
		getBaseService().makePersistent(contact);
		return SUCCESS;
	}

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		if (this.getId() != null) {
			contact = baseService.getEntityById(Contact.class, this.getId());
			Account account = contact.getAccount();
			if (account != null) {
				accountID = account.getId();
			}
			
			Contact report_to = contact.getReport_to();
			if (report_to != null) {
				reportToID = report_to.getId();
			}

			LeadSource leadSource = contact.getLeadSource();
			if (leadSource != null) {
				leadSourceID = leadSource.getId();
			}
		
			Campaign campaign = contact.getCampaign();
			if (campaign != null) {
				campaignID = campaign.getId();
			}		
			User user = contact.getAssigned_to();
			if (user != null) {
				assignedToID = user.getId();
			}				
			
		}else{
			ActionContext context = ActionContext.getContext();
			Map<String, Object> session = context.getSession();
			User loginUser = (User) session
					.get(AuthenticationSuccessListener.LOGIN_USER);
			this.assignedToID = loginUser.getId();
		}
		return SUCCESS;
	}
	
    /**
     * Prepares the list
     * 
     */	
	public void prepare() throws Exception {
		this.leadSources = leadSourceService.getAllObjects(LeadSource.class
				.getSimpleName());
	}


	/**
	 * @return the baseService
	 */
	public IBaseService<Contact> getBaseService() {
		return baseService;
	}


	/**
	 * @param baseService the baseService to set
	 */
	public void setBaseService(IBaseService<Contact> baseService) {
		this.baseService = baseService;
	}


	/**
	 * @return the accountService
	 */
	public IBaseService<Account> getAccountService() {
		return accountService;
	}


	/**
	 * @param accountService the accountService to set
	 */
	public void setAccountService(IBaseService<Account> accountService) {
		this.accountService = accountService;
	}


	/**
	 * @return the leadSourceService
	 */
	public IBaseService<LeadSource> getLeadSourceService() {
		return leadSourceService;
	}


	/**
	 * @param leadSourceService the leadSourceService to set
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
	 * @param userService the userService to set
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
	 * @param campaignService the campaignService to set
	 */
	public void setCampaignService(IBaseService<Campaign> campaignService) {
		this.campaignService = campaignService;
	}


	/**
	 * @return the contact
	 */
	public Contact getContact() {
		return contact;
	}


	/**
	 * @param contact the contact to set
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	/**
	 * @return the accountID
	 */
	public Integer getAccountID() {
		return accountID;
	}


	/**
	 * @param accountID the accountID to set
	 */
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}


	/**
	 * @return the leadSourceID
	 */
	public Integer getLeadSourceID() {
		return leadSourceID;
	}


	/**
	 * @param leadSourceID the leadSourceID to set
	 */
	public void setLeadSourceID(Integer leadSourceID) {
		this.leadSourceID = leadSourceID;
	}

	/**
	 * @return the campaignID
	 */
	public Integer getCampaignID() {
		return campaignID;
	}


	/**
	 * @param campaignID the campaignID to set
	 */
	public void setCampaignID(Integer campaignID) {
		this.campaignID = campaignID;
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
	 * @return the leadSource
	 */
	public List<LeadSource> getLeadSources() {
		return leadSources;
	}


	/**
	 * @param leadSource the leadSource to set
	 */
	public void setLeadSource(List<LeadSource> leadSources) {
		this.leadSources = leadSources;
	}


	/**
	 * @return the reportToID
	 */
	public Integer getReportToID() {
		return reportToID;
	}


	/**
	 * @param reportToID the reportToID to set
	 */
	public void setReportToID(Integer reportToID) {
		this.reportToID = reportToID;
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


	/**
	 * @return the documentService
	 */
	public IBaseService<Document> getDocumentService() {
		return documentService;
	}


	/**
	 * @param documentService the documentService to set
	 */
	public void setDocumentService(IBaseService<Document> documentService) {
		this.documentService = documentService;
	}



}
