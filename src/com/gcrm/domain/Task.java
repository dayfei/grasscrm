package com.gcrm.domain;

import java.io.Serializable;
import java.util.Date;

public class Task extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String subject;
	private TaskStatus status;
	private Date start_date;
	private Date due_date;
	private String related_object;
	private Integer related_record;
	private Contact contact;
	private TaskPriority priority;
	private String description;
	private User assigned_to;

	public Task clone() {
		Task o = null;
		try {
			o = (Task) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the status
	 */
	public TaskStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	/**
	 * @return the start_date
	 */
	public Date getStart_date() {
		return start_date;
	}

	/**
	 * @param start_date
	 *            the start_date to set
	 */
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	/**
	 * @return the due_date
	 */
	public Date getDue_date() {
		return due_date;
	}

	/**
	 * @param due_date
	 *            the due_date to set
	 */
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	/**
	 * @return the related_object
	 */
	public String getRelated_object() {
		return related_object;
	}

	/**
	 * @param related_object
	 *            the related_object to set
	 */
	public void setRelated_object(String related_object) {
		this.related_object = related_object;
	}

	/**
	 * @return the related_record
	 */
	public Integer getRelated_record() {
		return related_record;
	}

	/**
	 * @param related_record
	 *            the related_record to set
	 */
	public void setRelated_record(Integer related_record) {
		this.related_record = related_record;
	}

	/**
	 * @return the contact
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	/**
	 * @return the priority
	 */
	public TaskPriority getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(TaskPriority priority) {
		this.priority = priority;
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

}
