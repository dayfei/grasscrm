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
import java.util.Map;

import com.gcrm.exception.ServiceException;
import com.gcrm.util.CommonUtil;
import com.gcrm.util.Constant;
import com.gcrm.vo.SearchCondition;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Base List Action for entity list
 */
public abstract class BaseListAction extends ActionSupport {

	// How many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer rows = 0;
	// The requested page. By default grid sets this to 1.
	private Integer page = 0;
	// Sorting order - asc or desc
	private String order = "asc";
	// Sort order - i.e. user click to sort.
	private String sort;
	private String filters;
	private String _search;
	private String filter_key;
	private String filter_op;
	private String filter_value;
	private String createKey;
	private String removeKey;
	private String relationKey;
	private String relationValue;

	protected String seleteIDs = null;
	protected Integer id;
	private String q;

	private String fileName;
	private File upload;
	private int successfulNum;
	private int failedNum;
	private int totalNum;
	private Map<String, String> failedMsg;

	private static final long serialVersionUID = 5258442946380687955L;

	/**
	 * Gets the search condition for datagrid
	 * 
	 * @return search condition
	 */
	protected SearchCondition getSearchCondition() throws Exception {

		StringBuffer condition = new StringBuffer("");

		if (!CommonUtil.isNullOrEmpty(q)) {
			_search = Constant.BOOLEAN_TRUE;
			filter_key = "name";
			filter_op = Constant.FILTER_LIKE;
			filter_value = q;
		}

		if (Constant.BOOLEAN_TRUE.equals(_search)) {

			String op = filter_op;
			String data = filter_value;

			condition.append(filter_key).append(" ").append(op);

			if (Constant.FILTER_LIKE.equals(op)) {
				condition.append(" '%").append(data).append("%'");
			} else {
				condition.append(" ").append(data);
			}

		}

		int pageNo = page;
		int pageSize = rows;

		if (CommonUtil.isNullOrEmpty(sort)) {
			sort = "id";
			order = "asc";
		}

		SearchCondition searchCondition = new SearchCondition(pageNo, pageSize,
				sort, order, condition.toString());
		return searchCondition;
	}

	/**
	 * Sets filter expression and gets the list
	 * 
	 * @return filtered list result
	 */	
	public String filter() throws Exception {
		this.set_search(Constant.BOOLEAN_TRUE);
		this.setFilter_op("=");
		this.setFilter_value(String.valueOf(id));
		return this.list();
	}

	/**
	 * Abstract list method
	 * 
	 * @return list result
	 */		
	public abstract String list() throws Exception;

	public String filterPage() throws Exception {
		if (id == null) {
			id = 0;
		}
		return SUCCESS;
	}

	/**
	 * Goes to the select page
	 * 
	 * @return SUCCESS result
	 */		
	public String selectPage() throws ServiceException {
		return SUCCESS;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String get_search() {
		return _search;
	}

	public void set_search(String _search) {
		this._search = _search;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the filter_key
	 */
	public String getFilter_key() {
		return filter_key;
	}

	/**
	 * @param filter_key
	 *            the filter_key to set
	 */
	public void setFilter_key(String filter_key) {
		this.filter_key = filter_key;
	}

	/**
	 * @return the filter_op
	 */
	public String getFilter_op() {
		return filter_op;
	}

	/**
	 * @param filter_op
	 *            the filter_op to set
	 */
	public void setFilter_op(String filter_op) {
		this.filter_op = filter_op;
	}

	/**
	 * @return the filter_value
	 */
	public String getFilter_value() {
		return filter_value;
	}

	/**
	 * @param filter_value
	 *            the filter_value to set
	 */
	public void setFilter_value(String filter_value) {
		this.filter_value = filter_value;
	}

	/**
	 * @return the seleteIDs
	 */
	public String getSeleteIDs() {
		return seleteIDs;
	}

	/**
	 * @param seleteIDs
	 *            the seleteIDs to set
	 */
	public void setSeleteIDs(String seleteIDs) {
		this.seleteIDs = seleteIDs;
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
	 * @return the q
	 */
	public String getQ() {
		return q;
	}

	/**
	 * @param q
	 *            the q to set
	 */
	public void setQ(String q) {
		this.q = q;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the upload
	 */
	public File getUpload() {
		return upload;
	}

	/**
	 * @param upload
	 *            the upload to set
	 */
	public void setUpload(File upload) {
		this.upload = upload;
	}

	/**
	 * @return the successfulNum
	 */
	public int getSuccessfulNum() {
		return successfulNum;
	}

	/**
	 * @param successfulNum
	 *            the successfulNum to set
	 */
	public void setSuccessfulNum(int successfulNum) {
		this.successfulNum = successfulNum;
	}

	/**
	 * @return the failedNum
	 */
	public int getFailedNum() {
		return failedNum;
	}

	/**
	 * @param failedNum
	 *            the failedNum to set
	 */
	public void setFailedNum(int failedNum) {
		this.failedNum = failedNum;
	}

	/**
	 * @return the totalNum
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * @param totalNum
	 *            the totalNum to set
	 */
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * @return the failedMsg
	 */
	public Map<String, String> getFailedMsg() {
		return failedMsg;
	}

	/**
	 * @param failedMsg
	 *            the failedMsg to set
	 */
	public void setFailedMsg(Map<String, String> failedMsg) {
		this.failedMsg = failedMsg;
	}

	/**
	 * @return the createKey
	 */
	public String getCreateKey() {
		return createKey;
	}

	/**
	 * @param createKey
	 *            the createKey to set
	 */
	public void setCreateKey(String createKey) {
		this.createKey = createKey;
	}

	/**
	 * @return the removeKey
	 */
	public String getRemoveKey() {
		return removeKey;
	}

	/**
	 * @param removeKey
	 *            the removeKey to set
	 */
	public void setRemoveKey(String removeKey) {
		this.removeKey = removeKey;
	}

	/**
	 * @return the relationKey
	 */
	public String getRelationKey() {
		return relationKey;
	}

	/**
	 * @param relationKey
	 *            the relationKey to set
	 */
	public void setRelationKey(String relationKey) {
		this.relationKey = relationKey;
	}

	/**
	 * @return the relationValue
	 */
	public String getRelationValue() {
		return relationValue;
	}

	/**
	 * @param relationValue
	 *            the relationValue to set
	 */
	public void setRelationValue(String relationValue) {
		this.relationValue = relationValue;
	}

}
