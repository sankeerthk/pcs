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
@Table(name="PCS_USER_PRINCIPAL")
@Proxy(lazy= false)
public class UserPrincipal implements Serializable {

	private static final long serialVersionUID = 1L;

	private long userPrincipalId;
	private long userProfileId;
	private String password;
	private boolean isCurrent;
	private Date createdDate;
	private long createdBy;
	private Date lastUpdatedDate;
	private long lastUpdatedBy;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_USER_PRINCIPAL", allocationSize = 1)
	@Column(name = "USER_PRINCIPAL_ID")
	public long getUserPrincipalId() {
		return userPrincipalId;
	}
	
	public void setUserPrincipalId(long userPrincipalId) {
		this.userPrincipalId = userPrincipalId;
	}

	@Column(name = "USER_PROFILE_ID")
	public long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(long userProfileId) {
		this.userProfileId = userProfileId;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "IS_CURRENT", insertable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isCurrent() {
		return isCurrent;
	}

	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", insertable = false, updatable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "CREATED_BY")
	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATED_DATE", insertable = false)
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Column(name = "LAST_UPDATED_BY")
	public long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserPrincipal [id=").append(userPrincipalId).append(", userProfileId=").append(userProfileId)
		.append(", currentFlag=").append(isCurrent).append("]");
		return builder.toString();
	}

}
