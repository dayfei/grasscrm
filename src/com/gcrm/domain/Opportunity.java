package com.gcrm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Opportunity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String name;
	private Account account;
	private Currency currency;
	private Date expect_close_date;
	private String opportunity_amount;
	private OpportunityType type;
	private SalesStage sales_stage;
	private LeadSource lead_source;
	private double probability;
	private Campaign campaign;
	private String next_step;
	private String description;
	private User assigned_to;
	private Set<Contact> contacts = new HashSet<Contact>(0);
	private Set<Lead> leads = new HashSet<Lead>(0);	
	private Set<Document> documents = new HashSet<Document>(0);	
	
	public Opportunity clone() {
		Opportunity o = null;
		try {
			o = (Opportunity) super.clone();
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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the currenccy
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * @param currenccy
	 *            the currenccy to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * @return the expect_close_date
	 */
	public Date getExpect_close_date() {
		return expect_close_date;
	}

	/**
	 * @param expect_close_date
	 *            the expect_close_date to set
	 */
	public void setExpect_close_date(Date expect_close_date) {
		this.expect_close_date = expect_close_date;
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
	 * @return the type
	 */
	public OpportunityType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(OpportunityType type) {
		this.type = type;
	}

	/**
	 * @return the probability
	 */
	public double getProbability() {
		return probability;
	}

	/**
	 * @param probability
	 *            the probability to set
	 */
	public void setProbability(double probability) {
		this.probability = probability;
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
	public void Probability(Campaign campaign) {
		this.campaign = campaign;
	}

	/**
	 * @return the next_step
	 */
	public String getNext_step() {
		return next_step;
	}

	/**
	 * @param next_step
	 *            the next_step to set
	 */
	public void setNext_step(String next_step) {
		this.next_step = next_step;
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
	 * @return the sales_stage
	 */
	public SalesStage getSales_stage() {
		return sales_stage;
	}


	/**
	 * @param sales_stage the sales_stage to set
	 */
	public void setSales_stage(SalesStage sales_stage) {
		this.sales_stage = sales_stage;
	}


	/**
	 * @return the lead_source
	 */
	public LeadSource getLead_source() {
		return lead_source;
	}


	/**
	 * @param lead_source the lead_source to set
	 */
	public void setLead_source(LeadSource lead_source) {
		this.lead_source = lead_source;
	}


	/**
	 * @param campaign the campaign to set
	 */
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
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

}
