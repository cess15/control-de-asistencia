package com.istb.app.services.adjunto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Adjunto;
import com.istb.app.repository.AdjuntoRepository;

@Service
public class AdjuntoServiceImpl implements AdjuntoService {

	@Autowired
	AdjuntoRepository adjuntoRepository;

	@Override
	public void save(Adjunto adjunto) {
		adjuntoRepository.save(adjunto);

	}

}
