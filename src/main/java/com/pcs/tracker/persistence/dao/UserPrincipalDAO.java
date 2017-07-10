package com.pcs.tracker.persistence.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pcs.tracker.persistence.model.UserPrincipal;

public interface UserPrincipalDAO extends JpaRepository<UserPrincipal, Long> {

	@Query(value="delete from UserPrincipal up where up.userPrincipalId  in (select up1 from UserPrincipal up1 "
			+ "where up1.userProfileId = :userProfileId  order by up1.createdDate desc Offset 23 ROWS)",nativeQuery = true)
	void deleteOldPrincipals(@Param("userProfileId")long userProfileId);

	@Query("select tup from UserPrincipal tup where tup.userProfileId = :userProfileId and tup.current = true")
	UserPrincipal getActivePrincipalForUser(@Param("userProfileId")long userProfileId);

	@Query("select tup from UserPrincipal tup where tup.userProfileId = :userProfileId")
	List<UserPrincipal> getAllPrincipalsForUser(@Param("userProfileId")long id);

}
