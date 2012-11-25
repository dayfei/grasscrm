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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.gcrm.domain.Role;
import com.gcrm.domain.User;
import com.gcrm.domain.UserStatus;
import com.gcrm.security.AuthenticationFilter;
import com.gcrm.service.IBaseService;
import com.opensymphony.xwork2.Preparable;

/**
 * Edits User
 * 
 */
public class EditUserAction extends BaseEditAction implements Preparable{

	private static final long serialVersionUID = -2404576552417042445L;

	private IBaseService<User> baseService;
	private IBaseService<UserStatus> userStatusService;
	private IBaseService<Role> roleService;
	private User user;
	private List<UserStatus> statuses;
	private Integer statusID = null;
	private Integer reportToID = null;
	private List<Role> leftRoles;
	private List<Role> rightRoles;
	private String[] leftRole;
	private String[] rightRole;	
			
    /**
     * Saves the entity.
     * 
     * @return the SUCCESS result
     */	
	public String save() throws Exception {
		UserStatus status = null;
		if (statusID != null) {
			status = userStatusService.getEntityById(UserStatus.class,
					statusID);
		}
		if (rightRole != null && rightRole.length > 0) {
			Set<Role> roleSet = new HashSet<Role>(0);
			for (int i = 0; i < rightRole.length; i++) {
				String roleID = rightRole[i];
				Role role = roleService.getEntityById(
						Role.class, Integer.parseInt(roleID));
				roleSet.add(role);
			}
			user.setRoles(roleSet);
		}		
		
		user.setStatus(status);

		User reportTo = null;
		if (reportToID != null){
			reportTo = baseService.getEntityById(User.class, reportToID);
		}
		user.setReport_to(reportTo);
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (user.getId() != null) {
			User originalUser = baseService.getEntityById(User.class, user.getId());
			String oldPassword = originalUser.getPassword();
			if (!oldPassword.equalsIgnoreCase(user.getPassword())){
				user.setPassword(encoder.encodePassword(user.getPassword(), AuthenticationFilter.SALT));
			}
		}else{
			user.setPassword(encoder.encodePassword(user.getPassword(), AuthenticationFilter.SALT));			
		}
		getBaseService().makePersistent(user);
		return SUCCESS;
	}

    /**
     * Gets the entity.
     * 
     * @return the SUCCESS result
     */
	public String get() throws Exception {
		List<Role> allRole = roleService
				.getAllObjects(Role.class.getSimpleName());
		if (this.getId() != null) {
			user = baseService.getEntityById(User.class, this.getId());
			UserStatus status = user.getStatus();
			if (status != null) {
				statusID = status.getId();
			}
			User reportTo = user.getReport_to();
			if (reportTo != null) {
				reportToID = reportTo.getId();
			}
			Set<Role> roleSet = user.getRoles();
			rightRoles = new ArrayList<Role>();
			leftRoles = new ArrayList<Role>();
			for (Role role : roleSet) {
				rightRoles.add(role);
			}
			allRole.removeAll(rightRoles);
			leftRoles = allRole;			
			
		}else{
			this.leftRoles = allRole;
		}
		return SUCCESS;
	}

    /**
     * Prepares the list
     * 
     */	
	public void prepare() throws Exception {
		this.statuses = userStatusService.getAllObjects(UserStatus.class
				.getSimpleName());	
	}


	/**
	 * @return the baseService
	 */
	public IBaseService<User> getBaseService() {
		return baseService;
	}


	/**
	 * @param baseService the baseService to set
	 */
	public void setBaseService(IBaseService<User> baseService) {
		this.baseService = baseService;
	}


	/**
	 * @return the userStatusService
	 */
	public IBaseService<UserStatus> getUserStatusService() {
		return userStatusService;
	}


	/**
	 * @param userStatusService the userStatusService to set
	 */
	public void setUserStatusService(IBaseService<UserStatus> userStatusService) {
		this.userStatusService = userStatusService;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the statuses
	 */
	public List<UserStatus> getStatuses() {
		return statuses;
	}


	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuss(List<UserStatus> statuses) {
		this.statuses = statuses;
	}


	/**
	 * @return the statusID
	 */
	public Integer getStatusID() {
		return statusID;
	}


	/**
	 * @param statusID the statusID to set
	 */
	public void setStatusID(Integer statusID) {
		this.statusID = statusID;
	}


	/**
	 * @return the reportToID
	 */
	public Integer getReportToID() {
		return reportToID;
	}


	/**
	 * @param reportToID the reportToID to set
	 */
	public void setReportToID(Integer reportToID) {
		this.reportToID = reportToID;
	}


	/**
	 * @return the roleService
	 */
	public IBaseService<Role> getRoleService() {
		return roleService;
	}


	/**
	 * @param roleService the roleService to set
	 */
	public void setRoleService(IBaseService<Role> roleService) {
		this.roleService = roleService;
	}


	/**
	 * @return the leftRoles
	 */
	public List<Role> getLeftRoles() {
		return leftRoles;
	}


	/**
	 * @param leftRoles the leftRoles to set
	 */
	public void setLeftRoles(List<Role> leftRoles) {
		this.leftRoles = leftRoles;
	}


	/**
	 * @return the rightRoles
	 */
	public List<Role> getRightRoles() {
		return rightRoles;
	}


	/**
	 * @param rightRoles the rightRoles to set
	 */
	public void setRightRoles(List<Role> rightRoles) {
		this.rightRoles = rightRoles;
	}


	/**
	 * @return the leftRole
	 */
	public String[] getLeftRole() {
		return leftRole;
	}


	/**
	 * @param leftRole the leftRole to set
	 */
	public void setLeftRole(String[] leftRole) {
		this.leftRole = leftRole;
	}


	/**
	 * @return the rightRole
	 */
	public String[] getRightRole() {
		return rightRole;
	}


	/**
	 * @param rightRole the rightRole to set
	 */
	public void setRightRole(String[] rightRole) {
		this.rightRole = rightRole;
	}

}
