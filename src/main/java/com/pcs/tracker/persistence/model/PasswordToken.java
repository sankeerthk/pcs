package com.pcs.tracker.persistence.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "PCS_PASSWORD_TOKEN")
@Proxy(lazy = false)
public class PasswordToken {

	private static final int EXPIRATION = 60 * 24;

	private Long passwordTokenId;
	private String token;
	private UserProfile userProfile;
	private Date expiryDate;
	private boolean enabled;
	private Date createdDate;
	private long createdBy;
	private Date lastUpdatedDate;
	private long lastUpdatedBy;

	public PasswordToken() {
		super();
	}

	public PasswordToken(final String token) {
		super();

		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public PasswordToken(final String token, final UserProfile userProfile) {
		super();

		this.token = token;
		this.userProfile = userProfile;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_PCS_PASSWORD_TOKEN", allocationSize = 1)
	@Column(name = "TOKEN_ID", nullable = false)
	public Long getPasswordTokenId() {
		return passwordTokenId;
	}

	public void setPasswordTokenId(Long passwordTokenId) {
		this.passwordTokenId = passwordTokenId;
	}

	@Column(name = "TOKEN")
	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_PROFILE_ID")
	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(final UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE")
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(final Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name = "IS_ENABLED", nullable = false, insertable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((userProfile == null) ? 0 : userProfile.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PasswordToken other = (PasswordToken) obj;
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiryDate).append("]");
		return builder.toString();
	}

}
