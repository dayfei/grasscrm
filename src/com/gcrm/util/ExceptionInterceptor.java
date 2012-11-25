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
package com.gcrm.util;

import org.apache.log4j.Logger;


import com.gcrm.util.Constant;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * Exception interceptor
 */
public class ExceptionInterceptor implements Interceptor {

	private static final long serialVersionUID = -8471937545040722947L;

	private Logger logger = Logger.getLogger(this.getClass());

	public void destroy() {
		// no logic
	}

	public void init() {
		// no logic
	}

	/**
	 * Intercepts exception 
	 * 
	 * @param action target action
	 * @return Action result
	 */
	public String intercept(ActionInvocation action) throws Exception {
		String result = Action.SUCCESS;

		try {
			result = action.invoke();
		} catch(Exception e) {
			result = Action.INPUT;
			logger.error(e.getMessage(), e);
			ActionSupport actionSupport = (ActionSupport)action.getAction();
			actionSupport.addActionError(actionSupport.getText(Constant.GENERAL_ERROR_KEY));
		}

		return result;
	}
}
