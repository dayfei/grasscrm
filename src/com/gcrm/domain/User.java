package com.gcrm.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8250950813769457555L;

	private String name;
	private String first_name;
	private String last_name;
	private UserStatus status;
	private String password;
	private String title;
	private String email;
	private String mobile;
	private String phone;
	private String fax;
	private String department;
	private User report_to;
	private String mail_street;
	private String mail_city;
	private String mail_state;
	private String mail_postal_code;
	private String mail_country;
	private String other_street;
	private String other_city;
	private String other_state;
	private String other_postal_code;
	private String other_country;
	private Integer age;
	private String smtp_username;
	private String smtp_password;
	private String description;
	private Set<Role> roles = new HashSet<Role>(0);
	private Set<TargetList> targetLists = new HashSet<TargetList>(0);	
	private Set<Call> calls = new HashSet<Call>(0);
	private Set<Meeting> meetings = new HashSet<Meeting>(0);	

	public User clone() {
		User o = null;
		try {
			o = (User) super.clone();
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
	 * @return the status
	 */
	public UserStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(UserStatus status) {
		this.status = status;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return the report_to
	 */
	public User getReport_to() {
		return report_to;
	}

	/**
	 * @param report_to
	 *            the report_to to set
	 */
	public void setReport_to(User report_to) {
		this.report_to = report_to;
	}

	/**
	 * @return the mail_street
	 */
	public String getMail_street() {
		return mail_street;
	}

	/**
	 * @param mail_street
	 *            the mail_street to set
	 */
	public void setMail_street(String mail_street) {
		this.mail_street = mail_street;
	}

	/**
	 * @return the mail_city
	 */
	public String getMail_city() {
		return mail_city;
	}

	/**
	 * @param mail_city
	 *            the mail_city to set
	 */
	public void setMail_city(String mail_city) {
		this.mail_city = mail_city;
	}

	/**
	 * @return the mail_state
	 */
	public String getMail_state() {
		return mail_state;
	}

	/**
	 * @param mail_state
	 *            the mail_state to set
	 */
	public void setMail_state(String mail_state) {
		this.mail_state = mail_state;
	}

	/**
	 * @return the mail_postal_code
	 */
	public String getMail_postal_code() {
		return mail_postal_code;
	}

	/**
	 * @param mail_postal_code
	 *            the mail_postal_code to set
	 */
	public void setMail_postal_code(String mail_postal_code) {
		this.mail_postal_code = mail_postal_code;
	}

	/**
	 * @return the mail_country
	 */
	public String getMail_country() {
		return mail_country;
	}

	/**
	 * @param mail_country
	 *            the mail_country to set
	 */
	public void setMail_country(String mail_country) {
		this.mail_country = mail_country;
	}

	/**
	 * @return the other_street
	 */
	public String getOther_street() {
		return other_street;
	}

	/**
	 * @param other_street
	 *            the other_street to set
	 */
	public void setOther_street(String other_street) {
		this.other_street = other_street;
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
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	

	/**
	 * @return the smtp_username
	 */
	public String getSmtp_username() {
		return smtp_username;
	}

	/**
	 * @param smtp_username
	 *            the smtp_username to set
	 */
	public void setSmtp_username(String smtp_username) {
		this.smtp_username = smtp_username;
	}

	/**
	 * @return the smtp_password
	 */
	public String getSmtp_password() {
		return smtp_password;
	}

	/**
	 * @param smtp_password
	 *            the smtp_password to set
	 */
	public void setSmtp_password(String smtp_password) {
		this.smtp_password = smtp_password;
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
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

}
