package com.pcs.tracker.persistence.model;


import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

@Entity
@Table(name="PCS_USER_DETAILS")
@Proxy(lazy=false)
public class UserDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long userDeatilsId;
	private String firstName;
	private String lastName;
	private String title;
	private String workEmail;
	private String personalEmail;
	private String workPhoneNumber;
	private String personalPhoneNumber;
	private String status;
	private boolean employed;
	private Date createdDate;
	private long createdBy;
	private Date lastUpdatedDate;
	private long lastUpdatedBy;
	
	private UserDetails(){
		super();
	}
	
	@Id
	@GeneratedValue(strategy=SEQUENCE,generator="generator")
	@SequenceGenerator(name="generator",sequenceName="SEQ_PCS_USER_DETAILS",allocationSize=1)
	@Column(name="USER_DETAILS_ID")
	public long getUserDeatilsId() {
		return userDeatilsId;
	}
	public void setUserDeatilsId(long userDeatilsId) {
		this.userDeatilsId = userDeatilsId;
	}
	
	@Column(name="FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name="LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name="TITLE")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="WORK_EMAIL")
	public String getWorkEmail() {
		return workEmail;
	}
	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}
	
	@Column(name="PERSONAL_EMAIL")
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	
	@Column(name="WORK_PHONE_NUMBER")
	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}
	public void setWorkPhoneNumber(String workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}
	
	@Column(name="PERSONAL_PHONE_NUMBER")
	public String getPersonalPhoneNumber() {
		return personalPhoneNumber;
	}
	public void setPersonalPhoneNumber(String personalPhoneNumber) {
		this.personalPhoneNumber = personalPhoneNumber;
	}
	
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="IS_EMPLOYED")
	@Type(type="org.hibernate.type.NumericBooleanType")
	public boolean isEmployed() {
		return employed;
	}
	public void setEmployed(boolean employed) {
		this.employed = employed;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="CREATED_BY")
	public long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="LAST_UPDATED_DATE")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@Column(name="LAST_UPDATED_BY")
	public long getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
}
