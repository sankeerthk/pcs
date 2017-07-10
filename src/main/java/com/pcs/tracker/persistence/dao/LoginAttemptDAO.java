package com.pcs.tracker.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcs.tracker.persistence.model.LoginAttempt;

public interface LoginAttemptDAO extends JpaRepository<LoginAttempt, Long> {

}
