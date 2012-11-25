package com.gcrm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class TargetList extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String name;
	private TargetListType type;
	private String description;
	private User assigned_to;
	private Set<Target> targets = new HashSet<Target>(0);
	private Set<Contact> contacts = new HashSet<Contact>(0);
	private Set<Lead> leads = new HashSet<Lead>(0);		
	private Set<User> users = new HashSet<User>(0);
	private Set<Account> accounts = new HashSet<Account>(0);		
	
	public TargetList clone() {
		TargetList o = null;
		try {
			o = (TargetList) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the assigned_to
	 */
	public User getAssigned_to() {
		return assigned_to;
	}

	/**
	 * @param assigned_to the assigned_to to set
	 */
	public void setAssigned_to(User assigned_to) {
		this.assigned_to = assigned_to;
	}

	/**
	 * @return the type
	 */
	public TargetListType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(TargetListType type) {
		this.type = type;
	}

	/**
	 * @return the targets
	 */
	public Set<Target> getTargets() {
		return targets;
	}

	/**
	 * @param targets the targets to set
	 */
	public void setTargets(Set<Target> targets) {
		this.targets = targets;
	}

	/**
	 * @return the contacts
	 */
	public Set<Contact> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * @return the leads
	 */
	public Set<Lead> getLeads() {
		return leads;
	}

	/**
	 * @param leads the leads to set
	 */
	public void setLeads(Set<Lead> leads) {
		this.leads = leads;
	}

	/**
	 * @return the users
	 */
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * @return the accounts
	 */
	public Set<Account> getAccounts() {
		return accounts;
	}

	/**
	 * @param accounts the accounts to set
	 */
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
	

}
