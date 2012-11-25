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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import com.gcrm.domain.Permission;
import com.gcrm.domain.Role;
import com.gcrm.security.SecurityMetadataSource;
import com.gcrm.service.IBaseService;
import com.gcrm.util.CommonUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits Role
 * 
 */
public class EditRoleAction extends BaseEditAction implements Preparable {

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<Role> baseService;
	private IBaseService<Permission> permissionService;
	private Role role;
	private List<Permission> leftPermissions;
	private List<Permission> rightPermissions;
	private String[] leftPermission;
	private String[] rightPermission;

    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */		
	public String save() throws Exception {
		if (rightPermission != null && rightPermission.length > 0) {
			Set<Permission> permissionSet = new HashSet<Permission>(0);
			for (int i = 0; i < rightPermission.length; i++) {
				String permissionID = rightPermission[i];
				Permission permission = permissionService.getEntityById(
						Permission.class, Integer.parseInt(permissionID));
				permissionSet.add(permission);
			}
			role.setPermissions(permissionSet);
		}
		getBaseService().makePersistent(role);
		
		return SUCCESS;
	}

    /**
     * Resets permission Map
     * 
     * @return the SUCCESS result
     */	
	public String resetPermissionMap(){
		SecurityMetadataSource.permissionMap = null;
		new SecurityMetadataSource(this.getPermissionService());
		Map<String, Collection<ConfigAttribute>> permissionMap = SecurityMetadataSource.permissionMap;
		Map<String, String> urlPermissions = new HashMap<String, String>();
		for (String url :permissionMap.keySet()){
			String roles = "";
			Collection<ConfigAttribute> configAttributes = permissionMap.get(url);
			for (ConfigAttribute configAttribute : configAttributes){
				String roleName = configAttribute.getAttribute();
				if (roles.length() > 0 ){
					roles += ",";
				}
				roles += roleName;
			}
			urlPermissions.put(url, roles);
		}
		ActionContext context = ActionContext.getContext();
		Map<String, Object> session = context.getSession();
		session.put("permissions",urlPermissions);		
		return SUCCESS;
	}
	
    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		List<Permission> allPermission = permissionService
				.getAllObjects(Permission.class.getSimpleName());
		if (this.getId() != null) {
			role = baseService.getEntityById(Role.class, this.getId());
			Set<Permission> permissionSet = role.getPermissions();
			rightPermissions = new ArrayList<Permission>();
			leftPermissions = new ArrayList<Permission>();
			for (Permission permisson : permissionSet) {
				rightPermissions.add(permisson);
			}
			allPermission.removeAll(rightPermissions);
			leftPermissions = allPermission;
		} else {
			this.leftPermissions = allPermission;
		}
		return SUCCESS;
	}

    /**
     * Prepares the list
     * 
     */	
	public void prepare() throws Exception {
	}

	/**
	 * @return the baseService
	 */
	public IBaseService<Role> getBaseService() {
		return baseService;
	}

	/**
	 * @param baseService
	 *            the baseService to set
	 */
	public void setBaseService(IBaseService<Role> baseService) {
		this.baseService = baseService;
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

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the leftPermissions
	 */
	public List<Permission> getLeftPermissions() {
		return leftPermissions;
	}

	/**
	 * @param leftPermissions
	 *            the leftPermissions to set
	 */
	public void setLeftPermissions(List<Permission> leftPermissions) {
		this.leftPermissions = leftPermissions;
	}

	/**
	 * @return the rightPermissions
	 */
	public List<Permission> getRightPermissions() {
		return rightPermissions;
	}

	/**
	 * @param rightPermissions
	 *            the rightPermissions to set
	 */
	public void setRightPermissions(List<Permission> rightPermissions) {
		this.rightPermissions = rightPermissions;
	}

	/**
	 * @return the leftPermission
	 */
	public String[] getLeftPermission() {
		return leftPermission;
	}

	/**
	 * @param leftPermission
	 *            the leftPermission to set
	 */
	public void setLeftPermission(String[] leftPermission) {
		this.leftPermission = leftPermission;
	}

	/**
	 * @return the rightPermission
	 */
	public String[] getRightPermission() {
		return rightPermission;
	}

	/**
	 * @param rightPermission
	 *            the rightPermission to set
	 */
	public void setRightPermission(String[] rightPermission) {
		this.rightPermission = rightPermission;
	}

}
