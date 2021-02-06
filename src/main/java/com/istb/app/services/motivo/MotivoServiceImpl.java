package com.istb.app.services.motivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Motivo;
import com.istb.app.repository.MotivoRepository;
import com.istb.app.util.enums.TipoMotivo;

@Service
public class MotivoServiceImpl implements MotivoService {

	@Autowired
	MotivoRepository motivoRepository;
	
	@Override
	public void save(Motivo motivo) {
		motivoRepository.save(motivo);
		
	}

	@Override
	public Motivo findByTipo(TipoMotivo tipo) {
		Motivo motivo = motivoRepository.findByTipo(tipo);
		if (motivo != null) {
			return motivo;
		}
		return null;
	}

}
