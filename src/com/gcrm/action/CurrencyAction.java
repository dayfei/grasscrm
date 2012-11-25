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

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gcrm.domain.Currency;
import com.gcrm.exception.ServiceException;
import com.gcrm.service.IBaseService;
import com.gcrm.vo.SearchCondition;
import com.gcrm.vo.SearchResult;

/**
 * Manages the Currency dropdown list
 * 
 */
public class CurrencyAction extends BaseListAction {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Currency> baseService;
	private Currency currency;

	private static final String CLAZZ = Currency.class.getSimpleName();

	/**
	 * Gets the list JSON data.
	 * 
	 * @return list JSON data
	 */
	public String list() throws Exception {

		SearchCondition searchCondition = getSearchCondition();
		SearchResult<Currency> result = baseService.getPaginationObjects(CLAZZ,
				searchCondition);
		List<Currency> currencys = result.getResult();

		long totalRecords = result.getTotalRecords();

		// Constructs the JSON data
		String json = "{\"total\": " + totalRecords + ",\"rows\": [";
		int size = currencys.size();
		for (int i = 0; i < size; i++) {
			Currency instance = (Currency) currencys.get(i);
			Integer id = instance.getId();
			String name = instance.getName();
			String code = instance.getCode();
			double rate = instance.getRate();
			String symbol = instance.getSymbol();
			String status = instance.getStatus();

			json += "{\"id\":\"" + id + "\",\"currency.id\":\"" + id
					+ "\",\"currency.name\":\"" + name
					+ "\",\"currency.code\":\"" + code
					+ "\",\"currency.rate\":\"" + rate
					+ "\",\"currency.symbol\":\"" + symbol
					+ "\",\"currency.status\":\"" + status + "\"}";
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
	 * Saves the entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String save() throws Exception {
		getbaseService().makePersistent(currency);
		return SUCCESS;
	}

	/**
	 * Deletes the selected entity.
	 * 
	 * @return the SUCCESS result
	 */
	public String delete() throws ServiceException {
		baseService.batchDeleteEntity(Currency.class, this.getSeleteIDs());
		return SUCCESS;
	}

	public IBaseService<Currency> getbaseService() {
		return baseService;
	}

	public void setbaseService(IBaseService<Currency> baseService) {
		this.baseService = baseService;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
