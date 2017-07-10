package com.pcs.tracker.service;

import java.util.List;

import com.pcs.tracker.persistence.model.Role;

public interface RoleService {
	
	Role getRole(long id);

	
	@SuppressWarnings("rawtypes")
	List findAllRoles();
}
