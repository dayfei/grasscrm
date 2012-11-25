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


import com.gcrm.domain.Campaign;
import com.gcrm.domain.CampaignStatus;
import com.gcrm.domain.CampaignType;
import com.gcrm.domain.Currency;
import com.gcrm.domain.User;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Campaign
 * 
 */
public class EditCampaignAction extends BaseEditAction implements Preparable{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Campaign> baseService;
	private IBaseService<CampaignType> campaignTypeService;
	private IBaseService<CampaignStatus> campaignStatusService;
	private IBaseService<Currency> currencyService;
	private IBaseService<User> userService;
	private Campaign campaign;
	private List<CampaignType> types;
	private List<CampaignStatus> statuses;
	private List<Currency> currencies;
	private Integer statusID = null;
	private Integer typeID = null;
	private Integer currencyID = null;
	private Integer assignedToID = null;
	private String startDate = null;
	private String endDate = null;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */		
	public String save() throws Exception {
		CampaignStatus status = null;
		if (statusID != null) {
			status = campaignStatusService.getEntityById(CampaignStatus.class,
					statusID);
		}
		campaign.setStatus(status);
		CampaignType type = null;
		if (typeID != null){		
		type = campaignTypeService.getEntityById(
				CampaignType.class, typeID);
		}
		campaign.setType(type);
		Currency currency = null;
		if (currencyID != null){
		 currency = currencyService.getEntityById(Currency.class,
				currencyID);
		}
		campaign.setCurrency(currency);
		User assignedTo = null;
		if (assignedToID != null){
			assignedTo = userService.getEntityById(User.class, assignedToID);
		}
		campaign.setAssigned_to(assignedTo);
		SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
		Date start_date = null;
		if (!CommonUtil.isNullOrEmpty(startDate)){
		start_date = dateFormat.parse(startDate);
		}
		campaign.setStart_date(start_date);
		Date end_date = null;
		if (!CommonUtil.isNullOrEmpty(endDate)){
			end_date = dateFormat.parse(endDate);
		}
		campaign.setEnd_date(end_date);		
		
		super.updateBaseInfo(campaign);
		
		getbaseService().makePersistent(campaign);
		return SUCCESS;
	}

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		if (this.getId() != null) {
			campaign = baseService.getEntityById(Campaign.class, this.getId() );
			CampaignStatus status = campaign.getStatus();
			if (status != null) {
				statusID = status.getId();
			}
			CampaignType type = campaign.getType();
			if (type != null) {
				typeID = type.getId();
			}
			Currency currency = campaign.getCurrency();
			if (currency != null) {
				currencyID = currency.getId();
			}
			User assignedTo = campaign.getAssigned_to();
			if (assignedTo != null) {
				assignedToID = assignedTo.getId();
			}
			Date start_date = campaign.getStart_date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
			if (start_date != null) {
				startDate = dateFormat.format(start_date);
			}
			Date end_date = campaign.getEnd_date();
			if (end_date != null) {
				endDate = dateFormat.format(end_date);
			}			
		}
		return SUCCESS;
	}

    /**
     * Prepares the list
     * 
     */		
	public void prepare() throws Exception {
		this.statuses = campaignStatusService.getAllObjects(CampaignStatus.class
				.getSimpleName());
		this.types = campaignTypeService.getAllObjects(CampaignType.class
				.getSimpleName());		
		this.currencies = currencyService.getAllObjects(Currency.class
				.getSimpleName());
	}
		
	
	public IBaseService<Campaign> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Campaign> baseService) {
		this.baseService = baseService;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	/**
	 * @return the types
	 */
	public List<CampaignType> getTypes() {
		return types;
	}

	/**
	 * @return the campaignTypeService
	 */
	public IBaseService<CampaignType> getCampaignTypeService() {
		return campaignTypeService;
	}

	/**
	 * @return the statuses
	 */
	public List<CampaignStatus> getStatuses() {
		return statuses;
	}

	/**
	 * @return the campaignStatusService
	 */
	public IBaseService<CampaignStatus> getCampaignStatusService() {
		return campaignStatusService;
	}

	/**
	 * @param campaignStatusService
	 *            the campaignStatusService to set
	 */
	public void setCampaignStatusService(
			IBaseService<CampaignStatus> campaignStatusService) {
		this.campaignStatusService = campaignStatusService;
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
	 * @return the typeID
	 */
	public Integer getTypeID() {
		return typeID;
	}

	/**
	 * @param typeID
	 *            the typeID to set
	 */
	public void setTypeID(Integer typeID) {
		this.typeID = typeID;
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
	 * @return the currencies
	 */
	public List<Currency> getCurrencies() {
		return currencies;
	}

	/**
	 * @param currencies
	 *            the currencies to set
	 */
	public void setCurrencies() {
		this.currencies = currencyService.getAllObjects(Currency.class
				.getSimpleName());
	}

	/**
	 * @return the currencyID
	 */
	public Integer getCurrencyID() {
		return currencyID;
	}

	/**
	 * @param currencyID
	 *            the currencyID to set
	 */
	public void setCurrencyID(Integer currencyID) {
		this.currencyID = currencyID;
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
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	/**
	 * @param campaignTypeService the campaignTypeService to set
	 */
	public void setCampaignTypeService(
			IBaseService<CampaignType> campaignTypeService) {
		this.campaignTypeService = campaignTypeService;
	}


	/**
	 * @param types the types to set
	 */
	public void setTypes(List<CampaignType> types) {
		this.types = types;
	}


	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuses(List<CampaignStatus> statuses) {
		this.statuses = statuses;
	}


	/**
	 * @param currencies the currencies to set
	 */
	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
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
}
