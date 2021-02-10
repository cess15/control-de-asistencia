package com.istb.app.services.permiso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Permiso;
import com.istb.app.repository.PermisoRepository;

@Service
public class PermisoServiceImpl implements PermisoService{

	@Autowired
	PermisoRepository permisoRepository;
	
	@Override
	public void save(Permiso permiso) {
		permisoRepository.save(permiso);

	}
	
	@Override
	public void update(Permiso permiso) {
		permisoRepository.save(permiso);

	}
}
