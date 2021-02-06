package com.istb.app.services.inasistencia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Inasistencia;
import com.istb.app.repository.InasistenciaRepository;

@Service
public class InasistenciaServiceImpl implements InasistenciaService {
	
	@Autowired
	InasistenciaRepository inasistenciaRepository;
	
	@Override
	public void save(Inasistencia inasistencia) {
		inasistenciaRepository.save(inasistencia);

	}

	@Override
	public void update(Inasistencia inasistencia) {
		inasistenciaRepository.save(inasistencia);

	}
}
