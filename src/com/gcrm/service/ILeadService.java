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
package com.gcrm.service;

import com.gcrm.domain.Lead;

/**
 * Lead service Interface
 */
public interface ILeadService extends IBaseService<Lead> {
	/**
	 * Converts lead
	 * 
	 * @param id lead instance id
	 * @param accountCheck the flag to convert into account
	 * @param contactCheck the flag to convert into contact
	 * @param opportunityCheck the flag to convert into opportunity 
	 */	
	public void convert(Integer id, boolean accountCheck,boolean contactCheck, boolean opportunityCheck) throws Exception;

}
