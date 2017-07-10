package com.pcs.tracker.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pcs.tracker.persistence.model.Role;
import com.pcs.tracker.persistence.model.UserProfile;

public interface UserProfileDAO extends JpaRepository<UserProfile, Long>{

	UserProfile findByUserProfileId(long id);
	
	UserProfile findByUserName(String userName);

	@Query("select up from UserProfile up where up.enabled = true")
	List<UserProfile> findAllEnabledUsers(UserProfile user);
	
	//void lockUser(String userName);

	@Query("select up from UserProfile up where up.role = :role and up.enabled = true")
	List<UserProfile> enabledAdminUser(@Param("role")Role role);

	
	

}
