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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import com.gcrm.domain.Campaign;
import com.gcrm.domain.CampaignStatus;
import com.gcrm.domain.CampaignType;
import com.gcrm.domain.Currency;
import com.gcrm.domain.User;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Lists Campaign
 * 
 */
public class ListCampaignAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Campaign> baseService;
	private IBaseService<CampaignType> campaignTypeService;
	private IBaseService<CampaignStatus> campaignStatusService;
	private IBaseService<Currency> currencyService;
	private IBaseService<User> userService;
	private Campaign campaign;

	private static final String CLAZZ = Campaign.class.getSimpleName();

	/**
	 * Gets the list data.
	 * 
	 * @return null
	 */
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Campaign> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		List<Campaign> campaigns = result.getResult();

		long totalRecords = result.getTotalRecords();

		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = campaigns.size();

		String statusName = null;
		String typeName = null;
		String userName = null;
		for (int i = 0; i < size; i++) {
			Campaign instance = (Campaign) campaigns.get(i);
			int id = instance.getId();
			String name = CommonUtil.fromNullToEmpty(instance.getName());
			CampaignStatus status = instance.getStatus();
			if (status != null) {
				statusName = CommonUtil.fromNullToEmpty(status.getName());
			} else {
				statusName = "";
			}
			CampaignType type = instance.getType();
			if (type != null) {
				typeName = CommonUtil.fromNullToEmpty(type.getName());
			} else {
				typeName = "";
			}

			Date end_date = instance.getEnd_date();
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
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					Constant.DATE_TIME_FORMAT);
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
					+ "\",\"status\":\"" + statusName + "\",\"type\":\""
					+ typeName + "\",\"end_date\":\"" + end_date
					+ "\",\"user_name\":\"" + userName + "\",\"created_by\":\""
					+ createdByName + "\",\"updated_by\":\"" + updatedByName
					+ "\",\"created_on\":\"" + createdOnName
					+ "\",\"updated_on\":\"" + updatedOnName + "\"}";
			if (i < size - 1) {
				json += ",";
			}
		}
		json += "]}";

		// Returns JSON data back to page
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().write(json);
		return null;
	}

	/**
	 * Deletes the selected entities.
	 * 
	 * @return the SUCCESS result
	 */
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Campaign.class, this.getSeleteIDs());
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
				Campaign oriRecord = baseService.getEntityById(Campaign.class,
						Integer.valueOf(copyid));
				Campaign targetRecord = oriRecord.clone();
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
			final String[] header = new String[] { "ID", "Name", "Status",
					"Type", "Start Date", "End Date", "Currency",
					"Impressions", "Budget", "Expected Cost", "Actual Cost",
					"Expected Revenue", "Objective", "Description",
					"Assigned To" };
			writer.writeHeader(header);
			String[] ids = seleteIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				Campaign campaign = baseService.getEntityById(Campaign.class,
						Integer.parseInt(id));
				final HashMap<String, ? super Object> data1 = new HashMap<String, Object>();
				data1.put(header[0], campaign.getId());
				data1.put(header[1],
						CommonUtil.fromNullToEmpty(campaign.getName()));
				if (campaign.getStatus() != null) {
					data1.put(header[2], campaign.getStatus().getId());
				} else {
					data1.put(header[2], "");
				}
				if (campaign.getType() != null) {
					data1.put(header[3], campaign.getType().getId());
				} else {
					data1.put(header[3], "");
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
				Date startDate = campaign.getStart_date();
				if (startDate != null) {
					data1.put(header[4], dateFormat.format(startDate));
				} else {
					data1.put(header[4], "");
				}
				Date endDate = campaign.getEnd_date();
				if (endDate != null) {
					data1.put(header[5], dateFormat.format(endDate));
				} else {
					data1.put(header[5], "");
				}
				if (campaign.getCurrency() != null) {
					data1.put(header[6], campaign.getCurrency().getId());
				} else {
					data1.put(header[6], "");
				}
				data1.put(header[7], campaign.getImpressions());
				data1.put(header[8], campaign.getBudget());
				data1.put(header[9], campaign.getExpected_cost());
				data1.put(header[10], campaign.getActual_cost());
				data1.put(header[11], campaign.getExpected_revenue());
				data1.put(header[12],
						CommonUtil.fromNullToEmpty(campaign.getObjective()));
				data1.put(header[13],
						CommonUtil.fromNullToEmpty(campaign.getDescription()));
				if (campaign.getAssigned_to() != null) {
					data1.put(header[14], campaign.getAssigned_to().getId());
				} else {
					data1.put(header[14], "");
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

				Campaign campaign = new Campaign();
				try {
					String id = row.get("ID");
					if (!CommonUtil.isNullOrEmpty(id)) {
						campaign.setId(Integer.parseInt(id));
					}
					campaign.setName(CommonUtil.fromNullToEmpty(row.get("Name")));
					String statusID = row.get("Status");
					if (CommonUtil.isNullOrEmpty(statusID)) {
						campaign.setStatus(null);
					} else {
						CampaignStatus status = campaignStatusService
								.getEntityById(CampaignStatus.class,
										Integer.parseInt(statusID));
						campaign.setStatus(status);
					}
					String typeID = row.get("Type");
					if (CommonUtil.isNullOrEmpty(typeID)) {
						campaign.setType(null);
					} else {
						CampaignType type = campaignTypeService.getEntityById(
								CampaignType.class, Integer.parseInt(typeID));
						campaign.setType(type);
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"M/d/yyyy");
					String startDateS = row.get("Start Date");
					if (startDateS != null) {
						Date startDate = dateFormat.parse(startDateS);
						campaign.setStart_date(startDate);
					} else {
						campaign.setStart_date(null);
					}
					String endDateS = row.get("End Date");
					if (startDateS != null) {
						Date endDate = dateFormat.parse(endDateS);
						campaign.setEnd_date(endDate);
					} else {
						campaign.setEnd_date(null);
					}
					String currencyID = row.get("Currency");
					if (CommonUtil.isNullOrEmpty(currencyID)) {
						campaign.setCurrency(null);
					} else {
						Currency currency = currencyService.getEntityById(
								Currency.class, Integer.parseInt(currencyID));
						campaign.setCurrency(currency);
					}
					String impressions = row.get("Impressions");
					if (CommonUtil.isNullOrEmpty(impressions)) {
						campaign.setImpressions(0);
					} else {
						campaign.setImpressions(Double.parseDouble(impressions));
					}
					String budget = row.get("Budget");
					if (CommonUtil.isNullOrEmpty(budget)) {
						campaign.setBudget(0);
					} else {
						campaign.setBudget(Double.parseDouble(budget));
					}
					String expectedCost = row.get("Expected Cost");
					if (CommonUtil.isNullOrEmpty(expectedCost)) {
						campaign.setExpected_cost(0);
					} else {
						campaign.setExpected_cost(Double
								.parseDouble(expectedCost));
					}
					String actualCost = row.get("Actual Cost");
					if (CommonUtil.isNullOrEmpty(actualCost)) {
						campaign.setActual_cost(0);
					} else {
						campaign.setActual_cost(Double.parseDouble(actualCost));
					}
					String expectedRevenue = row.get("Expected Revenue");
					if (CommonUtil.isNullOrEmpty(expectedRevenue)) {
						campaign.setExpected_revenue(0);
					} else {
						campaign.setExpected_revenue(Double
								.parseDouble(expectedRevenue));
					}
					campaign.setObjective(CommonUtil.fromNullToEmpty(row
							.get("Objective")));
					campaign.setDescription(CommonUtil.fromNullToEmpty(row
							.get("Description")));
					String assignedToID = row.get("Assigned To");
					if (CommonUtil.isNullOrEmpty(assignedToID)) {
						campaign.setAssigned_to(null);
					} else {
						User assignedTo = userService.getEntityById(User.class,
								Integer.parseInt(assignedToID));
						campaign.setAssigned_to(assignedTo);
					}
					baseService.makePersistent(campaign);
					successfulNum++;
				} catch (Exception e) {
					failedNum++;
					failedMsg.put(campaign.getName(), e.getMessage());
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
	 * @return the campaignTypeService
	 */
	public IBaseService<CampaignType> getCampaignTypeService() {
		return campaignTypeService;
	}

	/**
	 * @param campaignTypeService
	 *            the campaignTypeService to set
	 */
	public void setCampaignTypeService(
			IBaseService<CampaignType> campaignTypeService) {
		this.campaignTypeService = campaignTypeService;
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

}
