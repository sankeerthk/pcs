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

@Entity
@Table(name = "PCS_LOGIN_ATTEMPT")
@Proxy(lazy = false)
public class LoginAttempt implements Serializable {

	private static final long serialVersionUID = 1L;

	private long attemptId;
	private String userName;
	private String status;
	private Date createdDate;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SEQ_PCS_LOGIN_ATTEMPT", allocationSize = 1)
	@Column(name = "ATTEMPT_ID", nullable = false)
	public long getAttemptId() {
		return this.attemptId;
	}

	public void setAttemptId(long attemptId) {
		this.attemptId = attemptId;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", insertable = false, updatable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + userName == null ? 0 : userName.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof LoginAttempt) {
			LoginAttempt loginAttempt = (LoginAttempt) obj;
			return this.attemptId == loginAttempt.attemptId;
		}
		return false;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Login [name=").append(userName).append("]").append("[id=").append(attemptId).append("]");
		return builder.toString();
	}
}
