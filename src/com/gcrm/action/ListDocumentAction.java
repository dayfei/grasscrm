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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.Account;
import com.gcrm.domain.Case;
import com.gcrm.domain.Contact;
import com.gcrm.domain.Document;
import com.gcrm.domain.DocumentCategory;
import com.gcrm.domain.DocumentStatus;
import com.gcrm.domain.DocumentSubCategory;
import com.gcrm.domain.Opportunity;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Document
 * 
 */
public class ListDocumentAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Document> baseService;
	private IBaseService<DocumentStatus> documentStatusService;
	private IBaseService<DocumentCategory> documentCategoryService;
	private IBaseService<DocumentSubCategory> documentSubCategoryService;
	private IBaseService<Account> accountService;
	private IBaseService<Contact> contactService;
	private IBaseService<Opportunity> opportunityService;
	private IBaseService<Case> caseService;
	private IBaseService<User> userService;
	private Document document;

	private static final String CLAZZ = Document.class.getSimpleName();

    /**
     * Gets the list data.
     * 
     * @return null
     */	
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Document> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		Iterator<Document> documents = result.getResult().iterator();
		long totalRecords = result.getTotalRecords();

		getListJson(documents, totalRecords);
		return null;
	}

    /**
     * Gets the list JSON data.
     * 
     * @return list JSON data
     */	
	public static void getListJson(Iterator<Document> documents,
			long totalRecords) throws Exception {

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";

		String userName = null;
		String publishDateS = null;
		String categoryName = null;
		while (documents.hasNext()) {
			Document instance = documents.next();
			int id = instance.getId();
			String name = instance.getName();
			Date publishDate = instance.getPublish_date();
			if (publishDate != null) {
				publishDateS = publishDate.toString();
			} else {
				publishDateS = "";
			}
			DocumentCategory category = instance.getCategory();
			if (category != null) {
				categoryName = category.toString();
			} else {
				categoryName = "";
			}
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
					+ "\",\"publishDateS\":\"" + publishDateS
					+ "\",\"categoryName\":\"" + categoryName
					+ "\",\"userName\":\"" + userName + 
					"\",\"created_by\":\"" + createdByName + 
					"\",\"updated_by\":\"" + updatedByName + 
					"\",\"created_on\":\"" + createdOnName + 
					"\",\"updated_on\":\"" + updatedOnName + 
					"\"}";
			if (documents.hasNext()) {
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
		Account account = null;
		Contact contact = null;
		Opportunity opportunity = null;
		Case caseInstance = null;
		Document document = null;
		Set<Document> documents = null;
		if ("Account".equals(this.getRelationKey())) {
			account = accountService.getEntityById(Account.class,
					Integer.valueOf(this.getRelationValue()));
			documents = account.getDocuments();
		} else if ("Contact".equals(this.getRelationKey())) {
			contact = contactService.getEntityById(Contact.class,
					Integer.valueOf(this.getRelationValue()));
			documents = contact.getDocuments();
		} else if ("Opportunity".equals(this.getRelationKey())) {
			opportunity = opportunityService.getEntityById(Opportunity.class,
					Integer.valueOf(this.getRelationValue()));
			documents = opportunity.getDocuments();
		} else if ("Case".equals(this.getRelationKey())) {
			caseInstance = caseService.getEntityById(Case.class,
					Integer.valueOf(this.getRelationValue()));
			documents = caseInstance.getDocuments();		
		}

		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String selectId = ids[i];
				document = baseService.getEntityById(Document.class,
						Integer.valueOf(selectId));
				documents.add(document);
			}
		}
		if ("Account".equals(super.getRelationKey())) {
			accountService.makePersistent(account);
		} else if ("Contact".equals(this.getRelationKey())) {
			contactService.makePersistent(contact);
		} else if ("Opportunity".equals(this.getRelationKey())) {
			opportunityService.makePersistent(opportunity);
		} else if ("Case".equals(this.getRelationKey())) {
			caseService.makePersistent(caseInstance);
		}
		return SUCCESS;
	}

    /**
     * Unselects the entities
     * 
     * @return the SUCCESS result
     */		
	public String unselect() throws ServiceException {
		Account account = null;
		Contact contact = null;
		Opportunity opportunity = null;
		Case caseInstance = null;
		Set<Document> documents = null;
		if ("Account".equals(this.getRelationKey())) {
			account = accountService.getEntityById(Account.class,
					Integer.valueOf(this.getRelationValue()));
			documents = account.getDocuments();
		} else if ("Contact".equals(this.getRelationKey())) {
			contact = contactService.getEntityById(Contact.class,
					Integer.valueOf(this.getRelationValue()));
			documents = contact.getDocuments();
		} else if ("Opportunity".equals(this.getRelationKey())) {
			opportunity = opportunityService.getEntityById(Opportunity.class,
					Integer.valueOf(this.getRelationValue()));
			documents = opportunity.getDocuments();
		} else if ("Case".equals(this.getRelationKey())) {
			caseInstance = caseService.getEntityById(Case.class,
					Integer.valueOf(this.getRelationValue()));
			documents = caseInstance.getDocuments();		
		}

		if (this.getSeleteIDs() != null) {
			String[] ids = seleteIDs.split(",");
			Collection<Document> selectedDocuments = new ArrayList<Document>();
			for (int i = 0; i < ids.length; i++) {
				Integer removeId = Integer.valueOf(ids[i]);
				A: for (Document document : documents) {
					if (document.getId().intValue() == removeId
							.intValue()) {
						selectedDocuments.add(document);
						break A;
					}
				}
			}
			documents.removeAll(selectedDocuments);
		}
		
		if ("Account".equals(super.getRelationKey())) {
			accountService.makePersistent(account);
		} else if ("Contact".equals(this.getRelationKey())) {
			contactService.makePersistent(contact);
		} else if ("Opportunity".equals(this.getRelationKey())) {
			opportunityService.makePersistent(opportunity);
		} else if ("Case".equals(this.getRelationKey())) {
			caseService.makePersistent(caseInstance);
		}
		return SUCCESS;
	}	
	
    /**
     * Gets the related accounts
     * 
     * @return null
     */		
	public String filterDocumentAccount() throws Exception {
		document = baseService.getEntityById(Document.class, id);
		Set<Account> accounts = document.getAccounts();
		Iterator<Account> accountIterator = accounts.iterator();
		long totalRecords = accounts.size();
		ListAccountAction.getListJson(accountIterator, totalRecords,false);
		return null;
	}
	
    /**
     * Gets the related contacts
     * 
     * @return null
     */		
	public String filterDocumentContact() throws Exception {
		document = baseService.getEntityById(Document.class, id);
		Set<Contact> contacts = document.getContacts();
		Iterator<Contact> contactIterator = contacts.iterator();
		long totalRecords = contacts.size();
		ListContactAction.getListJson(contactIterator, totalRecords);
		return null;
	}	
	
    /**
     * Gets the related opportunities
     * 
     * @return null
     */		
	public String filterDocumentOpportunity() throws Exception {
		document = baseService.getEntityById(Document.class, id);
		Set<Opportunity> opportunities = document.getOpportunities();
		Iterator<Opportunity> opportunityIterator = opportunities.iterator();
		long totalRecords = opportunities.size();
		ListOpportunityAction.getListJson(opportunityIterator, totalRecords);
		return null;
	}	
	
    /**
     * Gets the related cases
     * 
     * @return null
     */		
	public String filterDocumentCase() throws Exception {
		document = baseService.getEntityById(Document.class, id);
		Set<Case> cases = document.getCases();
		Iterator<Case> caseIterator = cases.iterator();
		long totalRecords = cases.size();
		ListCaseAction.getListJson(caseIterator, totalRecords);
		return null;
	}
	
    /**
     * Deletes the selected entities.
     * 
     * @return the SUCCESS result
     */	
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Document.class, this.getSeleteIDs());
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
				Document oriRecord = baseService.getEntityById(Document.class, Integer.valueOf(copyid));
				Document targetRecord = oriRecord.clone();
				targetRecord.setId(null);
				this.getbaseService().makePersistent(targetRecord);
			}
		}
		return SUCCESS;
	}
	
	public String execute() throws Exception {
		return SUCCESS;
	}

	public IBaseService<Document> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Document> baseService) {
		this.baseService = baseService;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
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
	 * @return the documentStatusService
	 */
	public IBaseService<DocumentStatus> getDocumentStatusService() {
		return documentStatusService;
	}

	/**
	 * @param documentStatusService
	 *            the documentStatusService to set
	 */
	public void setDocumentStatusService(
			IBaseService<DocumentStatus> documentStatusService) {
		this.documentStatusService = documentStatusService;
	}

	/**
	 * @return the documentCategoryService
	 */
	public IBaseService<DocumentCategory> getDocumentCategoryService() {
		return documentCategoryService;
	}

	/**
	 * @param documentCategoryService
	 *            the documentCategoryService to set
	 */
	public void setDocumentCategoryService(
			IBaseService<DocumentCategory> documentCategoryService) {
		this.documentCategoryService = documentCategoryService;
	}

	/**
	 * @return the documentSubCategoryService
	 */
	public IBaseService<DocumentSubCategory> getDocumentSubCategoryService() {
		return documentSubCategoryService;
	}

	/**
	 * @param documentSubCategoryService
	 *            the documentSubCategoryService to set
	 */
	public void setDocumentSubCategoryService(
			IBaseService<DocumentSubCategory> documentSubCategoryService) {
		this.documentSubCategoryService = documentSubCategoryService;
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
	 * @return the baseService
	 */
	public IBaseService<Document> getBaseService() {
		return baseService;
	}

	/**
	 * @param baseService the baseService to set
	 */
	public void setBaseService(IBaseService<Document> baseService) {
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
	 * @return the caseService
	 */
	public IBaseService<Case> getCaseService() {
		return caseService;
	}

	/**
	 * @param caseService the caseService to set
	 */
	public void setCaseService(IBaseService<Case> caseService) {
		this.caseService = caseService;
	}

}
