package com.istb.app.services.profesor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Periodo;
import com.istb.app.entities.PeriodoProfesor;
import com.istb.app.entities.Profesor;
import com.istb.app.entities.Role;
import com.istb.app.repository.PeriodoProfesorRepository;
import com.istb.app.repository.PeriodoRepository;
import com.istb.app.repository.ProfesorRepository;
import com.istb.app.services.rol.RolService;
import com.istb.app.services.user.UserService;

@Service
public class ProfesorServiceImpl implements ProfesorService {

	@Autowired
	UserService userService;

	@Autowired
	RolService rolService;

	@Autowired
	ProfesorRepository profesorRepository;

	@Autowired
	ProfesorService profesorService;

	@Autowired
	PeriodoRepository periodoRepository;

	@Autowired
	PeriodoProfesorRepository periodoProfesorRepository;

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
	public Map<String, String> update(Profesor profesor, int id) {
		Profesor _profesor = profesorRepository.findById(id).orElse(null);
		Map<String, String> errorAttributes = new HashMap<>();

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
			boolean existeCorreo = profesorRepository.findByCorreo(profesor.getCorreo()) != null;
			if (existeCorreo) {

				errorAttributes.put("correo",
						"Usuario con este correo: ".concat(profesor.getCorreo()).concat(" ya existe"));
				errorAttributes.put("clase", "text-danger");

				return errorAttributes;

			} else {
				_profesor.setCorreo(profesor.getCorreo());
			}
		}

		if (!profesor.getTelefono().isEmpty() && !profesor.getTelefono().equals(_profesor.getTelefono())) {
			_profesor.setTelefono(profesor.getTelefono());
		}

		if (!errorAttributes.isEmpty()) {
			return errorAttributes;
		}

		profesorService.save(profesor).forEach((k, v) -> errorAttributes.put(k, v));
		return errorAttributes;

	}

	@Override
	public void delete(int id) {

		Optional<Profesor> profesor = profesorRepository.findById(id);
		profesor.ifPresent(p -> profesorRepository.deleteById(p.getId()));

	}

	@Override
	public Map<String, String> save(Profesor profesor) {

		Map<String, String> errorAttributes = new HashMap<>();
		boolean existeCorreo = profesorRepository.findByCorreo(profesor.getCorreo()) != null;

		if (existeCorreo) {

			errorAttributes.put("correo",
					"Usuario con este correo: ".concat(profesor.getCorreo()).concat(" ya existe"));
			errorAttributes.put("clase", "text-danger");

			return errorAttributes;

		}

		PeriodoProfesor periodoProfesor = new PeriodoProfesor();
		Role rol = rolService.findByNombre("Docente");
		Periodo periodo = periodoRepository.findByVigente(true);
		profesor.getUsuario().addRol(rol);
		
		

		userService.save(profesor.getUsuario()).forEach((k, v) -> errorAttributes.put(k, v));

		if (!errorAttributes.isEmpty()) {
			return errorAttributes;
		}

		profesorRepository.save(profesor);
		
		periodoProfesor.setPeriodo(periodo);
		periodoProfesor.setProfesor(profesor);
		periodoProfesor.setDiasFaltados(0);
		periodoProfesorRepository.save(periodoProfesor);

		return errorAttributes;

	}

}
