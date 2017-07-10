package com.pcs.tracker.persistence.model;


import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

@Entity
@Table(name="PCS_USER_PROFILE")
@Proxy(lazy=false)
public class UserProfile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long userProfileId;
	private String userName;
	private String password;
	private Date passwordLastChanged;
	private boolean enabled;
	private boolean accountLocked;
	private UserDetails userDetails;
	private Role role;
	private Date createdDate;
	private long createdBy;
	private Date lastUpdatedDate;
	private long lastUpdatedBy;
	
	public UserProfile(){
		super();
	}
	
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_PCS_USER_PROFILE", allocationSize = 1)
	@Column(name="USER_PROFILE_ID")
	public long getUserProfileId() {
		return userProfileId;
	}
	public void setUserProfileId(long userProfileId) {
		this.userProfileId = userProfileId;
	}
	
	@Column(name="USER_NAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PASSWORD_LAST_CHANGED")
	public Date getPasswordLastChanged() {
		return passwordLastChanged;
	}
	public void setPasswordLastChanged(Date passwordLastChanged) {
		this.passwordLastChanged = passwordLastChanged;
	}
	
	
	@Column(name="IS_ENABLED")
	@Type(type="org.hibernate.type.NumericBooleanType")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Column(name="IS_ACCOUNT_LOCKED")
	@Type(type="org.hibernate.type.NumericBooleanType")
	public boolean isAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_DETAILS_ID")
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
	@ManyToOne(optional = false,fetch=FetchType.EAGER)
	@JoinColumn(name="ROLE_ID")
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
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
