package com.pcs.tracker.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcs.tracker.persistence.model.VerificationToken;

public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);

}
