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
import com.gcrm.domain.Document;
import com.gcrm.domain.Lead;
import com.gcrm.domain.LeadSource;
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
 * Lists Contact
 * 
 */
public class ListContactAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Contact> baseService;
	private IBaseService<Account> accountService;
	private IBaseService<LeadSource> leadSourceService;
	private IBaseService<User> userService;
	private IBaseService<Campaign> campaignService;
	private IBaseService<Opportunity> opportunityService;
	private IBaseService<TargetList> targetListService;	
	private IBaseService<Call> callService;	
	private IBaseService<Meeting> meetingService;
	private IBaseService<Document> documentService;
	private Contact contact;

	private static final String CLAZZ = Contact.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */	
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Contact> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);

		Iterator<Contact> contacts = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(contacts, totalRecords);
		return null;
	}

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */	
	public static void getListJson(Iterator<Contact> contacts,
			long totalRecords) throws Exception {

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";

		String userName = null;
		String accountName = null;
		while (contacts.hasNext()) {
			Contact instance = contacts.next();
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
			if (contacts.hasNext()) {
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
		Opportunity opportunity = null;
		TargetList targetList = null;
		Call call = null;
		Meeting meeting = null;
		Document document = null;
		Set<Contact> contacts = null;
		
		if ("Opportunity".equals(this.getRelationKey())) {
			opportunity = opportunityService.getEntityById(Opportunity.class,
					Integer.valueOf(this.getRelationValue()));
			contacts = opportunity.getContacts();
		} else if ("TargetList".equals(this.getRelationKey())) {
			targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
			contacts= targetList.getContacts();		
		} else if ("Call".equals(this.getRelationKey())) {
			call = callService.getEntityById(Call.class,
					Integer.valueOf(this.getRelationValue()));
			contacts= call.getContacts();		
		} else if ("Meeting".equals(this.getRelationKey())) {
			meeting = meetingService.getEntityById(Meeting.class,
					Integer.valueOf(this.getRelationValue()));
			contacts= meeting.getContacts();		
		} else if ("Document".equals(this.getRelationKey())) {
			document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			contacts= document.getContacts();		
		}

		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String selectId = ids[i];
				contact = baseService.getEntityById(Contact.class,
						Integer.valueOf(selectId));
				contacts.add(contact);
			}
		}
		if ("Opportunity".equals(super.getRelationKey())) {
			opportunityService.makePersistent(opportunity);
		} else if ("TargetList".equals(this.getRelationKey())) {
			targetListService.makePersistent(targetList);
		} else if ("Call".equals(this.getRelationKey())) {
			callService.makePersistent(call);
		} else if ("Meeting".equals(this.getRelationKey())) {
			meetingService.makePersistent(meeting);
		}  else if ("Document".equals(this.getRelationKey())) {
			documentService.makePersistent(document);
		}
		return SUCCESS;
	}

    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */		
	public String unselect() throws ServiceException {
		Opportunity opportunity = null;
		TargetList targetList = null;
		Call call = null;
		Meeting meeting = null;
		Document document = null;
		Set<Contact> contacts = null;
		if ("Opportunity".equals(this.getRelationKey())) {
			opportunity = opportunityService.getEntityById(Opportunity.class,
					Integer.valueOf(this.getRelationValue()));
			contacts = opportunity.getContacts();
		}else if ("TargetList".equals(this.getRelationKey())) {
			targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
		}else if ("Call".equals(this.getRelationKey())) {
			call = callService.getEntityById(Call.class,
					Integer.valueOf(this.getRelationValue()));
			contacts= call.getContacts();				
		}else if ("Meeting".equals(this.getRelationKey())) {
			meeting = meetingService.getEntityById(Meeting.class,
					Integer.valueOf(this.getRelationValue()));
			contacts= meeting.getContacts();				
		} else if ("Document".equals(this.getRelationKey())) {
			document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			contacts= document.getContacts();		
		}

		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			Collection<Contact> selectedContacts = new ArrayList<Contact>();
			for (int i = 0; i < ids.length; i++) {
				Integer removeId = Integer.valueOf(ids[i]);
				A: for (Contact contact : contacts) {
					if (contact.getId().intValue() == removeId
							.intValue()) {
						selectedContacts.add(contact);
						break A;
					}
				}
			}
			contacts.removeAll(selectedContacts);
		}
		if ("Opportunity".equals(super.getRelationKey())) {
			opportunityService.makePersistent(opportunity);
		}else if ("TargetList".equals(this.getRelationKey())) {
			targetListService.makePersistent(targetList);
		}else if ("Call".equals(this.getRelationKey())) {
			callService.makePersistent(call);
		}else if ("Meeting".equals(this.getRelationKey())) {
			meetingService.makePersistent(meeting);
		}  else if ("Document".equals(this.getRelationKey())) {
			documentService.makePersistent(document);
		}
		return SUCCESS;
	}	
	
    /**
     * Gets the related documents
     * 
     * @return null
     */		
	public String filterContactDocument() throws Exception {
		contact = baseService.getEntityById(Contact.class, id);
		Set<Document> documents = contact.getDocuments();
		Iterator<Document> documentIterator = documents.iterator();
		long totalRecords = documents.size();
		ListDocumentAction.getListJson(documentIterator, totalRecords);
		return null;
	}	
	
    /**
     * Gets the related opportunities
     * 
     * @return null
     */		
	public String filterContactOpportunity() throws Exception {
		contact = baseService.getEntityById(Contact.class, id);
		Set<Opportunity> opportunities = contact.getOpportunities();
		Iterator<Opportunity> opportunityIterator = opportunities.iterator();
		long totalRecords = opportunities.size();
		ListOpportunityAction.getListJson(opportunityIterator, totalRecords);
		return null;
	}

    /**
     * Gets the related leads
     * 
     * @return null
     */		
	public String filterContactLead() throws Exception {
		contact = baseService.getEntityById(Contact.class, id);
		Set<Lead> leads = contact.getLeads();
		Iterator<Lead> leadIterator = leads.iterator();
		long totalRecords = leads.size();
		ListLeadAction.getListJson(leadIterator, totalRecords);
		return null;
	}

    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */	
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Contact.class, this.getSeleteIDs());
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
				contact = baseService.getEntityById(Contact.class,
						Integer.valueOf(removeId));
				if ("Account".endsWith(super.getRemoveKey())) {
					contact.setAccount(null);
				}
				this.baseService.makePersistent(contact);
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
				Contact oriRecord = baseService.getEntityById(Contact.class,
						Integer.valueOf(copyid));
				Contact targetRecord = oriRecord.clone();
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
					"Department", "Fax", "Account", "Web Site",
					"Primary Address", "Primary City", "Primary State",
					"Primary Postal Code", "Primary Country", "Other Address",
					"Other City", "Other State", "Other Postal Code",
					"Other Country", "Email", "Description", "Report To",
					"Sync Outlook", "Do Not Call", "Lead Source", "Campaign",
					"Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Contact contact = baseService.getEntityById(Contact.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], contact.getId());
				data1.put(header[1],
						CommonUtil.fromNullToEmpty(contact.getFirst_name()));
				data1.put(header[2],
						CommonUtil.fromNullToEmpty(contact.getLast_name()));
				data1.put(header[3],
						CommonUtil.fromNullToEmpty(contact.getOffice_phone()));
				data1.put(header[4],
						CommonUtil.fromNullToEmpty(contact.getTitle()));
				data1.put(header[5],
						CommonUtil.fromNullToEmpty(contact.getMobile()));
				data1.put(header[6],
						CommonUtil.fromNullToEmpty(contact.getDepartment()));
				data1.put(header[7],
						CommonUtil.fromNullToEmpty(contact.getFax()));
				if (contact.getAccount() != null) {
					data1.put(header[8], contact.getAccount().getId());
				} else {
					data1.put(header[8], "");
				}
				data1.put(header[9],
						CommonUtil.fromNullToEmpty(contact.getWebsite()));
				data1.put(header[10], CommonUtil.fromNullToEmpty(contact
						.getMailing_address()));
				data1.put(header[11],
						CommonUtil.fromNullToEmpty(contact.getMailing_city()));
				data1.put(header[12],
						CommonUtil.fromNullToEmpty(contact.getMailing_state()));
				data1.put(header[13], CommonUtil.fromNullToEmpty(contact
						.getMailing_postal_code()));
				data1.put(header[14], CommonUtil.fromNullToEmpty(contact
						.getMailing_country()));
				data1.put(header[15],
						CommonUtil.fromNullToEmpty(contact.getOther_address()));
				data1.put(header[16],
						CommonUtil.fromNullToEmpty(contact.getOther_city()));
				data1.put(header[17],
						CommonUtil.fromNullToEmpty(contact.getOther_state()));
				data1.put(header[18], CommonUtil.fromNullToEmpty(contact
						.getOther_postal_code()));
				data1.put(header[19],
						CommonUtil.fromNullToEmpty(contact.getOther_country()));
				data1.put(header[20],
						CommonUtil.fromNullToEmpty(contact.getEmail()));
				data1.put(header[21],
						CommonUtil.fromNullToEmpty(contact.getDescription()));
				if (contact.getReport_to() != null) {
					data1.put(header[22], contact.getReport_to().getId());
				} else {
					data1.put(header[22], "");
				}
				data1.put(header[23], contact.isSync_outlook());
				data1.put(header[24], contact.isNot_call());
				if (contact.getLeadSource() != null) {
					data1.put(header[25], contact.getLeadSource().getId());
				} else {
					data1.put(header[25], "");
				}
				if (contact.getCampaign() != null) {
					data1.put(header[26], contact.getCampaign().getId());
				} else {
					data1.put(header[26], "");
				}
				if (contact.getAssigned_to() != null) {
					data1.put(header[27], contact.getAssigned_to().getId());
				} else {
					data1.put(header[27], "");
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

				Contact contact = new Contact();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						contact.setId(Integer.parseInt(id));
					}
					contact.setFirst_name(CommonUtil.fromNullToEmpty(row
							.get("First Name")));
					contact.setLast_name(CommonUtil.fromNullToEmpty(row
							.get("Last Name")));
					contact.setOffice_phone(CommonUtil.fromNullToEmpty(row
							.get("Office Phone")));
					contact.setTitle(CommonUtil.fromNullToEmpty(row
							.get("Title")));
					contact.setMobile(CommonUtil.fromNullToEmpty(row
							.get("Mobile")));
					contact.setDepartment(CommonUtil.fromNullToEmpty(row
							.get("Department")));
					contact.setFax(CommonUtil.fromNullToEmpty(row.get("Fax")));
					String accountID = row.get("Account");
					if (CommonUtil.isNullOrEmpty(accountID)) {
						contact.setAccount(null);
					} else {
						Account account = accountService.getEntityById(
								Account.class, Integer.parseInt(accountID));
						contact.setAccount(account);
					}
					contact.setWebsite(CommonUtil.fromNullToEmpty(row
							.get("Web Site")));
					contact.setMailing_address(CommonUtil.fromNullToEmpty(row
							.get("Mailing Address")));
					contact.setMailing_city(CommonUtil.fromNullToEmpty(row
							.get("Mailing City")));
					contact.setMailing_state(CommonUtil.fromNullToEmpty(row
							.get("Mailing State")));
					contact.setMailing_postal_code(CommonUtil
							.fromNullToEmpty(row.get("Mailing Postal Code")));
					contact.setMailing_country(CommonUtil.fromNullToEmpty(row
							.get("Mailing Country")));
					contact.setOther_address(CommonUtil.fromNullToEmpty(row
							.get("Other Address")));
					contact.setOther_city(CommonUtil.fromNullToEmpty(row
							.get("Other City")));
					contact.setOther_state(CommonUtil.fromNullToEmpty(row
							.get("Other State")));
					contact.setOther_postal_code(CommonUtil.fromNullToEmpty(row
							.get("Other Postal Code")));
					contact.setOther_country(CommonUtil.fromNullToEmpty(row
							.get("Other Country")));
					contact.setEmail(CommonUtil.fromNullToEmpty(row
							.get("Email")));
					contact.setEmail(CommonUtil.fromNullToEmpty(row
							.get("Email")));
					contact.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					String reportToID = row.get("Report To");
					if (CommonUtil.isNullOrEmpty(reportToID)) {
						contact.setReport_to(null);
					} else {
						Contact reportTo = baseService.getEntityById(
								Contact.class, Integer.parseInt(reportToID));
						contact.setReport_to(reportTo);
					}
					String syncOutlook = row.get("Sync Outlook");
					if (CommonUtil.isNullOrEmpty(syncOutlook)) {
						contact.setSync_outlook(false);
					} else {
						contact.setSync_outlook(Boolean
								.parseBoolean(syncOutlook));
					}
					String doNotCall = row.get("Do Not Call");
					if (CommonUtil.isNullOrEmpty(doNotCall)) {
						contact.setNot_call(false);
					} else {
						contact.setNot_call(Boolean.parseBoolean(doNotCall));
					}

					String leadSourceID = row.get("Lead Source");
					if (CommonUtil.isNullOrEmpty(leadSourceID)) {
						contact.setLeadSource(null);
					} else {
						LeadSource leadSource = leadSourceService
								.getEntityById(LeadSource.class,
										Integer.parseInt(leadSourceID));
						contact.setLeadSource(leadSource);
					}
					String campaignID = row.get("Campaign");
					if (CommonUtil.isNullOrEmpty(campaignID)) {
						contact.setCampaign(null);
					} else {
						Campaign campaign = campaignService.getEntityById(
								Campaign.class, Integer.parseInt(campaignID));
						contact.setCampaign(campaign);
					}
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						contact.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						contact.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(contact);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					String firstName = CommonUtil.fromNullToEmpty(contact
							.getFirst_name());
					String lastName = CommonUtil.fromNullToEmpty(contact
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

	public IBaseService<Contact> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Contact> baseService) {
		this.baseService = baseService;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
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
