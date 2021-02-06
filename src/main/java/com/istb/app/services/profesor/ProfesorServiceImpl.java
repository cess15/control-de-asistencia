package com.istb.app.services.profesor;

import java.util.List;
import java.util.Optional;

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

	@Override
	public void update(Profesor profesor) {
		profesorRepository.save(profesor);

	}

	@Override
	public void delete(int id) {
		profesorRepository.deleteById(id);

	}

}
