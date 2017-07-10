package com.pcs.tracker.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pcs.tracker.persistence.model.Role;

public interface RoleDAO extends JpaRepository<Role,Long>{

	Role findByRoleId(long id);

}
