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
import com.gcrm.domain.Case;
import com.gcrm.domain.CaseOrigin;
import com.gcrm.domain.CasePriority;
import com.gcrm.domain.CaseReason;
import com.gcrm.domain.CaseStatus;
import com.gcrm.domain.CaseType;
import com.gcrm.domain.Document;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Case
 * 
 */
public class EditCaseAction extends BaseEditAction implements Preparable{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Case> baseService;
	private IBaseService<CaseStatus> caseStatusService;
	private IBaseService<CasePriority> casePriorityService;
	private IBaseService<CaseType> caseTypeService;	
	private IBaseService<CaseOrigin> caseOriginService;
	private IBaseService<CaseReason> caseReasonService;	
	private IBaseService<Account> accountService;	
	private IBaseService<Document> documentService;		
	private IBaseService<User> userService;
	private Case caseInstance;
	private List<CaseStatus> statuses;
	private List<CasePriority> casePriorities;
	private List<CaseType> caseTypes;	
	private List<CaseOrigin> caseOrigins;	
	private List<CaseReason> caseReasons;		
	private Integer statusID = null;
	private Integer priorityID = null;
	private Integer typeID = null;
	private Integer originID = null;
	private Integer reasonID = null;
	private Integer accountID = null;	
	private Integer assignedToID = null;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */	
	public String save() throws Exception {
		CaseStatus status = null;
		if (statusID != null) {
			status = caseStatusService.getEntityById(CaseStatus.class,
					statusID);
		}
		caseInstance.setStatus(status);
		CasePriority priority = null;
		if (priorityID != null){		
			priority = casePriorityService.getEntityById(
					CasePriority.class, priorityID);
		}
		caseInstance.setPriority(priority);
		CaseType type = null;
		if (typeID != null){		
			type = caseTypeService.getEntityById(
					CaseType.class, typeID);
		}
		caseInstance.setType(type);	
		CaseOrigin origin = null;
		if (originID != null){		
			origin = caseOriginService.getEntityById(
					CaseOrigin.class, originID);
		}
		caseInstance.setOrigin(origin);	
		CaseReason reason = null;
		if (reasonID != null){		
			reason = caseReasonService.getEntityById(
					CaseReason.class, reasonID);
		}
		caseInstance.setReason(reason);			
		Account account = null;
		if (accountID != null){		
			account = accountService.getEntityById(
					Account.class, accountID);
		}
		caseInstance.setAccount(account);			
		
		User assignedTo = null;
		if (assignedToID != null){
			assignedTo = userService.getEntityById(User.class, assignedToID);
		}
		caseInstance.setAssigned_to(assignedTo);

		if ("Document".equals(this.getRelationKey())) {
			Document document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			Set<Document> documents = caseInstance.getDocuments();
			if (documents == null){
				documents =  new HashSet<Document>();
			}
			documents.add(document);
		}
		
        super.updateBaseInfo(caseInstance);		
		
		getBaseService().makePersistent(caseInstance);
		return SUCCESS;
	}

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		if (this.getId() != null) {
			caseInstance = baseService.getEntityById(Case.class, this.getId());
			CaseStatus status = caseInstance.getStatus();
			if (status != null) {
				statusID = status.getId();
			}
			CasePriority priority = caseInstance.getPriority();
			if (priority != null) {
				priorityID = priority.getId();
			}
			CaseType type = caseInstance.getType();
			if (type != null) {
				typeID = type.getId();
			}
			CaseOrigin origin = caseInstance.getOrigin();
			if (origin != null) {
				originID = origin.getId();
			}	
			CaseReason reason = caseInstance.getReason();
			if (reason != null) {
				reasonID = reason.getId();
			}				
			Account account = caseInstance.getAccount();
			if (account != null) {
				accountID = account.getId();
			}			
			User assignedTo = caseInstance.getAssigned_to();
			if (assignedTo != null) {
				assignedToID = assignedTo.getId();
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
		this.statuses = caseStatusService.getAllObjects(CaseStatus.class
				.getSimpleName());
		this.casePriorities = casePriorityService.getAllObjects(CasePriority.class
				.getSimpleName());	
		this.caseTypes = caseTypeService.getAllObjects(CaseType.class
				.getSimpleName());	
		this.caseOrigins = caseOriginService.getAllObjects(CaseOrigin.class
				.getSimpleName());		
		this.caseReasons = caseReasonService.getAllObjects(CaseReason.class
				.getSimpleName());			
	}


	/**
	 * @return the baseService
	 */
	public IBaseService<Case> getBaseService() {
		return baseService;
	}


	/**
	 * @param baseService the baseService to set
	 */
	public void setBaseService(IBaseService<Case> baseService) {
		this.baseService = baseService;
	}


	/**
	 * @return the caseStatusService
	 */
	public IBaseService<CaseStatus> getCaseStatusService() {
		return caseStatusService;
	}


	/**
	 * @param caseStatusService the caseStatusService to set
	 */
	public void setCaseStatusService(IBaseService<CaseStatus> caseStatusService) {
		this.caseStatusService = caseStatusService;
	}


	/**
	 * @return the casePriorityService
	 */
	public IBaseService<CasePriority> getCasePriorityService() {
		return casePriorityService;
	}


	/**
	 * @param casePriorityService the casePriorityService to set
	 */
	public void setCasePriorityService(
			IBaseService<CasePriority> casePriorityService) {
		this.casePriorityService = casePriorityService;
	}


	/**
	 * @return the caseTypeService
	 */
	public IBaseService<CaseType> getCaseTypeService() {
		return caseTypeService;
	}


	/**
	 * @param caseTypeService the caseTypeService to set
	 */
	public void setCaseTypeService(IBaseService<CaseType> caseTypeService) {
		this.caseTypeService = caseTypeService;
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
	 * @return the caseInstance
	 */
	public Case getCaseInstance() {
		return caseInstance;
	}


	/**
	 * @param caseInstance the caseInstance to set
	 */
	public void setCaseInstance(Case caseInstance) {
		this.caseInstance = caseInstance;
	}

	/**
	 * @return the statuses
	 */
	public List<CaseStatus> getStatuses() {
		return statuses;
	}


	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuses(List<CaseStatus> statuses) {
		this.statuses = statuses;
	}


	/**
	 * @return the casePriorities
	 */
	public List<CasePriority> getCasePriorities() {
		return casePriorities;
	}


	/**
	 * @param casePriorities the casePriorities to set
	 */
	public void setCasePriorities(List<CasePriority> casePriorities) {
		this.casePriorities = casePriorities;
	}


	/**
	 * @return the caseTypes
	 */
	public List<CaseType> getCaseTypes() {
		return caseTypes;
	}


	/**
	 * @param caseTypes the caseTypes to set
	 */
	public void setCaseTypes(List<CaseType> caseTypes) {
		this.caseTypes = caseTypes;
	}


	/**
	 * @return the statusID
	 */
	public Integer getStatusID() {
		return statusID;
	}


	/**
	 * @param statusID the statusID to set
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
	 * @param priorityID the priorityID to set
	 */
	public void setPriorityID(Integer priorityID) {
		this.priorityID = priorityID;
	}


	/**
	 * @return the typeID
	 */
	public Integer getTypeID() {
		return typeID;
	}


	/**
	 * @param typeID the typeID to set
	 */
	public void setTypeID(Integer typeID) {
		this.typeID = typeID;
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

	/**
	 * @return the caseOriginService
	 */
	public IBaseService<CaseOrigin> getCaseOriginService() {
		return caseOriginService;
	}

	/**
	 * @param caseOriginService the caseOriginService to set
	 */
	public void setCaseOriginService(IBaseService<CaseOrigin> caseOriginService) {
		this.caseOriginService = caseOriginService;
	}

	/**
	 * @return the caseReasonService
	 */
	public IBaseService<CaseReason> getCaseReasonService() {
		return caseReasonService;
	}

	/**
	 * @param caseReasonService the caseReasonService to set
	 */
	public void setCaseReasonService(IBaseService<CaseReason> caseReasonService) {
		this.caseReasonService = caseReasonService;
	}

	/**
	 * @return the caseOrigins
	 */
	public List<CaseOrigin> getCaseOrigins() {
		return caseOrigins;
	}

	/**
	 * @param caseOrigins the caseOrigins to set
	 */
	public void setCaseOrigins(List<CaseOrigin> caseOrigins) {
		this.caseOrigins = caseOrigins;
	}

	/**
	 * @return the caseReasons
	 */
	public List<CaseReason> getCaseReasons() {
		return caseReasons;
	}

	/**
	 * @param caseReasons the caseReasons to set
	 */
	public void setCaseReasons(List<CaseReason> caseReasons) {
		this.caseReasons = caseReasons;
	}

	/**
	 * @return the originID
	 */
	public Integer getOriginID() {
		return originID;
	}

	/**
	 * @param originID the originID to set
	 */
	public void setOriginID(Integer originID) {
		this.originID = originID;
	}

	/**
	 * @return the reasonID
	 */
	public Integer getReasonID() {
		return reasonID;
	}

	/**
	 * @param reasonID the reasonID to set
	 */
	public void setReasonID(Integer reasonID) {
		this.reasonID = reasonID;
	}
	
}
