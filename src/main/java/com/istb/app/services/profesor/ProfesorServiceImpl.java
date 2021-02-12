package com.istb.app.services.profesor;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Profesor;
import com.istb.app.repository.ProfesorRepository;

@Service
public class ProfesorServiceImpl implements ProfesorService {

	@Autowired
	ProfesorRepository profesorRepository;

	@Override
	public List<Profesor> findAll() {
		return profesorRepository.findAll();

	}

	@Override
	public Page<Profesor> findAll(Pageable pageable) {
		return profesorRepository.findAll(pageable);

	}

	@Override
	public Profesor findById(int id) {
		Optional<Profesor> profesor = profesorRepository.findById(id);
		if (profesor.isPresent()) {
			return profesor.get();
		}
		return null;
	}

	@Override
	public Profesor findByCedula(String cedula) {
		Profesor profesor = profesorRepository.findByCedula(cedula);
		if (profesor != null) {
			return profesor;
		}
		return null;

	}

	@Transactional
	@Override
	public void update(Profesor profesor, int id) {
		Profesor _profesor = profesorRepository
				.findById(id).orElse(null);
		
		if (!profesor.getCedula().isEmpty() && !profesor.getCedula().equals(_profesor.getCedula())) {
			_profesor.setCedula(profesor.getCedula());
		}
		
		if (!profesor.getNombres().isEmpty() && !profesor.getNombres().equals(_profesor.getNombres())) {
			_profesor.setNombres(profesor.getNombres());
		}
		
		if (!profesor.getApellidos().isEmpty() && !profesor.getApellidos().equals(_profesor.getApellidos())) {
			_profesor.setApellidos(profesor.getApellidos());
		}
		
		if (!profesor.getCorreo().isEmpty() && !profesor.getCorreo().equals(_profesor.getCorreo())) {
			_profesor.setCorreo(profesor.getCorreo());
		}
		
		if (!profesor.getTelefono().isEmpty() && !profesor.getTelefono().equals(_profesor.getTelefono())) {
			_profesor.setTelefono(profesor.getTelefono());
		}
		
		profesorRepository.save(_profesor);
	}

	@Override
	public void delete(int id) {
		// Elimina primero la asignación del rol del profesor
		// Luego el profesor
		// y Finalmente el usuario
		profesorRepository.deleteById(id);
	}

	@Override
	public void save(Profesor profesor) {
		profesorRepository.save(profesor);

	}
	
	@Override
	public Profesor findByCorreo(String correo) {
		Profesor profesor = profesorRepository.findByCedula(correo);
		if (profesor != null) {
			return profesor;
		}
		return null;
	}

}
