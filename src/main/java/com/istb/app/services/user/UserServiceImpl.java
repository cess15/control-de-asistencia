package com.istb.app.services.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Periodo;
import com.istb.app.entities.Permiso;
import com.istb.app.entities.Usuario;
import com.istb.app.repository.InasistenciaRepository;
import com.istb.app.repository.PeriodoRepository;
import com.istb.app.repository.PermisoRepository;
import com.istb.app.repository.ProfesorRepository;
import com.istb.app.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProfesorRepository profesorRepository;

	@Autowired
	PermisoRepository permisoRepository;

	@Autowired
	InasistenciaRepository inasistenciaRepository;

	@Autowired
	PeriodoRepository periodoRepository;

	@Override
	public Usuario findByNombreUsuario(String nombreUsuario) {
		Usuario usuario = userRepository.findByNombreUsuario(nombreUsuario);
		if (usuario != null) {
			return usuario;
		}
		return null;

	}

	@Override
	public void delete(int id) {
		userRepository.deleteById(id);

	}

	@Override
	public void save(Usuario usuario) {
		userRepository.save(usuario);

	}

	@Override
	public void update(Usuario usuario) {
		userRepository.save(usuario);

	}

}
