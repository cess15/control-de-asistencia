package com.istb.app.services.motivo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Motivo;
import com.istb.app.repository.MotivoRepository;

@Service
public class MotivoServiceImpl implements MotivoService {

	@Autowired
	MotivoRepository motivoRepository;

	@Override
	public void save(Motivo motivo) {
		motivoRepository.save(motivo);

	}

	@Override
	public Motivo findById(int id) {
		Optional<Motivo> motivo = motivoRepository.findById(id);
		if (motivo.isPresent()) {
			return motivo.get();
		}
		return null;
	}
}
