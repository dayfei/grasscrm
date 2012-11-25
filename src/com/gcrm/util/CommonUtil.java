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


/**
 * Common util
 */
public class CommonUtil {

	
	/**
	 * Confirms if the string is null or empty
	 * 
	 * @param str input string
	 * @return the flat to identify if the string is null or empty
	 */
	public static boolean isNullOrEmpty(String str) {

		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Converts string from null to empty
	 * 
	 * @param str input string
	 * @return the converted string
	 */	
	public static String fromNullToEmpty(String str) {

		if (str == null) {
			return "";
		}else{
			return str;
		}
	}

}
