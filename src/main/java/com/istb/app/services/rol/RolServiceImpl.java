package com.istb.app.services.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Role;
import com.istb.app.repository.RoleRepository;

@Service
public class RolServiceImpl implements RolService {

	@Autowired
	RoleRepository rolRepository;

	@Override
	public Role findByNombre(String nombre) {
		Role rol = rolRepository.findByNombre(nombre);
		if (rol != null) {
			return rol;
		}
		return null;

	}
}
