package com.pcs.tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pcs.tracker.persistence.dao.RoleDAO;
import com.pcs.tracker.persistence.model.Role;

@Service("roleService")
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDAO dao;

	@Override
	public Role getRole(long id) {
		return dao.findByRoleId(id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List findAllRoles() {
		return dao.findAll();
	}
}
