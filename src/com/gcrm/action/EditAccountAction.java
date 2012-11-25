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
import com.gcrm.domain.AccountType;
import com.gcrm.domain.Campaign;
import com.gcrm.domain.Document;
import com.gcrm.domain.Industry;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.service.IBaseService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Account
 * 
 */
public class EditAccountAction extends BaseEditAction implements Preparable{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Account> baseService;
	private IBaseService<AccountType> accountTypeService;
	private IBaseService<Industry> industryService;
	private IBaseService<User> userService;
	private IBaseService<Campaign> campaignService;
	private IBaseService<TargetList> targetListService;	
	private IBaseService<Document> documentService;	
	private Account account;
	private List<AccountType> types;
	private List<Industry> industries;
	private Integer typeID = null;
	private Integer industryID = null;
	private Integer assignedToID = null;
	private Integer campaignID = null;
	private Integer managerID = null;
			
    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */	
	public String save() throws Exception {
		AccountType type = null;
		if (typeID != null) {
			type = accountTypeService.getEntityById(AccountType.class,
					typeID);
		}
		account.setAccount_type(type);
		
		Industry industry = null;
		if (industryID != null){		
			industry = industryService.getEntityById(
				Industry.class, industryID);
		}
		account.setIndustry(industry);
		
		User assignedTo = null;
		if (assignedToID != null){
			assignedTo = userService.getEntityById(User.class, assignedToID);
		}
		account.setAssigned_to(assignedTo);
	
		Account manager = null;
		if (managerID != null){
			manager = baseService.getEntityById(Account.class, managerID);
		}
		account.setManager(manager);
		
		if ("TargetList".equals(this.getRelationKey())) {
			TargetList targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
			Set<TargetList> targetLists = account.getTargetLists();
			if (targetLists == null){
				targetLists =  new HashSet<TargetList>();
			}
			targetLists.add(targetList);
		}	else if ("Document".equals(this.getRelationKey())) {
			Document document = documentService.getEntityById(Document.class,
					Integer.valueOf(this.getRelationValue()));
			Set<Document> documents = account.getDocuments();
			if (documents == null){
				documents =  new HashSet<Document>();
			}
			documents.add(document);
		}				
		
        super.updateBaseInfo(account);
		
		getBaseService().makePersistent(account);
		return SUCCESS;
	}

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		if (this.getId() != null) {
			account = baseService.getEntityById(Account.class, this.getId());
			AccountType type = account.getAccount_type();
			if (type != null) {
				typeID = type.getId();
			}
			Industry industry = account.getIndustry();
			if (industry != null) {
				industryID = industry.getId();
			}

			User assignedTo = account.getAssigned_to();
			if (assignedTo != null) {
				assignedToID = assignedTo.getId();
			}
		
			Account manager = account.getManager();
			if (manager != null) {
				managerID = manager.getId();
			}				
			this.getBaseInfo(account);
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
		this.types = accountTypeService.getAllObjects(AccountType.class
				.getSimpleName());
		this.industries = industryService.getAllObjects(Industry.class
				.getSimpleName());		
	}

	/**
	 * @return the baseService
	 */
	public IBaseService<Account> getBaseService() {
		return baseService;
	}


	/**
	 * @param baseService the baseService to set
	 */
	public void setBaseService(IBaseService<Account> baseService) {
		this.baseService = baseService;
	}


	/**
	 * @return the accountTypeService
	 */
	public IBaseService<AccountType> getAccountTypeService() {
		return accountTypeService;
	}


	/**
	 * @param accountTypeService the accountTypeService to set
	 */
	public void setAccountTypeService(IBaseService<AccountType> accountTypeService) {
		this.accountTypeService = accountTypeService;
	}


	/**
	 * @return the industryService
	 */
	public IBaseService<Industry> getIndustryService() {
		return industryService;
	}


	/**
	 * @param industryService the industryService to set
	 */
	public void setIndustryService(IBaseService<Industry> industryService) {
		this.industryService = industryService;
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
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}


	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the types
	 */
	public List<AccountType> getTypes() {
		return types;
	}


	/**
	 * @param types the types to set
	 */
	public void setTypes(List<AccountType> types) {
		this.types = types;
	}


	/**
	 * @return the industries
	 */
	public List<Industry> getIndustries() {
		return industries;
	}


	/**
	 * @param industries the industries to set
	 */
	public void setIndustries(List<Industry> industries) {
		this.industries = industries;
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
	 * @return the industryID
	 */
	public Integer getIndustryID() {
		return industryID;
	}


	/**
	 * @param industryID the industryID to set
	 */
	public void setIndustryID(Integer industryID) {
		this.industryID = industryID;
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
	 * @return the manageID
	 */
	public Integer getManagerID() {
		return managerID;
	}


	/**
	 * @param manageID the manageID to set
	 */
	public void setManagerID(Integer managerID) {
		this.managerID = managerID;
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
