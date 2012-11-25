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

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

/**
 * Url access decision manager
 */
public class UrlAccessDecisionManager implements AccessDecisionManager {

	public void decide(Authentication authentication, Object securityObject,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		String url = null;

		try {
			if (securityObject instanceof FilterInvocation) {
				FilterInvocation filter = (FilterInvocation) securityObject;
				url = filter.getRequestUrl();
			}
			if (authentication.getPrincipal() instanceof String && "anonymousUser".equals(authentication.getPrincipal())){
				throw new AccessDeniedException(" no permission access！");
			}
			Map<String, Collection<ConfigAttribute>> permissionMap = SecurityMetadataSource.permissionMap;
			Collection<ConfigAttribute> customConfigAttributes = permissionMap.get(url);
			if (customConfigAttributes == null){
				return;
			}
			if (customConfigAttributes.size() == 0){
				throw new AccessDeniedException(" no permission access！");
			}			
			for (ConfigAttribute configAttribute : customConfigAttributes ){
				String needPermission = configAttribute.getAttribute();
				for (GrantedAuthority ga : authentication.getAuthorities()) {
					if (needPermission.equals(ga.getAuthority())) {
						return;
					}
				}
			}
			throw new AccessDeniedException(" no permission access！");
		} catch (Exception e) {
			throw new AccessDeniedException("Role check fails.");
		}
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}
