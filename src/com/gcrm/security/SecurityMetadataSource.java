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
package com.gcrm.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.gcrm.domain.Permission;
import com.gcrm.domain.Role;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;

/**
 * Security metadata source
 */
public class SecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	private IBaseService<Permission> permissionService;
	public static Map<String, Collection<ConfigAttribute>> permissionMap = null;

	public SecurityMetadataSource(IBaseService<Permission> permissionService) {
		this.permissionService = permissionService;
		loadResourceDefine();
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	private void loadResourceDefine() {
		if (permissionMap == null) {
			permissionMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Permission> permissions = this.permissionService
					.getAllObjects(Permission.class.getSimpleName());
			for (Permission permission : permissions) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				Set<Role> roles = permission.getRoles();
				for (Role role : roles) {
					ConfigAttribute configAttribute = new SecurityConfig(
							role.getName());
					configAttributes.add(configAttribute);
				}
				String url = permission.getUrl();
				if (!CommonUtil.isNullOrEmpty(url)) {
					String[] urls = url.split(",");
					for (int i=0;i<urls.length;i++){
					   String permissionURL = urls[i];	
					   permissionMap.put(permissionURL, configAttributes);
					}
				}
			}
		}
	}

	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {

		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		System.out.println("requestUrl is " + requestUrl);
		if (permissionMap == null) {
			loadResourceDefine();
		}
		return permissionMap.get(requestUrl);
	}

	/**
	 * @return the permissionService
	 */
	public IBaseService<Permission> getPermissionService() {
		return permissionService;
	}

	/**
	 * @param permissionService
	 *            the permissionService to set
	 */
	public void setPermissionService(IBaseService<Permission> permissionService) {
		this.permissionService = permissionService;
	}

}
