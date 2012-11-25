
package com.gcrm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Lead extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String first_name;
	private String last_name;
	private String office_phone;
	private String title;
	private String mobile;
	private String department;
	private String fax;
	private Account account;
	private String primary_address;
	private String primary_city;
	private String primary_state;
	private String primary_postal_code;
	private String primary_country;
	private String other_address;
	private String other_city;
	private String other_state;
	private String other_postal_code;
	private String other_country;
	private String email;
	private String description;
	private LeadStatus status;
	private String status_description;
	private LeadSource lead_source;
	private String lead_source_description;
	private String opportunity_amount;
	private String referred_by;
	private Campaign campaign;
	private boolean not_call;
	private User assigned_to;
	private Set<Contact> contacts = new HashSet<Contact>(0);
	private Set<Opportunity> opportunities = new HashSet<Opportunity>(0);
	private Set<TargetList> targetLists = new HashSet<TargetList>(0);
	private Set<Call> calls = new HashSet<Call>(0);
	private Set<Meeting> meetings = new HashSet<Meeting>(0);

	public Lead clone() {
		Lead o = null;
		try {
			o = (Lead) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	/**
	 * @return the first_name
	 */
	public String getFirst_name() {
		return first_name;
	}

	/**
	 * @param first_name
	 *            the first_name to set
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	/**
	 * @return the last_name
	 */
	public String getLast_name() {
		return last_name;
	}

	/**
	 * @param last_name
	 *            the last_name to set
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	/**
	 * @return the office_phone
	 */
	public String getOffice_phone() {
		return office_phone;
	}

	/**
	 * @param office_phone
	 *            the office_phone to set
	 */
	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the primary_address
	 */
	public String getPrimary_address() {
		return primary_address;
	}

	/**
	 * @param primary_address
	 *            the primary_address to set
	 */
	public void setPrimary_address(String primary_address) {
		this.primary_address = primary_address;
	}

	/**
	 * @return the primary_city
	 */
	public String getPrimary_city() {
		return primary_city;
	}

	/**
	 * @param primary_city
	 *            the primary_city to set
	 */
	public void setPrimary_city(String primary_city) {
		this.primary_city = primary_city;
	}

	/**
	 * @return the primary_state
	 */
	public String getPrimary_state() {
		return primary_state;
	}

	/**
	 * @param primary_state
	 *            the primary_state to set
	 */
	public void setPrimary_state(String primary_state) {
		this.primary_state = primary_state;
	}

	/**
	 * @return the primary_postal_code
	 */
	public String getPrimary_postal_code() {
		return primary_postal_code;
	}

	/**
	 * @param primary_postal_code
	 *            the primary_postal_code to set
	 */
	public void setPrimary_postal_code(String primary_postal_code) {
		this.primary_postal_code = primary_postal_code;
	}

	/**
	 * @return the primary_country
	 */
	public String getPrimary_country() {
		return primary_country;
	}

	/**
	 * @param primary_country
	 *            the primary_country to set
	 */
	public void setPrimary_country(String primary_country) {
		this.primary_country = primary_country;
	}

	/**
	 * @return the other_address
	 */
	public String getOther_address() {
		return other_address;
	}

	/**
	 * @param other_address
	 *            the other_address to set
	 */
	public void setOther_address(String other_address) {
		this.other_address = other_address;
	}

	/**
	 * @return the other_city
	 */
	public String getOther_city() {
		return other_city;
	}

	/**
	 * @param other_city
	 *            the other_city to set
	 */
	public void setOther_city(String other_city) {
		this.other_city = other_city;
	}

	/**
	 * @return the other_state
	 */
	public String getOther_state() {
		return other_state;
	}

	/**
	 * @param other_state
	 *            the other_state to set
	 */
	public void setOther_state(String other_state) {
		this.other_state = other_state;
	}

	/**
	 * @return the other_postal_code
	 */
	public String getOther_postal_code() {
		return other_postal_code;
	}

	/**
	 * @param other_postal_code
	 *            the other_postal_code to set
	 */
	public void setOther_postal_code(String other_postal_code) {
		this.other_postal_code = other_postal_code;
	}

	/**
	 * @return the other_country
	 */
	public String getOther_country() {
		return other_country;
	}

	/**
	 * @param other_country
	 *            the other_country to set
	 */
	public void setOther_country(String other_country) {
		this.other_country = other_country;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public LeadStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(LeadStatus status) {
		this.status = status;
	}

	/**
	 * @return the status_description
	 */
	public String getStatus_description() {
		return status_description;
	}

	/**
	 * @param status_description
	 *            the status_description to set
	 */
	public void setStatus_description(String status_description) {
		this.status_description = status_description;
	}

	/**
	 * @return the lead_source
	 */
	public LeadSource getLead_source() {
		return lead_source;
	}

	/**
	 * @param lead_source
	 *            the lead_source to set
	 */
	public void setLead_source(LeadSource lead_source) {
		this.lead_source = lead_source;
	}

	/**
	 * @return the lead_source_description
	 */
	public String getLead_source_description() {
		return lead_source_description;
	}

	/**
	 * @param lead_source_description
	 *            the lead_source_description to set
	 */
	public void setLead_source_description(String lead_source_description) {
		this.lead_source_description = lead_source_description;
	}

	/**
	 * @return the opportunity_amount
	 */
	public String getOpportunity_amount() {
		return opportunity_amount;
	}

	/**
	 * @param opportunity_amount
	 *            the opportunity_amount to set
	 */
	public void setOpportunity_amount(String opportunity_amount) {
		this.opportunity_amount = opportunity_amount;
	}

	/**
	 * @return the referred_by
	 */
	public String getReferred_by() {
		return referred_by;
	}

	/**
	 * @param referred_by
	 *            the referred_by to set
	 */
	public void setReferred_by(String referred_by) {
		this.referred_by = referred_by;
	}

	/**
	 * @return the campaign
	 */
	public Campaign getCampaign() {
		return campaign;
	}

	/**
	 * @param campaign
	 *            the campaign to set
	 */
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	/**
	 * @return the not_call
	 */
	public boolean isNot_call() {
		return not_call;
	}

	/**
	 * @param not_call
	 *            the not_call to set
	 */
	public void setNot_call(boolean not_call) {
		this.not_call = not_call;
	}

	/**
	 * @return the assigned_to
	 */
	public User getAssigned_to() {
		return assigned_to;
	}

	/**
	 * @param assigned_to
	 *            the assigned_to to set
	 */
	public void setAssigned_to(User assigned_to) {
		this.assigned_to = assigned_to;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return the opportunities
	 */
	public Set<Opportunity> getOpportunities() {
		return opportunities;
	}

	/**
	 * @param opportunities the opportunities to set
	 */
	public void setOpportunities(Set<Opportunity> opportunities) {
		this.opportunities = opportunities;
	}

	/**
	 * @return the targetLists
	 */
	public Set<TargetList> getTargetLists() {
		return targetLists;
	}

	/**
	 * @return the calls
	 */
	public Set<Call> getCalls() {
		return calls;
	}

	/**
	 * @param calls the calls to set
	 */
	public void setCalls(Set<Call> calls) {
		this.calls = calls;
	}

	/**
	 * @param targetLists the targetLists to set
	 */
	public void setTargetLists(Set<TargetList> targetLists) {
		this.targetLists = targetLists;
	}

	/**
	 * @return the meetings
	 */
	public Set<Meeting> getMeetings() {
		return meetings;
	}

	/**
	 * @param meetings the meetings to set
	 */
	public void setMeetings(Set<Meeting> meetings) {
		this.meetings = meetings;
	}

}
