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
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gcrm.domain.Role;
import com.gcrm.domain.User;
import com.gcrm.service.IUserService;

/**
 * User details service implementation
 */
public class UserDetailsServiceImpl implements
		UserDetailsService {
	private IUserService userService;
	
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {

		UserDetailsImpl userDetails = new UserDetailsImpl();

		User user = null;
		try {
			user = this.userService.findByName(userName);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Error found in getting user。");
		}
		if (user == null) {
			throw new UsernameNotFoundException("Hasn't found user information.");
		}
		userDetails.setUsername(user.getName());
		String password = user.getPassword();
		if (password == null){
			password = "";
		}
		userDetails.setPassword(user.getPassword());
		userDetails.setCredentialsNonExpired(true);
		userDetails.setAccountNonExpired(true);

		userDetails.setAccountNonLocked(true);
		userDetails.setEnabled(true);
		Set<Role> roles = user.getRoles();

		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (Role role: roles) {
			SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			authorities.add(grantedAuthority);
		}
		userDetails.setAuthorities(authorities);
		userDetails.setUser(user);
		return userDetails;
	}

	/**
	 * @return the userService
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
