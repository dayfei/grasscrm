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
import com.gcrm.domain.Campaign;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Currency;
import com.gcrm.domain.Document;
import com.gcrm.domain.Lead;
import com.gcrm.domain.LeadSource;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.OpportunityType;
import com.gcrm.domain.SalesStage;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Opportunity
 * 
 */
public class ListOpportunityAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Opportunity> baseService;
	private IBaseService<Account> accountService;
	private IBaseService<Currency> currencyService;
	private IBaseService<OpportunityType> opportunityTypeService;
	private IBaseService<SalesStage> salesStageService;
	private IBaseService<LeadSource> leadSourceService;
	private IBaseService<Campaign> campaignService;
	private IBaseService<Contact> contactService;
	private IBaseService<User> userService;
	private IBaseService<Document> documentService;
	private Opportunity opportunity;

	private static final String CLAZZ = Opportunity.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */	
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Opportunity> result = baseService.getPaginationObjects(
				CLAZZ, searchCondition);
		Iterator<Opportunity> opportunitys = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();
		getListJson(opportunitys, totalRecords);
		return null;
	}

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */	
	public static void getListJson(Iterator<Opportunity> opportunitys,
			long totalRecords) throws Exception {

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";

		String userName = null;
		String accountName = null;
		String stageName = null;
		while (opportunitys.hasNext()) {
			Opportunity instance = opportunitys.next();
			int id = instance.getId();
			String name = CommonUtil.fromNullToEmpty(instance.getName());
			Account account = instance.getAccount();
			if (account != null) {
				accountName = CommonUtil.fromNullToEmpty(account.getName());
			} else {
				accountName = "";
			}
			SalesStage salesStage = instance.getSales_stage();
			if (salesStage != null) {
				stageName = CommonUtil.fromNullToEmpty(salesStage.getName());
			} else {
				stageName = "";
			}
			String amount = CommonUtil.fromNullToEmpty(instance
					.getOpportunity_amount());
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
					+ "\",\"accountName\":\"" + accountName
					+ "\",\"stageName\":\"" + stageName + "\",\"amount\":\""
					+ amount + "\",\"user_name\":\"" + userName + 
					"\",\"created_by\":\"" + createdByName + 
					"\",\"updated_by\":\"" + updatedByName + 
					"\",\"created_on\":\"" + createdOnName + 
					"\",\"updated_on\":\"" + updatedOnName + 
					"\"}";
			if (opportunitys.hasNext()) {
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
		Contact contact = null;
		Document document = null;
		Set<Opportunity> opportunities = null;
		if ("Contact".equals(this.getRelationKey())) {
			contact = contactService.getEntityById(Contact.class,
					Integer.valueOf(this.getRelationValue()));
			opportunities = contact.getOpportunities();
		} else if ("Document".equals(this.getRelationKey())) {
			document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			opportunities= document.getOpportunities();		
		}

		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String selectId = ids[i];
				opportunity = baseService.getEntityById(Opportunity.class,
						Integer.valueOf(selectId));
				opportunities.add(opportunity);
			}
		}
		if ("Contact".equals(super.getRelationKey())) {
			contactService.makePersistent(contact);
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
		Contact contact = null;
		Document document = null;
		Set<Opportunity> opportunities = null;
		if ("Contact".equals(this.getRelationKey())) {
			contact = contactService.getEntityById(Contact.class,
					Integer.valueOf(this.getRelationValue()));
			opportunities = contact.getOpportunities();
		} else if ("Document".equals(this.getRelationKey())) {
			document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			opportunities= document.getOpportunities();		
		}

		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			Collection<Opportunity> selectedOpportunities = new ArrayList<Opportunity>();
			for (int i = 0; i < ids.length; i++) {
				Integer removeId = Integer.valueOf(ids[i]);
				A: for (Opportunity opportunityInstance : opportunities) {
					if (opportunityInstance.getId().intValue() == removeId
							.intValue()) {
						selectedOpportunities.add(opportunityInstance);
						break A;
					}
				}
			}
			opportunities.removeAll(selectedOpportunities);
		}
		if ("Contact".equals(super.getRelationKey())) {
			contactService.makePersistent(contact);
		}  else if ("Document".equals(this.getRelationKey())) {
			documentService.makePersistent(document);
		}
		return SUCCESS;
	}
	
    /**
     * Gets the related contacts.
     * 
     * @return null
     */	
	public String filterOpportunityContact() throws Exception {
		opportunity = baseService.getEntityById(Opportunity.class, id);
		Set<Contact> contacts = opportunity.getContacts();
		Iterator<Contact> contactIterator = contacts.iterator();
		long totalRecords = contacts.size();
		ListContactAction.getListJson(contactIterator, totalRecords);
		return null;
	}
	
    /**
     * Gets the related leads.
     * 
     * @return null
     */	
	public String filterOpportunityLead() throws Exception {
		opportunity = baseService.getEntityById(Opportunity.class, id);
		Set<Lead> leads = opportunity.getLeads();
		Iterator<Lead> leadIterator = leads.iterator();
		long totalRecords = leads.size();
		ListLeadAction.getListJson(leadIterator, totalRecords);
		return null;
	}
	
    /**
     * Gets the related documents.
     * 
     * @return null
     */	
	public String filteOpportunityDocument() throws Exception {
		opportunity = baseService.getEntityById(Opportunity.class, id);
		Set<Document> documents = opportunity.getDocuments();
		Iterator<Document> documentIterator = documents.iterator();
		long totalRecords = documents.size();
		ListDocumentAction.getListJson(documentIterator, totalRecords);
		return null;
	}	
	
    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */		
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Opportunity.class, this.getSeleteIDs());
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
				opportunity = baseService.getEntityById(Opportunity.class,
						Integer.valueOf(removeId));
				if ("Account".endsWith(super.getRemoveKey())) {
					opportunity.setAccount(null);
				}
				this.baseService.makePersistent(opportunity);
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
				Opportunity oriRecord = baseService.getEntityById(
						Opportunity.class, Integer.valueOf(copyid));
				Opportunity targetRecord = oriRecord.clone();
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
			final String[] header = new String[] { "ID", "Name", "Account",
					"Currency", "Expect Close Date", "Opportunity Amount",
					"Type", "Sales Stage", "Lead Source", "Probability",
					"Campaign", "Next Step", "Description", "Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Opportunity opportunity = baseService.getEntityById(
						Opportunity.class, Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], opportunity.getId());
				data1.put(header[1],
						CommonUtil.fromNullToEmpty(opportunity.getName()));
				if (opportunity.getAccount() != null) {
					data1.put(header[2], opportunity.getAccount().getId());
				} else {
					data1.put(header[2], "");
				}
				if (opportunity.getCurrency() != null) {
					data1.put(header[3], opportunity.getCurrency().getId());
				} else {
					data1.put(header[3], "");
				}
				Date expectCloseDate = opportunity.getExpect_close_date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
				if (expectCloseDate != null) {
					data1.put(header[4], dateFormat.format(expectCloseDate));
				} else {
					data1.put(header[4], "");
				}
				data1.put(header[5], CommonUtil.fromNullToEmpty(opportunity
						.getOpportunity_amount()));
				if (opportunity.getType() != null) {
					data1.put(header[6], opportunity.getType().getId());
				} else {
					data1.put(header[6], "");
				}
				if (opportunity.getSales_stage() != null) {
					data1.put(header[7], opportunity.getSales_stage().getId());
				} else {
					data1.put(header[7], "");
				}
				if (opportunity.getLead_source() != null) {
					data1.put(header[8], opportunity.getLead_source().getId());
				} else {
					data1.put(header[8], "");
				}
				data1.put(header[9], opportunity.getProbability());

				if (opportunity.getCampaign() != null) {
					data1.put(header[10], opportunity.getCampaign().getId());
				} else {
					data1.put(header[10], "");
				}
				data1.put(header[11],
						CommonUtil.fromNullToEmpty(opportunity.getNext_step()));
				data1.put(header[12], CommonUtil.fromNullToEmpty(opportunity
						.getDescription()));
				if (opportunity.getAssigned_to() != null) {
					data1.put(header[13], opportunity.getAssigned_to().getId());
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

				Opportunity opportunity = new Opportunity();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						opportunity.setId(Integer.parseInt(id));
					}
					opportunity.setName(CommonUtil.fromNullToEmpty(row
							.get("Name")));
					String accountID = row.get("Account");
					if (CommonUtil.isNullOrEmpty(accountID)) {
						opportunity.setAccount(null);
					} else {
						Account account = accountService.getEntityById(
								Account.class, Integer.parseInt(accountID));
						opportunity.setAccount(account);
					}
					String currencyID = row.get("Currency");
					if (CommonUtil.isNullOrEmpty(currencyID)) {
						opportunity.setCurrency(null);
					} else {
						Currency currency = currencyService.getEntityById(
								Currency.class, Integer.parseInt(currencyID));
						opportunity.setCurrency(currency);
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"M/d/yyyy");
					String expectCloseDateS = row.get("currencyID");

					if (expectCloseDateS != null) {
						Date expectCloseDate = dateFormat
								.parse(expectCloseDateS);
						opportunity.setExpect_close_date(expectCloseDate);
					} else {
						opportunity.setExpect_close_date(null);
					}
					opportunity.setOpportunity_amount(CommonUtil
							.fromNullToEmpty(row.get("Opportunity Amount")));
					String typeID = row.get("Type");
					if (CommonUtil.isNullOrEmpty(typeID)) {
						opportunity.setType(null);
					} else {
						OpportunityType opportunityType = opportunityTypeService
								.getEntityById(OpportunityType.class,
										Integer.parseInt(typeID));
						opportunity.setType(opportunityType);
					}
					String salesStageID = row.get("Sales Stage");
					if (CommonUtil.isNullOrEmpty(salesStageID)) {
						opportunity.setSales_stage(null);
					} else {
						SalesStage salesStage = salesStageService
								.getEntityById(SalesStage.class,
										Integer.parseInt(salesStageID));
						opportunity.setSales_stage(salesStage);
					}
					String leadSourceID = row.get("Lead Source");
					if (CommonUtil.isNullOrEmpty(leadSourceID)) {
						opportunity.setLead_source(null);
					} else {
						LeadSource leadSource = leadSourceService
								.getEntityById(LeadSource.class,
										Integer.parseInt(leadSourceID));
						opportunity.setLead_source(leadSource);
					}
					String probability = row.get("Probability");
					if (CommonUtil.isNullOrEmpty(probability)) {
						opportunity.setProbability(0);
					} else {
						opportunity.setProbability(Double
								.parseDouble(probability));
					}
					String campaignID = row.get("Campaign");
					if (CommonUtil.isNullOrEmpty(campaignID)) {
						opportunity.setCampaign(null);
					} else {
						Campaign campaign = campaignService.getEntityById(
								Campaign.class, Integer.parseInt(campaignID));
						opportunity.setCampaign(campaign);
					}
					opportunity.setNext_step(CommonUtil.fromNullToEmpty(row
							.get("Next Step")));
					opportunity.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						opportunity.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						opportunity.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(opportunity);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					failedMsg.put(opportunity.getName(), e.getMessage());
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

	public IBaseService<Opportunity> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Opportunity> baseService) {
		this.baseService = baseService;
	}

	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
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
	 * @return the currencyService
	 */
	public IBaseService<Currency> getCurrencyService() {
		return currencyService;
	}

	/**
	 * @param currencyService
	 *            the currencyService to set
	 */
	public void setCurrencyService(IBaseService<Currency> currencyService) {
		this.currencyService = currencyService;
	}

	/**
	 * @return the opportunityTypeService
	 */
	public IBaseService<OpportunityType> getOpportunityTypeService() {
		return opportunityTypeService;
	}

	/**
	 * @param opportunityTypeService
	 *            the opportunityTypeService to set
	 */
	public void setOpportunityTypeService(
			IBaseService<OpportunityType> opportunityTypeService) {
		this.opportunityTypeService = opportunityTypeService;
	}

	/**
	 * @return the salesStageService
	 */
	public IBaseService<SalesStage> getSalesStageService() {
		return salesStageService;
	}

	/**
	 * @param salesStageService
	 *            the salesStageService to set
	 */
	public void setSalesStageService(IBaseService<SalesStage> salesStageService) {
		this.salesStageService = salesStageService;
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
