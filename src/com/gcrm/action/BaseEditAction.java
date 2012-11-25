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
import java.util.Map;


import com.gcrm.domain.BaseEntity;
import com.gcrm.domain.User;
import com.gcrm.security.AuthenticationSuccessListener;
import com.gcrm.util.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Base Action for entity edit.
 */
public class BaseEditAction extends ActionSupport {
	
	private String createdBy = "";
	private String createdOn = "";
	private String updatedBy = "";
	private String updatedOn = "";
	private Integer id;
	private String relationKey;
	private String relationValue;

	private static final long serialVersionUID = -2404576552417042445L;

    /**
     * Updates the base information for entity.
     * 
     * @param entity instance
     */	
	protected void updateBaseInfo(BaseEntity entity) {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		User loginUser = (User) session
				.get(AuthenticationSuccessListener.LOGIN_USER);
		if (entity.getId() == null) {
			entity.setCreated_by(loginUser);
			entity.setCreated_on(new Date());
		} else {
			entity.setUpdated_by(loginUser);
			entity.setUpdated_on(new Date());
		}
	}

    /**
     * Gets the base information for entity.
     * 
     * @param entity instance
     */		
	protected void getBaseInfo(BaseEntity entity) {
		User createdUser = entity.getCreated_by();
		if (createdUser != null){
			this.setCreatedBy(createdUser.getName());
		}
		User updatedUser = entity.getUpdated_by();
		if (updatedUser != null){
			this.setUpdatedBy(updatedUser.getName());
		}	
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_TIME_FORMAT);
		Date createdOn = entity.getCreated_on();
		if (createdOn != null) {
			this.setCreatedOn(dateFormat.format(createdOn));
		}		
		Date updatedOn = entity.getUpdated_on();
		if (updatedOn != null) {
			this.setUpdatedOn(dateFormat.format(updatedOn));
		}			
	}
	
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedOn
	 */
	public String getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * @param updatedOn the updatedOn to set
	 */
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the relationKey
	 */
	public String getRelationKey() {
		return relationKey;
	}

	/**
	 * @param relationKey the relationKey to set
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
	 * @param relationValue the relationValue to set
	 */
	public void setRelationValue(String relationValue) {
		this.relationValue = relationValue;
	}

}
