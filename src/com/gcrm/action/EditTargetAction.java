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
import java.util.Set;


import com.gcrm.domain.Account;
import com.gcrm.domain.Target;
import com.gcrm.domain.TargetList;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Target
 * 
 */
public class EditTargetAction extends BaseEditAction implements Preparable{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Target> baseService;
	private IBaseService<Account> accountService;
	private IBaseService<User> userService;
	private IBaseService<TargetList> targetListService;	
	private Target target;
    private Integer accountID = null;
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
		target.setAccount(account);
		
		User assignedTo = null;
		if (assignedToID != null){
			assignedTo = userService.getEntityById(User.class, assignedToID);
		}
		target.setAssigned_to(assignedTo);
		
		if ("TargetList".equals(this.getRelationKey())) {
			TargetList targetList = targetListService.getEntityById(TargetList.class,
					Integer.valueOf(this.getRelationValue()));
			Set<TargetList> targetLists = target.getTargetLists();
			if (targetLists == null){
				targetLists =  new HashSet<TargetList>();
			}
			targetLists.add(targetList);
		}
		
        super.updateBaseInfo(target);
	
		getBaseService().makePersistent(target);
		return SUCCESS;
	}

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		if (this.getId() != null) {
			target = baseService.getEntityById(Target.class, this.getId());
			Account account = target.getAccount();
			if (account != null) {
				accountID = account.getId();
			}
				
			User assignedTo = target.getAssigned_to();
			if (assignedTo != null) {
				assignedToID = assignedTo.getId();
			}				
			
		}
		return SUCCESS;
	}

    /**
     * Prepares the list
     * 
     */	
	public void prepare() throws Exception {
	
	}


	/**
	 * @return the baseService
	 */
	public IBaseService<Target> getBaseService() {
		return baseService;
	}


	/**
	 * @param baseService the baseService to set
	 */
	public void setBaseService(IBaseService<Target> baseService) {
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
	 * @return the target
	 */
	public Target getTarget() {
		return target;
	}


	/**
	 * @param target the target to set
	 */
	public void setTarget(Target target) {
		this.target = target;
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

}
