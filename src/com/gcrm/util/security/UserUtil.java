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
package com.gcrm.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.gcrm.domain.User;
import com.gcrm.service.IUserService;
import com.gcrm.util.spring.SpringContextUtil;

/**
 * User util
 */
public class UserUtil {
	/**
	 * Gets current login user name
	 * 
	 * @return curretn login user name
	 */
	public static String getUserName() {
		try {
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();// 取得用户信息
			UserDetails userDetails = (UserDetails) authentication
					.getPrincipal();// 取得用户信息
			return userDetails.getUsername();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Gets user by user name
	 * 
	 * @param userName user name
	 * @return user instance
	 */
	public static User getUser(String userName) {
		IUserService userService = (IUserService) SpringContextUtil
				.getBean("userService");
		try {
			return userService.findByName(userName);
		} catch (Exception e) {
			return null;
		}
	}

}
