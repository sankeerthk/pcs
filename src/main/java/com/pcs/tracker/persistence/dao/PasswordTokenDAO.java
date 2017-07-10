package com.pcs.tracker.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pcs.tracker.persistence.model.PasswordToken;

public interface PasswordTokenDAO extends JpaRepository<PasswordToken, Long> {

	PasswordToken findByToken(String token);

	@Query("update PasswordToken pt set pt.enabled = 0 where pt.userProfile.userProfileId = :id and pt.enabled = 1")
	void disableTokens(@Param("id")long id);

}
