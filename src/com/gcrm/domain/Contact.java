package com.gcrm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Contact extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String first_name;
	private String last_name;
	private String office_phone;
	private String title;
	private String mobile;
	private String department;
	private String fax;
	private Account account;
	private String website;
	private String mailing_address;
	private String mailing_city;
	private String mailing_state;
	private String mailing_postal_code;
	private String mailing_country;
	private String other_address;
	private String other_city;
	private String other_state;
	private String other_postal_code;
	private String other_country;
	private String email;
	private String description;
	private Contact report_to;
	private boolean sync_outlook;
	private boolean not_call;
	private LeadSource leadSource;
	private Campaign campaign;
	private User assigned_to;
	private Set<Opportunity> opportunities = new HashSet<Opportunity>(0);
	private Set<Lead> leads = new HashSet<Lead>(0);
	private Set<TargetList> targetLists = new HashSet<TargetList>(0);
	private Set<Case> cases = new HashSet<Case>(0);	
	private Set<Call> calls = new HashSet<Call>(0);
	private Set<Meeting> meetings = new HashSet<Meeting>(0);	
	private Set<Document> documents = new HashSet<Document>(0);		

	public Contact clone() {
		Contact o = null;
		try {
			o = (Contact) super.clone();
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
	 * @return the report_to
	 */
	public Contact getReport_to() {
		return report_to;
	}

	/**
	 * @param report_to
	 *            the report_to to set
	 */
	public void setReport_to(Contact report_to) {
		this.report_to = report_to;
	}

	/**
	 * @return the sync_outlook
	 */
	public boolean isSync_outlook() {
		return sync_outlook;
	}

	/**
	 * @param sync_outlook
	 *            the sync_outlook to set
	 */
	public void setSync_outlook(boolean sync_outlook) {
		this.sync_outlook = sync_outlook;
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
	 * @return the leadSource
	 */
	public LeadSource getLeadSource() {
		return leadSource;
	}

	/**
	 * @param leadSource
	 *            the leadSource to set
	 */
	public void setLeadSource(LeadSource leadSource) {
		this.leadSource = leadSource;
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
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
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
	 * @return the targetLists
	 */
	public Set<TargetList> getTargetLists() {
		return targetLists;
	}

	/**
	 * @param targetLists the targetLists to set
	 */
	public void setTargetLists(Set<TargetList> targetLists) {
		this.targetLists = targetLists;
	}

	/**
	 * @return the cases
	 */
	public Set<Case> getCases() {
		return cases;
	}

	/**
	 * @param cases the cases to set
	 */
	public void setCases(Set<Case> cases) {
		this.cases = cases;
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

	/**
	 * @return the documents
	 */
	public Set<Document> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}

	/**
	 * @return the mailing_address
	 */
	public String getMailing_address() {
		return mailing_address;
	}

	/**
	 * @param mailing_address the mailing_address to set
	 */
	public void setMailing_address(String mailing_address) {
		this.mailing_address = mailing_address;
	}

	/**
	 * @return the mailing_city
	 */
	public String getMailing_city() {
		return mailing_city;
	}

	/**
	 * @param mailing_city the mailing_city to set
	 */
	public void setMailing_city(String mailing_city) {
		this.mailing_city = mailing_city;
	}

	/**
	 * @return the mailing_state
	 */
	public String getMailing_state() {
		return mailing_state;
	}

	/**
	 * @param mailing_state the mailing_state to set
	 */
	public void setMailing_state(String mailing_state) {
		this.mailing_state = mailing_state;
	}

	/**
	 * @return the mailing_postal_code
	 */
	public String getMailing_postal_code() {
		return mailing_postal_code;
	}

	/**
	 * @param mailing_postal_code the mailing_postal_code to set
	 */
	public void setMailing_postal_code(String mailing_postal_code) {
		this.mailing_postal_code = mailing_postal_code;
	}

	/**
	 * @return the mailing_country
	 */
	public String getMailing_country() {
		return mailing_country;
	}

	/**
	 * @param mailing_country the mailing_country to set
	 */
	public void setMailing_country(String mailing_country) {
		this.mailing_country = mailing_country;
	}

}
