package com.gcrm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Account extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String name;
	private String office_phone;
	private String website;
	private String fax;
	private String bill_street;
	private String bill_city;
	private String bill_state;
	private String bill_postal_code;
	private String bill_country;
	private String ship_street;
	private String ship_city;
	private String ship_state;
	private String ship_postal_code;
	private String ship_country;
	private String email;
	private String description;
	private AccountType account_type;
	private Industry industry;
	private String annual_revenue;
	private String employees;
	private String sic_code;
	private String ticket_symbol;
	private Account manager;
	private String ownship;
	private String rating;
	private User assigned_to;
	private Set<TargetList> targetLists = new HashSet<TargetList>(0);
	private Set<Document> documents = new HashSet<Document>(0);	

	public Account clone() {
		Account o = null;
		try {
			o = (Account) super.clone();
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
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
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
	 * @return the bill_street
	 */
	public String getBill_street() {
		return bill_street;
	}

	/**
	 * @param bill_street
	 *            the bill_street to set
	 */
	public void setBill_street(String bill_street) {
		this.bill_street = bill_street;
	}

	/**
	 * @return the bill_city
	 */
	public String getBill_city() {
		return bill_city;
	}

	/**
	 * @param bill_city
	 *            the bill_city to set
	 */
	public void setBill_city(String bill_city) {
		this.bill_city = bill_city;
	}

	/**
	 * @return the bill_state
	 */
	public String getBill_state() {
		return bill_state;
	}

	/**
	 * @param bill_state
	 *            the bill_state to set
	 */
	public void setBill_state(String bill_state) {
		this.bill_state = bill_state;
	}

	/**
	 * @return the bill_postal_code
	 */
	public String getBill_postal_code() {
		return bill_postal_code;
	}

	/**
	 * @param bill_postal_code
	 *            the bill_postal_code to set
	 */
	public void setBill_postal_code(String bill_postal_code) {
		this.bill_postal_code = bill_postal_code;
	}

	/**
	 * @return the bill_country
	 */
	public String getBill_country() {
		return bill_country;
	}

	/**
	 * @param bill_country
	 *            the bill_country to set
	 */
	public void setBill_country(String bill_country) {
		this.bill_country = bill_country;
	}

	/**
	 * @return the ship_street
	 */
	public String getShip_street() {
		return ship_street;
	}

	/**
	 * @param ship_street
	 *            the ship_street to set
	 */
	public void setShip_street(String ship_street) {
		this.ship_street = ship_street;
	}

	/**
	 * @return the ship_city
	 */
	public String getShip_city() {
		return ship_city;
	}

	/**
	 * @param ship_city
	 *            the ship_city to set
	 */
	public void setShip_city(String ship_city) {
		this.ship_city = ship_city;
	}

	/**
	 * @return the ship_state
	 */
	public String getShip_state() {
		return ship_state;
	}

	/**
	 * @param ship_state
	 *            the ship_state to set
	 */
	public void setShip_state(String ship_state) {
		this.ship_state = ship_state;
	}

	/**
	 * @return the ship_postal_code
	 */
	public String getShip_postal_code() {
		return ship_postal_code;
	}

	/**
	 * @param ship_postal_code
	 *            the ship_postal_code to set
	 */
	public void setShip_postal_code(String ship_postal_code) {
		this.ship_postal_code = ship_postal_code;
	}

	/**
	 * @return the ship_country
	 */
	public String getShip_country() {
		return ship_country;
	}

	/**
	 * @param ship_country
	 *            the ship_country to set
	 */
	public void setShip_country(String ship_country) {
		this.ship_country = ship_country;
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
	 * @return the account_type
	 */
	public AccountType getAccount_type() {
		return account_type;
	}

	/**
	 * @param account_type
	 *            the account_type to set
	 */
	public void setAccount_type(AccountType account_type) {
		this.account_type = account_type;
	}

	/**
	 * @return the industry
	 */
	public Industry getIndustry() {
		return industry;
	}

	/**
	 * @param industry
	 *            the industry to set
	 */
	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	/**
	 * @return the annual_revenue
	 */
	public String getAnnual_revenue() {
		return annual_revenue;
	}

	/**
	 * @param annual_revenue
	 *            the annual_revenue to set
	 */
	public void setAnnual_revenue(String annual_revenue) {
		this.annual_revenue = annual_revenue;
	}

	/**
	 * @return the employees
	 */
	public String getEmployees() {
		return employees;
	}

	/**
	 * @param employees
	 *            the employees to set
	 */
	public void setEmployees(String employees) {
		this.employees = employees;
	}

	/**
	 * @return the sic_code
	 */
	public String getSic_code() {
		return sic_code;
	}

	/**
	 * @param sic_code
	 *            the sic_code to set
	 */
	public void setSic_code(String sic_code) {
		this.sic_code = sic_code;
	}

	/**
	 * @return the ticket_symbol
	 */
	public String getTicket_symbol() {
		return ticket_symbol;
	}

	/**
	 * @param ticket_symbol
	 *            the ticket_symbol to set
	 */
	public void setTicket_symbol(String ticket_symbol) {
		this.ticket_symbol = ticket_symbol;
	}

	/**
	 * @return the manager
	 */
	public Account getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(Account manager) {
		this.manager = manager;
	}

	/**
	 * @return the ownship
	 */
	public String getOwnship() {
		return ownship;
	}

	/**
	 * @param ownship
	 *            the ownship to set
	 */
	public void setOwnship(String ownship) {
		this.ownship = ownship;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
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
