package com.gcrm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Document extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String fileName;
	private  byte[]  fileContent;
	private DocumentStatus status;
	private String name;
	private int revision;
	private Date publish_date;
	private Date expiration_date;
	private DocumentCategory category;
	private DocumentSubCategory sub_category;
	private String description;
	private Document related_document;
	private User assigned_to;
	private Set<Account> accounts = new HashSet<Account>(0);	
	private Set<Contact> contacts = new HashSet<Contact>(0);	
	private Set<Opportunity> opportunities = new HashSet<Opportunity>(0);
	private Set<Case> cases = new HashSet<Case>(0);

	public Document clone() {
		Document o = null;
		try {
			o = (Document) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	/**
	 * @return the status
	 */
	public DocumentStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(DocumentStatus status) {
		this.status = status;
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
	 * @return the revision
	 */
	public int getRevision() {
		return revision;
	}

	/**
	 * @param revision
	 *            the revision to set
	 */
	public void setRevision(int revision) {
		this.revision = revision;
	}

	/**
	 * @return the publish_date
	 */
	public Date getPublish_date() {
		return publish_date;
	}

	/**
	 * @param publish_date
	 *            the publish_date to set
	 */
	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
	}

	/**
	 * @return the expiration_date
	 */
	public Date getExpiration_date() {
		return expiration_date;
	}

	/**
	 * @param expiration_date
	 *            the expiration_date to set
	 */
	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}

	/**
	 * @return the category
	 */
	public DocumentCategory getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(DocumentCategory category) {
		this.category = category;
	}

	/**
	 * @return the sub_category
	 */
	public DocumentSubCategory getSub_category() {
		return sub_category;
	}

	/**
	 * @param sub_category
	 *            the sub_category to set
	 */
	public void setSub_category(DocumentSubCategory sub_category) {
		this.sub_category = sub_category;
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
	 * @return the related_document
	 */
	public Document getRelated_document() {
		return related_document;
	}

	/**
	 * @param related_document
	 *            the related_document to set
	 */
	public void setRelated_document(Document related_document) {
		this.related_document = related_document;
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
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

}
